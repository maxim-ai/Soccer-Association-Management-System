import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.*;

public class GuestTest {
    Guest guest;
    Account account1;Account account2;League league;Stadium stadium;Season season;Team team;
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;


    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(OS));
        Guest.resetGuestIDCounter();
        guest = new Guest();
        account1=new Account("Maxim",26,"MaximX","1234");
        account1.addRole(new Player("Maxim",new Date(),PositionEnum.Goalkeeper,team,null));
        account2=new Account("Tzlil",26,"TzlilX","1234");
        account2.addRole(new Coach("Tzlil","aaa","bbb",null));
        league=new League("International");
        stadium=new Stadium("Teddy");
        season=new Season("Winter");
        team=new Team("Barcelona",null,league,stadium);
        ((Player)account1.getRole(0)).setTeam(team);
        ((Coach)account2.getRole(0)).addTeam(team);
        DataManager.cleatDataBase();
        DataManager.addAccount(account1);
        DataManager.addAccount(account2);
        DataManager.addLeague(league);
        DataManager.addStadium(stadium);
        DataManager.addSeason(season);
        DataManager.addTeam(team);


        File loggerFile=new File("Logger");
        if(loggerFile.exists())
            loggerFile.delete();
    }
    @After
    public void restore(){
        System.setOut(PS);
    }

    //region Getters&Setters Tests
    @Test
    public void getGuestIDCounter() {
        assertEquals(1,Guest.getGuestIDCounter());
    }
    @Test
    public void resetGuestIDCounter(){
        Guest.resetGuestIDCounter();
        assertEquals(0,Guest.getGuestIDCounter());
    }
    @Test
    public void getId() {

        assertEquals(1,guest.getId());
    }
    @Test
    public void testToString() {

        assertEquals("Guest ID: 1",guest.toString());
    }
    //endregion

    //region UC and Technical Tests
    @Test
    public void logInAccistingAccount() throws IOException {
        assertEquals(account1,guest.LogIn("MaximX","1234"));
        assertTrue(CheckLoggerLines("Account MaximX logged in"));
    }
    @Test
    public void logInNotAccistingAccount() {
        assertNull(guest.LogIn("MaximY","1234"));
    }
    @Test
    public void signInAccistingAccount() {
        assertFalse(guest.SignIn("Sean",24,"MaximX","4321"));
    }
    @Test
    public void signInNotAccistingAccount() throws IOException {
        assertTrue(guest.SignIn("Sean",24,"SeanX","4321"));
        assertEquals("SeanX",DataManager.getAccount(2).getUserName());
        assertTrue(CheckLoggerLines("New account SeanX created"));
    }
    @Test
    public void showInfoAboutTeams() {
        guest.ShowInfo("Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutPlayers(){
        guest.ShowInfo("Players");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nTeam:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutCoaches(){
        guest.ShowInfo("Coaches");
        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutLeagues(){
        guest.ShowInfo("Leagues");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutSeasons(){
        guest.ShowInfo("Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchName() {
        guest.Search("Name","Barcelona");
        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil" +
                "\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\nLeague:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryTeams() {
        guest.Search("Category","Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryAccounts() {
        guest.Search("Category","Accounts");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nRoles:\r\nPlayer\r\n\r\nName:\r\nTzlil\r\n\r\nAge:\r\n26\r\n\r\n" +
                "Roles:\r\nCoach\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryLeagues() {
        guest.Search("Category","Leagues");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategorySeasons() {
        guest.Search("Category","Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRolePlayers() {
        guest.Filter("Role","Players");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nTeam:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleCoaches() {
        guest.Filter("Role","Coaches");
        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleTeamManagers() {
        Account checkAccount1=new Account("Tom",30,"TomX","1234");
        DataManager.addAccount(checkAccount1);
        checkAccount1.addRole(new Owner("Tom",team,null));
        Account checkAccount2=new Account("Tommy",30,"TommyX","1234");
        DataManager.addAccount(checkAccount2);
        checkAccount2.addRole(new TeamManager("Tommy",team,(Owner)checkAccount1.getRoles().get(0)));
        guest.Filter("Role","TeamManagers");
        String s="Name:\r\nTommy\r\n\r\nTeam managed:\r\nBarcelona\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleOwners() {
        Account checkAccount=new Account("Tom",30,"TomX","1234");
        DataManager.addAccount(checkAccount);
        checkAccount.addRole(new Owner("Tom",team,null));
        guest.Filter("Role","Owners");
        String s="Name:\r\nTom\r\nTeam owned:\r\nBarcelona\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleReferees() {
        Account account3=new Account("Eitan",25,"EitanX","1234");
        account3.addRole(new Referee("Abroad","Eitan"));
        DataManager.addAccount(account3);
        guest.Filter("Role","Referees");
        String s="Name:\r\nEitan\r\n\r\nTraining:\r\nAbroad\r\n\r\nMatches judged:\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterTeams(){
        guest.Filter("Team","");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterLeagues(){
        guest.Filter("League","");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterSeasons(){
        guest.Filter("Season","");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
        assertEquals(OS.toString(),s);
    }
    //endregion

    private boolean CheckLoggerLines(String s) {
        String line= null;
        try {
            BufferedReader BR=new BufferedReader(new FileReader(new File("Logger")));
            line = BR.readLine();
            BR.close();
            if(s.equals(line.substring(line.indexOf('-')+2)))
                return true;
            else
                return false;
        } catch (IOException e) { }
        return false;
    }
}