package engine;

/**
 * Encapsulates a set of vertex data.
 */
public class Mesh
{
	public final float[] vertexData;
	public final short[] indexData;
	
	// =======================================================================
	// Properties
	// =======================================================================
	public int getNumberOfVertices()
	{
		return indexData.length;
	}
	
	public int getNumberOfTrianges()
	{
		return getNumberOfVertices() / 3;
	}
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public Mesh(float[] vertexData, short[] indexData)
	{
		this.vertexData = vertexData;
		this.indexData = indexData;
	}
}
