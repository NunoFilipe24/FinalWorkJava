package view;
//
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;
import model.Module;

import java.util.Collection;


public class SelectModulesPane extends BorderPane{
	
	private  Button term1AddButton, term1RemoveButton, term2AddButton, term2RemoveButton, resetButton, submitButton;
	private TextField textterm1Credits, textterm2Credits;
	private ListView<Module> term1Unselect, term2Unselect,term1Select,term2Select, yearSelect;
	private int term1Credits, term2Credits;
	
	public SelectModulesPane() {
		
		HBox core = new HBox();
		this.setPadding(new Insets(20));
		
		Label labelterm1Unselect = new Label("Unselected Term 1 Modules");
		Label labelterm2Unselect = new Label("Unselected Term 2 Modules");
		Label labelterm1Select = new Label("Selected Term 1 Modules");
		Label labelterm2Select = new Label("Selected Term 2 Modules");
		Label labelyearLong = new Label("Selected Year Long Modules");
		Label labelterm1Buttons = new Label("Term 1:");
		Label labelterm2Buttons = new Label("Term 2:");
		Label labelterm1Credits = new Label("Current Term 1 Credits:");
		Label labelterm2Credits = new Label("Current Term 2 Credits:");
		
		
		term1Unselect = new ListView<>();
		term2Unselect = new ListView<>();
		term1Select = new ListView<>();
		term2Select = new ListView<>();
		yearSelect = new ListView<>();
		term1AddButton = new Button("Add");
		term1RemoveButton = new Button("Remove");
		term2AddButton = new Button("Add");
		term2RemoveButton = new Button("Remove");
		resetButton = new Button("Reset");
		submitButton = new Button("Submit");
		textterm1Credits = new TextField("0");
		textterm2Credits = new TextField("0");
		
		ObservableList<Module> term1UnselectMod = FXCollections.observableArrayList();
	    ObservableList<Module> term2UnselectMod = FXCollections.observableArrayList();
	    ObservableList<Module> term1SelectMod = FXCollections.observableArrayList();
	    ObservableList<Module> term2SelectMod = FXCollections.observableArrayList();
	    ObservableList<Module> YearSelectMod = FXCollections.observableArrayList();
	    
	    textterm1Credits.setEditable(false);
	    textterm2Credits.setEditable(false);
	    term1Unselect.setItems(term1UnselectMod);
	    term2Unselect.setItems(term2UnselectMod);
	    term1Select.setItems(term1SelectMod);
	    term2Select.setItems(term2SelectMod);
	    yearSelect.setItems(YearSelectMod);
	    
	    term1Unselect.setPrefSize(350, 100);
	    term2Unselect.setPrefSize(350, 100);
	    term1Select.setPrefSize(350, 100);
	    term2Select.setPrefSize(350, 100);
        yearSelect.setPrefSize(350, 50);
        textterm1Credits.setMaxWidth(50);
        textterm2Credits.setMaxWidth(50);
        term1AddButton.setPrefWidth(70);
        term1RemoveButton.setPrefWidth(70);
        term2AddButton.setPrefWidth(70);
        term2RemoveButton.setPrefWidth(70);
        resetButton.setPrefWidth(70);
        submitButton.setPrefWidth(70);
	    
	    
	    VBox vBoxterm1Unselect = new VBox();
	    VBox vBoxterm2Unselect = new VBox();
	    VBox vBoxterm1Select = new VBox();
	    VBox vBoxterm2Select = new VBox();
	    VBox vBoxYealSelect = new VBox();
	    HBox hBoxterm1Buttons  = new HBox();
	    hBoxterm1Buttons.setSpacing(20);
        HBox hBoxterm2Buttons  = new HBox();
        hBoxterm2Buttons.setSpacing(20);
        HBox hBoxterm1Credits  = new HBox();
        hBoxterm1Credits.setSpacing(20);
        HBox hBoxterm2Credits  = new HBox();
        hBoxterm2Credits.setSpacing(20);
        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(20);

	    
        
        vBoxterm1Unselect.getChildren().addAll(labelterm1Unselect, term1Unselect );
        vBoxterm2Unselect.getChildren().addAll(labelterm2Unselect, term2Unselect );
        vBoxterm1Select.getChildren().addAll(labelterm1Select, term1Select );
        vBoxterm2Select.getChildren().addAll(labelterm2Select, term2Select );
        vBoxYealSelect.getChildren().addAll(labelyearLong, yearSelect);
        hBoxterm1Buttons.getChildren().addAll(labelterm1Buttons,term1AddButton,term1RemoveButton);
        hBoxterm2Buttons.getChildren().addAll(labelterm2Buttons,term2AddButton,term2RemoveButton);
        hBoxterm1Credits.getChildren().addAll(labelterm1Credits,textterm1Credits );
        hBoxterm2Credits.getChildren().addAll(labelterm2Credits,textterm2Credits );
		hBoxButtons.getChildren().addAll(resetButton, submitButton);
		
		hBoxterm1Buttons.setAlignment(Pos.CENTER);
		hBoxterm2Buttons.setAlignment(Pos.CENTER);
        hBoxterm1Credits.setAlignment(Pos.CENTER);
        hBoxterm2Credits.setAlignment(Pos.CENTER);
        hBoxButtons.setAlignment(Pos.CENTER);
		
        VBox.setVgrow(term1Unselect, Priority.ALWAYS);
        VBox.setVgrow(term2Unselect, Priority.ALWAYS);
        VBox.setVgrow(term1Select, Priority.ALWAYS);
        VBox.setVgrow(term2Select, Priority.ALWAYS);
        VBox.setVgrow(yearSelect, Priority.ALWAYS);
	
        
        VBox.setVgrow(vBoxterm1Unselect, Priority.ALWAYS);
        VBox.setVgrow(vBoxterm2Unselect, Priority.ALWAYS);
        VBox.setVgrow(vBoxterm1Select, Priority.ALWAYS);
        VBox.setVgrow(vBoxterm2Select, Priority.ALWAYS);
        VBox.setVgrow(vBoxYealSelect, Priority.ALWAYS);
        
        VBox FColumn = new VBox (vBoxterm1Unselect, hBoxterm1Buttons,vBoxterm2Unselect, hBoxterm2Buttons, hBoxterm1Credits );
        FColumn.setSpacing(20);
        HBox.setHgrow(FColumn, Priority.ALWAYS);
        
        VBox SColumn = new VBox (vBoxYealSelect, vBoxterm1Select,vBoxterm2Select, labelterm2Credits );
        SColumn.setSpacing(20);
        HBox.setHgrow(SColumn, Priority.ALWAYS);
        BorderPane.setAlignment(core, Pos.CENTER);
        this.setCenter(new StackPane(core));
        this.setBottom(hBoxButtons);
        
        core.getChildren().addAll(FColumn, SColumn);
        core.setSpacing(20);
        core.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	}
	public void setCourseModules(Collection<Module> module) {
		
		term1Unselect.getItems().clear();
		term2Unselect.getItems().clear();
		term1Select.getItems().clear();
		term2Select.getItems().clear();
		yearSelect.getItems().clear();
	
	
		
		 module.forEach(m -> {
	            if (m.getDelivery().equals(RunPlan.TERM_1)) {
	                if (m.isMandatory()) {
	                    term1Select.getItems().add(m);
	                } else {
	                	term1Unselect.getItems().add(m);
	                }
	            } else if (m.getDelivery().equals(RunPlan.TERM_2)) {
	                if (m.isMandatory()) {
	                	term2Select.getItems().add(m);
	                } else {
	                	term2Unselect.getItems().add(m);
	                }
	            } else {
	                yearSelect.getItems().add(m);
	            }
	        });
	}
	
