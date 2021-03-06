
package org.usfirst.frc.team1452.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    Compressor c = new Compressor(0);
    DoubleSolenoid d = new DoubleSolenoid(1,0);
    Solenoid s = new Solenoid(2);
    Joystick j = new Joystick(0);
    DigitalInput touchSensor = new DigitalInput(0);
    Timer t = new Timer();
    int state;
    
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        c.setClosedLoopControl(true);
        state = -1;
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if(state == -1){
        	SmartDashboard.putNumber("state ", state);
        	d.set(DoubleSolenoid.Value.kForward);//forward is open
        	s.set(false);//false is down
        	state++;
        }
        if(state == 0){//touch sensor is unpressed and claw is down and open
        	if(touchSensor.get()){
        		SmartDashboard.putNumber("state ", state);
        		System.out.println("YAYYYYYYYYYY");
        		d.set(DoubleSolenoid.Value.kReverse);
        		t.reset();
        		t.start();
        		state++;
        	}
        }
        if(state == 1){//touch sensor has been triggered, and claw is closed but down
        	if(t.get() >= 2){
        		SmartDashboard.putNumber("state ", state);
        		s.set(true);
        		state++;
        	}
        }
        if(state == 2){//claw is up and closed
        	if(j.getRawButton(1)){
        		SmartDashboard.putNumber("state ", state);
        		d.set(DoubleSolenoid.Value.kForward);
        		t.reset();
        		t.start();
        		state++;
        	}
        }
        if(state==3){//claw is up and open
        	if(t.get() >= 1){
        		SmartDashboard.putNumber("state ", state);
        		s.set(false);
        		state = 0;
        	}
        }
    
    /**
     * This function is called periodically during test mode
     */
    }
    public void testPeriodic() {
    
    }
    
}
