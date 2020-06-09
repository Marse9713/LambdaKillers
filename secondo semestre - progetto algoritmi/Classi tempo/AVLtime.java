package seconda_parte_algoritmi.AVLtime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class AVLtime
{
    public static class Nodo
    {
        public int key;
        public String num;
        public int altezza;
        private Nodo ns;
        private Nodo nd;

        public Nodo(int key, String num)
        {
            this.key = key;
            this.num = num;
            altezza = 1;
            ns = null;
            nd = null;
        }

        public int getKey()
        {
            return key;
        }

        public String getNum()
        {
            return num;
        }

        public int getAltezza()
        {
            return altezza;
        }

    }

    public static class AVL
    {
        Nodo radice;

        public int altezza(Nodo N)
        {
            if (N == null)
                return 0;

            return N.altezza;
        }

        public int max(int a, int b)
        {
            return (a > b) ? a : b;
        }

        public Nodo roatazioneDestra(Nodo y)
        {
            Nodo x = y.ns;
            Nodo T2 = x.nd;

            x.nd = y;
            y.ns = T2;

            y.altezza = max(altezza(y.ns), altezza(y.nd)) + 1;
            x.altezza = max(altezza(x.ns), altezza(x.nd)) + 1;

            return x;
        }

        public Nodo roatazioneSinistra(Nodo x)
        {
            Nodo y = x.nd;
            Nodo T2 = y.ns;

            y.ns = x;
            x.nd = T2;

            x.altezza = max(altezza(x.ns), altezza(x.nd)) + 1;
            y.altezza = max(altezza(y.ns), altezza(y.nd)) + 1;

            return y;
        }

        public int bilancio(Nodo N)
        {
            if (N == null)
            {
                return 0;
            }

            return altezza(N.ns) - altezza(N.nd);
        }

        public Nodo inserimento(Nodo nodo, int key, String nome)
        {
            if (nodo == null)
            {
                return (new Nodo(key, nome));
            }

            if (key < nodo.key)
            {
                nodo.ns = inserimento(nodo.ns, key, nome);
            }
            else if (key > nodo.key)
            {
                nodo.nd = inserimento(nodo.nd, key, nome);
            }
            else
            {
                return nodo;
            }

            nodo.altezza = 1 + max(altezza(nodo.ns), altezza(nodo.nd));

            int bilancio = bilancio(nodo);

            if (bilancio > 1 && key < nodo.ns.key)
            {
                return roatazioneDestra(nodo);
            }

            if (bilancio < -1 && key > nodo.nd.key)
            {
                return roatazioneSinistra(nodo);
            }

            if (bilancio > 1 && key > nodo.ns.key)
            {
                nodo.ns = roatazioneSinistra(nodo.ns);
                return roatazioneDestra(nodo);
            }

            if (bilancio < -1 && key < nodo.nd.key)
            {
                nodo.nd = roatazioneDestra(nodo.nd);
                return roatazioneSinistra(nodo);
            }

            return nodo;
        }

        public Nodo ricerca(int valore, Nodo radice)
        {
            if (radice == null || valore == radice.getKey())
            {
                return radice;
            }
            else if (valore < radice.getKey())
            {
                return ricerca(valore, radice.ns);
            }
            else
            {
                return ricerca(valore, radice.nd);
            }
        }


        public void preorder(Nodo n)
        {
            if (n == null)
            {
                System.out.print("NULL ");
            }
            else if (n != null)
            {
                System.out.print(n.getKey() + ":" + n.getNum() + ":" + n.getAltezza() + " ");
                preorder(n.ns);
                preorder(n.nd);
            }
        }
    }

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
        double[] result = new double[3];
        result[0] = e;
        result[1] = delta;
        result[2] = s;
        return result;
    }

    public static void main(String[] args) throws IOException {

        int c = 2;
        double za = 2.32;
        double percent = 0.01;
        long tMin = (long)(Granularita()/percent);

        int contatore = 0;

        double[] sd = new double[10000];
        double[] mis;
        double DELTA=0.01;
        for(int i = 100; i<=1000; i = i + ((i * 10) / 100)) {

            System.out.println(i);
            mis = misurate(i, c, za, tMin, DELTA);
            if (mis[0] < 1000) {

                sd[contatore * 3] = mis[0]; //tempo
                sd[(contatore * 3) + 1] = mis[1]; //delta
                sd[(contatore * 3) + 2] = mis[2]; //s
                contatore++;
            }
            System.out.println("i:\t" + i + "\te:\t" + mis[0]);
        }


        XSSFWorkbook workbook = new XSSFWorkbook();

        OutputStream os = new FileOutputStream("TempiAVL.xlsx");

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
        cell02.setCellValue("Tempo ammortizzato");

        int cont = 0;

        for (int nn = 100; nn <= 1000; nn = nn + ((nn * 10) / 100)) {
            Row row1 = sheet.createRow(cont + 3); //numero elementi
            Cell cell2 = row1.createCell(1);
            cell2.setCellValue(nn);

            Cell cell3 = row1.createCell(2); //tempo
            cell3.setCellValue(sd[(cont * 3)]);

            Cell cell4 = row1.createCell(3); //delta
            cell4.setCellValue(sd[(cont * 3) + 1]);

            Cell cell5 = row1.createCell(4); //derivazione standard
            cell5.setCellValue(sd[cont * 3] + 2);

            Cell cell6 = row1.createCell(5); //tempo ammortizzato
            cell6.setCellValue(sd[cont * 3] / nn);

            cont++;
        }

        workbook.write(os);

    }

    public static void generatore(ArrayList lista)
    {
        AVL albero = new AVL();

        for (int i = 0; i < lista.size(); i++)
        {
            Nodo cercato = albero.ricerca((int) lista.get(i), albero.radice);

            if (cercato == null)
            {
                albero.radice = albero.inserimento(albero.radice, (int) lista.get(i), "");
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
}
