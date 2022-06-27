package com.sist.client;
import java.awt.*;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.sist.data.FoodLocationVO;
import com.sist.data.FoodMain;
import com.sist.main.NetworkMain;
public class PosterCard extends JPanel{
    JLabel poster=new JLabel();
    JLabel name=new JLabel();
    JLabel address =new JLabel();
    //FoodLocationVO m
    public PosterCard(FoodLocationVO m)
    {
    	setLayout(null);
    	poster.setBounds(5,5,165,170);

    	try
    	{
    		
    		URL url=new URL(m.getPoster());
    		Image img=NetworkMain.getImage(new ImageIcon(url), 168, 170);
    		poster.setIcon(new ImageIcon(img));
    	}catch(Exception ex) {}
    	
    	name.setBounds(5,180, 165, 30);

    	name.setText(m.getName());
    	address.setBounds(5, 215 , 165, 30);
    	address.setText(m.getAddress());
    	add(poster);
    	add(name);
    	add(address);
    }
}
