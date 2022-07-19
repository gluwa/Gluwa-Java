@gluwaSdk
  Feature: End to End Unit Tests for Gluwa SDK
    In order to manage my Enum
    As a System Admin
    I want to get the Enum

    @gluwaSdk1
    Scenario Outline: Post transaction happy path
      When I post transaction via Gluwa SDK for "<Currency>"
      Then I validate response that transaction is created
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

    @gluwaSdk2
    Scenario: Post transaction negative test
      When I post transaction via Gluwa SDK using invalid currency as "GCRE"
#   TO-DISCUSS: Gluwa SDK returns exception message instead of returning bad request response and failing validation step
#      Then I validate bad request response


    @gluwaSdk3
    Scenario: Get Payment QR Code happy path
      When I get payment QR code via Gluwa SDK
      Then I validate get response


#   TO-DO: Refactor to modern cucumber expression
    @gluwaSdk5
    Scenario Outline: Get transaction history for currencies with Complete status
      When I get list of transactions with "<Status>" status for "<Currency>"
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

#   TO-DO: After exception handler added to SDK, bad request validation to be added
    @gluwaSdk7
    Scenario: Get List of Transactions negative test
      When I get list of transactions for invalid currency "GCRE"
#      Then I validate bad request response

    @gluwaSdk8
    Scenario Outline: Get transaction details by hash happy path
      When I get a transaction by hash for "<Currency>"
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

      @gluwaSdk10
      Scenario Outline: Get payment QR code with Payload happy path
        When I get payment QR code with Payload via Gluwa SDK for "<Currency>"
        Then I validate get response
        Examples:
          | Currency |
          | USDCG    |
          | sUSDCG   |
          | NGNG     |
          | sNGNG    |

    @gluwaSdk11
    Scenario Outline: Get Address for currencies happy path
      When I get address via Gluwa SDK for "<Currency>"
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

    @gluwaSdk12
    Scenario Outline: Get fee for currency Positive
      When I get fee for currency "<Currency>"
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |


