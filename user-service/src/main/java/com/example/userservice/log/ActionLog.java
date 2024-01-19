package com.example.userservice.log;


import com.example.userservice.core.context.GlobalContext;
import com.example.userservice.web.session.SessionMap;
import com.example.userservice.core.util.StringSupporter;
import com.example.userservice.log.client.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 사용자의 요청에 대해 log를 남긴다.
 *
 * @since      2017. 10. 17.
 * @version    1.0
 * @author     H2017288
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 10. 17.  H2017288        최초 생성
 * </pre></dd>
 */
@Slf4j
public class ActionLog implements InitializingBean {
	@Autowired
	private SessionMap sessionMapHandler;

	@Autowired
	private LogService logService;

	@Override
	public void afterPropertiesSet() throws Exception {}

	/**
	 * dispatcher-servlet.xml에 설정된 pointcut에 따라 메소드 실행 전 후에 이 메소드가 실행되어 로그를 남긴다.
	 *
	 * @param joinPoint 호출되는 대상 객체, 메소드 그리고 전달되는 파라미터 목록에 접근할 수 있는 메소드를 제공한다.
	 * @return 호출되는 대상 객체, 메소드가 반환한 값
	 */
	public Object insertAround(MethodInvocation joinPoint) throws Throwable{
		log.debug("[ActionLog Insert Start]");

		String mapp = UUID.randomUUID().toString();
		String urlSrv = GlobalContext.getRequestURI();

		String type = null;
		HttpServletRequest request = GlobalContext.getRequest();
		HttpServletResponse response = GlobalContext.getResponse();
		// view
		if (urlSrv.contains("/view.do")) {
			request.setAttribute("MAPP", mapp);
			type = "V";
			// ajax
		} else {
			response.setHeader("MAPP", mapp);
		}

		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long end = System.currentTimeMillis();
		long timeHndl = end - start;

		String cdSys = GlobalContext.getBaseSysCd();
		String idUser = "";
		String ipUser = "";
		try {
			idUser = sessionMapHandler.getLoginId();
			ipUser = sessionMapHandler.getLoginIp();
		} catch (Exception e) {
			idUser = "guest";
			ipUser = "127.0.0.1";
		}

		String navi = StringSupporter.stringValueOf(GlobalContext.getAttrFromRequest("reqAppNavi"));
		String url = urlSrv;
		long timeResp = 0L;
		long timeLoad = 0L;

		long sizeReq = request.getContentLengthLong();
		if (sizeReq == -1L) { sizeReq = 0L; }

		String sSizeResp  = response.getHeader("Content-Length");
		long sizeResp = sSizeResp != null ? Long.parseLong(sSizeResp, 10) : 0L;

		//사용 로그 insert
		Map<String, Object> logParam = new HashMap<>();
		logParam.put("CD_SYS"           , cdSys   ); //시스템 코드
		logParam.put("ID_USE_PRSN"      , idUser  ); //로그인 사용자 아이디
		logParam.put("INFO_PTH_WRK_SCRN", navi    ); //작업 화면 경로
		logParam.put("INFO_URL_RQST"    , url     ); // url
		logParam.put("TYPE_USE"         , type    ); // type
		logParam.put("TIME_HNDL"        , timeHndl); // 서버 처리시간
		logParam.put("TIME_RSPS"        , timeResp); // 응답시간 0
		logParam.put("TIME_LOAD"        , timeLoad); // 로딩시간 0
		logParam.put("SIZE_RQST"        , sizeReq ); // 요청크기
		logParam.put("SIZE_DATA"        , sizeResp); // 응답크기
		logParam.put("ID_SPED_MAPP"     , mapp    ); // mapping

		logParam.put("ID_PRSN"        	, idUser); //
		logParam.put("IP_PRSN"     		, ipUser); //

		//입력, 수정 프로그램
		if(GlobalContext.getRequest() != null){
			String reqAppNavi;
			if (!navi.isEmpty())
				reqAppNavi = navi.substring(navi.lastIndexOf('>') + 1);
			else
				reqAppNavi = "emptyService";

			logParam.put("CD_IPUT_SYS", cdSys);
			logParam.put("CD_IPUT_SRV_ITEM", reqAppNavi);
			logParam.put("CD_REV_SYS", cdSys);
			logParam.put("CD_REV_SRV_ITEM", reqAppNavi);
		}

//		logService.saveActionLog(logParam);

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(new CallableImpl(logParam));
		executorService.shutdown();

		return result;
	}

	private class CallableImpl implements Callable<Integer> {
		private Map<String, Object> logParam;

		public CallableImpl(Map<String, Object> logParam){
			this.logParam = logParam;
		}

		@Override
		public Integer call() throws Exception {
			logService.saveActionLog(this.logParam);
			return 0;
		}
	}
}
