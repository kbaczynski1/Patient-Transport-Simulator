import classes.DataBase;
import classes.PatientsLoader;

public class JavaFXStarter {

	public static void main(String[] args) {
		PatientsLoader patientsLoader = new PatientsLoader();
		patientsLoader.loadDataFromFile("/home/rvbc-/git/2020Z_AISD_proj_zesp_GR1_GR2_GR3_gr4/patients.txt");
		DataBase.printPatiens();

		//application.MainGUI.main(args);
	}

}
