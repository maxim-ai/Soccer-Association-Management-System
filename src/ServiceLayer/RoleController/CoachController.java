package ServiceLayer.RoleController;
import BusinessLayer.OtherCrudOperations.Team;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.Coach;

import java.util.List;

public class CoachController {
    Coach coach;

    public CoachController(Coach coach) {
        this.coach = coach;
    }
    public void setPage(Page page) {
        coach.setPage(page);
    }

    public boolean setTraining(String aTraining)
    {
       return coach.setTraining(aTraining);
    }

    public boolean setTeamRole(String aTeamRole)
    {
        return coach.setTeamRole(aTeamRole);
    }

    public String getTraining()
    {
        return coach.getTraining();
    }

    public String getTeamRole()
    {
        return coach.getTeamRole();
    }

    public Team getTeam(int index)
    {
        return coach.getTeam(index);
    }

    public List<Team> getTeams()
    {
        return coach.getTeams();
    }

    /**
     * returns the number of teams that the coach coaches
     */
    public int numberOfTeams()
    {
        return coach.numberOfTeams();
    }

    /**
     * checks if the coach is coaching any team
     * @return
     */
    public boolean hasTeams()
    {
        return coach.hasTeams();
    }

    /**
     * returns the index of the BusinessLayer.OtherCrudOperations.Team in the list
     */
    public int indexOfTeam(Team aTeam)
    {
      return coach.indexOfTeam(aTeam);
    }

    public Page getPage()
    {
       return coach.getPage();
    }

    /**
     * sets the page of the coach to null
     */
    public void clear_page()
    {
        coach.clear_page();
    }

    public static int minimumNumberOfTeams()
    {
        return 0;
    }

    /**
     * adds a team to te coach's list
     */
    public boolean addTeam(Team aTeam)
    {
        return coach.addTeam(aTeam);
    }

    /**
     * removes a team from the coach
     */
    public boolean removeTeam(Team aTeam)
    {
        return coach.removeTeam(aTeam);
    }

    /**
     * deletes the coach, removes him from all his teams, deletes his page
     */
    public void delete()
    {
        coach.delete();
    }

    public String toString()
    {
        return coach.toString();
    }

    public void removePage() {
        coach.removePage();
    }

    /**
     * update coach details
     * @param training
     * @param teamRole
     */
    public void updateDetails(String training,String teamRole)
    {
        coach.updateDetails(training,teamRole);
    }

    public void ShowCoach() {
      coach.ShowCoach();
    }

    public void pageUpdated(){
      coach.pageUpdated();
    }
}
