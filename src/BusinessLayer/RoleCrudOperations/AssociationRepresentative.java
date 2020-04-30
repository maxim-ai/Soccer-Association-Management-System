package BusinessLayer.RoleCrudOperations;
import BusinessLayer.DataController;
import BusinessLayer.Logger.Logger;
import BusinessLayer.OtherCrudOperations.*;

import ServiceLayer.OurSystem;
import javafx.util.Pair;

import java.util.*;


public class AssociationRepresentative extends Role {

  public static HashMap<Pair<Owner,String>,Boolean> approvedTeams=new HashMap<>();

  public AssociationRepresentative(String aName)
  {
    super(aName);
  }

  /**
   * create new league and put teams inside
   * @param name
   * @param teams
   * @return
   */
  public League createNewLeague(String name, List<Team> teams){
    if(teams == null || name == null){
      return  null;
    }
    League league = new League(name);
    DataController.addLeague(league);
    for(Team team : teams){
      league.addTeam(team);
    }
    return league;
  }

  /**
   * set season to league by year
   * @param league
   * @param year
   * @return
   */
  public boolean setYearToLeague(League league, String year){
    boolean wasSet = false;
    if(league == null || year == null){
      return  wasSet;
    }
    Season season=new Season(year);
    DataController.addSeason(season);
    DataController.addLeague(league);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    if (sLsettings==null)
    {
      sLsettings = new SLsettings(new Policy(null,null));
    }
    wasSet = league.addSLsettingsToSeason(season,sLsettings);
    return wasSet;
  }

  /**
   * remove specific referee
   * @return
   */
  public void deleteReferee(Referee referee){
    if(referee == null){
      return;
    }
    SLsettings refSLsettings = referee.getsLsettings();
    for(Referee referee1 : refSLsettings.getReferees()){
      if(referee.equals(referee1)) {
        referee1.delete();
        return;
      }
    }
  }

  /**
   * creates an account and adds it as a referee to the system
   * @param name the name of the user
   * @param userName the desire user name to the account
   * @param password the desire password to the account
   * @param age the age of the user
   * @param training the training of the user, as a referee
   */
  public Referee addNewReferee(String training, String name, int age, String userName, String password){
    Account account = new Account(name,age,userName,password);
    DataController.addAccount(account);
    if(training != null && name != null) {
      return createNewReferee(account,training,name);
    }
    return null;
  }

  /**
   * create new referee and links it to a specific account
   * @param training
   * @param name
   * @param account
   */
  public Referee createNewReferee(Account account, String training, String name) {
    Referee referee = new Referee(training, name);
    account.addRole(referee);
    sendInvitation(referee);
    return referee;
  }

  /**
   * send invitation to the referee
   * @param referee
   */
  private void sendInvitation(Referee referee) {
    referee.addAlert(new Alert("Invitation to be a referee"));
  }

  /**
   * add referee to a league in specific season , add referee to SLsetting referee list
   * @param league
   * @param referee
   * @param season
   * @return
   */
  public boolean addRefereeToLeague(Referee referee, League league, Season season){ // to fix uc
    boolean wasAdded = false;
    if(league == null || referee == null || season == null){
      return  wasAdded;
    }
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    if (sLsettings==null)
    {
      sLsettings = new SLsettings(new Policy(null,null));
    }
    referee.setsLsettings(sLsettings);
    sLsettings.addReferee(referee);
    wasAdded= referee.addLeague(league,season);
    return  wasAdded;
  }

  /**
   * set league pointCalc policy in specific season
   * @param league
   * @param policy
   * @param season
   * @return
   */
  public  boolean setLeaguePointCalcPolicy(League league, Policy policy, Season season, String pointCalc){
    boolean wasAdded = false;
    if(league == null || policy == null || season == null){
      return  wasAdded;
    }
    policy.setPointCalc(pointCalc);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    if(sLsettings!=null)
      sLsettings.setPolicy(policy);
    else{
      sLsettings = new SLsettings(policy);
      season.addSLsettingsToLeague(league,sLsettings);
    }
    return true;
  }

  /**
   * set league game Schedule policy in specific season
   * @param league
   * @param policy
   * @param season
   * @return
   */
  public  boolean setLeagueGameSchedualPolicy(League league, Policy policy, Season season, String gameSchedule){
    boolean wasAdded = false;
    if(league == null || policy == null || season == null){
      return  wasAdded;
    }
    policy.setGameSchedual(gameSchedule);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    if(sLsettings!=null)
      sLsettings.setPolicy(policy);
    else{
      sLsettings = new SLsettings(policy);
      season.addSLsettingsToLeague(league,sLsettings);
    }    return true;
  }

  public Season setNewSeason(String year){
    Season season = new Season(year);
    DataController.addSeason(season);
    return season;
  }

  public void addAmountToAssociationBudget(double amount){

  }

  public void subtractAmountFromAssociationBudget(double amount){

  }

  /**
   * approve the opening of a team
   */
  public boolean approveTeam(String teamName, Owner owner)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);

    if(!approvedTeams.containsKey(request))
      return false;
    else
    {
      approvedTeams.put(request,true);
      OurSystem.notifyOtherRole("You are approved to open team: "+teamName,owner);
      Logger.getInstance().writeNewLine(getName()+" approved "+owner.getName()+" to open the team: "+teamName);
      return true;
    }
  }

  /**
   * add a request for opening a team
   */
  public static boolean addOpenTeamRequest(Owner owner, String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    if(approvedTeams.containsKey(request))
      return false;
    else
      approvedTeams.put(request,false);
    return true;
  }

  /**
   * remove a request for opening a team
   */
  public static boolean removeOpenTeamRequest(Owner owner, String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    if(!approvedTeams.containsKey(request))
      return false;
    else
      approvedTeams.remove(request);
    return true;
  }

  /**
   * check if a certain request for opening a team exists
   */
  public static boolean checkIfRequestExists(Owner owner, String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    return approvedTeams.containsKey(request);
  }

  /**
   * get the status of a pending request
   */
  public static boolean getRequestStatus(Owner owner, String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    return approvedTeams.get(request);
  }

}