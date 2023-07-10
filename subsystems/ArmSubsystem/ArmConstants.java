package frc.robot.subsystems.ArmSubsystem; 
// Constants for Arm Subsystem

public final class ArmConstants {
    //Global Joint Constants
    public static final Double DEFAULT_JOINT_SPEED =0.3+.2;
        //Resetting 
    public static final Double DEFAULT_HOMING_SPEED = 0.15;

    // Joint Specific Constants
    //AzimuthJoint
    public static final double AZIMUTH_JOINT_SPEED = DEFAULT_JOINT_SPEED;
    public static final double AZIMUTH_HOMING_SPEED = DEFAULT_HOMING_SPEED;
    public static final int AZIMUTH_MAX = 200;
    public static final int AZIMUTH_MIN = -200;
    public static final int AZIMUTH_START_OFFSET = -9;//for claw open positon
    public static final int AZIMUTH_MOTOR_ID = 2;
    public static final int AZIMUTH_HOMED_OFFSET = 0;
    
    //public static final int AZIMUTH_OFFSET_HOME =0 ;//not defined

    //ShoulderJoint
    public static final double SHOULDER_JOINT_SPEED = DEFAULT_JOINT_SPEED;
    public static final double SHOULDER_HOMING_SPEED = DEFAULT_HOMING_SPEED;
    public static final int THETA1_MAX = 135;
    public static final int THETA1_MIN = 30;
    public static final int THETA1_START_OFFSET = 126;//*****//90; //ANGLE FROM BASE BACK TO SEGMENT 1
    public static final int SHOULDER_MOTOR_ID = 3;
    public static final int THETA1_HOMED_OFFSET = 126;//angle to center of elbow axis not 2x4

    //ElbowJoint
    public static final double ELBOW_JOINT_SPEED = DEFAULT_JOINT_SPEED+ 0.1;
    public static final double ELBOW_HOMING_SPEED = DEFAULT_HOMING_SPEED;
    public static final int THETA2_MAX = 180;
    public static final int THETA2_MIN = 30;
    public static final int THETA2_START_OFFSET = 14;//ANGLE FROM ARM SEGMENT  1 TO 2 //was 13 is 18 only for pool noodle *****
    public static final int ELBOW_MOTOR_ID = 4;
    public static final int THETA2_HOMED_OFFSET = THETA2_START_OFFSET;
    //ClawJoint
    
    public static final double CLAW_JOINT_SPEED = 0.2;
    public static final double CLAW_HOMING_SPEED = DEFAULT_HOMING_SPEED;
    public static final int CLAW_MAX = 15;
    public static final int CLAW_MIN = 0;
    public static final int CLAW_START_OFFSET = 0;//for claw open positon
    public static final int CLAW_MOTOR_ID = 5;
    public static final int CLAW_HOMED_OFFSET = 0;

    // Arm Lengths
    public static final int ARM_LENGTH1 = 40; // Arm Length is in inches
    public static final int ARM_LENGTH2 = ARM_LENGTH1; 

    

    // Limit Switch Directions ; True = Forward, False = Reverse
    public static final boolean AZIMUTH_LIMIT_SWITCH_DIRECTION = false;
    public static final boolean SHOULDER_LIMIT_SWITCH_DIRECTION = false;
    public static final boolean ELBOW_LIMIT_SWITCH_DIRECTION = false;
    public static final boolean CLAW_LIMIT_SWITCH_DIRECTION = false;

    // degree conversions
    public static final double AZIMUTH_DEGREES_PER_REVOLUTION= 152.3/90;
    public static final double SHOULDER_DEGREES_PER_REVOLUTION= -(22.9-6)/20;//negative beceause the +motor direction is opposite the axis
    public static final double ELBOW_DEGREES_PER_REVOLUTION = 153.0/100;
    public static final double CLAW_DEGREES_PER_REVOLUTION= 1.0;





    // Limit Offsets
    
    
    //ublic static final int ELBOW_OFFSET = 10;

  }