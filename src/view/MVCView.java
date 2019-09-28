package view;

public class MVCView 
{
	/**
	 * Metodo constructor
	 */
	public MVCView()
	{}

	/**
	 * Método que imprime el menú por consola
	 */
	public void printMenu()
	{
		System.out.println("1. Cargar archivo");
		System.out.println("2. Buscar tiempos de viaje por trimestre, zona de origen y zona de destino (Tabla de Hash Linear Probing)");
		System.out.println("3. Buscar tiempos de viaje por trimestre, zona de origen y zona de destino (Tabla de Hash Separate Chaining)");
		System.out.println("4. Pruebas de tiempo ");
		System.out.println("5. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}
}