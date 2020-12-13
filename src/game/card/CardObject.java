package src.game.card;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;

public abstract class CardObject {

    public static float whratio = 0.68688f;
    public static int cardWidth = 60;
    public static int cardHeight = (int) (((float) cardWidth)/whratio); 
    public static final int HANDSIZE = 1;
    public static final int HUDSIZE = 2;

    protected int x;
    protected int y;

    protected String name;
    protected ATTRIBUTE attribute;
    protected int id;
    protected String description;
    protected BufferedImage image;
    protected BufferedImage handImage;
    protected BufferedImage hudImage;

    protected boolean isSelected;
    protected boolean isSettable;
    

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
        isSelected = false;
        isSettable = true;
    }

    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public abstract void destroy(); 
    public abstract void renderOptions(Graphics g);

    public void render(Graphics g, int size) {
        if (size == HANDSIZE) {
            g.drawImage(handImage, x, y, null);
        } else if (size == HUDSIZE) {
            g.drawImage(hudImage, x, y, null);
        }
        renderOptions(g);
    }

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

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean bool) {
        isSelected = bool;
    }

    public boolean getIsSettable() {
        return isSettable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //implement getters and setters eventually
    
}
