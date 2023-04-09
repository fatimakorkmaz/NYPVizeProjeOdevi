import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UyeYonetimSistemi {
    public ArrayList<Uye> uyeler = new ArrayList<Uye>();

    public static void main(String[] args) throws MessagingException {

        Scanner scanner = new Scanner(System.in);
        ElitUyeListesi elitUyeListesi = new ElitUyeListesi();//ElitUyeListesi nesnesi
        GenelUyeListesi genelUyeListesi = new GenelUyeListesi();//GenelUyeListesi nesnesi
        //hesap parolası için önce kullanılacak google test hesabın iki faktörlü doğrulaması aktif edilmeli
        // ardından uygulaması şifrelerinden posta uygulaması ve kullanılacak cihaz seçildikten sonra oluşturulacak uygulama şifresi kullanılmalı.
        // Google hesap şifresi ile çalışmıyor.
        MailGonder mailGonder = new MailGonder( "mailAdresi@gmail.com", "parola");

        while (true) {
            System.out.print("1 - Elit üye ekleme\n2 - Genel üye ekleme\n3 - Mail Gönderme\n4 - Çıkış\nSeçim: ");

            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {//ilk menu
                case 1://scanner ile elit uye eklemek
                    System.out.print("İsim: ");
                    String elitUyeAdi = scanner.nextLine();
                    System.out.print("Soyisim: ");
                    String elitUyeSoyadi = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String elitUyeEmail = scanner.nextLine();
                    ElitUyeListesi.ElitUye elitUye = new ElitUyeListesi.ElitUye(elitUyeAdi, elitUyeSoyadi, elitUyeEmail);
                    elitUyeListesi.uyeEkle(elitUye);
                    break;
                case 2://scanner ile genel uye eklemek
                    System.out.print("İsim: ");
                    String genelUyeAdi = scanner.nextLine();
                    System.out.print("Soyisim: ");
                    String genelUyeSoyadi = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String genelUyeEmail = scanner.nextLine();
                    GenelUyeListesi.GenelUye genelUye = new GenelUyeListesi.GenelUye(genelUyeAdi, genelUyeSoyadi, genelUyeEmail);
                    genelUyeListesi.uyeEkle(genelUye);
                    break;
                case 3://mail menusue giris
                    System.out.print("1 - Elit üyelere mail\n2 - Genel üyelere mail\n3 - Tüm üyelere mail\nSeçim: ");
                    int mailSecim = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Mail içeriği: ");
                    String mailIcerik = scanner.nextLine();
                    ArrayList<String> mail = new ArrayList<>();
                    switch (mailSecim) {//mail gonderme menusu
                        case 1://elit uyelere mail
                            mail = getMail(elitUyeListesi.dosya);
                            for (int i = 0; i < elitUyeListesi.elitArrayList.size(); i++) {
                                mailGonder.mailGonderici(mail.get(i), mailIcerik);
                            }
                            break;
                        case 2://Genel uyelere mail
                            mail = getMail(genelUyeListesi.dosya);
                            for (int i = 0; i < genelUyeListesi.genelArrayList.size(); i++) {
                                mailGonder.mailGonderici(mail.get(i), mailIcerik);
                            }
                            break;
                        case 3://elit&genel mail
                            mail = getMail(elitUyeListesi.dosya);
                            for (int i = 0; i < elitUyeListesi.elitArrayList.size(); i++) {
                                mailGonder.mailGonderici(mail.get(i), mailIcerik);
                            }
                            mail = getMail(genelUyeListesi.dosya);
                            for (int i = 0; i < genelUyeListesi.genelArrayList.size(); i++) {
                                mailGonder.mailGonderici(mail.get(i), mailIcerik);
                            }
                            break;
                        default://hatalı tercih
                            System.out.println("Geçersiz seçim");
                            break;
                    }
                    break;
                case 4://cıkıs
                    System.exit(0);
                    break;
                default://hatalı tercih
                    System.out.println("Geçersiz seçim");
                    break;
            }
        }
    }

    public static ArrayList<String> getMail(File dosya) {
        ArrayList<String> mail = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {//dosya okuma
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] kelimeler = satir.split("\t");// Boşluk karakterine göre sütunları ayırıyoruz
                String sonKelime = kelimeler[kelimeler.length - 1];// Son sütunu alıyoruz
                mail.add(sonKelime);// Son sütunu diziye yazdırıyoruz
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
        return mail;
    }

}
