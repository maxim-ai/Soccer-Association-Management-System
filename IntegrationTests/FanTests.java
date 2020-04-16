import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FanTests {
    Fan fan;
    Account account1;Account account2;League league;Stadium stadium;Season season;Team team;Page playerPage;Page teamPage;
    TeamManager teamManager; Owner owner; Match match;
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(OS));
        fan = new Fan("Danny");
        account1=new Account("Maxim",26,"MaximX","1234");
        account1.addRole(new Player("Maxim",new Date(),PositionEnum.Goalkeeper,team,null));
        account2=new Account("Tzlil",26,"TzlilX","1234");
        account2.addRole(new Coach("Tzlil","aaa","bbb",null));
        league=new League("International");
        stadium=new Stadium("Teddy");
        season=new Season("Winter");
        team=new Team("Barcelona",league,stadium);
        teamManager=new TeamManager("Yossi", team,null);
        owner=new Owner("Haim", team,null);
        match=new Match(new Date(),new Time(22,0,0),3,2,stadium,season,team,new Team("Rome",league,stadium),null,null,null);
        team.addMatch(match,"home");
        team.addOwner(owner);
        team.addTeamManager(teamManager);
        ((Player)account1.getRole(0)).setTeam(team);
        ((Coach)account2.getRole(0)).addTeam(team);
        playerPage=new Page((Player)account1.getRole(0));
        teamPage=new Page(team);
        ((Player)account1.getRole(0)).setPage(playerPage);
        team.setPage(teamPage);
        fan.addPage(playerPage);
        fan.addPage(teamPage);
        playerPage.addFan(fan);
        teamPage.addFan(fan);
        season.addMatch(match);
        DataManager.clearDataBase();
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

    //region UC and Technical Tests
    @Test
    public void showInfoAboutTeams() {
        fan.ShowInfo("Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutPlayers(){
        fan.ShowInfo("Players");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nTeam:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutCoaches(){
        fan.ShowInfo("Coaches");
        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutLeagues(){
        fan.ShowInfo("Leagues");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutSeasons(){
        fan.ShowInfo("Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchName() {
        fan.Search("Name","Barcelona");
        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Name"));
        assertTrue(searchHistory.get(0)[1].equals("Barcelona"));
    }
    @Test
    public void searchCategoryTeams() {
        fan.Search("Category","Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Category"));
        assertTrue(searchHistory.get(0)[1].equals("Teams"));

    }
    @Test
    public void searchCategoryAccounts() {
        fan.Search("Category","Accounts");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nRoles:\r\nPlayer\r\n\r\nName:\r\nTzlil\r\n\r\nAge:\r\n26\r\n\r\n" +
                "Roles:\r\nCoach\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Category"));
        assertTrue(searchHistory.get(0)[1].equals("Accounts"));
    }
    @Test
    public void searchCategoryLeagues() {
        fan.Search("Category","Leagues");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Category"));
        assertTrue(searchHistory.get(0)[1].equals("Leagues"));
    }
    @Test
    public void searchCategorySeasons() {
        fan.Search("Category","Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Category"));
        assertTrue(searchHistory.get(0)[1].equals("Seasons"));
    }
    @Test
    public void filterRolePlayers() {
        fan.Filter("Role","Players");
        String s="Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nTeam:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleCoaches() {
        fan.Filter("Role","Coaches");
        String s="Name:\r\nTzlil\r\n\r\nTraining:\r\naaa\r\n\r\nTeamRole:\r\nbbb\r\n\r\nTeamsCoaching:\r\nBarcelona\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleTeamManagers() {
        fan.Filter("Role","TeamManagers");
        String s="";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleOwners() {
        fan.Filter("Role","Owners");
        String s="";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleReferees() {
        Account account3=new Account("Eitan",25,"EitanX","1234");
        account3.addRole(new Referee("Abroad","Eitan"));
        DataManager.addAccount(account3);
        fan.Filter("Role","Referees");
        String s="Name:\r\nEitan\r\n\r\nTraining:\r\nAbroad\r\n\r\nMatches judged:\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterTeams(){
        fan.Filter("Team","");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterLeagues(){
        fan.Filter("League","");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\nRome\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterSeasons(){
        fan.Filter("Season","");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\nBarcelona against Rome\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void notifyFansAboutMatch() {
        Account checkAccount=new Account("Yossi",30,"YossiX","1234");
        checkAccount.addRole(fan);
        DataManager.addAccount(checkAccount);
        Stadium checkStadium=new Stadium("Blumfield");
        League checkLeague=new League("Haal");
        Season checkSeason=new Season("Summer");
        Referee ref1=new Referee("aaa","Eitan");
        Referee ref2=new Referee("bbb","Tzlil");
        Referee ref3=new Referee("bbb","Ziv");
        Team checkTeam=new Team("Rome",checkLeague,checkStadium);
        fan.SubscribeGetMatchNotifications();
        new Match(new Date(),new Time(22,0,0),0,0,checkStadium,
                checkSeason,team,checkTeam,ref1,ref2,ref3);
        assertEquals("Barcelona against Rome",fan.getAlertList().get(0).getDescription());
    }
    @Test
    public void report() {
        OurSystem OS=new OurSystem();
        OS.Initialize();
        fan.Report("aaa");
        CheckLoggerLines("Fan Danny sent report to the system manager");
    }
    @Test
    public void showSearchHistory() {
        fan.Search("Name","Barcelona");
        fan.Search("Category","Leagues");
        OS.reset();
        fan.ShowSearchHistory();
        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\nYossi\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\nHaim\r\n\r\n" +
                "Players:\r\nMaxim\r\n\r\nLeague:\r\nInternational\r\n\r\nMatches:\r\nBarcelona against Rome\r\n\r\nStadium:\r\nTeddy\r\n\r\nName:\r\nInternational\r\n\r\n" +
                "Teams in league:\r\nBarcelona\r\nRome\r\n\r\n";
        assertEquals(s,OS.toString());
    }
    //endregion


    //region HelpMethods
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
    //endregion


}