package monopoly.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public abstract class Space
{
	public int spaceNumber;
	public SpaceType spaceType;
	public int ID;
	
	private Vector3 centerPosition;
	
	public Space(SpaceType type, int spaceNumber)
	{
		this.spaceNumber = spaceNumber;
		spaceType = type;
		
		centerPosition = Board.getSharedInstance().getPositionFromSpace(spaceNumber);
	}
	
	public abstract void effect(Player player);
	
	public void movePawnToHere(Pawn pawn)
	{
		if (pawn.ID == 1)
		{
			pawn.pawnNode.position.set(centerPosition).add(-0.2f, -0.1f, 0f);
		}
		else if (pawn.ID == 2)
		{
			pawn.pawnNode.position.set(centerPosition).add(-0.2f, 0.1f, 0f);
		}
		else if (pawn.ID == 3)
		{
			pawn.pawnNode.position.set(centerPosition).add(0.2f, -0.1f, 0f);
		}
		else if (pawn.ID == 4)
		{
			pawn.pawnNode.position.set(centerPosition).add(0.2f, 0.1f, 0f);
		}
		else if (pawn.ID == 5)
		{
			pawn.pawnNode.position.set(centerPosition).add(0f, 0.3f, 0f);
		}
		else if (pawn.ID == 0)
		{
			pawn.pawnNode.position.set(centerPosition).add(0f, -0.3f, 0f);
		}	
	}
	
	public Vector3 getHousePosition(int slot)
	{
		Vector3 retPos = new Vector3(centerPosition);
		
		if (slot == 1)
		{
			return retPos.add(-0.2f, 0f, 0f);
		}
		else if (slot == 2)
		{
			return retPos.add(0.2f, 0f, 0f);
		}
		else if (slot == 3)
		{
			return retPos.add(0f, -0.2f, 0f);
		}
		else if (slot == 4)
		{
			return retPos.add(0f, 0.2f, 0f);
		}
		
		return retPos;
	}
}
