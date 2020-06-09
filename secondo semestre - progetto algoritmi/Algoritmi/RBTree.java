import java.util.Scanner;

public class RBTree
{
    public static final int RED = 0;
    public static final int BLACK = 1;

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

    public static final Nodo nil = new Nodo(-1, "");
    public static Nodo radice = nil;

    public static void inserimento(Nodo nodo)
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
                    }
                    else
                    {
                        temp = temp.sinistro;
                    }
                }
                else if (nodo.key >= temp.key)
                {
                    if (temp.destro == nil)
                    {
                        temp.destro = nodo;
                        nodo.parent = temp;
                        break;
                    }
                    else
                    {
                        temp = temp.destro;
                    }
                }
            }

            fixTree(nodo);

        }
    }

    public static void fixTree(Nodo nodo)
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
            }
            else
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

    public static void rotazioneSinistra(Nodo nodo)
    {
        if (nodo.parent != nil)
        {
            if (nodo == nodo.parent.sinistro)
            {
                nodo.parent.sinistro = nodo.destro;
            }
            else
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
        }
        else
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

    public static void rotazioneDestra(Nodo nodo)
    {
        if (nodo.parent != nil)
        {
            if (nodo == nodo.parent.sinistro)
            {
                nodo.parent.sinistro = nodo.sinistro;
            }
            else
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
        }
        else if (nodo != null && nodo.colore == BLACK)
        {
            System.out.print(nodo.key + ":" + nodo.nome + ":" + "black ");
            preorder(nodo.sinistro);
            preorder(nodo.destro);
        }
        else if (nodo != null && nodo.colore == RED)
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

    public static String comando = " ";
    public static int valore = 0;
    public static String nome = " ";

    public static void main(String[] args)
    {
        generatore();
    }

    public static void generatore()
    {
        Scanner Int = new Scanner(System.in);

        {
            while (Int.hasNextLine())
            {
                String a = Int.nextLine();
                String[] b = a.split(" ", 3);

                comando = b[0];

                if (b.length >= 2)
                {
                    valore = Integer.valueOf(b[1]);
                }

                if (b.length >= 3)
                {
                    nome = b[2];
                }

                if (comando.equals("insert"))
                {
                    Nodo nodo = new Nodo(valore, nome);
                    inserimento(nodo);
                }
                else if (comando.equals("show"))
                {
                    preorder(radice);
                    System.out.print("\n");
                }
                else if (comando.equals("find"))
                {
                    Nodo x = ricerca(valore, radice);
                    System.out.println(x.nome);
                }
                else if (comando.equals("exit"))
                {
                    break;
                }
            }
        }
    }
}