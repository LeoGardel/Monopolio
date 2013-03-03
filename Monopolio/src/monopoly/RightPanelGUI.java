package monopoly;

import monopoly.GUI.ButtonForm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class RightPanelGUI extends Stage
{	
	public static final int elementsMinSize = 5; 
	public static final int elementsMinPadding = 10; 
	private static RightPanelGUI sharedInstance;
	
	public static RightPanelGUI getSharedInstance()
	{
		if (sharedInstance == null) {
			sharedInstance = new RightPanelGUI();
		}
		return sharedInstance;
	}
	
	public RightPanelGUI(){
		super((Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor)), Gdx.graphics.getHeight(), false);
		
		ButtonForm.loadNinesForButtons();

		this.addActor(new ButtonForm("Botoes Super", 1,5,1,1));
		this.addActor(new ButtonForm("Lindos", 2,5,1,2));
		this.addActor(new ButtonForm("Gostosos", 2,5,2,2));
		this.addActor(new ButtonForm("Cheirosos", 3,5,1,2));
		this.addActor(new ButtonForm("e tudo +", 3,5,2,2));
		this.addActor(new ButtonForm("CLIQUE AQUI", 5,5,1,1, 200, 70) {
			@Override
			public void effect() {
				setText("VALEU LEK! E NOS");
			}
		});
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		for (Actor actor : this.getActors()) {
			if(testMousePosition(x, y, actor))
			{
				if (actor instanceof ButtonForm) {
					((ButtonForm) actor).touchDown();
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean testMousePosition(int x, int y, Actor actor)
	{
		if(x > actor.x && x < (actor.x + actor.width) && (Gdx.graphics.getHeight() - y) > actor.y && (Gdx.graphics.getHeight() - y) < (actor.y + actor.height))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		for (Actor actor : this.getActors()) {
			if(testMousePosition(x, y, actor))
			{
				if (actor instanceof ButtonForm) {
					if (actor.equals(ButtonForm.clickedButton)) {
						((ButtonForm) actor).touchUp();
						return true;
					}
					else {
						break;
					}
				}
			}
		}
		
		ButtonForm.clearClicked();
		
		return false;
	}
	
	@Override
	public boolean touchMoved(int x, int y)
	{
		for (Actor actor : this.getActors()) {
			if(testMousePosition(x, y, actor))
			{
				if (actor instanceof ButtonForm) {
					((ButtonForm) actor).setAsHovered();
					return true;
				}
			}
		}

		ButtonForm.clearHovered();
		
		return false;
	}
	
	public void resize() {
		float newXSize = Gdx.graphics.getWidth()*(1 - Monopoly.splitFactor);
		float newYSize = Gdx.graphics.getHeight();
		
		this.setViewport(newXSize, newYSize, true);
		for (Actor actor : this.getActors()) {
			if (actor instanceof ButtonForm) {
				((ButtonForm) actor).resize(newXSize, newYSize);
			}
		}
	}
}
