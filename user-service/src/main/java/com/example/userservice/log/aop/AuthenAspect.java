package com.example.userservice.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * login, logout시 해당 사용자의 정보에 대해 log를 남긴다.
 *
 * @since      2023. 11. 29.
 * @version    1.0
 * @author     IS068000
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2023. 11. 29.  IS068000        최초 생성
 * </pre></dd>
 */
//@Aspect
@Slf4j
public class AuthenAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("[AopAuthenLoggin authenLogInsertAround] Method Name : " + invocation.getMethod().getName());

//		if (authenLog != null) {
//			return authenLog.insertAround(invocation);
//		}
		return invocation.proceed();
	}
}
