package game.Tower_Defence_Game.level.Friendly_Units;

import game.Tower_Defence_Game.level.Unit;
import game.Tower_Defence_Game.level.levelBoard;

import javax.swing.*;
import java.awt.*;

import static java.awt.Image.SCALE_DEFAULT;

public class spearman extends Unit {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected int max_health = 50;
    protected int health = max_health;
    protected int damage = 8;
    protected int speed = 2;
    protected double acc_speed = 2;
    protected int range = 1;
    protected double attack_speed = 100;
    protected int attack_size = 1;
    protected int cost = 50;
    protected double distanceCounted;
    protected double internal_attack_timer;
    protected levelBoard.Direction direction;
    protected double lackedMovement;
    protected boolean isMoving = false;
    protected boolean isAttacking;
    protected int upgradesFirstPath;
    protected int upgradesSecondPath;
    protected int upgradesThrirdPath;


    public spearman(int x, int y, int upgradesFirstPath, int upgradesSecondPath, int upgrdadesThrirdPath, int health, levelBoard.Direction direction) {
        super(x,y,health, direction);
        this.upgradesFirstPath = upgradesFirstPath;
        this.upgradesSecondPath = upgradesSecondPath;
        this.upgradesThrirdPath = upgrdadesThrirdPath;
        visible = true;
        initUnit();
    }
    public void initUnit () {
            updateImage("src/recources/level_elements/Enemies/farmer", direction);
        }

}

