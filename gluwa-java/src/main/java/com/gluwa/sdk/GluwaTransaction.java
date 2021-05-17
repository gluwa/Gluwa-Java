package com.gluwa.sdk;

import java.io.Serializable;
import java.util.UUID;

public class GluwaTransaction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5510883158387021589L;
	Currency currency;
	String amount;
	String fee;
	String merchantOrderID;
	String note = "";
	int expiry;
	String targetAddress;
	String nonce;
	UUID idem;
	
	int limit = 100;
	String status = "Confirmed";
	int offset = 0;
	String txnHash;
	
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
	public int getExpiry() {
		return expiry;
	}
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMerchantOrderID() {
		return merchantOrderID;
	}
	public void setMerchantOrderID(String merchantOrderID) {
		this.merchantOrderID = merchantOrderID;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getTxnHash() {
		return txnHash;
	}
	public void setTxnHash(String txnHash) {
		this.txnHash = txnHash;
	}
	
	public UUID getIdem() {
		return idem;
	}
	public void setIdem(UUID idem) {
		this.idem = idem;
	}
	@Override
	public String toString() {
		return "GluwaTransaction [currency=" + currency + ", amount=" + amount + ", fee=" + fee + ", merchantOrderID="
				+ merchantOrderID + ", note=" + note + ", expiry=" + expiry + ", targetAddress=" + targetAddress
				+ ", nonce=" + nonce + ", limit=" + limit + ", status=" + status + ", offset=" + offset + ", txnHash="
				+ txnHash + "]";
	}
	
	
}
