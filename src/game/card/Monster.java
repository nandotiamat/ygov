package src.game.card;

import java.awt.image.BufferedImage;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;


    public Monster(String name, TYPE type, int id, String description, BufferedImage image, int level, int atk, int def) {
        super(name, type, id, description, image);
        this.level = level;
        this.atk = atk;
        this.def = def; 
    }

    //implement getters and setters eventually

    public abstract void attack();
    public abstract void summon();
    public abstract void set();
}
