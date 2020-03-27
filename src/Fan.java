/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;

// line 78 "model.ump"
// line 237 "model.ump"
public class Fan extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Fan Associations
  private List<Page> pages;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Fan(String aName)
  {
    super(aName);
    pages = new ArrayList<Page>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPages()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
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
  /* Code from template association_RemoveMany */
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPageAt(Page aPage, int index)
  {  
    boolean wasAdded = false;
    if(addPage(aPage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPages()) { index = numberOfPages() - 1; }
      pages.remove(aPage);
      pages.add(index, aPage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePageAt(Page aPage, int index)
  {
    boolean wasAdded = false;
    if(pages.contains(aPage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPages()) { index = numberOfPages() - 1; }
      pages.remove(aPage);
      pages.add(index, aPage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPageAt(aPage, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Page> copyOfPages = new ArrayList<Page>(pages);
    pages.clear();
    for(Page aPage : copyOfPages)
    {
      aPage.removeFan(this);
    }
    super.delete();
  }

}