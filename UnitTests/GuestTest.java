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
    //region Stubs Tests
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
