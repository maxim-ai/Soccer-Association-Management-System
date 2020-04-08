import java.util.List;

public class AssiciationRepresentiveController {

    AssiciationRepresentive assiciationRepresentive;

    public AssiciationRepresentiveController(AssiciationRepresentive assiciationRepresentive) {
        this.assiciationRepresentive = assiciationRepresentive;
    }

    /**
     * create new league and put teams inside
     */
    public League createNewLeague(String name, List<Team> teams){
        return assiciationRepresentive.createNewLeague(name,teams);
    }

    /**
     * set season to league by year
     */
    public boolean setYearToLeague(League league,String year){
        return  assiciationRepresentive.setYearToLeague(league,year);
    }
    /**
     * remove specific referee
     */
    public void deleteReferee(Referee referee){
        assiciationRepresentive.deleteReferee(referee);
    }
    /**
     * add new referee to system
     */
    public Referee addNewReferee(String training, String name){
        return assiciationRepresentive.addNewReferee(training,name);
    }
    /**
     * create new referee
     */
    public Referee CreateNewReferee(String training, String name) {
        return  assiciationRepresentive.CreateNewReferee(training,name);
    }

    /**
     * add referee to a league in specific season , add referee to SLsetting referee list
     */
    public boolean addRefereeToLeague(Referee referee,League league,Season season){ // to fix uc
        return assiciationRepresentive.addRefereeToLeague(referee,league,season);
    }

    /**
     * set league pointCalc policy in specific season
     */
    public  boolean setLeaguePointCalcPolicy(League league, Policy policy,Season season,String pointCalc){
        return assiciationRepresentive.setLeaguePointCalcPolicy(league,policy,season,pointCalc);
    }

    /**
     * set league game Schedule policy in specific season
     */
    public  boolean setLeagueGameSchedualPolicy(League league, Policy policy,Season season,String gameSchedule){
        return  assiciationRepresentive.setLeagueGameSchedualPolicy(league,policy,season,gameSchedule);
    }

    public void addAlert(Alert alert){
        assiciationRepresentive.addAlert(alert);
    }
    public void clearAlerts() {
        assiciationRepresentive.clearAlerts();
    }
    public void removeAlert(String s)
    {
        assiciationRepresentive.removeAlert(s);
    }

}
