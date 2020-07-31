package kirjahylly;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 */
public class Kirja implements Cloneable, Tietue{
    
    private int tunnusNro;
    private String nimi  = "";
    private int kirjailijaId = 0;
    private String genre  = "";
    private int julkaisuVuosi  = 0;
    private int sivumaara  = 0;
    private String tila  = "";
    private String lukuPvm  = "";
    private int arvio = 0;
    private String kommentit = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Vertailija nimen mukaan
     */
    public static class Vertailija implements Comparator<Kirja> {
        
        private int jarjestaEhto;
        /**
         * 
         * @param jarjestaEhto millä ehdolla järjestetään
         */
        public Vertailija(int jarjestaEhto) {
            this.jarjestaEhto = jarjestaEhto;
        }

        @Override
        public int compare(Kirja kirja1, Kirja kirja2) {
            return kirja1.getJarjesta(jarjestaEhto).compareTo(kirja2.getJarjesta(jarjestaEhto));
        }
        
    }
    
    
    /**
     * @param ehto miten järjestetään
     * @return kentän sisältö merkkijonona
     */
    public String getJarjesta(int ehto){
        switch ( ehto ) {
        case 0: return "" + nimi.toUpperCase();
        case 1: return "" + nimi.toUpperCase();
        case 2: return "" + arvio;
        case 3: return "" + tila;
        default: return "defaultti";
        }
    }
    
    
    /** 
     * Palauttaa kirjan tekstikenttien lukumäärän 
     * @return kenttien lukumäärä 
     */ 
    @Override
    public int getKenttia() { 
        return 10; 
    } 
 
 
    /** 
     * Eka kenttä joka on mielekäs kysyttäväksi 
     * @return ekan kentän indeksi 
     */ 
    @Override
    public int ekaKentta() { 
        return 1; 
    }
    
