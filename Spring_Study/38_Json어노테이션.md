# 38_Json 어노테이션

> @JsonIgnoreProperties vs @JsonInclude

DTO에 많이 쓰는 Json 관련 어노테이션으로 `@JsonIgnoreProperties`과 `@JsonInclude`가 있다.

비슷한 느낌이지만, 두 어노테이션의 역할에는 명백한 차이가 있다.

## 1. @JsonIgnoreProperties

역직렬화(JSON → Java 객체) 시 사용하는 어노테이션.

`ignoreUnknown` 옵션을 설정할 수 있으며, true로 설정할 경우 JSON에 있는 필드가 DTO의 필드로 없어도 에러를 내지 않도록 한다. (예) JSON에는 name 필드가 있지만, DTO에는 name 필드가 없는 경우)

이 어노테이션이 없을 경우 Jackson은 "맵핑할 필드가 없다"면서 `UnrecognizedPropertyException` 예외를 던진다.

내가 요청한 API의 응답에 DTO가 모르는 필드가 섞여 들어올 때 발생할 문제를 차단(굳이 신경쓰지 않음)하기 위해서 사용한다.

<br>

## 2. @JsonInclude

직렬화(Java 객체 → JSON) 시 사용하는 어노테이션.

`JsonInclude.Include.NON_NULL`옵션을 주로 사용한다. (NON_EMPTY, NON_DEFAULT 등 다양한 옵션 존재)

NON_NULL 옵션의 경우 DTO의 필드값이 null이면 이를 JSON 객체에 포함하지 않도록 한다. 즉, DTO에 name이라는 String 타입의 필드가 있지만 이 값이 null일 경우에 JSON에 name 필드를 포함시키지 않도록 한다.

만약 ALWAYS 옵션 혹은 @JsonInclude 어노테이션이 없을 경우에는 JSON 직렬화 시 해당 필드의 값으로 null이 들어간다.

API 요청에 대한 응답(내 API의 응답)에 혹은 요청(내가 외부 API로 요청) 시 의도치 않은 필드를 전송하는 것을 막기 위해 사용한다. 예) null을 전달해서 NPE를 유발하지 않기 위해 사용.