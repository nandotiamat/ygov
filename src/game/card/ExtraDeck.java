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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ExtraDeck {

    private ArrayList<CardObject> deck;
    private BufferedImage backCard;

    public ExtraDeck() {
        deck = getDeck();
        try {
            backCard = ImageIO.read(new File("src/img/cardBack.png"));
            backCard = resizeImage(backCard, CardObject.cardWidth, CardObject.cardHeight);

        } catch (IOException e) {
            System.out.println("Error. Couldn't load back image of Deck class.");
        }
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
        g.drawString(Integer.toString(deck.size()), pos[0] + 20, pos[1] + 40);
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
                deck.add(new FusionMonster(name, TYPE.Dark, index, description, image, level, atk, def));
            }
            
        }
        return deck;
    }

    private void printDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).name);
        }
    }

}