import static java.lang.System.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
public class DireccionesIP {
    
  public static void main(String[] args){ //Permite ingresar valores o argumentos por consola
      byte[] direccionLocal = {127, 0, 0, 1}; //Dirección IP del LocalHost
      InetAddress equipo; //Etiquetamos a la clase InetAddress con el nombre de "equipo" para luego crear objetos con diferentes parámatros segun se necesite
      try{
          //METODOS ESTÁTICOS DE INETADDRESS PARA OBTENER EL OBJETO EQUIPO
          equipo = InetAddress.getLocalHost(); // Creamos el objeto equipo de la clase InetAddress
          out.println("Mi equipo es: " + equipo);
          out.println("Su dirección IP es: " + equipo.getHostAddress()); //regresa un string de la representación decimal de la dirección IP
          out.println("Su nombre es: " + equipo.getHostName()); //regresa un string equivalente al nombre de dominio de la dirección IP
          out.println("Y su nombre canónico: " + equipo.getCanonicalHostName()); //se obtiene el nombre completo del host correspondiente a una dirección IP. 
          out.println();
          
          //OBTENEMOS EL OBJETO EQUIPO A PARTIR DEL NOMBRE
          String host = "www.google.com";
          equipo = InetAddress.getByName(host); //devuelve un objeto InetAddress(dirección IP) a patir de un dominio tipo String 
          out.println("el equipo "+ host + "es: " + equipo );
          out.println("Su dirección IP es: " + equipo.getHostAddress()); //regresa un string de la representación decimal de la dirección IP
          out.println("Su nombre es: " + equipo.getHostName());//regresa un string equivalente al nombre de dominio de la dirección IP
          out.println("Y su nombre canónico: " + equipo.getCanonicalHostName()); //se obtiene el nombre completo del host correspondiente a una dirección IP. 
          out.println();
          
          //OBTENEMOS EL EQUIPO A PARTIR DE SU DIRECCIÓN IP
          equipo = InetAddress.getByAddress(direccionLocal);
          out.println("Mi equipo es: " + equipo );
          out.println("Su dirección IP es: " + equipo.getHostAddress());
          out.println();
          
          //OBTENEMOS TODAS LAS DIRECCIONES IP DE UN EQUIPO
          InetAddress[] identidades; // Declaración del objeto (como poner una etiqueta al objeto)
          identidades = InetAddress.getAllByName(equipo.getHostName());
          
      } //Fin try
  }  //Fin de public static void main(String[] args)
    
    
}//fin Class DirecionesIP
