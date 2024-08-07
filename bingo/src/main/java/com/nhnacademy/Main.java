package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        
        Option serverOption = Option.builder("l")
                .hasArg()
                .build();
        options.addOption(serverOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        Server server = new Server();
        Client client = new Client();

        if (cmd.hasOption("l")) {
            server.open(Integer.parseInt(cmd.getOptionValue(serverOption.getOpt())));
        } else {
            client.connect(cmd.getArgs());
        }

    }
}