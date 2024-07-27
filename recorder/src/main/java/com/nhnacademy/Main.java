package com.nhnacademy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
        public static void main(String[] args) throws ParseException {
                Game game = new Game();
                List<User> users = new ArrayList<>();
                List<Item> items = new ArrayList<>();
                String filePath = "./recorder.json";
                if (!Files.exists(Paths.get(filePath))) {
                        try {
                                Files.createFile(Paths.get(filePath));
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                } else {
                        users = game.initUsers();
                        items = game.initItems();
                }
        
                Options options = new Options();

                Option add = Option.builder("a")
                        .longOpt("add")
                        .desc("Add Data")
                        .build();
                options.addOption(add);

                Option equip = Option.builder("q")
                        .longOpt("equip")
                        .hasArg()
                        .desc("Equip Data")
                        .build();
                options.addOption(equip);

                Option type = Option.builder("t")
                        .longOpt("type")
                        .hasArg()
                        .desc("Specify the type of data")
                        .build();
                options.addOption(type);

                Option id = Option.builder("i")
                        .longOpt("id")
                        .hasArg()
                        .build();
                options.addOption(id);

                Option name = Option.builder("n")
                        .longOpt("name")
                        .hasArg()
                        .build();
                options.addOption(name);

                Option list = Option.builder("l")
                        .longOpt("list")
                        .desc("Show list of <type> objects")
                        .build();
                options.addOption(list);

                Option count = Option.builder("c")
                        .longOpt("count")
                        .desc("Count")
                        .build();
                options.addOption(count);

                Option win = Option.builder("W")
                        .longOpt("Win")
                        .desc("Win count")
                        .build();
                options.addOption(win);

                Option help = Option.builder("h")
                        .longOpt("help")
                        .desc("Help")
                        .build();
                options.addOption(help);

                Option energy = Option.builder("e")
                        .longOpt("energy")
                        .hasArg()
                        .desc("Energy")
                        .build();
                options.addOption(energy);

                Option attack = Option.builder("p")
                        .longOpt("attack-power")
                        .hasArg()
                        .desc("attack-power")
                        .build();
                options.addOption(attack);

                Option remove = Option.builder("r")
                        .longOpt("remove")
                        .hasArg()
                        .build();
                options.addOption(remove);

                Option defence = Option.builder("d")
                        .longOpt("defence")
                        .hasArg()
                        .build();
                options.addOption(defence);

                Option movingSpeed = Option.builder("m")
                        .longOpt("moving-speed")
                        .hasArg()
                        .build();
                options.addOption(movingSpeed);

                Option attackSpeed = Option.builder("A")
                        .longOpt("attack-speed")
                        .hasArg()
                        .build();
                options.addOption(attackSpeed);

                Option history = Option.builder("L")
                        .longOpt("history")
                        .hasArg()
                        .build();
                options.addOption(history);

                Option dbFile = Option.builder("f")
                        .longOpt("db-file")
                        .hasArg()
                        .build();
                options.addOption(dbFile);

                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption(("h"))) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("recorder", options);
                System.exit(0);
                }
                if (cmd.hasOption(("a"))) {
                        String dataType = cmd.getOptionValue(type.getOpt());
                        if (dataType.equals("user")) {
                                game.createUser(cmd.getOptionValue(id.getOpt()), cmd.getOptionValue(name.getOpt()));
                        } else if (dataType.equals("item")) {
                                System.out.println(cmd.getOptionValue(energy.getOpt()));
                                System.out.println(cmd.getOptionValue(attack.getOpt()));
                                System.out.println(cmd.getOptionValue(defence.getOpt()));
                                System.out.println(cmd.getOptionValue(movingSpeed.getOpt()));
                                System.out.println(cmd.getOptionValue(attackSpeed.getOpt()));
                                game.createItem(
                                                cmd.getOptionValue(id.getOpt()),
                                                cmd.getOptionValue(name.getOpt()),
                                                cmd.getOptionValue(energy.getOpt()),
                                                cmd.getOptionValue(attack.getOpt()),
                                                cmd.getOptionValue(defence.getOpt()),
                                                cmd.getOptionValue(movingSpeed.getOpt()),
                                                cmd.getOptionValue(attackSpeed.getOpt()));
                        }
                }
                
                if (cmd.hasOption("q")) {
                        game.equip(cmd.getOptionValue(id.getOpt()), cmd.getOptionValue(id.getOpt()), users, items);
                }
                
                if (cmd.hasOption("l")) {
                String dataType = cmd.getOptionValue(type.getOpt());
                if (dataType == null){
                        game.printUserDetail();
                }
                else if (dataType.equals("user")) {
                        game.printUsers();
                } else if (dataType.equals("item")) {
                        game.printItems();
                }
                }

                if (cmd.hasOption("f")) {
                        System.out.println(cmd.getOptionValue("f"));
                }
    }
}