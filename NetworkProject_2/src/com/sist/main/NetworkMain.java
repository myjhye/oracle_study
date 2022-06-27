package com.sist.main;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sist.client.ControllerPanel;
import com.sist.client.LoginForm;
import com.sist.client.MenuForm;
import com.sist.client.WaitForm;
import com.sist.common.Function;
import com.sist.data.FoodLocationVO;
import com.sist.data.FoodSystem;

public class NetworkMain extends JFrame implements ActionListener, Runnable{
    MenuForm menu=new MenuForm();
    ControllerPanel cp=new ControllerPanel();
    WaitForm wr=new WaitForm();
    LoginForm lf = new LoginForm();
    int curpage=1;
    int totalpage=0;
    int cno=1;
//    int cno=1;
    Socket s;
    BufferedReader in;	// 쓰레드
    OutputStream out; // 일반 유저
    
    public NetworkMain()
    {
    	setTitle("네트워크 맛집 프로그램");
    	setLayout(null);// 사용자 정의 (직접 배치)
    	menu.setBounds(120, 15, 960, 40);
    	add(menu);
    	
    	cp.setBounds(120, 65, 850, 820);
    	add(cp);
//    	
    	wr.setBounds(980, 15, 250, 700);
    	add(wr);
    	
    	setSize(1250, 900);
//    	setVisible(true);
    	// 종료 
//     	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);    
    	// 이벤트 등록 
    	
    		cp.hf.m.addActionListener(this);
    	
//        for(int i=0;i<cp.hf.m.length;i++)
//        {
//        	cp.hf.m[i].addActionListener(this);
//        }
    	
    	cp.hf.b1.addActionListener(this);// 이전
    	cp.hf.b2.addActionListener(this);// 다음 
    	
    	totalpage=FoodSystem.foodTotalPage();
    	cp.hf.pagLa.setText(curpage+ " page / "+totalpage+" pages");
    	
    	// 1. menu 클릭
    	menu.chatBtn.addActionListener(this);
    	menu.exitBtn.addActionListener(this);
    	menu.homeBtn.addActionListener(this);
    	menu.newsBtn.addActionListener(this);
    	menu.foodBtn.addActionListener(this);
    	
    	cp.mf.btn.addActionListener(this);
    	
    	// 로그인 처리
    	lf.b1.addActionListener(this);
    	lf.b2.addActionListener(this);
    	
    	// 채팅
    	cp.cf.tf.addActionListener(this);
    }
    public static Image getImage(ImageIcon ii,int width,int height)
    {
    	Image dimg=ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	return dimg;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception ex){}
		new NetworkMain();
	}
	// 버튼 클릭시 처리 => 구현이 안됨 => 클릭을 하면 자동 시스템(JVM)에 의핸 자동 호출 

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cp.hf.b1)// 이전 
			{
				if(curpage>1)
				{
					curpage--;
					ArrayList<FoodLocationVO> list=
							   cp.hf.ms.foodListData(cno, curpage);
					
					cp.hf.mm.cardInit(list);
					cp.hf.mm.cardPrint(list);
					
					cp.hf.pagLa.setText(curpage+ " page / "+totalpage+" pages");
				}
			} else if (e.getSource()==lf.b1) {	// 로그인 처리
				// id
				String id = lf.tf1.getText();
				if (id.length()<1) {
					// alert("ID를 입력하세요")
					JOptionPane.showMessageDialog(this, "ID를 입력하세요");
					lf.tf1.requestFocus();
					return;
				}
				// name ==> 반드시 입력 => 유효성 검사 => JQuery
				// => 기본 (보안) => Spring Security
				String name = lf.tf2.getText();
				if (id.length()<1) {
					// alert("이름을 입력하세요")
					JOptionPane.showMessageDialog(this, "이름을 입력하세요");
					lf.tf2.requestFocus();
					return;
				}
				// 성별
				String sex="";
				if (lf.rb1.isSelected()) {	// 남자 버튼 클릭
					sex="남자";
				} else {
					sex="여자";
				}
				
				// 서버 연결
				try {
					s = new Socket("localhost",3355);
					in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					// 서버가 보내준 데이터 저장된 위치
					
					out = s.getOutputStream();	// 보내는 위치
					
					// 로그인 요청
					out.write((Function.LOGIN+"|"+id+"|"+name+"|"+sex+"\n").getBytes());
				} catch(Exception ex) {}
				
				// 서버에서 들어오는 데이터 읽어서 출력
				new Thread(this).start();
				
			} else if (e.getSource()==lf.b2) {
				System.exit(0);	// 정상 종료
			} else if(e.getSource()==cp.hf.b2) { 	// 다음 
				if(curpage<totalpage)
				{
					System.out.println("cno="+cno);
					curpage++;
					ArrayList<FoodLocationVO> list=
							   cp.hf.ms.foodListData(cno, curpage);
					
					cp.hf.mm.cardInit(list);
					cp.hf.mm.cardPrint(list);
					
					cp.hf.pagLa.setText(curpage+ " page / "+totalpage+" pages");
				}
			} else if (e.getSource()==cp.cf.tf) {
				// 1. 채팅 문자열 읽기
				String msg = cp.cf.tf.getText();
				if(msg.length()<1) 
					return;
				try {
					out.write((Function.CHAT+"|"+msg+"\n").getBytes());
				} catch(Exception ex) {}
				cp.cf.tf.setText("");
			} else if (e.getSource()==menu.chatBtn) {
				cp.card.show(cp, "CF");
			} else if (e.getSource()==menu.exitBtn) {
				try {
					out.write((Function.END+"|\n").getBytes());
				} catch(Exception ex) {}

			} else if (e.getSource()==menu.foodBtn) {
				cp.card.show(cp, "MF");
			} else if (e.getSource()==menu.newsBtn) {
				cp.card.show(cp, "NF");
			} else if (e.getSource()==menu.homeBtn) {
				cp.card.show(cp, "HF");
			} else if (e.getSource()==cp.mf.btn) {	// 검색 버튼 클릭시
				// 1. 입력값 읽어오기
				String fd = cp.mf.tf.getText();
				if (fd.length()<1) {	// 입력이 안 된 상태
					JOptionPane.showMessageDialog(this, "검색어를 입력하세요");
					cp.mf.tf.requestFocus();
					return;
				}
				ArrayList<FoodLocationVO> fList = FoodSystem.foodFind(fd);
				for(int i=cp.mf.model.getRowCount()-1; i>=0; i--) {	// 출력된 내용 지우기
					cp.mf.model.removeRow(i);
				}	// 밑에서부터 지우기
				try {
						for (FoodLocationVO m:fList) {
							URL url = new URL(m.getPoster());
							Image img = getImage(new ImageIcon(url), 35, 30);
							Object[] data = {
									m.getNo(),
									new ImageIcon(img),
									m.getName(),
									m.getAddress()
							};
							cp.mf.model.addRow(data);
						}
				} catch(Exception ex) {}
			}

			else if(e.getSource()==cp.hf.m)
				{
					curpage++;
					
					ArrayList<FoodLocationVO> list=
							   cp.hf.ms.foodListData(cno, curpage);
					
					cp.hf.mm.cardInit(list);
					cp.hf.mm.cardPrint(list);
					
					cp.hf.pagLa.setText(curpage+ " page / "+totalpage+" pages");
				}
			}
		
	

//}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				// 서버에서 보내주는 데이터 받기
				String msg = in.readLine();
				StringTokenizer st = new StringTokenizer(msg,"|");
				int protocol = Integer.parseInt(st.nextToken());
				switch(protocol) {
					case Function.LOGIN: {
						String[] data = {
							st.nextToken(),	// ID
							st.nextToken(), // Name
							st.nextToken()	// 성별
						};
						cp.cf.model.addRow(data);
					}
						break;
					case Function.MYLOG: {
						lf.setVisible(false);	// 로그인 종료
						setVisible(true);		// 메인창
					}
						break;
					case Function.CHAT: {
						cp.cf.ta.append(st.nextToken()+"\n");
					}
						break;
					case Function.SEND:
						break;
					case Function.END: {
						String myId = st.nextToken();
						for(int i=0; i<cp.cf.model.getRowCount(); i++) {
							String you = cp.cf.model.getValueAt(i, 0).toString();
							if(myId.equals(you)) {
								cp.cf.model.removeRow(i);
								break;
							}
						}
					}
						break;
					case Function.MYEND: {
						System.exit(0);
					}
						break;
				}
			}
		} catch(Exception ex) {}
	}
}
