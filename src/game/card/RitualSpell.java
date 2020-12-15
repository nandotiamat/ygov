package src.game.card;

import java.awt.image.BufferedImage;

import src.game.Match;

public class RitualSpell extends Spell {

    public RitualSpell(String name, int id, String description, BufferedImage image, Match match) {
        super(name, id, description, image, match);
    }

    public void destroy() {
    }

}
