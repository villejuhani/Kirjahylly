/**
 * 
 */
package kanta;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 */

public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import kirjahylly.Kirjailija;
     *   Kirjailija kir = new Kirjailija();
     *   kir.getKenttia() === 2;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Kirjailija kir = new Kirjailija();
     *   har.ekaKentta() === 1;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.getKysymys(2) === "ala";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "10";   
     *   har.anna(2) === "Kalastus";   
     *   har.anna(3) === "1949";   
     *   har.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   har.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     *   kopio instanceof Harrastus === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();

}

