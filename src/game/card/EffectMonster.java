package src.game.card;

import java.awt.image.BufferedImage;

public class EffectMonster extends Monster{

    public EffectMonster(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image, int level, int atk, int def, String type) {
        super(name, attribute, id, description, image, level, atk, def, type);
        
    }

    public void attack() {}

    public void set() {}

    public void destroy() {}

}
