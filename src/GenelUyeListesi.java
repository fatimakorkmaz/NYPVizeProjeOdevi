import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GenelUyeListesi {

    UyeYonetimSistemi uyeYonetimSistemi = new UyeYonetimSistemi();
    ArrayList<Uye> genelArrayList = uyeYonetimSistemi.uyeler;
    public File dosya = new File("genel_uyeler.txt");

    public GenelUyeListesi() {
        try {
            Scanner scanner = new Scanner(dosya); //dosyayı ,Scanner sınıfı ile oku
            while (scanner.hasNextLine()) { //yeni satırda yazı varsa
                String[] satir = scanner.nextLine().split("\t"); // satırı tab'e' göre böl
                GenelUye genelUye = new GenelUye(satir[0], satir[1], satir[2]); //bilgileri genelUye nesnesine atama
                uyeEkle(genelUye); //GenelUye nesnesini genelArrayList'e ekle
            }
            scanner.close(); //scanner kapat
        } catch (IOException e) { //dosya okuma hata mesajı
            System.out.println("Dosya okuma hatası:" + e.getMessage());
        }
    }

    public void uyeEkle(Uye uye) {
        genelArrayList.add(uye); // genelArrayList'e yeni üye ekleme
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) { //dosyaya yazma işlemi
            for (Uye uye1 : genelArrayList) {
                writer.write(uye1.getAdi() + "\t" + uye1.getSoyadi() + "\t" + uye1.getEmail() + "\n"); //listeyi dosyaya yaz
            }
        } catch (IOException e) { //dosya yazma hata mesajı
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    public static class GenelUye extends Uye {//Uye sınıfından kalıtım
        public GenelUye(String adi, String soyadi, String email) {
            super(adi, soyadi, email);  // üst sınıfın yapıcı metoduna parametreler aktarılıyor
        }
    }

}
