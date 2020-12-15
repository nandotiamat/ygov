package src.game.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import src.game.Game;
import src.game.Match;

public class ExtraDeck {

    private ArrayList<CardObject> deck;
    private Match match;
    private BufferedImage backCard;

    private boolean isSelected;
    private boolean canRenderList;

    private int x;
    private int y;
    private Rectangle listRectangle;

    public ExtraDeck(Match match, int[] pos) {
        this.match = match;
        x = pos[0];
        y = pos[1];
        isSelected = false;
        canRenderList = false;
        deck = getDeck();
        try {
            backCard = ImageIO.read(new File("src/img/cardBack.png"));
            backCard = resizeImage(backCard, CardObject.cardWidth, CardObject.cardHeight);

        } catch (IOException e) {
            System.out.println("Error. Couldn't load back image of Deck class.");
        }
        listRectangle = new Rectangle(x, y, 10 + deck.size()*(CardObject.cardWidth + 10), 20 + CardObject.cardHeight);
        organizeCardPositions();
    }
        
    // DA RIMUOVERE E CREARE UN FILE APPOSITO CON QUESTA FUNZIONE CHE PUÒ SERVIRE UN
    // PO IN GIRO!
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public void render(Graphics g, int[] pos) {
        g.drawImage(backCard, pos[0], pos[1], null);
        g.setColor(Color.white);
        g.drawString(Integer.toString(deck.size()), pos[0] + 20, pos[1] + 40);
        renderOptions(g);
        if (canRenderList) {
            g.setColor(Color.white);
            g.fillRect((Game.WIDTH - listRectangle.width)/2, (Game.HEIGHT - listRectangle.height)/2, listRectangle.width, listRectangle.height);
            for (int i=0; i<deck.size(); i++) {
                deck.get(i).render(g, CardObject.HANDSIZE);
            }
        }
    }

    private void renderOptions(Graphics g) {
        if (isSelected) {
            g.setColor(Color.red);
            g.fillRect(x + CardObject.cardWidth/2 - 15, y - 50, 30, 30);
        }
    }
    
    public void organizeCardPositions() {
        int tmprow = 0;
        for (int i=0; i<deck.size(); i++) {
            if (i % 6 == 0 && i != 0) 
                tmprow++;
            deck.get(i).setX((Game.WIDTH - listRectangle.width)/2 + 10 + (i - 6*tmprow)*(CardObject.cardWidth + 10));
            deck.get(i).setY((Game.HEIGHT - listRectangle.height)/2 + 10 + (tmprow) *(CardObject.cardHeight + 10));
        }
    }

    private ArrayList<CardObject> getDeck() {
        ArrayList<CardObject> deck = new ArrayList<CardObject>();
        JSONParser parser = new JSONParser();
        JSONArray database = null;
        JSONArray deckDb = null;

        try {
            // sistemare file json
            database = (JSONArray) parser.parse(new FileReader("src/game/list.json"));
            deckDb = (JSONArray) parser.parse(new FileReader("src/game/bellinideck.json"));
            deckDb = (JSONArray) deckDb.get(1);
        } catch (FileNotFoundException e) {
            System.out.println("JSON file not found.");
        } catch (ParseException e) {
            System.out.println("ParseException");
        } catch (IOException e) {
            System.out.println("IOException");
        }

        for (Object o : deckDb) {
            JSONObject element = (JSONObject) o;
            // id carta
            int index = Integer.parseInt((String) element.get("serial"));
            JSONObject cardParsed = null;

            for (int i = 0; i < database.size(); i++) {
                cardParsed = (JSONObject) database.get(i);
                if (Integer.parseInt((String) cardParsed.get("serial")) == index) {
                    break;
                }
            }
            String name = (String) cardParsed.get("name");
            String description = (String) cardParsed.get("carddescription");
            ATTRIBUTE attribute = ATTRIBUTE.valueOf((String) cardParsed.get("attribute"));
            String type = (String) cardParsed.get("type");
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/img/" + index + ".png"));
            } catch (IOException e) {
                System.out.println("Cant load image from files");
            }
            int atk = Integer.parseInt((String) cardParsed.get("atk"));
            int def = Integer.parseInt((String) cardParsed.get("def"));
            int level = Integer.parseInt((String) cardParsed.get("level"));

            for (int i = 0; i < (long) element.get("quantity"); i++) {
                deck.add(new FusionMonster(name, attribute, index, description, image, match, level, atk, def, type));
            }
            
        }
        return deck;
    }

    public void printDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).name);
        }
    }

    public Rectangle getListRectangle() {
        return listRectangle; 
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    
    public void setIsSelected(boolean bool) {
        isSelected = bool;
    }

    public boolean getCanRenderList() {
        return canRenderList;
    }
    
    public void setCanRenderList(boolean bool) {
        canRenderList = bool;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}