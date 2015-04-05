package character;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import engine.*;
import math.*;

/**
 * Delegate class that specifies initial parameters of the application.
 */
public class ApplicationInitializer implements IApplicationInitializer
{
	private final int WINDOW_WIDTH = 960;
	private final int WINDOW_HEIGHT = 540;
	private final float FOV = 90;
	private final String TITLE = "Kinematics";
	private final Vec4 CLEAR_COLOR = new Vec4(0, 0, 0, 1);
	
	public Window initWindow() throws LWJGLException
	{
		return new Window(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE, CLEAR_COLOR);
	}
	
	public Camera initCamera()
	{
		return new Camera(FOV);
	}
	
	public void initScene()
	{
		GameObject simpleRoot = AddSimpleModel();
		GameObject characterRoot = AddCharacterModel();
		
		ModelToggler toggler = new ModelToggler(simpleRoot, characterRoot);
		GameObject.loadGameObject(toggler);
	}
	
	private GameObject AddSimpleModel()
	{
		Rig rig = new Rig(new Vec3(0, 0, -1.75f));
		GameObject.loadGameObject(rig);
		
		// base box
		JointControlScheme originControl =
				new JointControlScheme(Keyboard.KEY_D, Keyboard.KEY_A);
		ControllableJoint originControllableJoint = 
				new ControllableJoint(new Vec3(0, 0, 0), -360, 360, 
						Vec3.Y_AXIS, originControl);
		rig.appendJointTo(rig, originControllableJoint);
		
		RectPrism box = new RectPrism(new Vec3(), 0.5f, 0.5f, 0.5f);
		rig.appendSegmentTo(originControllableJoint, box);
		
		// first flap
		JointControlScheme flapControl =
				new JointControlScheme(Keyboard.KEY_Q, Keyboard.KEY_E);
		ControllableJoint flapControllableJoint = 
				new ControllableJoint(new Vec3(-0.25f, 0.25f, 0), 0, 90, 
										Vec3.Z_AXIS, flapControl);
		rig.appendJointTo(box, flapControllableJoint);
		
		RectPrism flap = new RectPrism(new Vec3(0.25f, 0.05f, 0), 0.5f,
				0.1f, 0.5f);
		rig.appendSegmentTo(flapControllableJoint, flap);
		
		// spinning box
		JointControlScheme spinnerControl =
				new JointControlScheme(Keyboard.KEY_L, Keyboard.KEY_J);
		ControllableJoint spinnerControllableJoint =
				new ControllableJoint(new Vec3(0, 0.05f, 0), -360, 360, 
						Vec3.Y_AXIS, spinnerControl);
		rig.appendJointTo(flap, spinnerControllableJoint);
		
		RectPrism spinningBox = new RectPrism(new Vec3(0, 0.25f, 0), 0.5f,
				0.5f, 0.5f);
		rig.appendSegmentTo(spinnerControllableJoint, spinningBox);
		
		// second flap
		JointControlScheme flapControl2 =
				new JointControlScheme(Keyboard.KEY_K, Keyboard.KEY_I);
		ControllableJoint flapControllableJoint2 = 
				new ControllableJoint(new Vec3(0, 0.25f, -0.25f), -90, 0,
						Vec3.X_AXIS, flapControl2);
		rig.appendJointTo(spinningBox, flapControllableJoint2);
		
		RectPrism flap2 = new RectPrism(new Vec3(0, 0.05f, 0.25f), 
				0.5f, 0.1f, 0.5f);
		rig.appendSegmentTo(flapControllableJoint2, flap2);
		
		IcosahedralEndEffector effector = 
				new IcosahedralEndEffector(new Vec3(0, 0, 0.5f));
		rig.appendEndEffectorTo(flap2, effector);
		
		return originControllableJoint;
	}
	
