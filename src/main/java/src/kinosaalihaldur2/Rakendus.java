package src.kinosaalihaldur2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Rakendus {
    static List<Saal> saalid = new ArrayList<>();

    public static List<Saal> getSaalid() {
        return saalid;
    }

    public static void saalidAlgus() {
        saalid.add(new Saal("saal3", 3, 7));
        saalid.add(new Saal("saal4", 5, 9));
        new Mangufilm("Fight Club", saalid.get(0),"marul","Brad Pitt","2022-04-07", "12:00", 90);
        new Oudusfilm("Saw", saalid.get(0),"psyhholoogiline triller","Tobin Bell", 16, "2022-04-05", "12:00", 90);
        new Dokumentaalfilm("Planet Earth", "2022-04-06", "12:00", 90, saalid.get(1), "BBC", "loodus");
    }

    public static void setSaalid(List<Saal> saalid) {
        Rakendus.saalid = saalid;
    }

    public static void main(String[] args) {
        while(true) {
            Scanner in = new Scanner(System.in);
//            System.out.println("Tere tulemast!");
//            System.out.println("Sisestage nr 1, kui Te soovite pileteid osta.");
//            System.out.println("Sisestage nr 2, kui Te soovite saale hallata.");
//            System.out.println("Sisestage nr 3, kui Te soovite kinokava hallata.");
//            System.out.println("Sisestage nr 0, kui soovite töö lõpetada.");
            int kasutajaSisestus = Integer.parseInt(in.nextLine());

            if (kasutajaSisestus == 1){
                valiKuupaev();

            }
            else if (kasutajaSisestus == 2){
                saalid();

            }
            else if (kasutajaSisestus == 3){
                kinokavaMuutmine();

            }
            else if (kasutajaSisestus == 0){
                break;
            }

        }
    }

    /**
     * PILET STEP 1
     * valjastab koik kuupaevad millele on broneeritud seansid, laseb kasutajal valida, millise kuupaeva kava vaadata
     **/
    public static void valiKuupaev() {
        System.out.println("Sisestage sobiva kuupäeva ees olev number: ");
        for (int i = 0; i < getKuupäevad().size(); i++) {
//            System.out.println(i+1 + ". "  + getKuupäevad().get(i));
        }
        Scanner in = new Scanner(System.in);
        int kasutajaValik = Integer.parseInt(in.nextLine());
        String kasutajaValitudKuupaev = getKuupäevad().get(kasutajaValik-1);
        valiSeanss(kasutajaValitudKuupaev);
    }

    /**
     * PILET STEP 2
     * valjastab koik seansid, mis on broneeritud kuupaevale kuupaev ja laseb kasutajal valida, millisele pilet osta
     * @param kuupaev valitud kuupaev
     */
    public static List<Seanss> valiSeanss(String kuupaev) {
        List<Seanss> toimuvadSeansid = new ArrayList<>(); //kõik kasutaja valitul kuupäeval toimuvad seansid kõikidest saalidest
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < saalid.size(); i++) { //lisame kõik asjakohased seansid listi
            toimuvadSeansid.addAll(saalid.get(i).getBroneeringud(kuupaev));
        }
//        for (int i = 0; i < toimuvadSeansid.size(); i++) {
//            System.out.println(i+1 + ". "  + toimuvadSeansid.get(i));
//        }
////        System.out.println("Palun sisestage soovitud seansi ees olev number: ");
//        int kasutajaValik = Integer.parseInt(in.nextLine());
//        Seanss kasutajaValitudSeanss = toimuvadSeansid.get(kasutajaValik-1);
//        piletiteValimine(kasutajaValitudSeanss); //lisasin
        return toimuvadSeansid;
    }

    /**
     * PILET STEP 3
     * laseb valida mitu piletit osta valitud seansile ja suunab kohtade valimisse
     * @param seanss valitud seanss
     */
    public static void piletiteValimine(Seanss seanss){
//        System.out.println("Palun sisestage soovitud piletide arv: ");
        Scanner in = new Scanner(System.in);
        int valitudPiletiteArv = Integer.parseInt(in.nextLine());
        if (seanss instanceof Oudusfilm) {
            valitudPiletiteArv = vanuseKontroll(seanss,valitudPiletiteArv);
        }
        kohtadeValimine(seanss, valitudPiletiteArv);
    }

    /**
     * PILET STEP 4
     * tagastab mitu inimest on oudusfilmile lubatud
     * @param seanss valitud seanss
     * @param piletiteArv mitu inimest soovivad seansile
     * @return mitu inimest saavad seansile
     */
    public static int vanuseKontroll(Seanss seanss, int piletiteArv) {
        Scanner in = new Scanner(System.in);
        int lubatudPileteid = 0;
        for (int i = 0; i < piletiteArv; i++) {
            System.out.println("Sisestage palun pileti kasutaja vanus: ");
            int kasutajaVanus = Integer.parseInt(in.nextLine());
            if (seanss.kasSaabSeansile(kasutajaVanus)) {
                lubatudPileteid++;
            }
        }
        return  lubatudPileteid;
    }

    /**
     * ABI
     * tagaastab nimekirja kuupaevadest millal on broneeritud seansse
     * @return nimekiri kuupaevadest millal on broneeritud seansse
     */
    public static List<String> getKuupäevad() {
        Set<LocalDate> kuupaevad = new HashSet<>();
        for (Saal saal : saalid) {
            for (Seanss seanss : saal.getBroneeringud()) {
                kuupaevad.add(LocalDate.parse(seanss.getKuupäev()));
            }
        } //panen kõik hulka, sorteerin selle ära, ja siis võtan sealt kuupäevad stringidena
        List<LocalDate> k = new ArrayList<>(kuupaevad);
        k.sort(null);
        List<String> u = new ArrayList<>();
        for (LocalDate dateTime : k) {
            u.add(String.valueOf(dateTime).substring(0,10));
        }
        return u;
    }

    /**
     * SAAL ALGUS
     * kasutaja valib kas saale vaadata voi lisada
     */
    public static void saalid(){
//        System.out.println("Sisestage 1, kui te soovite saale vaadata: ");
//        System.out.println("Sisestage 2, kui te soovite saale lisada: ");
        Scanner in = new Scanner(System.in);
        int kasutajaValik = Integer.parseInt(in.nextLine());
        if (kasutajaValik == 1){
            saaliVaatamine();
        }
        else if(kasutajaValik == 2){
            saaliLisamine();
        }

    }

    /**
     * kasutaja saab saali lisada
     */
    public static void saaliLisamine(){
        System.out.println("Kas igas reas on sama arv kohti?");
        System.out.println("Sisestage 1, kui teie vastus on 'jah': ");
        System.out.println("Sisestage 0, kui teie vastus on 'ei': ");
        Scanner in = new Scanner(System.in);
        int kasutajaValik = Integer.parseInt(in.nextLine());
        if (kasutajaValik == 1){
            System.out.println("Sisestage palun saali nimi: ");
            Scanner in2 = new Scanner(System.in);
            String kasutajaValitudNimi = in2.nextLine();
            System.out.println("Sisestage palun ridade arv: ");
            int ridadeArv = Integer.parseInt(in.nextLine());
            System.out.println("Sisestage palun kohtade arv: ");
            int kohtadeArv = Integer.parseInt(in.nextLine());
            saalid.add(new Saal(kasutajaValitudNimi, ridadeArv, kohtadeArv));
        }
        else if (kasutajaValik == 0){
            System.out.println("Sisestage palun saali nimi: ");
            Scanner in2 = new Scanner(System.in);
            String kasutajaValitudNimi = in2.nextLine();
            System.out.println("Sisestage palun ridade arv: ");
            int ridadeArv = Integer.parseInt(in.nextLine());
            saalid.add(new Saal(kasutajaValitudNimi, ridadeArv));
        }
        System.out.println("Saal lisatud!");
        System.out.println("-------------------------------------");
    }

    /**
     * kasutaja saab saale vaadata
     */
    public static void saaliVaatamine(){
        for (Saal saal : saalid) {
            System.out.println(saal.saaliInfo());
        }

    }

    /**
     * kasutaja saab kinokava hallata
     */

    public static void kinokavaMuutmine() {
        System.out.println("Palun sisestage 1, kui te soovite seansse lisada: ");
        System.out.println("Palun sisestage 2, kui te soovite kinokava vaadata: ");
        Scanner in = new Scanner(System.in);
        int kasutajaValik = Integer.parseInt(in.nextLine());
        if (kasutajaValik == 1){
            seansiLisamine();
        }
        if (kasutajaValik == 2){
            seansiVaatamine();
        }
    }

    /**
     * kasutaja saab seansi lisada
     */
    public static void seansiLisamine(){
        System.out.println("Sisestage 1 kui soovite lisada dokumentaalfilmi: ");
        System.out.println("Sisestage 2 kui soovite lisada mängufilmi: ");
        System.out.println("Sisestage 3 kui soovite lisada õudusfilmi: ");
        Scanner in = new Scanner(System.in);
        String kasutajaValik = in.nextLine();
        if (kasutajaValik.equals("1")){
            dokFilmiLisamine();
        }
        else if (kasutajaValik.equals("2")){
            mängufilmilisamine();
        }
        else if (kasutajaValik.equals("3")){
            õudusfilmilisamine();
        }
    }

    /**
     * kasutaja saab lisada dokumentaalfilmi
     */
    public static void dokFilmiLisamine(){
        Scanner in = new Scanner(System.in);
        System.out.println("Sisestage dokumentaalfilmi pealkiri: ");
        String pealkiri = in.nextLine();
        System.out.println("Sisestage seansi toimumise kuupäev (kujul yyyy-mm-dd): ");
        String kuupäev = in.nextLine();
        System.out.println("Sisestage seansi algus (kujul hh:mm): ");
        String algus = in.nextLine();
        System.out.println("Sisestage filmi kestus (minutites): ");
        int kestus = Integer.parseInt(in.nextLine());
        //väljasta saalide list koos numbritega nagu kuupäevadega
        System.out.println("Valige üks saal ja sisestage selle saali ees olev number: ");
        for (int i = 0; i < saalid.size(); i++) {
            System.out.println(i+1 + ". "  + saalid.get(i));
        }
        int valik = Integer.parseInt(in.nextLine());
        Saal saal = saalid.get((valik)-1);
        System.out.println("Sisestage filmi tegijad: ");
        String tegijad = in.nextLine();
        System.out.println("Sisestage dokumentaali teema: ");
        String teema = in.nextLine();
        System.out.println("Pealkiri " + pealkiri);
        System.out.println("Kuupaev " + kuupäev);
        System.out.println("algus " + algus);
        System.out.println("Kestus " + kestus);
        System.out.println("saal " + saal);
        System.out.println("tegijad " + tegijad);
        System.out.println("teema " + teema);

        new Dokumentaalfilm(pealkiri, kuupäev, algus, kestus, saal, tegijad, teema);
        System.out.println("-------------------------------------");
    }

    /**
     * kasutaja saab lisada mängufilmi
     */
    public static void mängufilmilisamine(){
        Scanner in = new Scanner(System.in);
        System.out.println("Sisestage mängufilmi pealkiri: ");
        String pealkiri = in.nextLine();
        System.out.println("Valige üks saal ja sisestage selle saali ees olev number: ");
        for (int i = 0; i < saalid.size(); i++) {
            System.out.println(i+1 + ". "  + saalid.get(i));
        }
        String kasutajaValik = in.nextLine();
        Saal saal = saalid.get(Integer.parseInt(kasutajaValik)-1);
        System.out.println("Sisestage filmi žanr: ");
        String žanr = in.nextLine();
        System.out.println("Sisestage filmi peaosatäitjad: ");
        String näitlejad = in.nextLine();
        System.out.println("Sisestage seansi toimumise kuupäev (kujul yyyy-mm-dd): ");
        String kuupäev = in.nextLine();
        System.out.println("Sisestage seansi algus (kujul hh:mm): ");
        String algus = in.nextLine();
        System.out.println("Sisestage filmi kestus (minutites): ");
        int kestus = Integer.parseInt(in.nextLine());
        new Mangufilm(pealkiri, saal, žanr, näitlejad, kuupäev, algus, kestus);
        System.out.println("-------------------------------------");
    }

    /**
     * kasutaja saab lisada õudusfilmi
     */
    public static void õudusfilmilisamine(){
        Scanner in = new Scanner(System.in);
        System.out.println("Sisestage õudusfilmi pealkiri: ");
        String pealkiri = in.nextLine();
        System.out.println("Valige üks saal ja sisestage selle saali ees olev number: ");
        for (int i = 0; i < saalid.size(); i++) {
            System.out.println(i+1 + ". "  + saalid.get(i));
        }
        Saal saal = saalid.get((Integer.parseInt(in.nextLine()))-1);
        System.out.println("Sisestage filmi žanr: ");
        String žanr = in.nextLine();
        System.out.println("Sisestage filmi peaosatäitjad: ");
        String näitlejad = in.nextLine();
        System.out.println("Sisestage vanusepiirang seansile pääsemiseks: ");
        int vanusepiirang = (Integer.parseInt(in.nextLine()));
        System.out.println("Sisestage seansi toimumise kuupäev (kujul yyyy-mm-dd): ");
        String kuupäev = in.nextLine();
        System.out.println("Sisestage seansi algus (kujul hh:mm): ");
        String algus = in.nextLine();
        System.out.println("Sisestage filmi kestus (minutites): ");
        int kestus = Integer.parseInt(in.nextLine());
        new Oudusfilm(pealkiri, saal, žanr, näitlejad, vanusepiirang, kuupäev, algus, kestus);
        System.out.println("-------------------------------------");
    }

    /**
     * väljastab terve kinokava
     */
    public static void seansiVaatamine(){
        System.out.println("Sisestage 1, kui soovite vaadata kõikide filmide kava: ");
        Scanner in = new Scanner(System.in);
        int kasutajaValik = Integer.parseInt(in.nextLine());
        if (kasutajaValik == 1){
            for (Saal saal : saalid) {
                System.out.println(saal.getBroneeringud());
            }
        }
    }

    /**
     * PILET STEP 5
     * pakub suvalised jarjest vabad kohad vastavalt piletite arvule voi saadab kasutaja edasi ise valima(kui ei leia jarjest)
     * @param seanss valitud seanss
     * @param pileteid pakutavate piletite arv
     */
    public static void kohtadeValimine(Seanss seanss, int pileteid) {
        if(seanss.vabuKohti() < pileteid){                                          //koigepealt kontrollib kas nii palju
            System.out.println("Kahjuks sellel seansil ei ole piisavalt kohti!");   //vabu kohti on ja kui pole annab
            System.out.println("Kui tahad valida uue piletite arvu sisesta 1");     //edasi võimalused
            System.out.println("Kui tahad valida uue seansi sisesta 2");

            Scanner in = new Scanner(System.in);
            int valik = Integer.parseInt(in.nextLine());
            if(valik == 1) {
                piletiteValimine(seanss);
            }
            else if(valik == 2) {
                valiKuupaev();
            }
        }
        Random r = new Random();    //kui piisavalt kohti on siis ta hakkab otsima suvalist rida kuhu saaks nii palju
                                    //kohti järjest panna
        int rida;
        boolean t = false;
        for (int i = 0; i < 20 && !t; i++) {  //kuna ma ei võta järjest vaid valin suvalisi ridu ei saa ta kuidagi aru kas tal on juba
            rida = r.nextInt(seanss.getKohaplaan().size()); //voibolla koik read läbi vaadatud ja ükski ei sobi või kui ta oma juhuslike
            seanss.tühistaValitudKohad();                   //ridadega ei saagi äkki kõiki ridu vaadatud, niiet ta proovib 20 korda ja siis annab alla
            int jarjest=0;
            for (int j = 0; j < seanss.getKohaplaan().get(rida).size(); j++) { //käib valitud rea läbi ja vaatab kas seal on nii palju järjest
                if(pileteid >  seanss.getKohaplaan().get(rida).size()) {  //kui rida on lühem lõpetab kohe
                    break;
                }
                if(seanss.getKohaplaan().get(rida).get(j) == 0) {  //kui koht on vaba siis valib selle
                    jarjest++;
                    seanss.valiKoht(rida, j);
                    if(jarjest == pileteid) {                      //kui on järjest valinud piisavalt kohti siis pakub need välja
                        System.out.println("punane - hoivatud, roheline - vaba, lilla - valitud");
                        seanss.valjastaKohaplaan();
                        System.out.println("Kui valitud kohad sobivad sisesta 1");
                        System.out.println("Kui soovid valida teised kohad sisesta 2");
                        Scanner in = new Scanner(System.in);
                        int sisend = Integer.parseInt(in.nextLine());
                        if (sisend == 1) {
                            seanss.müüValitudKohad();
                            System.out.println("Valitud kohad on müüdud!");
                            System.out.println("------------------------------------------");
                            t = true;
                            return;
                        }
                        else if( sisend == 2 ) {
                            t = true;
                            seanss.tühistaValitudKohad();
                            kasutajaValibKohad(seanss, pileteid);
                            return;
                        }
                    }
                }
                else{ //kui koht ei ole vaba siis hakkab järgmisest uuesti valima
                    jarjest = 0;
                    seanss.tühistaValitudKohad();
                }
            }
        }
        seanss.tühistaValitudKohad();
        System.out.println("Vali ise kohad!");
        kasutajaValibKohad(seanss, pileteid);
    }

    /**
     * PILET STEP 6
     * kasutaja valib ise piletite jaoks kohad
     * @param seanss millisele seansile pileteid müüakse
     * @param pileteid mitu piletit
     */
    public static void kasutajaValibKohad(Seanss seanss, int pileteid) {
        System.out.println("punane - hoivatud, roheline - vaba, lilla - valitud");
        System.out.println("rida 1, koht 1, on vasakul üleval nurgas");
        seanss.valjastaKohaplaan();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < pileteid; i++) { //iga pileti jaoks küsib rida ja kohta
            int koht, rida;
            while (true){ //kui koht ei ole vaba küsib uuesti
                System.out.println("Sisesta rida " + (1 + i) + ". pileti jaoks");
                rida = Integer.parseInt(in.nextLine())-1;
                System.out.println("Sisesta koht " + (1 + i) + ". pileti jaoks");
                koht = Integer.parseInt(in.nextLine())-1;
                if(!seanss.kohtVaba(rida, koht)) {
                    System.out.println("See koht on juba hoivatud, vali uuesti");
                }
                else{
                    seanss.valiKoht(rida, koht);
                    break;
                }
            }
        }
        System.out.println("Kas valitud kohad sobivad?");
        System.out.println("punane - hoivatud, roheline - vaba, lilla - valitud");
        seanss.valjastaKohaplaan();
        System.out.println("Sisesta 1, kui kohad sobivad");
        System.out.println("Sisesta 2, kui soovid uuesti valida");
        int sisend = Integer.parseInt(in.nextLine());
        if(sisend == 1) {
            seanss.müüValitudKohad();
            System.out.println("Valitud kohad on müüdud!");
            System.out.println("------------------------------------------");
        }
        if(sisend == 2) {
            seanss.tühistaValitudKohad();
            kasutajaValibKohad(seanss, pileteid);
        }
    }
}
