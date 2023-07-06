package frc.robot.subsystems.ArmSubsystems;

//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import java.util.function.DoubleSupplier;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmConstants;


public class ArmSubsystem extends SubsystemBase {

  // Define all of the Joints
  // public Joint(int deviceID,Boolean limitSwitchDirection, 
  // double l_max_output, double l_min_output, 
  // double l_homingSpeed, int l_startingAngle,
  // int l_maxAngle, int l_minAngle,
  // int l_homePositionAngle, double l_degreesPerRev){
  private static Joint ShoulderJoint = new Joint(ArmConstants.SHOULDER_MOTOR_ID, ArmConstants.SHOULDER_LIMIT_SWITCH_DIRECTION,
  ArmConstants.SHOULDER_JOINT_SPEED,ArmConstants.SHOULDER_JOINT_SPEED,
  ArmConstants.SHOULDER_HOMING_SPEED, ArmConstants.THETA1_START_OFFSET,
  ArmConstants.THETA1_MAX, ArmConstants.THETA1_MIN,
  ArmConstants.THETA1_HOMED_OFFSET, ArmConstants.SHOULDER_DEGREES_PER_REVOLUTION);
  
  private static Joint ElbowJoint = new Joint(ArmConstants.ELBOW_MOTOR_ID, ArmConstants.ELBOW_LIMIT_SWITCH_DIRECTION,
  ArmConstants.ELBOW_JOINT_SPEED,ArmConstants.ELBOW_JOINT_SPEED,
  ArmConstants.ELBOW_HOMING_SPEED, ArmConstants.THETA2_START_OFFSET,
  ArmConstants.THETA2_MAX, ArmConstants.THETA2_MIN,
  ArmConstants.THETA2_HOMED_OFFSET, ArmConstants.ELBOW_DEGREES_PER_REVOLUTION);

  private static Joint AzimuthJoint = new Joint(ArmConstants.AZIMUTH_MOTOR_ID, ArmConstants.AZIMUTH_LIMIT_SWITCH_DIRECTION,
  ArmConstants.AZIMUTH_JOINT_SPEED,ArmConstants.AZIMUTH_JOINT_SPEED,
  ArmConstants.AZIMUTH_HOMING_SPEED, ArmConstants.AZIMUTH_START_OFFSET,
  ArmConstants.AZIMUTH_MAX, ArmConstants.AZIMUTH_MIN,
  ArmConstants.AZIMUTH_HOMED_OFFSET, ArmConstants.AZIMUTH_DEGREES_PER_REVOLUTION);

  private static Joint ClawJoint = new Joint(ArmConstants.CLAW_MOTOR_ID, ArmConstants.CLAW_LIMIT_SWITCH_DIRECTION,
  0.5,ArmConstants.CLAW_JOINT_SPEED,//OVERIDE MAX SPEED FOR CLOSING
  ArmConstants.CLAW_HOMING_SPEED, ArmConstants.CLAW_START_OFFSET,
  ArmConstants.CLAW_MAX, ArmConstants.CLAW_MIN,
  ArmConstants.CLAW_HOMED_OFFSET, ArmConstants.CLAW_DEGREES_PER_REVOLUTION);

  


  public void initialize() {
    //force claw and azimuth into homed condition
    AzimuthJoint.forceHomed();
    ClawJoint.forceHomed();

  }
  public static double azimuthPosition = 0;
  public static double shoulderPosition = 100;
  public static double elbowPosition =15;
  public static double clawPosition =0;

      
  public void setArmPosition( float m_theta1, float m_theta2, float m_azimuth) {
    //theta1CurrentSetting=m_theta1;
    // theta2CurrentSetting=m_theta2;  
    // azimuthCurrentSetting=m_azimuth; 
  }

