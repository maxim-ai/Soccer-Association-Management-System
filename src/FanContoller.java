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

    //region Transition Methods
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
            if(!(criterion.equals("Teams")||criterion.equals("Accounts")||criterion.equals("Leagues")||criterion.equals("Seasons")))
                return false;
        }
        fan.Search(criterion,query);
        return true;
    }

    public boolean Filter(String category, String roleFilter){
        if(category.length()==0||roleFilter.length()==0) return false;
        if(!(category.equals("Role")||category.equals("Team")||category.equals("League")||category.equals("Season")))
            return false;
        if(category.equals("Role")){
            if(!(roleFilter.equals("Players")||roleFilter.equals("Coaches")||roleFilter.equals("TeamManagers")||
                    roleFilter.equals("Owners")||roleFilter.equals("Referees")))
                return false;
        }
        fan.Filter(category,roleFilter);
        return true;
    }

    public void LogOut(){
        fan.Logout();
        OurSystem.removeAccount(DataManager.getAccountByRole(fan));
    }

    public void SubscribeTrackPersonalPages(){
        fan.SubscribeTrackPersonalPages();
    }

    public boolean Report(String report){
        if(report.length()==0)
            return false;
        for(int i=0;i<report.length();i++){
            if(!Character.isAlphabetic(report.charAt(i)))
                return false;
        }
        return true;
    }

    public void ShowSearchHistory(){
        fan.ShowSearchHistory();
    }

    public void ShowPersonalInfo(){
        fan.ShowPersonalInfo();
    }

    public boolean EditPersonalInfo(String newName,String newUserName,String newPassword){
        if(newName.length()==0&&newUserName.length()==0&&newPassword.length()==0)
            return false;
        fan.EditPersonalInfo(newName,newUserName,newPassword);
        return true;
    }
    //endregion
}
