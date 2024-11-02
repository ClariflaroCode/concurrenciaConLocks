public class Consumidor implements Runnable {
    private long waitTime;
    private IBuffer<Integer> buffer;

    public Consumidor(IBuffer<Integer> buffer, long waitTime) {
        this.buffer = buffer;
        this.waitTime = waitTime;
    }
    //habría que bloquear el buffer antes de acceder a él
    //para facilitar el diseño vamos a hacerlo desde la clase del buffer
    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        while(true) {
            Integer i = this.buffer.get();
            System.out.println("El thread "+name+" consumio "+i);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}


