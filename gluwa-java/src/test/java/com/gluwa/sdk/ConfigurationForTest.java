package com.gluwa.sdk;

import org.web3j.crypto.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class ConfigurationForTest extends Configuration {

    // Config variables
    public static final String API_KEY = getProperty("API_KEY_SANDBOX");
    public static final String API_SECRET = getProperty("API_SECRET_SANDBOX");
    public static final String WEBHOOK_SECRET = getProperty("WebhookSecret");
    public static final String ETH_PUBLIC_ADDRESS = getEthAddress(getProperty("SRC_PRIVATE_sNGNG_PROD"));
    public static final String ETH_PRIVATE_KEY = getProperty("SRC_PRIVATE_sNGNG_PROD");

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
        return (cs.getAddress());
    }

    private static String getProperty(String name){
        String property = null;
        try {
            if (isRanLocally()){
                property = getPropertyfromFile(name);
            } else {
                property = getPropertyfromEnv(name);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return property;
    }

    private static boolean isRanLocally(){
        boolean githubActionsVar = Boolean.parseBoolean(System.getProperty("GITHUB_ACTIONS"));
        return !githubActionsVar;
    }

//    private static boolean isRanOnWin() {
//        String os = System.getProperty("os.name").toLowerCase();
//        return os.contains("win");
//    }

    private static String getPropertyfromEnv(String name){
        return (System.getenv(name));
    }

    private static String getPropertyfromFile(String name){
        String property;
        Properties prop = new Properties();
        try {
            String dir = System.getProperty("user.dir");
            String separator = File.separator;
            FileInputStream ip = new FileInputStream(String.join(separator,dir,"src","test","resources","properties","config.properties"));
            prop.load(ip);
//            if (isRanOnWin()) {
//                InputStream ip = new FileInputStream(dir+"\\src\\test\\resources\\properties\\config.properties");
//                prop.load(ip);
//            } else {
//                InputStream ip = new FileInputStream(dir+"/src/test/resources/properties/config.properties");
//                prop.load(ip);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        property = prop.getProperty(name);
        return property;
    }
}


