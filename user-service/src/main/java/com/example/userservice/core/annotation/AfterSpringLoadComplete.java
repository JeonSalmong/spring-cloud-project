package com.example.userservice.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring 구동 후에 자동으로 실행될 메소드를 Annotation으로 관리하기 위한 클래스
 * <p>
 * Spring 구동 후 실행할 메소드 상단에 @AfterSpringLoadComplete 을 명시하여 사용한다.
 * </p>
 * 
 * @since      2017. 12. 27.
 * @version    1.0
 * @author     T160142
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 12. 27.  T160142         최초 생성
 * </pre></dd>
 */
@Target({
	ElementType.METHOD, 
	ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterSpringLoadComplete {
}
