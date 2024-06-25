package view;

import business.ReservationManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ReservationView extends Layout {
    private JPanel container;
    private JButton btn_reservation_add;
    private JRadioButton rb_wifi;

    private ReservationManager reservationManager = new ReservationManager();
    private Season season;
    private Pension pension;
    private Reservation reservation;
    private SeasonManager seasonManager = new SeasonManager();
    private RoomManager roomManager;

    private Room room;
    private String check_in_date;
    private String check_out_date;
    private Double adult_price;
    private Double child_price;
    private JTextField txtf_hotel_name;
    private JTextField txtf_city;
    private JTextField txtf_star;
    private JRadioButton rb_gym;
    private JRadioButton rb_spa;
    private JRadioButton rb_swim;
    private JRadioButton rb_carpark;
    private JRadioButton rb_room_service;
    private JTextField txtf_room_type;
    private JTextField txtf_bed_capasity;
    private JTextField txtf_room_field;
    private JTextField txtf_pension_type;
    private JTextField txtf_first_date;
    private JRadioButton rb_tv;
    private JRadioButton rb_air;
    private JRadioButton rb_cafe;
    private JRadioButton rb_iron;
    private JRadioButton rb_bar;
    private JTextField txtf_total;
    private JTextField txtf_total_guest;
    private JTextField txtf_name;
    private JTextField txtf_id;
    private JTextField txtf_mail;
    private JTextField txtf_phone;
    private JTextField txtf_finish_date;


    // Kurucu metod
    public ReservationView(Room room, String check_in_date, String check_out_date, int adult_numb, int child_numb, Reservation reservation) {

        // GUI'yi oluştur
        this.add(container);
        this.guiInitilaze(1200, 1200);
        this.container = container;

        // Oda ve rezervasyon bilgilerini atama
        this.room = room;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;


        if (this.reservation == null) ;
        {
            this.reservation = new Reservation();
            this.roomManager = new RoomManager();

        }
        int guest_count = adult_numb + child_numb;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkindate = LocalDate.parse(check_in_date, formatter);
        LocalDate checkoutdate = LocalDate.parse(check_out_date, formatter);
        long day_count = ChronoUnit.DAYS.between(checkindate, checkoutdate);

        double total_price = ((this.room.getAdult_price() * adult_numb)+ this.room.getChild_price() * child_numb) * day_count * this.seasonManager.returnPriceParameter(this.room.getSeason_id()) ;


        // GUI bileşenlerini doldur
        this.txtf_hotel_name.setText(this.room.getHotel().getName());
        this.txtf_city.setText(this.room.getHotel().getAddress());
        this.txtf_star.setText(this.room.getHotel().getStar());
        this.rb_carpark.setSelected(this.room.getHotel().isCar_park());
        this.rb_wifi.setSelected(this.room.getHotel().isWifi());
        this.rb_swim.setSelected(this.room.getHotel().isPool());
        this.rb_gym.setSelected(this.room.getHotel().isFitness());
        this.rb_spa.setSelected(this.room.getHotel().isSpa());
        this.rb_room_service.setSelected(this.room.getHotel().isRoom_service());
        this.txtf_room_type.setText(this.room.getType());
        this.txtf_bed_capasity.setText(String.valueOf(this.room.getBed_capacity()));
        this.txtf_pension_type.setText(this.room.getPension().getPension_type());
        this.txtf_room_field.setText(String.valueOf(this.room.getSquare_meter()));
        this.txtf_first_date.setText(String.valueOf(this.check_in_date));

        this.txtf_finish_date.setText(String.valueOf(this.check_out_date));
        this.rb_tv.setSelected(this.room.isTelevision());
        this.rb_air.setSelected(this.room.isAir());
        this.rb_cafe.setSelected(this.room.isCafe());
        this.rb_iron.setSelected(this.room.isIron());
        this.rb_bar.setSelected(this.room.isMinibar());
        this.txtf_total.setText(String.valueOf(total_price));
        this.txtf_total_guest.setText(String.valueOf(guest_count));

        // Rezervasyon ekleme butonuna dinleyici ekle
        btn_reservation_add.addActionListener(e -> {
            JTextField[] checkfieldEmpty = {this.txtf_name,this.txtf_id,this.txtf_mail,this.txtf_phone};
            if (Helper.isFieldListEmpty(checkfieldEmpty)){
                Helper.showMsg("fill");
            }else{
                boolean result;

                // Rezervasyon bilgilerini atama
                this.reservation.setTotal_price(Double.parseDouble(this.txtf_total.getText()));
                this.reservation.setGuest_count(Integer.parseInt(this.txtf_total_guest.getText()));
                this.reservation.setGuest_name(this.txtf_name.getText());
                this.reservation.setGuess_citizen_id(this.txtf_id.getText());
                this.reservation.setGuess_mail(this.txtf_mail.getText());
                this.reservation.setGuess_phone(this.txtf_phone.getText());
                this.reservation.setRoom_id(this.room.getId());
                this.reservation.setCheck_in_date(LocalDate.parse(this.check_in_date,formatter));
                this.reservation.setCheck_out_date(LocalDate.parse(this.check_out_date,formatter));

                result = this.reservationManager.save(this.reservation);
                if (result){
                    Helper.showMsg("done");

                    this.roomManager.getById(this.room.setStock(this.room.getStock()-1));
                    this.roomManager.updateStock(this.room);
                    dispose();
                }else {
                    Helper.showMsg("error");
                }

            }
        });
    }
}

