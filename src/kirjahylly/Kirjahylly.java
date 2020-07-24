/**
 * 
 */
package kirjahylly;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author ville
 * @version 24.7.2020
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
     *  
     *  Kirjailija kirj21 = new Kirjailija("Harari"); kirj21.rekisteroi(); kirjahylly.lisaa(kirj21); 
     *  Kirjailija kirj11 = new Kirjailija("Austen"); kirj11.rekisteroi(); kirjahylly.lisaa(kirj11); 
     *  Kirjailija kirj22 = new Kirjailija("Shelley"); kirj22.rekisteroi(); kirjahylly.lisaa(kirj22); 
     *  int id1 = kirj21.getTunnusNro();
     *  int id2 = kirj11.getTunnusNro();      
     *  
     *  Kirja kirja1 = new Kirja(), kirja2 = new Kirja(), kirja3 = new Kirja();
     *  kirja1.lisaaKirjailija(id1); kirja2.lisaaKirjailija(id2); 
     *  kirja1.rekisteroi(); kirja2.rekisteroi(); kirja3.rekisteroi();
     *  kirja3.lisaaKirjailija(id1);
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjahylly.annaKirjailijat(kirja3);
     *  loytyneet.size() === 1; 
     *  loytyneet = kirjahylly.annaKirjailijat(kirja1);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == kirj21 === true;
     *  loytyneet = kirjahylly.annaKirjailijat(kirja2);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == kirj22 === false;
     * </pre> 
     */
    public List<Kirjailija> annaKirjailijat(Kirja kirja) {
        return kirjailijat.annaKirjailijat(kirja.getKirjailijaId());
    }
    
    
    /**
     * Haetaan kaikki kirjailijat
     * @return tietorakenne jossa viiteet löydettyyn kirjailijaan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjahylly kirjahylly = new Kirjahylly();
     *
     *  Kirjailija kirj25 = new Kirjailija("Harari"); kirj25.rekisteroi(); kirjahylly.lisaa(kirj25); 
     *  Kirjailija kirj18 = new Kirjailija("Austen"); kirj18.rekisteroi(); kirjahylly.lisaa(kirj18); 
     *  Kirjailija kirj26 = new Kirjailija("Shelley"); kirj26.rekisteroi(); kirjahylly.lisaa(kirj26); 
     *  
     *  List<Kirjailija> loytyneet;
     *  loytyneet = kirjahylly.annaKirjailijat();
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == kirj25 === true;
     *  loytyneet.get(2) == kirj26 === true;
     *  loytyneet.get(0) == kirj18 === false;
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
            
            Kirjailija harari = new Kirjailija();
            Kirjailija austen = new Kirjailija("Austen");
            harari.vastaaHarari();
            harari.rekisteroi(); austen.rekisteroi();
            kirjahylly.lisaa(harari); kirjahylly.lisaa(austen);
            
            int id1 = harari.getTunnusNro();
            int id2 = austen.getTunnusNro();
            
            Kirja sapiens = new Kirja(id1);
            Kirja frankenstein = new Kirja(id2);
            Kirja deus = new Kirja();
            sapiens.rekisteroi();
            sapiens.taytaSapiensTiedoilla();
            frankenstein.rekisteroi();
            frankenstein.taytaSapiensTiedoilla();
            deus.rekisteroi();
            deus.taytaSapiensTiedoilla();

            kirjahylly.lisaa(sapiens);
            kirjahylly.lisaa(frankenstein);
            kirjahylly.lisaa(deus);
            deus.lisaaKirjailija(id1);
            
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
                i++;
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
