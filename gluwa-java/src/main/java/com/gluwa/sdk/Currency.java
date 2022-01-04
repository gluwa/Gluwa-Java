package com.gluwa.sdk;

public enum Currency {
	GCRE(false), KRWG(false), USDCG(false), BTC(false), NGNG(false), sUSDCG(true), sKRWCG(false), sNGNG(false);

	private boolean shorWeiDecimals;

	Currency(boolean b) {
		this.shorWeiDecimals = b;
	}

	public boolean isShorWeiDecimals() {
		return this.shorWeiDecimals;
	}
}
