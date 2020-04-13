import java.util.Date;

public class PlayerController {
    Player player;

    public PlayerController(Player player) {
        this.player = player;
    }
    public boolean setBirthday(Date aBirthday)
    {
        return player.setBirthday(aBirthday);
    }

    public boolean setPosition(PositionEnum aPosition)
    {
       return player.setPosition(aPosition);
    }

    public Date getBirthday()
    {
        return player.getBirthday();
    }

    public PositionEnum getPosition()
    {
        return player.getPosition();
    }

    public Team getTeam()
    {
        return player.getTeam();
    }

    public Page getPage()
    {
        return player.getPage();
    }

    public boolean setTeam(Team aTeam)
    {
        return player.setTeam(aTeam);
    }

    public void delete()
    {
        player.delete();
    }


    public String toString()
    {
        return player.toString();
    }

    public void removePage() {
        player.removePage();
    }

    public void setPage(Page page) {
        player.setPage(page);
    }

    /**
     * update player details
     * @param birthday
     * @param position
     * @param team
     */
    public void updateDetails(Date birthday, PositionEnum position,Team team)
    {
        player.updateDetails(birthday,position,team);
    }

    public void ShowPlayer() {
        player.ShowPlayer();
    }

    public void pageUpdated(){
        player.pageUpdated();
    }
}
