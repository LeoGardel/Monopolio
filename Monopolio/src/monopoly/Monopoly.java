/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package monopoly;

import java.io.IOException;
import java.util.ArrayList;

import monopoly.camera.CameraHandler;
import monopoly.logic.Board;
import monopoly.logic.Color;
import monopoly.logic.Player;
import monopoly.objects.InanimatedElement;
import monopoly.objects.InanimatedObject;
import monopoly.objects.ObjectRenderer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class Monopoly implements ApplicationListener, InputProcessor {
	
	public static final int applicationInitialWidth = 800;
	public static final int applicationInitialHeight = 512;
	public static final float splitFactor = 0.68f;
	public int numOfPlayersOut = 0;
	
	public ArrayList<Player> players = new ArrayList<Player>();
	
	public int currentPlayer = -1;

	private static Monopoly sharedInstance;
	
	public static Monopoly getSharedInstance()
	{
		if (sharedInstance == null) {
			sharedInstance = new Monopoly();
		}
		return sharedInstance;
	}
	
	
	public void startGame(int numberOfPlayers) 
	{
		for (int i=0; i < numberOfPlayers; i++) 
		{
			players.add( new Player("player " + i, i, Color.values()[i]) );
		}
		
		RightPanelGUI.getSharedInstance().setGameActors();
		Monopoly.getSharedInstance().callNextPlayer();
	}
	
	public void callNextPlayer()
	{
		int previousPlayer = currentPlayer;
		int i = 0;
		do{
			currentPlayer = nextPlayer(currentPlayer);
			i++;
		}
		while (players.get(currentPlayer).bankruptcy && !(i == players.size() + 1));
		
		if (i == players.size() + 1) {
			RightPanelGUI.getSharedInstance().nobodyWon();
			return;
		}
		
		if (previousPlayer == currentPlayer) {
			RightPanelGUI.getSharedInstance().setWinner(currentPlayer);
			return;
		}
		
		RightPanelGUI.getSharedInstance().showActualPlayer(currentPlayer);
		players.get(currentPlayer).rollDice();
	}
	
	public int nextPlayer(int currentPlayer) {
		if(currentPlayer + 1 == players.size())
			return 0;
		else
			return currentPlayer + 1;
	}
	
	@Override
	public void create () {
		
		sharedInstance = this;
		
		Gdx.input.setInputProcessor(this);
		
		Board.getSharedInstance().initSpaces();
	}

	@Override
	public void render () 
	{		
		CameraHandler.getSharedInstance().orbitalCameraUpdate();
		ObjectRenderer.getSharedInstance().drawAll();
	}

	
	
	@Override
	public void resize (int width, int height) {
		Gdx.graphics.getGL10().glViewport(0, 0, (int) (Gdx.graphics.getWidth() * splitFactor), Gdx.graphics.getHeight());
		RightPanelGUI.getSharedInstance().resize();
	}

	@Override
	public void pause () {
		
	}

	@Override
	public void resume () {
		
	}

	@Override
	public void dispose () {
		RightPanelGUI.getSharedInstance().dispose();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return RightPanelGUI.getSharedInstance().keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return RightPanelGUI.getSharedInstance().keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character)
	{
		if (new String("h").toCharArray()[0] == character) {
			try
			{
				InanimatedObject hotelModel = new InanimatedObject("hotel/hotel", "hotel/hotel2.png", false);
				InanimatedElement hotelNode = new InanimatedElement(hotelModel);
				
				hotelNode.position.set(new Vector3(5-4.1f, 5-4.1f, 0f));
				hotelNode.setVisible();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (new String("c").toCharArray()[0] == character) {
			try
			{
				InanimatedObject houseModel = new InanimatedObject("house/house", "house/house2.png", false);
				InanimatedElement houseNode = new InanimatedElement(houseModel);
				
				houseNode.position.set(new Vector3(5-3.1f, 5-4.1f, 0f));
				houseNode.setVisible();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return RightPanelGUI.getSharedInstance().keyTyped(character);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchDown((int) (x - splitFactor * Gdx.graphics.getWidth()), y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchUp((int) (x - splitFactor * Gdx.graphics.getWidth()), y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchDragged((int) (x - splitFactor * Gdx.graphics.getWidth()), y, pointer);
		}
		CameraHandler.getSharedInstance().orbitalCameraHandler();
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchMoved((int) (x - splitFactor * Gdx.graphics.getWidth()), y);
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		if (Gdx.input.getX() > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().scrolled(amount);
		}
		CameraHandler.getSharedInstance().orbitalCameraZoomHandler(amount);
		return false;
	}

}
