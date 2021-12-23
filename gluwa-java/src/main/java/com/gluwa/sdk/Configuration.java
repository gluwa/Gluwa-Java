package com.gluwa.sdk;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Numeric;

public class Configuration {

	static long SIG_DOMAIN_TRANSFER = 3;

	private boolean __DEV__ = false;
	private String apiKey;
	private String apiSecret;
	private String webhookSecret;
	private String masterEthereumPrivateKey;
	private String masterEthereumAddress;
	private ECKeyPair eCKeyPair;



	
	public boolean __DEV__() {
		return __DEV__;
	}

	/**
	 * sandbox mode
	 * @param __DEV__ true if sandbox mode, default = false
	 */
	public void set__DEV__(boolean __DEV__) {
		this.__DEV__ = __DEV__;
	}

	public String getApiKey() {
		return apiKey;
	}
	/**S
	 * set Api Key
	 * @param apiKey key for api
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getApiSecret() {
		return apiSecret;
	}
	/**
	 * set Api Secret
	 * @param apiSecret secret of apikey
	 */
	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	public String getWebhookSecret() {
		return webhookSecret;
	}
	/**
	 * set WebhookSecret Key
	 * @param webhookSecret webhooksecrect key
	 */
	public void setWebhookSecret(String webhookSecret) {
		this.webhookSecret = webhookSecret;
	}
	public String getMasterEthereumPrivateKey() {
		return masterEthereumPrivateKey;
	}
	/**
	 * set wallet private Key
	 * @param masterEthereumPrivateKey private key
	 */
	public void setMasterEthereumPrivateKey(String masterEthereumPrivateKey) {
		this.masterEthereumPrivateKey = masterEthereumPrivateKey;
		this.eCKeyPair = ECKeyPair.create(Numeric.toBigInt(this.masterEthereumPrivateKey));
	}
	public String getMasterEthereumAddress() {
		return masterEthereumAddress;
	}
	/**
	 * set wallet address
	 * @param masterEthereumAddress address
	 */
	public void setMasterEthereumAddress(String masterEthereumAddress) {
		this.masterEthereumAddress = masterEthereumAddress;
	}

	/**
	 * authorization code
	 * @return authorization code
	 */
	protected String getAuthorization() {
		Encoder base64Encoder = Base64.getEncoder();
		return base64Encoder.encodeToString((apiKey + ":" + apiSecret).getBytes());
	}
	/**
	 * @return private / public keyPair for signature
	 */
	protected ECKeyPair getECKeyPair() {
		return eCKeyPair;
	}
}
