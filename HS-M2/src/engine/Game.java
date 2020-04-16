package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

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
import model.cards.spells.FieldSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.HeroListener;
import model.heroes.Hunter;
import model.heroes.Warlock;
import view.MainWindow;

public class Game implements ActionValidator, HeroListener, ActionListener {
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	public static MainWindow MainScreen = new MainWindow();

	private ArrayList<JButton> curButtons = new ArrayList<JButton>();
	private ArrayList<JButton> curFieldMinions = new ArrayList<JButton>();
	private ArrayList<JButton> OppFieldMinions = new ArrayList<JButton>();

	JButton selected = new JButton();

	JButton curMinion = new JButton();
	Minion curSelMinion = null;

	JButton oppMinion = new JButton();
	Minion OppSelMinion = null;

	private GameListener listener;

	public Game(Hero p1, Hero p2) throws FullHandException, CloneNotSupportedException {
		firstHero = p1;
		secondHero = p2;
		firstHero.setListener(this);
		secondHero.setListener(this);
		firstHero.setValidator(this);
		secondHero.setValidator(this);
		int coin = (int) (Math.random() * 2);
		currentHero = coin == 0 ? firstHero : secondHero;
		opponent = currentHero == firstHero ? secondHero : firstHero;
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);
		for (int i = 0; i < 3; i++) {
			currentHero.drawCard();
		}
		for (int i = 0; i < 4; i++) {
			opponent.drawCard();
		}
	}

	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		if (user == opponent)
			throw new NotYourTurnException("You can not do any action in your opponent's turn");
	}

	public void validateAttack(Minion a, Minion t)
			throws TauntBypassException, InvalidTargetException, NotSummonedException, CannotAttackException {
		if (a.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (a.isSleeping())
			throw new CannotAttackException("Give this minion a turn to get ready");
		if (a.isAttacked())
			throw new CannotAttackException("This minion has already attacked");
		if (!currentHero.getField().contains(a))
			throw new NotSummonedException("You can not attack with a minion that has not been summoned yet");
		if (currentHero.getField().contains(t))
			throw new InvalidTargetException("You can not attack a friendly minion");
		if (!opponent.getField().contains(t))
			throw new NotSummonedException("You can not attack a minion that your opponent has not summoned yet");
		if (!t.isTaunt()) {
			for (int i = 0; i < opponent.getField().size(); i++) {
				if (opponent.getField().get(i).isTaunt())
					throw new TauntBypassException("A minion with taunt is in the way");
			}

		}

	}

	public void validateAttack(Minion m, Hero t)
			throws TauntBypassException, NotSummonedException, InvalidTargetException, CannotAttackException {
		if (m.getAttack() <= 0)
			throw new CannotAttackException("This minion Cannot Attack");
		if (m.isSleeping())
			throw new CannotAttackException("Give this minion a turn to get ready");
		if (m.isAttacked())
			throw new CannotAttackException("This minion has already attacked");
		if (!currentHero.getField().contains(m))
			throw new NotSummonedException("You can not attack with a minion that has not been summoned yet");
		if (t.getField().contains(m))
			throw new InvalidTargetException("You can not attack yourself with your minions");
		for (int i = 0; i < opponent.getField().size(); i++) {
			if (opponent.getField().get(i).isTaunt())
				throw new TauntBypassException("A minion with taunt is in the way");
		}
	}

	public void validateManaCost(Card c) throws NotEnoughManaException {
		if (currentHero.getCurrentManaCrystals() < c.getManaCost())
			throw new NotEnoughManaException("I don't have enough mana !!");
	}

	public void validatePlayingMinion(Minion m) throws FullFieldException {
		if (currentHero.getField().size() == 7)
			throw new FullFieldException("No space for this minion");
	}

	public void validateUsingHeroPower(Hero h) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
		if (h.getCurrentManaCrystals() < 2)
			throw new NotEnoughManaException("I don't have enough mana !!");
		if (h.isHeroPowerUsed())
			throw new HeroPowerAlreadyUsedException(" I already used my hero power");
	}

	@Override
	public void onHeroDeath() {

		listener.onGameOver();

	}

	@Override
	public void damageOpponent(int amount) {

		opponent.setCurrentHP(opponent.getCurrentHP() - amount);
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		Hero temp = currentHero;
		currentHero = opponent;
		opponent = temp;
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals() + 1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		for (Minion m : currentHero.getField()) {
			m.setAttacked(false);
			m.setSleeping(false);
		}
		currentHero.drawCard();

	}

	public static void main(String[] args) throws CloneNotSupportedException, FullHandException, IOException {
		UpdatingMainScreen();
	}

	public static void UpdatingMainScreen() throws FullHandException, CloneNotSupportedException, IOException {

		Game g = new Game(new Hunter(), new Warlock());

		for (int i = 0; i < 8; i++)
			g.endTurn();

		JLabel CardsLeft = new JLabel("There are " + g.opponent.getHand().size() + " Cards in The Opponent's Hand");
		
		CardsLeft.setFont(new Font("Serif", Font.PLAIN, MainScreen.getWidth() / 25));
		JButton OpponentButton = new JButton();
		OpponentButton
		MainScreen.opponent.add(OpponentButton);
		
		g.AddTwoButtons();
		g.UpdateAll();
		MainScreen.repaint();
		MainScreen.revalidate();
	}

	public void AddTwoButtons() {
		JButton UseHeroPower = new JButton("Use Hero Power");
		JButton EndTurn = new JButton("End Turn");
		UseHeroPower.addActionListener(this);
		EndTurn.addActionListener(this);
		MainScreen.TwoButtons.add(UseHeroPower);
		MainScreen.TwoButtons.add(EndTurn);
	}

	public void UpdateAll() {
		UpdateCurrentHeroStatus();
		UpdateCurrentOpponentStatus();
		UpdateHandButtons();
		UpdateCurFieldButtons();
		UpdateOppFieldButtons();

		curSelMinion = null;
		OppSelMinion = null;

		MainScreen.repaint();
		MainScreen.revalidate();
	}

	public void UpdateHandButtons() {
		MainScreen.currentHero.removeAll();
		curButtons.clear();
		for (Card c : currentHero.getHand()) {
			JButton jb = new JButton(c.getName());
			jb.addActionListener(this);
			jb.setActionCommand(c.getName());
			if (c instanceof Minion)
				jb.setText(CardDetails((Minion) (c)));
			else
				jb.setText(CardDetails(c));
			curButtons.add(jb);
			MainScreen.currentHero.add(jb);
		}
	}

	public void UpdateCurFieldButtons() {
		MainScreen.currentField.removeAll();
		curFieldMinions.clear();
		for (Minion c : currentHero.getField()) {
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

	public void UpdateOppFieldButtons() {
		MainScreen.OppField.removeAll();
		OppFieldMinions.clear();
		for (Minion c : opponent.getField()) {
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

	String CardDetails(Minion c) {
		String CardText = "";

		CardText = c.getName() + "\nMana Cost: " + c.getManaCost() + "\nRarity: " + c.getRarity() + "\nAttack: "
				+ c.getAttack() + "\nCurrent HP: " + c.getCurrentHP() + "\\" + c.getMaxHP() + "\n Taunt: " + c.isTaunt()
				+ "\n Divine: " + c.isDivine() + "\n Charge: " + !c.isSleeping() + "\n Attacked: " + c.isAttacked();

		CardText = "<html>" + CardText.replaceAll("\\n", "<br>") + "</html>";

		return CardText;
	}

	String CardDetails(Card card) {
		String CardText = "";

		CardText = card.getName() + "\nMana Cost: " + card.getManaCost() + "\nRarity: " + card.getRarity();
		CardText = "<html>" + CardText.replaceAll("\\n", "<br>") + "</html>";

		return CardText;
	}

	public void UpdateCurrentHeroStatus() {
		String s = "";
		s += "Name: " + currentHero.getName() + '\n';
		s += "Current HP: " + currentHero.getCurrentHP() + '\n';
		s += "Mana Crystals: " + currentHero.getCurrentManaCrystals() + "\\" + getCurrentHero().getTotalManaCrystals()
				+ "\n";
		s += "Remaining Deck Size: " + currentHero.getDeck().size() + '\n';

		MainScreen.curStatus.setText(s);
	}

	public void UpdateCurrentOpponentStatus() {
		String s = "";
		s += "Name: " + opponent.getName() + '\n';
		s += "Current HP: " + opponent.getCurrentHP() + '\n';
		s += "Mana Crystals: " + opponent.getCurrentManaCrystals() + "\\" + opponent.getTotalManaCrystals() + "\n";
		s += "Remaining Deck Size: " + opponent.getDeck().size() + '\n';

		MainScreen.OppStatus.setText(s);
	}

	public Hero getOpponent() {
		return opponent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton defaultButton = new JButton();
		selected = (JButton) e.getSource();
		if (e.getActionCommand() == "Use Hero Power")
			try {
				currentHero.useHeroPower();
				UpdateAll();
			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
					| FullFieldException | CloneNotSupportedException e1) {
				e1.printStackTrace();
				UpdateAll();
			}
		else if (e.getActionCommand() == "End Turn")
			try {
				currentHero.endTurn();
				UpdateAll();
			} catch (FullHandException | CloneNotSupportedException e1) {
				e1.printStackTrace();
				UpdateAll();
			}
		else if (selected != null) {

			if (curButtons.contains(selected)) {
				int i = curButtons.indexOf(selected);
				if (currentHero.getHand().get(i) instanceof Minion) {
					try {
						currentHero.playMinion((Minion) currentHero.getHand().get(i));
					} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e1) {
						e1.printStackTrace();
						UpdateAll();
					}
					MainScreen.currentHero.remove(selected);
				}

				else if (currentHero.getHand().get(i) instanceof Spell) {
					// TODO Cast Spell for the GUI

				}
				UpdateAll();
			}
			if (selected.getActionCommand().charAt(0) == 'C') {
				if (selected == curMinion) {
					selected.setBackground(defaultButton.getBackground());
					curMinion = null;
					curSelMinion = null;
				} else {
					if (curMinion != null)
						curMinion.setBackground(defaultButton.getBackground());
					curMinion = selected;
					if (curFieldMinions.contains(curMinion)) {
						int j = curFieldMinions.indexOf(curMinion);

						curSelMinion = currentHero.getField().get(j);

						if (OppSelMinion != null)
							try {
								currentHero.attackWithMinion(curSelMinion, OppSelMinion);
								UpdateAll();
							} catch (CannotAttackException | NotYourTurnException | TauntBypassException
									| InvalidTargetException | NotSummonedException e1) {
								UpdateAll();
								e1.printStackTrace();
							}

						curMinion.setBackground(Color.GREEN);
					}

				}
			} else if (selected.getActionCommand().charAt(0) == 'O') {

				if (selected == oppMinion) {
					selected.setBackground(defaultButton.getBackground());
					oppMinion = null;
					OppSelMinion = null;
				} else {
					if (oppMinion != null)
						oppMinion.setBackground(defaultButton.getBackground());

					oppMinion = selected;

					if (OppFieldMinions.contains(oppMinion)) {

						int j = OppFieldMinions.indexOf(oppMinion);

						OppSelMinion = opponent.getField().get(j);

						if (curSelMinion != null)
							try {
								currentHero.attackWithMinion(curSelMinion, OppSelMinion);
								UpdateAll();

							} catch (CannotAttackException | NotYourTurnException | TauntBypassException
									| InvalidTargetException | NotSummonedException e1) {
								UpdateAll();

								e1.printStackTrace();
							}

						oppMinion.setBackground(Color.RED);
					}

				}
			}
		}

	}

}
