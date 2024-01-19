package com.example.userservice.core.context;


import com.example.userservice.api.config.Device;
import com.example.userservice.core.exception.DnpException;
import com.example.userservice.core.exception.ErrorCode;
import com.example.userservice.core.file.AttachmentService;
import com.example.userservice.core.property.PropertyHelper;
import com.example.userservice.web.session.SessionMap;
import com.example.userservice.core.util.StringSupporter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.management.MalformedObjectNameException;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Context 정보를 핸들링하는 클래스
 * <p>
 * 시스템 정보, Session 정보 등을 제공한다.
 * </p>
 *
 * @since      2017. 11. 23.
 * @version    1.0
 * @author     T160142
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 11. 23.  T160142         최초 생성
 * </pre></dd>
 */
@Slf4j
@Component
public class GlobalContext {
//	private static boolean _privacy = false;

	private static SessionMap sessionMapHandler;

	private static String uploadFileRoot;

	private static String hostName;

	private static ResourceLoader resourceLoader;

	private static String activeProfile;

	@Value("${spring.profiles.active}")
	public void setActiveProfile(String activeProfile) {
		this.activeProfile = activeProfile;
	}

	@Value("${dnp.path.upload-root}")
	public void setUploadFileRoot(String upladroot) {
		this.uploadFileRoot = upladroot;
	}

	@Value("${dnp.host-name}")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Autowired
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**************************************************************************************************************************
	 *
	 * Application Context
	 *
	 **************************************************************************************************************************/
	/**
     * ApplicationContext에서 직접 Bean 객체를 가져온다.
     *
     * @param beanName 가져올 bean의 이름
     * @return
     */
    public static Object getBean(String beanName) {
    	ApplicationContext appContext = DnpApplicationListener.getApplicationContext();
		if (appContext != null){
			try{
				return appContext.getBean(beanName);
//			}catch(NoSuchBeanDefinitionException e){
//				return null;
			} catch(Exception ex) {
				return null;
			}
		}
        return null;
    }

    /**
     * ApplicationContext에서 직접 Bean 객체를 가져온다.
     *
     * @param classType 가져올 bean의 객체 타입
     * @return
     */
    public static Object getBean(Class<?> classType) {
    	ApplicationContext appContext = DnpApplicationListener.getApplicationContext();
		if (appContext != null){
			try{
				return appContext.getBean(classType);
//			}catch(NoSuchBeanDefinitionException en){
//				return null;
			} catch(Exception ex) {
				return null;
			}
		}
        return null;
    }

    /**
     * ApplicationContext에서 직접 Bean 객체를 가져온다.
     *
     * @param beanName 가져올 bean의 이름
     * @param classType 가져올 bean의 객체 타입
     * @return
     */
    public static Object getBean(String beanName, Class<?> classType) {
    	ApplicationContext appContext = DnpApplicationListener.getApplicationContext();
		if (appContext != null){
			try{
				return appContext.getBean(beanName, classType);
//			}catch(NoSuchBeanDefinitionException e){
//				return null;
			} catch(Exception ex) {
				return null;
			}
		}
        return null;
    }

    /**
     * 특정 클래스형의 bean을 Map형태로 반환한다
     * <p>
     * ApplicationContext, Root ApplicationContext에서 모두 찾아 반환한다.
     * </p>
     *
     * @param type 찾을 클래스형
     * @return
     */
    public static Map<String, ?> getBeansOfType(Class type){
    	ApplicationContext appContext = DnpApplicationListener.getApplicationContext();
    	WebApplicationContext rootContext = (WebApplicationContext) DnpApplicationListener.getRootApplicationContext();
    	Map<String, ?> returnMap = new HashMap();
    	if(appContext != null) {
    		returnMap.putAll(appContext.getBeansOfType(type));
    	}

    	if(rootContext != null){
    		returnMap.putAll(rootContext.getBeansOfType(type));
    	}

    	if(returnMap.isEmpty()) returnMap = null;

    	return returnMap;
    }




	/**************************************************************************************************************************
	 *
	 * Global Value
	 *
	 **************************************************************************************************************************/
    /**
	 * ApplicationContext getter
	 *
	 * @return
	 * @throws Exception
	 */
	public static ApplicationContext getApplicationContext() {
		ApplicationContext appContext = DnpApplicationListener.getRootApplicationContext();
		if (appContext != null){
			return appContext;
		} else {
			return null;
		}
	}

