package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import math.*;

/**
 * An object with a mesh that can be drawn.
 */
public abstract class DrawnObject extends GameObject
{
	protected static final String DEFAULT_VERTEX_SHADER_PATH = 
			"build/classes/shaders/MatrixPerspective.vert";
	protected static final String DEFAULT_FRAGMENT_SHADER_PATH = 
			"build/classes/shaders/StandardColors.frag";
	
	protected Mesh mesh;
		
	// openGL objects
	private int vertexBufferObject;
	private int indexBufferObject;
    private int vertexArrayObject;
	private static int programObject;
	private static int positionMatrixUnf;
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public DrawnObject(Vec3 initialPosition, Mesh mesh, String vertexShaderPath, 
			String fragmentShaderPath)
	{
		super(initialPosition);
		
		this.mesh = mesh;
		
		initializeBuffers();
		
		if (programObject == 0)
			initializeShaders(vertexShaderPath, fragmentShaderPath);
		
		initializeVertexArrayObject();
	}
	
	public DrawnObject(Vec3 initialPosition, Mesh mesh)
	{
		this(initialPosition, mesh, DEFAULT_VERTEX_SHADER_PATH,
				DEFAULT_FRAGMENT_SHADER_PATH);
	}
	
	// =======================================================================
	// Game Object Methods
	// =======================================================================
	public void handleClick(int mouseButton)
	{
		// do nothing
	}
	
	public void update()
	{
		// do nothing
	}
	
	public void handleCollision(Vec3 collisionSurface, 
			GameObject otherObject)
	{
		// do nothing
	}
	
	public void draw(Mat4 transform)
	{
		glUseProgram(programObject);
		
        glBindVertexArray(vertexArrayObject);
        
        glUniformMatrix4(positionMatrixUnf, false, 
				Utility.matrixToBuffer(transform));
        
        glDrawElements(GL_TRIANGLES, mesh.getNumberOfVertices(), 
        		GL_UNSIGNED_SHORT, 0);

        glBindVertexArray(0);
        glUseProgram(0);
	}
	
	// =======================================================================
	// Helper Methods
	// =======================================================================	
	private void initializeBuffers()
	{
		// format vertex data so it can be passed to openGL
		FloatBuffer vertexBuffer = 
				Utility.floatArrayToBuffer(mesh.vertexData);
		    
	    // create a buffer object and store the vertex data there
		vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// format index data so it can be passed to openGL
		ShortBuffer indexDataBuffer = 
				Utility.shortArrayToBuffer(mesh.indexData);
		
		// create a buffer object and store the index data there
		indexBufferObject = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexDataBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private static void initializeShaders(String vertexShaderPath, 
			String fragmentShaderPath)
	{
		ArrayList<Integer> shaderList = new ArrayList<Integer>();
		
		int vertexShader = ShaderLoader.loadShader(GL_VERTEX_SHADER, 
									vertexShaderPath);
		int fragShader = ShaderLoader.loadShader(GL_FRAGMENT_SHADER,
									fragmentShaderPath);
		
		shaderList.add(vertexShader);
		shaderList.add(fragShader);
		
		programObject = ShaderLoader.createProgram(shaderList);
		
		// initialize uniforms
		positionMatrixUnf = glGetUniformLocation(programObject, 
									"positionMatrix");
		int perspectiveMatrixUnf = glGetUniformLocation(programObject,
									"projectionMatrix");
		
		Mat4 projectionMatrix = 
				Application.mainCamera.getProjectionMatrix();
		
		glUseProgram(programObject);
		glUniformMatrix4(perspectiveMatrixUnf, false,
				Utility.matrixToBuffer(projectionMatrix));
		glUseProgram(0);
	}
	
	private void initializeVertexArrayObject()
	{
        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);
        
        int colorDataOffset = Application.FLOAT_SIZE * 
        		mesh.vertexData.length / 2;
        
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, colorDataOffset);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);

        glBindVertexArray(0);
	}
}
