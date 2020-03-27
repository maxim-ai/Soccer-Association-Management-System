public class Guest {

  private String id;

  public Guest(String aId)
  {
    id = aId;
  }
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

  public String toString()
  {
    return super.toString() + "["+ "id" + ":" + getId()+ "]";
  }

}