    /**
	 * ServletContext getter
	 *
	 * @return
	 * @throws Exception
	 */
	public static ServletContext getServletContext() {
		HttpServletRequest request = getRequest();
    	if (request != null)
    		return request.getServletContext();
    	else{
    		ApplicationContext appContext = DnpApplicationListener.getApplicationContext();
    		ServletContext context = null;
    		if (appContext != null && appContext instanceof WebApplicationContext){
				WebApplicationContext ctx = (WebApplicationContext)appContext;
			    context = ctx.getServletContext();
			}
    		return context;
    	}
	}

    /**
     * HttpServletRequest 객체를 가져온다.
     *
     * @return
     * @throws Exception
     */
    public static HttpServletRequest getRequest() {
    	try {
    		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
			if (attr != null)
				return attr.getRequest();
		} catch(Exception ex){}
    	return null;
    }

    /**
     * HttpServletResponse 객체를 가져온다.
     *
     * @return
     * @throws Exception
     */
    public static HttpServletResponse getResponse() {
    	try {
			ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
			if (attr != null)
				return attr.getResponse();
		} catch(Exception ex){}
    	return null;
    }

    /*
     * ServletContext 의 ContextPath 정보를 가져온다.
     */
    public static String getContextPath() throws Exception {
    	ServletContext context = getServletContext();

    	String sPath = "";

		if (context != null)
			sPath = context.getContextPath();

    	if ("".equals(sPath))
    		sPath = "/";

    	return sPath;
    }


    /*
     * ServletContext 의 ContextPath 정보를 가져온다.
     */
    public static String getScheme() throws Exception {
    	HttpServletRequest request = getRequest();
    	String scheme = "http";

    	if (request != null) {
    		scheme = request.getHeader("X-Forwarded-Proto");
			if (scheme == null || scheme.length() == 0 || "unknown".equalsIgnoreCase(scheme)) {
				scheme = request.getScheme();
			}
    	}
    	return scheme;
    }

   public static String getLocalAddr( ) {
	   try {
		   if ("".equals(hostName) == false) {
			   InetAddress ip = InetAddress.getByName(hostName);
			   return ip.getHostAddress();
		   } else {
			   HttpServletRequest request = getRequest();
			   return request.getLocalAddr();
		   }
	   } catch (Exception e) {
		   return "0.0.0.0";
	   }
   }

	/**
	 * client의 ip주소를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getLocalIp() {
		return getClientInfo();
	}
	
	public static String getLocalIp(HttpServletRequest request) {
        return getClientInfo(request);
	}
	
	/**
	 * client의 ip정보를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	private static String getClientInfo() {
	    return getClientInfo(getRequest());
	}
	
	private static String getClientInfo(HttpServletRequest request) {
		if (request == null)
			return ":::";

		String ip = StringSupporter.stringValueOf(request.getHeader("X-Forwarded-For")).split(",")[0];

	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-Real-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("X-Original-Forwarded-For");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_FORWARDED");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_VIA");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("REMOTE_ADDR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
		return ip;
	}
	
	/**
	 * 
	 */
	public static boolean isIntranet() {
	    return isIntranet(getRequest());
	}
	
	public static boolean isIntranet(HttpServletRequest request) {
	    boolean intranet = false;
	    String localIp = getLocalIp(request);
	    List<String> ipClasses = getIntranetClasses();
	    for (String ipClass : ipClasses) {
	        if (localIp.startsWith(ipClass)) {
	            intranet = true;
	            break;
	        }
	    }
	    return intranet;
	}
	
    @SuppressWarnings("unchecked")
    public static List<String> getIntranetClasses() {
        List<String> addrs = (List<String>) PropertyHelper.getVector("loginProperty", "INTRANET.CLASS", new Vector<String>());
        System.out.println("Classes : " + addrs.toString());
        return addrs;
    }
	
	/**
     * HttpServletRequest에서 attribute 값을 가져온다.
     *
     * @param key attribute에서 가져올 key 값
     * @return
     * @throws Exception
     */
    public static Object getAttrFromRequest(String key) throws Exception {
    	try {
    		HttpServletRequest request = getRequest();
        	if (request != null)
				return request.getAttribute(key);
		} catch(IllegalStateException ex){}
    	return null;
    }

