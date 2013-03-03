package monopoly.logic;

public class FreeStop extends Space{
	
	public FreeStop(int spaceNumber)
	{
		super(SpaceType.FreeStop, spaceNumber, "FreeStop");
	}
	
	@Override
	public void effect(Player player)
	{
		
	}
}
