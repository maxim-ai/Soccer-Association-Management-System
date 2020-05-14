import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Alert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest {
    Alert alert;

    @Before
    public void setUp() throws Exception {
        DataController.getInstance().clearDataBase();
        alert=new Alert("aaa");
    }

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
}