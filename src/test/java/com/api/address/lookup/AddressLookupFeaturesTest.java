package com.api.address.lookup;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, features = "src/test/resources/addressLookup", glue = { "com.lbg.ib.api.automation",
		"com.lbg.ib.api.address.lookup" }, format = { "pretty",
				"html:target/site/cucumber-pretty",
				"json:target/reports/cucumber-AddressLookupFeaturesTest.json" }, tags = { "~@ignore" })
public class AddressLookupFeaturesTest {

}
