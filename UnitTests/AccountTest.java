import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Account;
import Server.BusinessLayer.RoleCrudOperations.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {
    Account account = new Account("BusinessLayer.OtherCrudOperations.Account",10,"BusinessLayer.OtherCrudOperations.Account","Password");// for setters
    Account constAccount = new Account("BusinessLayer.OtherCrudOperations.Account",10,"Account10","Password"); //for getters

    @Before
    public void setUp(){
        DataController.getInstance().clearDataBase();
    }

    @Test
    public void getName() {
        assertEquals("BusinessLayer.OtherCrudOperations.Account", constAccount.getName());
    }

    @Test
    public void getAge() {
        assertEquals(10, constAccount.getAge());
    }

    @Test
    public void setName() {
        account.setName("test");
        assertEquals("test",account.getName());

    }

    @Test
    public void setAge() {
        account.setAge(11);
        assertEquals(11,account.getAge());
    }

    @Test
    public void setUserName() {
        account.setUserName("Account11");
        assertEquals("Account11", account.getUserName());
    }

    @Test
    public void setPassword() {
        account.setPassword("Password11");
        assertEquals("Password11", account.getPassword());

    }

    @Test
    public void getUserName() {
        assertEquals("Account10", constAccount.getUserName());
    }

    @Test
    public void getPassword() {
        assertEquals("Password", constAccount.getPassword());
    }

    @Test
    public void getRole() {
        List<Role> roleList = constAccount.getRoles();
        for(int index=0; index<constAccount.numberOfRoles();index++)
            assertEquals(roleList.get(index),constAccount.getRole(index));

    }

    @Test
    public void getRoles() {
        if (constAccount.hasRoles()) {
            assertNotNull(constAccount.getRoles());
        }
        else
            assertEquals(0,constAccount.numberOfRoles());
    }

    @Test
    public void numberOfRoles() {
        assertEquals(account.getRoles().size(),account.numberOfRoles());
    }

    @Test
    public void hasRoles() {
        if(account.numberOfRoles()>0)
            assertTrue(account.hasRoles());
        else
            assertFalse(account.hasRoles());
    }

    @Test
    public void indexOfRole() {
        List<Role> roleList = account.getRoles();
        for(Role roleInList:roleList) {
            assertEquals(roleList.indexOf(roleInList), account.indexOfRole(roleInList));
        }
    }

    @Test
    public void minimumNumberOfRoles() {
        assertEquals(1, Account.minimumNumberOfRoles());
    }

    @Test
    public void addRole() {
        Role testRole= new AssociationRepresentative("Test");
        List<Role> roleList = account.getRoles();
        int numberOfRolesBeforeAdding = account.numberOfRoles();
        if(!roleList.contains(testRole)) {
            account.addRole(testRole);
            assertEquals(numberOfRolesBeforeAdding + 1, account.numberOfRoles());
            assertTrue(account.getRoles().contains(testRole));
        }
        else {
            account.addRole(testRole);
            assertEquals(numberOfRolesBeforeAdding, account.numberOfRoles());
        }

    }

    @Test
    public void removeRole() {
        if(account.hasRoles()) {
            account.removeRole(account.getRole(0));
        }
        else{
            Role testRole= new AssociationRepresentative("Test");
            int numberOfRolesBeforeAdding=account.numberOfRoles();
            if(!account.getRoles().contains(testRole)) {
                account.addRole(testRole);
                account.removeRole(testRole);
                assertEquals(numberOfRolesBeforeAdding,account.numberOfRoles());
            }
            else{
                account.removeRole(testRole);
                assertEquals(numberOfRolesBeforeAdding-1,account.numberOfRoles());
            }
        }
    }

    @Test
    public void emptyRoles() {
        account.emptyRoles();
        assertEquals(0,account.numberOfRoles());
    }

    @Test
    public void toString1() {
        String toString = account.toString();
        String expected = account.getClass().getName()+"@"+Integer.toHexString(account.hashCode()) + "[userName:" + account.getUserName()+ "," +
                "password:" + account.getPassword()+ "]";
        assertEquals(expected,toString);
    }

    @Test
    public void checkIfCoach() {
        boolean isCoach = false;
        for(Role role:account.getRoles()){
            if(role instanceof Coach){
                isCoach=true;
            }
        }
        if(isCoach)
            assertNotNull(account.checkIfCoach());
        else
            assertNull(account.checkIfCoach());
    }

    @Test
    public void checkIfTeamManagr() {
        boolean isTeamManagr = false;
        for(Role role:account.getRoles()){
            if(role instanceof TeamManager){
                isTeamManagr=true;
            }
        }
        if(isTeamManagr)
            assertNotNull(account.checkIfTeamManagr());
        else
            assertNull(account.checkIfTeamManagr());
    }

    @Test
    public void checkIfOwner() {
        boolean isOwner = false;
        for(Role role:account.getRoles()){
            if(role instanceof Owner){
                isOwner=true;
            }
        }
        if(isOwner)
            assertNotNull(account.checkIfOwner());
        else
            assertNull(account.checkIfOwner());
    }

    @Test
    public void checkIfPlayer() {
        boolean isPlayer = false;
        for(Role role:account.getRoles()){
            if(role instanceof Player){
                isPlayer=true;
            }
        }
        if(isPlayer)
            assertNotNull(account.checkIfPlayer());
        else
            assertNull(account.checkIfPlayer());
    }

    @Test
    public void checkIfFan() {
        boolean isFan = false;
        for(Role role:account.getRoles()){
            if(role instanceof Fan){
                isFan=true;
            }
        }
        if(isFan)
            assertNotNull(account.checkIfFan());
        else
            assertNull(account.checkIfFan());
    }

    @Test
    public void checkIfSystemManager() {
        boolean isSystemManager = false;
        for(Role role:account.getRoles()){
            if(role instanceof SystemManager){
                isSystemManager=true;
            }
        }
        if(isSystemManager)
            assertNotNull(account.checkIfSystemManager());
        else
            assertNull(account.checkIfSystemManager());
    }

    @Test
    public void checkIfAssociationRepresentative() {
        boolean isAssociationRepresentive = false;
        for(Role role:account.getRoles()){
            if(role instanceof AssociationRepresentative){
                isAssociationRepresentive=true;
            }
        }

        if(isAssociationRepresentive)
            assertNotNull(account.checkIfAssiciationRepresentive());
        else
            assertNull(account.checkIfAssiciationRepresentive());
    }

    @Test
    public void checkIfReferee() {
        boolean isReferee = false;
        for(Role role:account.getRoles()){
            if(role instanceof Referee){
                isReferee=true;
            }
        }

        if(isReferee)
            assertNotNull(account.checkIfReferee());
        else
            assertNull(account.checkIfReferee());
    }

    @Test
    public void showAccount() {

    }

}
