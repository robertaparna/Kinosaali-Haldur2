package src.kinosaalihaldur2;


import java.util.*;

public class Saal {
    private String nimi;
    private List<List<Integer>> kohaplaan;
    private List<Seanss> broneeringud;

    public List<List<Integer>> getKohaplaan() {
        return kohaplaan;
    }

    /**
     * konstruktor ristkülikukujulise saali jaoks
     * @param nimi selle saali nimi
     * @param read mitu rida selles saalis
     * @param kohad mitu kohta igas reas
     */
    public Saal(String nimi, int read, int kohad) {
        this.nimi = nimi;
        this.broneeringud = new ArrayList<>();
        this.kohaplaan = new ArrayList<>();
        for (int i = 0; i < read; i++) {
            List<Integer> rida = new ArrayList<>();
            for (int j = 0; j < kohad; j++) {
                rida.add(0);
            }
            kohaplaan.add(rida);
        }
    }

    /**
     * konstruktor saali jaoks kus igas reas ei ole sama palju kohti
     * @param nimi saali nimi
     * @param read mitu rida selles saalis
     */
    public Saal(String nimi, int read) {
        this.broneeringud = new ArrayList<>();
        this.kohaplaan = new ArrayList<>();
        this.nimi = nimi;
        for (int i = 0; i < read; i++) {
            System.out.println("Mitu kohta on " + (i+1) + ". reas?");
            Scanner in = new Scanner(System.in);
            int kohti = Integer.parseInt(in.nextLine());
            List<Integer> rida = new ArrayList<>();
            for (int j = 0; j < kohti; j++) {
                rida.add(0);
            }
            kohaplaan.add(rida);
        }
    }

    /**
     * @return kõik broneeringud
     */
    public List<Seanss> getBroneeringud() {
        return broneeringud;
    }

    /**
     * @param kuupaev kuupäev
     * @return koik broneeringud sellel kuupäeval
     */
    public List<Seanss> getBroneeringud(String kuupaev) {
        List<Seanss> broneeringudPaeval = new ArrayList<>();
        for (Seanss seanss : broneeringud) {
            if(seanss.getKuupäev().equals(kuupaev)) {
                broneeringudPaeval.add(seanss);
            }
        }
        return broneeringudPaeval;
    }

    /**
     * kas soovitud aeg on juba broneeritud
     * @param interval millist aega broneerida tahame
     * @return kas see aeg on saadaval või ei
     */
    public boolean aegOnVaba(Interval interval){
        for (Seanss seanss : broneeringud) {
            if(interval.overlaps(seanss.getVahemik())) {
                return false;
            }
        }
        return true;
    }

    /**
     * seansi lisamine saali broneeringute hulka
     * @param seanss mida lisame
     */
    public void lisaBroneering(Seanss seanss) {
        broneeringud.add(seanss);
    }

    /**
     * saali plaani koostamine
     * @return saali plaan
     */
    public String skeem() {
        String saal = "";
        for (List<Integer> rida : kohaplaan) {
            for (Integer koht : rida) {
                saal += "\uD83D\uDFE9";
            }
            saal += '\n';
        }
        return saal;
    }

    @Override
    public String toString() {
        return nimi;
    }

    public String saaliInfo() {
        return nimi + '\n' + skeem();
    }
}
