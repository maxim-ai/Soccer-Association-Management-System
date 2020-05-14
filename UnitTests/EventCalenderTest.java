public class EventCalenderTest {
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
//    EventCalender eventCalender1=new EventCalender(match);
//    GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"Goal!!!",15,eventCalender1);
//    List<GameEvent>gameEvents1=new LinkedList<>();
//
//    @Before
//    public void setUp(){
//        DataController.getInstance().clearDataBase();
//    }
//    @Test
//    public void getMatch() {
//        assertEquals(match,eventCalender.getMatch());
//    }
//
//    @Test
//    public void getGameEvent() {
//        GameEvent gameEvent=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent);
//        assertEquals(gameEvent,eventCalender.getGameEvent(0));
//    }
//
//    @Test
//    public void getGameEvents() {
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent1);
//        eventCalender.addGameEvent(gameEvent2);
//        assertEquals(eventCalender.getGameEvents().get(0),gameEvent1);
//        assertEquals(eventCalender.getGameEvents().get(1),gameEvent2);
//    }
//
//    @Test
//    public void numberOfGameEvents() {
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent1);
//        eventCalender.addGameEvent(gameEvent2);
//        assertEquals(eventCalender.numberOfGameEvents(),2);
//    }
//
//    @Test
//    public void hasGameEvents() {
//        assertFalse(eventCalender.hasGameEvents());
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent1);
//        assertTrue(eventCalender.hasGameEvents());
//
//
//    }
//
//    @Test
//    public void indexOfGameEvent() {
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        GameEvent gameEvent2=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),eventCalender);
//        eventCalender.addGameEvent(gameEvent1);
//        eventCalender.addGameEvent(gameEvent2);
//        assertEquals(eventCalender.indexOfGameEvent(gameEvent2),1);
//        assertEquals(eventCalender.indexOfGameEvent(gameEvent1),0);
//    }
//
//    @Test
//    public void minimumNumberOfGameEvents() {
//        assertEquals(eventCalender.minimumNumberOfGameEvents(),0);
//    }
//
//
//    @Test
//    public void removeGameEvent() {
//
//        GameEvent gameEvent1=new GameEvent(EventEnum.goal,date,time,"goal!!!",(int)Referee.getDateDiff(match.getDate(),date, TimeUnit.MINUTES),null);
//        eventCalender.addGameEvent(gameEvent1);
//        eventCalender.removeGameEvent(gameEvent1);
//        assertFalse(eventCalender.getGameEvents().contains(gameEvent1));
//
//    }
//
//
//    @Test
//    public void addGameEvent() {
//            assertTrue(addGameEventTest(gameEvent1));
//    }
//    //func
//    public boolean addGameEventTest(GameEvent aGameEvent)
//    {
//        boolean wasAdded = true;
//        if (gameEvents1.contains(aGameEvent)) { return true; }
//        EventCalender existingEventCalender = getEventCalenderStub();
//        boolean isNewEventCalender = existingEventCalender != null && !this.equals(existingEventCalender);
//        if (isNewEventCalender)
//        {
//            setEventCalenderStub(eventCalender);
//        }
//        else
//        {
//            gameEvents1.add(aGameEvent);
//        }
//        wasAdded = true;
//        return wasAdded;
//    }
//    //stubs
//    public EventCalender getEventCalenderStub()
//    {
//        return eventCalender;
//    }
//    public boolean setEventCalenderStub(EventCalender aEventCalender)
//    {
//            return true;
//    }
}