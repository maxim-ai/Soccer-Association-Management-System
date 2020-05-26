import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.*;
import Server.BusinessLayer.Pages.Page;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.RoleCrudOperations.Player;
import Server.BusinessLayer.RoleCrudOperations.Referee;
import Server.BusinessLayer.RoleCrudOperations.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    Date date=new Date();
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;
    Time time=new Time(System.currentTimeMillis());
    Stadium stadium1=new Stadium("B7");
    Stadium stadium2=new Stadium("Ofakim");
    Season season=new Season("winter");
    League league=new League("super league");
    Team awayTeam=new Team("B7",league,stadium1);
    Page page1=new Page(awayTeam);
    Team homeTeam=new Team("Ofakim",league,stadium2);
    Page page2=new Page(homeTeam);
    Referee lineRefereeOne=new Referee("Football Association","zviali bar");
    Referee lineRefereeTwo=new Referee("Football Association","karapti roy");
    Player player=new Player("Yosi",date,PositionEnum.AttackingMidfielder,null,null);
    Fan fan=new Fan("tzlil");
     Account aAccount=new Account("tzlil",26,"ud","ud");
     List<Account> accounts=new LinkedList<>();
     List<Role>roles=new LinkedList<>();

    @Before
    public void init(){
        DataController.getInstance().clearDataBase();
        System.setOut(new PrintStream(OS));
    }

    @After
    public void restore(){
        System.setOut(PS);
    }
    @Test
    public void setBirthday() {
        Date newDate=new Date();
        player.setBirthday(newDate);
        assertTrue(player.getBirthday().compareTo(newDate)==0);
    }

    @Test
    public void setPosition() {
        player.setPage(page1);
        player.setPosition(PositionEnum.CenterBack);
        assertEquals(PositionEnum.CenterBack,player.getPosition());
    }

    @Test
    public void getBirthday() {
        assertTrue(date.compareTo(player.getBirthday())==0);
    }

    @Test
    public void getPosition() {
        assertEquals(player.getPosition(),PositionEnum.AttackingMidfielder);
    }

    @Test
    public void getTeam() {
        player.setPage(page1);
        player.setTeam(awayTeam);
        assertEquals(player.getTeam(),awayTeam);
    }

    @Test
    public void getPage() {
        player.setPage(page1);
        assertEquals(player.getPage(),page1);
    }


    @Test
    public void removePage() {
        player.setPage(page1);
        player.removePage();
        assertNull(player.getPage());
    }


    @Test
    public void updateDetails() {
        player.setPage(page1);
        Date newDate=new Date();
        player.updateDetails(newDate,PositionEnum.CenterBack,awayTeam);
        assertTrue(newDate.compareTo(player.getBirthday())==0);
        assertEquals(player.getPosition(),PositionEnum.CenterBack);
        assertEquals(player.getTeam(),awayTeam);
    }

    @Test
    public void showPlayer() {
        player.setTeam(awayTeam);
        accounts.add(aAccount);
        roles.add(player);
        showPlayerTest();
        assertEquals("Name:\r\n" +
                "adi lioz\r\n" +
                "\r\n" +
                "Age:\r\n" +
                "26\r\n" +
                "\r\n" +
                "Position:\r\n" +
                "AttackingMidfielder\r\n" +
                "\r\n" +
                "BusinessLayer.OtherCrudOperations.Team:\r\n" +
                "B7\r\n"
                ,OS.toString());
    }
    //func
    public void showPlayerTest() {
        System.out.println("Name:");
        System.out.println(getNameStub());
        System.out.println();
        System.out.println("Age:");
        int age=0;
        for(Account account:getAccountsStub()){
            for(Role role:getRolesStub()){
                if(role.equals(player))
                    age=getAgeStub();
            }
        }
        System.out.println(age);
        System.out.println();
        System.out.println("Position:");
        System.out.println(getPositionStub());
        System.out.println();
        System.out.println("BusinessLayer.OtherCrudOperations.Team:");
        System.out.println(getTeamNameStub());
    }
    //stubs
    public String getNameStub(){
        return "adi lioz";
    }
    public String getTrainingStub(){
        return "Football Association";
    }
    public List<Account> getAccountsStub() {
        return accounts;
    }
    public List<Role> getRolesStub()
    {
        return roles;
    }
    public int getAgeStub() {
        return 26;
    }
    public PositionEnum getPositionStub()
    {
        return PositionEnum.AttackingMidfielder;
    }
    public  String getTeamNameStub()
    {
        return "B7";
    }

    @Test
    public void setTeam() {
       assertTrue(setTeamTest(homeTeam));
    }
    //func
    public boolean setTeamTest(Team aTeam) {
        boolean wasSet = true;
        //Must provide team to player
        if (aTeam == null) {
            awayTeam = null;
            return wasSet;
        }

        Team existingTeam = awayTeam;
        if (existingTeam != null && !existingTeam.equals(aTeam)) {
            boolean didRemove = removePlayerStub(player);
            if (!didRemove) {
                return wasSet;
            }
        }
        awayTeam = aTeam;

        addPlayerStub(player);
        wasSet = true;
        pageUpdatedStub();
        return wasSet;
    }
    //stub
    public boolean removePlayerStub(Player aPlayer)
    {
        return true;
    }
    public boolean addPlayerStub(Player aPlayer)
    {
        return true;
    }
    public void pageUpdatedStub()
    {
        return;
    }


}