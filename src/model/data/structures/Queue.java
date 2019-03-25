package model.data.structures;

import java.util.Iterator;

public class Queue<T >  implements IQueue<T> 
{
	
	/**
	 * Nodo con referencia al primer elemento a�adido a la cola
	 */
	private Node<T> primero;
	/**
	 * Nodo con referencia al ultimo elemento a�adido de la cola
	 */
	private Node<T> ultimo;
	/**
	 * Entero con el tama�o de la cola
	 */
	private int tamano;
	/**
	 * Constructor de la clase
	 */
    public Queue() 
	{
		// TODO Auto-generated constructor stub
    	primero=null;
    	ultimo=null;
    	tamano=0;
	}
	

	@Override
	public boolean isEmpty() 
	{
		// TODO Auto-generated method stub
		return primero==null; //O puede ser tamano==0
	}

	@Override
	public int size() 
	{
		// TODO Auto-generated method stub
		return tamano;
	}

	@Override
	public void enqueue(T t) //A�ade un elemento nuevo al final de la cola
	{
		// TODO Auto-generated method stub
		Node<T> antiguoUltimo=ultimo; 
	    ultimo = new Node<T>(t,null);                 
		if (isEmpty()) primero = ultimo;      
		else    antiguoUltimo.setSiguiente(ultimo); 
		tamano++; 
	}

	@Override
	public T dequeue() 
	{
		// TODO Auto-generated method stub
	   T elemento=primero.getElemento();
	   primero=primero.getSiguiente();
	   if(isEmpty())ultimo=null;
	   tamano--;
	   return elemento;
	}


	@Override
	public Iterator<T> iterator() 
	{
		return  new Iterator<T>() {

			private Node<T> current = primero;
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return current != null;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub

				Node<T> item = current;
				current = current.getSiguiente();
				return item.getElemento();
			}
		};
	}

}
