package eg.edu.guc.supermarket.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import eg.edu.guc.supermarket.model.cart.Cart;
import eg.edu.guc.supermarket.model.product.GroceryProduct;
import eg.edu.guc.supermarket.model.supermarket.Supermarket;
import eg.edu.guc.supermarket.model.supermarket.SupermarketListener;
import eg.edu.guc.supermarket.view.SupermarketView;

public class Controller implements ActionListener, SupermarketListener {
	private Supermarket model;
	private SupermarketView view;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JButton selectedProduct;

	public Controller() {
		model = new Supermarket();
		model.setListener(this);
		view = new SupermarketView();

		ArrayList<GroceryProduct> products = model.getProducts();

		for (int i = 0; i < products.size(); i++) {
			JButton jb = new JButton();
			jb.setText(products.get(i).toString());
			jb.addActionListener(this);
			buttons.add(jb);
			view.getProducts().add(jb);
		}

		JButton addToCart = new JButton("Add to Cart");
		addToCart.addActionListener(this);
		view.add(addToCart, BorderLayout.SOUTH);

		view.getCart().setText("Cart:\n-------\nTotal:");

		view.revalidate();
		view.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		if (b.getActionCommand().equals("Add to Cart") && selectedProduct != null) {
			int r = buttons.indexOf(selectedProduct);
			model.getProducts().get(r).buy();
			selectedProduct.setIcon(null);
			selectedProduct = null;
			playSound("sounds/chaching.wav");
		} else {
			if (!b.getActionCommand().equals("Add to Cart")) {
				if (selectedProduct == null) {
					b.setIcon(new ImageIcon("images/check.png"));
					selectedProduct = b;
				} else {
					if (b == selectedProduct) {
						selectedProduct.setIcon(null);
						selectedProduct = null;
					} else {
						selectedProduct.setIcon(null);
						b.setIcon(new ImageIcon("images/check.png"));
						selectedProduct = b;
					}
				}
			}
		}
	}

	public void playSound(String filepath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onCartUpdated(Cart cart) {
		ArrayList<GroceryProduct> products = cart.getProducts();
		String s = "Cart:\n-------\n";

		for (GroceryProduct gp : products) {
			s += "- " + gp.toString() + "\n";
		}
		s += "--------\nTotal: " + cart.getTotal();

		view.getCart().setText(s);
	}

	public static void main(String[] args) {
		// initialize the supermarket GUI
		new Controller();
	}

}
