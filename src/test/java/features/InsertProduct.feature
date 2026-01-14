Feature: Insert products using post api

  Scenario Outline: Validate post product api status code work correctly
    Given I hit the url of post products api endpoint
    When I pass the url of products in the request
    And I pass the request body of product title <ProductTitle>
    Then I receive the response code as 200
    Examples:
      | ProductTitle |
      | Shoes        |

  Scenario Outline: Validate post product api response body work correctly
    Given I hit the url of post products api endpoint
    When I pass the url of products in the request
    And I pass the request body of product title <ProductTitle>
    Then I receive the response body with id as <id>
    Examples:
      | ProductTitle | id |
      | Shoes        | 21 |