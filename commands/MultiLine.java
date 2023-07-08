// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmFollowLineCommand;
import frc.robot.subsystems.ArmSubsystem.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public final class MultiLine {
  /** Example static factory for an autonomous command. */
  public static CommandBase MultiLine1(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 30, 4, 15), 
    new ArmFollowLineCommand(m_ArmSubsystem, 40, 10, 25),
    
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 20, 25),
    new ArmFollowLineCommand(m_ArmSubsystem, 20, 10, 35));
  }

  public static CommandBase MultiLine2(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 30, 4, 15), 
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 30, 25),
    
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 40, 10),
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 60, 35));
  }

  // private MultiLine() {
  //   throw new UnsupportedOperationException("This is a utility class!");
  // }
}
