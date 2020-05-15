package Server.BusinessLayer.Controllers;

import Server.BusinessLayer.OtherCrudOperations.Account;
import Server.BusinessLayer.OtherCrudOperations.Guest;
import Client.ServiceLayer.OurSystem;
import Server.BusinessLayer.RoleCrudOperations.*;

import java.util.ArrayList;
import java.util.List;

public class GuestBusinessController {
    private Guest guest;

    public GuestBusinessController(){
        this.guest=new Guest();
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
    public List<String> LogIn(String UserName, String Password) {
        //List<String> list=new ArrayList<>();
        try {
            Account account=guest.LogIn(UserName,Password);
            //OurSystem.addAccount(account);
            return makeControllersByRoles(account);
        } catch (Exception e) { e.printStackTrace(); }
        return null;

    }


    private List<String> makeControllersByRoles(Account account){

        List<String> controllerList=new ArrayList<>();
        for(Role role:account.getRoles()){
            if(role instanceof Owner)
                controllerList.add("Owner");
            else if(role instanceof TeamManager)
                controllerList.add("TeamManager");
            else if(role instanceof AssociationRepresentative)
                controllerList.add("AssociationRepresentative");
            else if(role instanceof SystemManager)
                controllerList.add("SystemManager");
            else if(role instanceof Player)
                controllerList.add("Player");
            else if(role instanceof Referee)
                controllerList.add("Referee");
            else if(role instanceof Coach)
                controllerList.add("Coach");
            else if(role instanceof Fan)
                controllerList.add("Fan");
        }

        return controllerList;

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
