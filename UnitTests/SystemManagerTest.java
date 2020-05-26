
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.Logger.Logger;
import Server.BusinessLayer.OtherCrudOperations.*;
import Server.BusinessLayer.Pages.Page;
import Server.BusinessLayer.RoleCrudOperations.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class SystemManagerTest {
    SystemManager systemManager = new SystemManager("nadav");

    @Before
    public void setUp() throws Exception {
        DataController.getInstance().clearDataBase();
    }
    @Test
    public void getComplaintAndComments() {
        HashMap<String,String> complaintAndComments = new HashMap<>();
        systemManager.setComplaintAndComments(complaintAndComments);
        assertEquals(complaintAndComments,systemManager.getComplaintAndComments());
    }

    @Test
    public void setComplaintAndComments() {
        HashMap<String,String> complaintAndComments = new HashMap<>();
        systemManager.setComplaintAndComments(complaintAndComments);
        assertEquals(complaintAndComments,systemManager.getComplaintAndComments());
    }

    @Test
    public void deleteTeamPermanently() {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        DataController.getInstance().addTeam(team);
        Page page = new Page(team);
        team.setPage(page);
        Owner owner = new Owner("nadav",team,null);
        TeamManager teamManager = new TeamManager("sean",team,owner);
        team.addOwner(owner);
        team.addTeamManager(teamManager);
        Fan fan = new Fan("zlil");
        fan.addPage(page);
        DeleteTeamPermanently(team);
        assertEquals(0,fan.getPages().size());
        assertEquals( "description: Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName(),owner.getAlertList().get(0).toString());
        assertEquals("description: Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName(),teamManager.getAlertList().get(0).toString());
        assertEquals(0, DataController.getInstance().getTeams().size());
    }


    public boolean DeleteTeamPermanently(Team team){
        boolean wasSet = false;
        if(team == null){
            return  wasSet;
        }
        wasSet = removeTeamFromDmStub(team);
        //saveAction(team);
        notifyOnDeletedStub(team);
        deleteFromAllFollowersStub(team);
        deleteStub(team);
        return wasSet;
    }

    public void deleteStub(Team team)
    {
        ArrayList<TeamManager> copyOfTeamManagers = new ArrayList<>(team.getTeamManagers());
        copyOfTeamManagers.clear();
        for(TeamManager aTeamManager : copyOfTeamManagers)
        {
            aTeamManager.setTeam(null);
        }
        Page existingPage = team.getPage();
        existingPage = null;
        if (existingPage != null)
        {
            existingPage.delete();
        }
        ArrayList<Coach> copyOfCoachs = new ArrayList<Coach>(team.getCoachs());
        copyOfCoachs.clear();
        for(Coach aCoach : copyOfCoachs)
        {
            aCoach.removeTeam(team);
        }
        ArrayList<Owner> copyOfOwners = new ArrayList<Owner>(team.getOwners());
        copyOfOwners.clear();
        for(Owner aOwner : copyOfOwners)
        {
            aOwner.setTeam(null);
        }
        ArrayList<Player> copyOfPlayers = new ArrayList<>(team.getPlayers());
        copyOfPlayers.clear();
        for(int i=copyOfPlayers.size(); i > 0; i--)
        {
            Player aPlayer = copyOfPlayers.get(i - 1);
            aPlayer.setTeam(null);
        }
        League placeholderLeague = team.getLeague();
        placeholderLeague = null;
        if(placeholderLeague != null)
        {
            placeholderLeague.removeTeam(team);
        }
        ArrayList<Match> copyOfMatchs = new ArrayList<Match>(team.getMatchs());
        copyOfMatchs.clear();
        for(Match aMatch : copyOfMatchs)
        {
            aMatch.delete();
        }
        Stadium placeholderStadium = team.getStadium();
        placeholderStadium=null;
        if(placeholderStadium != null)
        {
            placeholderStadium.removeTeam(team);
        }
    }

    private boolean removeTeamFromDmStub(Team aTeam) {
        boolean wasRemoved = true;
        DataController.getInstance().removeTeam(aTeam);
        wasRemoved = true;
        return wasRemoved;
    }

    private void notifyOnDeletedStub(Team team) {
        for (Owner owner : team.getOwners()){
            owner.addAlert(new Alert("Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName()));
        }
        for(TeamManager teamManager : team.getTeamManagers()){
            teamManager.addAlert(new Alert("Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName()));
        }
    }

    /**
     * delete team's page from all fans of the team (follower)
     * @param team
     */
    private void deleteFromAllFollowersStub(Team team) {
        Page teamPageToDelete = team.getPage();
        List<Fan> fans = teamPageToDelete.getFans();
        for (Fan fan : fans) {
            for (Page page : fan.getPages()) {
                if (page.equals(teamPageToDelete)) {
                    page.delete();
                    if(fans.size()==0)
                        return;
                    break;
                }
            }
        }

    }

    @Test
    public void deleteAccount() {
        Account account = new Account("nadav",12, "nadav270","12345");
        DataController.getInstance().addAccount(account);
        systemManager.deleteAccount(account);
        assertEquals(0, DataController.getInstance().getAccounts().size());

    }

    @Test
    public void showComplaints() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        systemManager.addComplain("1");
        systemManager.showComplaints();
        String expectedOutput  = "1\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void addComplain() {
        systemManager.addComplain("1");
        systemManager.addComment("1","1");
        String string =systemManager.getComplaintAndComments().get("1");
        assertEquals("1",string);

    }

    @Test
    public void addComment() {
        systemManager.addComplain("1");
        systemManager.addComment("2","1");
        String string =systemManager.getComplaintAndComments().get("1");
        assertEquals("2",string);
    }

    @Test
    public void showSystemLog(){
        assertNotNull(systemManager.showSystemLog());
    }

    @Test
    public void createOwner()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        Owner owner = new Owner("nadav",team,null);
        Account account = CreateOwner("yosi",99,"yosi","1234");
        assertEquals(account, DataController.getInstance().getAccount(0));
        assertEquals(account.getRole(0), DataController.getInstance().getAccount(0).getRole(0));
    }
    @Test
    public void createSystemManager()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        SystemManager systemManager = new SystemManager("nadav");
        Account account = CreateSystemManager("yosi",99,"yosi","1234");
        assertEquals(account, DataController.getInstance().getAccount(0));
        assertEquals(account.getRole(0), DataController.getInstance().getAccount(0).getRole(0));

    }

    @Test
    public void createAssociationRepresentative()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        AssociationRepresentative associationRepresentative = new AssociationRepresentative("nadav");
        Account account = CreateAssociationRepresentative("yosi",99,"yosi","1234");
        assertEquals(account, DataController.getInstance().getAccount(0));
        assertEquals(account.getRole(0), DataController.getInstance().getAccount(0).getRole(0));

    }

    public Account CreateOwner(String aName,int age,String userName, String password)
    {
        Owner owner=new Owner(aName,null,null);
        Account account=new Account(aName,age,userName,password);
        addRoleStub(owner,account);
        createAccountStub(account);
        Logger.getInstance().writeNewLine(systemManager.getName()+" just created a new owner: "+aName);
        return account;
    }

    public Account CreateSystemManager(String aName,int age,String userName, String password)
    {
        SystemManager systemManager=new SystemManager(aName);
        Account account=new Account(aName,age,userName,password);
        addRoleStub(systemManager,account);
        createAccountStub(account);
        Logger.getInstance().writeNewLine(systemManager.getName()+" just created a new System Manager: "+aName);
        return account;
    }

    public Account CreateAssociationRepresentative(String aName,int age,String userName, String password)
    {
        AssociationRepresentative associationRepresentative=new AssociationRepresentative(aName);
        Account account=new Account(aName,age,userName,password);
        addRoleStub(associationRepresentative,account);
        createAccountStub(account);
        Logger.getInstance().writeNewLine(systemManager.getName()+" just created a new Association Representative: "+aName);
        return account;
    }

    public static boolean createAccountStub(Account accountToAdd){
        DataController.getInstance().addAccount(accountToAdd);
        return true;
    }

    public boolean addRoleStub(Role aRole,Account account)
    {
        boolean wasAdded = true;
        if (account.getRoles().contains(aRole)) { return true; }
        account.addRole(aRole);
        return wasAdded;
    }


}
