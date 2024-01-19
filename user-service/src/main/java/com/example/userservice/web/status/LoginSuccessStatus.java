package com.example.userservice.web.status;

/**
 * <pre>
 * 로그인 성공 상태 정의 클래스
 * </pre>
 *
 * @Class Name   LoginSuccessStatus.java
 * @author       H2017288
 * @since        2017.12.19.
 * @version      1.0
 * @Description
 *
 *
 * @Modification Information
 *
 * 수정일         작성자          NOTE
 * -------------  --------------  -----------------------
 * 2017.12.19.    H2017288        최초 생성
 *
 * Copyright (C) by DNP All right reserved.
 */
public enum LoginSuccessStatus {
	// 검증 상태
	REQUEST,
	// 접속 가능 IP 성공
	IP,
	// 접속 아이디 성공
	ID,
	// 잠금 상태
	LOCKCHECK,
	// 휴면계정여부
	LASTLOGINCHECK,
	// 비번 검증
	PASSWORD,
	// OAUTH 인증
	OAUTH,
	// 권한 검증
	AUTH,
	// 사용정책 성공
	POLICY,
	// 다중로그인
	MULTILOGIN,
	// 2FA
	TFA,
	// SMS
	SMS,
	// SMS 체크
	SMSCHECK,
	// 패턴
	PATTERN,
	// 패턴 체크
	PATTERNCHECK,
	// 성공
	SUCCESS,
	// 실패
	FAIL
}
