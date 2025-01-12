package gui.interpreter;

import controller.IController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.values.IValue;


public class MainWindowController {


    private IController prgController;

    @FXML
    private TextField nrPrgsTextField;

    @FXML
    private ListView<Integer> prgStatesListVIew;

    @FXML
    private ListView<String> outputListVIew;

    @FXML
    private ListView<String> execStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableView<Pair<String, IValue>> symTableView;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symVariablesNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symValuesColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapAddressesColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> heapValuesColumn;


    @FXML
    private void initialize() {

        this.symVariablesNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.symValuesColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.heapAddressesColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.heapValuesColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));


    }

    @FXML
    private void onProgramClicked(MouseEvent event) {

        Integer id = this.prgStatesListVIew.getSelectionModel().getSelectedItem();
        if(id != null)
            setPrgContent(id);
    }

    @FXML
    private void onOneStepButtonClick(ActionEvent event) {
        try {
            this.prgController.oneStepForAllPrg();
        } catch (InterruptedException | RuntimeException e) {
            e.printStackTrace();
            // Create and configure the error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText(e.getMessage()); // Show the exception's message

            // Show the alert window
            alert.showAndWait();
        }
        refresh();

    }

    @FXML
    private void onAllStepsButtonClick(ActionEvent event) {
        try {
            this.prgController.allSteps();
        } catch (InterruptedException | RuntimeException e) {
            e.printStackTrace();
            // Create and configure the error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error Occurred");
            alert.setContentText(e.getMessage()); // Show the exception's message

            // Show the alert window
            alert.showAndWait();
        }
        refresh();

    }



    private void setSharedContent() {

        this.nrPrgsTextField.setText(this.prgController.nrOfPrgStates().toString());

        this.prgStatesListVIew.getItems().clear();
        this.prgController.getPrgStatesID().forEach(id -> this.prgStatesListVIew.getItems().add(id));

        this.heapTableView.getItems().clear();
        this.prgController.getHeap().getContent().forEach((addr, value) ->
                this.heapTableView.getItems().add(new Pair<>(addr, value)));

        this.fileListView.getItems().clear();
        this.prgController.getFileTable().getKeys().forEach(key -> this.fileListView.getItems().add(key.getValue()));

        this.outputListVIew.getItems().clear();
        this.prgController.getOutputList().getContent().forEach(value -> this.outputListVIew.getItems().add(value));
        if(this.prgController.getOutputList() != null)
            System.out.println(prgController.getOutputList().getContent().size());

    }

    private void setPrgContent(Integer id) {

        this.execStackListView.getItems().clear();
        this.prgController.getExeStackFromPrgState(id).getContent().
                forEach(value -> this.execStackListView.getItems().add(value.toString()));

        this.symTableView.getItems().clear();
        this.prgController.getSymTableFromPrgState(id).getContent().forEach((key, value) ->
                this.symTableView.getItems().add(new Pair<>(key, value)));


    }


    private void refresh(){
        setSharedContent();

        if (this.prgStatesListVIew.getItems().size() > 0) {

            Integer id = this.prgStatesListVIew.getItems().getFirst();
            setPrgContent(id);
        }else{
            this.execStackListView.getItems().clear();
            this.symTableView.getItems().clear();
        }

    }



    public void setPrgController(IController prgController) {
        this.prgController = prgController;
        refresh();


    }
}

