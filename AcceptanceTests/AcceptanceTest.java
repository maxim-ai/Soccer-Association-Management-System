import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.security.Permission;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AcceptanceTest {
    final long acceptableTime=100;
    OurSystem ourSystem;
    Account arAccount1, arAccount2;
    AssociationRepresentative ar1, ar2;
    League league1, league2;
    Season season;
    Policy policy1, policy2;
    Account refAccount1, refAccount2, refAccount3, refAccount4, refAccount5, refAccount6;
    Referee referee1, referee2, referee3, referee4, referee5, referee6;
    SLsettings sLsettings1, sLsettings2;
    Account ownerAccount1, ownerAccount2, ownerAccount3, ownerAccount4;
    Owner owner1, owner2 ,owner3, owner4;
    Account tmAccount1, tmAccount2, tmAccount3, tmAccount4;
    TeamManager tm1, tm2, tm3, tm4;
    Account playerAccount1, playerAccount2, playerAccount3, playerAccount4, playerAccount5, playerAccount6, playerAccount7, playerAccount8;
    Player player1, player2, player3, player4, player5, player6, player7, player8;
    Match match1, match2;
    Team team1, team2, team3, team4;
    Stadium stadium1, stadium2, stadium3, stadium4;
    Account coachAccount1, coachAccount2, coachAccount3, coachAccount4;
    Coach coach1, coach2, coach3, coach4;
    Page  playerPage1, playerPage2, playerPage3, playerPage4, playerPage5, playerPage6, playerPage7, playerPage8,
            coachPage1, coachPage2, coachPage3, coachPage4, teamPage1, teamPage2, teamPage3, teamPage4;
    Account fanAccount1, fanAccount2, fanAccount3, fanAccount4, fanAccount5, fanAccount6, fanAccount7, fanAccount8;
    Fan fan1, fan2, fan3, fan4, fan5, fan6, fan7, fan8;

    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;

    @Before
    public void setUp()  {
        System.setOut(new PrintStream(OS));
        InitDatabase();
        File file=new File("firstInitCheck");
        if(file.exists())
            file.delete();
        ourSystem=new OurSystem();
        ourSystem.Initialize();
        File loggerFile=new File("Logger");
        if(loggerFile.exists())
            loggerFile.delete();
    }
    @After
    public void restore(){
        DataManager.clearDataBase();
        System.setOut(PS);
    }


    //region System tests
    @Test
    public void UC1point1(){
        long before=0;
        long after=0;
        String s="";
        //Test for successful communication with external systems and create System manager
        OS.reset();
        before=System.currentTimeMillis();
        ourSystem.Initialize();
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Established connection to Accounty System\r\nEstablished connection to Federal Tax System\r\n";
        assertEquals(s,OS.toString());
        assertNotNull(OurSystem.getSM());
        assertEquals("Nadav",OurSystem.getSM().getName());
        //End test

        //Test successful for saving operation
        restore();setUp();
        GuestController GC=OurSystem.makeGuestController();
        GC.SignIn("Maxim",26,"MaximX","1234");
        FanContoller FC= (FanContoller) GC.LogIn("MaximX","1234").get(0);
        FC.Search("Name","Team1");
        ourSystem.logOffSystem();
        ourSystem.Initialize();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC= (FanContoller) GC.LogIn("MaximX","1234").get(0);
        FC.ShowSearchHistory();
        s="Criterion:\r\n" + "Name\r\n" + "Query:\r\n" + "Team1\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

    }
    //endregion

    //region Guest tests
    @Test
    public void UC2point1(){
        GuestController GC=null;
        long before=0, after=0;

        //Test for successful signing in
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.SignIn("Maxim",26,"MaximX","Password"));
        after=System.currentTimeMillis();
        assertNotNull(GC.LogIn("MaximX","Password"));
        assertTrue((after-before)<acceptableTime);
        // End test

        //Test for unsuccessful signing in (Username already exists)
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.SignIn("Sean",26,"MaximX","Passwroddd"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful sign in (name with numbers)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.SignIn("Maxim2",26,"Maxim2X","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful sign in (name is empty)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.SignIn("",26,"Maxim2X","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful sign in (username is empty)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.SignIn("Maxim2",26,"","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful sign in (password is empty)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        assertFalse(GC.SignIn("Maxim2",26,"Maxim2X",""));
        //End test

        //Test for unsuccessful sign in (age is negative)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.SignIn("Maxim2",-26,"Maxim2X","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }

    @Test
    public void UC2point2(){
        GuestController GC=null;
        long before=0, after=0;

        //Test for successful logging in
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertNotNull(GC.LogIn("Fan1X","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        assertEquals(1,OurSystem.getCurrAccounts().size());
        assertEquals(fanAccount1,OurSystem.getCurrAccounts().get(0));
        //End test

        //Test for unsuccessful logging in (username doesn't exists)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertNull(GC.LogIn("Fan1XX","Password"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }

    @Test
    public void UC2point3Guest(){
        GuestController GC=null;
        long before=0, after=0;
        String s="";

        //Test for successful info showing of leagues
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.ShowInfo("Leagues"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of coaches
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.ShowInfo("Coaches"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Coach1\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team1\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team2\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team3\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End Test

        //Test for successful info showing of teams
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.ShowInfo("Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of players
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.ShowInfo("Players"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of seasons
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.ShowInfo("Seasons"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful info showing (infoAbout is empty)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.ShowInfo(""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End Test

        //Test for unsuccessful info showing (infoAbout is not exist)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.ShowInfo("Animals"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End Test
    }

    @Test
    public void UC2point4Guest(){
        GuestController GC=null;
        long before=0, after=0;
        String s="";

        //Test for successful search by name
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Search("Name","Referee1"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Accounts with the name Referee1\r\n" + "Name:\r\n" + "Referee1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category teams
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Search("Category","Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category accounts
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Search("Category","Accounts"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "AR1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "AssociationRepresentative\r\n" + "\r\n" + "Name:\r\n" + "AR2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "AssociationRepresentative\r\n" + "\r\n" + "Name:\r\n" + "Referee1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Owner1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "TM1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n"
                + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Coach1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" +
                "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Fan1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Nadav\r\n" + "\r\n" + "Age:\r\n" + "26\r\n" + "\r\n" + "Roles:\r\n" + "SystemManager\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category Leagues
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Search("Category","Leagues"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category Seasons
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Search("Category","Seasons"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful search (criterion length is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Search("","Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (query length is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Search("Name",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (wrong criterion)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Search("Namee","Team1"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (wrong Category)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Search("Category","Footballs"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }

    @Test
    public void UC2point5Guest(){
        GuestController GC=null;
        long before=0, after=0;
        String s="";

        //Test for successful filtering by player role
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Role","Players"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by coach role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Role","Coaches"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Coach1\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team1\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team2\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team3\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by team manager role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Role","TeamManagers"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "TM1\r\n" + "\r\n" + "Team managed:\r\n" + "Team1\r\n" + "Name:\r\n" + "TM2\r\n" + "\r\n" + "Team managed:\r\n" + "Team2\r\n" + "Name:\r\n" + "TM3\r\n" + "\r\n" + "Team managed:\r\n" + "Team3\r\n" + "Name:\r\n" + "TM4\r\n" + "\r\n" + "Team managed:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by owner role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Role","Owners"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Owner1\r\n" + "Team owned:\r\n" + "Team1\r\n" + "Name:\r\n" + "Owner2\r\n" + "Team owned:\r\n" + "Team2\r\n" + "Name:\r\n" + "Owner3\r\n" + "Team owned:\r\n" + "Team3\r\n" + "Name:\r\n" + "Owner4\r\n" + "Team owned:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by referee role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Role","Referees"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Referee1\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee2\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee3\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee4\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee5\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee6\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by team
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Team",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by league
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("League",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by season
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertTrue(GC.Filter("Season",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful filtering (category lengh is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Filter("","Owner"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful filtering (wrong categoty)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Filter("Football",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful filtering (wrong role)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        before=System.currentTimeMillis();
        assertFalse(GC.Filter("Role","Commentator"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test


    }

    //endregion

    //region Fan tests
    @Test
    public void UC2point3Fan(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful info showing of leagues
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.ShowInfo("Leagues"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of coaches
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.ShowInfo("Coaches"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Coach1\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team1\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team2\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team3\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End Test

        //Test for successful info showing of teams
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.ShowInfo("Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of players
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.ShowInfo("Players"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful info showing of seasons
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.ShowInfo("Seasons"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful info showing (infoAbout is empty)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.ShowInfo(""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End Test

        //Test for unsuccessful info showing (infoAbout is not exist)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.ShowInfo("Animals"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End Test
    }

    @Test
    public void UC2point4Fan(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful search by name
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Search("Name","Referee1"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Accounts with the name Referee1\r\n" + "Name:\r\n" + "Referee1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category teams
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Search("Category","Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category accounts
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Search("Category","Accounts"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "AR1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "AssociationRepresentative\r\n" + "\r\n" + "Name:\r\n" + "AR2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "AssociationRepresentative\r\n" + "\r\n" + "Name:\r\n" + "Referee1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Referee6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Referee\r\n" + "\r\n" + "Name:\r\n" + "Owner1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" + "\r\n" + "Name:\r\n" + "Owner4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Owner\r\n" +
                "\r\n" + "Name:\r\n" + "TM1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "TM4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "TeamManager\r\n" + "\r\n" + "Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Player\r\n" + "\r\n" + "Name:\r\n"
                + "Coach1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Coach\r\n" + "\r\n" + "Name:\r\n" + "Fan1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Fan8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Roles:\r\n" + "Fan\r\n" + "\r\n" + "Name:\r\n" + "Nadav\r\n" + "\r\n" + "Age:\r\n" + "26\r\n" + "\r\n" + "Roles:\r\n" + "SystemManager\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category Leagues
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Search("Category","Leagues"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful search by category Seasons
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Search("Category","Seasons"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful search (criterion length is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Search("","Teams"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (query length is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Search("Name",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (wrong criterion)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Search("Namee","Team1"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful search (wrong Category)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Search("Category","Footballs"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }

    @Test
    public void UC2point5Fan(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful filtering by player role
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Role","Players"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player3\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player4\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team2\r\n" + "Name:\r\n" + "Player5\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player6\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team3\r\n" + "Name:\r\n" + "Player7\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n" + "Name:\r\n" + "Player8\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by coach role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Role","Coaches"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Coach1\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team1\r\n" + "Name:\r\n" + "Coach2\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team2\r\n" + "Name:\r\n" + "Coach3\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team3\r\n" + "Name:\r\n" + "Coach4\r\n" + "\r\n" + "Training:\r\n" + "Full\r\n" + "\r\n" + "TeamRole:\r\n" + "Main\r\n" + "\r\n" + "TeamsCoaching:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by team manager role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Role","TeamManagers"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "TM1\r\n" + "\r\n" + "Team managed:\r\n" + "Team1\r\n" + "Name:\r\n" + "TM2\r\n" + "\r\n" + "Team managed:\r\n" + "Team2\r\n" + "Name:\r\n" + "TM3\r\n" + "\r\n" + "Team managed:\r\n" + "Team3\r\n" + "Name:\r\n" + "TM4\r\n" + "\r\n" + "Team managed:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by owner role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Role","Owners"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Owner1\r\n" + "Team owned:\r\n" + "Team1\r\n" + "Name:\r\n" + "Owner2\r\n" + "Team owned:\r\n" + "Team2\r\n" + "Name:\r\n" + "Owner3\r\n" + "Team owned:\r\n" + "Team3\r\n" + "Name:\r\n" + "Owner4\r\n" + "Team owned:\r\n" + "Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by referee role
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Role","Referees"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Referee1\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee2\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee3\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee4\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee5\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "Name:\r\n" + "Referee6\r\n" + "\r\n" + "Training:\r\n" + "Complete\r\n" + "\r\n" + "Matches judged:\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee4, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by team
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Team",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Team1\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM1\r\n" + "\r\n" + "Coaches:\r\n" + "Coach1\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner1\r\n" + "\r\n" + "Players:\r\n" + "Player1\r\n" + "Player2\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team1 against Team2\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Name:\r\n" + "Team2\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM2\r\n" + "\r\n" + "Coaches:\r\n" + "Coach2\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner2\r\n" + "\r\n" + "Players:\r\n" + "Player3\r\n" + "Player4\r\n" + "\r\n" + "League:\r\n" + "League1\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium2\r\n" + "\r\n" + "Name:\r\n" + "Team3\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM3\r\n" + "\r\n" + "Coaches:\r\n" + "Coach3\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner3\r\n" + "\r\n" + "Players:\r\n" + "Player5\r\n" + "Player6\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team3 against Team4\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium3\r\n" + "\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by league
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("League",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "League1\r\n" + "\r\n" + "Teams in league:\r\n" + "Team1\r\n" + "Team2\r\n" + "\r\n" + "Name:\r\n" + "League2\r\n" + "\r\n" + "Teams in league:\r\n" + "Team3\r\n" + "Team4\r\n\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for successful filtering by season
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Filter("Season",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Season\r\n" + "\r\n" + "Matches:\r\n" + "Team2 against Team1\r\n" + "Team3 against Team4\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful filtering (category lengh is 0)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Filter("","Owner"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful filtering (wrong categoty)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Filter("Football",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful filtering (wrong role)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Filter("Role","Commentator"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test


    }

    @Test
    public void UC3point1(){
        GuestController GC=null;
        FanContoller FC=null;
        String s="";
        long before=0, after=0;

        //Test for successful logging out
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        FC.LogOut();
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        assertEquals(0,OurSystem.getCurrAccounts().size());
        s="Logged out\r\n";
        assertEquals(s, OS.toString());
        //End test
    }

    @Test
    public void UC3point2(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;

        //Test for successful subscribing track personal pages
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        FC.SubscribeTrackPersonalPages();
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        assertTrue(FC.getFan().isTrackPersonalPages());
        team4.setName("Team44");
        assertEquals(1,fan1.getAlertList().size());
        assertEquals("Team44 page updated",fan1.getAlertList().get(0).getDescription());
        //End test
    }

    @Test
    public void UC3point3(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;

        //Test for successful subscribing track matches
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        FC.SubscribeGetMatchNotifications();
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        assertTrue(FC.getFan().isGetMatchNotifications());

        match1=new Match(new Date(),new Time(22,0,0),0,0,stadium1,season
                ,team2,team1,null,null,null);
        assertEquals(1,fan1.getAlertList().size());
        assertEquals("Team2 against Team1",fan1.getAlertList().get(0).getDescription());
        //End test
    }

    @Test
    public void UC3point4(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;

        //Test for successful report
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.Report("aaa"));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful report (report is empty)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.Report(""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }

    @Test
    public void UC3point5(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful showing search history
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        FC.Search("Name","Player1");
        FC.Search("Category","Teams");
        OS.reset();
        before=System.currentTimeMillis();
        assertTrue(FC.ShowSearchHistory());
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Criterion:\r\n" + "Name\r\n" + "Query:\r\n" + "Player1\r\n" + "\r\n" + "Criterion:\r\n" + "Category\r\n" + "Query:\r\n" + "Teams\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test

        //Test for unsuccessful showing search history (search history is empty)
        restore();setUp();
        OS.reset();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.ShowSearchHistory());
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="";
        assertEquals(s,OS.toString());
        //End test

    }

    @Test
    public void UC3point6(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful showing personal info
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        OS.reset();
        before=System.currentTimeMillis();
        FC.ShowPersonalInfo();
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        s="Name:\r\n" + "Fan1\r\n" + "\r\n" + "Teams Tracked:\r\n" + "Name:\r\n" + "Team4\r\n" + "\r\n" + "TeamManagers:\r\n" + "TM4\r\n" + "\r\n" + "Coaches:\r\n" + "Coach4\r\n" + "\r\n" + "TeamOwners:\r\n" + "Owner4\r\n" + "\r\n" + "Players:\r\n" + "Player7\r\n" + "Player8\r\n" + "\r\n" + "League:\r\n" + "League2\r\n" + "\r\n" + "Matches:\r\n" + "Team4 against Team3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Players Tracked:\r\n" + "Name:\r\n" + "Player1\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "Name:\r\n" + "Player2\r\n" + "\r\n" + "Age:\r\n" + "99\r\n" + "\r\n" + "Position:\r\n" + "Striker\r\n" + "\r\n" + "Team:\r\n" + "Team1\r\n" + "\r\n";
        assertEquals(s,OS.toString());
        //End test
    }

    @Test
    public void UC3point7(){
        GuestController GC=null;
        FanContoller FC=null;
        long before=0, after=0;
        String s="";

        //Test for successful editing personal info
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertTrue(FC.EditPersonalInfo("","Fan1Y",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        assertEquals("Fan1Y",fanAccount1.getUserName());
        //End test

        //Test for unsuccessful editing personal info (username already exists)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.EditPersonalInfo("","Referee1X",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test

        //Test for unsuccessful editing personal info (all fields empty)
        restore();setUp();
        GC=OurSystem.makeGuestController();
        FC=(FanContoller)GC.LogIn("Fan1X","Password").get(0);
        before=System.currentTimeMillis();
        assertFalse(FC.EditPersonalInfo("","",""));
        after=System.currentTimeMillis();
        assertTrue((after-before)<acceptableTime);
        //End test
    }
    //endregion

    //region Player tests
    @Test
    public void UC4point1()
    {
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Player1X","Password");
        PlayerController playerController= (PlayerController)controllerList.get(0);
        long before=System.currentTimeMillis();
        Date testDate=new Date();
        playerController.updateDetails(testDate,PositionEnum.CenterBack,team2);
        assertTrue(player1.getPosition().equals(PositionEnum.CenterBack));
        assertEquals(player1.getTeam(),team2);
        assertTrue(System.currentTimeMillis()-before<acceptableTime);


    }
    //endregion

    //region Coach tests
    @Test
    public void UC5point1()
    {
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Coach1X","Password");
        CoachController coachController= (CoachController)controllerList.get(0);
        long before=System.currentTimeMillis();
        coachController.updateDetails("aa","bb");
        assertTrue(coach1.getTraining().equals("aa"));
        assertTrue(coach1.getTeamRole().equals("bb"));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

    }
    //endregion

    //region System manager tests
    @Test
    public void UC8point1(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("NadavX","1234");
        SystemManagerController systemManagerController=(SystemManagerController) controllerList.get(0);
        long before= System.currentTimeMillis();
        systemManagerController.DeleteTeamPermanently(team3);
        long after= System.currentTimeMillis();
        assertEquals(DataManager.getTeams().size(),3);
        assertEquals(null,team3.getStadium());
        assertEquals(0,team3.getCoachs().size());
        assertEquals(0,team3.getTeamManagers().size());
        assertEquals(0,team3.getOwners().size());
        assertEquals(null,team3.getLeague());
        assertEquals(null,team3.getPage());
        assertEquals(0,team3.getPlayers().size());
        assertEquals("description: Delete Team Permanently:" + team3.getName(),owner3.getAlertList().get(0).toString());
        assertEquals("description: Delete Team Permanently:" + team3.getName(),tm3.getAlertList().get(0).toString());
        long time = after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC8point2(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("NadavX","1234");
        SystemManagerController systemManagerController=(SystemManagerController) controllerList.get(0);
        long before= System.currentTimeMillis();
        systemManagerController.deleteAccount(tmAccount3);
        long after= System.currentTimeMillis();
        assertEquals(36,DataManager.getAccounts().size());
        systemManagerController.deleteAccount(refAccount3);
        assertEquals(35,DataManager.getAccounts().size());
        systemManagerController.deleteAccount(ownerAccount3);
        assertEquals(35,DataManager.getAccounts().size());
        long time = after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC8point3(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("NadavX","1234");
        SystemManagerController systemManagerController=(SystemManagerController) controllerList.get(0);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        long before= System.currentTimeMillis();
        systemManagerController.addComplain("team1 is the best");
        systemManagerController.showComplaints();
        long after= System.currentTimeMillis();
        String expectedOutput  = "team1 is the best\r\n";
        assertEquals(expectedOutput, outContent.toString());
        long time = after-before;
        assertTrue(time<acceptableTime);


    }

    @Test
    public void UC8point3point2(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("NadavX","1234");
        SystemManagerController systemManagerController=(SystemManagerController) controllerList.get(0);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        long before= System.currentTimeMillis();
        systemManagerController.addComplain("team1 is the best");
        systemManagerController.addComment("you are right","team1 is the best");
        long after= System.currentTimeMillis();
        String string =systemManagerController.getComplaintAndComments().get("team1 is the best");
        assertEquals("you are right",string);
        long time = after-before;
        assertTrue(time<acceptableTime);

    }

    @Test
    public void UC8point4(){
        (Logger.getInstance()).writeNewLine("Test line");
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("NadavX","1234");
        SystemManagerController systemManagerController=(SystemManagerController) controllerList.get(0);
        long before= System.currentTimeMillis();
        assertNotNull(systemManagerController.showSystemLog());
        long after= System.currentTimeMillis();
        long time = after-before;
        assertTrue(time<acceptableTime);
    }
    //endregion

    //region Owner tests

    @Test
    public void UC6point1a(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Owner1X","Password");
        OwnerController ownerController=null;
        for(Object o:controllerList)
            if(o instanceof OwnerController)
                ownerController= (OwnerController) o;

        Object userPickedAsset=null;
        // positive test
        long before=System.currentTimeMillis();
        userPickedAsset=player3;
        assertTrue(ownerController.addAssetToTeam(userPickedAsset));
        long after=System.currentTimeMillis();
        userPickedAsset=coach3;
        assertTrue(ownerController.addAssetToTeam(userPickedAsset));
        userPickedAsset=stadium3;
        assertTrue(ownerController.addAssetToTeam(userPickedAsset));
        //negative test
        userPickedAsset=null;
        assertFalse(ownerController.addAssetToTeam(userPickedAsset));
        userPickedAsset=referee3;
        assertFalse(ownerController.addAssetToTeam(userPickedAsset));
        userPickedAsset=league2;
        assertFalse(ownerController.addAssetToTeam(userPickedAsset));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC6point1b() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        Object userPickedAsset;
        //positive test
        long before=System.currentTimeMillis();
        userPickedAsset=player1;
        assertTrue(ownerController.removeAssetFromTeam(userPickedAsset));
        long after=System.currentTimeMillis();
        userPickedAsset=coach1;
        assertTrue(ownerController.removeAssetFromTeam(userPickedAsset));
        userPickedAsset=stadium1;
        assertTrue(ownerController.removeAssetFromTeam(userPickedAsset));
        //negative test
        userPickedAsset=player1;
        assertFalse(ownerController.removeAssetFromTeam(userPickedAsset));
        userPickedAsset=coach1;
        assertFalse(ownerController.removeAssetFromTeam(userPickedAsset));
        userPickedAsset=stadium1;
        assertFalse(ownerController.removeAssetFromTeam(userPickedAsset));
        userPickedAsset=null;
        assertFalse(ownerController.removeAssetFromTeam(userPickedAsset));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC6point1c() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        Object userPickedAsset;
        int userPickedAction;
        String userPickedInput;
        //positive test
        long before=System.currentTimeMillis();
        userPickedAsset=player1; userPickedAction=1; userPickedInput="newPlayer";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));
        long after=System.currentTimeMillis();

        userPickedAsset=player1; userPickedAction=2; userPickedInput="18-11-1997";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=player1; userPickedAction=3; userPickedInput=""+PositionEnum.CenterBack;
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=coach1; userPickedAction=1; userPickedInput="newPCoach";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=coach1; userPickedAction=2; userPickedInput="new";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=coach1; userPickedAction=3; userPickedInput="new";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=tm1; userPickedAction=1; userPickedInput="newTM";
        assertTrue(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        //negative test
        userPickedAsset=referee3; userPickedAction=1; userPickedInput="newPlayer";
        assertFalse(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=player1; userPickedAction=2; userPickedInput="bad date";
        assertFalse(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        userPickedAsset=player1; userPickedAction=3; userPickedInput="bad position";
        assertFalse(ownerController.editTeamAsset(userPickedAsset,userPickedAction,userPickedInput));

        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);

    }

    @Test
    public void UC6point2() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        Account userPickedAccount;
        //positive test
        long before=System.currentTimeMillis();
        userPickedAccount=ownerAccount2;
        assertTrue(ownerController.appointOwnerToTeam(userPickedAccount));
        long after=System.currentTimeMillis();
        userPickedAccount=playerAccount3;
        assertTrue(ownerController.appointOwnerToTeam(userPickedAccount));
        //negative test
        userPickedAccount=refAccount3;
        assertFalse(ownerController.appointOwnerToTeam(userPickedAccount));
        userPickedAccount=null;
        assertFalse(ownerController.appointOwnerToTeam(userPickedAccount));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC6point3() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        ownerController.appointOwnerToTeam(ownerAccount2);
        Owner userPickedOwner;
        //positive test
        long before=System.currentTimeMillis();
        userPickedOwner=ownerAccount2.checkIfOwner();
        assertTrue(ownerController.removeOwnerFromTeam(userPickedOwner));
        long after=System.currentTimeMillis();
        //negative test
        userPickedOwner=ownerAccount2.checkIfOwner();
        assertFalse(ownerController.removeOwnerFromTeam(userPickedOwner));
        userPickedOwner=null;
        assertFalse(ownerController.removeOwnerFromTeam(userPickedOwner));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC6point4() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        List<TeamManager.PermissionEnum> userPickedPermissions=new ArrayList<>();
        userPickedPermissions.add(TeamManager.PermissionEnum.manageLeague);
        Account userPickedAccount;
        //positive test
        long before=System.currentTimeMillis();
        userPickedAccount=tmAccount3;
        assertTrue(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        long after=System.currentTimeMillis();
        userPickedAccount=ownerAccount3;
        assertTrue(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        //negative test
        userPickedAccount=tmAccount1;
        assertFalse(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        userPickedAccount=ownerAccount1;
        assertFalse(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        userPickedAccount=null;
        assertFalse(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        userPickedAccount=ownerAccount1; userPickedPermissions=null;
        assertFalse(ownerController.appointTeamManagerToTeam(userPickedAccount,userPickedPermissions));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);

    }

    @Test
    public void UC6point5() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        TeamManager userPickedTeamManager;
        //positive test
        long before=System.currentTimeMillis();
        userPickedTeamManager=tmAccount1.checkIfTeamManagr();
        assertTrue(ownerController.removeTeamManagerFromTeam(userPickedTeamManager));
        long after=System.currentTimeMillis();
        //negative test
        userPickedTeamManager=tmAccount2.checkIfTeamManagr();
        assertFalse(ownerController.removeTeamManagerFromTeam(userPickedTeamManager));
        userPickedTeamManager=null;
        assertFalse(ownerController.removeTeamManagerFromTeam(userPickedTeamManager));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    @Test
    public void UC6point6a() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        Team ownerTeam=ownerController.getTeam();
        long before=System.currentTimeMillis();
        assertTrue(ownerController.deactivateTeam());
        long after=System.currentTimeMillis();
        assertEquals(ownerController.getNonActiveTeam("Team1"),ownerTeam);


        assertFalse(ownerTeam.hasMatchs());
        assertFalse(ownerTeam.hasPlayers());
        assertFalse(ownerTeam.hasOwners());
        assertFalse(ownerTeam.hasCoachs());
        assertFalse(ownerTeam.hasTeamManagers());

        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);

    }

    @Test
    public void UC6point6b() {
        GuestController guestController = OurSystem.makeGuestController();
        List<Object> controllerList = guestController.LogIn("Owner1X", "Password");
        OwnerController ownerController = null;
        for (Object o : controllerList)
            if (o instanceof OwnerController)
                ownerController = (OwnerController) o;

        ownerController.deactivateTeam();
        //bad input
        Team userPickedTeam=ownerController.getNonActiveTeam("badTeam");
        assertNull(userPickedTeam);
        //good input
        userPickedTeam=ownerController.getNonActiveTeam("Team1");
        assertNotNull(userPickedTeam);
        long before=System.currentTimeMillis();
        assertNotNull(ownerController.activateTeam("Team1"));
        long after=System.currentTimeMillis();
        assertNull(ownerController.getNonActiveTeam("Team1"));
        //time testing
        long time=after-before;
        assertTrue(time<acceptableTime);
    }

    //endregion

    //region Association representative tests

    //Association Representative UseCases
    @Test
    public void UCsetNewSeason(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);
        long beforeTime=System.currentTimeMillis();
        Season season = associationRepresentativeController.setNewSeason("2020");
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertNotNull(season);

//        UCcreateNewLeague();
//        UCsetYearToLeague();
//        UCaddNewReferee();
//        UCaddRefereeToLeague();
//        UCsetLeaguePointCalcPolicy();
//        UCsetLeagueGameSchedualPolicy();


    }

    @Test
    public void UC9point1(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);
        List<Team> teamList = new LinkedList<>();
        teamList.add(team1);
        teamList.add(team2);

        long beforeTime=System.currentTimeMillis();
        League league3 = associationRepresentativeController.createNewLeague("League3",teamList);
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertNotNull("The league was'nt created! Check if the team list contains teams",league3);
        assertEquals("League3",league3.getName());
        assertTrue(league3.hasTeams());
        assertEquals(team1,league3.getTeam(0));
        assertEquals(team2,league3.getTeam(1));
    }

    @Test
    public void UC9point2(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);

        long beforeTime=System.currentTimeMillis();
        assertTrue(associationRepresentativeController.setYearToLeague(league1,"2021"));
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        Season season2021=null;
        for(Season season: DataManager.getSeasons()){
            if(season.getName().equals("2021")){
                season2021=season;
            }
        }
        if(season2021!=null)
            assertNotNull("The link between league and season wasn't created",league1.getSLsettingsBySeason(season2021));

    }

    @Test
    public void UC9point3(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);

        long beforeTime=System.currentTimeMillis();
        associationRepresentativeController.deleteReferee(referee1);
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);
        //DataManager.getRefereesFromAccounts().contains(referee1);
        assertTrue(referee1.getMatchs().isEmpty());
    }

    @Test
    public void UC9point3point1(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);

        long beforeTime=System.currentTimeMillis();
        Referee referee9 = associationRepresentativeController.addNewReferee("Complete","Referee9",26,"Referee9X","Password");
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertNotNull(referee9);
        assertEquals("Referee9",referee9.getName());
        assertFalse("Invitation was'nt sent to the referee",referee9.getAlertList().isEmpty());

        Referee referee10=associationRepresentativeController.createNewReferee(refAccount1,"Complete","Referee10");
        assertNotNull(referee10);
        assertEquals("Referee10",referee10.getName());
    }

    @Test
    public void UC9point4(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);

        long beforeTime=System.currentTimeMillis();
        assertTrue(associationRepresentativeController.addRefereeToLeague(referee1,league2,season));
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertTrue(league2.getSLsettingsBySeason(season).getReferees().contains(referee1));
    }

    @Test
    public void UC9point5(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);
        String twoPointsPolicy = "Goal equals 2 points";

        long beforeTime=System.currentTimeMillis();
        assertTrue(associationRepresentativeController.setLeaguePointCalcPolicy(league1,policy1,season,twoPointsPolicy));
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertEquals(twoPointsPolicy,league1.getSLsettingsBySeason(season).getPolicy().getPointCalc());
    }

    @Test
    public void UC9point6(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("AR1X","Password");
        AssociationRepresentativeController associationRepresentativeController=(AssociationRepresentativeController) controllerList.get(0);
        String twoTeamsEachTime = "two teams play twice against each other";

        long beforeTime=System.currentTimeMillis();
        assertTrue(associationRepresentativeController.setLeagueGameSchedualPolicy(league1,policy1,season,twoTeamsEachTime));
        long afterTime=System.currentTimeMillis();
        long runTime = afterTime-beforeTime;
        assertTrue(runTime<acceptableTime);

        assertEquals(twoTeamsEachTime,league1.getSLsettingsBySeason(season).getPolicy().getGameSchedual());
    }

    //endregion

    //region Referee tests
    @Test
    public void UC10point1(){
        //10.1.1
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Referee1X","Password");
        RefereeController refereeController= (RefereeController)controllerList.get(0);
        long before=System.currentTimeMillis();
        refereeController.updateDetails("tzlil");
        assertTrue(referee1.getName().equals("tzlil"));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);


    }
    @Test
    public void UC10point2(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Referee1X","Password");
        RefereeController refereeController= (RefereeController)controllerList.get(0);

        //--test when no matches 10.2.2
        OS.reset();
        long before=System.currentTimeMillis();
        refereeController.removeMatch(match1);
        refereeController.displayAllMatches();
        assertEquals("No matches!\r\n",OS.toString());
        assertTrue(System.currentTimeMillis()-before<acceptableTime);
        //--test when one match 10.2.1
        OS.reset();
        before=System.currentTimeMillis();
        refereeController.addMatch(match1,"main");
        refereeController.displayAllMatches();
        assertEquals("Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n\r\n",OS.toString());
        assertTrue(System.currentTimeMillis()-before<acceptableTime);
        OS.reset();
        //--test when more two match
        before=System.currentTimeMillis();
        referee4.removeMatch(match2);
        refereeController.addMatch(match2,"main");
        refereeController.displayAllMatches();
        assertEquals("Score:\r\n" + "Team1: 0, Team2: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee2, Line2: Referee3\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium1\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "\r\n" + "Score:\r\n" + "Team4: 0, Team3: 0\r\n" + "\r\n" + "Referees:\r\n" + "Main: Referee1, Line1: Referee5, Line2: Referee6\r\n" + "\r\n" + "Stadium:\r\n" + "Stadium4\r\n" + "\r\n" + "Season:\r\n" + "Season\r\n" + "\r\n" + "Game Events:\r\n" + "\r\n",OS.toString());
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

    }
    @Test
    public void UC10point3(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Referee1X","Password");
        RefereeController refereeController= (RefereeController)controllerList.get(0);
        //--test when the update accrues during game 10.3.1
        long before=System.currentTimeMillis();
        refereeController.updateEventDuringMatch(match1,EventEnum.redCard,"Player get redCard");
        assertTrue(referee1.getMatchs().get(0).getEventCalender().getGameEvent(0).getType().equals(EventEnum.redCard));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

        //--test when the update accrues after game 10.3.2
        before=System.currentTimeMillis();
        Date afterAddingMins=new Date(match1.getDate().getTime()- (91 * 60000));
        afterAddingMins.setYear(match1.getDate().getYear());
        afterAddingMins.setMonth(match1.getDate().getMonth());
        match1.setDate(afterAddingMins);
        refereeController.updateEventDuringMatch(match1,EventEnum.redCard,"Player get redCard");
        assertEquals(referee1.getMatchs().get(0).getEventCalender().getGameEvents().size(),1);
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

    }
    @Test
    public void UC10point4(){
        GuestController guestController=OurSystem.makeGuestController();
        List<Object> controllerList=guestController.LogIn("Referee1X","Password");
        RefereeController refereeController= (RefereeController)controllerList.get(0);
        //--test when the gameEvent dont exist 10.4.1
        long before=System.currentTimeMillis();
        refereeController.editEventAfterGame(match1,null,EventEnum.goal,"change from redcard to goal");
        assertEquals(referee1.getMatchs().get(0).getEventCalender().getGameEvents().size(),0);
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

        //--test when do not pass 5 hours from the end of the game 10.4.2
        before=System.currentTimeMillis();
        referee1.updateEventDuringMatch(match1,EventEnum.redCard,"Player get redCard");
        refereeController.editEventAfterGame(match1,referee1.getMatchs().get(0).getEventCalender().getGameEvents().get(0),EventEnum.goal,"change from redcard to goal");
        assertTrue(referee1.getMatchs().get(0).getEventCalender().getGameEvents().get(0).getType().equals(EventEnum.redCard));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

        //--test when  pass 5 hours from the end of the game 10.4.3
        before=System.currentTimeMillis();
        Date beforeDate=new Date(match1.getDate().getTime());
        beforeDate.setYear(match1.getDate().getYear());
        beforeDate.setMonth(match1.getDate().getMonth());
        Date afterAddingMins=new Date(match1.getDate().getTime()- (391 * 60000));
        afterAddingMins.setYear(match1.getDate().getYear());
        afterAddingMins.setMonth(match1.getDate().getMonth());
        match1.setDate(afterAddingMins);
        refereeController.editEventAfterGame(match1,referee1.getMatchs().get(0).getEventCalender().getGameEvents().get(0),EventEnum.goal,"change from redcard to goal");
        assertTrue(referee1.getMatchs().get(0).getEventCalender().getGameEvents().get(0).getType().equals(EventEnum.goal));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

        //--test when referee not main referee 10.4.4
        before=System.currentTimeMillis();
        GuestController guestController2=OurSystem.makeGuestController();
        List<Object> controllerList2=guestController2.LogIn("Referee2X","Password");
        RefereeController refereeController2= (RefereeController)controllerList2.get(0);
        match1.setDate(beforeDate);
        referee2.updateEventDuringMatch(match1,EventEnum.foul,"Player get foul");
        refereeController2.editEventAfterGame(match1,match1.getEventCalender().getGameEvents().get(1),EventEnum.redCard,"change from foul to redCard");
        assertFalse(match1.getEventCalender().getGameEvents().get(1).getType().equals(EventEnum.redCard));
        assertTrue(System.currentTimeMillis()-before<acceptableTime);

    }
    //endregion










    private void InitDatabase() {

        //region AssociationRepresantives creations
        arAccount1=new Account("AR1",99,"AR1X","Password");
        ar1=new AssociationRepresentative("AR1");
        arAccount1.addRole(ar1);

        arAccount2=new Account("AR2",99,"AR2X","Password");
        ar2=new AssociationRepresentative("AR2");
        arAccount2.addRole(ar2);
        //endregion

        //region Referees creation
        refAccount1=new Account("Referee1",99,"Referee1X","Password");
        referee1=ar1.createNewReferee(refAccount1,"Complete","Referee1");

        refAccount2=new Account("Referee2",99,"Referee2X","Password");
        referee2=ar1.createNewReferee(refAccount2,"Complete","Referee2");

        refAccount3=new Account("Referee3",99,"Referee3X","Password");
        referee3=ar1.createNewReferee(refAccount3,"Complete","Referee3");

        refAccount4=new Account("Referee4",99,"Referee4X","Password");
        referee4=ar1.createNewReferee(refAccount4,"Complete","Referee4");

        refAccount5=new Account("Referee5",99,"Referee5X","Password");
        referee5=ar1.createNewReferee(refAccount5,"Complete","Referee5");

        refAccount6=new Account("Referee6",99,"Referee6X","Password");
        referee6=ar1.createNewReferee(refAccount6,"Complete","Referee6");

        //endregion

        //region Leagues and seasons creation
        league1=new League("League1");
        league2=new League("League2");
        season=new Season("Season");
        policy1=new Policy("Standart","aaa");
        policy2=new Policy("Double","bbb");
        sLsettings1=new SLsettings(policy1);
        sLsettings1.addReferee(referee1);
        sLsettings1.addReferee(referee2);
        sLsettings1.addReferee(referee3);
        sLsettings2=new SLsettings(policy2);
        sLsettings2.addReferee(referee4);
        sLsettings2.addReferee(referee5);
        sLsettings2.addReferee(referee6);
        league1.addSLsettingsToSeason(season,sLsettings1);
        league2.addSLsettingsToSeason(season,sLsettings2);
        season.addSLsettingsToLeague(league1,sLsettings1);
        season.addSLsettingsToLeague(league2,sLsettings2);
        referee1.setsLsettings(sLsettings1);
        referee2.setsLsettings(sLsettings1);
        referee3.setsLsettings(sLsettings1);
        referee4.setsLsettings(sLsettings2);
        referee5.setsLsettings(sLsettings2);
        referee6.setsLsettings(sLsettings2);
        referee1.addLeague(league1,season);
        referee2.addLeague(league1,season);
        referee3.addLeague(league1,season);
        referee4.addLeague(league2,season);
        referee5.addLeague(league2,season);
        referee6.addLeague(league2,season);
        policy1.setsLsettings(sLsettings1);
        policy2.setsLsettings(sLsettings2);

        //endregion  //

        //region Stadium creation
        stadium1=new Stadium("Stadium1");
        stadium2=new Stadium("Stadium2");
        stadium3=new Stadium("Stadium3");
        stadium4=new Stadium("Stadium4");
        //endregion

        //region Teams creation
        team1=new Team("Team1", league1, stadium1);
        team2=new Team("Team2",league1,stadium2);
        team3=new Team("Team3",league2,stadium3);
        team4=new Team("Team4",league2,stadium4);
        //endregion

        //region Owner creation
        ownerAccount1=new Account("Owner1",99,"Owner1X","Password");
        owner1=new Owner("Owner1",team1,null);
        ownerAccount1.addRole(owner1);

        ownerAccount2=new Account("Owner2",99,"Owner2X","Password");
        owner2=new Owner("Owner2",team2,null);
        ownerAccount2.addRole(owner2);

        ownerAccount3=new Account("Owner3",99,"Owner3X","Password");
        owner3=new Owner("Owner3",team3,null);
        ownerAccount3.addRole(owner3);

        ownerAccount4=new Account("Owner4",99,"Owner4X","Password");
        owner4=new Owner("Owner4",team4,null);
        ownerAccount4.addRole(owner4);
        //endregion

        //region TeamManagers creation
        tmAccount1=new Account("TM1",99,"TM1X","Password");
        List<TeamManager.PermissionEnum> tm1Permissions=new ArrayList<>();
        tm1Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm1Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner1.appointTeamManagerToTeam(tmAccount1,tm1Permissions);
        tm1=tmAccount1.checkIfTeamManagr();

        tmAccount2=new Account("TM2",99,"TM2X","Password");
        List<TeamManager.PermissionEnum> tm2Permissions=new ArrayList<>();
        tm2Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm2Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner2.appointTeamManagerToTeam(tmAccount2,tm2Permissions);
        tm2=tmAccount2.checkIfTeamManagr();

        tmAccount3=new Account("TM3",99,"TM3X","Password");
        List<TeamManager.PermissionEnum> tm3Permissions=new ArrayList<>();
        tm3Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm3Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner3.appointTeamManagerToTeam(tmAccount3,tm3Permissions);
        tm3=tmAccount3.checkIfTeamManagr();

        tmAccount4=new Account("TM4",99,"TM4X","Password");
        List<TeamManager.PermissionEnum> tm4Permissions=new ArrayList<>();
        tm4Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm4Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner4.appointTeamManagerToTeam(tmAccount4,tm4Permissions);
        tm4=tmAccount4.checkIfTeamManagr();

        //endregion

        //region Players creation
        playerAccount1=new Account("Player1",99,"Player1X","Password");
        player1=new Player("Player1",new Date(),PositionEnum.Striker,team1,null);
        playerAccount1.addRole(player1);

        playerAccount2=new Account("Player2",99,"Player2X","Password");
        player2=new Player("Player2",new Date(),PositionEnum.Striker,team1,null);
        playerAccount2.addRole(player2);

        playerAccount3=new Account("Player3",99,"Player3X","Password");
        player3=new Player("Player3",new Date(),PositionEnum.Striker,team2,null);
        playerAccount3.addRole(player3);

        playerAccount4=new Account("Player4",99,"Player4X","Password");
        player4=new Player("Player4",new Date(),PositionEnum.Striker,team2,null);
        playerAccount4.addRole(player4);

        playerAccount5=new Account("Player5",99,"Player5X","Password");
        player5=new Player("Player5",new Date(),PositionEnum.Striker,team3,null);
        playerAccount5.addRole(player5);

        playerAccount6=new Account("Player6",99,"Player6X","Password");
        player6=new Player("Player6",new Date(),PositionEnum.Striker,team3,null);
        playerAccount6.addRole(player6);

        playerAccount7=new Account("Player7",99,"Player7X","Password");
        player7=new Player("Player7",new Date(),PositionEnum.Striker,team4,null);
        playerAccount7.addRole(player7);

        playerAccount8=new Account("Player8",99,"Player8X","Password");
        player8=new Player("Player8",new Date(),PositionEnum.Striker,team4,null);
        playerAccount8.addRole(player8);
        //endregion

        //region Coach creation
        coachAccount1=new Account("Coach1",99,"Coach1X","Password");
        coach1=new Coach("Coach1","Full","Main",null);
        coachAccount1.addRole(coach1);

        coachAccount2=new Account("Coach2",99,"Coach2X","Password");
        coach2=new Coach("Coach2","Full","Main",null);
        coachAccount2.addRole(coach2);

        coachAccount3=new Account("Coach3",99,"Coach3X","Password");
        coach3=new Coach("Coach3","Full","Main",null);
        coachAccount3.addRole(coach3);

        coachAccount4=new Account("Coach4",99,"Coach4X","Password");
        coach4=new Coach("Coach4","Full","Main",null);
        coachAccount4.addRole(coach4);
        //endregion

        //region Owners, TeamManagers and Coaches teams setting
        owner1.setTeam(team1);
        owner2.setTeam(team2);
        owner3.setTeam(team3);
        owner4.setTeam(team4);
        tm1.setTeam(team1);
        tm2.setTeam(team2);
        tm3.setTeam(team3);
        tm4.setTeam(team4);
        coach1.addTeam(team1);
        coach2.addTeam(team2);
        coach3.addTeam(team3);
        coach4.addTeam(team4);
        //endregion

        //region Match creation
        match1=new Match(new Date(),new Time(22,0,0),0,0,stadium1,season
                ,team2,team1,null,null,null);

        match2=new Match(new Date(),new Time(19,0,0),0,0,stadium4,season
                ,team3,team4,null,null,null);
        //endregion

        //region Referess match setting
        referee1.addMatch(match1,"main");
        referee2.addMatch(match1,"line");
        referee3.addMatch(match1,"line");
        referee4.addMatch(match2,"main");
        referee5.addMatch(match2,"line");
        referee6.addMatch(match2,"line");
        //endregion

        //region Seasons matches setting
        season.addMatch(match1);
        season.addMatch(match2);
        //endregion

        //region Players teams setting
        player1.setTeam(team1);
        player2.setTeam(team1);
        player3.setTeam(team2);
        player4.setTeam(team2);
        player5.setTeam(team3);
        player6.setTeam(team3);
        player7.setTeam(team4);
        player8.setTeam(team4);
        //endregion

        //region Page creation
        playerPage1=new Page(player1);
        playerPage2=new Page(player2);
        playerPage3=new Page(player3);
        playerPage4=new Page(player4);
        playerPage5=new Page(player5);
        playerPage6=new Page(player6);
        playerPage7=new Page(player7);
        playerPage8=new Page(player8);
        coachPage1=new Page(coach1);
        coachPage2=new Page(coach2);
        coachPage3=new Page(coach3);
        coachPage4=new Page(coach4);
        teamPage1=new Page(team1);
        teamPage2=new Page(team2);
        teamPage3=new Page(team3);
        teamPage4=new Page(team4);
        //endregion

        //region Fan creation
        fanAccount1=new Account("Fan1",99,"Fan1X","Password");
        fan1= new Fan("Fan1");
        fanAccount1.addRole(fan1);

        fanAccount2=new Account("Fan2",99,"Fan2X","Password");
        fan2= new Fan("Fan2");
        fanAccount2.addRole(fan2);

        fanAccount3=new Account("Fan3",99,"Fan3X","Password");
        fan3= new Fan("Fan3");
        fanAccount3.addRole(fan3);

        fanAccount4=new Account("Fan4",99,"Fan4X","Password");
        fan4= new Fan("Fan4");
        fanAccount4.addRole(fan4);

        fanAccount5=new Account("Fan5",99,"Fan5X","Password");
        fan5= new Fan("Fan5");
        fanAccount5.addRole(fan5);

        fanAccount6=new Account("Fan6",99,"Fan6X","Password");
        fan6= new Fan("Fan6");
        fanAccount6.addRole(fan6);

        fanAccount7=new Account("Fan7",99,"Fan7X","Password");
        fan7= new Fan("Fan7");
        fanAccount7.addRole(fan7);

        fanAccount8=new Account("Fan8",99,"Fan8X","Password");
        fan8= new Fan("Fan8");
        fanAccount8.addRole(fan8);
        //endregion

        //region Fan page setting
        fan1.addPage(playerPage1);
        fan1.addPage(playerPage2);
        fan2.addPage(coachPage3);
        fan3.addPage(coachPage4);
        fan3.addPage(teamPage3);
        fan2.addPage(playerPage3);
        fan5.addPage(teamPage4);
        fan4.addPage(coachPage1);
        fan5.addPage(playerPage8);
        fan7.addPage(playerPage2);
        fan8.addPage(playerPage2);
        fan1.addPage(teamPage4);
        //endregion

        //region Datamanager adding
        DataManager.addAccount(arAccount1);
        DataManager.addAccount(arAccount2);
        DataManager.addAccount(refAccount1);
        DataManager.addAccount(refAccount2);
        DataManager.addAccount(refAccount3);
        DataManager.addAccount(refAccount4);
        DataManager.addAccount(refAccount5);
        DataManager.addAccount(refAccount6);
        DataManager.addAccount(ownerAccount1);
        DataManager.addAccount(ownerAccount2);
        DataManager.addAccount(ownerAccount3);
        DataManager.addAccount(ownerAccount4);
        DataManager.addAccount(tmAccount1);
        DataManager.addAccount(tmAccount2);
        DataManager.addAccount(tmAccount3);
        DataManager.addAccount(tmAccount4);
        DataManager.addAccount(playerAccount1);
        DataManager.addAccount(playerAccount2);
        DataManager.addAccount(playerAccount3);
        DataManager.addAccount(playerAccount4);
        DataManager.addAccount(playerAccount5);
        DataManager.addAccount(playerAccount6);
        DataManager.addAccount(playerAccount7);
        DataManager.addAccount(playerAccount8);
        DataManager.addAccount(coachAccount1);
        DataManager.addAccount(coachAccount2);
        DataManager.addAccount(coachAccount3);
        DataManager.addAccount(coachAccount4);
        DataManager.addAccount(fanAccount1);
        DataManager.addAccount(fanAccount2);
        DataManager.addAccount(fanAccount3);
        DataManager.addAccount(fanAccount4);
        DataManager.addAccount(fanAccount5);
        DataManager.addAccount(fanAccount6);
        DataManager.addAccount(fanAccount7);
        DataManager.addAccount(fanAccount8);
        DataManager.addLeague(league1);
        DataManager.addLeague(league2);
        DataManager.addSeason(season);
        DataManager.addStadium(stadium1);
        DataManager.addStadium(stadium2);
        DataManager.addStadium(stadium3);
        DataManager.addStadium(stadium4);
        DataManager.addTeam(team1);
        DataManager.addTeam(team2);
        DataManager.addTeam(team3);
        DataManager.addTeam(team4);
        //endregion

    }
}