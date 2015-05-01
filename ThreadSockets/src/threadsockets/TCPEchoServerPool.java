package threadsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPEchoServerPool {

    public static void main(String[] args) throws IOException {
        /*
         El número de puerto para que escuche en el número de hilos son a la vez pasa como argumentos
         to main (). Después de analizar los creamos las instancias ServerSocket y maderero. Tenga en cuenta que
         ambos tienen que ser declaradas final, debido a que se hace referencia dentro de la clase anónima
         instancia creada a continuación. 
        
         */
        if (args.length != 2) { // Verificación del número de argumentos
            throw new IllegalArgumentException("Parámetro(s): <Puerto> <Hilos>");
        }

        int echoServPort = Integer.parseInt(args[0]); // Puerto del Servidor
        int threadPoolSize = Integer.parseInt(args[1]);

        // Crea un socket para el servidor que aceptará las conexiones cuando se requiera
        final ServerSocket servSock = new ServerSocket(echoServPort);

        final Logger logger = Logger.getLogger("practical");

        /*
         Para cada iteración del bucle, se crea una instancia de una clase anónima que se extiende Thread.
         Cuando se llama al método start () de esta instancia, el hilo ejecuta el método (pista) 
        
         */
        for (int i = 0; i < threadPoolSize; i++) {
            Thread thread = new Thread() {
                public void run() {
                    while (true) {
                        try {

                            /*
                             Puesto que hay N diferentes hilos de ejecución del mismo bucle, hasta N hilos puede
                             ser bloqueado en servSock 's accept (), a la espera de una conexión entrante. El sistema
                             asegura que sólo un hilo consigue un zócalo para cualquier conexión particular. Si no hay temas
                             están bloqueados en aceptar () cuando se establece una conexión de cliente (es decir, si son todos
                             ocupado el servicio otras conexiones), la nueva conexión se pone en cola por el sistema hasta
                             la siguiente llamada a accept ()
                            
                             */
                            Socket clntSock = servSock.accept(); // esperando una conexión
                            /*
                             El método handleEchoClient () encapsula el conocimiento de los detalles del protocolo. Registra
                             la conexión cuando termina, así como las excepciones se encuentran en el camino. 
                            
                             */
                            EchoProtocol.handleEchoClient(clntSock, logger); // Manejo 
                        } catch (IOException ex) {
                            /*
                             Desde las discusiones se vuelven a utilizar, la solución hilo piscina sólo paga los gastos generales de la rosca
                             de creación de N veces, independientemente del número total de conexiones de cliente. Desde controlamos
                             el número máximo de hilos de ejecución simultáneamente, podemos controlar la programación y
                             sobrecarga de recursos. Por supuesto, si nos Spawn muy pocos hilos, todavía podemos tener clientes esperando
                             un largo tiempo para el servicio; Por lo tanto, el tamaño de la agrupación de hebras necesita ser sintonizado a la carga,
                             para que el tiempo de conexión del cliente se minimiza. Lo ideal sería un centro de despacho que
                             amplía el grupo de subprocesos (hasta un límite) cuando la carga aumenta, y se encoge a minimizar
                             sobrecarga en momentos en que la carga es ligera.
                             */
                            logger.log(Level.WARNING, "Aceptación del cliente a fallado", ex);
                        }
                    }
                }
            };
            thread.start();
            logger.info("Hilo aceptado e iniciado = " + thread.getName());
        }

    }

}

/*Al igual que el servidor de hilo-per-cliente, un servidor hilo-piscina comienza mediante la creación de un ServerSocket.
 Entonces se genera N hilos, cada uno de los cuales se enrolla para siempre, aceptando conexiones de la (compartido)
 Ejemplo ServerSocket. Cuando varios subprocesos simultáneamente llaman accept () en el mismo Servidor-
 Ejemplo Zócalo, que se establece todos los bloques hasta que una conexión. A continuación, el sistema selecciona uno
 hilo, y la instancia de Socket para la nueva conexión se devuelve sólo en ese hilo . El otro
 hilos permanecen bloqueadas hasta que se establezca la siguiente conexión y otro afortunado ganador es
 elegido.
 Puesto que cada hilo en la piscina bucles para siempre, conexiones de procesamiento de uno a uno, una
 servidor de hilos de la piscina es realmente como un conjunto de servidores iterativos. A diferencia del servidor de hilo por cliente,
 un hilo de rosca de la piscina no termina cuando se acaba con el cliente. En lugar de ello, se inicia
 otra vez, el bloqueo de aceptar (). Un ejemplo del paradigma hilo-pool se muestra en
 TCPEchoServerPool.java. */
