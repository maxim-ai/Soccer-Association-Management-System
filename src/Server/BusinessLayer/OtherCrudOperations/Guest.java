package Server.BusinessLayer.OtherCrudOperations;
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.Logger.Logger;
import Server.BusinessLayer.RoleCrudOperations.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Guest implements Serializable {


  private int id;
  private static int guestIDCounter=0;

  public Guest()
  {
    id=(++guestIDCounter);
  }



  //region Getters&&Setters
  public static int getGuestIDCounter() {
    return guestIDCounter;
  }

  public static void resetGuestIDCounter(){
    guestIDCounter=0;
  }

  public int getId()
  {
    return id;
  }

  public String toString()
  {
    return "BusinessLayer.OtherCrudOperations.Guest ID: "+id;
  }
  //endregion



  //region Methods for version 3
  public Account LogIn(String UserName, String Password) throws Exception {
    String[] accountInfo=DataController.getInstance().getUserNamePasswordDC(UserName);
    if(accountInfo==null){
      (Logger.getInstanceError()).writeNewLineError("Guest #"+this.id+"tried to log in with wrong username");
      throw new Exception("Username does not exist");
    }
    else if(accountInfo[0].equals(UserName)&&!accountInfo[1].equals(Password)){
      (Logger.getInstanceError()).writeNewLineError("Guest #"+this.id+"tried to log in with wrong password");
      throw new Exception("Wrong password");
    }
    else{
      Account account=new Account(accountInfo[2],Integer.parseInt(accountInfo[3]),accountInfo[0],accountInfo[1]);
      account.setLoggedIn(true);
      account=DataController.getInstance().getAccountRolesDC(account);
      (Logger.getInstance()).writeNewLine("Account "+UserName+" logged in");
      return account;
    }
//    List<Account> accountList = DataController.getInstance().getAccounts();
//    for(Account a:accountList){
//      if(a.getUserName().equals(UserName)){
//        if(a.getPassword().equals(Password)){
//          (Logger.getInstance()).writeNewLine("Account "+UserName+" logged in");
//          return a;
//        }
//        else{
//          (Logger.getInstanceError()).writeNewLineError("Guest #"+this.id+"tried to log in with wrong password");
//          throw new Exception("Wrong password");
//        }
//      }
//    }
//    (Logger.getInstanceError()).writeNewLineError("Guest #"+this.id+"tried to log in with wrong username");
//    throw new Exception("Username does not exist");
  }

  public void SignIn(String Name, int Age, String UserName, String Password) throws Exception {
    for(Account account: DataController.getInstance().getAccounts()){
      if(account.getUserName().equals(UserName)){
        (Logger.getInstanceError()).writeNewLineError("Guest #"+this.id+"tries to sign in with existing username");
        throw new Exception("Username already exists");
      }
    }
    Account newAccount=new Account(Name,Age,UserName,Password);
    newAccount.addRole(new Fan(newAccount.getName()));
    DataController.getInstance().addAccount(newAccount);
    (Logger.getInstance()).writeNewLine("New account "+UserName+" created");
  }
  //endregion

  //region Other UC methods
  public void ShowInfo(String InfoAbout){
    if(InfoAbout.equals("Teams")){
      for(Team team: DataController.getInstance().getTeams())
        team.ShowTeam();
    }

    if(InfoAbout.equals("Players")){
      List<Player> players= DataController.getInstance().getPlayersFromAccounts();
      for(Player player:players){
        player.ShowPlayer();
      }
    }
    if(InfoAbout.equals("Coaches")){
      List<Coach> coaches= DataController.getInstance().getCoachesFromAccounts();
      for(Coach coach:coaches){
        coach.ShowCoach();
      }
    }

    if(InfoAbout.equals("Leagues")){
      for(League league: DataController.getInstance().getLeagues())
        league.ShowLeague();
    }

    if(InfoAbout.equals("Seasons")){
      for(Season season: DataController.getInstance().getSeasons())
        season.ShowSeason();
    }

  }

  public void Search(String criterion, String query){
    if(criterion.equals("Name")){
      List<Team> teams=new LinkedList<>();
      for(Team team: DataController.getInstance().getTeams()){
        if(team.getName().equals(query))
          teams.add(team);
      }
      List<Account> accounts=new LinkedList<>();
      for(Account account: DataController.getInstance().getAccounts()){
        if(account.getName().equals(query))
          accounts.add(account);
      }
      List<League> leagues=new LinkedList<>();
      for(League league: DataController.getInstance().getLeagues()){
        if(league.getName().equals(query))
          leagues.add(league);
      }
      List<Season> seasons=new LinkedList<>();
      for(Season season: DataController.getInstance().getSeasons()){
        if(season.getName().equals(query))
          seasons.add(season);
      }

      if (!accounts.isEmpty()) {
        System.out.println("Accounts with the name "+query);
        for(Account account:accounts)
          account.ShowAccount();
      }
      if (!teams.isEmpty()) {
        System.out.println("Teams with the name "+query);
        for(Team team:teams)
          team.ShowTeam();
      }
      if (!leagues.isEmpty()) {
        System.out.println("Leagues with the name "+query);
        for(League league:leagues)
          league.ShowLeague();
      }
      if (!seasons.isEmpty()) {
        System.out.println("Seasons with the name "+query);
        for(Season season:seasons)
          season.ShowSeason();
      }

    }
    if(criterion.equals("Category")){
      if(query.equals("Teams")){
        for(Team team: DataController.getInstance().getTeams())
          team.ShowTeam();
      }
      if(query.equals("Accounts")){
        for(Account account: DataController.getInstance().getAccounts())
          account.ShowAccount();
      }
      if(query.equals("Leagues")){
        for(League league: DataController.getInstance().getLeagues())
          league.ShowLeague();
      }
      if(query.equals("Seasons")){
        for(Season season: DataController.getInstance().getSeasons())
          season.ShowSeason();
      }
    }
  }

  public void Filter(String category,String roleFilter){
    if(category.equals("BusinessLayer.RoleCrudOperations.Role")){
      if(roleFilter.equals("Players")){
        List<Player> players= DataController.getInstance().getPlayersFromAccounts();
        for(Player player:players)
          player.ShowPlayer();
      }

      if(roleFilter.equals("Coaches")){
        List<Coach> coaches= DataController.getInstance().getCoachesFromAccounts();
        for(Coach coach:coaches)
          coach.ShowCoach();
      }

      if(roleFilter.equals("TeamManagers")){
        List<TeamManager> tms= DataController.getInstance().getTeamManagersFromAccounts();
        for(TeamManager tm:tms)
          tm.ShowTeamManager();
      }

      if(roleFilter.equals("Owners")){
        List<Owner> owners= DataController.getInstance().getOwnersFromAccounts();
        for(Owner owner:owners)
          owner.ShowOwner();
      }

      if(roleFilter.equals("Referees")){//************************************
        List<Referee> refs= DataController.getInstance().getRefereesFromAccounts();
        for(Referee ref:refs)
          ref.ShowReferee();
      }

    }
    if(category.equals("BusinessLayer.OtherCrudOperations.Team")){
      for(Team team: DataController.getInstance().getTeams())
        team.ShowTeam();
    }
    if(category.equals("BusinessLayer.OtherCrudOperations.League")){
      for(League league: DataController.getInstance().getLeagues())
        league.ShowLeague();
    }
    if(category.equals("BusinessLayer.OtherCrudOperations.Season")){
      for(Season season: DataController.getInstance().getSeasons())
        season.ShowSeason();
    }
  }
  //endregion








}