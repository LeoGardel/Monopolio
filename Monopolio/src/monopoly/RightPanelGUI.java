package monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ComboBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;


public class RightPanelGUI extends Stage
{	
	ButtonStyle btStyle;
	Button button;
	NinePatch nine;
	NinePatch nine2;
	NinePatch nine3;
	
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
		
		nine = new NinePatch(new Texture(Gdx.files.internal("resources/background-button.png")), 0, 0, 0, 0);
		nine2 = new NinePatch(new Texture(Gdx.files.internal("resources/background-button2.png")), 0, 0, 0, 0);
		nine3 = new NinePatch(new Texture(Gdx.files.internal("resources/background-button3.png")), 0, 0, 0, 0);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont();
		labelStyle.fontColor = new Color(1, 0, 0, 1);
		Label label = new Label("teste teste", labelStyle);
		
		btStyle = new ButtonStyle();
		btStyle.up = nine;
		button = new Button(btStyle);
		
		button.y = 500;
		button.x = 30;
		button.width = 128;
		button.height = 32;
		
		button.add(label);
		this.addActor(button);
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if(testMousePosition(x, y, this.button))
		{
			btStyle.up = nine3;
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
		if(testMousePosition(x, y, this.button))
		{
			btStyle.up = nine2;
		}
		else
		{
			btStyle.up = nine;
		}
		return false;
	}
	
	@Override
	public boolean touchMoved(int x, int y)
	{
		if(testMousePosition(x, y, this.button))
		{
			btStyle.up = nine2;
		}
		else
		{
			btStyle.up = nine;
		}
		return false;
	}
}
