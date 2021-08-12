package io.github.altoukhovmax.rates;

import io.github.altoukhovmax.rates.gui.GraphicalApplication;
import javafx.application.Application;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class Rates {

    public static void main(final String[] args) {
        final Option helpOption = Option.builder("h")
                .longOpt("help")
                .required(false)
                .hasArg(false)
                .desc("print help message and exit")
                .build();
        final Option versionOption = Option.builder("v")
                .longOpt("version")
                .required(false)
                .hasArg(false)
                .desc("print version and exit")
                .build();
        final Options options = new Options()
                .addOption(helpOption)
                .addOption(versionOption);

        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine parsedArguments = parser.parse(options, args);
            if (parsedArguments.hasOption(helpOption.getOpt())) {
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("rates", options, true);
                return;
            }
            if (parsedArguments.hasOption(versionOption.getOpt())) {
                System.out.println("1.0.0");
                return;
            }
            Application.launch(GraphicalApplication.class, args);
        } catch (ParseException e) {
            System.err.println("Parsing error: " + e.getMessage());
        }
    }
}
