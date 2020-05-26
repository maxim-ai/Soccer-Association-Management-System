//import Server.BusinessLayer.DataController;
//import Server.BusinessLayer.OtherCrudOperations.Alert;
//import Server.BusinessLayer.RoleCrudOperations.Fan;
//import Server.BusinessLayer.RoleCrudOperations.Role;
//import Client.ServiceLayer.OurSystem;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class AlertTests {
//    Alert alert;
//
//    @Before
//    public void setUp() throws Exception {
//        DataController.getInstance().clearDataBase();
//        alert=new Alert("aaa");
//    }
//
//    @Test
//    public void notifyOtherRole() {
//        Role coach=new Fan("maxim");
//        OurSystem.notifyOtherRole(alert.getDescription(),coach);
//        assertEquals(coach.getAlertList().size(),1);
//        assertEquals(coach.getAlertList().get(0).getDescription(),"aaa");
//    }
//
//
//
//
//}