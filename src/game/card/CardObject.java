package src.game.card;

public abstract class CardObject {
    protected String name;
    protected TYPE type;
    protected int id;
    protected String description;
    
    public CardObject(String name, TYPE type, int id, String description) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
    }

    public abstract void destroy(); 

    //implement getters and setters eventually
    
}
