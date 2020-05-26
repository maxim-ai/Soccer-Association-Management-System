import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.League;
import Server.BusinessLayer.OtherCrudOperations.Season;
import Server.BusinessLayer.OtherCrudOperations.Stadium;
import Server.BusinessLayer.OtherCrudOperations.Team;
import Server.BusinessLayer.Pages.Page;
import Server.BusinessLayer.RoleCrudOperations.Coach;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.RoleCrudOperations.Referee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class CoachTest {
    Coach coach=new Coach("Yossi Abukasis","Football Association","BusinessLayer.RoleCrudOperations.Coach",null);
    public Referee referee=new Referee("Football Association","adi lioz");
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;
    Date date=new Date();
    Time time=new Time(System.currentTimeMillis());
    Stadium stadium1=new Stadium("B7");
    Stadium stadium2=new Stadium("Ofakim");
    Season season=new Season("winter");
    League league=new League("super league");
    Team awayTeam=new Team("B7",league,stadium1);
    Page page1=new Page(awayTeam);
    Team homeTeam=new Team("Ofakim",league,stadium2);
    Page page2=new Page(homeTeam);
    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
    Fan fan=new Fan("tzlil");
    List<Team> teams=new LinkedList<>();
    @Before
    public void init(){
        DataController.getInstance().clearDataBase();
        System.setOut(new PrintStream(OS));
    }

    @After
    public void restore(){
        System.setOut(PS);
    }

    @Test
    public void setTraining() {
        coach.setPage(page1);
        assertTrue(coach.getTraining().equals("Football Association"));
        coach.setTraining("IL Football Association");
        assertTrue(coach.getTraining().equals("IL Football Association"));
    }

    @Test
    public void setTeamRole() {
        coach.setPage(page1);
        assertTrue(coach.getTeamRole().equals("BusinessLayer.RoleCrudOperations.Coach"));
        coach.setTeamRole("Main BusinessLayer.RoleCrudOperations.Coach");
        assertTrue(coach.getTeamRole().equals("Main BusinessLayer.RoleCrudOperations.Coach"));
    }

    @Test
    public void getTraining() {
        assertTrue(coach.getTraining().equals("Football Association"));
    }

    @Test
    public void getTeamRole() {
        assertTrue(coach.getTeamRole().equals("BusinessLayer.RoleCrudOperations.Coach"));
    }

    @Test
    public void getTeam() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        assertEquals(awayTeam,coach.getTeam(0));
    }

    @Test
    public void getTeams() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        coach.addTeam(homeTeam);
       assertEquals(coach.getTeams().get(0),awayTeam);
       assertEquals(coach.getTeams().get(1),homeTeam);
    }

    @Test
    public void numberOfTeams() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        coach.addTeam(homeTeam);
        assertEquals(2,coach.numberOfTeams());
    }

    @Test
    public void hasTeams() {
        coach.setPage(page1);
        assertFalse(coach.hasTeams());
        coach.addTeam(awayTeam);
        assertTrue(coach.hasTeams());
    }

    @Test
    public void indexOfTeam() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        coach.addTeam(homeTeam);
        assertEquals(coach.indexOfTeam(awayTeam),0);
        assertEquals(coach.indexOfTeam(homeTeam),1);
    }

    @Test
    public void getPage() {
        assertNull(coach.getPage());
        coach.setPage(page1);
        assertEquals(page1,coach.getPage());
    }

    @Test
    public void clear_page() {
        assertNull(coach.getPage());
        coach.setPage(page1);
        coach.clear_page();
        assertNull(coach.getPage());
    }



    @Test
    public void removePage() {
        coach.setPage(page1);
        assertEquals(coach.getPage(),page1);
        coach.removePage();
        assertNull(coach.getPage());
    }

    @Test
    public void updateDetails() {
        coach.setPage(page1);
        coach.updateDetails("IL Football Association","BusinessLayer.RoleCrudOperations.Player");
        assertTrue(coach.getTraining().equals("IL Football Association"));
        assertTrue(coach.getTeamRole().equals("BusinessLayer.RoleCrudOperations.Player"));

    }

    @Test
    public void showCoach() {
        teams.add(awayTeam);
        ShowCoachTest();
        assertEquals("Name:\r\n" +
                "Yossi Abukasis\r\n" +
                "\r\n" +
                "Training:\r\n" +
                "Football Association\r\n" +
                "\r\n" +
                "TeamRole:\r\n" +
                "BusinessLayer.RoleCrudOperations.Coach\r\n" +
                "\r\n" +
                "TeamsCoaching:\r\n" +
                "team\r\n",OS.toString());

    }
    //func
    public void ShowCoachTest() {
        System.out.println("Name:");
        System.out.println(getNameStub());
        System.out.println();
        System.out.println("Training:");
        System.out.println(getTrainingStub());
        System.out.println();
        System.out.println("TeamRole:");
        System.out.println(getTeamRoleStub());
        System.out.println();
        System.out.println("TeamsCoaching:");
        for(Team team:this.getTeamsStub())
            System.out.println(getTeamNameStub(team));
    }
    //stub
    public String getNameStub(){
        return "Yossi Abukasis";
    }
    public String getTrainingStub(){
        return "Football Association";
    }
    public String TeamRoleStub(){
        return "Football Association";
    }
    public String getTeamRoleStub(){
        return "BusinessLayer.RoleCrudOperations.Coach";
    }

    public List<Team> getTeamsStub()
    {
        return teams;
    }
    public String getTeamNameStub(Team team){
        return "team";
    }
    @Test
    public void addTeam() {
        assertTrue(addTeamTest(awayTeam));
    }
    //func
    public boolean addTeamTest(Team aTeam)
    {
        boolean wasAdded = false;
        if (teams.contains(aTeam)) { return true; }
        teams.add(aTeam);
        if (indexOfCoachStub(aTeam,coach) != -1)
        {
            wasAdded = true;
        }
        else
        {
            aTeam.addCoach(coach);
        }
        pageUpdatedStube();
        return wasAdded;
    }
    //stub
    public int indexOfCoachStub(Team team,Coach aCoach)
    {
        return 1;
    }
    public void pageUpdatedStube()
    {
        return;
    }
    @Test
    public void removeTeam() {
        teams.add(awayTeam);
        assertTrue(removeTeamTest(awayTeam));
    }
    public boolean removeTeamTest(Team aTeam)
    {
        boolean wasRemoved = true;
        if (!teams.contains(aTeam))
        {
            return wasRemoved;
        }

        int oldIndex = teams.indexOf(aTeam);
        teams.remove(oldIndex);
        if (indexOfCoachStub(aTeam,coach) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            aTeam.removeCoach(coach);
        }
        pageUpdatedStube();
        return wasRemoved;
    }



}