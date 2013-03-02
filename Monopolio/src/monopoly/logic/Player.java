package monopoly.logic;

import java.util.ArrayList;
import java.util.Random;

import monopoly.Monopoly;

import com.badlogic.gdx.Gdx;

public class Player
{
	private Random generator = new Random();
	public Color playerColor;
	public String playerName;
	public int playerID;
	public Pawn playerPawn;
	public int numberOfSDPcards;
	public boolean isArrested;
	public PlayerCreditCard playerCreditCard;
	public  ArrayList<Property> properties;
	private int leavePrisonAttempts;
	private Board gameBoard;
	public int lastDiceValue;
	
	public Player(String name, int id, Color color)
	{
		this.playerName = name;
		this.playerColor = color;
		this.playerID = id;
		this.numberOfSDPcards = 0;
		this.isArrested = false;
		this.leavePrisonAttempts = 0;
		this.properties = new ArrayList<Property>();
		playerPawn = new Pawn(color, id, this);
		playerCreditCard = new PlayerCreditCard();
		this.gameBoard = Board.getSharedInstance();
	}

	public void rollDice(){
		this.showInformationMessageToUser("I'm player " + this.playerID + " and it's my turn! My balance: U$" + this.playerCreditCard.money);
		int plays = 0;
		int sum = 0;
		
		if(isArrested)
			tentarSairDaPrisao();
		else
			rollDiceStep(plays, sum);
	}

	private void rollDiceStep(int plays, int sum){
		int result1 = generator.nextInt(6) + 1;
		int result2 = generator.nextInt(6) + 1;
		
		this.showInformationMessageToUser("You rolled " + result1 + " and " + result2 + " .");
		
		sum += (result1 + result2);
		
		if(result1 == result2)
			plays ++;
		
		if(plays == 3){
			isArrested = true;
			this.playerPawn.goToJail();
			this.showInformationMessageToUser("You are arrested!");
			return;
		}
		
		if(result1 == result2)
		{
			rollDiceStep(plays, sum);
			return;
		}
		
		this.playerPawn.move(sum);
	
		lastDiceValue = sum;		gameBoard.spaces.get(playerPawn.currentSpace).effect(this);
		
	}
	
	private void tentarSairDaPrisao() {
		int result1 = generator.nextInt(6) + 1;
		int result2 = generator.nextInt(6) + 1;
		this.showInformationMessageToUser("You rolled " + result1 + " and " + result2 + " .");
		
		leavePrisonAttempts++;
		
		if(result1 == result2){
			isArrested = false;
			leavePrisonAttempts = 0;
			this.showInformationMessageToUser("You are free!");
			this.playerPawn.move(result1 + result2);
		}
		
		if(leavePrisonAttempts == 3)
		{
			if(playerCreditCard.money < 500)
			{
				declareBankruptcy();
			}
			else
			{
				isArrested = false;
				leavePrisonAttempts = 0;
				this.showInformationMessageToUser("You are free, but you have to pay 500...");
				playerCreditCard.debit(500);
			}
		}
	}

	public void declareBankruptcy() {
		// TODO Auto-generated method stub
		
	}
	
	public void showInformationMessageToUser(String message)
	{
		Gdx.app.log("", message);
	}
	
	public void buyProperty()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			((Property)gameBoard.spaces.get(playerPawn.currentSpace) ).buy(this);
		}
		else{
			Gdx.app.log("", "You cannot buy this property.");
		}
	}
	
	public void buildHouse()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			if (((Property)gameBoard.spaces.get(playerPawn.currentSpace)).type == PropertyType.Neighbourhood){
				if(((Neighbourhood)gameBoard.spaces.get(playerPawn.currentSpace)).buildHouse(this)){
					this.showInformationMessageToUser("Your house was built.");
					return;
				}
			}
		}
		this.showInformationMessageToUser("You can't build a house over there.");
	}
	
	public void buildHotel()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			if (((Property)gameBoard.spaces.get(playerPawn.currentSpace)).type == PropertyType.Neighbourhood){
				if(((Neighbourhood)gameBoard.spaces.get(playerPawn.currentSpace)).buildHotel(this)){
					this.showInformationMessageToUser("Your hotel was built.");
					return;
				}
			}
		}
		this.showInformationMessageToUser("You can't build a hotel over there.");
	}
	
	public void endTurn()
	{
		this.showInformationMessageToUser("Turn is over. My balance: U$ " + this.playerCreditCard.money);
		Monopoly.getSharedInstance().callNextPlayer();
	}
}
