package monopoly;

import monopoly.GUI.ButtonForm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actors.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


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
		
		Image img = new Image("bg", new Texture(Gdx.files.internal("resources/background-GUI.png")));
		this.addActor(img);
		
		ButtonForm.loadNinesForButtons();
		
		setFirstMoment();
		/*
		this.addActor(new ButtonForm("Botoes Super", 1,5,1,1));
		this.addActor(new ButtonForm("Lindos", 2,5,1,2));
		this.addActor(new ButtonForm("Gostosos", 2,5,2,2));
		this.addActor(new ButtonForm("Cheirosos", 3,5,1,2));
		this.addActor(new ButtonForm("e tudo +", 3,5,2,2));
		this.addActor(new ButtonForm("CLIQUE AQUI", 4,5,1,1, 200, 70) {
			@Override
			public void effect() {
				setText("VALEU LEK! E NOS");
			}
		});*/
	}
	
	private void setFirstMoment() {
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1));
		Label label = new Label("Choose number of Player:", labelStyle);
		label.y = Gdx.graphics.getHeight() - 60;
		label.x = 10;
		this.addActor(label);
		
		for(int i = 3; i<7; i++){
			this.addActor(new ButtonForm(String.valueOf(i), (int) (3 + Math.floor((i-3)/2)), 12, 1+(1-i%2), 2) {
				@Override
				public void effect(){
					//Monopoly.getSharedInstance().startGame(i);
				}
			});
		}
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
