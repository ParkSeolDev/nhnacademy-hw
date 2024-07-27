package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String nickName;
    private List<Item> itemList;
    private List<MatchHistory> matchHistoryList;
    private List<ChangeHistory> changeHistoryList;

    public User(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
        this.itemList = new ArrayList<>();
        this.matchHistoryList = new ArrayList<>();
        this.changeHistoryList = new ArrayList<>();
    }
    
    public User(String id, String nickName, List<Item> itemList) {
        this.id = id;
        this.nickName = nickName;
        this.itemList = itemList;
        this.matchHistoryList = new ArrayList<>();
        this.changeHistoryList = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public List<Item> getItemList() {
        return itemList;
    }
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
    public List<MatchHistory> getMatchHistory() {
        return matchHistoryList;
    }
    public void setMatchHistory(List<MatchHistory> matchHistoryList) {
        this.matchHistoryList = matchHistoryList;
    }
    public List<ChangeHistory> getChangeHistory() {
        return changeHistoryList;
    }
    public void setChangeHistory(List<ChangeHistory> changeHistoryList) {
        this.changeHistoryList = changeHistoryList;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"nickName\"" + nickName + ",\"itemList\"" + itemList + ",\"matchHistoryList\""
                + matchHistoryList + ",\"changeHistoryList\"" + changeHistoryList + "}";
    }
    
}
