import java.io.*;
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

    public void writeNewLine(String line)  {
        try {
            File loggerFile=new File("Logger");
            if(!loggerFile.exists())
                loggerFile.createNewFile();
            FileWriter FW=new FileWriter(loggerFile,true);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            FW.write(formatter.format(date)+" - "+line+"\n");
            FW.flush();
            FW.close();
        } catch (IOException e) { }
    }

    public String readLoggerFile(){
        String string="";
        try {
            File loggerFile=new File("Logger");
            BufferedReader BR=new BufferedReader(new FileReader(loggerFile));
            String line="";
            while((line=BR.readLine())!=null){
                string+=line;
                string+="\n";
            }
            BR.close();
        } catch (IOException e) { }
        return string;
    }



}
