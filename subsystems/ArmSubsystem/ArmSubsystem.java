package frc.robot.subsystems.ArmSubsystem;

import java.util.function.IntSupplier;

//import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import java.util.function.DoubleSupplier;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



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
  ArmConstants.THETA1_HOMED_OFFSET, ArmConstants.SHOULDER_DEGREES_PER_REVOLUTION,
  false);
  
  private static Joint ElbowJoint = new Joint(ArmConstants.ELBOW_MOTOR_ID, ArmConstants.ELBOW_LIMIT_SWITCH_DIRECTION,
  ArmConstants.ELBOW_JOINT_SPEED,ArmConstants.ELBOW_JOINT_SPEED,
  ArmConstants.ELBOW_HOMING_SPEED, ArmConstants.THETA2_START_OFFSET,
  ArmConstants.THETA2_MAX, ArmConstants.THETA2_MIN,
  ArmConstants.THETA2_HOMED_OFFSET, ArmConstants.ELBOW_DEGREES_PER_REVOLUTION,
  false);

  private static Joint AzimuthJoint = new Joint(ArmConstants.AZIMUTH_MOTOR_ID, ArmConstants.AZIMUTH_LIMIT_SWITCH_DIRECTION,
  ArmConstants.AZIMUTH_JOINT_SPEED,ArmConstants.AZIMUTH_JOINT_SPEED,
  ArmConstants.AZIMUTH_HOMING_SPEED, ArmConstants.AZIMUTH_START_OFFSET,
  ArmConstants.AZIMUTH_MAX, ArmConstants.AZIMUTH_MIN,
  ArmConstants.AZIMUTH_HOMED_OFFSET, ArmConstants.AZIMUTH_DEGREES_PER_REVOLUTION,
  true);

  private static Joint ClawJoint = new Joint(ArmConstants.CLAW_MOTOR_ID, ArmConstants.CLAW_LIMIT_SWITCH_DIRECTION,
  0.5,ArmConstants.CLAW_JOINT_SPEED,//OVERIDE MAX SPEED FOR CLOSING
  ArmConstants.CLAW_HOMING_SPEED, ArmConstants.CLAW_START_OFFSET,
  ArmConstants.CLAW_MAX, ArmConstants.CLAW_MIN,
  ArmConstants.CLAW_HOMED_OFFSET, ArmConstants.CLAW_DEGREES_PER_REVOLUTION,
  false);

  private static double azimuthAngle = 0;
  private static double ShoulderAngle = 90;
  private static double elbowAngle =15;
  private static double clawPosition =0;
  
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
    double l_radius = l_Hypotenus * Math.cos(l_ThetaGroundClaw * Math.PI / 180); 
    double l_height = l_Hypotenus * Math.sin(l_ThetaGroundClaw * Math.PI / 180);
    
    //azimuth does not need to be converted
    double[] l_currentPos = {azimuthAngle,l_radius,l_height};
    return l_currentPos;
  }
  public double[] getArmPositionXYZ() {
    // Calculations done in Radians for 2 segment arm of equal segment lengths
    //Length from shoulder to claw
    double l_x,l_y,l_z;
    double l_Hypotenus = 2 * ArmConstants.ARM_LENGTH1 * Math.sin(elbowAngle / 2 * Math.PI / 180);
    //intetior angle between humerous and claw
    double l_Theta1_1 = 90-elbowAngle/2;
    //angle between ground and claw
    double l_ThetaGroundClaw = ShoulderAngle - l_Theta1_1;
  
    // Define values
    double l_radius = l_Hypotenus * Math.cos(l_ThetaGroundClaw * Math.PI / 180); 
    l_x=l_radius*Math.sin(azimuthAngle* Math.PI / 180);
    l_y=l_radius*Math.cos(azimuthAngle* Math.PI / 180);
    l_z= l_Hypotenus * Math.sin(l_ThetaGroundClaw * Math.PI / 180);
    
    //azimuth does not need to be converted
    double[] l_currentPos = {l_x,l_y,l_z};
    return l_currentPos;
  }
  public boolean shoulderAndElbowHomedCondition() {
    // Query some boolean state, such as a digital sensor.
    return (ShoulderJoint.homedCondition()&&ElbowJoint.homedCondition());
  }
 
  // Homes the Shoulder joint First, Followed by the Elbow joint. not foolproof for in competition homing
  public CommandBase homeAll() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return run(()->{if(!ShoulderJoint.homedCondition()){ShoulderJoint.homeJoint();}
    else if(!ElbowJoint.homedCondition()){ElbowJoint.homeJoint();}}).until(ElbowJoint::homedCondition);
  }
 
  //Default Runs joints to the saved posiiton used for holding robot set position.
  public CommandBase RunJointsToSetAnglesCommand() {
    return runOnce(()->{RunJointsToSetAngles();});
  }
  public CommandBase setClawPositionCommand(int l_position) {
    return runOnce(()->{
      clawPosition=l_position;
      RunJointsToSetAngles();}
    );
  }
  //uses the set arm azimuth to take the POV input and move the arm quickly
  public CommandBase POVInputCommand(IntSupplier l_POVAziumuth) {
    return runOnce(() -> this.setArmAzimuth(l_POVAziumuth.getAsInt()))
        .withName("fine tuning Arm Motor");

  }
  //Self descriptive
  public CommandBase RunJointsToThetaRZCommand(double l_theta, double l_r, double l_z) {
    return runOnce(()->{RunJointsToThetaRZ(l_theta, l_r, l_z);});
  }
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
    if(ShoulderAngle>82){
      ElbowJoint.RunJointToAngle(elbowAngle,180,ArmConstants.THETA2_HOMED_OFFSET);
    }else{ElbowJoint.RunJointToAngle(elbowAngle);}
    
    ClawJoint.RunJointToAngle(clawPosition);
  }
