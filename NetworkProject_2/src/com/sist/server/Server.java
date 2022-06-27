package com.sist.server;

import java.io.*;	// 통신
import java.util.*;	// StringTokenizer

import com.sist.common.Function;
import com.sist.server.Server.Client;

import java.net.*;	// 네트워크

/*
 * 	================
 * 		접속 담당		==> 교환 소켓 ==> 1개 / 접속시 클라이언트 정보 (IP, PORT)
 * 	================
 * 		통신 담당		==> 통신 소켓 ==> 클라이언트 개수만큼
 * 	================			   --------------- 쓰레드
 * 	=> 클라이언트 정보 공유 => 내부 클래스(멤버 클래스)
 * 	class Server {
 * 		Vector
 * 		class Client {
 * 		}
 * 	}
 * 
 */

public class Server implements Runnable {
	
	private ServerSocket ss;	// 접속 담당 소켓
	private final int PORT = 3355; // 0~65535 => (0~1023 : 사용 중)
	private Vector<Client> waitVc = 
							new Vector<Client>(); // 클라이언트 정보 저장 (IP, PORT, ID, ...)
	
	// 서버 구동
	public Server() {
		try {
			
			ss = new ServerSocket(PORT);
			System.out.println("Server Start...");
			// => 사이트 (Spring) / 채팅 (NodeJS)
		} catch(Exception ex) {}
	}
	
	public void run() {
		// 클라이언트 점속시마다 처리
		try {
			while (true) {
				Socket s = ss.accept(); // 클라이언트가 접속시 호출
			// 클라이언트에 대한 정보 (IP, PORT => Socket)
			// s는 클라이언트 정보 
			// s => Thread에 전송 후 통신이 가능하게 만든다
				Client client = new Client(s);
				client.start();
			}
		} catch(Exception ex) {}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		new Thread(server).start();
	}
	
	class Client extends Thread {
		// 통신 담당
		String id, name, sex;
		Socket s;
		BufferedReader in;
		OutputStream out;
		
		public Client(Socket s) {
			// 클라이언트와 연결
			try {
				this.s = s;
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
				// 서버 => 클라이언트 정보
				// 클라이언트 => 서버 정보
			} catch(Exception ex) {}
		}
		
		// 실제 통신 => Thread가 동작 => 호출 시에 start()
		public void run() {
			try {
				while(true) {
					// 클라이언트의 요청사항 받기
					String msg = in.readLine();
					
					// 어떤 요구사항인지 확인
					StringTokenizer st = new StringTokenizer(msg, "|");
					int protocol = Integer.parseInt(st.nextToken());
					switch(protocol) {
						case Function.LOGIN: {
							id = st.nextToken();
							name = st.nextToken();
							sex = st.nextToken();
							
							// 접속된 모든 사람에게 전송
							// 로그인 된 사람
							messageAll(Function.LOGIN+"|"+id+"|"+name+"|"+sex);
							
							// 저장
							waitVc.add(this);
							
							// 로그인 하는 사람
							messageTo(Function.MYLOG+"|"+name);
							for(Client client:waitVc) {
								messageTo(Function.LOGIN+"|"
										+client.id+"|"
										+client.name+"|"
										+client.sex);
							}
						}
							break;
						case Function.CHAT: {
							messageAll(Function.CHAT+"|["+name+"]"+st.nextToken());
						}
							break;
						case Function.SEND:
							break;
						case Function.END: {
							messageAll(Function.END+"|"+id);
							for (int i=0; i<waitVc.size(); i++) {
								Client c = waitVc.get(i);
								if (id.equals(c.id)) {
									messageTo(Function.MYEND+"|");
									waitVc.remove(i);
									in.close();
									out.close();
									break;
								}
							}
						}
							break;
					}
				}
			} catch(Exception ex) {}
		}
		
		// 개인에게 전송
		public void messageTo(String msg) {
			try {
				out.write((msg+"\n").getBytes());
			} catch(Exception ex) {}
		}
		
		// 전체 접속자에게 전송
		public void messageAll(String msg) {
			try {
				for (Client client:waitVc) {
					client.messageTo(msg);
				}
			} catch(Exception ex) {}
		}
	}

}
