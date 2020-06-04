package PA2.RobeFinali;

import java.util.Scanner;

public class AVLTree
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

    public static String comando = " ";
    public static int valore = 0;
    public static String nome = " ";

    public static void main(String[] args)
    {
        AVL albero = new AVL();

        generatore(albero);
    }

    public static void generatore(AVL albero)
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
                    albero.radice = albero.inserimento(albero.radice, valore, nome);
                }
                else if (comando.equals("show"))
                {
                    albero.preorder(albero.radice);
                    System.out.print("\n");
                }
                else if (comando.equals("find"))
                {
                    Nodo x = albero.ricerca(valore, albero.radice);
                    System.out.println(x.getNum());
                }
                else if (comando.equals("exit"))
                {
                    break;
                }
            }
        }
    }
}