//The next two are protected classes used for the separate advanced commands within the subsystem
  protected void RunJointsToThetaRZ(double l_theta, double l_r, double l_z){//protected to keep control within commands
    double [] l_angles = convertThetaRZtoAngles(l_theta, l_r, l_z);
    //set those angles to the class variables
    
    azimuthAngle=l_angles[0];
    ShoulderAngle=l_angles[1];
    elbowAngle=l_angles[2];
    RunJointsToSetAngles();
  }
  protected void RunJointsToXYZ(double l_x, double l_y, double l_z){//protected to keep control within commands
    double [] l_angles = convertXYZtoAngles(l_x, l_y, l_z);
    //set those angles to the class variables
    
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

  private static double[] convertXYZtoAngles(double l_x, double l_y, double l_z){
    double l_hypotenus = Math.sqrt(l_x*l_x+l_y*l_y+l_z*l_z);
    double l_radius = Math.sqrt(l_x*l_x+l_y*l_y);
    double l_thetaGroundClaw= Math.atan(l_z/l_radius)*180/3.14;
    //if statement prevents attempt to reach beyond arm lengths
    if (l_hypotenus>(ArmConstants.ARM_LENGTH1+ArmConstants.ARM_LENGTH2)){
      l_hypotenus=ArmConstants.ARM_LENGTH1+ArmConstants.ARM_LENGTH2;
    }
    //angle btween claw and humerous // only works for same segment length arms
    double l_Theta1_1= Math.acos((l_hypotenus/2)/ArmConstants.ARM_LENGTH1)*180/3.14;
    double l_shoulder = l_thetaGroundClaw+l_Theta1_1;
    double l_elbow = 180-2*l_Theta1_1;

    double l_azimuth = Math.atan2(l_x, l_y)*180/3.14;

    double[] l_angles ={l_azimuth,l_shoulder,l_elbow};
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
  
  public void forInitUseOnlyHold(){
    azimuthAngle=AzimuthJoint.getTrueJointAngle();
    System.out.println("azimuth angle" + azimuthAngle);
    //if (shoulderAndElbowHomedCondition()){
      ShoulderAngle=ShoulderJoint.getTrueJointAngle();
      System.out.println("shoulder angle" + ShoulderAngle);
      elbowAngle= ElbowJoint.getTrueJointAngle();
      System.out.println("elbow angle" + elbowAngle);
    //}
    
  }

  /*Test Stuff
    // //temporary override to put the joints in another position
  // public CommandBase RunJointsToAngles(double l_angleAzimuth, double l_angleShoulder, double l_angleElbow, double l_clawPosition) {
  //   // Inline construction of command goes here.
  //   // Subsystem::RunOnce implicitly requires `this` subsystem.
  //   return runOnce(()->{AzimuthJoint.RunJointToAngle(l_angleAzimuth);
  //     ShoulderJoint.RunJointToAngle(l_angleShoulder);
  //     ElbowJoint.RunJointToAngle(l_angleElbow);
  //   ClawJoint.RunJointToAngle(l_clawPosition);});
  // }
  // public static void zeroEncodersHere(){//for test *****
  //   ShoulderJoint.zeroEncoder();
  //   ShoulderJoint.unhome();
  //   ElbowJoint.zeroEncoder();
  //   ElbowJoint.unhome();
  // }
*/
//   

}