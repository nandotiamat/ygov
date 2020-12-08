package src.game.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Deck {

    private ArrayList<CardObject> deck;
    private BufferedImage backCard;

    public Deck() {
        deck = parseDeck();
        // for (int i = 0; i < 5; i++) {
        //     Collections.shuffle(deck);
        // }
        try {
            backCard = ImageIO.read(new File("src/img/cardBack.png"));
            backCard = resizeImage(backCard, CardObject.cardWidth, CardObject.cardHeight);

        } catch (IOException e) {
            System.out.println("Error. Couldn't load back image of Deck class.");
        }
    }

    public void render(Graphics g, int[] pos) {
        if (deck.size() > 0) {
            g.drawImage(backCard, pos[0], pos[1], null);
            g.setColor(Color.white);
            g.drawString(Integer.toString(deck.size()), pos[0] + 20, pos[1] + 40);
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

    private ArrayList<CardObject> parseDeck() {
        ArrayList<CardObject> deck = new ArrayList<CardObject>();
        JSONParser parser = new JSONParser();
        JSONArray database = null;
        JSONArray deckDb = null;

        try {
            database = (JSONArray) parser.parse(new FileReader("src/game/list.json"));
            deckDb = (JSONArray) parser.parse(new FileReader("src/game/bellinideck.json"));
            deckDb = (JSONArray) deckDb.get(0);
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
            String cardType = (String) cardParsed.get("cardtype");
            ATTRIBUTE attribute = null;
            String type = null;
            int atk = 0;
            int def = 0;
            int level = 0;

            if (!cardType.equals("Trap") && !cardType.equals("Spell")) {
                atk = Integer.parseInt((String) cardParsed.get("atk"));
                def = Integer.parseInt((String) cardParsed.get("def"));
                level = Integer.parseInt((String) cardParsed.get("level"));
                attribute = ATTRIBUTE.valueOf((String) cardParsed.get("attribute"));
                type = (String) cardParsed.get("type");
            }

            if (cardType.equals("Monster")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new NormalMonster(name, attribute, index, description, image, level, atk, def, type));
                }
            } else if (cardType.equals("Effect")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new EffectMonster(name, attribute, index, description, image, level, atk, def, type));
                }
            } else if (cardType.equals("Ritual")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new RitualMonster(name, attribute, index, description, image, level, atk, def, type));
                }
            } else if (cardType.equals("Spell")) {
                if (((String) cardParsed.get("trapmagictype")).equals("Ritual")) {
                    for (int i = 0; i < (long) element.get("quantity"); i++) {
                        deck.add(new NormalSpell(name, index, description, image));
                    }
                } else if (((String) cardParsed.get("trapmagictype")).equals("Quick-Play")) {
                    for (int i = 0; i < (long) element.get("quantity"); i++) {
                        deck.add(new QuickPlaySpell(name, index, description, image));
                    }
                } else if (((String) cardParsed.get("trapmagictype")).equals("None")) {
                    for (int i = 0; i < (long) element.get("quantity"); i++) {
                        deck.add(new NormalSpell(name, index, description, image));
                    }
                }
            } else if (cardType.equals("Trap")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new Trap(name, index, description, image));
                }
            }
        }
        return deck;
    }

    public void printDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).name);
        }
    }

    public CardObject draw() {
        if (deck.size() != 0) {
            return deck.remove(0);
        } else {
            System.out.println("Hai finito il deck");
            return null;
        }
    }

    public ArrayList<CardObject> getDeck() {
        return deck;
    }
}
