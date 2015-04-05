package engine;

import math.*;

public class Camera
{		
	private Mat4 projectionMatrix;
	
	// ========================================================================
	// Property Accessors
	// ========================================================================
	public Mat4 getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	// ========================================================================
	// Initialization
	// ========================================================================
	public Camera(float FOV)
	{
		initProjectionMatrix(FOV);
	}
	
	// ========================================================================
	// Helper Methods
	// ========================================================================
	private void initProjectionMatrix(float FOV)
	{
		float frustumTopExtent = frustumTopExtentForFOV(FOV);
		float zNear = 1.0f;
		float zFar = 3.0f;
		int windowWidth = Application.window.getWidth();
		int windowHeight = Application.window.getHeight();
		float aspectRatio = windowWidth / (float) windowHeight;
		
		projectionMatrix = new Mat4();
		projectionMatrix.set(0, 0, 1 / (frustumTopExtent * aspectRatio));
		projectionMatrix.set(1, 1, 1 / frustumTopExtent);
		projectionMatrix.set(2, 2, (zFar + zNear) / (zNear - zFar));
		projectionMatrix.set(3, 2, (2 * zFar * zNear) / (zNear - zFar));
		projectionMatrix.set(2, 3, -1.0f);
		projectionMatrix.set(3, 3, 0);
//		Debug.log(this, "Perspective Matrix:\n" + perspectiveMatrix.toString());
	}
	
	private float frustumTopExtentForFOV(float FOV) {
		float FOVrad = (float) Math.toRadians(FOV);
		return (float) Math.tan(FOVrad / 2);
	}
}
