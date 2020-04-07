
import java.util.*;


public class Role
{

  private String name;
  private List<Alert> alertList;

  public Role(String aName)
  {
    name = aName;
    alertList=new ArrayList<>();
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }

  public List<Alert> getAlertList() {
    return alertList;
  }

  public void setAlertList(List<Alert> alertList) {
    this.alertList = alertList;
  }

  public void addAlert(Alert alert){
    alertList.add(alert);
  }
  public void clearAlerts(){
    alertList=new ArrayList<>();
  }
  public void removeAlert(String s){
    for(int i=0;i<alertList.size();i++){
      if(alertList.get(i).equals(s))
        alertList.remove(i);
    }
  }

}