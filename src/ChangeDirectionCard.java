/**
 * this is a subclass of {@link ActionCard} class and represents "card type 10" and
 * it's duty is to change the direction of the game
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)*/
public class ChangeDirectionCard extends ActionCard{

    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public ChangeDirectionCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
