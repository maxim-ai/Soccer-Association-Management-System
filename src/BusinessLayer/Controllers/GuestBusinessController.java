package BusinessLayer.Controllers;

import BusinessLayer.OtherCrudOperations.Account;
import BusinessLayer.OtherCrudOperations.Guest;
import ServiceLayer.OurSystem;

import java.util.ArrayList;
import java.util.List;

public class GuestBusinessController {
    private Guest guest;

    public GuestBusinessController(){
        this.guest=new Guest();
        OurSystem.addGuest(guest);
    }

    //region Getters&&Setters
    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    //endregion



    //region Transition methods for version 3
    public List<Object> LogIn(String UserName, String Password) {
        List<Object> list=new ArrayList<>();
        try {
            Account account=guest.LogIn(UserName,Password);
            OurSystem.addAccount(account);
            return OurSystem.makeControllersByRoles(account);
        } catch (Exception e) { list.add(e.getMessage()); }
        return list;
    }

    public String SignIn(String name, int age, String UserName, String Password){
        try {
            guest.SignIn(name,age,UserName,Password);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }
    //endregion

    //region Transition methods for other UC
    public void ShowInfo(String InfoAbout){
        guest.ShowInfo(InfoAbout);
    }

    public void Search(String criterion, String query){
        guest.Search(criterion,query);
    }

    public void  Filter(String category, String roleFilter){
        guest.Filter(category,roleFilter);
    }
    //endregion

}
