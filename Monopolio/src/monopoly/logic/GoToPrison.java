package monopoly.logic;

import monopoly.RightPanelGUI;

import com.badlogic.gdx.Gdx;

public class GoToPrison extends Space
{
	public GoToPrison(int spaceNumber)
	{
		super(SpaceType.GoToJail, spaceNumber, "Go to Jail");
	}
	
	@Override
	public void effect(Player player)
	{
		player.isArrested = true;
		Gdx.app.log("", "You are arrested!");
		RightPanelGUI.getSharedInstance().showMessage("You are arrested!");
		player.playerPawn.goToJail();
	}
}
