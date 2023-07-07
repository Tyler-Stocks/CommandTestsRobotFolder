package frc.robot.subsystems.ArmSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

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

  private static double azimuthAngle = 0;
  private static double ShoulderAngle = 90;
  private static double elbowAngle =15;
  private static double clawPosition =0;
  private static double radius;//not defined by default
  private static double height;//not defined by default
  private static int cumulativeDivisor=10;

  public void initialize() {
    //force claw and azimuth into homed condition since they have no limit switch
    AzimuthJoint.forceHomed();
    ClawJoint.forceHomed();
  }
     
  public double[] getArmAngles() {
    double[] l_currentPos = {azimuthAngle,ShoulderAngle,elbowAngle};
    return l_currentPos;
  }

  public double[] getArmPositionThetaRZ() {
    // Calculations done in Radians for 2 segment arm of equal segment lengths
    //Length from shoulder to claw
    double l_Hypotenus = 2 * ArmConstants.ARM_LENGTH1 * Math.sin(elbowAngle / 2 * Math.PI / 180);
    //intetior angle between humerous and claw
    double l_Theta1_1 = 90-elbowAngle/2;
    //angle between ground and claw
    double l_ThetaGroundClaw = ShoulderAngle - l_Theta1_1;
  
    // Define values
    radius = l_Hypotenus * Math.cos(l_ThetaGroundClaw * Math.PI / 180); 
    height = l_Hypotenus * Math.sin(l_ThetaGroundClaw * Math.PI / 180);
    
    //azimuth does not need to be converted
    double[] l_currentPos = {azimuthAngle,radius,height};
    return l_currentPos;
  }

  public boolean shoulderAndElbowHomedCondition() {
    // Query some boolean state, such as a digital sensor.
    return (ShoulderJoint.homedCondition()&&ElbowJoint.homedCondition());
  }

  public static void zeroEncodersHere(){//for test *****
     ShoulderJoint.zeroEncoder();
     ShoulderJoint.unhome();
     ElbowJoint.zeroEncoder();
     ElbowJoint.unhome();
  }
 
  // Homes the Shoulder joint First, Followed by the Elbow joint. not foolproof for in competition homing
  public CommandBase homeAll() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return run(()->{if(!ShoulderJoint.homedCondition()){ShoulderJoint.homeJoint();}
    else if(!ElbowJoint.homedCondition()){ElbowJoint.homeJoint();}}).until(ElbowJoint::homedCondition);
  }
 
  //Runs joints to the saved posiiton used for holding robot set position.
  public CommandBase RunJointsToSetAnglesCommand() {
    return runOnce(()->{RunJointsToSetAngles();});
  }
  public CommandBase cumulativeJointsToSetAngles(DoubleSupplier l_azimuthAxis,DoubleSupplier l_shoulderAxis,DoubleSupplier l_elbowAxis,DoubleSupplier l_clawTotal) {
    return runOnce(()->{
      azimuthAngle+= l_azimuthAxis.getAsDouble()/cumulativeDivisor;
      ShoulderAngle += l_shoulderAxis.getAsDouble()/cumulativeDivisor;
      elbowAngle += l_elbowAxis.getAsDouble()/cumulativeDivisor;
      clawPosition += l_clawTotal.getAsDouble();
      RunJointsToSetAngles();});
  }

  public CommandBase POVInputCommand(IntSupplier l_POVAziumuth) {
    return runOnce(() -> this.setArmAzimuth(l_POVAziumuth.getAsInt()))
        .withName("fine tuning Arm Motor");

  }

  public CommandBase RunJointsToThetaRZCommand(double l_theta, double l_r, double l_z) {
    return runOnce(()->{RunJointsToThetaRZ(l_theta, l_r, l_z);});
  }
  // //temporary override to put the joints in another position
  // public CommandBase RunJointsToAngles(double l_angleAzimuth, double l_angleShoulder, double l_angleElbow, double l_clawPosition) {
  //   // Inline construction of command goes here.
  //   // Subsystem::RunOnce implicitly requires `this` subsystem.
  //   return runOnce(()->{AzimuthJoint.RunJointToAngle(l_angleAzimuth);
  //     ShoulderJoint.RunJointToAngle(l_angleShoulder);
  //     ElbowJoint.RunJointToAngle(l_angleElbow);
  //   ClawJoint.RunJointToAngle(l_clawPosition);});
  // }
  

  //sets the arm azimuth to limit motion to +/-180 degrees. Does not affect fine control
  private void setArmAzimuth(double l_azimuth) {
    if (l_azimuth>180){
      l_azimuth-=360;
    }
    azimuthAngle = l_azimuth;
    System.out.println(l_azimuth);
  }

  private void RunJointsToSetAngles(){
    AzimuthJoint.RunJointToAngle(azimuthAngle);
    ShoulderJoint.RunJointToAngle(ShoulderAngle);
    if(ShoulderAngle>80){
      ElbowJoint.RunJointToAngle(elbowAngle,180,ArmConstants.THETA2_HOMED_OFFSET);
    }else{ElbowJoint.RunJointToAngle(elbowAngle);}
    
    ClawJoint.RunJointToAngle(clawPosition);
  }

  private void RunJointsToThetaRZ(double l_theta, double l_r, double l_z){
    double [] l_angles = convertThetaRZtoAngles(l_theta, l_r, l_z);
    //set those angles to the class variables
    radius=l_r;
    height=l_z;
    azimuthAngle=l_angles[0];
    ShoulderAngle=l_angles[1];
    elbowAngle=l_angles[2];
    RunJointsToSetAngles();
  }

  //does not work for negative radius, cannot handle the arm going overhead, no need to fix
  //also does not work for arms with different segment lengths
  private static double[] convertThetaRZtoAngles(double l_theta, double l_r, double l_z){
    double l_hypotenus = Math.sqrt(l_r*l_r+l_z*l_z);
    double l_thetaGroundClaw= Math.atan(l_z/l_r)*180/3.14;
    //if statement prevents attempt to reach beyond arm lengths
    if (l_hypotenus>(ArmConstants.ARM_LENGTH1+ArmConstants.ARM_LENGTH2)){
      l_hypotenus=ArmConstants.ARM_LENGTH1+ArmConstants.ARM_LENGTH2;
    }
    //angle btween claw and humerous // only works for same segment length arms
    double l_Theta1_1= Math.acos((l_hypotenus/2)/ArmConstants.ARM_LENGTH1)*180/3.14;
    double l_shoulder = l_thetaGroundClaw+l_Theta1_1;
    double l_elbow = 180-2*l_Theta1_1;

    double[] l_angles ={l_theta,l_shoulder,l_elbow};
    return l_angles;
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



}