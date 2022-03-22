package game.Tower_Defence_Game.level;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import static java.awt.Image.SCALE_DEFAULT;

public class enemy {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected int health;
    protected int damage;
    protected double speed;
    protected int range;
    protected double attack_speed;
    protected int attack_size;
    protected int value;
    protected int targeting;
    protected double internal_speed_timer;
    protected double internal_attack_timer;
    protected levelBoard.Direction direction = levelBoard.Direction.DOWN;

    public enemy(double x, double y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void updateImage(String imageName, levelBoard.Direction direction) {
        ImageIcon imageIcon = new ImageIcon(imageName + "_"+toString(direction) + ".png");

        image = imageIcon.getImage();

        getImageDimensions();
    }

    private String toString(levelBoard.Direction direction) {
        if (direction == levelBoard.Direction.DOWN){
            return "DOWN";
        } else if (direction == levelBoard.Direction.UP){
            return "UP";
        } else if (direction == levelBoard.Direction.RIGHT){
            return "RIGHT";
        } else
            return "LEFT";
    }

    protected void loadImage(String imageName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imageName);

        this.width = width;
        this.height = height;

        image = imageIcon.getImage().getScaledInstance(width, height, SCALE_DEFAULT);
    }


    protected void getImageDimensions() {
        if (width == 0) {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
    }

    public void setDirection(levelBoard.Direction direction) {
        this.direction = direction;
    }

//    public Image getImage(levelBoard.Direction direction) {
//        return image;
//    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public int getDamage(){
        return damage;
    }

    public double getSpeed() {
        return speed;
    }
    public int getRange(){
        return range;
    }

    public double getAttack_speed() {
        return attack_speed;
    }

    public int getAttack_size() {
        return attack_size;
    }

    public int getValue() {
        return value;
    }

    public int getTargeting() {
        return targeting;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void updateSpeedTimer (double increase){
        internal_speed_timer = internal_speed_timer + increase;
    }
    public void updateAttackTimer (double increase){
        internal_attack_timer = internal_attack_timer + increase;
    }
    public double getSpeedTimer() {
        return internal_speed_timer;
    }
    public double getAttackTimer() {
        return internal_attack_timer;
    }
}
