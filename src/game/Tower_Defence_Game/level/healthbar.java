package game.Tower_Defence_Game.level;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.Image.SCALE_DEFAULT;

public class healthbar {

        protected int x;
        protected int y;
        protected final int normal_width = 50;
        protected final int normal_height = 8;
        protected int width;
        protected int height;
        protected boolean visible;
        protected Image image;
        protected levelBoard.Direction direction;
        protected int health;
        protected int max_health;
        int distanceCounted = 0;


        public healthbar(int x, int y, int health, int max_health) {
            this.x = x;
            this.y = y;
            visible = true;
            this.health = health;
            this.max_health = max_health;
            updateHealthbar(0);
        }
        public int getDistanceCounted(){
            return  distanceCounted;
        }

    public void resetDistanceCounted() {
        distanceCounted = 0;
    }

    public void updateHealthbar(int damage) {
            health -= damage;
            loadImage();

        }
        private void setXY (int x, int y){
            this.x = x;
            this.y = y;
        }
    public void move (enemy Enemy) {
        if (Enemy.getLackedMovement() >= 1) {
            x +=  (Enemy.getSpeed() + 1)* Enemy.getDirection().getDx();
            y +=  (Enemy.getSpeed() + 1)* Enemy.getDirection().getDy();

        } else {
            x +=  Enemy.getSpeed() * Enemy.getDirection().getDx();
            y +=  Enemy.getSpeed() * Enemy.getDirection().getDy();
        }
    }
    public void move (Unit unit) {
        if (unit.getLackedMovement() >= 1) {
            x +=  (unit.getSpeed() + 1)* unit.getDirection().getDx();
            y +=  (unit.getSpeed() + 1)* unit.getDirection().getDy();

        } else {
            x +=  unit.getSpeed() * unit.getDirection().getDx();
            y +=  unit.getSpeed() * unit.getDirection().getDy();
        }
    }
        protected void loadImage() {
            ImageIcon imageIcon;
            double percentage = (double) health/max_health;
            if (percentage >= 0.9) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/100%.png");
            } else if (percentage >= 0.8) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/90%.png");
            } else  if (percentage >= 0.7) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/80%.png");
            } else if (percentage >= 0.6) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/70%.png");
            } else  if (percentage >= 0.5) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/60%.png");
            } else if (percentage >= 0.4) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/50%.png");
            } else  if (percentage >= 0.3) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/40%.png");
            } else if (percentage >= 0.2) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/30%.png");
            } else if (percentage >= 0.1) {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/20%.png");
            } else  {
                imageIcon = new ImageIcon("src/recources/level_elements/Health_bars/10%.png");
            }
            width = normal_width;
            height = normal_height;

            image = imageIcon.getImage().getScaledInstance(width, height, SCALE_DEFAULT);
        }

        public Image getImage() {
            return image;
        }

        public int getX() {
            return x;
        }
        public int getY(){
            return y;
        }

    public int getHealth() {
            return health;
    }

    public int getMaxHealth() {
        return max_health;
    }
}

