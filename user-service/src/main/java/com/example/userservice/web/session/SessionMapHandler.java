package com.example.userservice.web.session;


import com.example.userservice.core.context.GlobalContext;
import com.example.userservice.core.util.StringSupporter;
import com.example.userservice.web.status.LoginFailStatus;
import com.example.userservice.web.status.LoginSuccessStatus;
import org.hibernate.usertype.UserType;

import java.util.HashMap;
import java.util.Map;
/**
 * HttpSession에 attribute로 담아놓은 DNP 정의 사용자 정보 map을 핸들링 할 수 있는 클래스
 *
 * @since      2017. 12. 28.
 * @version    1.0
 * @author     H2017288
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 12. 28.  H2017288        최초 생성
 * </pre></dd>
 */
public class SessionMapHandler implements SessionMapData {
//	private static SessionMapHandler sessionMapHandler = new SessionMapHandler();
	public static String SESSION_MAP = "userMap";
	public static String GUEST_ID = "guest";
	private static String AUTH_MAP = "authMap";
    private static String USER_ID = "USER_ID";
    private static String USER_NM = "USER_NM";
    private static String LOGIN_ID = "LOGIN_ID";
    private static String LOGIN_IP = "LOGIN_IP";
    private static String DEPT_CD = "DEPT_CD";
    private static String ROLE_NM = "ROLE_NM";
    private static String ROLE_CD = "ROLE_CD";
    private static String IDV_VIEW = "IDV_VIEW";

    private static String SESSION_TIME_OUT = "SESSION_TIME_OUT";
    private static String LOGIN_STATUS = "LOGIN_STATUS";
    private static String USER_GB = "USER_GB";
    private static String IS_TFA_REQUIRED = "IS_TFA_REQUIRED";
    private static String SMS_CERTI_NO = "SMS_CERTI_NO";
    private static String SMS_CERTI_TIME_OUT = "SMS_CERTI_TIME_OUT";
    private static String CL_BIZ = "CL_BIZ";

    private static String EXIST_SESSION_ID = "EXIST_ID_SSSN";
    private static String EXIST_CONNECT_SERVER_IP = "EXIST_IP_CNTN_SVR";
    private static String EXIST_CONNECT_BROWSER = "EXIST_INFO_CNTN_PRSN_BRSR";
    private static String EXIST_CONNECT_PC = "EXIST_INFO_CNTN_PRSN_PC";

    private static String GWSSO_CALL = "GWSSO";
    private static String SSO_AUTH = "X";

