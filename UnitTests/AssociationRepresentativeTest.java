//import BusinessLayer.DataController;
//import BusinessLayer.Logger.Logger;
//import BusinessLayer.OtherCrudOperations.*;
//import BusinessLayer.RoleCrudOperations.AssociationRepresentative;
//import BusinessLayer.RoleCrudOperations.Owner;
//import BusinessLayer.RoleCrudOperations.Referee;
//import javafx.util.Pair;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class AssociationRepresentativeTest {
//    AssociationRepresentative associationRepresentative=new AssociationRepresentative("Test");
//
//    @Before
//    public void setUp(){
//        DataController.getInstance().clearDataBase();
//    }
//
//    @Test
//    public void createNewLeague() {
//        List<Team> teamsList = new LinkedList<>();
//        assertNotNull(createNewLeagueTest("TestLeague",teamsList));
//    }
//
//    private League createNewLeagueTest(String name,List<Team> teams){
//        if(teams == null || name == null){
//            return  null;
//        }
//        League league = new League(name);
//        DataController.getInstance().addLeague(league);
//        for(Team team : teams){
//            addTeamStub(team);
//        }
//        return league;
//    }
//
//    private void addTeamStub(Team team){
//
//    }
//
//    @Test
//    public void setYearToLeague() {
//        League testLeague = new League("Test");
//        assertTrue(setYearToLeagueTest(testLeague,"0000"));
//    }
//
//
//    private boolean setYearToLeagueTest(League league,String year){
//        boolean wasSet = false;
//        if(league == null || year == null){
//            return  wasSet;
//        }
//        Season season=new Season(year);
//        DataController.getInstance().addSeason(season);
//        DataController.getInstance().addLeague(league);
//        SLsettings sLsettings = getSLsettingsBySeasonStub(league,season);
//        if (sLsettings==null)
//        {
//            sLsettings = new SLsettings(new Policy(null,null));
//        }
//        wasSet = addSLsettingsToSeasonStub(league,season,sLsettings);
//        return wasSet;
//    }
//
//    private SLsettings getSLsettingsBySeasonStub(League league,Season season)
//    {
//        return null;
//    }
//
//    private boolean addSLsettingsToSeasonStub(League league,Season season,SLsettings sLsettings){
//        return true;
//    }
//
//    @Test
//    public void deleteReferee() {
//
//    }
//
//    @Test
//    public void addNewReferee() {
//        assertNotNull(addNewRefereeTest("Test","Test",0,"Test","Test"));
//    }
//
//    private Referee addNewRefereeTest(String training, String name, int age, String userName, String password){
//        Account account = new Account(name,age,userName,password);
//        DataController.getInstance().addAccount(account);
//        if(training != null && name != null) {
//            return createNewRefereeStub(account,training,name);
//        }
//        return null;
//    }
//
//    private Referee createNewRefereeStub(Account account,String training,String name){ return new Referee(training,name);}
//
//    @Test
//    public void createNewReferee() {
//        Account testAccount = new Account("Test",0,"Test","Test");
//        Referee referee=associationRepresentative.createNewReferee(testAccount, "Test","Test");
//        DataController.getInstance().addAccount(testAccount);
//        boolean actual=false;
//        if(DataController.getInstance().getAccountByRole(referee)!=null)
//            actual = true;
//        for(Alert alert:referee.getAlertList()){
//            if(alert.getDescription().startsWith("Invitation")){
//                assertTrue(actual);
//            }
//        }
//    }
//
//    @Test
//    public void addRefereeToLeague() {
//        Season testSeason = new Season("0000");
//        Referee testReferee = new Referee("Test","Test");
//        League testLeague = new League("Test");
//        assertTrue (addRefereeToLeagueTest(testReferee,testLeague,testSeason));
//    }
//
//    private boolean addRefereeToLeagueTest(Referee referee,League league,Season season){ // to fix uc
//        boolean wasAdded = false;
//        if(league == null || referee == null || season == null){
//            return  wasAdded;
//        }
//        SLsettings sLsettings = getSLsettingsBySeasonStub(league,season);
//        if (sLsettings==null)
//        {
//            sLsettings = new SLsettings(new Policy(null,null));
//        }
//        referee.setsLsettings(sLsettings);
//        addRefereeStub(sLsettings, referee);
//        wasAdded= addLeagueStub(referee,league,season);
//        return  wasAdded;
//    }
//
//    private boolean addLeagueStub(Referee referee, League league, Season season) {
//        return true;
//    }
//
//    private void addRefereeStub(SLsettings sLsettings, Referee referee){}
//
//
//    @Test
//    public void setLeaguePointCalcPolicy() {
//        Season testSeason = new Season("0000");
//        League testLeague = new League("Test");
//        Policy testPolicy = new Policy("Test","Test");
//        assertTrue(setLeaguePointCalcPolicyTest(testLeague,testPolicy,testSeason,"Test"));
//    }
//
//    private boolean setLeaguePointCalcPolicyTest(League league, Policy policy,Season season,String pointCalc){
//        boolean wasAdded = false;
//        if(league == null || policy == null || season == null){
//            return  wasAdded;
//        }
//        policy.setPointCalc(pointCalc);
//        SLsettings sLsettings = league.getSLsettingsBySeason(season);
//        if(sLsettings!=null)
//            sLsettings.setPolicy(policy);
//        else{
//            sLsettings = new SLsettings(policy);
//            addSLsettingsToLeagueStub(season,league,sLsettings);
//        }
//        return true;
//    }
//
//    private void addSLsettingsToLeagueStub(Season season,League league, SLsettings sLsettings) {
//
//    }
//
//    @Test
//    public void setLeagueGameSchedualPolicy() {
//        Season testSeason = new Season("0000");
//        League testLeague = new League("Test");
//        Policy testPolicy = new Policy("Test","Test");
//        assertTrue(setLeagueGameSchedualPolicyTest(testLeague,testPolicy,testSeason,"Test"));
//    }
//
//    private boolean setLeagueGameSchedualPolicyTest(League league, Policy policy,Season season,String gameSchedule){
//        boolean wasAdded = false;
//        if(league == null || policy == null || season == null){
//            return  wasAdded;
//        }
//        policy.setGameSchedual(gameSchedule);
//        SLsettings sLsettings = league.getSLsettingsBySeason(season);
//        if(sLsettings!=null)
//            sLsettings.setPolicy(policy);
//        else{
//            sLsettings = new SLsettings(policy);
//            addSLsettingsToLeagueStub(season, league,sLsettings);
//        }    return true;
//    }
//
//    @Test
//    public void setNewSeason() {
//        Season testSeason = associationRepresentative.setNewSeason("0000");
//        assertTrue(DataController.getInstance().getSeasons().contains(testSeason));
//    }
//
//    @Test
//    public void addAmountToAssociationBudget(){
//
//    }
//
//    @Test
//    public void subtractAmountFromAssociationBudget(){
//
//    }
//
//    @Test
//    public void approveTeam() {
//        Team team = new Team("Test",new League("Test"),new Stadium("Test"));
//        Owner owner= new Owner("Test",team,null);
//        AssociationRepresentative.approvedTeams.put(new Pair<>(owner,"Test"),true);
//        assertTrue(approveTeamTest("Test",owner));
//    }
//
//    private boolean approveTeamTest(String teamName,Owner owner)
//    {
//        if(teamName==null || owner==null)
//            return false;
//
//        Pair request=new Pair(owner,teamName);
//
//        if(!AssociationRepresentative.approvedTeams.containsKey(request))
//            return false;
//        else
//        {
//            AssociationRepresentative.approvedTeams.put(request,true);
//            notifyOtherRoleStub("You are approved to open team: "+teamName,owner);
//            Logger.getInstance().writeNewLine(associationRepresentative.getName()+" approved "+owner.getName()+" to open the team: "+teamName);
//            return true;
//        }
//    }
//
//    private void notifyOtherRoleStub(String message, Owner owner){}
//
//    @Test
//    public void addOpenTeamRequest() {
//        assertTrue(AssociationRepresentative.addOpenTeamRequest(new Owner("Test",null,null),"Test"));
//    }
//
//    @Test
//    public void removeOpenTeamRequest() {
//        Owner owner= new Owner("Test",null,null);
//        AssociationRepresentative.approvedTeams.put(new Pair<>(owner,"Test"),false);
//        assertTrue(AssociationRepresentative.removeOpenTeamRequest(owner,"Test"));
//    }
//
//    @Test
//    public void checkIfRequestExists() {
//        Owner owner= new Owner("Test",null,null);
//        AssociationRepresentative.approvedTeams.put(new Pair<>(owner,"Test"),false);
//        assertTrue(AssociationRepresentative.checkIfRequestExists(owner,"Test"));
//    }
//
//    @Test
//    public void getRequestStatus() {
//        Owner owner= new Owner("Test",null,null);
//        AssociationRepresentative.approvedTeams.put(new Pair<>(owner,"Test"),true);
//        assertTrue(AssociationRepresentative.getRequestStatus(owner,"Test"));
//    }
//}
