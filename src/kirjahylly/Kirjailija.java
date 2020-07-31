package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 */
public class Kirjailija implements Cloneable, Tietue {
    
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


    /** 
     * Palauttaa kirjan kenttien lukumäärän 
     * @return kenttien lukumäärä 
     */ 
    @Override
    public int getKenttia() {
        return 2;
    }

    
    /** 
     * Eka kenttä joka on mielekäs kysyttäväksi 
     * @return ekan kentän indeksi 
     */ 
    @Override
    public int ekaKentta() {
        return 1;
    }


    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "id";
            case 1:
                return "nimi";
        default:
            return "defaultti";
        }
    }

    
    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 
    @Override
    public String anna(int k) {
        switch ( k ) { 
        case 0: return "" + tunnusNro; 
        case 1: return "" + nimi;
        default:
            return "defaultti"; 
        }
    }


    /** 
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon 
     * @param k kuinka monennen kentän arvo asetetaan 
     * @param s jono joka asetetaan kentän arvoksi 
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus. 
     * @example 
     * <pre name="test"> 
     *   Kirjailija kirjailija = new Kirjailija(); 
     *   kirjailija.aseta(1,"Harari") === null; 
     *   kirjailija.aseta(1,"24") === "Vain kirjaimia ja '-' nimeen";  
     *   kirjailija.aseta(1,"Mörkö") === null;
     * </pre> 
     */ 
    @Override
    public String aseta(int k, String s) {
        String tjono = s.trim(); 
        StringBuffer sb = new StringBuffer(tjono); 
        switch ( k ) { 
        case 0: 
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro())); 
            return null; 
        case 1: 
            if ( !tjono.toUpperCase().matches("[(A-Z)(ÖÄÅ)]*") ) return "Vain kirjaimia ja '-' nimeen";
            nimi = tjono;
            return null;
        default:
           return "defaultti"; 
        }
    }


    @Override
    public Kirjailija clone() throws CloneNotSupportedException {
        return(Kirjailija)super.clone();
    }

}
