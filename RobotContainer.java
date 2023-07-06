// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ArmFineControlCommand;
//import frc.robot.Constants.OperatorConstants;
//import frc.robot.commands.Autos;
import frc.robot.commands.PrintCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmSubsystem;
import frc.robot.subsystems.Controllers.*;
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
  static final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  static final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  //private final ps4Brandon m_PersonalizedController =new ps4Brandon(0);
  static final xBoxBrandon m_PersonalizedController =new xBoxBrandon(0);
   // Replace with CommandPS4Controller or CommandJoystick if needed
  //private final CommandXboxController m_driverController =
      //new CommandXboxController(OperatorConstants.kDriverControllerPort);

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
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new PrintCommand("exampleCondition"));
    // new Trigger(m_PersonalizedController::povPressed)
    //     .onTrue(new PrintCommand("pov Pressed"));
    new Trigger(m_PersonalizedController::povPressed)
        .onTrue(m_ArmSubsystem.POVInputCommand(() -> (m_PersonalizedController.getPOV())));


    new JoystickButton(m_PersonalizedController, m_PersonalizedController.enableFineControl())
      .whileTrue(new ArmFineControlCommand(m_ArmSubsystem));
      //.whileTrue(new PrintCommand("enableFineControl"));
    new JoystickButton(m_PersonalizedController, 6)
      .onTrue(new PrintCommand("button 6"));
    

    
  }

  
  private void setDefaultCommands(){
    m_exampleSubsystem.setDefaultCommand(
      m_exampleSubsystem.exampleDefaultCommand()
    );
    m_ArmSubsystem.setDefaultCommand(
      //this.m_ArmSubsystem.RunJointsToAngles(0,90,15,3)
      m_ArmSubsystem.RunJointsToSetPoint()
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
    return m_ArmSubsystem.homeAll().until(m_ArmSubsystem::exampleCondition);
  }
}
