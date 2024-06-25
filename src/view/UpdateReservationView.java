package view;

import business.ReservationManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import entity.Pension;
import entity.Reservation;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UpdateReservationView extends Layout{
    private JButton btn_reservation_update;
    private JTextField txtf_total_guest;
    private JTextField txtf_name;
    private JTextField txtf_id;
    private JTextField txtf_mail;
    private JTextField txtf_phone;
    private JTextField txtf_room_type;
    private JTextField txtf_pension_type;
    private JTextField txtf_first_date;
    private JTextField txtf_finish_date;
    private JTextField txtf_bed_capasity;
    private JTextField txtf_room_field;
    private JTextField txtf_total;
    private JRadioButton rb_tv;
    private JRadioButton rb_air;
    private JRadioButton rb_bar;
    private JRadioButton rb_iron;
    private JRadioButton rb_cafe;
    private JRadioButton rb_wifi;
    private JRadioButton rb_gym;
    private JRadioButton rb_spa;
    private JRadioButton rb_swim;
    private JRadioButton rb_carpark;
    private JRadioButton rb_room_service;
    private JTextField txtf_hotel_name;
    private JTextField txtf_city;
    private JTextField txtf_star;
    private JPanel container;

    private ReservationManager reservationManager = new ReservationManager();
    private Season season;
    private Pension pension;
    private Reservation reservation;
    private SeasonManager seasonManager;
    private RoomManager roomManager;

    private Room room;
    private String check_in_date;
    private String check_out_date;
    private Double adult_price;
    private Double child_price;


    //Kurucu metod
    public UpdateReservationView(Room room, String check_in_date, String check_out_date, int adult_numb, int child_numb, Reservation reservation) {


        this.add(container);

        this.guiInitilaze(1000, 800);
        this.container = container;
        this.room = room;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;


        if (this.reservation == null) ;
        {
            this.reservation=this.reservationManager.getById(reservation.getId());
            this.roomManager = new RoomManager();

        }
        int guest_count = adult_numb + child_numb;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkindate = LocalDate.parse(check_in_date, formatter);
        LocalDate checkoutdate = LocalDate.parse(check_out_date, formatter);
        long day_count = ChronoUnit.DAYS.between(checkindate, checkoutdate);


        double total_price= (int) ((int) ((this.room.getAdult_price()*adult_numb+this.room.getChild_price()*child_numb))*day_count);



        this.txtf_name.setText(this.room.getHotel().getName());
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
        this.rb_iron.setSelected(this.room.isAir());
        this.rb_air.setSelected(this.room.isCafe());
        this.rb_cafe.setSelected(this.room.isIron());
        this.rb_bar.setSelected(this.room.isMinibar());
        this.reservation.setGuess_phone(this.txtf_phone.getText());
        this.txtf_total.setText(String.valueOf(this.reservationManager.getById(reservation.getId()).getTotal_price()));
        this.txtf_total_guest.setText(String.valueOf(this.reservationManager.getById(reservation.getId()).getGuest_count()));

        if (reservation != null){
            this.reservation.setGuess_citizen_id(this.txtf_id.getText());
            this.reservation.setGuest_name(this.txtf_name.getText());
            this.reservation.setGuess_mail(this.txtf_mail.getText());
            this.reservation.setGuess_phone(this.txtf_phone.getText());
        }

        //Save butonu ayarları

        btn_reservation_update.addActionListener(e -> {
            JTextField[] checkfieldEmpty = {this.txtf_name, this.txtf_id, this.txtf_mail, this.txtf_phone};
            if (Helper.isFieldListEmpty(checkfieldEmpty)) {
                Helper.showMsg("fill");
            } else {
                boolean result;

                this.reservation.setGuess_citizen_id(this.txtf_id.getText());
                this.reservation.setGuest_name(this.txtf_name.getText());
                this.reservation.setGuess_mail(this.txtf_mail.getText());
                this.reservation.setGuess_phone(this.txtf_phone.getText());


                if (this.reservation.getId() != 0) {
                    result = this.reservationManager.update(this.reservation);
                } else {
                    result = false;
                }
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
