package monopoly.logic;

import com.badlogic.gdx.Gdx;

public class PayTax extends Space
{
	private int tax;
	
	public PayTax(int spaceNumber)
	{
		super(SpaceType.PayTax, spaceNumber);
		tax = 200;
	}

	@Override
	public void effect(Player player) 
	{
		Gdx.app.log("", "You were taxed in " + tax + " dollars!");
		player.playerCreditCard.credit(tax);
	}
}