    /**
     * HttpServletRequest에 attribute 값을 설정한다.
     *
     * @param key attribute에 설정할 key 값
     * @param obj attribute에 설정할 value 값
     * @throws Exception
     */
    public static void setAttrToRequest(String key, Object obj) throws Exception {
    	try {
    		HttpServletRequest request = getRequest();
        	if (request != null)
        		request.setAttribute(key, obj);
		} catch(IllegalStateException ex){}
    }

    /**
	 * 서버 또는 로컬의 웹 어플리케이션 서버의 docBase설정값을 반환한다.
	 *
	 * @param uri 절대 경로로 찾아올 uri
	 * @return
	 * @throws Exception
	 * @Description (절대 경로 가져오기) ex)D:\project\webapps\npframework\
	 */
	public static String getRealPath(String uri) throws Exception {
		ServletContext context = getServletContext();
    	if (context != null)
    		return context.getRealPath(uri);
        return null;
	}

	/**
	 * 요청 uri 값을 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getRequestURI() throws Exception {
		HttpServletRequest request = getRequest();
    	if (request != null)
    		return request.getRequestURI();
        return null;
	}

	/*
     * ServletContext 의 ContextPath 를 제외한 Url 정보를 가져온다.
     */
    public static String getSystemRequestURI() throws Exception {
		String url = getRequestURI();
    	String contextPath = getContextPath();

		if (contextPath.equals("/") == false) {
			url = url.substring(contextPath.length());
		}

    	return url;
    }
    
