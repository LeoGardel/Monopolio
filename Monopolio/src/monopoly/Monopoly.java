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

import java.util.ArrayList;

import monopoly.GUI.InGameGUI;
import monopoly.GUI.MainMenu;
import monopoly.camera.CameraHandler;
import monopoly.logic.Board;
import monopoly.logic.Color;
import monopoly.logic.Player;
import monopoly.objects.ObjectRenderer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Monopoly implements ApplicationListener, InputProcessor {
	
	public static final float splitFactor = 0.8f;
	
	public ArrayList<Player> players = new ArrayList<Player>();
	public InGameGUI baseGUI;
	
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
	}
	
	public void callNextPlayer()
	{
		currentPlayer++;
		if(currentPlayer == players.size())
			currentPlayer = 0;
		
		players.get(currentPlayer).rollDice();
	}
	
	@Override
	public void create () {
		
		sharedInstance = this;
		
		Gdx.input.setInputProcessor(this);
		
		Board.getSharedInstance().initSpaces();
		
		baseGUI = new MainMenu();
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
		RightPanelGUI.getSharedInstance().setViewport((Gdx.graphics.getWidth()*(1 - splitFactor)), Gdx.graphics.getHeight(), true);
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
		baseGUI.effect();
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
		return RightPanelGUI.getSharedInstance().keyTyped(character);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchDown(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchUp(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchDragged(x, y, pointer);
		}
		CameraHandler.getSharedInstance().orbitalCameraHandler();
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y)
	{
		if (x > splitFactor * Gdx.graphics.getWidth()) {
			return RightPanelGUI.getSharedInstance().touchMoved(x, y);
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
