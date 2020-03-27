import java.util.*;


public class Account{

  private String userName;
  private String password;
  private List<Role> roles;


  public Account(String aUserName, String aPassword)
  {
    userName = aUserName;
    password = aPassword;
    roles = new ArrayList<Role>();
  }

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */

  public Role getRole(int index)
  {
    Role aRole = roles.get(index);
    return aRole;
  }

  public List<Role> getRoles()
  {
    List<Role> newRoles = Collections.unmodifiableList(roles);
    return newRoles;
  }

  public int numberOfRoles()
  {
    int number = roles.size();
    return number;
  }

  public boolean hasRoles()
  {
    boolean has = roles.size() > 0;
    return has;
  }

  public int indexOfRole(Role aRole)
  {
    int index = roles.indexOf(aRole);
    return index;
  }

  public static int minimumNumberOfRoles()
  {
    return 1;
  }

  public boolean addRole(Role aRole)
  {
    boolean wasAdded = false;
    if (roles.contains(aRole)) { return false; }
    roles.add(aRole);
    if (aRole.indexOfAccount(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aRole.addAccount(this);
      if (!wasAdded)
      {
        roles.remove(aRole);
      }
    }
    return wasAdded;
  }

  public boolean removeRole(Role aRole)
  {
    boolean wasRemoved = false;
    if (!roles.contains(aRole))
    {
      return wasRemoved;
    }

    int oldIndex = roles.indexOf(aRole);
    roles.remove(oldIndex);
    if (aRole.indexOfAccount(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aRole.removeAccount(this);
      if (!wasRemoved)
      {
        roles.add(oldIndex,aRole);
      }
    }
    return wasRemoved;
  }


  public void emptyRoles()
  {
    roles.clear();
  }

  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "password" + ":" + getPassword()+ "]";
  }

}