import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Policy;
import Server.BusinessLayer.OtherCrudOperations.SLsettings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolicyTest {

    SLsettings sLsettings;
    Policy policy;
    Policy policy1;
    SLsettings sLsettings1;

    @Before
    public void setUp()
    {
        DataController.getInstance().clearDataBase();
        policy=new Policy("pc","gs");
        sLsettings=new SLsettings(policy);
        policy1=new Policy("pc1","gs1");
        sLsettings1=new SLsettings(policy1);
    }
    @Test
    public void getsLsettings()
    {
        assertEquals(policy.getsLsettings(),sLsettings);
    }

    @Test
    public void setsLsettings()
    {
        policy.setsLsettings(sLsettings1);
        assertEquals(policy.getsLsettings(),sLsettings1);
    }

    @Test
    public void setPointCalc()
    {
        policy.setPointCalc("try");
        assertEquals(policy.getPointCalc(),"try");
    }

    @Test
    public void setGameSchedual()
    {
        policy.setGameSchedual("try");
        assertEquals(policy.getGameSchedual(),"try");
    }

    @Test
    public void getPointCalc()
    {
        assertEquals(policy.getPointCalc(),"pc");
    }

    @Test
    public void getGameSchedual()
    {
        assertEquals(policy.getGameSchedual(),"gs");
    }
    
}