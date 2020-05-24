package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.ControllerHearth;
import engine.Game;
import exceptions.FullHandException;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

import java.awt.Desktop;
import java.io.*;

public class StartScreen extends JFrame implements ActionListener {

	StartListener s;
	JPanel p;
	ImageIcon i = new ImageIcon(getClass().getResource("icons8-hearthstone-64.png"));

	public StartScreen() {
		p = new JPanel();
		setVisible(true);
		setTitle("HearthStone");

		setIconImage(i.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500, 250, 300, 350);
		setResizable(false);
		JButton B = new JButton("Start Game");
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
		JLabel l = new JLabel("Welcome to HearthStone ");
		l.setFont(new Font("Courier New", Font.TYPE1_FONT, 14));
		p.setLayout(null);
		l.setBounds(50, 60, 200, 40);
		B.setBounds(80, 200, 120, 30);
		
		JLabel But = new JLabel(i);
		But.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					MakeMeChooseHeros();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		});
		But.setBounds(100, 100, 70, 70);
		p.add(l);
		// p.add(B);
		p.add(But);
		add(p);

		revalidate();
		repaint();

	}

	public static void main(String[] args) {
		new StartScreen();

	}

	public void MakeMeChooseHeros() {
		setBounds(400, 280, 370, 320);
		p.removeAll();
		p.setLayout(null);

		JLabel l1 = new JLabel("First Player ! ");
		JLabel l2 = new JLabel("Second Player ! ");
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
		
		JButton instructions = new JButton("Instructions");
		instructions.setActionCommand("Instructions");
		instructions.setBounds(100, 10, 120, 40);
		instructions.addActionListener(this);
		p.add(instructions);
		
		
		JTextField T1 = new JTextField("your name please !");
		JList<String> list2 = new JList<>(L2);
		T1.addFocusListener(new FocusListener() {

			
			
			@Override
			public void focusGained(FocusEvent e) {
				if (T1.getText().equals("your name please !"))
					T1.setText("");

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (T1.getText().equals(""))
					T1.setText("your name please !");

			}

		});
		JTextField T2 = new JTextField("your name please !");
		T2.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (T2.getText().equals("your name please !"))
					T2.setText("");

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (T2.getText().equals(""))
					T2.setText("your name please !");

			}

		});

		JButton b = new JButton("Start Game");
		b.setPreferredSize(new Dimension(100, 30));
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				if (!T1.getText().equals("your name please !"))
					if (!T2.getText().equals("your name please !")) {
						if (list.getSelectedIndex() != -1) {
							if (list2.getSelectedIndex() != -1) {
								try {
									ControllerHearth cH = new ControllerHearth();
									cH.onStart(helper(list.getSelectedIndex()), T1.getText(),
											helper(list2.getSelectedIndex()), T2.getText());

									dispose();
								} catch (Exception e2) {
									// TODO: handle exception
									e2.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(null, "Choose Second Hero", "Problem",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Choose First Hero", "Problem",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "2nd P .. Please Enter A Name", "Problem",
								JOptionPane.INFORMATION_MESSAGE);
					}
				else {
					JOptionPane.showMessageDialog(null, "1st P .. Please Enter A Name", "Problem",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}

		});
		l1.setBounds(20, 20, 80, 40);
		p.add(l1);
		list.setBounds(20, 60, 100, 100);
		p.add(list);
		T1.setBounds(8, 180, 120, 20);
		p.add(T1);
		l2.setBounds(220, 20, 100, 40);
		p.add(l2);
		list2.setBounds(220, 60, 100, 100);
		p.add(list2);
		T2.setBounds(210, 180, 120, 20);
		p.add(T2);
		b.setBounds(120, 210, 100, 50);
		p.add(b);
		// this.getRootPane().setDefaultButton(b);
		revalidate();
		repaint();

	}

	public static Hero helper(int i) throws IOException, CloneNotSupportedException {
		switch (i) {
		case 0:
			return new Mage();
		case 1:
			return new Hunter();
		case 2:
			return new Priest();
		case 3:
			return new Warlock();
		case 4:
			return new Paladin();

		default:
			return null;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (((JButton) (e.getSource())).getActionCommand() == "Instructions") {
			JFrame inst = new JFrame("Instructions");
			inst.setBounds(new Rectangle(100,500, 800,500));
			TextArea instructions = new TextArea(" HearthStone MileStone 3 Instructions\r\n" + 
					"	1. How to select the two heroes.\r\n" + 
					"		a. Run the Project from the Class Start Screen\r\n" + 
					"		b. Press on the HearthStone logo\r\n" + 
					"		c. Choose a Hero for each Player from the two Lists\r\n" + 
					"		d. Write the Name for each Player in the Text Fields\r\n" + 
					"		e. Then Press Start Game\r\n" + 
					"	2. How the current Hero plays a minion:\r\n" + 
					"		Each Button in the bottom section of the game represent a Minion in\r\n" + 
					"		 the Hero’s Hand, Press the Minion’s Button you want to play.\r\n" + 
					"	3. How the current hero casts all types of spells: \r\n" + 
					"		a. Hero Target Spell, AOE Spell: Will trigger automatically once the Button is pressed\r\n" + 
					"		b. Minion Target Spell, Leeching Spell: Will trigger automatically once a minion is selected and button pressed\r\n" + 
					"		c. Both Minion Target and Hero Target: If a Minion is selected the Spell will have an effect on the Minion\r\n" + 
					"		, otherwise it will have an effect on a Hero.\r\n" + 
					"	4. How the current hero uses his minions to attack the opponent’s minion:\r\n" + 
					"		a. Play the Minion from the hero’s Hand.\r\n" + 
					"		b. Click on it from the field a Green Highlight will be shown indicating that it have been selected\r\n" + 
					"		c. Click on the Opponent’s minion and they will attack each other\r\n" + 
					"		NOTE: Choosing the Opponent’s minion first and then the Current Hero Minion is valid.\r\n" + 
					"	5. How to end the turn:\r\n" + 
					"		Press on the End Turn Button on the bottom right\r\n" + 
					"	6. Specify the screen orientation: \r\n" + 
					"	     The Screen is split into two halves each Hero own a half. The Hero’s Panel is split\r\n" + 
					"        	into two halves a half for the hand (only for the current Hero) and the \r\n" + 
					"	Hero’s Status (Name, Health, Current Mana Crystals, etc.) and the other half is for the Hero’s field.\r\n" + 
					"	7. Any other details that might be specific to your own implementation:\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					" ");
			inst.add(instructions);
			inst.setVisible(true);
			
			try {
				// constructor of file class having file as argument
				File file = new File("Instructions//Instructions.txt");
				
				if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
				{
					System.out.println("not supported");
					return;
				}
				Desktop desktop = Desktop.getDesktop();
				if (file.exists()) // checks file exists or not
					desktop.open(file); // opens the specified file
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	

}
