package Server.BusinessLayer.OutsideSystems;

public class ProxyTaxSystem implements TaxSystem {
    private TaxSystem taxSystem = new RealTaxSystem();

    private ProxyTaxSystem(){}

    private static ProxyTaxSystem proxyTaxSystem=null;

    public static ProxyTaxSystem getInstance(){
        if(proxyTaxSystem==null)
            proxyTaxSystem=new ProxyTaxSystem();
        return proxyTaxSystem;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        if(revenueAmount>0) {
            return taxSystem.getTaxRate(revenueAmount);
        }
        return -1;
    }
}
