package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.text.CompactNumberFormat;

public class UpdateHotelView extends Layout {
    int hotelId;

    private Hotel hotel;
    private HotelManager hotelManager;
    private JPanel container;
    private JTextField txtf_name;
    private JTextField txtf_city;
    private JTextField txtf_mail;
    private JTextField txtf_phone;
    private JTextField txtf_adress;
    private JComboBox cmb_star;
    private JRadioButton rb_car_park;
    private JRadioButton rb_spa;
    private JRadioButton rb_gym;
    private JRadioButton rb_wifi;
    private JRadioButton rb_swim;
    private JRadioButton rb_room_service;
    private JButton btn_update;
    private JLabel lbl_otel_save;

    private ComboItem comboItem;
    private SeasonManager seasonManager;
    private PensionManager pensionManager;
    private RoomManager roomManager;
    private Room room;
    private Season season;


    //Kurucu metod
    //Kurucu metod
    public UpdateHotelView(int hotelId) {
        this.add(container);
        this.guiInitilaze(500, 800);
        this.comboItem = new ComboItem();
        this.hotel = new Hotel();
        this.room = new Room();
        this.season = new Season();
        this.hotelId = hotelId;
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        Hotel currentHotel = hotelManager.getById(hotelId);

        //Açılan ekrandaki butonların, düzenlenen oteldeki verilerle eşleştirilmesi

        if(currentHotel.isCar_park()){rb_car_park.setSelected(true);}
        if(currentHotel.isWifi()){rb_wifi.setSelected(true);}
        if(currentHotel.isPool()){rb_swim.setSelected(true);}
        if(currentHotel.isFitness()){rb_gym.setSelected(true);}
        if(currentHotel.isSpa()){rb_spa.setSelected(true);}
        if(currentHotel.isRoom_service()){rb_room_service.setSelected(true);}

        txtf_name.setText(currentHotel.getName());
        txtf_adress.setText(currentHotel.getAddress());
        txtf_city.setText(currentHotel.getCity());
        txtf_mail.setText(currentHotel.getMail());
        txtf_phone.setText(currentHotel.getPhone());
        cmb_star.setSelectedItem(currentHotel.getStar());

        //Save butonu işlemleri

        btn_update.addActionListener(e -> {

            JTextField[] selectedHotelList = new JTextField[]{txtf_name, txtf_city, txtf_mail, txtf_phone};


            if (Helper.isFieldListEmpty(selectedHotelList)){
                Helper.showMsg("fill");
            }
            else {

            }

            boolean result;

            hotel = hotelManager.getById(currentHotel.getId());
            this.hotel.setName(txtf_name.getText());
            this.hotel.setAddress(txtf_adress.getText());
            this.hotel.setCity(txtf_city.getText());
            this.hotel.setMail(txtf_mail.getText());
            this.hotel.setPhone(txtf_phone.getText());
            this.hotel.setStar((String) cmb_star.getSelectedItem());
            this.hotel.setCar_park(rb_car_park.isSelected());
            this.hotel.setWifi(rb_wifi.isSelected());
            this.hotel.setPool(rb_swim.isSelected());
            this.hotel.setFitness(rb_gym.isSelected());
            this.hotel.setSpa(rb_spa.isSelected());
            this.hotel.setRoom_service(rb_room_service.isSelected());

            result = hotelManager.update(hotel);

            if (result){
                Helper.showMsg("done");
                dispose();
            }
            else {
                Helper.showMsg("error");
            }
        });
    }
}






