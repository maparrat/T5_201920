package model.logic;

import java.io.FileReader;

import com.opencsv.CSVReader;

import model.data_structures.INode;
import model.data_structures.LinearProbing;
import model.data_structures.Node;
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
	public void cargarArchivoCSVWeekly(int trimestre) throws Exception
	{
		boolean primeraLectura = true;

		CSVReader reader = new CSVReader(new FileReader("./data/bogota-cadastral-2018-" + trimestre + "-WeeklyAggregate.csv"));

		for(String[] line: reader)
		{
			if(!primeraLectura)
			{
				UBERTrip nuevo = new UBERTrip(Short.parseShort(line[0]), Short.parseShort(line[1]), Short.parseShort(line[2]), Float.parseFloat(line[3]), Float.parseFloat(line[4]), Float.parseFloat(line[5]), Float.parseFloat(line[6])); 
				String key = trimestre + "-" + line[0] + "-" + line[1];
				sc.put(key, nuevo);
				sc.put(key, nuevo);
				tamano++;
			}
			primeraLectura = false;
		}
		reader.close();
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
			// comienzo de la exploración en índice i
			indiceMenor = i;
			// j explora la sublista a[i+1]..a[n-1]
			for (j = i+1; j < n; j++)
				if (tiempos[j] < tiempos[indiceMenor])
					indiceMenor = j;
			// sitúa el elemento mas pequeño en a[i]
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
			// comienzo de la exploración en índice i
			indiceMenor = i;
			// j explora la sublista a[i+1]..a[n-1]
			for (j = i+1; j < n; j++)
				if (tiempos[j] < tiempos[indiceMenor])
					indiceMenor = j;
			// sitúa el elemento mas pequeño en a[i]
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
	 * Retorna el número de elementos en el modelo
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return tamano;
	}
}