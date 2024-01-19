package com.example.userservice.core.i18n;


import java.util.Map;

/**
 * Framework단에서 사용하는 메시지관련 Service 클래스
 *
 * @since      2017. 10. 31.
 * @version    1.0
 * @author     H2017289
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 10. 31.  H2017289        최초 생성
 * </pre></dd>
 */
public interface MessageFrameworkService {

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
	 * @param unitBizMsgCode 단위업무+"."+메시지 코드 "SYS.M001" 형태의 메시지 코드
	 * @return
	 * @throws Exception
	 */
	String getMsg(String unitBizMsgCode) throws Exception;

	/**
	 * 단위업무, 메시지 코드에 맞는 메시지 내용을 조회한다.
	 *
	 * @param unitBizMsgCode 단위업무+"."+메시지 코드 "SYS.M001" 형태의 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getMsg(String unitBizMsgCode, Object[] strArgs) throws Exception;

	/**
	 * 단위업무, 메시지 코드에 맞는 메시지 내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strMsgCode 메시지 코드
	 * @return
	 * @throws Exception
	 */
	String getMsg(String strBizUnitCd, String strMsgCode) throws Exception ;

	/**
	 * 파라미터가 있는 메시지내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strMsgCode 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getMsg(String strBizUnitCd, String strMsgCode, Object... strArgs) throws Exception;

	/**
	 * 파라미터가 있는 메시지내용을 조회한다.
	 *
	 * @param strBizUnitCd 단위업무 코드
	 * @param strMsgCode 메시지 코드
	 * @param strArgs 치환할 파라미터 값(n개)
	 * @return
	 * @throws Exception
	 */
	String getMsgArray(String strBizUnitCd, String strMsgCode, Object[] strArgs) throws Exception;

}

