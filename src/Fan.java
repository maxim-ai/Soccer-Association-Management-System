import java.util.*;

public class Fan extends Role
{
  private List<Page> pages;

  public Fan(String aName)
  {
    super(aName);
    pages = new ArrayList<Page>();
  }

  public Page getPage(int index)
  {
    Page aPage = pages.get(index);
    return aPage;
  }

  public List<Page> getPages()
  {
    List<Page> newPages = Collections.unmodifiableList(pages);
    return newPages;
  }

  public int numberOfPages()
  {
    int number = pages.size();
    return number;
  }

  public boolean hasPages()
  {
    boolean has = pages.size() > 0;
    return has;
  }

  public int indexOfPage(Page aPage)
  {
    int index = pages.indexOf(aPage);
    return index;
  }
  public static int minimumNumberOfPages()
  {
    return 0;
  }
  public boolean addPage(Page aPage)
  {
    boolean wasAdded = false;
    if (pages.contains(aPage)) { return false; }
    pages.add(aPage);
    if (aPage.indexOfFan(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPage.addFan(this);
      if (!wasAdded)
      {
        pages.remove(aPage);
      }
    }
    return wasAdded;
  }
  public boolean removePage(Page aPage)
  {
    boolean wasRemoved = false;
    if (!pages.contains(aPage))
    {
      return wasRemoved;
    }

    int oldIndex = pages.indexOf(aPage);
    pages.remove(oldIndex);
    if (aPage.indexOfFan(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPage.removeFan(this);
      if (!wasRemoved)
      {
        pages.add(oldIndex,aPage);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<Page> copyOfPages = new ArrayList<Page>(pages);
    pages.clear();
    for(Page aPage : copyOfPages)
    {
      aPage.removeFan(this);
    }
  }

}