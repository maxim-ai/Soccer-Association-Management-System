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

    //region Initialize the System


    public void Initialize() {
        System.out.println("Established connection to Accounty System");
        System.out.println("Established connection to Federal Tax System");

        if (!(new File("firstInitCheck").exists())) {
            Account SMaccount=new Account("Nadav",26,"NadavX","1234");
            SM=new SystemManager(SMaccount.getName());
            SMaccount.addRole(SM);
            DataManager.addAccount(SMaccount);
            (Logger.getInstance()).writeNewLine("System has been initialized");
        } else {
            loadData();
            SM=DataManager.getSystemManagersFromAccounts().get(0);
        }

    }
    private  void loadData(){
        try {
            FileInputStream fi = new FileInputStream(new File("AccountsData.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            DataManager.setAccounts((List<Account>)oi.readObject());

            fi=new FileInputStream(new File("TeamsData.txt"));
            oi = new ObjectInputStream(fi);
            DataManager.setTeams((List<Team>)oi.readObject());

            fi=new FileInputStream(new File("LeaguesData.txt"));
            oi = new ObjectInputStream(fi);
            DataManager.setLeagues((List<League>)oi.readObject());

            fi=new FileInputStream(new File("SeasonsData.txt"));
            oi = new ObjectInputStream(fi);
            DataManager.setSeasons((List<Season>)oi.readObject());

            fi=new FileInputStream(new File("StadiumsData.txt"));
            oi = new ObjectInputStream(fi);
            DataManager.setStadiums((List<Stadium>)oi.readObject());
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
        DataManager.clearDataBase();
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
                o.writeObject(DataManager.getAccounts());
            else if(dataAbout.equals("Teams"))
                o.writeObject(DataManager.getTeams());
            else if(dataAbout.equals("Leagues"))
                o.writeObject(DataManager.getLeagues());
            else if(dataAbout.equals("Seasons"))
                o.writeObject(DataManager.getSeasons());
            else if(dataAbout.equals("Stadiums"))
                o.writeObject(DataManager.getStadiums());
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
}
