public class TeamManagerTest {
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//
//    Account tm1;
//    Account tm2;
//    Account tm3;
//    Account o1;
//    Account o2;
//    Account o3;
//    Account p2;
//    Account c2;
//
//    Team t1;
//    Team t2;
//    Team t3;
//    @Before
//    public void setUp() throws Exception {
//        DataController.getInstance().clearDataBase();
//        DataController dataController = new DataController();
//        Logger logger = Logger.getInstance();
//        OurSystem ourSystem = new OurSystem();
//        ourSystem.Initialize();
//        List<TeamManager.PermissionEnum> permissions=new ArrayList<>();
//        permissions.add(TeamManager.PermissionEnum.manageLeague);
//        permissions.add(TeamManager.PermissionEnum.managePage);
//        permissions.add(TeamManager.PermissionEnum.manageName);
//        permissions.add(TeamManager.PermissionEnum.manageManagers);
//        permissions.add(TeamManager.PermissionEnum.manageCoaches);
//        permissions.add(TeamManager.PermissionEnum.manageMatches);
//        permissions.add(TeamManager.PermissionEnum.managePlayers);
//        permissions.add(TeamManager.PermissionEnum.manageStadium);
//
//
//        tm1=new Account("tm1",20,"tm1","tm1"); DataController.getInstance().addAccount(tm1);
//        tm2=new Account("tm2",20,"tm2","tm2"); DataController.getInstance().addAccount(tm2);
//        tm3=new Account("tm3",20,"tm3","tm3"); DataController.getInstance().addAccount(tm3);
//        o1=new Account("o1",20,"o1","o1"); DataController.getInstance().addAccount(o1);
//        o2=new Account("o2",20,"o2","o2"); DataController.getInstance().addAccount(o2);
//        o3=new Account("o3",20,"o3","o3"); DataController.getInstance().addAccount(o3);
//        p2=new Account("p2",20,"p2","p2"); DataController.getInstance().addAccount(p2);
//        c2=new Account("c2",20,"c2","c2"); DataController.getInstance().addAccount(c2);
//        t1=new Team("t1",new League("l1"),new Stadium("s1"));
//        t2=new Team("t2",new League("l2"),new Stadium("s2"));
//        t3=new Team("t3",new League("l3"),new Stadium("s3"));
//        o1.addRole(new Owner(o1.getName(),t1,null));
//        o2.addRole(new Owner(o2.getName(),t2,null));
//        o3.addRole(new Owner(o3.getName(),t3,null));
//        p2.addRole(new Player(p2.getName(),new Date(123456),PositionEnum.CenterBack,t2,null));
//        c2.addRole(new Coach(c2.getName(),"bla","bla",null));
//
//        o1.checkIfOwner().appointTeamManagerToTeam(tm1,permissions);
//        o2.checkIfOwner().appointTeamManagerToTeam(tm2,permissions);
//        o3.checkIfOwner().appointTeamManagerToTeam(tm3,permissions);
//
//    }
//
//
//    @Test
//    public void getTeam()
//    {
//        assertEquals(t1,tm1.checkIfTeamManagr().getTeam());
//    }
//
//    @Test
//    public void setTeam()
//    {
//        tm1.checkIfTeamManagr().setTeam(t2);
//        assertEquals(t2,tm2.checkIfTeamManagr().getTeam());
//    }
//
//    @Test
//    public void getAppointer()
//    {
//        assertEquals(o1.checkIfOwner(),tm1.checkIfTeamManagr().getAppointer());
//    }
//
//    @Test /*UC 7.1*/
//    public void changeTeamName() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().changeTeamName("newName");
//        assertEquals("newName",t1.getName());
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageName);
//        try {
//            tm1.checkIfTeamManagr().changeTeamName("newnewName");
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage team name");
//        }
//    }
//
//    @Test /*UC 7.1*/
//    public void addTeamManager() throws Exception {
//
//        //change with permissions
//
//        assertTrue(addTeamManager(tm2.checkIfTeamManagr()));
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers);
//
//        assertFalse(addTeamManager(tm3.checkIfTeamManagr()));
//    }
//
//    @Test /*UC 7.1*/
//    public void removeTeamManager() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().addTeamManager(tm2.checkIfTeamManagr());
//
//
//        assertTrue( removeTeamManager(tm2.checkIfTeamManagr()));
//
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers);
//
//        assertFalse(removeTeamManager(tm2.checkIfTeamManagr()));
//    }
//
//    @Test /*UC 7.1*/
//    public void addCoach() throws Exception {
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//
//        assertFalse( addCoach(c2.checkIfCoach()));
//
//        //change with permissions
//        o1.checkIfOwner().addPermissionToManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//
//
//        assertTrue(addCoach(c2.checkIfCoach()));
//    }
//
//    @Test /*UC 7.1*/
//    public void removeCoach() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        assertTrue(removeCoach(c2.checkIfCoach()));
//
//        //change without permissions
//        tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//
//        assertFalse(removeCoach(c2.checkIfCoach()));
//    }
//
//    @Test /*UC 7.1*/
//    public void addPlayer() throws Exception {
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//
//        assertFalse(addPlayer(p2.checkIfPlayer()));
//
//        //change with permissions
//        o1.checkIfOwner().addPermissionToManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//
//        assertTrue(addPlayer(p2.checkIfPlayer()));
//    }
//
//    @Test /*UC 7.1*/
//    public void removePlayer() throws Exception {
//        //change with permissions
//
//        assertTrue(addPlayer(p2.checkIfPlayer()));
//
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//
//        assertFalse(removePlayer(p2.checkIfPlayer()));
//    }
//
//    @Test /*UC 7.1*/
//    public void setLeague() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().setLeague(new League("newL"));
//        assertEquals(t1.getLeague().getName(),"newL");
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageLeague);
//        try {
//            tm1.checkIfTeamManagr().setLeague(new League("brandNewL"));
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage leauges");
//        }
//    }
//
//
//    @Test /*UC 7.1*/
//    public void removeMatch() throws Exception {
//        //change with permissions
//        Match match=new Match(new Date(123),new Time(132),0,0,t1.getStadium(),new Season("s1"),t2,t1,null,null,null);
//
//        assertTrue(removeMatch(match));
//        //change without permissions
//        match=new Match(new Date(123),new Time(132),0,0,t1.getStadium(),new Season("s1"),t2,t1,null,null,null);
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageMatches);
//
//        assertFalse(removeMatch(match));
//    }
//
//    @Test /*UC 7.1*/
//    public void setStadium() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().setStadium(new Stadium("newS"));
//        assertEquals(t1.getStadium().getName(),"newS");
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageStadium);
//        try {
//            tm1.checkIfTeamManagr().setStadium(new Stadium("brandNewS"));
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage stadiums");
//        }
//    }
//
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
//    public void showTeamManager()
//    {
//        tm1.checkIfTeamManagr().ShowTeamManager();
//        assertEquals("Name:\r\ntm1\r\n\r\nBusinessLayer.OtherCrudOperations.Team managed:\r\nt1\r\n",OS.toString());
//    }
//
//
//    //*----------------------------------------stubs-----------------------------------------------------*/
//
//    public boolean addTeamManager(TeamManager aTeamManager)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers))
//            return false;
//
//        loggerStub("");
//
//        return addTeamManagerStub(aTeamManager);
//    }
//
//    private boolean addTeamManagerStub(TeamManager aTeamManager) {
//        return true;
//    }
//
//    private void loggerStub(String s) {
//    }
//
//    public boolean removeTeamManager(TeamManager aTeamManager)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers))
//            return false;
//
//        loggerStub("");
//
//        return removeTeamManager(aTeamManager,tm1.checkIfTeamManagr().getTeam());
//    }
//
//    private boolean removeTeamManager(TeamManager aTeamManager, Team team) {
//        return true;
//    }
//
//    public boolean addCoach(Coach coach)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches))
//            return false;
//
//        loggerStub("");
//
//        return addCoachStub(coach);
//    }
//
//    private boolean addCoachStub(Coach coach) {
//        return true;
//    }
//
//
//    public boolean removeCoach(Coach coach)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches))
//            return false;
//
//        loggerStub("");
//
//        return removeCoach(coach,tm1.checkIfTeamManagr().getTeam());
//    }
//
//    private boolean removeCoach(Coach coach, Team team) {
//        return true;
//    }
//
//
//    public boolean addPlayer(Player player)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers))
//            return false;
//
//        loggerStub("");
//
//        return addPlayerStub(player);
//    }
//
//    private boolean addPlayerStub(Player player) {
//        return true;
//    }
//
//    public boolean removePlayer(Player player)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers))
//            return false;
//
//        loggerStub("");
//
//        return removePlayerStub(player,tm1.checkIfTeamManagr().getTeam());
//    }
//
//    private boolean removePlayerStub(Player player, Team team) {
//        return true;
//    }
//
//    public boolean removeMatch(Match match)
//    {
//        if(tm1.checkIfTeamManagr().getTeam()==null)
//            return false;
//        if(!tm1.checkIfTeamManagr().getAppointer().hasPermission(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageMatches))
//            return false;
//
//        loggerStub("");
//
//        return removeMatchStub(match,tm1.checkIfTeamManagr().getTeam());
//    }
//
//    private boolean removeMatchStub(Match match, Team team) {
//        return true;
//    }



    //*--------------------------------------------------------------------------------------------------*/

}