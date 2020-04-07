public class Alert
{

  private String description;

  public Alert(String aDescription)
  {
    description = aDescription;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]";
  }

  public static void notifyOtherRole(String notification,Role role){
    Alert alert=new Alert(notification);
    role.addAlert(alert);
  }
}