	private GameObject AddCharacterModel()
	{
		Rig rig = new Rig(new Vec3(0, 0, -2));
		GameObject.loadGameObject(rig);
		
		// ========= waist =================================================
		JointControlScheme waistControl =
				new JointControlScheme(Keyboard.KEY_C, Keyboard.KEY_Z);
		ControllableJoint waistControllableJoint = 
				new ControllableJoint(new Vec3(0, 0, 0), -180, 180, 
										Vec3.Y_AXIS, waistControl);
		rig.appendJointTo(rig, waistControllableJoint);
		
		RectPrism waist = new RectPrism(new Vec3(), 0.8f, 0.05f, 0.4f);
		rig.appendSegmentTo(waistControllableJoint, waist);
		
		// ========= torso =================================================
		JointControlScheme torsoControl =
				new JointControlScheme(Keyboard.KEY_D, Keyboard.KEY_A);
		ControllableJoint torsoControllableJoint = 
				new ControllableJoint(new Vec3(0, 0.025f, 0), -60, 60, 
						Vec3.Y_AXIS, torsoControl);
		rig.appendJointTo(waist, torsoControllableJoint);
		
		RectPrism torso = new RectPrism(new Vec3(0, 0.5f, 0), 0.8f, 1, 0.4f);
		rig.appendSegmentTo(torsoControllableJoint, torso);
		
		// ========= head =================================================
		JointControlScheme headControl =
				new JointControlScheme(Keyboard.KEY_E, Keyboard.KEY_Q);
		ControllableJoint headControllableJoint = 
				new ControllableJoint(new Vec3(0, 0.5f, 0), -80, 80, 
										Vec3.Y_AXIS, headControl);
		rig.appendJointTo(torso, headControllableJoint);
		
		RectPrism head = new RectPrism(new Vec3(0, 0.2f, 0), 0.4f, 0.4f, 0.4f);
		rig.appendSegmentTo(headControllableJoint, head);
		
		// ========= left shoulder =========================================
		JointControlScheme leftShoulderControl =
				new JointControlScheme(Keyboard.KEY_Y, Keyboard.KEY_U);
		ControllableJoint leftShoulderControllableJoint = 
				new ControllableJoint(new Vec3(-0.4f, 0.35f, 0), -80, 80, 
						Vec3.X_AXIS, leftShoulderControl);
		rig.appendJointTo(torso, leftShoulderControllableJoint);
		
		RectPrism leftShoulder = new RectPrism(new Vec3(-0.025f, 0, 0), 
				0.05f, 0.3f, 0.3f);
		rig.appendSegmentTo(leftShoulderControllableJoint, leftShoulder);
		
		// ========= left upper arm =========================================
		JointControlScheme leftUppArmScheme =
				new JointControlScheme(Keyboard.KEY_I, Keyboard.KEY_O);
		ControllableJoint leftUppArmControllableJoint = 
				new ControllableJoint(new Vec3(-0.0125f, 0, 0), -80, 0, 
						Vec3.Z_AXIS, leftUppArmScheme);
		rig.appendJointTo(leftShoulder, leftUppArmControllableJoint);
		
		RectPrism leftUppArm = new RectPrism(new Vec3(-0.1f, -0.1f, 0), 
				0.2f, 0.5f, 0.3f);
		rig.appendSegmentTo(leftUppArmControllableJoint, leftUppArm);
		
		// ========= left lower arm =========================================
		JointControlScheme leftLowArmScheme =
				new JointControlScheme(Keyboard.KEY_P, Keyboard.KEY_LBRACKET);
		ControllableJoint leftLowArmControllableJoint = 
				new ControllableJoint(new Vec3(0, -0.25f, 0), -80, 0, 
						Vec3.X_AXIS, leftLowArmScheme);
		rig.appendJointTo(leftUppArm, leftLowArmControllableJoint);
		
		RectPrism leftLowArm = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.2f, 0.5f, 0.3f);
		rig.appendSegmentTo(leftLowArmControllableJoint, leftLowArm);
		
		// ========= left arm effector ======================================
		IcosahedralEndEffector leftArmEffector = 
				new IcosahedralEndEffector(new Vec3(0, -0.5f, 0));
		rig.appendEndEffectorTo(leftLowArm, leftArmEffector);
		
		// ========= right shoulder =========================================
		JointControlScheme rightShoulderControl =
				new JointControlScheme(Keyboard.KEY_H, Keyboard.KEY_J);
		ControllableJoint rightShoulderControllableJoint = 
				new ControllableJoint(new Vec3(0.4f, 0.35f, 0), -80, 80, 
										Vec3.X_AXIS, rightShoulderControl);
		rig.appendJointTo(torso, rightShoulderControllableJoint);
		
		RectPrism rightShoulder = new RectPrism(new Vec3(0.025f, 0, 0), 
				0.05f, 0.3f, 0.3f);
		rig.appendSegmentTo(rightShoulderControllableJoint, rightShoulder);
		
