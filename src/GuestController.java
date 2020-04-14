public class GuestController {
    private Guest guest;

    public GuestController(){
        guest=new Guest();
        OurSystem.addGuest(guest);
    }

    //region Getters&Setters
    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    //endregion

    //region Transition Methods
    public boolean LogIn(String UserName,String Password){
        if(UserName.length()==0||Password.length()==0) return false;
        Account account=guest.LogIn(UserName,Password);
        if(account==null)
            return false;
        OurSystem.addAccount(account);
        OurSystem.makeControllersByRoles(account);
        return true;
    }

    public boolean SignIn(String name, int age, String UserName, String Password){
        if(name.length()==0||UserName.length()==0||Password.length()==0) return false;
        for(int i=0;i<name.length();i++){
            if(!Character.isAlphabetic(name.charAt(i)))
                return false;
        }
        if(age<=0)
            return false;
        return guest.SignIn(name,age,UserName,Password);
    }

    public boolean ShowInfo(String InfoAbout){
        if(InfoAbout.length()==0) return false;
        if(!(InfoAbout.equals("Teams")||InfoAbout.equals("Players")||InfoAbout.equals("Coaches")||
                InfoAbout.equals("Leagues")||InfoAbout.equals("Seasons")))
            return false;
        guest.ShowInfo(InfoAbout);
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
        guest.Search(criterion,query);
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
        guest.Filter(category,roleFilter);
        return true;
    }
    //endregion


}