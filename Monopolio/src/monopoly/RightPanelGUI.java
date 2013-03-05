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
	public static final int logoHeight = 120;
	public static final float logoFactor = ( 1024 - logoHeight) / 1024;
	private static RightPanelGUI sharedInstance;
	private Image img;
	private Label welcomePlayerMessage;
	private Label diceResults;
	private Label moneyLabel;
	private Label messageLabel;
	private Label spaceTypeLabel;
	private ButtonForm terminarRodada;
	private ButtonForm comprarCasa;
	private ButtonForm comprarHotel;
	private ButtonForm comprarPropriedade;
	
	public static RightPanelGUI getSharedInstance()
	{
		if (sharedInstance == null) {
			sharedInstance = new RightPanelGUI();
		}
		return sharedInstance;
	}
	
	public RightPanelGUI(){
		super((Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor)), Gdx.graphics.getHeight(), false);
		
		img = new Image("bg", new Texture(Gdx.files.internal("resources/background-GUI.png")));
		
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1));
		
		welcomePlayerMessage = new Label("", labelStyle);
		welcomePlayerMessage.width = (Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor));
		welcomePlayerMessage.y = Gdx.graphics.getHeight() - 80;
		welcomePlayerMessage.x = 10;
		
		diceResults = new Label("", labelStyle);
		diceResults.x = welcomePlayerMessage.x;
		
		moneyLabel = new Label("", labelStyle);
		setMoneyLabelPosition();
		
		messageLabel = new Label("", labelStyle);
		messageLabel.x = 10;
		messageLabel.width = (Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor));
		
		spaceTypeLabel = new Label("", labelStyle);
		
		ButtonForm.loadNinesForButtons();
		
		setFirstMoment();
	}
	
	private void setFirstMoment() {
		this.addActor(img);
		
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1));
		Label label = new Label("Choose number of Players:", labelStyle);
		label.y = Gdx.graphics.getHeight() - 60;
		label.x = 10;
		this.addActor(label);
		
		
		this.addActor(new ButtonForm("3", 4, 12, 1, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(3);
			}
		});
		this.addActor(new ButtonForm("4", 4, 12, 2, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(4);
			}
		});
		this.addActor(new ButtonForm("5", 5, 12, 1, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(5);
			}
		});
		this.addActor(new ButtonForm("6", 5, 12, 2, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(6);
			}
		});
	}
	
	private void setMoneyLabelPosition() {
		moneyLabel.y = - 20 +  ( Gdx.graphics.getHeight() * ( 1024 - logoHeight) / 1024);
		moneyLabel.x = (Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor)) - moneyLabel.getPrefWidth() - 10;
	}

	protected void setInitTurnMoment() {
		this.addActor(img);
		this.addActor(welcomePlayerMessage);
		this.addActor(diceResults);
		this.addActor(moneyLabel);
		this.addActor(messageLabel);
		this.addActor(spaceTypeLabel);
		setSpaceButtons();
	}
	
	private void setSpaceButtons(){
		terminarRodada = new ButtonForm("Terminar Rodada", 8, 8, 1, 1, 256, 64){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).endTurn();
			}
		};
		this.addActor(terminarRodada);
		
		comprarPropriedade = new ButtonForm("Comprar Propriedade", 7, 8, 1, 1, 256, 64){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buyProperty();
				
				}
			}
		};
		this.addActor(comprarPropriedade);
		
		comprarCasa = new ButtonForm("Comprar Casa", 6, 8, 1, 1, 256, 64){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHouse();
				}
			}
		};
		this.addActor(comprarCasa);
		
		comprarHotel = new ButtonForm("Comprar Hotel", 7, 8, 1, 1, 256, 64){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHotel();
				}
			}
		};
		this.addActor(comprarHotel);
	}
	
	public void setAvailableCompany() {
		terminarRodada.visible = true;
		comprarPropriedade.visible = true;
		comprarCasa.visible = false;
		comprarHotel.visible = false;
	}
	
	public void setOwnedCompany() {
		terminarRodada.visible = true;
		comprarPropriedade.visible = false;
		comprarCasa.visible = false;
		comprarHotel.visible = false;
	}
	
	public void setAvailableNeighbourhood() {
		terminarRodada.visible = true;
		comprarPropriedade.visible = true;
		comprarCasa.visible = false;
		comprarHotel.visible = false;
	}
	
	public void setOwnerNeighbourhood() {
		terminarRodada.visible = true;
		comprarPropriedade.visible = false;
		comprarCasa.visible = true;
		comprarHotel.visible = true;
	}
	
	public void setOwnedNeighbourhood(){
		terminarRodada.visible = true;
		comprarPropriedade.visible = false;
		comprarCasa.visible = false;
		comprarHotel.visible = false;
	}
	
	public void setElse() {
		terminarRodada.visible = true;
		comprarPropriedade.visible = false;
		comprarCasa.visible = false;
		comprarHotel.visible = false;
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
		
		img.height = Gdx.graphics.getHeight();
		img.width = Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor);
		
		setMoneyLabelPosition();
	}
	
	public void showActualPlayer(int playerID, int money, int result1, int result2, boolean diceAgain) {
		if(diceAgain){
			diceResults.setText(diceResults.getText() + "\n" + "You rolled " + result1 + "and " + result2);
		}
		else
		{
			messageLabel.setText("");
			welcomePlayerMessage.setText("I'm player " + (playerID+1) + " and it's my turn!");
			
			diceResults.setText("You rolled " + result1 + " and " + result2);
			
			setMoneyLabelValue(money);
			setMoneyLabelPosition();
		}
		
		diceResults.y = welcomePlayerMessage.y - 20 - diceResults.getPrefHeight();
	}

	public void setMoneyLabelValue(int money) {
		moneyLabel.setText("Current Money: R$" + money + ",00");
	}

	public void showMessage(String string) {
		if (!(messageLabel.getText().contains(string))){
		messageLabel.setText(messageLabel.getText() + "\n" + string);
		messageLabel.y = spaceTypeLabel.y - messageLabel.getPrefHeight();
		}
	}

	public void showSpaceType(String str) {
		spaceTypeLabel.setText("You are at " + str);
		spaceTypeLabel.y = diceResults.y - 30;
		spaceTypeLabel.x = 10;
		
		messageLabel.y = spaceTypeLabel.y - messageLabel.getPrefHeight();
	}
}
