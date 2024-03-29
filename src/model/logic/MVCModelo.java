package model.logic;

import java.io.FileReader;

import com.opencsv.CSVReader;

import model.data_structures.INode;
import model.data_structures.LinearProbing;
import model.data_structures.Node;
import model.data_structures.Queue;
import model.data_structures.SeparateChaining;

/**
 * Definicion del modelo del mundo
 */
public class MVCModelo{

	/**
	 * Atributos del modelo del mundo
	 */
	private SeparateChaining<String, UBERTrip> sc;

	private LinearProbing<String, UBERTrip> lp;

	private int tamano;

	/**
	 * Constructor del modelo del mundo
	 */
	public MVCModelo()
	{
		tamano = 0;
		lp = new LinearProbing<>(1);
		sc = new SeparateChaining<>(1);
	}

	/**
	 * Metodo que carga los archivos
	 * @param prutaArchivo CSV
	 */
	public UBERTrip[] cargarArchivoCSVWeekly(int trimestre) throws Exception
	{
		boolean primeraLectura = true;
		UBERTrip[] respuesta = new UBERTrip[2];

		CSVReader reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-" + trimestre + "-WeeklyAggregate.csv"));

		for(String[] line: reader)
		{
			if(!primeraLectura)
			{
				UBERTrip nuevo = new UBERTrip(Short.parseShort(line[0]), Short.parseShort(line[1]), Short.parseShort(line[2]), Float.parseFloat(line[3]), Float.parseFloat(line[4]), Float.parseFloat(line[5]), Float.parseFloat(line[6])); 
				String key = trimestre + "-" + line[0] + "-" + line[1];
				lp.put(key, nuevo);
				sc.put(key, nuevo);
				tamano++;

				if(respuesta[0] == null)
				{
					respuesta[0] = nuevo;
				}
				respuesta[1] = nuevo;
			}
			primeraLectura = false;
		}
		reader.close();
		return respuesta;
	}
	
	public UBERTrip[] tiemposDeViajeLinearProbing(int trimestre, int idOrigen, int idDestino)
	{
		LinearProbing<String, UBERTrip> temp = new LinearProbing<>(1);
		Queue llaves = (Queue) lp.keys();
		
		while(llaves.hasNext())
		{
			String actual = (String) llaves.next();
			UBERTrip datoActual = lp.get(actual);
			
			if(datoActual.darDatosViaje()[0] == idOrigen && datoActual.darDatosViaje()[1] == idDestino)
			{
				temp.put(actual, datoActual);
			}
		}
		
		UBERTrip[] respuesta = new UBERTrip[temp.darNumeroDeElementos()];
		
		Queue llavesTemp = (Queue) temp.keys();

		int k = 0;
		while(llavesTemp.hasNext())
		{
			String actual = (String) llavesTemp.next();
			
			respuesta[k] = temp.get(actual);
			k++;
		}
		
		//Ordenamiento por inserci�n
		for (int i = 0; i < respuesta.length; i++)
		{
			boolean enPos = false;
			for (int j = i; j > 0 && !enPos; j--)
			{				
				if(respuesta[j].darDatosViaje()[2] < respuesta[j-1].darDatosViaje()[2])
				{
					UBERTrip copia = respuesta[j-1];
					respuesta[j-1] = respuesta[j];
					respuesta[j] = copia;
				}
				else
				{
					enPos = true;
				}
			}
		}
		
		return respuesta;		
	}
	
	public UBERTrip[] tiemposDeViajeSeparateChaining(int trimestre, int idOrigen, int idDestino)
	{
		SeparateChaining<String, UBERTrip> temp = new SeparateChaining<>(1);
		Queue llaves = (Queue) sc.keys();
		
		while(llaves.hasNext())
		{
			String actual = (String) llaves.next();
			UBERTrip datoActual = sc.get(actual);
			
			if(datoActual.darDatosViaje()[0] == idOrigen && datoActual.darDatosViaje()[1] == idDestino)
			{
				temp.put(actual, datoActual);
			}
		}
		
		UBERTrip[] respuesta = new UBERTrip[temp.darNumeroDeElementos()];
		
		Queue llavesTemp = (Queue) temp.keys();

		int k = 0;
		while(llavesTemp.hasNext())
		{
			String actual = (String) llavesTemp.next();
			
			respuesta[k] = temp.get(actual);
			k++;
		}
		
		//Ordenamiento por inserci�n
		for (int i = 0; i < respuesta.length; i++)
		{
			boolean enPos = false;
			for (int j = i; j > 0 && !enPos; j--)
			{				
				if(respuesta[j].darDatosViaje()[2] < respuesta[j-1].darDatosViaje()[2])
				{
					UBERTrip copia = respuesta[j-1];
					respuesta[j-1] = respuesta[j];
					respuesta[j] = copia;
				}
				else
				{
					enPos = true;
				}
			}
		}
		
		return respuesta;		
	}	

