import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Alert;
import Server.BusinessLayer.RoleCrudOperations.Coach;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.Pages.Page;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageTests {
    Page page;
    Coach coach;
    Fan fan1;
    Fan fan2;

    @Before
    public void setUp() throws Exception {
        DataController.getInstance().clearDataBase();
        coach=new Coach("Maxim","aaa","bbb",null);
        page=new Page(coach);
        coach.setPage(page);

        fan1=new Fan("Tzlil");
        fan2=new Fan("Eitan");
        page.addFan(fan1);
        page.addFan(fan2);
        fan1.addPage(page);
        fan2.addPage(page);
    }
    @Test
    public void addFan() {
        Fan checkFan=new Fan("Ziv");
        page.addFan(checkFan);
        assertEquals(3,page.getFans().size());
        assertEquals(checkFan,page.getFan(2));
    }

    @Test
    public void removeFan() {
        page.removeFan(fan1);
        assertEquals(1,page.getFans().size());
        assertEquals(fan2,page.getFan(0));
    }

    @Test
    public void delete() {
        assertTrue(page.hasFans());
        assertTrue(fan1.hasPages());
        assertTrue(fan2.hasPages());
        page.delete();
        assertFalse(page.hasFans());
        assertFalse(fan1.hasPages());
        assertFalse(fan2.hasPages());

    }

    @Test
    public void notifyTrackingFans() {
        Fan checkFan=new Fan("Danny");
        page.addFan(checkFan);
        checkFan.addPage(page);
        fan1.setTrackPersonalPages(true);
        fan2.setTrackPersonalPages(false);
        checkFan.setTrackPersonalPages(true);

        Alert alert=new Alert("aaa");
        page.notifyTrackingFans(alert);

        assertEquals(1,fan1.getAlertList().size());
        assertEquals("aaa",fan1.getAlertList().get(0).getDescription());
        assertEquals(1,checkFan.getAlertList().size());
        assertEquals("aaa",checkFan.getAlertList().get(0).getDescription());

        assertEquals(0,fan2.getAlertList().size());


    }

}