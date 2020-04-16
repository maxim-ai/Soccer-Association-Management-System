import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTests {
    Alert alert;

    @Before
    public void setUp() throws Exception {
        alert=new Alert("aaa");
    }

    @Test
    public void notifyOtherRole() {
        Role coach=new Fan("maxim");
        Alert.notifyOtherRole(alert.getDescription(),coach);
        assertEquals(coach.getAlertList().size(),1);
        assertEquals(coach.getAlertList().get(0).getDescription(),"aaa");
    }




}