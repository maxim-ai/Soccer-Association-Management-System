package ServiceLayer.RoleController;
import BusinessLayer.Controllers.RefereeBusinessController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.Referee;
import BusinessLayer.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefereeController {
    RefereeBusinessController refereeBusinessController;

    public RefereeController(Referee referee) {
        refereeBusinessController=new RefereeBusinessController(referee);
    }
    public SLsettings getsLsettings() {
        return refereeBusinessController.getsLsettings();
    }
    public String getTraining()
    {
        return refereeBusinessController.getTraining();
    }
    public void setMatchs(List<Match> matchs) {
        refereeBusinessController.setMatchs(matchs);
    }
    public List<Match> getMatchs() {
        return refereeBusinessController.getMatchs();
    }
    public HashMap<League, Season> getLeagues() {
        return refereeBusinessController.getLeagues();
    }

    public void setLeagues(HashMap<String, String> leaguesAndSeason) {
        HashMap <League,Season> leagues = new HashMap<>();
        for(Map.Entry<String,String> entry : leaguesAndSeason.entrySet()){
            League league = League.convertStringToLeague(entry.getKey());
            Season season = Season.convertStringToSeason(entry.getValue());
            leagues.put(league,season);
        }
        refereeBusinessController.setLeagues(leagues);
    }
    public static int minimumNumberOfLeagues()
    {
        return 0;
    }
    public boolean addLeague(String leagueName,String seasonName)
    {
        League league = League.convertStringToLeague(leagueName);
        Season season =Season.convertStringToSeason(seasonName);
        return  refereeBusinessController.addLeague(league,season);
    }
    /**
     * remove the policy of the season
     */
    public boolean removeLeague(String leagueName, String seasonName)
    {
        League league = League.convertStringToLeague(leagueName);
        Season season = Season.convertStringToSeason(seasonName);
        return refereeBusinessController.removeLeague(league,season);
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
     return refereeBusinessController.addMatch(aMatch,mainORline);
    }
    /**
     * remove match from referee, remove referee from match
     * @param aMatch
     * @return
     */
    public boolean removeMatch(Match aMatch)
    {
        return refereeBusinessController.removeMatch(aMatch);
    }
    public void delete()
    {
        refereeBusinessController.delete();
    }
    public String toString()
    {
        return refereeBusinessController.toString();
    }

    /**
     * update referee name
     * @param name string
     */
    public void updateDetails(String name){
        refereeBusinessController.updateDetails(name);
    }

    /**
     * show all matches that referee taking part
     */
    public String displayAllMatches() {
        try {
            refereeBusinessController.displayAllMatches();
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    /**
     *
     * @param aMatch that going now
     * @param aType type of the event
     * @param aDescription of the event
     */
    public String updateEventDuringMatch(String aMatch, String aType, String aDescription) throws Exception {

        //BDIKAT KELETTTTTTTTTTTTTTTTTTTTTTTT


        return refereeBusinessController.updateEventDuringMatch(aMatch,aType,aDescription);

    }

    /**
     *
     * @param aMatch match that the event accrue
     * @param aGameEvent the event to edit
     * @param aType the new Enum type
     * @param aDescription the new description
     * @return
     */
    public String editEventAfterGame(String aMatch, String aGameEvent, String aType, String aDescription){
        Match match=Match.convertStringToMatch(aMatch);
        GameEvent gameEvent=GameEvent.convertStringToGameEvent(aGameEvent);
        EventEnum eventEnum=GameEvent.convertStringToEventEnum(aType);
        try {
             refereeBusinessController.editEventAfterGame(match,gameEvent,eventEnum,aDescription);
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }
    /**
     * show all referee details
     */
    public void ShowReferee() {
        refereeBusinessController.ShowReferee();
    }
}
