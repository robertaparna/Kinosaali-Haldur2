package src.kinosaalihaldur2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Seanss implements Comparable<Seanss>{
    private String pealkiri;
    private Saal saal;
    private Interval vahemik;
    private String algus;
    private String kuupäev;
    private int kestus;
    private List<List<Integer>> kohaplaan;
    private List<List<Integer>> valitudKohad;
    // interval.overlaps(interval)\
    //datetime.plusMinutes(minuteid)
    //datetime string "yyyy-mm-ddThh:mm"
    public Seanss(String pealkiri, String kuupäev, String algus, int kestus, Saal saal) {
        String a = kuupäev+ "T"+ algus; // teeme uhe stringi kus on oiges formaadis kuupaev ja kellaaeg
        LocalDateTime b = LocalDateTime.parse(a); // votame sellest datetime objekti alguseks
        LocalDateTime c = b.plusMinutes(kestus); // lisame algusele kestuse et lopp saada
        Interval vahemik = new Interval(b, c); //teeme nendest vahemiku
        if (saal.aegOnVaba(vahemik)){
            this.pealkiri = pealkiri;
            this.kuupäev = kuupäev;
            this.algus = algus;
            this.kestus = kestus;
            this.kohaplaan = new ArrayList<>(saal.getKohaplaan());
            this.vahemik = vahemik;
            this.saal = saal;
            this.valitudKohad = new ArrayList<>();
            saal.lisaBroneering(this);
            System.out.println("Seanss lisatud!");
        }
        else {
            System.out.println("Selleks ajaks on saal juba broneeritud!");
        }
    }

    public String getPealkiri() {
        return pealkiri;
    }

    public Saal getSaal() {
        return saal;
    }

    public String getAlgus() {
        return algus;
    }

    public String getKuupäev() {
        return kuupäev;
    }

    public int getKestus() {
        return kestus;
    }

    public Interval getVahemik() {
        return vahemik;
    }

    public List<List<Integer>> getKohaplaan() {
        return kohaplaan;
    }

    @Override //et seansse võrreldaks algusaja järgi
    public int compareTo(Seanss o) {
        return vahemik.getAlgus().compareTo(o.getVahemik().getAlgus());
    }

    /**
     * seansi kohaplaani hetkeseisu väljastamine
     */
    public void valjastaKohaplaan() {
        for (List<Integer> rida : kohaplaan) {
            for (Integer koht : rida) {
                if(koht == 1){
                    System.out.print("\uD83D\uDFE5");
                }
                else if(koht == 0){
                    System.out.print("\uD83D\uDFE9");
                }
                else if(koht == 2) {
                    System.out.print("\uD83D\uDFEA");
                }
            }
            System.out.println();
        }
    }

    /**
     * @return mitu vaba kohta hetkel seansil
     */
    public int vabuKohti() {
        int kohti = 0;
        for (List<Integer> integers : kohaplaan) {
            for (Integer integer : integers) {
                if(integer == 0) {
                    kohti++;
                }
            }
        }
        return kohti;
    }

    public boolean kasSaabSeansile(int vanus){
        return true;
    }

    /**
     * märgib koha kohaplaanis valituks ja jätab meelde
     * @param rida rida
     * @param koht koht
     */
    public void valiKoht(int rida, int koht){
        List<Integer> valitud = new ArrayList<>(){{add(rida); add(koht);}};
        valitudKohad.add(valitud);
        kohaplaan.get(rida).set(koht, 2);
    }

    /**
     * märgib valitud kohad hõivatuks ja tühjendab mälu
     */
    public void müüValitudKohad() {
        for (List<Integer> koht : valitudKohad) {
            kohaplaan.get(koht.get(0)).set(koht.get(1), 1);
        }
        valitudKohad.clear();
    }

    /**
     * märgib valitud kohad vabaks ja tühjendab mälu
     */
    public void tühistaValitudKohad() {
        for (List<Integer> koht : valitudKohad) {
            kohaplaan.get(koht.get(0)).set(koht.get(1), 0);
        }
        valitudKohad.clear();
    }

    /**
     * kas koht on vaba
     * @param rida rida
     * @param koht koht
     * @return kas see koht on vaba
     */
    public boolean kohtVaba(int rida, int koht) {
        return kohaplaan.get(rida).get(koht) == 0;
    }




}
