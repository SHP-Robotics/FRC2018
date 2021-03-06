/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2144;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    // joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    private Joystick left = new Joystick(0);
    private Joystick right = new Joystick(1);

    /**
     * @return the X position of the left joystick. Right = positive.
     */
    public double get_left_x() {
        return left.getX();
    }

    /**
     * @return the Y position of the left joystick. Forward = positive.
     */
    public double get_left_y() {
        return -left.getY();
    }

    /**
     * @return the X position of the right joystick. Right = positive.
     */
    public double get_right_x() {
        return right.getX();
    }

    /**
     * @return the Y position of the right joystick. Forward = positive.
     */
    public double get_right_y() {
        return -right.getY();
    }

    /**
     * @return the Z rotation of the right joystick. Right = positive.
     */
    public double get_right_z() {
        return right.getZ();
    }

    public boolean get_intake_pos() {
        return !right.getRawButton(ControlMap.intake_position);
    }

    public boolean get_intake() {
        return right.getRawButton(ControlMap.intake_intake);
    }

    public boolean get_output() {
        return left.getRawButton(ControlMap.intake_output);
    }

    public double get_intake_spd() {
        // return get_intake() ? (right.getThrottle() + 1) / 2 : get_output() ? Constants.intake_output_spd : 0;
        return get_intake() ? Constants.intake_intake_spd : get_output() ? Constants.intake_output_spd : 0;
    }

    public double get_guide_power() {
        return right.getRawButton(6) || right.getRawButton(3) ? 0.5 : right.getRawButton(5) ? -1 : 0;
    }

    public double get_climb_power() {
        return right.getRawButton(4) ? 1 : right.getRawButton(6) || right.getRawButton(7) ? -1 : 0;
    }
}
