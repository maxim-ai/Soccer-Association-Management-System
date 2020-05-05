//import BusinessLayer.DataController;
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
//import static org.junit.Assert.*;
//
//public class AssociationRepresentativeTests {
//    AssociationRepresentative associationRepresentative=new AssociationRepresentative("Test");
//
//    @Before
//    public void setUp(){
//        DataController.clearDataBase();
//    }
//
//
//    @Test
//    public void createNewLeague() {
//        List<Team> teamsList = new LinkedList<>();
//        League league = associationRepresentative.createNewLeague("TestLeague",teamsList);
//        assertTrue(DataController.getLeagues().contains(league));
//    }
//
//    @Test
//    public void setYearToLeague() {
//        DataController.clearDataBase();
//        League testLeague = new League("Test");
//        if(associationRepresentative.setYearToLeague(testLeague,"0000") && DataController.getLeagues().contains(testLeague)) {
//            List<Season> seasonsList = DataController.getSeasons();
//            for (Season season : seasonsList) {
//                if (season.getName().equals("0000")) {
//                    assertNotNull(season.getSLsettingsByLeague(testLeague));
//                }
//            }
//        }
//
//    }
//
//    @Test
//    public void addNewReferee() {
//        Referee referee=associationRepresentative.addNewReferee("Test","Test",0,"Test","Test");
//        assertNotNull(DataController.getAccountByRole(referee));
//    }
//
//    @Test
//    public void addRefereeToLeague() {
//        Season testSeason = new Season("0000");
//        Referee testReferee = new Referee("Test","Test");
//        League testLeague = new League("Test");
//        boolean actual = (associationRepresentative.addRefereeToLeague(testReferee,testLeague,testSeason));
//
//        if(DataController.getLeagues().contains(testLeague)&& DataController.getSeasons().contains(testSeason))
//            for(League league: DataController.getLeagues()){
//                SLsettings sLsettings = league.getSLsettingsBySeason(testSeason);
//                if(sLsettings!=null){
//                    if(testReferee.getsLsettings().equals(sLsettings))
//                        assertTrue(actual);                }
//            }
//
//    }
//
//    @Test
//    public void setLeaguePointCalcPolicy() {
//        Season testSeason = new Season("0000");
//        League testLeague = new League("Test");
//        Policy testPolicy = new Policy("Test","Test");
//        if(associationRepresentative.setLeaguePointCalcPolicy(testLeague,testPolicy,testSeason,"Test"))
//            assertEquals(testPolicy,testLeague.getSLsettingsBySeason(testSeason).getPolicy());
//        else fail();
//    }
//
//    @Test
//    public void setLeagueGameSchedualPolicy() {
//        Season testSeason = new Season("0000");
//        League testLeague = new League("Test");
//        Policy testPolicy = new Policy("Test","Test");
//        if(associationRepresentative.setLeagueGameSchedualPolicy(testLeague,testPolicy,testSeason,"Test"))
//            assertEquals(testPolicy,testLeague.getSLsettingsBySeason(testSeason).getPolicy());
//        else fail();
//    }
//
//    @Test
//    public void approveTeam() {
//        Team team = new Team("Test",new League("Test"),new Stadium("Test"));
//        Owner owner = new Owner("Test",team,null);
//        Pair request=new Pair(owner,"Test");
//        AssociationRepresentative.approvedTeams.put(request,true);
//        assertTrue(associationRepresentative.approveTeam("Test",owner));
//    }
//}
