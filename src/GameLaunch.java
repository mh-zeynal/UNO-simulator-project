import java.io.PrintWriter;
import java.util.*;
/**
 * this class manages the whole game and all essential actions are done by this class
 * @author mohammad-hossein zeynal zadeh
 * @version 1.0 (30.4.2021)
 *  */
public class GameLaunch {
    /**
     * this field represents number of players and it must be 3, 4, or 5
     * */
    private int gameMode;

    /**
     * this field represents the way game run
     * 1 for clockwise and -1 for anticlockwise
     * it can be changed by using {@link ChangeDirectionCard}
     * */
    private int turnMode;

    /**
     * this field is kinda index of {@link #players} arraylist and determines which player should play now
     * if {@link #turnMode} equals 1, everytime it advances 1 number and if {@link #turnMode} equals -1,
     * everytime it withdraws 1 number
     * method {@link #moveToNextPlayer()} has this duty
     * */
    private int turn;

    /**
     * this field determines the punishment of a player whenever his previous player gives a {@link RandomPick2Card}
     * its default amount must be zero and whenever a player uses {@link RandomPick2Card}, 1 number advances
     * and whenever player gets his random cards it will be zero again
     * */
    private int pickNumberX2;

    /**
     * this field determines the punishment of a player whenever his previous player gives a {@link RandomPick4card}
     * its default amount must be zero and whenever a player uses {@link RandomPick4card}, 1 number advances
     * and whenever player gets his random cards it will be zero again
     * */
    private int pickNumberX4;

    /**
     * this boolean is used for increasing punishment of players whenever they must receive some of
     * {@link RandomPick4card} and {@link RandomPick2Card}.
     * if a player that must get some random cards as punishment gives another 7 card, this boolean
     * does its work to give those random cards to next player
     * */
    private boolean pickCheck;

    /**
     * 1 for playing with humans and -1 for playing with bots
     * */
    private int playerMode;

    /**
     * if a player gives a {@link BonusPlayCard} and should have another chance to play,
     * this boolean does the job
     * */
    private boolean moveTurnLimitation;
    //ansi code of yellow and white color and reset color
    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_WHITE = "\u001B[37m";

    /**
     * arraylist of players
     * */
    private ArrayList<Player> players;
    /**
     * card deck that contains 52 cards as default. Cards are created inside {@link CardDeck#getDeck()}
     * */
    private LinkedList<Card> cardsDeck = CardDeck.getDeck();

    /**
     * initializes fields of class
     * @param gameMode a number between 3 and 5
     * @param playerMode 1 for playing with human and 1 for playing with bots
     * */
    public GameLaunch(int gameMode, int playerMode){
        this.turnMode = 1;
        this.gameMode = gameMode;
        this.turn = 0;
        this.pickNumberX2 = 0;
        this.pickNumberX4 = 0;
        this.pickCheck = false;
        this.playerMode = playerMode;
        this.moveTurnLimitation = false;
        players = new ArrayList<>();
    }

    /**
     * checks if there is any card that matches with top card in palyer's card deck("B card" is also accepted)
     * @return true if there is and false if there isn't
     * */
    public boolean isThereAnyMatchingCard(){
        for (int i = 0; i < players.get(turn).getPlayerCards().size(); i++) {
            if(!(players.get(turn).getPlayerCards().get(i) instanceof ChangeColorCard)){
                if(players.get(turn).getPlayerCards().get(i).getType().equals(getTopCard().getType()))
                    return true;
                else if(players.get(turn).getPlayerCards().get(i).getCardColorName().equals(getTopCard().getCardColorName()))
                    return true;
            }
            else if(players.get(turn).getPlayerCards().get(i) instanceof ChangeColorCard)
                return true;
        }
        return false;
    }

    /**
     * as role of game, there must be a {@link NormalCard} on top at the beginning of game
     * this method does the work
     * */
    public void putNormalCardOnTop(){
        for (int i = 0; i < cardsDeck.size(); i++) {
            if(cardsDeck.get(i) instanceof NormalCard){
                Card temp = cardsDeck.get(i);
                cardsDeck.remove(i);
                cardsDeck.add(temp);
            }
        }
    }

    /**
     * adds how many players that was added to {@link #gameMode}
     * */
    public void addPlayer(){
        for (int i = 1; i<= gameMode; i++)
            players.add(new Player());
    }

