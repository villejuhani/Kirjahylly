/**
 * 
 */
package kirjahylly;

import java.util.*;

/**
 * @author ville
 * @version 9.7.2020
 *
 */
public class Kirjailijat implements Iterable<Kirjailija> {
    
    private String                      tiedostonNimi = "";

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
     * Lukee kirjat tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".kirj";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kirjat tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kirjahyllyn kirjailijoiden lukumäärän
     * @return kirjailijoiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
     *  Kirjailija kirj21 = new Kirjailija(2); kirjailijat.lisaa(kirj21);
     *  Kirjailija kirj11 = new Kirjailija(1); kirjailijat.lisaa(kirj11);
     *  Kirjailija kirj22 = new Kirjailija(2); kirjailijat.lisaa(kirj22);
     *  Kirjailija kirj12 = new Kirjailija(1); kirjailijat.lisaa(kirj12);
     *  Kirjailija kirj23 = new Kirjailija(2); kirjailijat.lisaa(kirj23);
     * 
     *  Iterator<Kirjailija> i2=kirjailijat.iterator();
     *  i2.next() === kirj21;
     *  i2.next() === kirj11;
     *  i2.next() === kirj22;
     *  i2.next() === kirj12;
     *  i2.next() === kirj23;
     *  i2.next() === kirj12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int knrot[] = {2,1,2,1,2};
     *  
     *  for ( Kirjailija kirj:kirjailijat ) { 
     *    kirj.getKirjaNro() === knrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kirjailija> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kirjan kirjailija 
     * @param tunnusnro kirjan tunnusnumero jolle kirjailija haetaan
     * @return tietorakenne jossa viiteet löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();                      
     *  Kirjailija kirj21 = new Kirjailija(2); kirjailijat.lisaa(kirj21); 
     *  Kirjailija kirj11 = new Kirjailija(1); kirjailijat.lisaa(kirj11); 
     *  Kirjailija kirj22 = new Kirjailija(2); kirjailijat.lisaa(kirj22); 
     *  Kirjailija kirj12 = new Kirjailija(1); kirjailijat.lisaa(kirj12); 
     *  Kirjailija kirj23 = new Kirjailija(2); kirjailijat.lisaa(kirj23); 
     *
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjailijat.annaKirjailijat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kirjailijat.annaKirjailijat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == kirj11 === true;
     *  loytyneet.get(1) == kirj12 === true;
     *  loytyneet = kirjailijat.annaKirjailijat(2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == kirj23 === false;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat(int tunnusnro) {
        List<Kirjailija> loydetyt = new ArrayList<Kirjailija>();
        for (Kirjailija kirj : alkiot)
            if (kirj.getKirjaNro() == tunnusnro) loydetyt.add(kirj);
        return loydetyt;
    }


    /**
     * @return kaikki kirjailijat
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
        kirj1.vastaaHarari(2);
        Kirjailija kirj2 = new Kirjailija();
        kirj2.vastaaHarari(1);
        Kirjailija kirj3 = new Kirjailija();
        kirj3.vastaaHarari(2);
        Kirjailija kirj4 = new Kirjailija("Austen", 1);
        Kirjailija kirj5 = new Kirjailija("Shelley", 2);
 
        kirjailijat.lisaa(kirj1);
        kirjailijat.lisaa(kirj2);
        kirjailijat.lisaa(kirj3);
        kirjailijat.lisaa(kirj2);
        kirjailijat.lisaa(kirj4);
        kirjailijat.lisaa(kirj5);
        
 
        System.out.println("============= Kirjailijat testi =================");
 
        List<Kirjailija> kirjailijat2 = kirjailijat.annaKirjailijat(2);
 
        for (Kirjailija kirj : kirjailijat2) {
            System.out.print(kirj.getKirjaNro() + " ");
            kirj.tulosta(System.out);
        }
 
    }


}
