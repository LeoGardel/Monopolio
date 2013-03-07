package monopoly.logic;

import monopoly.RightPanelGUI;

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
		RightPanelGUI.getSharedInstance().showMessage("You are arrested!");
		player.playerPawn.goToJail();
	}
}
