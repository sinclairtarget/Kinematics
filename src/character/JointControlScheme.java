package character;

import engine.*;

import java.util.HashMap;
import org.lwjgl.input.Keyboard;

/**
 * Encapsulates a particular set of control-key bindings for rotating a joint.
 */
public class JointControlScheme
{	
	public enum Control { ROT, UNROT }
	
	private HashMap<Control, Integer> controlMap;
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public JointControlScheme(int rot, int unrot)
	{
		controlMap = new HashMap<Control, Integer>();
		controlMap.put(Control.ROT, rot);
		controlMap.put(Control.UNROT, unrot);
	}
	
	// =======================================================================
	// Querying
	// =======================================================================
	public boolean isControlDown(Control control)
	{
		int key = controlMap.get(control);
		return key != Keyboard.CHAR_NONE && 
				Input.isKeyDown(controlMap.get(control));
	}
}
