package Client.ServiceLayer;
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.Logger.Logger;
import Server.BusinessLayer.OtherCrudOperations.*;
import Server.BusinessLayer.Pages.Page;
import Server.BusinessLayer.RoleCrudOperations.*;
import Client.ServiceLayer.GuestController.GuestController;
import Client.ServiceLayer.RoleController.*;


import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OurSystem {

    private static SystemManager SM;

    private static List<Guest> currGuests;
    private static List<Account> currAccounts;

    public OurSystem(){
        currGuests=new ArrayList<>();
        currAccounts=new ArrayList<>();
    }

//    public static ArrayList<String> getOptions(String substring) {
//        ArrayList<String> strings=new ArrayList<>();
//        if(substring.equals("League"))
//        {
//            strings.add("LeagueOne");
//            strings.add("LeagueTwo");
//            strings.add("LeagueThree");
//            strings.add("LeagueFour");
//        }
//        else if(substring.equals("Season"))
//        {
//            strings.add("SeasonOne");
//            strings.add("SeasonTwo");
//            strings.add("SeasonThree");
//            strings.add("SeasonFour");
//        }
//
//        return strings;
//    }

    //region Initialize the System
    public void Initialize() {
        System.out.println("Established connection to Accounty System");
        System.out.println("Established connection to Federal Tax System");

        File checkFile=new File("firstInitCheck");
//        if(!checkFile.exists()){
            try { checkFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
            Account SMaccount=new Account("Nadav",26,"NadavX","1234");
            SM=new SystemManager(SMaccount.getName());
            SMaccount.addRole(SM);
            DataController.addAccount(SMaccount);
            (Logger.getInstance()).writeNewLine("System has been initialized");
            try { InitDatabase(); } catch (Exception e) { e.printStackTrace(); }
        //}

    }
    //endregion



    //region Getters&Setters Method
    public static SystemManager getSM() {
        return SM;
    }


    public static void setSM(SystemManager SM) {
        OurSystem.SM = SM;
        (Logger.getInstance()).writeNewLine("New system manager "+SM.getName()+" has been set");
    }


    public static List<Guest> getCurrGuests() {
        return currGuests;
    }

    public static List<Account> getCurrAccounts() {
        return currAccounts;
    }

    public static void removeGuest(Guest guest){
        for(Guest currGuest:currGuests){
            if(currGuest.equals(guest)){
                currGuests.remove(guest);
                break;
            }
        }
    }

    public static void removeAccount(Account account){
        for(Account currAccount:currAccounts){
            if(currAccount.equals(account)){
                currAccounts.remove(account);
                break;
            }
        }
    }

    public static void addGuest(Guest guest){
        currGuests.add(guest);
    }

    public static void addAccount(Account account){
        currAccounts.add(account);
    }
    //endregion

//    public static List<Object> makeControllersByRoles(Account account){
//
//        List<Object> controllerList=new ArrayList<>();
//        for(Role role:account.getRoles()){
//            if(role instanceof Owner)
//                controllerList.add(new OwnerController((Owner)role));
//            else if(role instanceof TeamManager)
//                controllerList.add(new TeamManagerController((TeamManager)role));
//            else if(role instanceof AssociationRepresentative)
//                controllerList.add(new AssociationRepresentativeController((AssociationRepresentative)role));
//            else if(role instanceof SystemManager)
//                controllerList.add(new SystemManagerController((SystemManager)role));
//            else if(role instanceof Player)
//                controllerList.add(new PlayerController((Player)role));
//            else if(role instanceof Referee)
//                controllerList.add(new RefereeController((Referee)role));
//            else if(role instanceof Coach)
//                controllerList.add(new CoachController((Coach)role));
//            else if(role instanceof Fan)
//                controllerList.add(new FanContoller((Fan)role));
//        }
//
//        return controllerList;
//
//    }

    public static GuestController makeGuestController(){
        return new GuestController();
    }

    public static void notifyOtherRole(String notification, Role role){
        Alert alert=new Alert(notification);
        role.addAlert(alert);
    }

    public static List<String> getDropList(String string,List<Object> controllers,List<String> arguments){
        List<String> list=new ArrayList<>();
        if(string.equals("Team")){
            List<Team> teams= DataController.getTeams();
            for(Team team:teams)
                list.add(team.getName());
        }
        else if(string.equals("Season")){
            List<Season> seasons=DataController.getSeasons();
            for(Season season:seasons)
                list.add(season.getName());
        }
        else if(string.equals("League")){
            List<League> leagues=DataController.getLeagues();
            for(League league:leagues)
                list.add(league.getName());
        }
        else if(string.equals("Stadium")){
            List<Stadium> stadiums=DataController.getStadiums();
            for(Stadium stadium:stadiums)
                list.add(stadium.getName());
        }
        else if(string.equals("EventEnum")){
            for(EventEnum eventEnum:EventEnum.values())
                list.add(eventEnum.toString());
        }
        else if(string.equals("Match")){
            list=((RefereeController)controllers.get(0)).getMatchList();
        }
        else if(string.equals("GameEvent")){
            list=((RefereeController)controllers.get(0)).getEvantsByMatch(arguments.get(0));
        }
        else if(string.equals("Referee")){
            List<Referee> refs=DataController.getRefereesFromAccounts();
            for(Referee ref:refs)
                list.add(ref.getName());
        }
        else if(string.equals("Account")){
            List<Account> accounts=DataController.getAccounts();
            for(Account account:accounts)
                list.add(account.getUserName());
        }

        return list;

    }

    public  void InitDatabase() throws Exception {
        Account arAccount1, arAccount2;
        AssociationRepresentative ar1, ar2;
        League league1, league2;
        Season season;
        Policy policy1, policy2;
        Account refAccount1, refAccount2, refAccount3, refAccount4, refAccount5, refAccount6;
        Referee referee1, referee2, referee3, referee4, referee5, referee6;
        SLsettings sLsettings1, sLsettings2;
        Account ownerAccount1, ownerAccount2, ownerAccount3, ownerAccount4;
        Owner owner1, owner2, owner3, owner4;
        Account tmAccount1, tmAccount2, tmAccount3, tmAccount4;
        TeamManager tm1, tm2, tm3, tm4;
        Account playerAccount1, playerAccount2, playerAccount3, playerAccount4, playerAccount5, playerAccount6, playerAccount7, playerAccount8;
        Player player1, player2, player3, player4, player5, player6, player7, player8;
        Match match1, match2;
        Team team1, team2, team3, team4;
        Stadium stadium1, stadium2, stadium3, stadium4;
        Account coachAccount1, coachAccount2, coachAccount3, coachAccount4;
        Coach coach1, coach2, coach3, coach4;
        Page playerPage1, playerPage2, playerPage3, playerPage4, playerPage5, playerPage6, playerPage7, playerPage8,
                coachPage1, coachPage2, coachPage3, coachPage4, teamPage1, teamPage2, teamPage3, teamPage4;
        Account fanAccount1, fanAccount2, fanAccount3, fanAccount4, fanAccount5, fanAccount6, fanAccount7, fanAccount8;
        Fan fan1, fan2, fan3, fan4, fan5, fan6, fan7, fan8;

        //region AssociationRepresantives creations
        arAccount1 = new Account("AR1", 99, "AR1X", "Password");
        ar1 = new AssociationRepresentative("AR1");
        arAccount1.addRole(ar1);

        arAccount2 = new Account("AR2", 99, "AR2X", "Password");
        ar2 = new AssociationRepresentative("AR2");
        arAccount2.addRole(ar2);
        //endregion

        //region Referees creation
        refAccount1 = new Account("Referee1", 99, "Referee1X", "Password");
        referee1 = ar1.createNewReferee(refAccount1, "Complete", "Referee1");

        refAccount2 = new Account("Referee2", 99, "Referee2X", "Password");
        referee2 = ar1.createNewReferee(refAccount2, "Complete", "Referee2");

        refAccount3 = new Account("Referee3", 99, "Referee3X", "Password");
        referee3 = ar1.createNewReferee(refAccount3, "Complete", "Referee3");

        refAccount4 = new Account("Referee4", 99, "Referee4X", "Password");
        referee4 = ar1.createNewReferee(refAccount4, "Complete", "Referee4");

        refAccount5 = new Account("Referee5", 99, "Referee5X", "Password");
        referee5 = ar1.createNewReferee(refAccount5, "Complete", "Referee5");

        refAccount6 = new Account("Referee6", 99, "Referee6X", "Password");
        referee6 = ar1.createNewReferee(refAccount6, "Complete", "Referee6");

        //endregion

        //region Leagues and seasons creation
        league1 = new League("League1");
        league2 = new League("League2");
        season = new Season("Season");
        policy1 = new Policy("Standart", "aaa");
        policy2 = new Policy("Double", "bbb");
        sLsettings1 = new SLsettings(policy1);
        sLsettings1.addReferee(referee1);
        sLsettings1.addReferee(referee2);
        sLsettings1.addReferee(referee3);
        sLsettings2 = new SLsettings(policy2);
        sLsettings2.addReferee(referee4);
        sLsettings2.addReferee(referee5);
        sLsettings2.addReferee(referee6);
        league1.addSLsettingsToSeason(season, sLsettings1);
        league2.addSLsettingsToSeason(season, sLsettings2);
        season.addSLsettingsToLeague(league1, sLsettings1);
        season.addSLsettingsToLeague(league2, sLsettings2);
        referee1.setsLsettings(sLsettings1);
        referee2.setsLsettings(sLsettings1);
        referee3.setsLsettings(sLsettings1);
        referee4.setsLsettings(sLsettings2);
        referee5.setsLsettings(sLsettings2);
        referee6.setsLsettings(sLsettings2);
        referee1.addLeague(league1, season);
        referee2.addLeague(league1, season);
        referee3.addLeague(league1, season);
        referee4.addLeague(league2, season);
        referee5.addLeague(league2, season);
        referee6.addLeague(league2, season);
        policy1.setsLsettings(sLsettings1);
        policy2.setsLsettings(sLsettings2);

        //endregion  //

        //region Stadium creation
        stadium1 = new Stadium("Stadium1");
        stadium2 = new Stadium("Stadium2");
        stadium3 = new Stadium("Stadium3");
        stadium4 = new Stadium("Stadium4");
        //endregion

        //region Teams creation
        team1 = new Team("Team1", league1, stadium1);
        team2 = new Team("Team2", league1, stadium2);
        team3 = new Team("Team3", league2, stadium3);
        team4 = new Team("Team4", league2, stadium4);
        //endregion

        //region Owner creation
        ownerAccount1 = new Account("Owner1", 99, "Owner1X", "Password");
        owner1 = new Owner("Owner1", team1, null);
        ownerAccount1.addRole(owner1);

        ownerAccount2 = new Account("Owner2", 99, "Owner2X", "Password");
        owner2 = new Owner("Owner2", team2, null);
        ownerAccount2.addRole(owner2);

        ownerAccount3 = new Account("Owner3", 99, "Owner3X", "Password");
        owner3 = new Owner("Owner3", team3, null);
        ownerAccount3.addRole(owner3);

        ownerAccount4 = new Account("Owner4", 99, "Owner4X", "Password");
        owner4 = new Owner("Owner4", team4, null);
        ownerAccount4.addRole(owner4);
        //endregion

        //region TeamManagers creation
        tmAccount1 = new Account("TM1", 99, "TM1X", "Password");
        List<TeamManager.PermissionEnum> tm1Permissions = new ArrayList<>();
        tm1Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm1Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner1.appointTeamManagerToTeam(tmAccount1, tm1Permissions);
        tm1 = tmAccount1.checkIfTeamManagr();

        tmAccount2 = new Account("TM2", 99, "TM2X", "Password");
        List<TeamManager.PermissionEnum> tm2Permissions = new ArrayList<>();
        tm2Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm2Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner2.appointTeamManagerToTeam(tmAccount2, tm2Permissions);
        tm2 = tmAccount2.checkIfTeamManagr();

        tmAccount3 = new Account("TM3", 99, "TM3X", "Password");
        List<TeamManager.PermissionEnum> tm3Permissions = new ArrayList<>();
        tm3Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm3Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner3.appointTeamManagerToTeam(tmAccount3, tm3Permissions);
        tm3 = tmAccount3.checkIfTeamManagr();

        tmAccount4 = new Account("TM4", 99, "TM4X", "Password");
        List<TeamManager.PermissionEnum> tm4Permissions = new ArrayList<>();
        tm4Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm4Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner4.appointTeamManagerToTeam(tmAccount4, tm4Permissions);
        tm4 = tmAccount4.checkIfTeamManagr();

        //endregion

        //region Players creation
        playerAccount1 = new Account("Player1", 99, "Player1X", "Password");
        player1 = new Player("Player1", new Date(), PositionEnum.Striker, team1, null);
        playerAccount1.addRole(player1);

        playerAccount2 = new Account("Player2", 99, "Player2X", "Password");
        player2 = new Player("Player2", new Date(), PositionEnum.Striker, team1, null);
        playerAccount2.addRole(player2);

        playerAccount3 = new Account("Player3", 99, "Player3X", "Password");
        player3 = new Player("Player3", new Date(), PositionEnum.Striker, team2, null);
        playerAccount3.addRole(player3);

        playerAccount4 = new Account("Player4", 99, "Player4X", "Password");
        player4 = new Player("Player4", new Date(), PositionEnum.Striker, team2, null);
        playerAccount4.addRole(player4);

        playerAccount5 = new Account("Player5", 99, "Player5X", "Password");
        player5 = new Player("Player5", new Date(), PositionEnum.Striker, team3, null);
        playerAccount5.addRole(player5);

        playerAccount6 = new Account("Player6", 99, "Player6X", "Password");
        player6 = new Player("Player6", new Date(), PositionEnum.Striker, team3, null);
        playerAccount6.addRole(player6);

        playerAccount7 = new Account("Player7", 99, "Player7X", "Password");
        player7 = new Player("Player7", new Date(), PositionEnum.Striker, team4, null);
        playerAccount7.addRole(player7);

        playerAccount8 = new Account("Player8", 99, "Player8X", "Password");
        player8 = new Player("Player8", new Date(), PositionEnum.Striker, team4, null);
        playerAccount8.addRole(player8);
        //endregion

        //region Coach creation
        coachAccount1 = new Account("Coach1", 99, "Coach1X", "Password");
        coach1 = new Coach("Coach1", "Full", "Main", null);
        coachAccount1.addRole(coach1);

        coachAccount2 = new Account("Coach2", 99, "Coach2X", "Password");
        coach2 = new Coach("Coach2", "Full", "Main", null);
        coachAccount2.addRole(coach2);

        coachAccount3 = new Account("Coach3", 99, "Coach3X", "Password");
        coach3 = new Coach("Coach3", "Full", "Main", null);
        coachAccount3.addRole(coach3);

        coachAccount4 = new Account("Coach4", 99, "Coach4X", "Password");
        coach4 = new Coach("Coach4", "Full", "Main", null);
        coachAccount4.addRole(coach4);
        //endregion

        //region Owners, TeamManagers and Coaches teams setting
        owner1.setTeam(team1);
        owner2.setTeam(team2);
        owner3.setTeam(team3);
        owner4.setTeam(team4);
        tm1.setTeam(team1);
        tm2.setTeam(team2);
        tm3.setTeam(team3);
        tm4.setTeam(team4);
        coach1.addTeam(team1);
        coach2.addTeam(team2);
        coach3.addTeam(team3);
        coach4.addTeam(team4);
        //endregion

        //region Match creation
        match1 = new Match("12/08/2009", new Time(22, 0, 0), 0, 0, stadium1, season
                , team2, team1, null, null, null);

        match2 = new Match("28/03/2008", new Time(19, 0, 0), 0, 0, stadium4, season
                , team3, team4, null, null, null);
        //endregion

        //region Referess match setting
        referee1.addMatch(match1, "main");
        referee2.addMatch(match1, "line");
        referee3.addMatch(match1, "line");
        referee4.addMatch(match2, "main");
        referee5.addMatch(match2, "line");
        referee6.addMatch(match2, "line");
        //endregion

        //region Seasons matches setting
        season.addMatch(match1);
        season.addMatch(match2);
        //endregion

        //region Players teams setting
        player1.setTeam(team1);
        player2.setTeam(team1);
        player3.setTeam(team2);
        player4.setTeam(team2);
        player5.setTeam(team3);
        player6.setTeam(team3);
        player7.setTeam(team4);
        player8.setTeam(team4);
        //endregion

        //region Server.BusinessLayer.Pages.Page creation
        playerPage1 = new Page(player1);
        playerPage2 = new Page(player2);
        playerPage3 = new Page(player3);
        playerPage4 = new Page(player4);
        playerPage5 = new Page(player5);
        playerPage6 = new Page(player6);
        playerPage7 = new Page(player7);
        playerPage8 = new Page(player8);
        coachPage1 = new Page(coach1);
        coachPage2 = new Page(coach2);
        coachPage3 = new Page(coach3);
        coachPage4 = new Page(coach4);
        teamPage1 = new Page(team1);
        teamPage2 = new Page(team2);
        teamPage3 = new Page(team3);
        teamPage4 = new Page(team4);
        //endregion

        //region Fan creation
        fanAccount1 = new Account("Fan1", 99, "Fan1X", "Password");
        fan1 = new Fan("Fan1");
        fanAccount1.addRole(fan1);

        fanAccount2 = new Account("Fan2", 99, "Fan2X", "Password");
        fan2 = new Fan("Fan2");
        fanAccount2.addRole(fan2);

        fanAccount3 = new Account("Fan3", 99, "Fan3X", "Password");
        fan3 = new Fan("Fan3");
        fanAccount3.addRole(fan3);

        fanAccount4 = new Account("Fan4", 99, "Fan4X", "Password");
        fan4 = new Fan("Fan4");
        fanAccount4.addRole(fan4);

        fanAccount5 = new Account("Fan5", 99, "Fan5X", "Password");
        fan5 = new Fan("Fan5");
        fanAccount5.addRole(fan5);

        fanAccount6 = new Account("Fan6", 99, "Fan6X", "Password");
        fan6 = new Fan("Fan6");
        fanAccount6.addRole(fan6);

        fanAccount7 = new Account("Fan7", 99, "Fan7X", "Password");
        fan7 = new Fan("Fan7");
        fanAccount7.addRole(fan7);

        fanAccount8 = new Account("Fan8", 99, "Fan8X", "Password");
        fan8 = new Fan("Fan8");
        fanAccount8.addRole(fan8);
        //endregion

        //region Fan page setting
        fan1.addPage(playerPage1);
        fan1.addPage(playerPage2);
        fan2.addPage(coachPage3);
        fan3.addPage(coachPage4);
        fan3.addPage(teamPage3);
        fan2.addPage(playerPage3);
        fan5.addPage(teamPage4);
        fan4.addPage(coachPage1);
        fan5.addPage(playerPage8);
        fan7.addPage(playerPage2);
        fan8.addPage(playerPage2);
        fan1.addPage(teamPage4);
        //endregion

        owner1.addAlert(new Server.BusinessLayer.OtherCrudOperations.Alert("bla bla bla bla"));
        owner1.addAlert(new Server.BusinessLayer.OtherCrudOperations.Alert("tralalalala"));

        match1.getEventCalender().addGameEvent(new GameEvent(EventEnum.goal,new Date(),new Time(1,1,1),"aaa",80,match1.getEventCalender()));




        //region Datamanager adding
        DataController.addLeague(league1);
        DataController.addLeague(league2);
        DataController.addSeason(season);
        DataController.addStadium(stadium1);
        DataController.addStadium(stadium2);
        DataController.addStadium(stadium3);
        DataController.addStadium(stadium4);
        DataController.addTeam(team1);
        DataController.addTeam(team2);
        DataController.addTeam(team3);
        DataController.addTeam(team4);
//        for(Match match:team1.getMatchs()) DataController.addMatchWithoutRefsDC(match);
//
//        for(Match match:team2.getMatchs()) DataController.addMatchWithoutRefsDC(match);
//
//        for(Match match:team3.getMatchs()) DataController.addMatchWithoutRefsDC(match);
//
//        for(Match match:team4.getMatchs()) DataController.addMatchWithoutRefsDC(match);




        DataController.addAccount(arAccount1);
        DataController.addAccount(arAccount2);
        DataController.addAccount(refAccount1);
        DataController.addAccount(refAccount2);
        DataController.addAccount(refAccount3);
//        DataController.addRefToMatch(match1,referee1,referee2,referee3);
        DataController.addAccount(refAccount4);
        DataController.addAccount(refAccount5);
        DataController.addAccount(refAccount6);
//        DataController.addRefToMatch(match2,referee4,referee5,referee6);
        DataController.addAccount(ownerAccount1);
        DataController.addAccount(ownerAccount2);
        DataController.addAccount(ownerAccount3);
        DataController.addAccount(ownerAccount4);
        DataController.addAccount(tmAccount1);
        DataController.addAccount(tmAccount2);
        DataController.addAccount(tmAccount3);
        DataController.addAccount(tmAccount4);
        DataController.addAccount(playerAccount1);
        DataController.addAccount(playerAccount2);
        DataController.addAccount(playerAccount3);
        DataController.addAccount(playerAccount4);
        DataController.addAccount(playerAccount5);
        DataController.addAccount(playerAccount6);
        DataController.addAccount(playerAccount7);
        DataController.addAccount(playerAccount8);
        DataController.addAccount(coachAccount1);
        DataController.addAccount(coachAccount2);
        DataController.addAccount(coachAccount3);
        DataController.addAccount(coachAccount4);
        DataController.addAccount(fanAccount1);
        DataController.addAccount(fanAccount2);
        DataController.addAccount(fanAccount3);
        DataController.addAccount(fanAccount4);
        DataController.addAccount(fanAccount5);
        DataController.addAccount(fanAccount6);
        DataController.addAccount(fanAccount7);
        DataController.addAccount(fanAccount8);




        //end
    }
}
