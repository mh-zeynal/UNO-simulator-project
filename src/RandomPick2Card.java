/**
 * this is a subclass of {@link ActionCard} class and is used whenever a "7 (except black) card" is used
 * the main action of ths card is written inside {@link GameLaunch} class, {@link GameLaunch#doCardOperation(Card)} method,
 * but in general, this card gives 2 cards to next player as punishment
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class RandomPick2Card extends ActionCard{
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public RandomPick2Card(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
