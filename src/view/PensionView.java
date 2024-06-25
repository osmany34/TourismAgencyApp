package view;

import business.HotelManager;
import business.PensionManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.User;

import javax.swing.*;

public class PensionView extends Layout {
    private JButton btn_save_pension;
    private JComboBox<ComboItem> cmb_pension;
    private JComboBox cmb_hotel;
    private PensionManager pensionManager;
    private Pension pension;
    private HotelManager hotelManager;
    private Hotel hotel;
    private User user;
    private JPanel container;
    private JLabel lbl_pen_add;


    //Kurucu metod
    public PensionView(int hotel_id) {

        this.hotel = new Hotel();
        this.hotelManager = new HotelManager();
        this.pensionManager = new PensionManager();
        this.pension = new Pension();
        this.add(container);
        this.guiInitilaze(375, 250);
        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_hotel.addItem(hotel.getComboItem());

        }

        this.btn_save_pension.addActionListener(e -> {
            boolean result = false;
            ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();
            this.pension.setHotel_id(selectedHotel.getKey());
            this.pension.setPension_type(cmb_pension.getSelectedItem().toString());

            if (this.pension.getId() != 0) {
                result = this.pensionManager.update(this.pension);

            } else {
                result = this.pensionManager.save(this.pension);
            }
            if (result) {
                Helper.showMsg("done");
                dispose();
            } else {
                Helper.showMsg("error");
            }
        });
    }

}


