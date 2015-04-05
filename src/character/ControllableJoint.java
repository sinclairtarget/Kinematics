package character;

import math.*;
import engine.*;
import engine.Joint;
import character.JointControlScheme;
import character.JointControlScheme.Control;

public class ControllableJoint extends Joint
{
	private JointControlScheme controlScheme;
	
	// =======================================================================
	// Initialization
	// =======================================================================
	public ControllableJoint(Vec3 initialPosition, float minRotation, 
							float maxRotation,  Vec3 localRotationAxis, 
							JointControlScheme controlScheme)
	{
		super(initialPosition, minRotation, maxRotation, localRotationAxis);
		
		this.controlScheme = controlScheme;				
	}

	// =======================================================================
	// Game Object Methods
	// =======================================================================
	public void update() 
	{
		float rotationDegrees = getRotationDegrees();
		
		if (controlScheme.isControlDown(Control.ROT))
			rotationDegrees += rotationSpeed * RunLoop.getDeltaTime();
		if (controlScheme.isControlDown(Control.UNROT))
			rotationDegrees -= rotationSpeed * RunLoop.getDeltaTime();
		
		rotateTo(rotationDegrees);
	}
}
