# 25_@Controller와 @RestController

> @Controller와 @RestController의 차이점

## 1. @Controller

전통적인 Spring MVC의 컨트롤러에 붙이는 어노테이션으로서, 주로 View를 반환하기 위해 사용한다.

- 컨트롤러가 반환한 View의 이름에 맞는 뷰를 렌더링하기 위해 ViewResovler가 사용되며, ViewResolver의 설정에 맞게 해당하는 View를 찾아 렌더링한다.

만약에 JSON/XML과 같은 데이터를 반환하고자 할 때는 일반적으로 보통 ResponseEntity 타입으로 감싸서 반환한다.

- 이 때에는 ViewResolver 대신에 HttpMessageConverter가 동작한다. 받는 데이터에 따라 사용되는 Converter 종류가 다르다.

- 또한 데이터로 반환하기 위해서는 @ResponseBody라는 어노테이션을 붙여줘야 한다.
  - @Controlller를 사용했을 때 @ResponseBody 없이 String 타입으로 반환을 하면, 반환 문자열을 뷰의 이름으로 간주하기 때문에 예상했던 텍스트를 볼 수 없고 Whitelabel Error Page를 보게 된다.

한 컨트롤러 내에서 View와 데이터를 반환하도록 혼재하여 사용해도 되지만, 가급적이면 데이터를 반환하는 RestController와 View를 반환하는 Controller를 분리하여 코드를 작성하는 것이 더 좋다.

<br>

## 2. @RestController

REST API를 개발할 때 주로 사용되는 어노테이션으로서 @Controller + @ResponseBody이다.

JSON/XML과 같은 데이터를 반환하기 위해 사용되며 객체를 ResponseEntity로 감싸서 반환한다. 동작 역시 @Controller에 @ResponseBody를 붙인 것과 동일하게 동작한다.

※ @RestController와 리다이렉트

@RestController는 @Controller에 @ResponseBody를 붙였기 때문에 동작 결과로서 리다이렉트를 시키고자 할 때에는 조금은 부적절하다. 정확히 말하면 일반적인 방법으로는 정상적으로 리다이렉트 시키지 못한다. 왜냐하면 반환하는 리다이렉트 url 자체도 데이터이기 때문이다.

@RestController를 사용했을 때 @ResponseBody, 즉 ResponseEntity를 활용해서 리다이렉트 시키려면 다음과 같이 코드를 작성해야 한다.

![image](https://github.com/user-attachments/assets/c77c3242-a8d7-4b46-8694-177cd44974e2)

- 이렇게 코드를 작성하면 '/api/redirect'를 호출했을 때, '/redirected-url'로 리다이렉트 된다.
