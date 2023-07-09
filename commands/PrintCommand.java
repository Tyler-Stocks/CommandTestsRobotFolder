// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem.ArmSubsystem;

/** An example command that uses an example subsystem. */
public class PrintCommand extends CommandBase {
  private String m_string;
  private ArmSubsystem m_ArmSubsystem;
  
   

  public PrintCommand(String l_string) {
    m_string = l_string;
  }
  public PrintCommand(String l_string, ArmSubsystem l_ArmSubsystem) {
    m_string = l_string;
    m_ArmSubsystem= l_ArmSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println(m_string);
    if(m_string.equals("XYZ")){
      double []values = m_ArmSubsystem.getArmPositionXYZ();
      System.out.print(values[0]+"; ");
      System.out.print(values[1]+"; ");
      System.out.println(values[2]+"; ");

    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
