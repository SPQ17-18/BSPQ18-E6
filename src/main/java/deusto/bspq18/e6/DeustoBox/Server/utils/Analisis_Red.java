package deusto.bspq18.e6.DeustoBox.Server.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * M�todo que analiza los puertos de la red wifi
 */
public class Analisis_Red {

	public static void main(String[] args) {
		
		/*Analisis_Red a = new Analisis_Red(0, 300);
		System.out.println(almacen);
		iniciar.start();
		System.out.println("Listado de puertos abiertos");
		System.out.println(almacen);*/
		System.out.println("Conexión a internet: " + TestInternet());
		System.out.println("Conexión puerto a BD: " + TestPuerto());
	}

	static Thread iniciar = new Thread() {
		@Override
		public void run() {
			
			ExecutorService executor = Executors.newCachedThreadPool();
			int inicio = 1;
			int fin = 100;

			while (fin <= 65500) {
				executor.execute(new ejecutarTareas(inicio, fin));
				inicio += 100;
				fin += 100;
			}
			executor.execute(new ejecutarTareas(65501, 65535));

			executor.shutdown();

			try {
				executor.awaitTermination(4, TimeUnit.MINUTES);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			System.out.println(almacen);
		}
	};
	
	
	
	public static ArrayList<Integer> almacen = new ArrayList<Integer>();


	public Analisis_Red(int puerto, int fin) {

		Socket prueba;
		String ip = "localhost";

		for (int port = puerto; port <= fin; port++) {

			try {
				prueba = new Socket(ip, port);

				System.out.println("Puerto " + port + " Abierto");
				almacen.add(port);
				prueba.close();

			} catch (Exception ex) {

			}

		}
	}

	/**
	 * M�todo que indica si hay disponible una conexi�n a internet
	 * 
	 * @return true si hay internet
	 * @return false si no hay internet
	 */
	public static boolean TestInternet() {

		String web = "www.google.es";
		int puerto = 80;
		boolean hayinternet = false;
		Socket test = null;
		try {
			test = new Socket(web, puerto);
			if (test.isConnected()) {
				// Si hay internet comprobamos el puerto 3306
				/*if (TestPuerto() == true) {
					hayinternet = true;
				}*/
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		// Cerramos el puerto
		try {
			test.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hayinternet;
	}

	/*
	 * M�todo que comprueba si el puerto necesario para conectarse a la bd est�
	 * abierto o cerrado
	 * 
	 * @return true si est� abierto
	 * @return false si est� cerrado
	 */
	private static boolean TestPuerto() {
		boolean abierto = true;
		try {
			Socket s = null;
			try {
				// Comprobamos con nuestro host
				s = new Socket("www.phpmyadmin.co", 3306);
			} catch (ConnectException e) {
				abierto = false;
			}
			// Cerramos el puerto
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return abierto;
	}
}

class ejecutarTareas implements Runnable {
	int inicio = 0;
	int fin;

	public ejecutarTareas(int inicio, int fin) {
		this.inicio = inicio;
		this.fin = fin;
	}

	//@Override
	public void run() {
		Analisis_Red scan = new Analisis_Red(inicio, fin);
	}

}
