package Server.BusinessLayer.RoleCrudOperations;
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.Alert;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;


public abstract class Role extends Observable implements Serializable
{

  private String name;
  private String username;
  private List<Alert> alertList;

  public Role(){
  }

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
//    return alertList;
    return DataController.getInstance().getAlertsForAccount(username);
  }

  public void setAlertList(List<Alert> alertList) {
    this.alertList = alertList;
  }

  public void addAlert(Alert alert){
    alertList.add(alert);
  }
  public void clearAlerts(){
    DataController.getInstance().clearAlertsForAccount(username);
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

  public void notifyAccount(String userName, String notification)
  {
    if(DataController.getInstance().isAccountloggedIn(userName))
    {
      Pair<String,String> massage=new Pair<>(userName,notification);
      notifyObservers(massage);
    }
    else
    {
      DataController.getInstance().addAlertToAccount(userName,notification);
    }
  }
}