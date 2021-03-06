package frc.team2144.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team2144.RobotMap;
import frc.team2144.commands.IntakeDrive;

public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private DoubleSolenoid solenoid;
    private Spark intake_r, intake_l;

    public Intake() {
        solenoid = new DoubleSolenoid(RobotMap.intake_up_sol, RobotMap.intake_down_sol);
        intake_r = new Spark(RobotMap.intake_motor_port_a);
        intake_l = new Spark(RobotMap.intake_motor_port_b);
    }

    /**
     * @param whether_to_be_up whether the intake should be up.
     */
    public void setUp(boolean whether_to_be_up) {
        if (whether_to_be_up) {
            solenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }

    /**
     * @param spd The speed to run the intake motors at. Negative intakes.
     */
    public void runMotors(double spd) {
        intake_r.set(spd);
        intake_l.set(-spd);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new IntakeDrive());
    }
}

