package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
    private JButton btn_login;
    private JLabel lbl_user;
    private JLabel lbl_pass;
    private UserManager userManager;
    private JPanel container;
    private JTextField txtf_user;
    private JPasswordField txtf_pass;

    // Kurucu metod
    public LoginView() {
        this.add(container);
        this.guiInitilaze(450, 450);
        this.userManager = new UserManager();

        // Giriş butonuna tıklama olayı için ActionListener tanımlaması
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtf_user.getText();
                String pass = new String(txtf_pass.getPassword()); // Şifreyi güvenli şekilde al

                if (username.isEmpty() || pass.isEmpty()) {
                    Helper.showMsg("Kullanıcı adı ve şifre boş bırakılamaz.");
                    return;
                }

                User entryUser = userManager.findByLoging(username, pass);
                if (entryUser == null) {
                    Helper.showMsg("Kullanıcı bulunamadı. Lütfen tekrar deneyiniz.");
                } else {
                    // Kullanıcı rolüne göre ilgili görünüme geçiş
                    if (entryUser.getRole().equals("admin")) {
                        AdminView adminView = new AdminView(entryUser);
                        adminView.setVisible(true);
                        dispose(); // Giriş ekranını kapat
                    } else if (entryUser.getRole().equals("employee")) {
                        EmployeeView employeeView = new EmployeeView(entryUser);
                        employeeView.setVisible(true);
                        dispose();// Giriş ekranını kapat
                    }else if (entryUser.getRole().equals("personel")) {
                        EmployeeView employeeView = new EmployeeView(entryUser);
                        employeeView.setVisible(true);
                        dispose(); // Giriş ekranını kapat
                    }else {
                        Helper.showMsg("Geçersiz kullanıcı rolü."); // Diğer rolleri de ele alabilirsiniz
                    }
                }
            }
        });
    }
}
