package com.gluwa.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

final class GluwaApiService {

	static final Logger LOGGER = LoggerFactory.getLogger(GluwaApiService.class);

	private final static String apiHost = "https://api.gluwa.com";
	private final static String apiHostDev = "https://sandbox.api.gluwa.com";

	protected final static String V1_PATH_QRCODE = "/v1/QRCode";
	protected final static String V1_PATH_QRCODE_PAYLOAD = "/v1/QRCode/payload";
	protected final static String V1_PATH_ADDRESS = "/v1/{Currency}/Addresses/";
	protected final static String V1_PATH_FEE = "/v1/{Currency}/fee?amount={Amount}";
	protected final static String V1_PATH_TRANSACTION = "/v1/Transactions";
	protected final static String V1_PATH_TRANSACTION_HISTORY = "/v1/{Currency}/Addresses/{MasterEthereumAddress}/Transactions";
	protected final static String V1_PATH_TRANSACTION_DETAIL = "/v1/{Currency}/Transactions/";

	private boolean __DEV__ = false;
	ObjectMapper mapper = new ObjectMapper();

	protected enum ParameterType {
		JSON;
	}

	protected GluwaApiService() {
	}

	protected GluwaApiService(boolean __DEV__) {
		this.__DEV__ = __DEV__;
	}

	protected String getApiHost() {
		if (__DEV__)
			return GluwaApiService.apiHostDev;
		else
			return GluwaApiService.apiHost;
	}

	protected String getNetType() {
		if (__DEV__)
			return "Testnet";
		else
			return "Mainnet";
	}

	protected String[] getContractAddressData(Currency currency) {

		try {
			String netType = getNetType();

			String apiURL = "https://api.gluwa.com/V1/Contract/Address/" + currency + "/" + netType;
			URL url = new URL(apiURL);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("path:{}", apiURL);
			}

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			int responseCode = con.getResponseCode();

			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer responseStr = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				responseStr.append(inputLine);
			}
			br.close();

			if (LOGGER.isDebugEnabled())
				LOGGER.debug("response:{}", responseStr);

			JSONObject responseJson = new JSONObject(responseStr.toString());

			return new String[] {
				responseJson.getString("Address"), // Address
				responseJson.getString("Currency"), // Currency
				responseJson.getNumber("Decimals").toString() // Decimals
			};
		} catch (Exception e) {
			System.out.println(e);
			return new String[]{null, null, null};
		}
	}

	protected GluwaResponse get(String path, Header... headers) throws IOException, URISyntaxException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("path:{}", path);
			if (headers != null)
				for (Header header : headers) {
					LOGGER.debug("header:{}", header);
				}
		}
		GluwaResponse response = new GluwaResponse();

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			HttpGet httpGet = new HttpGet(getApiHost() + path);
			httpGet.setHeaders(headers);

			try (CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
				response.setCode(httpResponse.getCode());
				response.setReason(httpResponse.getReasonPhrase());

				HttpEntity httpEntity = httpResponse.getEntity();
				response.setContentType(httpEntity.getContentType());
				InputStream is = httpEntity.getContent();

				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					sb.append(line);

				EntityUtils.consume(httpEntity);

				response.setBody(sb.toString());

				if (httpResponse.getCode() < 200 || httpResponse.getCode() > 300) {
					throw new GluwaSDKException(httpResponse.getReasonPhrase() + " " + response.getBody());
				} else {

					if (response.getBody() != null && response.getBody().length() > 0
							&& response.getContentType().toLowerCase().contains("application/json")) {

						if (sb.charAt(0) != '[') {
							sb.append("]");
							sb.insert(0, "[");
						}

						response.setMapList((List<Map<String, Object>>) mapper.readValue(sb.toString(),
								new TypeReference<List<Map<String, Object>>>() {
								}));
					}
				}
			}

		}
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("response:{}", response);
		return response;
	}

	protected GluwaResponse post(String path, ParameterType paramType, HashMap<String, ?> param, Header... headers)
			throws IOException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("path:{}", path);
			LOGGER.debug("paramType:{}", paramType);
			LOGGER.debug("param:{}", param);
			if (headers != null)
				for (Header header : headers) {
					LOGGER.debug("header:{}", header);
				}
		}

		GluwaResponse response = new GluwaResponse();

		HttpEntity entity;
		if (paramType == ParameterType.JSON) {

			entity = new StringEntity(mapper.writeValueAsString(param), ContentType.APPLICATION_JSON, "UTF-8", false);
		} else {
			List<NameValuePair> nvps = new ArrayList<>();
			for (String key : param.keySet()) {
				nvps.add(new BasicNameValuePair(key, param.get(key).toString()));
			}
			entity = new UrlEncodedFormEntity(nvps);
		}

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			HttpPost httpPost = new HttpPost(getApiHost() + path);
			httpPost.setHeaders(headers);
			httpPost.setEntity(entity);

			try (CloseableHttpResponse httpResponse = httpclient.execute(httpPost)) {
				response.setCode(httpResponse.getCode());
				response.setReason(httpResponse.getReasonPhrase());

				HttpEntity httpEntity = httpResponse.getEntity();
				response.setContentType(httpEntity.getContentType());
				InputStream is = httpEntity.getContent();

				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					sb.append(line);

				EntityUtils.consume(httpEntity);

				response.setBody(sb.toString());

				if (httpResponse.getCode() < 200 || httpResponse.getCode() > 300) {
					throw new GluwaSDKException(httpResponse.getReasonPhrase() + " " + response.getBody());
				} else {

					if (response.getBody() != null && response.getBody().length() > 0
							&& response.getContentType().toLowerCase().contains("application/json")) {

						if (sb.charAt(0) != '[') {
							sb.append("]");
							sb.insert(0, "[");
						}

						response.setMapList((List<Map<String, Object>>) mapper.readValue(sb.toString(),
								new TypeReference<List<Map<String, Object>>>() {
								}));
					}
				}
			}
		}

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("response:{}", response);

		entity.close();
		return response;
	}
}
