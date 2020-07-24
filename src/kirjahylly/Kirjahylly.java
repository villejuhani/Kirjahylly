/**
 * 
 */
package kirjahylly;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author ville
 * @version 9.7.2020
 *
 */
public class Kirjahylly {
    private Kirjat kirjat = new Kirjat();
    private Kirjailijat kirjailijat = new Kirjailijat();

    
    /**
     * Alustetaan kirjahyllyn tiedot
     */
    public Kirjahylly() {
        // ei tarvii mitään
    }


    /**
     * lisää kirjahyllyyn uuden kirjan
     * @param kirja lisättävä kirja
     * @throws SailoException jos lisäystä ei voi tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kirjahylly kirjahylly = new Kirjahylly();
     * Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     * kirjahylly.lisaa(kirja1);
     * kirjahylly.lisaa(kirja2);
     * kirjahylly.lisaa(kirja1); 
     * Collection<Kirja> loytyneet = kirjahylly.etsi("",1);
     * Iterator<Kirja> it = loytyneet.iterator();
     * it.next() === kirja1;
     * it.next() === kirja2;
     * it.next() === kirja1;
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }
    
    
    /**
     * Lisätään uusi kirjailija kirjahyllyyn
     * @param kirj lisättävä kirjailija
     */
    public void lisaa(Kirjailija kirj) {
        kirjailijat.lisaa(kirj);
    }
    
    
    /**
     * Haetaan kirjan kirjailija
     * @param kirja kirja jolle kirjailija haetaan
     * @return tietorakenne jossa viiteet löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjahylly kirjahylly = new Kirjahylly();
     *  Kirja kirja1 = new Kirja(), kirja2 = new Kirja(), kirja3 = new Kirja();
     *  kirja1.rekisteroi(); kirja2.rekisteroi(); kirja3.rekisteroi();
     *  int id1 = kirja1.getTunnusNro();
     *  int id2 = kirja2.getTunnusNro();                      
     *  Kirjailija kirj21 = new Kirjailija(id2); kirjahylly.lisaa(kirj21); 
     *  Kirjailija kirj11 = new Kirjailija(id1); kirjahylly.lisaa(kirj11); 
     *  Kirjailija kirj22 = new Kirjailija(id2); kirjahylly.lisaa(kirj22); 
     *  Kirjailija kirj12 = new Kirjailija(id1); kirjahylly.lisaa(kirj12); 
     *  Kirjailija kirj23 = new Kirjailija(id2); kirjahylly.lisaa(kirj23); 
     *
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjahylly.annaKirjailijat(kirja3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kirjahylly.annaKirjailijat(kirja1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == kirj11 === true;
     *  loytyneet.get(1) == kirj12 === true;
     *  loytyneet = kirjahylly.annaKirjailijat(kirja2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == kirj23 === false;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat(Kirja kirja) {
        return kirjailijat.annaKirjailijat(kirja.getTunnusNro());
    }
    
    
    /**
     * Haetaan kaikki kirjailijat
     * @return tietorakenne jossa viiteet löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjahylly kirjahylly = new Kirjahylly();
     *  Kirja kirja1 = new Kirja(), kirja2 = new Kirja(), kirja3 = new Kirja();
     *  kirja1.rekisteroi(); kirja2.rekisteroi(); kirja3.rekisteroi();
     *  int id1 = kirja1.getTunnusNro();
     *  int id2 = kirja2.getTunnusNro();                      
     *  Kirjailija kirj21 = new Kirjailija(id2); kirjahylly.lisaa(kirj21); 
     *  Kirjailija kirj11 = new Kirjailija(id1); kirjahylly.lisaa(kirj11); 
     *  Kirjailija kirj22 = new Kirjailija(id2); kirjahylly.lisaa(kirj22); 
     *  Kirjailija kirj12 = new Kirjailija(id1); kirjahylly.lisaa(kirj12); 
     *  Kirjailija kirj23 = new Kirjailija(id2); kirjahylly.lisaa(kirj23); 
     *
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjahylly.annaKirjailijat();
     *  loytyneet.size() === 5; 
     *  loytyneet.get(0) == kirj21 === true;
     *  loytyneet.get(3) == kirj12 === true;
     *  loytyneet.get(0) == kirj23 === false;
     *  loytyneet = kirjahylly.annaKirjailijat(kirja1);
     *  loytyneet.get(1) == kirj12 === true;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat() {
        return kirjailijat.annaKirjailijat();
    }
    
    
    /**
     * Palautetaan hakuehtoa vastaavien kirjojen viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenne löytyneistä kirjoista
     * @throws SailoException jos menee pieleen
     */
    public Collection<Kirja> etsi(String hakuehto, int k) throws SailoException{
        return kirjat.etsi(hakuehto, k);
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = nimi +"/";
        //if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        kirjat.setTiedostonPerusNimi(hakemistonNimi + "kirjat");
        kirjailijat.setTiedostonPerusNimi(hakemistonNimi + "kirjailijat");
    }
    
 
    /**
     * Lukee kirjahyllyn tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        kirjat = new Kirjat();
        kirjailijat = new Kirjailijat();
        
        setTiedosto(nimi);
        kirjat.lueTiedostosta();
        kirjailijat.lueTiedostosta();
    }


    /**
     * @return kirjojen määrän
     */
    public int getKirjoja() {
        return kirjat.getLkm();
    }
    
    
    /**
     * @return kirjailijoiden määrän
     */
    public int getKirjailijoita() {
        return kirjailijat.getLkm();
    }
    
    
    /**
     * Poistaa kirjoista ja kirjailijoista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako kirjaa poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Tallettaa kirjahyllyn tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kirjat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
 
        try {
            kirjailijat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }


    /**
     * palauttaa i:n kirjan
     * @param i monesko kirja palautetaan
     * @return viite i:teen kirjaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kirja annaKirja(int i) throws IndexOutOfBoundsException {
        return kirjat.anna(i);
    }
    
    
    /**
     * palauttaa i:n kirjan
     * @param i monesko kirja palautetaan
     * @return viite i:teen kirjaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kirjailija annaKirjailija(int i) throws IndexOutOfBoundsException {
        return kirjailijat.anna(i);
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjahylly kirjahylly = new Kirjahylly();

        try {
            Kirja sapiens = new Kirja();
            Kirja frankenstein = new Kirja();

            sapiens.rekisteroi();
            sapiens.taytaSapiensTiedoilla();
            frankenstein.rekisteroi();
            frankenstein.taytaSapiensTiedoilla();

            kirjahylly.lisaa(sapiens);
            kirjahylly.lisaa(frankenstein);
            int id1 = sapiens.getTunnusNro();
            int id2 = frankenstein.getTunnusNro();
            Kirjailija kirj1 = new Kirjailija(id1); kirj1.vastaaHarari(id1); kirjahylly.lisaa(kirj1);
            Kirjailija kirj2 = new Kirjailija(id1); kirj2.vastaaHarari(id1); kirjahylly.lisaa(kirj2);
            Kirjailija kirj3 = new Kirjailija(id2); kirj3.vastaaHarari(id2); kirjahylly.lisaa(kirj3);
            
            System.out.println(
                    "============= Kirjahylly testi =================");

            Collection<Kirja> kirjat = kirjahylly.etsi("", -1);
            int i = 0;
            for (Kirja kirja: kirjat) {
            System.out.println("Kirja paikassa: " + i);
                kirja.tulosta(System.out);
                List<Kirjailija> loytyneet = kirjahylly.annaKirjailijat(kirja);
                for (Kirjailija kirjailija : loytyneet)
                    kirjailija.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
