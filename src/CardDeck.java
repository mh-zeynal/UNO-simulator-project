import java.util.LinkedList;
/**
 * this class is for making 52 cards of game
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 * */
public class CardDeck {
    /**
     * @return game's card deck
     * */
    public  static LinkedList<Card> getDeck(){
        String[] cardTypes = {"A", "B", "C", "D", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] colors = {"blue", "red", "green", "black"};
        LinkedList<Card> cards = new LinkedList<>();
        for (String temp1 : cardTypes){
            for(String temp2 : colors){
                if(temp1.equals("A"))
                    cards.add(new SkipCard(11, temp2, temp1));
                else if(temp1.equals("B"))
                    cards.add(new ChangeColorCard(12, temp2, temp1));
                else if(temp1.equals("C"))
                    cards.add(new NormalCard(12,temp2 , temp1));
                else if(temp1.equals("D"))
                    cards.add(new NormalCard(13, temp2,temp1));
                else if(temp1.equals("3") || temp1.equals("4") ||
                        temp1.equals("5") || temp1.equals("6") ||
                        temp1.equals("9"))
                    cards.add(new NormalCard(Integer.parseInt(temp1), temp2, temp1));
                else if(temp1.equals("7")){
                    if(temp2.equals("black"))
                        cards.add(new RandomPick4card(15, temp2, temp1));
                    else
                        cards.add(new RandomPick2Card(10, temp2, temp1));
                }
                else if(temp1.equals("8"))
                    cards.add(new BonusPlayCard(8, temp2, temp1));
                else if(temp1.equals("10"))
                    cards.add(new ChangeDirectionCard(10, temp2, temp1));
                else if(temp1.equals("2"))
                    cards.add(new GiveAwayCard(2,temp2,temp1));
            }
        }
        return cards;
    }
}
