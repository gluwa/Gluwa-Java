package com.gluwa.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// import java.util.UUID;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Cucumber.class)
@CucumberOptions(
		publish = true,
		plugin = {"pretty", "html:target/cucumber/report.html", "json:target/cucumber/report.json"},
		features = "src/test/resources/features",
		glue = {"definitions", "com.gluwa.sdk"},
		tags = "@gluwaSdk" // same as VM option -Dcucumber.options="--tags @gluwaSdk"
)
public class GluwaApiSDKImplTest {

	static final Logger LOGGER = LoggerFactory.getLogger(GluwaApiSDKImplTest.class);

	@BeforeClass
	public static void setup() {
		//System.out.println("BeforeAll");
	}

	/*@Test
	public void getPaymentQRCode_Test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl sdkImpl = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setAmount("51");
		transaction.setExpiry(1800);

		GluwaResponse result = sdkImpl.getPaymentQRCode(transaction);
		System.out.println(result.getCode());
		System.out.println(result.getBody());
		assertNotNull(result);
	}*/

	@AfterClass
	public static void teardown() {
		//System.out.println("AfterAll");
	}
}
/*public class GluwaApiSDKImplTest {
	static final Logger LOGGER = LoggerFactory.getLogger(GluwaApiSDKImplTest.class);

	@Test
	public void getPaymentQRCode_Test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl sdkImpl = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setAmount("51");
		transaction.setExpiry(1800);

		GluwaResponse result = sdkImpl.getPaymentQRCode(transaction);
		assertNotNull(result);
	}

	// @Test
	// public void getPaymentQRCodeWithPayload_Test() {
	// 	Configuration conf = new ConfigurationForTest();
	// 	GluwaApiSDKImpl sdkImpl = new GluwaApiSDKImpl(conf);

	// 	GluwaTransaction transaction = new GluwaTransaction();
	// 	transaction.setCurrency(Currency.USDCG);
	// 	transaction.setAmount("51");
	// 	transaction.setExpiry(1800);

	//  // getPaymentQRCode API returns QR code png image as a Base64 string and payload.
	// 	GluwaResponse result = sdkImpl.getPaymentQRCodeWithPayload(transaction);
	// 	assertNotNull(result);
	// }

	@Test
	public void getAddresses_Test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);

		GluwaResponse result = wrapper.getAddresses(transaction);
		assertNotNull(result.getMapList().get(0));
	}

	@Test
	public void getFee_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setAmount("51");

		GluwaResponse result = wrapper.getFee(transaction);
		assertEquals(200, result.getCode());
	}

	// //@Ignore
	// @Test
	// public void postTransaction_test() {
	// 	GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(new ConfigurationForTest());
	// 	GluwaTransaction transaction = new GluwaTransaction();

	// 	// transaction.setCurrency(Currency.USDCG);
	// 	transaction.setCurrency(Currency.sUSDCG);
	// 	// transaction.setCurrency(Currency.sNGNG);
	// 	transaction.setAmount("20");
	// 	transaction.setTargetAddress("0x6ED043F0018396ce7F8f4B8423F69903d631A9de");
	// 	// transaction.setNote("This is For Test");
	// 	// transaction.setMerchantOrderID("My Order Id:20200101");
	// 	// transaction.setIdem(UUID.randomUUID());

	// 	GluwaResponse result = wrapper.postTransaction(transaction);
	// 	assertEquals(202, result.getCode());
	// }

	@Test
	public void getListTransactionHistory_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setLimit(2);
		transaction.setStatus("Confirmed");
		transaction.setOffset(0);

		GluwaResponse result = wrapper.getListTransactionHistory(transaction);
		assertNotNull(result.getMapList());
	}

	@Test
	public void getListTransactionHistoryIncomplete_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setLimit(2);
		transaction.setStatus("Incomplete");
		transaction.setOffset(0);

		GluwaResponse result = wrapper.getListTransactionHistory(transaction);
		assertNotNull(result.getMapList());
	}

	@Test
	public void getListTransactionDetail_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		// transaction.setTxnHash("0x115807d96828fb8de831214b66daf2cccba8b52d6e9121cf55491ecfb1a2f739");
		transaction.setTxnHash("0x576f3b54ab300882901ed4bbe544ac5a1957aa1db8ba05d55d96e69bc2613628");

		GluwaResponse result = wrapper.getListTransactionDetail(transaction);
		assertNotNull(result.getMapList().get(0));
	}

	@Test
	public void validateWebhook_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		// boolean result = wrapper.validateWebhook(
		// 		"{\"Data\":{\"MerchantOrderID\":\"250\",\"Amount\":\"13.193539603960396039\",\"EventType\":\"TransactionCreated\",\"Type\":\"Webhook\",\"ResourceID\":\"0xb917c93eed1bcca12ed81b34813d9d8cb9310bfd3c1c8888798144a57f4b8211\"}}",
		// 		"B6lWqXYF5pO7XyOg/LOUlIfzAqfP59MFbFat/adwGF8=");

		boolean result = wrapper.validateWebhook(
				"{\"ID\":\"62c5ff11-1102-45bf-aece-7663a40d228d\",\"CreatedDateTime\":\"2021-02-21T04:17:50.7255568Z\",\"ResourceType\":\"Transaction\",\"EventName\":\"TRANSACTION.CREATED\",\"Summary\":\"A transaction was created.\",\"Resource\":{\"ID\":\"00a41d80-0cce-45a6-847e-5d1c4cd08773\",\"TxHash\":\"0xbeb783c68c80df8971e40cc3f1fb933c50dfa8de3a58ea381ffc3a74d8a46a76\",\"Source\":\"0xf04349B4A760F5Aed02131e0dAA9bB99a1d1d1e5\",\"Target\":\"0x084Af3876A220F4732e21F7617dc212BB2A1f32E\",\"Amount\":\"10\",\"Fee\":\"0.5\",\"Currency\":\"sUSDCG\",\"Status\":\"Confirmed\"}}",
				"sXW5uGxKBFyAzrrjrO+GtsxUSwF5bOK2QyigO5Ut/9Y=");
		assertTrue(result);
	}

	@Test
	public void validateWebhook_fail_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		boolean result = wrapper.validateWebhook(
				"{\"Data\":{\"MerchantOrderID\":\"250\",\"Amount\":\"13.193539603960396039\",\"EventType\":\"TransactionConfirmed\",\"Type\":\"Webhook\",\"ResourceID\":\"0xb917c93eed1bcca12ed81b34813d9d8cb9310bfd3c1c8888798144a57f4b8211\"}}",
				"B9lWeXYF5pO7XyOg/LOUl1fzAqfP59M4bFat/a5wGF2=");
		assertFalse(result);
	}

	@Test
	public void timestampSignature_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaApiSDKImpl spy = Mockito.spy(wrapper);

		Mockito.doReturn("1596957916").when(spy).timestamp();

		String sing = spy.timestampSignature();

		LOGGER.debug("\n\n\n\n\n\nsing:{}\n\n\n\n", sing);

		assertEquals(
			"MTU5Njk1NzkxNi4weDU5MzhhOTVkYjgxZTVjZGVkMzljOWFhNWUzMTYxZjNkNWQzODMwODcyZDU3ZTE3YjAzZWZmMTY4NTUwYzg2MWU2YmMxMmIwY2NlMjMxMzkzNzQ2ZGU3ZTJmYTNlMmQzOTQ3YzM4OWE3MjcxNjA2ZDJjZGU0N2FmYzk1OTI1YWU3MWM=",
			sing
		);
	}

	@Test
	public void transactionHash_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);
		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.sUSDCG);
		transaction.setAmount("20");
		transaction.setTargetAddress("0x6ED043F0018396ce7F8f4B8423F69903d631A9de");
		transaction.setFee("3.643646"); // NOTE
		transaction.setNonce("1597560460"); // NOTE

		String result = wrapper.hashTransaction(transaction);

		LOGGER.debug("String:{}", result);
		byte[] b = Numeric.hexStringToByteArray(result);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i] & 0xff).append(" ");
		}
		LOGGER.debug("bytes:{}", sb.toString());
	}

	@Test
	public void signMessage_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);
		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDCG);
		transaction.setAmount("20");
		transaction.setTargetAddress("0x6ED043F0018396ce7F8f4B8423F69903d631A9de");
		transaction.setFee("3.643646"); // NOTE
		transaction.setNonce("1597560460"); // NOTE

		String message = wrapper.hashTransaction(transaction);

		LOGGER.debug("String:{}" , message);
		byte[] b = Numeric.hexStringToByteArray(message);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i] & 0xff).append(" ");
		}
		LOGGER.debug("bytes:{}", sb.toString());
		String signature = wrapper.signMessage(Numeric.hexStringToByteArray(message));
		LOGGER.debug("signature:{}", signature);
	}
}*/
