import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocksBuffer<T> implements IBuffer<T> {
    private List<T> data;
    private int maxSize;
    private Lock lock;
    private Condition bufferLleno;
    private Condition bufferVacio;

    public LocksBuffer(int maxSize) {
        this.maxSize = maxSize;
        this.data = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.bufferLleno = this.lock.newCondition();
        this.bufferVacio = this.lock.newCondition();
    }
    @Override
    public T get() {

        //debemos apropiarnos del lock
        this.lock.lock(); //bloqueamos el lock
        while (this.data.isEmpty()) {
            //si está vacío, qué variables debo esperar?
            try {
                System.out.println("El Thread "+ Thread.currentThread().getName()+" esta esperando por vacio");
                this.bufferVacio.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
        T val = this.data.remove(0);
        //si sacamos un elemento del buffer, sigue lleno?
        this.bufferLleno.signal(); //le indicamos al buffer que sacamos un elemento.
        this.lock.unlock(); //antes del return es importante desbloquear pero NO antes de hacer el remove
        return val;
    }

    @Override
    public void put(T value) {
        this.lock.lock();
        while(this.data.size() >= this.maxSize) {
            try {
                System.out.println("El Thread "+ Thread.currentThread().getName()+" esta esperando por lleno");
                this.bufferLleno.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.data.add(value);
        this.bufferVacio.signal();
        this.lock.unlock();
    }
}
