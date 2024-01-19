package com.example.userservice.core.exception;

import java.text.MessageFormat;

/**
 * 예외처리 확장을 위해 Exception 클래스를 상속받은 클래스
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
public class DnpException extends Exception {

	private static final long serialVersionUID = 1L;

	protected String message = null;
	protected String messageKey = null;
	protected Object[] messageParameters = null;
	protected Throwable wrappedException = null;
	private int statusCode;
	private boolean writeStackTrace = true;

    @Override
	public String getMessage() {
        return ErrorCode.ERR_APP_STR + " " + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public void setStatusCode(int sc) {
    	this.statusCode = sc;
    }

    public int getStatusCode() {
    	return statusCode;
    }
    
    public void setWriteStackTrace(boolean write) {
        this.writeStackTrace = write;
    }
    
    public boolean isWriteStackTrace() {
        return this.writeStackTrace;
    }    

    public Object[] getMessageParameters() {
    	Object[] ObmessageParameters = this.messageParameters;
        return ObmessageParameters;
    }

    public void setMessageParameters(Object[] messageParameters) {
        this.messageParameters = messageParameters;
    }

    public Throwable getWrappedException() {
        return wrappedException;
    }

    public void setWrappedException(Throwable wrappedException) {
        this.wrappedException = wrappedException;
    }
    
    public DnpException(int sc, String msg) {
        this.statusCode = sc;
        this.message = msg;
    }

    /**
     * 새로운 예외를 생성한다.<br>
     * 상세 메시지는 기본 메시지("DnpException without message")를 사용하고, 오류 원인은 초기화 되지 않는다.
     */
    public DnpException() {
        this("DnpException without message", null, null);
    }

    /**
     * 지정된 메시지를 가지는 새로운 예외를 생성한다.<br>
     * 오류 원인은 초기화 되지 않는다.
     *
     * @param defaultMessage 메시지
     */
    public DnpException(String defaultMessage) {
        this(defaultMessage, null, null);
    }

    /**
     * 지정된 상세 메시지와 원인을 사용해 새로운 예외를 생성한다.
     *
     * @param defaultMessage 메시지
     * @param wrappedException 오류 원인
     */
    public DnpException(String defaultMessage, Throwable wrappedException) {
        this(defaultMessage, null, wrappedException);
    }

    /**
     * 지정된 상세 메시지와 원인을 사용해 새로운 예외를 생성한다.<br>
     *
     * @param defaultMessage 메시지
     * @param messageParameters 메시지 파라미터
     * @param wrappedException 오류 원인
     */
    public DnpException(String defaultMessage, Object[] messageParameters, Throwable wrappedException) {
    	super(defaultMessage);
    	String userMessage = defaultMessage;
        if (messageParameters != null) {
            userMessage = MessageFormat.format(defaultMessage, messageParameters);
        }
        this.message = userMessage;
        this.wrappedException = wrappedException;
    }

}
