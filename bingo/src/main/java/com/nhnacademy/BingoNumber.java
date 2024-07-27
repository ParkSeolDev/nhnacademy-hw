package com.nhnacademy;

public class BingoNumber {
    private String bingoNumber;
    
    public BingoNumber(int num) {
        this.bingoNumber = num+"";
    }

    public BingoNumber(int num, int player) {
        if (player == 1) {
            this.bingoNumber = "@" + num;
        } else {
            this.bingoNumber = "#" + num;
        }
    }

    public String bingoNumber() {
        return bingoNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bingoNumber == null) ? 0 : bingoNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BingoNumber other = (BingoNumber) obj;
        if (bingoNumber == null) {
            if (other.bingoNumber != null)
                return false;
        } else if (!bingoNumber.equals(other.bingoNumber))
            return false;
        return true;
    }
    
    public boolean equalsPlayer1(BingoNumber other) {
        return (bingoNumber.startsWith("@") && other.bingoNumber.startsWith("@"));
    }

    public boolean equalsPlayer2(BingoNumber other) {
        return (bingoNumber.startsWith("#") && other.bingoNumber.startsWith("#"));
    }

    @Override
    public String toString() {
        return bingoNumber;
    }
}
