package PA2.RobeFinali;

import java.util.Scanner;

public class ABRTree
{
    public static class Nodo
    {
        public int key;
        public String num ;
        private Nodo gen;
        private Nodo ns;
        private Nodo nd;

        public Nodo(int key, String num)
        {
            this.key = key;
            this.num = num;
            gen = null;
            ns = null;
            nd = null;
        }

        public Nodo(int key, String num, Nodo gen, Nodo ns, Nodo nd)
        {
            this.key = key;
            this.num = num;
            this.gen = gen;
            this.ns = ns;
            this.nd = nd;
        }

        public int getKey()
        {
            return key;
        }

        public String getNum()
        {
            return num;
        }

        public Nodo getGen()
        {
            return gen;
        }

        public Nodo getNs()
        {
            return ns;
        }

        public Nodo getNd()
        {
            return nd;
        }

        public void setKey(int key)
        {
            this.key = key;
        }

        public void setNum(String num)
        {
            this.num = num;
        }

        public void setGen(Nodo gen)
        {
            this.gen = gen;
        }

        public void setNs(Nodo ns)
        {
            this.ns = ns;
        }

        public void setNd(Nodo nd)
        {
            this.nd = nd;
        }
    }

    public static class AlberoBinario
    {
        Nodo radice;

        public AlberoBinario()
        {
            radice = null;
        }

        public void agValore(AlberoBinario albero, int valore, String nome)
        {
            Nodo daIns = new Nodo(valore, nome);
            inserimento(radice, daIns);
        }

        public void inserimento(Nodo attuale, Nodo daIns)
        {

            if (attuale == null)
            {
                radice = daIns;
                return;
            }

            if (daIns.getKey() <= attuale.getKey())
            {
                if (attuale.getNs() == null)
                {
                    attuale.setNs(daIns);
                    daIns.setGen(attuale);
                }
                else
                {
                    inserimento(attuale.getNs(), daIns);
                }
            }
            else
            {
                if (attuale.getNd() == null)
                {
                    attuale.setNd(daIns);
                    daIns.setGen(attuale);
                }
                else
                {
                    inserimento(attuale.getNd(), daIns);
                }
            }
        }

        public Nodo ricerca(int valore, Nodo radice)
        {
            if (radice == null || valore == radice.getKey())
            {
                return radice;
            }
            else if (valore < radice.getKey())
            {
                return ricerca(valore, radice.getNs());
            }
            else
            {
                return ricerca(valore, radice.getNd());
            }
        }

        public  void preorder(AlberoBinario albero)
        {
            preorder1(radice);
        }

        public void preorder1(Nodo n)
        {

            if (n == null)
            {
                System.out.print("NULL ");
            }
            else
            {
                System.out.print(n.getKey() + ":" + n.getNum() + " ");
                preorder1(n.getNs());
                preorder1(n.getNd());
            }
        }
    }

    public static String comando = " ";
    public static int valore = 0;
    public static String nome = " ";

    public static void main(String[] args)
    {
        AlberoBinario albero = new AlberoBinario();

        generatore(albero);
    }

    public static void generatore(AlberoBinario albero)
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
                    albero.agValore(albero, valore, nome);
                }
                else if (comando.equals("show"))
                {
                    albero.preorder(albero);
                    System.out.print("\n");
                }
                else if (comando.equals("find"))
                {
                    System.out.println(albero.ricerca(valore, albero.radice).getNum());
                }
                else if (comando.equals("exit"))
                {
                    break;
                }
            }
        }
    }

}
