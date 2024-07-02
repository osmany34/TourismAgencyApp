package view;

import business.*;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private JComboBox<Hotel> cmb_hotel_res;
    private JComboBox<String> cmb_city_res;

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
        this.lbl_welcome.setText("Kullanıcı : " + user.getUsername());
        this.reservationManager = new ReservationManager();

        loadHotelTable();
        loadPensionTable();
        loadRoomTable(null);
        loadSeasonTable();
        loadRoomTableComponent();
        loadReservationTable(null);
        loadReservationTableComponent();
        loadReservationRoomTable(null);
        loadReservationRoomComponent();
        initializeHotelComboBox();

        tableRowSelect(tbl_hotel);
        tableRowSelect(tbl_pension);
        tableRowSelect(tbl_room);
        loadHotelTableComponent();
        loadPensionTableComponent();
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
                String startDate = txtf_date_first_room.getText();
                String endDate = txtf_date_finish_room.getText();

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate startLocalDate = LocalDate.parse(startDate, inputFormatter);
                    LocalDate endLocalDate = LocalDate.parse(endDate, inputFormatter);

                    String formattedStartDate = startLocalDate.format(outputFormatter);
                    String formattedEndDate = endLocalDate.format(outputFormatter);

                    Hotel selectedHotel = (Hotel) cmb_hotel_res.getSelectedItem();
                    String hotelName = selectedHotel != null ? selectedHotel.getName() : null;

                    String city = (String) cmb_city_res.getSelectedItem();

                    ArrayList<Room> rooms = roomManager.searchRooms(formattedStartDate, formattedEndDate, city, hotelName);
                    ArrayList<Object[]> roomData = roomManager.getForTable(col_room.length, rooms);
                    loadReservationRoomTable(roomData);

                } catch (DateTimeParseException dtpe) {
                    dtpe.printStackTrace();
                    Helper.showMsg("error");
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
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length, this.hotelManager.getAllHotels());
        createTable(this.tmdl_hotel, tbl_hotel, col_hotel, hotelList);
    }

    public void loadReservationTable(Reservation reservation) {
        Object[] col_res = {"ID", "Oda ID", "Giriş Tarihi", "Çıkış Tarihi", "Toplam Tutar", "Müşteri Numarası", "Müşteri Adı ", "TC", "E-mail", "Telefon"};
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
        col_room = new Object[]{"ID", "Hotel ID", "Pansiyon ID", "Sezon ID", "Tipi", "Stok", "Yetişkin Fiyatı", "Çocuk Fiyatı", "Yatak Kapasitesi", "Oda Alanı", "Televizyon", "Minibar", "Oyun Konsolu", "Kasa", "Projeksiyon"};
        if (roomList == null) {
            roomList = roomManager.getForTable(col_room.length, this.roomManager.getRoomByOtelId(selectedHotelID));
        }
        createTable(this.tmdl_room, tbl_room, col_room, roomList);
    }

    public void loadReservationRoomTable(ArrayList<Object[]> roomList) {
        col_res_room = new Object[]{"ID", "Hotel ID", "Pansiyon ID", "Sezon ID", "Tipi", "Stok", "Yetişkin Fiyatı", "Çocuk Fiyatı", "Yatak Kapasitesi", "Oda Alanı", "Televizyon", "Minibar", "Oyun Konsolu", "Kasa", "Projeksiyon"};
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
    public void loadRoomTableComponent() {
        tableRowSelect(tbl_room);
        this.room_menu = new JPopupMenu();

        this.room_menu.add("Oda Güncelle").addActionListener(e -> {
            int selectedRow = tbl_room.getSelectedRow();
            if (selectedRow != -1) {
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
                if (this.roomManager.delete(selectBrandId)) {
                    Helper.showMsg("done");
                    loadRoomTable(null);
                    loadHotelTable();
                    loadPensionTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu);
    }

    private void initializeHotelComboBox() {
        cmb_hotel_res.removeAllItems();
        cmb_city_res.removeAllItems();
        ArrayList<Hotel> hotels = roomManager.getAllHotels();
        for (Hotel hotel : hotels) {
            cmb_hotel_res.addItem(hotel);
        }

        cmb_hotel_res.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Hotel selectedHotel = (Hotel) cmb_hotel_res.getSelectedItem();
                    if (selectedHotel != null) {
                        initializeHotelCityComboBox(selectedHotel.getId());
                    }
                }
            }
        });
    }

    private void initializeHotelCityComboBox(int hotelId) {
        cmb_city_res.removeAllItems();
        ArrayList<String> cities = roomManager.getHotelCities(hotelId);
        for (String city : cities) {
            cmb_city_res.addItem(city);
        }
    }

    public void loadReservationRoomComponent() {
        tableRowSelect(tbl_room_statu);
        this.room_res_menu = new JPopupMenu();

        this.room_res_menu.add("Rezervasyon Ekle").addActionListener(e -> {
            int selectRoomId = this.getTableSelectedRow(tbl_room_statu, 0);
            Room selectedRoom = roomManager.getById(selectRoomId);

            if (selectedRoom == null) {
                Helper.showMsg("error");
                return;
            }

            Hotel selectedHotel = hotelManager.getById(selectedRoom.getHotel_id());

            if (selectedHotel == null) {
                Helper.showMsg("error");
                return;
            }

            String startDateStr = txtf_date_first_room.getText();
            String endDateStr = txtf_date_finish_room.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            ReservationView reservationView = new ReservationView(selectedHotel, selectedRoom, startDate, endDate);
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                    loadReservationTable(null);
                }
            });
            reservationView.setVisible(true);
        });

        this.tbl_room_statu.setComponentPopupMenu(room_res_menu);
    }
    public void loadReservationTableComponent() {
        tableRowSelect(tbl_reser); // Satır seçim işlevini çağır
        this.reservation_menu = new JPopupMenu();

        // Rezervasyon Güncelleme Menüsü
        this.reservation_menu.add("Rezervasyonu Güncelle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_reser, 0);
            Reservation selectReservation = this.reservationManager.getById(selectId);
            int selectRoomId = selectReservation.getRoom_id();
            Room selectRoom = this.roomManager.getById(selectRoomId);

            UpdateReservationView reservationView = new UpdateReservationView(
                    selectRoom,
                    selectReservation.getCheck_in_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    selectReservation.getCheck_out_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    selectReservation
            );

            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);
                }
            });
            reservationView.setVisible(true); // Bu satır eksikti, pencereyi görünür hale getirir
        });

        this.reservation_menu.addSeparator();

        // Rezervasyon Silme Menüsü
        this.reservation_menu.add("Rezervasyon Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectResId = this.getTableSelectedRow(tbl_reser, 0);
                int selectRoomId = this.reservationManager.getById(selectResId).getRoom_id();
                Room selectedRoom = this.roomManager.getById(selectRoomId);
                selectedRoom.setStock(selectedRoom.getStock() + 1);
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

        this.tbl_reser.setComponentPopupMenu(reservation_menu);
    }
    //Pansiyon tablosundaki sağ tık menüsü içeriğinin ayarlanması
    public void loadPensionTableComponent() {
        tableRowSelect(tbl_pension);
        this.pension_menu = new JPopupMenu();

        // Add new pension
        this.pension_menu.add("Yeni").addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotel, 0); // Assuming you need to select a hotel first
            if (selectedHotelId != -1) {
                PensionView pensionView = new PensionView(selectedHotelId);
                pensionView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPensionTable();
                    }
                });
                pensionView.setVisible(true); // Make the PensionView visible
            } else {
                Helper.showMsg("Lütfen yeni pansiyon eklemek için bir otel seçin.");
            }
        });

        // Update pension
        this.pension_menu.add("Güncelle").addActionListener(e -> {
            int selectedRow = tbl_pension.getSelectedRow();
            if (selectedRow != -1) {
                int selectedPensionId = (int) tmdl_pension.getValueAt(selectedRow, 0);
                Pension selectedPension = this.pensionManager.getById(selectedPensionId);
                PensionView pensionView = new PensionView(selectedPensionId);
                pensionView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPensionTable();
                    }
                });
                pensionView.setVisible(true); // Make the PensionView visible
            } else {
                Helper.showMsg("Lütfen güncellemek istediğiniz pansiyonu seçin.");
            }
        });

        // Delete pension
        this.pension_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedRow = tbl_pension.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedPensionId = (int) tmdl_pension.getValueAt(selectedRow, 0);
                    if (this.pensionManager.delete(selectedPensionId)) {
                        Helper.showMsg("done");
                        loadPensionTable();
                    } else {
                        Helper.showMsg("error");
                    }
                } else {
                    Helper.showMsg("Lütfen silmek istediğiniz pansiyonu seçin.");
                }
            }
        });

        this.tbl_pension.setComponentPopupMenu(pension_menu);
    }

    //Sezon tablosundaki sağ tık menüsü içeriğinin ayarlanması
    public void loadSeasonTableComponent() {
        tableRowSelect(tbl_season);
        this.season_menu = new JPopupMenu();

        // Add new season
        this.season_menu.add("Yeni").addActionListener(e -> {
            SeasonView seasonView = new SeasonView();
            seasonView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable();
                }
            });
            seasonView.setVisible(true); // Make the SeasonView visible
        });

        // Update season
        this.season_menu.add("Güncelle").addActionListener(e -> {
            int selectedRow = tbl_season.getSelectedRow();
            if (selectedRow != -1) {
                int selectedSeasonId = (int) tmdl_season.getValueAt(selectedRow, 0);
                Season selectedSeason = this.seasonManager.getById(selectedSeasonId);
                SeasonView seasonView = new SeasonView();
                seasonView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadSeasonTable();
                    }
                });
                seasonView.setVisible(true); // Make the SeasonView visible
            } else {
                Helper.showMsg("Lütfen güncellemek istediğiniz sezonu seçin.");
            }
        });
        this.season_menu.addSeparator();

        // Delete season
        this.season_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedRow = tbl_season.getSelectedRow();
                if (selectedRow != -1) {
                    int selectedSeasonId = (int) tmdl_season.getValueAt(selectedRow, 0);
                    if (this.seasonManager.delete(selectedSeasonId)) {
                        Helper.showMsg("done");
                        loadSeasonTable();
                    } else {
                        Helper.showMsg("error");
                    }
                } else {
                    Helper.showMsg("Lütfen silmek istediğiniz sezonu seçin.");
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

    private void createUIComponents() throws ParseException {
        this.txtf_date_first_room = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_date_first_room.setText("01/01/2024");
        this.txtf_date_finish_room = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txtf_date_finish_room.setText("01/05/2024");
    }
}