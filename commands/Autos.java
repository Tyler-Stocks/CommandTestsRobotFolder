// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveSubsystem;
//import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAutoDrive(DriveSubsystem subsystem) {
    return Commands.sequence(subsystem.driveDistanceCommand(3, .5), subsystem.driveDistanceCommand(-3, 0.5));
  }

  public static Command exampleAutoArm(ArmSubsystem subsystem) {
    return Commands.sequence(subsystem.RunJointsToThetaRZCommand(0,22,10), subsystem.RunJointsToThetaRZCommand(10,20,20));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
