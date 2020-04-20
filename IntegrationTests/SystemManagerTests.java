
import BusinessLayer.OtherCrudOperations.Account;
import BusinessLayer.OtherCrudOperations.League;
import BusinessLayer.OtherCrudOperations.Stadium;
import BusinessLayer.OtherCrudOperations.Team;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.*;
import DataLayer.DataManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemManagerTests {
    SystemManager systemManager = new SystemManager("nadav");

    @Before
    public void setUp() throws Exception {
        DataManager.clearDataBase();
    }

    @Test
    public void deleteTeamPermanently() {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        DataManager.addTeam(team);
        Page page = new Page(team);
        team.setPage(page);
        Owner owner = new Owner("nadav",team,null);
        TeamManager teamManager = new TeamManager("sean",team,owner);
        team.addOwner(owner);
        team.addTeamManager(teamManager);
        Fan fan = new Fan("zlil");
        fan.addPage(page);
        systemManager.DeleteTeamPermanently(team);
        assertEquals(0,fan.getPages().size());
        assertEquals("description: Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName(),owner.getAlertList().get(0).toString());
        assertEquals("description: Delete BusinessLayer.OtherCrudOperations.Team Permanently:" + team.getName(),teamManager.getAlertList().get(0).toString());
        assertEquals(0,DataManager.getTeams().size());
    }
    @Test
    public void createOwner()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        Owner owner = new Owner("nadav",team,null);
        Account account = systemManager.createOwner("yosi",99,"yosi","1234");
        assertEquals(account, DataManager.getAccount(0));
        assertEquals(account.getRole(0),DataManager.getAccount(0).getRole(0));

    }
    @Test
    public void createSystemManager()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        SystemManager systemManager = new SystemManager("nadav");
        Account account = systemManager.createSystemManager("yosi",99,"yosi","1234");
        assertEquals(account,DataManager.getAccount(0));
        assertEquals(account.getRole(0),DataManager.getAccount(0).getRole(0));

    }

    @Test
    public void createAssociationRepresentative()
    {
        League league = new League("First BusinessLayer.OtherCrudOperations.League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        AssociationRepresentative associationRepresentative = new AssociationRepresentative("nadav");
        Account account = systemManager.createAssociationRepresentative("yosi",99,"yosi","1234");
        assertEquals(account,DataManager.getAccount(0));
        assertEquals(account.getRole(0),DataManager.getAccount(0).getRole(0));

    }

}
