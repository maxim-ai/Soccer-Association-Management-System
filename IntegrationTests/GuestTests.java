import BusinessLayer.DataController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class GuestTests {
//    Guest guest;
//    Account account1;Account account2;League league;
//    Stadium stadium;Season season;
//    Team team;
//    TeamManager teamManager; Owner owner; Match match;
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//
//
//    @Before
//    public void setUp() throws Exception {
//        DataController.clearDataBase();
//        System.setOut(new PrintStream(OS));
//        Guest.resetGuestIDCounter();
//        guest = new Guest();
//        account1=new Account("Maxim",26,"MaximX","1234");
//        account1.addRole(new Player("Maxim",new Date(), PositionEnum.Goalkeeper, team,null));
//        account2=new Account("Tzlil",26,"TzlilX","1234");
//        account2.addRole(new Coach("Tzlil","aaa","bbb",null));
//        league=new League("International");
//        stadium=new Stadium("Teddy");
//        season=new Season("Winter");
//        team =new Team("Barcelona",league,stadium);
//        teamManager=new TeamManager("Yossi", team,null);
//        owner=new Owner("Haim", team,null);
//        match=new Match(new Date(),new Time(22,0,0),3,2,stadium,season,team,new Team("Rome",league,stadium),null,null,null);
//        team.addMatch(match,"home");
//        team.addOwner(owner);
//        team.addTeamManager(teamManager);
//        ((Player)account1.getRole(0)).setTeam(team);
//        ((Coach)account2.getRole(0)).addTeam(team);
//        season.addMatch(match);
//        DataController.clearDataBase();
//        DataController.addAccount(account1);
//        DataController.addAccount(account2);
//        DataController.addLeague(league);
//        DataController.addStadium(stadium);
//        DataController.addSeason(season);
//        DataController.addTeam(team);
//
//
//        File loggerFile=new File("Logger");
//        if(loggerFile.exists())
//            loggerFile.delete();
//    }
//    @After
//    public void restore(){
//        System.setOut(PS);
//    }
//
//    //region UC and Technical Tests
//    @Test
//    public void showInfoAboutTeams() {
//        guest.ShowInfo("Teams");
//        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
//                "BusinessLayer.OtherCrudOperations.League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nBusinessLayer.OtherCrudOperations.Stadium:\r\nTeddy\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void showInfoAboutPlayers(){
//        guest.ShowInfo("Players");
//        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nBusinessLayer.OtherCrudOperations.Team:\r\nBarcelona\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void showInfoAboutCoaches(){
//        guest.ShowInfo("Coaches");
//        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void showInfoAboutLeagues(){
//        guest.ShowInfo("Leagues");
//        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void showInfoAboutSeasons(){
//        guest.ShowInfo("Seasons");
//        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void searchName() {
//        guest.Search("Name","Barcelona");
//        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
//                "BusinessLayer.OtherCrudOperations.League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nBusinessLayer.OtherCrudOperations.Stadium:\r\nTeddy\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void searchCategoryTeams() {
//        guest.Search("Category","Teams");
//        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
//                "BusinessLayer.OtherCrudOperations.League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nBusinessLayer.OtherCrudOperations.Stadium:\r\nTeddy\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void searchCategoryAccounts() {
//        guest.Search("Category","Accounts");
//        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nRoles:\r\nBusinessLayer.RoleCrudOperations.Player\r\n\r\nName:\r\nTzlil\r\n\r\nAge:\r\n26\r\n\r\n" +
//                "Roles:\r\nBusinessLayer.RoleCrudOperations.Coach\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void searchCategoryLeagues() {
//        guest.Search("Category","Leagues");
//        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void searchCategorySeasons() {
//        guest.Search("Category","Seasons");
//        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterRolePlayers() {
//        guest.Filter("BusinessLayer.RoleCrudOperations.Role","Players");
//        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nBusinessLayer.OtherCrudOperations.Team:\r\nBarcelona\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterRoleCoaches() {
//        guest.Filter("BusinessLayer.RoleCrudOperations.Role","Coaches");
//        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterRoleTeamManagers() {
//        Account checkAccount1=new Account("Tom",30,"TomX","1234");
//        DataController.addAccount(checkAccount1);
//        checkAccount1.addRole(new Owner("Tom", team,null));
//        Account checkAccount2=new Account("Tommy",30,"TommyX","1234");
//        DataController.addAccount(checkAccount2);
//        checkAccount2.addRole(new TeamManager("Tommy", team,(Owner)checkAccount1.getRoles().get(0)));
//        guest.Filter("BusinessLayer.RoleCrudOperations.Role","TeamManagers");
//        String s="Name:\r\nTommy\r\n\r\nBusinessLayer.OtherCrudOperations.Team managed:\r\nBarcelona\r\n";
//        assertEquals(s,OS.toString());
//    }
//    @Test
//    public void filterRoleOwners() {
//        Account checkAccount=new Account("Tom",30,"TomX","1234");
//        DataController.addAccount(checkAccount);
//        checkAccount.addRole(new Owner("Tom", team,null));
//        guest.Filter("BusinessLayer.RoleCrudOperations.Role","Owners");
//        String s="Name:\r\nTom\r\nBusinessLayer.OtherCrudOperations.Team owned:\r\nBarcelona\r\n";
//        assertEquals(s,OS.toString());
//    }
//    @Test
//    public void filterRoleReferees() {
//        Account account3=new Account("Eitan",25,"EitanX","1234");
//        account3.addRole(new Referee("Abroad","Eitan"));
//        DataController.addAccount(account3);
//        guest.Filter("BusinessLayer.RoleCrudOperations.Role","Referees");
//        String s="Name:\r\nEitan\r\n\r\nTraining:\r\nAbroad\r\n\r\nMatches judged:\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterTeams(){
//        guest.Filter("BusinessLayer.OtherCrudOperations.Team","");
//        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
//                "BusinessLayer.OtherCrudOperations.League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nBusinessLayer.OtherCrudOperations.Stadium:\r\nTeddy\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterLeagues(){
//        guest.Filter("BusinessLayer.OtherCrudOperations.League","");
//        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    @Test
//    public void filterSeasons(){
//        guest.Filter("BusinessLayer.OtherCrudOperations.Season","");
//        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
//        assertEquals(OS.toString(),s);
//    }
//    //endregion

}