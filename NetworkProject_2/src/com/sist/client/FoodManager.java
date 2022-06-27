package com.sist.client;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.sist.data.FoodLocationVO;
import com.sist.data.FoodSystem;
import com.sist.main.NetworkMain;
public class FoodManager extends JPanel implements MouseListener{
    public PosterCard[] foods=new PosterCard[15];
    public JPanel pan=new JPanel();
    public ControllerPanel cp;
    public FoodManager(ControllerPanel cp)
    {
    	this.cp=cp;
    }
    public void cardPrint(ArrayList<FoodLocationVO> list)
    {
    	setLayout(null);
    	pan.setLayout(new GridLayout(3,5));
    	int i=0;
    	for(FoodLocationVO m:list)
    	{
    		
    		foods[i]=new PosterCard(m);
    		pan.add(foods[i]);
    		foods[i].addMouseListener(this);
    		i++;
    	}
    	
    	pan.setBounds(10, 35, 840, 750);
    	add(pan);
    	
    	
    }
    public void cardInit(ArrayList<FoodLocationVO> list)
    {
    	for(int i=0;i<list.size();i++)
    	{
    		
    		foods[i].poster.setIcon(null);
    		foods[i].name.setText("");
    		foods[i].address.setText("");
   
    	}
    	pan.removeAll();
		pan.validate();
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<foods.length;i++)
		{
			if(e.getSource()==foods[i])
			{
				String title=foods[i].name.getText();
				for(int j=0;j<FoodSystem.getList().size();j++)
				{
					FoodLocationVO m=FoodSystem.getList().get(j);
					if(m.getName().equals(title))
					{
						cp.df.name.setText(m.getName());
						cp.df.address.setText(m.getAddress());
						cp.df.tel.setText(m.getTel());
						cp.df.type.setText(m.getType());
						cp.df.price.setText(m.getPrice());
						cp.df.parking.setText(m.getParking());
						cp.df.time.setText(m.getTime());
						cp.df.menu.setText(m.getMenu());
						try
				    	{
				    		URL url=new URL(m.getPoster());
				    		Image img=NetworkMain.getImage(new ImageIcon(url), 350, 250);
				    		cp.df.posterLa.setIcon(new ImageIcon(img));
				    		
				    	}catch(Exception ex) {}
						break;
					}
				}
				cp.card.show(cp, "DF");// 화면 이동 
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
