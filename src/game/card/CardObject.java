package src.game.card;

import java.awt.image.BufferedImage;

public abstract class CardObject {

    public static float whratio = 0.68688f;
    public static int cardWidth = 60;
    public static int cardHeight = (int) (((float) cardWidth)/whratio); 

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

    public BufferedImage getImage() {
        return image;
    }
    //implement getters and setters eventually
    
}
