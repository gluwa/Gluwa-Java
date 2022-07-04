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
#      TO-DISCUSS: Gluwa SDK returns exception message instead of returning bad request response and failing validation step
      Then I validate bad request response


    @gluwaSdk3
    Scenario: Get Payment QR Code happy path
      When I get payment QR code via Gluwa SDK
      Then I validate get response

    @gluwaSdk5
    Scenario Outline: Get List of Transactions happy path
      When I get list of transactions for "<Currency>"
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

    @gluwaSdk7
    Scenario Outline: Get transaction details by hash happy path
      When I get a transaction by hash for "<Currency>"
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |

      @gluwaSdk9
      Scenario: Get payment QR code with Payload happy path
        When I get payment QR code with Payload via Gluwa SDK
        Then I validate get response



