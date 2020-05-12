package BusinessLayer.Controllers;

import BusinessLayer.DataController;
import BusinessLayer.OtherCrudOperations.Account;
import BusinessLayer.OtherCrudOperations.PositionEnum;
import BusinessLayer.OtherCrudOperations.Team;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.Player;
import BusinessLayer.RoleCrudOperations.Role;
import BusinessLayer.RoleCrudOperations.TeamManager;

import java.util.Date;

public class PlayerBusinessController {
    Player player;

    public PlayerBusinessController(Player player) {
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
     * @param teamName
     */
    public String updateDetails(String birthday, String position, String teamName)
    {
        Date dateConv=convertStringToDate(birthday);
        PositionEnum positionConv=convertStringToPositionEnum(position);
        Team newTeam=new Team(teamName,null,null);
        player.updateDetails(dateConv,positionConv,newTeam);
        return "Update successful";
    }

    public void ShowPlayer() {
        player.ShowPlayer();
    }

    public void pageUpdated(){
        player.pageUpdated();
    }
    public static PositionEnum convertStringToPositionEnum(String positionEnum) {
        return PositionEnum.valueOf(positionEnum);
    }
    public static Date convertStringToDate(String date)
    {
        Date newDate=new Date();
        String seprate=date.substring(new String("Date: ").length(),date.length());
        String[]splited=seprate.split("/");
        newDate.setDate(Integer.parseInt(splited[0]));
        newDate.setYear(Integer.parseInt(splited[1]));
        newDate.setYear(Integer.parseInt(splited[2]));
        return newDate;
    }

    public static Player convertStringToPlayer(String userName){
        for (Account account : DataController.getInstance().getAccounts()){
            if(account.getUserName().equals(userName)){
                for(Role role : account.getRoles()){
                    if(role instanceof TeamManager){
                        return (Player) role;
                    }
                }
            }
        }
        return null;
    }

}
