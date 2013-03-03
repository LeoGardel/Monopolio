package monopoly.objects;

import java.util.HashMap;

import monopoly.Monopoly;
import monopoly.RightPanelGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class ObjectRenderer
{
	private static ObjectRenderer sharedInstance;
	
	int IDCounter;
	
	GL10 gl;
	HashMap <Integer, InanimatedElement> inanimOpaqueDic = new HashMap <Integer, InanimatedElement>();
	HashMap <Integer, InanimatedElement> inanimTranspDic = new HashMap <Integer, InanimatedElement>();
	HashMap <Integer, AnimatedElement> animOpaqueDic = new HashMap <Integer, AnimatedElement>();
	HashMap <Integer, AnimatedElement> animTranspDic = new HashMap <Integer, AnimatedElement>();
	
	public static ObjectRenderer getSharedInstance()
	{
		if (sharedInstance == null)
		{
			sharedInstance = new ObjectRenderer();
		}
		return sharedInstance;
	}
	
	private ObjectRenderer()
	{
		gl = Gdx.graphics.getGL10();
		
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);
	}
	
	public void drawAll()
	{
		gl.glViewport(0, 0, (int) (Gdx.graphics.getWidth() * Monopoly.splitFactor ), Gdx.graphics.getHeight());
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		gl.glFrontFace(GL10.GL_CCW);
		for ( InanimatedElement inaEle : inanimOpaqueDic.values() )
		{
			inaEle.draw();
		}
		
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		for ( InanimatedElement inaEle : inanimTranspDic.values() )
		{
			inaEle.draw();
		}
		
		gl.glFrontFace(GL10.GL_CW);
		for ( AnimatedElement creature : animTranspDic.values() )
		{
			creature.draw();
		}
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		for ( AnimatedElement creature : animOpaqueDic.values() )
		{
			creature.draw();
		}
		gl.glViewport((int) (Gdx.graphics.getWidth() * Monopoly.splitFactor) , 0, (int) (Gdx.graphics.getWidth() * (1 - Monopoly.splitFactor)), Gdx.graphics.getHeight());
		RightPanelGUI.getSharedInstance().draw();
	}
	
	
	// REGISTERS
	public int registerInanimOpaque(InanimatedElement inaElement)
	{
		inanimOpaqueDic.put(IDCounter, inaElement);
		return ++IDCounter;
	}
	
	public int registerInanimTransp(InanimatedElement inaElement)
	{
		inanimTranspDic.put(IDCounter, inaElement);
		return ++IDCounter;
	}
	
	public int registerAnimOpaque(AnimatedElement creature)
	{
		animOpaqueDic.put(IDCounter, creature);
		return ++IDCounter;
	}
	
	public int registerAnimTransp(AnimatedElement creature)
	{
		animTranspDic.put(IDCounter, creature);
		return ++IDCounter;
	}
	// END
	
	
	// UNREGISTERS
	public void unregisterInanimOpaque(int ID)
	{
		inanimOpaqueDic.remove(ID);
	}
	
	public void unregisterInanimTransp(int ID)
	{
		inanimTranspDic.remove(ID);
	}
	
	public void unregisterAnimOpaque(int ID)
	{
		animOpaqueDic.remove(ID);
	}
	
	public void unregisterAnimTransp(int ID)
	{
		animTranspDic.remove(ID);
	}
	// END
	
	
	public void setClearColor(float r, float g, float b, float a)
	{
		gl.glClearColor(r, g, b, a);
	}
	
}
