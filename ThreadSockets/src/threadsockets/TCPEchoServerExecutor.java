package threadsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPEchoServerExecutor {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { // Verificación del número de argumentos
            throw new IllegalArgumentException("Parametro(s): <Puerto>");
        }

        /* El puerto es el único argumento. Creamos las instancias ServerSocket y Logger como antes;
         no necesitan ser declaradas última aquí, porque no necesitamos un hilo anónimo
         subclase. */
        int echoServPort = Integer.parseInt(args[0]); // Puerto del servidor

        // Crea un socket de servidor para aceptar las conexiones requeridas
        ServerSocket servSock = new ServerSocket(echoServPort);

        Logger logger = Logger.getLogger("practical");
        /*
         El método de fábrica estática newCachedThreadPool () de Ejecutores de clase crea una instancia
         de ExecutorService. Cuando su método execute () se invoca con una instancia Ejecutable, la
         servicio ejecutor crea un nuevo hilo para manejar la tarea de ser necesario. Sin embargo, primero
         intenta volver a utilizar un tema existente. Cuando un mensaje ha estado inactivo durante al menos 60 segundos,
         se retira de la piscina. Esta es casi siempre va a ser más eficaz que cualquiera de
         los dos últimos TCPEchoServer
        
         */
        Executor service = Executors.newCachedThreadPool();  // Despacha svc

        // Corre por siempre, acepta y asigna un hilo para cada conexión
        /*
         Cuando llega una nueva conexión, se crea una nueva instancia EchoProtocol y se pasa al
         ejecutar () de servicio , ya sea que se lo da apagado a un hilo ya existente o
         crea un nuevo hilo para manejarlo. Tenga en cuenta que en el estado de equilibrio, el grupo de subprocesos en caché
         Servicio Ejecutor acaba teniendo sobre el número correcto de las discusiones, de modo que cada hilo
         se mantiene ocupado y la creación / destrucción de las discusiones es raro. */
        while (true) {
            Socket clntSock = servSock.accept(); // esperando bloque de conexión
            service.execute(new EchoProtocol(clntSock, logger));
        }

    }

}


/*
 En los apartados anteriores hemos visto que encapsula los detalles del cliente-servidor
 protocolo (como en EchoProtocol.java) nos permite utilizar diferentes "despachar" métodos con el mismo
 implementación del protocolo (por ejemplo, TCPEchoServerThread.java y TCPEchoServerThreadPool.java). En
 hecho lo mismo es cierto para los propios métodos de secuenciación. El Ejecutor interfaz (parte
 del paquete java.util.concurrent) representa un objeto que se ejecuta instancias ejecutables
 de acuerdo con alguna estrategia, que pueden incluir detalles sobre las colas y la programación, o cómo
 se seleccionan los trabajos para su ejecución. La interfaz Ejecutor especifica un único método:
 interfaz Ejecutor {
 void execute (tarea Ejecutable);
 }
 Java proporciona una serie de implementaciones incorporadas de Ejecutor que sean convenientes y
 fácil de usar, y otros que son ampliamente configurable. Algunas de ellas ofrecen el manejo de
 detalles sucios como el mantenimiento de rosca. Por ejemplo, si un hilo se detiene debido a un uncaught
 excepción u otra falla, se reproducen automáticamente un nuevo hilo para reemplazarlo. 

 La interfaz ExecutorService extiende Ejecutor para proporcionar una instalación más sofisticada
 que permite un servicio a ser cerrado, ya sea con gracia o abruptamente. ExecutorService también permite
 para las tareas que devuelven un resultado, a través de la interfaz invocable, que es como Ejecutable, sólo con un
 valor de retorno.
 Las instancias de ExecutorService se pueden obtener llamando a varios métodos estáticos de fábrica
 los Ejecutores clase conveniencia. El programa TCPEchoServerExecutor.java ilustra el uso
 de los servicios básicos del ejecutor. 

 */
