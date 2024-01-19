/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package com.example.userservice.core.exception;


import com.example.userservice.core.context.GlobalContext;
import com.example.userservice.core.i18n.LabelFrameworkService;
import com.example.userservice.core.i18n.MessageFrameworkService;
import com.example.userservice.core.property.PropertyHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.text.MessageFormat;

/**
 * <pre>
 * dnp.framework.exception
 *    |_ DnpExceptionResolver.java
 *
 * </pre>
 * @date : 2019. 1. 10. 오후 3:20:22
 * @version :
 * @author : YoungRok Ahn
 */
@Slf4j
public class DnpExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
    protected ModelAndView doResolveException(HttpServletRequest request,
											  HttpServletResponse response,
											  Object handler,
											  Exception exception) {
    	// Call super method to get the ModelAndView
    	ModelAndView mav = super.doResolveException(request, response, handler, exception);
    	String sTitle = PropertyHelper.getString("commonProperty", "DNP.VIEW.TITLE");
		String sAddr = GlobalContext.getLocalAddr();
		sAddr = sAddr.substring(sAddr.lastIndexOf(".") + 1);
		mav.addObject("VIEW_TITLE", MessageFormat.format("{0}(W{1})", sTitle, sAddr));
		MessageFrameworkService dnpMsg = (MessageFrameworkService) GlobalContext.getBean(MessageFrameworkService.class);
		mav.addObject("DnpMsg", dnpMsg);

		LabelFrameworkService dnplbl = (LabelFrameworkService) GlobalContext.getBean(LabelFrameworkService.class);
		mav.addObject("DnpLbl", dnplbl);

        return mav;
    }

}
