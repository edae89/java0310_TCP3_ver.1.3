package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private String wId = null;
	private Scanner in = new Scanner(System.in);

	ClientChat(Socket c) {
		this.withServer = c;
		streamSet();
		receive();
		send();
	}
	
	private void receive() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("receive start~~");
					while(true) {
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						System.out.println(msg);
						
					}
				} catch (Exception e) {
					System.out.println("client receive end !!!");
					return;
				}
			}
		}).start();
		
	}


	private void send() {
		new Thread(new Runnable() {
			
			

			@Override
			public void run() {
				try {
					System.out.println("send start~~");
					while(true) {
						System.out.println("�޽��� Ÿ���� �����ϼ���( ��ü = /n, �ӼӸ� = /w )>");
						String msgType = in.nextLine();
						System.out.println("�޽����� �Է��ϼ���>");
						String reMsg = in.nextLine();
						if(msgType.equals("/n")) {
							String totalMsg = msgType+" "+reMsg;
							sendMsg = withServer.getOutputStream();
							sendMsg.write(totalMsg.getBytes());
							
						}else if(msgType.equals("/w")) {
							System.out.println("�޽����� ���� ���̵� �Է��ϼ���>");
							wId = in.nextLine();
							System.out.println(wId);
							String totalMsg = msgType+" "+wId+" "+reMsg;
							sendMsg = withServer.getOutputStream();
							sendMsg.write(totalMsg.getBytes());
							
						}
						
					}
				} catch (Exception e) {
					System.out.println("client send end !!!");
					return;
				}
			}

		}).start();
	}
	

	
	private void streamSet() {
		try {
			System.out.println("ID�� �Է��ϼ��� >");
			id = in.nextLine();
			sendMsg = withServer.getOutputStream();
			sendMsg.write(id.getBytes());
			
			
			reMsg = withServer.getInputStream();
			byte[] reBuffer = new byte[100];
			reMsg.read(reBuffer);
			String msg = new String(reBuffer);
			msg = msg.trim();
			System.out.println(msg);
			
			
		} catch (Exception e) {
		
		}
	}
}
