package view;

import business.ReservationManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.Reservation;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ReservationView extends Layout {
    private JPanel container;
    private JButton btn_reservation_add;
    private JRadioButton rb_wifi;
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

    private ReservationManager reservationManager;
    private RoomManager roomManager;
    private SeasonManager seasonManager;

    private Room room;
    private Hotel hotel;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor
    public ReservationView(Hotel hotel, Room room, LocalDate startDate, LocalDate endDate) {
        this.hotel = hotel;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;

        this.reservationManager = new ReservationManager();
        this.roomManager = new RoomManager();
        this.seasonManager = new SeasonManager();

        this.add(container);
        this.guiInitilaze(1200, 1000);

        loadData();
        btn_reservation_add.addActionListener(e -> onAddReservation());
    }
    private void loadData() {
        long dayCount = ChronoUnit.DAYS.between(startDate, endDate);
        double totalPrice = room.getAdult_price() * dayCount * seasonManager.returnPriceParameter(room.getSeason_id());

        txtf_hotel_name.setText(hotel.getName());
        txtf_city.setText(hotel.getCity());
        txtf_star.setText(hotel.getStar());
        rb_carpark.setSelected(hotel.isCar_park());
        rb_wifi.setSelected(hotel.isWifi());
        rb_swim.setSelected(hotel.isPool());
        rb_gym.setSelected(hotel.isFitness());
        rb_spa.setSelected(hotel.isSpa());
        rb_room_service.setSelected(hotel.isRoom_service());
        txtf_room_type.setText(room.getType());
        txtf_bed_capasity.setText(String.valueOf(room.getBed_capacity()));
        txtf_pension_type.setText(room.getPension() != null ? room.getPension().getPension_type() : "");
        txtf_room_field.setText(String.valueOf(room.getSquare_meter()));
        txtf_first_date.setText(startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtf_finish_date.setText(endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        rb_tv.setSelected(room.isTelevision());
        rb_air.setSelected(room.isGame_console());
        rb_cafe.setSelected(room.isCash_box());
        rb_iron.setSelected(room.isProjection());
        rb_bar.setSelected(room.isMinibar());
        txtf_total.setText(String.valueOf(totalPrice));
        txtf_total_guest.setText(String.valueOf(room.getBed_capacity()));
    }

    private void onAddReservation() {
        JTextField[] checkfieldEmpty = {txtf_name, txtf_id, txtf_mail, txtf_phone};
        if (Helper.isFieldListEmpty(checkfieldEmpty)) {
            Helper.showMsg("fill");
        } else {
            boolean result;

            Reservation reservation = new Reservation();
            reservation.setTotal_price(Double.parseDouble(txtf_total.getText()));
            reservation.setGuest_count(Integer.parseInt(txtf_total_guest.getText()));
            reservation.setGuest_name(txtf_name.getText());
            reservation.setGuess_citizen_id(txtf_id.getText());
            reservation.setGuess_mail(txtf_mail.getText());
            reservation.setGuess_phone(txtf_phone.getText());
            reservation.setRoom_id(room.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            reservation.setCheck_in_date(LocalDate.parse(txtf_first_date.getText(), formatter));
            reservation.setCheck_out_date(LocalDate.parse(txtf_finish_date.getText(), formatter));

            result = reservationManager.save(reservation);
            if (result) {
                Helper.showMsg("done");
                room.setStock(room.getStock() - 1);
                roomManager.updateStock(room);
                dispose();
            } else {
                Helper.showMsg("error");
            }
        }
    }
}