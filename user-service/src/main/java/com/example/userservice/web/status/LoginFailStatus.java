package com.example.userservice.web.status;

/**
 * <pre>
 * 로그인 실패 상태 정의 클래스
 * </pre>
 *
 * @Class Name   LoginFailStatus.java
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
public enum LoginFailStatus {
	// 비밀번호 인증 잠금
	PASSWORDFAIL,
	// 2FA 인증번호 실패
	TFAFAIL,
	// OAUTH 인증 실패
	OAUTHFAIL,
	// 예외 잠금
	ETCFAIL,
	// 잠금
	HOLD,
	// 다중 로그인 잠금
	MULTILOGINHOLD,
	// 2FA 인증번호 장금
	TFAHOLD,
	// SMS 잠금
	SMSHOLD,
	// SMS 체크 잠금
	SMSCHECKHOLD,
	// 패턴 잠금
	PATTERNHOLD,
	// 권한 선태 잠금
	DEPTHOLD,
	// 권한 체크 잠금
	DEPTCHECKHOLD,
}
