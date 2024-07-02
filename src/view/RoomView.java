package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoomView extends Layout {
    private JPanel container;
    private JComboBox cmb_hotel;
    private JComboBox cmb_pension;
    private JComboBox cmb_season;
    private JComboBox cmb_room_type;
    private JTextField txtf_stock;
    private JTextField txtf_human_price;
    private JTextField txtf_children_price;
    private JTextField txtf_bed_capasity;
    private JTextField txtf_square;
    private JButton btn_save_roommenu;
    private JLabel lbl_title_add;
    private JRadioButton rb_television;
    private JRadioButton rb_game_console;
    private JRadioButton rb_minibar;
    private JRadioButton rb_cashbox;
    private JRadioButton rb_projection;
    private HotelManager hotelManager;
    private SeasonManager seasonManager;
    private PensionManager pensionManager;
    private ComboItem comboItem;
    private Hotel hotel;
    private Room room;
    private Season season;
    private RoomManager roomManager;

    public RoomView() {
        // GUI ve kod içerisindeki değişkenler ayarlanır.
        this.add(container);
        this.guiInitilaze(725, 700);
        this.comboItem = new ComboItem();
        this.hotel = new Hotel();
        this.room = new Room();
        this.season = new Season();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();

        for (Hotel hotel : hotelManager.getAllHotels()) {
            this.cmb_hotel.addItem(hotel.getComboItem());
        }

        cmb_hotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // comboboxlardaki değerleri dolduran metodlar
                ComboItem selectedOtelItem = (ComboItem) cmb_hotel.getSelectedItem();
                int selectedOtelId = selectedOtelItem.getKey();
                ArrayList<Pension> pensions = pensionManager.getPensionByOtelId(((ComboItem) cmb_hotel.getSelectedItem()).getKey());
                System.out.println("pansiyon: " + pensions);
                cmb_pension.removeAllItems();

                for (Pension pension : pensions) {
                    cmb_pension.addItem(pension.getComboItem());
                }

                ArrayList<Season> seasons = seasonManager.getSeasonsByOtelId(((ComboItem) cmb_hotel.getSelectedItem()).getKey());
                System.out.println("sezonlar: " + seasons);
                cmb_season.removeAllItems();

                for (Season season : seasons) {
                    cmb_season.addItem(season.getComboItem());

                }
            }
        });

        //Save butonuna tıklandığında çalışan metod
        btn_save_roommenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField[] selectedRoomList = new JTextField[]{txtf_human_price, txtf_children_price, txtf_bed_capasity, txtf_bed_capasity, txtf_square};

                if (Helper.isFieldListEmpty(selectedRoomList)) {
                    Helper.showMsg("fill");
                } else {
                    boolean result = false;
                    ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();
                    ComboItem selectedPension = (ComboItem) cmb_pension.getSelectedItem();
                    ComboItem selectedSeason = (ComboItem) cmb_season.getSelectedItem();//: TODO hata veriyor
                    room.setSeason_id(selectedSeason.getKey());
                    room.setPension_id(selectedPension.getKey());
                    room.setHotel_id(selectedHotel.getKey());
                    room.setType((String) cmb_room_type.getSelectedItem());
                    room.setStock(Integer.parseInt(txtf_stock.getText()));
                    room.setAdult_price(Double.parseDouble(txtf_human_price.getText()));
                    room.setChild_price(Double.parseDouble(txtf_children_price.getText()));
                    room.setBed_capacity(Integer.parseInt(txtf_bed_capasity.getText()));
                    room.setSquare_meter(Integer.parseInt(txtf_square.getText()));
                    room.setTelevision(rb_television.isSelected());
                    room.setMinibar(rb_minibar.isSelected());
                    room.setGame_console(rb_game_console.isSelected());
                    room.setCash_box(rb_cashbox.isSelected());
                    room.setProjection(rb_projection.isSelected());

                    if (room.getId() != 0) {
                        result = roomManager.update(room);
                    } else {
                        result = roomManager.save(room);
                    }
                    if (result) {
                        Helper.showMsg("done");
                        /*employeeGUI.loadReservationTable();*/

                        dispose();
                    } else {
                        Helper.showMsg("error");
                    }
                }

            }

        });
    }
}

