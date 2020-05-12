import BusinessLayer.DataController;
import BusinessLayer.Logger.Logger;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.*;
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
        DataController.getInstance().clearDataBase();
        Logger logger=Logger.getInstance();
        OurSystem ourSystem=new OurSystem();
        ourSystem.Initialize();
        ownerAccount=new Account("sean",20,"sean","sean");
        secondAccount=new Account("notSean",30,"sean","sean");
        DataController.getInstance().addAccount(ownerAccount);
        DataController.getInstance().addAccount(secondAccount);

        owner=new Owner("sean",new Team("myTeam",new League("myLeague"),new Stadium("myStadium")),null);
        tmpOwner=new Owner("tmp",owner.getTeam(),owner);
        tempTeam=new Team("newTeam",new League("newLeague"),new Stadium("newStadium"));
        DataController.getInstance().addTeam(tempTeam);

        ownerAccount.addRole(owner);
    }


    @Test /*UC 6.1*/
    public void addAssetToTeam() throws Exception {
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

        try {
            owner.addAssetToTeam(tmpReferee);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Input is not a team asset");
        }
        try {
            owner.addAssetToTeam(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Input is not a team asset");
        }
        owner.deactivateTeam();
        try {
            owner.addAssetToTeam(tmpCoach);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Owner does not have a team");
        }
        owner.setTeam(null);
        try {
            owner.addAssetToTeam(tmpCoach);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Owner does not have a team");
        }
    }

    @Test /*UC 6.1*/
    public void removeAssetFromTeam() throws Exception {
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

        try {
            owner.removeAssetFromTeam(tmpCoach);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The coach does no exist in the team");
        }
        try {
            owner.removeAssetFromTeam(tmpPlayer);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The player does no exist in the team");
        }
        try {
            owner.removeAssetFromTeam(tmpStadium);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The stadium does no exist in the team");
        }
        try {
            owner.removeAssetFromTeam(tmpReferee);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Input is not a team asset");
        }
        try {
            owner.removeAssetFromTeam(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Input is not a team asset");
        }
        owner.deactivateTeam();
        try {
            owner.removeAssetFromTeam(tmpCoach);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Owner does not have a team");
        }
        owner.setTeam(null);
        try {
            owner.removeAssetFromTeam(tmpCoach);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Owner does not have a team");
        }

    }


    @Test /*UC 6.2*/
    public void appointOwnerToTeam() throws Exception {
        Account account=new Account("newacc",20,"cla","cla");
        account.addRole(new Referee("bla","bla"));
        try {
            owner.appointOwnerToTeam(account);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The account already has a role");
        }

        account.addRole(new Coach("newC","bla","bla",null));
        account.removeRole(account.checkIfReferee());
        assertTrue(owner.appointOwnerToTeam(account));
        assertTrue(owner.getTeam().getOwners().contains(account.checkIfOwner()));

        assertNotNull(account.checkIfOwner());
        assertNotNull(account.checkIfCoach());

        try {
            owner.appointOwnerToTeam(account);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The account is already an owner for the team");
        }
    }

    @Test /*UC 6.3*/
    public void removeOwnerFromTeam() throws Exception {
        owner.removeOwnerFromTeam(tmpOwner); //remove the owner from different test

        // checks standard removal
        Account account=new Account("tmp",20,"cla","cla");
        DataController.getInstance().addAccount(account);
        account.addRole(new Coach(account.getName(),"bla","bla",null));
        owner.appointOwnerToTeam(account);

        assertNotNull(account.checkIfOwner());
        assertNotNull(account.checkIfCoach());

        assertTrue(owner.removeOwnerFromTeam(account.checkIfOwner()));
        assertNull(account.checkIfOwner());
        assertNull(account.checkIfCoach());

        //check that owner cant be removed if theres only one
        try {
            owner.removeOwnerFromTeam(owner);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The team has only one owner");
        }

    }

    @Test /*UC 6.4*/
    public void appointTeamManagerToTeam() throws Exception {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        //not allow appointment if allready has role
        secondAccount.addRole(new Coach(secondAccount.getName(),"bla","bla",null));
        try {
            owner.appointTeamManagerToTeam(secondAccount,permissionEnumList);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"The account already has another role");
        }
        //check standard appointment
        secondAccount.removeRole(secondAccount.checkIfCoach());
        assertTrue(owner.appointTeamManagerToTeam(secondAccount,permissionEnumList));

    }

    @Test /*UC 6.5*/
    public void removeTeamManagerFromTeam() throws Exception {
        List<TeamManager.PermissionEnum> permissionEnumList=new ArrayList<>();
        permissionEnumList.add(TeamManager.PermissionEnum.manageLeague);
        permissionEnumList.add(TeamManager.PermissionEnum.manageName);
        permissionEnumList.add(TeamManager.PermissionEnum.managePage);

        owner.appointTeamManagerToTeam(secondAccount,permissionEnumList);
        //try to remove not by the appointer
        try {
            tmpOwner.removeTeamManagerFromTeam(secondAccount.checkIfTeamManagr());
        } catch (Exception e) {
            assertEquals(e.getMessage(),"tmp is no the oppinter of notSean");
        }
        assertNotNull(secondAccount.checkIfTeamManagr());
        //standard removal
        assertTrue(owner.removeTeamManagerFromTeam(secondAccount.checkIfTeamManagr()));
        assertFalse(owner.getTeam().getTeamManagers().contains(secondAccount.checkIfTeamManagr()));
        assertNull(secondAccount.checkIfTeamManagr());

    }


    @Test /*UC 6.6*/
    public void deactivateTeam() throws Exception {
        Team existingTeam=owner.getTeam();
        try {
            owner.getNonActiveTeam(existingTeam.getName());
        } catch (Exception e) {
            assertEquals(e.getMessage(),"the entered team is not a previous team for the owner");
        }
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
    public void createPlayer() throws Exception {
        Account test=owner.createPlayer("testPla",20,new Date(123),PositionEnum.CenterBack,"bla11","bla");
        assertTrue(DataController.getInstance().getAccounts().contains(test));
        assertNotNull(test.checkIfPlayer());
    }

    @Test
    public void createTeamManager() throws Exception {
        Account test=owner.createTeamManager("testPla",20,null,"bla11","bla");
        assertTrue(DataController.getInstance().getAccounts().contains(test));
        assertNotNull(test.checkIfTeamManagr());
    }

    @Test
    public void createCoach() throws Exception {
        Account test=owner.createCoach("testPla",20,"bla","bla","bla11","bla");
        assertTrue(DataController.getInstance().getAccounts().contains(test));
        assertNotNull(test.checkIfCoach());
    }

}