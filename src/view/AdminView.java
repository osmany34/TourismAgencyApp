package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JTable tbl_user;
    private JComboBox<ComboItem> cmb_user;
    private JButton btn_search;
    private JButton btn_clear;
    private JButton btn_logout;
    private JTextField txtf_username;
    private JTextField txtf_password;
    private JComboBox cmb_user_role;
    private JButton btn_add;
    private JButton btn_delete;
    private JLabel lbl_welcome;
    private JButton btn_new_user;
    private JScrollPane scrl_user;
    private JPanel w_top;
    private JPanel w_bot;

    private User user;
    private UserManager userManager;
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private Object[] col_user;
    private JPopupMenu user_menu;


    // Kurucu metot
    public AdminView(User user) {
        // Değişkenleri başlat
        this.user_menu = new JPopupMenu();
        this.col_user = col_user;
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;

        // Kullanıcı girişi yoksa pencereyi kapat
        if (user == null) {
            dispose();

        }

        // Kullanıcı bilgisini etiketi
        this.lbl_welcome.setText("Kullanıcı : " + this.user.getUsername());

        // Kullanıcı tablosunu yükle ve satır seçme olayı ekle
        loadUserTable(null);
        tableRowSelect(tbl_user);

        // Çıkış butonuna dinleyici ekle
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                dispose();
            }
        });

        // Kullanıcı ekleme butonuna dinleyici ekle
        btn_add.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldListEmpty(new JTextField[]{txtf_username, txtf_password})) {
                    Helper.showMsg("fill");
                } else {
                    boolean result;
                    User user1 = new User();
                    if (getUserUpdated() != null) {
                        user1 = getUserUpdated();
                    }


                    user1.setUsername(txtf_username.getText());
                    user1.setPassword(txtf_password.getText());
                    user1.setRole((String) cmb_user_role.getSelectedItem());

                    if (btn_add.getText().equals("UPDATE")) {
                        result = userManager.update(user1);

                    } else {
                        result = userManager.save(user1);
                    }


                    if (result) {
                        Helper.showMsg("done");
                        loadUserTable(null);
                    } else {
                        Helper.showMsg("error");

                    }
                }
            }
        });

        // Kullanıcı silme butonuna dinleyici ekle
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.confirm("sure")) {

                    int selectUserId = getTableSelectedRow(tbl_user, 0);
                    if (userManager.delete(selectUserId)) {
                        Helper.showMsg("done");
                        loadUserTable(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });

        // Kullanıcı arama butonuna dinleyici ekle
        btn_search.addActionListener(e -> {
            String selectedUser = (String) this.cmb_user.getSelectedItem();
            ArrayList<User> userListBySearch = this.userManager.searchForTable(selectedUser);
            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(col_user.length, userListBySearch);
            loadUserTable(userRowListBySearch);

        });
        tableRowSelect(tbl_user);

        // Yeni kullanıcı butonuna dinleyici ekle
        this.btn_new_user.addActionListener(e -> {
            this.txtf_username.setEnabled(true);
            this.txtf_password.setEnabled(true);
            this.cmb_user_role.setEnabled(true);
            this.btn_add.setEnabled(true);
            this.txtf_username.setText(null);
            this.txtf_password.setText(null);
            this.cmb_user_role.setSelectedItem("ADMIN");
            this.btn_add.setText("Ekle");
            setUserUpdated(null);

        });

        // Kullanıcı güncelleme seçeneğini içeren sağ tıklama menüsü ekle
        this.user_menu.add("Güncelle").addActionListener(e -> {
            this.txtf_username.setEnabled(true);
            this.txtf_password.setEnabled(true);
            this.cmb_user_role.setEnabled(true);
            this.btn_add.setEnabled(true);
            int selectUserId = this.getTableSelectedRow(tbl_user, 0);
            User userUpdate = this.userManager.getById(selectUserId);
            this.txtf_username.setText(userUpdate.getUsername());
            this.txtf_password.setText(userUpdate.getPassword());
            this.cmb_user_role.setSelectedItem(userUpdate.getRole());
            this.btn_add.setText("Güncelle");
            setUserUpdated(userUpdate);
        });
        tbl_user.setComponentPopupMenu(user_menu);

        // Kullanıcı tablosunu temizleme butonuna dinleyici ekle
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmb_user.setSelectedItem(null);
                loadUserTable(null);
            }
        });
    }

    // Kullanıcı tablosunu yükleme metodu
    public void loadUserTable(ArrayList<Object[]> userList) {

        this.col_user = new Object[]{"ID", "Kullanıcı", "Şifre", "Rol"};
        if (userList == null) {
            userList = this.userManager.getForTable(this.col_user.length, this.userManager.findAll());
        }
        createTable(this.tmdl_user, this.tbl_user, col_user, userList);
    }

    // Tablodaki satırı seçme olayı
    public void tableRowSelect(JTable table) {
        table.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseReleased(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    private User getUserUpdated() {
        return user;

    }

    private void setUserUpdated(User user) {
        this.user = user;
    }

}


