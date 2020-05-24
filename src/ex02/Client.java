package ex02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public void connect() {
		try {
			System.out.println("접속시도");
			clientSocket = new Socket("로컬호스트입력", 10002);
			System.out.println("접속완료");
			
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
						if(sendData.equals("/exit"))
							isThread = false;
						else { 
							dataOutputStream.writeUTF(sendData);
						}
					} catch(Exception e) {}
				}
			}
		}).start();
	}
	
	public Client() {
		connect();
		streamSetting();
		dataSend();
		dataRecv();
	}
	
	public static void main(String[] args) {	
		new Client();
	}

}
