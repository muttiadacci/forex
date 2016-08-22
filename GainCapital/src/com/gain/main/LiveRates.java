package com.gain.main;

import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.gain.common.Constants;
import com.gain.common.PairRate;
import com.gain.socket.SocketClient;

import WebServices.com.GainCapital.www.AuthenticationResult;
import WebServices.com.GainCapital.www.AuthenticationServiceSoapProxy;

public class LiveRates {

	HashMap<String,PairRate> rateTable;
		
	public LiveRates(){
		super();
		rateTable = new HashMap<String,PairRate>();
	}
	
	public void UpdateRates(List<PairRate> rates){
		
		for(PairRate r :rates){
			String key = r.getKey();
	
			if(rateTable.containsKey(key)){
	
				rateTable.get(key).setAsk(r.getAsk());
				rateTable.get(key).setBid(r.getBid());
				rateTable.get(key).setDealable(r.getDealable());
				rateTable.get(key).setLastUpdated(r.getLastUpdated());
				if(r.getBid().compareTo(rateTable.get(key).getLow()) < 0) rateTable.get(key).setLow(r.getBid());
				if(r.getAsk().compareTo(rateTable.get(key).getHigh()) > 0) rateTable.get(key).setHigh(r.getAsk());
			
			}
			else {
				rateTable.put(key,new PairRate(r));
			}
			
		}
		
		return;
	}

	public void PublishRates(){
		for(PairRate r:rateTable.values()){
			System.out.println(r.ToString());
		}		
	}
	public static void main(String[] args) {

		try {
			
			LiveRates LiveRatesTable = new LiveRates();
			
			SocketClient client = new SocketClient(Constants.SOCKET_SERVER, Constants.SOCKET_SERVER_PORT);

			// check authentication token validity

			AuthenticationServiceSoapProxy proxy = new AuthenticationServiceSoapProxy();

			AuthenticationResult result = proxy.authenticateCredentials("api_madacal@gmail.com", "mustafa");

			String token = result.getToken();

			System.out.println("Authenticated successfully, Token: \n" + token);

//			ConfigurationServiceSoapProxy configProxy = new ConfigurationServiceSoapProxy();
//			ConfigurationSettings settings = configProxy.getConfigurationSettings(token);
//			
//			CollectionOfString userSettingsProductSubscriptionCollection;
//			userSettingsProductSubscriptionCollection.getInput() = "";
//			
			
//			configProxy.saveUserProductSubscriptionSetting(token, userSettingsProductSubscriptionCollection);
			
			// test validity of Token

			
			
			// Connection to Server
			System.out.println("Connecting to " + Constants.SOCKET_SERVER + " on port " + Constants.SOCKET_SERVER_PORT);

			System.out.println("Just connected to " + client.getSocket().getRemoteSocketAddress());

			String authMsg = token + "\r";

			client.getOut().write(authMsg.getBytes());

			System.out.println("Message sent.. ");

			byte byteRead = -1;

			try {

				while (true) {

					String servermsg = "";
					int bytesRead = 0;

					byteRead = client.getIn().readByte();
					bytesRead++;

					while (byteRead != -1 && byteRead != 13) {

						servermsg += (char) byteRead;
						byteRead = client.getIn().readByte();
						bytesRead++;

					}

					System.out.println("# of bytes : " + bytesRead + " Msg : " + servermsg);

					if (bytesRead > 1) {

						char messageType = servermsg.charAt(0);

						if (messageType == 'S') {

							List<PairRate> rates = client.ProcessSMessage(servermsg);

							LiveRatesTable.UpdateRates(rates);
							LiveRatesTable.PublishRates();

						}
						
						else if(messageType == 'R'){
							
							List<PairRate> rates = client.ProcessRMessage(servermsg);

							LiveRatesTable.UpdateRates(rates);
							LiveRatesTable.PublishRates();
						}
						else {
							System.out.println("Unknown message received : " + servermsg);
						}
						

					}
					
					else {
						System.out.println("Server returned one byte : " + byteRead);

					}

				}
			} catch (EOFException e) {

				System.out.print("End of file...");
			}

			client.CloseSocket();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
