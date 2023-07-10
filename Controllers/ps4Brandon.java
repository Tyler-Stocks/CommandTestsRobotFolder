package frc.robot.Controllers;

import edu.wpi.first.wpilibj.PS4Controller;

public class ps4Brandon extends PS4Controller implements PersonalizedController {
    private int m_lastPovValue=-1;

    private enum Button {
        kSquare(1),
        kCross(2),
        kCircle(3),
        kTriangle(4),
        kL1(5),
        kR1(6),
        kL2(7),
        kR2(8),
        kShare(9),
        kOptions(10),
        kL3(11),
        kR3(12),
        kPS(13),
        kTouchpad(14);

        private final int value;
        Button(int index) {
            this.value = index;
        }
    }
    public enum Axis {
        kLeftX(0),
        kLeftY(1),
        kRightX(2),
        kRightY(5),
        kL2(3),
        kR2(4);
    
        public final int value;
    
        Axis(int index) {
          value = index;
        }
    }
    public ps4Brandon(int port) {
        super(port);
    }

    @Override
    public PersonalizedController getcontroller() {
        return this;
    }

    @Override
    public int enableFineControlButton() {
        return Button.kL1.value;
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

    @Override
    public double ArmUpDownAxis() {
        return -getRightY();
    }

    @Override
    public double ArmLeftRightAxis() {
        //System.out.println(getRightX());
        return getRightX();
    }

    @Override
    public double ArmInOutAxis() {
        return -getLeftY();
    }

    @Override
    public int getPOVValue() {
        return getPOV();
    }

    @Override
    public int goToHighPosButton() {
        return Button.kTriangle.value;
    }

    @Override
    public int goToMedPosButton() {
        return Button.kSquare.value;
    }

    @Override
    public int goToGroundPickupPosButton() {
        return Button.kCross.value;
    }

    @Override
    public int goToShelfPickupButton() {
        return Button.kCircle.value;
    }

    @Override
    public int goToHomeButton() {
        return Button.kTouchpad.value;
    }

    @Override
    public int shiftLowButton() {
        return Button.kR3.value;
    }

    @Override
    public int shiftHighButton() {
        return Button.kL3.value;
    }
    
}
