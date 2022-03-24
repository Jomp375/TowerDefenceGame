package game.Tower_Defence_Game.level;

import javax.swing.*;
import java.awt.*;

import static java.awt.Image.SCALE_DEFAULT;

public class Unit {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected int max_health;
    protected int health;
    protected int damage;
    protected int speed;
    protected double acc_speed;
    protected int range;
    protected double attack_speed;
    protected int attack_size;
    protected int value;
    protected int targeting;
    protected double distanceCounted;
    protected double internal_attack_timer;
    protected levelBoard.Direction direction = levelBoard.Direction.DOWN;
    protected double lackedMovement;
    protected String enemyType;
    protected boolean isMoving = false;
    protected boolean isAttacking;
    protected String unitType;
    protected int cost;
    protected int upgradesFirstPath;
    protected int upgradesSecondPath;
    protected int upgradesThrirdPath;
    protected String imageName;


    public Unit(int x, int y, String unitType,  int health, levelBoard.Direction direction) {
        this.x = x;
        this.y = y;
        this.health = health;
        visible = true;
        this.direction = direction;
        this.unitType = unitType;
    }

    protected void updateImage( levelBoard.Direction direction) {
        ImageIcon imageIcon = new ImageIcon(imageName + "_"+toString(direction) + ".png");

        image = imageIcon.getImage();

        getImageDimensions();
    }

    public String toString(levelBoard.Direction direction) {
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

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isMoving() {
        return isMoving;
    }
    public void setMoving(boolean isMoving){
        this.isMoving = isMoving;
    }
    public void setAttacking(boolean isAttacking){
        this.isAttacking = isAttacking;
    }

    protected void getImageDimensions() {
        if (width == 0) {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
    }
    public Image getImage(){
        return image;
    }
    public void setDirection(levelBoard.Direction direction) {
        this.direction = direction;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
    public void damage (int damage){
        health -= damage;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public void move () {
        if (lackedMovement >= 1) {
            x +=  (speed + 1)* direction.getDx();
            y +=  (speed + 1)* direction.getDy();
            lackedMovement -= 1;
            distanceCounted += speed + 1;

        } else {
            x +=  speed * direction.getDx();
            y +=  speed * direction.getDy();
            lackedMovement += acc_speed - speed;
            distanceCounted += speed ;
        }

    }

    public double getDistanceCounted() {
        return distanceCounted;
    }

    public void setXY(int x, int y) {
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
    public double getLackedMovement() {
        return lackedMovement;
    }
    public void updateLackedMovement (double increase){
        lackedMovement = lackedMovement + increase;
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

    public void resetAttackTimer () {
        internal_attack_timer = 0;
    }
    public void updateAttackTimer (double increase){
        internal_attack_timer = internal_attack_timer + increase;
    }

    public double getAttackTimer() {
        return internal_attack_timer;
    }

    public int getMax_health() {
        return max_health;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public levelBoard.Direction getDirection() {
        return direction;
    }

    public void resetDistanceCounted() {
        distanceCounted = 0;
    }

    public void initUnit() {
        if (unitType == "spearman"){
            imageName = "src/recources/level_elements/Friendly_units/Spearman/spearman";
            updateImage( direction);
            max_health = 50;
            health = max_health;
            damage = 8;
            speed = 2;
            acc_speed = 2;
            range = 1;
            attack_speed = 1000;
            attack_size = 1;
            cost = 50;
        }
    }
}

