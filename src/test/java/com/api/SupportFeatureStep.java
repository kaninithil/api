package com.api;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.api.automation.util.FileUtil;
import com.api.automation.util.JsonManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SupportFeatureStep {

	public static final String FILE_EXTENSION = ".json";

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

	/**
	 * @param productName
	 * @param mnemonic
	 * @return
	 */
	protected Map<String, String> getKeysToBeUpdated(String productName, String mnemonic) {
		Map<String, String> placeHolderMap = new HashMap<String, String>();
		placeHolderMap.put("$.primaryInvolvedParty.firstName", RandomStringUtils.randomAlphabetic(5));
		placeHolderMap.put("$.primaryInvolvedParty.lastName", RandomStringUtils.randomAlphabetic(5));
		placeHolderMap.put("$.productArrangement.name", productName);
		placeHolderMap.put("$.productArrangement.mnemonic", mnemonic);
		return placeHolderMap;
	}

	
	/**
	   * @param productName
	   * @param mnemonic
	   * @return
	   */
	  protected Map<String, String> getKeysToBeUpdated(String productName, String mnemonic,
	      Map<String, String> additionalKeyMap) {
	    Map<String, String> placeHolderMap = new HashMap<String, String>();
	    placeHolderMap.put("$.primaryInvolvedParty.firstName", RandomStringUtils.randomAlphabetic(5));
	    placeHolderMap.put("$.primaryInvolvedParty.lastName", RandomStringUtils.randomAlphabetic(5));
	    placeHolderMap.put("$.productArrangement.name", productName);
	    placeHolderMap.put("$.productArrangement.mnemonic", mnemonic);
	    if (!additionalKeyMap.isEmpty()) {
	      placeHolderMap.putAll(additionalKeyMap);
	    }
	    return placeHolderMap;
	  }


	/**
	 * @param jsonBody
	 * @return
	 * @throws JsonSyntaxException
	 */
	@SuppressWarnings("unchecked")
	protected String getUpdatedJSON(String jsonBody, Map<String, String> placeHolderMap) throws JsonSyntaxException {
		if (StringUtils.isNotEmpty(jsonBody)) {
			// update the firstname and last name of the jsonbody to avoid
			// duplicate requests
			Gson gSon = new Gson();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap = gSon.fromJson(jsonBody, jsonMap.getClass());
			JsonManager jsonManager = new JsonManager(jsonMap);
			for (String placeHolderKey : placeHolderMap.keySet()) {
				jsonManager.update(placeHolderKey, placeHolderMap.get(placeHolderKey));

			}
			Map<String, Object> formattedJsonMap = jsonManager.map();
			jsonBody = gSon.toJson(formattedJsonMap);
		}
		return jsonBody;
	}
}
