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
import java.util.*;

/**
 * @author ville
 * @version 24.7.2020
 *
 */
public class Kirjailijat implements Iterable<Kirjailija> {
    
    private boolean muutettu = false;
    private String  tiedostonNimi = "";

    /** Taulukko kirjailijoista */
    private Collection<Kirjailija> alkiot        = new ArrayList<Kirjailija>();
    


    /**
     * Kirjailijan alustaminen
     */
    public Kirjailijat() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden kirjailijan tietorakenteeseen.  Ottaa kirjailijan omistukseensa.
     * @param kirj lisättävä kirjailija.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kirjailija kirj) {
        alkiot.add(kirj);
        muutettu = true;
    }
    
    
    /**
     * Palauttaa viitteen i:teen kirjaan.
     * @param i monennenko kirjan viite halutaan
     * @return viite kirjaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirjailija anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || getLkm() <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        Object[] uusialkio = alkiot.toArray();
        return (Kirjailija) uusialkio[i];
    }


    /**
     * Lukee kirjailijat tiedostosta.
     * @param tiedosto tiedoston nimen alku
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija kirjailija21 = new Kirjailija(); kirjailija21.vastaaHarari();
     *  Kirjailija kirjailija11 = new Kirjailija(); kirjailija11.vastaaHarari();
     *  Kirjailija kirjailija22 = new Kirjailija(); kirjailija22.vastaaHarari(); 
     *  Kirjailija kirjailija12 = new Kirjailija(); kirjailija12.vastaaHarari(); 
     *  Kirjailija kirjailija23 = new Kirjailija(); kirjailija23.vastaaHarari(); 
     *  String tiedNimi = "testikirjailijat";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kirjailijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjailijat.lisaa(kirjailija21);
     *  kirjailijat.lisaa(kirjailija11);
     *  kirjailijat.lisaa(kirjailija22);
     *  kirjailijat.lisaa(kirjailija12);
     *  kirjailijat.lisaa(kirjailija23);
     *  kirjailijat.tallenna();
     *  kirjailijat = new Kirjailijat();
     *  kirjailijat.lueTiedostosta(tiedNimi);
     *  Iterator<Kirjailija> i = kirjailijat.iterator();
     *  i.next().toString() === kirjailija21.toString();
     *  i.next().toString() === kirjailija11.toString();
     *  i.next().toString() === kirjailija22.toString();
     *  i.next().toString() === kirjailija12.toString();
     *  i.next().toString() === kirjailija23.toString();
     *  i.hasNext() === false;
     *  kirjailijat.lisaa(kirjailija23);
     *  kirjailijat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi())))){
            
            
            while (fi.hasNext()) {
                String rivi = fi.nextLine();
                rivi.trim();
                Kirjailija kirjailija = new Kirjailija();
                kirjailija.parse(rivi);
                lisaa(kirjailija);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
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
     * 2|Yuval Noah Harari
     * 3|Jane Austen
     * </pre>
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");
  
        try ( PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath(),true)) ) {
            for (Kirjailija kirjailija : this) {
                fo.println(kirjailija.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

  
        muutettu = false;
    }


    /**
     * Palauttaa kirjahyllyn kirjailijoiden lukumäärän
     * @return kirjailijoiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonNimi = tied;
    }
 
 
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
 
 
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }
 
 
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }
    
    
    /**
     * Iteraattori kaikkien kirjailijoiden läpikäymiseen
     * @return kirjailijaiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija kirj21 = new Kirjailija("Harari"); kirjailijat.lisaa(kirj21);
     *  Kirjailija kirj11 = new Kirjailija("Austen"); kirjailijat.lisaa(kirj11);
     *  Kirjailija kirj22 = new Kirjailija("Shelley"); kirjailijat.lisaa(kirj22);
     * 
     *  Iterator<Kirjailija> i2=kirjailijat.iterator();
     *  i2.next() === kirj21;
     *  i2.next() === kirj11;
     *  i2.next() === kirj22;
     *  i2.next() === kirj11;  #THROWS NoSuchElementException  
     *
     * </pre>
     */
    @Override
    public Iterator<Kirjailija> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kirjan kirjailija 
     * @param kirjailijaId kirjailijan tunnusnumero
     * @return tietorakenne jossa viiteet löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();                      
     *  Kirjailija kirj21 = new Kirjailija(); kirj21.vastaaHarari(); kirj21.rekisteroi(); kirjailijat.lisaa(kirj21); 
     *  Kirjailija kirj11 = new Kirjailija(); kirj11.vastaaHarari(); kirj11.rekisteroi(); kirjailijat.lisaa(kirj11); 
     *  Kirjailija kirj22 = new Kirjailija(); kirj22.vastaaHarari(); kirj22.rekisteroi(); kirjailijat.lisaa(kirj22); 
     *  Kirjailija kirj12 = new Kirjailija(); kirj12.vastaaHarari(); kirj12.rekisteroi(); kirjailijat.lisaa(kirj12); 
     *  Kirjailija kirj23 = new Kirjailija(); kirj23.vastaaHarari(); kirj23.rekisteroi(); kirjailijat.lisaa(kirj23); 
     *
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjailijat.annaKirjailijat(3);
     *  loytyneet.size() === 1; 
     *  loytyneet = kirjailijat.annaKirjailijat(1);
     *  loytyneet.get(0) == kirj21 === true;
     *  loytyneet.get(0) == kirj12 === false;
     *  loytyneet = kirjailijat.annaKirjailijat(2);
     *  loytyneet.get(0) == kirj11 === true;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat(int kirjailijaId) {
        List<Kirjailija> loydetyt = new ArrayList<Kirjailija>();
        for (Kirjailija kirj : alkiot)
            if (kirj.getTunnusNro() == kirjailijaId) loydetyt.add(kirj);
        return loydetyt;
    }


    /**
     * haetaan kaikki kirjailijat
     * @return kaikki kirjailijat
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *
     *  Kirjailija kirj25 = new Kirjailija("Harari"); kirj25.rekisteroi(); kirjailijat.lisaa(kirj25); 
     *  Kirjailija kirj17 = new Kirjailija("Austen"); kirj17.rekisteroi(); kirjailijat.lisaa(kirj17); 
     *  Kirjailija kirj27 = new Kirjailija("Shelley"); kirj27.rekisteroi(); kirjailijat.lisaa(kirj27); 
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjailijat.annaKirjailijat();
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == kirj25 === true;
     *  loytyneet.get(2) == kirj27 === true;
     *  loytyneet.get(0) == kirj17 === false;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat() {
        List<Kirjailija> loydetyt = new ArrayList<Kirjailija>();
        for (Kirjailija kirj : alkiot)
             loydetyt.add(kirj);
        return loydetyt;
    }
 
 
    /**
     * Testiohjelma kirjailijoille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailijat kirjailijat = new Kirjailijat();
        Kirjailija kirj1 = new Kirjailija();
        kirj1.vastaaHarari(); kirj1.rekisteroi();
        Kirjailija kirj2 = new Kirjailija();
        kirj2.vastaaHarari(); kirj2.rekisteroi();
        Kirjailija kirj3 = new Kirjailija();
        kirj3.vastaaHarari(); kirj3.rekisteroi();
        Kirjailija kirj4 = new Kirjailija("Austen"); kirj4.rekisteroi();
        Kirjailija kirj5 = new Kirjailija("Shelley"); kirj5.rekisteroi();
 
        kirjailijat.lisaa(kirj1);
        kirjailijat.lisaa(kirj2);
        kirjailijat.lisaa(kirj3);
        kirjailijat.lisaa(kirj4);
        kirjailijat.lisaa(kirj5);
        
 
        System.out.println("============= Kirjailijat testi =================");
 
        List<Kirjailija> kirjailijat2 = kirjailijat.annaKirjailijat();
        List<Kirjailija> kirjailijat3 = kirjailijat.annaKirjailijat(4);
 
        for (Kirjailija kirj : kirjailijat2) {
            kirj.tulosta(System.out);
        }
        
        for (Kirjailija kirj : kirjailijat3) {
            kirj.tulosta(System.out);
        }
 
    }


}
