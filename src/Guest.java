import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Guest {


  private String id;

  public Guest(String aId)
  {
    id = aId;
  }

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String toString()
  {
    return super.toString() + "["+ "id" + ":" + getId()+ "]";
  }

  public Account LogIn(String UserName, String Password){
    List<Account> accountList=Controller.getAccounts();
    for(Account a:accountList){
      if(a.getUserName().equals(UserName)&&a.getPassword().equals(Password))
        return a;
    }
    return null;
  }

  public void SignIn(String Name, int Age, String UserName, String Password){
    Account newAccount=new Account(Name,Age,UserName,Password);
    newAccount.addRole(new Fan(newAccount.getName()));
    Controller.addAccount(newAccount);
  }

  public void ShowInfo(String InfoAbout){
    if(InfoAbout.equals("Teams")){
      for(Team team:Controller.getTeams())
        ShowTeam(team);
    }

    if(InfoAbout.equals("Players")){
      List<Player> players= Controller.getPlayersFromAccounts();
      for(Player player:players){
        ShowPlayer(player);
      }
    }
    if(InfoAbout.equals("Coaches")){
      List<Coach> coaches= Controller.getCoachesFromAccounts();
      for(Coach coach:coaches){
        ShowCoach(coach);
      }
    }

    if(InfoAbout.equals("Leagues")){
      for(League league:Controller.getLeagues())
        ShowLeague(league);
    }

    if(InfoAbout.equals("Seasons")){
      for(Season season:Controller.getSeasons())
        ShowSeason(season);
    }

  }

  //Need to be Changed//
  public void Search(String criterion, String query){
    if(criterion.equals("Name")){
      List<Team> teams=new LinkedList<>();
      for(Team team:Controller.getTeams()){
        if(team.getName().equals(query))
          teams.add(team);
      }
      List<Account> accounts=new LinkedList<>();
      for(Account account:Controller.getAccounts()){
        if(account.getName().equals(query))
          accounts.add(account);
      }
      List<League> leagues=new LinkedList<>();
      for(League league:Controller.getLeagues()){
        if(league.getName().equals(query))
          leagues.add(league);
      }
      List<Season> seasons=new LinkedList<>();
      for(Season season:Controller.getSeasons()){
        if(season.getName().equals(query))
          seasons.add(season);
      }

      System.out.println("Teams with the name: "+query);
      for(Team team:teams)
        ShowTeam(team);
      System.out.println("Accounts with the name:"+query);
      for(Account account:accounts)
        ShowAccount(account);
      System.out.println("Leagues with the name: "+query);
      for(League league:leagues)
        ShowLeague(league);
      System.out.println("Seasons with the name: "+query);
      for(Season season:seasons)
        ShowSeason(season);

    }
    if(criterion.equals("Category")){
      if(query.equals("Teams")){
        for(Team team:Controller.getTeams())
          ShowTeam(team);
      }
      if(query.equals("Accounts")){
        for(Account account:Controller.getAccounts())
          ShowAccount(account);
      }
      if(query.equals("Leagues")){
        for(League league:Controller.getLeagues())
          ShowLeague(league);
      }
      if(query.equals("Seasons")){
        for(Season season:Controller.getSeasons())
          ShowSeason(season);
      }
    }
//    if(criterion.equals("Keyword")){ } //***********************************************************
  }

  //Need to be Changed//
  public void Filter(String category,String roleFilter){
    if(category.equals("Role")){
      if(roleFilter.equals("Players")){
        List<Player> players= Controller.getPlayersFromAccounts();
        for(Player player:players)
          ShowPlayer(player);
      }

      if(roleFilter.equals("Coaches")){
        List<Coach> coaches=Controller.getCoachesFromAccounts();
        for(Coach coach:coaches)
          ShowCoach(coach);
      }

      if(roleFilter.equals("TeamManagers")){
        List<TeamManager> tms=Controller.getTeamManagersFromAccounts();
        for(TeamManager tm:tms)
          ShowTeamManager(tm);
      }

      if(roleFilter.equals("Owners")){
        List<Owner> owners=Controller.getOwnersFromAccounts();
        for(Owner owner:owners)
          ShowOwner(owner);
      }

      if(roleFilter.equals("Referee")){//************************************

      }

    }
    if(category.equals("Team")){
      for(Team team:Controller.getTeams())
        ShowTeam(team);
    }
    if(category.equals("League")){
      for(League league:Controller.getLeagues())
        ShowLeague(league);
    }
    if(category.equals("Season")){
      for(Season season:Controller.getSeasons())
        ShowSeason(season);
    }
  }



  public void ShowSeason(Season season) {
    System.out.println("Name:");
    System.out.println(season.getName());
    System.out.println();
    System.out.println("Matches:");
    for(Match match:season.getMatchs())
      System.out.println(match.getHomeTeam()+" against "+match.getAwayTeam());
  }

  //Need to be Changed//
  public void ShowLeague(League league) {

    System.out.println("Name:");
    System.out.println(league.getName());
    for(Team team:league.getTeams())
      System.out.println(team.getName());
    System.out.println("SHOULD PRINT INFO ABOUT LEAGUE'S REFEREES"); //*****************************************
  }

  public void ShowCoach(Coach coach) {
    System.out.println("Name:");
    System.out.println(coach.getName());
    System.out.println();
    System.out.println("Training:");
    System.out.println(coach.getTraining());
    System.out.println();
    System.out.println("TeamRole:");
    System.out.println(coach.getTeamRole());
    System.out.println();
    System.out.println("TeamsCoaching:");
    for(Team team:coach.getTeams())
      System.out.println(team.getName());
  }

  public void ShowPlayer(Player player) {
    System.out.println("Name:");
    System.out.println(player.getName());
    System.out.println();
    System.out.println("Age:");
    int age=2020-player.getBirthday().getYear();
    System.out.println(age);
    System.out.println();
    System.out.println("Position:");
    System.out.println(player.getPosition());
    System.out.println();
    System.out.println("Team:");
    System.out.println(player.getTeam().getName());
  }

  public void ShowTeamManager(TeamManager tm){
    System.out.println("Name:");
    System.out.println(tm.getName());
    System.out.println();
    System.out.println("Team managed:");
    System.out.println(tm.getTeam().getName());

  }

  public void ShowOwner(Owner owner){
    System.out.println("Name:");
    System.out.println(owner.getName());
    System.out.println("Team owned:");
    System.out.println(owner.getTeam().getName());
  }

  public void ShowTeam(Team team) {
    System.out.println("Name:");
    System.out.println(team.getName());
    System.out.println();
    System.out.println("TeamManagers:");
    for(TeamManager teamManager:team.getTeamManagers())
      System.out.println(teamManager.getName());
    System.out.println();
    System.out.println("Coaches");
    for(Coach coach:team.getCoachs())
      System.out.println(coach.getName());
    System.out.println();
    System.out.println("TeamOwners:");
    for(Owner owner:team.getOwners())
      System.out.println(owner.getName());
    System.out.println();
    System.out.println("Players:");
    for(Player player:team.getPlayers())
      System.out.println(player.getName());
    System.out.println();
    System.out.println("League:");
    System.out.println(team.getLeague().getName());
    System.out.println();
    System.out.println("Matches:");
    for(Match match:team.getMatchs()){
      System.out.print(team.getName()+" against ");
      if(match.getAwayTeam().getName().equals(team.getName()))
        System.out.println(match.getHomeTeam().getName());
      else
        System.out.println(match.getAwayTeam().getName());
    }
    System.out.println();
    System.out.println("Stadium:");
    System.out.println(team.getStadium().getName());
    System.out.println();
  }

  public void ShowAccount(Account account){
    System.out.println("Name:");
    System.out.println(account.getName());
    System.out.println();
    System.out.println("Age:");
    System.out.println(account.getAge());
    System.out.println();
    System.out.println("Roles:");
    for(Role role:account.getRoles())
      System.out.println(role.getClass().getName());
  }

}