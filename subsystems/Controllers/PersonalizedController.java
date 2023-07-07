package frc.robot.subsystems.Controllers;

public interface PersonalizedController {
    PersonalizedController getcontroller();
    int[] getbuttons();
    int enableFineControlButton();
    boolean povPressed();
    double getUpDown() ;
    double getLeftRight();
    double getInOut();

    
}
