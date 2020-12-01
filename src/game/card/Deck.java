package src.game.card;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Deck {

    private ArrayList<CardObject> deck;

    public Deck() {
        this.deck = getDeck();
        printDeck(this.deck);
    }

    private ArrayList<CardObject> getDeck() {
        ArrayList<CardObject> deck = new ArrayList<CardObject>();
        JSONParser parser = new JSONParser();
        JSONArray database = null;
        JSONArray deckDb = null;

        try {
            database = (JSONArray) parser.parse(new FileReader("src/game/list.json"));
            deckDb = (JSONArray) parser.parse(new FileReader("src/game/bellinideck.json"));
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
            for (int i=0; i<database.size(); i++) {
                 cardParsed = (JSONObject) database.get(i);

                if (Integer.parseInt((String) cardParsed.get("serial")) == index) {
                    break;
                }
            }

            String name = (String) cardParsed.get("name");
            String description = (String) cardParsed.get("carddescription");
            String cardType = (String) cardParsed.get("cardtype");
            int atk = 0;
            int def = 0;
            int level = 0;
            
            if ((String)cardParsed.get("atk") != "") {
                atk = Integer.parseInt((String) cardParsed.get("atk"));
            }
            if ((String)cardParsed.get("def") != "") {
                def = Integer.parseInt((String) cardParsed.get("def"));
            }
            if ((String)cardParsed.get("level") != "") {
                level = Integer.parseInt((String) cardParsed.get("level"));
            }

            if (cardType.equals("Monster")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new NormalMonster(name, TYPE.Dark, index, description, level, atk, def));
                }
            } else if (cardType.equals("Effect")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new EffectMonster(name, TYPE.Dark, index, description, level, atk, def));
                }
            } else if (cardType.equals("Ritual")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new RitualMonster(name, TYPE.Dark, index, description, level, atk, def));
                }
            } else if (cardType.equals("Spell")) {
                if (((String) cardParsed.get("trapmagictype")).equals("Ritual")) {
                    for (int i = 0; i < (long) element.get("quantity"); i++) {
                        deck.add(new RitualSpell(name, index, description));
                    }
                } else if (((String) cardParsed.get("trapmagictype")).equals("Quick-Play")) {
                    for (int i = 0; i < (long) element.get("quantity"); i++) {
                        deck.add(new QuickPlaySpell(name, index, description));
                    }
                }
            } else if (cardType.equals("Trap")) {
                for (int i = 0; i < (long) element.get("quantity"); i++) {
                    deck.add(new Trap(name, index, description));
                }
            }
        }
        return deck;
    }

    private void printDeck(ArrayList<CardObject> deck) {
        for (int i=0; i < deck.size(); i++) {
            System.out.println(deck.get(i).name);
        }
    } 
}
