import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest {
    Alert alert;

    @Before
    public void setUp() throws Exception {
        alert=new Alert("aaa");
    }

    //region Getters&Setters Tests
    @Test
    public void setDescription() {
        alert.setDescription("bbb");
        assertEquals("bbb",alert.getDescription());
    }

    @Test
    public void getDescription() {
        assertEquals("aaa",alert.getDescription());
    }

    @Test
    public void testToString() {
        assertEquals("description: aaa","description: "+alert.getDescription());
    }
    //endregion

    //region UC and Technical Tests
    @Test
    public void notifyOtherRole() {
        Role coach=new Fan("maxim");
        Alert.notifyOtherRole(alert.getDescription(),coach);
        assertEquals(coach.getAlertList().size(),1);
        assertEquals(coach.getAlertList().get(0).getDescription(),"aaa");
    }
    //endregion
}