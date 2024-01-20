package tobyspring.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringBootApplication {

    public static void run(Class<?> applicationClass, String... args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class); //new TomcatServletWebServerFactory();
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                dispatcherServlet.setApplicationContext(this); // 이 메서드는 호출하지 않아도 됨 -> 왜냐하면 DispatcherServlet이라는 클래스가 ApplicationContextAware라는 인터페이스를 구현해서 사용하고 있기 때문
                // 디스패처 서블릿
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this) // GenericWebApplicationContext 클래스를 호출해야만 사용 가능
                    ).addMapping("/*");
                });
                webServer.start();
            }
        };

        applicationContext.register(applicationClass);
        applicationContext.refresh(); // 빈을 생성(초기화) -> 스프링 컨테이너를 초기화
    }
}
