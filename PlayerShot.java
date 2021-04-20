import java.awt.*;

class PlayerShot// Class which is the player shot object
{
    private static int shotX = 0;
    private static int shotY = 0;
    private static int mouseX = 0;
    private static int mouseY = 0;
    private boolean moving = false;

    PlayerShot()// PlayerShot constructor
    {
    }

    void drawShot(Graphics g, int charX, int charY)// Method which draws the shot
    {
        if (moving == false) {
            shotX = charX;
            shotY = charY;
        }
        if (moving == true) {
            g.setColor(Color.WHITE);
            g.fillRect(shotX, shotY, 10, 10);
        }
    }

    int getX()// Method which gets the shotX
    {
        return shotX;
    }

    int getY()// Method which get the shotY
    {
        return shotY;
    }

    void setSpot(int charX, int charY)// Method which sets the shotX and shotY
    {
        shotX = charX;
        shotY = charY;
    }

    void setMove(int curmouseX, int curmouseY)// Method which sets where to move
    {
        mouseX = curmouseX;
        mouseY = curmouseY;
        moving = true;
    }

    boolean checkMove()// Method which checks if it is moving
    {
        if (moving == false) {
            return false;
        } else
            return true;
    }

    boolean checkSpot(int charX, int charY)// Method which checks if it is at the character
    {
        if (charX == shotX && charY == shotY) {
            return true;
        } else
            return false;
    }

    void shotReset(int charX, int charY)// Method which resets the shot
    {
        shotX = charX;
        shotY = charY;
    }

    void moveShot(int curmouseX, int curmouseY)// Method which moves the shot
    {
        if (moving == false) {
            mouseX = curmouseX;
            mouseY = curmouseY;
        }
        if (shotX > mouseX) {
            shotX -= 1;
        }
        if (shotX < mouseX) {
            shotX += 1;
        }
        if (shotY > mouseY) {
            shotY -= 1;
        }
        if (shotY < mouseY) {
            shotY += 1;
        }
        if (shotX == mouseX && shotY == mouseY)// Runs if the shot reaches it's target
        {
            moving = false;
        }
    }
}