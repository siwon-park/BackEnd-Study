package com.example.springsecurity.config.auth;

// 시큐리티가 /login 주소 요청을 낚아채서 로그인을 진행시킴
// 로그인 진행이 안료되면 시큐리티가 갖고 있는 시큐리티 session을 만들어줌 (Security ContextHolder 키값을 이용해 세션 정보를 저장)
// 세션에 들어갈 수 있는 정보, 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함
// User 오브젝트 타입 => UserDetails 타입 객체
// Security Session에 들어갈 수 있는 타입이 Authentication 타입의 객체만들어가기 때문에 PrincipalDetails를 만듦

// Security Session => Authentication => UserDetails 타입 (PrincipalDetails)

import com.example.springsecurity.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    } // 일반 로그인 시 사용하는 객체

    public PrincipalDetails(User user, Map<String, Object> attributes) { // Oauth 로그인 시 사용하는 객체
        this.user = user;
        this.attributes = attributes;
    }
    @Override // 해당 User의 권한을 리턴하는 곳
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // user.getRole() // String이기 때문에 해당 값으로 반환 불가능
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override // String으로 반환 가능
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override // 계정 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호 만료 여부
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정 활성화 여부
    public boolean isEnabled() {
        // 1년 동안 회원이 로그인을 안 하면 휴면 계정으로 전환시킴
        // 현재 시간 - 최종 로그인 시간이 1년을 초과하면 return false를 하면 됨
        return true;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
