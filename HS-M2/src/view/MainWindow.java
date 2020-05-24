package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;


import model.cards.Card;
import model.cards.minions.Minion;

public class MainWindow extends JFrame {
	ImageIcon i=new ImageIcon(getClass().getResource("icons8-hearthstone-64.png")) ;
	// consts
	int PanelWidth, PanelHeight = 0;
	int HandWidth, HandHeight = 0;
	int DeckWidth, DeckHeight = 0;

	// CurrentHero Variables
	public JPanel BottomPanel = new JPanel();
	public JPanel currentHeroHand = new JPanel();
	public JPanel currentField = new JPanel();
	public JPanel CHDeck = new JPanel();
	public JPanel TwoButtons = new JPanel();
	public JTextArea curStatus = new JTextArea();

	// Opponent Variables
	public JPanel TopPanel = new JPanel();
	public JPanel opponentHand = new JPanel();
	public JPanel OppField = new JPanel();
	public JPanel OPDeck = new JPanel();
	public JTextArea OppStatus = new JTextArea();

	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Setting Up Dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		PanelHeight = (int) (getHeight() * 0.25f);
		PanelWidth = (int) (getWidth());

		HandHeight = (int) (getHeight() * 0.25f);
		HandWidth = (int) (getWidth() * 0.84f);

		DeckHeight = (int) (HandHeight);
		DeckWidth = (int) (getWidth() - HandWidth - (HandWidth * 0.01f));

		Dimension Panels = new Dimension(PanelWidth, PanelHeight);
		BottomPanel.setPreferredSize(Panels);
		TopPanel.setPreferredSize(Panels);

		Dimension Hands = new Dimension(HandWidth, HandHeight);
		opponentHand.setPreferredSize(Hands);
		currentHeroHand.setPreferredSize(Hands);

		Dimension Decks = new Dimension(DeckWidth, DeckHeight);
		CHDeck.setPreferredSize(Decks);
		OPDeck.setPreferredSize(Decks);

		Dimension Fields = new Dimension(HandWidth, (int) (HandHeight * 0.2f));
		
		Dimension Status = new Dimension(DeckWidth, (int) (DeckHeight * 0.5f));
		curStatus.setPreferredSize(Status);
		OppStatus.setPreferredSize(Status);
		curStatus.setEditable(false);
		OppStatus.setEditable(false);
		//----------------------------------------------------------------------------------------
		
		// Setting up the current Hero Panel
		BottomPanel.setLayout(new BorderLayout());
		add(BottomPanel, BorderLayout.SOUTH);

		BottomPanel.add(currentHeroHand, BorderLayout.WEST);

		// Setting The Deck
		BottomPanel.add(CHDeck, BorderLayout.EAST);
		CHDeck.setBackground(Color.BLACK);

		//Setting The Field
		JPanel TheFields = new JPanel(new GridLayout(0, 1));
		
//		currentField.setLayout(new GridLayout(1, 1));
//		OppField.setLayout(new GridLayout(1, 1));
		
		TheFields.setPreferredSize(Fields);
		add(TheFields,BorderLayout.CENTER);
		
		OppField.setBackground(Color.DARK_GRAY);
		
		currentField.setBackground(Color.GRAY);
		TheFields.add(OppField);
		TheFields.add(currentField);
		
		// Setting Text
		CHDeck.setLayout(new BorderLayout());
		CHDeck.add(curStatus, BorderLayout.SOUTH);

		// Setting GridLayout
		currentHeroHand.setLayout(new GridLayout(1, 1));
		
		//Setting the UseHeroPower and EndTurn Buttons
		TwoButtons.setLayout(new FlowLayout());
		TwoButtons.setPreferredSize(Status);
		TwoButtons.setLayout(new GridLayout(1, 1));
		CHDeck.add(TwoButtons,BorderLayout.NORTH);
		
		// ===============================================================================

		// Setting up the opponent Panel
		TopPanel.setLayout(new BorderLayout());
		add(TopPanel, BorderLayout.NORTH);
		opponentHand.setBackground(Color.RED);
		TopPanel.add(opponentHand, BorderLayout.WEST);
		
		//Setting Opponent Layout
		opponentHand.setLayout(new BorderLayout());
		
		// Setting The Deck
		TopPanel.add(OPDeck, BorderLayout.EAST);
//		OPDeck.setBackground(Color.BLACK);

		// Setting Text
		OPDeck.setLayout(new BorderLayout());
		OPDeck.add(OppStatus, BorderLayout.CENTER);
		setVisible(true);
		setIconImage(i.getImage());
		setTitle("HearthStone");
		repaint();
		revalidate();
		
	}
}

