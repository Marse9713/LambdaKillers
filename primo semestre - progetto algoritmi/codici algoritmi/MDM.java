import java.util.*;

public class MDM
{
    public static void main(String[] args){

        Scanner Int = new Scanner(System.in);

        ArrayList<Integer> array = new ArrayList<>(0);

        String a = Int.nextLine();
        String[] b = a.split(" ");

        int k = Int.nextInt();
        Int.close();

        for (String aa : b)
        {
            if (a.isEmpty())
            {
                break;
            }
            else
            {
                int n = Integer.valueOf(String.valueOf(aa));
                array.add(n);
            }
        }
        System.out.println(MedianSelect(array, 0, array.size() - 1, k));
    }

    public static void scambia(ArrayList<Integer> array, int a, int b){

        int a1 = array.get(a);
        array.set(Math.toIntExact(a), array.get(b));
        array.set(b, a1);

    }

    public static int Mediana(ArrayList<Integer> array, int inizio, int fine){

        return array.get(inizio + fine / 2);
    }

    public static int Partizione(ArrayList<Integer> array, int inizio, int fine, int mdm){

        for (int i = inizio; i < fine; i++){

            if (array.get(i) == mdm){

                scambia(array, i, fine);
                break;

            }

        }

        int pivot = array.get(fine);
        int contatore = inizio;

        for (int j = inizio; j <= fine; j++){

            if (array.get(j) < pivot){

                scambia(array, contatore, j);
                contatore = contatore + 1;

            }

        }

        scambia(array, fine, contatore);
        return contatore;
    }

    public static int MedianSelect(ArrayList<Integer> array, int inizio, int fine, int k) {

        if (k > 0 && k <= fine - inizio + 1) {

            int a = fine - inizio + 1;
            ArrayList<Integer> arraymediane = new ArrayList<>();
            int i;
            for (i = 0; i < a / 5; i++) {

                arraymediane.add(Mediana(array, inizio + i * 5, 5));

            }

            if (i * 5 < a) {

                arraymediane.add(Mediana(array, inizio + i * 5, a % 5));

            }

            int mdm;

            if (i == 1) {

                mdm = arraymediane.get(i - 1);

            } else {

                mdm = MedianSelect(arraymediane, 0, i - 1, i / 2);

            }

            int posizione = Partizione(array, inizio, fine, mdm);

            if (posizione - inizio == k - 1) {

                return array.get(posizione);

            }

            if (posizione - inizio > k - 1) {

                return MedianSelect(array, inizio, posizione - 1, k);

            }

            return MedianSelect(array, posizione + 1, fine, k - posizione + inizio - 1);

        }

        return -1;

    }

}