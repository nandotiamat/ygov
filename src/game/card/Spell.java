package src.game.card;

import java.awt.image.BufferedImage;

public abstract class Spell extends CardObject{

    public Spell(String name, int id, String description, BufferedImage image) {
        super(name, TYPE.Spell, id, description, image);

    }

    public void set(){}


}
