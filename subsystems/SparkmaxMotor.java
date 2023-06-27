package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants.ArmConstants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;

//import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkmaxMotor extends SubsystemBase {
  
  private CANSparkMax m_motor;
  private SparkMaxPIDController m_pidControllerPos;
  private RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  
  protected boolean m_limitSwitchDirection=false; //false reverse true forward
  public SparkMaxLimitSwitch limitSwitch;

  public SparkmaxMotor(int deviceID,Boolean limitSwitchDirection) {
    m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_pidControllerPos = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();
    if(m_limitSwitchDirection){
      limitSwitch = m_motor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }else{
      limitSwitch = m_motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }
    
      
    // PID coefficients
    kP = 0.1; 
    kI = 0;//1e-4;
    kD = 0.01; //was 1 feb 24 test
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 0.2;//1; feb 24 test
    kMinOutput = -0.2;//-1; feb 24 test

    // set PID coefficients
    m_pidControllerPos.setP(kP);
    m_pidControllerPos.setI(kI);
    m_pidControllerPos.setD(kD);
    m_pidControllerPos.setIZone(kIz);
    m_pidControllerPos.setFF(kFF);
    m_pidControllerPos.setOutputRange(kMinOutput, kMaxOutput);
    

  }

  public SparkmaxMotor(int deviceID,Boolean limitSwitchDirection, double l_max_output, double l_min_output) {
    m_motor = new CANSparkMax(deviceID, MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_pidControllerPos = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();
    if(m_limitSwitchDirection){
      limitSwitch = m_motor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }else{
      limitSwitch = m_motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    }
    
    // PID coefficients
    kP = 0.1; 
    kI = 0;//1e-4;
    kD = 0.01; //was 1 feb 24 test
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = l_max_output;//1; march 2 test
    kMinOutput = -l_min_output;//-1; march 2 test

    // set PID coefficients
    m_pidControllerPos.setP(kP);
    m_pidControllerPos.setI(kI);
    m_pidControllerPos.setD(kD);
    m_pidControllerPos.setIZone(kIz);
    m_pidControllerPos.setFF(kFF);
    m_pidControllerPos.setOutputRange(kMinOutput, kMaxOutput);
    

  }
  public void coast(){
    //m_motor.disable();
    m_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

  }
  public void adjustMaxPID(double min, double max){
    m_pidControllerPos.setOutputRange(min, max);
  }

  public void resetMaxPID(){
    m_pidControllerPos.setOutputRange(kMinOutput, kMaxOutput);
  }

  
  public RelativeEncoder getEncoder(){
    return this.m_encoder;
  }
  public void zeroEncoder(){
   
    m_encoder.setPosition(0);
    System.out.println("zeroed ");
    m_pidControllerPos.setReference(0, CANSparkMax.ControlType.kPosition);
    
  }

  public void runToposition(double targetPosition){
   
    m_pidControllerPos.setReference(targetPosition, CANSparkMax.ControlType.kPosition);
  }

  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  // This method will be called once per scheduler run
  @Override
  public void periodic() {}

   // This method will be called once per scheduler run during simulation
  @Override
  public void simulationPeriodic() {}




}