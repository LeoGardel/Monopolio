package monopoly.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g3d.loaders.ogre.mesh.Animations;
import com.badlogic.gdx.math.Vector3;

public class AnimatedElement
{
	protected GL10 gl;
	
	int ID;
	public AnimatedObject srcObject;
	
	public Vector3 position = new Vector3();
	public Vector3 scale = new Vector3(1,1,1);
	public Vector3 rotation = new Vector3();
	
	public boolean isVisible = true;
	
	float animTime = 0;
	
	public AnimatedElement(AnimatedObject srcObject)
	{
		gl = Gdx.app.getGraphics().getGL10();
		
		this.srcObject = srcObject;
		
		if (srcObject.isTransparent)
		{
			ID = ObjectRenderer.getSharedInstance().registerAnimTransp(this);
		}
		else
		{
			ID = ObjectRenderer.getSharedInstance().registerAnimOpaque(this);
		}
	}
	
	public void draw()
	{
		srcObject.mesh.setAnimation(srcObject.animations[0].name, animTime, false);
		
		animTime += Gdx.graphics.getDeltaTime();
		if (animTime >= srcObject.animations[0].totalDuration) {
			animTime = 0;
		}

		srcObject.diffuseTex.bind();

		gl.glPushMatrix();

		gl.glTranslatef(position.x, position.y, position.z);
		gl.glRotatef(rotation.x, 0, 0, 1);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.z, 1, 0, 0);
		gl.glScalef(scale.x, scale.y, scale.z);

		srcObject.mesh.render();

		gl.glPopMatrix();
	}
	
	public void setVisible()
	{
		if (!isVisible)
		{
			if (srcObject.isTransparent)
			{
				ID = ObjectRenderer.getSharedInstance().registerAnimTransp(this);
			}
			else
			{
				ID = ObjectRenderer.getSharedInstance().registerAnimOpaque(this);
			}
		}
	}
	
	public void setInvisible()
	{
		if (isVisible)
		{
			if (srcObject.isTransparent)
			{
				ObjectRenderer.getSharedInstance().unregisterAnimTransp(ID);
			}
			else
			{
				ObjectRenderer.getSharedInstance().unregisterAnimOpaque(ID);
			}
		}
	}
	
	protected void finalize() throws Throwable
	{
		setInvisible();
		super.finalize();
	} 
}
