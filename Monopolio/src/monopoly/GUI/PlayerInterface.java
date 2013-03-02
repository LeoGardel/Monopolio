package monopoly.GUI;

import monopoly.Monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerInterface extends InGameGUI
{
	public PlayerInterface()
	{
		//Gdx.app.log("", "Press space key to start! ");
	}
	
	public void effect()
	{
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).endTurn();
		}
		else if (Gdx.input.isKeyPressed(Keys.B))
		{
			Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buyProperty();
		}
		else if (Gdx.input.isKeyPressed(Keys.C))
		{
			Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHouse();
		}
		else if (Gdx.input.isKeyPressed(Keys.H))
		{
			Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHotel();
		}
	}
}
