package ServiceLayer.RoleController;
import BusinessLayer.Controllers.CoachBusinessController;
import BusinessLayer.OtherCrudOperations.Team;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.Coach;

import java.util.List;

public class CoachController {
    CoachBusinessController coachBusinessController;
    public CoachController(Coach coach){
        coachBusinessController= new CoachBusinessController(coach);

    }
    public String updateDetails(String training,String teamRole)
    {
       return coachBusinessController.updateDetails(training,teamRole);
    }
}
