package com.example.userservice.core.property;

import com.example.userservice.core.util.StringSupporter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ExtendedProperties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class FileProperty implements Property, BeanNameAware {
    protected static StandardPBEStringEncryptor encryptor;

	private ExtendedProperties propertiesMap = new ExtendedProperties();
	private ResourceLoader resourceLoader = null;
	
    private Set<?> extFileName;
    private String beanName;

    public FileProperty() {
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

    public FileProperty(StandardPBEStringEncryptor encryptor) {
    	this.encryptor = encryptor;
    }
	
	/**
	 * encryptor 설정을 지정
	 */
    @Override
	public void setEncryptor(StandardPBEStringEncryptor encryptor) {
		this.encryptor = encryptor;
	}

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
    /**
     * extFileName을 지정할 때 Attribute로 정의
     *
     * @param extFileName
     */
    public void setExtFileName(Set<?> extFileName) {
        this.extFileName = extFileName;
    }

    @Override
    public void load() throws Exception {
    	try {
    		propertiesMap = new ExtendedProperties();
    		Iterator<?> it = extFileName.iterator();

    		while (it != null && it.hasNext()) {
    			// Get element
    			Object element = it.next();
    			String enc = null;
    			String fileName = null;
    			
    			if (element instanceof Map) {
    				Map<?, ?> ele = (Map<?, ?>) element;
    				enc = StringSupporter.stringValueOf(ele.get("encoding"));
    				if ("".equals(enc))
    					enc = null;
    				fileName = StringSupporter.stringValueOf(ele.get("filename"));
    			} else {
    				fileName = StringSupporter.stringValueOf(element);
    			}
    			loadPropertyResources(fileName, enc);
    		}
    	} catch (Exception e) {
			throw e;
		}
    	
    }

    @Override
    public void replace() throws Exception {
    	this.load();
    }
    
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.propertiesMap.size();
	}

    @Override
    public ExtendedProperties getPropertys() {
        return this.propertiesMap;
    }

    @Override
    public void setProperty(String key, Object value) {
    	this.propertiesMap.put(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return this.propertiesMap.get(key);
    }
    
    /**
	 * 파일위치정보를 가지고 resources 정보 추출
	 *
	 * @param location 파일위치
	 * @param encoding Encoding 정보
	 * @throws Exception
	 */
	private void loadPropertyResources(String location, String encoding) throws Exception {

		if (resourceLoader instanceof ResourcePatternResolver) {
			try {
				org.springframework.core.io.Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);

				if (resources.length == 0)
					log.error(" file not found : " + location);

				loadPropertyLoop(resources, encoding);
			} catch (IOException ex) {
				throw new BeanDefinitionStoreException("Could not resolve Properties resource pattern [" + location + "]", ex);
			}
		} else {

			org.springframework.core.io.Resource resource = resourceLoader.getResource(location);

			log.error("file not found : " + resource.getURL().getPath());
			loadPropertyRes(resource, encoding);
		}
	}

	/**
	 * 멀티로 지정된 경우 처리를 위해 LOOP 처리
	 *
	 * @param resources 리소스정보
	 * @param encoding 인코딩정보
	 * @throws Exception
	 */
	private void loadPropertyLoop(org.springframework.core.io.Resource[] resources, String encoding) throws Exception {
		Assert.notNull(resources, "Resource array must not be null");
		for (int i = 0; i < resources.length; i++) {
			loadPropertyRes(resources[i], encoding);
		}
	}

	/**
	 * 파일 정보를 읽어서 egovProperties에 저장
	 *
	 * @param resource 리소스정보
	 * @param encoding 인코딩정보
	 * @throws Exception
	 */
	private void loadPropertyRes(org.springframework.core.io.Resource resource, String encoding) throws Exception {
		log.debug("프로퍼티 파일이름은 ["+resource.getFilename()+"]입니다. 파일 인코딩 타입은 "+encoding+"입니다.");

		try {
			if (resource.exists()) {
				ExtendedProperties prop = new ExtendedProperties();
				prop.load(resource.getInputStream(), encoding);

				if (encryptor != null){
					Iterator iterator = prop.getKeys();
					if (iterator != null) {
						while (iterator.hasNext()) {
							String name = (String)iterator.next();
							propertiesMap.setProperty(name, prop.get(name));
							String value = prop.getString(name);
							if (PropertyValueEncryptionUtils.isEncryptedValue(value)) {
								value = PropertyValueEncryptionUtils.decrypt(value, encryptor);
								propertiesMap.setProperty(name, value);
							}
						}
					}
				}
				prop.clear();

				if (!ResourceUtils.isJarURL(resource.getURL())) {
					File propertyFile = resource.getFile();
					propertiesMap.setProperty("lastModified", propertyFile.lastModified());
					propertiesMap.setProperty("propertyFilePath", propertyFile.getAbsolutePath());
				} else {
					propertiesMap.setProperty("isJar", "true");
				}
				
				String pId = propertiesMap.getString("WHOIAM");
				String pFn = resource.getFilename();
				log.debug("프로퍼티 Whoami : " + pId + " / 파일명 : " + pFn + " / 인코딩 : " + encoding);
			} else {
				log.error(" file not found : " + resource.getURL().getPath());
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
