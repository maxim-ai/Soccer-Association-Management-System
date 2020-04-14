public class Policy
{
  
  private String pointCalc;
  private String gameSchedual;
  private SLsettings sLsettings;

  public Policy(String aPointCalc, String aGameSchedual)
  {
    pointCalc = aPointCalc;
    gameSchedual = aGameSchedual;
  }

  public SLsettings getsLsettings() {
    return sLsettings;
  }

  public void setsLsettings(SLsettings sLsettings) {
    this.sLsettings = sLsettings;
  }

  public boolean setPointCalc(String aPointCalc)
  {
    boolean wasSet = false;
    pointCalc = aPointCalc;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameSchedual(String aGameSchedual)
  {
    boolean wasSet = false;
    gameSchedual = aGameSchedual;
    wasSet = true;
    return wasSet;
  }

  public String getPointCalc()
  {
    return pointCalc;
  }

  public String getGameSchedual()
  {
    return gameSchedual;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "pointCalc" + ":" + getPointCalc()+ "," +
            "gameSchedual" + ":" + getGameSchedual()+ "]";
  }
}