package MyBotEclipse;
import robocode.*;


/**
 * DwRotater - a robot by (developerWorks)
 */
public class DwRotater extends Robot
{
	static double midX;
	static double midY;
	static double leadAngle[] = {3,6, 10 };
	static int idx = 0;
	/**
	 * run: DwRotater's default behavior
	 */
	public void run() {
		midX = getBattleFieldWidth()/2;
		midY =getBattleFieldHeight()/2;
		
		double myX = getX();
		double myY = getY();
		
		turnLeft(getHeading());
		if( myY < midY)
		   ahead(midY - myY);
		else {
			turnLeft(180);
			ahead(myY - midY);
			turnLeft(180);
		}
        turnRight(90);		

		if( myX < midX)
		   ahead(midX - myX);
		else {
			turnLeft(180);
			ahead(myX - midX);
		}
		
		while(true) {
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		turnGunRight(leadAngle[idx]);
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		ahead(100);
		back(100);
    
	}
	// adjust angle if missed
	public void onBulletMiss(BulletMissedEvent e) {
	   idx = idx++ % leadAngle.length;
    }
	}
