package monopoly.objects;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderOld;

public class Object 
{
	public Mesh mesh;
	public Texture diffuseTex;
	
	public boolean isTransparent;
	
	public Object(String meshFileName, String diffuseFileName, boolean isTransparent) throws IOException
	{
		this.isTransparent = isTransparent;
		
		mesh = ModelLoaderOld.loadObj(Gdx.files.internal("data/" + meshFileName + ".obj").read());
				
		if (isTransparent)
		{			
			diffuseTex = new Texture(Gdx.files.internal("data/" + diffuseFileName), Format.RGBA8888, true);
		}
		else
		{
			diffuseTex = new Texture(Gdx.files.internal("data/" + diffuseFileName), Format.RGB888, true);
		}
		diffuseTex.setFilter(TextureFilter.MipMap, TextureFilter.Linear);	
	}
}
