/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/



// line 153 "model.ump"
// line 307 "model.ump"
public class Policy
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Policy Attributes
  private String pointCalc;
  private String gameSchedual;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Policy(String aPointCalc, String aGameSchedual)
  {
    pointCalc = aPointCalc;
    gameSchedual = aGameSchedual;
  }

  //------------------------
  // INTERFACE
  //------------------------

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