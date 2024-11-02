public class Productor implements Runnable {
    private long waitTime;
    private  int maxGenerated;
    private IBuffer<Integer> buffer;

    public Productor(int maxGenerated, IBuffer<Integer> buffer, long waitTime) {
        this.maxGenerated = maxGenerated;
        this.buffer = buffer;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for(int i = 0; i < maxGenerated; i++) {
            System.out.println("Thread " + name + " produce " + i);
            this.buffer.put(i);

            //El script a continuación, salvo por el sleep, te lo pide Java
            //pero no deberías forzar el corte con una interrupción.
            try {
                Thread.sleep(this.waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
