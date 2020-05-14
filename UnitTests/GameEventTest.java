public class GameEventTest {
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
//    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
//    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
//    Fan fan=new Fan("tzlil");
//    Match match=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
//    EventCalender eventCalender=new EventCalender(match);
//
//    GameEvent gameEvent=new GameEvent(EventEnum.goal,date,time,"Goal!!!",15,eventCalender);
//
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//    @Before
//    public void init(){
//        DataController.getInstance().clearDataBase();
//        System.setOut(new PrintStream(OS));
//    }
//
//    @After
//    public void restore(){
//        System.setOut(PS);
//    }
//    @Test
//    public void setType() {
//        gameEvent.setType(EventEnum.redCard);
//        assertTrue(gameEvent.getType().equals(EventEnum.redCard));
//    }
//
//    @Test
//    public void setDate() {
//        Date newDate = new java.util.Date();
//        gameEvent.setDate(newDate);
//        assertTrue(newDate.compareTo(gameEvent.getDate())==0);
//
//    }
//
//    @Test
//    public void setHour() {
//        Time newTime=new Time(System.currentTimeMillis());
//        gameEvent.setHour(newTime);
//        assertTrue(newTime.compareTo(gameEvent.getHour())==0);
//    }
//
//    @Test
//    public void setDescription() {
//        gameEvent.setDescription("new Description");
//        assertTrue(gameEvent.getDescription().equals("new Description"));
//    }
//
//    @Test
//    public void setGameMinute() {
//        gameEvent.setGameMinute(5);
//        assertEquals(gameEvent.getGameMinute(),5);
//    }
//
//    @Test
//    public void getType() {
//        assertTrue(gameEvent.getType().equals(EventEnum.goal));
//    }
//
//    @Test
//    public void getDate() {
//        assertTrue(date.compareTo(gameEvent.getDate())==0);
//    }
//
//    @Test
//    public void getHour() {
//        assertTrue(time.compareTo(gameEvent.getHour())==0);
//
//    }
//
//    @Test
//    public void getDescription() {
//        assertTrue(gameEvent.getDescription().equals("Goal!!!"));
//    }
//
//    @Test
//    public void getGameMinute() {
//        assertEquals(gameEvent.getGameMinute(),15);
//    }
//
//    @Test
//    public void getEventCalender() {
//        assertEquals(eventCalender,gameEvent.getEventCalender());
//    }
//
//
//    @Test
//    public void ShowGameEvent() {
//        gameEvent.ShowGameEvent();
//        assertEquals("Type:\r\n" +
//                "goal\r\n" +
//                "\r\n" +
//                "Date:\r\n" +
//                date.toString()+"\r\n" +
//                "\r\n" +
//                "Hour:\r\n" +
//                time.toString()+"\r\n" +
//                "\r\n" +
//                "Description:\r\n" +
//                "Goal!!!\r\n" +
//                "\r\n" +
//                "Game Minute\r\n" +
//                "15\r\n\r\n", OS.toString());
//
//    }
//    @Test
//    public void setEventCalender() {
//        Match match2=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
//        EventCalender eventCalender2=new EventCalender(match2);
//        assertTrue(setEventCalenderTest(eventCalender2));
//    }
//    public boolean setEventCalenderTest(EventCalender aEventCalender)
//    {
//        boolean wasSet = false;
//        if (aEventCalender == null)
//        {
//            eventCalender=null;
//            return wasSet;
//        }
//
//        EventCalender existingEventCalender = eventCalender;
//        eventCalender = aEventCalender;
//        if (existingEventCalender != null && !existingEventCalender.equals(aEventCalender))
//        {
//            removeGameEventStub(gameEvent);
//        }
//        addGameEventStub(gameEvent);
//        wasSet = true;
//        return wasSet;
//    }
//    //stub
//    public boolean removeGameEventStub(GameEvent aGameEvent)
//    {
//        return true;
//    }
//    public boolean addGameEventStub(GameEvent aGameEvent)
//    {
//       return true;
//    }
}