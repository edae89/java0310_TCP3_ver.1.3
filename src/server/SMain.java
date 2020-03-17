package server;
//전체 채팅과 귓속말을 할 수 있는 프로그램(완)
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class SMain {
	public static void main(String[] args) throws Exception{
		
		ServerSocket serverS = null;
		Socket withClient = null;
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.101",9999));
		
		ArrayList<Socket> cList = new ArrayList<>();
		ServerCenter sc = new ServerCenter();
		while(true) {
			System.out.println("");
			withClient = serverS.accept();
			cList.add(withClient);
			System.out.println(cList);
			System.out.println(withClient.getInetAddress()+"");
			ServerChat s =new ServerChat(withClient,sc);
		
			sc.addServerChat(s);
			s.start();
		
		}
	}

}