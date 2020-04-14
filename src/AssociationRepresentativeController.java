import java.util.List;

public class AssociationRepresentativeController {

    AssociationRepresentative associationRepresentative;

    public AssociationRepresentativeController(AssociationRepresentative associationRepresentative) {
        this.associationRepresentative = associationRepresentative;
    }

    /**
     * create new league and put teams inside
     */
    public League createNewLeague(String name, List<Team> teams) {
        return associationRepresentative.createNewLeague(name, teams);
    }

    /**
     * set season to league by year
     */
    public boolean setYearToLeague(League league, String year) {
        return associationRepresentative.setYearToLeague(league, year);
    }

    /**
     * remove specific referee
     */
    public void deleteReferee(Referee referee) {
        associationRepresentative.deleteReferee(referee);
    }

    /**
     * creates an account and adds it as a referee to the system
     */
    public Referee addNewReferee(String training, String name, int age, String userName, String password) {
        return associationRepresentative.addNewReferee(training, name, age, userName, password);
    }

    /**
     * create new referee and link it to account
     */
    public Referee CreateNewReferee(Account account, String training, String name) {
        return associationRepresentative.createNewReferee(account, training, name);
    }

    /**
     * add referee to a league in specific season , add referee to SLsetting referee list
     */
    public boolean addRefereeToLeague(Referee referee, League league, Season season) { // to fix uc
        return associationRepresentative.addRefereeToLeague(referee, league, season);
    }

    /**
     * set league pointCalc policy in specific season
     */
    public boolean setLeaguePointCalcPolicy(League league, Policy policy, Season season, String pointCalc) {
        return associationRepresentative.setLeaguePointCalcPolicy(league, policy, season, pointCalc);
    }

    /**
     * set league game Schedule policy in specific season
     */
    public boolean setLeagueGameSchedualPolicy(League league, Policy policy, Season season, String gameSchedule) {
        return associationRepresentative.setLeagueGameSchedualPolicy(league, policy, season, gameSchedule);
    }

    /**
     * Sets new season in specific year
     *
     * @param year the year in which the season will take place
     * @return
     */
    public Season setNewSeason(String year) {
        return associationRepresentative.setNewSeason(year);
    }

    public void addAmountToAssociationBudget(double amount) {
        associationRepresentative.addAmountToAssociationBudget(amount);
    }

    public void subtractAmountToAssociationBudget(double amount) {
        associationRepresentative.subtractAmountFromAssociationBudget(amount);
    }

        public boolean approveTeam(String teamName, Owner owner) {
        return associationRepresentative.approveTeam(teamName, owner);
    }

    public boolean addOpenTeamRequest(Owner owner, String teamName)
    {
        return AssociationRepresentative.addOpenTeamRequest(owner,teamName);
    }

    public boolean removeOpenTeamRequest(Owner owner,String teamName)
    {
        return AssociationRepresentative.removeOpenTeamRequest(owner,teamName);
    }

    public boolean checkIfRequestExists(Owner owner,String teamName){
        return AssociationRepresentative.checkIfRequestExists(owner, teamName);
    }

    public boolean getRequestStatus(Owner owner,String teamName)
    {
        return AssociationRepresentative.getRequestStatus(owner,teamName);
    }




}
