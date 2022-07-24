![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 01_MVC Pattern

## 01_MVC

스프링의 소프트웨어 디자인 패턴으로 각각 M: Model, V: View, C: Controller를 의미한다. 스프링 프레임워크는 현재 MVC2 패턴을 채택하고 있다

### Model

어플리케이션의 데이터, DB 등 모든 데이터 정보를 가공하여 갖고 있는 컴포넌트

- 사용자가 이용하려는 모든 데이터를 갖고 있어야하며, View와 Controller에 대해 어떠한 정보도 알 수 없어야 한다
- 변경이 발생할 경우 그에 대한 처리 방법을 구현해야한다



### View

UI, 시각적인 부분을 담당하는 컴포넌트

- Model과 Controller에 대한 정보를 알면 안되며, 단순히 표현/렌더링을 하는 역할을 한다
- 변경이 발생할 경우 그에 대한 처리 방법을 구현해야한다



### Controller

Model과 View를 연결하는 역할의 컴포넌트로, 데이터와 비즈니스 로직 간의 상호작용을 담당한다

- Model과 View에 대한 정보를 알고 있어야하며, 변경에 대해 대처를 해야한다.

<br>

## 02_MVC Pattern 동작 구조

![image](https://user-images.githubusercontent.com/93081720/172411593-e1b641a8-393a-4698-aa7e-5977c709c0b1.png)

1.  Client가 url을 통해서 요청(request)을 보냄
2.  DispatcherServlet은 요청을 처리하기 위한 Controller를 HandlerMapping에게 검색을 요청한다
3.  HandlerMapping은 요청된 URL을 이용해서 이를 처리할 Controller를 DispathcerServlet에게 return한다
4.  DispathcerServlet은 HandlerAdapter에게  Controller가 요청을 처리할 수 있도록 요청 처리를 위임한다
5.  HandlerAdapter는 Controller에게 요청에 알맞는 method를 호출하도록 요청한다
6.  Controller는 Service에게 비즈니스 로직 처리를 위임한다
7.  Service는 요청에 필요한 작업을 수행하거나, DB에 접근이 필요할 경우 DAO에게 처리를 위임한다
8.  DAO(Data Access Object)는 DB정보를 DTO(Data Transfer Object)를 통해 전달받아 서비스에게 전달한다
9.  Controller는 비즈니스 로직 처리 결과를 HandlerAdapter에게 반환한다
10.  HandlerAdapter는 DispatcherServlet에게 처리 결과를 ModelAndView 객체로 변환하여 반환한다
11.  DispatcherServlet은 결과를 보여줄 View를 찾기 위해 ViewResolver에게 ModelAndView안의 해당 View를 검색 요청한다
12.  ViewResolver는 ModelAndView안에 있는 View 이름에 해당하는 View객체를 찾거나 생성해서 반환한다
13.  DispatcherServlet은 전달받은 View 객체에게 request result 생성을 요청한다
14.  View 객체는 JSP를 사용하는 경우 JSP를 실행하여 result를 Rendering한 후 Client에게 Rendering된 View를 응답(Response)한다
