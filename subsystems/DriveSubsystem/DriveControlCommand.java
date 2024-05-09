// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.DriveSubsystem; //it is in this package so it can control the arm 
//without lowering the restrictions on the private and protected classes within the ArmSubsystem 
//If it is not in the same package, then it leaves the Subsystem Vulnerable to access outside 
//the command structure

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Controllers.PersonalizedController;

/** An example command that uses an example subsystem. */
public class DriveControlCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_driveSubsystem;
  private final PersonalizedController m_controller;
  //private int i=0;
  private static double[] m_FTheta= new double[2];
   

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveControlCommand(DriveSubsystem subsystem, PersonalizedController Controller) {
    m_driveSubsystem = subsystem;
    m_controller=Controller;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("drive Enabled");
    getFTheta(() -> (m_controller.ArmInOutAxis()),() -> (m_controller.ArmLeftRightAxis()));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    getFTheta(() -> (m_controller.ArmInOutAxis()),() -> (m_controller.ArmLeftRightAxis()));
    m_driveSubsystem.arcadeDriveSquared(m_FTheta[0],m_FTheta[1]);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("drive Disabled");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  private void getFTheta(DoubleSupplier l_fwd,DoubleSupplier l_theta){

    m_FTheta[0]=l_fwd.getAsDouble();
    m_FTheta[1]=-l_theta.getAsDouble();
  }
}
