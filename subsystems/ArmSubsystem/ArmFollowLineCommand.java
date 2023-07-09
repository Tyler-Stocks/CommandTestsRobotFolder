// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ArmSubsystem; //it is in this package so it can control the arm 
//without lowering the restrictions on the private and protected classes within the ArmSubsystem 
//If it is not in the same package, then it leaves the Subsystem Vulnerable to access outside 
//the command structure

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArmFollowLineCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ArmSubsystem m_armSubsystem;
  private final double  m_r2, m_z2, m_speedIPS;
  private double m_r1,  m_z1;
  private int numOfCycles,m_cycleNumber;
  private double m_Theta;
  //private int i=0;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmFollowLineCommand(ArmSubsystem subsystem, double l_endR, double l_endZ, double speedIPS) {
    m_armSubsystem = subsystem;
    m_r2=l_endR;
    m_z2=l_endZ;
    m_speedIPS=speedIPS;
    
    addRequirements(m_armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double[] l_thetaRZStart=m_armSubsystem.getArmPositionThetaRZ();
    m_Theta=l_thetaRZStart[0];
    m_r1=l_thetaRZStart[1];//this all must be in initialize() because if in the constructor it happens on robot init
    m_z1=l_thetaRZStart[2];
    //calculate number of cycles given speed
    numOfCycles= Math.max((int)(Math.hypot(m_r2-m_r1, m_z2-m_z1)/m_speedIPS*50),1);//set minimum cycles to 1 since the execute runs before it checks if it is finished
    //it goes wild if it tries do 0*1/0 (zero times infinity!), which would happen if point 1 is where point 2 is;
    m_cycleNumber=1;//not zero because we do want it to move on the first execution
    System.out.println("linear Interpolating");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //this version of java seems to have a hard time if I use a double array to return both the x and y values from any method.
    double[] l_rzTarget={interpolate(m_r1, m_r2,m_cycleNumber, numOfCycles),interpolate( m_z1, m_z2,m_cycleNumber, numOfCycles)};

    m_armSubsystem.RunJointsToThetaRZ(m_Theta, l_rzTarget[0], l_rzTarget[1]);
    m_cycleNumber++;
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("done interpolating");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_cycleNumber>numOfCycles);
  }
  private static double interpolate(double x1, double x2,  int step, int totalSteps){
    double output=x1 + (x2 - x1) * step/totalSteps;
    return output;
  }
  
}
