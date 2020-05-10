package BusinessLayer;

import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.*;
import DataLayer.DBAdapter;
import ServiceLayer.RoleController.*;

import java.sql.Date;
import java.util.*;

public class DataController {

  private static List<Account> accounts = new ArrayList<Account>();
  private static List<Team> teams= new ArrayList<Team>();
  private static List<League> leagues = new ArrayList<League>();
  private static List<Season> seasons = new ArrayList<Season>();
  private static List<Stadium> stadiums=new ArrayList<>();





  //region Teams Data Methods
  public static Team getTeam(int index) {
    Team aTeam = teams.get(index);
    return aTeam;
  }

  public static List<Team> getTeams(){return teams;}

  public static List<String> getTeamNames() {
    return DBAdapter.getTeamNames();
  }

  public static int numberOfTeams() {
    int number = teams.size();
    return number;
  }

  public static boolean hasTeams() {
    boolean has = teams.size() > 0;
    return has;
  }

  public static int indexOfTeam(Team aTeam) {
    int index = teams.indexOf(aTeam);
    return index;
  }

  public static int minimumNumberOfTeams() {
    return 0;
  }

  public static boolean addTeam(Team aTeam) {
    boolean wasAdded = true;
    if (teams.contains(aTeam)) {
      return true;
    }
    teams.add(aTeam);
    wasAdded = true;
    return wasAdded;
  }

  public static void addTeamDC(Team team){
    DBAdapter.addPage(team.getPage().getPageID());
    DBAdapter.addTeam(team.getName(),team.getPage().getPageID(),team.getLeague().getName(),team.getStadium().getName(),team.getPoints());
  }

  public static void addMatchDC(Match match){
    DBAdapter.addMatch(match.getDate(),match.getTime(),
            match.getAwayScore(),match.getHomeScore(),match.getAwayTeam().getName(),match.getHomeTeam().getName(),
            match.getMainReferee().getUsername(),match.getLineRefereeOne().getUsername(),match.getLineRefereeTwo().getUsername(),
            match.getStadium().getName(),match.getSeason().getName());
  }

  public static void addMatchWithoutRefsDC(Match match){
    DBAdapter.addMatch(match.getDate(),match.getTime(),
            match.getAwayScore(),match.getHomeScore(),match.getAwayTeam().getName(),match.getHomeTeam().getName(),
            null,null,null,
            match.getStadium().getName(),match.getSeason().getName());

    List<GameEvent> eventList=match.getEventCalender().getGameEvents();
    for(GameEvent GE:eventList)
      DBAdapter.addGameEvent(GE.getType().toString(),GE.getHour(),GE.getDescription(),GE.getGameMinute(),match.getDate(),
              match.getAwayTeam().getName(),match.getHomeTeam().getName());
  }

  public static void addRefToMatchDC(Match match,Referee ref1,Referee ref2,Referee ref3){
    DBAdapter.addRefsToMatch(match.getDate(),match.getTime(),match.getAwayTeam().getName(),
            match.getHomeTeam().getName(),ref1.getUsername(),ref2.getUsername(),ref3.getUsername());
  }




  public static boolean removeTeam(Team aTeam) {
    boolean wasRemoved = true;
    teams.remove(aTeam);
    wasRemoved = true;
    return wasRemoved;
  }

  //endregion

  //region Accounts Data Methods
  public static Account getAccount(int index) {
    Account aAccount = accounts.get(index);
    return aAccount;
  }

  public static List<Account> getAccounts() {
    return accounts;
  }

  public static int numberOfAccounts() {
    int number = accounts.size();
    return number;
  }

  public static boolean hasAccounts() {
    boolean has = accounts.size() > 0;
    return has;
  }

  public static int indexOfAccount(Account aAccount) {
    int index = accounts.indexOf(aAccount);
    return index;
  }

  public static int minimumNumberOfAccounts() {
    return 0;
  }

  public static boolean addAccount(Account aAccount) {


    boolean wasAdded = true;
    if (accounts.contains(aAccount)) {
      return true;
    }
    accounts.add(aAccount);
    wasAdded = true;
    return wasAdded;
  }

