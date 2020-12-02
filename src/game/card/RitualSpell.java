package src.game.card;

import java.awt.image.BufferedImage;

public class RitualSpell extends Spell {

    public RitualSpell(String name, int id, String description, BufferedImage image) {
        super(name, id, description, image);
    }

    public void destroy() {}
}
