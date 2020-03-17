package server;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class ServerCenter {
	private ArrayList<ServerChat> sList = new ArrayList<>();
	
	public void addServerChat(ServerChat s) {
		this.sList.add(s);
	}
	
	
	//전체 보내기
	public void reMsg(String msg, String id) {
		String msgCut= msg.substring(2);
		sendAll("[ "+id+" ] "+msgCut);
	}
	
	
	public void sendAll(String msg) {
		for(int i=0; i < sList.size(); i++) {
			sList.get(i).sendTypeAll(msg);
		}
	}
		
		
		
		
	//귓속말 보내기
	public void reMsg2(String msg, String id, String wId) {
		String msgCut= msg.substring(msg.lastIndexOf(wId)+wId.length()+1);
		sendWhisper("[ "+id+" ] >> [ "+wId+" ] "+msgCut);
		System.out.println(id+"가  "+wId+"에게 귓속말을 함 ");
	}
	


	public void sendWhisper(String msg) {	
		StringTokenizer st = new StringTokenizer(msg);
		for(int i = 0; i < 6; i++) {
			String token = st.nextToken(" ");
			if(i==5) {
				String wId = token;
				for(int j = 0; j < sList.size(); j++) {
					if(sList.get(j).getID().equals(wId)) {
						sList.get(j).sendTypeWhisper(msg);
						break;
					}
					
				}
			}
		}

	}

}
