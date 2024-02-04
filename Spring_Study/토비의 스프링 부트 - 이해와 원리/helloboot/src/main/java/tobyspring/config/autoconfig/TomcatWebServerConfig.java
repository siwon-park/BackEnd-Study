package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;

@Configuration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
//@Conditional(TomcatWebServerConfig.TomcatCondition.class)
public class TomcatWebServerConfig {
    @Bean("TomcatWebServerFactory")
    @ConditionalOnMissingBean // 해당 타입으로 스프링 빈이 등록된 것이 없으면 등록함 -> 사용자가 직접 작성하여 등록한게 있으면 직접 작성한 것을 우선시 하기 위함
    /*
    * 단, ConditionalOnMissingBean을 적용할 때는 Configuration 클래스의 적용 순서가 중요하다.
    * 컨테이너에 등록된 빈 정보를 기준으로 체크하기 때문에 그렇다.
    * 개발자가 직접 정의한 커스텀 빈 구성 정보는 자동 구성 정보보다 우선 처리되지만,
    * 자동 구성 정보에서 이를 사용할 경우에는 나중에 등록될 빈에 포함되어 등록될 예정인데
    * Configuration 순서가 더 앞서서 먼저 등록되는 일이 발생하여 빈 오브젝트 등록에 문제가 생긴다.
    * */
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            // Tomcat과 관련된 라이브러리가 있다면 Tomcat을 실행하고, 아니면 실행하지 않음
//            return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat",
//                    context.getClassLoader());
//        }
//    }
}
