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

import static org.junit.Assert.*;

public class CoachTests {
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
    public void setPage() {
        assertNull(coach.getPage());
        coach.setPage(page1);
        assertEquals(coach.getPage(),page1);
    }

    @Test
    public void addTeam() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        assertEquals(coach.getTeam(0),awayTeam);
    }

    @Test
    public void removeTeam() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        assertEquals(coach.getTeams().size(),1);
        coach.removeTeam(awayTeam);
        assertEquals(coach.getTeams().size(),0);
    }

    @Test
    public void delete() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        coach.delete();
        assertNull(coach.getPage());
        assertEquals(coach.getTeams().size(),0);
    }




    @Test
    public void showCoach() {
        coach.setPage(page1);
        coach.addTeam(awayTeam);
        coach.ShowCoach();
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
                "B7\r\n",OS.toString());

    }

}