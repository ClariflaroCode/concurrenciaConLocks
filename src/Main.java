public class Main {
    public static void main(String[] args) {
        IBuffer<Integer> buffer = new LocksBuffer<>(10);
        Productor p = new Productor(100, buffer, 1000);
        Consumidor consumidor = new Consumidor(buffer, 10);
        Thread tp = new Thread(p);
        tp.setName("Productor");
        tp.start();
        Thread tc = new Thread(consumidor);
        tc.setName("Consumidor");
        tc.start();
    }
}