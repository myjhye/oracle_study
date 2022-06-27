package com.sist.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DetailForm extends JPanel implements ActionListener{
   public ControllerPanel cp;
   JLabel posterLa=new JLabel();
   JLabel name = new JLabel();
   JLabel address=new JLabel();
   JLabel tel=new JLabel();
   JLabel type=new JLabel();
   JLabel price=new JLabel();
   JLabel parking=new JLabel();
   JLabel time=new JLabel();
   JLabel menu=new JLabel();
   JButton b1;
   public DetailForm(ControllerPanel cp)
   {
	   b1=new JButton("목록");
	   this.cp=cp;
//	   // 배치 
	   setLayout(null);
	   posterLa.setBounds(10, 15, 350, 350 );
	   name.setBounds(365, 15, 400, 35);
	   address.setBounds(365, 55, 400, 35);
	   tel.setBounds(365, 95, 400, 35);
	   type.setBounds(365, 135, 400, 35);
	   price.setBounds(365, 175, 400, 35);
	   parking.setBounds(365, 215, 400, 35);
	   time.setBounds(365, 255, 400, 35);
	   menu.setBounds(365, 295, 400, 35);
//	   movie.setVisible(false);
	   JPanel p=new JPanel();
	   p.add(b1);
	   p.setBounds(365, 350, 400, 35);
//	   
	   add(posterLa);add(name);add(address);
	   add(tel);add(type);
	   add(price);add(parking);
	   add(time);add(menu);
	   add(p);
//	   
	   b1.addActionListener(this);
   }

@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			cp.card.show(cp,"HF");
		}

	}
}
