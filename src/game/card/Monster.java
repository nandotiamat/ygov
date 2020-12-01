package src.game.card;

public abstract class Monster extends CardObject{

    protected int level;
    protected int atk;
    protected int def;


    public Monster(String name, TYPE type, int id, String description, int level, int atk, int def) {
        super(name, type, id, description);
        this.level = level;
        this.atk = atk;
        this.def = def; 
    }

    //implement getters and setters eventually

    public abstract void attack();
    public abstract void summon();
    public abstract void set();
}