    /* 
     * System Domain URL을 가져온다.
     */
    public static String getSystemURL(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) {
            scheme = request.getScheme();
        }
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }
    
    public static String getSystemURL() {
        return getSystemURL(getRequest());
    }

    
	/**
	 * HttpServletRequest의 header 정보를 가져온다.
	 *
	 * @param key header 정보에서 가져올 정보의 key 값
	 * @return
	 * @throws Exception
	 */
	public static String getHeader(String key) throws Exception{
		HttpServletRequest request = getRequest();
    	if (request != null)
    		return request.getHeader(key);
        return null;
	}

	/**
	 * Client의 브라우저 종류를 반환한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getClientBrowserType() throws Exception {
		HttpServletRequest request = getRequest();
		return getClientBrowserType(request);
	}
	
	public static String getClientBrowserType(HttpServletRequest request) throws Exception {
		if(request == null) return "";

		String sUserAgent = StringSupporter.stringValueOf(request.getHeader("User-Agent")).toLowerCase();
		String browser = null;
        
        if(sUserAgent.indexOf("chrome") > -1) {                                             // Chrome  
            browser = "CHROME";
        } else if(sUserAgent.indexOf("edge") > -1) {                                         // Edge
            browser = "EDGE";
        } else if(sUserAgent.indexOf("trident") > -1) {                                      // IE
            browser = "IE";
        } else if (sUserAgent.contains("msie")) {
            browser = "IE";
        } else if(sUserAgent.indexOf("firefox") > -1) {                                       // Firefox
            browser = "FIREFOX";
        } else if(sUserAgent.indexOf("safari") > -1) {  // Safari
            browser = "SAFARI";     
        } else if(sUserAgent.indexOf("opera") > -1 || sUserAgent.indexOf("opr") > -1) {       // Opera
            browser = "OPERA";
        } else if(sUserAgent.indexOf("whale") > -1) {                                        // Naver Whale
            browser = "WHALE";
        } else {
            browser = "";
        }
        
        return browser;
	}

	/**
	 * 브라우저별 파일다운로드 명
	 *
	 * @param filename
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	public String getDisposition(String filename, String browser) throws Exception {
		String encodedFilename = null;
		if (browser.equalsIgnoreCase("IE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equalsIgnoreCase("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equalsIgnoreCase("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equalsIgnoreCase("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
		} else if (browser.equalsIgnoreCase("Edge")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new RuntimeException("Not supported browser");
		}
		return encodedFilename;
	}

	/**
	 * 모바일 접속 체크
	 *
	 * @return
	 * @throws Exception
	 */
	public static Boolean isMobile() {
	    return isMobile(getRequest());
	}
	
	public static Boolean isMobile(HttpServletRequest request) {
		if(request == null) return false;

		Device device = Device.getDevice(request);

		return device.isMobileDevice();
	}

	public static String getServerIp() {
		String ip = null;
		try{
			ip = InetAddress.getByName("dockerhost").getHostAddress();
		} catch (UnknownHostException e) {}
		
		if (ip == null) {
		    try {
                InetAddress addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress();
            } catch (Exception ex) {}
		}
		
		if (ip == null) {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                            //return inetAddress.getHostAddress().toString();
                            ip = inetAddress.getHostAddress();
                            break;
                        }
                    }
                    if (ip != null) break;
                }
            } catch (SocketException ex) {}
		}
		
		return ip;
	}
	
	/**
	 * 서버 ip:port 반환
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getServerIpPort() {
		return getServerIp() + getServerPort(":");
	}
	
	public static String getServerIpPort(HttpServletRequest request) {
	    return getServerIp() + getServerPort(request, ";");
	}

	public static String getServerPort(String prefix) {
	    return getServerPort(getRequest(), prefix);
	}
	
	
	public static String getServerPort(HttpServletRequest request, String prefix) {
        try{
            String port = "";
            if(prefix != null){
                port = StringSupporter.stringValueOf(prefix + getPort(request));
            }else{
                port = StringSupporter.stringValueOf(prefix + getPort(request));
            }

            return port;
        }catch(Exception e){
            //TODO default 8080? 80?
            return "";
        }
    }
	/**
	 * server의 port
	 * <p>
	 * TODO
	 * 현재 주석되어있는 방식으로 가져오면 tomcat에 설정된 모든 포트가 나옴
	 * 추후에 현재 내가 접속한 서버의 ip를 알수 있도록 관리할 것이기 때문에
	 * 현재는 property에 설정하고 읽어오도록함
	 * </p>
	 *
	 * @return
	 */
	public static String getPort() throws MalformedObjectNameException { //
		/*
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

        return StringSupporter.stringValueOf(objectNames.iterator().next().getKeyProperty("port"));
        */
		return getPort(getRequest());
	}
	
	public static String getPort(HttpServletRequest request) throws MalformedObjectNameException {
		if(request == null)
			return PropertyHelper.getString("commonProperty", "DNP.SERVER.PORT");
		else {
			return Integer.toString(request.getServerPort());
		}
	}

	public static String getHostName() throws UnknownHostException{
		InetAddress local = InetAddress.getLocalHost();
		//local.getCanonicalHostName();  //전체 컴퓨터 이름 DH2017288N01.DAELIM.COM
  		//local.getHostName();           //컴퓨터 이름 DH2017288N01
		return StringSupporter.stringValueOf(local.getHostName());
	}

	/******************************************************** Session ********************************************************/
	/**
     * HttpSession 객체를 가져온다.
     *
     * @return
     * @throws Exception
     */
    public static HttpSession getSession() {
		return getSession(false);
    }

    /**
     * HttpSession 객체를 가져온다.
     *
     * @param gen 재생성여부 (true/false)
     * @return
     * @throws Exception
     */
    public static HttpSession getSession(boolean gen) {
    	HttpServletRequest request = getRequest();
    	if (request != null)
    		return request.getSession(gen);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static boolean isSessioned(HttpServletRequest request) {
        boolean sessioned = false;

        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
                if (userMap != null) {
                    try {
                        //if (userMap.get("USER_ID") != null && StringSupporter.stringValueOf(LoginSuccessStatus.SUCCESS).equals(StringSupporter.stringValueOf(userMap.get("LOGIN_STATUS")))) {
						if (userMap.get("USER_ID") != null && "SUCCESS".equals(userMap.get("LOGIN_STATUS"))) {
                            sessioned = true;
                        }
                    } catch (Exception e) {
                        
                        e.printStackTrace();
                    }
                }
            }
        }
        return sessioned;
    }
    
    public static boolean isSessioned() {
        return isSessioned(getRequest());
    }
    
    /**
     * HttpSession에서 attribute값을 가져온다.
     *
     * @param key attribute에서 가져올 key 값
     * @return
     * @throws Exception
     */
    public static Object getAttrFromSession(String key) {
    	try {
    		HttpSession session = getSession();
        	if (session != null)
				return session.getAttribute(key);
		} catch(IllegalStateException ex){}
    	return null;
    }

    /**
     * HttpSession에 attribute값을 설정한다.
     *
     * @param key attribute에 설정할 key 값
     * @param obj attribute에 설정할 value 값
     * @throws Exception
     */
    public static void setAttrToSession(String key, Object obj) {
    	try {
    		HttpSession session = getSession();
        	if (session != null)
				session.setAttribute(key, obj);
		} catch(IllegalStateException ex){}
    }

    /**
     * HttpSession에 attribute값을 제거한다.
     *
     * @param key attribute에 설정할 key 값
     * @throws Exception
     */
    public static void removeAttrToSession(String key) {
    	try {
    		HttpSession session = getSession();
        	if (session != null)
				session.removeAttribute(key);
		} catch(IllegalStateException ex){}
    }

    public static String getBaseSysCd() {
		String cd = getSrvSys();
		if (cd == null)
			return getSysCd();
		else
			return cd;
    }

    /**
	 * </pre>
	 *
	 * @return 요청에 대한 시스템 코드가 없을 경우 프로퍼티에 설정된 기본 시스템 코드가 반환된다.
	 * @throws Exception
	 */
	public static String getSysCd() {
		try{
			@SuppressWarnings("unchecked")
			HttpServletRequest request = getRequest();

			Object oCD = getsessionMapHandler().getAttrFromSessionUserMap("CD_SYS");
			if (oCD != null) {
				return (String)oCD;
			} else {
				if (request != null) {
					oCD = request.getAttribute("CD_SYS");
				}

				if (oCD != null)
					return (String)oCD;
			}
		} catch(Exception e){ }

		return PropertyHelper.getString("commonProperty", "DNP.DEFAULT.CD_SYS", "DHE4");
	}

	/**
	 * </pre>
	 *
	 * @return 요청에 대한 서비스 시스템를 반환된다.
	 * @throws Exception
	 */
	public static String getSrvSys() {
		if (getsessionMapHandler() == null)
			return null;
		Object oSrvSys = getsessionMapHandler().getAttrFromSessionUserMap("CD_SRV_SYS");
		if (oSrvSys == null)
			return null;
		else
			return (String)oSrvSys;
	}

	/**
	 * </pre>
	 *
	 * @return 요청에 대한 시스템 CI를 반환된다.
	 * @throws Exception
	 */
	public static String getSysCI() {
		Object oCdCi = getsessionMapHandler().getAttrFromSessionUserMap("CD_CI");
		if (oCdCi == null)
			return null;
		else
			return (String)oCdCi;
	}
	
	public static String getPsid() {
	    String psid = PropertyHelper.getString("commonProperty", "DNP.DEFAULT.CD_SYS");
	    return psid;
	}

	public static String getSsid() {
	    return getSsid(getRequest());
	}
    
	@SuppressWarnings("unchecked")
    public static String getSsid(HttpServletRequest request) {
	    String ssid = null;
	    if (request == null) {
	        return getPsid();
	    }
	    
	    HttpSession session = request.getSession(false);
        
	    if (ssid == null) {
    	    if (session != null) {
    	        Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
    	        if (userMap != null) {
    	            ssid = (String) userMap.get("SSID");
                    ssid = Objects.toString(ssid, (String) (userMap.get("CD_SRV_SYS")));
    	        } else {
    	            ssid = (String) session.getAttribute("SSID");
                    ssid = Objects.toString(ssid, (String) (session.getAttribute("CD_SRV_SYS")));	            
    	        }
    	    } else {
                ssid = (String) request.getAttribute("SSID");
                ssid = Objects.toString(ssid, (String) (request.getAttribute("CD_SRV_SYS"))); 
    	    }
	    }
	    
	    if (ssid == null) {
            if (session != null) {
                Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
                if (userMap != null) {
                    ssid = Objects.toString(ssid, (String) (userMap.get("CD_SYS")));
                } else {
                    ssid = Objects.toString(ssid, (String) (session.getAttribute("CD_SYS")));               
                }
            } else {
                ssid = Objects.toString(ssid, (String) (request.getAttribute("CD_SYS")));   
            }
	    }
	    if (ssid == null) {
	        ssid = getPsid();
	    }
	    return ssid;
	}
	
	/**
	 * 현재 언어코드를 가져온다.
	 *
	 * @return HttpServletRequest에서 언어코드를 가져올 수 없을 경우 "kor"가 반환된다.
	 * @throws Exception
	 */
	public static String getLngeCd() {
		@SuppressWarnings("unchecked")
		HttpServletRequest request = getRequest();
		Object oCD = null;

		if (request != null) {
			oCD = request.getAttribute("CD_LNGE");
		}

		if (oCD == null)
			return  PropertyHelper.getString("commonProperty", "DNP.DEFAULT.CD_LNGE", "kor");
		else
			return (String)oCD;
	}

	/**
	 * 현재 2자리 언어코드를 가져온다.
	 *
	 * @return HttpServletRequest에서 언어코드를 가져올 수 없을 경우 "ko"가 반환된다.
	 * @throws Exception
	 */
	public static String getLnge2Cd() {
		@SuppressWarnings("unchecked")
		HttpServletRequest request = getRequest();
		Object oCD = null;

		if (request != null) {
			oCD = request.getAttribute("CD2_LNGE");
		}

		if (oCD == null)
			return PropertyHelper.getString("commonProperty", "DNP.DEFAULT.CD2_LNGE", "ko");
		else
			return (String)oCD;
	}

	/**
	 * 시스템 계열사코드를 가져온다.
	 *
	 * @return 계열사 설정 코드.
	 * @throws Exception
	 */
	public static String getCpnyCd() {
		return  PropertyHelper.getString("loginProperty", "RATHON.IAM.RT.CODE", "DAE");
	}

	/**
     * 현재 client가 개발자 모드인지를 확인한다.
     * ip가 127.0.0.1일 경우 개발자 모드로 인식한다.
     *
     * @return
     * @throws Exception
     */
    public static String getDebugMode() {
    	String strDebugMode = "false";
    	boolean systemManager = false;
   		//systemManager여부는 추후에 DB에서 select해서 binding한다.
   		String strIp = getLocalIp();

        if("127.0.0.1".equals(strIp)){
          	strDebugMode = "true";
        }else if(systemManager){
           	strDebugMode = "true";
        }

    	return strDebugMode;
    }

    /**
     * 서버 타입을 반환하는 메소드
     * enum ServerType
     * LOCAL / DEV / TEST / REAL
     * context-properties.xml 참조
     * IP 가 127.0.0.1인 경우 무조건 LOCAL을 반환한다.
     *
     * @return ServerType
     */
    public static ServerType getServerType() {
    	ServerType type = null;
    	String mode = GlobalContext.getDebugMode();
    	if ("true".equals(mode)) {
    		type = ServerType.LOCAL;
    	}
    	if (type == null) {
	    	String sType = activeProfile; //System.getProperty("spring.profiles.active");
	    	if ("real".equals(sType)) {
	    		type = ServerType.REAL;
	    	} else if ("test".equals(sType)) {
	    		type = ServerType.TEST;
	    	} else if ("dev".equals(sType)) {
	    		type = ServerType.DEV;
	    	} else if ("local".equals(sType)) {
	    		type = ServerType.LOCAL;
	    	} else {
	    		type = ServerType.LOCAL;
	    	}
    	}
    	return type;
    }


    /**
	 * 시스템의 업로드 루트 경로를 조회한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getUploadRootPath() throws Exception {
		return getUploadRootPath(true);
	}

	/**
	 * 시스템의 업로드 루트 경로를 조회한다.
	 *
	 * @return
	 * @throws Exception
	 */
