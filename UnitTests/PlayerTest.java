import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class PlayerTest {
    Date date=new Date();
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;
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
    Player player=new Player("Yosi",date,PositionEnum.AttackingMidfielder,null);
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
    public void setBirthday() {
        Date newDate=new Date();
        player.setBirthday(newDate);
        assertTrue(player.getBirthday().compareTo(newDate)==0);
    }

    @Test
    public void setPosition() {
        player.setPage(page1);
        player.setPosition(PositionEnum.CenterBack);
        assertEquals(PositionEnum.CenterBack,player.getPosition());
    }

    @Test
    public void getBirthday() {
        assertTrue(date.compareTo(player.getBirthday())==0);
    }

    @Test
    public void getPosition() {
        assertEquals(player.getPosition(),PositionEnum.AttackingMidfielder);
    }

    @Test
    public void getTeam() {
        player.setPage(page1);
        player.setTeam(awayTeam);
        assertEquals(player.getTeam(),awayTeam);
    }

    @Test
    public void getPage() {
        player.setPage(page1);
        assertEquals(player.getPage(),page1);
    }

    @Test
    public void setTeam() {
        player.setPage(page1);
        player.setTeam(awayTeam);
        assertEquals(player.getTeam(),awayTeam);
    }

    @Test
    public void delete() {
        player.setPage(page1);
        player.setTeam(awayTeam);
        player.delete();
        assertNull(player.getPage());
    }

    @Test
    public void removePage() {
        player.setPage(page1);
        player.removePage();
        assertNull(player.getPage());
    }

    @Test
    public void setPage() {
        player.setPage(page1);
        assertEquals(player.getPage(),page1);
    }

    @Test
    public void updateDetails() {
        player.setPage(page1);
        Date newDate=new Date();
        player.updateDetails(newDate,PositionEnum.CenterBack,awayTeam);
        assertTrue(newDate.compareTo(player.getBirthday())==0);
        assertEquals(player.getPosition(),PositionEnum.CenterBack);
        assertEquals(player.getTeam(),awayTeam);
    }

    @Test
    public void showPlayer() {
        player.setPage(page1);
        player.setTeam(awayTeam);
        player.ShowPlayer();
        assertEquals("Name:\r\n" +
                "Yosi\r\n" +
                "\r\n" +
                "Age:\r\n" +
                "0\r\n" +
                "\r\n" +
                "Position:\r\n" +
                "AttackingMidfielder\r\n" +
                "\r\n" +
                "Team:\r\n" +
                "B7\r\n"
                ,OS.toString());
    }

    @Test
    public void pageUpdated() {
    }
}