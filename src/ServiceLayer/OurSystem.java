package ServiceLayer;
import BusinessLayer.DataController;
import BusinessLayer.Logger.Logger;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.RoleCrudOperations.*;
import ServiceLayer.GuestController.GuestController;
import ServiceLayer.RoleController.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OurSystem {

    private static SystemManager SM;

    private static List<Guest> currGuests;
    private static List<Account> currAccounts;

    public OurSystem(){
        currGuests=new ArrayList<>();
        currAccounts=new ArrayList<>();
    }

//    public static ArrayList<String> getOptions(String substring) {
//        ArrayList<String> strings=new ArrayList<>();
//        if(substring.equals("League"))
//        {
//            strings.add("LeagueOne");
//            strings.add("LeagueTwo");
//            strings.add("LeagueThree");
//            strings.add("LeagueFour");
//        }
//        else if(substring.equals("Season"))
//        {
//            strings.add("SeasonOne");
//            strings.add("SeasonTwo");
//            strings.add("SeasonThree");
//            strings.add("SeasonFour");
//        }
//
//        return strings;
//    }

    //region Initialize the System


    public void Initialize() {
        System.out.println("Established connection to Accounty System");
        System.out.println("Established connection to Federal Tax System");

        if (!(new File("firstInitCheck").exists())) {
            Account SMaccount=new Account("Nadav",26,"NadavX","1234");
            SM=new SystemManager(SMaccount.getName());
            SMaccount.addRole(SM);
            DataController.addAccount(SMaccount);
            (Logger.getInstance()).writeNewLine("System has been initialized");
        } else {
            loadData();
            SM= DataController.getSystemManagersFromAccounts().get(0);
        }

    }
    private  void loadData(){
        try {
            FileInputStream fi = new FileInputStream(new File("AccountsData.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            DataController.setAccounts((List<Account>)oi.readObject());

            fi=new FileInputStream(new File("TeamsData.txt"));
            oi = new ObjectInputStream(fi);
            DataController.setTeams((List<Team>)oi.readObject());

            fi=new FileInputStream(new File("LeaguesData.txt"));
            oi = new ObjectInputStream(fi);
            DataController.setLeagues((List<League>)oi.readObject());

            fi=new FileInputStream(new File("SeasonsData.txt"));
            oi = new ObjectInputStream(fi);
            DataController.setSeasons((List<Season>)oi.readObject());

            fi=new FileInputStream(new File("StadiumsData.txt"));
            oi = new ObjectInputStream(fi);
            DataController.setStadiums((List<Stadium>)oi.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion



    //region Log off the System
    public  void logOffSystem(){
        saveData("Teams");
        saveData("Accounts");
        saveData("Leagues");
        saveData("Seasons");
        saveData("Stadiums");
        DataController.clearDataBase();
        File logOnCheckFile = new File("firstInitCheck");
        try {
            if(!logOnCheckFile.exists())
                logOnCheckFile.createNewFile();
        } catch (IOException e) { }

    }
    private  void saveData(String dataAbout)  {
        try {
            FileOutputStream f = new FileOutputStream(new File(dataAbout+"Data.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            if(dataAbout.equals("Accounts"))
                o.writeObject(DataController.getAccounts());
            else if(dataAbout.equals("Teams"))
                o.writeObject(DataController.getTeams());
            else if(dataAbout.equals("Leagues"))
                o.writeObject(DataController.getLeagues());
            else if(dataAbout.equals("Seasons"))
                o.writeObject(DataController.getSeasons());
            else if(dataAbout.equals("Stadiums"))
                o.writeObject(DataController.getStadiums());
            o.flush();
            o.close();
            f.flush();
            f.close();
        } catch (IOException e) { }
    }
    //endregion


    //region Getters&Setters Method
    public static SystemManager getSM() {
        return SM;
    }


    public static void setSM(SystemManager SM) {
        OurSystem.SM = SM;
        (Logger.getInstance()).writeNewLine("New system manager "+SM.getName()+" has been set");
    }


    public static List<Guest> getCurrGuests() {
        return currGuests;
    }

    public static List<Account> getCurrAccounts() {
        return currAccounts;
    }

    public static void removeGuest(Guest guest){
        for(Guest currGuest:currGuests){
            if(currGuest.equals(guest)){
                currGuests.remove(guest);
                break;
            }
        }
    }

    public static void removeAccount(Account account){
        for(Account currAccount:currAccounts){
            if(currAccount.equals(account)){
                currAccounts.remove(account);
                break;
            }
        }
    }

    public static void addGuest(Guest guest){
        currGuests.add(guest);
    }

    public static void addAccount(Account account){
        currAccounts.add(account);
    }
    //endregion

    //Need to be Changed//
    public static List<Object> makeControllersByRoles(Account account){

        List<Object> controllerList=new ArrayList<>();
        for(Role role:account.getRoles()){
            if(role instanceof Owner)
                controllerList.add(new OwnerController((Owner)role));
            else if(role instanceof TeamManager)
                controllerList.add(new TeamManagerController((TeamManager)role));
            else if(role instanceof AssociationRepresentative)
                controllerList.add(new AssociationRepresentativeController((AssociationRepresentative)role));
            else if(role instanceof SystemManager)
                controllerList.add(new SystemManagerController((SystemManager)role));
            else if(role instanceof Player)
                controllerList.add(new PlayerController((Player)role));
            else if(role instanceof Referee)
                controllerList.add(new RefereeController((Referee)role));
            else if(role instanceof Coach)
                controllerList.add(new CoachController((Coach)role));
            else if(role instanceof Fan)
                controllerList.add(new FanContoller((Fan)role));
        }

        return controllerList;

    }

    public static GuestController makeGuestController(){
        return new GuestController();
    }

    public static void notifyOtherRole(String notification, Role role){
        Alert alert=new Alert(notification);
        role.addAlert(alert);
    }

    public static List<String> getDropList(String string,List<Object> controllers,List<String> arguments){
        List<String> list=new ArrayList<>();
        if(string.equals("Team")){
            List<Team> teams= DataController.getTeams();
            for(Team team:teams)
                list.add(team.getName());
        }
        else if(string.equals("Season")){
            List<Season> seasons=DataController.getSeasons();
            for(Season season:seasons)
                list.add(season.getName());
        }
        else if(string.equals("League")){
            List<League> leagues=DataController.getLeagues();
            for(League league:leagues)
                list.add(league.getName());
        }
        else if(string.equals("Stadium")){
            List<Stadium> stadiums=DataController.getStadiums();
            for(Stadium stadium:stadiums)
                list.add(stadium.getName());
        }
        else if(string.equals("EventEnum")){
            for(EventEnum eventEnum:EventEnum.values())
                list.add(eventEnum.toString());
        }
        else if(string.equals("Match")){
            list=((RefereeController)controllers.get(0)).getMatchList();
        }
        else if(string.equals("GameEvent")){
            list=((RefereeController)controllers.get(0)).getEvantsByMatch(arguments.get(0));
        }
        else if(string.equals("Referee")){
            List<Referee> refs=DataController.getRefereesFromAccounts();
            for(Referee ref:refs)
                list.add(ref.getName());
        }
        else if(string.equals("Account")){
            List<Account> accounts=DataController.getAccounts();
            for(Account account:accounts)
                list.add(account.getUserName());
        }

        return list;

    }
}
