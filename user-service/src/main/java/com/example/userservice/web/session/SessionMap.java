package com.example.userservice.web.session;

import java.util.Map;

public interface SessionMap {
    /**
     * HttpSession의 GWSSO 호출여부를 설정한다.
     *
     * @param gwsso_call
     * @throws Exception
     */
    void setGWSSOCall(String gwsso_call) throws Exception;

    /**
     * HttpSession의 GWSSO 호출여부를 가져온다.
     *
     * @return gwsso_call
     * @throws Exception
     */
    String getGWSSOCall() throws Exception;

    /**
     * 내부사용자 계정 SSO_AUTH 정보를 설정한다.
     *
     * @param sso_auth
     * @throws Exception
     */
    void setSSOAuth(Object sso_auth) throws Exception;

    /**
     * 내부사용자 계정 SSO_AUTH 정보를 가져온다.
     *
     * @return sso_auth
     * @throws Exception
     */
    String getSSOAuth() throws Exception;

    /**
     * 사용자 id를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getUserId() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 사용자 id를 설정한다.
     *
     * @param userId
     * @throws Exception
     */
    void setUserId(String userId) throws Exception;

    /**
     * 로그인 id를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getLoginId() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 로그인 id를 설정한다.
     *
     * @param loginId
     * @throws Exception
     */
    void setLoginId(String loginId) throws Exception;

    /**
     * 사용자 이름을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getUserNm()  throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 사용자 이름을 설정한다.
     *
     * @param userNm
     * @throws Exception
     */
    void setUserNm(String userNm) throws Exception;

    /**
     * 로그인 ip를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getLoginIp() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 로그인 ip를 설정한다.
     *
     * @param loginIp
     * @throws Exception
     */
    void setLoginIp(String loginIp) throws Exception ;

    /**
     * 사용자의 부서코드를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getDeptCd() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 부서 코드를 설정한다.
     *
     * @param deptCd
     * @throws Exception
     */
    void setDeptCd(String deptCd) throws Exception;

    /**
     * 사용자의 권한 이름을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getRoleNm() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 권한 이름을 설정한다.
     *
     * @param roleNm
     * @throws Exception
     */
    void setRoleNm(String roleNm) throws Exception;

    /**
     * 사용자의 권한 코드를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getRoleCd() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 권한 코드을 설정한다.
     *
     * @param roleCd
     * @throws Exception
     */
    void setRoleCd(String roleCd) throws Exception;

    /**
     * 세션 만료 제한시간을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getSessionTimeOut() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 세션 만료 제한시간을 설정한다.
     *
     * @param sessionTimeOut
     * @throws Exception
     */
    void setSessionTimeOut(String sessionTimeOut) throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 로그인 상태를 설정한다.
     *
     * @param strStatus
     */
    void setLoginStatus(String strStatus);

    /**
     * HttpSession의 사용자 정보 map에 로그인 상태를 설정한다.
     *
     * @param loginStatus
     */
    void setLoginStatus(Enum loginStatus);

//    /**
//     * HttpSession의 사용자 정보 map에 로그인 성공 상태를 설정한다.
//     *
//     * @param successStatus
//     */
//    void setLoginSuccessStatus(LoginSuccessStatus successStatus) throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 로그인 실패 상태를 설정한다.
     *
     * @param strStatus
     */
    void setLoginFailStatus(String strStatus);

//    /**
//     * HttpSession의 사용자 정보 map에 로그인 실패 상태를 설정한다.
//     *
//     * @param failStatus
//     */
//    void setLoginFailStatus(LoginFailStatus failStatus);



    /**
     * 로그인 상태를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getLoginStatus() throws Exception;

//    /**
//     * HttpSession의 사용자 정보 map에 사용자 구분을 설정한다.
//     *
//     * @param userGb
//     * @throws Exception
//     */
//    //void setUserGb(String userGb) throws Exception {
//    void setUserGb(UserType userGb) throws Exception;

    /**
     * 사용자 구분을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getUserGb() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 개인정보 마스킹 처리 유무(Y : 안함, N: 처리함)
     *
     * @param masking
     * @throws Exception
     */
    void setNoMasking(String masking) throws Exception;

