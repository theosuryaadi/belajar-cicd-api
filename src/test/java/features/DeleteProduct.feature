Feature: Delete products using delete api

  Scenario Outline: Validate delete product api status code work correctly
    Given I hit the url of delete products api endpoint
    When I pass the url of delete products in the request with <ProductNumber>
    Then I receive the response code as 200
    Examples:
      | ProductNumber |
      | 9             |