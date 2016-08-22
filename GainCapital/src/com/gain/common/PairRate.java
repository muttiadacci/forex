package com.gain.common;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class PairRate {

	String Key;
	String CurrencyPair;
	BigDecimal Bid;
	BigDecimal Ask;
	BigDecimal High;
	BigDecimal Low;
	char Dealable;
	char Domain;
	int Decimals;
	BigDecimal Close;
	ZonedDateTime LastUpdated;
	
	
	
	public PairRate(PairRate r) {
		super();
		this.setKey(r.getKey());
		this.setCurrencyPair(r.getCurrencyPair());
		this.setBid(r.getBid());
		this.setAsk(r.getAsk());
		this.setHigh(r.getHigh());
		this.setLow(r.getLow());
		this.setDealable(r.getDealable());
		this.setDomain(r.getDomain());
		this.setDecimals(r.getDecimals());
		this.setClose(r.getClose());
		this.setLastUpdated(r.getLastUpdated());		
	}
	public ZonedDateTime getLastUpdated() {
		return LastUpdated;
	}
	public void setLastUpdated(ZonedDateTime lastUpdated) {
		LastUpdated = lastUpdated;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public PairRate() {
		super();
	}
	public String getCurrencyPair() {
		return CurrencyPair;
	}
	public void setCurrencyPair(String currencyPair) {
		CurrencyPair = currencyPair;
	}
	public BigDecimal getBid() {
		return Bid;
	}
	public void setBid(BigDecimal bid) {
		Bid = bid;
	}
	public BigDecimal getAsk() {
		return Ask;
	}
	public void setAsk(BigDecimal ask) {
		Ask = ask;
	}
	public BigDecimal getHigh() {
		return High;
	}
	public void setHigh(BigDecimal high) {
		High = high;
	}
	public BigDecimal getLow() {
		return Low;
	}
	public void setLow(BigDecimal low) {
		Low = low;
	}
	public char getDealable() {
		return Dealable;
	}
	public void setDealable(char dealable) {
		Dealable = dealable;
	}
	public char getDomain() {
		return Domain;
	}
	public void setDomain(char domain) {
		Domain = domain;
	}
	public int getDecimals() {
		return Decimals;
	}
	public void setDecimals(int decimals) {
		Decimals = decimals;
	}
	public BigDecimal getClose() {
		return Close;
	}
	public void setClose(BigDecimal close) {
		Close = close;
	}
	
	public String ToString(){
		String result = "Pair ";
		
		result += "Key: " + this.getKey();
		result += " CurrencyPair: " + this.getCurrencyPair();
		result += " Bid: " + this.getBid().toString();
		result += " Ask: " + this.getAsk().toString();
		result += " High: " + this.getHigh().toString();
		result += " Low: " + this.getLow().toString();
		result += " Dealable: " + this.getDealable();
		result += " Domain: " + this.getDomain();
		result += " Decimals: " + this.getDecimals();
		result += " Close: " + this.getClose().toString();
		result += " Updated: " + this.getLastUpdated().toString();
		
		return result;
	}
	
	

}
