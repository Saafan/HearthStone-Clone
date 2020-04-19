package controller;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Mage;
import model.heroes.Priest;

import view.MainWindow;
import view.StartListener;


public class ControllerHearth implements GameListener, ActionListener, StartListener {
	private static ControllerHearth cH=new ControllerHearth();
	private static Hero P1;
	private static Hero P2;
	private static String S1, S2;
	public MainWindow MainScreen ;

	private static ArrayList<JButton> curButtons = new ArrayList<JButton>();
	private static ArrayList<JButton> curFieldMinions = new ArrayList<JButton>();
	private static ArrayList<JButton> OppFieldMinions = new ArrayList<JButton>();

	 JButton selected = new JButton();
	 JButton UseHeroButton = new JButton();

	 JButton curMinion = new JButton();
	 Minion curSelMinion = null;

	 JButton oppMinion = new JButton();
	 Minion OppSelMinion = null;
	 JButton OppButton = new JButton();
	 Hero oppSelHero = null;
	
	 Game g;

	
	public static void main(String[] args) throws FullHandException, CloneNotSupportedException, IOException {
		
		
	}
	public  void UpdatingMainScreen() throws FullHandException, CloneNotSupportedException, IOException {
		MainScreen =new MainWindow();
		 g = new Game(new Mage(), new Mage());
		 
		 
		g.setListener(this);
		for (int i = 0; i < 8; i++)
			g.endTurn();
		g.getCurrentHero().setCurrentHP(1);
		JLabel CardsLeft = new JLabel("There are " + g.getOpponent().getHand().size() + " Cards in The Opponent's Hand");

		CardsLeft.setFont(new Font("Serif", Font.PLAIN, MainScreen.getWidth() / 25));
		JButton OpponentButton = new JButton("Opponent");
		OpponentButton.setFont(new Font("Courier New", Font.PLAIN, 30));
		OpponentButton.setActionCommand("opponent");
		OpponentButton.addActionListener(this);
		MainScreen.opponentHand.add(OpponentButton);
		UpdateAll();
		MainScreen.repaint();
		MainScreen.revalidate();
		
		}

	public  void AddTwoButtons() {

		JButton UseHeroPower = new JButton("Use Hero Power");
		JButton EndTurn = new JButton("End Turn");
		UseHeroPower.addActionListener(this);
		EndTurn.addActionListener(this);
		MainScreen.TwoButtons.removeAll();
		MainScreen.TwoButtons.add(UseHeroPower);
		MainScreen.TwoButtons.add(EndTurn);
	}

	public  void UpdateAll() {
		UpdateCurrentHeroStatus();
		UpdateCurrentOpponentStatus();
		UpdateHandButtons();
		UpdateCurFieldButtons();
		UpdateOppFieldButtons();
		AddTwoButtons();
		curSelMinion = null;
		OppSelMinion = null;
		oppSelHero = null;
		OppButton = null;
		UseHeroButton = null;
		MainScreen.repaint();
		MainScreen.revalidate();
		onGameOver();
	}

	public  void UpdateHandButtons() {
		MainScreen.currentHeroHand.removeAll();
		curButtons.clear();

		for (Card c : g.getCurrentHero().getHand()) {
			JButton jb = new JButton(c.getName());
			jb.addActionListener(this);
			jb.setActionCommand(c.getName());

			if (c instanceof Minion)
				jb.setText(CardDetails((Minion) (c)));
			else
				jb.setText(CardDetails(c));
			curButtons.add(jb);
			MainScreen.currentHeroHand.add(jb);
		}
	}

	public  void UpdateCurFieldButtons() {
		MainScreen.currentField.removeAll();
		curFieldMinions.clear();
		for (Minion c : g.getCurrentHero().getField()) {
			JButton jb = new JButton();
			jb.addActionListener(this);
			jb.setActionCommand('C' + c.getName());

			jb.setPreferredSize(
					new Dimension(MainScreen.currentField.getWidth() / 7, MainScreen.currentField.getHeight()));

			curFieldMinions.add(jb);

			jb.setText(CardDetails(c));
			MainScreen.currentField.add(jb);
		}
	}

