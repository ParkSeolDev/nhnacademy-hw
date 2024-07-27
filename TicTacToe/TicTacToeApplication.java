package TicTacToe;

import TicTacToe.common.util.PointGenerator;
import TicTacToe.common.util.RandomPointGenerator;
import TicTacToe.model.Point;
import TicTacToe.model.Points;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.view.InputView;
import TicTacToe.view.ResultView;

public final class TicTacToeApplication {
    private static final int USER = 1;
    private static final int NO = 0;

    private static PointGenerator RandomPointGenerator = new RandomPointGenerator();

    public static void main(String[] args) {
        List<Integer> randomStorage = new ArrayList<>();
        Points points = Points.pointsFactory();
        String winner = "DRAW";

        while (!points.endGame()) {
            int userPoint = InputView.inputUserPoint();
            points.setPoint(userPoint, Point.USER);
            randomStorage.add(userPoint);
            ResultView.printMatrix(points);
            int winnerNumber = points.winner();
            if (winnerNumber != NO) {
                winner = (winnerNumber == USER) ? "USER" : "COM";
                break;
            }
            winnerNumber = NO;

            if (points.endGame())
                break;

            System.out.println("컴퓨터 턴");
            
            int random;
            do {
                random = RandomPointGenerator.generate();
            } while (randomStorage.contains(random));

            points.setPoint(random, Point.COM);
            randomStorage.add(random);
            ResultView.printMatrix(points);
            winnerNumber = points.winner();
            if (winnerNumber != NO) {
                winner = (winnerNumber == USER) ? "USER" : "COM";
                break;
            }
            winnerNumber = NO;
        }
        
        System.out.println();
        System.out.println("Winner : " + winner);
    }
}
