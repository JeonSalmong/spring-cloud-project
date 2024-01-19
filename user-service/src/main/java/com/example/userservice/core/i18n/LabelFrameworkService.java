/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package com.example.userservice.core.i18n;

import java.util.Map;

/**
 * Framework단에서 사용하는 Leable관련 Service 클래스
 *
 * @since      2018. 6. 27.
 * @version    1.0
 * @author     T160142
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2018. 6. 27.   T160142        최초 생성
 * </pre></dd>
 */
public interface LabelFrameworkService {

	/**
	 * paramMap을 가져온다.
	 *
	 * @return
	 */
	Map<String, Object> getParamMap();

	/**
	 * 시스템 코드를 설정한다.
	 *
	 * @param strCdSys
	 */
	void setCdSys(String strCdSys);

	/**
	 * 언어 코드를 설정한다.
	 *
	 * @param strCdLnge
	 */
	void setCdLnge(String strCdLnge);

	/**
	 * 단위업무, 메시지 코드에 맞는 메시지 내용을 조회한다.
	 *
	 * @param unitBizLabelCode 단위업무+"."+메시지 코드 "SYS.M001" 형태의 메시지 코드
	 * @return
	 * @throws Exception
	 */
	String getLabel(String unitBizLabelCode) throws Exception;

	/**
	 * 단위업무, 메시지 코드에 맞는 메시지 내용을 조회한다.
	 *
	 * @param unitBizLabelCode 단위업무+"."+메시지 코드 "SYS.M001" 형태의 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getLabel(String unitBizLabelCode, Object[] strArgs) throws Exception;

	/**
	 * 단위업무, 메시지 코드에 맞는 메시지 내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strLabelCode 메시지 코드
	 * @return
	 * @throws Exception
	 */
	String getLabel(String strBizUnitCd, String strLabelCode) throws Exception ;

	/**
	 * 파라미터가 있는 메시지내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strLabelCode 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getLabel(String strBizUnitCd, String strLabelCode, Object... strArgs) throws Exception;

	/**
	 * 파라미터가 있는 메시지내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strLabelCode 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getLabelArray(String strBizUnitCd, String strLabelCode, Object[] strArgs) throws Exception;
}
