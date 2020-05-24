package ex01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public void connect() {
		try {
			clientSocket = new Socket("로컬호스트입력.", 10002);
			System.out.println("접속완료");
			dataInputStream = new DataInputStream(clientSocket.getInputStream());
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			
			dataOutputStream.writeUTF("안녕하세요 클라이언트 입니다.");
			
			String recvData = dataInputStream.readUTF();
			System.out.println(recvData);
			
		} catch (IOException e) {}
	}
	
	public Client() {
		connect();
	}
	
	public static void main(String[] args) {	
		new Client();
	}

}
