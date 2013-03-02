package monopoly.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;


public class CameraHandler
{
	public Camera camera;
	
	private static CameraHandler sharedInstance;
	
	public static CameraHandler getSharedInstance()
	{
		if (sharedInstance == null)
		{
			sharedInstance = new CameraHandler();
		}
		return sharedInstance;
	}
	
	public CameraHandler()
	{
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.lookAt(0, 3, 0);
		camera.up.set(0, 0, 1);
		camera.far = 120.0f;
		camera.near = 0.1f;
		camera.update();
		camera.apply(Gdx.graphics.getGL10());
	}

	private float _PI = 3.141592f;

	private boolean touchOn = false;
	private float _orbit = 5f;
	private float _orbitMin = 1f;
	private float _theta = 1.2f;
	private float _phi = 1.0f;
	private float _vTheta = 0f;
	private float _vPhi = 0f;
	private float _damping = 0.89f;
	
	private Vector3 _lookAtPosition = new Vector3();
	private Vector3 _vLookAtPosition = new Vector3();
	private Vector3 _cameraUp = new Vector3(0, 0, 1);

	private boolean _doubleTapEnabled = true;
	private boolean pan_or_rota = true;


	public boolean orbitalCameraHandler()
	{
		// rotate camera
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
		{
			_vPhi = -Gdx.input.getDeltaY() / 200f;
			_vTheta = Gdx.input.getDeltaX() / 200f;
		}
		
        // pan camera
		else if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE))
		{
			_vLookAtPosition.x = (Gdx.input.getDeltaY() * MathUtils.cos(_theta) -
					Gdx.input.getDeltaX() * MathUtils.sin(_theta)) * 0.04f;
			_vLookAtPosition.y = (Gdx.input.getDeltaY() * MathUtils.sin(_theta) + 
					Gdx.input.getDeltaX() * MathUtils.cos(_theta)) * 0.04f;
		}
		
// camera zoom code
//				Vector3 location2 = Vector3.create(me.getX(1), me.getY(1), 0);
//				Vector3 previousLocation2 = Vector3.create(me.getHistoricalX(1, 1), me.getHistoricalY(1, 1), 0);
//
//				float previousDistance = distanceBetweenPoints(previousLocation1, previousLocation2); 
//				float currentDistance = distanceBetweenPoints(location1, location2);
//
//				float changeInDistance = currentDistance - previousDistance;
//				_orbit -= changeInDistance * 0.1;
		return true;
	}
	
	
	public boolean orbitalCameraZoomHandler(int amount)
	{
		_orbit += amount * .3f;
		
		return true;
	}

	public void orbitalCameraUpdate()
	{
		// Update and limit the camera angles
		_theta -= _vTheta;
		_phi -= _vPhi;
		if (_phi >= 1.57075f) {
			_phi = 1.57074f;
		}
		if (_phi <= 0.1f) {
			_phi = 0.1f;
		}

		if (_orbit < _orbitMin) {
			_orbit = _orbitMin;
		}

		_lookAtPosition.x -= _vLookAtPosition.x;
		_lookAtPosition.y -= _vLookAtPosition.y;

		// Convert camera angles to positions
		float l = _orbit * MathUtils.cos(_phi);
		float x = l * MathUtils.cos(_theta) + _lookAtPosition.x;
		float y = l * MathUtils.sin(_theta) + _lookAtPosition.y;
		float z = _orbit * MathUtils.sin(_phi) + _lookAtPosition.z;

		// Translate camera and change its 'lookAt' position
		camera.position.set(x, y, z);
		camera.lookAt(_lookAtPosition.x, _lookAtPosition.y, _lookAtPosition.z);
		camera.update();
		camera.apply(Gdx.graphics.getGL10());

		// Add damping to camera velocities
		_vTheta *= _damping;
		_vPhi *= _damping;
		_vLookAtPosition.x *= _damping;
		_vLookAtPosition.y *= _damping;
	}
}
