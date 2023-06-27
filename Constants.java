// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  // Constants for Arm Subsystem
  public static final class ArmConstants {

    // Arm angle mininum values in degrees
    public static final int THETA1_MAX = 135;
    public static final int THETA2_MAX = 180;
    public static final int THETA1_MIN = 30;
    public static final int THETA2_MIN = 30;

    public static final int THETA1_START = 98; //ANGLE FROM BASE BACK TO SEGMENT 1
    public static final int THETA2_START = 10;//ANGLE FROM ARM SEGMENT  1 TO 2
    public static final int AZIMUTH_START = -9;//for claw open positon

  

    // Arm Lengths
    public static final int ARM_LENGTH1 = 40; // Arm Length is in inches
    public static final int ARM_LENGTH2 = ARM_LENGTH1; 

    // Motor ID's
    public static final int AZIMUTH_MOTOR_ID = 2;
    public static final int SHOULDER_MOTOR_ID = 3;
    public static final int ELBOW_MOTOR_ID = 4;
    public static final int CLAW_MOTOR_ID = 5;

    //Resseting Constants
    public static final Double RESETTING_SPEED = 0.1;

    // Limit Switch Directions ; True = Forward, False = Reverse
    public static final boolean AZIMUTH_LIMIT_SWITH_DIRECTION = false;
    public static final boolean SHOULDER_LIMIT_SWITCH_DIRECTION = false;
    public static final boolean ELBOW_LIMIT_SWITCH_DIRECTION = false;
    public static final boolean CLAW_LIMIT_SWITCH_DIRECTION = false;

    // degree conversions
    public static final double AZIMUTH_DEGREES_PER_REVOLUTION= 152.3/90;
    public static final double SHOULDER_DEGREES_PER_REVOLUTION= (22.9-6)/20;
    public static final double ELBOW_DEGREES_PER_REVOLUTION = 153.0/100;
     //1;(44-16)/20;
    public static final double CLAW_DEGREES_PER_REVOLUTION= 1;





    // Limit Offsets
    public static final int AZIMUTH_OFFSET = -15;
    public static final int SHOULDER_OFFSET = 130;
    public static final int ELBOW_OFFSET = 10;

  }
}
