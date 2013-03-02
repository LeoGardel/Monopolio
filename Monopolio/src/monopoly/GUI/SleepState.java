package monopoly.GUI;

import monopoly.Monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class SleepState extends InGameGUI
{
	public SleepState()
	{
		Gdx.app.log("", "Press space key to start! ");
	}
	
	public void effect()
	{
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			Monopoly.getSharedInstance().callNextPlayer();
			Monopoly.getSharedInstance().baseGUI = new PlayerInterface();
		}
	}
}
