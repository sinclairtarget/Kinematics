package character;

import engine.*;
import math.*;

/**
 * A rectangular prism with different colored sides.
 */
public class RectPrism extends DrawnObject
{	
	protected final float width;
	protected final float height;
	protected final float depth;
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public RectPrism(Vec3 initialPosition, float width, float height,
			float depth)
	{
		super(initialPosition, createMesh(width, height, depth));
		this.width = width;
		this.height = height;
		this.depth = depth;
		
//		collider = new AABoxCollider(this, width, height, depth);
	}
	
	// =======================================================================
	// Helper Methods
	// =======================================================================	
	// creates a mesh for a rectangular prism with the origin at the center
	private static Mesh createMesh(float width, float height, float depth)
	{
		float halfWidth = width / 2;
		float halfHeight = height / 2;
		float halfDepth = depth / 2;
		
		float[] vertexData = {
			// vertex data for a rectangular prism
			// 6 * 2 = 12 triangles
			// 12 * 3 = 36 vertices
			// === front face ===
			// top-left
            halfWidth, halfHeight, halfDepth, 1.0f,
            halfWidth, -halfHeight, halfDepth, 1.0f,
            -halfWidth, halfHeight, halfDepth, 1.0f,

            // bottom-right
            halfWidth, -halfHeight, halfDepth, 1.0f,
            -halfWidth, -halfHeight, halfDepth, 1.0f,
            -halfWidth, halfHeight, halfDepth, 1.0f,

            // === back face ===
            // top-left
            halfWidth, halfHeight, -halfDepth, 1.0f,
            -halfWidth, halfHeight, -halfDepth, 1.0f,
            halfWidth, -halfHeight, -halfDepth, 1.0f,

            // bottom-right
            halfWidth, -halfHeight, -halfDepth, 1.0f,
            -halfWidth, halfHeight, -halfDepth, 1.0f,
            -halfWidth, -halfHeight, -halfDepth, 1.0f,

            // === left face ==
            // top-right
            -halfWidth, halfHeight, halfDepth, 1.0f,
            -halfWidth, -halfHeight, halfDepth, 1.0f,
            -halfWidth, -halfHeight, -halfDepth, 1.0f,

            // bottom-right
            -halfWidth, halfHeight, halfDepth, 1.0f,
            -halfWidth, -halfHeight, -halfDepth, 1.0f,
            -halfWidth, halfHeight, -halfDepth, 1.0f,

            // === right face ===
            // bottom-left
            halfWidth, halfHeight, halfDepth, 1.0f,
            halfWidth, -halfHeight, -halfDepth, 1.0f,
            halfWidth, -halfHeight, halfDepth, 1.0f,

            // top-right
            halfWidth, halfHeight, halfDepth, 1.0f,
            halfWidth, halfHeight, -halfDepth, 1.0f,
            halfWidth, -halfHeight, -halfDepth, 1.0f,

            // === top face ===
            // bottom-right
            halfWidth, halfHeight, -halfDepth, 1.0f,
            halfWidth, halfHeight, halfDepth, 1.0f,
            -halfWidth, halfHeight, halfDepth, 1.0f,

            // top-left
            halfWidth, halfHeight, -halfDepth, 1.0f,
            -halfWidth, halfHeight, halfDepth, 1.0f,
            -halfWidth, halfHeight, -halfDepth, 1.0f,

            // === bottom face ===
            halfWidth, -halfHeight, -halfDepth, 1.0f,
            -halfWidth, -halfHeight, halfDepth, 1.0f,
            halfWidth, -halfHeight, halfDepth, 1.0f,

            halfWidth, -halfHeight, -halfDepth, 1.0f,
            -halfWidth, -halfHeight, -halfDepth, 1.0f,
            -halfWidth, -halfHeight, halfDepth, 1.0f,

            // vertex color data
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,

            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,
            0.8f, 0.8f, 0.8f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,

            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f
		};
		
		// need to have index data for compatibility with Mesh, even if
		// coloring here makes it kind of useless
		short[] indexData = {
			0, 1, 2,
			3, 4, 5,
			6, 7, 8,
			9, 10, 11,
			12, 13, 14,
			15, 16, 17,
			18, 19, 20,
			21, 22, 23,
			24, 25, 26,
			27, 28, 29,
			30, 31, 32,
			33, 34, 35
		};
		
		return new Mesh(vertexData, indexData);
	}
}
