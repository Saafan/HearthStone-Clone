package eg.edu.guc.supermarket.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SupermarketView extends JFrame {
	private JPanel products;
	private JTextArea cart;

	public SupermarketView() {
		setTitle("Supermarket");
		setBounds(300, 100, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // to terminate the program like WindowDestroyer

		products = new JPanel();
		products.setLayout(new GridLayout(0, 3));
		products.setPreferredSize(new Dimension(600, getHeight()));
		add(products, BorderLayout.WEST);

		cart = new JTextArea();
		cart.setPreferredSize(new Dimension(200, getHeight()));
		cart.setEditable(false);

		add(cart, BorderLayout.EAST);

		this.revalidate();
		this.repaint();
		setVisible(true);
	}

	public JPanel getProducts() {
		return products;
	}

	public JTextArea getCart() {
		return cart;
	}

	public static void main(String[] args) {
//		SupermarketView sp = new SupermarketView();
//		sp.setVisible(true);
	}
}
