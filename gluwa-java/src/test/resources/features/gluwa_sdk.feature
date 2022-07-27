@gluwaSdk
  Feature: End to End Unit Tests for Gluwa SDK

    @gluwaSdk1
    Scenario Outline: Post transaction happy path
      When I post transaction via Gluwa SDK for <Currency>
      Then I validate response that transaction is created
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

    @gluwaSdk2
    Scenario Outline: Post transaction negative test unsupported currency
      When I post transaction via Gluwa SDK using unsupported currency for <Currency>
      Then I validate request response <Code> and <Message>
      Examples:
        | Currency | Code | Message |
        |   GCRE   | 400  | Unsupported currency GCRE.    |

    @gluwaSdk3
    Scenario Outline: Post transaction negative test invalid currency
      When I post transaction via Gluwa SDK using invalid currency for <Currency>
      Then I validate request response <Code> and <Message>
      Examples:
        | Currency | Code |                 Message                 |
        |   DOGE   | 400  | one of more Url parameters are invalid. |

    @gluwaSdk4
    Scenario Outline: Get Payment QR Code happy path
      When I get payment QR code via Gluwa SDK for currency <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | GCRE     |

    @gluwaSdk5
    Scenario Outline: Get Payment QR Code negative invalid currency
      When I get payment QR code via Gluwa SDK for invalid currency <Currency>
      Then I validate request response <Code> and <Message>
      Examples:
        | Currency | Code |           Message                       |
        |   DOGE   | 400  | One or more fields are invalid in body. |

    @gluwaSdk6
    Scenario Outline: Get transaction history for currencies with different statuses
      When I get list of transactions with <Status> status for <Currency>
      Then I validate get response
      Examples:
        | Currency | Status     |
        | USDCG    | Confirmed  |
        | sUSDCG   | Confirmed  |
        | NGNG     | Confirmed  |
        | sNGNG    | Confirmed  |
        | USDCG    | Incomplete |
        | sUSDCG   | Incomplete |
        | NGNG     | Incomplete |
        | sNGNG    | Incomplete |


    @gluwaSdk8
    Scenario Outline: Get transaction details by hash Positive
      When I get transaction by <TxnHash> for <Currency>
      Then I validate get response
      Examples:
        | Currency |                                TxnHash                             |
        | USDCG    | 0xb0015ecb8f2d4b2a77dbad40dbd024739f1346e5b4e5026631db88ed0f1ad5b4 |
        | sUSDCG   | 0xeab93826d01a8b9958db234e2ee8820b6d5aa7c00e38d75e951b894676f9f345 |
        | NGNG     | 0xbde2dbee48e18e18f5d10560e549c4d58b77f4cf84c46717f27ece026afb6e4f |
        | sNGNG    | 0xc1d74c3c3a7791a96bd0cae42e2e5ddb993562131e83b248ed6d10b93a8ea366 |


      @gluwaSdk10
      Scenario Outline: Get payment QR code with Payload Positive
        When I get payment QR code with Payload via Gluwa SDK for <Currency>
        Then I validate get response
        Examples:
          | Currency |
          | USDCG    |
          | sUSDCG   |
          | NGNG     |
          | sNGNG    |
          | GCRE     |


    @gluwaSdk12
    Scenario Outline: Get Address for currencies Positive
      When I get address via Gluwa SDK for <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |
        | GCRE     |
|

    @gluwaSdk14
    Scenario Outline: Get fee for currency Positive
      When I get fee for transaction of <Amount> for currency <Currency>
      Then I validate get response
      Examples:
        | Currency | Amount |
        | USDCG    | 50     |
        | sUSDCG   | 50     |
        | NGNG     | 50     |
        | sNGNG    | 50     |
        | GCRE     | 50     |


