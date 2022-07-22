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
    Scenario Outline: Post transaction negative test
      When I post transaction via Gluwa SDK using unsupported currency for <Currency>
      Then I validate bad request response for unsupported currency <Currency>
      Examples:
        | Currency |
        |   GCRE   |

#   TO-DO: Uncomment NGNG and sNGNG currencies when fixed
    @gluwaSdk3
    Scenario Outline: Get Payment QR Code happy path
      When I get payment QR code via Gluwa SDK for currency <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        #| NGNG     |
        #| sNGNG    |


    @gluwaSdk5
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

    @gluwaSdk7
    Scenario Outline: Get List of Transactions negative test
      When I get list of transactions with <Status> for unsupported currency <Currency>
      Then I validate bad request response for unsupported currency <Currency>
      Examples:
        | Currency |   Status   |
        |   GCRE   |  Confirmed |


    @gluwaSdk8
    Scenario Outline: Get transaction details by hash happy path
      When I get a transaction by hash for <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

      @gluwaSdk10
      Scenario Outline: Get payment QR code with Payload happy path
        When I get payment QR code with Payload via Gluwa SDK for <Currency>
        Then I validate get response
        Examples:
          | Currency |
          | USDCG    |
          | sUSDCG   |
          | NGNG     |
          | sNGNG    |

    @gluwaSdk11
    Scenario Outline: Get Address for currencies happy path
      When I get address via Gluwa SDK for <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

    @gluwaSdk12
    Scenario Outline: Get fee for currency Positive
      When I get fee for currency <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |
