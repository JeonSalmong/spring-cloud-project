package com.example.userservice.core.property;

import org.apache.commons.collections.ExtendedProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.ResourceLoaderAware;

public interface Property extends ResourceLoaderAware {
    
	/**
	 * encryptor 설정을 지정
	 */
	void setEncryptor(StandardPBEStringEncryptor encryptor);
	
	/**
     * Property 설정을 읽어 드린다.
     */
    void load() throws Exception;

    /**
     * Property 변경 사항을 확인하여 재 설정 한다.
     */
    void replace() throws Exception;

    /**
     * Property 설정 정보를 가져온다
     * @return
     */
    ExtendedProperties getPropertys();
    
    /**
     * Property의 size를 반환한다.
     * @return 
     */
    int size();

    /**
     * Property 설정을 등록한다.
     * @param key
     * @param value
     */
    void setProperty(String key, Object value);

    /**
     * Property의 특정 Key의 값을 가져온다.
     * @param key
     * @return
     */
    Object getProperty(String key);

    enum PropertyType {
        FILE,
        DB,
        PROPERTY
    }
}
