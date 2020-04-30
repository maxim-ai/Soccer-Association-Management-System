import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.*;
import BusinessLayer.DataController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GuestTest {
    Guest guest;
    Account account1;Account account2;League league;Stadium stadium;Season season;Team team;
    TeamManager teamManager; Owner owner; Match match;
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;


    @Before
    public void setUp() throws Exception {
        DataController.clearDataBase();
        System.setOut(new PrintStream(OS));
        Guest.resetGuestIDCounter();
        guest = new Guest();
        account1=new Account("Maxim",26,"MaximX","1234");
        account1.addRole(new Player("Maxim",new Date(),PositionEnum.Goalkeeper, team,null));
        account2=new Account("Tzlil",26,"TzlilX","1234");
        account2.addRole(new Coach("Tzlil","aaa","bbb",null));
        league=new League("International");
        stadium=new Stadium("Teddy");
        season=new Season("Winter");
        team =new Team("Barcelona",league,stadium);
        teamManager=new TeamManager("Yossi", team,null);
        owner=new Owner("Haim", team,null);
        match=new Match(new Date(),new Time(22,0,0),3,2,stadium,season,team,new Team("Rome",league,stadium),null,null,null);
        team.addMatch(match,"home");
        team.addOwner(owner);
        team.addTeamManager(teamManager);
        ((Player)account1.getRole(0)).setTeam(team);
        ((Coach)account2.getRole(0)).addTeam(team);
        DataController.clearDataBase();
        DataController.addAccount(account1);
        DataController.addAccount(account2);
        DataController.addLeague(league);
        DataController.addStadium(stadium);
        DataController.addSeason(season);
        DataController.addTeam(team);


        File loggerFile=new File("Logger");
        if(loggerFile.exists())
            loggerFile.delete();

        loggerFile=new File("errorLogger");
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
        assertEquals(1, Guest.getGuestIDCounter());
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

        assertEquals("BusinessLayer.OtherCrudOperations.Guest ID: 1",guest.toString());
    }
    //endregion

    //region UC and Technical Tests
    @Test
    public void logInAccistingAccount() throws Exception {
        assertEquals(account1,guest.LogIn("MaximX","1234"));
        assertTrue(CheckLoggerLines("Account MaximX logged in","Logger"));
    }
    @Test
    public void logInNotAccistingAccount() throws Exception {
        try {
            guest.LogIn("MaximY","1234");
        } catch (Exception e) {
            assertEquals("Username does not exist",e.getMessage());
            assertTrue(CheckLoggerLines("Username does not exist","errorLogger"));
        }
    }
    @Test
    public void signInAccistingAccount() {
        assertFalse(guest.SignIn("Sean",24,"MaximX","4321"));
    }
    @Test
    public void signInNotAccistingAccount() throws IOException {
        assertTrue(guest.SignIn("Sean",24,"SeanX","4321"));
        assertEquals("SeanX", DataController.getAccount(2).getUserName());
        assertTrue(CheckLoggerLines("New account SeanX created","Logger"));
    }
    //region Stubs Tests
    @Test
    public void showInfoAboutTeams() {
        ShowInfo("Teams");
        String s="BusinessLayer.OtherCrudOperations.Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutPlayers(){
        ShowInfo("Players");
        String s="BusinessLayer.RoleCrudOperations.Player\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutCoaches(){
        ShowInfo("Coaches");
        String s="BusinessLayer.RoleCrudOperations.Coach\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutLeagues(){
        ShowInfo("Leagues");
        String s="BusinessLayer.OtherCrudOperations.League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void showInfoAboutSeasons(){
        ShowInfo("Seasons");
        String s="BusinessLayer.OtherCrudOperations.Season\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchName() {
        Search("Name","Barcelona");
        String s="Teams with the name Barcelona\r\nBusinessLayer.OtherCrudOperations.Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryTeams() {
        Search("Category","Teams");
        String s="BusinessLayer.OtherCrudOperations.Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryAccounts() {
        Search("Category","Accounts");
        String s="BusinessLayer.OtherCrudOperations.Account\r\nBusinessLayer.OtherCrudOperations.Account\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategoryLeagues() {
        Search("Category","Leagues");
        String s="BusinessLayer.OtherCrudOperations.League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void searchCategorySeasons() {
        Search("Category","Seasons");
        String s="BusinessLayer.OtherCrudOperations.Season\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRolePlayers() {
        Filter("BusinessLayer.RoleCrudOperations.Role","Players");
        String s="BusinessLayer.RoleCrudOperations.Player\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleCoaches() {
        Filter("BusinessLayer.RoleCrudOperations.Role","Coaches");
        String s="BusinessLayer.RoleCrudOperations.Coach\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterRoleTeamManagers() {
        Account checkAccount1=new Account("Tom",30,"TomX","1234");
        DataController.addAccount(checkAccount1);
        checkAccount1.addRole(new Owner("Tom", team,null));
        Account checkAccount2=new Account("Tommy",30,"TommyX","1234");
        DataController.addAccount(checkAccount2);
        checkAccount2.addRole(new TeamManager("Tommy", team,(Owner)checkAccount1.getRoles().get(0)));
        Filter("BusinessLayer.RoleCrudOperations.Role","TeamManagers");
        String s="BusinessLayer.RoleCrudOperations.TeamManager\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleOwners() {
        Account checkAccount=new Account("Tom",30,"TomX","1234");
        DataController.addAccount(checkAccount);
        checkAccount.addRole(new Owner("Tom", team,null));
        Filter("BusinessLayer.RoleCrudOperations.Role","Owners");
        String s="BusinessLayer.RoleCrudOperations.Owner\r\n";
        assertEquals(s,OS.toString());
    }
    @Test
    public void filterRoleReferees() {
        Account account3=new Account("Eitan",25,"EitanX","1234");
        account3.addRole(new Referee("Abroad","Eitan"));
        DataController.addAccount(account3);
        Filter("BusinessLayer.RoleCrudOperations.Role","Referees");
        String s="BusinessLayer.RoleCrudOperations.Referee\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterTeams(){
        Filter("BusinessLayer.OtherCrudOperations.Team","");
        String s="BusinessLayer.OtherCrudOperations.Team\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterLeagues(){
        Filter("BusinessLayer.OtherCrudOperations.League","");
        String s="BusinessLayer.OtherCrudOperations.League\r\n";
        assertEquals(OS.toString(),s);
    }
    @Test
    public void filterSeasons(){
        Filter("BusinessLayer.OtherCrudOperations.Season","");
        String s="BusinessLayer.OtherCrudOperations.Season\r\n";
        assertEquals(OS.toString(),s);
    }
    //endregion
    //endregion






    //region Help Methods
    private boolean CheckLoggerLines(String s, String fileName) {
        String line= null;
        try {
            BufferedReader BR=new BufferedReader(new FileReader(new File(fileName)));
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
            for(Team team: DataController.getTeams())
                ShowTeamStub();
        }

        if(InfoAbout.equals("Players")){
            List<Player> players= DataController.getPlayersFromAccounts();
            for(Player player:players){
                ShowPlayerStub();
            }
        }
        if(InfoAbout.equals("Coaches")){
            List<Coach> coaches= DataController.getCoachesFromAccounts();
            for(Coach coach:coaches){
                ShowCoachStub();
            }
        }

        if(InfoAbout.equals("Leagues")){
            for(League league: DataController.getLeagues())
                ShowLeagueStub();
        }

        if(InfoAbout.equals("Seasons")){
            for(Season season: DataController.getSeasons())
                ShowSeasonStub();
        }

    }
    private void Search(String criterion, String query){
        if(criterion.equals("Name")){
            List<Team> teams=new LinkedList<>();
            for(Team team: DataController.getTeams()){
                if(team.getName().equals(query))
                    teams.add(team);
            }
            List<Account> accounts=new LinkedList<>();
            for(Account account: DataController.getAccounts()){
                if(account.getName().equals(query))
                    accounts.add(account);
            }
            List<League> leagues=new LinkedList<>();
            for(League league: DataController.getLeagues()){
                if(league.getName().equals(query))
                    leagues.add(league);
            }
            List<Season> seasons=new LinkedList<>();
            for(Season season: DataController.getSeasons()){
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
                for(Team team: DataController.getTeams())
                    ShowTeamStub();
            }
            if(query.equals("Accounts")){
                for(Account account: DataController.getAccounts())
                    ShowAccountStub();
            }
            if(query.equals("Leagues")){
                for(League league: DataController.getLeagues())
                    ShowLeagueStub();
            }
            if(query.equals("Seasons")){
                for(Season season: DataController.getSeasons())
                    ShowSeasonStub();
            }
        }
    }
    public void Filter(String category,String roleFilter){
        if(category.equals("BusinessLayer.RoleCrudOperations.Role")){
            if(roleFilter.equals("Players")){
                List<Player> players= DataController.getPlayersFromAccounts();
                for(Player player:players)
                    ShowPlayerStub();
            }

            if(roleFilter.equals("Coaches")){
                List<Coach> coaches= DataController.getCoachesFromAccounts();
                for(Coach coach:coaches)
                    ShowCoachStub();
            }

            if(roleFilter.equals("TeamManagers")){
                List<TeamManager> tms= DataController.getTeamManagersFromAccounts();
                for(TeamManager tm:tms)
                    ShowTeamManagerStub();
            }

            if(roleFilter.equals("Owners")){
                List<Owner> owners= DataController.getOwnersFromAccounts();
                for(Owner owner:owners)
                    ShowOwnerStub();
            }

            if(roleFilter.equals("Referees")){//************************************
                List<Referee> refs= DataController.getRefereesFromAccounts();
                for(Referee ref:refs)
                    ShowRefereeStub();
            }

        }
        if(category.equals("BusinessLayer.OtherCrudOperations.Team")){
            for(Team team: DataController.getTeams())
                ShowTeamStub();
        }
        if(category.equals("BusinessLayer.OtherCrudOperations.League")){
            for(League league: DataController.getLeagues())
                ShowLeagueStub();
        }
        if(category.equals("BusinessLayer.OtherCrudOperations.Season")){
            for(Season season: DataController.getSeasons())
                ShowSeasonStub();
        }
    }
    //endregion

    //region ShowStubs
    private void ShowTeamStub(){
        System.out.println("BusinessLayer.OtherCrudOperations.Team");
    }
    private void ShowLeagueStub(){
        System.out.println("BusinessLayer.OtherCrudOperations.League");
    }
    private void ShowSeasonStub(){
        System.out.println("BusinessLayer.OtherCrudOperations.Season");
    }
    private void ShowRefereeStub(){
        System.out.println("BusinessLayer.RoleCrudOperations.Referee");
    }
    private void ShowAccountStub(){
        System.out.println("BusinessLayer.OtherCrudOperations.Account");
    }
    private void ShowPlayerStub(){
        System.out.println("BusinessLayer.RoleCrudOperations.Player");
    }
    private void ShowCoachStub(){
        System.out.println("BusinessLayer.RoleCrudOperations.Coach");
    }
    private void ShowOwnerStub(){
        System.out.println("BusinessLayer.RoleCrudOperations.Owner");
    }
    private void ShowTeamManagerStub(){
        System.out.println("BusinessLayer.RoleCrudOperations.TeamManager");
    }
    //endregion
}
