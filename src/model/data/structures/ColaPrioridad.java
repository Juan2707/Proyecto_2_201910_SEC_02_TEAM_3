/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ColaPrioridad.java,v 1.16 2008/10/11 22:01:21 alf-mora Exp $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Jorge Villalobos - Abr 11, 2006
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package model.data.structures;

import java.io.Serializable;

/**
 * Implementaci�n de una cola de prioridad
 * @param <T> Tipo de datos que contiene cada nodo de la cola.
 * @param <E> Tipo de datos que se utilizara para darle la prioridad a los objetos de la cola. Los objetos de tipo T deben implentar la interface <b>Comparable</b> para poder
 *        realizar la inserci�n en la cola de forma correcta
 */
public class ColaPrioridad<T extends Comparable<? super T>, E> implements Serializable
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
	 * Constante para la serializaci�n
	 */
	private static final long serialVersionUID = 1L;	

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
    /**
     * Primer elemento de la cola encadenada
     */
    protected NodoColaPrioridad<T, E> primero;

    /**
     * Ultimo elemento de la cola encadenada
     */
    protected NodoColaPrioridad<T, E> ultimo;

    /**
     * N�mero de elementos de la cola
     */
    protected int numElems;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Constructor de la cola encadenada vac�a. <br>
     * <b>post: </b> Se construy� una cola vac�a. primero==null, ultimo==null, numElems = 0<br>
     */
    public ColaPrioridad( )
    {
        primero = null;
        ultimo = null;
        numElems = 0;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------
    /**
     * Retorna la longitud de la cola (n�mero de elementos). <br>
     * <b>post: </b> Se retorn� la longitud de la cola<br>.
     * @return El n�mero de elementos de la cola. Entero positivo o cero.<br>
     */
    public int darLongitud( )
    {
        return numElems;
    }

    /**
     * Retorna el primer elemento y lo elimina de la cola. <br>
     * <b>post: </b> Se retorn� y elimin� el primer elemento de la cola. Si es el �nico elemento, el primero y el ultimo son null. La cantidad de los elementos se reduce en 1<br>
     * @return El primer elemento de la cola. Diferente de null<br>
     * @throws ColaVaciaException Si la cola no tiene elementos<br>
     */
    public E tomarElemento( ) throws ColaVaciaException
    {
        if( primero == null )
            throw new ColaVaciaException( "No hay elementos en la cola" );
        else
        {
            NodoColaPrioridad<T, E> p = primero;
            primero = primero.desconectarPrimero( );
            if( primero == null )
                ultimo = null;
            numElems--;
            return p.darElemento( );
        }
    }

    /**
     * Inserta un elemento en la cola de acuerdo a su prioridad. <br>
     * <b>pre: </b> elem!=null. <br>
     * <b>post: </b> Se insert� el elemento en la cola de acuerdo a su prioridad.
     * @param prioridad Elemento con el que se va a tomar la prioridad de la cola
     * @param elem El elemento a ser insertado
     */
    public void insertar( T prioridad, E elem )
    {
        NodoColaPrioridad<T, E> nodo = new NodoColaPrioridad<T, E>( prioridad, elem );
        if( primero == null )
        {
            primero = nodo;
            ultimo = nodo;
        }
        // Verifica si tiene mayor prioridad que el primer elemento de la cola
        else if( primero.darPrioridad( ).compareTo( prioridad ) < 0 )
        {
            nodo.insertarDespues( primero );
            primero = nodo;
        }
        else
        {
            // Recorre la cola hasta encontrar un nodo de menor prioridad
            boolean inserto = false;
            for( NodoColaPrioridad<T, E> p = primero; !inserto && p.darSiguiente( ) != null; p = p.darSiguiente( ) )
            {
                if( p.darSiguiente( ).darPrioridad( ).compareTo( prioridad ) < 0 )
                {
                    nodo.insertarDespues( p.darSiguiente( ) );
                    p.insertarDespues( nodo );
                    inserto = true;
                }
            }
            if( !inserto )
            {
                // No lo ha insertado porque tiene la menor prioridad de toda la cola
                ultimo = ultimo.insertarDespues( nodo );
            }
        }
        numElems++;
    }

    /**
     * Retorna el primer nodo de la cola. Sin eliminarlo<br>
     * <b>post: </b> Se retorn� el primer nodo de la cola.
     * @return El primer nodo de la cola
     */
    public NodoColaPrioridad<T, E> darPrimero( )
    {
        return primero;
    }

    /**
     * Retorna el �ltimo nodo de la cola. Sin eliminarlo<br>
     * <b>post: </b> Se retorn� el �ltimo nodo de la cola.<br>
     * @return El �ltimo nodo de la cola<br>
     */
    public NodoColaPrioridad<T, E> darUltimo( )
    {
        return ultimo;
    }

    /**
     * Indica si la cola se encuentra vac�a (no tiene elementos). <br>
     * <b>post: </b> Se retorn� true si primero==null o false en caso contrario.<br>
     * @return True si primero==null o false en caso contrario<br>
     */
    public boolean estaVacia( )
    {
        return primero == null;
    }

    /**
     * Convierte la cola a un String. <br>
     * <b>post: </b> Se retorn� la representaci�n en String de la cola. El String tiene el formato "[numeroElementos]: e1->e2->e3..->en", donde e1, e2, ..., en son los los
     * elementos de la cola y numeroElementos su tama�o. <br>
     * @return La representaci�n en String de la cola
     */
    @Override
    public String toString( )
    {
        String resp = "[" + numElems + "]:";
        for( NodoColaPrioridad<T, E> p = primero; p != null; p = p.darSiguiente( ) )
        {
            resp += "->" + p.toString( );
        }
        return resp;
    }

}
