package ex01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public void serverSetting() {
		try {
			serverSocket = new ServerSocket(10002);
			clientSocket = serverSocket.accept();
			System.out.println("클라이언트 소켓 연결");
			
			
			dataInputStream = new DataInputStream(clientSocket.getInputStream());
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			
			String recvData = dataInputStream.readUTF();
			System.out.println(recvData);
			dataOutputStream.writeUTF("잘받앗어요");
			
		} catch (IOException e) {}
		
	}
	
	public void closeAll() {
		try {
			System.out.println("연결끊어요");
			serverSocket.close();
			clientSocket.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (IOException e) {}
	}
	
	public Server() {
		serverSetting();
		closeAll();
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
