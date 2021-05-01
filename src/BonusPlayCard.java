/**
 * this is a subclass of {@link ActionCard} class and is used whenever we have "8 card"
 * it gives to player another chance to play (if he has any matching card to the top card)
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class BonusPlayCard extends ActionCard {
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public BonusPlayCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
