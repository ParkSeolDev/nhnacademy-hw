package com.nhnacademy;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Game {
    // public static void main(String[] args) {

    //     Console console = System.console();

    //     while (true) {
    //         System.out.print("command : ");
    //         List<User> users = initUsers();
    //         List<Item> items = initItems();
        
    //         String line = console.readLine();
    //         if (line.equals("quit")) {
    //             break;
    //         }

    //         String[] strArr = line.split(" ");
    //         if (strArr[0].equals("user")) {
    //             if (!createUser(strArr))
    //                 continue;
    //             if (!deleteUser(strArr, users))
    //                 continue;
    //             userList(strArr);
    //         }

    //         if (strArr[0].equals("item")) {
    //             if (!createItem(strArr))
    //                 continue;
    //             if (!deleteItem(strArr))
    //                 continue;
    //             itemList(strArr);
    //         }

    //         if (strArr[0].equals("equip")) {
    //             if (strArr.length != 3) {
    //                 System.out.println("커맨드 오류! 다시 입력해주세요.");
    //                 continue;
    //             }
    //             users = equip(strArr, users, items);
    //         }
    //     }
    // }

    public List<User> initUsers() {
        JSONObject allFile = new JSONObject(readAllFile());
        List<User> users = new ArrayList<>();
        JSONArray userArray = allFile.getJSONArray("users");
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject userObject = userArray.getJSONObject(i);
            HashMap<String, Object> userMap = parseJsonObject(userObject.toString());

            JSONArray itemListArray = userObject.getJSONArray("itemList");
            List<Item> itemList = new ArrayList<>();
            if (itemListArray.length() != 0) {
                for (int j = 0; j < itemListArray.length(); j++) {
                    JSONObject itemObject = itemListArray.getJSONObject(j);
                    itemList.add(new Item(itemObject.getString("id"),
                                        itemObject.getString("model"),
                                        itemObject.getInt("hp"),
                                        itemObject.getInt("attackPower"),
                                        itemObject.getInt("shieldPower"),
                                        itemObject.getInt("moveSpeed"),
                                        itemObject.getInt("attackSpeed")));
                }
            }

            users.add(new User(userMap.get("id").toString(), userMap.get("nickName").toString(), itemList));
        }
        
            return users;

    }
    
    public List<Item> initItems() {
        JSONObject allFile = new JSONObject(readAllFile());
        List<Item> list = new ArrayList<>();
        JSONArray itemArray = allFile.getJSONArray("items");
        for (int j = 0; j < itemArray.length(); j++) {
            JSONObject itemObject = itemArray.getJSONObject(j);
            HashMap<String, Object> itemMap = parseJsonObject(itemObject.toString());
            list.add(new Item(itemMap.get("id").toString(), itemMap.get("model").toString(),
                    Integer.parseInt(itemMap.get("hp").toString()),
                    Integer.parseInt(itemMap.get("attackPower").toString()),
                    Integer.parseInt(itemMap.get("shieldPower").toString()),
                    Integer.parseInt(itemMap.get("moveSpeed").toString()),
                    Integer.parseInt(itemMap.get("attackSpeed").toString())));
        }
        return list;
    }

    public List<User> equip(String[] strArr, List<User> users, List<Item> items) {
        JSONObject allFile = new JSONObject(readAllFile());
        JSONArray userArray = allFile.getJSONArray("users");
        JSONObject userObject = userArray.getJSONObject(getIndex(userArray, strArr[1]));

        HashMap<String, Object> userMap = parseJsonObject(userObject.toString());

        JSONArray itemArray = allFile.getJSONArray("items");
        JSONObject itemObject = itemArray.getJSONObject(getIndex(itemArray, strArr[2]));

        HashMap<String, Object> itemMap = parseJsonObject(itemObject.toString());
        
        List<Item> list = new ArrayList<>();
        
        JSONArray itemListArray = userObject.getJSONArray("itemList");
        List<Item> itemList = new ArrayList<>();
            if (itemListArray.length() != 0) {
                for (int j = 0; j < itemListArray.length(); j++) {
                    JSONObject item = itemListArray.getJSONObject(j);
                    itemList.add(new Item(item.getString("id"),
                                        item.getString("model"),
                                        item.getInt("hp"),
                                        item.getInt("attackPower"),
                                        item.getInt("shieldPower"),
                                        item.getInt("moveSpeed"),
                                        item.getInt("attackSpeed")));
                }
            }
        list.addAll(itemList);

        list.add(new Item(itemMap.get("id").toString(), itemMap.get("model").toString(), Integer.parseInt(itemMap.get("hp").toString()), Integer.parseInt(itemMap.get("attackPower").toString()), 
        Integer.parseInt(itemMap.get("shieldPower").toString()), Integer.parseInt(itemMap.get("moveSpeed").toString()), Integer.parseInt(itemMap.get("attackSpeed").toString())));
        
        String[] createUserArr = new String[] {"user", "add", strArr[1], userMap.get("nickName").toString()};

        List<User> newUsers = createEquipedUser(createUserArr, users, list);

        JSONObject newFile = new JSONObject();
        newFile.put("items", readFile("items"));
            
        newFile.put("users", newUsers);

            
        try (FileWriter filewWriter = new FileWriter(
                    "./recorder.json")) {
            filewWriter.write(newFile.toString());
            filewWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUsers;
    }

    private List<User> createEquipedUser(String[] strArr, List<User> users, List<Item> items) {
        JSONArray jsonArray = readFile("users");
        User user = new User(strArr[2], strArr[3], items);
        JSONObject userObject = new JSONObject(user);

        JSONObject allFile = new JSONObject(readAllFile());
        JSONArray userArray = allFile.getJSONArray("users");
        userArray.put(userObject);

        User oldUser = users.get(getIndex(userArray, strArr[2]));
        users.remove(oldUser);
        users.add(user);

        JSONObject newFile = new JSONObject();
        newFile.put("items", readFile("items"));
        
        newFile.put("users", users);


        try (FileWriter fileWriter = new FileWriter(
                    "./recorder.json")) {
            jsonArray.put(userObject);
            fileWriter.write(newFile.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public HashMap<String, Object> parseJsonObject(String jsonString) {
        HashMap<String, Object> map = new HashMap<>();
        jsonString = jsonString.substring(1, jsonString.length() - 1); // remove curly braces
        String[] keyValuePairs = jsonString.split(",");
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            String key = entry[0].trim().replaceAll("\"", ""); // remove quotes and trim spaces
            String value = entry[1].trim();
            map.put(key, parseValue(value));
        }
        return map;
    }

    public Object parseValue(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1); // remove quotes
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.parseBoolean(value);
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return value;
            }
        }
    }

    private String init() {
        return "{\"items\": [],\"users\": []}";
    }
    
    public void userList(String[] strArr) {
        if (strArr[1].equals("list")) {
            String text = readFile("users").toString();
            text = text.substring(1, text.length() - 1);
            System.out.println(text);
        }
    }

    public boolean createUser(String[] strArr) {
        if (strArr[1].equals("add")) {
            if (strArr.length != 4) {
                System.out.println("커맨드 오류! 다시 입력해주세요.");
                return false;
            }
            JSONArray jsonArray = readFile("users");
            User user = new User(strArr[2], strArr[3]);
            JSONObject userObject = new JSONObject(user);

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray userArray = allFile.getJSONArray("users");
            userArray.put(userObject);

            JSONObject newFile = new JSONObject();
            newFile.put("items", readFile("items"));

            newFile.put("users", userArray);

            try (FileWriter fileWriter = new FileWriter(
                    "./recorder.json")) {
                jsonArray.put(userObject);
                fileWriter.write(newFile.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    public String deleteUsers() {
        JSONObject allFile = new JSONObject(readAllFile());
        allFile.remove("users");
        return allFile.toString();
    }

    public boolean deleteUser(String[] strArr, List<User> users) {
        if (strArr[1].equals("del")) {
            if (strArr.length != 3) {
                System.out.println("커맨드 오류! 다시 입력해주세요.");
                return false;
            }

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray userArray = allFile.getJSONArray("users");
            // userArray.remove(getIndex(userArray, strArr[2]));

            users.remove(getIndex(userArray, strArr[2]));
            
            JSONObject newFile = new JSONObject();
            newFile.put("items", readFile("items"));
            
            newFile.put("users", users);

            
            try (FileWriter filewWriter = new FileWriter(
                    "./recorder.json")) {
                filewWriter.write(newFile.toString());
                filewWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    
    private int getIndex(JSONArray jsonArray, String id) {
        int index = 0;
        if (jsonArray.toString().contains(id)) {
            String[] jsonObjectArr = jsonArray.toString().split("}");
            while (!jsonObjectArr[index].contains(id))
                index++;
        } else {
            return -1;
        }
        return index;
    }

    private String readAllFile() {
        try {
            String all = new String(Files.readAllBytes(Paths.get(
                    "./recorder.json")));
            return all.equals("") ? init() : all;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray readFile(String devision) {
        JSONObject jsonObject = new JSONObject(readAllFile());
        JSONArray jsonArray = jsonObject.getJSONArray(devision);

        if (jsonArray == null) {
            jsonArray.put(new JSONObject());
        }
        return jsonArray;

    }
    
    public boolean createItem(String[] strArr) {
        if (strArr[1].equals("add")) {
            if (strArr.length != 9) {
                System.out.println(strArr.length);
                System.out.println("커맨드 오류! 다시 입력해주세요.");
                return false;
            }
            JSONArray jsonArray = readFile("items");
            Item user = new Item(strArr[2], strArr[3], Integer.parseInt(strArr[4]), Integer.parseInt(strArr[5]), Integer.parseInt(strArr[6]), Integer.parseInt(strArr[7]), Integer.parseInt(strArr[8]));
            JSONObject itemObject = new JSONObject(user);

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray itemArray = allFile.getJSONArray("items");
            itemArray.put(itemObject);

            JSONObject newFile = new JSONObject();
            newFile.put("users", readFile("users"));
            
            newFile.put("items", itemArray);


            try (FileWriter fileWriter = new FileWriter(
                    "./recorder.json")) {
                jsonArray.put(itemObject);
                fileWriter.write(newFile.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteItem(String[] strArr) {
        if (strArr[1].equals("del")) {
            if (strArr.length != 3) {
                System.out.println("커맨드 오류! 다시 입력해주세요.");
                return false;
            }

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray itemArray = allFile.getJSONArray("items");
            itemArray.remove(getIndex(itemArray, strArr[2]));

            JSONObject newFile = new JSONObject();
            newFile.put("users", readFile("users"));
            newFile.put("items", itemArray);

            try (FileWriter filewWriter = new FileWriter(
                    "./recorder.json")) {
                filewWriter.write(newFile.toString());
                filewWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void itemList(String[] strArr) {
        if (strArr[1].equals("list")) {
            String text = readFile("items").toString();
            text = text.substring(1, text.length() - 1);
            System.out.println(text);
        }
    }
//////////////////
    public void createUser(String optionValue, String optionValue2) {
        
            JSONArray jsonArray = readFile("users");
            User user = new User(optionValue, optionValue2);
            JSONObject userObject = new JSONObject(user);

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray userArray = allFile.getJSONArray("users");
            userArray.put(userObject);

            JSONObject newFile = new JSONObject();
            newFile.put("items", readFile("items"));

            newFile.put("users", userArray);

            try (FileWriter fileWriter = new FileWriter(
                    "./recorder.json")) {
                jsonArray.put(userObject);
                fileWriter.write(newFile.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void createItem(String optionValue, String optionValue2, String optionValue3, String optionValue4,
            String optionValue5, String optionValue6, String optionValue7) {
        JSONArray jsonArray = readFile("items");
            Item item = new Item(optionValue, optionValue2, Integer.parseInt(optionValue3), Integer.parseInt(optionValue4), Integer.parseInt(optionValue5), Integer.parseInt(optionValue6), Integer.parseInt(optionValue7));
            JSONObject itemObject = new JSONObject(item);

            JSONObject allFile = new JSONObject(readAllFile());
            JSONArray itemArray = allFile.getJSONArray("items");
            itemArray.put(itemObject);

            JSONObject newFile = new JSONObject();
            newFile.put("users", readFile("users"));
            
            newFile.put("items", itemArray);


            try (FileWriter fileWriter = new FileWriter(
                    "./recorder.json")) {
                jsonArray.put(itemObject);
                fileWriter.write(newFile.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void printUserDetail() {
        String text = readFile("users").toString();
        text = text.substring(1, text.length() - 1);
        System.out.println(text);
    }

    public void printUsers() {
        String text = readFile("users").toString();
        text = text.substring(1, text.length() - 1);
        System.out.println(text);
    }

    public void printItems() {
        String text = readFile("items").toString();
        text = text.substring(1, text.length() - 1);
        System.out.println(text);
    }

    public List<User> equip(String optionValue, String optionValue2, List<User> users, List<Item> items) {
        JSONObject allFile = new JSONObject(readAllFile());
        JSONArray userArray = allFile.getJSONArray("users");
        JSONObject userObject = userArray.getJSONObject(getIndex(userArray, optionValue));

        HashMap<String, Object> userMap = parseJsonObject(userObject.toString());

        JSONArray itemArray = allFile.getJSONArray("items");
        JSONObject itemObject = itemArray.getJSONObject(getIndex(itemArray, optionValue2));

        HashMap<String, Object> itemMap = parseJsonObject(itemObject.toString());
        
        List<Item> list = new ArrayList<>();
        
        JSONArray itemListArray = userObject.getJSONArray("itemList");
        List<Item> itemList = new ArrayList<>();
            if (itemListArray.length() != 0) {
                for (int j = 0; j < itemListArray.length(); j++) {
                    JSONObject item = itemListArray.getJSONObject(j);
                    itemList.add(new Item(item.getString("id"),
                                        item.getString("model"),
                                        item.getInt("hp"),
                                        item.getInt("attackPower"),
                                        item.getInt("shieldPower"),
                                        item.getInt("moveSpeed"),
                                        item.getInt("attackSpeed")));
                }
            }
        list.addAll(itemList);

        list.add(new Item(itemMap.get("id").toString(), itemMap.get("model").toString(), Integer.parseInt(itemMap.get("hp").toString()), Integer.parseInt(itemMap.get("attackPower").toString()), 
        Integer.parseInt(itemMap.get("shieldPower").toString()), Integer.parseInt(itemMap.get("moveSpeed").toString()), Integer.parseInt(itemMap.get("attackSpeed").toString())));
        
        String[] createUserArr = new String[] {"user", "add", optionValue, userMap.get("nickName").toString()};

        List<User> newUsers = createEquipedUser(createUserArr, users, list);

        JSONObject newFile = new JSONObject();
        newFile.put("items", readFile("items"));
            
        newFile.put("users", newUsers);

            
        try (FileWriter filewWriter = new FileWriter(
                    "./recorder.json")) {
            filewWriter.write(newFile.toString());
            filewWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUsers;
    }
}