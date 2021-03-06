package src.kinosaalihaldur2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    public Seanss(String pealkiri, String kuupäev, String algus, int kestus, Saal saal) throws DateTimeParseException, AegHoivatudErind {
        String a = kuupäev+ "T"+ algus; // teeme uhe stringi kus on oiges formaadis kuupaev ja kellaaeg
        LocalDateTime b = LocalDateTime.parse(a); // votame sellest datetime objekti alguseks
        LocalDateTime c = b.plusMinutes(kestus); // lisame algusele kestuse et lopp saada
        Interval vahemik = new Interval(b, c); //teeme nendest vahemiku
        if (saal.aegOnVaba(vahemik)){
            this.pealkiri = pealkiri;
            this.kuupäev = kuupäev;
            this.algus = algus;
            this.kestus = kestus;

            List<List<Integer>> kohaplaaniKoopia = new ArrayList<>();
            int ridu = saal.getKohaplaan().size();
            int kohti = saal.getKohaplaan().get(0).size();

            for (int i = 0; i < ridu; i++) {
                kohaplaaniKoopia.add(new ArrayList<>());
                for (int j = 0; j < kohti; j++) {
                    kohaplaaniKoopia.get(i).add(0);
                }
            }
            this.kohaplaan = kohaplaaniKoopia;

            this.vahemik = vahemik;
            this.saal = saal;
            this.valitudKohad = new ArrayList<>();
            saal.lisaBroneering(this);
            System.out.println("Seanss lisatud!");
        }
        else {
            throw new AegHoivatudErind();
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
        List<List<Integer>> uusKohaplaan = List.copyOf(getKohaplaan());
        valitudKohad.add(valitud);
        uusKohaplaan.get(rida).set(koht, 2);
        setKohaplaan(uusKohaplaan);
    }

    public void vabastaKoht(int rida, int koht){
        List<List<Integer>> uusKohaplaan = List.copyOf(getKohaplaan());
        uusKohaplaan.get(rida).set(koht, 0);
        setKohaplaan(uusKohaplaan);
    }


    /**
     * märgib valitud kohad hõivatuks ja tühjendab mälu
     */
    public void müüValitudKohad() {
        boolean muudetud = false;
        for (int i = 0; i < kohaplaan.size(); i++) {
            for (int j = 0; j < kohaplaan.get(0).size(); j++) {
                if(kohaplaan.get(i).get(j) == 2) {
                    kohaplaan.get(i).set(j, 1);
                    muudetud = true;
                }
            }
        }
        if (!muudetud) {
            throw new PoleValitudErind();
        }
        valitudKohad.clear();
    }

    public void setKohaplaan(List<List<Integer>> kohaplaan) {
        this.kohaplaan = kohaplaan;
    }

    /**
     * märgib valitud kohad vabaks ja tühjendab mälu
     */
    public void tühistaValitudKohad() {
        for (int i = 0; i < kohaplaan.size(); i++) {
            for (int j = 0; j < kohaplaan.get(0).size(); j++) {
                if(kohaplaan.get(i).get(j) == 2) {
                    kohaplaan.get(i).set(j, 0);
                }
            }
        }
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
