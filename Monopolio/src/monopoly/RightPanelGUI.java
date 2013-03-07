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
	private Image backgroundImage;
	private Label welcomePlayerMessage;
	private Label diceResults;
	private Label moneyLabel;
	private Label messageLabel;
	private Label spaceTypeLabel;
	private Label choseNumOfPlayersLabel;
	public LabelStyle labelStyle;
	private ButtonForm endTurn;
	private ButtonForm buildHouse;
	private ButtonForm buildHotel;
	private ButtonForm buyProperty;
	
	public static RightPanelGUI getSharedInstance()
	{
		if (sharedInstance == null) {
			sharedInstance = new RightPanelGUI();
		}
		return sharedInstance;
	}
	
	public RightPanelGUI(){
		super((Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor)), Gdx.graphics.getHeight(), false);
		
		backgroundImage = new Image("bg", new Texture(Gdx.files.internal("resources/background-GUI.png")));
		
		this.labelStyle = new LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1));
		
		choseNumOfPlayersLabel = new Label("Choose number of Players:", labelStyle);
		
		welcomePlayerMessage = new Label("", labelStyle);
		
		diceResults = new Label("", labelStyle);
		
		moneyLabel = new Label("", labelStyle);
		
		messageLabel = new Label("", labelStyle);
		
		spaceTypeLabel = new Label("", labelStyle);
		
		setLabelsPosition();
		
		ButtonForm.loadNinesForButtons();
		
		setFirstMoment();
	}
	
	private void setFirstMoment() {
		this.addActor(backgroundImage);
		this.addActor(choseNumOfPlayersLabel);
		
		this.addActor(new ButtonForm("3", 8, 12, 1, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(3);
				choseNumOfPlayersLabel = null;
			}
		});
		this.addActor(new ButtonForm("4", 8, 12, 2, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(4);
				choseNumOfPlayersLabel = null;
			}
		});
		this.addActor(new ButtonForm("5", 9, 12, 1, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(5);
				choseNumOfPlayersLabel = null;
			}
		});
		this.addActor(new ButtonForm("6", 9, 12, 2, 2){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().startGame(6);
				choseNumOfPlayersLabel = null;
			}
		});
	}
	
	private void setLabelsPosition() {
		float totalX = (Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor));
		float totalY = ( Gdx.graphics.getHeight() * ( 1024 - logoHeight) / 1024);
		float defaultLabelPerfHeight = elementsMinPadding;
		float defaultLabelX = elementsMinPadding;
		
		if(choseNumOfPlayersLabel != null) {
			choseNumOfPlayersLabel.x = defaultLabelX;
			choseNumOfPlayersLabel.y = totalY - defaultLabelPerfHeight - (6 * elementsMinPadding);
		}
		
		moneyLabel.x = totalX - moneyLabel.getPrefWidth() - elementsMinPadding;
		moneyLabel.y = totalY - defaultLabelPerfHeight - elementsMinPadding;
		
		welcomePlayerMessage.x = defaultLabelX;
		welcomePlayerMessage.y = moneyLabel.y - (3 * defaultLabelPerfHeight) - elementsMinPadding;
		
		diceResults.x = defaultLabelX;
		diceResults.y = welcomePlayerMessage.y - (4 * defaultLabelPerfHeight) - elementsMinPadding;
		
		spaceTypeLabel.x = defaultLabelX;
		spaceTypeLabel.y = diceResults.y - (3 * defaultLabelPerfHeight) - elementsMinPadding;
		
		messageLabel.x = defaultLabelX;
		messageLabel.y = spaceTypeLabel.y - (6 * defaultLabelPerfHeight) - elementsMinPadding;
		
	}

	protected void setGameActors() {
		this.clear();
		this.addActor(backgroundImage);
		this.addActor(welcomePlayerMessage);
		this.addActor(diceResults);
		this.addActor(moneyLabel);
		this.addActor(messageLabel);
		this.addActor(spaceTypeLabel);
		setActionButtons();
	}
	
	private void setActionButtons(){
		int totalRows = 8;
		int actionButtonWidth = (int) (Monopoly.applicationInitialWidth * ( 1 - Monopoly.splitFactor));
		int actionButtonHeight = (int) (Monopoly.applicationInitialHeight / totalRows);
		endTurn = new ButtonForm("End Turn", totalRows, totalRows, 1, 1, actionButtonWidth, actionButtonHeight){
			@Override
			public void effect(){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).endTurn();
			}
		};
		this.addActor(endTurn);
		
		buyProperty = new ButtonForm("Buy Property", totalRows - 1, totalRows, 1, 1, actionButtonWidth, actionButtonHeight){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buyProperty();
				
				}
			}
		};
		this.addActor(buyProperty);
		
		buildHouse = new ButtonForm("Build House", totalRows - 2, totalRows, 1, 1, actionButtonWidth, actionButtonHeight){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHouse();
				}
			}
		};
		this.addActor(buildHouse);
		
		buildHotel = new ButtonForm("Build Hotel", totalRows - 1, totalRows, 1, 1, actionButtonWidth, actionButtonHeight){
			@Override
			public void effect(){
				if (this.visible){
				Monopoly.getSharedInstance().players.get(Monopoly.getSharedInstance().currentPlayer).buildHotel();
				}
			}
		};
		this.addActor(buildHotel);
	}
	
	public void setAvailableCompany() {
		endTurn.visible = true;
		buyProperty.visible = true;
		buildHouse.visible = false;
		buildHotel.visible = false;
	}
	
	public void setOwnedCompany() {
		endTurn.visible = true;
		buyProperty.visible = false;
		buildHouse.visible = false;
		buildHotel.visible = false;
	}
	
	public void setAvailableNeighbourhood() {
		endTurn.visible = true;
		buyProperty.visible = true;
		buildHouse.visible = false;
		buildHotel.visible = false;
	}
	
	public void setOwnerNeighbourhood() {
		endTurn.visible = true;
		buyProperty.visible = false;
		buildHouse.visible = true;
		buildHotel.visible = true;
	}
	
	public void setOwnedNeighbourhood(){
		endTurn.visible = true;
		buyProperty.visible = false;
		buildHouse.visible = false;
		buildHotel.visible = false;
	}
	
	public void setElse() {
		endTurn.visible = true;
		buyProperty.visible = false;
		buildHouse.visible = false;
		buildHotel.visible = false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		for (Actor actor : this.getActors()) {
			if(testMousePosition(x, y, actor))
			{
				if (actor instanceof ButtonForm && actor.visible) {
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
				if (actor instanceof ButtonForm && actor.visible) {
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
				if (actor instanceof ButtonForm && actor.visible) {
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
		
		backgroundImage.height = Gdx.graphics.getHeight();
		backgroundImage.width = Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor);
		
		setLabelsPosition();
	}
	
	public void showActualPlayer(int playerID) {
		welcomePlayerMessage.setText("I'm player " + (playerID+1) + " and it's my turn!");
		diceResults.setText("");
		spaceTypeLabel.setText("");
		messageLabel.setText("");
		
		setMoneyLabelValue(Monopoly.getSharedInstance().players.get(playerID).playerCreditCard.money);
		setLabelsPosition();
	}

	public void setMoneyLabelValue(int money) {
		moneyLabel.setText("Current Money: R$" + money + ",00");
	}
	
	public void showDiceResults(int result1, int result2) {
		if (!(diceResults.getText().equals(""))) {
			diceResults.setText(diceResults.getText() + "\n");
		}
		diceResults.setText(diceResults.getText() + "You rolled " + result1 + " and " + result2);
	}

	public void showMessage(String string) {
		if (!(messageLabel.getText().contains(string))){
			messageLabel.setText(messageLabel.getText() + "\n" + string);
		}
	}

	public void showSpaceType(String str) {
		spaceTypeLabel.setText("You are at " + str);
	}
	
	public void setWinner(int currentPlayer) {
		welcomePlayerMessage.setText("");
		diceResults.setText("");
		spaceTypeLabel.setText("");
		messageLabel.setText("I'M PLAYER " + (currentPlayer + 1) + " AND I WON!!!\n :D");
	}
	
	public void nobodyWon() {
		welcomePlayerMessage.setText("");
		diceResults.setText("");
		spaceTypeLabel.setText("");
		messageLabel.setText("Nobody won this game...\n :(");
	}
}
