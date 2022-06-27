package com.sist.client;

import javax.swing.*;	// Container, Component 
import java.awt.*;
import javax.swing.table.*;

public class FoodFindForm extends JPanel {

	public JTextField tf;
	public JButton btn;
	public JTable table;
	public DefaultTableModel model;
	
	// 초기화
	public FoodFindForm() {
		tf=new JTextField();
		btn = new JButton("검색");
		String[] col = {"순위", "", "이름", "위치"};
		// 순위:int 사진:ImageIcon 곡명,가수명:String => Object
		Object[][] row = new Object[0][4];
		model = new DefaultTableModel(row, col) {
			// 이미지 출력
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
			
		};	// 익명의 클래스 => 생성자 안에서 재정의
		table = new JTable(model);
		table.setRowHeight(40);
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane js = new JScrollPane(table);
		
		// 배치
		
		setLayout(null);	// 직접 배치
		tf.setBounds(10, 15, 200, 30);
		btn.setBounds(215, 15, 100, 30);
		js.setBounds(10, 55, 800, 500);
		
		add(tf);
		add(btn);
		add(js);
		
		
		
		
	}
	
	
	
}