	public double[] tiemposLineal()
	{
		double[] respuesta =  new double[3];
		// Crea el linealProbing con 8000 datos
		LinearProbing<Integer,Integer > tabla = new LinearProbing<>(8000);

		for(Integer i = 0; i< 8000;i++)
		{
			tabla.put(i, i);
		}
		//Genera arreglo de tiempos y crea los 10000 get para datos aleatorios 
		long[] tiempos = new long[10000];

		for(int i = 0; i<10000 ; i++)
		{
			long startTime = System.currentTimeMillis();
			int valorDado = (int) Math.floor(Math.random()*9999+1);
			tabla.get(valorDado);
			long endTime = System.currentTimeMillis() - startTime;
			tiempos[i] = endTime;
		}
		//Ordena los tiempos 
		int indiceMenor, i, j, n;
		n = tiempos.length;
		for (i = 0; i < n-1; i++)
		{
			// comienzo de la exploraci�n en �ndice i
			indiceMenor = i;
			// j explora la sublista a[i+1]..a[n-1]
			for (j = i+1; j < n; j++)
				if (tiempos[j] < tiempos[indiceMenor])
					indiceMenor = j;
			// sit�a el elemento mas peque�o en a[i]
			if (i != indiceMenor)
				intercambiar(tiempos, i, indiceMenor);
		}
		//Agrega el valor minimo y maximo
		respuesta[0] =  tiempos[0];
		respuesta[2] =  tiempos[9999];
		//Calcula el promedio
		double suma= 0;
		for(int x = 0; x <10000;x++)
		{
			suma= (suma+ tiempos[x]);
		}
		double promedio  = (suma/10000);
		//agrega el promedio
		respuesta[1] = promedio;
		return respuesta;

	}
	public double[] tiemposSeparate()
	{
		double[] respuesta =  new double[3];
		// Crea el linealProbing con 8000 datos
		SeparateChaining<Integer, Integer> tabla = new SeparateChaining<>(8000);

		for(Integer i = 0; i< 8000;i++)
		{
			tabla.put(i, i);
		}
		//Genera arreglo de tiempos y crea los 10000 get para datos aleatorios 
		long[] tiempos = new long[10000];

		for(int i = 0; i<10000 ; i++)
		{
			long startTime = System.currentTimeMillis();
			int valorDado = (int) Math.floor(Math.random()*9999+1);
			tabla.get(valorDado);
			long endTime = System.currentTimeMillis() - startTime;
			tiempos[i] = endTime;
		}
		//Ordena los tiempos 
		int indiceMenor, i, j, n;
		n = tiempos.length;
		for (i = 0; i < n-1; i++)
		{
			// comienzo de la exploraci�n en �ndice i
			indiceMenor = i;
			// j explora la sublista a[i+1]..a[n-1]
			for (j = i+1; j < n; j++)
				if (tiempos[j] < tiempos[indiceMenor])
					indiceMenor = j;
			// sit�a el elemento mas peque�o en a[i]
			if (i != indiceMenor)
				intercambiar(tiempos, i, indiceMenor);
		}
		//Agrega el valor minimo y maximo
		respuesta[0] =  tiempos[0];
		respuesta[2] =  tiempos[9999];
		//Calcula el promedio
		double suma= 0;
		for(int x = 0; x <10000;x++)
		{
			suma= (suma+ tiempos[x]);
		}
		double promedio  = (suma/10000);
		//agrega el promedio
		respuesta[1] = promedio;
		return respuesta;

	}
	public static void intercambiar(long []a, int i, int j)
	{
		long aux = a[i];
		a[i] = a[j];
		a[j]= aux ;
	}
	/**
	 * Retorna el n�mero de elementos en el modelo
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return tamano;
	}

	public int[] darDatosLinearProbing()
	{
		int N = lp.darNumeroDeElementos();
		int M = lp.tamanoArreglo();

		int[] respuesta = new int[4];
		respuesta[0] = N;
		respuesta[1] = M;
		double factorCarga = (N*100/M);
		respuesta[2] = (int) factorCarga;
		respuesta[3] = (int)(Math.log(M)/Math.log(2));
		return respuesta;
	}
	
	public int[] darDatosSeparateChaining()
	{
		int N = sc.darNumeroDeElementos();
		int M = sc.tamanoArreglo();

		int[] respuesta = new int[4];
		respuesta[0] = N;
		respuesta[1] = M;
		double factorCarga = (N*100/M);
		respuesta[2] = (int) factorCarga;
		respuesta[3] = (int)(Math.log(M)/Math.log(2));
		return respuesta;
	}
}