    /**
     * 개인정보 마스킹 처리 유무(Y : 안함, N: 처리함)를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getNoMasking() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 2FA필요 여부를 설정한다.
     *
     * @param isTfaRequired
     * @throws Exception
     */
    void setIsTfaRequired(String isTfaRequired) throws Exception;

    /**
     * 2FA필요 여부를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getIsTfaRequired() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 SMS 인증번호를 설정한다.
     *
     * @param smsCertiNo
     * @throws Exception
     */
    void setSmsCertiNo(String smsCertiNo) throws Exception;

    /**
     * SMS 인증번호를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getSmsCertiNo() throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 SMS 인증 제한시간을 설정한다.
     *
     * @param smsCertiTimeOut
     * @throws Exception
     */
    void setSmsCertiTimeOut(String smsCertiTimeOut) throws Exception;

    /**
     * SMS 인증 제한시간을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getSmsCertiTimeOut() throws Exception;

    /**
     * 이전 로그인의 세션 id를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getExistSessionId() throws Exception;

    /**
     * 이전 로그인의 접속 서버 ip를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getExistConnectServerIp() throws Exception;

    /**
     * 이전 로그인의 접속 브라우저 종류를 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getExistConnectBrowser() throws Exception;

    /**
     * 이전 로그인의 접속 pc 명을 가져온다.
     *
     * @return
     * @throws Exception
     */
    String getExistConnectPc() throws Exception ;

    /**
     * 업무 구분 정보를 가져온다
     *
     * @return
     * @throws Exception
     */
    String getClBiz() throws Exception;

    /**
     * HttpSession의 사용자 정보 map 을 반환한다.
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> getSessionMap() throws Exception;

    /**
     * HttpSession의 사용자 정보 map 에서 특정 정보를 가져온다.
     * 예제)
     *
     *
     * @param key dnpLoginPolicy.properties 파일에 "SESSION.KEY" prefix로 등록된 값들
     * @return
     * @throws Exception
     */
    Object getAttrFromSessionUserMap(String key);

    /**
     * HttpSession의 사용자 정보 map 에서 특정 정보를 가져온다.
     * 예제)
     * 사용자 아이디 : SessionMapHandler.getUserData(PropertyHelper.getString("loginPolicy", "SESSION.KEY.USERID"));
     * 사용자 이름   : SessionMapHandler.getUserData(PropertyHelper.getString("loginPolicy", "SESSION.KEY.USERNM"));
     *
     * @param key dnpLoginPolicy.properties 파일에 "SESSION.KEY" prefix로 등록된 값들
     * @return
     * @throws Exception
     */
    Object getUserData(String key) throws Exception;

    /**
     * HttpSession 영역의 사용자 정보 map 에 객체를 담는다.
     *
     * @param pMap HttpSession에 담을 객체
     * @throws Exception
     */
    void setAttrToSessionUserMap(Map pMap);

    /**
     * HttpSession 영역의 사용자 정보 map 에 객체를 담는다.
     *
     * @param key HttpSession에 담을 객체 key
     * @param value HttpSession에 담을 객체 value
     * @throws Exception
     */
    void setAttrToSessionUserMap(String key, Object value);

    /**
     * HttpSession 객체의 'userMap'에 사용자 정보를 셋팅한다.
     *
     * @param userMap HttpSession 객체에 셋팅할 사용자 정보
     * @throws Exception
     */
    void setUserInfoToSession(Map userMap);

    /**
     * HttpSession의 권한 정보를 제거한다.
     *
     */
    void clearAuthMap();

    /**
     * HttpSession의 권한 정보 map 에서 특정 정보를 가져온다.
     *
     * @param key
     * @return
     * @throws Exception
     */
    Object getAttrFromAuthMap(String key);

    /**
     * HttpSession 영역의 권한 정보 map 에 객체를 담는다.
     *
     * @param key HttpSession에 담을 객체 key
     * @param value HttpSession에 담을 객체 value
     * @throws Exception
     */
    void setAttrToAuthMap(String key, Object value);

    void clear(String key);
}
