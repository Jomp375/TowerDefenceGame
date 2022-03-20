package game.Tower_Defence_Game.level;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Main_base {
    public static final Random random = new Random();
    public int WIDTH;
    public int HEIGHT;
    private boolean isBadGuy;

    private int x;
    private int y;
    private Image image;
    private boolean isVisible;
    private long start;
    private long end;

    public Main_base(int x, int y) {
        this.x = x;
        this.y = y;
        loadImage();
    }


    private void loadImage() {
        ImageIcon ii;

        ii = new ImageIcon("src/recources/level_elements/main_base.png");
        image = ii.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        loadImage();
        return image;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean isVisible) {
        if (isVisible != this.isVisible) {
            if (isVisible) {
                start = System.currentTimeMillis();
                end = 0;
            } else {
                end = System.currentTimeMillis();
            }
        } else {
            start = System.currentTimeMillis();
            end = 0;
        }
        this.isVisible = isVisible;
    }

    private long getShowTime() {
        if (!isVisible) {
            return end - start;
        } else {
            return System.currentTimeMillis() - start;
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getTime() {
        return (int)(end - start);
    }

    public void determineType( int probabilityBadGuy) {
        int counter = random.nextInt(100);
        isBadGuy = counter < probabilityBadGuy;

        loadImage();
    }

}