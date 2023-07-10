package frc.robot.Controllers;

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
        XboxController.Button.kY.value,// arm to high score position
        XboxController.Button.kX.value,// arm to medium score position
        XboxController.Button.kA.value,// arm to ground pickup score position
        XboxController.Button.kB.value,// arm to shelf pickup position
        XboxController.Button.kRightBumper.value,// arm to home/drive position
        XboxController.Button.kLeftStick.value,// Shift High
        XboxController.Button.kRightStick.value// Shift Low
        };
    private int m_lastPovValue=-1;

    public xBoxBrandon(int port) {
        super(port);
    }

    @Override
    public PersonalizedController getcontroller() {
        return this;
    }

    @Override
    public int enableFineControlButton() {
        return buttonMappings[0];
    }
    @Override
    public int goToHighPosButton() {
        return buttonMappings[1];
    }
    @Override
    public int goToMedPosButton() {
        return buttonMappings[2];
    }
    @Override
    public int goToGroundPickupPosButton() {
        return buttonMappings[3];
    }
    @Override
    public int goToShelfPickupButton() {
        return buttonMappings[4];
    }
    
    @Override
    public int goToHomeButton() {
        return buttonMappings[5];
    } 
    
    @Override
    public int shiftLowButton() {
        return buttonMappings[6];
    }
    @Override
    public int shiftHighButton() {
        return buttonMappings[7];
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

    public double ArmUpDownAxis() {
        return -getRightY();
    }
    public double ArmLeftRightAxis() {
        //System.out.println(getRightX());
        return getRightX();
    }
    public double ArmInOutAxis() {
        return -getLeftY();
    }

}
