package MyBotEclipse;

import java.awt.geom.Point2D;

import robocode.*;
import robocode.util.Utils;

/*
 * @author: Argha Sarkar
 * Student ID: 1221352
 * Module: CS255
 */
public class HandIn1 extends Robot {

	//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------

		//THIS IS THE ENEMY ENERGY
		private double enemy_energy = -1000;							
		//HOW MUCH DAMAGE THE BULLET CAN DO
		private double firepower = 3;	
		//HOW MANY TIMES THE ROBOT HAS MOVED TO DODGE A BULLET
		private int dodge_count = 0;
		//HOW MANY SHOTS MISSED IN A ROW
		private int missed_shots_row = 0;
		//HOW MANY SHOTS MISSED IN TOTAL
		private int missed_shots_total = 0;
		//TARGETING METHOD - LINEAR / STATIONARY
		private String targeting_method = "linear";

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

		private double findTargetStationary2(double enemy_bearing, double my_heading, double my_gun_heading) {
			double angle = my_heading - my_gun_heading;
			angle += enemy_bearing;
			//RETURNS THE ANGLE TE GUN SHOULD BE TURNED AS A DOUBLE
			return angle;
		}

		private double findLinearTarget3(double bearing_rads, double distance, double enemy_heading, double enemy_velocity) {
			/*
			 * THIS IS A LINEAR TARGETTING SYSTEM. 
			 * IT PREDICTS WHERE A STRAIGHT MOVING ROBOT WILL BE IN THE FUTURE. IT THEN AIMS AND SHOOTS TAKING CARE OF THE FUTURE
			 * SO THAT THE BULLET HITS THE ROBOT EVEN WHEN IT IS FAR AWAY
			 * 
			 * I HAVE USED ONLINE SOURCE TO HELP ME WITH THIS PIECE OF CODE
			 */

			double current_X = this.getX();										//MY X COORDINATE
			double current_Y = this.getY();										//MY Y COORDINATE

			//GETS THE ABSOLUTE BEARING BY ADDING MY HEADING (ABSOLUTE) TO THE ENEMY BEARING (RELATIVE TO MY HEADING)
			double absolute_bearing =  Math.toRadians(this.getHeading()) + bearing_rads;		

			//WORKS OUT THE ENEMY'S POSITION BY USING SOME TRIG AND MY POSITION
			double enemy_X = current_X + distance * Math.sin(absolute_bearing);
			double enemy_Y = current_Y + distance * Math.cos(absolute_bearing);

			//THIS WILL BE USED LATER TO WORK OUT THE PREDICTED POSITION OF THE ENEMY LATER
			double delta_time = 0;
			double predicted_enemy_X = enemy_X;
			double predicted_enemy_Y = enemy_Y;

			while ( ((++delta_time) * getBulletVelocity()) < Point2D.Double.distance(current_X, current_Y, predicted_enemy_X, predicted_enemy_Y) ) {

				predicted_enemy_X += Math.sin(enemy_heading) * enemy_velocity;
				predicted_enemy_Y += Math.cos(enemy_heading) * enemy_velocity;

			}

			/*
			 * THIS BIT CHECKS IF THE BULLET WILL GO OFF THE MAP. 
			 * IF IT IS PREDICTED TO GO OFF THE MAP, THEN IT RETURNS AN ARBITARY VALUE OF -1000 
			 * THE RETURNED VALUE IS CHECKED TO SEE IF IT -1000. IF ITS NOT, THEN THE SHOT IS FIRED
			 */
			if ((predicted_enemy_X > getBattleFieldWidth() || predicted_enemy_Y > getBattleFieldHeight()) || 
					(predicted_enemy_X < 0 || predicted_enemy_Y < 0))
			{
				return -1000;
			}

			//WORKS OUT THE ANGLE
			double angle = Utils.normalAbsoluteAngle(Math.atan2(predicted_enemy_X - current_X, predicted_enemy_Y - current_Y));
			angle = Utils.normalRelativeAngle(angle - Math.toRadians(this.getGunHeading()));
			angle = Math.toDegrees(angle);

			//RETURNS THE ANGLE WHICH HAS BEEN WORKED OUT
			return angle;
		}

		private double getBulletVelocity() {
			return 20 - (3 * firepower);
		}

		private double setFirepowerByDistance(double distance) {
			//SETS THE FIREPOWER DEPENDING ON HOW FAR THE ENEMY IS
			if (distance < 400) { return 3.0; }
			if (distance < 600) { return 2.5; }
			if (distance < 800) { return 2.0; }
			if (distance < 1000) { return 1.5; }
			if (distance < 1200) { return 1.0; }
			return 0.75;
		}

