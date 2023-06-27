package frc.robot.subsystems.ArmSubsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import java.util.function.DoubleSupplier;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.SparkmaxMotor;


public class Joint extends SparkmaxMotor {

  // Define all of the motors
  SparkmaxMotor jointMotor; //= new SparkmaxMotor(ArmConstants.AZIMUTH_MOTOR_ID, ArmConstants.AZIMUTH_LIMIT_SWITH_DIRECTION,0.3, 0.3);

  private double m_thetaCurrentSetting,m_homingSpeed, m_degreesPerRev;

  private int m_maxAngle,m_minAngle,m_homePositionAngle,m_startingAngle;
  
  private int i=0;
  private boolean JointZeroed=false;

  public Joint(int deviceID,Boolean limitSwitchDirection, 
  double l_max_output, double l_min_output, 
  double l_homingSpeed, int l_startingAngle,
  int l_maxAngle, int l_minAngle,
  int l_homePositionAngle, double l_degreesPerRev){

    super(deviceID, limitSwitchDirection,l_max_output,l_min_output);
    m_thetaCurrentSetting=l_startingAngle;
    m_homingSpeed = l_homingSpeed;
    m_maxAngle=l_maxAngle;
    m_minAngle=l_minAngle;
    m_degreesPerRev=l_degreesPerRev;
  }

  //shorter method constructor for default max/min angles
  public void RunJointToPosition(double l_thetaDesiredPos){
    RunJointToPosition(l_thetaDesiredPos,m_maxAngle,m_minAngle);
  }

  //longer constructor to allow changed max and min angles when other joints are limiting the range
  public void RunJointToPosition(double l_thetaDesiredPos,int l_maxAngle, int l_minAngle) {
    //checks for software limits with min and max angles
    double m_theta1 = Math.min(Math.max(l_minAngle, l_thetaDesiredPos),l_maxAngle);
    
    //adjustments for offsets and conversions
    if (JointZeroed){
      runToposition((m_theta1-m_homePositionAngle)/m_degreesPerRev);
    }else{
      runToposition((m_theta1-m_startingAngle)/m_degreesPerRev);
    }

    
    
  }

  public CommandBase homeJoint() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return run(
      () -> {
        
        if(i%2==0){
          //System.out.println("home "+i);
          if (!JointZeroed && !limitSwitch.isPressed()) {
            adjustMaxPID(-m_homingSpeed, m_homingSpeed);
            if (super.m_limitSwitchDirection) {
              //set velocity forward
              m_thetaCurrentSetting+=0.5;
              RunJointToPosition(m_thetaCurrentSetting);
              //m_pidControllerVel.setReference(ArmConstants.RESETTING_SPEED, CANSparkMax.ControlType.kVelocity);
            }else {
              //set velocity reverse
              m_thetaCurrentSetting-=0.5;
              RunJointToPosition(m_thetaCurrentSetting);
        
            }
          }else{
            if(limitSwitch.isPressed()){
              zeroEncoder(); 
              resetMaxPID();
              JointZeroed=true;
              
            }
          }
        }
      });

  }
  public CommandBase RunJointToAngle(double angle) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          RunJointToPosition(angle);
       
          if(i%50==0){
            System.out.println("default "+i); 
           }
        });
  }


  public boolean homedCondition() {
    // Query some boolean state, such as a digital sensor.
    
    return JointZeroed;
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

}