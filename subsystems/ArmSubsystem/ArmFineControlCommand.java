// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.ArmSubsystem; //it is in this package so it can control the arm 
//without lowering the restrictions on the private and protected classes within the ArmSubsystem 
//If it is not in the same package, then it leaves the Subsystem Vulnerable to access outside 
//the command structure

import frc.robot.subsystems.Controllers.PersonalizedController;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArmFineControlCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ArmSubsystem m_armSubsystem;
  private final PersonalizedController m_controller;
  //private int i=0;
  private static double[] m_armThetaRZ= new double[2];
   

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArmFineControlCommand(ArmSubsystem subsystem, PersonalizedController Controller) {
    m_armSubsystem = subsystem;
    m_controller=Controller;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("fine Control Enabled");
    m_armThetaRZ= m_armSubsystem.getArmPositionThetaRZ();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    getCumulativeThetaRZ(() -> (m_controller.getLeftRight()),() -> (m_controller.getInOut()),() -> (m_controller.getUpDown()));
    m_armSubsystem.RunJointsToThetaRZ(m_armThetaRZ[0],m_armThetaRZ[1],m_armThetaRZ[2]);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("fine Control Disabled");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  private void getCumulativeThetaRZ(DoubleSupplier l_thetaIncrement,DoubleSupplier l_rIncrement,DoubleSupplier l_zIncrement){
    int l_divisor =50/10;//50 hz divided by inches per second
    m_armThetaRZ[0]-=l_thetaIncrement.getAsDouble()*Math.abs(l_thetaIncrement.getAsDouble())/l_divisor*2;//sped up because this axis is degrees not inches
    m_armThetaRZ[1]-=l_rIncrement.getAsDouble()*Math.abs(l_rIncrement.getAsDouble())/l_divisor;
    m_armThetaRZ[2]-=l_zIncrement.getAsDouble()*Math.abs(l_zIncrement.getAsDouble())/l_divisor;
  }
}
