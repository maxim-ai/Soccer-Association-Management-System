import java.io.*;
import java.util.*;

public class main{


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashSet<String> set=new HashSet<>();
        while(set.size()<720)
        {
            int x1= (int) (Math.random()*10);
            int x2=(int) (Math.random()*10);
            int x3=(int) (Math.random()*10);
            int x4=(int) (Math.random()*10);
            int x5=(int) (Math.random()*10);
            int x6=(int) (Math.random()*10);
            String num="05"+x1+"-65"+x2+x3+x4+x5+x6;
            if(!set.contains(num))
                set.add(num);
        }
//        System.out.println(set.size());
        Arrays.sort(new HashSet[]{set});
        for(String s:set)
            System.out.println(s);
        int n=0;


    }

}