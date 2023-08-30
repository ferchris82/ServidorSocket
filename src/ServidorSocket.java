
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ServidorSocket {
    public static void main(String[] args){
        
        ServerSocket ss = null;
        Socket s = null;
        
        BufferedReader textoRecibidoCliente;
        DataOutputStream textoEnviarAlCliente;
        String stringRecibido;
        
        try{
            ss = new ServerSocket(5432);
            System.out.println("Servidor escuchando");
            //Esperando a recibir una conexion de cliente en puerto 5432
            s = ss.accept();
            System.out.println("Ya se conecto el cliente");
            textoRecibidoCliente = new BufferedReader(new InputStreamReader(s.getInputStream()));
            textoEnviarAlCliente = new DataOutputStream(s.getOutputStream());
            //Recibir y responder hasta que llegue vacío
            do{
                stringRecibido = textoRecibidoCliente.readLine();
                System.out.println("Llego el texto: " + stringRecibido);
                //Respondemos al cliente. En este caso, devolvemos respuesta si hemos encontrado 
                //la vocal 'a' en el texto recibido.
                String stringBuscar = "a";
                int posicionResultado = stringRecibido.indexOf(stringBuscar);//indexOf si encuentra la vocal a 
                //devolvera un entero con el número de posición, si no encuentra devuelve -1
                if(posicionResultado == -1){
                    textoEnviarAlCliente.writeUTF("No se encontro la vocal a. ");}
                else{
                    textoEnviarAlCliente.writeUTF("Si se encontro la vocal a");
                }                
            }while(stringRecibido.length()!=0);
            //Enviamos mensaje de cierre de conexion.
            System.out.println("Ya termine de recibir, cerrando conexión");
            textoEnviarAlCliente.close();
            textoRecibidoCliente.close();
            s.close();
            ss.close();
        }catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }   
    }
}
