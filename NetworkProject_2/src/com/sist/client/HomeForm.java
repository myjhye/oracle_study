package com.sist.client;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sist.data.FoodLocationVO;
import com.sist.data.FoodSystem;

public class HomeForm extends JPanel{
	public JTextField tf;
	public JButton btn;
    public JButton b1,b2; // 이전 , 다음 
    public JButton m=new JButton();
    public FoodManager mm;
    public FoodSystem ms=new FoodSystem();
    public JLabel pagLa=new JLabel("0 page / 0 pages");
    
    
    public HomeForm(ControllerPanel cp)
    {
    	mm=new FoodManager(cp);
    	b1=new JButton("이전");
    	b2=new JButton("다음");
    	
		tf=new JTextField();
		btn = new JButton("검색");

    	
    	// 배치 
//    	setLayout(null);
    	
		setLayout(null);	// 직접 배치
		tf.setBounds(10, 0, 200, 30);
		btn.setBounds(215, 0, 100, 30);
//		js.setBounds(10, 55, 800, 500);
		
		add(tf);
		add(btn);
//		add(js);

    	mm.setBounds(0,0, 840, 780);
    	add(mm);
    	
    	JPanel p1=new JPanel();
    	p1.add(b1);
    	p1.add(pagLa);
    	p1.add(b2);
    	
    	p1.setBounds(0, 790, 840, 35);
    	add(p1);
    	
    	// 시작과 동시에 데이터를 받기 
    	ArrayList<FoodLocationVO> list=FoodSystem.foodListData(1, 1);
    	mm.cardPrint(list);
    	
    }
}