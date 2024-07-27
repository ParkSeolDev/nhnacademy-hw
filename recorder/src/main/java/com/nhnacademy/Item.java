package com.nhnacademy;

public class Item {
    private String id;
    private String model;
    private int hp;
    private int attackPower;
    private int shieldPower;
    private int moveSpeed;
    private int attackSpeed;

    public Item(String id, String model, int hp, int attackPower, int shieldPower, int moveSpeed, int attackSpeed) {
        this.id = id;
        this.model = model;
        this.hp = hp;
        this.attackPower = attackPower;
        this.shieldPower = shieldPower;
        this.moveSpeed = moveSpeed;
        this.attackSpeed = attackSpeed;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getAttackPower() {
        return attackPower;
    }
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
    public int getShieldPower() {
        return shieldPower;
    }
    public void setShieldPower(int shieldPower) {
        this.shieldPower = shieldPower;
    }
    public int getMoveSpeed() {
        return moveSpeed;
    }
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    public int getAttackSpeed() {
        return attackSpeed;
    }
    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"model\"" + model+ ",\"hp\"" + hp + ",\"attackPower\"" + attackPower + ",\"shieldPower\""
                + shieldPower + ",\"moveSpeed\"" + moveSpeed + ",\"attackSpeed\"" + attackSpeed + "}";
    }
}
