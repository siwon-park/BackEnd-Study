package tobyspring.helloboot;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 본 어노테이션의 수명을 설정
@Target(ElementType.TYPE) // 클래스나 인터페이스와 같은 '타입' 속성에 적용됨을 명시 (타겟을 여러 개 지정 가능함)
@Component
public @interface MyComponent {
 // 이제 클래스나 인터페이스에 @MyComponent라고 쓰면 @Component가 적용됨
}