//	private static String _uploadRootPath = "";
	private static String getUploadRootPathDB() {
//		if(("").equals(_uploadRootPath)){
//			//DB에서 가져옴
//			//TODO 통테 끝나고 DB에서 가져오는거 주석 해제
//			/*Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("SERVER_TYPE", getServerType());
//			AttachmentService attachmentService = getBean(AttachmentService.class);
//			Map<String, Object> pathMap = attachmentService.selectOneUploadRootPathServerType(paramMap);
//
//			if(pathMap != null){
//				_uploadRootPath = StringSupporter.stringValueOf(pathMap.get("PTH_UPLD"));
//			}*/
//			_uploadRootPath = PropertyHelper.getString("commonProperty", "DNP.FILE.UPLOAD_ROOT_PATH_TEMP", "");
//		}
		return uploadFileRoot;
	}

	public static String getUploadRootPath(Boolean appendContextPath) throws Exception {
		HttpServletRequest request = getRequest();

		if (request != null && request.getAttribute("ROOTUPLOADPATH") != null) {
			return (String)request.getAttribute("ROOTUPLOADPATH");
		}

//		ServletContext context = getServletContext();
		String contextRootPath = "";
		if(appendContextPath) {
			//contextRootPath = context.getRealPath("/");

			//TODO 테스트, 운영서버는 이중화되어있어 NAS 경로로 해야함
			// /app_attach/DCM
			// /app_attach/DCR
			contextRootPath = getUploadRootPathDB();

		}

		AttachmentService attachmentService = (AttachmentService)getBean(AttachmentService.class);
		Map<String, Object> resultMap = attachmentService.selectOneUploadRootPath(new HashMap<String, Object>());
		if(resultMap == null) {
			return "";
		}else{
			if(("").equals(StringSupporter.stringValueOf(resultMap.get("PTH_UPLD"), ""))){
				return contextRootPath;
			}else{
				String path = contextRootPath + StringSupporter.stringValueOf(resultMap.get("PTH_UPLD"));
				if (request != null) {
					request.setAttribute("ROOTUPLOADPATH", path);
				}
				return path;
			}
		}
	}

	/**
	 * 시스템의 임시 업로드 경로를 조회한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getTempUploadPath() {
		String rootPath = getUploadRootPathDB();
		if(("").equals(rootPath)){
			return "";
		}else{
			return rootPath + "/temp";
		}
	}

	/**
	 * 시스템의 템플릿 파일 경로를 조회한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getTemplateUploadPath() throws Exception {
		HttpServletRequest request = getRequest();

		if (request != null && request.getAttribute("TEMPUPLOADPATH") != null) {
			return (String)request.getAttribute("TEMPUPLOADPATH");
		}

		AttachmentService attachmentService = (AttachmentService) getBean(AttachmentService.class);
		Map<String, Object> resultMap = attachmentService.selectOneTemplateUploadPath(new HashMap<String, Object>());
		if(resultMap != null) {
			String pathTemp = StringSupporter.stringValueOf(resultMap.get("PTH_TMPT"));

			if(("").equals(pathTemp) != false){
				ServletContext context = getServletContext();
				String path = context.getRealPath(pathTemp);
				if (path == null) {
					path = pathTemp;
				}

				if (request != null) {
					request.setAttribute("TEMPUPLOADPATH", path);
				}
				return path;
			}
		}
		return "";
	}
	public static URL getTemplateUploadURL() throws Exception {
		return getTemplateUploadURL(null);
	}

	public static URL getTemplateUploadURL(String path) throws Exception {
		HttpServletRequest request = getRequest();
		if (request != null && request.getAttribute("TEMPUPLOADPATH") != null) {
			return getURL((String)request.getAttribute("TEMPUPLOADPATH"));
		}

		AttachmentService attachmentService = (AttachmentService) getBean("attachmentService");
		Map<String, Object> resultMap = attachmentService.selectOneTemplateUploadPath(new HashMap<String, Object>());
		if(resultMap != null) {

			String pathTemp = StringSupporter.stringValueOf(resultMap.get("PTH_TMPT"));

			if (path != null) {
				if (path.startsWith("/"))
					pathTemp += path;
				else
					pathTemp += "/" + path;
			}

			if(("").equals(pathTemp) == false){
				if (request != null) {
					request.setAttribute("TEMPUPLOADPATH", pathTemp);
				}

				return getURL(pathTemp);
			}
		}

		return null;
	}

	public static URL getURL(String path) {
		try {
			ServletContext context = getServletContext();

			if (path != null) {
				path = path.replaceAll("//", "/");
			}

			String rPath = context.getRealPath(path);
			File file;
			if (rPath != null) {
				file = new File(rPath);
				if (file.exists()) {
					return file.toURI().toURL();
				}
			} else {

				try {
					file = new File(path);
				} catch (Exception e) {
					file = null;
				}

				if (file != null && file.exists()) {
					return file.toURI().toURL();
				}

				Resource resource = resourceLoader.getResource(path);
				if (resource != null) {
					try {
						return resource.getURL();
					} catch (IOException e) {}
				}

				return context.getResource(path);
			}
		} catch (MalformedURLException e) {
			GlobalContext.setException(e);
			/*fuck off*/
		}

		return null;
	}

	/**
	 * 병합권한 사용 여부를 조회한다
	 *
	 * 타 시스템에서 호출해서 사용할 경우 QueryMap에
	 * 호출한 시스템에서 자기 시스템 auth.properties 에 USE_MERGE_AUTH 값을 보내줌
	 *
	 * 그 정보가 최우선이고 없을 경우
	 * 구동되고 있는 시스템의 auth.properties를 읽어옴
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getUseMergeAuth() {
		@SuppressWarnings("unchecked")
		HttpServletRequest request = getRequest();

		String mergeAuth = PropertyHelper.getString("authProperty", "USE_MERGE_AUTH", "false");

		if(GlobalContext.getBean("bizSysAuthProperty") != null){
			//업무파트 별 xxxAuth.properties 파일에 USE_MERGE_AUTH 정의 된 값으로 병합 권한 여부 체크
			mergeAuth = PropertyHelper.getString("bizSysAuthProperty", "USE_MERGE_AUTH", mergeAuth);
		}

		if(request != null){
			QueryData qMap = (QueryData) request.getAttribute("QUERY_MAP");
			if(qMap.getMergeAuth() != null){
				mergeAuth = qMap.getMergeAuth();
			}
		}

		Object oMA = getsessionMapHandler().getAttrFromSessionUserMap("MERGE_AUTH");
		if (oMA != null)
			mergeAuth = (String)oMA;

		return mergeAuth;
	}

	/**
	 * 부서권한 사용 여부를 조회한다
	 *
	 * 타 시스템에서 호출해서 사용할 경우 QueryMap에
	 * 호출한 시스템에서 자기 시스템 auth.properties 에 USE_DEPT_AUTH 값을 보내줌
	 *
	 * 그 정보가 최우선이고 없을 경우
	 * 구동되고 있는 시스템의 auth.properties를 읽어옴
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getUseDeptAuth() {
		@SuppressWarnings("unchecked")
		HttpServletRequest request = getRequest();

		String deptAuth = PropertyHelper.getString("authProperty", "USE_DEPT_AUTH", "true");

		if(GlobalContext.getBean("bizSysAuthProperty") != null){
			//업무파트 별 xxxAuth.properties 파일에 USE_MERGE_AUTH 정의 된 값으로 병합 권한 여부 체크
			deptAuth = PropertyHelper.getString("bizSysAuthProperty", "USE_DEPT_AUTH", deptAuth);
		}

		if(request != null){
			QueryData qMap = (QueryData) request.getAttribute("QUERY_MAP");
			if(qMap.getMergeAuth() != null){
				deptAuth = qMap.getDeptAuth();
			}
		}

		Object oMA = getsessionMapHandler().getAttrFromSessionUserMap("DEPT_AUTH");
		if (oMA != null)
			deptAuth = (String)oMA;

		return deptAuth;
	}

	/**
	 * 개인정보 설정 여부.
	 *
	 * @return
	 * @throws Exception
	 */
	public static boolean isLoadPrivacy() {
		ServletContext context = getServletContext();
		if (context != null){
			try{
				return "Y".equalsIgnoreCase(StringSupporter.stringValueOf(context.getAttribute("privacy"), "Y"));
			}catch(Exception e){
			}
		}
		return false;
	}

	/**
	 * 개인정보 설정을 저장한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public static void setPrivacy(String privacy) throws Exception {
		ServletContext context = getServletContext();
		if (context != null){
			context.setAttribute("privacy", privacy);
		}
	}

	/**
	 * 에러 로그 처리
	 */
	public static void setException(Throwable th) {
		try{
//			ExceptionFrameworkService exceptionFrameworkService = (ExceptionFrameworkService) GlobalContext.getBean("exceptionFrameworkService");
//			if (exceptionFrameworkService != null)
//				exceptionFrameworkService.insertErrorLog(th);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 에러 로그 처리
	 */
	public static void setException(String msg) {
		setException(new DnpException(msg));
	}

	/**
	 * 에러 로그 처리
	 */
	public static void setException(ErrorCode errorCode, String msg) {
		try{
//			ExceptionFrameworkService exceptionFrameworkService = (ExceptionFrameworkService) GlobalContext.getBean("exceptionFrameworkService");
//			if (exceptionFrameworkService != null)
//				exceptionFrameworkService.insertErrorLog(errorCode, msg);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static SessionMap getsessionMapHandler() {
		if (sessionMapHandler == null) {
			sessionMapHandler = (SessionMap)getBean(SessionMap.class);
		}

		return sessionMapHandler;
	}

}
