import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    public static void main(String[] args) {
        PrintWriter writer = new PrintWriter(System.out);
        while (true){
            String ANSI_RESET = "\u001B[0m";
            String insideMenu = ANSI_RESET + "1.play with human\n2.play with bot\n3.exit\n";
            writer.write(insideMenu);
            writer.flush();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("1.three players\n2.four player\n3.five players");
                Scanner gameTypeScanner = new Scanner(System.in);
                int gameType = gameTypeScanner.nextInt();
                GameLaunch gameLaunch = null;
                if(gameType == 1)
                    gameLaunch = new GameLaunch(3, 1);
                else if(gameType == 2)
                    gameLaunch = new GameLaunch(4, 1);
                else if(gameType == 3)
                    gameLaunch = new GameLaunch(5, 1);
                gameLaunch.addPlayer();
                gameLaunch.distributeCards();
                gameLaunch.putNormalCardOnTop();
                int cardNumber = 0;
                while (!gameLaunch.isScoreOfPlayerZero()){
                    PrintCards.printCardDeckTopCard(gameLaunch.getCardsDeck());
                    gameLaunch.printOtherPlayersCardsNumber();
                    System.out.println(ANSI_RESET + "player" + (gameLaunch.getTurn() + 1) + ":");
                    PrintCards.printPlayerCards(gameLaunch.getPlayer());
                    if(!gameLaunch.isPickCheck()){
                        int pickOrGive = 0;
                        if(gameLaunch.isThereAnyMatchingCard()){
                            System.out.println(ANSI_RESET + "1.play your turn\n2.pick a card");
                            Scanner scanner1 = new Scanner(System.in);
                            pickOrGive = scanner1.nextInt();
                        }
                        else{
                            pickOrGive = 2;
                            System.out.println(ANSI_RESET + "you don't have any card matching with top card\nSo...");
                        }

                        if (pickOrGive == 1) {
                            do {
                                String cardCommand = ANSI_RESET + "enter the number of your card:";
                                writer.write(cardCommand);
                                writer.flush();
                                cardNumber = scanner.nextInt();
                                if (!(gameLaunch.doesTheCardMatches(cardNumber - 1)) &&
                                        !(gameLaunch.getPlayers().get(gameLaunch.getTurn()).getCardWithOutRemove(cardNumber - 1) instanceof ChangeColorCard))
                                    System.out.println(ANSI_RESET + "no match\ntry again");
                            } while (!gameLaunch.doesTheCardMatches(cardNumber - 1) &&
                                    !(gameLaunch.getPlayers().get(gameLaunch.getTurn()).getCardWithOutRemove(cardNumber - 1) instanceof ChangeColorCard));
                            gameLaunch.playTurn(cardNumber - 1);
                            if(gameLaunch.isMoveTurnLimitation()){
                                gameLaunch.setMoveTurnLimitation(false);
                            }
                        } else if (pickOrGive == 2) {
                            gameLaunch.addRandomCardToPlayer();
                            gameLaunch.moveToNextPlayer();
                            System.out.println(ANSI_RESET + "one card added to your card deck");
                            pressAnyKeyToContinue();
                        }
                    }
                    else{
                        gameLaunch.penaltyPick(gameLaunch.getTopCard());
                    }

                }
                System.out.println(ANSI_RESET + "the game is finished");
                pressAnyKeyToContinue();
                gameLaunch.printRanks();
                pressAnyKeyToContinue();
            }
            else if(choice == 2){
                System.out.println("1.three players\n2.four player\n3.five players");
                Scanner gameTypeScanner = new Scanner(System.in);
                int gameType = gameTypeScanner.nextInt();
                GameLaunch gameLaunch = null;
                if(gameType == 1)
                    gameLaunch = new GameLaunch(3, -1);
                else if(gameType == 2)
                    gameLaunch = new GameLaunch(4, -1);
                else if(gameType == 3)
                    gameLaunch = new GameLaunch(5, -1);
                gameLaunch.addPlayer();
                gameLaunch.distributeCards();
                gameLaunch.putNormalCardOnTop();
                int cardNumber = 0;
                while (!gameLaunch.isScoreOfPlayerZero()){
                    if(gameLaunch.getTurn() == 0) {
                        PrintCards.printCardDeckTopCard(gameLaunch.getCardsDeck());
                        gameLaunch.printOtherPlayersCardsNumber();
                        System.out.println(ANSI_RESET + "player" + (gameLaunch.getTurn() + 1) + ":");
                        PrintCards.printPlayerCards(gameLaunch.getPlayer());
                        if (!gameLaunch.isPickCheck()) {
                            int pickOrGive = 0;
                            if (gameLaunch.isThereAnyMatchingCard()) {
                                System.out.println(ANSI_RESET + "1.play your turn\n2.pick a card");
                                Scanner scanner1 = new Scanner(System.in);
                                pickOrGive = scanner1.nextInt();
                            } else {
                                pickOrGive = 2;
                                System.out.println(ANSI_RESET + "you don't have any card matching with top card\nSo...");
                            }

                            if (pickOrGive == 1) {
                                do {
                                    String cardCommand = ANSI_RESET + "enter the number of your card:";
                                    writer.write(cardCommand);
                                    writer.flush();
                                    cardNumber = scanner.nextInt();
                                    if (!(gameLaunch.doesTheCardMatches(cardNumber - 1)) &&
                                            !(gameLaunch.getPlayers().get(gameLaunch.getTurn()).getCardWithOutRemove(cardNumber - 1) instanceof ChangeColorCard))
                                        System.out.println(ANSI_RESET + "no match\ntry again");
                                } while (!gameLaunch.doesTheCardMatches(cardNumber - 1) &&
                                        !(gameLaunch.getPlayers().get(gameLaunch.getTurn()).getCardWithOutRemove(cardNumber - 1) instanceof ChangeColorCard));
                                gameLaunch.playTurn(cardNumber - 1);
                                if (gameLaunch.isMoveTurnLimitation()) {
                                    gameLaunch.setMoveTurnLimitation(false);
                                }
                            } else if (pickOrGive == 2) {
                                gameLaunch.addRandomCardToPlayer();
                                gameLaunch.moveToNextPlayer();
                                System.out.println(ANSI_RESET + "one card added to your card deck");
                                pressAnyKeyToContinue();
                            }
                        } else {
                            gameLaunch.penaltyPick(gameLaunch.getTopCard());
                        }
                    }
                    else{
                        PrintCards.printCardDeckTopCard(gameLaunch.getCardsDeck());
                        if(gameLaunch.isThereAnyMatchingCard()){
                            System.out.println(ANSI_RESET + "player" + (gameLaunch.getTurn() + 1) + ":");
                            gameLaunch.botPlayTurn();
                            if (gameLaunch.isMoveTurnLimitation()) {
                                System.out.println(ANSI_RESET + "player" + (gameLaunch.getTurn() + 1) + ":");
                                System.out.println("bonus turn played");
                                pressAnyKeyToContinue();
                                gameLaunch.setMoveTurnLimitation(false);
                            }
                        }
                        else{
                            PrintCards.printCardDeckTopCard(gameLaunch.getCardsDeck());
                            System.out.println(ANSI_RESET + "player" + (gameLaunch.getTurn() + 1) + ":");
                            System.out.println(ANSI_RESET + "one card added to player" + (gameLaunch.getTurn() + 1)
                            + "'s deck");
                            pressAnyKeyToContinue();
                            gameLaunch.addRandomCardToPlayer();
                            gameLaunch.moveToNextPlayer();
                        }
                    }
                }
                System.out.println(ANSI_RESET + "the game is finished");
                pressAnyKeyToContinue();
                gameLaunch.printRanks();
                pressAnyKeyToContinue();
            }
            else if (choice == 3)
                break;
        }

    }
}
