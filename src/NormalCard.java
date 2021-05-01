/**
 * this class is a subclass of {@link Card} super class
 * instances of ths class have no specific effect on game
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class NormalCard extends Card{
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public NormalCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
