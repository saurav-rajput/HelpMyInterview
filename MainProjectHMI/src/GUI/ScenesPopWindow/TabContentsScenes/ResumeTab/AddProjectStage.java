package GUI.ScenesPopWindow.TabContentsScenes.ResumeTab;

import Beans.Models.User;
import Beans.Models.UserLab;
import DAO.ProjectDAO;
import GUI.Scenes.OnUserLogInScene;
import Beans.Models.Project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddProjectStage {
    private static boolean isSavePressed = false;
    public static boolean show(){
        UserLab userLab = UserLab.get();
        String userName = OnUserLogInScene.getUserName();
        User user = OnUserLogInScene.getLoggedInUser();


        Stage window = new Stage();

        GridPane projectGripPane = new GridPane();
        projectGripPane.setVgap(20);
        projectGripPane.setHgap(20);


        Label projectTitleLabel = new Label("Title : ");
        TextField projectTitleTextField = new TextField();
        projectTitleTextField.setPromptText("enter project's name");
        projectTitleTextField.setMaxWidth(300);

        Label projectLinkLabel = new Label("Project Link : ");
        TextField projectLinkTextField = new TextField();
        projectLinkTextField.setPromptText("enter projects URL");

        Label projectDescriptionLabel = new Label("Description : ");
        TextArea projectDescriptionTextArea = new TextArea();
        projectDescriptionTextArea.setPromptText("enter project description");
        projectDescriptionTextArea.setMaxWidth(300);
        projectDescriptionTextArea.setMaxHeight(60);


        CheckBox isProjectOnGoingCheckBox = new CheckBox("Currently ongoing");
        isProjectOnGoingCheckBox.setStyle("-fx-text-fill: white");


        GridPane.setConstraints(projectTitleLabel,0,0);
        GridPane.setConstraints(projectTitleTextField,1,0);
        GridPane.setConstraints(projectLinkLabel,0,1);
        GridPane.setConstraints(projectLinkTextField,1,1);
        GridPane.setConstraints(isProjectOnGoingCheckBox,1,2);
        GridPane.setConstraints(projectDescriptionLabel,0,3);
        GridPane.setConstraints(projectDescriptionTextArea,1,3);
        GridPane.setValignment(projectDescriptionLabel, VPos.TOP);

        projectGripPane.getChildren().addAll(projectTitleLabel,projectTitleTextField,projectLinkLabel,projectLinkTextField,isProjectOnGoingCheckBox,projectDescriptionLabel,projectDescriptionTextArea);

        HBox projectGridHBox = new HBox();
        projectGridHBox.getChildren().add(projectGripPane);
        projectGridHBox.setAlignment(Pos.CENTER);
        projectGridHBox.setPadding(new Insets(30,10,10,10));

        Button saveButton = new Button("Save!");
        DataAdditionValidation.validate(projectTitleTextField,projectLinkTextField,projectDescriptionTextArea,saveButton);


        saveButton.setOnAction(event -> {
            Project project = new Project(projectTitleTextField.getText(),
                    projectLinkTextField.getText(),
                    isProjectOnGoingCheckBox.isSelected(),
                    projectDescriptionTextArea.getText());
            isSavePressed = true;
            user.getProjectArrayList().add(project);
            ProjectDAO.getProjectDAOInstance().insertProject(project,userName);
            window.close();
        });


        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(projectGridHBox,saveButton);



        Scene scene = new Scene(root,500,370);
        scene.getStylesheets().add("GUI/Themes/Nevike.css");
        window.setScene(scene);
        window.setTitle("Projects");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.showAndWait();

        return isSavePressed;
    }
}
