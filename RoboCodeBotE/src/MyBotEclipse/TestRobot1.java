package MyBotEclipse;

import robocode.*;

/*
 * @author: Argha Sarkar
 * Student ID: 1221352
 * Module: CS255
 */
public class TestRobot1 extends Robot{

	final int move_distance = 100;
	
	public void run() {
		/*
		 * THIS IS THE MAIN METHOD WHICH IS EXECUTED
		 */
		int angle = 0;
        while (true) {
        	this.turnRadarRight(360);
        }
    }
    
	public void onBattleEnded(BattleEndedEvent e) {
		/*
		 * A BattleEndedEvent is sent to onBattleEnded() when the battle is ended.
		 */
	}
	
	public void onBulletHitBullet(BulletHitBulletEvent e) {
		/*
		 * This event is sent to onBulletHitBullet when one of your bullets has hit another bullet.
		 */
		ahead(move_distance);
	}
	
	public void onBulletHit(BulletHitEvent e) {
		/*
		 * This event is sent to onBulletHit when one of your bullets has hit another robot.
		 */
		fire(2);
	}
	
	public void onBulletMissed(BulletMissedEvent e) {
		/*
		 * This event is sent to onBulletMissed when one of your bullets has missed.
		 *  i.e. when the bullet has reached the border of the battlefield.
		 */
	}

	public void onDeath(DeathEvent e) {
		/*
		 * This event is sent to onDeath() when your robot dies.
		 */
	}
	
	public void onHitByBullet(HitByBulletEvent  e) {
		/*
		 * A HitByBulletEvent is sent to onHitByBullet() when your robot has been hit by a bullet.
		 */
		ahead(move_distance);
	}
	
	public void onHitRobot(HitRobotEvent e) {
		/*
		 * A HitRobotEvent is sent to onHitRobot() when your robot collides with another robot.
		 */
		back(move_distance / 2);
		ahead(move_distance);

	}
	
	public void onPaint(PaintEvent e) {
		/*
		 * This event occurs when your robot should paint, where the onPaint() is called on your robot.
		 */
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
		/*
		 * This event is sent to onRobotDeath() when another robot (not your robot) dies.
		 */
	}
		
	public void onHitWall(HitWallEvent e) {
    	/*
    	 * A HitWallEvent is sent to onHitWall() when you collide a wall.
    	 */
		turnRight(90);
		back(move_distance * 3);
    }
	
	public void onRoundEnded(RoundEndedEvent e) {
		/*
		 * A RoundEndedEvent is sent to onRoundEnded() when a round has ended.
		 */
	}
	
    public void onScannedRobot(ScannedRobotEvent e) {
    	/*
    	 * This method is called when your robot sees another robot, i.e. when the robot's radar scan "hits" another robot.
    	 */
    	turnGunRight(getHeading() - getGunHeading());
    	turnGunRight(e.getBearing());
    	fire(2);
    	turnRight(e.getBearing());
    	ahead(e.getDistance());
    	turnRight(90);
    }
    
    public void onSkippedTurn(SkippedTurnEvent e) {
    	/*
    	 * A SkippedTurnEvent is sent to onSkippedTurn() when your robot is forced to skipping a turn.
    	 */
    }
    
    public void onStatus(StatusEvent e) {
    	/*
    	 * This event is sent to onStatus() every turn in a battle to provide the status of the robot.
    	 */
    }
    
    public void onWin(WinEvent e) {
    	/*
    	 * This event is sent to onWin() when your robot wins the round in a battle
    	 */
    	int angle = 0;
    	while (angle <= 360) {
    		turnGunRight(90);
    		fire(5);
        	turnLeft(90);
        	fire(5);
        	angle += 90;
    	}
    	
    	
    }
    
}
