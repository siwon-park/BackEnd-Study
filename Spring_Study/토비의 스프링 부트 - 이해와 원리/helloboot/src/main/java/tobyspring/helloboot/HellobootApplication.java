package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		// 스프링 컨테이너 생성 -> 어플리케이션 구성 정보
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				// 디스패처 서블릿
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this) // GenericWebApplicationContext 클래스를 호출해야만 사용 가능
					).addMapping("/*");
				});
				webServer.start();
			}
		};
		applicationContext.registerBean(HelloController.class); // 클래스 정보를 Bean에 담아 등록함
		/*
		* 그런데 스프링 컨테이너는 어떻게 HelloController가 SimpleHelloService를 사용할 것인지 알 수 있는가?
		* HelloController를 보면 생성자를 통해 HelloService라는 인터페이스를 주입받는다.
		* 예전에는 이를 xml로 context 정보에 등록했었지만, 이제는 많은 부분이 간소화되었다.
		* 스프링 컨테이너는 Bean으로 등록되어 있는 클래스 중 HelloService를 구현한 클래스가 있는지 전부 다 뒤져서 찾아본 다음
		* 의존성을 주입한다.
		* */
		applicationContext.registerBean(SimpleHelloService.class); // 명확하게 어떤 클래스인지 등록해줘야 함
		applicationContext.refresh(); // 빈을 생성(초기화) -> 스프링 컨테이너를 초기화

		// 톰캣 서버 시작 -> 그러나 실제 톰캣을 설치해서 보면 많은 설정이 필요한 것처럼
		// .start()만으로 실행하기는 준비해야할 것이 많아서 쉽지 않다.
//		new Tomcat().start();

		// TomcatServletWebServerFactory; 스프링부트가 톰캣 서블릿 컨테이너를 쉽게 띄울 수 있도록 만든 클래스
		// TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//		WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer() {
//			@Override
//			public void onStartup(ServletContext servletContext) throws ServletException {
//
//			}
//		});

		/* 일반 서블릿 컨테이너 -> 그러나 모든 요청에 대해 mapping해주기엔 한계가 있음 -> 프론트 컨트롤러로 전환
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					String name = req.getParameter("name"); // name = 이라는 변수명으로 들어오는 파라미터를 받는다
					// 웹 request에 따른 응답
					resp.setStatus(HttpStatus.OK.value()); // 생략 가능
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // "Content-Type", "text/plain"을 직접 타이핑 하기보다 ENUM 클래스 활용
					resp.getWriter().println("Hello Servlet " + name);
				}
			}).addMapping("/hello"); // "/hello"로 들어오는 요청을 HttpServlet 익명 클래스에서 처리하겠다는 의미

		});
		*/
		
		// 프론트 컨트롤러
//		WebServer webServer = serverFactory.getWebServer(servletContext -> {
////			HelloController helloController = new HelloController(); // HelloController 생성
//			servletContext.addServlet("frontcontroller", new HttpServlet() {
//				@Override
//				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
//						String name = req.getParameter("name");
//
//						HelloController helloController = applicationContext.getBean(HelloController.class); // Bean으로 받음
//						String ret = helloController.hello(name);
//						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
//						resp.getWriter().println(ret);
//					} else if (req.getRequestURI().equals("/user")) {
//						//
//					} else {
//						resp.setStatus(HttpStatus.NOT_FOUND.value()); // 404반환
//					}
//				}
//			}).addMapping("/*"); // "/hello"로 들어오는 요청을 HttpServlet 익명 클래스에서 처리하겠다는 의미
//
//		});


	}

}
