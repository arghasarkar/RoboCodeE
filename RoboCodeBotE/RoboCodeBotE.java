package MyBotEclipse;

import robocode.*;

public class RoboCodeBotE extends Robot{

	public void run() {
        while (true) {
            ahead(50);
            turnGunRight(180);
            back(75);
            turnGunRight(80);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(2);
    }

}
