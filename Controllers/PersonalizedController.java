package frc.robot.Controllers;

public interface PersonalizedController {
    PersonalizedController getcontroller();
    int enableFineControlButton();
    int goToHighPosButton();
    int goToMedPosButton();
    int goToGroundPickupPosButton();
    int goToShelfPickupButton();
    int goToHomeButton();
    int shiftLowButton();
    int shiftHighButton();
    boolean povPressed();
    int getPOVValue();
    double ArmUpDownAxis() ;
    double ArmLeftRightAxis();
    double ArmInOutAxis();

    
}
