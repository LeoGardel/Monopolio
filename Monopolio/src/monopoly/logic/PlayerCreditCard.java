package monopoly.logic;

import monopoly.Monopoly;
import monopoly.RightPanelGUI;

public class PlayerCreditCard
{
	public int money;
	
	public PlayerCreditCard(){
		money = 1500;
	}
	
	public void debit(int value){
		money -= value;
		if (Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).playerCreditCard == this) {
			RightPanelGUI.getSharedInstance().setMoneyLabelValue(this.money);
		}
		if (money < 0) {
			Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).declareBankruptcy();
		}
	}
	
	public void credit(int value){
		money += value;
		if (Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).playerCreditCard == this) {
			RightPanelGUI.getSharedInstance().setMoneyLabelValue(this.money);
		}
	}
}
