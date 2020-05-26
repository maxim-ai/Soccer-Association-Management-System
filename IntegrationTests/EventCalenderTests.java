public class EventCalenderTests {
//    Date date=new Date();
//    Time time=new Time(System.currentTimeMillis());
//    Stadium stadium1=new Stadium("B7");
//    Stadium stadium2=new Stadium("Ofakim");
//    Season season=new Season("winter");
//    League league=new League("super league");
//    Team awayTeam=new Team("B7",league,stadium1);
//    Page page1=new Page(awayTeam);
//    Team homeTeam=new Team("Ofakim",league,stadium2);
//    Page page2=new Page(homeTeam);
//    Referee referee=new Referee("Football Association","adi lioz");
//
//    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
//    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
//    Fan fan=new Fan("tzlil");
//    Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
//    EventCalender eventCalender=new EventCalender(match);
//
//    @Before
//    public void setUp(){
//        DataController.getInstance().clearDataBase();
//    }
//
//
//    @Test
//    public void addGameEvent() {
//        Match match2=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
//        EventCalender eventCalender2=new EventCalender(match2);
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int) Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
//        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
//        assertTrue(eventCalender.addGameEvent(gameEvent1));
//        assertTrue(eventCalender2.addGameEvent(gameEvent1));
//        assertTrue(eventCalender.addGameEvent(gameEvent2));
//    }
//
//
//    @Test
//    public void delete() {
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent1);
//        eventCalender.addGameEvent(gameEvent2);
//        eventCalender.delete();
//        assertEquals(eventCalender.getGameEvents().size(),0);
//    }

}