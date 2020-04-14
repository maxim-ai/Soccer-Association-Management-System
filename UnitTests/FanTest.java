import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FanTest {
    Fan fan;
    Account account1;Account account2;League league;Stadium stadium;Season season;Team team;Page playerPage;Page teamPage;
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
        team=new Team("Barcelona",null,league,stadium);
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
    public void getPage() {
        assertEquals(teamPage,fan.getPage(1));
    }

    @Test
    public void getPages() {
        List<Page> checkList=new ArrayList<>();
        checkList.add(playerPage);
        checkList.add(teamPage);
        int counter=0;
        for(Page page:fan.getPages()){
            assertEquals(checkList.get(counter++),page);
        }
    }

    @Test
    public void numberOfPages() {
        assertEquals(2,fan.numberOfPages());
    }

    @Test
    public void hasPages() {
        assertTrue(fan.hasPages());
    }

    @Test
    public void indexOfPage() {
        assertEquals(0,fan.indexOfPage(playerPage));
    }


    @Test
    public void addPage() {
        Stadium checkStadium=new Stadium("Terner");
        League checkLeague=new League("Haal");
        Team checkTeam=new Team("Spartak",null,checkLeague,checkStadium);
        Page checkPage=new Page(checkTeam);
        checkTeam.setPage(checkPage);
        fan.addPage(checkPage);
        assertEquals(checkPage,fan.getPage(2));
    }

    @Test
    public void removePage() {
        fan.removePage(playerPage);
        assertEquals(1,fan.numberOfPages());
        assertEquals(teamPage,fan.getPage(0));
    }

    @Test
    public void delete() {
        fan.deleteAllPages();
        assertFalse(fan.hasPages());
        assertFalse(playerPage.hasFans());
        assertFalse(teamPage.hasFans());
    }

    @Test
    public void isTrackPersonalPages() {
        assertFalse(fan.isTrackPersonalPages());
    }

    @Test
    public void setTrackPersonalPages() {
        fan.setTrackPersonalPages(true);
        assertTrue(fan.isTrackPersonalPages());
    }

    @Test
    public void isGetMatchNotifications() {
        assertFalse(fan.isGetMatchNotifications());
    }

    @Test
    public void setGetGameNotifications() {
        fan.setGetGameNotifications(true);
        assertTrue(fan.isGetMatchNotifications());
    }

    @Test
    public void getSearchHistory(){
        fan.Search("Name","Barcelona");
        fan.Search("Category","Teams");
        List<String[]> searchHistory=fan.getSearchHistory();
        assertEquals("Name",searchHistory.get(0)[0]);
        assertEquals("Barcelona",searchHistory.get(0)[1]);
        assertEquals("Category",searchHistory.get(1)[0]);
        assertEquals("Teams",searchHistory.get(1)[1]);
    }
    //endregion

    //region UC and Technical Tests
    @Test
    public void showInfoAboutTeams() {
        fan.ShowInfo("Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
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
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutSeasons(){
        fan.ShowInfo("Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchName() {
        fan.Search("Name","Barcelona");
        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil" +
                "\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\nLeague:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Name"));
        assertTrue(searchHistory.get(0)[1].equals("Barcelona"));
    }
    @Test
    public void searchCategoryTeams() {
        fan.Search("Category","Teams");
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
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
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
        List<String[]> searchHistory=fan.getSearchHistory();
        assertTrue(searchHistory.get(0)[0].equals("Category"));
        assertTrue(searchHistory.get(0)[1].equals("Leagues"));
    }
    @Test
    public void searchCategorySeasons() {
        fan.Search("Category","Seasons");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
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
        String s="Name:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\nPlayers:\r\nMaxim\r\n\r\n" +
                "League:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterLeagues(){
        fan.Filter("League","");
        String s="Name:\r\nInternational\r\n\r\nTeams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterSeasons(){
        fan.Filter("Season","");
        String s="Name:\r\nWinter\r\n\r\nMatches:\r\n";
        assertEquals(OS.toString(),s);
    }

    @Test
    public void logout() {
        fan.Logout();
        assertEquals("Logged out\r\n",OS.toString());
        assertTrue(CheckLoggerLines("Fan Danny logged out"));
    }

    @Test
    public void subscribeTrackPersonalPages() {
        fan.SubscribeTrackPersonalPages();
        assertTrue(fan.isTrackPersonalPages());
    }

    @Test
    public void subscribeGetMatchNotifications(){
        fan.SubscribeGetMatchNotifications();
        assertTrue(fan.isGetMatchNotifications());
    }

    @Test
    public void notifyFansAboutMatch() {
        Account checkAccount=new Account("Yossi",30,"YossiX","1234");
        checkAccount.addRole(fan);
        DataManager.addAccount(checkAccount);
        Stadium checkStadium=new Stadium("Blumfield");
        League checkLeague=new League("Haal");
        Season checkSeason=new Season("Summer");
        Referee ref=new Referee("aaa","Eitan");
        Team checkTeam=new Team("Rome",null,checkLeague,checkStadium);
        fan.SubscribeGetMatchNotifications();
        Match checkMatch=new Match(new Date(),new Time(22,0,0),0,0,checkStadium,
                checkSeason,team,checkTeam,ref,null,null);
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
        String s="Teams with the name Barcelona\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\n" +
                "Players:\r\nMaxim\r\n\r\nLeague:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\nName:\r\nInternational\r\n\r\n" +
                "Teams in league:\r\nBarcelona\r\n\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void showPersonalInfo() {
        fan.ShowPersonalInfo();
        String s="Name:\r\nDanny\r\n\r\nTeams Tracked:\r\nName:\r\nBarcelona\r\n\r\nTeamManagers:\r\n\r\nCoaches:\r\nTzlil\r\n\r\nTeamOwners:\r\n\r\n" +
                "Players:\r\nMaxim\r\n\r\nLeague:\r\nInternational\r\n\r\nMatches:\r\n\r\nStadium:\r\nTeddy\r\n\r\nPlayers Tracked:\r\n" +
                "Name:\r\nMaxim\r\n\r\nAge:\r\n26\r\n\r\nPosition:\r\nGoalkeeper\r\n\r\nTeam:\r\nBarcelona\r\n\r\nCoaches Tracked:\r\n";
        assertEquals(s,OS.toString());
    }

    @Test
    public void editPersonalInfo() {
        Account checkAccount=new Account("Eddie",27,"EddieX","1234");
        checkAccount.addRole(fan);
        DataManager.addAccount(checkAccount);
        fan.EditPersonalInfo("Edddie","","");
        assertEquals("Edddie",checkAccount.getName());
        assertEquals("EddieX",checkAccount.getUserName());
        assertEquals("1234",checkAccount.getPassword());
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