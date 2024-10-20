import java.util.ArrayList;
import java.util.List;

import model.Attackable;
import model.Card;
import model.CardSymbol;
import model.Cards;
import model.Face;
import model.Hand;
import model.Player;
import model.Players;
import view.InputView;

public final class OneCardApplication {
    private static final int INIT_CARD_COUNT = 5;
    static boolean isClockwise = true;
    static int cardShape = 0;

    public static void main(String[] args) {

        int playersCount = InputView.inputPlayersCount();

        Players players = Players.from(playersCount);

        for (int i = 0; i < playersCount; i++) {
            List<Card> initCards = new ArrayList<>(INIT_CARD_COUNT);

            for (int j = 0; j < INIT_CARD_COUNT; j++) {
                initCards.add(Cards.getNextCard());
            }

            Hand hand = new Hand();
            players.setPlayer(i, Player.of(i, hand.receivedHand(initCards)));
        }

        Card topCard = Cards.getNextCard();
        cardShape = topCard.cardShape().unitNumber();
        Card tempCard = null;
        int currentPlayerIndex = 0;

        while (true) {
            Player currentPlayer = players.player(currentPlayerIndex);

            tempCard = currentPlayer.playCard(topCard.cardSymbol(), topCard.cardShape());

            if (tempCard != null) {
                topCard = tempCard;
                cardShape = topCard.cardShape().unitNumber();

                currentPlayerIndex = handleSpecialCards((CardSymbol)tempCard.card().get(0), currentPlayerIndex, players);

                if (currentPlayer.hasWon()) {
                    System.out.println(currentPlayer.playerNum() + " WIN!");
                    break;
                }
            } else {
                Card nextCard = Cards.getNextCard();
                if (nextCard != null) {
                    currentPlayer.hand().receivedHandOneCard(nextCard);
                } else {
                    System.out.println("DRAW!");
                    break;
                }
                
            }

            currentPlayerIndex = getNextPlayerIndex(currentPlayerIndex, players.size());
        }

    }
    
    private static int handleSpecialCards(CardSymbol cardSymbol, int currentPlayerIndex, Players players) {
        int nextPlayerIndex = currentPlayerIndex;
        if (cardSymbol instanceof Attackable) {
            ((Attackable) cardSymbol).attack(players.player(getNextPlayerIndex(currentPlayerIndex, players.size())));
        } else if (cardSymbol instanceof Face) {
            if (cardSymbol.checkJack()) {
                nextPlayerIndex = getNextPlayerIndex(currentPlayerIndex, players.size());
            } else if (cardSymbol.checkKing()) {
                nextPlayerIndex = getPreviousPlayerIndex(currentPlayerIndex, players.size());
            } else {
                reverseDirection();
            }
        } else if (cardSymbol.unitNumber() == 7) {
            System.out.println(cardSymbol.toString());
            cardShape = InputView.inputCardShape();
        }
        
        return nextPlayerIndex;
    }
    
    private static int getPreviousPlayerIndex(int currentPlayerIndex, int totalPlayers) {
        if (currentPlayerIndex == 0) {
            return totalPlayers - 1;
        } else {
            return currentPlayerIndex - 1;
        }
    }

    private static void reverseDirection() {
        isClockwise = !isClockwise;
    }

    private static int getNextPlayerIndex(int currentPlayerIndex, int totalPlayers) {
        if (isClockwise) {
            return (currentPlayerIndex + 1) % totalPlayers;
        } else {
            return (currentPlayerIndex - 1 + totalPlayers) % totalPlayers;
        }
    }
}