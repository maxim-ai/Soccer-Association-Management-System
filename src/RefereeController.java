import java.util.HashMap;
import java.util.List;

public class RefereeController {
    Referee referee;

    public RefereeController(Referee referee) {
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
        return removeMatch(aMatch);
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
    public void displayAllMatches() {
        referee.displayAllMatches();
    }

    /**
     *
     * @param match that going now
     * @param aType type of the event
     * @param aDescription of the event
     */
    public void updateEventDuringMatch(Match match,EventEnum aType, String aDescription)
    {
        referee.updateEventDuringMatch(match,aType,aDescription);
    }

    /**
     *
     * @param match match that the event accrue
     * @param gameEvent the event to edit
     * @param aType the new Enum type
     * @param aDescription the new description
     * @return
     */
    public boolean editEventAfterGame(Match match, GameEvent gameEvent, EventEnum aType, String aDescription){
        return referee.editEventAfterGame(match,gameEvent,aType,aDescription);
    }
    /**
     * show all referee details
     */
    public void ShowReferee() {
        referee.ShowReferee();
    }
}