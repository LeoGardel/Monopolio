package monopoly.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;

import monopoly.objects.InanimatedElement;
import monopoly.objects.InanimatedObject;

public class Neighbourhood extends Property
{
	private int rentValue;
	private int houseCostValue;
	private int hotelCostValue;
	private int numberOfHouses;
	private int mortgageValue;
	private Boolean hotelExists;
	private int colorValue;
	
	private InanimatedObject houseModel;
	private ArrayList<InanimatedElement> houseNodes = new ArrayList<InanimatedElement>();
	
	private InanimatedObject hotelModel;
	private InanimatedElement hotelNode;
	
	public Neighbourhood(String name, String cost, String color, 
			String houseCost, String hotelCost, String mortgage, String rent, String spaceNumberString)
	{
		super(PropertyType.Neighbourhood, Integer.parseInt(spaceNumberString));
		super.name = name;
		super.price = Integer.parseInt(cost);;
		rentValue = Integer.parseInt(rent);
		houseCostValue = Integer.parseInt(houseCost);
		hotelCostValue = Integer.parseInt(hotelCost);
		mortgageValue = Integer.parseInt(mortgage);
		colorValue = Integer.parseInt(color);
		hotelExists = false;
		numberOfHouses = 0;
	}
	
	public int getRentValue()
	{
		return rentValue;
	}
	
	public int getHouseCostValue()
	{
		return houseCostValue;
	}
	
	public int getHotelCostValue()
	{
		return hotelCostValue;
	}
	
	public boolean buildHouse(Player player) 
	{
		if (super.owner != player ||
			this.hotelExists ||
			this.numberOfHouses == 4 ||
			player.playerCreditCard.money < this.houseCostValue)
		{
			return false;
		}
		
		Board boardInstance = Board.getSharedInstance();
		Iterator<Space>itr = boardInstance.spaces.iterator();
	    while (itr.hasNext()) {
	    	Space element = (Space) itr.next();
	    	if(element.spaceType != SpaceType.Property){
	    		continue;
	    	}
	    	if( ((Property)element).type != PropertyType.Neighbourhood){
	    		continue;
	    	}
	    	if ( ((Neighbourhood)element).colorValue != this.colorValue )
	    	{
	    		continue;
	    	}
	    	if ( ((Property)element).owner != player ){
	    		return false;
	    	}
	    }
		
		
		player.playerCreditCard.debit(this.houseCostValue);
		this.numberOfHouses++;
		
		
		loadHouseModel();
		return true;
	}
	
	private void loadHouseModel()
	{
		try
		{
			houseModel = new InanimatedObject("house/house", "house/house2.png", false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		InanimatedElement houseNode = new InanimatedElement(houseModel);
		
		houseNode.position.set(getHousePosition(this.numberOfHouses));
		
		houseNodes.add(houseNode);
	}
	
	
	public boolean buildHotel(Player player)
	{
		if (numberOfHouses != 4 ||
			super.owner != player ||
			this.hotelExists ||
			player.playerCreditCard.money < this.houseCostValue)
		{
			return false;
		}
				
		Board boardInstance = Board.getSharedInstance();
		Iterator<Space>itr = boardInstance.spaces.iterator();
		while (itr.hasNext()) {
		Space element = (Space) itr.next();
	   	if(element.spaceType != SpaceType.Property){
	    		continue;
	    	}
	    	if( ((Property)element).type != PropertyType.Neighbourhood){
	    		continue;
	    	}
	    	if ( ((Neighbourhood)element).colorValue != this.colorValue )
	    	{
	    		continue;
	    	}
	    	if ( ((Neighbourhood)element).owner != player ){
	    		return false;
	    	}
	    }
			
		player.playerCreditCard.debit(this.hotelCostValue);
		this.numberOfHouses = 0;
		this.hotelExists = true;
	    
		loadHotelModel();
		Iterator<InanimatedElement>itr2 = houseNodes.iterator();
		while (itr2.hasNext()) {
			( (InanimatedElement)itr2.next() ).position.set(0, 0, 100000);
		}
		
		return true;
	}
	
	private void loadHotelModel()
	{
		try
		{
			hotelModel = new InanimatedObject("hotel/hotel", "hotel/hotel2.png", false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		hotelNode = new InanimatedElement(hotelModel);
		
		hotelNode.position.set(getHousePosition(0));
		hotelNode.setVisible();
	}
	
	@Override
	public void payRent(Player player)
	{
		if(player.playerCreditCard.money > rentValue){
			player.playerCreditCard.debit(rentValue);
			super.owner.playerCreditCard.credit(rentValue);
			Gdx.app.log("", "You paid a rent of " + rentValue + " dollars!");
		}
		else
			player.declareBankruptcy();
	}
}
