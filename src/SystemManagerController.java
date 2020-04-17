import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemManagerController {
    SystemManager systemManager;

    public SystemManagerController(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    public HashMap<String, String> getComplaintAndComments() {
            return systemManager.getComplaintAndComments();
    }

    public void setComplaintAndComments(HashMap<String, String> complaintAndComments) {
         setComplaintAndComments(complaintAndComments);
    }

    /**
     * delete team for Permanently, saving all her actions, notify owner and team manger and delete team's
     * page from all fans.
     */
    public boolean DeleteTeamPermanently(Team team)
    {
        return systemManager.DeleteTeamPermanently(team);
    }

    /**
     * remove account from system
     */
    public boolean deleteAccount(Account account){
      return systemManager.deleteAccount(account);
    }

    /**
     * show all the complaints of the accounts in the system
     */
    public void showComplaints(){
       systemManager.showComplaints();
    }

    /**
     * add comment to the complaint
     */
    public void addComplain(String complain){
        systemManager.addComplain(complain);
    }

    /**
     * add comment to the complaint
     */
    public void addComment(String comment,String acomplain){
        systemManager.addComment(comment,acomplain);
    }

    /**
     * show system logger
     */
    public String showSystemLog(){
        return systemManager.showSystemLog();
    }

    /**
     * build recommendation system
     */
    public void buildRecommendationSystem(){
        systemManager.buildRecommendationSystem();
    }


}
