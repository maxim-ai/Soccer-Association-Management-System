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
<<<<<<< HEAD
=======
  /* Code from template association_GetOne */
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1

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

<<<<<<< HEAD
  /**
   * returns the minimus number of roles that an account can have
   */
=======
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
  public static int minimumNumberOfRoles()
  {
    return 1;
  }

<<<<<<< HEAD
  /**
   * adds a role to the account
   * @return
   */
  public boolean addRole(Role aRole)
  {
    boolean wasAdded = true;
    if (roles.contains(aRole)) { return true; }
=======
  public boolean addRole(Role aRole)
  {
    boolean wasAdded = true;
    if (roles.contains(aRole)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    roles.add(aRole);
    return wasAdded;
  }

<<<<<<< HEAD
  /**
   * remove a role from an acount
   */
=======
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
  public boolean removeRole(Role aRole)
  {
    boolean wasRemoved = true;
    if (!roles.contains(aRole))
    {
      return wasRemoved;
    }
<<<<<<< HEAD
    roles.remove(aRole);
    return wasRemoved;
  }

  /**
   * removes all the roles from the account
   */
=======
    int oldIndex = roles.indexOf(aRole);
    roles.remove(oldIndex);
    return wasRemoved;
  }

>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
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