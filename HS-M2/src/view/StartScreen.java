package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import engine.Game;
import exceptions.FullHandException;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class StartScreen extends JFrame {
	JPanel p;
	public StartScreen () {
		p=new JPanel();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500,250,300,300);
		JButton B=new JButton("Start Game");
		B.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MakeMeChooseHeros();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		JLabel l=new JLabel("Welcome to HearthStone ");
		l.setFont(new Font("Courier New", Font.PLAIN, 12));
		p.setLayout(new GridLayout(2,1));
		p.add(l);
		p.add(B);
		add(p,BorderLayout.CENTER);
		
		
	
		revalidate();
		repaint();
		
	}
	public static void main(String[] args) {
		new StartScreen();
	}
	public void MakeMeChooseHeros() {
		p.removeAll();
		remove(p);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel l1=new JLabel("Choose First Hero");
		JLabel l2=new JLabel("Choose Second Hero");
        DefaultListModel<String> L1 = new DefaultListModel<>();  
        L1.addElement("Mage");  
        L1.addElement("Hunter");  
        L1.addElement("Priest");  
        L1.addElement("Warlock"); 
        L1.addElement("Paladin");
        JList<String> list = new JList<>(L1);
        DefaultListModel<String> L2 = new DefaultListModel<>();  
        L2.addElement("Mage");  
        L2.addElement("Hunter");  
        L2.addElement("Priest");  
        L2.addElement("Warlock"); 
        L2.addElement("Paladin");
        JList<String> list2 = new JList<>(L2);
        p1.setLayout(new GridLayout(2,1));
		p1.add(l1);
		p1.add(list);
		p2.setLayout(new GridLayout(2,1));
		p2.add(l2);
		p2.add(list2);
		add(p1,BorderLayout.WEST);
		add(p2,BorderLayout.EAST);
		JButton b=new JButton("Start Game!");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex()!=-1) {
					if(list2.getSelectedIndex()!=-1) {
						try{
							Game.setP1(helper(list.getSelectedIndex()));
							Game.setP2(helper(list2.getSelectedIndex()));
							Game.main(null);
						}catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Choose Second Hero","Problem",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Choose First Hero","Problem",JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		add(b,BorderLayout.SOUTH);
		revalidate();
		repaint();
		
		
	}
	public static Hero helper(int i) throws IOException, CloneNotSupportedException {
		switch (i) {
		case 0:return new Mage();
		case 1:return new Hunter();
		case 2:return new Priest();
		case 3:return new Warlock();
		case 4:return new Paladin();

		default:
			return null;
		}
		
	}
	

}
