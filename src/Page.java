
import java.util.*;

public class Page
{

  private Pageable type;
  private List<Fan> fans;

  public Page(Pageable pageable)
  {
    type = pageable;
    fans = new ArrayList<Fan>();
  }

  public Pageable getType() {
    return type;
  }

  public void setType(Pageable type) {
    this.type = type;
  }

  public Fan getFan(int index)
  {
    Fan aFan = fans.get(index);
    return aFan;
  }
  public List<Fan> getFans()
  {
    List<Fan> newFans = Collections.unmodifiableList(fans);
    return newFans;
  }

  public int numberOfFans()
  {
    int number = fans.size();
    return number;
  }

  public boolean hasFans()
  {
    boolean has = fans.size() > 0;
    return has;
  }

  public int indexOfFan(Fan aFan)
  {
    int index = fans.indexOf(aFan);
    return index;
  }

  public static int minimumNumberOfFans()
  {
    return 0;
  }

  public boolean addFan(Fan aFan)
  {
    boolean wasAdded = true;
    if (fans.contains(aFan)) { return true; }
    fans.add(aFan);
    if (aFan.indexOfPage(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aFan.addPage(this);
      if (!wasAdded)
      {
        fans.remove(aFan);
      }
    }
    return wasAdded;
  }

  public boolean removeFan(Fan aFan)
  {
    boolean wasRemoved = true;
    if (!fans.contains(aFan))
    {
      return wasRemoved;
    }

    int oldIndex = fans.indexOf(aFan);
    fans.remove(oldIndex);
    if (aFan.indexOfPage(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aFan.removePage(this);
      if (!wasRemoved)
      {
        fans.add(oldIndex,aFan);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<Fan> copyOfFans = new ArrayList<Fan>(fans);
    fans.clear();
    for(Fan aFan : copyOfFans)
    {
      aFan.removePage(this);
    }
    type.removePage();
  }

  public void notifyTrackingFans(Alert alert){
    for(Fan fan:fans){
      if(fan.isTrackPersonalPages()){
        fan.addPageAlert(alert);
      }
    }
  }

}