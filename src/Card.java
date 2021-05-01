/**
 * this a super class for any kind of card inside this game
 * abstract things about every card is written inside this class,
 * including type, color and score
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class Card{

    /**
     * this field represents type of card
     * types are including: A, B, C, D, 2, 3, 4, 5, 6, 7, 8, 9 and 10
     * */
    private String type;

    /**
     * this field represents color of card including: blue, red, green and black
     * */
    private String cardColor;

    /**
     * represents score of cards
     * */
    private int score;

    /**
     * ansi code of black text
     * */
    private static final String ANSI_BLACK = "\u001B[30m";

    /**
     * ansi code of red text
     * */
    private static final String ANSI_RED = "\u001B[31m";

    /**
     * ansi code of green text
     * */
    private static final String ANSI_GREEN = "\u001B[32m";


    /**
     * ansi code of blue text
     * */
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * initializes fields
     * @param score is score of cars
     * @param cardColor is color of the card
     * @param type is type of card
     * */
    public Card(int score, String cardColor, String type){
        this.score = score;
        this.cardColor = cardColor;
        this.type = type;
    }

    /**
     * @return type of card
     * */
    public String getType() {
        return type;
    }

    /**
     * @return merely returns the name of card color
     * */
    public String getCardColorName() {
        return cardColor;
    }

    /**
     * useful for printing cards
     * @return card color ansi code
     * */
    public String getColorItself(){
        String temp = "";
        if(cardColor.equals("red"))
            temp = ANSI_RED;
        else if(cardColor.equals("blue"))
            temp =  ANSI_BLUE;
        else if(cardColor.equals("black"))
            temp = ANSI_BLACK;
        else if(cardColor.equals("green"))
            temp = ANSI_GREEN;
        return temp;
    }

    /**
     * @return score of card
     * */
    public int getScore() {
        return score;
    }

    /**
     * sets a new color for {@link #cardColor}
     * @param newColor new color as string
     * */
    public void setCardColor(String newColor){
        this.cardColor = newColor;
    }

}
