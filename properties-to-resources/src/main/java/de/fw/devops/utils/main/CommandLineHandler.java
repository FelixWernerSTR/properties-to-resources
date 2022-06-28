package de.fw.devops.utils.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Felix Werner
 */
public class CommandLineHandler {
  static Logger logger = LogManager.getLogger(CommandLineHandler.class);
  
  private static Options getCommandLineOptions() {
    Options options = new Options();
    
    Option i = new Option("i", "input", true, "data model properties path");
    i.setRequired(true);
    options.addOption(i);
    
    Option o = new Option("o", "output", true, "output directory for resources");
    o.setRequired(true);
    options.addOption(o);
    
    Option t = new Option("t", "templates", true, "templates directory");
    t.setRequired(true);
    options.addOption(t);
    
    return options;
  }
  
  static final String HELP_OPTION = "help";
  
  static void printHelp(String programName, Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(programName, options);
  }
  
  static CommandLine parseCommandLineOptions(String programName, String[] commandLineArguments) {
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine line = parser.parse(getCommandLineOptions(), commandLineArguments);
      if (line.hasOption(HELP_OPTION)) {
        printHelp(programName, getCommandLineOptions());
        return null;
      }
      return line;
    } catch (ParseException e) {
      logger.error(e);
      printHelp(programName, getCommandLineOptions());
      return null;
    }
  }
  
}
