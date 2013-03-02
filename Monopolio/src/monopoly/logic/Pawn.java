package monopoly.logic;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

import monopoly.objects.InanimatedElement;
import monopoly.objects.InanimatedObject;

public class Pawn
{
	public int currentSpace;
	public Player owner;
	InanimatedObject pawnModel;
	public InanimatedElement pawnNode;
	
	public int ID;
	
	public Pawn(Color color, int pawnID, Player pawnOwner)
	{
		try
		{
			pawnModel = new InanimatedObject("pawn/pawn", "pawn/pawn_" + color.toString() + ".png", false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pawnNode = new InanimatedElement(pawnModel);
		
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
			Gdx.app.log("", "You crossed the start line. Receive U$ 200");
			this.owner.playerCreditCard.credit(200);
		}
		Board.getSharedInstance().spaces.get(currentSpace).movePawnToHere(this);
	}
}
