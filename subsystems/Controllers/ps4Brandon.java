package frc.robot.subsystems.Controllers;

import edu.wpi.first.wpilibj.PS4Controller;

public class ps4Brandon extends PS4Controller implements PersonalizedController {

    public ps4Brandon(int port) {
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
    public int enableFineControlButton() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public boolean povPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getUpDown() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getLeftRight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getInOut() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
