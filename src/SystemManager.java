import java.util.*;


public class SystemManager extends Role
{

  HashMap<String,String> complaintAndComments;

  public SystemManager(String aName)
  {
    super(aName);
    complaintAndComments= new HashMap<>();
  }

  public HashMap<String, String> getComplaintAndComments() {
    return complaintAndComments;
  }

  public void setComplaintAndComments(HashMap<String, String> complaintAndComments) {
    this.complaintAndComments = complaintAndComments;
  }

  /**
   * delete team for Permanently, saving all her actions, notify owner and team manger and delete team's
   * page from all fans.
   * @param team
   * @return
   */
  public boolean DeleteTeamPermanently(Team team){
    boolean wasSet = false;
    if(team == null){
      return  wasSet;
    }
    wasSet = DataManager.removeTeam(team);
    DataManager.saveAction(team);
    DataManager.notifyOnDelete(team);
    DataManager.deleteFromAllFollowers(team);
    team.delete();
    return wasSet;
  }

  /**
   * remove account from system
   * @param account
   * @return
   */
  public boolean deleteAccount(Account account){
    boolean wasSet = false;
    if (account == null){
      return  wasSet;
    }
    List <Role> roles = account.getRoles();
    boolean isOwner= false;
    boolean isSystemManger = false;
    for(Role role : roles){
      if(role instanceof Owner){
        isOwner = true;
      }
      if(role instanceof SystemManager){
        isSystemManger = true;
      }
    }
    if(isOwner){
      return false;// to find someone else who will be next owner before remove this one
    }
    if(isSystemManger){
      List<Account> accounts = DataManager.getAccounts();
      for(Account account1 : accounts){
        for(Role role : account1.getRoles()){
          if(role instanceof SystemManager && !account.equals(account1))
          {
            wasSet = DataManager.removeAccount(account);
            return  wasSet;
          }
        }
      }
    }

    wasSet = DataManager.removeAccount(account);
    return wasSet;
  }

  /**
   * show all the complaints of the accounts in the system
   */
  public void showComplaints(){
//    DataManager.displayComplaint();
  }

  /**
   * add comment to the complaint
   */
  public void addComplain(String complain){
    complaintAndComments.put(complain,null);
  }


  /**
   * add comment to the complaint
   */
  public void addComment(String comment,String acomplain){
    for(Map.Entry <String,String> complain : complaintAndComments.entrySet()){
      if(complain.getKey().equals(acomplain)){
        complaintAndComments.put(acomplain,comment);
      }
    }

  }

  /**
   * show system logger
   */
  public void showSystemLog(){

  }

  /**
   * build recommendation system
   */
  public void buildRecommendationSystem(){

  }

  /**
   * creates and account in the database
   */
  public static boolean createAccount(Account account)
  {
    if(account==null)
      return false;
    return DataManager.addAccount(account);
  }


}