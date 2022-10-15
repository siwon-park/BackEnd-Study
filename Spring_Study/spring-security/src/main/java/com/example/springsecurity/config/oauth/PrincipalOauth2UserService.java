package com.example.springsecurity.config.oauth;

import com.example.springsecurity.config.auth.PrincipalDetails;
import com.example.springsecurity.config.oauth.provider.FacebookUserInfo;
import com.example.springsecurity.config.oauth.provider.GoogleUserInfo;
import com.example.springsecurity.config.oauth.provider.NaverUserInfo;
import com.example.springsecurity.config.oauth.provider.Oauth2UserInfo;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    // 구글 로그인 후 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override  // 함수 종료 시 @AuthenticationPrinciapl 어노테이션이 만들어진다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getClientRegistration()); // 서버에 들어오는 기본 정보 => 어떤 Oauth로 들어오는지 확인
        System.out.println(userRequest.getAccessToken().getTokenValue()); // 액세스 토큰 정보
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 return (Oauth-Client 라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출(구글로 부터 회원 프로필을 받아옴)
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // Oauth2.0 회원가입
        Oauth2UserInfo oauth2UserInfo = null;
        String reqProvider = userRequest.getClientRegistration().getRegistrationId();
        if (reqProvider.equals("google")) {
            oauth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (reqProvider.equals("facebook")) {
            oauth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (reqProvider.equals("naver")) {
            oauth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response")); // 네이버는 response를 키로 가지는 객체를 리턴하기 때문에 이렇게 하는 것임
        }

        String provider = oauth2UserInfo.getProvider(); // google, facebook, naver
        String providerId = oauth2UserInfo.getProviderId(); // getAttributes와 getAttribute는 다름!!
        String username = provider + "_" + providerId; // google_12321512512;
        String password = bCryptPasswordEncoder.encode("그냥 설정하는 의미없는 비밀번호");
        String email = oauth2UserInfo.getEmail();
        String role = "ROLE_USER";

//        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
//        String providerId = oAuth2User.getAttribute("sub"); // getAttributes와 getAttribute는 다름!!
//        String username = provider + "_" + providerId; // google_12321512512;
//        String password = bCryptPasswordEncoder.encode("그냥 설정하는 의미없는 비밀번호");
//        String email = oAuth2User.getAttribute("email");
//        String role = "ROLE_USER";

        // 회원가입 중복 검사
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
//        return super.loadUser(userRequest);
    }
}
