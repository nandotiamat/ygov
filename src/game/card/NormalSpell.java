package src.game.card;

import java.awt.image.BufferedImage;

import src.game.Match;

public class NormalSpell extends Spell {

    public NormalSpell(String name, int id, String description, BufferedImage image, Match match) {
        super(name, id, description, image, match);
    }

    public void destroy() {
    }

}
