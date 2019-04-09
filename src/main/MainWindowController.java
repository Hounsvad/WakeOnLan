package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXTextField;
import com.sun.javaws.exceptions.InvalidArgumentException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hounsvad
 */
public class MainWindowController implements Initializable {

    @FXML
    private ListView<?> deviceList;
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

    /**
     * Updates the device based on the entered data
     *
     * @param event
     */
    @FXML
    private void updateDevice(KeyEvent event) {
        Device device = (Device) deviceList.getSelectionModel().getSelectedItem();
        //insert switch that checks which field has been updated and updates selectivly
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
    private byte[] buildMagicpacket(String cleanMac) throws InvalidArgumentException {
        if (!cleanMac.matches("([A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2})")) {
            throw new InvalidArgumentException(new String[]{"The given MAC address was not a valid mac address", cleanMac.toUpperCase()});
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
