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

  /**
   * returns the number of roles the account has
   */
  public int numberOfRoles()
  {
    int number = roles.size();
    return number;
  }

  /**
   * checks if the account has any roles
   */
  public boolean hasRoles()
  {
    boolean has = roles.size() > 0;
    return has;
  }

  /**
   * returns the index of the Role in the list
   */
  public int indexOfRole(Role aRole)
  {
    int index = roles.indexOf(aRole);
    return index;
  }

  /**
   * returns the minimus number of roles that an account can have
   */
  public static int minimumNumberOfRoles()
  {
    return 1;
  }

  /**
   * adds a role to the account
   * @return
   */
  public boolean addRole(Role aRole)
  {
    boolean wasAdded = true;
    if (roles.contains(aRole)) { return true; }
    roles.add(aRole);
    return wasAdded;
  }

  /**
   * remove a role from an acount
   */
  public boolean removeRole(Role aRole)
  {
    boolean wasRemoved = true;
    if (!roles.contains(aRole))
    {
      return wasRemoved;
    }
    roles.remove(aRole);
    return wasRemoved;
  }

  /**
   * removes all the roles from the account
   */
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