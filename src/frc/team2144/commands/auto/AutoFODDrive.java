package frc.team2144.commands.auto;

import frc.team2144.CommandBase;
import frc.team2144.Constants;


public class AutoFODDrive extends CommandBase {
    private double travelAngle, turnAngle, nyoomSpeed, turnSpeed;
    private double linearDistance;
    private boolean turnComplete = false, distanceComplete = false;

    /**
     * This is a fun one - Field Oriented Drive.
     *
     * @param linearDistance - The linear distance (in encoder ticks) to drive.
     * @param travelAngle    - The angle (in degrees, relative to navX 0) to travel at. (+
     *                       right, - left)
     * @param turnAngle      - The angle (in degrees) to turn. (+ right, - left)
     * @param nyoomSpeed     - The speed [0..1.0] to drive at.
     * @param turnSpeed      - The speed [0..1.0] to turn at.
     */
    public AutoFODDrive(double linearDistance, double travelAngle, double turnAngle, double nyoomSpeed, double turnSpeed) {
        // Use requires() here to declare subsystem dependencies
        requires(gyro);
        requires(drivetrain);
        this.linearDistance = linearDistance;
        this.travelAngle = travelAngle;
        this.turnAngle = normalizeAngle(turnAngle);
        this.nyoomSpeed = nyoomSpeed;
        this.turnSpeed = Math.abs(turnSpeed);
    }

    private double normalizeAngle(double angle) {
        while (angle < -180) {
            angle += 360;
        }
        while (angle > 180) {
            angle -= 360;
        }
        return angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("FOD Starting");
        gyro.resetOrientation();
        drivetrain.reset_encoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double turnError, nyoomError, x, y;

        turnError = (turnAngle - normalizeAngle(gyro.getOrientation())) * 0.05;
        turnError = turnError > turnSpeed ? turnSpeed : turnError < -turnSpeed ? -turnSpeed : turnError;

        nyoomError = Math.sqrt(Math.abs(linearDistance - drivetrain.average_encoders()))
                * ((linearDistance - drivetrain.average_encoders()) < 0 ? -0.05 : 0.05);
        // System.out.println("DEBUG - nyoomError = " + nyoomError);
        // System.out.println("DEBUG - Aenc = " +
        // drivetrain.average_encoders());
//        nyoomError = nyoomError > nyoomSpeed ? nyoomSpeed : nyoomError < -nyoomSpeed ? -nyoomSpeed : nyoomError;

        nyoomError = nyoomSpeed;

        turnComplete = normalizeAngle(gyro.getOrientation()) > turnAngle - Constants.K_TURN_TOLERANCE
                && normalizeAngle(gyro.getOrientation()) < turnAngle + Constants.K_TURN_TOLERANCE;

        if (drivetrain.have_encoders_reached((int) (linearDistance * Constants.K_DRIVE_PPIN))) {
            distanceComplete = true;
            x = 0;
            y = 0;
        } else {
            distanceComplete = false;
            x = nyoomError * Math.sin(Math.toRadians(travelAngle));
            y = nyoomError * Math.cos(Math.toRadians(travelAngle));
        }

        double yaw = gyro.getOrientation(); // godsdang it, hardware
        drivetrain.mecanumCartesian(-x, y, turnError, yaw); // what
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return turnComplete && distanceComplete;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("FOD Complete");
        drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
