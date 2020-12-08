package src.game.card;

import java.awt.image.BufferedImage;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;
    protected String type;

    public Monster(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image, int level, int atk, int def, String type) {
        super(name, attribute, id, description, image);
        this.level = level;
        this.atk = atk;
        this.def = def; 
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public String getType() {
        return type;
    }
    //implement getters and setters eventually

    public abstract void attack();
    public abstract void summon();
    public abstract void set();
}
