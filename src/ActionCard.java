/**
 * this class is another subclass of {@link Card} super class,
 * but unlike {@link NormalCard} subclass, this class(mainly its subclasses) has some important effects on the game
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class ActionCard extends Card{
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public ActionCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
