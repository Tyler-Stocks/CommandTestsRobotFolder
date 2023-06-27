package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import java.util.function.DoubleSupplier;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmConstants;


public class ArmSubsystem extends SubsystemBase {

  // Define all of the motors
  SparkmaxMotor AzimuthMotor = new SparkmaxMotor(ArmConstants.AZIMUTH_MOTOR_ID, ArmConstants.AZIMUTH_LIMIT_SWITH_DIRECTION,0.3, 0.3);
  public static SparkmaxMotor ShoulderMotor = new SparkmaxMotor(ArmConstants.SHOULDER_MOTOR_ID, ArmConstants.SHOULDER_LIMIT_SWITCH_DIRECTION,0.3, 0.3);
  SparkmaxMotor ElbowMotor = new SparkmaxMotor(ArmConstants.ELBOW_MOTOR_ID, ArmConstants.ELBOW_LIMIT_SWITCH_DIRECTION,0.4,0.4);
  SparkmaxMotor ClawMotor = new SparkmaxMotor(ArmConstants.CLAW_MOTOR_ID, ArmConstants.CLAW_LIMIT_SWITCH_DIRECTION,0.5,0.2); 

  private static float theta1CurrentSetting=ArmConstants.THETA1_START;
  private int i=0;
  private boolean ShoulderZeroed=false;

  private void RunShoulderToPosition(double m_theta1DesiredPos,double m_theta2AnalogOffset, double m_azimuthAnalogOffset,double m_claw) {
    double m_theta1;//=theta1CurrentSetting;

    //checks for software limits
    
      m_theta1= Math.max(98, m_theta1DesiredPos);//set  minimum angle to 98 when arm is within robot frame perimeter
    
    //adjustments for offsets and conversions
    if (ShoulderZeroed){
      ShoulderMotor.runToposition((-m_theta1+135)/ArmConstants.SHOULDER_DEGREES_PER_REVOLUTION);
    }else{
      ShoulderMotor.runToposition((-m_theta1+98)/ArmConstants.SHOULDER_DEGREES_PER_REVOLUTION);
    }

    
    
  }
  public void setArmPosition( float m_theta1, float m_theta2, float m_azimuth) {
    theta1CurrentSetting=m_theta1;
    // theta2CurrentSetting=m_theta2;  
    // azimuthCurrentSetting=m_azimuth; 
  }

  public CommandBase homeShoulder() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return run(
      () -> {
        
        if(i%2==0){
          //System.out.println("home "+i);
          if (!ShoulderZeroed && !ShoulderMotor.limitSwitch.isPressed()) {
            ShoulderMotor.adjustMaxPID(-0.2, 0.2);
            if (ArmConstants.SHOULDER_LIMIT_SWITCH_DIRECTION) {
              //set velocity forward
              //m_pidControllerVel.setReference(ArmConstants.RESETTING_SPEED, CANSparkMax.ControlType.kVelocity);
            }else {
              //set velocity reverse
              theta1CurrentSetting+=0.5;
              RunShoulderToPosition(theta1CurrentSetting,0,0,0);
        
            }
          }else{
            if(ShoulderMotor.limitSwitch.isPressed()){
              ShoulderMotor.zeroEncoder(); 
              ShoulderMotor.resetMaxPID();
              ShoulderZeroed=true;
              
            }
          }
        }
      });

  }
  public CommandBase RunShoulderToAngle(double angle) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          RunShoulderToPosition(angle,0,0,0);
       
          if(i%50==0){
            System.out.println("default "+i); 
           }
        });
  }


  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    
    return ShoulderZeroed;
  }

  @Override
  public void periodic() {
    i++;
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