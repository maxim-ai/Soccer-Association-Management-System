import BusinessLayer.Logger.Logger;
import ServiceLayer.GuestController.GuestController;
import ServiceLayer.OurSystem;
import sun.rmi.runtime.Log;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class main{


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {


//        OurSystem ourSystem=new OurSystem();
//        GuestController GC= OurSystem.makeGuestController();
//        Method m= GC.getClass().getDeclaredMethod("LogIn",String.class,String.class);
//
//        List<Object> list=new ArrayList<>();
//        list.add("Maxi");
//        list.add("Maxim");
//
//        Object[] arr=new Object[2];
//        arr[0]="aaa";
//        arr[1]="bbb";
//
//        m.invoke(GC,arr);

        String s=new String("Red Card");
        boolean b=s.replaceAll(" ","").equalsIgnoreCase("redCard");
        System.out.println(b);

        int n=0;

    }

//    public static Object getControllerByMethod(Method argM){
//        for(Object o:controllers){
//            for(Method M:o.getClass().getDeclaredMethods()){
//                if(argM.getName().equals(M.getName()))
//                    return o;
//            }
//        }
//        return null;
//    }

}