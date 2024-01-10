package com.example.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.LiteDeviceResolver;


import java.util.Objects;

public class Device {
	private Client client = null;
	private DeviceType type = null;
	private DevicePlatform platform = null;
	private Browser browser = null;

	private Device() {
	}

	private Device(Client client, DeviceType type, DevicePlatform platform, Browser browser) {
		this.client = client;
		this.type = type;
		this.platform = platform;
		this.browser = browser;
	}

	public Client getClient() {
		return this.client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

	public DeviceType getDeviceType() {
		return this.type;
	}
	public void setDeviceType(DeviceType type) {
		this.type = type;
	}

	public DevicePlatform getDevicePlatform() {
		return platform;
	}
	public void setDevicePlatform(DevicePlatform platform) {
		this.platform = platform;
	}
	
	public Browser getBrowser() {
	    return browser;
	}
	public void setBrowser(Browser browser) {
	    this.browser = browser;
	}

	public boolean isMobileDevice() {
		return (DeviceType.TABLET.equals(this.type) || DeviceType.PHONE.equals(this.type));
	}

	/*
	 *
	 */
	public static Device getDevice(HttpServletRequest request) {
		String sUserAgent = request.getHeader("User-Agent");
		sUserAgent = Objects.toString(sUserAgent, "").toLowerCase();
		    
//		/*
//		 * iOS 10 이상인 경우, Native 호출시 UserAgent 값이
//		 * <AppName>/<AppVersion> CFNetwork/<CFNetworkVersion> Darwin/<DarwinVersion>
//		 * 형식으로 나옴
//		 */
//		if (sUserAgent.contains("cfnetwork") && sUserAgent.contains("darwin")) {
//			Device device = new Device(Client.NATIVE, DeviceType.PHONE, DevicePlatform.IOS, Browser.UNKNOWN);
//			return device;
//		}
//
//		CustomLiteDeviceResolver resolver = new CustomLiteDeviceResolver();
//		org.springframework.mobile.device.Device sDevice = resolver.resolveDevice(request);


//		DeviceType dType = null;
//
//        if (sDevice.isNormal()) {
//            dType = DeviceType.DESKTOP;
//        } else if (sDevice.isMobile()) {
//			dType = DeviceType.PHONE;
//		} else if (sDevice.isTablet()) {
//			dType = DeviceType.TABLET;
//		} else {
//			dType = DeviceType.UNKNOWN;
//		}

		DeviceType dType = null;

		if (sUserAgent.matches(".*(Android|iPhone|iPad).*")) {
			dType = DeviceType.PHONE;
		} else {
			dType = DeviceType.DESKTOP;
		}

		String sOrigin = request.getHeader("Origin");
		Client dClient = null;
		Browser dBrowser = Browser.UNKNOWN;

		String client = request.getParameter("Client");
		if ("DevTool".equals(client)) {
		    dClient = Client.DEVTOOL;
		    
		} else if ("file://".equals(sOrigin)) {
			// 안드로이드 구버전, iOS UIWebView (file:// 까지만 있고 파일명은 없음. StringSupporter.isLocalFileName() 사용하면 안 됨)
			dClient = Client.HYBRID;

		} else if ("null".equals(sOrigin)) {
			// 개발툴, iOS WkWebView, iOS Native 호출 (to 로컬)
			String sSecFetchMode = request.getHeader("Sec-Fetch-Mode");

			if (sSecFetchMode == null) {
				// iOS WkWebView
				dClient = Client.HYBRID;
			} else {
				dClient = Client.DEVTOOL;
			}
		} else {
            String sReferer = request.getHeader("referer");
            if (sReferer != null && sReferer.startsWith("http")) {
                dClient = Client.BROWSER;
            } else {
                // 안드로이드 최신 버전, iOS Native 호출 (to 서버)
                dClient = Client.HYBRID;
            }
		}
		
		// DMS02H10 app 2023.05.22
		if (sUserAgent.contains("hybrid")) {
		    dClient = Client.HYBRID;
		}
			
        if (sUserAgent != null) {
            if(sUserAgent.indexOf("chrome") > -1) {                                             // Chrome  
                dBrowser = Browser.CHROME;
            } else if(sUserAgent.indexOf("edge") > -1) {                                         // Edge
                dBrowser = Browser.EDGE;
            } else if(sUserAgent.indexOf("trident") > -1) {                                      // IE
                dBrowser = Browser.IE;
            } else if (sUserAgent.indexOf("msie") > -1) {
                dBrowser = Browser.IE;
            } else if(sUserAgent.indexOf("firefox") > -1) {                                       // Firefox
                dBrowser = Browser.FIREFOX;
            } else if(sUserAgent.indexOf("safari") > -1 && sUserAgent.indexOf("chrome") == -1 ) {  // Safari
                dBrowser = Browser.SAFARI;     
            } else if(sUserAgent.indexOf("opera") > -1 || sUserAgent.indexOf("opr") > -1) {       // Opera
                dBrowser = Browser.OPERA;
            } else if(sUserAgent.indexOf("whale") > -1) {                                        // Naver Whale
                dBrowser = Browser.WHALE;
            }
        }

		DevicePlatform dPlatform = null;
		if (sUserAgent.indexOf("windows nt") > -1) {
		    dPlatform = DevicePlatform.WINNDOWS;
        } else if (sUserAgent.indexOf("android") > -1) {
            dPlatform = DevicePlatform.ANDROID;
		} else if (sUserAgent.indexOf("iphone") > -1) {
		    dPlatform = DevicePlatform.IOS;
        } else if (sUserAgent.indexOf("mac") > -1) {
            dPlatform = DevicePlatform.MACOS;
		} else if (sUserAgent.indexOf("ipad") > -1) {
		    dPlatform = DevicePlatform.IOS;
		} else if (sUserAgent.indexOf("linux") > -1) {
		    dPlatform = DevicePlatform.LINUX;
		} else{
		    dPlatform = DevicePlatform.UNKNOWN;
		}            

		Device device = new Device(dClient, dType, dPlatform, dBrowser);

		return device;
	}

	public enum Client {
		HYBRID,
		NATIVE,
		BROWSER,
		DEVTOOL
	}

	public enum DeviceType {
		PHONE,
		TABLET,
		DESKTOP,
		UNKNOWN
	}

	public enum DevicePlatform {
		ANDROID,
		IOS,
		WINNDOWS,
		MACOS,
		LINUX,
		UNKNOWN
	}
	
	public enum Browser {
	    EDGE,
	    CHROME,
	    IE,
	    WHALE,
	    OPERA,
	    FIREFOX,
	    SAFARI,
	    UNKNOWN
	}
}