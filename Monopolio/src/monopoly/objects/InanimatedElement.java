package monopoly.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;

public class InanimatedElement
{
	GL10 gl;
	int ID;
	
	public InanimatedObject srcObject;
	
	public Vector3 position = new Vector3();
	public Vector3 scale = new Vector3(1,1,1);
	public Vector3 rotation = new Vector3();
	
	public boolean isVisible = true;
	
	public InanimatedElement(InanimatedObject srcObject)
	{
		gl = Gdx.app.getGraphics().getGL10();
		
		this.srcObject = srcObject;
		
		if (srcObject.isTransparent)
		{
			ID = ObjectRenderer.getSharedInstance().registerInanimTransp(this);
		}
		else
		{
			ID = ObjectRenderer.getSharedInstance().registerInanimOpaque(this);
		}
	}
	
	public void draw()
	{
		srcObject.diffuseTex.bind();

		gl.glPushMatrix();
		
		gl.glTranslatef(position.x, position.y, position.z);
		gl.glRotatef(rotation.x, 0, 0, 1);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.z, 1, 0, 0);
		gl.glScalef(scale.x, scale.y, scale.z);
		
		srcObject.mesh.render(GL10.GL_TRIANGLES);
		
		gl.glPopMatrix();
	}
	
	public void setVisible()
	{
		if (!isVisible)
		{
			if (srcObject.isTransparent)
			{
				ID = ObjectRenderer.getSharedInstance().registerInanimTransp(this);
			}
			else
			{
				ID = ObjectRenderer.getSharedInstance().registerInanimOpaque(this);
			}
		}
	}

	public void setInvisible()
	{
		if (isVisible)
		{
			if (srcObject.isTransparent)
			{
				ObjectRenderer.getSharedInstance().unregisterInanimTransp(ID);
			}
			else
			{
				ObjectRenderer.getSharedInstance().unregisterInanimOpaque(ID);
			}
		}
	}
	
	protected void finalize() throws Throwable
	{
		setInvisible();
		super.finalize();
	} 
}
