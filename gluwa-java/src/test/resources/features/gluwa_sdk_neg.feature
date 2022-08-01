@gluwaSdkNeg
Feature: Negative Unit Tests for Gluwa SDK

  @gluwaSdkNeg1
  Scenario Outline: Get List of Transactions negative test
    When I get list of transactions using request parameters <limit> <offset> <Status> <Sign> <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency |  Status   | Code | limit | offset | Sign   |           Message              |
      |   GCRE   | Confirmed | 400  | 0     | 0      |        | Unsupported currency GCRE.     |
      |   DOGE   | Confirmed | 400  | 0     | 0      |        | The value 'DOGE' is not valid. |
      |   NGNG   | Confirmed | 400  | -1    | 0      |        | The value '-1' is not valid.   |
      |   NGNG   | Confirmed | 400  | 0     | -1     |        | The value '-1' is not valid.   |
      |   NGNG   | Confirmed | 403  | 0     | 0      | invalid | Address signature does not have a valid format.  |
      |   NGNG   | Confirmed | 403  | 0     | 0      | null   | No request signature header was provided. |


  @gluwaSdkNeg2
  Scenario Outline: Get fee for currency Negative
    When I get fee for transaction of <Amount> for currency <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency | Code | Amount | Message                            |
      | DOGE     | 400  | 50     | The value 'DOGE' is not valid.     |
      | KRWG     | 503  | 50     | Unsupported currency               |
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
    When I get payment QR code Payload via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee> <Auth>
    Then I validate request response <Code> and <Message>
    Examples:
      |  Code  | Currency |              Message                      |  Amount |  Expiry | Fee | Auth    |
      |  400   |   DOGE   |  "DOGE" is not a valid Currency.          |    1    |  1800   |  1  |         |
      |  503   |   KRWG   |  Unsupported currency                     |    1    |  1800   |  1  |         |
      |  400   |   USDCG  |  The Amount must be greater than the fee  |    1    |  1800   |  1  |         |
      |  400   |   USDCG  |  The Amount must be greater than the fee  |   -1    |  1800   |  1  |         |
      |  400   |   USDCG  |  The Amount field is required.            |         |  1800   |  1  |         |
      |  400   |   USDCG  |  The field Expiry must be between 0 and   |    5    |  -1     |  1  |         |
      |  400   |   NGNG   |  The Amount must be greater than the fee  |    1    |  1800   |  1  |         |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5     |  1800   |  1  | null    |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5     |  1800   |  1  | BITCOIN |

  @gluwaSdkNeg5
  Scenario Outline: Get transaction details by hash Negative
    When I get transaction using parameters <TxnHash> <Currency> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency |  Code |                                TxnHash                             |          Message                                |  Sign   |
      |   GCRE   |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | Unsupported currency GCRE.                      |         |
      |   DOGE   |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | The value 'DOGE' is not valid.                  |         |
      |   NGNG   |  400  | 0x10d68ef19b84afbdd305acinvalid                                    | Invalid txnHash value.                          |         |
      |   USDCG  |  403  | 0x821af423dc65def5b116b20876aeba2275cb914161b23c32f8fbd15a578ce9b8 | Address signature does not have a valid format. | invalid |
      |   USDCG  |  403  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | No request signature header was provided.       | null    |
      |   USDCG  |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | Transaction hash:                               |         |

  @gluwaSdkNeg6
  Scenario Outline: Post transaction negative tests
    When I post transaction via Gluwa SDK using parameters <Currency> <Amount> <TargetAddress> <Fee> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency | Code | Message                                          | Amount  |               TargetAddress                |   Fee  | Sign  |
      |   GCRE   | 400  | Unsupported currency GCRE.                       |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   DOGE   | 400  | The value 'DOGE' is not valid.                   |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | Target address is not a valid ethereum address.  |   1     | TronSolanaBNBMaticNearMixAddressesInvalid  |   1    |       |
      |   USDCG  | 400  | amount should be a positive number               |  -1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | amount should be a positive number               |  foobar | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | Amount must be greater than 0.                   |   0     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |

  @gluwaSdkNeg7
  Scenario Outline: Get Payment QR Code negative tests
    When I get payment QR code via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee> <Auth>
    Then I validate request response <Code> and <Message>
    Examples:
      | Code   | Currency |           Message                         | Amount |  Expiry | Fee | Auth    |
      |  400   |   DOGE   | "DOGE" is not a valid Currency.           |   1    |  1800   |  1  |         |
      |  400   |   USDCG  | The Amount must be greater than the fee   |   1    |  1800   |  1  |         |
      |  503   |   KRWG   |  Unsupported currency                     |   1    |  1800   |  1  |         |
      |  400   |   USDCG  |  The Amount must be greater than the fee  |  -1    |  1800   |  1  |         |
      |  400   |   USDCG  |  The Amount field is required.            |        |  1800   |  1  |         |
      |  400   |   USDCG  |  The field Expiry must be between 0 and   |   5    |  -1     |  1  |         |
      |  400   |   NGNG   |  The Amount must be greater than the fee  |   1    |  1800   |  1  |         |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5    |  1800   |  1  | null    |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5    |  1800   |  1  | BITCOIN |
