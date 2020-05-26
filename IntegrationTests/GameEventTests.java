public class GameEventTests {
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
//    public void setEventCalender() {
//
//        Match match2=new Match(date,time,1,0,stadium1,season,awayTeam,homeTeam,referee,lineRefereeOne,lineRefereeTwo);
//        EventCalender eventCalender2=new EventCalender(match2);
//        gameEvent.setEventCalender(eventCalender2);
//        assertEquals(eventCalender2,gameEvent.getEventCalender());
//    }
//
//    @Test
//    public void delete() {
//        gameEvent.delete();
//        assertNull(gameEvent.getEventCalender());
//
//    }
//
//

}