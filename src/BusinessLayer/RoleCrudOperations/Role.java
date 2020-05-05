package BusinessLayer.RoleCrudOperations;
import BusinessLayer.OtherCrudOperations.Alert;

import java.io.Serializable;
import java.util.*;


public abstract class Role implements Serializable
{

  private String name;
  private String username;
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
    return "name: "+this.name;
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


  public void removeAlert(Alert alert){
    for(int i=0;i<alertList.size();i++){
      if(alertList.get(i).equals(alert))
        alertList.remove(i);
    }
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}