/**
 * this is a subclass of {@link ActionCard} class and is used whenever a "7 black card" is used
 * whenever an instance of this class is used, {@link GameLaunch} must motivate player to give a card to another player
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 *  */
public class GiveAwayCard extends ActionCard{
    /**
     * it just initializes {@link Card} super class fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public GiveAwayCard(int score, String cardColor, String type){
        super(score, cardColor, type);
    }
}
