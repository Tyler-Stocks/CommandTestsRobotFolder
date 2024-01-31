// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Controllers.*;
import frc.robot.commands.*;
import frc.robot.Constants.ArmMoveConstants;
import frc.robot.subsystems.ArmSubsystem.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  static final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  //private final ps4Brandon m_PersonalizedController =new ps4Brandon(0);
  static final xBoxBrandon m_PersonalizedController =new xBoxBrandon(0);
   // Replace with CommandPS4Controller or CommandJoystick if needed
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    m_ArmSubsystem.initialize();
    setDefaultCommands();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        
    new Trigger(m_PersonalizedController::povPressed)
        .onTrue(m_ArmSubsystem.POVInputCommand(() -> (m_PersonalizedController.getPOVValue())));

    new JoystickButton(m_PersonalizedController, m_PersonalizedController.enableFineControlButton())
      .whileTrue(new ArmFineControlCommand(m_ArmSubsystem,m_PersonalizedController));
      
    new JoystickButton(m_PersonalizedController, m_PersonalizedController.goToHomeButton())// go to home (arm doesn't fall when turned off)
      .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, 12, 1, 15));
/*//test code to run to Theta R and Z at constant joint speeds
    // new JoystickButton(m_PersonalizedController, 1)
    //   .onTrue(m_ArmSubsystem.RunJointsToThetaRZCommand(-15, 12, 1));
    // new JoystickButton(m_PersonalizedController, 2)
    //   .onTrue(m_ArmSubsystem.RunJointsToThetaRZCommand(-15, 16, 6));
    // new JoystickButton(m_PersonalizedController, 3)
    //   .onTrue(m_ArmSubsystem.RunJointsToThetaRZCommand(-15, 28, 6));
    // new JoystickButton(m_PersonalizedController, 4)
    //   .onTrue(m_ArmSubsystem.RunJointsToThetaRZCommand(-15, 0, 80));

//Test code to Run to lines within a constant Theta.
    // new JoystickButton(m_PersonalizedController, 1)
    //   .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, 30, 4, 5));
    // new JoystickButton(m_PersonalizedController, 2)
    //   .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, 40, 10, 5));
    // new JoystickButton(m_PersonalizedController, 3)
    //   .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, 20, 10, 10));
    // new JoystickButton(m_PersonalizedController, 4)
    //   .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, 30, 20, 5) );//.withInterruptBehavior(Command.InterruptionBehavior.kCancelIncoming));
*/

    new JoystickButton(m_PersonalizedController, m_PersonalizedController.goToGroundPickupPosButton())
      .onTrue(new ArmFollowLineCommand(m_ArmSubsystem, ArmMoveConstants.GROUND_PICKUP_POSITION_RZ[0], ArmMoveConstants.GROUND_PICKUP_POSITION_RZ[1], 5));
    new JoystickButton(m_PersonalizedController, m_PersonalizedController.goToShelfPickupButton())
      .onTrue(MultiLine.MultiLineToShelf(m_ArmSubsystem));
    new JoystickButton(m_PersonalizedController, m_PersonalizedController.goToMedPosButton())
      .onTrue(MultiLine.MultiLineToMedium(m_ArmSubsystem));
    new JoystickButton(m_PersonalizedController, m_PersonalizedController.goToHighPosButton())
      .onTrue(MultiLine.MultiLineToHigh(m_ArmSubsystem));

    // new JoystickButton(m_PersonalizedController, m_PersonalizedController.openClawButton())
    //   .onTrue(m_ArmSubsystem.setClawPositionCommand(Constants.ArmMoveConstants.CLAW_OPEN_POSITION));
    new JoystickButton(m_PersonalizedController, m_PersonalizedController.openClawButton())
      .onTrue(MultiLine.MultiLineHHTest(m_ArmSubsystem));

    new JoystickButton(m_PersonalizedController, m_PersonalizedController.closeClawCubeButton())
      .onTrue(m_ArmSubsystem.setClawPositionCommand(Constants.ArmMoveConstants.CLAW_CUBE_POSITION));

    new JoystickButton(m_PersonalizedController, m_PersonalizedController.closeClawConeButton())
      .onTrue(m_ArmSubsystem.setClawPositionCommand(Constants.ArmMoveConstants.CLAW_CONE_POSITION));
    
  }
  
  private void setDefaultCommands(){
    
    m_ArmSubsystem.setDefaultCommand(
      m_ArmSubsystem.RunJointsToSetAnglesCommand()
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_ArmSubsystem.homeAll().until(m_ArmSubsystem::shoulderAndElbowHomedCondition);
  }
}
