package kirjahylly.test;
// Generated by ComTest BEGIN
import java.io.File;
import kirjahylly.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.07.23 18:29:17 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KirjailijatTest {



  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta66 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta66() throws SailoException {    // Kirjailijat: 66
    Kirjailijat kirjailijat = new Kirjailijat(); 
    Kirjailija kirjailija21 = new Kirjailija(); kirjailija21.vastaaHarari(2); 
    Kirjailija kirjailija11 = new Kirjailija(); kirjailija11.vastaaHarari(1); 
    Kirjailija kirjailija22 = new Kirjailija(); kirjailija22.vastaaHarari(2); 
    Kirjailija kirjailija12 = new Kirjailija(); kirjailija12.vastaaHarari(1); 
    Kirjailija kirjailija23 = new Kirjailija(); kirjailija23.vastaaHarari(2); 
    String tiedNimi = "testikirjailijat"; 
    File ftied = new File(tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    kirjailijat.lueTiedostosta(tiedNimi); 
    fail("Kirjailijat: 78 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    kirjailijat.lisaa(kirjailija21); 
    kirjailijat.lisaa(kirjailija11); 
    kirjailijat.lisaa(kirjailija22); 
    kirjailijat.lisaa(kirjailija12); 
    kirjailijat.lisaa(kirjailija23); 
    kirjailijat.tallenna(); 
    kirjailijat = new Kirjailijat(); 
    kirjailijat.lueTiedostosta(tiedNimi); 
    Iterator<Kirjailija> i = kirjailijat.iterator(); 
    assertEquals("From: Kirjailijat line: 88", kirjailija21.toString(), i.next().toString()); 
    assertEquals("From: Kirjailijat line: 89", kirjailija11.toString(), i.next().toString()); 
    assertEquals("From: Kirjailijat line: 90", kirjailija22.toString(), i.next().toString()); 
    assertEquals("From: Kirjailijat line: 91", kirjailija12.toString(), i.next().toString()); 
    assertEquals("From: Kirjailijat line: 92", kirjailija23.toString(), i.next().toString()); 
    assertEquals("From: Kirjailijat line: 93", false, i.hasNext()); 
    kirjailijat.lisaa(kirjailija23); 
    kirjailijat.tallenna(); 
    assertEquals("From: Kirjailijat line: 96", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Kirjailijat line: 98", true, fbak.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator214 */
  @Test
  public void testIterator214() {    // Kirjailijat: 214
    Kirjailijat kirjailijat = new Kirjailijat(); 
    Kirjailija kirj21 = new Kirjailija(2); kirjailijat.lisaa(kirj21); 
    Kirjailija kirj11 = new Kirjailija(1); kirjailijat.lisaa(kirj11); 
    Kirjailija kirj22 = new Kirjailija(2); kirjailijat.lisaa(kirj22); 
    Kirjailija kirj12 = new Kirjailija(1); kirjailijat.lisaa(kirj12); 
    Kirjailija kirj23 = new Kirjailija(2); kirjailijat.lisaa(kirj23); 
    Iterator<Kirjailija> i2=kirjailijat.iterator(); 
    assertEquals("From: Kirjailijat line: 226", kirj21, i2.next()); 
    assertEquals("From: Kirjailijat line: 227", kirj11, i2.next()); 
    assertEquals("From: Kirjailijat line: 228", kirj22, i2.next()); 
    assertEquals("From: Kirjailijat line: 229", kirj12, i2.next()); 
    assertEquals("From: Kirjailijat line: 230", kirj23, i2.next()); 
    try {
    assertEquals("From: Kirjailijat line: 231", kirj12, i2.next()); 
    fail("Kirjailijat: 231 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
    int n = 0; 
    int knrot[] = { 2,1,2,1,2} ; 
    for ( Kirjailija kirj:kirjailijat ) {
    assertEquals("From: Kirjailijat line: 237", knrot[n], kirj.getKirjaNro()); n++; 
    }
    assertEquals("From: Kirjailijat line: 240", 5, n); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaKirjailijat255 */
  @Test
  public void testAnnaKirjailijat255() {    // Kirjailijat: 255
    Kirjailijat kirjailijat = new Kirjailijat(); 
    Kirjailija kirj21 = new Kirjailija(2); kirjailijat.lisaa(kirj21); 
    Kirjailija kirj11 = new Kirjailija(1); kirjailijat.lisaa(kirj11); 
    Kirjailija kirj22 = new Kirjailija(2); kirjailijat.lisaa(kirj22); 
    Kirjailija kirj12 = new Kirjailija(1); kirjailijat.lisaa(kirj12); 
    Kirjailija kirj23 = new Kirjailija(2); kirjailijat.lisaa(kirj23); 
    List<Kirjailija> loytyneet; 
    loytyneet = kirjailijat.annaKirjailijat(3); 
    assertEquals("From: Kirjailijat line: 268", 0, loytyneet.size()); 
    loytyneet = kirjailijat.annaKirjailijat(1); 
    assertEquals("From: Kirjailijat line: 270", 2, loytyneet.size()); 
    assertEquals("From: Kirjailijat line: 271", true, loytyneet.get(0) == kirj11); 
    assertEquals("From: Kirjailijat line: 272", true, loytyneet.get(1) == kirj12); 
    loytyneet = kirjailijat.annaKirjailijat(2); 
    assertEquals("From: Kirjailijat line: 274", 3, loytyneet.size()); 
    assertEquals("From: Kirjailijat line: 275", false, loytyneet.get(0) == kirj23); 
  } // Generated by ComTest END
}