/**
 * 
 */
package com.api.address.lookup;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.api.SupportFeatureStep;
import com.api.automation.http.RequestWrapper;
import com.api.automation.http.ResponseWrapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@ContextConfiguration(value = "classpath*:cucumber.xml")
public class AddressLookupFeaturesStep extends SupportFeatureStep {
	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	private ResponseWrapper responseWrapper;

	@Given("^I have a postcode \"([^\"]*)\"$")
	public void i_have_a_postcode(String postcode) throws Throwable {

		String endPoint = requestWrapper.getEndPoint();
		System.out.println("end point registered" + endPoint);
		endPoint = new StringBuilder(endPoint).append(postcode).toString();
		requestWrapper.setEndPoint(endPoint);

	}

	@Then("^I should see the address details with valid postcode$")
	public void i_should_see_the_address_details_with_valid_postcode() throws Throwable {
		String statusMessage = responseWrapper.getResponseBody();
		assertNotNull(statusMessage);

	}

}
