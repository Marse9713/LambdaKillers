import java.util.*;

public class Heap {

    public static void main(String[] args) {

        Scanner Int = new Scanner(System.in);  // Crea uno scanner

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
        System.out.println(HeapSelect(data, k-1));
    }

    private class Node{
        int pos;
        int val;

        public Node(int pos, int val){
            this.pos = pos;
            this.val = val;
        }
    }

    private ArrayList<Node> array = new ArrayList<>();

    public Heap(){
    }

    public Heap(List<Integer> h1) {

        int contatore = -1;
        for (Integer i : h1) {
            contatore++;
            array.add(new Node(contatore, i));
        }
        BuildHeap();

    }

    private void BuildHeap(){

        for(int i = ((array.size()/2)-1); i >= 0; --i){
            MinHeapify(i);
        }
    }

    private void MinHeapify(int i) {
        assert(i >= 0);

        int smallest = i;
        int PosLeft = Left(i);
        int PosRight = Right(i);

        if(PosLeft < array.size() && array.get(PosLeft).val < array.get(smallest).val){
            smallest = PosLeft;
        }

        if(PosRight < array.size() && array.get(PosRight).val < array.get(smallest).val){
            smallest = PosRight;
        }

        if(smallest != i){
            Swap(i, smallest);
            MinHeapify(smallest);
        }
    }

    private void Swap(int a, int b){
        Node temp;

        temp = array.get(a);
        array.set(a, array.get(b));
        array.set(b, temp);
    }

    private static int Left(int i)
    {
        return (i * 2) + 1;
    }

    private static int Right(int i)
    {
        return (2 * i) + 2;
    }

    private static int Parente(int i) {

        if (i % 2 == 1) {
            return i / 2;
        } else {
            return ((i - 1) / 2);
        }
    }

    private void Inserimento(Node s){
        array.add(s);
        FaiSalire(array.size()-1);
    }

    public void FaiSalire(int posizione){
        int PosParente = Parente(posizione);
        int ValParente = array.get(PosParente).val;

        if(posizione == 0){
            return;
        }

        if(array.get(posizione).val < ValParente){
            Swap(PosParente, posizione);
            FaiSalire(PosParente);
        }
    }

    private Node Cancellazione(){

        Node t = array.get(0);
        Swap(0, array.size() -1);
        array.remove(array.size()-1);
        MinHeapify(0);
        return t;
    }

    private int GetValue(int i){
        return this.array.get(i).val;
    }

    private Node GetNode(int j){
        return this.array.get(j);
    }

    private int TrovaNodo(Node c){
        for(int i = 0; i < array.size(); i++){
            Node t = array.get(i);
            if(t.pos == c.pos && t.val == c.val){
                return i;
            }
        }
        throw new RuntimeException();
    }

    public int Size(){
        return this.array.size();
    }

    public static int HeapSelect(List<Integer> data, int k){
        Heap h1 = new Heap(data);
        Heap h2 = new Heap();

        h2.Inserimento(h1.GetNode(0));

        for(int i = 0; i < k; i++){
            Node t = h2.Cancellazione();
            int pos = h1.TrovaNodo(t);
            int FiglioSinistro = Left(pos);
            int FiglioDestro = Right(pos);

            if(FiglioSinistro < h1.Size()){
                h2.Inserimento(h1.GetNode(FiglioSinistro));
            }

            if(FiglioDestro < h1.Size()){
                h2.Inserimento(h1.GetNode(FiglioDestro));
            }
        }
        return h2.GetValue(0);
    }
}