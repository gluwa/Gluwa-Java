@gluwaSdkNeg
Feature: Negative Unit Tests for Gluwa SDK

  @gluwaSdkNeg1
  Scenario Outline: Get List of Transactions negative test
    When I get list of transactions using request parameters <limit> <offset> <Status> <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency |  Status   | Code | limit | offset |           Message            |
      |   GCRE   | Confirmed | 400  | 0     | 0      | Unsupported currency GCRE.    |
      |   DOGE   | Confirmed | 400  | 0     | 0      | The value 'DOGE' is not valid. |
      |   NGNG   | Confirmed | 400  | -1    | 0      | The value '-1' is not valid. |
      |   NGNG   | Confirmed | 400  | 0     | -1     | The value '-1' is not valid. |