  public CommandBase homeAll() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return run(()->{if(!ShoulderJoint.homedCondition()){ShoulderJoint.homeJoint();}
    else if(!ElbowJoint.homedCondition()){ElbowJoint.homeJoint();}}).until(ElbowJoint::homedCondition);
    

  }
  public CommandBase RunJointsToAngles(double l_angleAzimuth, double l_angleShoulder, double l_angleElbow, double l_clawPosition) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(()->{AzimuthJoint.RunJointToAngle(l_angleAzimuth);
      ShoulderJoint.RunJointToAngle(l_angleShoulder);
      ElbowJoint.RunJointToAngle(l_angleElbow);
    ClawJoint.RunJointToAngle(l_clawPosition);});
  }
  public CommandBase RunJointsToSetPoint() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(()->{AzimuthJoint.RunJointToAngle(azimuthPosition);
      ShoulderJoint.RunJointToAngle(shoulderPosition);
      ElbowJoint.RunJointToAngle(elbowPosition);
    ClawJoint.RunJointToAngle(clawPosition);});
  }
  public CommandBase cumulativeJointsToSetPoint(double l_azimuthAxis,double l_shoulderAxis,double l_elbowAxis,double l_clawTotal) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    
    return runOnce(()->{
      azimuthPosition+= l_azimuthAxis;
      shoulderPosition += l_shoulderAxis;
      elbowPosition += l_elbowAxis;
      clawPosition += l_clawTotal;
      AzimuthJoint.RunJointToAngle(azimuthPosition);
      ShoulderJoint.RunJointToAngle(shoulderPosition);
      ElbowJoint.RunJointToAngle(elbowPosition);
    ClawJoint.RunJointToAngle(clawPosition);});
  }


  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return (ShoulderJoint.homedCondition()&&ElbowJoint.homedCondition());
  }

  public static void zeroEncodersHere(){//for test
     ShoulderJoint.zeroEncoder();
     ShoulderJoint.unhome();
     ElbowJoint.zeroEncoder();
     ElbowJoint.unhome();
  }

  @Override
  public void periodic() {
    //i++;
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  // private static float theta1CurrentSetting=ArmConstants.THETA1_START;
  // private static float theta2CurrentSetting= ArmConstants.THETA2_START;
  // private static float azimuthCurrentSetting=0;
  // private static float theta1AnalogOffset=0;
  // private static float theta2AnalogOffset=0;
  // private static float azimuthAnalogOffset=0;
  // private static float claw=0;


//   public float[] getArmPosition(float Theta1, float Theta2) {

//     // Calculations done in Radians
//     float Hypotenus = 2 * ArmConstants.ARM_LENGTH1 * (float) Math.sin(Theta2 / 2 * Math.PI / 180);
//     float Theta1_1 = (float) Math.asin((ArmConstants.ARM_LENGTH1 / Hypotenus) * Math.sin(Theta2 * Math.PI / 180));
//     float Theta1_2 = Theta1 - Theta1_1;
  
//     // Define an arry of values
//     float[] HeightAndRadius = new float[1];
//     HeightAndRadius[0] = Hypotenus * (float) Math.cos(Theta1_2 * Math.PI / 180); 
//     HeightAndRadius[1] = Hypotenus * (float) Math.sin(Theta1_2 * Math.PI / 180);
    
//     // Return values
//     return HeightAndRadius;

//   }
    
//   public float[] GetArmAngle( float Hypotenus, float Theta1, float Theta2) {
//     float[] Thetas = new float[2]; // make it [ conversion factor * this.ShoulderMotor.getEncoderValue(), other one];

//     // Checks to see if the angles are the correct ones.
//     if (Theta1 > ArmConstants.THETA1_MIN || Theta1 < ArmConstants.THETA1_MAX || Theta2 > ArmConstants.THETA2_MIN || Theta2 < ArmConstants.THETA2_MAX) {

//       // Calculations done in Radians
//       Thetas[0] = (float) Math.acos((Math.pow(ArmConstants.ARM_LENGTH1, 2) + Math.pow(Hypotenus, 2) - Math.pow(ArmConstants.ARM_LENGTH2, 2)) / (2 * ArmConstants.ARM_LENGTH1 * Hypotenus));
//       Thetas[1] = (float) Math.acos((Math.pow(ArmConstants.ARM_LENGTH1, 2) + Math.pow(ArmConstants.ARM_LENGTH2, 2) - Math.pow(Hypotenus, 2)) / (2 * ArmConstants.ARM_LENGTH1 * ArmConstants.ARM_LENGTH2)); 
      
//       }
    
//     return Thetas;

//   }

//   private void RunAllMotorsToPosition(double m_theta1AnalogOffset,double m_theta2AnalogOffset, double m_azimuthAnalogOffset,double m_claw) {
//     float m_theta1=this.theta1CurrentSetting;
//     float m_theta2=this.theta2CurrentSetting;
//     float m_azimuth = this.azimuthCurrentSetting;

//     //checks for software limits
//     if (m_theta2<30){
//       m_theta1= Math.max(98,m_theta1);//set  minimum angle to 98 when arm is within robot frame perimeter
//     }
//     m_theta2=Math.max(m_theta2, 9);// prevent the elbow from trying to close less then start
//     if(m_theta1<97){
//       m_theta2=Math.max(m_theta2, 30);//prevent bringing the arm into the perimeter from the side
//     }

//     //adjustments for offsets and conversions

//     ShoulderMotor.runToposition((-m_theta1+ArmConstants.THETA1_START+m_theta1AnalogOffset)/ArmConstants.SHOULDER_DEGREES_PER_REVOLUTION);
//     ElbowMotor.runToposition((m_theta2-ArmConstants.THETA2_START+m_theta2AnalogOffset)/ArmConstants.ELBOW_DEGREES_PER_REVOLUTION);
//     AzimuthMotor.runToposition((m_azimuth-ArmConstants.AZIMUTH_START+m_azimuthAnalogOffset)/ArmConstants.AZIMUTH_DEGREES_PER_REVOLUTION);
//     ClawMotor.runToposition(m_claw);



//   }



//   public CommandBase analogArmInputsCommand(DoubleSupplier m_Aziumuth,DoubleSupplier m_Shoulder,DoubleSupplier m_Elbow,DoubleSupplier m_Claw) {

//     // A split-stick arcade command, with forward/backward controlled by the left
//     // hand, and turning controlled by the right.
//     return run(() -> this.RunAllMotorsToPosition(m_Shoulder.getAsDouble(),m_Elbow.getAsDouble(),m_Aziumuth.getAsDouble(),m_Claw.getAsDouble()))
//         .withName("fine tuning Arm Motor");

//   }
//   public CommandBase noAnalogArmCommand() {

//     // A split-stick arcade command, with forward/backward controlled by the left
//     // hand, and turning controlled by the right.
//     return run(() -> this.RunAllMotorsToPosition(0,0,0,0))
//         .withName("fixed arm position");

//   }
  

//   public CommandBase setArmPositionCommand( float c_theta1, float c_theta2) {

//     return runOnce(()->setArmPosition(c_theta1, c_theta2));

//   }
//   public CommandBase setArmAzimuthCommand( float l_azimuth) {

//     return runOnce(()->setArmPosition(theta1CurrentSetting, theta2CurrentSetting, l_azimuth));

//   }
//   public CommandBase setArmAzimuthR30Command() {

//     return runOnce(()->setArmPosition(theta1CurrentSetting, theta2CurrentSetting, azimuthCurrentSetting-30));

//   }
//   public CommandBase setArmAzimuthL30Command() {

//     return runOnce(()->setArmPosition(theta1CurrentSetting, theta2CurrentSetting, azimuthCurrentSetting+30));

//   }  
//   public CommandBase setArmPositionCommand( float c_theta1, float c_theta2, float c_azimuth) {

//     return runOnce(()->setArmPosition(c_theta1, c_theta2,c_azimuth));

// }
//   public void setArmPosition( float m_theta1, float m_theta2) {
//     theta1CurrentSetting=m_theta1;
//     theta2CurrentSetting=m_theta2;   
//   }
//   public void setArmPosition( float m_theta1, float m_theta2, float m_azimuth) {
//     theta1CurrentSetting=m_theta1;
//     theta2CurrentSetting=m_theta2;  
//     azimuthCurrentSetting=m_azimuth; 
//   }


}