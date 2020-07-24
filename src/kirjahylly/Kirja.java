package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author ville
 * @version 9.7.2020
 *
 */
public class Kirja {
    
    private int tunnusNro;
    private String nimi  = "";
    private String genre  = "";
    private int julkaisuVuosi  = 0;
    private String sivumaara  = "";
    private String tila  = "";
    private String lukuPvm  = "";
    private int arvio = 0;
    private String kommentit = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * @return kirjan nimi
     * @example
     * <pre name="test">
     *   Kirja sapiens = new Kirja();
     *   sapiens.taytaSapiensTiedoilla();
     *   sapiens.getNimi() =R= "Sapiens: Ihmisen lyhyt historia .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     * TODO: poista kun kaikki toimii
     */
    public void taytaSapiensTiedoilla() {
        nimi = "Sapiens: Ihmisen lyhyt historia";
        genre = "Tietokirjallisuus";
        julkaisuVuosi = 2011;
        sivumaara = "491";
        tila = "luettu";
        lukuPvm = "3.2020";
        arvio = 8;
        kommentit = "hauska";
    }
   
    
    /**
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro));
        out.println(nimi);
        out.println("genre: " + genre);
        out.println("julkaistu: " + julkaisuVuosi);
        out.println("sivumäärä: " + sivumaara);
        out.println(tila + ": " + lukuPvm +
                " arvio: " + arvio);
        out.println("kommentit: " + kommentit);
    }
    
    /**
     * Tulostetaan kirjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
 
    /**
     * Antaa kirjalle seuraavan rekisterinumeron.
     * @return kirjan uusi tunnusNro
     * @example
     * <pre name="test">
     *   Kirja sapiens = new Kirja();
     *   sapiens.getTunnusNro() === 0;
     *   sapiens.rekisteroi();
     *   Kirja frankenstein = new Kirja();
     *   frankenstein.rekisteroi();
     *   int n1 = sapiens.getTunnusNro();
     *   int n2 = frankenstein.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
           return tunnusNro;
    }
    
    
    /**
     * Palauttaa kirjan tunnusnumeron.
     * @return kirjan tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    
    /**
     * Palauttaa kirjann tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kirja tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("  1  |  Sapiens  | Tietokirjallisuus");
     *   kirja.toString().startsWith("1|Sapiens|Tietokirjallisuus|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                nimi + "|" +
                genre + "|" +
                julkaisuVuosi + "|" +
                sivumaara + "|" +
                tila + "|" +
                lukuPvm + "|" +
                arvio + "|" +
                kommentit;
    }
    
    
    /**
     * Selvittää kirjan tiedot | erotellusta merkkijonosta
     * Huolehtii että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta kirjan tiedot otetaan
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("   1  |  Sapiens   | Tietokirjallisuus");
     *   kirja.getTunnusNro() === 1;
     *   kirja.toString().startsWith("1|Sapiens|Tietokirjallisuus|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *  
     *   kirja.rekisteroi();
     *   int n = kirja.getTunnusNro();
     *   kirja.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   kirja.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   kirja.getTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        genre = Mjonot.erota(sb, '|', genre);
        julkaisuVuosi = Mjonot.erota(sb, '|', julkaisuVuosi);
        sivumaara = Mjonot.erota(sb, '|', sivumaara);
        tila = Mjonot.erota(sb, '|', tila);
        lukuPvm = Mjonot.erota(sb, '|', lukuPvm);
        arvio = Mjonot.erota(sb, '|', arvio);
        kommentit = Mjonot.erota(sb, '|', kommentit);
    }
    
    
    @Override
    public boolean equals(Object kirja) {
        if ( kirja == null ) return false;
        return this.toString().equals(kirja.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }

    

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    Kirja sapiens = new Kirja();
    
    sapiens.rekisteroi();
    sapiens.taytaSapiensTiedoilla();
    sapiens.tulosta(System.out);
    
    }

}
