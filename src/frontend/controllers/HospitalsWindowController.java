package controllers;

import classes.DataBase;
import classes.Hospital;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.geom.Point2D;

public class HospitalsWindowController {
    @FXML
    private TableView hospitalsTable;
    @FXML
    public TableColumn hospitalsTableIdColumn;
    @FXML
    public TableColumn hospitalsTableNameColumn;
    @FXML
    public TableColumn hospitalsTableAllBedsColumn;
    @FXML
    public TableColumn hospitalsTableFreeBedsColumn;
    @FXML
    public TableColumn hospitalsTableIdPatientsColumn;

    @FXML
    void initialize() {
        hospitalsTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        hospitalsTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        hospitalsTableAllBedsColumn.setCellValueFactory(new PropertyValueFactory<>("bedsAmount"));
        hospitalsTableFreeBedsColumn.setCellValueFactory(new PropertyValueFactory<>("freeBedsAmount"));

        Thread refreshTableThread = new Thread(() -> {
            while(true) {
                hospitalsTable.refresh();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        refreshTableThread.setDaemon(true);
        refreshTableThread.start();


    }

    public void updateHospitalTable(){
        for(Hospital hospital : DataBase.getHospitalsList()){
            hospitalsTable.getItems().addAll(hospital);
        }
    }
}
