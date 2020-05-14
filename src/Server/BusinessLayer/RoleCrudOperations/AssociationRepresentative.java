package Server.BusinessLayer.RoleCrudOperations;
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.Logger.Logger;
import Server.BusinessLayer.OtherCrudOperations.*;

import javafx.util.Pair;

import java.util.*;


public class AssociationRepresentative extends Role {

  public static HashMap<Pair<Owner,String>,Boolean> approvedTeams=new HashMap<>();


  public AssociationRepresentative(String username){setUsername(username);}

  /**
   * create new league and put teams inside
   * @param name
   * @param teams
   * @return
   */
  public String createNewLeague(String name, List<Team> teams){
    League league = new League(name);
    DataController.getInstance().addLeague(league);
    for(Team team : teams){
      league.addTeam(team);
    }
    Logger.getInstance().writeNewLine(this.getUsername()+" created new leauge: "+(league.getName() ));
    return "New leauge created successfully";
  }

  /**
   * set season to league by year
   * @param league
   * @param year
   * @return
   */
  public String setYearToLeague(League league, String year){
    Season season=new Season(year);
    DataController.getInstance().addSeason(season);
    DataController.getInstance().addLeague(league);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    if (sLsettings==null)
    {
      sLsettings = new SLsettings(new Policy(null,null));
    }
    league.addSLsettingsToSeason(season,sLsettings);
    Logger.getInstance().writeNewLine(this.getUsername()+" set new year to league: "+(league.getName() ));
    return "Set year to league successfully";
  }

  /**
   * remove specific referee
   * @return
   */
  public String deleteReferee(Referee referee){
    SLsettings refSLsettings = referee.getsLsettings();
    for(Referee referee1 : refSLsettings.getReferees()){
      if(referee.equals(referee1)) {
        referee1.delete();
        Logger.getInstance().writeNewLine(this.getUsername()+" remove referee: "+(referee.getName() ));
        return "Remove referee successfully";
      }
    }
    Logger.getInstanceError().writeNewLineError(this.getUsername()+" delete "+(referee.getName() +" failed"));
    return "Remove referee failed";
  }

  /**
   * creates an account and adds it as a referee to the system
   * @param name the name of the user
   * @param userName the desire user name to the account
   * @param password the desire password to the account
   * @param age the age of the user
   * @param training the training of the user, as a referee
   */
  public String addNewReferee(String training, String name, int age, String userName, String password){
    Account account = new Account(name,age,userName,password);
    DataController.getInstance().addAccount(account);
    Referee referee = createNewReferee(account,training,name);
    Logger.getInstance().writeNewLine(this.getUsername()+" add referee: "+(referee.getName()));
    return "Add new referee successfully";
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
  public String addRefereeToLeague(Referee referee, League league, Season season){
//    SLsettings sLsettings = league.getSLsettingsBySeason(season);
//    if (sLsettings==null)
//    {
//      sLsettings = new SLsettings(new Policy(null,null));
//    }
//    referee.setsLsettings(sLsettings);
//    sLsettings.addReferee(referee);
//    referee.addLeague(league,season);
    DataController.getInstance().addRefereeToLeague(referee,league,season);
    Logger.getInstance().writeNewLine(this.getUsername()+" add referee: " +referee.getName()+  " to league: "+(league.getName()));
    return  "Added referee to league successfully";
  }

  /**
   * set league pointCalc policy in specific season
   * @param league
   * @param season
   * @return
   */
  public  String setLeaguePointCalcPolicy(League league, Season season, String pointCalc){
//    policy.setPointCalc(pointCalc);
//    SLsettings sLsettings = league.getSLsettingsBySeason(season);
//    if(sLsettings!=null)
//      sLsettings.setPolicy(policy);
//    else{
//      sLsettings = new SLsettings(policy);
//      season.addSLsettingsToLeague(league,sLsettings);
//    }
    DataController.getInstance().updateLeaguePolicy(season,league,"PointCalc",pointCalc);
    Logger.getInstance().writeNewLine(this.getUsername()+" set League Point Calculation Policy: " +(pointCalc));
    return "Set league point calculation policy successfully";
  }

  /**
   * set league game Schedule policy in specific season
   * @param league
   * @param season
   * @return
   */
  public  String setLeagueGameSchedualPolicy(League league, Season season, String gameSchedule){
//    policy.setGameSchedual(gameSchedule);
//    SLsettings sLsettings = league.getSLsettingsBySeason(season);
//    if(sLsettings!=null)
//      sLsettings.setPolicy(policy);
//    else{
//      sLsettings = new SLsettings(policy);
//      season.addSLsettingsToLeague(league,sLsettings);
//    }
    DataController.getInstance().updateLeaguePolicy(season,league,"GameSchedual",gameSchedule);
    Logger.getInstance().writeNewLine(this.getUsername()+" set League Game schedule Policy: " +(gameSchedule));
    return "Set league game schedule policy successfully";
  }

  public String setNewSeason(String year){
    Season season = new Season(year);
    DataController.getInstance().addSeason(season);
    Logger.getInstance().writeNewLine(this.getUsername()+" set new season: " +(season.getName()));
    return "Set new season successfully";
  }

  public void addAmountToAssociationBudget(double amount){

  }

  public void subtractAmountFromAssociationBudget(double amount){

  }

  /**
   * approve the opening of a team
   */
  public String approveTeam(String teamName, Owner owner)
  {
    Pair request=new Pair(owner,teamName);
    if(!checkIfRequestExists(owner,teamName)){
      Logger.getInstanceError().writeNewLineError(this.getUsername()+" approve team "+(teamName) +" failed");
      return "Request doesn't exist";
    }
    else
    {
      DataController.getInstance().approveTeam(owner,this,teamName);
      notifyAccount(owner.getUsername(),"You are approved to open team: "+teamName+" by "+getUsername());
//      OurSystem.notifyOtherRole("You are approved to open team: "+teamName,owner);
      Logger.getInstance().writeNewLine(getName()+" approved "+owner.getName()+" to open the team: "+teamName);

      return "Team approved successfully";
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
    if(checkIfRequestExists(owner,teamName))
      return false;
    else
      DataController.getInstance().addOpenTeamRequest(owner,teamName);
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
    if(!checkIfRequestExists(owner,teamName))
      return false;
    else
      DataController.getInstance().removeOpenTeamRequest(owner,teamName);
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
    return DataController.getInstance().checkIfRequestExists(owner,teamName);
  }

  /**
   * get the status of a pending request
   */
  public static boolean getRequestStatus(Owner owner, String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    return DataController.getInstance().getRequestStatus(owner,teamName);
  }

}