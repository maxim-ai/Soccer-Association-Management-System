package ServiceLayer.RoleController;
import BusinessLayer.Controllers.RefereeBusinessController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.Referee;
import BusinessLayer.*;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefereeController {
    RefereeBusinessController refereeBusinessController;

    /**
     *
     * @param referee
     */
    public RefereeController(Referee referee) {
        refereeBusinessController=new RefereeBusinessController(referee);
    }

    /**
     * update referee name uc 10.1
     * @param name string
     */
    public String updateDetails(String name){
        if(containsDigit(name))
            return "Referee name contains invalid characters";
       return refereeBusinessController.updateDetails(name);
    }

    /**
     * show all matches that referee taking part uc 10.2
     */
    public String displayAllMatches() throws Exception {
           return refereeBusinessController.displayAllMatches();
    }

    /**
     * uc 10.3
     * @param aMatch that going now
     * @param aType type of the event
     * @param aDescription of the event
     */
    public String updateEventDuringMatch(String aMatch, String aType, String aDescription) throws Exception {

        if(containsDigit(aType))
            return "The event type contains invalid characters";
        else if(aDescription.length()<5)
            return "Description must contain at least 5 characters";
        return refereeBusinessController.updateEventDuringMatch(aMatch,aType,aDescription);

    }

    /**
     *uc 10.4
     * @param aMatch match that the event accrue
     * @param aGameEvent the event to edit
     * @param aType the new Enum type
     * @param aDescription the new description
     * @return
     */
    public String editEventAfterGame(String aMatch, String aGameEvent, String aType, String aDescription) throws Exception {
        if(containsDigit(aType))
            return "The event type contains invalid characters";
        else if(aDescription.length()<5)
            return "Description must contain at least 5 characters";
           return   refereeBusinessController.editEventAfterGame(aMatch,aGameEvent,aType,aDescription);
    }
    public List<String> getEvantsByMatch(String matchString)
    {
        return refereeBusinessController.getEvantsByMatch(matchString);
    }
    public List<String> getMatchList(){
        return refereeBusinessController.getMatchList();
    }
    public boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }

    public HashMap<String, Pair<Method,List<String>>> showUserMethods() throws NoSuchMethodException
    {
        HashMap<String, Pair<Method,List<String>>> options=new HashMap<>();
        List<String> showUserList=new ArrayList<>();
        showUserList.add("Match@Match");
        showUserList.add("Event type@EventEnum");
        showUserList.add("Description");
        options.put("Add event during a match",new Pair<>(this.getClass().getDeclaredMethod("updateEventDuringMatch",String.class,String.class,String.class),showUserList));

        showUserList=new ArrayList<>();
        showUserList.add("Match@Match");
        showUserList.add("Event@GameEvent");
        showUserList.add("Event type@EventEnum");
        showUserList.add("Description");
        options.put("Edit an event after the mach",new Pair<>(this.getClass().getDeclaredMethod("editEventAfterGame",String.class,String.class,String.class,String.class),showUserList));

        return options;
    }
}
