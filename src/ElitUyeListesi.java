import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElitUyeListesi {
    UyeYonetimSistemi uyeYonetimSistemi = new UyeYonetimSistemi();
    ArrayList<Uye> elitArrayList = uyeYonetimSistemi.uyeler;
    public File dosya = new File("elit_uyeler.txt");

    public ElitUyeListesi() {
        try {
            Scanner scanner = new Scanner(dosya);//dosyayı ,Scanner sınıfı ile oku
            while (scanner.hasNextLine()) {//yeni satırda yazı varsa
                String[] satir = scanner.nextLine().split("\t");// satırı tab'e' göre böl
                ElitUye elitUye = new ElitUye(satir[0], satir[1], satir[2]); //bilgileri elitUye nesnesine atama
                uyeEkle(elitUye);//ElitUye nesnesini elitArrayList'e ekle
            }
            scanner.close();//scanner kapat
        } catch (IOException e) {//dosya okuma hata mesajı
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }

    public void uyeEkle(Uye uye) {
        elitArrayList.add(uye); // elitArrayList'e yeni üye ekleme
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) { //dosyaya yazma işlemi
            for (Uye uye1 : elitArrayList) {
                writer.write(uye1.getAdi() + "\t" + uye1.getSoyadi() + "\t" + uye1.getEmail() + "\n"); //listeyi dosyaya yaz
            }
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage()); //dosya yazma hata mesajı
        }
    }

    public static class ElitUye extends Uye { //Uye sınıfından kalıtım
        public ElitUye(String adi, String soyadi, String email) {
            super(adi, soyadi, email); // üst sınıfın yapıcı metoduna parametreler aktarılıyor
        }
    }

}