    /**
     * checks if the player that must have been punished had put any "7 card" or not
     * */
    public boolean isPickCheck(){
        return pickCheck;
    }

    /**
     * checks if the card player selected matches with top card or not
     * @param index uses {@link Player#getCardWithOutRemove(int)} to return a card
     * @return true if it does and false if it doesn't match
     * */
    public boolean doesTheCardMatches(int index){
        if((players.get(turn).getColorOfCard(index).equals(cardsDeck.getLast().getCardColorName())) ||
                (players.get(turn).getTypeOfCard(index).equals(cardsDeck.getLast().getType())))
                return true;
        else if(players.get(turn).getCardWithOutRemove(index) instanceof ChangeColorCard)
            return true;
        return false;
    }

    /**
     * this method is used at the beginning of game to give 7 random cards to each player from {@link #cardsDeck}
     * */
    public void distributeCards(){
        int counter1 = 0;
        int counter2 = 1;
        Random random = new Random();
        while (counter1 < gameMode){
            counter2 = 1;
            while (counter2 <= 7){
                int temp = random.nextInt(cardsDeck.size());
                players.get(counter1).addNewCard(cardsDeck.get(temp));
                players.get(counter1).addAmountToWholeScore(cardsDeck.get(temp).getScore());
                cardsDeck.remove(cardsDeck.get(temp));
                counter2++;
            }
            players.get(counter1).calculateWholeScore();
            counter1++;
        }
    }

    /**
     * checks if player has used {@link BonusPlayCard} or not
     * @return true if he has and false if he hasn't
     * */
    public boolean isMoveTurnLimitation() {
        return moveTurnLimitation;
    }

    /**
     * @return arraylist of {@link #players}
     * */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * lets player to play his turn
     * @param cardIndex index of a card from player's card deck
     * */
    public void playTurn(int cardIndex){
        moveTurnLimitation = false;
        Card temp1 = players.get(turn).getCardWithOutRemove(cardIndex);
        if(temp1 instanceof ChangeColorCard){
            Card temp = players.get(turn).getCardViaIndex(cardIndex);
            players.get(turn).addAmountToWholeScore(-1 * temp.getScore());
            addCard(temp);
            doCardOperation(temp);
        }
        else{
            if(doesTheCardMatches(cardIndex)){
                Card temp = players.get(turn).getCardViaIndex(cardIndex);
                players.get(turn).addAmountToWholeScore(-1 * temp.getScore());
                addCard(temp);
                doCardOperation(temp);
            }
            else{
                if((turn == 0 && playerMode == -1) || playerMode == 1){
                    System.out.println("no match");
                    Main.pressAnyKeyToContinue();
                }
                players.get(turn).addNewCard(pickRandomCard());
            }
        }

    }

    /**
     * it's used to let bot play its turn. It's as same as {@link #playTurn(int)}. Their difference is
     * this method picks a random index, but it still must obey the roles of matching with top card
     * */
    public void botPlayTurn(){
        Random random = new Random();
        int randomIndex = 0;
        while (true){
            randomIndex = random.nextInt(players.get(turn).getNumberOfPlayerCards());
            if(doesTheCardMatches(randomIndex)){
                playTurn(randomIndex);
                break;
            }
        }

    }

    /**
     * checks if player has {@link RandomPick2Card} or {@link RandomPick4card} or not
     * @return true if he has and false if he hasn't
     * */
    public boolean isThereAnyMatchWithPunishmentCard(){
        for (int i = 0; i < players.get(turn).getPlayerCards().size(); i++) {
            if((players.get(turn).getPlayerCards().get(i) instanceof RandomPick2Card) ||
                    (players.get(turn).getPlayerCards().get(i) instanceof RandomPick4card)){
                return true;
            }
        }
        return false;
    }

