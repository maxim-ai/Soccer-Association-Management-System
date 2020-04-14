import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AssociationRepresentativeTests {
    AssociationRepresentative associationRepresentative=new AssociationRepresentative("Test");

    @Test
    public void createNewLeague() {
        List<Team> teamsList = new LinkedList<>();
        League league = associationRepresentative.createNewLeague("TestLeague",teamsList);
        assertTrue(DataManager.getLeagues().contains(league));
    }

    @Test
    public void setYearToLeague() {
        League testLeague = new League("Test");
        if(associationRepresentative.setYearToLeague(testLeague,"0000") && DataManager.getLeagues().contains(testLeague)) {
            List<Season> seasonsList = DataManager.getSeasons();
            for (Season season : seasonsList) {
                if (season.getName().equals("0000")) {
                    assertNotNull(season.getSLsettingsByLeague(testLeague));
                }
            }
        }

    }

    @Test
    public void deleteReferee() {

    }

    @Test
    public void addNewReferee() {
        Referee referee=associationRepresentative.addNewReferee("Test","Test",0,"Test","Test");
        assertNotNull(DataManager.getAccountByRole(referee));
    }

    @Test
    public void createNewReferee() {
        Account testAccount = new Account("Test",0,"Test","Test");
        Referee referee=associationRepresentative.createNewReferee(testAccount, "Test","Test");
        DataManager.addAccount(testAccount);
        boolean actual=false;
        if(DataManager.getAccountByRole(referee)!=null)
            actual = true;
        for(Alert alert:referee.getAlertList()){
            if(alert.getDescription().startsWith("Invitation")){
                assertTrue(actual);
            }
        }
    }

    @Test
    public void addRefereeToLeague() {
        Season testSeason = new Season("0000");
        Referee testReferee = new Referee("Test","Test");
        League testLeague = new League("Test");
        boolean actual = (associationRepresentative.addRefereeToLeague(testReferee,testLeague,testSeason));

        if(DataManager.getLeagues().contains(testLeague)&&DataManager.getSeasons().contains(testSeason))
            for(League league:DataManager.getLeagues()){
                SLsettings sLsettings = league.getSLsettingsBySeason(testSeason);
                if(sLsettings!=null){
                    if(testReferee.getsLsettings().equals(sLsettings))
                        assertTrue(actual);                }
            }

    }

    @Test
    public void setLeaguePointCalcPolicy() {
        Season testSeason = new Season("0000");
        League testLeague = new League("Test");
        Policy testPolicy = new Policy("Test","Test");
        if(associationRepresentative.setLeaguePointCalcPolicy(testLeague,testPolicy,testSeason,"Test"))
            assertEquals(testPolicy,testLeague.getSLsettingsBySeason(testSeason).getPolicy());
        else fail();
    }

    @Test
    public void setLeagueGameSchedualPolicy() {
        Season testSeason = new Season("0000");
        League testLeague = new League("Test");
        Policy testPolicy = new Policy("Test","Test");
        if(associationRepresentative.setLeagueGameSchedualPolicy(testLeague,testPolicy,testSeason,"Test"))
            assertEquals(testPolicy,testLeague.getSLsettingsBySeason(testSeason).getPolicy());
        else fail();
    }

    @Test
    public void setNewSeason() {
        Season testSeason = associationRepresentative.setNewSeason("0000");
        assertTrue(DataManager.getSeasons().contains(testSeason));
    }

    @Test
    public void addAmountToAssociationBudget(){

    }

    @Test
    public void subtractAmountFromAssociationBudget(){

    }

    @Test
    public void addAlert() {
    }

    @Test
    public void clearAlerts() {
    }

    @Test
    public void removeAlert() {
    }

    @Test
    public void approveTeam() {
    }

    @Test
    public void addOpenTeamRequest() {
    }

    @Test
    public void removeOpenTeamRequest() {
    }

    @Test
    public void checkIfRequestExists() {
    }

    @Test
    public void getRequestStatus() {
    }
}
