import java.util.*;

public class QSAlg {

    public static void main(String[] args) {

        Scanner Int = new Scanner(System.in);

        String a = Int.nextLine();
        String[] b = a.split(" ");

        int k = Int.nextInt();
        Int.close();

        ArrayList<Integer> data = new ArrayList<>();

        for (String aa : b)
        {
            int n = Integer.valueOf(String.valueOf(aa));
            data.add(n);
        }
        assert(k>=1);
        System.out.println(QuickSelect(data, 0, data.size(), k-1));
    }

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