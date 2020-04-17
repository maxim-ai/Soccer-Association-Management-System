import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class FanTest {
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
        Team checkTeam=new Team("Spartak",checkLeague,checkStadium);
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

    //region Stub tests
    @Test
    public void showInfoAboutTeams() {
        ShowInfo("Teams");
        String s="Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutPlayers(){
        ShowInfo("Players");
        String s="Player\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutCoaches(){
        ShowInfo("Coaches");
        String s="Coach\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutLeagues(){
        ShowInfo("Leagues");
        String s="League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutSeasons(){
        ShowInfo("Seasons");
        String s="Season\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchName() {
        Search("Name","Barcelona");
        String s="Teams with the name Barcelona\r\nTeam\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryTeams() {
        Search("Category","Teams");
        String s="Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryAccounts() {
        Search("Category","Accounts");
        String s="Account\r\nAccount\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryLeagues() {
        Search("Category","Leagues");
        String s="League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategorySeasons() {
        Search("Category","Seasons");
        String s="Season\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRolePlayers() {
        Filter("Role","Players");
        String s="Player\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleCoaches() {
        Filter("Role","Coaches");
        String s="Coach\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleTeamManagers() {
        Account checkAccount1=new Account("Tom",30,"TomX","1234");
        DataManager.addAccount(checkAccount1);
        checkAccount1.addRole(new Owner("Tom", team,null));
        Account checkAccount2=new Account("Tommy",30,"TommyX","1234");
        DataManager.addAccount(checkAccount2);
        checkAccount2.addRole(new TeamManager("Tommy", team,(Owner)checkAccount1.getRoles().get(0)));
        Filter("Role","TeamManagers");
        String s="TeamManager\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleOwners() {
        Account checkAccount=new Account("Tom",30,"TomX","1234");
        DataManager.addAccount(checkAccount);
        checkAccount.addRole(new Owner("Tom", team,null));
        Filter("Role","Owners");
        String s="Owner\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleReferees() {
        Account account3=new Account("Eitan",25,"EitanX","1234");
        account3.addRole(new Referee("Abroad","Eitan"));
        DataManager.addAccount(account3);
        Filter("Role","Referees");
        String s="Referee\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterTeams(){
        Filter("Team","");
        String s="Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterLeagues(){
        Filter("League","");
        String s="League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterSeasons(){
        Filter("Season","");
        String s="Season\r\n";
        assertEquals(OS.toString(),s);
    }
    //endregion


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
    public void showPersonalInfo() {
        fan.ShowPersonalInfo();
        String s="Name:\r\n" + "Danny\r\n" + "\r\n" + "Teams Tracked:\r\n" + "Name:\r\n" + "Barcelona\r\n" + "\r\n" + "TeamManagers:\r\n" + "Yossi\r\n" + "\r\n" + "Coaches:\r\n" + "Tzlil\r\n" + "\r\n" + "TeamOwners:\r\n" + "Haim\r\n" + "\r\n" + "Players:\r\n" + "Maxim\r\n" + "\r\n" + "League:\r\n" + "International\r\n" + "\r\n" + "Matches:\r\n" + "Barcelona against Rome\r\n" + "\r\n" + "Stadium:\r\n" + "Teddy\r\n" + "\r\n" + "Players Tracked:\r\n" + "Name:\r\n" + "Maxim\r\n" + "\r\n" + "Age:\r\n" + "26\r\n" + "\r\n" + "Position:\r\n" + "Goalkeeper\r\n" + "\r\n" + "Team:\r\n" + "Barcelona\r\n" + "\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void editPersonalInfoUserNameNotExists() {
        Account checkAccount=new Account("Eddie",27,"EddieX","1234");
        checkAccount.addRole(fan);
        DataManager.addAccount(checkAccount);
        assertTrue(fan.EditPersonalInfo("Edddie","",""));
        assertEquals("Edddie",checkAccount.getName());
        assertEquals("EddieX",checkAccount.getUserName());
        assertEquals("1234",checkAccount.getPassword());
    }
    @Test
    public void editPersonalInfoUserNameExists() {
        Account checkAccount=new Account("Eddie",27,"EddieX","1234");
        checkAccount.addRole(fan);
        DataManager.addAccount(checkAccount);
        assertFalse(fan.EditPersonalInfo("Edddie","MaximX",""));
        assertEquals("Eddie",checkAccount.getName());
        assertEquals("EddieX",checkAccount.getUserName());
        assertEquals("1234",checkAccount.getPassword());
    }
    @Test
    public void showSearchHistory() {
        fan.Search("Name","Barcelona");
        fan.Search("Category","Leagues");
        OS.reset();
        fan.ShowSearchHistory();
        String s="Criterion:\r\n" + "Name\r\n" + "Query:\r\n" + "Barcelona\r\n" + "\r\n" + "Criterion:\r\n" + "Category\r\n" + "Query:\r\n" + "Leagues\r\n" + "\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void logout() {
        fan.Logout();
        assertEquals("Logged out\r\n",OS.toString());
        assertTrue(CheckLoggerLines("Fan Danny logged out"));
    }

    //endregion




    //region Help Methods
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
    private void ShowInfo(String InfoAbout){
        if(InfoAbout.equals("Teams")){
            for(Team team: DataManager.getTeams())
                ShowTeamStub();
        }

        if(InfoAbout.equals("Players")){
            List<Player> players= DataManager.getPlayersFromAccounts();
            for(Player player:players){
                ShowPlayerStub();
            }
        }
        if(InfoAbout.equals("Coaches")){
            List<Coach> coaches= DataManager.getCoachesFromAccounts();
            for(Coach coach:coaches){
                ShowCoachStub();
            }
        }

        if(InfoAbout.equals("Leagues")){
            for(League league: DataManager.getLeagues())
                ShowLeagueStub();
        }

        if(InfoAbout.equals("Seasons")){
            for(Season season: DataManager.getSeasons())
                ShowSeasonStub();
        }

    }
    private void Search(String criterion, String query){
        if(criterion.equals("Name")){
            List<Team> teams=new LinkedList<>();
            for(Team team: DataManager.getTeams()){
                if(team.getName().equals(query))
                    teams.add(team);
            }
            List<Account> accounts=new LinkedList<>();
            for(Account account: DataManager.getAccounts()){
                if(account.getName().equals(query))
                    accounts.add(account);
            }
            List<League> leagues=new LinkedList<>();
            for(League league: DataManager.getLeagues()){
                if(league.getName().equals(query))
                    leagues.add(league);
            }
            List<Season> seasons=new LinkedList<>();
            for(Season season: DataManager.getSeasons()){
                if(season.getName().equals(query))
                    seasons.add(season);
            }

            if (!accounts.isEmpty()) {
                System.out.println("Accounts with the name "+query);
                for(Account account:accounts)
                    ShowAccountStub();
            }
            if (!teams.isEmpty()) {
                System.out.println("Teams with the name "+query);
                for(Team team:teams)
                    ShowTeamStub();
            }
            if (!leagues.isEmpty()) {
                System.out.println("Leagues with the name "+query);
                for(League league:leagues)
                    ShowLeagueStub();
            }
            if (!seasons.isEmpty()) {
                System.out.println("Seasons with the name "+query);
                for(Season season:seasons)
                    ShowSeasonStub();
            }

        }
        if(criterion.equals("Category")){
            if(query.equals("Teams")){
                for(Team team: DataManager.getTeams())
                    ShowTeamStub();
            }
            if(query.equals("Accounts")){
                for(Account account: DataManager.getAccounts())
                    ShowAccountStub();
            }
            if(query.equals("Leagues")){
                for(League league: DataManager.getLeagues())
                    ShowLeagueStub();
            }
            if(query.equals("Seasons")){
                for(Season season: DataManager.getSeasons())
                    ShowSeasonStub();
            }
        }
    }
    public void Filter(String category,String roleFilter){
        if(category.equals("Role")){
            if(roleFilter.equals("Players")){
                List<Player> players= DataManager.getPlayersFromAccounts();
                for(Player player:players)
                    ShowPlayerStub();
            }

            if(roleFilter.equals("Coaches")){
                List<Coach> coaches= DataManager.getCoachesFromAccounts();
                for(Coach coach:coaches)
                    ShowCoachStub();
            }

            if(roleFilter.equals("TeamManagers")){
                List<TeamManager> tms= DataManager.getTeamManagersFromAccounts();
                for(TeamManager tm:tms)
                    ShowTeamManagerStub();
            }

            if(roleFilter.equals("Owners")){
                List<Owner> owners= DataManager.getOwnersFromAccounts();
                for(Owner owner:owners)
                    ShowOwnerStub();
            }

            if(roleFilter.equals("Referees")){//************************************
                List<Referee> refs= DataManager.getRefereesFromAccounts();
                for(Referee ref:refs)
                    ShowRefereeStub();
            }

        }
        if(category.equals("Team")){
            for(Team team: DataManager.getTeams())
                ShowTeamStub();
        }
        if(category.equals("League")){
            for(League league: DataManager.getLeagues())
                ShowLeagueStub();
        }
        if(category.equals("Season")){
            for(Season season: DataManager.getSeasons())
                ShowSeasonStub();
        }
    }

    //endregion

    //region ShowStubs
    private void ShowTeamStub(){
        System.out.println("Team");
    }
    private void ShowLeagueStub(){
        System.out.println("League");
    }
    private void ShowSeasonStub(){
        System.out.println("Season");
    }
    private void ShowRefereeStub(){
        System.out.println("Referee");
    }
    private void ShowAccountStub(){
        System.out.println("Account");
    }
    private void ShowPlayerStub(){
        System.out.println("Player");
    }
    private void ShowCoachStub(){
        System.out.println("Coach");
    }
    private void ShowOwnerStub(){
        System.out.println("Owner");
    }
    private void ShowTeamManagerStub(){
        System.out.println("TeamManager");
    }
    //endregion

}