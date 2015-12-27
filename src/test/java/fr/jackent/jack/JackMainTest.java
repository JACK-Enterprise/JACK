package fr.jackent.jack;

import org.junit.Test;

public class JackMainTest 
{
	private static final String[] HELP_ARGS_ARRAY = new String[] {"help"};
	
    @Test
    public void shouldPrintHelp() {
    	JackMain.main(HELP_ARGS_ARRAY);
    }
    
    @Test
    public void shouldCatchNoArgs_NullException() {
        JackMain.main(null);
    }
    
    @Test
    public void shouldCatchNoArgs_EmptyException() {
        JackMain.main(new String[] {});
    }
}
