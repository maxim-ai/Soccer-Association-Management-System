package Client.PresentationLayer;

import Client.ServiceLayer.GuestController.GuestController;
import Client.ServiceLayer.OurSystem;
import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GuiMediator implements Observer {
    OurSystem ourSystem;
    List controllers=new ArrayList();
    HashMap<String, Pair<Method,List<String>>> functionsToUser=new HashMap<>(); //key: name of option for user
                                                                                //value: pairKey:actual method
                                                                                //       pairValue: list of args for method

    public GuiMediator() {
        this.ourSystem = new OurSystem();
        ourSystem.Initialize();
    }

    public OurSystem getOurSystem() {
        return ourSystem;
    }

    public List getControllers() {
        return controllers;
    }

    public HashMap<String, Pair<Method, List<String>>> getFunctionsToUser() {
        return functionsToUser;
    }

    public String getUserControllers(String userName, String password)
    {
        GuestController guestController= OurSystem.makeGuestController();
        List<Object> controllerList= (List<Object>) guestController.LogIn(userName,password);
        if(controllerList.size()==1 && controllerList.get(0) instanceof String)
        {
            return (String)controllerList.get(0);
        }
        else
        {
            controllers=controllerList;
            return "true";
        }
    }

    public List<String> createOptionsForUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> options=new ArrayList();
        for(Object o:controllers)
        {
            Method method=o.getClass().getDeclaredMethod("showUserMethods");
            HashMap<String, Pair<Method,List<String>>> returnedMap= (HashMap<String, Pair<Method, List<String>>>) method.invoke(o);
            for(Map.Entry<String, Pair<Method,List<String>>> entry:returnedMap.entrySet())
            {
                functionsToUser.put(entry.getKey(),entry.getValue());
                options.add(entry.getKey());
            }
        }
        return options;
    }

    public List<String> getDropDownList(String arg,List<String> prevArgs)
    {
        List<String> choices = new ArrayList<>();
        List<String> dropDownFunc = OurSystem.getDropList(arg.substring(arg.indexOf("@")+1),controllers,prevArgs);
        for(String pick:dropDownFunc)
        {
            choices.add(pick);
        }
        return choices;
    }

    public String executeMethod(Pair<Method, List<String>> methodPair, List<String> userArgs) throws InvocationTargetException, IllegalAccessException
    {
        Object Controller=null;
        for(Object o:controllers){
            for(Method M:o.getClass().getDeclaredMethods()){
                if(methodPair.getKey().getName().equals(M.getName()))
                    Controller=o;
            }
        }
        if(Controller!=null)
        {
           return (String) methodPair.getKey().invoke(Controller,userArgs.toArray());
        }

        else
        {
            return "Method is not implemented";
        }
    }

    public Pair<Method, List<String>> getUserChoice(String choice)
    {
        return functionsToUser.get(choice);
    }

    public List<String> getAllAlerts() throws InvocationTargetException, IllegalAccessException {
        List<String> alerts=new ArrayList();
        for(Object o:controllers)
        {
            Method method= null;
            try {
                method = o.getClass().getDeclaredMethod("getAlerts");
            }
            catch (NoSuchMethodException e) {
                continue;
            }
            alerts.addAll((List<String>) method.invoke(o));
        }
        return alerts;
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
