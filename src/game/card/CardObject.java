package src.game.card;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;

public abstract class CardObject {

    public static float whratio = 0.68688f;
    public static int cardWidth = 60;
    public static int cardHeight = (int) (((float) cardWidth)/whratio); 

    protected String name;
    protected ATTRIBUTE attribute;
    protected int id;
    protected String description;
    protected BufferedImage image;
    private BufferedImage handImage;
    private BufferedImage hudImage;


    public CardObject(String name, ATTRIBUTE attribute, int id, String description, BufferedImage image) {
        this.name = name;
        this.attribute = attribute;
        this.id = id;
        this.description = description;
        this.image = image;
        try {
            handImage = resizeImage(image, cardWidth, cardHeight);
            hudImage = resizeImage(image, cardWidth*3, cardHeight*3);
        } catch (Exception e) {
            System.out.println("its lit!");
        }
    }

    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public abstract void destroy(); 

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getHandImage() {
        return handImage;
    }

    public BufferedImage getHUDImage() {
        return hudImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public ATTRIBUTE getAttribute() {
        return attribute;
    }
    //implement getters and setters eventually
    
}
