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

    @gluwaSdk7
    Scenario Outline: Get List of Transactions negative test
      When I get list of transactions with <Status> for unsupported currency <Currency>
      Then I validate request response <Code> and <Message>
      When I get list of transactions with <Status> for invalid currency <invalidCurrency>
      Then I validate request response <Code> and <InvalidCurrencyMessage>
      Examples:
        | Currency |   Status   | Code |            Message            |  invalidCurrency |              InvalidCurrencyMessage       |
        |   GCRE   |  Confirmed | 400  | Unsupported currency GCRE.    |   DOGE           |   one of more Url parameters are invalid. |


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

    @gluwaSdk9
    Scenario Outline: Get transaction details by hash negative tests
      When I get transaction by hash for unsupported <Currency>
      Then I validate request response <Code> and <Message>
      When I get transaction by hash for invalid <invalidCurrency>
      Then I validate request response <Code> and <InvalidCurrencyMessage>
      Examples:
        | Currency |  Code |            Message            |  invalidCurrency |              InvalidCurrencyMessage       |
        |   GCRE   |  400  | Unsupported currency GCRE.    |   DOGE           |   one of more Url parameters are invalid. |

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
          | GCRE     |

    @gluwaSdk11
    Scenario Outline: Get payment QR code with Payload negative tests
      When I get payment QR code Payload via Gluwa SDK for invalid <invalidCurrency>
      Then I validate request response <Code> and <InvalidCurrencyMessage>
      Examples:
        |  Code  |  invalidCurrency |              InvalidCurrencyMessage       |
        |  400   |   DOGE           |   One or more fields are invalid in body. |

    @gluwaSdk12
    Scenario Outline: Get Address for currencies happy path
      When I get address via Gluwa SDK for <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |
        | GCRE     |

    @gluwaSdk13
    Scenario Outline: Get Address for currencies negative tests
      When I get address via Gluwa SDK with invalid <invalidCurrency>
      Then I validate request response <Code> and <InvalidCurrencyMessage>
      Examples:
        |  Code  |  invalidCurrency |              InvalidCurrencyMessage       |
        |  400   |   DOGE           |   one of more Url parameters are invalid. |

    @gluwaSdk14
    Scenario Outline: Get fee for currency Positive
      When I get fee for currency <Currency>
      Then I validate get response
      Examples:
        | Currency |
        | USDCG    |
        | sUSDCG   |
        | NGNG     |
        | sNGNG    |
        | GCRE     |

    @gluwaSdk15
    Scenario Outline: Get fee for currency Negative
      When I get fee for currency <Currency>
      Then I validate request response <Code> and <Message>
      Examples:
        | Currency | Code | Message |
        | DOGE     | 400  | one of more Url parameters are invalid. |
