package game.Tower_Defence_Game.level.enemies;

import game.Tower_Defence_Game.level.enemy;
public class farmer extends enemy {
        public farmer(double x, double y) {
        super(x, y);
    }
    public void initFarmer () {
        updateImage("src/recources/level_elements/Enemies/farmer", direction);
        getImageDimensions();
        health = 30;
        damage = 7;
        attack_speed = 1.3;
        speed = 1.5;
        range = 1;
        value = 15;
        targeting = 5;
    }
}
