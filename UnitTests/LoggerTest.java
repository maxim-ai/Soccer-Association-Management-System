import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class LoggerTest {
    Logger logger;


    //region UC and Technical Test
    @Test
    public void getInstanceOnePointer() {
        logger=Logger.getInstance();
        assertNotNull(logger);
    }
    @Test
    public void getInstanceTwoPointers() {
        logger=Logger.getInstance();
        Logger logger2=Logger.getInstance();
        assertEquals(logger,logger2);
    }

    @Test
    public void writeNewLineFileNotExists() throws IOException {
        File loggerFile=new File("Logger");
        if(loggerFile.exists())
            loggerFile.delete();
        logger=Logger.getInstance();
        logger.writeNewLine("aaa");
        assertTrue(CheckLoggerLines("aaa"));
    }

    @Test
    public void writeNewLineFileExists() throws IOException{
        File loggerFile=new File("Logger");
        if(!loggerFile.exists())
            loggerFile.createNewFile();
        logger=Logger.getInstance();
        logger.writeNewLine("aaa");
        assertTrue(CheckLoggerLines("aaa"));
    }

    @Test
    public void readLoggerFile() {
        logger=Logger.getInstance();
        logger.writeNewLine("aaa");
        String s=logger.readLoggerFile();
        assertEquals("aaa\n",s.substring(s.indexOf('-')+2));
    }
    //endregion

    private boolean CheckLoggerLines(String s) {
        String line= null;
        try {
            BufferedReader BR=new BufferedReader(new FileReader(new File("Logger")));
            line = BR.readLine();
            BR.close();
            if(s.equals(line.substring(line.indexOf('-')+2)))
                return true;
            else
                return false;
        } catch (IOException e) { }
        return false;
    }
}