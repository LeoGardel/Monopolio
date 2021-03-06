package monopoly.logic;

import java.io.IOException;

import monopoly.RightPanelGUI;
import monopoly.objects.Element;
import monopoly.objects.Object;

public class Pawn
{
	public int currentSpace;
	public Player owner;
	Object pawnModel;
	public Element pawnNode;
	
	public int ID;
	
	public Pawn(Color color, int pawnID, Player pawnOwner)
	{
		try
		{
			pawnModel = new Object("pawn/pawn", "pawn/pawn_" + color.toString() + ".png", false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pawnNode = new Element(pawnModel);
		
		currentSpace = 0;
		
		this.ID = pawnID;
		this.owner = pawnOwner;
		Board.getSharedInstance().spaces.get(currentSpace).movePawnToHere(this);
	}
	
	public void goToJail(){
		currentSpace = 10;
		Board.getSharedInstance().spaces.get(currentSpace).movePawnToHere(this);
	}
	
	public void move(int numOfSpaces)
	{
		currentSpace += numOfSpaces;
		if(currentSpace >= Board.getSharedInstance().spaces.size())
		{
			currentSpace -= Board.getSharedInstance().spaces.size();
			RightPanelGUI.getSharedInstance().showMessage("You crossed the start line. \nReceive R$200,00");
			this.owner.playerCreditCard.credit(200);
		}
		Board.getSharedInstance().spaces.get(currentSpace).movePawnToHere(this);
	}
}
