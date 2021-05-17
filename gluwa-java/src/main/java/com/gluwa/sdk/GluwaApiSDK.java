package com.gluwa.sdk;

public interface GluwaApiSDK {
	
	/**
	 * https://docs.gluwa.com/api/qr-code#post-v-1-qrcode
	 * 
	 * @param transaction parameter
	 * @return GluwaResponse
	 */
	public GluwaResponse getPaymentQRCode(GluwaTransaction transaction);
	
	/**
	 * https://docs.gluwa.com/api/balance#get-v-1-currency-addresses-address
	 * 
	 * @param transaction parameter
	 * @return GluwaResponse
	 */
	public GluwaResponse getAddresses(GluwaTransaction transaction);
	
	/**
	 * https://docs.gluwa.com/api/fee#get-v-1-currency-fee
	 * @param transaction
	 * @return
	 */
	public GluwaResponse getFee(GluwaTransaction transaction);
	
	/**
	 * https://docs.gluwa.com/api/transaction#get-v-1-currency-addresses-address-transactions
	 * 
	 * @param transaction
	 * @return
	 */
	public GluwaResponse getListTransactionHistory(GluwaTransaction transaction) ;
	
	
	/**
	 * https://docs.gluwa.com/api/transaction#get-v-1-currency-transactions-txnhash
	 * 
	 * @param transaction
	 * @return GluwaResponse
	 */
	public GluwaResponse getListTransactionDetail(GluwaTransaction transaction);
	
	/**
	 * https://docs.gluwa.com/api/transaction#post-v-1-transactions
	 * 
	 * @param transaction
	 * @return GluwaResponse
	 */
	public GluwaResponse postTransaction(GluwaTransaction transaction);
	
	
	/**
	 * https://docs.gluwa.com/exchange-webhook#the-exchange-request
	 * 
	 * @param payload
	 * @param signature
	 * @return true if success
	 */
	public boolean validateWebhook(String payload, String signature) ;
	
}