    /**
     * alustetaan tietyn kirjailijan kirja
     * @param kirjailijaId kirjailijan viitenumero
     */
    public Kirja(int kirjailijaId) {
        this.kirjailijaId = kirjailijaId;
    }
    
    
    /**
     * tyhjän kirjan alustus
     */
    public Kirja() {
        // attribuuttien alustus riittää
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
        case 2: return "" + kirjailijaId; 
        case 3: return "" + genre; 
        case 4: return "" + julkaisuVuosi; 
        case 5: return "" + sivumaara; 
        case 6: return "" + tila; 
        case 7: return "" + lukuPvm; 
        case 8: return "" + arvio; 
        case 9: return "" + kommentit; 
        default: return "Äääliö"; 
        } 
    } 
 
 
    /** 
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon 
     * @param k kuinka monennen kentän arvo asetetaan 
     * @param jono jonoa joka asetetaan kentän arvoksi 
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus. 
     * @example 
     * <pre name="test"> 
     *   Kirja kirja = new Kirja(); 
     *   kirja.aseta(1,"Sapiens") === null; 
     *   kirja.aseta(4,"baeraa") === "Vain numeroita julkaisuvuoteen";  
     *   kirja.aseta(4,"2014") === null;  
     *   kirja.aseta(5,"kissa") === "Vain numeroita sivumäärään"; 
     *   kirja.aseta(5,"211") === null; 
     * </pre> 
     */ 
    @Override
    public String aseta(int k, String jono) { 
        String tjono = jono.trim(); 
        StringBuffer sb = new StringBuffer(tjono); 
        switch ( k ) { 
        case 0: 
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro())); 
            return null; 
        case 1: 
            nimi = tjono; 
            if (nimi == "") return "kirjalle täytyy antaa nimi";
            return null; 
        case 2: 
            kirjailijaId  = Mjonot.erota(sb, '§', kirjailijaId); 
            return null; 
        case 3: 
            if ( !tjono.toUpperCase().matches("[(A-Z)(,)(\\-)(\\s)]*")) return "Vain kirjaimet ja , - sallittu";
            genre = tjono; 
            return null; 
        case 4: 
            if ( !tjono.matches("[0-9]*")) return "Vain numeroita julkaisuvuoteen";
            try { 
                julkaisuVuosi = Mjonot.erotaEx(sb, '§', julkaisuVuosi); 
            } catch ( NumberFormatException ex ) { 
                return "Ei kirjaimia julkaisuvuoteen"; 
            } 
            return null; 
        case 5: 
            if ( !tjono.matches("[0-9]*")) return "Vain numeroita sivumäärään";
            try { 
                sivumaara = Mjonot.erotaEx(sb, '§', sivumaara); 
            } catch ( NumberFormatException ex ) { 
                return "Ei kirjaimia sivumäärään"; 
            } 
            return null; 
        case 6: 
            tila = tjono; 
            return null; 
        case 7: 
            lukuPvm = tjono; 
            return null; 
        case 8: 
            arvio = Mjonot.erota(sb, '§', arvio); 
            return null; 
        case 9: 
            kommentit = tjono;
            return null; 
        default: 
            return "defaultti"; 
        } 
    } 

    
    /** 
     * Palauttaa k:tta kirjan kenttää vastaavan kysymyksen 
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen) 
     * @return k:netta kenttää vastaava kysymys 
     */ 
    @Override
    public String getKysymys(int k) { 
        switch ( k ) { 
        case 0: return "Tunnus nro"; 
        case 1: return "Nimi"; 
        case 2: return "Kirjailija"; 
        case 3: return "Genre"; 
        case 4: return "Julkaisuvuosi"; 
        case 5: return "Sivumäärä"; 
        case 6: return "Tila"; 
        case 7: return "Luku pvm"; 
        case 8: return "Arvio"; 
        case 9: return "Kommentit"; 
        default: return "defaultti"; 
        } 
    } 
    

    /**
     * @return kirjan nimi
     * @example
     * <pre name="test">
     *   Kirja sapiens = new Kirja();
     *   sapiens.taytaSapiensTiedoilla();
     *   sapiens.getNimi() =R= "Sapiens: Ihmisen lyhyt historia.*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     */
    public void taytaSapiensTiedoilla() {
        nimi = "Sapiens: Ihmisen lyhyt historia";
        genre = "Tietokirjallisuus";
        julkaisuVuosi = 2011;
        sivumaara = 491;
        tila = "luettu";
        lukuPvm = "3.2020";
        arvio = 8;
        kommentit = "hauska";
    }
    
    
    /**
     * Lisätään kirjalla kirjailija
     * @param kirjailijanId kirjailijan viitenumero
     */
    public void lisaaKirjailija(int kirjailijanId) {
        kirjailijaId = kirjailijanId;
    }
    
    
    /**
     * palauttaa kirjan kirjailijan tunnusnumeron
     * @return kirjailijan Id
     * 
     */
    public int getKirjailijaId() {
        return this.kirjailijaId;
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
     * @param s kirjalle annettava nimi
     * @return virheilmoitus tai null, jos ok
     */
    public String setNimi(String s) {
        this.nimi = s;
        return null;
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
     * @return genre
     */
    public String getGenre() {
        return this.genre;
    }


    /**
     * @return sivumäärä
     */
    public int getSivut() {
        return this.sivumaara;
    }


    /**
     * @return julkaisuvuosi
     */
    public int getVuosi() {
        return this.julkaisuVuosi;
    }


    /**
     * @return tila, luettu tai lukematta
     */
    public String getTila() {
        return this.tila;
    }


    /**
     * @return luku päivämäärä
     */
    public String getPvm() {
        return this.lukuPvm;
    }


    /**
     * @return arvio
     */
    public int getArvio() {
        return this.arvio;
    }


    /**
     * @return kommentit
     */
    public String getKommentit() {
        return this.kommentit;
    }
    
    
    /**
     * @param s genrelle annettava nimi
     * @return virheilmoitus tai null, jos ok
     */
    public String setGenre(String s) {
        this.genre = s;
        return null;
    }

    
    /**
     * @param s sivumäärälle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setSivut(int s) {
        this.sivumaara = s;
        return null;
    }

    
    /**
     * @param s julkaisuvuodelle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setVuosi(int s) {
        this.julkaisuVuosi = s;
        return null;
    }

    
    /**
     * @param s tilalle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setTila(String s) {
        this.tila = s;
        return null;
    }

    
    /**
     * @param s luku päivämäärälle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setPvm(String s) {
        this.lukuPvm = s;
        return null;
    }

    /**
     * @param s arviolle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setArvio(int s) {
        this.arvio = s;
        return null;
    }

    
    /**
     * @param s kommenteille annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setKommentit(String s) {
        this.kommentit = s;
        return null;
    }

    
    /**
     * @param s kirjailijanId:lle annettava arvo
     * @return virheilmoitus tai null, jos ok
     */
    public String setKirjailijaId(int s) {
        this.kirjailijaId = s;
        return null;
    }

    
    /**
     * Palauttaa kirjann tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kirja tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("  1  |  Sapiens  | 1 |Tietokirjallisuus");
     *   kirja.toString().startsWith("1|Sapiens|1|Tietokirjallisuus|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String erotin = "";
        for (int i = 0; i < getKenttia(); i++) {
            sb.append(erotin);
            sb.append(anna(i));
            erotin = "|";
        }
        return sb.toString();
    }
    
    
    /**
     * Selvittää kirjan tiedot | erotellusta merkkijonosta
     * Huolehtii että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta kirjan tiedot otetaan
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("   1  |  Sapiens   | 1 | Tietokirjallisuus");
     *   kirja.getTunnusNro() === 1;
     *   kirja.toString().startsWith("1|Sapiens|1|Tietokirjallisuus|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
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
        for (int i = 0; i < getKenttia(); i++) {
            aseta(i, Mjonot.erota(sb, '|'));
        }
    }
    
    
    @Override
    public boolean equals(Object kirja) {
        if (kirja instanceof Kirja ) return equals((Kirja) kirja);
        return false;
    }

    /** 
     * Tutkii onko kirjan tiedot samat kuin parametrina tuodun kirjan tiedot 
     * @param kirja johon verrataan 
     * @return true jos kaikki tiedot samat, false muuten 
     * @example 
     * <pre name="test"> 
     *   Kirja kirja1 = new Kirja(); 
     *   kirja1.parse("   1  |  Sapiens   | 1 | Tietokirjallisuus"); 
     *   Kirja kirja2 = new Kirja(); 
     *   kirja2.parse("   1  |  Sapiens   | 1 | Tietokirjallisuus"); 
     *   Kirja kirja3 = new Kirja(); 
     *   kirja3.parse("   1  |  Sapiens   | 1 | Kauhu"); 
     *   
     *   kirja1.equals(kirja2) === true; 
     *   kirja2.equals(kirja1) === true; 
     *   kirja1.equals(kirja3) === false; 
     *   kirja3.equals(kirja2) === false; 
     * </pre> 
     */ 
    public boolean equals(Kirja kirja) { 
        if ( kirja == null ) return false; 
        for (int k = 0; k < getKenttia(); k++) 
            if ( !anna(k).equals(kirja.anna(k)) ) return false; 
        return true; 
    } 
    

    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    
    @Override
    public Kirja clone() throws CloneNotSupportedException{
        Kirja uusi;
        uusi = (Kirja) super.clone();
        return uusi;
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
