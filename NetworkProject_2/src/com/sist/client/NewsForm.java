package com.sist.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sist.data.NaverNewsMain;
import com.sist.data.News;

public class NewsForm extends JPanel {
	public NewsCard[] nc = new NewsCard[10];
	JLabel la = new JLabel("실시간 네이버 뉴스", JLabel.CENTER);
	public JTextField tf;
	public JButton btn;
	
//	String data="뮤직";
	
	public NewsForm() {
		ArrayList<News> list = NaverNewsMain.newsAllData();
		JPanel p = new JPanel();
		
		tf=new JTextField();
		btn = new JButton("검색");

		
		p.setLayout(new GridLayout(10,1,5,0));
//    	p1.setOpaque(true);
//    	p1.setBackground(Color.cyan);
		for(int i=0; i<nc.length; i++) {
			nc[i] = new NewsCard();
			nc[i].la.setText(list.get(i).getTitle());
			nc[i].ta.setText(list.get(i).getDescription());
			p.add(nc[i]);
		}
		

		
		tf.setBounds(10, 55, 200, 30);
		btn.setBounds(215, 55, 100, 30);
    
		
		add(tf);
		add(btn);
		
		setLayout(new BorderLayout());
		la.setFont(new Font("돋움체",Font.BOLD,45));
		add("North",la);
//		add("Center",p);
		add(p);
	}
}
