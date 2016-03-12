package fr.jackent.jack;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Entry point class of the JACK app, a geo referenced 3D cartography application 
 * 
 * @author ACID-KILLA666
 *
 */
@Slf4j
public class JackMain 
{
	private static final String ARG_ARRAY_ERROR_STRING = 
			"Argument array doesn't match with expected ! Aborting process...";
	private static final String PARSING_STRATEGY_ERROR_STRING = 
			"Given parsing strategy is not correct ! Aborting process...";
	
	/**
     * Default constructor set to private to abort accidental instantiation
     */
    private JackMain() {
    }
    
    public static void main( String[] args )
    {
    	log.info("Launching logparser application.");
        byte strategy = argsArrayErrorHandler(args);

        switch (strategy) {
        case -1:
            log.error(ARG_ARRAY_ERROR_STRING);
            break;
        default:
            exec(args, strategy);
            break;
        }
    }
    
    /**
     * Catches parameters' error and throws exceptions
     * 
     * @param args
     *            Command-line arguments
     * 
     * @throws IllegalArgumentException
     * 
     * @return The execution focus that has been detected.
     */
    public static byte argsArrayErrorHandler(String[] args) {

        byte returnValue = 0;

        if (args == null || args.length == 0) {
            returnValue = -1;
        }

        else if (args.length == 1 && args[0].equals("help")) {
            returnValue = 0;
        }

        else {
            returnValue = -1;
        }

        return returnValue;

    }
    
    /**
     * Launches the log parsing process using pre checked-arguments
     * 
     * @param args
     *            Command-line arguments
     * @param strategy
     *            The execution focus to apply.
     * 
     * @throws IOException
     */
    public static void exec(String[] args, byte strategy) {

        switch (strategy) {
        case 0:
            log.info("Launching help process.");
            printHelp();
            break;
        default:
            log.error(PARSING_STRATEGY_ERROR_STRING);
        }
    }
    
    /**
     * Shows program's manual.
     */
    public static void printHelp() {
        System.out.println("Welcome to JACK Application ! \n\n"
        		+ "Description :\n\n"
        		+ "\tJAVA Advanced Cartography Kernel is a geo referenced 3D\n"
        		+ "\tcartography application which has been built in order to \n"
        		+ "\tprovide a simple and customizable library for Java developpers \n"
        		+ "\twho wants to work with a cartography API without any knowledges \n"
        		+ "\tin this domain.\n\n"
        		+ "Launching options :\n\n"
                + "\t\"help\"            : Shows explanations about how "
        		+ "this app actually works.\n\n"
                + "Credits :\n\n"
        		+ "\tThis application has been developped by JACK Enterprise, \n"
                + "\ta small ESGI's 3 students organization : \n\n"
        		+ "\t\t - ACID-KILLA666\n"
        		+ "\t\t - Marma92\n"
        		+ "\t\t - Sorion\n\n"
        		+ "Thanks for downloading !");
    }
}
