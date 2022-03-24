package game.Tower_Defence_Game.level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class levelBoard extends JPanel {
    private final int CELL_SIZE = 60;
    private final int MOVING_CELL = -1;
    private final int EMPTY_CELL = 0;
    private final int ROCK_CELL = 2;
    private final int ENEMY_CELL = 1;
    private final int MAIN_BASE_CELL = 5;
    private final int N_ROWS = 25;
    private final int N_COLS = 13;
    private final int FPS = 60;
    private final double PERIOD = 1000 / FPS;
    private final int BOARD_WIDTH = N_ROWS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_COLS * CELL_SIZE + 10;
    private boolean inGame;
    private boolean doRepaint = true;
    private JLabel statusbar;
    public int level;
    private boolean started;
    public int[][] Field = new int[N_COLS][N_ROWS];
    public enemy [][] enemies = new enemy [N_COLS][ N_ROWS ];
    public healthbar[][] healthbars = new healthbar[N_COLS][N_ROWS];
    private final int startMoney = 200;
    private int money = startMoney;
    private int number = 1;
    private int CURRENT_WAVE = 1;
    private double MILLISECONDS_PASSED = 0;
    private final int AMOUNT_OF_WAVES = 7;
    private ArrayList<HitEffect> hitEffects = new ArrayList<HitEffect>();

    public int MAX_HEALTH = 500;
    public int CURRENT_HEALTH = MAX_HEALTH;
    public levelBoard(JLabel statusbar, int level) {
        this.statusbar = statusbar;
        this.level = level;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH , BOARD_HEIGHT));
        setBackground(Color.white);
        addMouseListener(new MinesAdapter());
        javax.swing.Timer timer = new Timer((int) PERIOD, new GameCycle());
        timer.start();
        newGame();
    }
    private void newGame() {
        MAX_HEALTH = 500;
        CURRENT_HEALTH = MAX_HEALTH;
        MILLISECONDS_PASSED = 0;
        for (int j = 0; j < N_COLS; j++) {
            for (int i = 0; i < N_ROWS; i++) {
                enemies[j][i] = null;
                healthbars[j][i]= null;
            }
        }
        money = startMoney;
        var random = new Random();
        inGame = true;
        statusbar.setText("Wave " + CURRENT_WAVE + " / " + AMOUNT_OF_WAVES + "            Money: " +money + "$                  health: " + CURRENT_HEALTH + " / " + MAX_HEALTH  );
        setLevelLayOut(level);
        int i = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        boolean isFirstBase = true;
        enemy current_enemy;
        ImageIcon rock = new ImageIcon("src/recources/level_elements/Rock.png");
        ImageIcon empty = new ImageIcon("src/recources/level_elements/empty.png");
        ImageIcon base = new ImageIcon("src/recources/level_elements/main_base.png");
        if (inGame) {
            for (int j = 0; j < N_COLS; j++) {
                for (int i = 0; i < N_ROWS; i++) {
                    if (healthbars[j][i] != null) {
                        g2d.drawImage(healthbars[j][i].getImage(), healthbars[j][i].getX(), healthbars[j][i].getY(), this);
                    }
                    if (Field[j][i] == EMPTY_CELL) {
//                   g2d.drawImage(empty.getImage(), i * CELL_SIZE,
//                            j * CELL_SIZE, this);

                    } else if (Field[j][i] == ROCK_CELL) {

                        g2d.drawImage(rock.getImage(), i * CELL_SIZE,
                                j * CELL_SIZE, this);
                    } else if (Field[j][i] == MAIN_BASE_CELL && isFirstBase) {
                        g2d.drawImage(base.getImage(), i * CELL_SIZE,
                                j * CELL_SIZE, this);
                        isFirstBase = false;
                    }
                    if (enemies[j][i] != null) {
                        current_enemy = enemies[j][i];
                        g2d.drawImage(current_enemy.getImage(),
                                current_enemy.getX(),
                                current_enemy.getY(),
                                this);
                    }
                }
            }
            for (int i = 0; i < hitEffects.size(); i++) {
                g2d.drawImage(hitEffects.get(i).getImage(), hitEffects.get(i).getX(),
                        hitEffects.get(i).getY(), this);
                hitEffects.get(i).updateAge();
                if (hitEffects.get(i).getAge() >= 10) {
                    hitEffects.remove(i);
                }
            }
        }
    }
    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;
            if (enemies [cRow][cCol] != null){
                enemies[cRow][cCol].damage(1);
                healthbars[cRow][cCol].updateHealthbar(1);
                hitEffects.add(new HitEffect(x, y, 1));
                if (enemies[cRow][cCol].getHealth() <= 0){
                    money += enemies[cRow][cCol].getValue();
                    enemies[cRow][cCol] = null;
                    healthbars[cRow][cCol] = null;
                    Field[cRow][cCol] = 0;
                }
            }
            if (inGame){
                started = true;
            }
            if (!inGame) {
                newGame();
                repaint();
            }
            if (doRepaint) {
                        repaint();
                    }
                }
            }




        private void setLevelLayOut(int level) {
            if (level == 1) {

                Field = new int[][]{{2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,0,0},
                                    {2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,2,2,2,2,2,2,2,2,0,0},
                                    {2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,2,2,2,2,2,2,2,0,0,0},
                                    {0,0,0,0,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5},
                                    {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,5,5,5},
                                    {2,2,2,0,0,0,0,0,0,0,2,2,0,0,0,2,2,0,0,0,0,0,0,0,0},
                                    {0,2,2,2,2,0,0,0,0,2,2,0,0,0,0,2,2,2,0,0,0,0,0,0,0},
                                    {0,0,0,2,2,2,2,2,2,2,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0}};

            }
        }
    /**
     * Finds the shortest path from cat to mouse in the given labyrinth.
     *
     * @param lab the labyrinth's matrix with walls indicated by {@code true}
     * @param cx the cat's X coordinate
     * @param cy the cat's Y coordinate

     * @return the direction of the shortest path
     */
    private Direction findShortestPathToTarget
    (int[][] lab, int cx, int cy, int target) {
        // Create a queue for all nodes we will process in breadth-first order.
        // Each node is a data structure containing the cat's position and the
        // initial direction it took to reach this point.
        Queue<Node> queue = new ArrayDeque<>();

        // Matrix for "discovered" fields
        // (I know we're wasting a few bytes here as the cat and mouse can never
        // reach the outer border, but it will make the code easier to read. Another
        // solution would be to not store the outer border at all - neither here nor
        // in the labyrinth. But then we'd need additional checks in the code
        // whether the outer border is reached.)
        boolean[][] discovered = new boolean[lab.length][lab[0].length];
        // "Discover" and enqueue the cat's start position
        discovered[cy][cx] = true;
        queue.add(new Node(cx, cy, null));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            // Go breath-first into each direction
            for (Direction dir : Direction.values()) {
                if ((node.x != 0 || dir.getDx() >= 0)&& (node.x != lab[0].length -1 || dir.getDx() <= 0) && (node.y != 0 || dir.getDy() >= 0)&& (node.y != lab.length-1 || dir.getDy() <= 0)) {
                    int newX = node.x + dir.getDx();
                    int newY = node.y + dir.getDy();
                    Direction newDir = node.initialDir == null ? dir : node.initialDir;

                    // Mouse found?
                    for (int j = 0; j < N_COLS; j++) {
                        for (int i = 0; i < N_ROWS; i++) {
                            if (Field[j][i] >= target) {
                                if (newX == i && newY == j) {
                                    return newDir;
                                }
                            }
                        }
                    }


                    // Is there a path in the direction (= is it a free field in the labyrinth)?
                    // And has that field not yet been discovered?
                    if ((lab[newY][newX] != ROCK_CELL) && (lab[newY][newX] != ENEMY_CELL) &&  !discovered[newY][newX]) {
                        // "Discover" and enqueue that field
                        discovered[newY][newX] = true;
                        queue.add(new Node(newX, newY, newDir));
                    }
                }
            }
        }

       return Direction.NOMOVE;
    }

    private static class Node {
        final int x;
        final int y;
        final Direction initialDir;

        public Node(int x, int y, Direction initialDir) {
            this.x = x;
            this.y = y;
            this.initialDir = initialDir;
        }
    }

    public enum Direction {
        RIGHT(1, 0),
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        NOMOVE(0,0);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }
    }
    private void doGameCycle() {

        if (inGame) {
            updateWave(level);
            cleanBoard();
            fightOrFlight();
            updateStatusbar();
            if (CURRENT_HEALTH <= 0){
                gameover();
            }
        }
        if (doRepaint) {
            repaint();
        }

    }

    private void cleanBoard() {
        for (int j = 0; j < N_COLS; j++) {
            for (int i = 0; i < N_ROWS; i++) {
                if (Field[j][i] == MOVING_CELL) {
                    boolean hasEnemy = false;
                    for (Direction dir : Direction.values()) {
                        if     (j + dir.getDy() >= 0 &&
                                j + dir.getDy() < N_COLS &&
                                i + dir.getDx() >= 0 &&
                                i + dir.getDx() < N_ROWS){
                        if (enemies [j + dir.getDy()][i + dir.getDx()] != null){
                            hasEnemy = true;
                        }
                    }
                    }
                    if (!hasEnemy){
                        Field[j][i] = EMPTY_CELL;
                    }
                }
            }
            }
    }

    private void gameover() {
        statusbar.setText("Game over. You have lost. Click to restart.                                                      tip: dont let enemies get to the main base");
        inGame = false;
    }

    private void updateStatusbar() {
        statusbar.setText("Wave " + CURRENT_WAVE + " / " + AMOUNT_OF_WAVES + "            Money: " +money + "$                  health: " + CURRENT_HEALTH + " / " + MAX_HEALTH  );
    }

    private void fightOrFlight() {
         for (int j = 0; j < N_COLS; j++) {
                    for (int i = 0; i < N_ROWS; i++) {
                         if (enemies [j][i] != null) {
                             enemy current_enemy = enemies[j][i];
                             current_enemy.setAttacking(false);

                            for (Direction dir : Direction.values()) {
                                if     (j + dir.getDy()*current_enemy.getRange() >= 0 &&
                                        j + dir.getDy()*current_enemy.getRange() < N_COLS &&
                                        i + dir.getDx()*current_enemy.getRange() >= 0 &&
                                        i + dir.getDx()*current_enemy.getRange() < N_ROWS){

                                    if (Field[j + dir.getDy()* current_enemy.getRange()][i + dir.getDx()* current_enemy.getRange()] >= 3 && !current_enemy.isAttacking()) {
                                        current_enemy.setAttacking(true);
                                        current_enemy.setDirection(dir);
                                    current_enemy.updateAttackTimer(PERIOD);
                                    if (current_enemy.getAttackTimer() >= current_enemy.getAttack_speed()) {
                                        if (Field[j + dir.getDy() * current_enemy.getRange()][i + dir.getDx() * current_enemy.getRange()] == 5) {
                                            CURRENT_HEALTH -= current_enemy.getDamage();
                                            hitEffects.add(new HitEffect((i + dir.getDx()* current_enemy.getRange())*CELL_SIZE,(j + dir.getDy()* current_enemy.getRange())*CELL_SIZE,current_enemy.getDirection(),current_enemy.getDamage()));
                                        }
                                        current_enemy.resetAttackTimer();
                                    }
                                }
                                }
                            }
                            if (!current_enemy.isAttacking()){
                                current_enemy.resetAttackTimer();
                                if (!current_enemy.isMoving()) {
                                    if (findShortestPathToTarget(Field, i, j, current_enemy.getTargeting()) != Direction.NOMOVE) {
                                        current_enemy.setDirection(findShortestPathToTarget(Field, i, j, current_enemy.getTargeting()));
                                        current_enemy.setMoving(true);
                                    }
                                }
                                    if (current_enemy.isMoving && Field[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()]!= MOVING_CELL) {
                                        Field[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = MOVING_CELL;
                                    }
                                    current_enemy.move();
                                    healthbars[j][i].move(current_enemy);
                                    if (current_enemy.getDistanceCounted() >= CELL_SIZE) {
                                        current_enemy.resetDistanceCounted();
                                        current_enemy.setMoving(false);
                                        healthbars[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = new healthbar(healthbars[j][i].getX(), healthbars[j][i].getY(), healthbars[j][i].getHealth(), healthbars[j][i].getMaxHealth());
                                        enemies[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = new enemy(current_enemy.getX(), current_enemy.getY(), current_enemy.getEnemyType(), current_enemy.getHealth(), current_enemy.getDirection());
                                        enemies[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()].initEnemy();
                                        Field[j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = ENEMY_CELL;
                                        Field[j][i] = 0;
                                        enemies[j][i] = null;
                                        healthbars[j][i] = null;
                                    }
                                }
                        }
                         repaint();
                    }
         }
        
    }


    private void updateWave(int level) {
        if (started) {
            if (level == 1) {
                if (CURRENT_WAVE == 1) {
                    if (MILLISECONDS_PASSED >= 2000 && MILLISECONDS_PASSED <= 2020) {
                        MakeEnemy(0, 5, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 10000 && MILLISECONDS_PASSED <= 10020) {
                        MakeEnemy(0, 3, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 13000 && MILLISECONDS_PASSED <= 13020) {
                        MakeEnemy(0, 12, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 30000 && MILLISECONDS_PASSED <= 30020) {
                        CURRENT_WAVE = 2;
                        MILLISECONDS_PASSED = 0;
                    }
                }
                if (CURRENT_WAVE == 2) {
                    if (MILLISECONDS_PASSED >= 100 && MILLISECONDS_PASSED <= 120) {
                        MakeEnemy(0, 4, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 2500 && MILLISECONDS_PASSED <= 2520) {
                        MakeEnemy(0, 4, "farmer", 30);

                    }
                    if (MILLISECONDS_PASSED >= 6800 && MILLISECONDS_PASSED <= 6820) {
                        MakeEnemy(0, 9, "farmer", 30);
                        MakeEnemy(0, 3, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 21000 && MILLISECONDS_PASSED <= 21020) {
                        MakeEnemy(0, 5, "farmer", 30);
                        MakeEnemy(1, 7, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 25000 && MILLISECONDS_PASSED <= 25020) {
                        MakeEnemy(0, 3, "farmer", 30);
                        MakeEnemy(0, 6, "farmer", 30);
                    }
                    if (MILLISECONDS_PASSED >= 40000 && MILLISECONDS_PASSED <= 40020) {
                        CURRENT_WAVE = 3;
                        MILLISECONDS_PASSED = 0;
                    }
                }
            }
        }
    }
    public void MakeEnemy (int x, int y, String enemytype, int health){
        enemies[y][x] = new enemy(x*CELL_SIZE, y * CELL_SIZE, enemytype, health, Direction.LEFT);
        enemies[y][x].initEnemy();
        healthbars[y][x] = new healthbar(x*CELL_SIZE, y * CELL_SIZE - 20, enemies[y][x].getHealth(), enemies[y][x].getMax_health());
        Field[y][x] = ENEMY_CELL;
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (started) {
                MILLISECONDS_PASSED = MILLISECONDS_PASSED + PERIOD;
                doGameCycle();
            }
        }
    }
    }
