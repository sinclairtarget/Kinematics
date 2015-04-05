package character;

import java.util.ArrayList;
import math.*;
import engine.*;
import org.lwjgl.input.*;

/**
 * Simple class responsible for enabling and disabling models.
 */
public class ModelToggler extends Node
{
	private ArrayList<GameObject> models;
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public ModelToggler(GameObject... models)
	{
		super(new Vec3());
		
		if (models.length > 3)
			Debug.logError(this, 
					"ModelToggler only supports up to three models.");
		
		this.models = new ArrayList<GameObject>();
		
		for (GameObject model : models)
			this.models.add(model);
		
		disableAll();
		
		if (this.models.size() > 0)
			this.models.get(0).enabled = true;
	}
	
	// =======================================================================
	// Game Object Methods
	// =======================================================================
	public void update()
	{
		if (Input.keyPressed(Keyboard.KEY_1) && models.size() > 0)
		{
			disableAll();
			models.get(0).enabled = true;
		}
		
		if (Input.keyPressed(Keyboard.KEY_2) && models.size() > 1)
		{
			disableAll();
			models.get(1).enabled = true;
		}
		
		if (Input.keyPressed(Keyboard.KEY_3) && models.size() > 2)
		{
			disableAll();
			models.get(2).enabled = true;
		}
	}
	
	// =======================================================================
	// Helper Methods
	// =======================================================================
	private void disableAll()
	{
		for (GameObject model : models)
			model.enabled = false;
	}
}
