package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int n;
    private int index = 0;
    private List<List<BingoNumber>> board;

    public Board() {
        this.n = 0;
        board = new ArrayList<>();
    }

    public void setBoard(int n) {
        this.n = n;
        board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<BingoNumber> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new BingoNumber(0));
            }
            board.add(row);
        }
    }

    public void initNumber(String numStr, int player) {
        int number = Integer.parseInt(numStr);
        int rowSize = board.size();
        int colSize = board.size();

        if (index >= rowSize * colSize) {
            return;
        }

        int row = index / colSize;
        int col = index % colSize;

        if (player == 1) {
            if (index % 2 == 0) {
                board.get(row).set(col, new BingoNumber(number));
                index++;
            }
        } else {
            if (index % 2 != 0) {
                board.get(row).set(col, new BingoNumber(number));
                index++;
            }
        }
    }

    public int size() {
        return board.size();
    }

    public List<BingoNumber> getRow(int row) {
        return this.board.get(row);
    }

    public boolean selectNumber(String numStr, int player) {
        int num = Integer.parseInt(numStr);
        BingoNumber bingoNumber = new BingoNumber(num);
        if (containsBingoNumber(bingoNumber)) {
            int[] indexArr = findIndex(bingoNumber);
            board.get(indexArr[0]).set(indexArr[1], new BingoNumber(num, player));
            return true;
        } else {
            return false;
        }
    }

    private boolean containsBingoNumber(BingoNumber bingoNumber) {
        for (int i = 0; i < board.size(); i++) {
            List<BingoNumber> row = board.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).equals(bingoNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] findIndex(BingoNumber number) {
        for (int i = 0; i < board.size(); i++) {
            List<BingoNumber> row = board.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).equals(number)) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    public int checkBingo() {
        for (int player = 1; player <= 2; player++) {
            List<BingoNumber> comparisonList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                comparisonList.clear();
                for (int j = 0; j < n; j++) {
                    comparisonList.add(board.get(i).get(j));
                }
                if (compare(comparisonList, player)) {
                    return player;
                }
            }

            for (int j = 0; j < n; j++) {
                comparisonList.clear();
                for (int i = 0; i < n; i++) {
                    comparisonList.add(board.get(i).get(j));
                }
                if (compare(comparisonList, player)) {
                    return player;
                }
            }

            comparisonList.clear();
            for (int i = 0; i < n; i++) {
                comparisonList.add(board.get(i).get(i));
            }
            if (compare(comparisonList, player)) {
                return player;
            }

            comparisonList.clear();
            for (int i = 0; i < n; i++) {
                comparisonList.add(board.get(i).get(n - i - 1));
            }
            if (compare(comparisonList, player)) {
                return player;
            }
        }
        return 0;
    }

    private boolean compare(List<BingoNumber> comparisonList, int player) {
        BingoNumber bingoNumber = comparisonList.get(0);
        if (player == 1) {
            for (int i = 1; i < comparisonList.size(); i++) {
                if (!bingoNumber.equalsPlayer1(comparisonList.get(i))) {
                    return false;
                }
                bingoNumber = comparisonList.get(i);
            }
        } else {
            for (int i = 1; i < comparisonList.size(); i++) {
                if (!bingoNumber.equalsPlayer2(comparisonList.get(i))) {
                    return false;
                }
                bingoNumber = comparisonList.get(i);
            }
        }
        return true;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<BingoNumber> row : board) {
            for (BingoNumber number : row) {
                sb.append(number).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
