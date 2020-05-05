package ServiceLayer.RoleController;
import BusinessLayer.Controllers.PlayerBusinessController;
import BusinessLayer.RoleCrudOperations.Player;

public class PlayerController {
    PlayerBusinessController playerBusinessController;
    public PlayerController(Player player) {
        playerBusinessController=new PlayerBusinessController(player);
    }

    public String updateDetails(String birthday, String position, String team)
    {
        return  playerBusinessController.updateDetails(birthday,position,team);
    }
}
