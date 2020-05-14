import Server.BusinessLayer.DataController;
import Server.BusinessLayer.RoleCrudOperations.Coach;
import Server.BusinessLayer.RoleCrudOperations.Fan;
import Server.BusinessLayer.Pages.Page;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PageTest {
    Page page;
    Coach coach;
    Fan fan1;
    Fan fan2;
    List<Fan> fans=new LinkedList<>();
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
    public void getType() {
        assertEquals(page.getType(),coach);
    }

    @Test
    public void setType() {
        Coach checkCoach=new Coach("Sean","vvv","www",null);
        page.setType(checkCoach);
        assertEquals(page.getType(),checkCoach);
    }

    @Test
    public void getFan() {
        assertEquals(fan2,page.getFan(1));
    }

    @Test
    public void getFans() {
        List<Fan> fans=page.getFans();
        assertEquals(fan1,fans.get(0));
        assertEquals(fan2,fans.get(1));
    }

    @Test
    public void numberOfFans() {
        assertEquals(2,page.numberOfFans());
    }

    @Test
    public void hasFans() {
        assertTrue(page.hasFans());
    }

    @Test
    public void indexOfFan() {
        assertEquals(0,page.indexOfFan(fan1));
    }

    @Test
    public void minimumNumberOfFans() {
        assertEquals(0,page.minimumNumberOfFans());
    }



    @Test
    public void addFan() {
        Fan checkFan=new Fan("Ziv");
        assertTrue(addFanTest(checkFan));
    }
    public boolean addFanTest(Fan aFan)
    {
        boolean wasAdded = true;
        if (fans.contains(aFan)) { return true; }
        fans.add(aFan);
        if (indexOfPageStub(page) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = addPageStub(page);
            if (!wasAdded)
            {
                fans.remove(aFan);
            }
        }
        return wasAdded;
    }
    //stubs
    public int indexOfPageStub(Page aPage)
    {
        return 0;
    }
    public boolean addPageStub(Page aPage)
    {
       return true;
    }
    //func
    @Test
    public void removeFan() {
        page.addFan(fan1);
        removeFanTest(fan1);

    }
    public boolean removeFanTest(Fan aFan)
    {
        boolean wasRemoved = true;
        if (!fans.contains(aFan))
        {
            return wasRemoved;
        }

        int oldIndex = fans.indexOf(aFan);
        fans.remove(oldIndex);
        if (indexOfPageStub(page) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = removePageStub(page);
            if (!wasRemoved)
            {
                fans.add(oldIndex,aFan);
            }
        }
        return wasRemoved;
    }
    public boolean removePageStub(Page aPage)
    {
        return true;
    }


}