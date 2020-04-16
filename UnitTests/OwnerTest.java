import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
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

        //successfull assersion
        assertTrue(addAssetToTeam(tmpCoach,owner));
        assertTrue(addAssetToTeam(tmpPlayer,owner));
        assertTrue(addAssetToTeam(tmpStadium,owner));


        //unsuccessful assersion
        assertFalse(addAssetToTeam(tmpReferee,owner));
        assertFalse(addAssetToTeam(null,owner));

        owner.setTeam(null);
        assertFalse(addAssetToTeam(tmpCoach,owner));
    }

    @Test /*UC 6.1*/
    public void removeAssetFromTeam()
    {
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18),PositionEnum.CenterBack,tempTeam,null);
        Coach tmpCoach=new Coach("tmpCoach","bla","bla",null);
        Stadium tmpStadium=new Stadium("tmpStadium");
        Referee tmpReferee=new Referee("bla","bla");

        //unsuccessful removal
        assertFalse(removeAssetFromTeam(tmpCoach,owner));
        assertFalse(removeAssetFromTeam(tmpPlayer,owner));
        assertFalse(removeAssetFromTeam(tmpStadium,owner));
        assertFalse(removeAssetFromTeam(tmpReferee,owner));
        assertFalse(removeAssetFromTeam(null,owner));



        owner.addAssetToTeam(tmpCoach);
        owner.addAssetToTeam(tmpPlayer);
        owner.addAssetToTeam(tmpStadium);

        //successful removal
        assertTrue(removeAssetFromTeam(tmpCoach,owner));
        assertTrue(removeAssetFromTeam(tmpPlayer,owner));
        assertTrue(removeAssetFromTeam(tmpStadium,owner));

        //unsuccessful removal
        owner.setTeam(null);
        assertFalse(removeAssetFromTeam(tmpCoach,owner));

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

        //successful editing

        assertTrue(owner.editTeamAsset(tmpPlayer,1,"newName"));
        assertTrue(owner.editTeamAsset(tmpPlayer,2,"01-01-1990"));
        assertTrue(owner.editTeamAsset(tmpPlayer,3,"Goalkeeper"));
        assertTrue(owner.editTeamAsset(tmpCoach,1,"newName"));
        assertTrue(owner.editTeamAsset(tmpCoach,2,"newTraining"));
        assertTrue(owner.editTeamAsset(tmpCoach,3,"newRole"));
        assertTrue(owner.editTeamAsset(tmpStadium,1,"newName"));

        //successful editing

        assertFalse(owner.editTeamAsset(tmpPlayer,4,"01-01-1990"));
        assertFalse(owner.editTeamAsset(tmpCoach,4,"01-01-1990"));
        assertFalse(owner.editTeamAsset(tmpStadium,4,"01-01-1990"));

        assertFalse(owner.editTeamAsset(tmpPlayer,2,"01.01.1990"));
    }

    @Test /*UC 6.1*/
    public void showEditingOptions()
    {
        Player tmpPlayer=new Player("tmpPlayer",new Date(1997,11,18),PositionEnum.CenterBack,tempTeam,null);
        Coach tmpCoach=new Coach("tmpCoach","bla","bla",null);
        Stadium tmpStadium=new Stadium("tmpStadium");
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<String> list3=new ArrayList<>();
        list1.add("name");list2.add("name");list3.add("name");
        list2.add("training"); list2.add("teamRole");
        list3.add("birthday");list3.add("position");
        //successful
        assertEquals(owner.showEditingOptions(tmpStadium),list1);
        assertEquals(owner.showEditingOptions(tmpCoach),list2);
        assertEquals(owner.showEditingOptions(tmpPlayer),list3);
        //unsuccessful
        assertNull(owner.showEditingOptions("sdf"));
        owner.setTeam(null);
        assertNull(owner.showEditingOptions(tmpStadium));
    }

    @Test /*UC 6.2*/
    public void appointOwnerToTeam()
    {
        Account account=new Account("newacc",20,"cla","cla");
        account.addRole(new Referee("bla","bla"));
        assertFalse(owner.appointOwnerToTeam(account));

        account.addRole(new Coach("newC","bla","bla",null));
        account.removeRole(account.checkIfReferee());

        //success
        assertTrue(appointOwnerToTeam(account,owner));


        //unseccess in integration

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


        assertTrue(removeOwnerFromTeam(account.checkIfOwner(),owner));

        //check that owner cant be removed if theres only one
        owner.removeOwnerFromTeam(account.checkIfOwner());
        assertFalse(removeOwnerFromTeam(owner,owner));

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
        assertFalse(appointTeamManagerToTeam(secondAccount,permissionEnumList,owner));
        //check standard appointment
        secondAccount.removeRole(secondAccount.checkIfCoach());
        assertTrue(appointTeamManagerToTeam(secondAccount,permissionEnumList,owner));

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
        assertFalse(removeTeamManagerFromTeam(secondAccount.checkIfTeamManagr(),tmpOwner));
        //standard removal in integration
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
        assertTrue(deactivateTeam(owner));
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
        assoAccount.addRole(new AssociationRepresentative(assoAccount.getName()));
        SystemManager.createAccount(assoAccount);

        String ans=createTeam("newTeam",new League("newLeague"),new Stadium("newSta"),owner);
        assertTrue(ans.contains("Wrong input"));


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



    //*----------------------------------------------stubs--------------------------------------------------------------------*/
    public boolean addAssetToTeam(Object o,Owner owner)
    {
        if (owner.getTeam() == null)
            return false;

        else if (o instanceof Coach)
        {
            loggerStub("");
            return addCoachStub((Coach) o);
        }
        else if (o instanceof Player) {
            loggerStub("");
            return addPlayerStub((Player) o);
        }
        else if (o instanceof Stadium) {
            loggerStub("");
            return setStadiumStub((Stadium) o);
        }
        else
            return false;
    }

    private void loggerStub(String s)
    {
        return;
    }

    private boolean setStadiumStub(Stadium o) {
        return true;
    }

    private boolean addPlayerStub(Player o) {
        return true;
    }

    private boolean addCoachStub(Coach o) {
        return true;
    }

    public boolean removeAssetFromTeam(Object o,Owner owner)
    {
        boolean validRemoval = true;
        if (owner.getTeam() == null)
            return false;

        else if (o instanceof Coach) {
            if (owner.getTeam().getCoachs()==null || owner.getTeam().indexOfCoach((Coach) o) == -1)
                return false;
            loggerStub("");
            return removeCoach((Coach) o);
        }
        else if (o instanceof Player) {
            if (owner.getTeam().getPlayers()==null || owner.getTeam().indexOfPlayer((Player) o) == -1)
                return false;
            loggerStub("");
            return removePlayer((Player) o);
        }
        else if (o instanceof Stadium) {
            if (owner.getTeam().getStadium()==null || !owner.getTeam().getStadium().equals(o))
                return false;
            loggerStub("");
            return setStadium(null);
        }
        else
            return false;
    }

    private boolean setStadium(Object o) {
        return true;
    }

    private boolean removePlayer(Player o) {
        return true;
    }

    private boolean removeCoach(Coach o) {
        return true;
    }

    public boolean appointOwnerToTeam(Account account,Owner owner)
    {

        if(account.hasRoles() && account.checkIfTeamManagr()==null && account.checkIfCoach()==null && account.checkIfPlayer()==null)
            return false;
        Owner checkOwner = account.checkIfOwner();
        if (checkOwner != null) {
            if (owner.getTeam().indexOfOwner(checkOwner) != -1)
                return false;
        }
        else
        {
            checkOwner = new Owner(account.getRole(0).getName(), owner.getTeam(), owner);
            addRoleStub(checkOwner);
        }
        loggerStub("");
        return addOwnerStub(checkOwner);
    }

    private boolean addOwnerStub(Owner checkOwner) {
        return true;
    }

    private void addRoleStub(Role role) {
        return;
    }

    public boolean removeOwnerFromTeam(Owner owner,Owner currOwner) {
        if (owner.getTeam().numberOfOwners() == 1)
            return false;

        if (!owner.getTeam().equals(currOwner.getTeam()) || !owner.getAppointer().equals(currOwner))
            return false;

        removeOwnerStub(owner);

        for (Account account : DataManager.getAccounts())
            for (Role role : account.getRoles())
                if (role instanceof Owner && role.equals(owner))
                {
                    removeRoleStub(role);
                    removeRoleStub(account.checkIfPlayer());
                    removeRoleStub(account.checkIfCoach());
                    removeRoleStub(account.checkIfTeamManagr());

                    if(account.checkIfPlayer()!=null) account.checkIfPlayer().delete();
                    if(account.checkIfCoach()!=null) account.checkIfCoach().delete();
                    if(account.checkIfTeamManagr()!=null) account.checkIfTeamManagr().delete();
                    deleteStub(((Owner) role));

                    loggerStub("");
                    return true;
                }

        return true;
    }

    private void deleteStub(Role role) {
        return;
    }

    private void removeRoleStub(Role role) {
        return;
    }

    private void removeOwnerStub(Owner owner) {
        return;
    }

    public boolean appointTeamManagerToTeam(Account account,List<TeamManager.PermissionEnum> permissions,Owner owner) {
        TeamManager tempTeamManager=account.checkIfTeamManagr();
        if(account.hasRoles())
            return false;

        if(tempTeamManager==null)
        {
            tempTeamManager=new TeamManager(account.getName(),owner.getTeam(),owner);
            addRoleStub(tempTeamManager);
        }

        loggerStub("");

        return addTeamManagerStub(tempTeamManager);
    }

    private boolean addTeamManagerStub(TeamManager tempTeamManager) {
        return true;
    }

    public boolean removeTeamManagerFromTeam(TeamManager teamManager,Owner owner)
    {
        if (!teamManager.getTeam().equals(owner.getTeam()) || !teamManager.getAppointer().equals(this))
            return false;

        deleteStub(teamManager);

        List<Account> tamp=DataManager.getAccounts();

        for (Account account : DataManager.getAccounts())
        {
            if(account.checkIfTeamManagr()!=null && account.checkIfTeamManagr().equals(teamManager))
            {
                removeRole(teamManager);
                break;
            }
        }

        loggerStub("");

        return true;
    }

    private void removeRole(TeamManager teamManager) {
        return;
    }

    public boolean deactivateTeam(Owner ownerc)
    {
        String notification=ownerc.getName()+" has deactivated team: "+ownerc.getTeam().getName();
        for(Owner owner:ownerc.getTeam().getOwners())
            Alert.notifyOtherRole(notification,owner);
        for(TeamManager teamManager:ownerc.getTeam().getTeamManagers())
            Alert.notifyOtherRole(notification,teamManager);
        for(SystemManager systemManager:DataManager.getSystemManagersFromAccounts())
            Alert.notifyOtherRole(notification,systemManager);

        loggerStub("");
        deleteStub(ownerc.getTeam());

        return true;
    }

    private void deleteStub(Team team) {
        return;
    }

    /**
     * creates a new team, provided there is an authorisation from the Association
     */
    public String createTeam(String teamName,League league, Stadium stadium,Owner owner)
    {
        boolean teamExists=false;
        for(Team t: DataManager.getTeams())
        {
            if(t.getName().equals(teamName))
            {
                teamExists=true;
                break;
            }
        }

        if(teamExists)
            return "Wrong input, team already exists";

        else if(!checkIfRequestExistsStub(owner,teamName))
        {
            for(AssociationRepresentative ar:DataManager.getAssiciationRepresentivesFromAccounts())
            {
                Alert.notifyOtherRole(owner.getName()+" is requesting to create a new team, teamName: "+teamName,ar);
                addOpenTeamRequestStub(owner,teamName);
            }
            return "Request sent, waiting for approval";
        }
        else
        {
            if(!getRequestStatusStub(owner,teamName))
                return "waiting for approval";
            else
            {
                Team team=new Team(teamName,league,stadium);
                addOwnerStub(owner,team);
                DataManager.addTeam(team);
                removeOpenTeamRequestStub(owner,teamName);
                Logger.getInstance().writeNewLine(owner.getName()+" just opened the team: "+teamName);
                return "Team successfully added";
            }
        }

    }

    private void removeOpenTeamRequestStub(Owner owner, String teamName) {
        return;
    }

    private boolean getRequestStatusStub(Owner owner, String teamName) {
        return true;
    }

    private void addOpenTeamRequestStub(Owner owner, String teamName) {
        return;
    }

    private void addOwnerStub(Owner owner, Team team) {
        return;
    }

    private boolean checkIfRequestExistsStub(Owner owner, String teamName) {
        return true;
    }


    //*------------------------------------------------------------------------------------------------------------------------*/



}