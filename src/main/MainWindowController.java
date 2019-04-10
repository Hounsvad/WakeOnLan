package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXTextField;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Hounsvad
 */
public class MainWindowController implements Initializable {

    private final static boolean DEBUG = true;
    @FXML
    private ListView<Device> deviceList;
    @FXML
    private Label processLabel;
    @FXML
    private JFXTextField deviceName;

    private DatagramSocket socket;

    private DatagramPacket packet;
    @FXML
    private JFXTextField deviceIP;
    @FXML
    private JFXTextField deviceMAC;
    @FXML
    private JFXTextField devicePort;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            post(ex.toString(), MessageType.ERROR);
        }
    }

    /**
     * Closes the program
     *
     * @param event
     */
    @FXML
    private void close(ActionEvent event) {
        ((Stage) processLabel.getScene().getWindow()).close();
    }

    /**
     * clears the selection
     *
     * @param event
     */
    @FXML
    private void clear(ActionEvent event) {
        deviceList.getSelectionModel().clearSelection();
    }

    /**
     * Displays data for the selected device
     */
    @FXML
    private void deviceSelected() {
        Device device = (Device) deviceList.getSelectionModel().getSelectedItem();
        if (device == null) {
            return;
        }
        deviceName.setText(device.getName());
        deviceIP.setText(device.getIp());
        deviceMAC.setText(device.getMac());
        devicePort.setText(Integer.toString(device.getPort()));
    }

    /**
     * Deletes the selected device
     *
     * @param event
     */
    @FXML
    private void deleteSelected(ActionEvent event) {
        deviceList.getItems().remove(deviceList.getSelectionModel().getSelectedIndex());
    }

    private void shake(JFXTextField field) {
        TranslateTransition translator = new TranslateTransition(Duration.millis(50), field);
        translator.setCycleCount(6);
        translator.autoReverseProperty().set(true);
        translator.setFromX(field.getBoundsInLocal().getMinX());
        translator.setToX(field.getBoundsInLocal().getMinX() + 5.0);
        translator.play();
    }

    /**
     * Updates the device based on the entered data
     *
     * @param event
     */
    @FXML
    private void updateDevice(ActionEvent event) {
        Device device = (Device) deviceList.getSelectionModel().getSelectedItem();
        if (device == null) {
            Device newDevice = new Device(deviceName.getText(), "FF:FF:FF:FF:FF:FF");
            deviceList.getItems().add(newDevice);
            deviceList.getSelectionModel().select(newDevice);
            return;
        }
        JFXTextField source = (JFXTextField) event.getSource();
        if (source.equals(deviceName)) {
            try {
                device.update(deviceName.getText(), null, null, null);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                shake(deviceName);
            }
        } else if (source.equals(deviceIP)) {
            try {
                device.update(null, null, deviceIP.getText(), null);
            } catch (IllegalArgumentException e) {
                shake(deviceIP);
            }
        } else if (source.equals(deviceMAC)) {

            try {
                device.update(null, deviceMAC.getText(), null, null);
            } catch (IllegalArgumentException e) {
                shake(deviceMAC);
            }
        } else if (source.equals(devicePort)) {
            try {
                device.update(null, null, null, Integer.parseInt(devicePort.getText()));
            } catch (IllegalArgumentException e) {
                shake(devicePort);
            }
        }
    }

    @FXML
    private void tutorial(ActionEvent event) {
        //open a tutorial window for showing an intoduction to WOL and how to use the software
    }

    @FXML
    private void wake(ActionEvent event) {
//        if () {
//            try {
//                socket = new DatagramSocket();
//                DatagramPacket = new DatagramPacket(buildMagicpacket(cleanMac), 0);
//
//            } catch (SocketException ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    /**
     * Shows the message and type in the process bar at the bottom of the
     * program.
     * <p>
     * The message will be formatted as Type:Message
     *
     * @param message to be displayed
     * @param type    to be displayed
     */
    private void post(String message, MessageType type) {
        processLabel.setText(type + message);
    }

    /**
     * Builds a byte array of 102 bytes containing a magicpacket specified to
     * the given MAC
     *
     * @param cleanMac is a MAC address formatted as XX-XX-XX-XX-XX-XX
     * @return a magic packet for the given MAC;
     */
    private byte[] buildMagicpacket(String cleanMac) throws IllegalArgumentException {
        if (!cleanMac.matches("([A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2})")) {
            throw new IllegalArgumentException("The given MAC address was not a valid mac address");
        }
        String[] tokens = cleanMac.toUpperCase().split("-");
        byte[] output = new byte[102];
        List<Byte> outputList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            outputList.add(Byte.MAX_VALUE);
        }
        for (int i = 0; i < 16; i++) {
            for (String s : tokens) {
                outputList.add(Byte.parseByte(s, 16));
            }
        }
        outputList.forEach((t) -> {
            output[outputList.indexOf(t)] = t;
        });
        return output;

    }

    private enum MessageType {
        ERROR("Error"),
        QUERY("SQL");

        String message;

        MessageType(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message + ": ";
        }
    }

}
