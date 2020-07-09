/**
 * 
 */
package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author ville
 * @version 30.6.2020
 *
 */
public class Kirjailija {
    
    private int tunnusNro;
    private int kirjaNro;
    private String nimi;
    private static int seuraavaNro = 1;
    
    
    /**
     * alustetaan kirjailija
     */
    public Kirjailija() {
        //ei vielä tarvita mitään
    }
    
    
    /**
     * alustetaan kirjailija
     * @param nimi kirjailijan nimi
     */
    public Kirjailija(String nimi) {
        this.nimi = nimi;
    }
    
    
    /**
     * alustetaan kirjailija
     * @param nimi kirjailijan nimi
     * @param kirjaNro kirja
     */
    public Kirjailija(String nimi, int kirjaNro) {
        this.nimi = nimi;
        this.kirjaNro = kirjaNro;
    }
    
    
    /**
     * alustetaan tietyn kirjan kirjailija
     * @param kirjaNro kirjan viitenumero
     */
    public Kirjailija(int kirjaNro) {
        this.kirjaNro = kirjaNro;
    }
    
    
    /**
     * @return kirjailijan nimi
     * @example
     * <pre name="test">
     *   Kirjailija sapiens = new Kirjailija("Shelley");
     *   sapiens.getNimi() =R= "Shelley .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * apumetodi, jolla saadaan täytettyä testiarvot Kirjailijalle
     */
    public void vastaaHarari() {
        nimi = "Yuval Noah Harari";
    }
    
    
    /**
     * apumetodi, jolla saadaan täytettyä testiarvot Kirjailijalle
     * @param nro kirja
     */
    public void vastaaHarari(int nro) {
        kirjaNro = nro;
        nimi = "Yuval Noah Harari";
    }
    
    
    /**
     * tulostetaan kirjailijan nimi
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Kirjoittanut: " + nimi);
    }
    
    
    /**
     * tulostetatan kirjan tiedot
     * @param os tietovirta johon tulostetataan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa kirjailijalle seuraavan rekisterinumeron.
     * @return kirajilijan uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Kirjailija kirj = new Kirjailija();
     *   kirj.getTunnusNro() === 0;
     *   kirj.rekisteroi();
     *   Kirjailija kirja = new Kirjailija();
     *   kirja.rekisteroi();
     *   int n1 = kirj.getTunnusNro();
     *   int n2 = kirja.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
 
 
    /**
     * Palautetaan kirjailijan oma id
     * @return kirjailijan id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
 
 
    /**
     * Palautetaan mille kirjalle kirjailija kuuluu
     * @return kirjan id
     */
    public int getKirjaNro() {
        return kirjaNro;
    }
    

    /**
     * @param args käytössä
     */
    public static void main(String[] args) {
    Kirjailija kirj = new Kirjailija();
    kirj.vastaaHarari();
    kirj.tulosta(System.out);
    Kirjailija uusi = new Kirjailija("Shelley");
    uusi.tulosta(System.out);
    }

}
