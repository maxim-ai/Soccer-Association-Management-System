public class TeamManagerTests {
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
//        p2.addRole(new Player(p2.getName(),new Date(123456), PositionEnum.CenterBack,t2,null));
//        c2.addRole(new Coach(c2.getName(),"bla","bla",null));
//
//        o1.checkIfOwner().appointTeamManagerToTeam(tm1,permissions);
//        o2.checkIfOwner().appointTeamManagerToTeam(tm2,permissions);
//        o3.checkIfOwner().appointTeamManagerToTeam(tm3,permissions);
//
//    }
//
//    @Test /*UC 7.1*/
//    public void addTeamManager() throws Exception {
//
//        //change with permissions
//        tm1.checkIfTeamManagr().addTeamManager(tm2.checkIfTeamManagr());
//        assertTrue(t1.getTeamManagers().contains(tm2.checkIfTeamManagr()));
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers);
//        try {
//            tm1.checkIfTeamManagr().addTeamManager(tm3.checkIfTeamManagr());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage team managers");
//
//        }
//    }
//
//    @Test /*UC 7.1*/
//    public void removeTeamManager() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().addTeamManager(tm2.checkIfTeamManagr());
//        assertTrue(t1.getTeamManagers().contains(tm2.checkIfTeamManagr()));
//        tm1.checkIfTeamManagr().removeTeamManager(tm2.checkIfTeamManagr());
//        assertFalse(t1.getTeamManagers().contains(tm2.checkIfTeamManagr()));
//
//        //change without permissions
//        tm1.checkIfTeamManagr().addTeamManager(tm2.checkIfTeamManagr());
//        assertTrue(t1.getTeamManagers().contains(tm2.checkIfTeamManagr()));
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageManagers);
//        try {
//            tm1.checkIfTeamManagr().removeTeamManager(tm2.checkIfTeamManagr());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage team managers");
//
//        }
//    }
//
//    @Test /*UC 7.1*/
//    public void addCoach() throws Exception {
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//        try {
//            tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage coaches");
//        }
//
//        //change with permissions
//        o1.checkIfOwner().addPermissionToManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//        tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        assertTrue(t1.getCoachs().contains(c2.checkIfCoach()));
//    }
//
//    @Test /*UC 7.1*/
//    public void removeCoach() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        assertTrue(t1.getCoachs().contains(c2.checkIfCoach()));
//        tm1.checkIfTeamManagr().removeCoach(c2.checkIfCoach());
//        assertFalse(t1.getCoachs().contains(c2.checkIfCoach()));
//        //change without permissions
//        tm1.checkIfTeamManagr().addCoach(c2.checkIfCoach());
//        assertTrue(t1.getCoachs().contains(c2.checkIfCoach()));
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageCoaches);
//        try {
//            tm1.checkIfTeamManagr().removeCoach(c2.checkIfCoach());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage coaches");
//
//        }
//    }
//
//    @Test /*UC 7.1*/
//    public void addPlayer() throws Exception {
//        //change without permissions
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//        try {
//            tm1.checkIfTeamManagr().addPlayer(p2.checkIfPlayer());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage players");
//
//        }
//
//        //change with permissions
//        o1.checkIfOwner().addPermissionToManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//        tm1.checkIfTeamManagr().addPlayer(p2.checkIfPlayer());
//        assertTrue(t1.getPlayers().contains(p2.checkIfPlayer()));
//    }
//
//    @Test /*UC 7.1*/
//    public void removePlayer() throws Exception {
//        //change with permissions
//        tm1.checkIfTeamManagr().addPlayer(p2.checkIfPlayer());
//        assertTrue(t1.getPlayers().contains(p2.checkIfPlayer()));
//        tm1.checkIfTeamManagr().removePlayer(p2.checkIfPlayer());
//        assertFalse(t1.getPlayers().contains(p2.checkIfPlayer()));
//        //change without permissions
//        tm1.checkIfTeamManagr().addPlayer(p2.checkIfPlayer());
//        assertTrue(t1.getPlayers().contains(p2.checkIfPlayer()));
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.managePlayers);
//        try {
//            tm1.checkIfTeamManagr().removePlayer(p2.checkIfPlayer());
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage players");
//
//        }
//    }
//
//
//    @Test /*UC 7.1*/
//    public void removeMatch() throws Exception {
//        //change with permissions
//        Match match=new Match(new Date(123),new Time(132),0,0,t1.getStadium(),new Season("s1"),t2,t1,null,null,null);
//        tm1.checkIfTeamManagr().removeMatch(match);
//        assertFalse(t1.getMatchs().contains(match));
//        //change without permissions
//        match=new Match(new Date(123),new Time(132),0,0,t1.getStadium(),new Season("s1"),t2,t1,null,null,null);
//        o1.checkIfOwner().removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageMatches);
//        try {
//            tm1.checkIfTeamManagr().removeMatch(match);
//        } catch (Exception e) {
//            assertEquals(e.getMessage(),"The Team manager does not have permission to manage matches ");
//
//        }
//    }
//
//
//    @Test
//    public void delete()
//    {
//        tm1.checkIfTeamManagr().delete();
//        assertFalse(t1.getTeamManagers().contains(tm1.checkIfTeamManagr()));
//    }

}