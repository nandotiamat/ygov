package src.game.card;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class CardObject {

    public static final int HANDSIZE = 1;
    public static final int HUDSIZE = 2;
    
    public static float whratio;
    public static int cardWidth;
    public static int cardHeight;
    protected static BufferedImage backImage;
    protected static BufferedImage handBackImage;
    protected static BufferedImage hudBackImage;

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
    protected boolean isCovered; 
    
    static {
        whratio = 0.68688f;
        cardWidth = 60;
        cardHeight = (int) (((float) cardWidth)/whratio); 
        try {
            backImage = ImageIO.read(new File("src/img/cardBack.png"));
            handBackImage = resizeImage(backImage, cardWidth, cardHeight);
            hudBackImage = resizeImage(backImage, cardWidth*3, cardHeight*3);
        } catch (IOException e) {
            System.out.println("Can't load cardBack image in static method of CardObject.");
        }
    }
    
    //DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN PO IN GIRO!
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

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
        isCovered = false;
    }


    public abstract void destroy(); 
    public abstract void renderOptions(Graphics g);

    public void render(Graphics g, int size) {
        if (size == HANDSIZE) {
            if (isCovered) 
                g.drawImage(handBackImage, x, y, null);
            else 
                g.drawImage(handImage, x, y, null); 
        } else if (size == HUDSIZE) {
            if (isCovered) 
                g.drawImage(hudBackImage, x, y, null);
            else
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

    public void setIsCovered(boolean bool) {
        isCovered = bool; 
    }

    //implement getters and setters eventually
    
}
