package seconda_parte_algoritmi.RBtime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class RBtime
{
    public static final int RED = 0;
    public static final int BLACK = 1;
    public static final Nodo nil = new Nodo(-1, "");

    public static class Nodo
    {
        public int key = -1;
        public String nome;
        public int colore;
        private Nodo parent;
        private Nodo sinistro;
        private Nodo destro;

        public Nodo(int key, String nome)
        {
            this.key = key;
            this.nome = nome;
            colore = BLACK;
            parent = nil;
            sinistro = nil;
            destro = nil;
        }

    }

    public static class RBT
    {
        Nodo radice = nil;

        public void inserimento(Nodo nodo)
        {
            Nodo temp = radice;
            if (radice == nil)
            {
                radice = nodo;
                nodo.colore = BLACK;
                nodo.parent = nil;
            }
            else
            {
                nodo.colore = RED;

                while (true)
                {
                    if (nodo.key < temp.key)
                    {
                        if (temp.sinistro == nil)
                        {
                            temp.sinistro = nodo;
                            nodo.parent = temp;
                            break;
                        } else
                        {
                            temp = temp.sinistro;
                        }
                    } else if (nodo.key >= temp.key)
                    {
                        if (temp.destro == nil)
                        {
                            temp.destro = nodo;
                            nodo.parent = temp;
                            break;
                        } else
                        {
                            temp = temp.destro;
                        }
                    }
                }

                fixTree(nodo);

            }
        }

        public void fixTree(Nodo nodo)
        {
            while (nodo.parent.colore == RED)
            {
                Nodo zio = nil;
                if (nodo.parent == nodo.parent.parent.sinistro)
                {
                    zio = nodo.parent.parent.destro;

                    if (zio != nil && zio.colore == RED)
                    {
                        nodo.parent.colore = BLACK;
                        zio.colore = BLACK;
                        nodo.parent.parent.colore = RED;
                        nodo = nodo.parent.parent;
                        continue;
                    }

                    if (nodo == nodo.parent.destro)
                    {
                        nodo = nodo.parent;
                        rotazioneSinistra(nodo);
                    }

                    nodo.parent.colore = BLACK;
                    nodo.parent.parent.colore = RED;
                    rotazioneDestra(nodo.parent.parent);
                } else
                {
                    zio = nodo.parent.parent.sinistro;

                    if (zio != nil && zio.colore == RED)
                    {
                        nodo.parent.colore = BLACK;
                        zio.colore = BLACK;
                        nodo.parent.parent.colore = RED;
                        nodo = nodo.parent.parent;
                        continue;
                    }

                    if (nodo == nodo.parent.sinistro)
                    {
                        nodo = nodo.parent;
                        rotazioneDestra(nodo);
                    }

                    nodo.parent.colore = BLACK;
                    nodo.parent.parent.colore = RED;
                    rotazioneSinistra(nodo.parent.parent);
                }
            }

            radice.colore = BLACK;
        }

        public void rotazioneSinistra(Nodo nodo)
        {
            if (nodo.parent != nil)
            {
                if (nodo == nodo.parent.sinistro)
                {
                    nodo.parent.sinistro = nodo.destro;
                } else
                {
                    nodo.parent.destro = nodo.destro;
                }

                nodo.destro.parent = nodo.parent;
                nodo.parent = nodo.destro;

                if (nodo.destro.sinistro != nil)
                {
                    nodo.destro.sinistro.parent = nodo;
                }

                nodo.destro = nodo.destro.sinistro;
                nodo.parent.sinistro = nodo;
            } else
            {
                Nodo destro = radice.destro;
                radice.destro = destro.sinistro;
                destro.sinistro.parent = radice;
                radice.parent = destro;
                destro.sinistro = radice;
                destro.parent = nil;
                radice = destro;
            }
        }

        public void rotazioneDestra(Nodo nodo)
        {
            if (nodo.parent != nil)
            {
                if (nodo == nodo.parent.sinistro)
                {
                    nodo.parent.sinistro = nodo.sinistro;
                } else
                {
                    nodo.parent.destro = nodo.sinistro;
                }

                nodo.sinistro.parent = nodo.parent;
                nodo.parent = nodo.sinistro;

                if (nodo.sinistro.destro != nil)
                {
                    nodo.sinistro.destro.parent = nodo;
                }
                nodo.sinistro = nodo.sinistro.destro;
                nodo.parent.destro = nodo;
            }
            else
            {
                Nodo sinistro = radice.sinistro;
                radice.sinistro = radice.sinistro.destro;
                sinistro.destro.parent = radice;
                radice.parent = sinistro;
                sinistro.destro = radice;
                sinistro.parent = nil;
                radice = sinistro;
            }
        }

        public static void preorder(Nodo nodo)
        {
            if (nodo == nil)
            {
                System.out.print("NULL ");
            } else if (nodo != null && nodo.colore == BLACK)
            {
                System.out.print(nodo.key + ":" + nodo.nome + ":" + "black ");
                preorder(nodo.sinistro);
                preorder(nodo.destro);
            } else if (nodo != null && nodo.colore == RED)
            {
                System.out.print(nodo.key + ":" + nodo.nome + ":" + "red ");
                preorder(nodo.sinistro);
                preorder(nodo.destro);
            }
        }

        public static Nodo ricerca(int valore, Nodo radice)
        {
            if (radice == null || valore == radice.key)
            {
                return radice;
            }
            else if (valore < radice.key)
            {
                return ricerca(valore, radice.sinistro);
            }
            else
            {
                return ricerca(valore, radice.destro);
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

                    sd[contatore * 3] = mis[contatore * 3]; //tempo
                    sd[(contatore * 3) + 1] = mis[(contatore * 3) + 1]; //delta
                    sd[(contatore * 3) + 2] = mis[(contatore * 3) + 2]; //s
                    contatore++;

                }
                System.out.println("i:\t" + i + "\te:\t" + mis[0]);
            }


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
            RBT albero = new RBT();

            for (int i = 0; i < lista.size(); i++)
            {
                Nodo cercato = albero.ricerca((int) lista.get(i), albero.radice);

                if (cercato == null)
                {
                    Nodo nodo = new Nodo((int) lista.get(i), "");
                    albero.inserimento(nodo);
                }
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
