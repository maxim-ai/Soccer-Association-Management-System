public class TeamTest {
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
//        pl1=new Player("pl1",new Date(123), PositionEnum.CenterBack,t1,null);
//        pl2=new Player("pl2",new Date(123),PositionEnum.CenterBack,t2,null);
//        c1=new Coach("c1","bla","bla",null);
//        c2=new Coach("c2","bla","bla",null);
//        t1.addCoach(c1);
//        t2.addCoach(c2);
//        m1=new Match(new Date(123456),new Time(123456),0,0,s1,new Season("s1"),t2,t1,null,null,null);
//    }
//    @Test
//    public void setName()
//    {
//        t1.setName("newName");
//        assertEquals("newName",t1.getName());
//    }
//
//    @Test
//    public void getName()
//    {
//        assertEquals("t1",t1.getName());
//    }
//
//    @Test
//    public void getTeamManager()
//    {
//        assertEquals(t1.getTeamManager(0),tm1);
//    }
//
//    @Test
//    public void getTeamManagers()
//    {
//        assertTrue(t1.getTeamManagers().contains(tm1));
//    }
//
//    @Test
//    public void numberOfTeamManagers()
//    {
//        assertEquals(t1.numberOfTeamManagers(),1);
//    }
//
//    @Test
//    public void hasTeamManagers()
//    {
//        assertTrue(t1.hasTeamManagers());
//    }
//
//    @Test
//    public void indexOfTeamManager()
//    {
//        assertEquals(t1.indexOfTeamManager(tm1),0);
//    }
//
//    @Test
//    public void getPage()
//    {
//        assertEquals(t1.getPage(),pa1);
//    }
//
//    @Test
//    public void getCoach()
//    {
//        assertEquals(t1.getCoach(0),c1);
//    }
//
//    @Test
//    public void getCoachs()
//    {
//        assertTrue(t1.getCoachs().contains(c1));
//    }
//
//    @Test
//    public void numberOfCoachs()
//    {
//        assertEquals(t1.numberOfCoachs(),1);
//    }
//
//    @Test
//    public void hasCoachs()
//    {
//        assertTrue(t1.hasCoachs());
//    }
//
//    @Test
//    public void indexOfCoach()
//    {
//        assertEquals(t1.indexOfCoach(c1),0);
//        assertEquals(t1.indexOfCoach(c2),-1);
//    }
//
//    @Test
//    public void getOwner()
//    {
//        assertEquals(o1,t1.getOwner(0));
//    }
//
//    @Test
//    public void getOwners()
//    {
//        assertTrue(t1.getOwners().contains(o1));
//    }
//
//    @Test
//    public void numberOfOwners()
//    {
//        assertEquals(t1.numberOfOwners(),1);
//    }
//
//    @Test
//    public void hasOwners()
//    {
//        assertTrue(t1.hasOwners());
//    }
//
//    @Test
//    public void indexOfOwner()
//    {
//        assertEquals(t1.indexOfOwner(o1),0);
//    }
//
//    @Test
//    public void getPlayer()
//    {
//        assertEquals(t1.getPlayer(0),pl1);
//    }
//
//    @Test
//    public void getPlayers()
//    {
//        assertTrue(t1.getPlayers().contains(pl1));
//        assertFalse(t1.getPlayers().contains(pl2));
//    }
//
//    @Test
//    public void numberOfPlayers()
//    {
//        assertEquals(t1.numberOfPlayers(),1);
//    }
//
//    @Test
//    public void hasPlayers()
//    {
//        assertTrue(t1.hasPlayers());
//    }
//
//    @Test
//    public void indexOfPlayer()
//    {
//        assertEquals(t1.indexOfPlayer(pl1),0);
//        assertEquals(t1.indexOfPlayer(pl2),-1);
//    }
//
//    @Test
//    public void getLeague()
//    {
//        assertEquals(t1.getLeague(),l1);
//    }
//
//    @Test
//    public void getMatch()
//    {
//        assertEquals(t1.getMatch(0),m1);
//    }
//
//    @Test
//    public void getMatchs()
//    {
//        assertTrue(t1.getMatchs().contains(m1));
//    }
//
//    @Test
//    public void numberOfMatchs()
//    {
//        assertEquals(t1.numberOfMatchs(),1);
//    }
//
//    @Test
//    public void hasMatchs()
//    {
//        assertTrue(t1.hasMatchs());
//    }
//
//    @Test
//    public void indexOfMatch()
//    {
//        assertEquals(t1.indexOfMatch(m1),0);
//    }
//
//    @Test
//    public void getStadium()
//    {
//        assertEquals(t1.getStadium(),s1);
//    }
//
//
//    @Test
//    public void addTeamManager()
//    {
//        t1.addTeamManager(tm2);
//        assertTrue(t1.getTeamManagers().contains(tm2));
//        assertEquals(tm2.getTeam(),t1);
//    }
//
//    @Test
//    public void removeTeamManager()
//    {
//        t1.removeTeamManager(tm1);
//        assertFalse(t1.getTeamManagers().contains(tm1));
//        assertNull(tm1.getTeam());
//    }
//
//    @Test /*UC 5.2*/
//    public void addCoach()
//    {
//
//        assertTrue(addCoach(c2));
//    }
//
//    @Test /*UC 5.2*/
//    public void removeCoach()
//    {
//        assertTrue(removeCoach(c1));
//    }
//
//    @Test
//    public void addOwner()
//    {
//        t1.addOwner(o2);
//        assertTrue(t1.getOwners().contains(o2));
//        assertTrue(o2.getTeam().equals(t1));
//    }
//
//    @Test
//    public void removeOwner()
//    {
//        t1.removeOwner(o1);
//        assertFalse(t1.getOwners().contains(o1));
//    }
//
//    @Test /*UC 4.2*/
//    public void addPlayer()
//    {
//        t1.addPlayer(pl2);
//        assertTrue(t1.getPlayers().contains(pl2));
//        assertTrue(pl2.getTeam().equals(t1));
//    }
//
//    @Test /*UC 4.2*/
//    public void removePlayer()
//    {
//        t1.removePlayer(pl1);
//        assertFalse(t1.getPlayers().contains(pl1));
//        assertNull(pl1.getTeam());
//    }
//
//    @Test
//    public void setLeague()
//    {
//        assertTrue(setLeague(l2));
//    }
//
//    @Test
//    public void addMatch()
//    {
//        m2=new Match(new Date(123),new Time(123),0,0,s2,new Season("ss1"),null,null,null,null,null);
//        t1.addMatch(m2,"away");
//        assertEquals(m2.getAwayTeam(),t1);
//        assertTrue(t1.getMatchs().contains(m2));
//    }
//
//    @Test
//    public void removeMatch()
//    {
//        t1.removeMatch(m1);
//        assertFalse(t1.getMatchs().contains(m1));
//        assertNotEquals(m1.getHomeTeam(),t1);
//        assertNotEquals(m1.getAwayTeam(),t1);
//    }
//
//    @Test
//    public void setStadium()
//    {
//
//        assertTrue(setStadium(s2));
//    }
//
//
//    @Test
//    public void removePage()
//    {
//        t1.removePage();
//        assertNull(t1.getPage());
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
//    public void showTeam()
//    {
//        t1.ShowTeam();
//        assertEquals("Name:\r\nt1\r\n\r\nTeamManagers:\r\ntm1\r\n\r\nCoaches:\r\nc1\r\n\r\nTeamOwners:\r\no1\r\n\r\nPlayers:\r\npl1\r\n\r\n" +
//                "BusinessLayer.OtherCrudOperations.League:\r\nl1\r\n\r\nMatches:\r\nt1 against t2\r\n\r\nBusinessLayer.OtherCrudOperations.Stadium:\r\ns1\r\n\r\n",OS.toString());
//    }
//
//    //*----------------------------------stubs-----------------------------------------*/
//
//    public boolean addCoach(Coach aCoach)
//    {
//        boolean wasAdded = true;
//        if (t1.getCoachs().contains(aCoach)) { return true; }
//        if (aCoach.indexOfTeam(t1) != -1)
//        {
//            wasAdded = true;
//        }
//        else
//        {
//            addTeamStub(t1);
//            if (!wasAdded)
//            {
//                t1.getCoachs().remove(aCoach);
//            }
//        }
//        pageUpdatedStub();
//        return wasAdded;
//    }
//
//    private void pageUpdatedStub() {
//    }
//
//    private void addTeamStub(Team t1) {
//    }
//
//    public boolean removeCoach(Coach aCoach)
//    {
//        boolean wasRemoved = true;
//        if (!t1.getCoachs().contains(aCoach))
//        {
//            return wasRemoved;
//        }
//
//        int oldIndex = t1.getCoachs().indexOf(aCoach);
//        if (aCoach.indexOfTeam(t1) == -1)
//        {
//            wasRemoved = true;
//        }
//        else
//        {
//            wasRemoved = removeTeamStub(t1);
//            if (!wasRemoved)
//            {
//                t1.getCoachs().add(oldIndex,aCoach);
//            }
//        }
//        pageUpdatedStub();
//        return wasRemoved;
//    }
//
//    private boolean removeTeamStub(Team t1) {
//        return true;
//    }
//
//    public boolean setLeague(League aLeague)
//    {
//        boolean wasSet = true;
//        if (aLeague == null)
//        {
////            league=null;
//            return wasSet;
//        }
//
////        league = aLeague;
//
//        addTeamStub(t1);
//        wasSet = true;
//        pageUpdatedStub();
//        return wasSet;
//    }
//
//    public boolean setStadium(Stadium aStadium)
//    {
//        boolean wasSet = true;
//        if (aStadium == null)
//        {
////            stadium=null;
//            wasSet=true;
//            return wasSet;
//        }
//
//        Stadium existingStadium = t1.getStadium();
////        stadium = aStadium;
//        if (existingStadium != null && !existingStadium.equals(aStadium))
//        {
//            boolean didRemove = removeTeamStub(t1);
//            if (!didRemove)
//            {
////                stadium = existingStadium;
//                return wasSet;
//            }
//        }
//        addTeamStub(t1);
//        wasSet = true;
//        pageUpdatedStub();
//        return wasSet;
//    }


}