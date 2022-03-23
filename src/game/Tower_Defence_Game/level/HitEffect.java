package game.Tower_Defence_Game.level;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.awt.Image.SCALE_DEFAULT;

public class HitEffect {
    protected int x;
    protected int y;
    protected final int normal_width = 20;
    protected final int normal_height = 20;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected levelBoard.Direction direction;
    protected int power;
    protected int age = 0;


    public HitEffect(int x, int y, levelBoard.Direction direction, int power) {
        this.x = x;
        this.y = y;
        visible = true;
        this.direction = direction;
        this.power = power;
        initHitEffectRNG();
    }
    public HitEffect(int x, int y, int power) {
        this.x = x;
        this.y = y;
        visible = true;
        this.power = power;
        loadImage();
    }

    private void initHitEffectRNG() {
        Random random = new Random();
        int Random = random.nextInt(10, 50);
        x += direction.getDy()*Random;
        y += direction.getDx()*Random;
        loadImage();

    }
    protected void loadImage() {
        ImageIcon imageIcon = new ImageIcon("src/recources/level_elements/HitEffect.png");

        width = normal_width+ power;
        height = normal_height+ power;

        image = imageIcon.getImage().getScaledInstance(width, height, SCALE_DEFAULT);
    }

    public Image getImage() {
        return image;
    }

    public void setPower(int power){
        this.power = power;
    }

    public int getX() {
        return x;
    }
    public int getY(){
        return y;
    }

    public void updateAge() {
        age += 1;
    }
    public int getAge(){
        return age;
    }
}
