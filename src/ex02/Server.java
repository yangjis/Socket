package ex02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public void serverSetting() {
		try {
			serverSocket = new ServerSocket(10002);
			System.out.println("서버 생성");
			clientSocket = serverSocket.accept();
			System.out.println("클라이언트 소켓 연결");
			
			
		} catch (IOException e) {}
		
	}
	
	public void streamSetting() {
		try {
			dataInputStream = new DataInputStream(clientSocket.getInputStream());
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {}
	}
	
	public void dataRecv() {
		new Thread(new Runnable() {
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread) {
					try {
						String recvData = dataInputStream.readUTF();
						
						if(recvData.equals("/exit")) {
							isThread = false;
						}
						else { 
							System.out.println("상대방: " + recvData);
						}
					}catch(Exception e){}
				}
			}
			
		}).start();
	}
	
	public void dataSend() {
		new Thread(new Runnable() {
			Scanner scan = new Scanner(System.in);
			boolean isThread = true;
			@Override
			public void run() {
				while(isThread) {
					try {
						String sendData = scan.nextLine();
						
						if(sendData.equals("/exit")) {
							isThread = false;
						}	
						else {
							dataOutputStream.writeUTF(sendData);
						}
					} catch(Exception e) {}
				}
			}
		}).start();
	}
	
	public void closeAll() {
		try {
			serverSocket.close();
			clientSocket.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (IOException e) {}
	}
	
	public Server() {
		serverSetting();
		streamSetting();
		dataRecv();
		dataSend();
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
