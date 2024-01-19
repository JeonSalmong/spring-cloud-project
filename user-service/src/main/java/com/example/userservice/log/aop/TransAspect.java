package com.example.userservice.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 시스템 연계시 log를 남긴다
 * <ul>
 *   <li>
 *     jsonrpc로만 시스템 연계를 하게되면 @JsonRpcService 라는 annotation을 사용하기 때문에
 *     pointcut 표현식을 해당 annotation을 갖는 class, method로 정의할 수 있다
 *   </li>
 *   <li>
 *     그게 아니라면 따로 시스템 연계를 구분 지을 수 있는 annotation class를 만들어서
 *     pointcut 표현식을 해당 annotation을 갖는 class, method로 정의할 수도 있다
 *   </li>
 * </ul>
 *
 * @since      2023. 11. 29.
 * @version    1.0
 * @author     H2017288
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2023. 11. 29.  H2017288        최초 생성
 * </pre></dd>
 */
//@Aspect //AOP Bean
@Slf4j
public class TransAspect implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("[AopTransLoggin transLogInsertAround]");
//		if (transLog != null) {
//			return transLog.insertAround(invocation);
//		}
		return invocation.proceed();
	}
}
