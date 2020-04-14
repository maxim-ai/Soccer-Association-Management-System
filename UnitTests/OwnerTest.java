import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OwnerTest {
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;

    Owner owner;
    Owner tmpOwner;
    Team tempTeam;
    Account ownerAccount;
    Account secondAccount;

    @Before
    public void setUp() throws Exception {
        Logger logger=new Logger();
        OurSystem ourSystem=new OurSystem();
        ourSystem.Initialize();
        ownerAccount=new Account("sean",20,"sean","sean");
        secondAccount=new Account("notSean",30,"sean","sean");
        DataManager.addAccount(ownerAccount);
        DataManager.addAccount(secondAccount);

        owner=new Owner("sean",new Team("myTeam",new League("myLeague"),new Stadium("myStadium")),null);
        tmpOwner=new Owner("tmp",owner.getTeam(),owner);
        tempTeam=new Team("newTeam",new League("newLeague"),new Stadium("newStadium"));
        DataManager.addTeam(tempTeam);

        ownerAccount.addRole(owner);
    }

    @Test
    public void setName() {
        owner.setName("doby");
        assertEquals("doby",owner.getName());
    }

    @Test
    public void getName() {
        assertEquals("sean",owner.getName());
    }

    @Test
    public void getTeam() {
        Team tempTeam=new Team("myTeam",new League("myLeague"),new Stadium("myStadium"));
        assertEquals(tempTeam.getName(),owner.getTeam().getName());
        assertEquals(tempTeam.getLeague().getName(),owner.getTeam().getLeague().getName());
        assertEquals(tempTeam.getStadium().getName(),owner.getTeam().getStadium().getName());
    }

    @Test
    public void setTeam() {
        owner.setTeam(tempTeam);
        assertEquals("newTeam",owner.getTeam().getName());
        assertEquals("newLeague",owner.getTeam().getLeague().getName());
        assertEquals("newStadium",owner.getTeam().getStadium().getName());
        assertTrue(owner.getTeam().getLeague().getTeams().contains(tempTeam));
        assertTrue(owner.getTeam().getStadium().getTeams().contains(tempTeam));
    }

    @Test
    public void getAppointer() {
        assertEquals(owner,tmpOwner.getAppointer());
    }

    @Test
    public void setAppointer() {
        owner.setAppointer(tmpOwner);
        assertEquals(tmpOwner,owner.getAppointer());

    }

    @Test /*UC 6.1*/
    public void addAssetToTeam()
    {
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18),PositionEnum.CenterBack,tempTeam,null);
        Coach tmpCoach=new Coach("tmpCoach","bla","bla",null);
        Stadium tmpStadium=new Stadium("tmpStadium");
        Referee tmpReferee=new Referee("bla","bla");
        assertTrue(owner.addAssetToTeam(tmpCoach));
        assertTrue(owner.addAssetToTeam(tmpPlayer));
        assertTrue(owner.addAssetToTeam(tmpStadium));

        assertTrue(tmpCoach.getTeams().contains(owner.getTeam()));
        assertTrue(tmpPlayer.getTeam().equals(owner.getTeam()));
        assertTrue(tmpStadium.getTeams().contains(owner.getTeam()));

        assertFalse(owner.addAssetToTeam(tmpReferee));
        assertFalse(owner.addAssetToTeam(null));
        owner.deactivateTeam();
        assertFalse(owner.addAssetToTeam(tmpCoach));
        owner.setTeam(null);
        assertFalse(owner.addAssetToTeam(tmpCoach));
    }

    @Test /*UC 6.1*/
    public void removeAssetFromTeam()
    {
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18),PositionEnum.CenterBack,tempTeam,null);
        Coach tmpCoach=new Coach("tmpCoach","bla","bla",null);
        Stadium tmpStadium=new Stadium("tmpStadium");
        Referee tmpReferee=new Referee("bla","bla");
        owner.addAssetToTeam(tmpCoach);
        owner.addAssetToTeam(tmpPlayer);
        owner.addAssetToTeam(tmpStadium);
        assertTrue(owner.removeAssetFromTeam(tmpCoach));
        assertTrue(owner.removeAssetFromTeam(tmpPlayer));
        assertTrue(owner.removeAssetFromTeam(tmpStadium));

        assertFalse(owner.removeAssetFromTeam(tmpCoach));
        assertFalse(owner.removeAssetFromTeam(tmpPlayer));
        assertFalse(owner.removeAssetFromTeam(tmpStadium));
        assertFalse(owner.removeAssetFromTeam(tmpReferee));
        assertFalse(owner.removeAssetFromTeam(null));
        owner.deactivateTeam();
        assertFalse(owner.removeAssetFromTeam(tmpCoach));
        owner.setTeam(null);
        assertFalse(owner.removeAssetFromTeam(tmpCoach));

    }

    @Test /*UC 6.1*/
    public void editTeamAsset()
    {
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18),PositionEnum.CenterBack,tempTeam,null);
        Coach tmpCoach=new Coach("tmpCoach","bla","bla",null);
        Stadium tmpStadium=new Stadium("tmpStadium");
        owner.addAssetToTeam(tmpCoach);
        owner.addAssetToTeam(tmpPlayer);
        owner.addAssetToTeam(tmpStadium);

        assertTrue(owner.editTeamAsset(tmpPlayer,1,"newName"));
        assertTrue(owner.editTeamAsset(tmpPlayer,2,"01-01-1990"));
        assertTrue(owner.editTeamAsset(tmpPlayer,3,"Goalkeeper"));
        assertTrue(owner.editTeamAsset(tmpCoach,1,"newName"));
        assertTrue(owner.editTeamAsset(tmpCoach,2,"newTraining"));
        assertTrue(owner.editTeamAsset(tmpCoach,3,"newRole"));
        assertTrue(owner.editTeamAsset(tmpStadium,1,"newName"));

        assertFalse(owner.editTeamAsset(tmpPlayer,4,"01-01-1990"));
        assertFalse(owner.editTeamAsset(tmpCoach,4,"01-01-1990"));
        assertFalse(owner.editTeamAsset(tmpStadium,4,"01-01-1990"));

        assertFalse(owner.editTeamAsset(tmpPlayer,2,"01.01.1990"));



    }

    @Test /*UC 6.1*/
    public void showEditingOptions() {
    }

    @Test /*UC 6.2*/
    public void appointOwnerToTeam()
    {
        Account account=new Account("newacc",20,"cla","cla");
        account.addRole(new Referee("bla","bla"));
        assertFalse(owner.appointOwnerToTeam(account));

        account.addRole(new Coach("newC","bla","bla",null));
        account.removeRole(account.checkIfReferee());
        assertTrue(owner.appointOwnerToTeam(account));
        assertTrue(owner.getTeam().getOwners().contains(account.checkIfOwner()));

        assertNotNull(account.checkIfOwner());
        assertNotNull(account.checkIfCoach());

        assertFalse(owner.appointOwnerToTeam(account));
    }

    @Test /*UC 6.3*/
    public void removeOwnerFromTeam()
    {
        owner.removeOwnerFromTeam(tmpOwner); //remove the owner from different test

        // checks standard removal
        Account account=new Account("tmp",20,"cla","cla");
        DataManager.addAccount(account);
        account.addRole(new Coach(account.getName(),"bla","bla",null));
        owner.appointOwnerToTeam(account);

        assertNotNull(account.checkIfOwner());
        assertNotNull(account.checkIfCoach());

        assertTrue(owner.removeOwnerFromTeam(account.checkIfOwner()));
        assertNull(account.checkIfOwner());
        assertNull(account.checkIfCoach());

        //check that owner cant be removed if theres only one
        assertFalse(owner.removeOwnerFromTeam(owner));

    }

    @Test /*UC 6.4*/
    public void appointTeamManagerToTeam()
    {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        //not allow appointment if allready has role
        secondAccount.addRole(new Coach(secondAccount.getName(),"bla","bla",null));
        assertFalse(owner.appointTeamManagerToTeam(secondAccount,permissionEnumList));
        //check standard appointment
        secondAccount.removeRole(secondAccount.checkIfCoach());
        assertTrue(owner.appointTeamManagerToTeam(secondAccount,permissionEnumList));

    }

    @Test /*UC 6.5*/
    public void removeTeamManagerFromTeam()
    {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        owner.appointTeamManagerToTeam(secondAccount,permissionEnumList);
        //try to remove not by the appointer
        assertFalse(tmpOwner.removeTeamManagerFromTeam(secondAccount.checkIfTeamManagr()));
        assertNotNull(secondAccount.checkIfTeamManagr());
        //standard removal
        assertTrue(owner.removeTeamManagerFromTeam(secondAccount.checkIfTeamManagr()));
        assertFalse(owner.getTeam().getTeamManagers().contains(secondAccount.checkIfTeamManagr()));
        assertNull(secondAccount.checkIfTeamManagr());

    }

    @Test
    public void hasPermission()
    {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        owner.appointTeamManagerToTeam(secondAccount,permissionEnumList);

        assertTrue(owner.hasPermission(secondAccount.checkIfTeamManagr(),TeamManager.PermissionEnum.manageLeague));
        assertFalse(owner.hasPermission(secondAccount.checkIfTeamManagr(),TeamManager.PermissionEnum.manageManagers));
    }

    @Test
    public void getAllPermitions()
    {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        owner.appointTeamManagerToTeam(secondAccount,permissionEnumList);

        assertEquals(owner.getAllPermitions(secondAccount.checkIfTeamManagr()),permissionEnumList);
    }

    @Test /*UC 6.6*/
    public void deactivateTeam()
    {
        Team existingTeam=owner.getTeam();
        assertNull(owner.getNonActiveTeam(existingTeam.getName()));
        owner.deactivateTeam();
        assertEquals(owner.getNonActiveTeam(existingTeam.getName()),existingTeam);
    }

    @Test /*UC 6.6*/
    public void activateTeam()
    {
        Team existingTeam=owner.getTeam();
        owner.deactivateTeam();
        assertEquals(owner.getNonActiveTeam(existingTeam.getName()),existingTeam);
        owner.activateTeam(existingTeam.getName());
        assertNull(owner.getNonActiveTeam(existingTeam.getName()));

    }

    @Test
    public void getNonActiveTeam()
    {
        Team existingTeam=owner.getTeam();
        assertNull(owner.getNonActiveTeam(existingTeam.getName()));
        owner.deactivateTeam();
        assertEquals(owner.getNonActiveTeam(existingTeam.getName()),existingTeam);
        assertNull(owner.getNonActiveTeam("check"));

    }


    @Test
    public void delete()
    {
        Team tmp=owner.getTeam();
        owner.delete();
        assertFalse(tmp.getOwners().contains(owner));
    }

    @Test /*UC 6.4*/
    public void addPermissionToManager()
    {
        List<TeamManager.PermissionEnum> permissions=new ArrayList<>();
        permissions.add(TeamManager.PermissionEnum.manageName);
        Account tm1=new Account("tm1",20,"tm1","tm1");
        owner.appointTeamManagerToTeam(tm1,permissions);

        //check without permissions
        tm1.checkIfTeamManagr().setStadium(new Stadium("newSta"));
        assertFalse(owner.getTeam().getStadium().getName().equals("newSta"));

        //check with permissions
        owner.addPermissionToManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageStadium);
        tm1.checkIfTeamManagr().setStadium(new Stadium("newSta"));
        assertTrue(owner.getTeam().getStadium().getName().equals("newSta"));

    }

    @Test /*UC 6.4*/
    public void removePermissionFromManager()
    {
        List<TeamManager.PermissionEnum> permissions=new ArrayList<>();
        permissions.add(TeamManager.PermissionEnum.manageName);
        Account tm1=new Account("tm1",20,"tm1","tm1");
        owner.appointTeamManagerToTeam(tm1,permissions);

        //check with permissions
        tm1.checkIfTeamManagr().changeTeamName("newTName");
        assertTrue(owner.getTeam().getName().equals("newTName"));

        //check with permissions
        owner.removePermissionFromManager(tm1.checkIfTeamManagr(), TeamManager.PermissionEnum.manageName);
        tm1.checkIfTeamManagr().changeTeamName("brandNewTName");
        assertFalse(owner.getTeam().getName().equals("brandNewTName"));

    }



    @Test
    public void createTeam()
    {
        Account assoAccount=new Account("asso",20,"bla","bla");
        assoAccount.addRole(new AssiciationRepresentive(assoAccount.getName()));
        SystemManager.createAccount(assoAccount);

        String ans=owner.createTeam("newTeam",new League("newLeague"),new Stadium("newSta"));
        assertTrue(ans.contains("Wrong input"));
        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"Request sent, waiting for approval");
        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"waiting for approval");

        assertTrue(AssiciationRepresentive.checkIfRequestExists(owner,"assoTeam"));
        assoAccount.checkIfAssiciationRepresentive().approveTeam("assoTeam",owner);

        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"Team successfully added");
        assertEquals(owner.getTeam().getName(),"assoTeam");
        assertFalse(AssiciationRepresentive.checkIfRequestExists(owner,"assoTeam"));
    }

    @Test
    public void createPlayer()
    {
        Account test=owner.createPlayer("testPla",20,new Date(123),PositionEnum.CenterBack,"bla11","bla");
        assertTrue(DataManager.getAccounts().contains(test));
        assertNotNull(test.checkIfPlayer());
    }

    @Test
    public void createTeamManager()
    {
        Account test=owner.createTeamManager("testPla",20,null,"bla11","bla");
        assertTrue(DataManager.getAccounts().contains(test));
        assertNotNull(test.checkIfTeamManagr());
    }

    @Test
    public void createCoach()
    {
        Account test=owner.createCoach("testPla",20,"bla","bla","bla11","bla");
        assertTrue(DataManager.getAccounts().contains(test));
        assertNotNull(test.checkIfCoach());
    }

    @Before
    public void init(){
        System.setOut(new PrintStream(OS));
    }

    @After
    public void restore(){
        System.setOut(PS);
    }

    @Test
    public void showOwner()
    {
        owner.ShowOwner();
        assertEquals("Name:\r\nsean\r\nTeam owned:\r\nmyTeam\r\n",OS.toString());
    }

}