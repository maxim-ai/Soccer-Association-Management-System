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
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

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
//    public Button alertButton;

    GuiMediator guiMediator;

    public void chooseLogin()
    {
        Notifications notificationBuilder=Notifications.create()
                .title("complete")
                .text("bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla ")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_CENTER);
        notificationBuilder.showInformation();
        starter_pane.setVisible(false);
        login_pane.setVisible(true);
    }

    public void logIn() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String userName=username_input.getText();
        String password=password_input.getText();

        String result=guiMediator.getUserControllers(userName,password);
        if(!result.equals("true"))
        {
            showAlert(result, Alert.AlertType.WARNING);
        }
        else
        {
            login_pane.setVisible(false);
            options_pane.setVisible(true);
            createOptionsForUser();
            setAlerts();
        }
    }

    public void createOptionsForUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        List<String> options=guiMediator.createOptionsForUser();
        ObservableList<String> details = FXCollections.observableArrayList(options);
        col_options.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        options_table.setItems(details);

    }

    public void executeOption() throws InvocationTargetException, IllegalAccessException {
        String choice=options_table.getSelectionModel().getSelectedItem();
        List<String> arguments=new ArrayList<>();
        Pair<Method, List<String>> methodPair=guiMediator.getUserChoice(choice);
        for(String arg:methodPair.getValue())
        {
            Dialog dialog;
            if(arg.contains("@"))
            {
                List<String> choices = guiMediator.getDropDownList(arg,arguments);
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
        String answer= guiMediator.executeMethod(methodPair,arguments);
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
        guiMediator=new GuiMediator();

    }


    public void showRunQueryButton()
    {
        runMethod_button.setVisible(true);
    }


    public void setAlerts() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> alerts=guiMediator.getAllAlerts();


        if(alerts.size()>0)
        {

//            List<Label> labels=new ArrayList<>();
//            for(String alert:alerts)
//            {
//                labels.add(new Label(alert));
//            }
//            VBox vBox=new VBox();
//            vBox.getChildren().addAll(labels.get(0));
//            PopOver popOver = new PopOver(vBox);
//            alertButton.setOnMouseEntered(mouseEvent -> {
//                //Show PopOver when mouse enters label
//                popOver.show(alertButton);
//            });


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