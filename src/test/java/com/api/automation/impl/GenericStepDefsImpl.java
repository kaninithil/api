package com.api.automation.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.automation.EnvironmentConfig;
import com.api.automation.GenericStepDefs;
import com.api.automation.http.APIRequestManager;
import com.api.automation.http.RequestWrapper;
import com.api.automation.http.ResponseWrapper;
import com.api.automation.util.JsonUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class GenericStepDefsImpl implements GenericStepDefs {

	@Autowired
	private EnvironmentConfig environmentConfig;

	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	private ResponseWrapper responseWrapper;

	@Autowired
	private APIRequestManager httpRequestManager;

	@Override
	@Given("^I have  service available for feature \"([^\"]*)\" and brand \"([^\"]*)\"$")
	public void givenIhaveServiceAvailableForFeatureAndBrand(String feature, String brand) {
		String currentEnvironment = environmentConfig.getCurrentEnvironment();
		if (StringUtils.isEmpty(currentEnvironment)) {
			throw new RuntimeException("Environment not defined");
		}
		String host = null;
		System.out.println("brand information:" + brand);
		host = environmentConfig.getHostInformation(brand);
		System.out.println("host information:" + host);
		String featureEndPoint = environmentConfig.getProperty(feature);
		System.out.println("feature end point:" + featureEndPoint);
		String urlEndPoint = new StringBuilder(host).append(featureEndPoint).toString();
		System.out.println("serviceEndPoint:" + urlEndPoint);
		assertTrue(StringUtils.isNotEmpty(urlEndPoint));
		requestWrapper.setEndPoint(urlEndPoint);

	}

	@Override
	@When("^I try to get response from the API for the request$")
	public void whenITryToInvokeTheService() {

		httpRequestManager.callAPI();
	}

	@Override
	@Then("^I should see http statuscode as \"([^\"]*)\"$")
	public void thenIshouldGetResponseFromTheService(String responseCode) {
		assertTrue(responseCode.equalsIgnoreCase(responseWrapper.getResponseCode()));
	}

	@Then("^I should see response with response code \"([^\"]*)\"$")
	public void iShouldSeeResponseWithResponseCode(String responseCode) throws Throwable {
		String messageBody = responseWrapper.getResponseBody();
		String responseCodeActual = JsonUtil.parseJson(messageBody, "code").replaceAll("\"", "");
		assertTrue(responseCodeActual.equalsIgnoreCase(responseCode));

	}

	@Then("^I should see message \"([^\"]*)\"$")
	public void iShouldSeeMessage(String message) throws Throwable {
		String messageBody = responseWrapper.getResponseBody();
		String messageActual = JsonUtil.parseJson(messageBody, "message");
		if (null != messageActual) {
			messageActual = messageActual.replaceAll("\"", "");
			assertTrue(messageActual.equalsIgnoreCase(message));
		} else {
			assertTrue(String.valueOf(messageActual).equalsIgnoreCase(message));
		}
	}

	@When("^I send a POST request with with data \"([^\"]*)\"$")
	public void i_send_a_POST_request_with_with_data(String datatype) throws Throwable {
		String jsonBody = environmentConfig.getProperty(datatype);
		requestWrapper.setRequestBody(jsonBody);
		httpRequestManager.callAPI();

	}

	public RequestWrapper getRequestWrapper() {
		return requestWrapper;
	}

	public ResponseWrapper getResponseWrapper() {
		return responseWrapper;
	}

}
