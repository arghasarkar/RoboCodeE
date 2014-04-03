package MyBotEclipse;

import java.awt.geom.Point2D;

import robocode.*;
import robocode.util.Utils;

/*
 * @author: Argha Sarkar
 * Student ID: 1221352
 * Module: CS255
 */
public class LinearTargetBot extends Robot {
	
	int scannedCalled = 0;							   //THIS IS USED FOR DEBUGGING OPTION
	
	//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------
	
		//HOW MUCH DAMAGE THE BULLET CAN DO
		double firepower = 3;				
		
		//DIRECTIONS
		final int NORTH = 0;
		final int EAST = 90;
		final int SOUTH = 180;
		final int WEST = 270;
		
		private double getNegativeHeight() {
			//GETS THE DISTANCE TO THE TOP WALL
			double negHeight = this.getBattleFieldHeight() - this.getY();
			return negHeight;
		}
		
		private double getNegativeWidth() {
			//GETS THE DISTANCE TO THE RIGHT WALL
			double negWidth = this.getBattleFieldWidth() - this.getX();
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
			turnLeft(bearing - EAST);
		}
		
		private void pointToSouth() {
			//POINTS THE FRONT OF THE ROBOT TOWARDS SOUTH
			double bearing = this.getHeading();
			turnLeft(bearing - SOUTH);
		}
		
		private void pointToWest() {
			//POINTS THE FRONT OF THE ROBOT TOWARDS WEST
			double bearing = this.getHeading();
			turnLeft(bearing - WEST);
		}
		
		private void moveToNearestCorner() {
			//MOVES THE ROBOT TO THE NEAREST CORNER
			pointToNorth();
			//ahead(this.getBattleFieldHeight());
			if (getNegativeHeight() >= this.getY()) {
				ahead(-1 * this.getY());
			} else {
				ahead(this.getY());
			}
			pointToEast();
			if (getNegativeWidth() >= this.getX()) {
				ahead(-1 * this.getX());
			} else {
				ahead(this.getX());
			}
		}
		
		private void findTargetStationary(double enemy_bearing) {
			//POINTS THE GUN AT THE ENEMY
			turnGunRight(this.getHeading() - this.getGunHeading());
			turnGunRight(enemy_bearing);
		}
		
	private double findLinearTarget3(double bearing_rads, double distance, double enemy_heading, double enemy_velocity) {
			/*
			 * THIS IS A LINEAR TARGETTING SYSTEM. 
			 * IT PREDICTS WHERE A STRAIGHT MOVING ROBOT WILL BE IN THE FUTURE. IT THEN AIMS AND SHOOTS TAKING CARE OF THE FUTURE
			 * SO THAT THE BULLET HITS THE ROBOT EVEN WHEN IT IS FAR AWAY
			 */
		
			double my_X = this.getX();										//MY X COORDINATE
			double my_Y = this.getY();										//MY Y COORDINATE
			
			//GETS THE ABSOLUTE BEARING BY ADDING MY HEADING (ABSOLUTE) TO THE ENEMY BEARING (RELATIVE TO MY HEADING)
			double absolute_bearing =  Math.toRadians(this.getHeading()) + bearing_rads;		
			
			//WORKS OUT THE ENEMY'S POSITION BY USING SOME TRIG AND MY POSITION
			double enemy_X = my_X + distance * Math.sin(absolute_bearing);
			double enemy_Y = my_Y + distance * Math.cos(absolute_bearing);
			
			//THIS WILL BE USED LATER TO WORK OUT THE PREDICTED POSITION OF THE ENEMY LATER
			double delta_time = 0;
			double predicted_X = enemy_X;
			double predicted_Y = enemy_Y;
			
			while ( ((++delta_time) * getBulletVelocity()) < Point2D.Double.distance(my_X, my_Y, predicted_X, predicted_Y) ) {
				
				predicted_X += Math.sin(enemy_heading) * enemy_velocity;
				predicted_Y += Math.cos(enemy_heading) * enemy_velocity;
				
			}
			
			/*
			 * THIS BIT CHECKS IF THE BULLET WILL GO OFF THE MAP. 
			 * IF IT IS PREDICTED TO GO OFF THE MAP, THEN IT RETURNS AN ARBITARY VALUE OF -1000 
			 * THE RETURNED VALUE IS CHECKED TO SEE IF IT -1000. IF ITS NOT, THEN THE SHOT IS FIRED
			 */
			if ((predicted_X > getBattleFieldWidth() || predicted_Y > getBattleFieldHeight()) || 
					(predicted_X < 0 || predicted_Y < 0))
			{
				return -1000;
			}
			
			//WORKS OUT THE ANGLE
			double angle = Utils.normalAbsoluteAngle(Math.atan2(predicted_X - my_X, predicted_Y - my_Y));
			angle = Utils.normalRelativeAngle(angle - Math.toRadians(this.getGunHeading()));
			angle = Math.toDegrees(angle);
			
			//RETURNS THE ANGLE WHICH HAS BEEN WORKED OUT
			return angle;
		}

		private double getBulletVelocity() {
			return 20 - (3 * firepower);
		}
			
		private int getCorner(double x, double y) {
			/*
			 * RETURNS THE CORNER NUMBER. STARTING AT 0 AND GOING ANTI-CLOCKWISE FROM BOTTOM RIGHT
			 * 			    1 --------- 2
			 *              |           |
			 * 				0 --------- 3
			 */
			
			if (x <= (double) (this.getBattleFieldWidth() / 2)) {
				if (y <= (double) (this.getBattleFieldHeight() / 2)) {
					return 0;
				} else {
					return 1;
				}
			} else {
				if (y <= (double) (this.getBattleFieldHeight() / 2)) {
					return 3;
				} else {
					return 2;
				}
			}
		}
				
		private void moveToCorner(int corner) {
			/*
			 *  MOVES THE ROBOT TO A CORNER DEFINED BY THE INPUT. STARTING AT 0 AND GOING ANTI-CLOCKWISE FROM BOTTOM RIGHT
			 * 			    1 --------- 2
			 *              |           |
			 * 				0 --------- 3
			 */
			
			//THE X AND THE Y CO-ORDINATE OF THE REQUIRED CORNER
			double required_x = 0;
			double required_y = 0;
			
			switch (corner) {
				case 0: {
					required_x = 0;
					required_y = 0;
				} break;
				case 1: {
					required_x = 0;
					required_y = this.getBattleFieldHeight();					
				} break;
				case 2: {
					required_x = this.getBattleFieldWidth();
					required_y = this.getBattleFieldWidth();
				} break;
				case 3: {
					required_x = this.getBattleFieldWidth();
					required_y = 0;
				} break;
			}
			
			pointToNorth();
			ahead(required_y - this.getY());
			pointToEast();
			ahead(required_x - this.getX());
			
		}
			
		//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------	


	public void run() {
		/*
		 * THIS IS THE MAIN METHOD WHICH IS EXECUTED
		 */
		//pointToNorth();
		this.setAdjustRadarForGunTurn(true);
        while (true) {
        	turnRadarRight(360);
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
    	/*double angle = findLinearTarget2(e.getDistance(), e.getBearing(), e.getVelocity(), e.getHeading());
    	turnGunRight(angle);
    	fire(firepower);
    	System.out.println("HIyaarr");*/
    	double angle = findLinearTarget3(e.getBearingRadians(), e.getDistance(), e.getHeadingRadians(), e.getVelocity());
    	
    	System.out.println("Angle: " + angle);
    	if (angle != -1000) {
    		turnGunRight(angle);
    		fire(firepower);
    	}
    	
    	System.out.println("Scan called:" + scannedCalled++);
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
