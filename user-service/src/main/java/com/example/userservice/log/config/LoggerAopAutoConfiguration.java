package com.example.userservice.log.config;


import com.example.userservice.core.annotation.DnpAutoConfig;
import com.example.userservice.log.ActionLog;
import com.example.userservice.log.aop.ActionAspect;
import com.example.userservice.log.aop.AuthenAspect;
import com.example.userservice.log.aop.AuthenBeforeAspect;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Srpint Boot의 AOP 자동 설정 Class
 *
 * @since       2023. 11. 29.
 * @version     1.0
 * @author      IS068000
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DCCP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 *   DATE           AUTHOR          NOTE
 *   -------------  --------------  -----------------------
 *   2023. 11. 29.  IS068000        최초 생성
 * </pre></dd>
 */
@Slf4j
@DnpAutoConfig
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerAopAutoConfiguration {

	@Value("${dnp.aop-pointcut.action}")
	String pointcutAction;

	@Value("${dnp.aop-pointcut.authen}")
	String pointcutAuthen;

	@Value("${dnp.aop-pointcut.authen-before}")
	String pointcutAuthenBefore;

	@Value("${dnp.aop-pointcut.trans}")
	String pointcutTrans;

	@Bean
	public Advice actionPointcutAspect(ActionLog actionLog) {
//		Advice aspect = new ActionAspect();
		return new ActionAspect();
	}

	@Bean
	public ActionLog actionLog() {
		return new ActionLog();
	}

	@Bean
	public AspectJExpressionPointcutAdvisor actionLogAspectJ(Advice actionPointcutAspect) {
		if ("".equals(this.pointcutAction))
			this.pointcutAction = "!@annotation(com.example.userservice.log.annotation.NoLogging) || @annotation(com.example.userservice.log.annotation.ActionLogging)";
		else
			this.pointcutAction = "((" +this.pointcutAction + ") && !@annotation(com.example.userservice.log.annotation.NoLogging)) || @annotation(com.example.userservice.log.annotation.ActionLogging)";

		AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
		pointCut.setExpression( this.pointcutAction);

		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
		advisor.setExpression(pointCut.getExpression());
		advisor.setAdvice(actionPointcutAspect);

		return advisor;
	}

	@Bean
	public Advice authenPointcutAspect() {
//		Advice aspect = new AuthenAspect();
		return new AuthenAspect();
	}

	@Bean
	public Advice authenBeforePointcutAspect() {
//		Advice aspect = new AuthenBeforeAspect();
		return new AuthenBeforeAspect();
	}

	@Bean
	public AspectJExpressionPointcutAdvisor authenLogAspectJ() {
		if ("".equals(this.pointcutAuthen))
			this.pointcutAuthen = "!@annotation(com.example.userservice.log.annotation.NoLogging) || @annotation(com.example.userservice.log.annotation.AuthenLogging)";
		else
			this.pointcutAuthen = "((" +this.pointcutAuthen + ") && !@annotation(com.example.userservice.log.annotation.NoLogging)) || @annotation(com.example.userservice.log.annotation.AuthenLogging)";


		AspectJExpressionPointcut pointCut1 = new AspectJExpressionPointcut();
		pointCut1.setExpression(this.pointcutAuthen);

		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
		advisor.setExpression(pointCut1.getExpression());
		advisor.setAdvice(authenPointcutAspect());

		return advisor;
	}

	@Bean
	public AspectJExpressionPointcutAdvisor authenLogBeforeAspectJ() {
		if ("".equals(this.pointcutAuthenBefore))
			this.pointcutAuthenBefore = "!@annotation(com.example.userservice.log.annotation.NoLogging) || @annotation(com.example.userservice.log.annotation.AuthenLogging)";
		else
			this.pointcutAuthenBefore = "((" +this.pointcutAuthenBefore + ") && !@annotation(com.example.userservice.log.annotation.NoLogging)) || @annotation(com.example.userservice.log.annotation.AuthenLogging)";

		AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
		pointCut.setExpression(this.pointcutAuthenBefore);

		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
		advisor.setExpression(pointCut.getExpression());
		advisor.setAdvice(authenBeforePointcutAspect());

		return advisor;
	}

//	@Bean
//	public Advice transPointcutAspect() {
//		Advice aspect = new TransAspect();
//		return aspect;
//	}
//
//	@Bean
//	public AspectJExpressionPointcutAdvisor transLogAspectJ() {
//		if ("".equals(this.pointcutTrans))
//			this.pointcutTrans = "!@annotation(dnp.framework.boot.annotation.NoLogging) || @annotation(dnp.framework.boot.annotation.TransLogging)";
//		else
//			this.pointcutTrans = "((" +this.pointcutTrans + ") && !@annotation(dnp.framework.boot.annotation.NoLogging)) || @annotation(dnp.framework.boot.annotation.TransLogging)";
//
//		AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
//		pointCut.setExpression(this.pointcutTrans);
//
//		AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
//		advisor.setExpression(pointCut.getExpression());
//		advisor.setAdvice(transPointcutAspect());
//
//		return advisor;
//	}
}
