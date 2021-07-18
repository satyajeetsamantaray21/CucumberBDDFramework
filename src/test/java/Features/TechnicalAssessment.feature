Feature: This Feature Contains scenarios for the testcases in Technical Assignment

  Background:
    Given I navigate to home page

    ################################TestCase1#########################################
  @Test
  Scenario Outline: Go to contacts page and validate the errors when no data is entered

    When I click on contact link
    And I verify I am at "Contact" Page
    And I click on submit link
    And I validate Error Message for fields
      | Field    | ErrorMessage         |
      | Forename | Forename is required |
      | Email    | Email is required    |
      | Message  | Message is required  |
    When I populate the form with below Data
      | Forename   | Surname   | Email   | Telephone   | Message   |
      | <Forename> | <Surname> | <Email> | <Telephone> | <Message> |


    Examples:
      | Forename   | Surname      | Email       | Telephone  | Message |
      | satyajeet | Samantaray    | abc@abc.com | 0466499499 | message |


  ################################TestCase2#########################################
  @Test
  Scenario Outline: Go to Contacts page and validate successful submission message

    When I click on contact link
    And I verify I am at "Contact" Page
    When I populate the form with below Data
      | Forename   | Surname   | Email   | Telephone   | Message   |
      | <Forename> | <Surname> | <Email> | <Telephone> | <Message> |
    And I click on submit link
    Then I validate the form is submitted successfully

    Examples:
      | Forename  | Surname       | Email       | Telephone  | Message |
      | satyajeet | Samantaray    | abc@abc.com | 0466499499 | message |


  ################################TestCase3#########################################
  @Test
  Scenario Outline: Go to contacts page and validate errors for invalid data

    When I click on contact link
    And I verify I am at "Contact" Page
    When  I populate the form with below Data
      | Forename   | Surname   | Email   | Telephone   | Message   |
      | <Forename> | <Surname> | <Email> | <Telephone> | <Message> |
    And I validate the error message for invalid email id
    Examples:
      | Forename | Surname | Email | Telephone  | Message |
      | satyajeet | Samantaray    | com | 0466499499 | message |


    ################################TestCase4#########################################
  @Test
  Scenario: Go to shop page and verify the items in the cart

    When I click on shop link
    And I verify I am at "Shop" Page
    When I click on a item for "Funny Cow"
    When I click on a item for "Funny Cow"
    When I click on a item for "Fluffy Bunny"
    Then I click the cart
    Then I verify the items in the cart
      | ItemName  | Count |
      | Funny Cow    |     2 |
      | Fluffy Bunny |     1 |
    Then I verify the items in the cart
       | ItemName     | Count |
       | Funny Cow    |     2 |
       | Fluffy Bunny |     1 |




