package src.game.card;

public abstract class Spell extends CardObject{

    public Spell(String name, int id, String description) {
        super(name, TYPE.Spell, id, description);

    }

    public void set(){}


}
