package MyBotEclipse;

import robocode.*;

/*
 * @author: Argha Sarkar
 * Student ID: 1221352
 * Module: CS255
 */
public class MyCornerBot extends Robot{
	
	//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------
	
	private double getNegativeHeight() {
		//GETS THE DISTANCE TO THE TOP WALL
		double negHeight = this.getBattleFieldHeight() - this.getHeight();
		return negHeight;
	}
	
	private double getNegativeWidth() {
		//GETS THE DISTANCE TO THE RIGHT WALL
		double negWidth = this.getBattleFieldWidth() - this.getWidth();
		return negWidth;
	}
	
	private void pointToNorth() {
		//POINTS THE FRONT OF THE ROBOT TOWARDS NORTH
		double bearing = this.getHeading();
		turnLeft(bearing);
	}
	
	private void pointToEast() {
		//POINTS THE FRONT OF THE ROBOT TOWARDS EAST
		double bearing = this.getHeading();
		turnLeft(bearing - 90);
	}
	
	private void pointToSouth() {
		//POINTS THE FRONT OF THE ROBOT TOWARDS SOUTH
		double bearing = this.getHeading();
		turnLeft(bearing - 180);
	}
	
	private void pointToWest() {
		//POINTS THE FRONT OF THE ROBOT TOWARDS WEST
		double bearing = this.getHeading();
		turnLeft(bearing - 270);
	}
	
	private void moveToNearestCorner() {
		if ( 1 > 1) {
			
		}
	}
	

	//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------
	
	public void run() {
		/*
		 * THIS IS THE MAIN METHOD WHICH IS EXECUTED
		 */
        while (true) {
        	
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
	}
	
	public void onBulletHit(BulletHitEvent e) {
		/*
		 * This event is sent to onBulletHit when one of your bullets has hit another robot.
		 */
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
	}
	
	public void onHitRobot(HitRobotEvent e) {
		/*
		 * A HitRobotEvent is sent to onHitRobot() when your robot collides with another robot.
		 */
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
    }
    
}
