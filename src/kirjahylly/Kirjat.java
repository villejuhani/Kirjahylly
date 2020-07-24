/**
 * 
 */
package kirjahylly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author ville
 * @version 24.7.2020
 *
 */
public class Kirjat implements Iterable<Kirja> {
    private static final int MAX_KIRJOJA   = 8;
    private boolean          muutettu = false;
    private int              lkm           = 0;
    private String           kokoNimi = "";
    private String           tiedPerusNimi = "kirjat";
    private Kirja            alkiot[]      = new Kirja[MAX_KIRJOJA];


    /**
     * Oletusmuodostaja
     */
    public Kirjat() {
        // Attribuuttien oma alustus riittää
    }


    /**
     * Lisää uuden kirjan tietorakenteeseen.  Ottaa kirjan omistukseensa.
     * @param kirja lisättävän kirjan viite.  Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Kirjat kirjat = new Kirjat();
     * Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     * kirjat.getLkm() === 0;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 1;
     * kirjat.lisaa(kirja2); kirjat.getLkm() === 2;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 3;
     * kirjat.anna(0) === kirja1;
     * kirjat.anna(1) === kirja2;
     * kirjat.anna(2) === kirja1;
     * kirjat.anna(1) == kirja1 === false;
     * kirjat.anna(1) == kirja2 === true;
     * kirjat.anna(3) === kirja1; #THROWS IndexOutOfBoundsException 
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 4;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 5;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 6;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 7;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 8;
     * kirjat.lisaa(kirja1); kirjat.getLkm() === 9;
     * </pre>
     */
    public void lisaa(Kirja kirja) {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, alkiot.length + 5);
        alkiot[lkm] = kirja;
        lkm++;
        muutettu = true;
    }


    /**
     * Palauttaa viitteen i:teen kirjaan.
     * @param i monennenko kirjan viite halutaan
     * @return viite kirjaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee kirjat tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Kirjat kirjat = new Kirjat();
     *  Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     *  kirja1.taytaSapiensTiedoilla();
     *  kirja2.taytaSapiensTiedoilla();
     *  String hakemisto = "testikirjat";
     *  String tiedNimi = hakemisto+"/kirjat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  kirjat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjat.lisaa(kirja1);
     *  kirjat.lisaa(kirja2);
     *  kirjat.tallenna();
     *  kirjat = new Kirjat();            // Poistetaan vanhat luomalla uusi
     *  kirjat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Kirja> i = kirjat.iterator();
     *  i.next() === kirja1;
     *  i.next() === kirja2;
     *  i.hasNext() === false;
     *  kirjat.lisaa(kirja2);
     *  kirjat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedNimi())))){
            
           // tiedostonNimi = fi.nextLine();
           // if (tiedostonNimi == null) throw new SailoException("Kirjahyllyn nimi puuttuu");
           
           // if (rivi == null) throw new SailoException("Maksimikoko puuttuu");
            
            
            while (fi.hasNext()) {
                String rivi = fi.nextLine();
                rivi = rivi.trim();
               // if ( "".equals(rivi) || rivi.charAt(0) == ';') continue;
                Kirja kirja = new Kirja();
                kirja.parse(rivi);
                lisaa(kirja);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedNimi() + " ei aukea");
        }

    }
    
    
   /**
    * Luetaan aikaisemmin annetun nimisestä tiedostosta
    * @throws SailoException jos tulee poikkeus
    */
   public void lueTiedostosta() throws SailoException {
       lueTiedostosta(getTiedostonPerusNimi());
   }



    /**
     * Tallentaa kirjat tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     * Tiedoston muoto:
     * <pre>
     * 2|Sapiens|Tietokirjallisuus|2011|491|luettu|||3.2020|8|hauska
     * 3|Frankenstein|Kauhu, romantiikka|1800|200|luettu|2017|||7|-
     * </pre>
     */
   public void tallenna() throws SailoException {
       if ( !muutettu ) return;

       File fbak = new File(getBakNimi());
       File ftied = new File(getTiedNimi());
       fbak.delete(); // if .. System.err.println("Ei voi tuhota");
       ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

       try ( PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath(), true)) ) {
           for (Kirja kirja : this) {
               fo.println(kirja.toString());
           }
           //} catch ( IOException e ) { // ei heitä poikkeusta
           //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
       } catch ( FileNotFoundException ex ) {
           throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
       } catch ( IOException ex ) {
           throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
       }


       muutettu = false;
   }

    
    
    /**
     * Palauttaa Kirjahyllyn koko nimen
     * @return Kirjahyllyn koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedPerusNimi + ".bak";
    }



    /**
     * Palauttaa kirjahyllyn kirjojen lukumäärän
     * @return kirjojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Luokka kirjojen iteroimiseksi
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Kirjat kirjat = new Kirjat();
     * Kirja kirja1 = new Kirja(); Kirja kirja2 = new Kirja();
     * kirja1.rekisteroi(); kirja2.rekisteroi();
     * kirjat.lisaa(kirja1); kirjat.lisaa(kirja2);
     * 
     * StringBuilder sb = new StringBuilder();
     * for (Kirja kirja : kirjat) sb.append(" " + kirja.getTunnusNro());
     * String tulos = " " + kirja1.getTunnusNro() + " " + kirja2.getTunnusNro();
     * sb.toString() === tulos;
     * 
     * sb = new StringBuilder();
     * for (Iterator<Kirja>  i=kirjat.iterator(); i.hasNext(); ) {
     *   Kirja kirja = i.next();
     *   sb.append(" "+kirja.getTunnusNro());           
     * }
     * 
     * sb.toString() === tulos;
     * 
     * Iterator<Kirja>  i=kirjat.iterator();
     * i.next() == kirja1  === true;
     * i.next() == kirja2  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class KirjatIterator implements Iterator<Kirja> {
        private int kohdalla = 0;
        
        
        /**
         * Onko olemassa vielä seuraavaa kirjaa
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä kirjoja
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava kirja
         * @return seuraava kirja
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Kirja next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori kirjoista.
     * @return kirja iteraattori
     */
    @Override
    public Iterator<Kirja> iterator() {
        return new KirjatIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjojen viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kirjoista 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Kirjat kirjat = new Kirjat(); 
     *   Kirja kirja1 = new Kirja(); kirja1.parse("2|Sapiens|Tietokirjallisuus|2011|491|luettu|||3.2020|8|hauska"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("3|Frankenstein|Kauhu, romantiikka|1800|200|luettu|2017|||7|-");
     *   kirjat.lisaa(kirja1); kirjat.lisaa(kirja2);
     *   // TODO: toistaiseksi palauttaa kaikki kirjat 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Kirja> etsi(String hakuehto, int k) { 
        Collection<Kirja> loytyneet = new ArrayList<Kirja>(); 
        for (Kirja kirja : this) { 
            loytyneet.add(kirja);  
        } 
        return loytyneet; 
    }


    /**
     * Testiohjelma kirjoille
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kirjat kirjat = new Kirjat();

        Kirja sapiens = new Kirja(), frankenstein = new Kirja();
        sapiens.rekisteroi();
        sapiens.taytaSapiensTiedoilla();
        frankenstein.rekisteroi();
        frankenstein.taytaSapiensTiedoilla();

        kirjat.lisaa(sapiens);
        kirjat.lisaa(frankenstein);

        System.out.println("============= Kirjat testi =================");

        for (int i = 0; i < kirjat.getLkm(); i++) {
            Kirja kirja = kirjat.anna(i);
            System.out.println("Kirja nro: " + i);
            kirja.tulosta(System.out);
            System.out.println("");
        }
    }

    
}