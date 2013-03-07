package monopoly.logic;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector3;

import monopoly.Monopoly;
import monopoly.RightPanelGUI;

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
	public boolean bankruptcy;
	
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
		this.bankruptcy = false;
	}

	public void rollDice(){
		int plays = 0;
		int sum = 0;
		
		if(isArrested){
			tentarSairDaPrisao();
		} else {
			rollDiceStep(plays, sum);
		}
	}

	private void rollDiceStep(int plays, int sum){
		int result1 = throwDice();
		int result2 = throwDice();
		RightPanelGUI.getSharedInstance().showDiceResults(result1, result2);
		
		sum += (result1 + result2);
		
		if(result1 == result2)
			plays ++;
		
		if(plays == 3){
			isArrested = true;
			this.playerPawn.goToJail();
			RightPanelGUI.getSharedInstance().showMessage("You are arrested!");
			return;
		}
		
		if(result1 == result2)
		{
			rollDiceStep(plays, sum);
			return;
		}
		
		boolean arrest = false;
		if(playerPawn.currentSpace + sum == 30)
		{
			arrest = true;
		}
		this.playerPawn.move(sum);
	
		lastDiceValue = sum;		gameBoard.spaces.get(playerPawn.currentSpace).effect(this);
		
		showWherePlayerStopped(arrest);
		
	}
	
	private int throwDice() {
		return generator.nextInt(6) + 1;
	}
	
	private void showWherePlayerStopped(boolean arrest) {
		if(arrest){
			RightPanelGUI.getSharedInstance().showSpaceType("Prison");
			RightPanelGUI.getSharedInstance().setElse();
		}
		else{
			String str = gameBoard.spaces.get(playerPawn.currentSpace).name;
			str = str.replaceAll("_", " ");
			
			RightPanelGUI.getSharedInstance().showSpaceType(str);
			if(str == "FreeStop" || str == "Pay Tax")
			{
				RightPanelGUI.getSharedInstance().setElse();
			}
			else
			{
				Property prop = ((Property)gameBoard.spaces.get(playerPawn.currentSpace));
				if(prop.type.toString() == "Company")
				{
					if (prop.owner == null || prop.owner.playerID == Monopoly.getSharedInstance().currentPlayer)
						RightPanelGUI.getSharedInstance().setAvailableCompany();
					else
						RightPanelGUI.getSharedInstance().setOwnedCompany();
				}
				else if (prop.type.toString() == "Neighbourhood")
				{
					if(prop.owner == null)
						RightPanelGUI.getSharedInstance().setAvailableNeighbourhood();
					else if(prop.owner.playerID == Monopoly.getSharedInstance().currentPlayer)
						RightPanelGUI.getSharedInstance().setOwnerNeighbourhood();
					else
						RightPanelGUI.getSharedInstance().setOwnedNeighbourhood();
				}
			}
		}
	}

	private void tentarSairDaPrisao() {
		int result1 = throwDice();
		int result2 = throwDice();
		RightPanelGUI.getSharedInstance().showDiceResults(result1, result2);
		
		leavePrisonAttempts++;
		
		if(result1 == result2){
			isArrested = false;
			leavePrisonAttempts = 0;
			RightPanelGUI.getSharedInstance().showMessage("You are free");
			this.playerPawn.move(result1 + result2);
			lastDiceValue = result1 + result2;
			gameBoard.spaces.get(playerPawn.currentSpace).effect(this);
			
			showWherePlayerStopped(false);
		}
		else if(leavePrisonAttempts == 3)
		{
			if(playerCreditCard.money < 200)
			{
				declareBankruptcy();
			}
			else
			{
				isArrested = false;
				leavePrisonAttempts = 0;
				RightPanelGUI.getSharedInstance().showSpaceType("Prison");
				RightPanelGUI.getSharedInstance().showMessage("You are free, but you have to pay 200...");
				
				playerCreditCard.debit(200);
			}
		}
		else {
			RightPanelGUI.getSharedInstance().showSpaceType("Prison");
			RightPanelGUI.getSharedInstance().showMessage("You still arrested");
		}
	}

	public void declareBankruptcy() {
		RightPanelGUI.getSharedInstance().showMessage("You declared Bankruptcy.");
		this.bankruptcy = true;
		Monopoly.getSharedInstance().numOfPlayersOut++;
		playerPawn.pawnNode.position.set(new Vector3(6-4.1f - (Monopoly.getSharedInstance().numOfPlayersOut * 0.3f), 7-4.1f, 0f));
	}
	
	public void buyProperty()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			((Property)gameBoard.spaces.get(playerPawn.currentSpace) ).buy(this);
		}
		else{
			RightPanelGUI.getSharedInstance().showMessage("You cannot buy this property.");
		}
	}
	
	public void buildHouse()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			if (((Property)gameBoard.spaces.get(playerPawn.currentSpace)).type == PropertyType.Neighbourhood){
				if(((Neighbourhood)gameBoard.spaces.get(playerPawn.currentSpace)).buildHouse(this)){
					RightPanelGUI.getSharedInstance().showMessage("Your house was built.");
					return;
				}
			}
		}
		RightPanelGUI.getSharedInstance().showMessage("You can't build a house over there.");
	}
	
	public void buildHotel()
	{
		if(gameBoard.spaces.get(playerPawn.currentSpace).spaceType == SpaceType.Property){
			if (((Property)gameBoard.spaces.get(playerPawn.currentSpace)).type == PropertyType.Neighbourhood){
				if(((Neighbourhood)gameBoard.spaces.get(playerPawn.currentSpace)).buildHotel(this)){
					RightPanelGUI.getSharedInstance().showMessage("Your hotel was built.");
					return;
				}
			}
		}
		RightPanelGUI.getSharedInstance().showMessage("You can't build a hotel over there.");
	}
	
	public void endTurn()
	{
		Monopoly.getSharedInstance().callNextPlayer();
	}
}
