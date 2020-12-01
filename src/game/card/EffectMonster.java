package src.game.card;

public class EffectMonster extends Monster{

    public EffectMonster(String name, TYPE type, int id, String description, int level, int atk, int def) {
        super(name, type, id, description, level, atk, def);
        
    }

    public void attack() {}

    public void summon() {}

    public void set() {}

    public void destroy() {}
    
}
