package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

public class StartScreen extends JFrame {

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
		// JLabel credits=new JLabel("Created ,Designed and programmed by Sa3fan 'n
		// Mesameh");
		// credits.setBounds(20, 300, 200, 40);
		// p.add(credits);
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
		b.setPreferredSize(new Dimension(100,30));
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!T1.getText().equals("your name please !"))
					if (!T2.getText().equals("your name please !")) {
						if (list.getSelectedIndex() != -1) {
							if (list2.getSelectedIndex() != -1) {
								try {

									ControllerHearth.getcH().onStart(helper(list.getSelectedIndex()), T1.getText(),
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

}
