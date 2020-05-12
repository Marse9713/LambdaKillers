import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class QuickSelect {

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
            rip = rip*2;
            t0 = System.currentTimeMillis();
            for(int i = 0; i <= rip; i++) {
                execute(prepare(d), d);
            }
            t1= System.currentTimeMillis();
        }
        
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

        double[] mis;
        double[] t = new double[1000];

        for (int i = 100; i <= 110; i = i + ((i * 10) / 100)) {

            mis = misurate(i, c, za, tMin, DELTA);
            if (mis[0] < 100000) {

                t[contatore] = mis[0];
                contatore++;

            }
        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        OutputStream os = new FileOutputStream("TempiHS.xlsx");

        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(1);
        Cell cell = row.createCell(1);

        cell.setCellValue("n");

        Cell cell1 = row.createCell(2);
        cell1.setCellValue("Tempo");

        int cont = 0;

        for (int nn = 100; nn <= 110; nn = nn + ((nn * 10) / 100)) {
            Row row1 = sheet.createRow(cont + 3);
            Cell cell2 = row1.createCell(1);
            cell2.setCellValue(nn);

            Cell cell3 = row1.createCell(2);
            cell3.setCellValue(t[(cont)]);
            cont++;
        }

        workbook.write(os);
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

    public static ArrayList<Integer> e = new ArrayList<Integer>();

    public static void scambia(ArrayList<Integer> array, int a, int b)
    {
        int a1 = array.get(a);
        array.set(Math.toIntExact(a), array.get(b));
        array.set(b, a1);
    }

    public static int partition(ArrayList<Integer> array, int inizio, int fine)
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
        if (inizio == fine)
        {
            return array.get(inizio);
        }

        int indice = partition(array, inizio, fine);

        if (k == indice) 
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
