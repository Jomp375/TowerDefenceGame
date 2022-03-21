package game.Tower_Defence_Game.level;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class levelBoard extends JPanel {
    private final int CELL_SIZE = 40;
    private final int EMPTY_CELL = 0;
    private final int ROCK_CELL = 2;
    private final int MAIN_BASE_CELL = 5;
    private final int N_ROWS = 30;
    private final int N_COLS = 19;
    private final int BOARD_WIDTH = N_ROWS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_COLS * CELL_SIZE + 10;
    private boolean inGame;
    private Image[] img;
    private final JLabel statusbar;
    public int level;
    public int[][] Field = new int[N_ROWS][ N_COLS ];
    private final ArrayList<Main_base> mainBaseCellsList = new ArrayList<Main_base>();
    private final int startMoney = 200;
    private int money = startMoney;

    private int CURRENT_WAVE = 1;
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
        for (int i = 0; i < Field.length; i++) {
            for (int j = 0; j < Field[i].length; j++) {
                if (Field[i][j] == EMPTY_CELL) {
                   g2d.drawImage(empty.getImage(), i * CELL_SIZE,
                            j * CELL_SIZE, this);

                } else if (Field[i][j] == ROCK_CELL) {

                    g2d.drawImage(rock.getImage(), i * CELL_SIZE,
                            j * CELL_SIZE, this);
                } else if (Field[i][j] == MAIN_BASE_CELL && isFirstBase) {
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
                for (int i = 0; i < Field.length; i++) {
                    for (int j = 0; j < Field[i].length; j++) {
                        if ((j <=1 || j >= 17 || (j >=10 && j <=12)) && i <=15 && i >= 13){
                            Field [i][j] = ROCK_CELL;
                        }else if ((j == 5 || j == 13)&& i <= 22){
                            Field [i][j] = ROCK_CELL;
                        } else if (((j >= 3 && j <= 8)|| (j >= 13 && j <= 15))&& i >= 5 && i <= 7){
                            Field [i][j] = ROCK_CELL;
                        } else if ((j >= 8 && j <= 10) && i >= 27){
                            Field [i][j] = MAIN_BASE_CELL;
                        }
                    }
                }
            }
        }
    /**
     * Finds the shortest path from cat to mouse in the given labyrinth.
     *
     * @param lab the labyrinth's matrix with walls indicated by {@code true}
     * @param cx the cat's X coordinate
     * @param cy the cat's Y coordinate
     * @param mx the mouse's X coordinate
     * @param my the mouse's Y coordinate
     * @return the direction of the shortest path
     */
    private Direction findShortestPathToMouse
    (int[][] lab, int cx, int cy, int mx, int my) {
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
                int newX = node.x + dir.getDx();
                int newY = node.y + dir.getDy();
                Direction newDir = node.initialDir == null ? dir : node.initialDir;

                // Mouse found?
                if (newX == mx && newY == my) {
                    return newDir;
                }

                // Is there a path in the direction (= is it a free field in the labyrinth)?
                // And has that field not yet been discovered?
                if (lab[newY][newX] != ROCK_CELL && !discovered[newY][newX]) {
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

    }