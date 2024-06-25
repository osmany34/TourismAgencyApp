package view;

import business.*;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JButton btn_exit;
    private JPanel container;
    private JTabbedPane tabbedPane1;
    private JTable tbl_hotel;
    private JTable tbl_pension;
    private JTable tbl_season;
    private JTable tbl_room;
    private JPanel tab_home;
    private JPanel tbl_reservation;
    private JTable tbl_reser;
    private JTable tbl_room_statu;
    private JTextField txtf_name_room;
    private JTextField txtf_city_room;
    private JTextField txtf_children_room;
    private JTextField txtf_human_room;
    private JButton btn_search_room;
    private JButton btn_clear_room;
    private JFormattedTextField txtf_date_first_room;
    private JFormattedTextField txtf_date_finish_room;
    private JLabel lbl_welcome;

    public JTable getTbl_room() {
        return tbl_room;
    }

    public void setTbl_room(JTable tbl_room) {
        this.tbl_room = tbl_room;
    }
    private Hotel hotel;
    private JPopupMenu hotel_menu;
    private HotelManager hotelManager = new HotelManager();
    private Pension pension = new Pension();
    private PensionManager pensionManager = new PensionManager();
    private SeasonManager seasonManager = new SeasonManager();
    private RoomManager roomManager = new RoomManager();
    private UserManager userManager = new UserManager();
    private JPopupMenu pension_menu;
    private JPopupMenu season_menu;
    private JPopupMenu room_menu;
    private JPopupMenu room_res_menu;
    private JPopupMenu reservation_menu;
    private ReservationManager reservationManager;
    private Room room;
    private int selectedHotelID;
    private int selectedRoomID;

    private Object[] col_room;

    private Object[] col_res_room;

    // Sütunlar ve tablolar için varsayılan tablo modelleri
    DefaultTableModel tmdl_hotel = new DefaultTableModel();
    DefaultTableModel tmdl_pension = new DefaultTableModel();
    DefaultTableModel tmdl_season = new DefaultTableModel();
    DefaultTableModel tmdl_room = new DefaultTableModel();
    DefaultTableModel tmdl_res = new DefaultTableModel();
    DefaultTableModel tmdl_res_room = new DefaultTableModel();


    public EmployeeView() {

    }

    //Kurucu metod - Kullanıcı bilgisi ile
    public EmployeeView(User user) {
        this.room = new Room();
        this.col_room = col_room;
        this.col_res_room = col_res_room;
        this.hotelManager = new HotelManager();
        this.hotel = new Hotel();
        this.add(container);
        this.guiInitilaze(1400, 850);
        this.pension_menu = new JPopupMenu();
        this.season_menu = new JPopupMenu();
        this.room_menu = new JPopupMenu();
        this.room_res_menu = new JPopupMenu();
        this.reservation_menu = new JPopupMenu();
        this.lbl_welcome.setText("Username : " + user.getUsername());
        this.reservationManager = new ReservationManager();

        loadHotelTable();
        loadPensionTable();
        loadRoomTable(null);
        loadSeasonTable();
        LoadRoomTableComponent();
        loadReservationTable(null);
        LoadReservationTableComponent();
        loadReservationRoomTable(null);
        LoadReservationRoomComponent();

        tableRowSelect(tbl_hotel);
        tableRowSelect(tbl_pension);
        tableRowSelect(tbl_room);
        loadHotelTableComponent();
        LoadPensionTableComponent();
        loadSeasonTableComponent();

        // Bu butona basıldığında oda için filtrelemeyi sıfırlıyoruz
        btn_clear_room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReservationRoomTable(null);
            }
        });

        btn_search_room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] roomJTextField = new JTextField[]{txtf_human_room, txtf_children_room};
                if (Helper.isFieldListEmpty(roomJTextField)) {
                    Helper.showMsg("Lütfen çocuk ve yetişkin sayısını giriniz");
                } else {
                    String adultText = txtf_human_room.getText();
                    String childText = txtf_children_room.getText();

                    try {
                        int selectedAdult = Integer.parseInt(adultText);
                        int selectedChild = Integer.parseInt(childText);

                        if (selectedAdult < 0 || selectedChild < 0) {
                            Helper.showMsg("Lütfen yetişkin ve çocuk için 0'dan büyük sayılar giriniz");
                        } else {
                            ArrayList<Room> roomList = roomManager.searchForTable(
                                    txtf_name_room.getText(),
                                    txtf_city_room.getText(),
                                    txtf_date_first_room.getText(),
                                    txtf_date_finish_room.getText(),
                                    txtf_human_room.getText(),
                                    txtf_children_room.getText()
                            );

                            if (roomList != null) {
                                ArrayList<Object[]> searchResult = roomManager.getForTable(col_res_room.length, roomList);
                                loadReservationRoomTable(searchResult);
                            } else {
                                Helper.showMsg("Arama sonucu bulunamadı");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        Helper.showMsg("Lütfen geçerli sayılar giriniz");
                    }
                }
            }
        });



        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                loadHotelTable();
                dispose();
            }
        });

        tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbl_hotel.getSelectedRow();
                if (selectedRow != -1) {

                    selectedHotelID = (int) tmdl_hotel.getValueAt(selectedRow, 0);
                    loadSeasonTable();
                    loadPensionTable();
                    loadRoomTable(null);
                }
            }
        });

    }

    //oluşturulan tabloların ekranda görünmesini sağlayan metodlar

    public void loadHotelTable() {
        Object[] col_hotel = {"ID", "Otel Adı", "Adres", "Şehir", "E-mail", "Telefon", "Puan", "Otopark", "Wifi", "Yüzme Havuzu", "Gym", "Spa", "Oda Servisi"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.findAll());
        createTable(this.tmdl_hotel, tbl_hotel, col_hotel, hotelList);
    }

    public void loadReservationTable(Reservation reservation) {
        Object[] col_res = {"ID", "Oda ID", "Giriş Tarihi", "Çıkış Tarihi","Toplam Tutar", "Müşteri Numarası", "Müşteri Adı ", "TC", "E-mail", "Telefon"};
        ArrayList<Object[]> resList = this.reservationManager.getForTable(col_res.length, this.reservationManager.findAll());
        createTable(this.tmdl_res, tbl_reser, col_res, resList);
    }


    public void loadPensionTable() {
        Object[] col_pension = {"ID", "Otel ID", "Pansiyon Tipi"};
        ArrayList<Object[]> pensionList = this.pensionManager.getForTable(col_pension.length, this.pensionManager.getPensionByOtelId(selectedHotelID));
        createTable(this.tmdl_pension, tbl_pension, col_pension, pensionList);
    }

    public void loadSeasonTable() {
        Object[] col_season = {"ID", "Otel ID", "Giriş Tarihi", "Çıkış Tarihi", "Fiyat Hesaplama"};
        ArrayList<Object[]> seasonList = this.seasonManager.getForTable(col_season.length, this.seasonManager.getSeasonsByOtelId(selectedHotelID));
        createTable(this.tmdl_season, tbl_season, col_season, seasonList);

    }

    public void loadRoomTable(ArrayList<Object[]> roomList) {
        col_room = new Object[]{"ID", "Hotel ID", "Pansiyon ID", "Sezon ID", "Tipi", "Stok", "Yetişkin Fiyatı", "Çocuk Fiyatı", "Yatak Kapasitesi", "Oda Alanı", "Televizyon", "Minibar", "Klima", "Çay / Kahve Seti", "Ütü"};
        if (roomList == null) {
            roomList = roomManager.getForTable(col_room.length, this.roomManager.getRoomByOtelId(selectedHotelID));
        }
        createTable(this.tmdl_room, tbl_room, col_room, roomList);
    }

    public void loadReservationRoomTable(ArrayList<Object[]> roomList) {
        col_res_room = new Object[]{"ID", "Hotel ID", "Pansiyon ID", "Sezon ID", "Tipi", "Stok", "Yetişkin Fiyatı", "Çocuk Fiyatı", "Yatak Kapasitesi", "Oda Alanı", "Televizyon", "Minibar", "Klima", "Çay / Kahve Seti", "Ütü"};
        if (roomList == null) {
            roomList = roomManager.getForTable(col_res_room.length, this.roomManager.getRoomByOtelId(selectedHotelID));
        }
        createTable(this.tmdl_res_room, tbl_room_statu, col_res_room, roomList);
    }

    @Override
    public void tableRowSelect(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    //Oda tablosundaki sağ tık menüsü içeriğinin ayarlanması
    public void LoadRoomTableComponent() {
        tableRowSelect(tbl_room);
        this.room_menu = new JPopupMenu();

        this.room_menu.add("Oda Güncelle").addActionListener(e -> {
            int selectedRow = tbl_room.getSelectedRow();
            if (selectedRow != -1)
            {
                int selectedRoomID = (int) tmdl_room.getValueAt(selectedRow, 0);
                loadRoomTable(null);
                UpdateRoomView updateRoomView = new UpdateRoomView(selectedRoomID);
                updateRoomView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRoomTable(null);
                    }
                });
            }
        });

        this.room_menu.addSeparator();

        this.room_menu.add("Oda Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_room, 0);
                if (this.roomManager.delete(selectBrandId))
                {
                    Helper.showMsg("done");
                    loadRoomTable(null);
                    loadHotelTable();
                    loadPensionTable();
                }
                else
                {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu);
    }

    //Rezervasyon araması tablosundaki sağ tık menüsü içeriğinin ayarlanması
    public void LoadReservationRoomComponent() {
        tableRowSelect(tbl_room_statu);
        this.room_res_menu = new JPopupMenu();

        this.room_res_menu.add("Rezervasyon Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_room_statu, 0);
            JTextField[] roomJTextField = new JTextField[]{txtf_date_first_room, txtf_date_finish_room, txtf_human_room, txtf_children_room};
            if (Helper.isFieldListEmpty(roomJTextField))
            {
                Helper.showMsg("fill");
            }
            else
            {
                int adult_numb = Integer.parseInt(this.txtf_human_room.getText());
                int child_numb = Integer.parseInt(this.txtf_children_room.getText());
                ReservationView reservationView = new ReservationView(this.roomManager.getById(selectId), this.txtf_date_first_room.getText(), this.txtf_date_finish_room.getText(), adult_numb, child_numb, null);
                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRoomTable(null);
                        loadReservationTable(null);
                    }
                });
            }
        });

        this.tbl_room_statu.setComponentPopupMenu(room_res_menu);
    }


    //Rezervasyon tablosundaki sağ tık menüsü içeriğinin ayarlanması

    public void LoadReservationTableComponent() {
        tableRowSelect(tbl_reser);
        this.reservation_menu = new JPopupMenu();

        this.reservation_menu.add("Rezervasyon Güncelle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_reser, 0);
            Reservation selectReservation = this.reservationManager.getById(selectId);
            int selectRoomId = selectReservation.getRoom_id();
            Room selectRoom = this.roomManager.getById(selectRoomId);
            UpdateReservationView reservationView = new UpdateReservationView(selectRoom,selectReservation.check_in_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),selectReservation.check_out_date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),selectReservation.getAdult_count(), selectReservation.getChild_count(), selectReservation);
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);

                }
            });
        });

        this.reservation_menu.addSeparator();

        this.reservation_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectResId = this.getTableSelectedRow(tbl_reser, 0);
                int selectRoomId = this.reservationManager.getById(selectResId).getRoom_id();
                Room selectedRoom = this.roomManager.getById(selectRoomId);
                selectedRoom.setStock(selectedRoom.getStock()+1);
                this.roomManager.updateStock(selectedRoom);
                if (this.reservationManager.delete(selectResId)) {
                    Helper.showMsg("done");
                    loadRoomTable(null);
                    loadReservationTable(null);
                    loadHotelTable();
                    loadPensionTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_reservation.setComponentPopupMenu(reservation_menu);
    }

    //Pansiyon tablosundaki sağ tık menüsü içeriğinin ayarlanması

    public void LoadPensionTableComponent() {
        tableRowSelect(tbl_pension);
        this.pension_menu = new JPopupMenu();


        this.pension_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_pension, 0);
                if (this.pensionManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadPensionTable();


                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_pension.setComponentPopupMenu(pension_menu);
    }

    //Sezon tablosundaki sağ tık menüsü içeriğinin ayarlanması

    public void loadSeasonTableComponent() {
        tableRowSelect(tbl_season);
        this.season_menu = new JPopupMenu();

        this.season_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_season, 0);
                if (this.seasonManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadSeasonTable();

                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_season.setComponentPopupMenu(season_menu);
    }

    //Otel tablosundaki sağ tık menüsü içeriğinin ayarlanması

    public void loadHotelTableComponent() {
        tableRowSelect(tbl_hotel);
        tableRowSelect(tbl_pension);
        tableRowSelect(tbl_room);
        tableRowSelect(tbl_season);

        this.hotel_menu = new JPopupMenu();

        this.hotel_menu.add("Otel Ekle").addActionListener(e -> {
            HotelView hotelView = new HotelView();
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
        });

        this.hotel_menu.add("Sezon Ekle").addActionListener(e -> {
            SeasonView seasonView = new SeasonView();
            seasonView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable();
                }
            });
        });

        this.hotel_menu.add("Oda Ekle").addActionListener(e -> {
            RoomView roomView = new RoomView();
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });

        this.hotel_menu.add("Pansiyon Ekle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_hotel, 0);
            PensionView pensionView = new PensionView(selectId);
            pensionView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable();
                }
            });
        });

        this.hotel_menu.addSeparator();

        this.hotel_menu.add("Otel Güncelle").addActionListener(e -> {
            int selectedRow = tbl_hotel.getSelectedRow();
            if (selectedRow != -1) {
                int selectedHotelId = (int) tmdl_hotel.getValueAt(selectedRow, 0);
                loadHotelTable();
                UpdateHotelView updateHotelView = new UpdateHotelView(selectedHotelID);
                updateHotelView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelTable();
                    }
                });
            }
        });

        this.hotel_menu.addSeparator();

        this.hotel_menu.add("Otel Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_hotel, 0);
                if (this.hotelManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadHotelTable();
                    loadPensionTable();
                    loadRoomTable(null);
                    loadSeasonTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_hotel.setComponentPopupMenu(hotel_menu);
    }

    //Formatlı olarak ayarlanmış tarih hücrelerinin ayarlanması

    private void createUIComponents() throws ParseException {
        this.txtf_date_first_room = new JFormattedTextField(new MaskFormatter("##-##-####"));
        this.txtf_date_first_room.setText("01-01-2024");
        this.txtf_date_finish_room = new JFormattedTextField(new MaskFormatter("##-##-####"));
        this.txtf_date_finish_room.setText("01-05-2024");
    }


}
