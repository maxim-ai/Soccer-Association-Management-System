public class MatchTest {
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//
//    Team awayTeam;
//    Team homeTeam;
//    Match match;
//    Account refAcc1;
//    Account refAcc2;
//    Account refAcc3;
//    Stadium s1;
//    Season se1;
//
//    @Before
//    public void setUp()
//    {
//        DataController.getInstance().clearDataBase();
//        Logger logger=Logger.getInstance();
//        OurSystem ourSystem=new OurSystem();
//        ourSystem.Initialize();
//
//        refAcc1=new Account("ref1",40,"ref1","ref2");
//        refAcc2=new Account("ref2",40,"ref2","ref2");
//        refAcc3=new Account("ref3",40,"ref3","ref3");
//
//        DataController.getInstance().addAccount(refAcc1);
//        DataController.getInstance().addAccount(refAcc2);
//        DataController.getInstance().addAccount(refAcc3);
//
//        refAcc1.addRole(new Referee("good1",refAcc1.getName()));
//        refAcc2.addRole(new Referee("good2",refAcc2.getName()));
//        refAcc3.addRole(new Referee("good3",refAcc3.getName()));
//        League l1=new League("l1");
//        se1=new Season("se1");
//        s1=new Stadium("s1");
//        awayTeam =new Team("t1",l1,s1);
//        homeTeam =new Team("t2",l1,s1);
//        match=new Match(new Date(123456),new Time(123456),0,0,s1,se1, awayTeam, homeTeam,refAcc1.checkIfReferee(),refAcc2.checkIfReferee(),refAcc3.checkIfReferee());
//    }
//
//    @Test
//    public void setDate()
//    {
//        match.setDate(new Date(123));
//        assertEquals(new Date(123),match.getDate());
//    }
//
//    @Test
//    public void setTime()
//    {
//        match.setTime(new Time(123));
//        assertEquals(new Time(123),match.getTime());
//    }
//
//    @Test
//    public void setAwayScore()
//    {
//        match.setAwayScore(3);
//        assertEquals(3,match.getAwayScore());
//    }
//
//    @Test
//    public void setHomeScore()
//    {
//        match.setHomeScore(3);
//        assertEquals(3,match.getHomeScore());
//    }
//
//    @Test
//    public void getDate()
//    {
//        assertEquals(new Date(123456),match.getDate());
//    }
//
//    @Test
//    public void getTime()
//    {
//        assertEquals(new Time(123456),match.getTime());
//
//    }
//
//    @Test
//    public void getAwayScore()
//    {
//        assertEquals(0,match.getAwayScore());
//    }
//
//    @Test
//    public void getHomeScore()
//    {
//        assertEquals(0,match.getHomeScore());
//    }
//
//    @Test
//    public void getAwayTeam()
//    {
//        assertEquals(awayTeam,match.getAwayTeam());
//    }
//
//    @Test
//    public void setAwayTeam()
//    {
//        match.setAwayTeam(homeTeam);
//        assertEquals(homeTeam,match.getAwayTeam());
//    }
//
//    @Test
//    public void getHomeTeam()
//    {
//        assertEquals(homeTeam,match.getHomeTeam());
//    }
//
//    @Test
//    public void setHomeTeam()
//    {
//        match.setAwayTeam(awayTeam);
//        assertEquals(awayTeam,match.getAwayTeam());
//    }
//
//    @Test
//    public void getMainReferee()
//    {
//        assertEquals(refAcc1.checkIfReferee(),match.getMainReferee());
//    }
//
//    @Test
//    public void setMainReferee()
//    {
//        match.setMainReferee(refAcc2.checkIfReferee());
//        assertEquals(refAcc2.checkIfReferee(),match.getMainReferee());
//    }
//
//    @Test
//    public void getLineRefereeOne()
//    {
//        assertEquals(refAcc2.checkIfReferee(),match.getLineRefereeOne());
//    }
//
//    @Test
//    public void setLineRefereeOne()
//    {
//        match.setLineRefereeOne(refAcc1.checkIfReferee());
//        assertEquals(refAcc1.checkIfReferee(),match.getLineRefereeOne());
//    }
//
//    @Test
//    public void getLineRefereeTwo()
//    {
//        assertEquals(refAcc3.checkIfReferee(),match.getLineRefereeTwo());
//
//    }
//
//    @Test
//    public void setLineRefereeTwo()
//    {
//        match.setLineRefereeTwo(refAcc1.checkIfReferee());
//        assertEquals(refAcc1.checkIfReferee(),match.getLineRefereeTwo());
//    }
//
//    @Test
//    public void getStadium()
//    {
//        assertEquals(s1,match.getStadium());
//    }
//
//    @Test
//    public void getSeason()
//    {
//        assertEquals(se1,match.getSeason());
//    }
//
//    @Test
//    public void getEventCalender() {
//    }
//
//    @Test
//    public void setStadium()
//    {
//        match.setStadium(new Stadium("newStad"));
//        assertEquals("newStad",match.getStadium().getName());
//    }
//
//    @Test
//    public void setSeason()
//    {
//        match.setSeason(new Season("newSea"));
//        assertEquals("newSea",match.getSeason().getName());
//    }
//
//
//
//    @Test
//    public void removeSeason()
//    {
//        match.removeSeason();
//        assertNull(match.getSeason());
//    }
//
//    @Before
//    public void init(){
//        System.setOut(new PrintStream(OS));
//    }
//
//    @After
//    public void restore(){
//        System.setOut(PS);
//    }
//
//    @Test
//    public void showMatch()
//    {
//        match.ShowMatch();
//        assertEquals("Score:\r\n" +
//                "t2: 0, t1: 0\r\n" +
//                "\r\n" +
//                "Referees:\r\n" +
//                "Main: ref1, Line1: ref2, Line2: ref3\r\n" +
//                "\r\n" +
//                "BusinessLayer.OtherCrudOperations.Stadium:\r\n" +
//                "s1\r\n" +
//                "\r\n" +
//                "BusinessLayer.OtherCrudOperations.Season:\r\n" +
//                "se1\r\n" +
//                "\r\n" +
//                "Game Events:\r\n",OS.toString());
//    }
}