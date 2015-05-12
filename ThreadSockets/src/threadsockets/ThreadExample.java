
package threadsockets;
import java.util.concurrent.TimeUnit;
import static java.lang.System.*;

public class ThreadExample implements Runnable{
  private String greeting; // Imprime un mensaje en la consola
  
    public ThreadExample(String greeting) {
    this.greeting = greeting;
  }
    @Override
    public void run() {
        
            while (true) {
      out.println(Thread.currentThread().getName() + ":  " + greeting); // El método Thread.currentThread estática () devuelve una referencia a la rosca de la cual
                                                                        // se le llama, y ​​getName () devuelve una cadena que contiene el nombre de ese hilo. 
      try {
        // Sleep 0 to 100 milliseconds
          
          /*
           Después de imprimir el mensaje de saludo de su instancia, cada hilo tiene capacidad para una cantidad aleatoria
           de tiempo (entre 0 y 100 milisegundos) llamando al método estático Thread.sleep (),  
           que tiene el número de milisegundos a dormir como un parámetro. Math.random () devuelve
           un doble aleatorio entre 0,0 y 1,0. Thread.sleep () puede ser interrumpido por otro
           hilo, en cuyo caso se produce una InterruptedException. Nuestro ejemplo no incluye
           una llamada de interrupción, por lo que la excepción no sucederá en esta aplicación. 
          */
        TimeUnit.MILLISECONDS.sleep(((long) Math.random() * 100));
      } catch (InterruptedException e) {
      } // Should not happen
    }
     
    }
    
      public static void main(String[] args) {
          /*
           Cada uno de los tres estados en main () hace lo siguiente: 1) crea una nueva instancia de
           ThreadExample con una cadena saludo diferente, 2) pasa esta nueva instancia a la con-
           structor de hilo, y 3) las llamadas a métodos de la nueva instancia Thread start (). Cada hilo
           ejecuta independientemente del método run () de ThreadExample, mientras que la principal terminal de rosca
           Tenga en cuenta que la JVM no termina hasta que todos nondaemon (ver API Thread) hilos
           terminará. 
          */
    new Thread(new ThreadExample("Hello")).start();
    new Thread(new ThreadExample("Aloha")).start();
    new Thread(new ThreadExample("Ciao")).start();
  }
    
}



/*
La interfaz Ejecutable contiene un solo
método prototipo:
interface Runnable {
void run ();
}
Cuando se invoca el método start () de una instancia de Thread, la JVM hace que el
El método de instancia run () que se ejecutará en un nuevo tema, al mismo tiempo que todos los demás.
Mientras tanto, los originales retornos hilo de su llamamiento para iniciar () y continúa su ejecución
de forma independiente. (Tenga en cuenta que se ejecutan llamando directamente () no crea un nuevo hilo, sino que el
ejecutar () es simplemente ejecutar en el hilo de la persona que llama, al igual que cualquier otra llamada al método.) El
declaraciones de método de cada hilo de ejecución () se intercalan de forma no determinista, por lo que en
general, no es posible predecir con precisión el orden en que las cosas van a suceder en diferentes
hilos.
En el siguiente ejemplo, ThreadExample.java implementa la interfaz Ejecutable con una
método run () que imprime repetidamente un saludo a la corriente de salida del sistema. 

*/