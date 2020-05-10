package programmi;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class classetempoqs {

    private static ArrayList<Integer> prepare (int d){

        return creatore(d);

    }

    private static long execute(ArrayList<Integer> array, int d){

        return QuickSelect(array, 0, array.size() - 1, d / 2);

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
            rip= (max+min)/2;
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
        double tLordo = t1 - t0; 
        double tMedio = (tLordo / ripLordo) - (tTara / ripTara);
        return tMedio;
    }

    private static double[] misurate(int d, int c, double za,
                                     long tMin, double DELTA) {
        double t=0;
        double sum2 = 0;
        double cn = 0;
        double e;
        double s;
        double delta;
        double m = 0;
        do {
            for(int i=1; i<=c; i++) {
                m = mediumNetTime(d, tMin);
                t+=m;
                sum2 = sum2 + (m*m);
            }
            cn = cn+c;
            e=t/cn;
            s= Math.sqrt((sum2/cn - (e*e)));
            delta = (1/Math.sqrt(cn)) *za *s;
        }
        while(delta > DELTA);
        double[] result = new double[4];
        result[0] = e;
        result[1] = sum2;
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

                for (int i = 100; i <= 6000000; i = i + ((i * 10) / 100)) {

                    System.out.println(i);
                    mis = misurate(i, c, za, tMin, DELTA);
                    if (mis[0] < 1000) { // deve essere minore di 1 secondo

                        t[contatore] = mis[0];
                        contatore++;

                    }

                    System.out.println("i:\t" + i + "\te:\t" + mis[0]);
                }
            }

            if(cm == 1) {

                for (int i = 100; i <= 6000000; i = i + ((i * 10) / 100)) {

                    System.out.println(i + "\t2\t");
                    mis2 = misurate(i, c, za, tMin, DELTA);
                    if (mis2[0] < 1000) { // deve essere minore di 1 secondo

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
            em = (t0 + t1) / 2;
            results[(in * 3)] = em;
            sm = Math.sqrt((sum / 2 - (em * em)));
            results[(in * 3) + 1] = sm;
            delta = (1 / Math.sqrt(2)) * za * sm;
            results[(in * 3) + 2] = delta;

           // System.out.println("\te: \t" + em + "\tsum2: \t" + sum + "\tdelta: \t" + delta + "\tsm: \t" + sm);


            XSSFWorkbook workbook = new XSSFWorkbook();

            OutputStream os = new FileOutputStream("Tempiqs10k.xlsx");

            Sheet sheet = workbook.createSheet();

            Row row = sheet.createRow(1);
            Cell cell = row.createCell(1);

            cell.setCellValue("n");

            Cell cell1 = row.createCell(2);
            cell1.setCellValue("Tempo");

            Cell cell0 = row.createCell(3);
            cell0.setCellValue("delta");

            Cell cell01 = row.createCell(4);
            cell01.setCellValue("sm");

            int cont = 0;

            for (int nn = 100; nn <= 6000000; nn = nn + ((nn * 10) / 100)) {
                Row row1 = sheet.createRow(cont + 3);
                Cell cell2 = row1.createCell(1);
                cell2.setCellValue(nn);

                Cell cell3 = row1.createCell(2);
                cell3.setCellValue(results[(cont * 3)]);

                Cell cell4 = row1.createCell(3);
                cell4.setCellValue(results[(cont * 3) + 2]);

                Cell cell5 = row1.createCell(4);
                cell5.setCellValue(results[(cont * 3) + 1]);
                cont++;
            }

            workbook.write(os);

        }
    }


    public static ArrayList creatore(int n) //crea l'array con tutti gli input forniti
    {
        Random random = new Random();
        int limiteI = -100000000; // numero più piccolo
        int limiteS = 100000000; // numero più grande
        int cicli = n;
        int y = limiteS - limiteI + 1;

        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 0; i < cicli; i++) // quantita desiderata di numeri
        {
            int a = random.nextInt(y) + limiteI;
            array.add(a);
        }

        return array;

    }

    public static ArrayList<Integer> e = new ArrayList<Integer>();

    public static void scambia(ArrayList<Integer> array, int a, int b) // normale funzione di scambio
    {
        int a1 = array.get(a);
        array.set(Math.toIntExact(a), array.get(b));
        array.set(b, a1);
    }

    public static int partition(ArrayList<Integer> array, int inizio, int fine) // classica funzione partition
    {
        int pivot = array.get(fine);
        int sindice = inizio;
        for (int i = inizio; i < fine; i++)
        {
            if (array.get(i) < pivot)
            {
                scambia(array, sindice, i);
                sindice = sindice + 1;
            }
        }
        scambia(array, fine, sindice);
        return sindice;
    }

    public static int QuickSelect(ArrayList<Integer> array, int inizio, int fine, int k)
    {
        if (inizio == fine) // array con 1 elemento restituisce tale elemento
        {
            return array.get(inizio);
        }

        int indice = partition(array, inizio, fine);

        if (k == indice) // cerca l'elemento in base al indice calcolato con partition
        {
            return array.get(Math.toIntExact(k));
        }
        else if (k < indice)
        {
            return QuickSelect(array, inizio, indice - 1, k);
        }
        else
        {
            return QuickSelect(array, indice + 1, fine, k);
        }
    }

}
