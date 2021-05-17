package com.gluwa.sdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

/**
 * call Gluwa Api
 * 
 * @author Gluwa developer
 *
 */
public class GluwaApiSDKImpl implements GluwaApiSDK {

	static final Logger LOGGER = LoggerFactory.getLogger(GluwaApiSDKImpl.class);

	Configuration configuration;
	GluwaApiService api;
	Encoder base64Encoder = Base64.getEncoder();

	public GluwaApiSDKImpl(Configuration configuration) {
		this.configuration = configuration;
		this.api = new GluwaApiService(configuration.__DEV__());
	}

	/**
	 * get payment QRCode Image
	 * 
	 * @param transaction
	 * @return api response
	 */
	@Override
	public GluwaResponse getPaymentQRCode(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Content-Type", "application/json");
		Header h2 = new BasicHeader("Authorization", "Basic " + configuration.getAuthorization());

		HashMap<String, Object> params = new HashMap<>();
		params.put("Signature", timestampSignature());
		params.put("Currency", transaction.getCurrency().toString());
		params.put("Target", configuration.getMasterEthereumAddress());
		params.put("Amount", transaction.getAmount());
		if (transaction.getMerchantOrderID() != null)
			params.put("MerchantOrderID", transaction.getMerchantOrderID());
		if (transaction.getNote() != null)
			params.put("Note", transaction.getNote());
		if (transaction.getExpiry() != 0)
			params.put("Expiry", transaction.getExpiry());

		GluwaResponse result = new GluwaResponse();
		try {
			result = api.post(GluwaApiService.V1_PATH_QRCODE, GluwaApiService.ParameterType.JSON, params, h1, h2);
		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:{}", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}
		return result;

	}

	@Override
	public GluwaResponse getAddresses(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Accept", "application/json");
		Header h2 = new BasicHeader("Content-Type", "application/json;charset=UTF-8");
		String path = generatePath(GluwaApiService.V1_PATH_ADDRESS, transaction.getCurrency().name())
				+ configuration.getMasterEthereumAddress();

		GluwaResponse result = new GluwaResponse();
		try {
			result = api.get(path, h1, h2);
		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:{}", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}
		return result;

	}

	@Override
	public GluwaResponse getFee(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Accept", "application/json");
		Header h2 = new BasicHeader("Content-Type", "application/json;charset=UTF-8");
		String path = generatePath(GluwaApiService.V1_PATH_FEE, transaction.getCurrency().name());

		GluwaResponse result = new GluwaResponse();
		try {
			result = api.get(path, h1, h2);

		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:{}", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}

		return result;

	}

	@Override
	public GluwaResponse postTransaction(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Accept", "application/json");
		Header h2 = new BasicHeader("Content-Type", "application/json;charset=UTF-8");
		String path = GluwaApiService.V1_PATH_TRANSACTION;

		GluwaResponse feeResponse = getFee(transaction);

		transaction.setFee((String) feeResponse.getMapList().get(0).get("MinimumFee"));
		transaction.setNonce(nonce());

		String hash = hashTransaction(transaction);

		HashMap<String, Object> params = new HashMap<>();
		params.put("Signature", signMessage(Numeric.hexStringToByteArray(hash)));
		params.put("Source", configuration.getMasterEthereumAddress());
		params.put("Currency", transaction.getCurrency().toString());
		params.put("Target", transaction.getTargetAddress());
		params.put("Amount", transaction.getAmount());
		params.put("Fee", transaction.getFee());
		params.put("Nonce", transaction.getNonce());
		if (transaction.getNote() != null)
			params.put("Note", transaction.getNote());
		if (transaction.getMerchantOrderID() != null)
			params.put("MerchantOrderID", transaction.getMerchantOrderID());
		if (transaction.getIdem() != null)
			params.put("Idem", transaction.getIdem());

		GluwaResponse result = new GluwaResponse();

		try {
			result = api.post(path, GluwaApiService.ParameterType.JSON, params, h1, h2);
		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}

		return result;

	}

	@Override
	public GluwaResponse getListTransactionHistory(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Accept", "application/json");
		Header h2 = new BasicHeader("X-REQUEST-SIGNATURE", timestampSignature());

		String path = generatePath(GluwaApiService.V1_PATH_TRANSACTION_HISTORY, transaction.getCurrency().name());
		path = path.replaceAll("\\{MasterEthereumAddress\\}", configuration.getMasterEthereumAddress());
		path += "?Limit=" + transaction.getLimit() + "&Status=" + transaction.getStatus() + "&Offset="
				+ transaction.getOffset();

		GluwaResponse result = new GluwaResponse();
		try {
			result = api.get(path, h1, h2);

		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:{}", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}

		return result;

	}

	@Override
	public GluwaResponse getListTransactionDetail(GluwaTransaction transaction) {

		Header h1 = new BasicHeader("Accept", "application/json");
		Header h2 = new BasicHeader("X-REQUEST-SIGNATURE", timestampSignature());
		String path = generatePath(GluwaApiService.V1_PATH_TRANSACTION_DETAIL, transaction.getCurrency().name())
				+ transaction.getTxnHash();

		GluwaResponse result = new GluwaResponse();
		try {
			result = api.get(path, h1, h2);
		} catch (Exception e) {
			LOGGER.info("GluwaTransaction:{}", transaction);
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}

		return result;

	}

	@Override
	public boolean validateWebhook(String payload, String signature) {

		byte[] hashedPayload = hmacSha256(configuration.getWebhookSecret().getBytes(), payload.getBytes());
		return signature.equals(base64Encoder.encodeToString(hashedPayload));

	}

	protected byte[] hmacSha256(byte[] key, byte[] input) {
		HMac hMac = new HMac(new SHA256Digest());
		hMac.init(new KeyParameter(key));
		hMac.update(input, 0, input.length);
		byte[] out = new byte[32];
		hMac.doFinal(out, 0);
		return out;
	}

	protected String timestampSignature() {

		String timestamp = timestamp();
		return base64Encoder.encodeToString((timestamp + "." + signMessage(timestamp.getBytes())).getBytes());
	}

	protected String nonce() {

		int max = 9, min = 1;

		SecureRandom rd = null;
		try {
			rd = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
			throw new GluwaSDKException(e);
		}

		StringBuilder sb = new StringBuilder();

		int randomInt = rd.nextInt(max + 1 - min) + min;
		sb.append(randomInt);
		for (int i = 0; i <= 3; i++) {
			long randomLong = Math.abs(rd.nextLong());
			sb.append(String.format("%019d", randomLong));
		}

		return sb.substring(0, 75);

	}

	protected String timestamp() {
		long now = System.currentTimeMillis() / 1000L;
		return Long.toString(now);
	}

	private String generatePath(String path, String currency) {
		return path.replaceAll("\\{Currency\\}", currency);
	}

	protected String signMessage(byte[] message) {

		SignatureData signatureData = Sign.signPrefixedMessage(message, configuration.getECKeyPair());
		return Numeric.toHexString(signatureData.getR())
				+ Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getS()))
				+ Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getV()));
	}

	protected String hashTransaction(GluwaTransaction transaction) {

		StringBuffer sb = new StringBuffer();

		sb.append(Numeric.cleanHexPrefix(api.getContractAddress(transaction.getCurrency()))); // contract
		sb.append(Numeric.cleanHexPrefix(configuration.getMasterEthereumAddress()));// sender address
		sb.append(Numeric.cleanHexPrefix(transaction.getTargetAddress()));// receiver address

		Uint256 unit2 = new Uint256(Convert.toWei(transaction.getAmount(), Unit.ETHER).toBigInteger()); // amount
		sb.append(TypeEncoder.encode(unit2));

		Uint256 unit3 = new Uint256(Convert.toWei(transaction.getFee(), Unit.ETHER).toBigInteger()); // fee
		sb.append(TypeEncoder.encode(unit3));

		Uint256 unit1 = new Uint256(new BigInteger(transaction.getNonce())); // nonce
		sb.append(TypeEncoder.encode(unit1));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("transaction:{}", transaction);
			LOGGER.debug("beforeString:{}", sb.toString());
		}
		return Hash.sha3(sb.toString());
	}

}
