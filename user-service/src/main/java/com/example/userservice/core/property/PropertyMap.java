package com.example.userservice.core.property;

import org.apache.commons.collections.ExtendedProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * context-properties.xml 에 설정된 프로퍼티 정보를 모두 담고있는 static 객체
 * 
 * @since      2018. 1. 17.
 * @version    1.0
 * @author     H2017288
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2018. 1. 17.   H2017288        최초 생성
 * </pre></dd>
 */
public class PropertyMap{

	private static Map<String, ExtendedProperties> propertyMap = new HashMap<String, ExtendedProperties>();
	
	/**
	 * propertyMap에 프로퍼티 정보를 설정한다.
	 * <br>
	 * PropertyLoader에서만 사용한다.
	 *
	 * @param key context-properties.xml에 설정한 프로퍼티 파일들의 bean name
	 * @param extProp 프로퍼티 정보를 담고있는 객체
	 */
	public static void setPropertyMap(String key, ExtendedProperties extProp){
		propertyMap.put(key, extProp);
	}
	
	/**
	 * 특정 프로퍼티 객체를 반환한다.
	 *
	 * @param key 가져올 context-properties.xml에 설정한 프로퍼티 파일의 bean name
	 * @return 프로퍼티 객체
	 */
	public static ExtendedProperties getPropertyMap(String key){
		return propertyMap.get(key);
	}
	
	/**
	 * propertyMap에 설정된 모든 프로퍼티 정보를 Map 형태로 반환한다.
	 *
	 * @return 모든 프로퍼티 객체
	 */
	public static Map<String, ExtendedProperties> getAllPropertyMap(){
		return propertyMap;
	}
	
	/**
	 * propertyMap에 설정된 모든 프로퍼티 Map의 Key를 반환한다.
	 *
	 * @return
	 */
	public static Set<String> getPropertyKeySet(){
		return propertyMap.keySet();
	}
	
	/**
	 * propertyMap에 설정된 모든 key값, value값을 반환한다.
	 *
	 * @return
	 */
	public static Set<Entry<String, ExtendedProperties>> getPropertyEntrySet(){
		return propertyMap.entrySet();
	}
	
	/**
	 * PropertyLoader Bean destory() 시점에 propertyMap도 해제
	 *
	 */
	public static void destory(){
		propertyMap = null;
	}
	
	/**
	 * PropertyLoader Bean destory() bean name별로 propertyMap 삭제
	 *
	 * @param propertyBeanName
	 */
	public static void destoryPropertyMap(String propertyBeanName){
		propertyMap.remove(propertyBeanName);
		if(propertyMap.isEmpty()) destory();
	}
}
