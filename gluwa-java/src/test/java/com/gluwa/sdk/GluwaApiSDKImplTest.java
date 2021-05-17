package com.gluwa.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.utils.Numeric;

public class GluwaApiSDKImplTest {

	static final Logger LOGGER = LoggerFactory.getLogger(GluwaApiSDKImplTest.class);

	@Test
	public void getPaymentQRCode_Test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl sdkImpl = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDG);
		transaction.setAmount("11");
		transaction.setExpiry(1800);

		GluwaResponse result = sdkImpl.getPaymentQRCode(transaction);
		assertEquals("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", result.getBody());
	}

	@Test
	public void getAddresses_Test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDG);

		GluwaResponse result = wrapper.getAddresses(transaction);
		assertNotNull(result.getMapList().get(0));
	}

	@Test
	public void getFee_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDG);

		GluwaResponse result = wrapper.getFee(transaction);
		assertEquals(200, result.getCode());
	}

	//@Ignore
	@Test
	public void postTransaction_test() {

		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(new ConfigurationForTest());
		GluwaTransaction transaction = new GluwaTransaction();

		transaction.setCurrency(Currency.USDG);
		transaction.setAmount("11");
		transaction.setTargetAddress("0xF72D698dA19446fB368eFb6e70B591e6649649b1");
		transaction.setNote("This is For Test");
		transaction.setMerchantOrderID("My Order Id:20200101");
		transaction.setIdem(UUID.randomUUID());
//		transaction.setPaymentID();
//		transaction.setPaymentSig();
		
		

		GluwaResponse result = wrapper.postTransaction(transaction);
		assertEquals(202, result.getCode());
	}

	@Test
	public void getListTransactionHistory_test() {

		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDG);
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
		transaction.setCurrency(Currency.USDG);
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
		transaction.setCurrency(Currency.USDG);
		transaction.setTxnHash("0xeb50ea01a1906c2d6eab041484708f4fff50a7454123f5d349bbc21c29a25ab8");

		GluwaResponse result = wrapper.getListTransactionDetail(transaction);
		assertNotNull(result.getMapList().get(0));
	}

	@Test
	public void validateWebhook_test() {

		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);

		boolean result = wrapper.validateWebhook(
				"{\"Data\":{\"MerchantOrderID\":\"250\",\"Amount\":\"13.193539603960396039\",\"EventType\":\"TransactionCreated\",\"Type\":\"Webhook\",\"ResourceID\":\"0xb917c93eed1bcca12ed81b34813d9d8cb9310bfd3c1c8888798144a57f4b8211\"}}",
				"B6lWqXYF5pO7XyOg/LOUlIfzAqfP59MFbFat/adwGF8=");
		assertTrue(result);
	}

	@Test
	public void validateWebhook_test_fail() {

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

		assertEquals(
				"MTU5Njk1NzkxNi4weDcxYWNjMmYzYWQ0YWUwOGQ0ZjBjMTA0Mjk3MDI4NTM0ZDc5NDlkMzNlMmE2YmI4YjY1NWZiN2M4NzI1OTQ0ZDgyNjhiMGRhNTBjYzBmYjNmY2RmMjM5NDllNThkOWNhMzNlODM3YmJiMmZjOTcxMGFlYzY0ZGU3ZjQ2ZDI4YjE4MWI=",
				sing);

	}

	@Test
	public void transactionHash_test() {
		Configuration conf = new ConfigurationForTest();
		GluwaApiSDKImpl wrapper = new GluwaApiSDKImpl(conf);
		GluwaTransaction transaction = new GluwaTransaction();
		transaction.setCurrency(Currency.USDG);
		transaction.setAmount("12");
		transaction.setTargetAddress("0xf04349B4A760F5Aed02131e0dAA9bB99a1d1d1e5");
		transaction.setFee("3.643646");
		transaction.setNonce("1597560460");

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
		transaction.setCurrency(Currency.USDG);
		transaction.setAmount("12");
		transaction.setTargetAddress("0xf04349B4A760F5Aed02131e0dAA9bB99a1d1d1e5");
		transaction.setFee("3.643646");
		transaction.setNonce("1597560460");

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

}
