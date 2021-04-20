import java.awt.*;

class Enemy// Class which is the enemy object
{
    private int enemyMoveSpeed = 4;
    Enemy()// Enemy constructor
    {
    }

    int spotSetX()// Method which sets the enemyX
    {
        int enemyX = (int) (Math.random() * 800) + 200;
        return enemyX;
    }

    int spotSetY()// Method which sets the enemyY
    {
        int enemyY = (int) (Math.random() * 400) + 200;
        return enemyY;
    }

    void drawEnemy(Graphics g, int enemyX, int enemyY, int health)// Method which draws the enemy
    {
        g.setColor(Color.RED);
        g.fillRect(enemyX + 5, enemyY, 20, 25);
        g.setColor(Color.RED);
        g.fillRect(enemyX + 5, enemyY + 25, 6, 15);
        g.fillRect(enemyX + 19, enemyY + 25, 6, 15);
        g.setColor(Color.BLACK);
        g.fillRect(enemyX - 1, enemyY, 6, 20);
        g.fillRect(enemyX + 25, enemyY, 6, 20);
        g.setColor(Color.RED);
        g.fillRect(enemyX + 5, enemyY - 18, 20, 18);
        g.setColor(Color.YELLOW);
        g.fillRect(enemyX + 8, enemyY - 13, 4, 4);
        g.fillRect(enemyX + 18, enemyY - 13, 4, 4);
        g.setColor(Color.GRAY);
        g.fillRect(enemyX - 1, enemyY - 40, 33, 10);
        g.setColor(Color.GREEN);
        g.fillRect(enemyX - 1, enemyY - 40, (int) health / 3, 10);
    }

    int moveX(int spotX, int charX)// Method which moves the enemyX
    {
        if (spotX > charX + 40) {
            spotX -= enemyMoveSpeed;
        }
        if (spotX < charX - 40) {
            spotX += enemyMoveSpeed;
        }
        return spotX;
    }

    int moveY(int spotY, int charY)// Method which moves the enemyY
    {
        if (spotY > charY + 40) {
            spotY -= enemyMoveSpeed;
        }
        if (spotY < charY - 40) {
            spotY += enemyMoveSpeed;
        }
        return spotY;
    }
}
