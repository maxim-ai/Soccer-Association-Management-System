import BusinessLayer.Logger.Logger;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.*;
import DataLayer.DataManager;
import ServiceLayer.OurSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OwnerTests {
    private final ByteArrayOutputStream OS=new ByteArrayOutputStream();
    private final PrintStream PS=System.out;

    Owner owner;
    Owner tmpOwner;
    Team tempTeam;
    Account ownerAccount;
    Account secondAccount;

    @Before
    public void setUp() throws Exception {
        DataManager.clearDataBase();
        Logger logger=Logger.getInstance();
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
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18), PositionEnum.CenterBack,tempTeam,null);
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


    @Test /*UC 6.6*/
    public void deactivateTeam()
    {
        Team existingTeam=owner.getTeam();
        assertNull(owner.getNonActiveTeam(existingTeam.getName()));
        owner.deactivateTeam();
        assertEquals(owner.getNonActiveTeam(existingTeam.getName()),existingTeam);

        assertFalse(existingTeam.hasMatchs());
        assertFalse(existingTeam.hasPlayers());
        assertFalse(existingTeam.hasOwners());
        assertFalse(existingTeam.hasCoachs());
        assertFalse(existingTeam.hasTeamManagers());
    }



    @Test
    public void createTeam()
    {
        Account assoAccount=new Account("asso",20,"bla","bla");
        assoAccount.addRole(new AssociationRepresentative(assoAccount.getName()));
        SystemManager.createAccount(assoAccount);

        String ans=owner.createTeam("newTeam",new League("newLeague"),new Stadium("newSta"));
        assertTrue(ans.contains("Wrong input"));
        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"Request sent, waiting for approval");
        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"waiting for approval");

        assertTrue(AssociationRepresentative.checkIfRequestExists(owner,"assoTeam"));
        assoAccount.checkIfAssiciationRepresentive().approveTeam("assoTeam",owner);

        ans=owner.createTeam("assoTeam",new League("assoLeague"),new Stadium("assoSta"));
        assertEquals(ans,"BusinessLayer.OtherCrudOperations.Team successfully added");
        assertEquals(owner.getTeam().getName(),"assoTeam");
        assertFalse(AssociationRepresentative.checkIfRequestExists(owner,"assoTeam"));
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

}