package com.gluwa.sdk;

import org.web3j.crypto.Credentials;

public class ConfigurationForTest extends Configuration {

    // Config variables
    public static final String API_KEY = System.getenv("API_KEY_SANDBOX");
    public static final String API_SECRET = System.getenv("API_SECRET_SANDBOX");
    public static final String WEBHOOK_SECRET = System.getenv("WebhookSecret");
    public static final String ETH_PUBLIC_ADDRESS = getEthAddress(System.getenv("SRC_PRIVATE_GENERAL"));
    public static final String ETH_PRIVATE_KEY = System.getenv("SRC_PRIVATE_GENERAL");

    public ConfigurationForTest() {
        super.set__DEV__(true);
        super.setApiKey(API_KEY);
        super.setApiSecret(API_SECRET);
        super.setWebhookSecret(WEBHOOK_SECRET);
        super.setMasterEthereumAddress(ETH_PUBLIC_ADDRESS);
        super.setMasterEthereumPrivateKey(ETH_PRIVATE_KEY);
    }

    private static String getEthAddress(String privateKey){
        Credentials cs = Credentials.create(privateKey);
        String publicAddress = cs.getAddress();
        return (publicAddress);
    }
}

