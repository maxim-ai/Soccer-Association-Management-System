
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SystemManagerTest {
    SystemManager systemManager = new SystemManager("nadav");

    @Before
    public void setUp() throws Exception {
        DataManager.cleatDataBase();
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
        League league = new League("First League");
        Stadium stadium = new Stadium("Bloomfield");
        Team team = new Team("Maccabi",league,stadium);
        DataManager DM = new DataManager();
        DataManager.addTeam(team);
        Page page = new Page(team);
        team.setPage(page);
        systemManager.DeleteTeamPermanently(team);
        assertEquals(0,DataManager.getTeams().size());
    }

    @Test
    public void deleteAccount() {
        Account account = new Account("nadav",12, "nadav270","12345");
        DataManager dataManager = new DataManager();
        DataManager.addAccount(account);
        systemManager.deleteAccount(account);
        assertEquals(0,DataManager.getAccounts().size());

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
    public void showSystemLog() {
    }

    @Test
    public void buildRecommendationSystem() {
    }

}
