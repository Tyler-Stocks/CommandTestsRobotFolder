// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import frc.robot.Constants.ArmMoveConstants;
import frc.robot.subsystems.ArmSubsystem.ArmFollowLineCommand;
import frc.robot.subsystems.ArmSubsystem.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public final class MultiLine {
  /** Example static factory for an autonomous command. */
  public static CommandBase MultiLineToShelf(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 15, 35, 35), 
    new ArmFollowLineCommand(m_ArmSubsystem, ArmMoveConstants.SHELF_POSITION_RZ[0], ArmMoveConstants.SHELF_POSITION_RZ[1], 15));
    
  }
  public static CommandBase MultiLineToMedium(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 20, 35, 35), 
    new ArmFollowLineCommand(m_ArmSubsystem, ArmMoveConstants.MEDIUM_SCORING_POSITION_RZ[0], ArmMoveConstants.MEDIUM_SCORING_POSITION_RZ[1], 15));
    
  }
  public static CommandBase MultiLineToHigh(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 30, 46, 35), 
    new ArmFollowLineCommand(m_ArmSubsystem, ArmMoveConstants.HIGH_SCORING_POSITION_RZ[0], ArmMoveConstants.HIGH_SCORING_POSITION_RZ[1], 15));
    
  }

  public static CommandBase MultiLineDiamondTest(ArmSubsystem m_ArmSubsystem) {
    return Commands.sequence(new ArmFollowLineCommand(m_ArmSubsystem, 30, 4, 15), 
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 30, 25),
    
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 40, 10),
    new ArmFollowLineCommand(m_ArmSubsystem, 30, 60, 35));
  }

  // private MultiLine() {
  //   throw new UnsupportedOperationException("This is a utility class!");
  // }
}
