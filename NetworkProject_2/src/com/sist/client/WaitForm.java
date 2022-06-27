package com.sist.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sist.data.FoodLocationVO;
import com.sist.data.FoodSystem;
//import com.sist.data.Music;
import com.sist.main.NetworkMain;
// Network 
public class WaitForm extends JPanel{
	JTable table;
	DefaultTableModel model;
	JLabel la = new JLabel("Top 10 맛집");

    public WaitForm()
    {
    	String[] col={"","Name"};
    	Object[][] row=new Object[0][3];
    	model=new DefaultTableModel(row,col) {	// 익명의 클래스 => 상속없이 오버라이딩

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;	// 편집 방지
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return getValueAt(0, columnIndex).getClass();
			}
    		
    	};

    	table=new JTable(model);
    	table.setRowHeight(50);
    	JScrollPane js1=new JScrollPane(table);

    	
    	// 배치
    	setLayout(null);// 사용자 정의

    	js1.setBounds(0, 100 , 250, 500);
    	add(js1);
    	
//		setLayout(new BorderLayout());
		la.setBounds(70, 50, 250, 30);
		la.setFont(new Font("돋움체",Font.BOLD,20));
		add("North",la);
    	
       	try {
    		ArrayList<FoodLocationVO> list = FoodSystem.foodTop10();
    		for(FoodLocationVO m:list) {
    			URL url = new URL(m.getPoster());
    			Image img = NetworkMain.getImage(new ImageIcon(url), 50, 45);
    			Object[] data = {
    				new ImageIcon(img),
    				m.getName()
    			};
    			model.addRow(data);
    		}
    	} catch(Exception ex) {}

    	
    }
}
