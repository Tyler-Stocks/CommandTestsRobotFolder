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
    public static final class ArmMoveConstants{
        public static final int[] GROUND_PICKUP_POSITION_RZ = {20,-3};
        public static final int[] SHELF_POSITION_RZ = {25,36};
        public static final int[] HIGH_SCORING_POSITION_RZ = {45,45};
        public static final int[] MEDIUM_SCORING_POSITION_RZ = {(int)Math.hypot(34.1, 37.3),32};
        public static final int CLAW_OPEN_POSITION = 0;
        public static final int CLAW_CONE_POSITION = 100;//13; 100 Goes right to the max set in armConstants
        public static final int CLAW_CUBE_POSITION = 5;
    }
  
  

  
}
