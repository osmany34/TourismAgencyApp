package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import core.ComboItem;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateRoomView extends Layout {
    private JComboBox cmb_pension;
    private JComboBox cmb_season;
    private JComboBox cmb_room_type;
    private JTextField txtf_stock;
    private JTextField txtf_human_price;
    private JTextField txtf_children_price;
    private JTextField txtf_bed_capasity;
    private JTextField txtf_square;
    private JButton btn_update_roommenu;
    private JPanel container;
    private JRadioButton rb_television;
    private JRadioButton rb_game_console;
    private JRadioButton rb_cashbox;
    private JRadioButton rb_minibar;
    private JRadioButton rb_projection;
    private JTextField txtf_hotel_name;

    private HotelManager hotelManager;
    private SeasonManager seasonManager;
    private PensionManager pensionManager;
    private ComboItem comboItem;
    private EmployeeView employeeView = new EmployeeView();
    private Hotel hotel;
    private Room room;
    private Season season;
    private RoomManager roomManager;
    private int roomId;

    public UpdateRoomView(int roomId) {
        this.add(container);
        this.guiInitilaze(725, 700);
        this.comboItem = new ComboItem();
        this.hotel = new Hotel();
        this.room = new Room();
        this.season = new Season();
        this.roomId = roomId;
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        Room currentRoom = roomManager.getById(roomId);


        //Açılan ekrandaki değerler, seçilen odanın değerleri ile eşitleme ayarları

        if (currentRoom.isCash_box()) {
            rb_cashbox.setSelected(true);
        }
        if (currentRoom.isGame_console()) {
            rb_game_console.setSelected(true);
        }
        if (currentRoom.isMinibar()) {
            rb_minibar.setSelected(true);
        }
        if (currentRoom.isProjection()) {
            rb_projection.setSelected(true);
        }
        if (currentRoom.isTelevision()) {
            rb_television.setSelected(true);
        }

        ArrayList<Pension> pensions = pensionManager.getPensionByOtelId(currentRoom.getHotel_id());
        for (Pension pension : pensions) {
            cmb_pension.addItem(pension.getComboItem());
        }

        ArrayList<Season> seasons = seasonManager.getSeasonsByOtelId(currentRoom.getHotel_id());
        for (Season season : seasons) {
            cmb_season.addItem(season.getComboItem());
        }

        txtf_hotel_name.setText(currentRoom.getHotel().getName());
        cmb_pension.setSelectedItem(pensionManager.getPensionByOtelId(currentRoom.getHotel_id()));
        cmb_season.setSelectedItem(seasonManager.getById(currentRoom.getSeason_id()));
        cmb_room_type.setSelectedItem(currentRoom.getType());
        txtf_stock.setText(String.valueOf(currentRoom.getStock()));
        txtf_human_price.setText(String.valueOf(currentRoom.getAdult_price()));
        txtf_children_price.setText(String.valueOf(currentRoom.getChild_price()));
        txtf_bed_capasity.setText(String.valueOf(currentRoom.getBed_capacity()));
        txtf_square.setText(String.valueOf(currentRoom.getSquare_meter()));

        //Save butonu ayarları

        btn_update_roommenu.addActionListener(e -> {

            JTextField[] selectedRoomList = new JTextField[]{txtf_human_price, txtf_children_price, txtf_bed_capasity, txtf_bed_capasity, txtf_square};

            if (Helper.isFieldListEmpty(selectedRoomList)) {
                Helper.showMsg("fill");
            } else {

            }
            boolean result;

            room = roomManager.getById(currentRoom.getId());

            ComboItem selectedPension = (ComboItem) cmb_pension.getSelectedItem();
            ComboItem selectedSeason = (ComboItem) cmb_season.getSelectedItem();

            this.room.setHotel_id(currentRoom.getHotel_id());
            this.room.setSeason_id(selectedSeason.getKey());
            this.room.setPension_id(selectedPension.getKey());
            this.room.setType((String) cmb_room_type.getSelectedItem());
            this.room.setStock(Integer.parseInt(txtf_stock.getText()));
            this.room.setAdult_price(Double.parseDouble(txtf_human_price.getText()));
            this.room.setChild_price(Double.parseDouble(txtf_children_price.getText()));
            this.room.setBed_capacity(Integer.parseInt(txtf_bed_capasity.getText()));
            this.room.setSquare_meter(Integer.parseInt(txtf_square.getText()));
            this.room.setTelevision(rb_television.isSelected());
            this.room.setMinibar(rb_minibar.isSelected());
            this.room.setGame_console(rb_game_console.isSelected());
            this.room.setCash_box(rb_cashbox.isSelected());
            this.room.setProjection(rb_projection.isSelected());

            result = roomManager.update(room);

            if (result) {
                Helper.showMsg("done");
                dispose();
            } else {
                Helper.showMsg("error");
            }

        });
    }
}
