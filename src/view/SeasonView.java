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

public class SeasonView extends Layout {
    private HotelManager hotelManager;
    private Hotel hotel;
    private SeasonManager seasonManager;
    private Season season;

    private JPanel container;
    private JComboBox<ComboItem> cmb_season_hotel;
    private JButton btn_save_season;
    private JFormattedTextField txtf_season_start;
    private JFormattedTextField txtf_season_finish;
    private JFormattedTextField txtf_price;

    public SeasonView() {
        // İlgili iş mantığı sınıflarının ve GUI bileşenlerinin başlatılması
        this.hotelManager = new HotelManager();
        this.seasonManager = new SeasonManager();
        this.hotel = new Hotel();
        this.season = new Season();

        // Container'ın eklenmesi ve GUI'nin başlatılması
        this.add(container);
        this.guiInitilaze(375, 300);

        // Otellerin ComboBox'a eklenmesi
        for (Hotel hotel : this.hotelManager.getAllHotels()) {
            this.cmb_season_hotel.addItem(hotel.getComboItem());
        }

        // Save butonunun ActionListener'ı
        btn_save_season.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = false;

                // Seçilen otel bilgilerinin alınması
                ComboItem selectedHotel = (ComboItem) cmb_season_hotel.getSelectedItem();
                season.setHotel_id(selectedHotel.getKey());
                season.setSeason_type(selectedHotel.getValue());

                // Diğer giriş alanlarının değerlerinin alınması ve kontrol edilmesi
                season.setPrice_parameter(Double.parseDouble(txtf_price.getText()));
                JFormattedTextField[] checkDateList = {txtf_season_start, txtf_season_finish};

                if (Helper.isFieldListEmpty(checkDateList)) {
                    Helper.showMsg("fill");
                } else {
                    try {
                        // Tarihlerin alınması ve Season nesnesine atanması
                        season.setStart_date(LocalDate.parse(txtf_season_start.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        season.setFinish_date(LocalDate.parse(txtf_season_finish.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        // SeasonManager üzerinden sezonun kaydedilmesi
                        result = seasonManager.save(season);

                    } catch (DateTimeException ex) {
                        Helper.showMsg("Tarih Formatı Yanlış!");
                        return;
                    }
                }

                // Sonucun kullanıcıya bildirilmesi
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    // JFormattedTextField'ların oluşturulması ve başlangıç değerlerinin atanması
    private void createUIComponents() throws ParseException {
        this.txtf_season_start = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_season_start.setText("01/06/2024");
        this.txtf_season_finish = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_season_finish.setText("01/12/2024");
    }
}
