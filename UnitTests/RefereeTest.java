import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;

public class RefereeTest {

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
        System.setOut(new PrintStream(OS));
    }

    @After
    public void restore(){
        System.setOut(PS);
    }
    @Test
    public void getsLsettings() {
        Policy policy=new Policy("max points","every month");
        SLsettings sLsettings=new SLsettings(policy);
        referee.setsLsettings(sLsettings);
        assertEquals(referee.getsLsettings(),sLsettings);
    }

    @Test
    public void setsLsettings() {
        assertNull(referee.getsLsettings());
        Policy policy=new Policy("max points","every month");
        SLsettings sLsettings=new SLsettings(policy);
        referee.setsLsettings(sLsettings);
        assertNotNull(referee.getsLsettings());
    }

    @Test
    public void setTraining() {
        assertTrue(referee.getTraining().equals("Football Association"));
        referee.setTraining("IL Football Association");
        assertTrue(referee.getTraining().equals("IL Football Association"));
    }

    @Test
    public void getTraining() {
        assertTrue(referee.getTraining().equals("Football Association"));
    }

    @Test
    public void setMatchs() {
        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
        List<Match>matches=new ArrayList<Match>();
        matches.add(match);
        referee.setMatchs(matches);
        assertEquals(matches,referee.getMatchs());
    }

    @Test
    public void getMatchs() {
        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
        List<Match>matches=new ArrayList<Match>();
        matches.add(match);
        referee.setMatchs(matches);
        assertEquals(matches,referee.getMatchs());
    }

    @Test
    public void getLeagues() {
        HashMap<League,Season> leagueSeasonHashMap=new HashMap<>();
        leagueSeasonHashMap.put(league,season);
        referee.setLeagues(leagueSeasonHashMap);
        assertEquals(leagueSeasonHashMap,referee.getLeagues());
    }

    @Test
    public void setLeagues() {
        HashMap<League,Season> leagueSeasonHashMap1=new HashMap<>();
        HashMap<League,Season> leagueSeasonHashMap2=new HashMap<>();
        leagueSeasonHashMap1.put(league,season);
        leagueSeasonHashMap2.put(league,season);
        referee.setLeagues(leagueSeasonHashMap1);
        assertEquals(leagueSeasonHashMap1,referee.getLeagues());
        referee.setLeagues(leagueSeasonHashMap2);
        assertEquals(leagueSeasonHashMap2,referee.getLeagues());
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
    public void addMatch() {
        Referee line1Referee=new Referee("Football Association","yoni zlion");
        Referee line2Rreferee=new Referee("Football Association","avi choh");

        Match match1=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,lineRefereeOne,lineRefereeTwo);
        assertTrue(referee.addMatch(match1,"main"));
        assertFalse(referee.addMatch(match1,"main"));

        Match match2=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,null,lineRefereeTwo);
        assertTrue(line1Referee.addMatch(match2,"line"));
        assertFalse(line2Rreferee.addMatch(match2,"line"));

        Match match3=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,null,null);
        assertTrue(line1Referee.addMatch(match3,"line"));
        assertTrue(line2Rreferee.addMatch(match3,"line"));
        assertFalse(referee.addMatch(match3,"line"));

        Match match4=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,null,null);
        assertTrue(referee.addMatch(match4,"main"));
        assertTrue(line1Referee.addMatch(match4,"line"));
        assertTrue(line2Rreferee.addMatch(match4,"line"));

    }

    @Test
    public void removeMatch() {
        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
        referee.addMatch(match,"main");
        referee.removeMatch(match);
        assertEquals(referee.getMatchs().size(),0);
    }

    @Test
    public void delete() {
        referee.delete();
        assertEquals(referee.getMatchs().size(),0);
    }

    @Test
    public void updateDetails() {
        referee.updateDetails("zviali bar");
        assertEquals(referee.getName(),"zviali bar");
    }
    @Test
    public void displayAllMatches() {

        Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,null,lineRefereeOne,lineRefereeTwo);
        referee.addMatch(match,"main");
        referee.displayAllMatches();
        assertEquals("Date:\r\n" +
                date.toString()+"\r\n" +
                "\r\n" +
                "Time:\r\n" +
                time.toString()+"\r\n" +
                "\r\n" +
                "Score:\r\n" +
                "Ofakim: 0, B7: 1\r\n" +
                "\r\n" +
                "Referees:\r\n" +
                "Main: adi lioz, Line1: zviali bar, Line2: karapti roy\r\n" +
                "\r\n" +
                "Stadium:\r\n" +
                "B7\r\n" +
                "\r\n" +
                "Season:\r\n" +
                "winter\r\n" +
                        "\r\n" +
                "Game Events:\r\n\r\n",OS.toString());
    }

    @Test
    public void updateEventDuringMatch() {
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