	public  void UpdateOppFieldButtons() {
		MainScreen.OppField.removeAll();
		OppFieldMinions.clear();
		for (Minion c : g.getOpponent().getField()) {
			JButton jb = new JButton(c.getName());
			jb.addActionListener(this);
			jb.setActionCommand('O' + c.getName());

			jb.setPreferredSize(
					new Dimension(MainScreen.currentField.getWidth() / 7, MainScreen.currentField.getHeight()));

			jb.setText(CardDetails(c));

			OppFieldMinions.add(jb);
			MainScreen.OppField.add(jb);
		}
	}

	static String CardDetails(Minion c) {
		String CardText = "";

		CardText = c.getName() + "\nMana Cost: " + c.getManaCost() + "\nRarity: " + c.getRarity() + "\nAttack: "
				+ c.getAttack() + "\nCurrent HP: " + c.getCurrentHP() + "\\" + c.getMaxHP() + "\n Taunt: " + c.isTaunt()
				+ "\n Divine: " + c.isDivine() + "\n Charge: " + !c.isSleeping() + "\n Attacked: " + c.isAttacked();

		CardText = "<html>" + CardText.replaceAll("\\n", "<br>") + "</html>";

		return CardText;
	}

	static String CardDetails(Card card) {
		String CardText = "";

		CardText = card.getName() + "\nMana Cost: " + card.getManaCost() + "\nRarity: " + card.getRarity();
		CardText = "<html>" + CardText.replaceAll("\\n", "<br>") + "</html>";

		return CardText;
	}

	public  void UpdateCurrentHeroStatus() {
		String s = "";
		s += "Player " + (g.getCurrentHero().equals(g.getFirstHero()) ? S1 : S2) + "\n";
		s += "Name: " + g.getCurrentHero().getName() + '\n';
		s += "Current HP: " + g.getCurrentHero().getCurrentHP() + '\n';
		s += "Mana Crystals: " + g.getCurrentHero().getCurrentManaCrystals() + "\\" + g.getCurrentHero().getTotalManaCrystals()
				+ "\n";
		s += "Remaining Deck Size: " + g.getCurrentHero().getDeck().size() + '\n';

		MainScreen.curStatus.setText(s);
	}