  public static void addAccountDC(Account account){
    List<Role> roles=account.getRoles();
    List<String> alerts=new ArrayList<>();
    DBAdapter.addAccount(account.getUserName(),account.getPassword(),account.getName(),account.getAge());

    for(Role role: roles){
      for(Alert alert:role.getAlertList())
        alerts.add(alert.getDescription());
      DBAdapter.addAlert(account.getUserName(),alerts);
      if(role instanceof Owner)
        DBAdapter.addOwnerRole(account.getUserName(),role.getName(),((Owner) role).getTeam().getName(),((Owner) role).getAppointer().getUsername());
      else if(role instanceof Player){
        DBAdapter.addPage(((Player) role).getPage().getPageID());
        DBAdapter.addPlayerRole(account.getUserName(),role.getName(),new Date(((Player) role).getBirthday().getYear(),((Player) role).getBirthday().getMonth(),((Player) role).getBirthday().getMonth()),
                ((Player) role).getPosition().toString(), ((Player) role).getTeam().getName(),((Player) role).getPage().getPageID());
      }
      else if(role instanceof Fan){
        List<Integer> pagesIDs=new ArrayList<>();
        for(Page page:((Fan) role).getPages())
          pagesIDs.add(new Integer(page.getPageID()));
        DBAdapter.addFanRole(account.getUserName(),role.getName(),((Fan) role).isTrackPersonalPages(),((Fan) role).isGetMatchNotifications(),pagesIDs);
      }
      else if(role instanceof Referee){
        HashMap<String,String> refLeaguesAndSeasons=new HashMap<>();
        for(Map.Entry<League,Season> entry: ((Referee) role).getLeagues().entrySet())
          refLeaguesAndSeasons.put(entry.getKey().getName(),entry.getValue().getName());
        DBAdapter.addRefereeRole(account.getUserName(),role.getName(),((Referee) role).getTraining(),refLeaguesAndSeasons);
      }
      else if(role instanceof TeamManager){
        List<String> permissions=new ArrayList<>();
        for(TeamManager.PermissionEnum permissionEnum:((TeamManager) role).getAppointer().getAllPermitions((TeamManager) role))
          permissions.add(permissionEnum.toString());
        DBAdapter.addTeamManagerRole(account.getUserName(),role.getName(),((TeamManager) role).getTeam().getName(),
                ((TeamManager) role).getAppointer().getUsername(),permissions);
      }
      else if(role instanceof SystemManager)
        DBAdapter.addSystemManagerRole(account.getUserName(),role.getName(),((SystemManager) role).getComplaintAndComments());
      else if(role instanceof AssociationRepresentative)
        DBAdapter.addAssociationRepresentativeRole(account.getUserName(),role.getName());
      else if(role instanceof Coach){
        DBAdapter.addPage(((Coach) role).getPage().getPageID());
        DBAdapter.addCoachRole(account.getUserName(),role.getName(),((Coach) role).getTraining(),((Coach) role).getTeamRole(),
                ((Coach) role).getPage().getPageID(),((Coach) role).getTeams().get(0).getName());
      }

    }
  }

  public static boolean removeAccount(Account aAccount) {
    boolean wasRemoved = true;
    accounts.remove(aAccount);
    wasRemoved = true;
    return wasRemoved;
  }
  //endregion



  //region League Data Methods
  public static League getLeague(int index) {
    League aLeague = leagues.get(index);
    return aLeague;
  }

  public static List<League> getLeagues() {
    return leagues;
  }

  public static int numberOfLeagues() {
    int number = leagues.size();
    return number;
  }

  public static boolean hasLeagues() {
    boolean has = leagues.size() > 0;
    return has;
  }

  public static int indexOfLeague(League aLeague) {
    int index = leagues.indexOf(aLeague);
    return index;
  }

  public static int minimumNumberOfLeagues() {
    return 0;
  }

  public static boolean addLeague(League aLeague) {
    boolean wasAdded = true;
    if (leagues.contains(aLeague)) {
      return true;
    }
    leagues.add(aLeague);
    wasAdded = true;
    return wasAdded;
  }