    /**
     * whenever we use {@link RandomPick4card} or {@link RandomPick2Card} this method is used
     * and gives a mixture of these two cards to next player or let him to give a "7 card"
     * */
    public void penaltyPick(Card card){
        Random randomChoice = new Random();
        if(card instanceof RandomPick2Card){
            pickNumberX2 += 1;
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            if(pickNumberX2 < 4){
                if(isThereAnyMatchWithPunishmentCard()){
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.println(ANSI_RESET + "1.pick " + (((pickNumberX2) * 2) + (pickNumberX4 * 4)) +
                                " random cards\n" +
                                "2.give a card with number 7");
                        choice = scanner.nextInt();
                    }
                    else
                        choice = 2;
                }
                else{
                    if((turn == 0 && playerMode == -1) || playerMode == 1)
                        System.out.println(ANSI_RESET + "you don't have any card matching with top card\nSo...");
                    choice = 1;
                }
                if(choice == 1){
                    for (int i = 1; i <= ((pickNumberX2 * 2) + (pickNumberX4 * 4)); i++) {
                        players.get(turn).addNewCard(pickRandomCard());
                    }
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.println(ANSI_RESET + ((pickNumberX2 * 2) + (pickNumberX4 * 4)) +
                                " random cards have been added to your deck");
                        Main.pressAnyKeyToContinue();
                    }
                    else{
                        System.out.println(ANSI_RESET + ((pickNumberX2 * 2) + (pickNumberX4 * 4)) +
                                " random cards have been added to player" + (turn + 1) + "'s deck");
                        Main.pressAnyKeyToContinue();
                    }
                    pickNumberX2 = 0;
                    pickNumberX4 = 0;
                    pickCheck = false;
                }
                else {
                    int enterChoice = 0;
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.print(ANSI_RESET + "enter the number of card 7:");
                        enterChoice = scanner.nextInt();
                    }
                    else{
                        while (true){
                            enterChoice = randomChoice.nextInt(players.get(turn).getNumberOfPlayerCards());
                            if (players.get(turn).getCardWithOutRemove(enterChoice) instanceof RandomPick4card ||
                                    players.get(turn).getCardWithOutRemove(enterChoice) instanceof RandomPick2Card)
                                break;
                        }
                    }
                    addCard(players.get(turn).getCardViaIndex(enterChoice - 1));
                    pickCheck = true;
                    moveToNextPlayer();
                }
            }
        }
        else if(card instanceof RandomPick4card){
            pickNumberX4 += 1;
            if(pickNumberX4 < 4){
                Scanner scanner = new Scanner(System.in);
                int choice = 0;
                if(isThereAnyMatchWithPunishmentCard()){
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.println(ANSI_RESET + "1.pick " + ((pickNumberX2 * 2) + (pickNumberX4 * 4)) +
                                " random cards\n" +
                                "2.give a card with number 7");
                        choice = scanner.nextInt();
                    }
                    else
                        choice = 2;
                }
                else{
                    if((turn == 0 && playerMode == -1) || playerMode == 1)
                        System.out.println(ANSI_RESET + "you don't have any card matching with top card\nSo...");
                    choice = 1;
                }
                if(choice == 1){
                    for (int i = 1; i <= ((pickNumberX2 * 2) + (pickNumberX4 * 4)); i++) {
                        players.get(turn).addNewCard(pickRandomCard());
                    }
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.println(ANSI_RESET + ((pickNumberX2 * 2) + (pickNumberX4 * 4)) +
                                " random cards have been added to your deck");
                        Main.pressAnyKeyToContinue();
                    }
                    else{
                        System.out.println(ANSI_RESET + ((pickNumberX2 * 2) + (pickNumberX4 * 4)) +
                                " random cards have been added to player" + (turn + 1) + "'s deck");
                        Main.pressAnyKeyToContinue();
                    }
                    pickNumberX2 = 0;
                    pickNumberX4 = 0;
                    pickCheck = false;
                }
                else {
                    int enterChoice = 0;
                    if((turn == 0 && playerMode == -1) || playerMode == 1){
                        System.out.print(ANSI_RESET + "enter the number of card 7:");
                        enterChoice = scanner.nextInt();
                    }
                    else{
                        while (true){
                            enterChoice = randomChoice.nextInt(players.get(turn).getNumberOfPlayerCards());
                            if (players.get(turn).getCardWithOutRemove(enterChoice) instanceof RandomPick4card ||
                                    players.get(turn).getCardWithOutRemove(enterChoice) instanceof RandomPick2Card){
                                enterChoice += 1;
                                break;
                            }
                        }
                    }
                    addCard(players.get(turn).getCardViaIndex(enterChoice - 1));
                    pickCheck = true;
                    moveToNextPlayer();
                }
            }
        }
    }

    /**
     * @return the card before top card
     * */
    public Card pickCard(){
        int index = cardsDeck.size() - 2;
        Card temp = cardsDeck.get(index);
        cardsDeck.remove(index);
        return temp;
    }

    /**
     * @return a random card from {@link #cardsDeck}
     * */
    public Card pickRandomCard(){
        Random random = new Random();
        int randomNumber = random.nextInt(cardsDeck.size() - 1);
        Card temp = cardsDeck.get(randomNumber);
        cardsDeck.remove(randomNumber);
        return temp;
    }

    /**
     * adds a random card to player from {@link #cardsDeck}
     * */
    public void addRandomCardToPlayer(){
        players.get(turn).addNewCard(pickRandomCard());
    }

    /**
     * adds a card to  {@link #cardsDeck}
     * @param card an intended that is given from onr of players
     * */
    public void addCard(Card card){
        cardsDeck.add(card);
    }

    /**
     * sets the amount of player's {@link #turn}
     * @param turn a new amount for game turn
     * */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * gives 1 number to {@link #turn} if {@link #turnMode} is 1
     * and gets 1 number from it if {@link #turnMode} is -1
     * */
    public void moveToNextPlayer(){
        if(turnMode == 1){
            turn += 1;
            if(turn == gameMode)
                turn = 0;
        }
        else{
            turn -= 1;
            if(turn == -1)
                turn = gameMode - 1;
        }
    }

    /**
     * @return turn of player
     * */
    public int getTurn() {
        return turn;
    }

    /**
     * @return last card of {@link #cardsDeck}
     * */
    public Card getTopCard(){
        return cardsDeck.getLast();
    }

    /**
     * this method is used whenever we use a {@link SkipCard} and skips one player
     * */
    public void skipPlayer(){
        if(turnMode == 1){
            if(turn + 2 == gameMode)
                setTurn(0);
            else if(turn == gameMode - 1)
                setTurn(1);
            else{
                setTurn(getTurn() + 2);
            }
        }
        else{
            if(turn - 2 == -1)
                setTurn(gameMode - 1);
            else if(turn == 0)
                setTurn(gameMode - 2);
            else{
                setTurn(getTurn() - 2);
            }
        }
    }

    /**
     * gives a card to a specific player
     * @param cardIndex index of a card
     * @param playerIndex index of player that the card must be given to
     * */
    public void giveCardAway(int playerIndex, int cardIndex){
        Card temp = players.get(turn).getCardViaIndex(cardIndex);
        players.get(playerIndex).addNewCard(temp);
    }

    /**
     * sets amount of {@link #moveTurnLimitation}
     * */
    public void setMoveTurnLimitation(boolean moveTurnLimitation) {
        this.moveTurnLimitation = moveTurnLimitation;
    }

    /**
     * checks if player has {@link BonusPlayCard} or not
     * @return if he does and false if he doesn't
     * */
    public boolean doesContainBonusCard(){
        for (int i = 0; i < players.get(getTurn()).getNumberOfPlayerCards(); i++) {
            if(players.get(getTurn()).getCardWithOutRemove(i) instanceof BonusPlayCard)
                return true;
        }
        return false;
    }

    /**
     * it does specific actions that every {@link ActionCard} has using {@link #giveCardAway(int, int)},
     *  {@link #skipPlayer()} and {@link #penaltyPick(Card)}.Some other operations are done inside the method
     *  @param card the card that player gives
     *  */
    public void doCardOperation(Card card){
        if(card instanceof SkipCard){
            players.get(turn).calculateWholeScore();
            skipPlayer();
            System.out.println("the turn skipped to player" + (getTurn() + 1));
            Main.pressAnyKeyToContinue();
        }
        else if(card instanceof ChangeDirectionCard){
            players.get(turn).calculateWholeScore();
            turnMode *= -1;
            System.out.println(ANSI_RESET + "reversed -> it's " +
                    (turnMode== 1 ? "clockwise " : "anti clockwise ") + "now");
            Main.pressAnyKeyToContinue();
            moveToNextPlayer();
        }
        else if(card instanceof NormalCard){
            players.get(turn).calculateWholeScore();
            if((turn == 0 && playerMode == -1) || playerMode == 1){
                System.out.println(ANSI_RESET + "you've done your role");
                Main.pressAnyKeyToContinue();
            }
            else{
                System.out.println(ANSI_RESET + "player" + (turn + 1) + " has done his role");
                Main.pressAnyKeyToContinue();
            }
            moveToNextPlayer();
        }
        else if(card instanceof RandomPick4card){
            moveToNextPlayer();
            if((turn == 0 && playerMode == -1) || playerMode == 1){
                System.out.println(ANSI_RESET + "player" + (getTurn() + 1) + ":");
                PrintCards.printCardDeckTopCard(getCardsDeck());
                PrintCards.printPlayerCards(getPlayer());
            }
            penaltyPick(card);
        }
        else if(card instanceof RandomPick2Card){
            moveToNextPlayer();
            if((turn == 0 && playerMode == -1) || playerMode == 1){
                System.out.println(ANSI_RESET + "player" + (getTurn() + 1) + ":");
                PrintCards.printCardDeckTopCard(getCardsDeck());
                PrintCards.printPlayerCards(getPlayer());
            }
            else
                System.out.println(ANSI_RESET + "player" + (getTurn() + 1) + ":");

            penaltyPick(card);
        }
        else if(card instanceof BonusPlayCard){
            System.out.println(ANSI_RESET + "bonus:");
            String str = "";
            Scanner scanner = new Scanner(System.in);
            if(isThereAnyMatchingCard()){
                if((turn == 0 && playerMode == -1) || playerMode == 1){
                    PrintCards.printCardDeckTopCard(getCardsDeck());
                    PrintCards.printPlayerCards(getPlayer());
                    System.out.println(ANSI_RESET + "wanna have another choice: [y\\n]");
                    str = scanner.next();
                }
                else{
                    if(!doesContainBonusCard())
                        str = "y";
                }

            }
            else
                str = "n";

            if(str.equals("y")){
                int choice = 0;
                if((turn == 0 && playerMode == -1) || playerMode == 1){
                    PrintCards.printCardDeckTopCard(getCardsDeck());
                    PrintCards.printPlayerCards(getPlayer());
                    System.out.print(ANSI_RESET + "choose a card:");
                    choice = scanner.nextInt();
                }
                else{
                    Random random = new Random();
                    choice = random.nextInt(players.get(getTurn()).getNumberOfPlayerCards());
                    choice += 1;
                }
                playTurn(choice - 1);
                setMoveTurnLimitation(true);
            }
            else{
                if((turn == 0 && playerMode == -1) || playerMode == 1){
                    System.out.println(ANSI_RESET + "there was no matching card. So," +
                            "one card has been added to your card deck");
                    Main.pressAnyKeyToContinue();
                }
                else{
                    System.out.println(ANSI_RESET + "there was no matching card. So," +
                            "one card has been added to player" + (getTurn() + 1) +  "'s card deck");
                    Main.pressAnyKeyToContinue();
                }
                players.get(turn).addNewCard(pickRandomCard());
                moveToNextPlayer();
            }
        }
        else if(card instanceof GiveAwayCard){
            int playerChoice = 0;
            Random randomized = new Random();
            if((turn == 0 && playerMode == -1) || playerMode == 1){
                System.out.println(ANSI_RESET + "choose one of players to give a card to him(except yourself)");
                String playersStatus = "";
                for (int i = 0; i < players.size(); i++) {
                    playersStatus += (i + 1 == (turn + 1) ? ANSI_WHITE + (i + 1) : ANSI_YELLOW + (i + 1)) + "\t";
                }
                while (true){
                    System.out.println(playersStatus + "\n" + ANSI_RESET + "your choice:");
                    Scanner scanner = new Scanner(System.in);
                    playerChoice = scanner.nextInt();
                    if(playerChoice != turn + 1)
                        break;
                    else {
                        System.out.println("you can't choose yourself\ntry again");
                    }
                }
            }
            else{
                playerChoice = findPlayerWithLeastCards() + 1;
            }
            int cardChoice = randomized.nextInt(players.get(turn).getNumberOfPlayerCards());
            giveCardAway(playerChoice - 1, cardChoice);
            players.get(turn).calculateWholeScore();
            players.get(playerChoice - 1).calculateWholeScore();
            System.out.println("one card was gaven from player" + (turn + 1) + " to player" + playerChoice);
            Main.pressAnyKeyToContinue();
            moveToNextPlayer();
        }
        else if(card instanceof ChangeColorCard){
            int colorChoice = 0;
            if((turn == 0 && playerMode == -1) || playerMode == 1){
                String colorCommand = "choose color:\n1.blue\n2.red\n3.green\n4.black\n";
                PrintWriter printWriter = new PrintWriter(System.out);
                printWriter.write(colorCommand);
                Scanner scanner = new Scanner(System.in);
                printWriter.flush();
                colorChoice = scanner.nextInt();
            }
            else{
                Random random = new Random();
                colorChoice = random.nextInt(4);
                colorChoice += 1;
            }
            if(colorChoice == 1)
                card.setCardColor("blue");
            else if(colorChoice == 2)
                card.setCardColor("red");
            else if(colorChoice == 3)
                card.setCardColor("green");
            else
                card.setCardColor("black");
            moveToNextPlayer();
            System.out.println(ANSI_RESET + "the color of top card has been changed to " + card.getCardColorName());
            Main.pressAnyKeyToContinue();
        }
    }

    /**
     * used bubble sort in this method<a href="https://www.geeksforgeeks.org/bubble-sort/">bubble list URL reference</a>
     * @return index of player that has the least cards
     * */
    public int findPlayerWithLeastCards(){
        int n = players.size();
        int[] numberOfCards = new int[gameMode];
        int[] indexes = new int[gameMode];
        for (int i = 0; i < players.size(); i++) {
            indexes[i] = i;
            numberOfCards[i] = players.get(i).getNumberOfPlayerCards();
        }
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (numberOfCards[j] > numberOfCards[j+1])
                {
                    int temp1 = numberOfCards[j];
                    int temp2 = indexes[j];
                    numberOfCards[j] = numberOfCards[j+1];
                    indexes[j] = indexes[j+1];
                    numberOfCards[j+1] = temp1;
                    indexes[j+1] = temp2;
                }
        if(indexes[0] != turn)
            return indexes[0];
        return indexes[1];
    }
    /**
     * this method is condition of finishing game
     * if cards of a player finishes, this will inform us and the game finishes
     * @return true if one of players has no card
     * */
    public boolean isScoreOfPlayerZero(){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getWholeScore() == 0)
                return true;
        }
        return false;
    }

    /**
     * @return player whose term it is
     * */
    public Player getPlayer(){
        return players.get(turn);
    }

    /**
     * this method uses bubble sort to show rank of players
     * used bubble sort in this method<a href="https://www.geeksforgeeks.org/bubble-sort/">bubble list URL reference</a>
     * rank #1 has the lowest card score
     * */
    public void printRanks(){
        int[] ranks = new int[gameMode];
        int[] temporaryArray = new int[gameMode];
        for (int i = 0; i < gameMode; i++) {
            temporaryArray[i] = players.get(i).getWholeScore();
            ranks[i] = i;
        }
        int n = temporaryArray.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (temporaryArray[j] > temporaryArray[j+1])
                {
                    // swap temporaryArray[j+1] and temporaryArray[j]
                    int temp2 = ranks[j];
                    int temp1 = temporaryArray[j];
                    ranks[j] = ranks[j+1];
                    temporaryArray[j] = temporaryArray[j+1];
                    ranks[j+1] = temp2;
                    temporaryArray[j+1] = temp1;
                }
        for (int i = 0; i < temporaryArray.length; i++){
            System.out.println((i+1) + ")" + "player" + (ranks[i] + 1) +(ranks[i] == 0 && playerMode == -1 ? "(you)" : "") +
                    ":" + players.get(ranks[i]).getWholeScore() +
                    (players.get(i).getWholeScore() == 0 ? " (won)" : ""));
        }

    }

    /**
     * @return game's {@link #cardsDeck}
     * */
    public LinkedList<Card> getCardsDeck(){
        return cardsDeck;
    }

    /**
     * prints number of cards of players except the person who is playing
     * */
    public void printOtherPlayersCardsNumber(){
        System.out.println(ANSI_RESET + "status:");
        for (int i = 0; i < players.size(); i++) {
            if(i != getTurn()){
                System.out.println("player" + (i + 1) + " has " + players.get(i).getNumberOfPlayerCards() + " cards");
            }
        }
    }
}
