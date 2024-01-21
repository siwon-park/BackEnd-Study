package tobyspring.study;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
        // isSameAs; 주솟값까지 완전히 같은 오브젝트인지 확인
        // Assertions.assertThat(new Common()).isSameAs(new Common()); // 다른 오브젝트임

        Common common = new Common();
        Assertions.assertThat(common).isSameAs(common); // 미리 생성한 것을 비교하면 당연히 같은 오브젝트임

        // 그냥 bean1과 bean2를 생성해서 비교해도 역시나 다른 오브젝트이지만, 스프링컨텍스트로 등록하면 같은 오브젝트임
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();
        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Test
    void proxyCommonMethodTest() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;
        @Override
        Common common() {
            if (this.common == null) this.common = super.common();
            return this.common;
        }
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Common  {

    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }


}
