package monopoly.logic;

import monopoly.RightPanelGUI;

import com.badlogic.gdx.Gdx;

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
		Gdx.app.log("", "You were taxed in " + tax + " dollars!");
		RightPanelGUI.getSharedInstance().showMessage("You were taxed in " + tax + " dollars!");
		player.playerCreditCard.credit(tax);
	}
}
