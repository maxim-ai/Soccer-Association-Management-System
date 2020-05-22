package Server.BusinessLayer.OutsideSystems;

public class ProxyAssociationAccountingSystem implements AssociationAccountingSystem {
    private AssociationAccountingSystem associationAccountingSystem = new RealAssociationAccountingSystem();

    private ProxyAssociationAccountingSystem(){}

    private static ProxyAssociationAccountingSystem proxyAssociationAccountingSystem=null;

    public static ProxyAssociationAccountingSystem getInstance(){
        if(proxyAssociationAccountingSystem==null)
            proxyAssociationAccountingSystem=new ProxyAssociationAccountingSystem();
        return proxyAssociationAccountingSystem;
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if(teamName!=null && date!=null && amount>0) {
            return associationAccountingSystem.addPayment(teamName, date, amount);
        }
        return false;
    }
}
