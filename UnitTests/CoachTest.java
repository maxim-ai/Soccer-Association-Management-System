import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class CoachTest {
    Coach coach=new Coach("Yossi Abukasis","Football Association","Coach",null);
    public Referee referee=new Referee("Football Association","adi lioz");
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;
    Date date=new Date();
    Time time=new Time(System.currentTimeMillis());
    Stadium stadium1=new Stadium("B7");
    Stadium stadium2=new Stadium("Ofakim");
    Season season=new Season("winter");
    League league=new League("super league");
    Page page1=new Page(new Pageable() {
        @Override
        public void removePage() {

        }
    });
    Page page2=new Page(new Pageable() {
        @Override
        public void removePage() {

        }
    });
    Team awayTeam=new Team("B7",page1,league,stadium1);
    Team homeTeam=new Team("Ofakim",page2,league,stadium2);
    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
    Fan fan=new Fan("tzlil");
    @Before
    public void init(){
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
    public void setTraining() {
        coach.setPage(page1);
        assertTrue(coach.getTraining().equals("Football Association"));
        coach.setTraining("IL Football Association");
        assertTrue(coach.getTraining().equals("IL Football Association"));
    }

    @Test
    public void setTeamRole() {
        coach.setPage(page1);
        assertTrue(coach.getTeamRole().equals("Coach"));
        coach.setTeamRole("Main Coach");
        assertTrue(coach.getTeamRole().equals("Main Coach"));
    }

    @Test
    public void getTraining() {
        assertTrue(coach.getTraining().equals("Football Association"));
    }

    @Test
    public void getTeamRole() {
        assertTrue(coach.getTeamRole().equals("Coach"));
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
    public void removePage() {
        coach.setPage(page1);
        assertEquals(coach.getPage(),page1);
        coach.removePage();
        assertNull(coach.getPage());
    }

    @Test
    public void updateDetails() {
        coach.setPage(page1);
        coach.updateDetails("IL Football Association","Player");
        assertTrue(coach.getTraining().equals("IL Football Association"));
        assertTrue(coach.getTeamRole().equals("Player"));

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
                "Coach\r\n" +
                "\r\n" +
                "TeamsCoaching:\r\n" +
                "B7\r\n",OS.toString());

    }

}