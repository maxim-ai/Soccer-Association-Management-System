
import java.util.*;

public class DataManager {

  private static List<Team> teams= new ArrayList<Team>();
  private static List<Account> accounts = new ArrayList<Account>();
  private static List<Page> pages = new ArrayList<Page>() ;
  private static List<League> leagues = new ArrayList<League>();
  private static List<Season> seasons = new ArrayList<Season>();
  private static List<Stadium> stadiums=new ArrayList<>();


  //region Teams Data Methods
  public static Team getTeam(int index) {
    Team aTeam = teams.get(index);
    return aTeam;
  }

  public static List<Team> getTeams() {
    List<Team> newTeams = Collections.unmodifiableList(teams);
    return newTeams;
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
    List<Account> newAccounts = Collections.unmodifiableList(accounts);
    return newAccounts;
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

  public static boolean removeAccount(Account aAccount) {
    boolean wasRemoved = true;
    accounts.remove(aAccount);
    wasRemoved = true;
    return wasRemoved;
  }
  //endregion

  //region Pages Data Methods
  public static Page getPage(int index) {
    Page aPage = pages.get(index);
    return aPage;
  }

  public static List<Page> getPages() {
    List<Page> newPages = Collections.unmodifiableList(pages);
    return newPages;
  }

  public static int numberOfPages() {
    int number = pages.size();
    return number;
  }

  public static boolean hasPages() {
    boolean has = pages.size() > 0;
    return has;
  }

  public static int indexOfPage(Page aPage) {
    int index = pages.indexOf(aPage);
    return index;
  }


  public static int minimumNumberOfPages() {
    return 0;
  }

  public static boolean addPage(Page aPage) {
    boolean wasAdded = true;
    if (pages.contains(aPage)) {
      return true;
    }
    pages.add(aPage);
    wasAdded = true;
    return wasAdded;
  }

  public static boolean removePage(Page aPage) {
    boolean wasRemoved = true;
    pages.remove(aPage);
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
    List<League> newLeagues = Collections.unmodifiableList(leagues);
    return newLeagues;
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
    List<Season> newSeasons = Collections.unmodifiableList(seasons);
    return newSeasons;
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
    for(Account account: DataManager.getAccounts()){
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
    for(Account account: DataManager.getAccounts()){
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
    for(Account account: DataManager.getAccounts()){
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
    for(Account account: DataManager.getAccounts()){
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
    for(Account account: DataManager.getAccounts()){
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
    List<Account> accounts=DataManager.getAccounts();
    if(accounts.size()==0) return null;
    for(Account account: DataManager.getAccounts()){
      for(Role role:account.getRoles()){
        if(role instanceof Fan){
          fans.add((Fan) role);
          break;
        }
      }
    }
    return fans;
  }
  //endregion




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

  public static void cleatDataBase(){
    teams= new ArrayList<Team>();
    accounts = new ArrayList<Account>();
    pages = new ArrayList<Page>() ;
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



}
