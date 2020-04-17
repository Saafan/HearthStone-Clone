package view;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageWin extends JFrame{
	private ImageIcon im;
	private JLabel l;
	public ImageWin() {
		setLayout(new FlowLayout());
		im=new ImageIcon(getClass().getResource("image.jpg"));
		l=new JLabel(im);
		add(l);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	public static void main(String[] args) {
		new ImageWin();
	}

}
