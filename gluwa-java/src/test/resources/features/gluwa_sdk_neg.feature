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


  @gluwaSdkNeg2
  Scenario Outline: Get fee for currency Negative
    When I get fee for transaction of <Amount> for currency <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency | Code | Amount | Message |
      | DOGE     | 400  | 50     | The value 'DOGE' is not valid. |
      | KRWG     | 503  | 50     | Unsupported currency |
      | NGNG     | 400  | -4     | amount should be a positive number |
      | NGNG     | 400  | aa     | amount should be a positive number |


  @gluwaSdkNeg3
  Scenario Outline: Get Address for invalid currency negative tests
    When I get address via Gluwa SDK with invalid <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      |  Code  |  Currency |              Message           |
      |  400   |   DOGE    | The value 'DOGE' is not valid. |


  @gluwaSdkNeg4
  Scenario Outline: Get payment QR code with Payload negative tests
    When I get payment QR code Payload via Gluwa SDK for invalid <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      |  Code  | Currency |              Message             |
      |  400   |   DOGE   |  "DOGE" is not a valid Currency. |
      |  503   |   KRWG   |  Unsupported currency            |



  @gluwaSdkNeg5
  Scenario Outline: Get transaction details by hash Negative
    When I get transaction by <TxnHash> for <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency |  Code |                                TxnHash                             |          Message         |
      |   GCRE   |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | Unsupported currency GCRE. |
      |   DOGE   |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | The value 'DOGE' is not valid. |
      |   NGNG   |  400  | 0x10d68ef19b84afbdd305acinvalid                                    | Invalid txnHash value. |