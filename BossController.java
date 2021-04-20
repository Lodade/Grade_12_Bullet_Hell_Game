import java.awt.*;

public class BossController// Class which controls the boss shots and moving
{
    private EnemyShot bossShots[];
    private int bossshotsX[] = new int[5];
    private int bossshotsY[] = new int[5];
    private int bossshotscurX[] = new int[5];
    private int bossshotscurY[] = new int[5];
    private int bossX;
    private int bossY;
    private int bossHealth = 2000;
    private int moveSpeed = 3;
    private int enemyShotSpeed;

    public BossController(int bossHealth, int bossX, int bossY, int charX, int charY, int enemyShotSpeed) {
        this.bossHealth = bossHealth;
        this.bossX = bossX;
        this.bossY = bossY;
        this.enemyShotSpeed = enemyShotSpeed;
        initializeShots();
        initializeShotLocations(charX, charY);
    }

    private void initializeShots() {
        this.bossShots = new EnemyShot[5];
        for (int i = 0; i < bossShots.length; i++) {
            bossShots[i] = new EnemyShot();
        }
    }

    public void initializeShotLocations(int charX, int charY) {
        for (int i = 0; i < bossShots.length; i++) {
            bossshotsX[i] = bossX;
            bossshotsY[i] = bossY;
            bossShots[i].setMove();
        }
        bossshotscurX[0] = charX;
        bossshotscurY[0] = charY;
        bossshotscurX[1] = 0;
        bossshotscurY[1] = bossY;
        bossshotscurX[2] = bossX;
        bossshotscurY[2] = 0;
        bossshotscurX[3] = 1200;
        bossshotscurY[3] = bossY;
        bossshotscurX[4] = bossX;
        bossshotscurY[4] = 800;
    }

    public int getBossHealth() {
        return bossHealth;
    }

    public EnemyShot[] getBossShots() {
        return bossShots;
    }

    public void moveBoss()// Method which moves the boss
    {
        if (GameScreen.bossroom == true && GameScreen.charhealth > 0 && bossHealth > 0) {
            if (bossX < GameScreen.charX) {
                bossX += moveSpeed;
            }
            if (bossX > GameScreen.charX) {
                bossX -= moveSpeed;
            }
            if (bossY < GameScreen.charY) {
                bossY += moveSpeed;
            }
            if (bossY > GameScreen.charY) {
                bossY -= moveSpeed;
            }
        }
    }

    public void bossHitDetection() {
        boolean charhit = false;
        for (int i = 0; i < 5; i++) {
            charhit = bossShots[i].checkSpot(GameScreen.charX, GameScreen.charY, bossshotsX[i], bossshotsY[i]);
            if (charhit == true && GameScreen.charhealth >= 50 && bossHealth > 0)// runs if everyone is alive and the
                                                                                 // player is hit
            {
                GameScreen.charhealth -= 50;
                charhit = false;
            }
            if (charhit == true && GameScreen.charhealth < 50 && bossHealth > 0)// runs if everyone is alive and the
                                                                                // player is hit with
            // less than 50 health
            {
                GameScreen.charhealth = 0;
                charhit = false;
            }
        }
        for (int p = 0; p < 8; p++) {
            int tempX = GameScreen.shots[p].getX();
            int tempY = GameScreen.shots[p].getY();
            // Runs if the player shot is within the hitbox of the boss
            if (tempX < bossX + 60 && tempX > bossX - 10 && tempY > bossY - 10 && tempY < bossY + 90
                    && bossHealth > 0) {
                bossHealth -= 4;
                GameScreen.score++;
            }
        }
    }

