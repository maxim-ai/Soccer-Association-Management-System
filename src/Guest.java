/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/



// line 2 "model.ump"
// line 164 "model.ump"
public class Guest
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Guest Attributes
  private String id;

  //Guest Associations
  private System system;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Guest(String aId, System aSystem)
  {
    id = aId;
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create guest due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public System getSystem()
  {
    return system;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSystem(System aSystem)
  {
    boolean wasSet = false;
    if (aSystem == null)
    {
      return wasSet;
    }

    System existingSystem = system;
    system = aSystem;
    if (existingSystem != null && !existingSystem.equals(aSystem))
    {
      existingSystem.removeGuest(this);
    }
    system.addGuest(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    System placeholderSystem = system;
    this.system = null;
    if(placeholderSystem != null)
    {
      placeholderSystem.removeGuest(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "id" + ":" + getId()+ "]";
  }
}