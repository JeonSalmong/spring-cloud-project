package com.example.userservice.core.property;


import com.example.userservice.core.context.GlobalContext;
import com.example.userservice.core.util.StringSupporter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ExtendedProperties;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * 프로퍼티 사용시 서버 구동시 로딩된 static 객체에서 모든 프로퍼티 정보에 접근할 수 있도록함
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
@Slf4j
public abstract class PropertyHelper {
	/**
	 * boolean 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name  프로퍼티키
	 * @return boolean 타입의 값
	 */
	public static boolean getBoolean(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getBoolean(name);
	}

	/**
	 * boolean 타입의 프로퍼티 값 얻기(디폴트값을 입력받음)
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return boolean 타입의 값
	 */
	public static boolean getBoolean(String propertyBeanName, String name, boolean def){
		return getConfiguration(propertyBeanName).getBoolean(name, def);
	}

	/**
	 * double 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return double 타입의 값
	 */
	public static double getDouble(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getDouble(name);
	}

	/**
	 * double 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return double 타입의 값
	 */
	public static double getDouble(String propertyBeanName, String name, double def){
		return getConfiguration(propertyBeanName).getDouble(name, def);
	}

	/**
	 * float 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return Float 타입의 값
	 */
	public static float getFloat(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getFloat(name);
	}

	/**
	 * float 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return float 타입의 값
	 */
	public static float getFloat(String propertyBeanName, String name, float def){
		return getConfiguration(propertyBeanName).getFloat(name, def);
	}

	/**
	 * int 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return int 타입의 값
	 */
	public static int getInt(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getInt(name);
	}

	/**
	 * int 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return int 타입의 값
	 */
	public static int getInt(String propertyBeanName, String name, int def){
		return getConfiguration(propertyBeanName).getInt(name, def);
	}

	/**
	 * 프로퍼티 키 목록 읽기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @return Key를 위한 Iterator
	 */
	public static Iterator<?> getKeys(String propertyBeanName){
		return getConfiguration(propertyBeanName).getKeys();
	}

	/**
	 * prefix를 이용한 키 목록 읽기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param prefix prefix
	 * @return prefix에 매칭되는 키목록
	 */
	public static Iterator<?> getKeys(String propertyBeanName, String prefix){
		return getConfiguration(propertyBeanName).getKeys(prefix);
	}

	/**
	 * long 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return long 타입의 값
	 */
	public static long getLong(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getLong(name);
	}

	/**
	 * long 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return long 타입의 값
	 */
	public static long getLong(String propertyBeanName, String name, long def){
		return getConfiguration(propertyBeanName).getLong(name, def);
	}

	/**
	 * String 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return String 타입의 값
	 */
	public static String getString(String propertyBeanName, String name){
		return StringSupporter.UnicodeConvert(getConfiguration(propertyBeanName).getString(name));
	}

	/**
	 * String 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return String 타입의 값
	 */
	public static String getString(String propertyBeanName, String name, String def){
		return StringSupporter.UnicodeConvert(getConfiguration(propertyBeanName).getString(name, def));
	}

	/**
	 * String[] 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return String[] 타입의 값
	 */
	public static String[] getStringArray(String propertyBeanName, String name){
		String[] values = getConfiguration(propertyBeanName).getStringArray(name);

		if (values != null && values.length > 0){
			int size = values.length;
			for (int i=0; i<size; i++)
			{
				values[i] = StringSupporter.UnicodeConvert(values[i]);
			}
		}
		return values;
	}

	/**
	 * Vector 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @return Vector 타입의 값
	 */
	public static Vector<?> getVector(String propertyBeanName, String name){
		return getConfiguration(propertyBeanName).getVector(name);
	}

	/**
	 * Vector 타입의 프로퍼티 값 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @param name 프로퍼티키
	 * @param def 디폴트 값
	 * @return Vector 타입의 값
	 */
	public static Vector<?> getVector(String propertyBeanName, String name, Vector<?> def){
		return getConfiguration(propertyBeanName).getVector(name, def);
	}

	/**
	 * 전체 키/값 쌍 얻기
	 *
	 * @param propertyBeanName 설정한 프로퍼티 파일의 bean name
	 * @return Vector 타입의 값
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Collection<String> getAllKeyValue(String propertyBeanName){
		return getConfiguration(propertyBeanName).values();
	}

	/**
	 * 설정한 프로퍼티 파일의 bean id로 해당 프로퍼티 객체를 반환한다.
	 *
	 * @param propertyBeanName 찾을 프로퍼티 파일의 bean id
	 * @return 프로퍼티 객체
	 */
	private static ExtendedProperties getConfiguration(String propertyBeanName){
		ExtendedProperties extendedproperties = PropertyMap.getPropertyMap(propertyBeanName);

		if (extendedproperties == null)
			extendedproperties = new ExtendedProperties();


		return extendedproperties;
	}

	/**
	 * 지정 프로퍼티 refresh
	 *
	 */
	public static void refreshProperty(String propertyBeanName) {
		refreshPropertyLoader((PropertyLoader) GlobalContext.getBean(propertyBeanName));
	}


	/**
	 * 프로퍼티 전체 refresh (WAS재기동 없이 프로퍼티 변경 가능하기 위해 사용)
	 *
	 */
	public static void refreshProperty() {
		Map<String, PropertyLoader> propertyLoaders = (Map<String, PropertyLoader>) GlobalContext.getBeansOfType(PropertyLoader.class);

		if (propertyLoaders != null) {
			for(String propertyKey: propertyLoaders.keySet()){
				refreshPropertyLoader( propertyLoaders.get(propertyKey));
			}
		}
	}

	/**
	 * 프로퍼티 파일내용 재 설정
	 *
	 */
	private static void refreshPropertyLoader(PropertyLoader propertyLoader) {
		if (propertyLoader != null) {
			try{
				propertyLoader.refreshProperty();
			}catch(Exception e){
				log.debug("Exception occur while refreshing property files..");
			}
		}
	}
}

