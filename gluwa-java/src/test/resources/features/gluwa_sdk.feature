@gluwaSdk
  Feature: End to End Unit Tests for Gluwa SDK
    In order to manage my Enum
    As a System Admin
    I want to get the Enum

    @gluwaSdk1
    Scenario Outline: Post transaction end to end
      When I post transaction via Gluwa SDK for "<Currency>"
      Then I validate response that transaction is created
    Examples:
      | Currency |
      | USDCG    |
      | sUSDCG   |
      | NGNG     |
      | sNGNG    |


    @gluwaSdk2
    Scenario: Get Payment QR Code end to end
      When I get payment QR code via Gluwa SDK
      Then I validate get response

    @gluwaSdk3
    Scenario: Get List of Transactions end to end
      When I get list of transactions for "USDCG"
      Then I validate get response