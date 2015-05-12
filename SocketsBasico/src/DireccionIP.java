/*
¿Como funciona el programa?

Se debe aprender que la clase InetAddress que es la clase que representa la dirección del Protocolo de Internet (IP). 

    El método getByName() que es el que determina la dirección IP de un host, teniendo en cuenta el nombre del host.
    El método getHostAddress() devuelve la cadena de dirección IP en la presentación del texto.
    El método getHostName() obtiene el nombre de host para esta dirección IP.
    Por último el método getLocalHost() regresa un LocalHost.


*/

/**
 *
 * @author ASUS
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DireccionIP {
    public static void main(String[]args) {
        try {
            InetAddress host=InetAddress.getByName("www.google.com.ec");
            System.out.println("Host: "+host);
            //Da la direccion IP
            System.out.println("IP: "+host.getHostAddress());
            //Da el Nombre
            System.out.println("Nombre: "+host.getHostName());
            //Da el local Host y la IP de donde se esta jalando.
            System.out.println("Localhost: "+InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            System.err.println("Host desconocido");
            System.exit(0);
        }
    }
}

