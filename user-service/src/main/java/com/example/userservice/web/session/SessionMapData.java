package com.example.userservice.web.session;


import com.example.userservice.web.status.LoginFailStatus;
import com.example.userservice.web.status.LoginSuccessStatus;
import org.hibernate.usertype.UserType;

public interface SessionMapData extends SessionMap {


    /**
     * HttpSession의 사용자 정보 map에 로그인 성공 상태를 설정한다.
     *
     * @param successStatus
     */
    void setLoginSuccessStatus(LoginSuccessStatus successStatus) throws Exception;

    /**
     * HttpSession의 사용자 정보 map에 로그인 실패 상태를 설정한다.
     *
     * @param failStatus
     */
    void setLoginFailStatus(LoginFailStatus failStatus);

    /**
     * HttpSession의 사용자 정보 map에 사용자 구분을 설정한다.
     *
     * @param userGb
     * @throws Exception
     */
    //void setUserGb(String userGb) throws Exception {
    void setUserGb(UserType userGb) throws Exception;
}
