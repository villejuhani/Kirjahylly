/**
 * 
 */
package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author ville
 * @version 24.7.2020
 *
 */
public class Kirjailija {
    
    private int tunnusNro;
    private String nimi;
    private static int seuraavaNro = 1;
    
    
    /**
     * alustetaan kirjailija
     */
    public Kirjailija() {
        //attribuuttien alustus riittää
    }
    
    
    /**
     * alustetaan kirjailija
     * @param nimi kirjailijan nimi
     */
    public Kirjailija(String nimi) {
        this.nimi = nimi;
    }
    
    
    /**
     * @return kirjailijan nimi
     * @example
     * <pre name="test">
     *   Kirjailija sapiens = new Kirjailija("Shelley");
     *   sapiens.getNimi() =R= "Shelley";
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
    * Asettaa tunnusnumeron ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava tunnusnumero
    */
   private void setTunnusNro(int nr) {
       tunnusNro = nr;
       if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
   }


   /**
    * Palauttaa kirjailijan tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return kirjailija tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Kirjailija kirjailija = new Kirjailija();
    *   kirjailija.parse("   2   | Yuval Noah Harari");
    *   kirjailija.toString()    === "2|Yuval Noah Harari";
    * </pre>
    */
   @Override
   public String toString() {
       return "" + getTunnusNro() + "|" + nimi;
   }


   /**
    * Selvittää kirjailijan tiedot | erotellusta merkkijonosta.
    * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
    * @param rivi josta kirjailijan tiedot otetaan
    * @example
    * <pre name="test">
    *   Kirjailija kirjailija = new Kirjailija();
    *   kirjailija.parse("   2   | Yuval Noah Harari");
    *   kirjailija.toString()    === "2|Yuval Noah Harari";
    *   
    *   kirjailija.rekisteroi();
    *   int n = kirjailija.getTunnusNro();
    *   kirjailija.parse(""+(n+20));
    *   kirjailija.rekisteroi();
    *   kirjailija.getTunnusNro() === n+20+1;
    *   kirjailija.toString()     === "" + (n+20+1) + "|Yuval Noah Harari";
    * </pre>
    */
   public void parse(String rivi) {
       StringBuilder sb = new StringBuilder(rivi);
       setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
       nimi = Mjonot.erota(sb, '|', nimi);
   }


   @Override
   public boolean equals(Object obj) {
       if ( obj == null ) return false;
       return this.toString().equals(obj.toString());
   }
   

   @Override
   public int hashCode() {
       return tunnusNro;
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
