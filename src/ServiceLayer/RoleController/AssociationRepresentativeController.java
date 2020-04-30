package ServiceLayer.RoleController;
import BusinessLayer.Controllers.RefereeBusinessController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.AssociationRepresentative;
import BusinessLayer.RoleCrudOperations.Owner;
import BusinessLayer.RoleCrudOperations.Referee;

import java.util.LinkedList;
import java.util.List;

public class AssociationRepresentativeController {

    AssociationRepresentative associationRepresentative;

    public AssociationRepresentativeController(AssociationRepresentative associationRepresentative) {
        this.associationRepresentative = associationRepresentative;
    }

    /**
     * create new league and put teams inside
     */
    public League createNewLeague(String name, List<String> teamNames) {
        List <Team> teams = new LinkedList<>();
        for(String teamName : teamNames){
            Team team = Team.convertStringToTeam(teamName);
            teams.add(team);
        }
        return associationRepresentative.createNewLeague(name, teams);
    }

    /**
     * set season to league by year
     */
    public boolean setYearToLeague(String leagueName, String year) {
        League league = League.convertStringToLeague(leagueName);
        return associationRepresentative.setYearToLeague(league, year);
    }

    /**
     * remove specific referee
     */
    public void deleteReferee(String userName) {
        Referee referee = RefereeBusinessController.convertStringToReferee(userName);
        associationRepresentative.deleteReferee(referee);
    }

    /**
     * creates an account and adds it as a referee to the system
     */
    public Referee addNewReferee(String training, String name, String stringAge, String userName, String password) {
        int age = Integer.getInteger(stringAge);
        return associationRepresentative.addNewReferee(training, name, age, userName, password);
    }

    /**
     * create new referee and link it to account
     */
    public Referee createNewReferee(String userName, String training, String name) {
        Account account = Account.convertStringToAccount(userName);
        return associationRepresentative.createNewReferee(account, training, name);
    }

    /**
     * add referee to a league in specific season , add referee to SLsetting referee list
     */
    public boolean addRefereeToLeague(String userName, String leagueName, String seasonName) { // to fix uc
        Referee referee = RefereeBusinessController.convertStringToReferee(userName);
        League league = League.convertStringToLeague(leagueName);
        Season season = Season.convertStringToSeason(seasonName);
        return associationRepresentative.addRefereeToLeague(referee, league, season);
    }

    /**
     * set league pointCalc policy in specific season
     */
    public boolean setLeaguePointCalcPolicy(String leagueName, String policyID, String seasonName, String pointCalc) {
        League league = League.convertStringToLeague(leagueName);
        Season season = Season.convertStringToSeason(seasonName);
        Policy policy = Policy.convertStringToPolicy(policyID);
        return associationRepresentative.setLeaguePointCalcPolicy(league, policy, season, pointCalc);
    }

    /**
     * set league game Schedule policy in specific season
     */
    public boolean setLeagueGameSchedualPolicy(String leagueName, String policyID, String seasonName, String gameSchedule) {
        Policy policy = Policy.convertStringToPolicy(policyID);
        League league = League.convertStringToLeague(leagueName);
        Season season = Season.convertStringToSeason(seasonName);
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

    public void addAmountToAssociationBudget(String amountName) {
        double amount = Double.parseDouble(amountName);
        associationRepresentative.addAmountToAssociationBudget(amount);
    }

    public void subtractAmountToAssociationBudget(String amountName) {
        double amount = Double.parseDouble(amountName);
        associationRepresentative.subtractAmountFromAssociationBudget(amount);
    }

        public boolean approveTeam(String teamName, String userName) {
        Owner owner = Owner.convertStringToOwner(userName);
        return associationRepresentative.approveTeam(teamName, owner);
    }

    public boolean addOpenTeamRequest(String userName, String teamName)
    {
        Owner owner = Owner.convertStringToOwner(userName);
        return AssociationRepresentative.addOpenTeamRequest(owner,teamName);
    }

    public boolean removeOpenTeamRequest(String userName, String teamName)
    {
        Owner owner = Owner.convertStringToOwner(userName);
        return AssociationRepresentative.removeOpenTeamRequest(owner,teamName);
    }

    public boolean checkIfRequestExists(String userName, String teamName){
        Owner owner = Owner.convertStringToOwner(userName);
        return AssociationRepresentative.checkIfRequestExists(owner, teamName);
    }

    public boolean getRequestStatus(String userName, String teamName)
    {
        Owner owner = Owner.convertStringToOwner(userName);
        return AssociationRepresentative.getRequestStatus(owner,teamName);
    }


}
