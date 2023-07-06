package frc.robot.subsystems.ArmSubsystem;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkmaxMotor  {
  
  private CANSparkMax m_motor;
  protected SparkMaxPIDController m_pidControllerPos;
  protected RelativeEncoder m_encoder;
  protected double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  
  protected boolean m_limitSwitchDirection=false; //false reverse true forward
  protected SparkMaxLimitSwitch limitSwitch;

  protected SparkmaxMotor(int deviceID,Boolean limitSwitchDirection, double l_max_output, double l_min_output) {
    m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_pidControllerPos = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();
    if(m_limitSwitchDirection){
      limitSwitch = m_motor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }else{
      limitSwitch = m_motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }
    initializePID(l_max_output,l_min_output);
  }

  protected void adjustMaxPID(double min, double max){
    m_pidControllerPos.setOutputRange(min, max);
  }

  protected void resetMaxPID(){
    m_pidControllerPos.setOutputRange(kMinOutput, kMaxOutput);
  }

  private void initializePID(double l_max_output, double l_min_output){
    // PID coefficients
    kP = 0.1; 
    kI = 0;//1e-4;
    kD = 0.01; //was 1 feb 24 test
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = l_max_output;
    kMinOutput = -l_min_output;
    // set PID coefficients
    m_pidControllerPos.setP(kP);
    m_pidControllerPos.setI(kI);
    m_pidControllerPos.setD(kD);
    m_pidControllerPos.setIZone(kIz);
    m_pidControllerPos.setFF(kFF);
    m_pidControllerPos.setOutputRange(kMinOutput, kMaxOutput);    
  }
  
  protected RelativeEncoder getEncoder(){
    return this.m_encoder;
  }

  protected void zeroEncoder(){   
    m_encoder.setPosition(0);
    System.out.println("zeroed "+m_motor.getDeviceId());
    m_pidControllerPos.setReference(0, CANSparkMax.ControlType.kPosition);    
  }

  protected void runToposition(double targetPosition){   
    m_pidControllerPos.setReference(targetPosition, CANSparkMax.ControlType.kPosition);
  }

  protected boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }
}