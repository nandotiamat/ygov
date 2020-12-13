package src.game.card;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Trap extends CardObject {

    public Trap(String name, int id, String description, BufferedImage image) {
        super(name, ATTRIBUTE.Trap, id, description, image);

    }

    public void set() {
    }

    public void destroy() {
    }

    @Override
    public void renderOptions(Graphics g) {
        // TODO Auto-generated method stub

    }


}
