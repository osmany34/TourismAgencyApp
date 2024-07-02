package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.awt.*;

public class UpdateHotelView extends Layout {
    private int hotelId;
    private HotelManager hotelManager;
    private JPanel container;
    private JTextField txtf_name;
    private JTextField txtf_city;
    private JTextField txtf_mail;
    private JTextField txtf_phone;
    private JTextField txtf_address;
    private JComboBox<String> cmb_star;
    private JRadioButton rb_car_park;
    private JRadioButton rb_spa;
    private JRadioButton rb_gym;
    private JRadioButton rb_wifi;
    private JRadioButton rb_swim;
    private JRadioButton rb_room_service;
    private JButton btn_update;
    private JLabel lbl_hotel_update;

    // Constructor
    public UpdateHotelView(int hotelId) {
        this.hotelId = hotelId;
        this.hotelManager = new HotelManager();

        // Initialize the GUI components
        initializeComponents();

        // Load the hotel details
        loadHotelDetails(hotelId);

        // Add action listener to the update button
        btn_update.addActionListener(e -> updateHotelDetails());
    }

    // Initialize the GUI components
    private void initializeComponents() {
        container = new JPanel(new GridLayout(0, 2));

        txtf_name = new JTextField();
        txtf_city = new JTextField();
        txtf_mail = new JTextField();
        txtf_phone = new JTextField();
        txtf_address = new JTextField();
        cmb_star = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        rb_car_park = new JRadioButton("Car Park");
        rb_spa = new JRadioButton("Spa");
        rb_gym = new JRadioButton("Gym");
        rb_wifi = new JRadioButton("WiFi");
        rb_swim = new JRadioButton("Swimming Pool");
        rb_room_service = new JRadioButton("Room Service");
        btn_update = new JButton("Update");
        lbl_hotel_update = new JLabel("Update Hotel");

        container.add(new JLabel("Hotel Name:"));
        container.add(txtf_name);
        container.add(new JLabel("City:"));
        container.add(txtf_city);
        container.add(new JLabel("Email:"));
        container.add(txtf_mail);
        container.add(new JLabel("Phone:"));
        container.add(txtf_phone);
        container.add(new JLabel("Address:"));
        container.add(txtf_address);
        container.add(new JLabel("Star Rating:"));
        container.add(cmb_star);
        container.add(rb_car_park);
        container.add(rb_spa);
        container.add(rb_gym);
        container.add(rb_wifi);
        container.add(rb_swim);
        container.add(rb_room_service);
        container.add(btn_update);

        this.add(container);
        this.guiInitilaze(500, 800);
    }

    // Load hotel details into the form
    private void loadHotelDetails(int hotelId) {
        Hotel currentHotel = hotelManager.getById(hotelId);

        txtf_name.setText(currentHotel.getName());
        txtf_address.setText(currentHotel.getAddress());
        txtf_city.setText(currentHotel.getCity());
        txtf_mail.setText(currentHotel.getMail());
        txtf_phone.setText(currentHotel.getPhone());
        cmb_star.setSelectedItem(currentHotel.getStar());
        rb_car_park.setSelected(currentHotel.isCar_park());
        rb_wifi.setSelected(currentHotel.isWifi());
        rb_swim.setSelected(currentHotel.isPool());
        rb_gym.setSelected(currentHotel.isFitness());
        rb_spa.setSelected(currentHotel.isSpa());
        rb_room_service.setSelected(currentHotel.isRoom_service());
    }

    // Update hotel details
    private void updateHotelDetails() {
        JTextField[] requiredFields = {txtf_name, txtf_city, txtf_mail, txtf_phone};
        if (Helper.isFieldListEmpty(requiredFields)) {
            Helper.showMsg("fill");
            return;
        }

        Hotel hotel = hotelManager.getById(hotelId);
        hotel.setName(txtf_name.getText());
        hotel.setAddress(txtf_address.getText());
        hotel.setCity(txtf_city.getText());
        hotel.setMail(txtf_mail.getText());
        hotel.setPhone(txtf_phone.getText());
        hotel.setStar((String) cmb_star.getSelectedItem());
        hotel.setCar_park(rb_car_park.isSelected());
        hotel.setWifi(rb_wifi.isSelected());
        hotel.setPool(rb_swim.isSelected());
        hotel.setFitness(rb_gym.isSelected());
        hotel.setSpa(rb_spa.isSelected());
        hotel.setRoom_service(rb_room_service.isSelected());

        boolean result = hotelManager.update(hotel);
        if (result) {
            Helper.showMsg("done");
            dispose();
        } else {
            Helper.showMsg("error");
        }
    }
}
