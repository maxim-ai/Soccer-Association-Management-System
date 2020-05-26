import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Alert;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.RoleCrudOperations.Role;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoleTest {
    Role role;

    @Before
    public void setUp() throws Exception {
        DataController.getInstance().clearDataBase();
        role=new Fan("Maxim");
    }

    //region Getters&Setters Test
    @Test
    public void setName() {
        role.setName("Sean");
        assertEquals("Sean",role.getName());
    }

    @Test
    public void getName() {
        assertEquals("Maxim",role.getName());
    }

    @Test
    public void testToString() {
        assertEquals("name: Maxim",role.toString());
    }

    @Test
    public void getAlertList() {
        Alert alert1=new Alert("aaa");
        Alert alert2=new Alert("bbb");
        List<Alert> alertList=new ArrayList<>();
        alertList.add(alert1);
        alertList.add(alert2);
        role.setAlertList(alertList);
        assertEquals(role.getAlertList(),alertList);
    }

    @Test
    public void setAlertList() {
        Alert alert1=new Alert("aaa");
        Alert alert2=new Alert("bbb");
        List<Alert> alertList=new ArrayList<>();
        alertList.add(alert1);
        alertList.add(alert2);

    }
    //endregion

    //region UC and Technical Tests
    @Test
    public void addAlert() {
        Alert alert=new Alert("aaa");
        role.addAlert(alert);
        assertEquals(alert,role.getAlertList().get(0));
    }

    @Test
    public void clearAlerts() {
        Alert alert=new Alert("aaa");
        role.addAlert(alert);
        role.clearAlerts();
        assertEquals(0,role.getAlertList().size());
    }

    @Test
    public void removeAlert() {
        Alert alert1=new Alert("aaa");
        Alert alert2=new Alert("bbb");
        role.addAlert(alert1);
        role.addAlert(alert2);
        role.removeAlert(alert1);
        assertEquals(1,role.getAlertList().size());
        assertEquals(alert2,role.getAlertList().get(0));
    }
    //endregion
}