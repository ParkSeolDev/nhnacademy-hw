import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import common.util.CardComparator;
import model.Card;
import model.CardUnit;
import model.Cards;
import model.Hand;
import model.Money;
import model.Player;
import model.Players;
import model.WinnerCardsArray;
import view.InputView;

public final class PockerApplication {
    private static final int INIT_CARD_COUNT = 3;
    private static Comparator<Card> CardComparator = new CardComparator();

    public static void main(String[] args) {
        int playersCount = InputView.inputPlayersCount();
        int playersSeedMoney = InputView.inputSeedMoney();

        Players players = Players.from(playersCount, playersSeedMoney);

        for (int i = 0; i < playersCount; i++) {
            List<Card> initCards = new ArrayList<>(INIT_CARD_COUNT);

            for (int j = 0; j < INIT_CARD_COUNT; j++) {
                initCards.add(Cards.getNextCard());
            }

            Hand hand = new Hand();
            players.setPlayer(i, Player.of(i, Money.from(playersSeedMoney), hand.receivedHand(initCards)));
        }

        Card maxCard = players.player(0).hand().cards().get(0);
        int maxPlayerNum = 0;
        for (int i = 1; i < playersCount; i++) {
            Card currentCard = players.player(i).hand().findMaxCard();
            if (currentCard.compare(players.player(i - 1).hand().findMaxCard()) > 0) {
                maxCard = currentCard;
                maxPlayerNum = i;
            }
        }

        System.out.println(maxPlayerNum + " 부터 시작합니다.");

        for (int i = maxPlayerNum; i < playersCount; i++) {
            Collections.sort(players.player(i).hand().receivedHandOneCard(Cards.getNextCard()).cards(),
                    CardComparator);
            int bettingMoney = InputView.inputBettingMoney(); 
            players.setPlayer(i,
                    Player.of(i, Money.of(players.player(i).money().seedMoney() - bettingMoney, bettingMoney),
                            players.player(i).hand()));
        }

        for (int i = 0; i < maxPlayerNum - 1; i++) {
            Collections.sort(players.player(i).hand().receivedHandOneCard(Cards.getNextCard()).cards(),
                    CardComparator);
            int bettingMoney = InputView.inputBettingMoney(); 
            players.setPlayer(i,
                    Player.of(i, Money.of(players.player(i).money().seedMoney() - bettingMoney, bettingMoney),
                            players.player(i).hand()));
        }

        for (int i = maxPlayerNum; i < playersCount; i++) {
            Collections.sort(players.player(i).hand().receivedHandOneCard(Cards.getNextCard()).cards(),
                    CardComparator);
            int bettingMoney = InputView.inputBettingMoney(); 
            players.setPlayer(i,
                    Player.of(i, Money.of(players.player(i).money().seedMoney() - bettingMoney, bettingMoney),
                            players.player(i).hand()));
        }
        
        for (int i = 0; i < maxPlayerNum - 1; i++) {
            Collections.sort(players.player(i).hand().receivedHandOneCard(Cards.getNextCard()).cards(),
                    CardComparator);
            int bettingMoney = InputView.inputBettingMoney();
            players.setPlayer(i,
                    Player.of(i, Money.of(players.player(i).money().seedMoney() - bettingMoney, bettingMoney),
                            players.player(i).hand()));
        }
        
        System.out.println("게임 결과");

        for (int i = 0; i < players.players().size(); i++) {
            List<Integer> playerScore = new ArrayList<>();

            players.player(i).hand().cards().forEach(c -> playerScore.add(c.card().get(0).unitNumber()));

            List<CardUnit> card = players.player(i).hand().cards().get(0).card();
            Boolean isSameShape = players.player(i).hand().cards().stream().allMatch(c -> c.card().get(1).equals(card.get(1)));

            System.out.println(playerScore);
            System.out.println(i + " : " + WinnerCardsArray.findWinnerCardArray(playerScore, isSameShape));
        }
    }
}