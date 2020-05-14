public class SeasonTest {
//    Season season = new Season("2019");
//
//    @Before
//    public void setUp(){
//        DataController.getInstance().clearDataBase();
//    }
//
//    @Test
//    public void setName() {
//        season.setName("2018");
//        assertEquals("2018",season.getName());
//    }
//
//    @Test
//    public void getName() {
//        assertEquals("2019",season.getName());
//    }
//
//    @Test
//    public void getSLsettingsByLeague() {
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
//        sLsettings.put(league,sLsetting);
//        assertEquals(sLsetting,season.getSLsettingsByLeague(league));
//    }
//
//    @Test
//    public void getMatch() {
//        Date date = new Date();
//        Time time= new Time(60,60,60);
//        Stadium stadium = new Stadium("bloomfield");
//        League league = new League("first league");
//        Team away= new Team("maccbi",league,stadium);
//        Team home= new Team("haifa",league,stadium);
//        Referee main = new Referee("3","ziv");
//        Referee line1 = new Referee("3","ziv");
//        Referee line2 = new Referee("3","ziv");
//        Match match = new Match(date,time,3,3,stadium,season,away,home,main,line1,line2);
//        season.addMatch(match);
//        assertEquals(match,season.getMatch(0));
//    }
//
//    @Test
//    public void getMatchs() {
//        List<Match> matches = new LinkedList<>();
//        season.setMatchs(matches);
//        assertEquals(matches,season.getMatchs());
//    }
//
//    @Test
//    public void numberOfMatchs() {
//        Date date = new Date();
//        Time time= new Time(60,60,60);
//        Stadium stadium = new Stadium("bloomfield");
//        League league = new League("first league");
//        Team away= new Team("maccbi",league,stadium);
//        Team home= new Team("haifa",league,stadium);
//        Referee main = new Referee("3","ziv");
//        Referee line1 = new Referee("3","ziv");
//        Referee line2 = new Referee("3","ziv");
//        Match match = new Match(date,time,3,3,stadium,season,away,home,main,line1,line2);
//        season.addMatch(match);
//        assertEquals(1,season.numberOfMatchs());
//    }
//
//    @Test
//    public void hasMatchs() {
//        assertEquals(false,season.hasMatchs());
//        Date date = new Date();
//        Time time= new Time(60,60,60);
//        Stadium stadium = new Stadium("bloomfield");
//        League league = new League("first league");
//        Team away= new Team("maccbi",league,stadium);
//        Team home= new Team("haifa",league,stadium);
//        Referee main = new Referee("3","ziv");
//        Referee line1 = new Referee("3","ziv");
//        Referee line2 = new Referee("3","ziv");
//        Match match = new Match(date,time,3,3,stadium,season,away,home,main,line1,line2);
//        season.addMatch(match);
//        assertEquals(true,season.hasMatchs());
//
//    }
//
//    @Test
//    public void addSLsettingsToLeague() {
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
//        addSLsettingsToLeague(league,sLsetting);
//        assertEquals(sLsetting,season.getSLsettingsByLeague(league));
//        assertEquals(sLsetting,league.getSLsettingsBySeason(season));
//
//    }
//    public boolean addSLsettingsToLeague(League aLeague,SLsettings asLsettings)
//    {
//        season.getsLsettings().put(aLeague,asLsettings);
//        if(!aLeague.hasPolicy(season,asLsettings)){
//            addSLsettingsToSeasonStub(season, asLsettings,aLeague);
//        }
//        return true;
//    }
//
//    public boolean addSLsettingsToSeasonStub(Season aSeason,SLsettings aSlSLsettings,League league)
//    {
//        league.getsLsetting().put(aSeason,aSlSLsettings);
//        if(!aSeason.hasPolicy(league,aSlSLsettings)){
//            addSLsettingsToLeague(league,aSlSLsettings);
//        }
//        return true;
//    }
//
//
//
//    @Test
//    public void removeSLsettingsFromLeague() {
//
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> SLsettings = season.getsLsettings();
//        season.addSLsettingsToLeague(league,sLsetting);
//        removeSLsettingsFromLeague(league,true);
//        assertEquals(0,season.getsLsettings().size());
//    }
//
//    public boolean removeSLsettingsFromLeague(League aLeague,Boolean bool)
//    {
//        if (!season.getsLsettings().containsKey(aLeague)) {
//            return true;
//        }
//        if(aLeague.hasPolicy(season,season.getsLsettings().get(aLeague)) && bool){
//            removeSLsettingsFromSeasonStub(season,false,aLeague);
//        }
//        season.getsLsettings().remove(aLeague);
//        return true;
//    }
//
//    public boolean removeSLsettingsFromSeasonStub(Season aSeason, boolean bool, League league)
//    {
//        if (!league.getsLsetting().containsKey(aSeason)) {
//            return true;
//        }
//        if(aSeason.hasPolicy(league,league.getsLsetting().get(aSeason)) && bool){
//            removeSLsettingsFromLeague(league,false);
//        }
//        league.getsLsetting().remove(aSeason);
//        return true;
//    }
//
//
//    @Test
//    public void addMatch() {
//        Date date = new Date();
//        Time time= new Time(60,60,60);
//        Stadium stadium = new Stadium("bloomfield");
//        League league = new League("first league");
//        Team away= new Team("maccbi",league,stadium);
//        Team home= new Team("haifa",league,stadium);
//        Referee main = new Referee("3","ziv");
//        Referee line1 = new Referee("3","ziv");
//        Referee line2 = new Referee("3","ziv");
//        Match match = new Match(date,time,3,3,stadium,season,away,home,main,line1,line2);
//        season.addMatch(match);
//        assertEquals(match,season.getMatch(0));
//    }
//
//    @Test
//    public void removeMatch() {
//        Date date = new Date();
//        Time time= new Time(60,60,60);
//        Stadium stadium = new Stadium("bloomfield");
//        League league = new League("first league");
//        Team away= new Team("maccbi",league,stadium);
//        Team home= new Team("haifa",league,stadium);
//        Referee main = new Referee("3","ziv");
//        Referee line1 = new Referee("3","ziv");
//        Referee line2 = new Referee("3","ziv");
//        Match match = new Match(date,time,3,3,stadium,season,away,home,main,line1,line2);
//        season.addMatch(match);
//        season.removeMatch(match);
//        assertEquals(0,season.getMatchs().size());
//    }
//
//    @Test
//    public void hasPolicy() {
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
//        season.addSLsettingsToLeague(league,sLsetting);
//        assertEquals(true, season.hasPolicy(league,sLsetting));
//    }
//
//    @Test
//    public void deleteLeague() {
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
//        season.addSLsettingsToLeague(league,sLsetting);
//        season.deleteLeague(league,sLsetting);
//        assertEquals(0,season.getsLsettings().size());
//    }
//
//    @Test
//    public void hasLeague() {
//        League league = new League("first league");
//        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
//        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
//        season.addSLsettingsToLeague(league,sLsetting);
//        assertEquals(true, season.hasLeague(league));
//    }
//
//    @Test
//    public void showSeason() {
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        season.ShowSeason();
//        String expectedOutput  = "Name:\r\n2019\r\n\r\nMatches:\r\n";
//        assertEquals(expectedOutput, outContent.toString());;
//    }

}
