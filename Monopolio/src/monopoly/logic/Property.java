package monopoly.logic;

import monopoly.RightPanelGUI;

import com.badlogic.gdx.Gdx;

public class Property extends Space
{
	public int price;
	public Player owner;
	public PropertyType type;
	
	public Property(PropertyType propType, int spaceNumber, String name)
	{
		super(SpaceType.Property, spaceNumber, name);
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
			RightPanelGUI.getSharedInstance().showMessage("You bought this property for " + price + " dollars!");
			return;
		}
		Gdx.app.log("", "You cannot afford this property, or it's already owned.");
		RightPanelGUI.getSharedInstance().showMessage("You cannot buy this property.");
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
