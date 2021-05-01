/**
 * this is a subclass of {@link ActionCard} class and is represents act of "B card" and can change
 * color of top card even it doesn't match with it
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class ChangeColorCard extends ActionCard{

    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public ChangeColorCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
