package com.example.userservice.core.context;

import com.example.userservice.core.annotation.AfterSpringLoadComplete;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Application context에 접근할 수 있도록하는 클래스
 * <p>
 * 스프링이 구동 될때 ApplicationListener 인터페이스를 구현하여
 * ContextRefreshedEvent(ApplicationContext가 초기화되거나 갱신 될 때 실행됨) 이벤트에서
 * 하나의 컨트롤러에서 자신이 사용하는 컨텍스트 이외에, 다른 컨텍스트를 이용하는 경우
 * 사용하는 class bean들의 id값을 가지고 applicationcontext객체로 부터 동적으로 객체를 얻을 때 사용한다.
 * </p>
 *
 * @since      2017. 11. 23.
 * @version    1.0
 * @author     T160142
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 11. 23.  T160142         최초 생성
 * </pre></dd>
 */
@Slf4j
public class DnpApplicationListener implements ApplicationListener {
	private static ApplicationContext appContext;
	private static ServletContext context;

	private List<String> basenames;

	public List<String> getBasenames() {
		return basenames;
	}

	public void setBasenames(List<String> basenames) {
		this.basenames = basenames;
	}

	/**
	 * ApplicationContext getter
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return appContext;
	}

	/**
	 * Root ApplicationContext getter
	 *
	 * @return
	 */
	public static ApplicationContext getRootApplicationContext(){
		ApplicationContext rootWac, parent;

		rootWac = parent = getApplicationContext();
		while (parent != null) {
			if (parent instanceof ApplicationContext && !(parent.getParent() instanceof ApplicationContext)) {
				rootWac = parent;
				break;
			}
			parent = parent.getParent();
		}

		return rootWac;
	}

	/**
	 * ServletContext getter
	 *
	 * @param
	 * @exception @return
	 */
	public static ServletContext getServletContext() {
		return context;
	}

	/**
	 * Application이 구동되거나 갱신될때 실행된다.
	 *
	 * @param event
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			log.info("context is refreshed.");
			ContextRefreshedEvent e = (ContextRefreshedEvent) event;
			appContext = e.getApplicationContext();

			if (appContext instanceof WebApplicationContext){
				WebApplicationContext ctx = (WebApplicationContext) e.getApplicationContext();
			    context = ctx.getServletContext();
			}

			//
			// Annotations 선언 필요
			// @Retention(RetentionPolicy.RUNTIME)
			// public @interface AfterSpringLoadComplete {
			// }

			String[] names = appContext.getBeanDefinitionNames();
			for (String name : names) {
				try {
					// log.debug(name);
					ConfigurableListableBeanFactory factory = ((AbstractApplicationContext) appContext).getBeanFactory();
					BeanDefinition definition = factory.getBeanDefinition(name);
					String originalClassName = definition.getBeanClassName();
					if (originalClassName == null) continue;
					Class<?> originalClass = Class.forName(originalClassName);
					Method[] methods = originalClass.getMethods();
					for (Method method : methods) {
						if (method.isAnnotationPresent(AfterSpringLoadComplete.class)) {
							// log.debug(method.getName());
							Object bean = appContext.getBean(name);
							Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
							currentMethod.invoke(bean);
						}
					}

				} catch (ClassNotFoundException | NoSuchMethodException exception) {
					// Catch expected Exceptions.
					log.error("an exception onApplicationEvent thrown", exception);
					// Find underlying causal Exception.
					if (exception.getCause() != null) {
						log.error("an exception onApplicationEvent_getCause thrown" + exception.getCause());
					}
				} catch (Exception exception) {
					// Catch unexpected Exceptions.
					log.error("an exception onApplicationEvent thrown", exception);
				}
			}
			// // 사용예
			// @AfterSpringLoadComplete
			// public void init() {}

			// // 추후 basenames의 List를 가지고 bind 하고자 하는 예제
			// try {
			// log.debug("dnp ApplicationEvent");
			// // TODO Auto-generated method stub
			// if (basenames != null) {
			// Iterator<String> itr = basenames.iterator();
			// while (itr.hasNext()) {
			// log.debug(itr.next());
			// }
			// }
			// } catch (Exception exception) {
			// // Catch unexpected Exceptions.
			// log.debug(exception);
			// }
		} else if (event instanceof ContextClosedEvent) {
			log.info("context is closed.");
		}
	}

}
