package tobyspring.helloboot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tobyspring.config.MySpringBootAnnotation;

//@Configuration // @Bean 메서드가 있는 클래스임을 명시하여 스프링 컨테이너가 이를 인식하여 빈 오브젝트를 생성함 구성 정보를 가진 클래스
//@ComponentScan
@MySpringBootAnnotation
public class HellobootApplication {
//	@Bean
//	ApplicationRunner applicationRunner(Environment env) {
//		return args -> {
//			String name = env.getProperty("my.name");
//			System.out.println("my.name: " + name);
//		};
//	}

//
//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		return new TomcatServletWebServerFactory();
//	}
//
//	@Bean
//	public DispatcherServlet dispatcherServlet() {
//		return new DispatcherServlet();
//	}

//	@Bean // 빈 오브젝트를 생성하는 팩토리 메서드임을 명시 -> 상위 클래스에는 @Configuration을 붙여줘야 함
//	public HelloController helloController(HelloService helloService) {
//		return new HelloController(helloService);
//	}
//
//	@Bean
//	public HelloService helloService() { // 인터페이스 타입으로 사용
//		return new SimpleHelloService();
//	}

	public static void main(String[] args) {
		// 스프링 컨테이너 생성 -> 어플리케이션 구성 정보
//		run(HellobootApplication.class, args);
		SpringApplication.run(HellobootApplication.class, args);
		// MySpringBootApplication.run(HellobootApplication.class, args); // 결국 @SpringBootApplication이 붙어서 처음 만들었을 때와 동일한 구조!
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
	
	// 1. run 메서드로 추출 -> 2. MySpringBootApplication 참조
	private static void run(Class<?> applicationClass, String... args) {
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
		// applicationContext.registerBean(HelloController.class); // 클래스 정보를 Bean에 담아 등록함
		/*
		* 그런데 스프링 컨테이너는 어떻게 HelloController가 SimpleHelloService를 사용할 것인지 알 수 있는가?
		* HelloController를 보면 생성자를 통해 HelloService라는 인터페이스를 주입받는다.
		* 예전에는 이를 xml로 context 정보에 등록했었지만, 이제는 많은 부분이 간소화되었다.
		* 스프링 컨테이너는 Bean으로 등록되어 있는 클래스 중 HelloService를 구현한 클래스가 있는지 전부 다 뒤져서 찾아본 다음
		* 의존성을 주입한다.
		* */
		//applicationContext.registerBean(SimpleHelloService.class); // 명확하게 어떤 클래스인지 등록해줘야 함
		applicationContext.register(applicationClass);
		applicationContext.refresh(); // 빈을 생성(초기화) -> 스프링 컨테이너를 초기화
	}

}