	public void addterm1Module(Module m) {
		term1Unselect.getItems().remove(m);
		term1Select.getItems().add(m);
	}
	public void addterm2Module(Module m) {
		term2Unselect.getItems().remove(m);
		term2Select.getItems().add(m);
	}
	public void removeterm1Module (Module m) {
		term1Select.getItems().remove(m);
		term1Unselect.getItems().remove(m);
		}
	public void removeterm2Module (Module m) {
		term2Select.getItems().remove(m);
		term2Unselect.getItems().remove(m);
		}
	
	public Module getSelectionterm1Add() {
		return term1Unselect.getSelectionModel().getSelectedItem();
	}
	public Module getSelectionterm1Remove() {
		return term1Select.getSelectionModel().getSelectedItem();
	}
	public Module getSelectionterm2Add() {
		return term2Unselect.getSelectionModel().getSelectedItem();
	}
	public Module getSelectionterm2Remove() {
		return term2Select.getSelectionModel().getSelectedItem();
	}
	
	
	
	 public void addResetHandler(EventHandler<ActionEvent> handler) {
	        resetButton.setOnAction(handler);
	    }

	    public void addSubmitHandler(EventHandler<ActionEvent> handler) {
	        submitButton.setOnAction(handler);
	    }

	    public void addterm1AddHandler(EventHandler<ActionEvent> handler) {
	        term1AddButton.setOnAction(handler);
	    }

	    public void addterm1RemoveHandler(EventHandler<ActionEvent> handler) {
	        term1RemoveButton.setOnAction(handler);
	    }

	    public void addterm2AddHandler(EventHandler<ActionEvent> handler) {
	    	term2AddButton.setOnAction(handler);
	    }

	    public void addterm2RemoveHandler(EventHandler<ActionEvent> handler) {
	    	term2RemoveButton.setOnAction(handler);
	    }

	    public void StartCredits() {
	    	term1Credits = 0;
	    	term2Credits = 0;
	    	term1Select.getItems().forEach(m -> term1Credits += m.getModuleCredits());
	    	term2Select.getItems().forEach(m -> term2Credits += m.getModuleCredits());
	        yearSelect.getItems().forEach(m -> term1Credits += (m.getModuleCredits() / 2));
	        yearSelect.getItems().forEach(m -> term2Credits += (m.getModuleCredits() / 2));
	        textterm1Credits.setText("" + term1Credits);
	        textterm2Credits.setText("" + term2Credits);
	    }
	    
	    public int getterm1Credits() {
	    	return term1Credits;
	    }
	    public int getterm2Credits() {
	    	return term2Credits;
	    }
	    public void incrementterm1Credits(int i) {
	    	term1Credits += i;
	    	textterm1Credits.setText("" + term1Credits);
	    }

	    public void decrementterm1Credits(int i) {
	    	term1Credits -= i;
	    	textterm1Credits.setText("" + term1Credits);
	    }

	    public void incrementterm2Credits(int i) {
	    	term2Credits += i;
	    	textterm2Credits.setText("" + term2Credits);
	    }

	    public void decrementterm2Credits(int i) {
	    	term2Credits -= i;
	    	textterm2Credits.setText("" + term2Credits);
	    }

	    public ObservableList<Module> getyearSelectMod() {
	        return yearSelect.getItems();
	    }

	    public ObservableList<Module> getterm1SelectMod() {
	        return term1Select.getItems();
	    }

	    public ObservableList<Module> getterm2SelectMod() {
	        return term2Select.getItems();
	    }
	    
	    
	    
	    
	    }
	