  public static void addLeagueDC(League league){
    List<String> seasonList=new ArrayList<>();
    List<String[]> policyList=new ArrayList<>();
    for(Map.Entry<Season,SLsettings> entry:league.getsLsetting().entrySet()){
      seasonList.add(entry.getKey().getName());
      Policy policy=entry.getValue().getPolicy();
      String[] arr={policy.getPointCalc(),policy.getGameSchedual(),policy.getId()};
      policyList.add(arr);
    }
    DBAdapter.addLeague(league.getName(),seasonList,policyList);
  }

  public static boolean removeLeague(League aLeague) {
    boolean wasRemoved = true;
    if (!leagues.contains(aLeague)) {
      return wasRemoved;
    }
    wasRemoved = true;
    leagues.remove(aLeague);
    return wasRemoved;
  }

  //endregion

  //region Seasons Data Methods
  public static Season getSeason(int index) {
    Season aSeason = seasons.get(index);
    return aSeason;
  }

  public static List<Season> getSeasons() {
    return seasons;
  }

  public static int numberOfSeasons() {
    int number = seasons.size();
    return number;
  }

  public static boolean hasSeasons() {
    boolean has = seasons.size() > 0;
    return has;
  }

  public static int indexOfSeason(Season aSeason) {
    int index = seasons.indexOf(aSeason);
    return index;
  }


  public static int minimumNumberOfSeasons() {
    return 0;
  }

  public static boolean addSeason(Season aSeason) {
    boolean wasAdded = true;
    if (seasons.contains(aSeason)) {
      return true;
    }
    seasons.add(aSeason);
    wasAdded = true;
    return wasAdded;
  }

  public static void addSeasonDC(Season season){
    List<String> leagueList=new ArrayList<>();
    List<String[]> policyList=new ArrayList<>();
    for(Map.Entry<League,SLsettings> entry:season.getsLsettings().entrySet()){
      leagueList.add(entry.getKey().getName());
      Policy policy=entry.getValue().getPolicy();
      String[] arr={policy.getPointCalc(),policy.getGameSchedual(),policy.getId()};
      policyList.add(arr);
    }
    DBAdapter.addSeason(season.getName(),leagueList,policyList);
  }

  public static boolean removeSeason(Season aSeason) {
    boolean wasRemoved = true;
    seasons.remove(aSeason);
    wasRemoved = true;
    return wasRemoved;
  }

  //endregion

  //region Stadiums Data Methods
  public static Stadium getStadium(int index) {
    return stadiums.get(index);
  }

  public static List<Stadium> getStadiums() {
    return stadiums;
  }

  public static int numberOfStadiums() {
    return stadiums.size();
  }

  public static boolean hasStadiums() {
    return stadiums.isEmpty();
  }

  public static int indexOfStadium(Team aTeam) {
    return stadiums.indexOf(aTeam);
  }

  public static int minimumNumberOfStadiums() {
    return 0;
  }

  public static boolean addStadium(Stadium aStadium) {
    boolean wasAdded = true;
    if (stadiums.contains(aStadium)) {
      return true;
    }
    stadiums.add(aStadium);
    wasAdded = true;
    return wasAdded;
  }

  public static void addStadiumDC(Stadium stadium){
    DBAdapter.addStadium(stadium.getName());
  }

  public static boolean removeStadium(Stadium aStadium) {
    boolean wasRemoved = true;
    stadiums.remove(aStadium);
    wasRemoved = true;
    return wasRemoved;
    //endregion
  }
  //endregion

