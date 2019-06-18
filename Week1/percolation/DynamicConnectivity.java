/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class DynamicConnectivity {
    public static void main(String[] args) {

        int n = 10;
        // IUnionFind uf = new QuickFind(n);
        // IUnionFind uf = new QuickUnion(n);
        IUnionFind uf = new QuickUnionWeightedPathCompression(n);

        uf.union(4, 3);
        uf.union(3, 8);
        uf.union(6, 5);
        uf.union(9, 4);
        uf.union(2, 1);
        uf.union(8, 9);
        System.out.println("Connected(5, 0): " + uf.connected(5, 0)); // FALSE
        uf.union(5, 0);
        System.out.println("Connected(5, 0): " + uf.connected(5, 0)); // TRUE
        uf.union(7, 2);
        uf.union(6, 1);
        System.out.println("Connected(7, 0): " + uf.connected(7, 0)); // TRUE
        System.out.println("Connected(8, 4): " + uf.connected(8, 4)); // TRUE
        System.out.println("Connected(7, 8): " + uf.connected(7, 8)); // FALSE

        uf.debug();
    }

    public interface IUnionFind {
        boolean connected(int p, int q);

        void union(int p, int q);

        void debug();
    }

    // Option 3: QuickUnion - Weighted / Path compression - log * N
    public static class QuickUnionWeightedPathCompression implements IUnionFind {
        private int[] id;
        private int[] sz;

        public QuickUnionWeightedPathCompression(int n) {
            id = new int[n];
            sz = new int[n];

            for (int i = 0; i < n; i++) {
                id[i] = i;
                sz[i] = 1; // initialize sz to 1
            }
        }

        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int i = root(p);
            int j = root(q);

            if (i == j) return;

            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
            }
            else {
                id[j] = i;
                sz[i] += sz[j];
            }
        }

        private int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // this extra line does the trick
                i = id[i];
            }

            return i;
        }

        // Debug Method -----------------------
        public void debug() {
            System.out.println("Debug -------------");

            for (int i = 0; i < id.length; i++)
                System.out.println("id[" + i + "]: " + id[i]);

            System.out.println("-------------------");
        }
    }

    // Option 2: QuickUnion - N
    public static class QuickUnion implements IUnionFind {
        private int[] id;

        public QuickUnion(int n) {
            id = new int[n];

            for (int i = 0; i < n; i++)
                id[i] = i;
        }

        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int i = root(p);
            int j = root(q);
            id[i] = j;
        }

        private int root(int i) {
            while (i != id[i])
                i = id[i];

            return i;
        }

        // Debug Method -----------------------
        public void debug() {
            System.out.println("Debug -------------");

            for (int i = 0; i < id.length; i++)
                System.out.println("id[" + i + "]: " + id[i]);

            System.out.println("-------------------");
        }
    }

    // Option 1: QuickFind - Quadratic
    public static class QuickFind implements IUnionFind {
        private int[] id;

        public QuickFind(int n) {
            id = new int[n];

            for (int i = 0; i < n; i++)
                id[i] = i;
        }

        public boolean connected(int p, int q) {
            return id[p] == id[q];
        }

        public void union(int p, int q) {
            int pid = id[p];
            int qid = id[q];

            for (int i = 0; i < id.length; i++)
                if (id[i] == pid)
                    id[i] = qid;
        }

        // Debug Method -----------------------
        public void debug() {
            System.out.println("Debug -------------");

            for (int i = 0; i < id.length; i++)
                System.out.println("id[" + i + "]: " + id[i]);

            System.out.println("-------------------");
        }
    }
}
