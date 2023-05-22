package view;
//
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class OverviewSelectionTab extends BorderPane {

    private TextArea profile;
    private Button saveButton;

    public OverviewSelectionTab() {
        profile = new TextArea();
        profile.setEditable(false);
        this.setPadding(new Insets(40));

        saveButton = new Button("Save Profile");
        BorderPane.setMargin(profile, new Insets(20));
        BorderPane.setAlignment(saveButton, Pos.CENTER);

        this.setCenter(profile);
        this.setBottom(saveButton);
    }

    public void addSaveProfileHandler(EventHandler<ActionEvent> handler) {
    	saveButton.setOnAction(handler);
    }

    public TextArea getProfile() {
        return profile;
    }
}