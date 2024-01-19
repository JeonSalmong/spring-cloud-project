package com.example.userservice.core.exception;

/**
 * Client와 약속된 에러코드 정의 클래스
 * 
 * @since      2017. 10. 12.
 * @version    1.0
 * @author     H2017289
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 10. 12.  H2017289        최초 생성
 * </pre></dd>
 */
public class ErrorCode {
	/**
	 * 정상
	 */
	public static final String OK = "OK";
	
	/**
	 * Application 발생 에러 
	 */
	public static final String APPLICATION = "APP";
	
	/**
	 * DB 발생 에러 
	 */
	public static final String DB = "DB";
	
	/**
	 * System 발생 에러
	 */
	public static final String SYSTEM = "SYS";
	
	/**
	 * 권한 관련 에러 
	 */
	public static final String NOAUTH = "AUTH";
	
	/**
	 * 잘못된 접근 에러
	 */
	public static final String BADACCESS = "BAC";
//	public static final String DEVELOPER = "DEVELOPER";

	/**
	 * System 발생 에러 외 에러(OTH로 설정하여야 하나 SYSTEM ERROR를 SYS한가지로 사용한다.)
	 */
	public static final String OTHER = "SYS";	// OTH로 설정하여야 하나 SYSTEM ERROR를 SYS한가지로 사용한다.

	/**
	 * DB에러를 판별하기 위한 문자열 상수
	 */
	public static final String ERR_DB_STR = "java.sql";
	
	/**
	 * Egovframework에러를 판별하기 위한 문자열 상수 
	 */
	public static final String ERR_EGOV_STR = "egovframework";
	
	/**
	 * Application에러를 판별하기 위한 문자열 상수 
	 */
	public static final String ERR_APP_STR = "DNPAPPMSG";
	
	/**
	 * 권한 관련 에러를 판별하기 위한 문자열 상수 
	 */
	public static final String ERR_NOAUTH_STR = "DNPNOAUTH";
    
}
