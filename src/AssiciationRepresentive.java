import javafx.util.Pair;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.*;


public class AssiciationRepresentive extends Role
{
  private static HashMap<Pair<Owner,String>,Boolean> approvedTeams=new HashMap<>();

  public AssiciationRepresentive(String aName)
  {
    super(aName);
  }

  /**
   * create new league and put teams inside
   * @param name
   * @param teams
   * @return
   */
  public League createNewLeague(String name,List<Team> teams){
    if(teams == null || name == null){
      return  null;
    }
    League league = new League(name);
    DataManager.addLeague(league);
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
  public boolean setYearToLeague(League league,String year){
    boolean wasSet = false;
    if(league == null || year == null){
      return  wasSet;
    }
    Season season = new Season(year);
    DataManager.addSeason(season);
    wasSet = league.addSLsettingsToSeason(season,null);
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
   * add new referee to system
   * @param training
   */
  public Referee addNewReferee(String training, String name){
    if(training != null && name != null) {
      return  DataManager.CreateNewReferee(training,name);
    }
    return null;
  }

  /**
   * add referee to a league in specific season , add referee to SLsetting referee list
   * @param league
   * @param referee
   * @param season
   * @return
   */
  public boolean addRefereeToLeague(Referee referee,League league,Season season){ // to fix uc
    boolean wasAdded = false;
    if(league == null || referee == null || season == null){
      return  wasAdded;
    }
    wasAdded= referee.addLeague(league,season);
    SLsettings refSLsettings = referee.getsLsettings();
    refSLsettings.addReferee(referee);
    return  wasAdded;
  }

  /**
   * set league pointCalc policy in specific season
   * @param league
   * @param policy
   * @param season
   * @return
   */
  public  boolean setLeaguePointCalcPolicy(League league, Policy policy,Season season,String pointCalc){
    boolean wasAdded = false;
    if(league == null || policy == null || season == null){
      return  wasAdded;
    }
    policy.setPointCalc(pointCalc);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    sLsettings.setPolicy(policy);
    return true;
  }

  /**
   * set league game Schedule policy in specific season
   * @param league
   * @param policy
   * @param season
   * @return
   */
  public  boolean setLeagueGameSchedualPolicy(League league, Policy policy,Season season,String gameSchedule){
    boolean wasAdded = false;
    if(league == null || policy == null || season == null){
      return  wasAdded;
    }
    policy.setGameSchedual(gameSchedule);
    SLsettings sLsettings = league.getSLsettingsBySeason(season);
    sLsettings.setPolicy(policy);
    return true;
  }

  /**
   * approve the opening of a team
   */
  public boolean approveTeam(String teamName,Owner owner)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);

    if(!approvedTeams.containsKey(request))
      return false;
    else
    {
      approvedTeams.put(request,true);
      Alert.notifyOtherRole("You are approved to open team: "+teamName,owner);
      Logger.getInstance().writeNewLine(getName()+" approved "+owner.getName()+" to open the team: "+teamName);
      return true;
    }
  }

  /**
   * add a request for opening a team
   */
  public static boolean addOpenTeamRequest(Owner owner,String teamName)
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
  public static boolean removeOpenTeamRequest(Owner owner,String teamName)
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
  public static boolean checkIfRequestExists(Owner owner,String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    return approvedTeams.containsKey(request);
  }

  /**
   * get the status of a pending request
   */
  public static boolean getRequestStatus(Owner owner,String teamName)
  {
    if(teamName==null || owner==null)
      return false;

    Pair request=new Pair(owner,teamName);
    return approvedTeams.get(request);
  }






}