package com.gluwa.sdk;

import org.web3j.crypto.Credentials;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
        String publicAddress = cs.getAddress();
        return (publicAddress);
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
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win") ||  os.contains("mac")) {
            return true;
        } else {
            return false;
        }
    }

    private static String getPropertyfromEnv(String name){
//        if (name.equals("SRC_PRIVATE_sNGNG_PROD")){
//            return getEthAddress(System.getenv(name));
//        } else {
        return (System.getenv(name));
    }

    private static String getPropertyfromFile(String name){
        String property;
        Properties prop = new Properties();
        try {
            InputStream ip = new FileInputStream("C:\\GluwaDev\\Gluwa-Java\\gluwa-java\\src\\test\\resources\\properties\\config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            e.printStackTrace();
        }
        property = prop.getProperty(name);
        return property;
    }

    public static void main (String[] args){
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(getEthAddress(getProperty("SRC_PRIVATE_sNGNG_PROD")));
        System.out.println(os);
    }
}


