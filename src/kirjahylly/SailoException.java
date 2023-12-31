package kirjahylly;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 23.6.2020
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
   
   
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}

