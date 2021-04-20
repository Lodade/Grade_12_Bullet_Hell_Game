import java.awt.*;

public class EnemyShot// class which is the enemy shot object
{
    private boolean moving = false;
    private int curX = 0;
    private int curY = 0;

    // EnemyShot constructor
    EnemyShot() {
    }

    // Method which draws the enemy shot
    public void drawShot(Graphics g, int shotX, int shotY) {
        g.setColor(Color.ORANGE);
        g.fillRect(shotX, shotY, 20, 20);
        g.setColor(Color.RED);
        g.fillRect(shotX + 5, shotY + 5, 10, 10);
    }

    // Method which sets moving to true
    public void setMove() {
        this.moving = true;
    }

    // Method which sets the targetX
    public int setTargetX(int charX) {
        curX = charX;
        return curX;
    }

    // Method which the targetY
    public int setTargetY(int charY) {
        curY = charY;
        return curY;
    }

    // Method which checks if it is moving
    boolean checkMove() {
        if (moving == false) {
            return false;
        } else
            return true;
    }

    // Method which checks if the shot arrived at its target
    boolean checkArrive(int shotX, int shotY, int curX, int curY) {
        // Runs if the shot's X and Y is at it's target X and Y
        if (shotX == curX && shotY == curY) {
            return true;
        } else
            return false;
    }

    // Method which checks if the shot hits the player
    boolean checkSpot(int charX, int charY, int shotX, int shotY) {
        boolean contact = false;
        // Runs if the shot is in the player hit box
        if (shotX > charX - 20 && shotX < charX + 50 && shotY > charY - 20 && shotY < charY + 50) {
            contact = true;
        }
        return contact;
    }

    // Method which moves the shotX
    int moveShotX(int shotX, int curX) {
        if (shotX > curX) {
            shotX --;
        }
        if (shotX < curX) {
            shotX ++;
        }
        return shotX;
    }

    // Method which moves the shotY
    int moveShotY(int shotY, int curY) {
        if (shotY > curY) {
            shotY --;
        }
        if (shotY < curY) {
            shotY ++;
        }
        return shotY;
    }
}
