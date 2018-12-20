import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class client {

    public client() {}
    //Conexión con servidor (Esto debe ser ejecutado por el proceso coordinador)
    public static String Descifrar(String ruta_archivo, String ip_server) {

		BufferedReader br = null;
		FileReader fr = null;
        String mensajeCifrado = "";
		String numberGroup = "grupo_17";
		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(ruta_archivo);
			br = new BufferedReader(fr);
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				mensajeCifrado += sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        try {
            Registry registry = LocateRegistry.getRegistry(ip_server);
            InterfaceServer stub = (InterfaceServer) registry.lookup("PublicKey");
            String uk = stub.getKey(numberGroup);
            String mensajeDescifrado = stub.decipher(numberGroup, mensajeCifrado, uk);
			return mensajeDescifrado;
        } catch (Exception e) {
            return "error";
        }
    }
}