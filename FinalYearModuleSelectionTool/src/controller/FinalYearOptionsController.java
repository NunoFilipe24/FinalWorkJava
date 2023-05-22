package controller;
//
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import model.*;
import model.Module;
import view.*;
import java.io.*;
import java.util.Collection;
import java.util.Optional;

public class FinalYearOptionsController {

	//fields to be used throughout class
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private FinalYearOptionsMenuBar mstmb;
	private OverviewSelectionTab ost;
	private SelectModulesPane smp;
	private Course[] course;

	public FinalYearOptionsController(StudentProfile model, FinalYearOptionsRootPane view) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		ost = view.getOverviewSelectionTab();
		smp = view.getSelectModulesPane();
		course = buildModulesAndCourses();

		//add courses to combobox in create student profile pane using the buildModulesAndCourses helper method below
		cspp.addCourseDataToComboBox(buildModulesAndCourses());
		

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		cspp.createButtonDisableBind();
		
		smp.addterm1AddHandler(new term1AddHandler());
	    smp.addterm2AddHandler(new term2AddHandler());
	    smp.addterm1RemoveHandler(new term1RemoveHandler());
	    smp.addterm2RemoveHandler(new term2RemoveHandler());
	    smp.addResetHandler(new ResetHandler());
	    smp.addSubmitHandler(new SubmitHandler());
		ost.addSaveProfileHandler(new SaveProfileHandler());
		mstmb.addLoadHandler(new LoadHandler());
		mstmb.addSaveHandler(new SaveHandler());
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null,
																"App Developed by Nuno\n\n App develop to help Computer Science students choose the 3rd year module"));		
		
		
	}
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			if (cspp.checkValid()) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Information Missing.", "Please insert all the information required.");
                return;
				
			}
			if(cspp.checkPNumberValid()) {
                alertDialogBuilder(AlertType.ERROR, "Error", "Invalid PNumber.", "Please insert a valid P number.");
                return;
			
		}
			
			model.setStudentPnumber(cspp.getPNumberInput());
			model.setStudentName(new Name(cspp.getFirstNameInput(), cspp.getSurnameInput()));
			model.setStudentEmail(cspp.getEmailInput());
            model.setSubmissionDate(cspp.getDateInput());
            model.setStudentCourse(cspp.getSelectedCourse());
            smp.setCourseModules(course[cspp.getcboValue()].getAllModulesOnCourse());
            smp.StartCredits();
            alertDialogBuilder(AlertType.CONFIRMATION, "Profile Created", null, "Your profile has successfully been created.");
            view.changeTab(1);
		}}
	public class term1AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module selection = smp.getSelectionterm1Add();
			if(selection != null) {
			if (smp.getterm1Credits()>= 60) {
				alertDialogBuilder(AlertType.ERROR, "You exceed the credit limit!", null, "You need remove some modules to be possible to add more!");
				return;
			}
			smp.incrementterm1Credits(selection.getModuleCredits());
			smp.addterm1Module(selection);
			}
		else {
			alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "You need select a Module to continue.");
		}
		}
	}
	public class term2AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module selection = smp.getSelectionterm2Add();
			if(selection != null) {
			if (smp.getterm2Credits()>= 60) {
				alertDialogBuilder(AlertType.ERROR, "You exceed the credit limit!", null, "You need remove some modules to be possible to add more!");
				return;
			}
			smp.incrementterm2Credits(selection.getModuleCredits());
			smp.addterm2Module(selection);
			}
		else {
			alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "You need select a Module to continue.");
		}
		}
	}
	public class term1RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module selection = smp.getSelectionterm1Remove();
			if (selection != null) {
				if(selection.isMandatory()) {
					alertDialogBuilder(AlertType.ERROR, "It is a mandatory module", null, "You can not remove this module from the selection");
					return;
				}
				smp.decrementterm1Credits(selection.getModuleCredits());
				smp.removeterm1Module(selection);
			}
			else {
				alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to remove.");
				}
						}
		}
	public class term2RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module selection = smp.getSelectionterm2Remove();
			if (selection != null) {
				if(selection.isMandatory()) {
					alertDialogBuilder(AlertType.ERROR, "It is a mandatory module", null, "You can not remove this module from the selection");
					return;
				}
				smp.decrementterm2Credits(selection.getModuleCredits());
				smp.removeterm2Module(selection);
			}
			else {
				alertDialogBuilder(AlertType.INFORMATION, "No option selected", null, "Please select an option to remove.");
				}
			}
		}
	 public class ResetHandler implements EventHandler<ActionEvent> {
	        public void handle(ActionEvent e) {
	            smp.setCourseModules(course[cspp.getcboValue()].getAllModulesOnCourse());
	            smp.StartCredits(); 
	        }
	
	}
	 public class SubmitHandler implements EventHandler<ActionEvent> {
	        public void handle(ActionEvent e) {
	            if (smp.getterm1Credits() + smp.getterm2Credits() != 120) {
	                alertDialogBuilder(AlertType.ERROR, "Not enough modules selected", null, "You need select 60 credits to each term!");
	                return;
	            }
	            ObservableList<Module> yrModules = smp.getyearSelectMod();
	            ObservableList<Module> t1Modules = smp.getterm1SelectMod();
	            ObservableList<Module> t2Modules = smp.getterm1SelectMod();
	            yrModules.forEach(m -> model.addSelectedModule(m));
	            t1Modules.forEach(m -> model.addSelectedModule(m));
	            t2Modules.forEach(m -> model.addSelectedModule(m));
	            setProfileOverview();
	            alertDialogBuilder(AlertType.CONFIRMATION, "Modules selected", null, "Modules have successfully been selected.");
	            view.changeTab(2);
	        }  
	 }
	 
	  public class SaveProfileHandler implements EventHandler<ActionEvent> {
	        public void handle(ActionEvent e) {
	            Collection<Module> modules = model.getAllSelectedModules();
	            File file = new File(model.getStudentPnumber().toUpperCase() + ".txt");
	            try {
	                PrintWriter writer = new PrintWriter(file);
	                writer.println("Name: " + model.getStudentName().getFullName());
	                writer.println("PNum: " + model.getStudentPnumber().toUpperCase());
	                writer.println("Email: " + model.getStudentEmail());
	                writer.println("Date: " + model.getSubmissionDate());
	                writer.println("Course: " + model.getStudentCourse());
	                writer.println("Selected Modules: ");
	                modules.forEach(m -> {
	                    writer.println(m.toString());
	                });
	                writer.flush();
	                writer.close();
	            }
	            catch (FileNotFoundException e1) {
	                e1.printStackTrace();
	            }

	            alertDialogBuilder(AlertType.CONFIRMATION, "Success", null, "Your profile has been saved to " + model.getStudentPnumber().toUpperCase() + ".txt! \n\n" +
	                    "you can find this file in the root directory.");
	        }
	    }
	  public class SaveHandler implements EventHandler<ActionEvent> {
	        public void handle(ActionEvent e) {
	            try {
	                if(cspp.getPNumberInput().isEmpty()) {
	                    alertDialogBuilder(AlertType.ERROR, "PNumber Required", null, "PNumber is required to proceed the register.");
	                    return;
	                }
	                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cspp.getPNumberInput().toUpperCase() + "Obj.dat"));
	                oos.writeObject(cspp);
	                oos.flush();

	                alertDialogBuilder(AlertType.CONFIRMATION, "Success", null, "Your data was save in the system");
	            }
	            catch (IOException ioE) {
	                ioE.printStackTrace();
	            }
	        }
	    }
	  public class LoadHandler implements EventHandler<ActionEvent> {
		  CreateStudentProfilePane student;
	        String pNumber;
	        public void handle(ActionEvent e) {
	            TextInputDialog input = new TextInputDialog();
	            input.setTitle("Enter PNumber");
	            input.setContentText("Please enter a PNumber to load: ");
	            Optional<String> inputPNumber = input.showAndWait();
	            inputPNumber.ifPresent(p -> pNumber = p);
	            try {
	                FileInputStream fis = new FileInputStream(pNumber.toUpperCase() + "Obj.dat");
	                ObjectInputStream ois = new ObjectInputStream(fis);

	                student = (CreateStudentProfilePane) ois.readObject();

	            }
	            catch(IOException ioE) {
	                alertDialogBuilder(AlertType.ERROR, "Error", null, "The requested Student Profile pane data was not found.");
	            }
	            catch (ClassNotFoundException c) {
	                c.printStackTrace();
	            }

	            model.setStudentPnumber(student.gettxtPnumber());
	            model.setStudentName(student.getTxtNames());
	            model.setStudentEmail(student.getTxtEmail());
	            model.setSubmissionDate(student.getDate());

	        }
	    }

	
	    public void setProfileOverview() {
	        TextArea profileArea = ost.getProfile();
	        Collection<Module> modules = model.getAllSelectedModules();
	        profileArea.setText(
	                "Name: " + model.getStudentName().getFullName() + "\n" +
	                "PNum: " + model.getStudentPnumber().toUpperCase() + "\n" +
	                "Email: " + model.getStudentEmail() + "\n" +
	                "Date: " + model.getSubmissionDate() + "\n" +
	                "Course: " + model.getStudentCourse() + "\n\n" +
	                "Selected Modules: " + "\n" + "================================" + "\n"
	        );
	        modules.forEach(m -> {
	            profileArea.appendText(m.toString() + "\n"); //utilises the toString method in module.java to return formatted module list.
	        });}
		

	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
		
	}
	//helper method - builds modules and course data and returns courses within an array
	private Course[] buildModulesAndCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, RunPlan.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, RunPlan.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, RunPlan.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, RunPlan.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, RunPlan.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, RunPlan.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, RunPlan.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, RunPlan.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, RunPlan.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, RunPlan.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, RunPlan.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, RunPlan.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, RunPlan.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, RunPlan.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, RunPlan.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, RunPlan.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, RunPlan.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, RunPlan.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
