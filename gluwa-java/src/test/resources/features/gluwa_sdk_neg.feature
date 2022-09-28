@gluwaSdkNeg
Feature: Negative Unit Tests for Gluwa SDK

  @gluwaSdkNeg1
  Scenario Outline: Get List of Transactions negative test
    When I get list of transactions using request parameters <limit> <offset> <Status> <Sign> <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency  |  Status   | Code | limit | offset | Sign    |           Message                                |
      |   GCRE    | Confirmed | 400  | 0     | 0      |         | Unsupported currency GCRE.                       |
      |   DOGE    | Confirmed | 400  | 0     | 0      |         | The value 'DOGE' is not valid.                   |
      |   USDCG   | Confirmed | 400  | -1    | 0      |         | The value '-1' is not valid.                     |
      |   USDCG   | Confirmed | 400  | 0     | -1     |         | The value '-1' is not valid.                     |
      |   USDCG   | Confirmed | 403  | 0     | 0      | invalid | Address signature does not have a valid format.  |
      |   USDCG   | Confirmed | 403  | 0     | 0      | null    | No request signature header was provided.        |


  @gluwaSdkNeg2
  Scenario Outline: Get fee for currency Negative
    When I get fee for transaction of <Amount> for currency <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency  | Code | Amount | Message                            |
      | DOGE      | 400  | 50     | The value 'DOGE' is not valid.     |
      | KRWG      | 503  | 50     | Unsupported currency               |
      | USDCG     | 400  | -4     | amount should be a positive number |
      | USDCG     | 400  | aa     | amount should be a positive number |

  @gluwaSdkNeg3
  Scenario Outline: Get Address for invalid currency negative tests
    When I get address via Gluwa SDK with invalid <Currency>
    Then I validate request response <Code> and <Message>
    Examples:
      |  Code  |  Currency |              Message           |
      |  400   |   DOGE    | The value 'DOGE' is not valid. |

  @gluwaSdkNeg4
  Scenario Outline: Get payment QR code with Payload negative tests
    When I get payment QR code Payload via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee> <Auth> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      |  Code  | Currency |              Message                      |  Amount |  Expiry | Fee | Auth    |   Sign |
      |  400   |   DOGE   |  "DOGE" is not a valid Currency.          |    1    |  1800   |  1  |         |        |
      |  503   |   KRWG   |  Unsupported currency                     |    1    |  1800   |  1  |         |        |
      |  400   |   USDCG  |  The Amount must be greater than the fee  |   -1    |  1800   |  1  |         |        |
      |  400   |   USDCG  |  The Amount field is required.            |         |  1800   |  1  |         |        |
      |  400   |   USDCG  |  The field Expiry must be between 0 and   |    5    |  -1     |  1  |         |        |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5     |  1800   |  1  | BITCOIN |        |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5     |  1800   |  1  | null    |        |
      |  400   |   USDCG  |  The Signature field is required.         |   5     |  1800   |  1  |         | null   |
      |  400   |   USDCG  |  Invalid address signature.               |   5     |  1800   |  1  |         | invalid|

  @gluwaSdkNeg5
  Scenario Outline: Get transaction details by hash Negative
    When I get transaction using parameters <TxnHash> <Currency> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency |  Code |                                TxnHash                             |          Message                                |  Sign   |
      |   DOGE   |  400  | 0x10d68ef19b84afbdd305acf18a06a155864cae00716f47397f5f343c0adef1e9 | The value 'DOGE' is not valid.                  |         |
      |   USDCG  |  400  | 0x10d68ef19b84afbdd305acinvalid                                    | Invalid txnHash value.                          |         |
      |   USDCG  |  403  | 0xc7fe16e72cc6a0e7436b6c5f984366e210442c4fe2823b413751eb92a7ab8309 | Address signature does not have a valid format. | invalid |
      |   USDCG  |  403  | 0xc7fe16e72cc6a0e7436b6c5f984366e210442c4fe2823b413751eb92a7ab8309 | No request signature header was provided.       | null    |

  @gluwaSdkNeg6
  Scenario Outline: Post transaction negative tests
    When I post transaction via Gluwa SDK using parameters <Currency> <Amount> <TargetAddress> <Fee> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      | Currency | Code | Message                                          | Amount  |               TargetAddress                |   Fee  | Sign  |
      |   DOGE   | 400  | The value 'DOGE' is not valid.                   |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | Target address is not a valid ethereum address.  |   1     | TronSolanaBNBMaticNearMixAddressesInvalid  |   1    |       |
      |   USDCG  | 400  | amount should be a positive number               |  -1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | amount should be a positive number               |  foobar | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG  | 400  | Amount must be greater than 0.                   |   0     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   GCRE   | 400  | The signed transaction format is invalid.        |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   1    |       |
      |   USDCG   | 400  | The Signature field is required.                |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   10   | null  |
      |   NGNG   | 400  | Signature is invalid                             |   1     | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |   10   | invalid |


  @gluwaSdkNeg7
  Scenario Outline: Get Payment QR Code negative tests
    When I get payment QR code via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee> <Auth> <Sign>
    Then I validate request response <Code> and <Message>
    Examples:
      | Code   | Currency |           Message                         | Amount |  Expiry | Fee | Auth    |  Sign   |
      |  400   |   DOGE   | "DOGE" is not a valid Currency.           |   1    |  1800   |  1  |         |         |
      |  503   |   KRWG   |  Unsupported currency                     |   1    |  1800   |  1  |         |         |
      |  400   |   USDCG  |  The Amount must be greater than the fee  |  -1    |  1800   |  1  |         |         |
      |  400   |   USDCG  |  The Amount field is required.            |        |  1800   |  1  |         |         |
      |  400   |   USDCG  |  The field Expiry must be between 0 and   |   5    |  -1     |  1  |         |         |
      |  400   |   NGNG   |  The Amount must be greater than the fee  |   1    |  1800   |  1  |         |         |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5    |  1800   |  1  | null    |         |
      |  403   |   USDCG  |  Not authorized to use this endpoint.     |   5    |  1800   |  1  | BITCOIN |         |
      |  400   |   USDCG  |  The Signature field is required.         |   5     |  1800   |  1  |         | null   |
      |  400   |   USDCG  |  Invalid address signature.               |   5     |  1800   |  1  |         | invalid|
