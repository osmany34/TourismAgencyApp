package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import java.text.CompactNumberFormat;

public class HotelView extends Layout {
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
    private JButton btn_save;
    private JLabel lbl_otel_save;


    //Kurucu metod
    public HotelView() {
        this.hotelManager = new HotelManager();
        this.hotel = new Hotel();
        this.add(container);
        this.guiInitilaze(500, 600);
        if(this.hotel.getId() != 0) {
            this.txtf_name.setText(this.hotel.getName());
            this.txtf_mail.setText(this.hotel.getMail());
            this.txtf_city.setText(this.hotel.getCity());
            this.txtf_phone.setText(this.hotel.getPhone());
            this.txtf_adress.setText(this.hotel.getAddress());
            this.cmb_star.setSelectedItem(this.hotel.getStar());
        }
        btn_save.addActionListener(e -> {

            JTextField[] checkFieldList = {this.txtf_name, this.txtf_mail, this.txtf_phone, this.txtf_adress, this.txtf_city};

            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");

            } else {
                boolean result = true;
                this.hotel.setName(this.txtf_name.getText());
                this.hotel.setMail(this.txtf_mail.getText());
                this.hotel.setCity(this.txtf_city.getText());
                this.hotel.setPhone(this.txtf_phone.getText());
                this.hotel.setAddress(this.txtf_adress.getText());
                this.hotel.setStar((String)this.cmb_star.getSelectedItem());
                this.hotel.setCar_park(this.rb_car_park.isSelected());
                this.hotel.setWifi(this.rb_wifi.isSelected());
                this.hotel.setPool(this.rb_swim.isSelected());
                this.hotel.setFitness(this.rb_gym.isSelected());
                //this.hotel.setConcierge(this.rbut_concierge.isSelected());
                this.hotel.setSpa(this.rb_spa.isSelected());
                this.hotel.setRoom_service(this.rb_room_service.isSelected());
                result = this.hotelManager.save(hotel);
                if (result) {
                    Helper.showMsg("done");

                    dispose();

                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}





