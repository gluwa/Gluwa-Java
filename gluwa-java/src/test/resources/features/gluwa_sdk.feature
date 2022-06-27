@gluwaSdk
  Feature: End to End Unit Tests for Gluwa SDK

    @gluwaSdk1
    Scenario: Post transaction using Usdcg with happy path
      When I post transaction via Gluwa SDK
      Then I validate response that transaction is created

    @gluwaSdk2
    Scenario: Get Payment QR Code end to end
      When I get payment QR code via Gluwa SDK