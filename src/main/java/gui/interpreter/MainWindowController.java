package gui.interpreter;

import gui.interpreter.controller.IController;
import gui.interpreter.model.values.IValue;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class MainWindowController {

    private IController prgController;

    @FXML
    private TextField nrPrgsTextField;

    @FXML
    private ListView<Integer> prgStatesListView;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> execStackListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private TableView<Pair<String, IValue>> symTableView;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableView<Pair<Integer, Integer>> lockTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symVariablesNameColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symValuesColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapAddressesColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> heapValuesColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockIndexesColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockPrgStatesColumn;

    @FXML
    private void initialize() {

        this.symVariablesNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.symValuesColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.heapAddressesColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.heapValuesColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.lockIndexesColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.lockPrgStatesColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

    }

    @FXML
    private void onProgramClicked(MouseEvent event) {

        Integer id = this.prgStatesListView.getSelectionModel().getSelectedItem();
        if (id != null)
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

        this.prgStatesListView.getItems().clear();
        this.prgController.getPrgStatesID().forEach(id -> this.prgStatesListView.getItems().add(id));

        this.heapTableView.getItems().clear();
        this.prgController.getHeap()
                .forEach((addr, value) -> this.heapTableView.getItems().add(new Pair<>(addr, value)));

        this.fileListView.getItems().clear();
        this.prgController.getFileTableKeys().forEach(key -> this.fileListView.getItems().add(key.getValue()));

        this.outputListView.getItems().clear();
        this.prgController.getOutputList().forEach(value -> this.outputListView.getItems().add(value));

        this.lockTableView.getItems().clear();
        this.prgController.getLockTable()
                .forEach((key, value) -> this.lockTableView.getItems().add(new Pair<>(key, value)));

    }

    private void setPrgContent(Integer id) {

        this.execStackListView.getItems().clear();
        this.prgController.getExeStackFromPrgState(id)
                .forEach(value -> this.execStackListView.getItems().add(value.toString()));

        this.symTableView.getItems().clear();
        this.prgController.getSymTableFromPrgState(id)
                .forEach((key, value) -> this.symTableView.getItems().add(new Pair<>(key, value)));

    }

    private void refresh() {
        setSharedContent();

        if (this.prgStatesListView.getItems().size() > 0) {

            Integer id = this.prgStatesListView.getItems().getFirst();
            setPrgContent(id);
        } else {
            this.execStackListView.getItems().clear();
            this.symTableView.getItems().clear();
        }

    }

    public void setPrgController(IController prgController) {
        this.prgController = prgController;
        refresh();

    }
}
