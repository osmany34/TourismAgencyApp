package view;

import business.HotelManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeasonView extends Layout{
    private HotelManager hotelManager;
    private Hotel hotel;
    private SeasonManager seasonManager;
    private Season season;

    private JPanel container;
    private JComboBox cmb_season_hotel;
    private JButton btn_save_season;
    private JFormattedTextField txtf_season_start;
    private JFormattedTextField txtf_season_finish;
    private JFormattedTextField txtf_price;
    private JLabel lbl_hotel_id;


    public SeasonView() {
        this.hotelManager = new HotelManager();
        this.hotel = new Hotel();
        this.seasonManager = new SeasonManager();
        this.season = new Season();
        this.cmb_season_hotel.getSelectedItem();
        this.add(container);
        this.guiInitilaze(375, 300);


        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_season_hotel.addItem(hotel.getComboItem());
        }
        btn_save_season.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = false;
                ComboItem selectSeason = (ComboItem) cmb_season_hotel.getSelectedItem();
                season.setHotel_id(selectSeason.getKey());
                season.setSeason_type(cmb_season_hotel.getSelectedItem().toString());
                season.setPrice_parameter(Double.parseDouble(txtf_price.getText()));
                JFormattedTextField[] checkDateList = {txtf_season_start, txtf_season_finish};
                if (Helper.isFieldListEmpty(checkDateList)) {
                    Helper.showMsg("fill");
                } else {
                    try {

                        season.setStart_date(LocalDate.parse(txtf_season_start.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        season.setFinish_date(LocalDate.parse(txtf_season_finish.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        result = seasonManager.save(season);

                    } catch (DateTimeException ex) {
                        Helper.showMsg("Tarih Formatı Yanlış!");
                        return;
                    }
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


    private void createUIComponents() throws ParseException {
        this.txtf_season_start = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_season_start.setText("01/06/2024");
        this.txtf_season_finish = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_season_start.setText("01/12/2024");
    }
}

