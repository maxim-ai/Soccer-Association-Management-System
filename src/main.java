import sun.rmi.runtime.Log;

import java.io.*;
import java.util.*;

public class main{


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Guest guest1=new Guest();
        Guest guest2=new Guest();
        List<Guest> guests=new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);
        FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(guests);
        o.flush(); o.close();
        f.flush(); f.close();

        FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
       List<Guest> guests2 = (List<Guest>) oi.readObject();

        for(Guest g:guests2)
            System.out.println(g);
    }

}