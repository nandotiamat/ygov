package src.game.card;

import java.awt.image.BufferedImage;

public class Trap extends CardObject{

    public Trap(String name, int id, String description, BufferedImage image) {
        super(name, TYPE.Trap, id, description, image);

    }

    public void set(){}

    public void destroy(){}


}