import javafx.event.EventType;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class EventCalenderTest {
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
    Referee referee=new Referee("Football Association","adi lioz");
    Team awayTeam=new Team("B7",page1,league,stadium1);
    Team homeTeam=new Team("Ofakim",page2,league,stadium2);
    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
    Fan fan=new Fan("tzlil");
    Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
    EventCalender eventCalender=new EventCalender(match);
    @Test
    public void getMatch() {
        assertEquals(match,eventCalender.getMatch());
    }

    @Test
    public void getGameEvent() {
        GameEvent gameEvent=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent);
        assertEquals(gameEvent,eventCalender.getGameEvent(0));
    }

    @Test
    public void getGameEvents() {
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent1);
        eventCalender.addGameEvent(gameEvent2);
        assertEquals(eventCalender.getGameEvents().get(0),gameEvent1);
        assertEquals(eventCalender.getGameEvents().get(1),gameEvent2);
    }

    @Test
    public void numberOfGameEvents() {
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent1);
        eventCalender.addGameEvent(gameEvent2);
        assertEquals(eventCalender.numberOfGameEvents(),2);
    }

    @Test
    public void hasGameEvents() {
        assertFalse(eventCalender.hasGameEvents());
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent1);
        assertTrue(eventCalender.hasGameEvents());


    }

    @Test
    public void indexOfGameEvent() {
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent1);
        eventCalender.addGameEvent(gameEvent2);
        assertEquals(eventCalender.indexOfGameEvent(gameEvent2),1);
        assertEquals(eventCalender.indexOfGameEvent(gameEvent1),0);
    }

    @Test
    public void minimumNumberOfGameEvents() {
        assertEquals(eventCalender.minimumNumberOfGameEvents(),0);
    }

    @Test
    public void addGameEvent() {
        Match match2=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
        EventCalender eventCalender2=new EventCalender(match2);
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
        assertTrue(eventCalender.addGameEvent(gameEvent1));
        assertTrue(eventCalender2.addGameEvent(gameEvent1));
        assertTrue(eventCalender.addGameEvent(gameEvent2));
    }

    @Test
    public void removeGameEvent() {

        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
        eventCalender.addGameEvent(gameEvent1);
        eventCalender.removeGameEvent(gameEvent1);
        assertFalse(eventCalender.getGameEvents().contains(gameEvent1));

    }

    @Test
    public void delete() {
        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
        eventCalender.addGameEvent(gameEvent1);
        eventCalender.addGameEvent(gameEvent2);
        eventCalender.delete();
        assertEquals(eventCalender.getGameEvents().size(),0);
    }
}