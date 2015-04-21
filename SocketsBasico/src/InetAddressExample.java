import java.util.Enumeration;
import java.net.*;
import static java.lang.System.*;


/**
 *
 * @author ASUS
 */
public class InetAddressExample {
     public static void main(String[] args){
        
        //Obtener las interfaces de red y las direcciones correctas para el anfitrión
         try{
             Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces(); // Los getNetworkInterfaces método estático, devuelve una lista que contiene una instancia de NetworkInterface para cada una de las interfaces del host. 
             if (interfaceList == null){  // La interfaz de bucle de retorno es generalmente siempre se incluye, incluso si el host no tiene ninguna otra red conexión, por lo que esta comprobación tendrá éxito si el host no tiene subsistema de red en absoluto
                 out.println("-- Sin interfaces establecidas"); //Salida de lista vacía.
                 
             }else {                                                 // Objetener una lista de impresión para cada interfaz de la lista
                 while (interfaceList.hasMoreElements()) { 
                     NetworkInterface iface = interfaceList.nextElement();
                     out.println("Interface " + iface.getName() + ":"); //Se imprime el nombre de la interfaz. //El método getName () devuelve un nombre local para la interfaz. Esto suele ser una combinación de letras y números que indican el tipo y en particular instancia de la interfaz, por ejemplo, "lo0" o "eth0". 
                     Enumeration<InetAddress> addrList = iface.getInetAddresses(); // Obtener las direcciones asociadas con la interfaz // Los getInetAddresses () método devuelve otra enumeración, esta vez contiene casos de InetAddress y uno por la dirección asociada a la interfaz. Dependiente de cómo esté configurado el anfitrión, la lista puede contener sólo IPv4, IPv6 sólo, o una mezcla de ambos tipos de dirección. 
                     if (!addrList.hasMoreElements()){
                         out.println("\t(Ninguna dirección para esta Interfaz)"); //Salida de lista vacía                
                     }
                     while (addrList.hasMoreElements()){
                         InetAddress address = addrList.nextElement();
                         out.print("\tDirección "
                             + ((address instanceof Inet4Address ? "(v4)"
                                : (address instanceof Inet6Address ? "(v6)" : "(?)"))));
                         out.println(": " + address.getHostAddress());
                     }
                 }//Fin while Interface List
             }
         
         }
         catch(SocketException se){
            out.println("Error al obtener las interfaces de red: " + se.getMessage());
         }
         //
         
         
    }
}
