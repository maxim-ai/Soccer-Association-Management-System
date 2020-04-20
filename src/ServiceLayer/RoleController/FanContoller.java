package ServiceLayer.RoleController;
import BusinessLayer.OtherCrudOperations.Account;
import BusinessLayer.RoleCrudOperations.Fan;
import BusinessLayer.RoleCrudOperations.Role;
import ServiceLayer.OurSystem;

public class FanContoller {
    private Fan fan;

    public FanContoller(Fan fan){
        this.fan=fan;
    }

    //region Getters&Setters
    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }
    //endregion

    //region Transition methods
    public boolean ShowInfo(String InfoAbout){
        if(InfoAbout.length()==0) return false;
        if(!(InfoAbout.equals("Teams")||InfoAbout.equals("Players")||InfoAbout.equals("Coaches")||
                InfoAbout.equals("Leagues")||InfoAbout.equals("Seasons")))
            return false;
        fan.ShowInfo(InfoAbout);
        return true;
    }

    public boolean Search(String criterion, String query){
        if(criterion.length()==0||query.length()==0) return false;
        if(!(criterion.equals("Name")||criterion.equals("Category")))
            return false;
        if(criterion.equals("Category")){
            if(!(query.equals("Teams")||query.equals("Accounts")||query.equals("Leagues")||query.equals("Seasons")))
                return false;
        }
        fan.Search(criterion,query);
        return true;
    }

    public boolean Filter(String category, String roleFilter){
        if(category.length()==0) return false;
        if(!(category.equals("BusinessLayer.RoleCrudOperations.Role")||category.equals("BusinessLayer.OtherCrudOperations.Team")||category.equals("BusinessLayer.OtherCrudOperations.League")||category.equals("BusinessLayer.OtherCrudOperations.Season")))
            return false;
        if(category.equals("BusinessLayer.RoleCrudOperations.Role")){
            if(!(roleFilter.equals("Players")||roleFilter.equals("Coaches")||roleFilter.equals("TeamManagers")||
                    roleFilter.equals("Owners")||roleFilter.equals("Referees")))
                return false;
        }
        fan.Filter(category,roleFilter);
        return true;
    }

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

    public void SubscribeTrackPersonalPages(){
        fan.SubscribeTrackPersonalPages();
    }

    public void SubscribeGetMatchNotifications(){fan.SubscribeGetMatchNotifications();}

    public boolean Report(String report){
        if(report.length()==0)
            return false;
        fan.Report("report");
        return true;
    }

    public boolean ShowSearchHistory(){
        return fan.ShowSearchHistory();
    }

    public void ShowPersonalInfo(){
        fan.ShowPersonalInfo();
    }

    public boolean EditPersonalInfo(String newName,String newUserName,String newPassword){
        if(newName.length()==0&&newUserName.length()==0&&newPassword.length()==0)
            return false;
        return fan.EditPersonalInfo(newName,newUserName,newPassword);
    }
    //endregion
}
