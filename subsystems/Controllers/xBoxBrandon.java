package frc.robot.subsystems.Controllers;

import edu.wpi.first.wpilibj.XboxController;
// kLeftX(0),
    // kRightX(4),
    // kLeftY(1),
    // kRightY(5),
    // kLeftTrigger(2),
    // kRightTrigger(3);
    // kLeftBumper(5),
    // kRightBumper(6),
    // kLeftStick(9),
    // kRightStick(10),
    // kA(1),
    // kB(2),
    // kX(3),
    // kY(4),
    // kBack(7),
    // kStart(8);



public class xBoxBrandon extends XboxController implements PersonalizedController {
    int[] buttonMappings = {
        XboxController.Button.kLeftBumper.value,//0 enable fine control //5 left bumper july 5th
        XboxController.Button.kY.value,//4 arm to high score position
        XboxController.Button.kY.value,//4 arm to medium score position
        2};
    private int m_lastPovValue=-1;

    public xBoxBrandon(int port) {
        super(port);
        //TODO Auto-generated constructor stub
    }

    @Override
    public PersonalizedController getcontroller() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getbuttons() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int enableFineControl() {
        return buttonMappings[0];
    }

    @Override
    public boolean povPressed() {
        int l_pov = getPOV();
        if(l_pov!=-1 && l_pov!=m_lastPovValue){
            m_lastPovValue=l_pov;
            return true;
        }else {
            m_lastPovValue=l_pov;
            return false;
        }
    }
    public int getPOVValue() {
        return getPOV();
        
    }
        
    


    
}
