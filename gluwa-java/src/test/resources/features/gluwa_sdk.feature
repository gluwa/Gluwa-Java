@gluwaSdk
  Feature: End to End Unit Tests for Gluwa SDK

    @gluwaSdk1
    Scenario Outline: Post transaction happy path
      When I post transaction via Gluwa SDK using parameters <Currency> <Amount> <TargetAddress> <Fee> <Sign>
      Then I validate response that transaction is created
      Examples:
        | Currency | Amount |                TargetAddress               | Fee | Sign  |
        | USDCG    |   1    | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |  1  |       |
        | sUSDCG   |   1    | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |  1  |       |
        | NGNG     |   1    | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |  1  |       |
        | sNGNG    |   1    | 0xfd91d059f0d0d5f6adee0f4aa1fdf31da2557bc9 |  1  |       |

    @gluwaSdk4
    Scenario Outline: Get Payment QR Code happy path
      When I get payment QR code via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee>
      Then I validate get response
      Examples:
        | Currency | Amount |  Expiry | Fee |
        | USDCG    |   5    |  1800   |  0  |
        | sUSDCG   |   1    |  1800   |  0  |
        | GCRE     |   1    |  1800   |  0  |
        | NGNG     |   101  |  1800   |  1  |

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
        When I get payment QR code Payload via Gluwa SDK using parameters <Currency> <Amount> <Expiry> <Fee>
        Then I validate get response
        Examples:
          | Currency | Amount | Expiry | Fee |
          | USDCG    |   5    |  1800  |  1  |
          | sUSDCG   |   1    |  1800  |  1  |
          | NGNG     |   101  |  1800  |  1  |
          | sNGNG    |   1    |  1800  |  1  |
          | GCRE     |   1    |  1800  |  1  |

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

    @gluwaSdk14
    Scenario Outline: Get fee for currency Positive
      When I get fee for transaction of <Amount> for currency <Currency>
      Then I validate get response
      Examples:
        | Currency | Amount |
        | USDCG    | 10     |
        | sUSDCG   | 10     |
        | NGNG     | 10     |
        | sNGNG    | 10     |
        | GCRE     | 10     |