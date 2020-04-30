package BusinessLayer.Controllers;

import BusinessLayer.DataController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.Referee;
import BusinessLayer.RoleCrudOperations.Role;

import java.util.HashMap;
import java.util.List;

public class RefereeBusinessController {
    Referee referee;

    public RefereeBusinessController(Referee referee) {
        this.referee = referee;
    }
    public SLsettings getsLsettings() {
        return referee.getsLsettings();
    }
    public String getTraining()
    {
        return referee.getTraining();
    }
    public void setMatchs(List<Match> matchs) {
        referee.setMatchs(matchs);
    }
    public List<Match> getMatchs() {
        return referee.getMatchs();
    }
    public HashMap<League, Season> getLeagues() {
        return referee.getLeagues();
    }
    public void setLeagues(HashMap<League, Season> leagues) {
        referee.setLeagues(leagues);
    }
    public static int minimumNumberOfLeagues()
    {
        return 0;
    }
    public boolean addLeague(League aLeague,Season aSeason)
    {
        return  referee.addLeague(aLeague,aSeason);
    }
    /**
     * remove the policy of the season
     */
    public boolean removeLeague(League league, Season aSeason)
    {
        return removeLeague(league,aSeason);
    }
    public static int minimumNumberOfMatchs()
    {
        return 0;
    }
    /**
     * add match to referee, is the match is full return false
     * @param aMatch
     * @param mainORline
     * @return
     */
    public boolean addMatch(Match aMatch, String mainORline)
    {
        return referee.addMatch(aMatch,mainORline);
    }
    /**
     * remove match from referee, remove referee from match
     * @param aMatch
     * @return
     */
    public boolean removeMatch(Match aMatch)
    {
        return referee.removeMatch(aMatch);
    }
    public void delete()
    {
        referee.delete();
    }
    public String toString()
    {
        return referee.toString();
    }

    /**
     * update referee name
     * @param name string
     */
    public void updateDetails(String name){
        referee.updateDetails(name);
    }

    /**
     * show all matches that referee taking part
     */
    public void displayAllMatches() throws Exception {
        referee.displayAllMatches();
    }

    /**
     *
     * @param aMatch that going now
     * @param aType type of the event
     * @param aDescription of the event
     */
    public String updateEventDuringMatch(String aMatch, String aType, String aDescription) throws Exception {
        try {
            Match match=Match.convertStringToMatch(aMatch);
            EventEnum eventEnum=GameEvent.convertStringToEventEnum(aType);
            referee.updateEventDuringMatch(match,eventEnum,aDescription);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     *
     * @param match match that the event accrue
     * @param gameEvent the event to edit
     * @param aType the new Enum type
     * @param aDescription the new description
     * @return
     */
    public boolean editEventAfterGame(Match match, GameEvent gameEvent, EventEnum aType, String aDescription) throws Exception {
        return referee.editEventAfterGame(match,gameEvent,aType,aDescription);
    }
    /**
     * show all referee details
     */
    public void ShowReferee() {
        referee.ShowReferee();
    }

    public static Referee convertStringToReferee(String userName){
        for (Account account : DataController.getAccounts()){
            if(account.getUserName().equals(userName)){
                for(Role role : account.getRoles()){
                    if(role instanceof Referee){
                        return (Referee) role;
                    }
                }
            }
        }
        return null;
    }

}
