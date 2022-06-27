package com.sist.client;

import java.awt.*;
import javax.swing.*;

public class NewsCard extends JPanel {
	public JLabel la;
	public JTextArea ta;
	public NewsCard() {
		la = new JLabel("");
		la.setForeground(Color.red);
		ta = new JTextArea();
		
		setLayout(null);
		
		ta.setEditable(false);
		
//		la.setBounds(10, 15, 700, 30);
		la.setBounds(10, 30, 700, 20);
		ta.setBounds(10, 50, 700, 80);
//		ta.setBounds(10, 50, 700, 90);
//    	la.setOpaque(true);
//    	la.setBackground(Color.orange);
//    	ta.setOpaque(true);
//    	ta.setBackground(Color.pink);
    	
    	
		add(la);
		add(ta);
	}
}
