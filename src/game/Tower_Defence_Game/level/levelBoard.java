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
    private final int EMPTY_CELL = 0;
    private final int ROCK_CELL = 2;
    private final int ENEMY_CELL = 1;
    private final int MAIN_BASE_CELL = 5;
    private final int N_ROWS = 25;
    private final int N_COLS = 13;
    private final int FPS = 50;
    private final int PERIOD = 1000 / FPS;
    private final int BOARD_WIDTH = N_ROWS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_COLS * CELL_SIZE + 10;
    private boolean inGame;
    private boolean doRepaint = true;
    private final JLabel statusbar;
    public int level;
    public int[][] Field = new int[N_ROWS][ N_COLS ];
    public enemy [][] enemies = new enemy [N_COLS][ N_ROWS ];
    private final int startMoney = 200;
    private int money = startMoney;
    private int number = 1;
    private int CURRENT_WAVE = 1;
    private double MILLISECONDS_PASSED = 0;
    private final int AMOUNT_OF_WAVES = 7;

    public int MAX_HEALTH = 2000;
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
        javax.swing.Timer timer = new Timer(PERIOD, new GameCycle());
        timer.start();
        newGame();
    }
    private void newGame() {
        int cell;
        var random = new Random();
        inGame = true;
        statusbar.setText("Wave " + CURRENT_WAVE + " / " + AMOUNT_OF_WAVES);
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
        for (int j = 0; j < N_COLS; j++) {
            for (int i = 0; i < N_ROWS; i++) {
                if (Field[j][i] == EMPTY_CELL) {
//                   g2d.drawImage(empty.getImage(), i * CELL_SIZE,
//                            j * CELL_SIZE, this);

                } else if (Field[j][i] == ROCK_CELL) {

                    g2d.drawImage(rock.getImage(), i * CELL_SIZE,
                            j * CELL_SIZE, this);
                } else if (Field[j][i] == MAIN_BASE_CELL && isFirstBase) {
                    g2d.drawImage(base.getImage(),i * CELL_SIZE,
                            j * CELL_SIZE, this);
                    isFirstBase = false;
                } else if (Field[j][i] == ENEMY_CELL) {
                    current_enemy = enemies [j][i];
                    g2d.drawImage(current_enemy.getImage(),
                            current_enemy.getX(),
                            current_enemy.getY(),
                            this);
                }
            }
        }
//        drawBase(g);
        if ( inGame && isFirstBase) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }
    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;
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
                    if ((lab[newY][newX] != ROCK_CELL) && (lab[newY][newX] != ENEMY_CELL) && !discovered[newY][newX]) {
                        // "Discover" and enqueue that field
                        discovered[newY][newX] = true;
                        queue.add(new Node(newX, newY, newDir));
                    }
                }
            }
        }

        throw new IllegalStateException("No path found");
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
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

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
            fightOrFlight();
        }
        if (doRepaint) {
            repaint();
        }
    }
    private void fightOrFlight() {
         for (int j = 0; j < N_ROWS; j++) {
                    for (int i = 0; i < N_COLS; i++) {
                         if (enemies [j][i] != null) {
                             enemy current_enemy = enemies[j][i];
                             current_enemy.setAttacking(false);
                             statusbar.setText("Wave " + CURRENT_WAVE + " / " + AMOUNT_OF_WAVES + "           " + "We have moved " + number + " times" );
                            for (Direction dir : Direction.values()) {
                                if (j + dir.getDy()*current_enemy.getRange() >= 0 &&
                                        j + dir.getDy()*current_enemy.getRange() <= N_COLS &&
                                        i + dir.getDx()*current_enemy.getRange() >= 0 &&
                                        i + dir.getDx()*current_enemy.getRange() <= N_ROWS){

                                    if (Field[j + dir.getDy()* current_enemy.getRange()][i + dir.getDx()* current_enemy.getRange()] >= 3 && !current_enemy.isAttacking()) {
                                        current_enemy.setAttacking(true);
                                        current_enemy.setDirection(dir);
                                    current_enemy.updateAttackTimer(PERIOD);
                                    if (current_enemy.getAttackTimer() == current_enemy.getAttack_speed()) {
                                        if (Field[j + dir.getDy() * CELL_SIZE * current_enemy.getRange()][i + dir.getDx() * CELL_SIZE * current_enemy.getRange()] == 5) {
                                            CURRENT_HEALTH -= current_enemy.getDamage();
                                        }
                                    }
                                }
                                }
                            }
                            if (!current_enemy.isAttacking()){
                                current_enemy.resetAttackTimer();
                                if (!current_enemy.isMoving()) {
                                    current_enemy.setDirection(findShortestPathToTarget(Field, i, j, current_enemy.getTargeting()));
                                    current_enemy.setMoving(true);
                                }
                                current_enemy.move();
                                if (current_enemy.getDistanceCounted() >= CELL_SIZE){
                                    current_enemy.resetDistanceCounted();
                                    current_enemy.setMoving(false);
                                    enemies [j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = new enemy(current_enemy.getX(), current_enemy.getY(), current_enemy.getEnemyType(),current_enemy.getHealth());
                                    Field [j + current_enemy.getDirection().getDy()][i + current_enemy.getDirection().getDx()] = ENEMY_CELL;
                                    Field [j][i] = 0;
                                    enemies [j][i] = null;
                                    number += 1;
                                }
                            }
                        }
                         repaint();
                    }
         }
        
    }
    private void updateWave(int level) {
        if (level == 1) {
            if (CURRENT_WAVE == 1){
                if (MILLISECONDS_PASSED >= 10000 && MILLISECONDS_PASSED <= 10020){
                    enemies[5][0] = new enemy(0,5* CELL_SIZE, "farmer", 30);
                    enemies[5][0].initFarmer();
                    Field [5][0] = ENEMY_CELL;
                }
            }
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MILLISECONDS_PASSED = MILLISECONDS_PASSED +PERIOD;
            doGameCycle();
        }
    }
    }
