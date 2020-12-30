package src.game.player;

import src.game.card.CardObject;
import src.game.card.Deck;
import src.game.card.ExtraDeck;
import src.game.Graveyard;
import src.game.Hand;
import src.game.Match;
import src.game.Table;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Player {
    private Deck deck;
    private Hand hand;
    private Graveyard graveyard;
    private ExtraDeck extraDeck;
    private Table table;
    private String name;
    private BufferedImage HUDImage;


    private int lifePoints;
    private boolean canNormalSummon;

    public Player(Deck deck, Hand hand, Graveyard graveyard, ExtraDeck extraDeck, Table table) {
        this.deck = deck;
        this.hand = hand;
        this.graveyard = graveyard;
        this.extraDeck = extraDeck;
        this.table = table;
        lifePoints = 4000;
        canNormalSummon = true;
    }    

    public Player(Match match) {
        JSONParser parser = new JSONParser();
        JSONObject playerInfo = null;
        try {
            playerInfo = (JSONObject) parser.parse(new FileReader("src/game/players.json"));
        } catch (FileNotFoundException e) {
            System.out.println("JSON players file not found.");
        } catch (ParseException e) {
            System.out.println("ParseException");
        } catch (IOException e) {
            System.out.println("IOException");
        }

        playerInfo = (JSONObject) playerInfo.get("Bellini");

        name = (String) playerInfo.get("name");
        HUDImage = null;
        try {
            HUDImage = ImageIO.read(new File("src/img/" + playerInfo.get("image")));
        } catch (IOException e) {
            System.out.println("Can't load" + playerInfo.get("image"));
        }
        String databaseName = (String) playerInfo.get("deck");
        deck = new Deck(match, databaseName);
        hand = new Hand();
        graveyard = new Graveyard(match);
        extraDeck = new ExtraDeck(match, databaseName);
        table = new Table();
        lifePoints = 4000;
        canNormalSummon = true;
    }

    public void render(Graphics g) {
        hand.render(g);
        deck.render(g);
        extraDeck.render(g);
        graveyard.render(g);
    }

    public void draw() {
        CardObject cardDrawed = deck.draw();
        if (cardDrawed != null) {
            ArrayList<CardObject> cards = hand.getHand();
            cards.add(cardDrawed);

            if (cards.size() >= 5) {
                // graveyard.getGraveyard().add(cards.remove(0));
                graveyard.add(cards.remove(0));
            }

            hand.organizePositions();
        }
    }

    public ArrayList<CardObject> getDeck() {
        return deck.getDeck();
    }


    public Hand getHand() {
        return hand;
    }

    public ExtraDeck getExtraDeck() {
        return extraDeck;
    }

    public boolean getCanNormalSummon() {
        return canNormalSummon;
    }

    public void setCanNormalSummon(boolean bool) {
        canNormalSummon = bool;
    }
    
    public Graveyard getGraveyard() {
        return graveyard;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public String getName() {
        return name;
    }

    public void decreaseLifePoints(int decrease) {
        lifePoints -= decrease;
    }

    public void increaseLifePoints(int increase) {
        lifePoints += increase;
    }
    
    public BufferedImage getHUDImage() {
        return HUDImage;
    }
}
