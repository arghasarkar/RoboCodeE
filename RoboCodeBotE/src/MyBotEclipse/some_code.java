package MyBotEclipse;

import robocode.*;
import robocode.util.Utils;

public class some_code extends Robot{

	private void findLinearTarget(double my_heading, double enemy_bearing, double enemy_heading_radians, double enemy_velocity, double bullet_velocity) {
		@Deprecated
		double absoluteBearing = my_heading + enemy_bearing;
		double radian_turn = Utils.normalRelativeAngle(absoluteBearing - this.getGunHeading() + (enemy_velocity * Math.sin(enemy_heading_radians - 
			    absoluteBearing) / bullet_velocity)); 
		turnGunRight(Math.toDegrees(radian_turn));
	}
	
	private double findLinearTarget2(double distance, double bearing, double velocity_E, double heading_E) {
		@Deprecated		
		//MY ATTEMPTING AT IMPLEMENTING LINEAR TARGETTING
		double x1 = 0, y1 = 0;							//MY POSITION
		double x2 = 0, y2 = 0;							//ENEMY POSITION
		double x3 = 0, y3 = 0;							//MEETING POINT OF ENEMY AND BULLET
		
		double delta_x = 0, delta_y = 0;				//DISTANCE VECTORS BETWEEN ME AND THE ENEMY
		double delta_x1 = 0, delta_y1 = 0;				//DISTANCE VECTORS BETWEEN THE ENEMY AND THE BULLET MEETING POINT
		double delta_x2 = 0, delta_y2 = 0;				//DISTANCE VECTORS BETWEEN ME AND THE BULLET MEETING POINT
		
		/* distance	-- THE DISTANCE BETWEEN ME AND THE ENEMY */
		/* bearing -- THE ANGLE BEARING BETWEEN ME AND THE ENEMY */
				
		//POINTS ALL OF MY ROBOT TO NORTH
		pointToNorth();
		//pointRadarToNorth();
		//pointGunToNorth();
		
		//GETS MY POISITION
		x1 = this.getX();
		y1 = this.getY();

		delta_x = mySin(bearing) * distance;
		delta_y = myCos(bearing) * distance;
		
		x2 = x1 + delta_x;
		y2 = y1 + delta_y;
		
		delta_x1 = mySin(heading_E) * velocity_E;
		delta_y1 = myCos(heading_E) * velocity_E;
		
		x3 = x2 + delta_x1;
		y3 = y2 + delta_y1;
		
		delta_x2 = x3 - x1;
		delta_y2 = y3 - y1;
		
		System.out.println("Bullet velocity: " + getBulletVelocity() + " Delta x2" + delta_x2 + "Delta y2" + delta_y2);
		
		double angle = 0;
		angle = my_a_Sin((delta_x2 / getBulletVelocity()));
		System.out.println("Value of deltaX2 / Vb " + (delta_x2 / getBulletVelocity()));
		System.out.println("Angle found using sin: " + angle);
		angle = my_a_Cos((delta_y2 / getBulletVelocity()));
		System.out.println("Value of deltaY2 / Vb " + (delta_y2 / getBulletVelocity()));
		System.out.println("Angle found using cos: " + angle);
		
		return angle;
		//turnGunRight(angle);
	}
	public some_code() {
		// TODO Auto-generated constructor stub
	}

}
