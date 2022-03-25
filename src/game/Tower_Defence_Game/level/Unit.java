package game.Tower_Defence_Game.level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;

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
    protected double distanceCounted;
    protected double internal_attack_timer;
    protected levelBoard.Direction direction;
    protected double lackedMovement;
    protected String enemyType;
    protected boolean isMoving;
    protected boolean isAttacking;
    protected String unitType;
    protected int cost;
    protected int upgradesFirstPath;
    protected int upgradesSecondPath;
    protected int upgradesThrirdPath;
    protected String imageName;
    protected int targetX;
    protected int targetY;
    protected double healing_timer;
    int heal_time;
    int healing;
    double healed;
    int collum;
    int row;

    public Unit(int x, int y, String unitType,  int health, levelBoard.Direction direction, int collum, int row, int targetX, int targetY, boolean isMoving) {
        this.x = x;
        this.y = y;
        this.health = health;
        visible = true;
        this.direction = direction;
        this.unitType = unitType;
        this.targetX = targetX;
        this.targetY = targetY;
        this.collum = collum;
        this.row = row;
        this.isMoving = isMoving;
   }

    protected void updateImage( levelBoard.Direction direction) {
        ImageIcon imageIcon = new ImageIcon(imageName + "_"+toString(direction) + ".png");

        image = imageIcon.getImage();

        getImageDimensions();
    }

    public int getTargetX(){
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTarget (int x, int y){
        targetX = x;
        targetY = y;
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
    public void updatePosition (int collumincrease, int rowincrease){
        collum += collumincrease;
        row += rowincrease;
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
    public void move (int [][] lab) {
        setDirection(findShortestPathToGoal(lab, collum, row, targetX, targetY));
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
    public void resetHealTimer () {
        healing_timer = 0;
        healing = 0;
    }
    public void updateHealTimer (double increase){
        healing_timer = healing_timer + increase;
    }

    public double getHealing_timer() {
        return healing_timer;
    }

    public levelBoard.Direction getDirection() {
        return direction;
    }

    public void resetDistanceCounted() {
        distanceCounted = 0;
    }

    public void initUnit() {
        if (unitType.equals( "spearman")){
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
            heal_time = 3000;
            healed = 0.1;
        }
    }
    public int heal (double period){
        healing_timer += period;
        if (healing_timer >= heal_time){
            healing += healed;
            if (healing >= 1){
                health += 1;
                healing -= 1;
                return 1;
            } else{
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int getHeal_time() {
        return heal_time;
    }

    public String getUnitType() {
        return unitType;
    }
    private levelBoard.Direction findShortestPathToGoal
            (int [][] lab, int cx, int cy, int goalx, int goaly) {
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
            for (levelBoard.Direction dir : levelBoard.Direction.values()) {
                if ((node.x != 0 || dir.getDx() >= 0) && (node.x != lab[0].length - 1 || dir.getDx() <= 0) && (node.y != 0 || dir.getDy() >= 0) && (node.y != lab.length - 1 || dir.getDy() <= 0)) {
                    int newX = node.x + dir.getDx();
                    int newY = node.y + dir.getDy();
                    levelBoard.Direction newDir = node.initialDir == null ? dir : node.initialDir;

                    // Mouse found?
                    if (newX == goalx && newY == goaly) {
                        return newDir;
                    }

                    // Is there a path in the direction (= is it a free field in the labyrinth)?
                    // And has that field not yet been discovered?
                    if ((lab[newY][newX] < 2) && !discovered[newY][newX]) {
                        // "Discover" and enqueue that field
                        discovered[newY][newX] = true;
                        queue.add(new Node(newX, newY, newDir));
                    }
                }
            }
        }
        throw new IllegalStateException("no path found");
    }

    private static class Node {
        final int x;
        final int y;
        final levelBoard.Direction initialDir;

        public Node(int x, int y, levelBoard.Direction initialDir) {
            this.x = x;
            this.y = y;
            this.initialDir = initialDir;
        }
    }
}

