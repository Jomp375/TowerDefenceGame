package game.Tower_Defence_Game.level;

import java.awt.Image;
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
    protected levelBoard.Direction direction = levelBoard.Direction.DOWN;

    public enemy(double x, double y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {
        ImageIcon imageIcon = new ImageIcon(imageName);

        image = imageIcon.getImage();

        getImageDimensions();
    }

    protected void loadImage(String imageName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imageName);

        this.width = width;
        this.height = width;

        image = imageIcon.getImage().getScaledInstance(width, height, SCALE_DEFAULT);
    }

    protected void getImageDimensions() {
        if (width == 0) {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
    }

    public Image getImage() {
        return image;
    }

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
}