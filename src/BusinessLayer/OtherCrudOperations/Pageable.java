package BusinessLayer.OtherCrudOperations;
import BusinessLayer.Pages.Page;

public interface Pageable {

    public void removePage();
    public Page getPage();
    public void setPage(Page page);

}
