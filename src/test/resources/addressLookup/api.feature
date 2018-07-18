@ApiTesting 
Feature: post code API Validation

  ########################
  ####1
  ########################
  Background: 
    Given I have  service available for feature "addressLookup" 

  Scenario Outline: Valid post code with http response
    Given I have a postcode "<postcode>"
    When I try to get response from the API for the request
    Then I should see the address details with valid postcode
    And I should see http statuscode as "<httpStatusCode>"

    Examples: 
      | postcode | httpStatusCode |
      | WC2H8AD  | 200            |

  Scenario Outline: Valid post code with  http response
    Given I have a postcode "<postcode>"
    When I try to get response from the API for the request
    Then I should see the address details with valid postcode
    And I should see http statuscode as "<httpStatusCode>"

    Examples: 
      | postcode | httpStatusCode |
      | BR13FG   | 200            |

  
