public class OurSystem {

    private static SystemManager SM;
    private static AssiciationRepresentive AR;

    public  void Initialize() {
        System.out.println("Established connection to Accounty System");
        System.out.println("Established connection to Federal Tax System");
        Account SMaccount=new Account("Maxim",26,"Maxim","1234");
        SM=new SystemManager(SMaccount.getName());
        SMaccount.addRole(SM);
        DataManager.addAccount(SMaccount);

        Account ARaccount=new Account("Sean",24,"Sean","4321");
        AR=new AssiciationRepresentive(ARaccount.getName());
        ARaccount.addRole(AR);
        DataManager.addAccount(ARaccount);
    }

    public static SystemManager getSM() {
        return SM;
    }

    public static AssiciationRepresentive getAR() {
        return AR;
    }
}
