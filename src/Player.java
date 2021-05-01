import java.util.ArrayList;
/**
 * this class is for saving cards of every player and having access to them
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class Player {

    /**
     * saves all cards of a player has
     * */
    private ArrayList<Card> playerCards;

    /**
     * saves whole score of player cards
     * */
    private int wholeScore;

    /**
     * initializes {@link #playerCards} arraylist and {@link #wholeScore}
     * */
    public Player(){
        playerCards = new ArrayList<>();
        wholeScore = 0;
    }

    /**
     * @return array list of player cards
     * */
    public ArrayList<Card> getPlayerCards(){
        return playerCards;
    }

    /**
     * adds a new card to player cards
     * @param card an instance of {@link Card} class
     * */
    public void addNewCard(Card card){
        playerCards.add(card);
    }

    /**
     * adds a new amount to {@link #wholeScore} or subtract a amount from it
     * @param newAmount a positive or negative amount that makes changes on {@link #wholeScore}
     * */
    public void addAmountToWholeScore(int newAmount) {
        this.wholeScore += newAmount;
    }

    /**
     * @return {@link #wholeScore} of player
     * */
    public int getWholeScore() {
        return wholeScore;
    }

    /**
     * @return a card with specific index and removes it from {@link #playerCards}
     * @param index related to a card from {@link #playerCards} array list
     * */
    public Card getCardViaIndex(int index){
        Card temp =  playerCards.get(index);
        playerCards.remove(index);
        return temp;
    }

    /**
     * it kinda updates {@link #wholeScore} amount
     * */
    public void calculateWholeScore(){
        wholeScore = 0;
        for (int i = 0; i < playerCards.size(); i++) {
            wholeScore += playerCards.get(i).getScore();
        }
    }

    /**
     * @return number of cards that a player has
     * */
    public int getNumberOfPlayerCards(){
        return playerCards.size();
    }

    /**
     * @return type of a card with specific index
     * */
    public String getTypeOfCard(int index){
        return playerCards.get(index).getType();
    }

    /**
     * @return name of a card with specific index
     * @param index related to a card from {@link #playerCards} array list
     * */
    public String getColorOfCard(int index){
        return playerCards.get(index).getCardColorName();
    }

    /**
     * it gets a card from {@link #playerCards} array list, but unlike {@link #getCardViaIndex(int)}
     * doesn't remove it from player's card deck
     * @param index related to a card from {@link #playerCards} array list
     * @return a card with specific index and removes it from {@link #playerCards}
     * */
    public Card getCardWithOutRemove(int index){
        Card temp =  playerCards.get(index);
        return temp;
    }
}
