package src.game.card;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class QuickPlaySpell extends Spell {

    public QuickPlaySpell(String name, int id, String description, BufferedImage image) {
        super(name, id, description, image);
    }

    public void destroy() {
    }

    @Override
    public void renderOptions(Graphics g) {
        // TODO Auto-generated method stub

    }
}
