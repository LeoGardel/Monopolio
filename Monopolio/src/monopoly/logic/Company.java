package monopoly.logic;

import com.badlogic.gdx.Gdx;

public class Company extends Property
{
	public int factor;

	public Company(String name, String cost, String mortgage, String fatorMultiplicativo, String spaceNumberString){
		super(PropertyType.Company, Integer.parseInt(spaceNumberString));
		super.price = Integer.parseInt(cost);
		factor = Integer.parseInt(fatorMultiplicativo);
	}
	
	@Override
	public void payRent(Player player)
	{
		int rentValue = player.lastDiceValue * factor;
		
		if(player.playerCreditCard.money > rentValue)
		{
			player.playerCreditCard.debit(rentValue);
			super.owner.playerCreditCard.credit(rentValue);
			Gdx.app.log("", "You paid a rent of " + rentValue + " dollars!");
		}
		
		else
			player.declareBankruptcy();
	}
}
