package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    // Temayı ayarlayan metot
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());// Sayfanın teması
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    // Mesaj gösteren metot
    public static void showMsg(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill":
                // Tüm alanları doldurun mesajı
                msg = "Lütfen tüm alanları doldurun.";
                title = "Hata!";
                break;
            case "done":
                // Başarılı mesajı
                msg = "Başarılı";
                title = "Sonuç";
                break;
            case "notFound":
                // Bulunamadı mesajı
                msg = str + " Bulunamadı!";
                title = "Bulunamadı.";
                break;
            case "error":
                // Hata mesajı
                msg = "Yanlış bir işlem yaptınız!";
                title = "Hata!";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;
        if (str.equals("Sure")) {
            // İşlemi yapmak istediğinizden emin misiniz? mesajı
            msg = "Bu işlemi yapmak istediğinizden emin misiniz?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Emin misiniz ?", JOptionPane.YES_NO_OPTION) == 0;
    }

    // JTextField'ın boş olup olmadığını kontrol eden metot
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    // JTextField dizisinin boş olup olmadığını kontrol eden metot
    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    // Pencerenin konumunu ayarlayan metot
    public static int getLocationPoint(String type, Dimension size) {
        // Pencere tipine göre konum belirle
        switch (type) {
            case "x":
                return (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y":
                return (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default:
                return 0;
        }
    }
}
