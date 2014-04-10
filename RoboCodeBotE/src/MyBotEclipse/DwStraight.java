package MyBotEclipse;
import robocode.*;


/**
 * DWStraight - a robot by (your name here)
 */
public class DwStraight extends Robot
{
	
	/**
	 * run: DWStraight's default behavior
	 */
	public void run() {
		turnLeft(getHeading());
		
		while(true) {
			ahead(1000);
			turnRight(90);
			
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(180);
	}}