  //region Get Roles From Accounts
  public static List<Player> getPlayersFromAccounts(){
    List<Player> players=new LinkedList<>();
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Player){
          players.add((Player)role);
          break;
        }
      }
    }
    return players;
  }

  public static List<Coach> getCoachesFromAccounts(){
    List<Coach> coaches=new LinkedList<>();
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Coach){
          coaches.add((Coach)role);
          break;
        }
      }
    }
    return coaches;
  }

  public static List<TeamManager> getTeamManagersFromAccounts(){
    List<TeamManager> tms=new LinkedList<>();
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof TeamManager){
          tms.add((TeamManager) role);
          break;
        }
      }
    }
    return tms;
  }

  public static List<Owner> getOwnersFromAccounts(){
    List<Owner> owners=new LinkedList<>();
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Owner){
          owners.add((Owner) role);
          break;
        }
      }
    }
    return owners;
  }

  public static List<Referee> getRefereesFromAccounts(){
    List<Referee> refs=new LinkedList<>();
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Referee){
          refs.add((Referee) role);
          break;
        }
      }
    }
    return refs;
  }

  public static List<Fan> getFansFromAccounts(){
    List<Fan> fans=new LinkedList<>();
    List<Account> accounts= DataController.getAccounts();
    if(accounts.size()==0) return null;
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Fan){
          fans.add((Fan) role);
          break;
        }
      }
    }
    return fans;
  }

  public static List<AssociationRepresentative> getAssiciationRepresentivesFromAccounts(){
    List<AssociationRepresentative> assiciationRepresentives=new LinkedList<>();
    List<Account> accounts= DataController.getAccounts();
    if(accounts.size()==0) return null;
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof AssociationRepresentative){
          assiciationRepresentives.add((AssociationRepresentative) role);
          break;
        }
      }
    }
    return assiciationRepresentives;
  }

  public static List<SystemManager> getSystemManagersFromAccounts() {
    List<SystemManager> systemManagers=new LinkedList<>();
    List<Account> accounts= DataController.getAccounts();
    if(accounts.size()==0) return null;
    for(Account account: DataController.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof SystemManager){
          systemManagers.add((SystemManager) role);
          break;
        }
      }
    }
    return systemManagers;
  }
  //endregion


  public static void setAccounts(List<Account> accounts) {
    DataController.accounts = accounts;
  }

  public static void setTeams(List<Team> teams) {
    DataController.teams = teams;
  }

  public static void setLeagues(List<League> leagues) {
    DataController.leagues = leagues;
  }

  public static void setSeasons(List<Season> seasons) {
    DataController.seasons = seasons;
  }

  public static void setStadiums(List<Stadium> stadiums) {
    DataController.stadiums = stadiums;
  }

  public static void saveAction(Team team) {
  }

  public static void notifyOnDelete(Team team) {

  }

  /**
   *
   */

  /**
   * create new referee
   *
   * @param training
   * @param name
   */
  public static Referee CreateNewReferee(String training, String name) {
    Referee referee = new Referee(training, name);
    sendInvitation(referee);
    return referee;

  }

  /**
   * send invitation to the referee
   *
   * @param referee
   */
  private static void sendInvitation(Referee referee) {

  }


  /**
   * delete team's page from all fans of the team (follower)
   *
   * @param team
   */
  public static void deleteFromAllFollowers(Team team) {
    Page teamPageToDelete = team.getPage();
    List<Fan> fans = teamPageToDelete.getFans();
    for (Fan fan : fans) {
      for (Page page : fan.getPages()) {
        if (page.equals(teamPageToDelete)) {
          page.delete();
          break;
        }
      }
    }
  }

  public static void clearDataBase(){
    teams= new ArrayList<Team>();
    accounts = new ArrayList<Account>();
    leagues = new ArrayList<League>();
    seasons = new ArrayList<Season>();
    stadiums=new ArrayList<>();
  }

  public static Account getAccountByRole(Role role){
    for(Account account:accounts){
      for(Role currRole:account.getRoles()){
        if(role.equals(currRole))
          return account;
      }
    }
    return null;
  }


  public static String[] getUserNamePasswordDC(String userName) {
    return DBAdapter.getUserNamePasswordDC(userName);
  }

  public static Account getAccountRolesDC(Account account) {
    List<String> roles=DBAdapter.getAccountRoles(account.getUserName());
    for(String role:roles){
      if(role.equals("Owner"))
        account.addRole(new Owner(account.getName()));
      else if(role.equals("TeamManager"))
        account.addRole(new TeamManager(account.getName()));
      else if(role.equals("AssociationRepresentative"))
        account.addRole(new AssociationRepresentative(account.getName()));
      else if(role.equals("SystemManager"))
        account.addRole(new SystemManager(account.getName()));
      else if(role.equals("Player"))
        account.addRole(new Player(account.getName()));
      else if(role.equals("Referee"))
        account.addRole(new Referee(account.getName()));
      else if(role.equals("Coach"))
        account.addRole(new Coach(account.getName()));
      else if(role.equals("Fan"))
        account.addRole(new Fan(account.getName()));
    }
    return account;
  }
}