    /**
	 * HttpSession의 GWSSO 호출여부를 설정한다.
	 *
	 * @param gwsso_call
	 * @throws Exception
	 */
	public void setGWSSOCall(String gwsso_call) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(GWSSO_CALL, gwsso_call);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * HttpSession의 GWSSO 호출여부를 가져온다.
	 *
	 * @return gwsso_call
	 * @throws Exception
	 */
	public String getGWSSOCall() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(GWSSO_CALL)) : "" ;
	}

	/**
	 * 내부사용자 계정 SSO_AUTH 정보를 설정한다.
	 *
	 * @param sso_auth
	 * @throws Exception
	 */
	public void setSSOAuth(Object sso_auth) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(SSO_AUTH, sso_auth);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 내부사용자 계정 SSO_AUTH 정보를 가져온다.
	 *
	 * @return sso_auth
	 * @throws Exception
	 */
	public String getSSOAuth() throws Exception {
		@SuppressWarnings("unchecked")
		String sso_auth = "";
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(SSO_AUTH)) : "" ;
	}

	/**
	 * 사용자 id를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getUserId() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);


		return userMap != null ? StringSupporter.stringValueOf(userMap.get(USER_ID), GUEST_ID) : GUEST_ID ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 사용자 id를 설정한다.
	 *
	 * @param userId
	 * @throws Exception
	 */
	public void setUserId(String userId) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(USER_ID, userId);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 로그인 id를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getLoginId() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);


		return userMap != null ? StringSupporter.stringValueOf(userMap.get(LOGIN_ID), GUEST_ID) : GUEST_ID ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 id를 설정한다.
	 *
	 * @param loginId
	 * @throws Exception
	 */
	public void setLoginId(String loginId) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(LOGIN_ID, loginId);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 사용자 이름을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getUserNm()  throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(USER_NM), "GUEST") : "GUEST" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 사용자 이름을 설정한다.
	 *
	 * @param userNm
	 * @throws Exception
	 */
	public void setUserNm(String userNm) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(USER_NM, userNm);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 로그인 ip를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getLoginIp() throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(LOGIN_IP), GlobalContext.getLocalIp()) : GlobalContext.getLocalIp() ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 ip를 설정한다.
	 *
	 * @param loginIp
	 * @throws Exception
	 */
	public void setLoginIp(String loginIp) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(LOGIN_IP, loginIp);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 사용자의 부서코드를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getDeptCd() throws Exception {
		@SuppressWarnings("unchecked")
		String deptCd = "";

		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(DEPT_CD)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 부서 코드를 설정한다.
	 *
	 * @param deptCd
	 * @throws Exception
	 */
	public void setDeptCd(String deptCd) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(DEPT_CD, deptCd);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 사용자의 권한 이름을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getRoleNm() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);


		return userMap != null ? StringSupporter.stringValueOf(userMap.get(ROLE_NM)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 권한 이름을 설정한다.
	 *
	 * @param roleNm
	 * @throws Exception
	 */
	public void setRoleNm(String roleNm) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(ROLE_NM, roleNm);

		setAttrToSessionUserMap(pMap);
	}

    /**
     * 사용자의 권한 코드를 가져온다.
     *
     * @return
     * @throws Exception
     */
	public String getRoleCd() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(ROLE_CD)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 권한 코드을 설정한다.
	 *
	 * @param roleCd
	 * @throws Exception
	 */
	public void setRoleCd(String roleCd) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(ROLE_CD, roleCd);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 세션 만료 제한시간을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getSessionTimeOut() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return  userMap != null ? StringSupporter.stringValueOf(userMap.get(SESSION_TIME_OUT)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 세션 만료 제한시간을 설정한다.
	 *
	 * @param sessionTimeOut
	 * @throws Exception
	 */
	public void setSessionTimeOut(String sessionTimeOut) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(SESSION_TIME_OUT, sessionTimeOut);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 상태를 설정한다.
	 *
	 * @param strStatus
	 */
	public void setLoginStatus(String strStatus) {
		try{
			LoginSuccessStatus[] successStatuses = LoginSuccessStatus.values();
			for(LoginSuccessStatus successStatus : successStatuses){
				if(StringSupporter.stringValueOf(successStatus).equals(strStatus)){
					setLoginSuccessStatus(successStatus);
					break;
				}
			}
		}catch(Exception e){
			setLoginFailStatus(strStatus);
		}
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 상태를 설정한다.
	 *
	 * @param loginStatus
	 */
	public void setLoginStatus(Enum loginStatus) {
		try{
			LoginSuccessStatus status = (LoginSuccessStatus) loginStatus;
			setLoginSuccessStatus(status);
		}catch(Exception e){
			LoginFailStatus status = (LoginFailStatus) loginStatus;
			setLoginFailStatus(status);
		}
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 성공 상태를 설정한다.
	 *
	 * @param successStatus
	 */
	public void setLoginSuccessStatus(LoginSuccessStatus successStatus) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(LOGIN_STATUS, successStatus);

		GlobalContext.setAttrToSession(LOGIN_STATUS, successStatus);
		setAttrToSessionUserMap(pMap);
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 실패 상태를 설정한다.
	 *
	 * @param strStatus
	 */
	public void setLoginFailStatus(String strStatus) {
		LoginFailStatus[] failStatuses = LoginFailStatus.values();

		for(LoginFailStatus failStatus : failStatuses){
			if(StringSupporter.stringValueOf(failStatus).equals(strStatus)){
				setLoginFailStatus(failStatus);
				break;
			}
		}
	}

	/**
	 * HttpSession의 사용자 정보 map에 로그인 실패 상태를 설정한다.
	 *
	 * @param failStatus
	 */
	public void setLoginFailStatus(LoginFailStatus failStatus) {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(LOGIN_STATUS, failStatus);

		setAttrToSessionUserMap(pMap);
	}



	/**
	 * 로그인 상태를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getLoginStatus() throws Exception {
	//public Enum getLoginStatus() throws Exception {
		String loginStatus = "";

		if(GlobalContext.getAttrFromSession(SESSION_MAP) == null){
			loginStatus = StringSupporter.stringValueOf(LoginSuccessStatus.REQUEST);
		}else{
			Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

			//Enum loginStatus = null;
			//loginStatus = (Enum) userMap.get(LOGIN_STATUS);
			loginStatus = userMap != null ? StringSupporter.stringValueOf(userMap.get(LOGIN_STATUS)) : "" ;
		}

		return loginStatus;
	}

	/**
	 * HttpSession의 사용자 정보 map에 사용자 구분을 설정한다.
	 *
	 * @param userGb
	 * @throws Exception
	 */
	//public void setUserGb(String userGb) throws Exception {
	public void setUserGb(UserType userGb) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(USER_GB, StringSupporter.stringValueOf(userGb));

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 사용자 구분을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getUserGb() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(USER_GB)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 개인정보 마스킹 처리 유무(Y : 안함, N: 처리함)
	 *
	 * @param masking
	 * @throws Exception
	 */
	public void setNoMasking(String masking) throws Exception {

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(IDV_VIEW, masking);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 개인정보 마스킹 처리 유무(Y : 안함, N: 처리함)를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getNoMasking() throws Exception {
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(IDV_VIEW)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 2FA필요 여부를 설정한다.
	 *
	 * @param isTfaRequired
	 * @throws Exception
	 */
	public void setIsTfaRequired(String isTfaRequired) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(IS_TFA_REQUIRED, isTfaRequired);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * 2FA필요 여부를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getIsTfaRequired() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(IS_TFA_REQUIRED)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map에 SMS 인증번호를 설정한다.
	 *
	 * @param smsCertiNo
	 * @throws Exception
	 */
	public void setSmsCertiNo(String smsCertiNo) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(SMS_CERTI_NO, smsCertiNo);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * SMS 인증번호를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getSmsCertiNo() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(SMS_CERTI_NO)) : "";
	}

	/**
	 * HttpSession의 사용자 정보 map에 SMS 인증 제한시간을 설정한다.
	 *
	 * @param smsCertiTimeOut
	 * @throws Exception
	 */
	public void setSmsCertiTimeOut(String smsCertiTimeOut) throws Exception {
		@SuppressWarnings("unchecked")

		Map<String, Object> pMap = new HashMap<>();
		pMap.put(SMS_CERTI_TIME_OUT, smsCertiTimeOut);

		setAttrToSessionUserMap(pMap);
	}

	/**
	 * SMS 인증 제한시간을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getSmsCertiTimeOut() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(SMS_CERTI_TIME_OUT)) : "" ;
	}

	/**
	 * 이전 로그인의 세션 id를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getExistSessionId() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(EXIST_SESSION_ID)) : "" ;
	}

	/**
	 * 이전 로그인의 접속 서버 ip를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getExistConnectServerIp() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(EXIST_CONNECT_SERVER_IP)) : "" ;
	}

	/**
	 * 이전 로그인의 접속 브라우저 종류를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getExistConnectBrowser() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(EXIST_CONNECT_BROWSER)) : "" ;
	}

	/**
	 * 이전 로그인의 접속 pc 명을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String getExistConnectPc() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(EXIST_CONNECT_PC)) : "" ;
	}

	/**
	 * 업무 구분 정보를 가져온다
	 *
	 * @return
	 * @throws Exception
	 */
	public String getClBiz() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> userMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

		return userMap != null ? StringSupporter.stringValueOf(userMap.get(CL_BIZ)) : "" ;
	}

	/**
	 * HttpSession의 사용자 정보 map 을 반환한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSessionMap() throws Exception{
		try {
    		Map<String, Object> sessionMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);
    		if(sessionMap != null){
    			return sessionMap;
    		}
		} catch(IllegalStateException ex){}
    	return null;
	}

    /**
     * HttpSession의 사용자 정보 map 에서 특정 정보를 가져온다.
     * 예제)
     *
     *
     * @param key dnpLoginPolicy.properties 파일에 "SESSION.KEY" prefix로 등록된 값들
     * @return
     * @throws Exception
     */
    public Object getAttrFromSessionUserMap(String key) {
    	try {
    		Map<String, Object> sessionMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);
    		if(sessionMap != null){
    			return sessionMap.get(key);
    		}
		} catch(IllegalStateException ex){}
    	return null;
    }

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
    public Object getUserData(String key) throws Exception{
    	try {
    		return getAttrFromSessionUserMap(key);
		} catch(IllegalStateException ex){}
    	return null;
    }

    /**
     * HttpSession 영역의 사용자 정보 map 에 객체를 담는다.
     *
     * @param pMap HttpSession에 담을 객체
     * @throws Exception
     */
    public void setAttrToSessionUserMap(Map pMap) {
    	try {
    		//map에 key가 기존 SESSION_MAP에 있으면 내용 바꾸고 기존 SESSION_MAP에 있던 key들 엎어치지 않음
    		Map<String, Object> curMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

    		Map<String, Object> newMap = new HashMap<>();
    		if(curMap != null){
    			newMap.putAll(curMap);
    			for(String curKey : curMap.keySet()){
        			for(Object pKey : pMap.keySet()){
        				if(curKey == pKey){
        					newMap.put(curKey, pMap.get(pKey));
        				}else{
        					newMap.put((String)pKey, pMap.get(pKey));
        				}
        			}
        		}
    		}else{
    			newMap.putAll(pMap);
    		}

    		GlobalContext.setAttrToSession(SESSION_MAP, newMap);
		} catch(IllegalStateException ex){}
    }

    /**
	 * HttpSession 영역의 사용자 정보 map 에 객체를 담는다.
	 *
	 * @param key HttpSession에 담을 객체 key
	 * @param value HttpSession에 담을 객체 value
	 * @throws Exception
	 */
	public void setAttrToSessionUserMap(String key, Object value) {
		try {
			// map에 key가 기존 SESSION_MAP에 있으면 내용 바꾸고 기존 SESSION_MAP에 있던 key들 엎어치지 않음
			Map<String, Object> curMap = (Map<String, Object>) GlobalContext.getAttrFromSession(SESSION_MAP);

			if (curMap != null) {
				curMap.put(key, value);
			}
		} catch(IllegalStateException ex){}
	}

    /**
	 * HttpSession 영역의 사용자 정보 map 에 객체를 담는다.
	 * 이 함수 대신 setAttrToSessionUserMap 을 사용할 것.
	 *
	 * @param key HttpSession에 담을 객체 key
	 * @param value HttpSession에 담을 객체 value
	 * @throws Exception
	 */
	@Deprecated
    public void setAttrFromSessionUserMap(String key, Object value) {
		// 함수명이 잘못되어서 deprecated 시키고 바뀐 함수로 이동
		setAttrToSessionUserMap(key, value);
    }

    /**
	 * HttpSession 객체의 'userMap'에 사용자 정보를 셋팅한다.
	 *
	 * @param userMap HttpSession 객체에 셋팅할 사용자 정보
	 * @throws Exception
	 */
	public void setUserInfoToSession(Map userMap) {
		//SEM API 사용자 role정보
		/*List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
		String userId = StringSupporter.stringValueOf(userMap.get(USER_ID));
		String deptCd = StringSupporter.stringValueOf(userMap.get(DEPT_CD));

		UserRoleFinder roleFinder;
		try {
			roleFinder = UserRoleFinder.getUserRoleFinder(SessionMapHandler.getUserGb());
			roleList = roleFinder.getUserRole(userId, deptCd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//TODO roleList가 없다면 에러 내야함!!!!!!!!!!!!!!!!!!!!!!!!

		String roleNm = "";
		String roleCd = "";
		if(roleList.size() > 0) {
			//TODO 사용자에 매핑된 role이 1개 이상일때 , 구분자로 붙여서 보내줄지 ? 정의 필요
			roleCd = StringSupporter.stringValueOf(roleList.get(0).get("CD"));
			roleNm = StringSupporter.stringValueOf(roleList.get(0).get("NM"));
		}
		userMap.put(ROLE_CD, roleCd);
		userMap.put(ROLE_NM, roleNm);

		*/
		GlobalContext.setAttrToSession(LOGIN_STATUS, getAttrFromSessionUserMap(LOGIN_STATUS));
        if(userMap != null || !userMap.isEmpty()) setAttrToSessionUserMap(userMap);
	}

	/**
     * HttpSession의 권한 정보를 제거한다.
     *
     */
    public void clearAuthMap() {
    	clear(AUTH_MAP);
    }

    /**
     * HttpSession의 권한 정보 map 에서 특정 정보를 가져온다.
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Object getAttrFromAuthMap(String key) {
    	try {
    		Map<String, Object> authMap = (Map<String, Object>) GlobalContext.getAttrFromSession(AUTH_MAP);
    		if(authMap != null){
    			return authMap.get(key);
    		}
		} catch(IllegalStateException ex){}
    	return null;
    }

	/**
	 * HttpSession 영역의 권한 정보 map 에 객체를 담는다.
	 *
	 * @param key HttpSession에 담을 객체 key
	 * @param value HttpSession에 담을 객체 value
	 * @throws Exception
	 */
	public void setAttrToAuthMap(String key, Object value) {
		try {
			Map<String, Object> authMap = (Map<String, Object>) GlobalContext.getAttrFromSession(AUTH_MAP);

			if (authMap == null) {
				authMap = new HashMap<>();
				GlobalContext.setAttrToSession(AUTH_MAP, authMap);
			}
			authMap.put(key, value);
		} catch(IllegalStateException ex){}
	}

	 public void clear(String key) {
    	try {
			GlobalContext.removeAttrToSession(key);
		} catch(IllegalStateException ex){}
    }


//	/**
//	 * SessionManager 인스턴스를 반환한다.
//	 *
//	 * @return
//	 */
//	public static SessionMapData getInstance() {
//		return sessionMapHandler;
//	}
}
