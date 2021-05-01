/**
 * this is a subclass of {@link ActionCard} class and its duty is to skip one player
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class SkipCard extends ActionCard{
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public SkipCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }

}
