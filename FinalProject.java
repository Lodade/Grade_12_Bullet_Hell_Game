
/*Final Project
 * Date: June 13,2016
 * By: Jacob Wagner
 * This program is meant to be a 2d player shooting game where you go through levels and at the
 * end you get to a boss where if you beat him you win the game. The controls consist of WASD to move the player
 * and mouse click where you want the character to shoot their shot.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FinalProject// The main class
{
  public static JFrame mainpage;// Creates the jframe
  public static GameScreen game;// Creates the GameScreen

  public static void main(String[] args)// Sets up the base jframe and game jpanel
  {
    mainpage = new JFrame("Game");
    mainpage.setSize(1200, 858);
    mainpage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    game = new GameScreen();
    mainpage.add(game);
    mainpage.setVisible(true);
  }
}

class healing implements ActionListener// Class which controls the healing of the player
{
  public void actionPerformed(ActionEvent e)// actionPerformed method
  {
    GameScreen.heal();
  }
}

class GameScreen extends JPanel implements MouseListener, KeyListener, ActionListener// Class which houses the Jpanel
                                                                                     // and Listeners
{
  // int declarations
  public static int charX = 578;
  public static int charY = 395;
  public static int mouseX = 0;
  public static int mouseY = 0;
  // boolean declarations
  public static boolean shoot = false;
  public static boolean right = false;
  public static boolean left = false;
  public static boolean up = false;
  public static boolean down = false;
  public static boolean frontmenu = true;
  public static boolean maingame = false;
  public static boolean gameover = false;
  public static boolean playershoot = false;
  public static boolean fighting = false;
  public static boolean spawn = true;
  public static boolean bossroom = false;
  public static boolean win = false;
  public static boolean instructions = false;
  // int declarations
  public static int roomscleared = 0;
  public static boolean first = true;
  public static int charhealth = 300;
  public static int dmgtoplayer = 20;
  public static int go = 0;
  public static int score = 0;
  public static int enemyShotSpeed = 5;
  // object and int arrays declarations
  public static PlayerShot shots[] = new PlayerShot[8];
  public static Enemy enemies[] = new Enemy[8];
  public static int[] enemyhealth = new int[8];
  public static int[] enemyX = new int[8];
  public static int[] enemyY = new int[8];
  public static int[] enemyshotsX = new int[8];
  public static int[] enemyshotsY = new int[8];
  public static int[] enemyshotscurX = new int[8];
  public static int[] enemyshotscurY = new int[8];
  public static EnemyShot eshots[] = new EnemyShot[8];
  // JLabel declarations
  JLabel title = new JLabel("Adventure Realm");
  JLabel Over = new JLabel("Game Over");
  JLabel MainMenu = new JLabel("Main Menu");
  JLabel start = new JLabel("Start");
  JLabel instruct = new JLabel("Instructions");
  JLabel Line1 = new JLabel("To move the character, use the WASD keys.");
  JLabel Line2 = new JLabel("To shoot shots, click the mouse.");
  JLabel Line3 = new JLabel("To aim the shots, move the mouse.");
  JLabel Line4 = new JLabel("Beat the enemies in each room to move to the next one.");
  JLabel scores = new JLabel();
  JLabel showX = new JLabel();
  JLabel showY = new JLabel();
  JLabel mouseX2 = new JLabel();
  JLabel mouseY2 = new JLabel();
  // Timer declarations
  Timer unifiedTimer = new Timer(10, this);
  healing heals = new healing();
  Timer healhim = new Timer(200, heals);
  static BossController bossgo;

  public void actionPerformed(ActionEvent z)// actionPerformed method
  {
    for (int b = 0; b < 8; b++) {
      if (playershoot == true && shots[b].checkMove() == false) {
        shots[b].setMove(mouseX, mouseY);
      }
      if (shots[b].checkMove() == true) {
        shots[b].moveShot(mouseX, mouseY);
      }
    }
    if (bossgo != null) {
      bossgo.moveBoss();
      bossgo.moveBossShots();
    }
    GameScreen.hitdetect();
    GameScreen.bossdetect();
    GameScreen.enemyMoves();
    GameScreen.shotMoves();
    fighting = fightCheck();
    charMove();
    roommove();
    repaint();
  }

  public GameScreen()// GameScreen constructor
  {
    addMouseListener(this);
    addKeyListener(this);
    setFocusable(true);
    for (int p = 0; p < 8; p++) {
      enemies[p] = new Enemy();
      eshots[p] = new EnemyShot();
    }
    for (int i = 0; i < 8; i++) {
      shots[i] = new PlayerShot();
    }
    unifiedTimer.start();
    healhim.start();
  }

  static boolean fightCheck()// Method which check's if there is fighting
  {
    for (int l = 0; l < 8; l++) {
      if (enemyhealth[l] > 0) {
        return true;
      }
    }
    if (bossroom == true) {
      return true;
    }
    return false;
  }

  public static void enemyMoves()// Method which moves the enemies
  {
    for (int i = 0; i < 8; i++) {
      if (enemyX[i] != 0 && enemyY[i] != 0) {
        enemyX[i] = enemies[i].moveX(enemyX[i], charX);
        enemyY[i] = enemies[i].moveY(enemyY[i], charY);
      }
    }
  }

  public static void shotMoves()// Method which moves the enemy shots
  {
    for (int b = 0; b < 8; b++) {
      if (eshots[b].checkMove() == true && spawn == false)// Checks if the enemy shot b is moving
      {
        for (int i = 0; i < enemyShotSpeed; i++) {
          enemyshotsX[b] = eshots[b].moveShotX(enemyshotsX[b], enemyshotscurX[b]);
          enemyshotsY[b] = eshots[b].moveShotY(enemyshotsY[b], enemyshotscurY[b]);
          if (eshots[b].checkArrive(enemyshotsX[b], enemyshotsY[b], enemyshotscurX[b], enemyshotscurY[b]) == true
              && spawn == false)// Checks if the enemy shot arrived at it's target
          {
            enemyshotsX[b] = enemyX[b];
            enemyshotsY[b] = enemyY[b];
            enemyshotscurX[b] = eshots[b].setTargetX(charX);
            enemyshotscurY[b] = eshots[b].setTargetY(charY);
            break;
          }
        }
      }
    }
  }

  public static void heal()// Method which heals the player
  {
    if (charhealth < 300 && charhealth != 0) {
      charhealth++;
    }
  }

  public static void bossdetect()// Method which does the bossfight hit detection
  {
    if (bossroom == true) {
      if (first == true) {
        bossgo = new BossController(2000, 600, 350, charX, charY, enemyShotSpeed);
        charhealth = 300;
        first = false;
      }
      bossgo.bossHitDetection();
      if (bossgo.getBossHealth() <= 0 && gameover != true) {
        win = true;
        score = 0;
        roomscleared = 0;
        maingame = false;
      }
    }
  }

  public static void hitdetect()// Method which does the normal fights hit detection
  {
    for (int b = 0; b < 8; b++) {
      boolean charhit = false;
      charhit = eshots[b].checkSpot(charX, charY, enemyshotsX[b], enemyshotsY[b]);
      if (charhit == true && charhealth >= dmgtoplayer && enemyhealth[b] > 0)// Runs if the player is hit and the enemy
                                                                             // is alive
      {
        charhealth -= dmgtoplayer;
        charhit = false;
        shots[b].shotReset(charX, charY);
      }
      if (charhit == true && charhealth < dmgtoplayer && enemyhealth[b] > 0)// Runs if the player is hit, the enemy is
                                                                            // alive and the player has less health than
                                                                            // the shot's damage
      {
        charhealth = 0;
        charhit = false;
        shots[b].shotReset(charX, charY);
      }
      for (int u = 0; u < 8; u++) {
        int tempX = shots[u].getX();
        int tempY = shots[u].getY();
        if (tempX < enemyX[b] + 40 && tempX > enemyX[b] - 10 && tempY > enemyY[b] - 10 && tempY < enemyY[b] + 40
            && enemyhealth[b] > 0)// Runs if the shot is within the enemy hitbox
        {
          enemyhealth[b] -= 4;
          score++;
        }
      }
    }
  }

  public void paintComponent(Graphics g)// paintComponent method
  {
    super.paintComponent(g);
    if (frontmenu == true)// Draws the front menu screen
    {
      remove(Over);
      remove(MainMenu);
      remove(Line1);
      remove(Line2);
      remove(Line3);
      remove(Line4);
      charhealth = 300;
      charX = 578;
      charY = 395;
      for (int y = 0; y < 8; y++)// Sets the spot of the enemy shots
      {
        enemyX[y] = -100;
        enemyY[y] = -100;
        enemyshotsX[y] = -100;
        enemyshotsY[y] = -100;
        enemyshotscurX[y] = -100;
        enemyshotscurY[y] = -100;
      }
      win = false;
      spawn = true;
      bossroom = false;
      first = true;
      Color maroon = new Color(144, 18, 18);
      setBackground(maroon);
      drawBackground(1200, 800, 0, 0, g, go);
      g.setColor(Color.WHITE);
      g.fillRect(550, 375, 100, 50);
      g.fillRect(525, 500, 150, 50);
      add(title);
      add(start);
      add(instruct);
      title.setVisible(true);
      title.setForeground(Color.WHITE);
      title.setBounds(470, 100, 250, 150);
      start.setVisible(true);
      start.setForeground(Color.BLACK);
      start.setBounds(576, 376, 100, 50);
      instruct.setVisible(true);
      instruct.setForeground(Color.BLACK);
      instruct.setBounds(543, 500, 150, 50);
      start.setFont(new Font("Arial", Font.BOLD, 20));
      title.setFont(new Font("Arial", Font.BOLD, 30));
      instruct.setFont(new Font("Arial", Font.BOLD, 20));
    }
    if (instructions == true) {
      remove(title);
      remove(start);
      remove(instruct);
      add(MainMenu);
      add(instruct);
      add(Line1);
      add(Line2);
      add(Line3);
      add(Line4);
      g.setColor(Color.WHITE);
      g.fillRect(50, 700, 120, 50);
      instruct.setVisible(true);
      instruct.setForeground(Color.WHITE);
      instruct.setBounds(543, 170, 200, 50);
      Font cur = new Font("Arial", Font.BOLD, 20);
      instruct.setFont(new Font("Arial", Font.BOLD, 30));
      MainMenu.setVisible(true);
      MainMenu.setForeground(Color.BLACK);
      MainMenu.setBounds(58, 700, 130, 50);
      MainMenu.setFont(cur);
      Line1.setVisible(true);
      Line1.setForeground(Color.WHITE);
      Line1.setBounds(415, 250, 450, 50);
      Line1.setFont(cur);
      Line2.setVisible(true);
      Line2.setForeground(Color.WHITE);
      Line2.setBounds(470, 320, 450, 50);
      Line2.setFont(cur);
      Line3.setVisible(true);
      Line3.setForeground(Color.WHITE);
      Line3.setBounds(463, 390, 450, 50);
      Line3.setFont(cur);
      Line4.setVisible(true);
      Line4.setForeground(Color.WHITE);
      Line4.setBounds(358, 460, 560, 50);
      Line4.setFont(cur);
    }
    if (maingame == true)// Draws the main game screen
    {
      remove(title);
      remove(start);
      remove(instruct);
      Color maroon = new Color(144, 18, 18);
      setBackground(maroon);
      drawBackground(1200, 800, 0, 0, g, go);
      if (charhealth > 0)// Runs if the player is alive
      {
        drawPlayer(g);
      }
      if (charhealth == 0)// Runs if the player dies
      {
        maingame = false;
        score = 0;
        gameover = true;
        roomscleared = 0;
      }
      add(scores);
      showX.setVisible(true);
      showY.setVisible(true);
      mouseX2.setVisible(true);
      mouseY2.setVisible(true);
      scores.setVisible(true);
      showX.setForeground(Color.WHITE);
      showY.setForeground(Color.WHITE);
      mouseX2.setForeground(Color.WHITE);
      mouseY2.setForeground(Color.WHITE);
      scores.setForeground(Color.WHITE);
      showX.setBounds(100, 100, 100, 50);
      showY.setBounds(100, 200, 100, 50);
      mouseX2.setBounds(100, 300, 100, 50);
      mouseY2.setBounds(100, 400, 100, 50);
      scores.setBounds(500, 700, 200, 50);
      showX.setText("charX " + charX);
      showY.setText("charY " + charY);
      mouseX2.setText("mouseX " + mouseX);
      mouseY2.setText("mouseY " + mouseY);
      scores.setFont(new Font("Arial", Font.BOLD, 20));
      scores.setText("Score " + score);
      for (int f = 0; f < 8; f++) {
        if (shots[f].checkSpot(charX, charY) == false && fighting == true && charhealth > 0)// Runs if the player is
                                                                                            // alive and the player shot
                                                                                            // is not on the player
        {
          shots[f].drawShot(g, charX, charY);
        }
        if (enemyX[f] != 0 && enemyY[f] != 0 && enemyhealth[f] > 0 && charhealth > 0)// Runs if the enemy is not in the
                                                                                     // top left corner and the player
                                                                                     // is alive
        {
          enemies[f].drawEnemy(g, enemyX[f], enemyY[f], enemyhealth[f]);
        }
        if (eshots[f].checkMove() == true && enemyhealth[f] > 0 && charhealth > 0)// Runs if the enemy shot is moving
                                                                                  // and the enemy is not dead
        {
          eshots[f].drawShot(g, enemyshotsX[f], enemyshotsY[f]);
        }
      }
      if (bossroom == true)// Draws the boss room screen
      {
        if (bossgo != null && bossgo.getBossShots() != null) {
          bossgo.timeToDrawBossCheck(g, charhealth);
        }
      }
    }
    if (win == true)// Draws the win screen
    {
      remove(scores);
      add(Over);
      add(MainMenu);
      dmgtoplayer = 20;
      fighting = false;
      for (int i = 0; i < 8; i++) {
        enemyhealth[i] = 0;
      }
      Over.setVisible(true);
      MainMenu.setVisible(true);
      Over.setFont(new Font("Arial", Font.BOLD, 40));
      MainMenu.setFont(new Font("Arial", Font.BOLD, 20));
      Over.setText("You win!");
      Over.setForeground(Color.WHITE);
      MainMenu.setForeground(Color.BLACK);
      Over.setBounds(520, 200, 1000, 150);
      MainMenu.setBounds(560, 350, 1000, 150);
      g.setColor(Color.WHITE);
      g.fillRect(537, 400, 150, 50);
    }
    if (gameover == true)// Draws the game over screen
    {
      remove(scores);
      add(Over);
      add(MainMenu);
      dmgtoplayer = 20;
      fighting = false;
      for (int i = 0; i < 8; i++) {
        enemyhealth[i] = 0;
      }
      Over.setVisible(true);
      MainMenu.setVisible(true);
      Over.setFont(new Font("Arial", Font.BOLD, 40));
      MainMenu.setFont(new Font("Arial", Font.BOLD, 20));
      Over.setForeground(Color.WHITE);
      MainMenu.setForeground(Color.BLACK);
      Over.setBounds(500, 200, 1000, 150);
      MainMenu.setBounds(560, 350, 1000, 150);
      g.setColor(Color.WHITE);
      g.fillRect(537, 400, 150, 50);
    }
  }

  public int drawBackground(int pageW, int pageH, int curX, int curY, Graphics g, int run)// Method which draws the
                                                                                          // background recursively
  {
    if (curX == pageW && curY == pageH)// Returns run if the current X and Y reachs the page W and H
    {
      return run;
    }
    g.setColor(Color.BLACK);
    g.fillRect(curX, 0, 20, 800);
    g.fillRect(0, curY, 1200, 20);
    if (curX < pageW) {
      curX += 100;
    }
    if (curY < pageH) {
      curY += 100;
    }
    run = drawBackground(pageW, pageH, curX, curY, g, run);
    return run;
  }

  public void roommove()// Method which moves the player from room to room
  {
    if (fighting == false && charX < 5)// Runs if fighting is not happening and the character is to the left of the
                                       // screen
    {
      charX = 1170;
      roomscleared += 1;
      dmgtoplayer += 2;
      spawn = false;
      enemySetter();
      charhealth = 300;
    }
    if (fighting == false && charX > 1170)// Runs if fighting is not happening and the character is to the right of the
                                          // screen
    {
      charX = 5;
      roomscleared += 1;
      dmgtoplayer += 2;
      spawn = false;
      enemySetter();
      charhealth = 300;
    }
    if (fighting == false && charY < 5)// Runs if fighting is not happening and the character is to the top of the
                                       // screen
    {
      charY = 730;
      roomscleared += 1;
      dmgtoplayer += 2;
      spawn = false;
      enemySetter();
      charhealth = 300;
    }
    if (fighting == false && charY > 731)// Runs if fighting is not happening and the character is to the bottom of the
                                         // screen
    {
      charY = 4;
      roomscleared += 1;
      dmgtoplayer += 2;
      spawn = false;
      enemySetter();
      charhealth = 300;
    }
  }

  public void charMove()// Method which moves the character
  {
    if (right == true && charX < 1173) {
      charX += 5;
    }
    if (left == true && charX > 4) {
      charX -= 5;
    }
    if (up == true && charY > 4) {
      charY -= 5;
    }
    if (down == true && charY < 732) {
      charY += 5;
    }
  }

  void enemySetter()// Method which sets up the enemies
  {
    if (roomscleared == 2)// Runs if rooms cleared is eight
    {
      bossroom = true;
    }
    for (int k = 0; k < 8; k++) {
      if (bossroom == false)// Sets up the normal ones
      {
        enemyhealth[k] = 100;
        enemyX[k] = enemies[k].spotSetX();
        enemyY[k] = enemies[k].spotSetY();
        enemyshotsX[k] = enemyX[k];
        enemyshotsY[k] = enemyY[k];
        eshots[k].setMove();
        enemyshotscurX[k] = eshots[k].setTargetX(charX);
        enemyshotscurY[k] = eshots[k].setTargetY(charY);
      }
    }
  }

  void drawPlayer(Graphics g)// Method which draws the player
  {
    Color silverish = new Color(230, 231, 238);
    Color blueish = new Color(22, 70, 229);
    Color redish = new Color(252, 10, 10);
    g.setColor(blueish);
    g.fillRect(charX + 5, charY, 20, 25);
    g.setColor(silverish);
    g.fillRect(charX + 10, charY + 4, 10, 12);
    g.setColor(Color.BLACK);
    g.fillRect(charX + 5, charY + 25, 6, 15);
    g.fillRect(charX + 19, charY + 25, 6, 15);
    g.setColor(silverish);
    g.fillRect(charX - 1, charY, 6, 20);
    g.fillRect(charX + 25, charY, 6, 20);
    g.setColor(Color.BLACK);
    g.fillRect(charX + 5, charY - 18, 20, 18);
    g.setColor(redish);
    g.setColor(Color.WHITE);
    g.fillOval(charX + 10, charY - 13, 10, 4);
    g.setColor(Color.GRAY);
    g.fillRect(860, 710, 300, 30);
    g.setColor(Color.GREEN);
    g.fillRect(860, 710, charhealth, 30);
  }

  public void mouseExited(MouseEvent e)// Method which triggers when mouse exited
  {
  }

  public void mouseEntered(MouseEvent e)// Method which triggers when mouse entered
  {
  }

  public void mouseReleased(MouseEvent e)// Method which triggers when mouse released
  {
    if (maingame == true) {
      playershoot = false;
    }
  }

  public void mousePressed(MouseEvent e)// Method which triggers when mouse pressed
  {
    mouseX = e.getX();
    mouseY = e.getY();
    if (maingame == true) {
      playershoot = true;
    }
  }

  public void mouseClicked(MouseEvent e)// Method which triggers when mouse clicked
  {
    mouseX = e.getX();
    mouseY = e.getY();
    if (frontmenu == true && mouseX > 550 && mouseX < 650 && mouseY > 375 && mouseY < 425)// Runs if the player clicks
                                                                                          // on the game start button
    {
      frontmenu = false;
      maingame = true;
    }
    if (frontmenu == true && mouseX > 525 && mouseX < 675 && mouseY > 500 && mouseY < 550) {
      frontmenu = false;
      instructions = true;
    }
    if (instructions == true && mouseX > 50 && mouseX < 170 && mouseY > 700 && mouseY < 750) {
      frontmenu = true;
      instructions = false;
    }
    if (gameover == true && mouseX > 537 && mouseX < 687 && mouseY > 400 && mouseY < 450)// Runs if the player clicks on
                                                                                         // the return to main menu
                                                                                         // button
    {
      gameover = false;
      frontmenu = true;
    }
    if (win == true && mouseX > 537 && mouseX < 687 && mouseY > 400 && mouseY < 450)// Runs if the player clicks on the
                                                                                    // return to main menu button
    {
      win = false;
      frontmenu = true;
    }
  }

  public void keyTyped(KeyEvent e)// Method which triggers when key typed
  {

  }

  public void keyPressed(KeyEvent e)// Method which triggers when key pressed
  {
    if (e.getKeyCode() == KeyEvent.VK_D) {
      right = true;
    } else if (e.getKeyCode() == KeyEvent.VK_A) {
      left = true;
    } else if (e.getKeyCode() == KeyEvent.VK_W) {
      up = true;
    } else if (e.getKeyCode() == KeyEvent.VK_S) {
      down = true;
    }
  }

  public void keyReleased(KeyEvent e)// Method which triggers when key released
  {
    if (e.getKeyCode() == KeyEvent.VK_D) {
      right = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_A) {
      left = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_W) {
      up = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      down = false;
    }
  }
}