    public void moveBossShots()// Method which moves the boss shots
    {
        if (bossHealth > 0) {
            // Checks if the first boss bullet is moving
            if (bossShots[0].checkMove() == true && GameScreen.bossroom == true) {
                for (int i = 0; i < enemyShotSpeed; i++) {
                    bossshotsX[0] = bossShots[0].moveShotX(bossshotsX[0], bossshotscurX[0]);
                    bossshotsY[0] = bossShots[0].moveShotY(bossshotsY[0], bossshotscurY[0]);
                    // Checks if the boss shot has arrived at its target
                    if (bossShots[0].checkArrive(bossshotsX[0], bossshotsY[0], bossshotscurX[0],
                            bossshotscurY[0]) == true) {
                        bossshotsX[0] = bossX;
                        bossshotsY[0] = bossY;
                        bossshotscurX[0] = GameScreen.charX;
                        bossshotscurY[0] = GameScreen.charY;
                        break;
                    }
                }
            }
            // Checks if the second boss bullet is moving
            if (bossShots[1].checkMove() == true && GameScreen.bossroom == true) {
                for (int i = 0; i < enemyShotSpeed; i++) {
                    bossshotsX[1] = bossShots[1].moveShotX(bossshotsX[1], bossshotscurX[1]);
                    bossshotsY[1] = bossShots[1].moveShotY(bossshotsY[1], bossshotscurY[1]);
                    // Checks if the boss bullet has arrived at its target
                    if (bossShots[1].checkArrive(bossshotsX[1], bossshotsY[1], bossshotscurX[1],
                            bossshotscurY[1]) == true) {
                        bossshotsX[1] = bossX;
                        bossshotsY[1] = bossY;
                        bossshotscurX[1] = 0;
                        bossshotscurY[1] = bossY;
                        break;
                    }
                }
            }
            // Checks if the third boss bullet is moving
            if (bossShots[2].checkMove() == true && GameScreen.bossroom == true) {
                for (int i = 0; i < enemyShotSpeed; i++) {
                    bossshotsX[2] = bossShots[2].moveShotX(bossshotsX[2], bossshotscurX[2]);
                    bossshotsY[2] = bossShots[2].moveShotY(bossshotsY[2], bossshotscurY[2]);
                    // Checks if the boss bullet has arrived at its target
                    if (bossShots[2].checkArrive(bossshotsX[2], bossshotsY[2], bossshotscurX[2],
                            bossshotscurY[2]) == true) {
                        bossshotsX[2] = bossX;
                        bossshotsY[2] = bossY;
                        bossshotscurX[2] = bossX;
                        bossshotscurY[2] = 0;
                        break;
                    }
                }
            }
            // Checks if the fourth boss bullet is moving
            if (bossShots[3].checkMove() == true && GameScreen.bossroom == true) {
                for (int i = 0; i < enemyShotSpeed; i++) {
                    bossshotsX[3] = bossShots[3].moveShotX(bossshotsX[3], bossshotscurX[3]);
                    bossshotsY[3] = bossShots[3].moveShotY(bossshotsY[3], bossshotscurY[3]);
                    // Checks if the boss bullet has arrived at its target
                    if (bossShots[3].checkArrive(bossshotsX[3], bossshotsY[3], bossshotscurX[3],
                            bossshotscurY[3]) == true) {
                        bossshotsX[3] = bossX;
                        bossshotsY[3] = bossY;
                        bossshotscurX[3] = 1200;
                        bossshotscurY[3] = bossY;
                        break;
                    }
                }
            }
            // Checks if the fifth boss bullet is moving
            if (bossShots[4].checkMove() == true && GameScreen.bossroom == true) {
                for (int i = 0; i < enemyShotSpeed; i++) {
                    bossshotsX[4] = bossShots[4].moveShotX(bossshotsX[4], bossshotscurX[4]);
                    bossshotsY[4] = bossShots[4].moveShotY(bossshotsY[4], bossshotscurY[4]);
                    // Checks if the boss bullet has arrived at its target
                    if (bossShots[4].checkArrive(bossshotsX[4], bossshotsY[4], bossshotscurX[4],
                            bossshotscurY[4]) == true) {
                        bossshotsX[4] = bossX;
                        bossshotsY[4] = bossY;
                        bossshotscurX[4] = bossX;
                        bossshotscurY[4] = 800;
                        break;
                    }
                }
            }
        }
    }

    public void drawBoss(Graphics g)// Method which draws the boss
    {
        g.setColor(Color.BLACK);
        g.fillRect(bossX, bossY, 50, 80);
        g.setColor(Color.RED);
        g.fillRect(bossX, bossY - 40, 50, 40);
        g.fillRect(bossX - 25, bossY, 25, 50);
        g.fillRect(bossX + 50, bossY, 25, 50);
        g.fillRect(bossX, bossY + 80, 20, 40);
        g.fillRect(bossX + 30, bossY + 80, 20, 40);
        g.setColor(Color.WHITE);
        g.fillRect(bossX + 10, bossY - 35, 10, 10);
        g.fillRect(bossX + 30, bossY - 35, 10, 10);
        g.setColor(Color.GRAY);
        g.fillRect(270, 50, (int) 2000 / 3, 40);
        g.setColor(Color.GREEN);
        g.fillRect(270, 50, (int) bossHealth / 3, 40);
    }

    public void timeToDrawBossCheck(Graphics g, int charHealth) {
        if (getBossHealth() > 0 && charHealth > 0)// Runs if the player and boss is alive
        {
            drawBoss(g);
        }
        for (int i = 0; i < bossShots.length; i++) {
            if (getBossHealth() > 0)// Runs if the boss is alive
            {
                bossShots[i].drawShot(g, bossshotsX[i], bossshotsY[i]);
            }
        }
    }
}
