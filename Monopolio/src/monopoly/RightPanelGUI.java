package monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class RightPanelGUI extends Stage
{	
	LabelStyle labelStyle;
	Skin skin;	
	
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
		
		labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont();
		labelStyle.fontColor = new Color(1, 0, 0, 1);
		
		skin = new Skin();
		skin.addStyle("default", labelStyle);
		Label label = new Label("teste teste teste teste teste", skin);
		this.addActor(label);
	}
}
