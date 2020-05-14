public class OurSystemTest {
//    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
//    private final PrintStream PS=System.out;
//    OurSystem ourSystem;
//
//    @Before
//    public void setUp() throws Exception {
//        DataController.getInstance().clearDataBase();
//        ourSystem=new OurSystem();
//        System.setOut(new PrintStream(OS));
//        File loggerFile=new File("Logger");
//        if(loggerFile.exists())
//            loggerFile.delete();
//    }
//    @After
//    public void restore(){
//        System.setOut(PS);
//    }
//
//    //region Getters&Setters Tests
//    @Test
//    public void getSM() {
//        SystemManager SM=new SystemManager("Nadav");
//        OurSystem.setSM(SM);
//        assertNotNull(OurSystem.getSM());
//        assertEquals(SM,OurSystem.getSM());
//    }
//
//    @Test
//    public void setSM() throws IOException {
//        SystemManager SM=new SystemManager("Nadav");
//        OurSystem.setSM(SM);
//        assertNotNull(OurSystem.getSM());
//        assertEquals(SM,OurSystem.getSM());
//        assertTrue(CheckLoggerLines("New system manager Nadav has been set"));
//    }
//
//    @Test
//    public void getCurrGuests(){
//        Guest guest1=new Guest();
//        Guest guest2=new Guest();
//        List<Guest> guestList=new ArrayList<>();
//        guestList.add(guest1); guestList.add(guest2);
//        OurSystem.addGuest(guest1); OurSystem.addGuest(guest2);
//        int counter=0;
//        for(Guest guest:OurSystem.getCurrGuests())
//            assertEquals(guest,guestList.get(counter++));
//    }
//
//    @Test
//    public void getCurrAccounts(){
//        Account account1=new Account("Maxim",26,"MaximX","1234");
//        Account account2=new Account("Sean",26,"SeanX","1234");
//        List<Account> accountList=new ArrayList<>();
//        accountList.add(account1); accountList.add(account2);
//        OurSystem.addAccount(account1); OurSystem.addAccount(account2);
//        int counter=0;
//        for(Account account:OurSystem.getCurrAccounts())
//            assertEquals(account,accountList.get(counter++));
//    }
//
//    @Test
//    public void addGuest(){
//        Guest guest=new Guest();
//        OurSystem.addGuest(guest);
//        assertEquals(guest,OurSystem.getCurrGuests().get(0));
//    }
//
//    @Test
//    public void addAccount(){
//        Account account=new Account("Maxim",26,"MaximX","1234");
//        OurSystem.addAccount(account);
//        assertEquals(account,OurSystem.getCurrAccounts().get(0));
//    }
//
//    @Test
//    public void removeGuest(){
//        Guest guest=new Guest();
//        OurSystem.addGuest(guest);
//        assertEquals(guest,OurSystem.getCurrGuests().get(0));
//        OurSystem.removeGuest(guest);
//        assertEquals(0,OurSystem.getCurrGuests().size());
//    }
//
//    @Test
//    public void removeAccount(){
//        Account account=new Account("Maxim",26,"MaximX","1234");
//        OurSystem.addAccount(account);
//        assertEquals(account,OurSystem.getCurrAccounts().get(0));
//        OurSystem.removeAccount(account);
//        assertEquals(0,OurSystem.getCurrAccounts().size());
//    }
//    //endregion
//
//    //region UC and Technical Tests
//    @Test
//    public void initializeFirstTime() throws IOException {
//        deleteDataFiles();
//        ourSystem.Initialize();
//        assertEquals("Established connection to Accounty System"+"\r\n"+"Established connection to Federal Tax System"+"\r\n",OS.toString());
//        assertNotNull(OurSystem.getSM());
//        assertEquals("Nadav",OurSystem.getSM().getName());
//        assertTrue(CheckLoggerLines("System has been initialized"));
//    }
//    //endregion
//
//
//
//
//    //region Help methods
//    private boolean CheckLoggerLines(String s) {
//        String line= null;
//        try {
//            BufferedReader BR=new BufferedReader(new FileReader(new File("Logger")));
//            line = BR.readLine();
//            BR.close();
//            if(s.equals(line.substring(line.indexOf('-')+2)))
//                return true;
//            else
//                return false;
//        } catch (IOException e) { }
//        return false;
//    }
//
//    private boolean CheckTwoAccountListsEqual(List<Account> a, List<Account> b){
//        if(a.size()!=b.size())
//            return false;
//        for(int i=0;i<a.size();i++){
//            if(!(a.get(i).getUserName().equals(b.get(i).getUserName())))
//                return false;
//        }
//        return true;
//    }
//
//    private boolean CheckTwoTeamListsEqual(List<Team> a, List<Team> b){
//        if(a.size()!=b.size()) return false;
//        for(int i=0;i<a.size();i++){
//            if(!a.get(i).getName().equals(b.get(i).getName()))
//                return false;
//        }
//        return true;
//    }
//
//    private boolean CheckTwoLeagueListsEqual(List<League> a, List<League> b){
//        if(a.size()!=b.size()) return false;
//        for(int i=0;i<a.size();i++){
//            if(!a.get(i).getName().equals(b.get(i).getName()))
//                return false;
//
//        }
//        return true;
//    }
//
//    private boolean CheckTwoStadiumListsEqual(List<Stadium> a, List<Stadium> b){
//        if(a.size()!=b.size()) return false;
//        for(int i=0;i<a.size();i++){
//            if(!a.get(i).getName().equals(b.get(i).getName()))
//                return false;
//
//        }
//        return true;
//    }
//
//    private boolean CheckTwoSeasonListsEqual(List<Season> a, List<Season> b){
//        if(a.size()!=b.size()) return false;
//        for(int i=0;i<a.size();i++){
//            if(!a.get(i).getName().equals(b.get(i).getName()))
//                return false;
//            if(a.get(i).getMatchs().size()!=b.get(i).getMatchs().size())
//                return false;
//            for(int j=0;j<a.get(i).getMatchs().size();j++){
//                if(!checkIfTwoMatchesEqual(a.get(i).getMatch(j),b.get(i).getMatch(j)))
//                    return false;
//            }
//
//
//        }
//        return true;
//    }
//
//    private boolean checkIfTwoMatchesEqual(Match a, Match b){
//        return a.getDate().getTime()==b.getDate().getTime()&&
//                a.getHomeTeam().getName().equals(b.getHomeTeam().getName())&&
//                a.getAwayTeam().getName().equals(b.getAwayTeam().getName())&&
//                a.getAwayScore()==b.getAwayScore()&&
//                a.getHomeScore()==b.getHomeScore()&&
//                a.getStadium().getName().equals(b.getStadium().getName());
//    }
//
//    private void setUpDatabase(List<Account> checkListAccounts, List<Team> checkListTeams, List<League> checkListLeagues, List<Season> checkListSeasons, List<Stadium> checkListStadiums) {
//        Account refAccount1 = new Account("Maxim",26,"MaximX","1234");
//        refAccount1.addRole(new Referee("High","Maxim"));
//        DataController.getInstance().addAccount(refAccount1);
//        checkListAccounts.add(refAccount1);
//        Account refAccount2 = new Account("Maxim",26,"MaximX","1234");
//        refAccount2.addRole(new Referee("High","Maxim"));
//        DataController.getInstance().addAccount(refAccount2);
//        checkListAccounts.add(refAccount2);
//        Account refAccount3 = new Account("Maxim",26,"MaximX","1234");
//        refAccount3.addRole(new Referee("High","Maxim"));
//        DataController.getInstance().addAccount(refAccount3);
//        checkListAccounts.add(refAccount3);
//        List<Referee> refs=new ArrayList<>();
//        refs.add(((Referee)refAccount1.getRole(0)));
//        refs.add(((Referee)refAccount2.getRole(0)));
//        refs.add(((Referee)refAccount3.getRole(0)));
//        Policy checkPolicy=new Policy("aaa","bbb");
//
//        SLsettings checkSLsettings=new SLsettings(checkPolicy);
//        checkSLsettings.setRefereeList(refs);
//        checkPolicy.setsLsettings(checkSLsettings);
//
//        League checkLeague=new League("Champions");
//        DataController.getInstance().addLeague(checkLeague);
//        checkListLeagues.add(checkLeague);
//        Season checkSeason=new Season("Winter");
//        DataController.getInstance().addSeason(checkSeason);
//        checkListSeasons.add(checkSeason);
//        HashMap<Season,SLsettings> checkSeasonSLSettingsMap=new HashMap();
//        checkSeasonSLSettingsMap.put(checkSeason,checkSLsettings);
//        HashMap<League,SLsettings> checkLeagueSLSettingsMap=new HashMap<>();
//        checkLeagueSLSettingsMap.put(checkLeague,checkSLsettings);
//        checkLeague.setsLsetting(checkSeasonSLSettingsMap);
//        checkSeason.setsLsettings(checkLeagueSLSettingsMap);
//
//
//        Stadium CampNo=new Stadium("CampNo");
//        DataController.getInstance().addStadium(CampNo);
//        checkListStadiums.add(CampNo);
//        Stadium Vasermil=new Stadium("Vasermil");
//        DataController.getInstance().addStadium(Vasermil);
//        checkListStadiums.add(Vasermil);
//
//        Team barcelona=new Team("Barcelona",checkLeague,CampNo);
//        DataController.getInstance().addTeam(barcelona);
//        checkListTeams.add(barcelona);
//        Team rome=new Team("Rome",checkLeague,Vasermil);
//        DataController.getInstance().addTeam(rome);
//        checkListTeams.add(rome);
//
//        Account coachAccount1=new Account("Sean",25,"SeanX","1234");
//        DataController.getInstance().addAccount(coachAccount1);
//        checkListAccounts.add(coachAccount1);
//        coachAccount1.addRole(new Coach("Sean","aaa","bbb",null));
//        ((Coach)coachAccount1.getRole(0)).addTeam(barcelona);
//        Account coachAccount2=new Account("Sean",25,"ascxvxcv","1234");
//        DataController.getInstance().addAccount(coachAccount2);
//        checkListAccounts.add(coachAccount2);
//        coachAccount2.addRole(new Coach("Sean","aaa","bbb",null));
//        ((Coach)coachAccount2.getRole(0)).addTeam(rome);
//
//        Account ownerAccount1=new Account("Maxim",26,"sdsadas","3asd");
//        DataController.getInstance().addAccount(ownerAccount1);
//        checkListAccounts.add(ownerAccount1);
//        ownerAccount1.addRole(new Owner("Maxim",barcelona,null));
//        Account ownerAccount2=new Account("Maxim",26,"cvxvcx","3asd");
//        DataController.getInstance().addAccount(ownerAccount2);
//        checkListAccounts.add(ownerAccount2);
//        ownerAccount2.addRole(new Owner("Maxim",rome,null));
//
//        Account playerAccount1=new Account("Maxim",26,"vcxvcxvx","3asd");
//        DataController.getInstance().addAccount(playerAccount1);
//        checkListAccounts.add(playerAccount1);
//        playerAccount1.addRole(new Player("Maxim",new Date(),PositionEnum.Goalkeeper,barcelona,null));
//        Account playerAccount2=new Account("Maxim",26,"bter","3asd");
//        DataController.getInstance().addAccount(playerAccount2);
//        checkListAccounts.add(playerAccount2);
//        playerAccount2.addRole(new Player("Maxim",new Date(),PositionEnum.Goalkeeper,rome,null));
//
//        Account teamManagerAccount1=new Account("Maxim",26,"vcxvchgfxvx","3asd");
//        DataController.getInstance().addAccount(teamManagerAccount1);
//        checkListAccounts.add(teamManagerAccount1);
//        teamManagerAccount1.addRole(new TeamManager("Maxim",barcelona,(Owner)ownerAccount1.getRole(0)));
//        Account teamManagerAccount2=new Account("Maxim",26,"bter","3asd");
//        DataController.getInstance().addAccount(teamManagerAccount2);
//        checkListAccounts.add(teamManagerAccount2);
//        teamManagerAccount2.addRole(new TeamManager("Maxim",rome,(Owner)ownerAccount2.getRole(0)));
//
//        Match checkMatch=new Match(new Date(),new Time(1),5,3,CampNo,checkSeason,rome,barcelona,(Referee) refAccount1.getRole(0),(Referee) refAccount2.getRole(0),(Referee) refAccount3.getRole(0));
//
//        barcelona.setLeague(checkLeague);
//        barcelona.setStadium(CampNo);
//        barcelona.addTeamManager((TeamManager)teamManagerAccount1.getRole(0));
//        barcelona.addCoach((Coach)coachAccount1.getRole(0));
//        barcelona.addOwner((Owner)ownerAccount1.getRole(0));
//        barcelona.addMatch(checkMatch,"home");
//        rome.setLeague(checkLeague);
//        rome.setStadium(Vasermil);
//        rome.addTeamManager((TeamManager)teamManagerAccount2.getRole(0));
//        rome.addCoach((Coach)coachAccount2.getRole(0));
//        rome.addOwner((Owner)ownerAccount2.getRole(0));
//        rome.addMatch(checkMatch,"away");
//
//        checkSeason.addMatch(checkMatch);
//        CampNo.addMatch(checkMatch);
//    }
//
//    private void deleteDataFiles() {
//        File file=new File("firstInitCheck");
//        if(file.exists())
//            file.delete();
//        File[] dataFiles=new File[5];
//        dataFiles[0]=new File("AccountsData.txt");
//        dataFiles[1]=new File("TeamsData.txt");
//        dataFiles[2]=new File("LeaguesData.txt");
//        dataFiles[3]=new File("SeasonsData.txt");
//        dataFiles[4]=new File("StadiumsData.txt");
//        for(File f:dataFiles){
//            if(f.exists())
//                f.delete();
//        }
//
//    }
//    //endregion
//





}