import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//mail gönderme işlemi
public class MailGonder {
    private String kullaniciMaili;
    private String parola;
    public MailGonder(String kullaniciMaili, String parola) {
        this.kullaniciMaili = kullaniciMaili;
        this.parola = parola;
    }

    //mail gönderme işlemi
    public void mailGonderici(String alici, String govde) throws MessagingException {

        // SMTP sunucusu ve gerekli diğer özellikleri belirtiyoruz.
        Properties ozellikler = System.getProperties();
        ozellikler.setProperty("mail.smtp.host", "smtp.gmail.com");
        ozellikler.setProperty("mail.smtp.port", String.valueOf(587));
        ozellikler.setProperty("mail.smtp.starttls.enable", String.valueOf(true));
        ozellikler.setProperty("mail.smtp.auth", "true");

        // Bir oturum başlatıyoruz.
        Session oturum = Session.getDefaultInstance(ozellikler);
        if (oturum != null) {
            try {
                // Bir MIME mesajı oluşturuyoruz ve alıcının e-posta adresini, mesajını belirtiyoruz.
                MimeMessage mesaj = new MimeMessage(oturum);
                mesaj.setFrom(new InternetAddress(kullaniciMaili));
                mesaj.addRecipient(Message.RecipientType.TO, new InternetAddress(alici));
                mesaj.setText(govde);

                // E-postayı gönderiyoruz.
                Transport tasiyici = oturum.getTransport("smtp");
                tasiyici.connect("smtp.gmail.com", kullaniciMaili, parola);
                tasiyici.sendMessage(mesaj, mesaj.getAllRecipients());
                tasiyici.close();
                System.out.println("Mailiniz " + alici + " kişisine başarıyla gönderildi :)");
            } catch (MessagingException mex) {
                // Mesaj gönderme işlemi başarısız olursa, hata mesajı ekrana yazdırılır.
                System.out.println("Göderim basarisiz, exception: " + mex);
            }
        } else {
            System.out.println("Oturum oluşturulamadı!");
        }
    }
}