		private double setFirepowerReduction() {
			//REDUCES THE POWER ACCORDING TO ARBITARY VALUE DEPENDING ON HOW MANY SHOTS HAVE BEEN MISSED IN A ROW
			if (missed_shots_row >= 10) { return 3.0; } 
			if (missed_shots_row >= 7) { return 2.0; }
			if (missed_shots_row >= 5) { return 1.5; }
			if (missed_shots_row >= 2) { return 1.0; }
			return 0.0;
		}

		private double setFirepower(double distance) {
			//SET THE FIREPOWER DEPENDING ON DISTANCE AND SHOTS MISSED
			double local_firepower = 0;							//FIREPOWER VARIBLE BEFORE BEING RETURNED
			local_firepower = setFirepowerByDistance(distance);
			local_firepower -= setFirepowerReduction();
			return local_firepower;
		}

		
		public void dodgeBullet(double energy) {
			//MOVES THE ROBOT RANDOMLY IN A DIRECTION TO DODGE BULLETS
			if (enemy_energy == -1000) {
				//SETS UP THE ENERGY FOR THE FIRST TIME USE
				enemy_energy = energy;
			}
			System.out.println("Enemy energy: " + enemy_energy + "    Energy: " + energy);
			if (enemy_energy != energy) {
				enemy_energy = energy;
				if (((enemy_energy - energy) <= 3.0) || ((enemy_energy - energy) >= 0.1)) {
					//turnRight(90);
					if (dodge_count % 10 == 0) {
						ahead(this.getHeight() * 2);
					} else {
						back(this.getHeight() * 2);
					}
				}
			}
		}

		//-------------------------------------------------USEFUL FUNCTIONS--------------------------------------------------------	


	public void run() {
		/*
		 * THIS IS THE MAIN METHOD WHICH IS EXECUTED
		 */
		
        while (true) {
        	turnRadarRight(360);
        }
    }
   
	public void onBulletHit(BulletHitEvent e) {
		/*
		 * This event is sent to onBulletHit when one of your bullets has hit another robot.
		 */
		//RESETS THE MISSED SHOTS IN A ROW COUNTER
		missed_shots_row = 0;
	}

	public void onBulletMissed(BulletMissedEvent e) {
		/*
		 * This event is sent to onBulletMissed when one of your bullets has missed.
		 *  i.e. when the bullet has reached the border of the battlefield.
		 */
		//INCREMENTS THE BULLET HIT COUNTER. THIS WILL BE USED TO REDUCE FIREPOWER WHEN TOO MANY
		//BULLEDS HAVE FAILED TO HIT THE TARGET
		missed_shots_row++;
		missed_shots_total++;

		//USED TO SWITCH THE TARGETING METHOD: LINEAR / STATIONARY
		if (missed_shots_row > 3) {
			if (targeting_method.equals("linear")) {
				targeting_method = "stationary";
			} 
		}
	}

    public void onScannedRobot(ScannedRobotEvent e) {
    	/*
    	 * This method is called when your robot sees another robot, i.e. when the robot's radar scan "hits" another robot.
    	 */
  
    	
    	//THIS IS THE BIT FOR THE RADAR LOCK
    	double radar_turn = Math.toRadians(this.getHeading()) + e.getBearingRadians() - Math.toRadians(this.getRadarHeading());
    	turnRadarRight(Math.toDegrees( 1.2 * (Utils.normalRelativeAngle(radar_turn))));   	
    	
    	if (dodge_count == 0) {
    		turnRight(e.getBearing() + 90);
    	}
    	if (dodge_count % 5 == 0) {
    		turnRight(e.getBearing() + 90);
    		dodgeBullet(e.getEnergy());
    	} else {
    		//SETS THE FIREPOWER ACCORDING TO THE DISTANCE OF THE ENEMY ROBOT
        	firepower = setFirepower(e.getDistance());
        	
        	//GETS THE GUN TURN ANGLE
        	double angle = 0;
        	if (e.getName().equals("sample.MyFirstRobot") == true || targeting_method.equals("stationary") ) {
        		this.setAdjustGunForRobotTurn(false);
        		this.setAdjustRadarForGunTurn(false);
        		this.setAdjustRadarForRobotTurn(false);
        		System.out.println("Stationary");
        		angle = findTargetStationary2(e.getBearing(), this.getHeading(), this.getGunHeading());
        	} else {
        		angle = findLinearTarget3(e.getBearingRadians(), e.getDistance(), e.getHeadingRadians(), e.getVelocity());
        	}
        	
        	//WILL ONLY FIRE THE GUN IF THE BULLET WILL NOT GO OFF THE BATTLEFIELD
        	if (angle != -1000) {
        		turnGunRight(angle);
        		fire(firepower);
        	} 
    	}
    	dodge_count++;
    	System.out.println("Targeting method: " + targeting_method);
    	//System.out.println("Scan called:" + scannedCalled++);
    }
    
}