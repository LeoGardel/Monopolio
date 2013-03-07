package monopoly.logic;

import monopoly.RightPanelGUI;

public class PayTax extends Space
{
	private int tax;
	
	public PayTax(int spaceNumber)
	{
		super(SpaceType.PayTax, spaceNumber, "Pay Tax");
		tax = 200;
	}

	@Override
	public void effect(Player player) 
	{
		RightPanelGUI.getSharedInstance().showMessage("You were taxed in " + tax + " dollars!");
		
		//TODO debit ou credit??
		player.playerCreditCard.debit(tax);
	}
}
