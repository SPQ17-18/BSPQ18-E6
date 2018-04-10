package deusto.bspq18.e6.DeustoBox.Server.gui;

import java.io.File;
import deusto.bspq18.e6.DeustoBox.Server.bd.BDConnection;

public class idea_server {

				// Cargar instalador del servidor
				/*
				 * Pedir ruta donde estará la Box Global.
				 * Progressbar mientras intala todo.
				 * 
				 */
				
				// Ejecutar el programa
				/*
				 * Login del administrador.
				 * GUI que esté activa mientras se está ejecutando el programa.
				 * Se creará una carpeta por cada usuario registrado (con sus ficheros)
				 * Se sincroniza la Box.
				 * 
				 */
				
				// Conexión con la Base de datos externa
				/*
				 * 
				 * 
				 */
	
				// Sincronizar la Box
				/*
				 * Comprobar si se ha modificado algún archivo.
				 * Descargar/Subir los archivos.
				 * 
				 */
		public static void main(String[] args) {
			//String dir = "C:\\\\Users\\\\deusto_06\\\\Desktop";
			String myDir = "D:\\aitor\\Escritorio";
			String completeDir = myDir + "\\Deusto-Box";
			File directorio = new File(completeDir); 
			directorio.mkdir(); 
			
			BDConnection bd = null;
			
			/*if(Analisis_Red.TestPuerto()) {
				System.out.println("Conectado a BD externa");
				bd = BDConnection.getBD(true);
			}else {*/
				System.out.println("Conectado a BD local");
				bd = BDConnection.getBD(false);
			//}
				
			System.out.println("Creando tabla");
			bd.createTable();
			
			System.out.println("Añadiendo usuarios");
			if(!bd.userExits("aitorugarte@opendeusto.es", bd.getStat(), bd.getConexion())) {
				bd.registerUser(bd.getStat(), "aitorugarte@opendeusto.es", "Aitor", "123");
			}
			if(!bd.userExits("markeluko@opendeusto.es", bd.getStat(), bd.getConexion())) {
				bd.registerUser(bd.getStat(), "markeluko@opendeusto.es", "Markel", "123");
			}
			
			for (int i = 0; i < bd.getUsers().size(); i++) {
				File userFolder = new File(completeDir + "\\" + bd.getUsers().get(i)); 
				userFolder.mkdir(); 
			}
			
			
		}
}
