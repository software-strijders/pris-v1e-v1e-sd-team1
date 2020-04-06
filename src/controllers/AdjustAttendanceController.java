package controllers;

import enums.AttendanceType;
import enums.SubjectType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Attendance;
import models.Lecture;
import models.user.Student;
import models.user.User;
import models.Class;
import utils.FXUtils;

import javax.security.auth.Subject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AdjustAttendanceController {

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, SubjectType> subjectColumn;
    @FXML private TableColumn<Attendance, AttendanceType> reasonColumn;
    @FXML private TableColumn<Attendance, LocalTime> timeColumn;
    @FXML private ComboBox<AttendanceType> reasonBox;
    @FXML private DatePicker datePicker;
    @FXML private TextArea descriptionBox;

    private Student loggedInStudent;

    @FXML
    public void initialize() throws IllegalAccessException {
        if (User.getLoggedInUser() instanceof Student) {
            this.loggedInStudent = (Student)User.getLoggedInUser();
        } else {
            throw new IllegalAccessException("U heeft niet de bevoegde rol voor deze informatie :(");
        }
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tomorrow = LocalDate.now().plusDays(1);

                setDisable(empty || date.compareTo(tomorrow) < 0 );
            }
        });

        datePicker.setValue(LocalDate.now());
        setUpAttendanceTable();
        fillReasonBox();
    }

    private void setUpAttendanceTable() {
        this.timeColumn.setCellValueFactory(new PropertyValueFactory<>("lectureDate"));
        this.subjectColumn.setCellValueFactory(new PropertyValueFactory<>("lectureSubject"));
        this.reasonColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        this.attendanceTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            updateInfo();
        });

        updateAttendanceTable();
    }

    private void updateInfo() {
        Attendance attendance = attendanceTable.getSelectionModel().getSelectedItem();
        reasonBox.setValue(attendance.getType());
        descriptionBox.setText(attendance.getDescription());
    }

    private void updateAttendanceTable() {
        ArrayList<Attendance> attendances = new ArrayList<>();

        for(Attendance attendance : Attendance.getAttendancesbyStudent(loggedInStudent)) {
            if (attendance.getLecture().getStartDate().toLocalDate().isEqual(datePicker.getValue()) && !attendance.getType().equals(AttendanceType.PRESENT)) {
                attendances.add(attendance);
            }
        }
        attendanceTable.setItems(FXCollections.observableList(attendances));
    }

    @FXML
    private void fillReasonBox() {
        this.reasonBox.setItems(FXCollections.observableArrayList(Arrays.asList(AttendanceType.Absent.values())));
    }


    @FXML
    private void onConfirmButtonClick(ActionEvent event) {
        String description = descriptionBox.getText();
        Attendance selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
        AttendanceType selectedType = reasonBox.getValue();

        selectedAttendance.setDescription(description);
        selectedAttendance.setType(selectedType);
        FXUtils.showInfo("Absentie aangepast.");
    }

    @FXML
    private void onDatePickerAction() {
        updateAttendanceTable();
    }

    @FXML
    private void onMakePresentButtonClick(ActionEvent event) {
        Attendance selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
        selectedAttendance.setDescription("");
        selectedAttendance.setType(AttendanceType.PRESENT);
        clearFields();
    }

    private void clearFields() {
        descriptionBox.setText("");
        reasonBox.setValue(null);
        setUpAttendanceTable();
    }
}
