public class MatchTests {
//    private final ByteArrayOutputStream OS = new ByteArrayOutputStream();
//    private final PrintStream PS = System.out;
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
//    public void setUp() {
//        DataController.getInstance().clearDataBase();
//        Logger logger = Logger.getInstance();
//        OurSystem ourSystem = new OurSystem();
//        ourSystem.Initialize();
//
//        refAcc1 = new Account("ref1", 40, "ref1", "ref2");
//        refAcc2 = new Account("ref2", 40, "ref2", "ref2");
//        refAcc3 = new Account("ref3", 40, "ref3", "ref3");
//
//        DataController.getInstance().addAccount(refAcc1);
//        DataController.getInstance().addAccount(refAcc2);
//        DataController.getInstance().addAccount(refAcc3);
//
//        refAcc1.addRole(new Referee("good1", refAcc1.getName()));
//        refAcc2.addRole(new Referee("good2", refAcc2.getName()));
//        refAcc3.addRole(new Referee("good3", refAcc3.getName()));
//        League l1 = new League("l1");
//        se1 = new Season("se1");
//        s1 = new Stadium("s1");
//        awayTeam = new Team("t1", l1, s1);
//        homeTeam = new Team("t2", l1, s1);
//        match = new Match(new Date(123456), new Time(123456), 0, 0, s1, se1, awayTeam, homeTeam, refAcc1.checkIfReferee(), refAcc2.checkIfReferee(), refAcc3.checkIfReferee());
//    }
//
//    @Test
//    public void delete() {
//        match.delete();
//        assertFalse(homeTeam.getMatchs().contains(match));
//        assertFalse(awayTeam.getMatchs().contains(match));
//        assertFalse(refAcc1.checkIfReferee().getMatchs().contains(match));
//        assertFalse(refAcc2.checkIfReferee().getMatchs().contains(match));
//        assertFalse(refAcc3.checkIfReferee().getMatchs().contains(match));
//        assertFalse(s1.getMatchs().contains(match));
//        assertFalse(se1.getMatchs().contains(match));
//        assertNull(match.getStadium());
//        assertNull(match.getSeason());
//    }
//

}