	public  void UpdateCurrentOpponentStatus() {
		String s = "";
		s += "Player " + (g.getOpponent().equals(g.getFirstHero()) ? S1 : S2) + "\n";
		s += "Name: " + g.getOpponent().getName() + '\n';
		s += "Current HP: " + g.getOpponent().getCurrentHP() + '\n';
		s += "Mana Crystals: " + g.getOpponent().getCurrentManaCrystals() + "\\" + g.getOpponent().getTotalManaCrystals() + "\n";
		s += "Remaining Deck Size: " + g.getOpponent().getDeck().size() + '\n';

		MainScreen.OppStatus.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) {

		JButton defaultButton = new JButton();
		selected = (JButton) e.getSource();
		if (e.getActionCommand() == "Use Hero Power")
			try {
				if (g.getCurrentHero() instanceof Mage || g.getCurrentHero() instanceof Priest) {
					if (selected == UseHeroButton) {
						UseHeroButton = null;
						selected.setBackground(defaultButton.getBackground());
						selected = null;
						UpdateAll();
					} else {
						UseHeroButton = selected;
						selected.setBackground(Color.GREEN);
						if (g.getCurrentHero() instanceof Mage) {
							if (OppSelMinion != null) {
								((Mage) g.getCurrentHero()).useHeroPower(OppSelMinion);
								UpdateAll();
							}
							if (oppSelHero != null) {
								((Mage) g.getCurrentHero()).useHeroPower(oppSelHero);
								UpdateAll();
							}

						}
						if (g.getCurrentHero() instanceof Priest) {
							int dialogButton = JOptionPane.YES_NO_OPTION;
							int dResult=JOptionPane.showConfirmDialog(null, "Do you want to heal yourself?", "Problem",
									dialogButton);
							
							if (dResult == 0) {
								((Priest) g.getCurrentHero()).useHeroPower(g.getCurrentHero());
								UpdateAll();
							} else {
								if (curSelMinion != null) {
									((Priest) g.getCurrentHero()).useHeroPower(curSelMinion);
									UpdateAll();
								}else
									JOptionPane.showMessageDialog(null, "choose first a minion you want to heal", "Problem",
											JOptionPane.INFORMATION_MESSAGE);

							}

						}

					}

				} else {
					g.getCurrentHero().useHeroPower();
					UpdateAll();
				}

			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
					| FullFieldException | CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem", JOptionPane.INFORMATION_MESSAGE);
				UpdateAll();
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem", JOptionPane.INFORMATION_MESSAGE);
				e1.printStackTrace();
			}
		else if (e.getActionCommand() == "End Turn")
			try {
				g.getCurrentHero().endTurn();
				UpdateAll();
			} catch (FullHandException | CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem", JOptionPane.INFORMATION_MESSAGE);
				UpdateAll();
			}
		else if (selected != null) {

			if (curButtons.contains(selected)) {
				int i = curButtons.indexOf(selected);
				if (g.getCurrentHero().getHand().get(i) instanceof Minion) {
					try {
						g.getCurrentHero().playMinion((Minion) g.getCurrentHero().getHand().get(i));
						// MainScreen.g.getCurrentHero().remove(selected);
					} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
								JOptionPane.INFORMATION_MESSAGE);

					}

					UpdateAll();
				}

				else if (g.getCurrentHero().getHand().get(i) instanceof Spell) {
					try {
						Spell s = (Spell) g.getCurrentHero().getHand().get(i);
						// to cast field spell
						if (s.getClass().getInterfaces()[0].getName().contains("FieldSpell"))
							g.getCurrentHero().castSpell((FieldSpell) s);
						// to cast minion target spells
						else if (s.getClass().getInterfaces()[0].getName().contains("MinionTargetSpell")
								|| s.getClass().getInterfaces()[0].getName().contains("LeechingSpell")) {
							Minion m;
							if (curSelMinion != null)
								m = curSelMinion;
							else if (OppSelMinion != null)
								m = OppSelMinion;
							else
								throw new Exception("Please first Choose A minion then cast the spell");
							g.getCurrentHero().castSpell((MinionTargetSpell) s, m);

						} else if (s.getClass().getInterfaces()[0].getName().contains("AOESpell"))
							g.getCurrentHero().castSpell((AOESpell) s, g.getOpponent().getField());
						else if (s.getClass().getInterfaces()[0].getName().contains("HeroTargetSpell"))
							g.getCurrentHero().castSpell((HeroTargetSpell) s, g.getOpponent());

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
								JOptionPane.INFORMATION_MESSAGE);
					}
					UpdateAll();
				}
				UpdateAll();
			}
			if (selected.getActionCommand().charAt(0) == 'C') {
				if (selected == curMinion) {
					selected.setBackground(defaultButton.getBackground());
					curMinion = null;
					curSelMinion = null;
					UpdateAll();
				} else {
					if (curMinion != null)
						curMinion.setBackground(defaultButton.getBackground());
					curMinion = selected;
					if (curFieldMinions.contains(curMinion)) {
						int j = curFieldMinions.indexOf(curMinion);

						curSelMinion = g.getCurrentHero().getField().get(j);

						if (OppSelMinion != null)
							try {
								g.getCurrentHero().attackWithMinion(curSelMinion, OppSelMinion);
								UpdateAll();
							} catch (CannotAttackException | NotYourTurnException | TauntBypassException
									| InvalidTargetException | NotSummonedException e1) {
								UpdateAll();
								JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
										JOptionPane.INFORMATION_MESSAGE);
							}
						if (UseHeroButton != null)
							UseHeroButton.setBackground(defaultButton.getBackground());
						curMinion.setBackground(Color.GREEN);
					}

				}
			} else if (selected.getActionCommand().charAt(0) == 'O') {

				if (selected == oppMinion) {
					selected.setBackground(defaultButton.getBackground());
					oppMinion = null;
					OppSelMinion = null;
					UpdateAll();
				} else {
					if (oppMinion != null)
						oppMinion.setBackground(defaultButton.getBackground());

					oppMinion = selected;

					if (OppFieldMinions.contains(oppMinion)) {

						int j = OppFieldMinions.indexOf(oppMinion);

						OppSelMinion = g.getOpponent().getField().get(j);

						if (UseHeroButton != null && g.getCurrentHero() instanceof Mage) {
							try {
								((Mage) g.getCurrentHero()).useHeroPower(OppSelMinion);
							} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
									| FullHandException | FullFieldException | CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							UpdateAll();
						}

						else if (curSelMinion != null)
							try {
								g.getCurrentHero().attackWithMinion(curSelMinion, OppSelMinion);
								UpdateAll();

							} catch (CannotAttackException | NotYourTurnException | TauntBypassException
									| InvalidTargetException | NotSummonedException e1) {
								UpdateAll();

								JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
										JOptionPane.INFORMATION_MESSAGE);
							}
						if (OppSelMinion != null)
							oppMinion.setBackground(Color.RED);
					}

				}
			} else if (selected.getActionCommand().charAt(0) == 'o') {

				if (selected == OppButton) {
					selected.setBackground(defaultButton.getBackground());
					OppButton = null;
					oppSelHero = null;
				} else {

					try {
						if (UseHeroButton != null) {
							((Mage) g.getCurrentHero()).useHeroPower(g.getOpponent());
							UpdateAll();
							return;
						}
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
								JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
					}

					if (OppButton != null)
						OppButton.setBackground(Color.GRAY);

					OppButton = selected;

					if (curFieldMinions.contains(curMinion)) {

						int j = curFieldMinions.indexOf(curMinion);

						curSelMinion = g.getCurrentHero().getField().get(j);

						if (OppButton != null)
							try {
								g.getCurrentHero().attackWithMinion(curSelMinion, g.getOpponent());

								UpdateAll();

							} catch (CannotAttackException | NotYourTurnException | TauntBypassException
									| InvalidTargetException | NotSummonedException e1) {
								UpdateAll();

								JOptionPane.showMessageDialog(null, e1.getMessage(), "Problem",
										JOptionPane.INFORMATION_MESSAGE);
							}
					}
				}
			}
		}

	}

	@Override
	public void onGameOver() {
		endGameChecker();
		
	}
	

	

	/**
	 * @return the p2
	 */
	public  void StartGame(Hero h1,String P1,Hero h2,String P2 ) {
		try {
			UpdatingMainScreen();
		} catch (FullHandException | CloneNotSupportedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Hero getP2() {
		return P2;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public static void setP2(Hero p2) {
		P2 = p2;
	}

	/**
	 * @return the p1
	 */
	public static Hero getP1() {
		return P1;
	}

	/**
	 * @param p1 the p1 to set
	 */
	public static void setP1(Hero p1) {
		P1 = p1;
	}

	public static boolean isHasTaunt(Hero h) {
		for (Minion m : h.getField())
			if (m.isTaunt())
				return true;
		return false;
	}

	/**
	 * @return the s2
	 */
	public static String getS2() {
		return S2;
	}

	/**
	 * @param s2 the s2 to set
	 */
	public static void setS2(String s2) {
		S2 = s2;
	}

	/**
	 * @return the s1
	 */
	public static String getS1() {
		return S1;
	}

	/**
	 * @param s1 the s1 to set
	 */
	public static void setS1(String s1) {
		S1 = s1;
	}

	public void endGameChecker() {
		if (g.getFirstHero().getCurrentHP() == 0) {
			MainScreen.dispose();
			//new ImageWin();
			JOptionPane.showMessageDialog(null, "Congrats ....!" + S2, "Gratulation", JOptionPane.INFORMATION_MESSAGE);

		} else if (g.getSecondHero().getCurrentHP() == 0) {
			MainScreen.dispose();
		//	new ImageWin();
			JOptionPane.showMessageDialog(null, "Congrats ....!" + S1, "Gratulation", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void onStart(Hero hero1,String name1,Hero hero2,String name2) {
		P1=hero1;
		P2=hero2;
		S1=name1;
		S2=name2;
		
		try {
			UpdatingMainScreen();
		} catch (FullHandException | CloneNotSupportedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the cH
	 */
	public static ControllerHearth getcH() {
		return cH;
	}
	/**
	 * @param cH the cH to set
	 */
	public static void setcH(ControllerHearth cH) {
		ControllerHearth.cH = cH;
	}
}
