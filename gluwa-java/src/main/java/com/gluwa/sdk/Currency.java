package com.gluwa.sdk;

public enum Currency {
	BTC(false),
	GCRE(false),
	USDCG(false),
	sUSDCG(true),
	NGNG(false),
	sNGNG(false);

	private boolean shorWeiDecimals;

	Currency(boolean b) {
		this.shorWeiDecimals = b;
	}

	public boolean isShorWeiDecimals() {
		return this.shorWeiDecimals;
	}
}
