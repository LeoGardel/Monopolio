package monopoly.logic;

import com.badlogic.gdx.Gdx;

public class GoToPrison extends Space
{
	public GoToPrison(int spaceNumber)
	{
		super(SpaceType.GoToJail, spaceNumber);
	}
	
	@Override
	public void effect(Player player)
	{
		player.isArrested = true;
		Gdx.app.log("", "You are arrested!");
		player.playerPawn.goToJail();
	}
}
