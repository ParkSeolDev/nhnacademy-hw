package model;

public class Money {
    private Integer seedMoney;
    private Integer bettingMoney;

    private Money(Integer seedMoney, Integer bettingMoney) {
        this.seedMoney = seedMoney;
        this.bettingMoney = bettingMoney;
    }

    public static Money from(Integer seedMoney) {
        return new Money(seedMoney, 0);
    }

    public static Money of(Integer seedMoney, Integer bettingMoney) {
        return new Money(seedMoney, bettingMoney);
    }

    public Integer seedMoney() {
        return seedMoney;
    }
}
