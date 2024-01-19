package com.example.userservice.web.status;

public enum LoginHoldStatus {
	// 다중 로그인
	MULTILOGIN,
	// 2FA 인증번호
	TFA,
	// Sms 인증번호
	SMS,
	// SMS 체크
	SMSCHECK,
	// 권한 선태
	DEPTCHECK
}
