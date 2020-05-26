public class TeamTests {
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//
//    Team t1; Team t2;
//    Owner o1; Owner o2;
//    TeamManager tm1; TeamManager tm2;
//    Coach c1; Coach c2;
//    Player pl1; Player pl2;
//    Page pa1; Page pa2;
//    Match m1; Match m2;
//    League l1; League l2;
//    Stadium s1; Stadium s2;
//
//    @Before
//    public void setUp()
//    {
//        DataController.getInstance().clearDataBase();
//        DataController dataController = new DataController();
//        Logger logger = Logger.getInstance();
//        OurSystem ourSystem = new OurSystem();
//        ourSystem.Initialize();
//
//        l1=new League("l1");
//        l2=new League("l2");
//        s1=new Stadium("s1");
//        s2=new Stadium("s2");
//        t1=new Team("t1",l1,s1);
//        t2=new Team("t2",l1,s2);
//        o1=new Owner("o1",t1,null);
//        o2=new Owner("o2",t2,null);
//        tm1=new TeamManager("tm1",t1,o1);
//        t1.addTeamManager(tm1);
//        tm2=new TeamManager("tm2",t2,o2);
//        t2.addTeamManager(tm2);
//        pa1=new Page(t1);
//        pa2=new Page(t2);
//        pl1=new Player("pl1",new Date(123),PositionEnum.CenterBack,t1,null);
//        pl2=new Player("pl2",new Date(123), PositionEnum.CenterBack,t2,null);
//        c1=new Coach("c1","bla","bla",null);
//        c2=new Coach("c2","bla","bla",null);
//        t1.addCoach(c1);
//        t2.addCoach(c2);
//        m1=new Match(new Date(123456),new Time(123456),0,0,s1,new Season("s1"),t2,t1,null,null,null);
//    }
//
//
//    @Test /*UC 5.2*/
//    public void addCoach()
//    {
//        t1.addCoach(c2);
//        assertTrue(t1.getCoachs().contains(c2));
//        assertTrue(c2.getTeams().contains(t1));
//    }
//
//    @Test /*UC 5.2*/
//    public void removeCoach()
//    {
//        t1.removeCoach(c1);
//        assertFalse(t1.getCoachs().contains(c1));
//        assertFalse(c1.getTeams().contains(t1));
//    }
//
//    @Test
//    public void setLeague()
//    {
//        t1.setLeague(l2);
//        assertEquals(t1.getLeague(),l2);
//        assertTrue(l2.getTeams().contains(t1));
//    }
//
//    @Test
//    public void setStadium()
//    {
//        t1.setStadium(s2);
//        assertEquals(t1.getStadium(),s2);
//        assertTrue(s2.getTeams().contains(t1));
//    }
//
//    @Test
//    public void delete()
//    {
//        t1.delete();
//        assertFalse(t1.hasMatchs());
//        assertFalse(t1.hasPlayers());
//        assertFalse(t1.hasOwners());
//        assertFalse(t1.hasCoachs());
//        assertFalse(t1.hasTeamManagers());
//    }
//
//    @Test
//    public void removeLeauge()
//    {
//        t1.removeLeauge(l1);
//        assertNull(t1.getLeague());
//        assertFalse(l1.getTeams().contains(t1));
//    }
//
//
//    @Test
//    public void setPage()
//    {
//        t1.setPage(pa2);
//        assertEquals(t1.getPage(),pa2);
//
//    }

}