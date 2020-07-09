/**
 * 
 */
package kirjahylly;

/**
 * @author ville
 * @version 9.7.2020
 *
 */
public class Kirjat {
    private static final int MAX_KIRJOJA   = 8;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
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
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
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
     * kirjat.lisaa(kirja1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja;
        lkm++;
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
     * Lukee kirjat tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/kirjat.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kirjat tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kirjahyllyn kirjojen lukumäärän
     * @return kirjojen lukumäärä
     */
    public int getLkm() {
        return lkm;
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

        try {
            kirjat.lisaa(sapiens);
            kirjat.lisaa(frankenstein);

            System.out.println("============= Kirjat testi =================");

            for (int i = 0; i < kirjat.getLkm(); i++) {
                Kirja kirja = kirjat.anna(i);
                System.out.println("Kirja nro: " + i);
                kirja.tulosta(System.out);
                System.out.println("");
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}