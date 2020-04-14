import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger logger=null;

    public Logger(){}

    public static Logger getInstance(){
        if(logger==null)
            logger=new Logger();
        return logger;
    }

    public void writeNewLine(String line) {
        try
        {
            File loggerFile = new File("Logger");
            if (!loggerFile.exists())
                loggerFile.createNewFile();
            FileWriter FW = new FileWriter(loggerFile, true);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            FW.write(formatter.format(date) + " - " + line + "\n");
            FW.flush();
            FW.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
