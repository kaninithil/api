/**
 * 
 */
package com.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.api.services.util.FileUtil;

/**
 * Loads the property file app.properties from the resource folder
 */

public class BaseEnvSetUp {
	private static final String APP_PROPERTIES = "app.properties";
	private Properties appProperties;
	public static final String FILE_EXTENSION = ".json";

	/**
	 * Loads property file and stores it as a java.util.Property instance
	 * 
	 * @throws IOException
	 */
	public void loadAppProperties() throws IOException {

		InputStream in = null;
		try {
			in = FileUtil.loadFile(APP_PROPERTIES);
			appProperties = new Properties();
			appProperties.load(in);
		} finally {
			if (null != in) {
				in.close();
			}

		}

	}

	/**
	 * 
	 * @return returns application property store
	 * @throws IOException
	 */
	public Properties getAppProperties() throws IOException {
		if (null == appProperties) {
			loadAppProperties();
		}
		return this.appProperties;
	}

	public String getJsonRequest(String dataType) throws IOException {
		System.out.println("dataType:" + dataType);
		InputStream ins = null;
		try {
			ins = FileUtil
					.loadFile("jsonRequestFiles" + System.getProperty("file.separator") + dataType + FILE_EXTENSION);
			if (null != ins) {
				StringWriter writer = new StringWriter();
				IOUtils.copy(ins, writer, "UTF-8");
				String jsonBody = writer.toString();
				// System.out.println("jsonBody:" + jsonBody);
				assertNotNull(jsonBody);
				return jsonBody;
			}
		} finally {
			if (null != ins) {
				ins.close();
			}

		}
		return null;
	}

}
