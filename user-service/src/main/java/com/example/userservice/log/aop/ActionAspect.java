package com.example.userservice.log.aop;

import com.example.userservice.log.ActionLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 사용자의 요청에 대해 log를 남긴다.
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
//@Component
@Slf4j
public class ActionAspect implements MethodInterceptor {

	@Autowired(required = false)
	private ActionLog actionLog;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("[AopActionLoggin actionLogInsertAround]");
		if (actionLog != null) {
			return actionLog.insertAround(invocation);
		}
		return invocation.proceed();
	}
}
