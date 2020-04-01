import java.util.*;

public class main{


    public static void main(String[] args){
        String abs="hi";
        bla(abs);

        java.lang.System.out.println(bla(abs));
    }
    public static boolean bla(Object o)
    {
        if(o instanceof String)
            return true;
        return false;
    }

}