/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.userservice.core.property;

import com.example.userservice.core.util.StringSupporter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ExtendedProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import java.util.*;

/**
 * Property 서비스의 구현 클래스
 * <p>
 * <b>NOTE</b>: 이 서비스를 통해 어플리케이션에서 유일한 키값으로 키/값쌍을 가지고 오도록 서비스 한다.
 * </p>
 *
 * @since      2022. 5. 11.
 * @version    1.0
 * @author     안영록
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2022. 5. 11.  안영록           최초 생성
 * </pre></dd>
 */
@Slf4j
public class PropertyLoader implements InitializingBean, DisposableBean, ResourceLoaderAware, BeanNameAware {
	private StandardPBEStringEncryptor encryptor;

	private ExtendedProperties propertiesMap = null;
	private ResourceLoader resourceLoader = null;

	private List<?> propertyList;
	private String beanName;

	public PropertyLoader() {
		try {
			if (this.encryptor == null) {
				this.encryptor = new StandardPBEStringEncryptor();

				org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig pbeConfig = new org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig();
				pbeConfig.setAlgorithm("PBEWithMD5AndDES");
				byte[] bPW = new byte[] { 100, 110, 112, 102, 114, 97, 109, 101, 119, 111, 114, 107 }; //dnpframework
				char[] cPW = new String(bPW).toCharArray();
				pbeConfig.setPasswordCharArray(cPW);
				this.encryptor.setConfig(pbeConfig);
			}
		} catch(Exception e){
			this.encryptor = null;
			log.error("프로퍼티 내용 암복호화 Class 선언 오류");;
		}
	}

	@Override
	public void setBeanName(String name){
		this.beanName = name;
	}
	public String getBeanName(){
		return this.beanName;
	}

	/**
	 * Bean 초기화 함수로 최초 생성시 필요한 Property 세티처리
	 *
	 * @throws Exception fail to initialize
	 */
	@Override
	@SuppressWarnings({"rawtypes", "unchecked" })
	public void afterPropertiesSet() throws Exception {
		try {
			refreshProperty();
		} catch (Exception e) {
			log.error("등록 서비스를 초기화하는 데 실패합니다.");
			throw new Exception("등록 서비스를 초기화하는 데 실패합니다.", e);
		}
	}

	/**
	 * encryptor 설정을 지정
	 */
	public void setEncryptor(StandardPBEStringEncryptor encryptor) {
		this.encryptor = encryptor;
	}

	/**
	 * propertyList을 지정할 때 Attribute로 정의
	 *
	 * @param propertyList
	 */
	public void setPropertyList(List<?> propertyList) {
		this.propertyList = propertyList;
	}

	/**
	 * 서비스 종료처리
	 */
	@Override
	public void destroy() {
		propertiesMap = null;
		PropertyMap.destoryPropertyMap(getBeanName());
	}

	/**
	 * 리소스 로더 세팅
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * resource 변경시 refresh
	 */
	public void refreshProperty() throws Exception {

		ExtendedProperties oldPropsMap = PropertyMap.getPropertyMap(getBeanName());
		try {
			this.propertiesMap = new ExtendedProperties();
			Iterator<?> it = propertyList.iterator();
			while (it != null && it.hasNext()) {
				// Get element
				Object element = it.next();
				String enc = null;
				if (element instanceof Properties) {
					Properties props = (Properties) element;
					Enumeration enuKeys = props.keys();
					while (enuKeys.hasMoreElements()) {
						String key = (String) enuKeys.nextElement();
						this.propertiesMap.setProperty(key, props.get(key));
						String value = props.getProperty(key);

						log.debug("프로퍼티 key = "+key+", 값 = "+value+" 은 이 설정파일에 정의되어 있습니다.");

						if (key == null || key.equals("")) {
							log.error("key의 <element> 필수적인 속성입니다.");
							throw new Exception("key의 <element> 필수적인 속성입니다.", null);
						}

						if (encryptor != null && PropertyValueEncryptionUtils.isEncryptedValue(value)){
							value = PropertyValueEncryptionUtils.decrypt(value, encryptor);
							this.propertiesMap.setProperty(key, value);
						}
					}
				} else if (element instanceof Property) {
					Property property = (Property) element;
					property.setResourceLoader(this.resourceLoader);
					if (property.size() == 0)
						property.load();
					else {
						property.replace();
					}
					this.propertiesMap = property.getPropertys();
				} else {
					FileProperty fileProperty = new FileProperty(this.encryptor);
					fileProperty.setResourceLoader(this.resourceLoader);

					Set<Map> fileSet = new HashSet<>();
					Map<String, String> fileMap = new HashMap<String, String>();
					if (element instanceof Map) {
						Map<?, ?> ele = (Map<?, ?>) element;
						fileMap.put("encoding", StringSupporter.stringValueOf(ele.get("encoding")));
						fileMap.put("filename", StringSupporter.stringValueOf(ele.get("filename")));
					} else {
						fileMap.put("filename", StringSupporter.stringValueOf(element));
					}

					fileSet.add(fileMap);
					fileProperty.setExtFileName(fileSet);
					fileProperty.load();
					this.propertiesMap = fileProperty.getPropertys();
				}
			}

			PropertyMap.setPropertyMap(getBeanName(), this.propertiesMap);
		} catch (Exception e) {

			log.error(getBeanName() + "의 일부 프로퍼티 정의에 문제가 있습니다.");
			PropertyMap.setPropertyMap(getBeanName(), oldPropsMap);
			throw new Exception("error.properties.refresh.files", e);
		}
	}
}
