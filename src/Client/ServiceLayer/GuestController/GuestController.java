package Client.ServiceLayer.GuestController;
import Client.Client;
import Client.ServiceLayer.RoleController.*;
import Server.BusinessLayer.Controllers.GuestBusinessController;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GuestController {


    private GuestBusinessController guestBusinessController;


    public GuestController()
    {
        //guestBusinessController=new GuestBusinessController();
    }

    //region Getters&&Setters
//    public GuestBusinessController getGuestBusinessController() {
//        return guestBusinessController;
//    }

//    public void setGuestBusinessController(GuestBusinessController guestBusinessController) {
//        this.guestBusinessController = guestBusinessController;
//    }
    //endregion

    //region Transition methods for version 3
    public List<Object> LogIn(String UserName, String Password) {
        List<Object> list=new ArrayList<>();
        if(UserName.length()==0||Password.length()==0){
            if(UserName.length()==0)
                list.add("Username length is 0");
            if(Password.length()==0)
                list.add("Password length is 0");
            return list;
        }
        List<String> parameters = new LinkedList<>();
        parameters.add(UserName);
        parameters.add(Password);
        Client.getInstance().setUserName(UserName);
        String sendToServer = "Guest@"+Client.getUserName();
        List<String> stringControllers = (List<String>) Client.connectToServer(new Pair<>(sendToServer,new Pair<>("LogIn",parameters)));
        Client.getInstance().startListen(UserName);
        return getControllersList(stringControllers);
//        return guestBusinessController.LogIn(UserName,Password);

    }

    private List<Object> getControllersList(List<String> roles){
        List<Object> serviceControllersList = new LinkedList<>();
        for(String role:roles){
            if(role.equals("Owner"))
                serviceControllersList.add(new OwnerController());
            else if(role.equals("TeamManager"))
                serviceControllersList.add(new TeamManagerController());
            else if(role.equals("AssociationRepresentative"))
                serviceControllersList.add(new AssociationRepresentativeController());
            else if(role.equals("SystemManager"))
                serviceControllersList.add(new SystemManagerController());
            else if(role.equals("Player"))
                serviceControllersList.add(new PlayerController());
            else if(role.equals("Referee"))
                serviceControllersList.add(new RefereeController());
            else if(role.equals("Coach"))
                serviceControllersList.add(new OwnerController());
            else if(role.equals("Fan"))
                serviceControllersList.add(new FanContoller());
        }
        return serviceControllersList;
    }

    public String SignIn(String name, int age, String UserName, String Password){
        if(name.length()==0) return "Name field is empty";
        if(UserName.length()==0) return "Username field is empty";
        if(Password.length()==0) return "Password field is empty";
        for(int i=0;i<name.length();i++){
            if(!Character.isAlphabetic(name.charAt(i)))
                return "Name field can not contain symbols other than letters";
        }
        if(age<=0)
            return "Age has to be postive";
        return guestBusinessController.SignIn(name,age,UserName,Password);
    }
    //endregion

    //region Transition methods for other UC
    public String ShowInfo(String InfoAbout){
        if(InfoAbout.length()==0) return "InfoAbout field is empty";
        if(!(InfoAbout.equals("Teams")||InfoAbout.equals("Players")||InfoAbout.equals("Coaches")||
                InfoAbout.equals("Leagues")||InfoAbout.equals("Seasons")))
            return "Wrong InfoAbout field";
        guestBusinessController.ShowInfo(InfoAbout);
        return "";
    }

    public String Search(String criterion, String query){
        if(criterion.length()==0) return "Criterion field is empty";
        if(query.length()==0) return "Query length is empty";
        if(!(criterion.equals("Name")||criterion.equals("Category"))) return "Wrong criterion";
        if(criterion.equals("Category")){
            if(!(query.equals("Teams")||query.equals("Accounts")||query.equals("Leagues")||query.equals("Seasons")))
                return "Wrong query";
        }
        guestBusinessController.Search(criterion,query);
        return "";
    }

    public String Filter(String category, String roleFilter){
        if(category.length()==0) return "Catergory field is empty";
        if(!(category.equals("Role")||category.equals("Team")||category.equals("League")||category.equals("Season")))
            return "Wrong category";
        if(category.equals("Role")){
            if(!(roleFilter.equals("Players")||roleFilter.equals("Coaches")||roleFilter.equals("TeamManagers")||
                    roleFilter.equals("Owners")||roleFilter.equals("Referees")))
                return "Wrong role filter";
        }
        guestBusinessController.Filter(category,roleFilter);
        return "";
    }
    //endregion


}
