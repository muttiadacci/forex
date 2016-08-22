package com.gain.socket;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.gain.common.Constants;
import com.gain.common.GainUtils;
import com.gain.common.PairRate;

import WebServices.com.GainCapital.www.AuthenticationResult;
import WebServices.com.GainCapital.www.AuthenticationServiceSoapProxy;


public class SocketClient {

	private Socket socket;

	private OutputStream outToServer;
	private DataOutputStream out;
	private InputStream inFromServer;
	private DataInputStream in;

	public SocketClient(String serverName, int port) throws IOException {
		super();
		this.socket = new Socket(serverName, port);

		outToServer = this.getSocket().getOutputStream();
		out = new DataOutputStream(outToServer);

		inFromServer = this.getSocket().getInputStream();
		in = new DataInputStream(inFromServer);

	}

	public Socket getSocket() {
		return socket;
	}

	public void CloseSocket() throws IOException {
		this.getSocket().close();
	}

	public DataOutputStream getOut() {
		return out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public List<PairRate> ProcessSMessage(String msg) {

		List<PairRate> rates = new ArrayList<PairRate>();
		msg = msg.substring(1);

		String[] rateList = msg.split("[$]");

		for (String rate : rateList) {

			String[] rateData = rate.split("\\\\");

			PairRate ratePair = new PairRate();

			if (rateData.length > 9) {

				ratePair.setKey(rateData[0]);
				ratePair.setCurrencyPair(rateData[1]);
				ratePair.setBid(new BigDecimal(rateData[2]));
				ratePair.setAsk(new BigDecimal(rateData[3]));
				ratePair.setHigh(new BigDecimal(rateData[4]));
				ratePair.setLow(new BigDecimal(rateData[5]));
				ratePair.setDealable(rateData[6].charAt(0));
				ratePair.setDomain(rateData[7].charAt(0));
				ratePair.setDecimals(Integer.parseInt(rateData[8]));
				ratePair.setClose(new BigDecimal(rateData[9]));
				ratePair.setLastUpdated(ZonedDateTime.now(ZoneId.of("America/New_York")));

				rates.add(ratePair);
			}

		}
		return rates;

	}

	public List<PairRate> ProcessRMessage(String msg) {

		List<PairRate> rates = new ArrayList<PairRate>();
		msg = msg.substring(1);
		
		String[] rateList = msg.split("R");

		for (String rate : rateList) {

			String[] rateData = rate.split("\\\\");

			PairRate ratePair = new PairRate();

			
			if (rateData.length >= 7) {

				ratePair.setKey(rateData[0]);
				ratePair.setBid(new BigDecimal(rateData[1]));
				ratePair.setAsk(new BigDecimal(rateData[2]));
				ratePair.setDealable(rateData[3].charAt(0));
				
				ratePair.setLastUpdated( ZonedDateTime.parse(rateData[6]+" America/New_York", DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));

				// fill in the blank fields by dummy values
				ratePair.setHigh(new BigDecimal(0));
				ratePair.setLow(new BigDecimal(0));
				ratePair.setDomain('x');
				ratePair.setDecimals(0);
				ratePair.setClose(new BigDecimal(0));
				
				rates.add(ratePair);
			}

		}
		return rates;

	}

}
