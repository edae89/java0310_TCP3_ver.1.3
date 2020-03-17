package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerChat extends Thread {

	private Socket withClient = null;
	private ServerCenter sc = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private String wId = null;
	
	
	ServerChat(Socket c, ServerCenter s) {
		this.withClient = c;
		this.sc = s;
		
	}
	
	public String getID() {
		return id;
	}
	
	public String getWId() {
		return wId;
	}
	
	@Override
	public void run() {
		streamSet();
		receive();
	}

	private void receive() {	
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("receive start~~");
					while (true) {
				
						reMsg = withClient.getInputStream();// 메시지타입+메시지
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						if(msg.contains("/n")) {		//전체 메시지
							sc.reMsg(msg, id);
							
						}else if(msg.contains("/w")) {	//귓속말
							StringTokenizer st = new StringTokenizer(msg);
							for(int i = 0; i < 3; i++) {
								String token = st.nextToken();
								
								if(i==1) {
									wId = token;
									break;
								}
							}
							sc.reMsg2(msg, id, wId);
						}
						
					}
				} catch (Exception e) {
					System.out.println("receive End");
					return;
				}
			}
		}).start();

	}

	public void sendTypeAll(String reMsg) {
		try {
			sendMsg = withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());

		} catch (Exception e) {
			return;
		}
	}
	
	
	public void sendTypeWhisper(String reMsg) {
		try {
			sendMsg = withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());

		} catch (Exception e) {
			return;
		}
		
		
	}
	
	

	

	private void streamSet() {
		try {
			reMsg = withClient.getInputStream();
			byte[] reBuffer = new byte[100];
			reMsg.read(reBuffer);
			id = new String(reBuffer);
			id = id.trim();

			InetAddress c_ip = withClient.getInetAddress();
			String ip = c_ip.getHostAddress();
			int port = withClient.getPort();
			System.out.println(id + " (" + ip + ")"+port);

			String reMsg = "접속 확인";
			sendMsg = withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());

		} catch (Exception e) {
		}
	}

	

}
