package src.game.card;

import java.awt.image.BufferedImage;

import src.game.Match;

public class QuickPlaySpell extends Spell {

    public QuickPlaySpell(String name, int id, String description, BufferedImage image, Match match) {
        super(name, id, description, image, match);
    }

    public void destroy() {
    }
}
