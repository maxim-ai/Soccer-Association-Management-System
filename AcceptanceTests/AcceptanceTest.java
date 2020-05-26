
import Client.ServiceLayer.GuestController.GuestController;
import Client.ServiceLayer.OurSystemClient;
import Client.ServiceLayer.RoleController.OwnerController;
import Server.BusinessLayer.OurSystemServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AcceptanceTest {
    private final ByteArrayOutputStream OS = new ByteArrayOutputStream();
    private final PrintStream PS = System.out;
    OurSystemClient OSC;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(OS));
        OSC=new OurSystemClient();
        OSC.Initialize();
    }

    @After
    public void restore() {
        System.setOut(PS);
    }


    //region Guest tests
    @Test
    public void LogInTest(){
        GuestController GC=OSC.makeGuestController();
        List<Object> list=null;
        //Test for empty username field
        list=GC.LogIn("","Password");
        assertEquals("Username length is 0",list.get(0));
        //endtest

        //Test for empty password field
        list=GC.LogIn("Owner1X","");
        assertEquals("Password length is 0",list.get(0));
        //endtest

        //Test for both username and password empty
        list=GC.LogIn("","");
        assertEquals("Username and password length is 0",list.get(0));
        //endtest

        //Test for not existing username
        (new OurSystemServer()).Initialize();
        list=GC.LogIn("Owner1XX","Pasword");
        assertEquals("Username does not exist",list.get(0));
        //endtest

        //Test for wrong password
        list=GC.LogIn("Owner1X","Paswordd");
        assertEquals("Wrong password",list.get(0));
        //endtest

        //Test for successful logging in
        list=GC.LogIn("Owner1X","Password");
        assertTrue(list.get(0) instanceof OwnerController);
        //endtest

    }


    //endregion




}