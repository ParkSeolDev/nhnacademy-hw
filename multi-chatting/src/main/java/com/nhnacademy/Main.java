package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        Option helpOption = new Option("h", false, "Help");
        options.addOption(helpOption);

        Option serverOption = Option.builder("l")
                .hasArg()
                .build();
        options.addOption(serverOption);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption(("h"))) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("multi-chatting", options);
                System.exit(0);
            }

            if (commandLine.hasOption("l")) {
                Server server = new Server();
                server.open(Integer.parseInt(commandLine.getOptionValue(serverOption.getOpt())));
                
            } else {
                Client client = new Client();
                client.connect(commandLine.getArgs());
            }

        } catch (org.apache.commons.cli.ParseException e) {
            
        }
    }
}