		// ========= right upper arm =========================================
		JointControlScheme rightUppArmScheme =
				new JointControlScheme(Keyboard.KEY_L, Keyboard.KEY_K);
		ControllableJoint rightUppArmControllableJoint = 
				new ControllableJoint(new Vec3(0.0125f, 0, 0), 0, 80, 
						Vec3.Z_AXIS, rightUppArmScheme);
		rig.appendJointTo(rightShoulder, rightUppArmControllableJoint);
		
		RectPrism rightUppArm = new RectPrism(new Vec3(0.1f, -0.1f, 0), 
				0.2f, 0.5f, 0.3f);
		rig.appendSegmentTo(rightUppArmControllableJoint, rightUppArm);
		
		// ========= right lower arm =========================================
		JointControlScheme rightLowArmScheme =
				new JointControlScheme(Keyboard.KEY_SEMICOLON, 
						Keyboard.KEY_APOSTROPHE);
		ControllableJoint rightLowArmControllableJoint = 
				new ControllableJoint(new Vec3(0, -0.25f, 0), -80, 0, 
						Vec3.X_AXIS, rightLowArmScheme);
		rig.appendJointTo(rightUppArm, rightLowArmControllableJoint);
		
		RectPrism rightLowArm = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.2f, 0.5f, 0.3f);
		rig.appendSegmentTo(rightLowArmControllableJoint, rightLowArm);
		
		// ========= right arm effector ======================================
		IcosahedralEndEffector rightArmEffector = 
				new IcosahedralEndEffector(new Vec3(0, -0.5f, 0));
		rig.appendEndEffectorTo(rightLowArm, rightArmEffector);
		
		// ========= left thigh =========================================
		JointControlScheme leftThighScheme =
				new JointControlScheme(Keyboard.KEY_R, Keyboard.KEY_4);
		ControllableJoint leftThighControllableJoint = 
				new ControllableJoint(new Vec3(-0.25f, -0.025f, 0), -80, 0, 
						Vec3.X_AXIS, leftThighScheme);
		rig.appendJointTo(waist, leftThighControllableJoint);
		
		RectPrism leftThigh = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.3f, 0.5f, 0.4f);
		rig.appendSegmentTo(leftThighControllableJoint, leftThigh);
		
		// ========= left shin =========================================
		JointControlScheme leftShinScheme =
				new JointControlScheme(Keyboard.KEY_V, Keyboard.KEY_F);
		ControllableJoint leftShinControllableJoint = 
				new ControllableJoint(new Vec3(0, -0.25f, 0), 0, 80, 
						 Vec3.X_AXIS, leftShinScheme);
		rig.appendJointTo(leftThigh, leftShinControllableJoint);
		
		RectPrism leftShin = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.3f, 0.5f, 0.4f);
		rig.appendSegmentTo(leftShinControllableJoint, leftShin);
		
		// ========= left shin effector ======================================
		IcosahedralEndEffector leftShinEffector = 
				new IcosahedralEndEffector(new Vec3(0, -0.5f, 0));
		rig.appendEndEffectorTo(leftShin, leftShinEffector);
		
		// ========= right thigh =========================================
		JointControlScheme rightThighScheme =
				new JointControlScheme(Keyboard.KEY_T, Keyboard.KEY_5);
		ControllableJoint rightThighControllableJoint = 
				new ControllableJoint(new Vec3(0.25f, -0.025f, 0), -80, 0, 
						Vec3.X_AXIS, rightThighScheme);
		rig.appendJointTo(waist, rightThighControllableJoint);
		
		RectPrism rightThigh = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.3f, 0.5f, 0.4f);
		rig.appendSegmentTo(rightThighControllableJoint, rightThigh);
		
		// ========= right shin =========================================
		JointControlScheme rightShinScheme =
				new JointControlScheme(Keyboard.KEY_B, Keyboard.KEY_G);
		ControllableJoint rightShinControllableJoint = 
				new ControllableJoint(new Vec3(0, -0.25f, 0), 0, 80, 
						Vec3.X_AXIS, rightShinScheme);
		rig.appendJointTo(rightThigh, rightShinControllableJoint);
		
		RectPrism rightShin = new RectPrism(new Vec3(0, -0.25f, 0), 
				0.3f, 0.5f, 0.4f);
		rig.appendSegmentTo(rightShinControllableJoint, rightShin);
		
		// ========= right shin effector ======================================
		IcosahedralEndEffector rightShinEffector = 
				new IcosahedralEndEffector(new Vec3(0, -0.5f, 0));
		rig.appendEndEffectorTo(rightShin, rightShinEffector);
		
		return waistControllableJoint;
	}
}
