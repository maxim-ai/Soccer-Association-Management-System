package PresentationLayer;

import BusinessLayer.DataController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.*;
import ServiceLayer.GuestController.GuestController;
import ServiceLayer.OurSystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

//import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.ResourceBundle;


public class FXcontroller implements Initializable {

    @FXML
    public AnchorPane starter_pane;
    public AnchorPane login_pane;
    public AnchorPane options_pane;

    public TableView<String> options_table;
    public TextField username_input;
    public TextField password_input;
    public Button login_button;
    public Button submit_button;
    public TableColumn<String,String> col_options;
    public Button runMethod_button;
    public TableView<String> alerts_table;
    public TableColumn<String,String> col_alerts;


    OurSystem ourSystem;
    List controllers=new ArrayList();
    HashMap<String, Pair<Method,List<String>>> functionsToUser=new HashMap<>(); //key: name of option for user
                                                                                //value: pairKey:actual method
                                                                                //       pairValue: list of args for method

    public void chooseLogin()
    {
        starter_pane.setVisible(false);
        login_pane.setVisible(true);
    }

    public void logIn() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String userName=username_input.getText();
        String password=password_input.getText();
        GuestController guestController= OurSystem.makeGuestController();
        ArrayList<Object> controllerList= (ArrayList<Object>) guestController.LogIn(userName,password);
        if(controllerList.size()==1 && controllerList.get(0) instanceof String)
        {
            showAlert((String)controllerList.get(0), Alert.AlertType.WARNING);
            return;
        }
        controllers=controllerList;
        login_pane.setVisible(false);
        options_pane.setVisible(true);
        createOptionsForUser();
        setAlerts();
    }

    public void createOptionsForUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List options=new ArrayList();
        for(Object o:controllers)
        {
            Method method=o.getClass().getDeclaredMethod("showUserMethods");
            HashMap<String, Pair<Method,List<String>>> returnedMap= (HashMap<String, Pair<Method, List<String>>>) method.invoke(o);
            for(Map.Entry<String, Pair<Method,List<String>>> entry:returnedMap.entrySet())
            {
                functionsToUser.put(entry.getKey(),entry.getValue());
                options.add(entry.getKey());
            }
            //options= (List) ((HashMap<String, Pair<Method,List<String>>>)method.invoke(o)).keySet();
        }

        ObservableList<String> details = FXCollections.observableArrayList(options);
        col_options.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        options_table.setItems(details);

    }

    public void executeOption() throws InvocationTargetException, IllegalAccessException {
        String choice=options_table.getSelectionModel().getSelectedItem();
        List<String> arguments=new ArrayList<>();
        Pair<Method, List<String>> methodPair=functionsToUser.get(choice);
        for(String arg:methodPair.getValue())
        {
            Dialog dialog;
            if(arg.contains("@"))
            {
                List<String> choices = new ArrayList<>();
                List<String> dropDownFunc = OurSystem.getDropList(arg.substring(arg.indexOf("@")+1),controllers,arguments);
                for(String pick:dropDownFunc)
                {
                    choices.add(pick);
                }
                dialog=new ChoiceDialog<String>("", choices);
                dialog.setHeaderText("list Input");
                dialog.setContentText(arg.substring(0,arg.indexOf("@"))+":");
            }
            else
            {
                dialog = new TextInputDialog("");
                dialog.setHeaderText("Text Input");
                dialog.setContentText(arg+":");
            }
            dialog.setTitle("");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                arguments.addAll(Collections.singleton(result.get()));
            }
            else
            {
                showAlert("Operation Cancelled", Alert.AlertType.INFORMATION);
                return;
            }
        }
        String answer= (String) methodPair.getKey().invoke(getControllerByMethod(methodPair.getKey()),arguments.toArray());
        showAlert(answer, Alert.AlertType.INFORMATION);
    }





    /**
     * The function shows an alert with the given text
     * @param context
     */
    public void showAlert(String context, Alert.AlertType type)
    {
        Alert a=new Alert(type);
        a.setContentText(context);
        a.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ourSystem=new OurSystem();
        ourSystem.Initialize();

    }

    private Object getControllerByMethod(Method argM){
        for(Object o:controllers){
            for(Method M:o.getClass().getDeclaredMethods()){
                if(argM.getName().equals(M.getName()))
                    return o;
            }
        }
        return null;
    }

    public void showRunQueryButton()
    {
        runMethod_button.setVisible(true);
    }


    public void setAlerts() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> alerts=new ArrayList();
        for(Object o:controllers)
        {
            Method method= null;
            try {
                method = o.getClass().getDeclaredMethod("getAlerts");
            }
            catch (NoSuchMethodException e) {
                continue;
            }
            alerts.addAll((List<String>) method.invoke(o));
        }
        if(alerts.size()>0)
        {
            alerts_table.setVisible(true);
            ObservableList<String> details = FXCollections.observableArrayList(alerts);
            col_alerts.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
            alerts_table.setItems(details);
        }
        else
        {
            alerts_table.setVisible(false);
        }

    }

    public void logout()
    {
        options_pane.setVisible(false);
        login_pane.setVisible(true);
        username_input.setText("");
        password_input.setText("");
    }

}
