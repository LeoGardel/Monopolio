package monopoly.logic;

import com.badlogic.gdx.Gdx;

public class Property extends Space
{
	public String name;
	public int price;
	public Player owner;
	public PropertyType type;
	
	public Property(PropertyType propType, int spaceNumber)
	{
		super(SpaceType.Property, spaceNumber);
		this.type = propType;
	}

	@Override
	public void effect(Player player)
	{
		if(owner != null && owner != player)
			payRent(player);
	}
	
	public void buy(Player ownerPlayer)
	{
		if (owner == null && ownerPlayer.playerCreditCard.money >= price)
		{
			this.owner = ownerPlayer;
			owner.playerCreditCard.debit(price);
			ownerPlayer.properties.add(this);
			Gdx.app.log("", "You bought this property for " + price + " dollars!");
			return;
		}
		Gdx.app.log("", "You cannot afford this property, or it's already owned.");
	}
	
	public void payRent(Player player)
	{
		
	}
	
	public void recoverFromMortgage()
	{
		
	}
	
	public void mortgage()
	{
		
	}

}
