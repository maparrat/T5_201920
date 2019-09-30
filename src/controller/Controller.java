package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.data_structures.Node;
import model.logic.MVCModelo;
import model.logic.UBERTrip;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller()
	{
		view = new MVCView();
		modelo = new MVCModelo();
	}

	/**
	 * Hilo de ejecución del programa
	 */
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin )
		{
			view.printMenu();

			String in;
			in = lector.next();

			int option;
			try
			{
				option = Integer.parseInt(in);
			}
			catch(NumberFormatException e)
			{
				option = 0;
			}

			switch(option){
			case 1:

				int numeroTrimestre;
				try
				{
					System.out.println("--------- \nCargar archivo \nDar numero del trimestre: ");
					numeroTrimestre = lector.nextInt();
				}
				catch(InputMismatchException e)
				{
					System.out.println("Debe ingresar un valor numérico (1 a 4)\n---------");
					break;
				}

				if(numeroTrimestre >= 1 && numeroTrimestre <= 4)
				{
					try
					{	
						UBERTrip[] respuesta = modelo.cargarArchivoCSVWeekly(numeroTrimestre);

						System.out.println("Archivo cargado");
						System.out.println("Numero actual de elementos " + modelo.darTamano() + "\n---------");

						UBERTrip primero = respuesta[0];
						UBERTrip ultimo = respuesta[1];

						System.out.println("Datos primer viaje:\nId zona origen: " + primero.darDatosViaje()[0] + "\nId zona destino: " + primero.darDatosViaje()[1] + "\nDía de la semana: " + primero.darDatosViaje()[2] + "\nTiempo promedio de viaje: " + primero.darDatosViaje()[3] + "\n---------");
						System.out.println("Datos último viaje:\nId zona origen: " + ultimo.darDatosViaje()[0] + "\nId zona destino: " + ultimo.darDatosViaje()[1] + "\nDía de la semana: " + ultimo.darDatosViaje()[2] + "\nTiempo promedio de viaje: " + ultimo.darDatosViaje()[3] + "\n---------");
					}
					catch (Exception e)
					{
						System.out.println("Se ha producido un error al cargar el archivo\n---------");
					}
				}
				else
				{
					System.out.println("Ingrese un valor válido (1 o 4)\n---------");	
				}
				break;

			case 2:

				int trimestreLp;
				int zonaOrigenLp;
				int zonaDestinoLp;
				try
				{
					System.out.println("--------- \nDar trimestre a buscar: ");
					trimestreLp = lector.nextInt();
					System.out.println("--------- \nDar id zona de origen a buscar: ");
					zonaOrigenLp = lector.nextInt();
					System.out.println("--------- \nDar id zona de destino a buscar: ");
					zonaDestinoLp = lector.nextInt();
				}
				catch(InputMismatchException e)
				{
					System.out.println("Debe ingresar valores numéricos\n---------");
					break;
				}

				if(trimestreLp >= 1 && trimestreLp <= 4)
				{
					UBERTrip[] viajes = modelo.tiemposDeViajeLinearProbing(trimestreLp, zonaOrigenLp, zonaDestinoLp);

					if(viajes.length == 0)
					{
						System.out.println("No hay viajes registrados con los datos datos.\n---------");

					}
					else
					{
						int i = 1;
						for(UBERTrip actual: viajes) 
						{
							if(actual != null)
							{
								System.out.println("Datos viaje #" + i + ":\nTrimestre: " + trimestreLp + "\nId zona origen: " + actual.darDatosViaje()[0] + "\nId zona destino: " + actual.darDatosViaje()[1] + "\nDía de la semana: " + actual.darDatosViaje()[2] + "\nTiempo promedio de viaje: " + actual.darDatosViaje()[3] + "\n---------");
								i++;
							}
						}
					}
				}
				else
				{
					System.out.println("Ingrese un valor válido (1 o 4)\n---------");	
				}			

				break;
				
			case 3:

				int trimestreSc;
				int zonaOrigenSc;
				int zonaDestinoSc;
				try
				{
					System.out.println("--------- \nDar trimestre a buscar: ");
					trimestreSc = lector.nextInt();
					System.out.println("--------- \nDar id zona de origen a buscar: ");
					zonaOrigenSc = lector.nextInt();
					System.out.println("--------- \nDar id zona de destino a buscar: ");
					zonaDestinoSc = lector.nextInt();
				}
				catch(InputMismatchException e)
				{
					System.out.println("Debe ingresar valores numéricos\n---------");
					break;
				}

				if(trimestreSc >= 1 && trimestreSc <= 4)
				{
					UBERTrip[] viajes = modelo.tiemposDeViajeSeparateChaining(trimestreSc, zonaOrigenSc, zonaDestinoSc);

					if(viajes.length == 0)
					{
						System.out.println("No hay viajes registrados con los datos datos.\n---------");

					}
					else
					{
						int i = 1;
						for(UBERTrip actual: viajes) 
						{
							if(actual != null)
							{
								System.out.println("Datos viaje #" + i + ":\nTrimestre: " + trimestreSc + "\nId zona origen: " + actual.darDatosViaje()[0] + "\nId zona destino: " + actual.darDatosViaje()[1] + "\nDía de la semana: " + actual.darDatosViaje()[2] + "\nTiempo promedio de viaje: " + actual.darDatosViaje()[3] + "\n---------");
								i++;
							}
						}
					}
				}
				else
				{
					System.out.println("Ingrese un valor válido (1 o 4)\n---------");	
				}			

				break;

			case 4:
				System.out.println("LinearProbing: ");
				double x []= modelo.tiemposLineal();
				System.out.println("El mejor tiempo: "+x[0] +"Milisegundos");
				System.out.println("El tiempo promedio: "+x[1]+"Milisegundos");
				System.out.println("El peor tiempo: "+x[2]+"Milisegundos");
				System.out.println("---------------------");
				System.out.println("SeparateChaining: ");
				double y []= modelo.tiemposSeparate();
				System.out.println("El mejor tiempo: "+y[0]+"Milisegundos");
				System.out.println("El tiempo promedio: "+y[1]+"Milisegundos");
				System.out.println("El peor tiempo: "+y[2]+"Milisegundos");
				break;

			case 5: 
				System.out.println("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}
	}	
}