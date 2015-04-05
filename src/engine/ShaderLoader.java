package engine;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Static utility class for loading and compiling shaders.
 */
public class ShaderLoader
{
	// =======================================================================
	// Public Interface
	// =======================================================================
	// reads a shader from a file and compiles it
	public static int loadShader(int shaderType, String filename)
	{
		String shaderText = loadShaderText(filename);
		return compileShader(shaderType, shaderText);
	}
	
	// creates a program by linking the passed shaders
	public static int createProgram(ArrayList<Integer> shaders)
	{
		int programObject = glCreateProgram();
		
		linkProgram(programObject, shaders);
		
		return programObject;
	}
	
	// =======================================================================
	// File IO
	// =======================================================================
	private static String loadShaderText(String filename)
	{
		StringBuilder text = new StringBuilder();
		Path path = Paths.get(filename);
		
		try
		{
			BufferedReader reader = Files.newBufferedReader(path, 
					Charset.defaultCharset());
			
			String line;
			while ((line = reader.readLine()) != null)
				text.append(line).append("\n");
		}
		catch (IOException exception)
		{
			System.err.println("Exception encountered: " + exception);
		}
		
		return text.toString();
	}
	
	// =======================================================================
	// Shader Compilation
	// =======================================================================
	// compiles a shader
	private static int compileShader(int shaderType, String shaderText)
	{
		int shaderObject = glCreateShader(shaderType);
		glShaderSource(shaderObject, shaderText); // set shader source
		
		glCompileShader(shaderObject);
		
		// error checking
		int status = glGetShaderi(shaderObject, GL_COMPILE_STATUS);
        if (status == GL_FALSE) 
        	printCompileError(shaderObject, shaderType);
		
		return shaderObject;
	}
	
	// links shaders into a program
	private static int linkProgram(int programObject, 
								   ArrayList<Integer> shaderList)
	{		
		// attach shaders
		for (int shaderObject : shaderList)
			glAttachShader(programObject, shaderObject);
		
		glLinkProgram(programObject);
		
		// error checking
		int status = glGetProgrami(programObject, GL_LINK_STATUS);
		if (status == GL_FALSE)
			printLinkError(programObject);

		// detach shaders which we no longer need
		for (int shaderObject : shaderList)
			glDetachShader(programObject, shaderObject);
		
		return programObject;
	}
	
	// =======================================================================
	// Printing Errors
	// =======================================================================
	private static void printCompileError(int shaderObject, int shaderType)
	{
		int infoLogLength = glGetShaderi(shaderObject, GL_INFO_LOG_LENGTH );

        String infoLog = glGetShaderInfoLog(shaderObject, infoLogLength);

        String shaderTypeStr = null;
        switch (shaderType) {
            case GL_VERTEX_SHADER:
                shaderTypeStr = "vertex";
                break;
            case GL_GEOMETRY_SHADER:
                shaderTypeStr = "geometry";
                break;
            case GL_FRAGMENT_SHADER:
                shaderTypeStr = "fragment";
                break;
        }

        Debug.logError(ShaderLoader.class, 
        		"Compile failure in " + shaderTypeStr + "s shader:"
        		+ "\n" + infoLog);
	}
	
	private static void printLinkError(int programObject)
	{
		int infoLogLength = glGetProgrami(programObject, GL_INFO_LOG_LENGTH);
        String infoLog = glGetProgramInfoLog(programObject, infoLogLength);
        Debug.logError(ShaderLoader.class, "Linker failure: " + infoLog);
	}
}
