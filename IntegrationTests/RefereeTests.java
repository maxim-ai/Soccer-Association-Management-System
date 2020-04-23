import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.Fan;
import BusinessLayer.RoleCrudOperations.Referee;
import DataLayer.DataManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

public class RefereeTests {
    Referee referee=new Referee("Football Association","adi lioz");
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
        DataManager.clearDataBase();
        System.setOut(new PrintStream(OS));
    }

    @After
    public void restore(){
        System.setOut(PS);
    }

    @Test
    public void addLeague() {
        assertEquals(referee.getLeagues().size(),0);
        Policy policy=new Policy("max points","every month");
        SLsettings sLsettings=new SLsettings(policy);
        league.addSLsettingsToSeason(season,sLsettings);
        referee.addLeague(league,season);
        assertEquals(referee.getLeagues().size(),1);

    }

    @Test
    public void displayAllMatches() {

        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,lineRefereeOne,lineRefereeTwo);
        referee.addMatch(match,"main");
        referee.displayAllMatches();
        assertEquals(
                "Score:\r\n" +
                "Ofakim: 0, B7: 1\r\n" +
                "\r\n" +
                "Referees:\r\n" +
                "Main: adi lioz, Line1: zviali bar, Line2: karapti roy\r\n" +
                "\r\n" +
                "BusinessLayer.OtherCrudOperations.Stadium:\r\n" +
                "B7\r\n" +
                "\r\n" +
                "BusinessLayer.OtherCrudOperations.Season:\r\n" +
                "winter\r\n" +
                "\r\n" +
                "Game Events:\r\n\r\n",OS.toString());
    }

    @Test
    public void updateEventDuringMatch() throws Exception {
        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,lineRefereeOne,lineRefereeTwo);
        referee.addMatch(match,"main");
        referee.updateEventDuringMatch(match, EventEnum.foul,"aya aya");
        boolean ans=false;
        for (GameEvent gameEvent:match.getEventCalender().getGameEvents()  ){
            if(gameEvent.getType().equals(EventEnum.foul))
                ans=true;

        }
        assertTrue(ans);
        match.getDate().setTime(1000);
        referee.updateEventDuringMatch(match, EventEnum.redCard,"zavik red card");
        ans=false;
        for (GameEvent gameEvent:match.getEventCalender().getGameEvents()  ){
            if(gameEvent.getType().equals(EventEnum.redCard))
                ans=true;

        }
        assertFalse(ans);
    }
    @Test
    public void editEventAfterGame() {
        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,lineRefereeOne,lineRefereeTwo);
        referee.addMatch(match,"main");
        match.getDate().setTime(1000);
        EventCalender eventCalender=new EventCalender(match);
        GameEvent gameEvent=new GameEvent(EventEnum.goal,date,time,"Goal!!!",15,match.getEventCalender());
        referee.editEventAfterGame(match,gameEvent,EventEnum.redCard,"check edit");
        assertTrue(gameEvent.getType().equals(EventEnum.redCard));
        assertTrue(gameEvent.getDescription().equals("check edit"));
    }

    @Test
    public void showReferee() {
        referee.ShowReferee();
        assertEquals("Name:\r\n" +
                "adi lioz\r\n" +
                "\r\n" +
                "Training:\r\n" +
                "Football Association\r\n" +
                "\r\n" +
                "Matches judged:\r\n",OS.toString());
    }
}