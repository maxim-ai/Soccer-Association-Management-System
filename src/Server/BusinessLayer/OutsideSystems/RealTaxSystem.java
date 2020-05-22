package Server.BusinessLayer.OutsideSystems;

public class RealTaxSystem implements TaxSystem {



    @Override
    public double getTaxRate(double revenueAmount) {
        return 0;
    }
}
