package src.game.card;

import java.awt.image.BufferedImage;

public class EffectMonster extends Monster{

    public EffectMonster(String name, TYPE type, int id, String description, BufferedImage image, int level, int atk, int def) {
        super(name, type, id, description, image, level, atk, def);
        
    }

    public void attack() {}

    public void summon() {}

    public void set() {}

    public void destroy() {}
    
}
