import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class TempoHeap
{

    public static ArrayList<nodo> h1 = new ArrayList<>(0);

    public static void scambia(ArrayList<nodo> array, int a, int b)
    {
        int a0 = array.get(a).getKey();
        int b0 = array.get(a).getPos();

        int a1 = array.get(b).getKey();
        int b1 = array.get(b).getPos();

        array.set(a, new nodo(a1, b0));
        array.set(b, new nodo(a0, b1));
    }

    public static void scambia1(ArrayList<nodo> array, int a, int b)
    {
        nodo aa, bb;

        aa = array.get(a);
        bb = array.get(b);

        array.set(a, bb);
        array.set(b, aa);
    }

    public static int Left(int i)
    {
        return (i * 2) + 1;
    }

    public static int Right(int i)
    {
        return (2 * i) + 2;
    }

    public static int Parente(int i)
    {
        if(i%2 == 1)
        {
            return i / 2;
        }
        else
        {
            return (i - 1) / 2;
        }
    }

    public static void MinHeapify(ArrayList<nodo> array, int i)
    {
        int l = Left(i);
        int r = Right(i);
        int min = i;

        if (l < array.size() && array.get(l).getKey() < array.get(min).getKey())
        {
            min = l;
        }

        if (r < array.size() && array.get(r).getKey() < array.get(min).getKey())
        {
            min = r;
        }

        if (!(min == i)) {
            scambia(array, i, min);
            MinHeapify(array, min);
        }
    }

    public static void MinHeapify1(ArrayList<nodo> array, int i)
    {
        int l = Left(i);
        int r = Right(i);
        int min = i;

        if (l < array.size() && array.get(l).getKey() < array.get(min).getKey())
        {
            min = l;
        }

        if (r < array.size() && array.get(r).getKey() < array.get(min).getKey())
        {
            min = r;
        }

        if (!(min == i)) {
            scambia1(array, i, min);
            MinHeapify1(array, min);
        }
    }

    public static void BuildMinHeap(ArrayList<nodo> array)
    {
        int heapSize = array.size();

        for (int i = (heapSize / 2); i > 0; i--)
        {
            MinHeapify(array, i - 1);
        }
    }


    public static void Inserimento(ArrayList<nodo> array, nodo s)
    {
        array.add(s);
        FaiSalire(array, array.size()-1);
    }

    public static void FaiSalire(ArrayList<nodo> array, int posizione){
        int PosParente = Parente(posizione);
        int ValParente = array.get(PosParente).getKey();

        if(posizione == 0){
            return;
        }

        if(array.get(posizione).getKey() <= ValParente){
            scambia1(array, PosParente, posizione);
            FaiSalire(array, PosParente);
        }
    }

    public static long heapSelect(ArrayList<nodo> array, int k)
    {
        // BuildMinHeap(array);
        ArrayList<nodo> h2 = new ArrayList<>();
        h2.add(new nodo(array.get(0).getKey(), array.get(0).getPos()));

        for (int i = 0; i < k - 1; i++)
        {
            int parente = h2.get(0).getPos();
            int fSinistro = Left(parente);
            int fDestro = Right(parente);

            scambia1(h2,0, h2.size() -1);
            h2.remove(h2.size()-1);
            MinHeapify1(h2, 0);

            if (fSinistro < array.size())
            {
                Inserimento(h2, array.get(fSinistro));
            }
            if (fDestro < array.size())
            {
                Inserimento(h2, array.get(fDestro));
            }

        }
        return h2.get(0).key;
    }

    public static class nodo
    {
        public Integer key;
        private int pos;

        public nodo(int key, int pos)
        {
            this.key = key;
            this.pos = pos;
        }

        public int getKey()
        {
            return key;
        }

        public int getPos()
        {
            return pos;
        }
    }

    private static ArrayList<nodo> prepare (int d){

        return creatore(d);

    }

    private static long execute(ArrayList<nodo> array, int d){

        return heapSelect(array, d / 2);

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

        int c = 5;
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

        ArrayList<nodo> array = new ArrayList<>();
        int posizione = 0;

        for (int i = 0; i < cicli; i++)
        {
            int a = random.nextInt(y) + limiteI;
            array.add(new nodo(a, posizione));
            posizione = posizione + 1;
        }

        BuildMinHeap(array);
        return array;

    }
}
