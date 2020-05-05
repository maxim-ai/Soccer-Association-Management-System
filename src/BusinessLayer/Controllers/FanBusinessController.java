package BusinessLayer.Controllers;

import BusinessLayer.OtherCrudOperations.Account;
import BusinessLayer.OtherCrudOperations.Alert;
import BusinessLayer.RoleCrudOperations.Fan;
import BusinessLayer.RoleCrudOperations.Role;
import ServiceLayer.OurSystem;

import java.util.ArrayList;
import java.util.List;

public class FanBusinessController {

    private Fan fan;

    public FanBusinessController(Fan fan){
        this.fan=fan;
    }

    //region Getters&&Setters
    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }
    //endregion

    //region Transition methods for version 3
    public void LogOut(){
        fan.Logout();
        for(Account account: OurSystem.getCurrAccounts()){
            for(Role role:account.getRoles()){
                if(role.equals(fan)){
                    OurSystem.removeAccount(account);
                    return;
                }
            }
        }
    }

    public String EditPersonalInfo(String newName,String newUserName,String newPassword){
        try {
            fan.EditPersonalInfo(newName,newUserName,newPassword);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }
    //endregion

    //region Transition methods for other UC
    public void ShowInfo(String InfoAbout){
        fan.ShowInfo(InfoAbout);
    }

    public void Search(String criterion, String query){
        fan.Search(criterion,query);
    }

    public void  Filter(String category, String roleFilter){
        fan.Filter(category,roleFilter);
    }



    public void SubscribeTrackPersonalPages(){
        fan.SubscribeTrackPersonalPages();
    }

    public void SubscribeGetMatchNotifications(){fan.SubscribeGetMatchNotifications();}

    public boolean Report(String report){
        if(report.length()==0)
            return false;
        fan.Report(report);
        return true;
    }

    public String ShowSearchHistory() throws Exception {
        try {
            fan.ShowSearchHistory();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    public void ShowPersonalInfo(){
        fan.ShowPersonalInfo();
    }
	
	public List<String> getAlerts()
	{
        List<Alert> alerts=fan.getAlertList();
        List<String> strings=new ArrayList<>();
        for(Alert alert:alerts)
        {
            strings.add(alert.getDescription());
        }
        return strings;
    }


    //endregion
}
