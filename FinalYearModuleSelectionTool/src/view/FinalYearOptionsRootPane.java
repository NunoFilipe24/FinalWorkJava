package view;
//
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class FinalYearOptionsRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private TabPane tp;
	private OverviewSelectionTab ost;
	private SelectModulesPane smp;
	
	public FinalYearOptionsRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		cspp = new CreateStudentProfilePane();
		mstmb = new FinalYearOptionsMenuBar();
		ost = new OverviewSelectionTab();
		smp = new SelectModulesPane();
		
		
		
		//create panes
		cspp = new CreateStudentProfilePane();
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Overview Selection", ost);
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1,t2,t3);
		
		//create menu bar
		mstmb = new FinalYearOptionsMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);
		
	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	
	public FinalYearOptionsMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}
	
	//private OverviewSelectionTab ost;
	//private SelectModulesPane smp;
	
	public OverviewSelectionTab getOverviewSelectionTab() {
		return ost;
	}
	
	public SelectModulesPane getSelectModulesPane() {
		return smp;

	 }
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
