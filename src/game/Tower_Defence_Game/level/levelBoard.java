package game.Tower_Defence_Game.level;

import game.Tower_Defence_Game.level.enemies.farmer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class levelBoard extends JPanel {
    private final int CELL_SIZE = 60;
    private final int EMPTY_CELL = 0;
    private final int ROCK_CELL = 2;
    private final int ENEMY_CELL = 1;
    private final int MAIN_BASE_CELL = 5;
    private final int N_ROWS = 25;
    private final int N_COLS = 13;
    private final double DELTA_TIME = 0.1;
    private final int BOARD_WIDTH = N_ROWS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_COLS * CELL_SIZE + 10;
    private enemy current_enemy;
    private boolean inGame;
    private Image[] img;
    private final JLabel statusbar;
    public int level;
    public int[][] Field = new int[N_ROWS][ N_COLS ];
    public enemy [][] enemies = new enemy [N_ROWS][ N_COLS ];
    private final ArrayList<Main_base> mainBaseCellsList = new ArrayList<Main_base>();
    private final int startMoney = 200;
    private int money = startMoney;

    private int CURRENT_WAVE = 1;
    private int SECONDS_PASSED = 0;
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
        addMouseListener(new MinesAdapter());
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
        Graphics2D g2d = (Graphics2D) g;
        boolean isFirstBase = true;
        ImageIcon rock = new ImageIcon("src/recources/level_elements/Rock.png");
        ImageIcon empty = new ImageIcon("src/recources/level_elements/empty.png");
        ImageIcon base = new ImageIcon("src/recources/level_elements/main_base.png");
        for (int j = 0; j < N_COLS; j++) {
            for (int i = 0; i < N_ROWS; i++) {
                if (Field[j][i] == EMPTY_CELL) {
                   g2d.drawImage(empty.getImage(), i * CELL_SIZE,
                            j * CELL_SIZE, this);

                } else if (Field[j][i] == ROCK_CELL) {

                    g2d.drawImage(rock.getImage(), i * CELL_SIZE,
                            j * CELL_SIZE, this);
                } else if (Field[j][i] == MAIN_BASE_CELL && isFirstBase) {
                    g2d.drawImage(base.getImage(),i * CELL_SIZE,
                            j * CELL_SIZE, this);
                    isFirstBase = false;
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
            boolean doRepaint = false;
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
                                    {2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,5,5,5},
                                    {2,2,2,0,0,0,0,0,0,0,0,2,0,0,0,2,2,0,0,0,0,0,0,0,0},
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
                int newX = node.x + dir.getDx()*CELL_SIZE;
                int newY = node.y + dir.getDy()*CELL_SIZE;
                Direction newDir = node.initialDir == null ? dir : node.initialDir;

                // Mouse found?
                for (int j = 0; j < N_COLS; j++) {
                    for (int i = 0; i < N_ROWS; i++) {
                        if (Field [j][i] >= target) {
                            if (newX == i*CELL_SIZE && newY == j * CELL_SIZE) {
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
        repaint();
    }
    private void fightOrFlight() {
         for (int j = 0; j < N_COLS; j++) {
                    for (int i = 0; i < N_ROWS; i++) {
                         if (enemies [j][i] != null) {
                             current_enemy = enemies [j][i];
                       
                            for (Direction dir : Direction.values()) {
                                if (Field[j + dir.getDy()*CELL_SIZE*current_enemy.getRange()][i + dir.getDx()*CELL_SIZE*current_enemy.getRange()] >= 3){
                                    
                                }
                            }
                        }
                    }
         }
        
    }
    private void updateWave(int level) {
        if (level == 1) {
            if (CURRENT_WAVE == 1){
                if (SECONDS_PASSED == 10){
                    enemies[5][0] = new farmer(0,5* CELL_SIZE);
                    Field [5][0] = ENEMY_CELL;
                }
            }
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SECONDS_PASSED = SECONDS_PASSED + DELTA_TIME;
            doGameCycle();
        }
    }
    }
