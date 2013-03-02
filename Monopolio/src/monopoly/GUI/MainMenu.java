package monopoly.GUI;

import monopoly.Monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MainMenu extends InGameGUI
{
	public MainMenu()
	{
		Gdx.app.log("", "Choose number of players (enter a number from 3-6): ");
	}
	
	public void effect()
	{
		if (Gdx.input.isKeyPressed(Keys.NUM_3))
		{
			Gdx.app.log("", "You selected 3 players");
			Monopoly.getSharedInstance().startGame(3);
			Monopoly.getSharedInstance().baseGUI = new SleepState();
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_4))
		{
			Gdx.app.log("", "You selected 4 players");
			Monopoly.getSharedInstance().startGame(4);
			Monopoly.getSharedInstance().baseGUI = new SleepState();
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_5))
		{
			Gdx.app.log("", "You selected 5 players");
			Monopoly.getSharedInstance().startGame(5);
			Monopoly.getSharedInstance().baseGUI = new SleepState();
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_6))
		{
			Gdx.app.log("", "You selected 6 players");
			Monopoly.getSharedInstance().startGame(6);
			Monopoly.getSharedInstance().baseGUI = new SleepState();
		}
	}
}
