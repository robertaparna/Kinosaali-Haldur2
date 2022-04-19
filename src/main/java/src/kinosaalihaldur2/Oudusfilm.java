package src.kinosaalihaldur2;

public class Oudusfilm extends Mangufilm {

    private int vanusepiirang;

    public Oudusfilm(String pealkiri, Saal saal, String žanr, String näitlejad, int vanusepiirang, String kuupäev, String algus, int kestus) {
        super(pealkiri, saal, žanr, näitlejad, kuupäev, algus, kestus);
        this.vanusepiirang = vanusepiirang;
    }

    @Override
    public boolean kasSaabSeansile(int vanus){
        if (vanus < vanusepiirang){
            System.out.println("Kahjuks ei ole Te seansile lubatud. " +
                    "Seansile pääasevad " +vanusepiirang +"-aastased ja vanemad inimesed.");
            return false;
        }
        else {
            System.out.println("Olete seansile lubatud!");
            return true;
        }
    }

    @Override
    public String toString() {
        return "Film: " + super.getPealkiri() + '\n' + "Žanr: " +
                super.getŽanr() + '\n' + "Peaosas mängivad näitlejad: " + super.getNäitlejad() + '\n' +
                "Teie seanss toimub: " + super.getKuupäev() + " kell: " + super.getAlgus() + '\n' +
                "Seanss kestab: " + super.getKestus() + " minutit," + '\n' + "Saalis: " + super.getSaal();
    }
}
