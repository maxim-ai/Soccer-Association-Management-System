public class Guest {

  private String id;

  public Guest(String aId)
  {
    id = aId;
  }
<<<<<<< HEAD

=======
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
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