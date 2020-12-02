package src.game.card;

import java.awt.image.BufferedImage;

public abstract class CardObject {
    protected String name;
    protected TYPE type;
    protected int id;
    protected String description;
    protected BufferedImage image;

    public CardObject(String name, TYPE type, int id, String description, BufferedImage image) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public abstract void destroy(); 

    //implement getters and setters eventually
    
}
