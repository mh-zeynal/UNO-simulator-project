import java.util.ArrayList;
import java.util.LinkedList;
/**
 * this class is responsible for printing cards of player and top card
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (1.5.2021)
 * */
public class PrintCards {

    /**
     * prints cards of player with details of type and color
     * @param player an object of {@link Player} class that its cards must be printed
     * */
    public static void printPlayerCards(Player player){
        ArrayList<Card> myCardsArrayList = player.getPlayerCards();
        String cardView = "";
        String ANSI_RESET = "\u001B[0m";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + "┍━━━";

        }
        cardView += "━━━━┑";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            String str = myCardsArrayList.get(i).getType();
            cardView += myCardsArrayList.get(i).getColorItself() + "│ " + str + (str.length() == 2 ? "" : " ");

        }
        cardView += "    │";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + "│   ";

        }
        cardView += "    │";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + "│   ";

        }
        cardView += "    │";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + "│   ";

        }
        cardView += "    │";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + "┕━━━";

        }
        cardView += "━━━━┙";
        cardView += "\n";
        for (int i = 0; i < player.getNumberOfPlayerCards(); i++) {
            cardView += myCardsArrayList.get(i).getColorItself() + (i + 1) + "   ";

        }
        System.out.println(cardView + ANSI_RESET);
    }

    /**
     * prints the last card of {@link GameLaunch#getCardsDeck()}
     * @param cardsDeck a linked list of game cards
     * */
    public static void printCardDeckTopCard(LinkedList<Card> cardsDeck){
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_RESET + "top card:");
        String str = cardsDeck.getLast().getType();
        System.out.println(cardsDeck.getLast().getColorItself() + "┍━━━━━━━━┑\n" +
                "│ " + str + (str.length() == 2 ? "     │\n" : "      │\n" ) +
                "│        │\n" +
                "│        │\n" +
                "│        │\n" +
                "┕━━━━━━━━┙\n" + ANSI_RESET);
    }
}
