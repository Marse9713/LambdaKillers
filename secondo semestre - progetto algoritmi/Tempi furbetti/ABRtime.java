package seconda_parte_algoritmi.ABRtime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ABRtime
{
    private static ArrayList<Integer> prepare (int d){

        return creatore(d);

    }

    private static void execute(ArrayList<Integer> array, int d){

        generatore(array);

    }


    public static long Granularita(){
        long t0 = System.currentTimeMillis();
        long t1 = System.currentTimeMillis();

        while (t1 == t0){
            t1 = System.currentTimeMillis();
        }

        return (t1 - t0);

    }

    public static double ripCalculateTare(int d, long tMin){

        long t0 = 0;
        long t1 = 0;
        long rip = 1;

        while (t1 - t0 <= tMin){
            rip = rip * 2;
            t0 = System.currentTimeMillis();
            for (int i = 1; i <= rip; i++){
                prepare(d);
            }
            t1 = System.currentTimeMillis();
        }

        long max=rip;
        long min=rip/2;
        int failedCycles=5;
        while((max-min)>=failedCycles) {
            rip= (max+min)/2; //medium value
            t0=System.currentTimeMillis() ;
            for(int i=0; i<=rip; i++) {
                prepare(d);
            }
            t1=System.currentTimeMillis();
            if(t1-t0<= tMin) {
                min=rip;
            }else {
                max=rip;
            }
        }
        return max;

    }

    private static long ripCalculateGross(int d, long tMin){
        long t0=0;
        long t1=0;
        long rip=1;
        while(t1-t0 <= tMin) {
            rip = rip*2; //exponential growth
            t0 = System.currentTimeMillis();
            for(int i = 0; i <= rip; i++) {
                execute(prepare(d), d);
            }
            t1= System.currentTimeMillis();
        }
        //exact research of repetition's number by bisection method approximated to 5 cycles.
        long max = rip;
        long min = rip / 2 ;
        int cicliErrati = 5 ;

        while (max-min >= cicliErrati) {
            rip = (max+min) / 2 ;
            t0 = System.currentTimeMillis();
            for(int i = 1; i<=rip; i++) {
                execute (prepare(d), d);
            }
            t1= System.currentTimeMillis();
            if(t1-t0 <= tMin) {
                min = rip;
            } else {
                max = rip;
            }
        }
        return max;
    }

    private static double mediumNetTime(int d, long tMin) {

        double ripTara = ripCalculateTare(d, tMin);
        double ripLordo = ripCalculateGross(d, tMin);
        long t0 = System.currentTimeMillis();
        for(int i = 1; i<=ripTara; i++){
            prepare(d);
        }
        long t1 = System.currentTimeMillis();
        long tTara = t1-t0;
        t0 = System.currentTimeMillis();
        for(int i = 1; i<=ripLordo; i++){
            execute(prepare(d), d);
        }
        t1 = System.currentTimeMillis();
        double tLordo = t1 - t0;  //execution full time
        double tMedio = (tLordo / ripLordo) - (tTara / ripTara); //execution medium time
        return tMedio;
    }

    private static double[] misurate(int d, int c, double za, long tMin, double DELTA) {
        double t=0;
        double sum2 = 0;
        double cn = 0;
        double e;
        double s;
        double delta;
        double m;
        do {
            for(int i=1; i<=c; i++) {
                m = mediumNetTime(d, tMin);
                t+=m;
                sum2 = sum2 +(m*m);
            }
            cn= cn+c;
            e=t/cn;
            s= Math.sqrt((sum2/cn - (e*e)));
            delta= (1/Math.sqrt(cn)) *za *s;
        }
        while(delta>DELTA);
        double[] result = new double[2];
        result[0] = e;
        result[1] = delta;
        return result;
    }

    public static void main(String[] args) throws IOException {

        int c = 1;
        double za = 2.32;
        double percent = 0.01;
        long tMin = (long) (Granularita() / percent);
        double DELTA = 0.01;

        int contatore = 0;
        int contatore1 = 0;

        double[] mis; // riempimento con i risultati delle singole iterazioni
        double[] mis2; //stesso ma del primo ciclo
        double[] t = new double[1000];
        double[] t2 = new double[1000];
        double[] sum2 = new double[1000];
        double[] sum22 = new double[1000];

        for (int cm = 0; cm <= 1; cm++) {

            if (cm == 0) {

                for (int i = 100; i <= 1000; i = i + ((i * 10) / 100)) {

                    System.out.println(i);
                    mis = misurate(i, c, za, tMin, DELTA);
                    if (mis[0] < 100000) { // deve essere minore di 1 secondo

                        t[contatore] = mis[0];
                        contatore++;

                    }

                    System.out.println("i:\t" + i + "\te:\t" + mis[0]);
                }
            }

            if(cm == 1) {

                for (int i = 100; i <= 1000; i = i + ((i * 10) / 100)) {

                    System.out.println(i + "\t2\t");
                    mis2 = misurate(i, c, za, tMin, DELTA);
                    if (mis2[0] < 100000) { // deve essere minore di 1 secondo

                        t2[contatore1] = mis2[0];
                        contatore1++;

                    }

                    System.out.println("i:\t" + i + "\te:\t" + mis2[0]);
                }
            }
        }
        System.out.println(t[0]);
        System.out.println(t2[0]);
        System.out.println(contatore);
        conversionedati(t, t2, contatore);

    }

    public static void conversionedati(double[] t, double[] t2, int contatore) throws IOException {

        double[] results = new double[1000];
        double tm = 0;
        double sum = 0;
        double cn = 0;
        double em = 0;
        double sm;
        double delta = 0;
        double za = 2.32;
        double m = 0;
        double t0 = 0;
        double t1 = 0;

        for (int in = 0; in <= contatore; in++) {
            for (int i = 1; i <= 2; i++) {
                if (i == 1) {

                    m = t[in];
                    t0 = t[in];
                    tm = tm + m;

                } else {

                    m = t2[in];
                    t1 = t2[in];
                    tm = tm + m;

                }

                sum = sum + (m * m);

                tm = 0;
                m = 0;

            }

            cn = 2;
            em = (t0 + t1) / 2; // tempo
            results[(in * 3)] = em;
            sm = Math.sqrt((sum / 2 - (em * em))); // derivazione standard
            results[(in * 3) + 1] = sm;
            delta = (1 / Math.sqrt(2)) * za * sm; // delta
            results[(in * 3) + 2] = delta;

            // System.out.println("\te: \t" + em + "\tsum2: \t" + sum + "\tdelta: \t" + delta + "\tsm: \t" + sm);


            XSSFWorkbook workbook = new XSSFWorkbook();

            OutputStream os = new FileOutputStream("TempiABR.xlsx");

            Sheet sheet = workbook.createSheet();

            Row row = sheet.createRow(1);
            Cell cell = row.createCell(1);

            cell.setCellValue("n");

            Cell cell1 = row.createCell(2);
            cell1.setCellValue("Tempo");

            Cell cell0 = row.createCell(3);
            cell0.setCellValue("delta");

            Cell cell01 = row.createCell(4);
            cell01.setCellValue("ds");

            Cell cell02 = row.createCell(5);
            cell02.setCellValue("Tempo Molleggiato");

            int cont = 0;

            for (int nn = 100; nn <= 1000; nn = nn + ((nn * 10) / 100)) {
                Row row1 = sheet.createRow(cont + 3);
                Cell cell2 = row1.createCell(1);
                cell2.setCellValue(nn);

                Cell cell3 = row1.createCell(2);
                cell3.setCellValue(results[(cont * 3)]);

                Cell cell4 = row1.createCell(3);
                cell4.setCellValue(results[(cont * 3) + 2]);

                Cell cell5 = row1.createCell(4);
                cell5.setCellValue(results[cont * 3] + 1);

                Cell cell6 = row1.createCell(5);
                cell6.setCellValue(results[cont * 3] / nn);

                cont++;
            }

            workbook.write(os);

        }
    }

    public static class Nodo
    {
        public int key;  //valore del nodo
        public String num ; //nome che vine inserito
        private Nodo gen; //nodo genitore
        private Nodo ns; //nodo sinistro
        private Nodo nd; //nodo destro

        public Nodo(int key, String num) //crea un nodo generico
        {
            this.key = key;
            this.num = num;
            gen = null;
            ns = null;
            nd = null;
        }


        public int getKey() //da il valore del nodo
        {
            return key;
        }

        public String getNum() //restituisce il nime
        {
            return num;
        }

        public Nodo getGen() //da il genitore del nodo
        {
            return gen;
        }

        public Nodo getNs() //da il filgio sinistro del nodo
        {
            return ns;
        }

        public Nodo getNd() //da il figlio destro del nodo
        {
            return nd;
        }

        public void setKey(int key) //setta il valore del nodo
        {
            this.key = key;
        }

        public void setNum(String num) //setta il nome
        {
            this.num = num;
        }

        public void setGen(Nodo gen) //setta il genitore del nodo
        {
            this.gen = gen;
        }

        public void setNs(Nodo ns) //setta il filgio sinistro
        {
            this.ns = ns;
        }

        public void setNd(Nodo nd) //setta il filgio destro
        {
            this.nd = nd;
        }
    }

    public static class AlberoBinario
    {
        Nodo radice;

        public AlberoBinario() //crea un albero generico con radice nulla
        {
            radice = null;
        }

        public void agValore(AlberoBinario albero, int valore, String nome) //funzione che aggiuge un valore al albero
        {
            Nodo daIns = new Nodo(valore, nome); //trasforma il valore in un nodo genetico
            inserimento(radice, daIns);
        }

        public void inserimento(Nodo attuale, Nodo daIns) //funzione che aggiunge un nodo al albero
        {

            if (attuale == null) //essendo il primo nodo sempre nullo al inizio viene sostituito con il primo inserimento
            {
                radice = daIns;
                return;
            }

            if (daIns.getKey() <= attuale.getKey()) //se il valore del nodo da inserire è minore o uguale al valore della radice
            {
                if (attuale.getNs() == null)
                {
                    attuale.setNs(daIns);
                    daIns.setGen(attuale);
                }
                else
                {
                    inserimento(attuale.getNs(), daIns); //scende nel albero a sinistra
                }
            }
            else //se il valore è maggiore del valore della radice
            {
                if (attuale.getNd() == null)
                {
                    attuale.setNd(daIns);
                    daIns.setGen(attuale);
                }
                else
                {
                    inserimento(attuale.getNd(), daIns); //scende nel albero a destra
                }
            }
        }

        public Nodo ricerca(AlberoBinario albero, int valore) //funzione di ricerca del valore
        {
            return ricercaInAlbero(valore, radice); //viene richiamata la vera funzione con la radice del albero
        }

        public Nodo ricercaInAlbero(int valore, Nodo radice) //trova il valore cominciando a cercae dalla radice
        {
            if (radice == null || valore == radice.getKey())
            {
                return radice;
            }
            else if (valore < radice.getKey())
            {
                return ricercaInAlbero(valore, radice.getNs()); //scende sul albero a sinistra
            }
            else
            {
                return ricercaInAlbero(valore, radice.getNd()); //scende sul albero  adestra
            }
        }

    }

    public static ArrayList creatore(int n)
    {
        Random random = new Random();
        int limiteI = -100000000;
        int limiteS = 100000000;
        int cicli = n;
        int y = limiteS - limiteI + 1;

        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 0; i < cicli; i++)
        {
            int a = random.nextInt(y) + limiteI;
            array.add(a);
        }

        return array;

    }

    public static void generatore(ArrayList lista)
    {
        AlberoBinario albero = new AlberoBinario();

        for (int i = 0; i < lista.size(); i++)
        {
            Nodo cercato = albero.ricerca(albero, (int)lista.get(i));

            if (cercato == null)
            {
                albero.agValore(albero, (int)lista.get(i), "");
            }
        }
    }
}