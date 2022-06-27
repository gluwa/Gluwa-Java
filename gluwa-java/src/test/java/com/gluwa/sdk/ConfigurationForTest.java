package com.gluwa.sdk;

public class ConfigurationForTest extends Configuration {

    // Config variables
    public static final String API_KEY = System.getenv("ApiKeySandbox");
    public static final String API_SECRET = System.getenv("ApiSecretSandbox");
    public static final String WEBHOOK_SECRET = System.getenv("WebhookSecret");
    public static final String ETH_PUBLIC_ADDRESS = System.getenv("MasterEthPublicAddress");
    public static final String ETH_PRIVATE_KEY = System.getenv("MasterEthPrivateKey");

    public ConfigurationForTest() {
        super.set__DEV__(true);
        super.setApiKey(API_KEY);
        super.setApiSecret(API_SECRET);
        super.setWebhookSecret(WEBHOOK_SECRET);
        super.setMasterEthereumAddress(ETH_PUBLIC_ADDRESS);
        super.setMasterEthereumPrivateKey(ETH_PRIVATE_KEY);
    }
}
