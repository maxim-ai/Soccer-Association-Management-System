import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.*;
import Server.BusinessLayer.Pages.Page;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.RoleCrudOperations.Player;
import Server.BusinessLayer.RoleCrudOperations.Referee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class PlayerTests {
    Date date=new Date();
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;
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
    Player player=new Player("Yosi",date, PositionEnum.AttackingMidfielder,null,null);
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
    public void setPage() {
        player.setPage(page1);
        assertEquals(player.getPage(),page1);
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
                        "BusinessLayer.OtherCrudOperations.Team:\r\n" +
                        "B7\r\n"
                ,OS.toString());
    }

}