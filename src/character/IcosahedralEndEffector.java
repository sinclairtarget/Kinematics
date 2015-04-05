package character;

import engine.*;
import engine.physics.*;
import math.*;

/**
 * A solid-colored icosahedron.
 */
public class IcosahedralEndEffector extends EndEffector
{
	protected static float t = (float) (1 + Math.sqrt(5)) / 2;
	protected static float scaleAdjust = 0.1f;
	protected static float scale = 
			(float) ((float) 1 / Math.sqrt(1 + Math.pow(t, 2))) * scaleAdjust;
	
	// =======================================================================
	// Initialization
	// =======================================================================	
	public IcosahedralEndEffector(Vec3 initialPosition)
	{
		super(initialPosition, createMesh());
	}
	
	// =======================================================================
	// Sandbox Methods
	// =======================================================================	
	protected Collider createCollider() {
		return new SphereCollider(this, scaleAdjust);
	}
	
	// =======================================================================
	// Helper Methods
	// =======================================================================	
	// creates a mesh for a icosahedron with vertices 1 unit away from origin
	private static Mesh createMesh()
	{		
		float[] vertexData = {
			// vertices
			t, 1, 0, 1,				// 0
			-t, 1, 0, 1,
			t, -1, 0, 1,
			-t, -1, 0, 1,			
			1, 0, t, 1,				// 4
			1, 0, -t, 1,
			-1, 0, t, 1,
			-1, 0, -t, 1,			
			0, t, 1, 1,				// 8
			0, -t, 1, 1,
			0, t, -1, 1,
			0, -t, -1, 1,			// 11
			
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f,


//		      // colors
//		      0.0f, 0.0f, 1.0f, 1.0f,   // 0
//		      0.8f, 0.8f, 0.8f, 1.0f,
//		      0.0f, 1.0f, 0.0f, 1.0f,
//		      0.5f, 0.5f, 0.0f, 1.0f,
//		      1.0f, 0.0f, 0.0f, 1.0f,   // 4
//		      0.0f, 1.0f, 1.0f, 1.0f,
//		      0.0f, 0.0f, 1.0f, 1.0f,
//		      0.8f, 0.8f, 0.8f, 1.0f,
//		      0.0f, 1.0f, 0.0f, 1.0f,   // 8
//		      0.5f, 0.5f, 0.0f, 1.0f,
//		      1.0f, 0.0f, 0.0f, 1.0f,
//		      0.0f, 1.0f, 1.0f, 1.0f
		};
		
		for (int i = 0; i < 12 * 4; i++) {
			if ((i + 1) % 4 == 0)
				continue;
			
			vertexData[i] = scale * vertexData[i];
		}
		
		short[] indexData = {
				0, 4, 8,
				0, 10, 5,
				2, 9, 4,
				2, 5, 11,
				1, 8, 6,
				1, 7, 10,
				3, 6, 9,
				3, 11, 7,
				0, 8, 10,
				1, 10, 8,
				2, 11, 9,
				3, 9, 11, 
				4, 0, 2,
				5, 2, 0,
				6, 3, 1,
				7, 1, 3,
				8, 4, 6,
				9, 6, 4,
				10, 7, 5,
				11, 5, 7
			};
		
		return new Mesh(vertexData, indexData);
	}
}
