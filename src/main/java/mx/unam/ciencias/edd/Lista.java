package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            anterior =  null;
            siguiente =  cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(siguiente == null) throw new NoSuchElementException();

            anterior = siguiente;
            siguiente = anterior.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(anterior == null) throw new NoSuchElementException();

            siguiente = anterior;
            anterior = siguiente.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return getElementos();
    }

    /**
     * 
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return rabo == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null) throw new IllegalArgumentException("null agregado");
        
        Nodo n = new Nodo(elemento);        
        longitud++;
        
        if (rabo == null) {
            cabeza = rabo = n;
            return;
        }
        
        else{
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }

    } 

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento == null) throw new IllegalArgumentException("null agregadodo inicio");

        Nodo n = new Nodo(elemento);
        longitud++;
        
        if(rabo  == null){ 
            cabeza = rabo = n;
            return;
        }
        else{
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        } 
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if(elemento == null) throw new IllegalArgumentException("null insertado");

         Nodo n = new Nodo(elemento);

        if(i < 1) {
            agregaInicio(elemento);
            return;
        }
        if(i > longitud -1){
             agregaFinal(elemento);
             return;
        }

        Nodo  prenodo = iesimoNodo(i-1);
        longitud++;
        
        n. siguiente = prenodo.siguiente;
        n.anterior = prenodo;
        prenodo.siguiente.anterior = n;
        prenodo.siguiente = n;
    }

    private Nodo iesimoNodo(int i){
        if(i < 1) return cabeza;
        if(i > longitud -2) return rabo;
        
        
        
        Nodo it;
        if(i > (longitud>>1)){
            it = rabo;
            i = longitud-i-1;
            for(;i > 0; i--){
                it = it.anterior;
            }
        }
        else{
            it = cabeza;
            for(;i > 0; i--){
            it = it.siguiente;
            }
        }
    
        return it;

    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Nodo buscado = buscaNodo(elemento);

        if(buscado != null){
             eliminaNodo(buscado);
             longitud--;
        }
    }


    private Nodo buscaNodo(T elemento){
        if(elemento == null) return null;

        Nodo encontrado = cabeza;

        while(encontrado != null){
            if(encontrado.elemento.equals(elemento)) break;
            encontrado = encontrado.siguiente;
        }
        
        return encontrado;
    }

    private void eliminaNodo(Nodo nodoaeliminar){
        if(nodoaeliminar == null) return;
        
        
        if(cabeza == rabo){ 
            cabeza = rabo = null;
            return;
        }

        if(nodoaeliminar ==  cabeza){
            cabeza.siguiente.anterior = null;
            cabeza = cabeza.siguiente;
            return;
        }

        if(nodoaeliminar == rabo){
            rabo.anterior.siguiente = null;
            rabo = rabo.anterior;
            return;
        }

        nodoaeliminar.anterior.siguiente = nodoaeliminar.siguiente;
        nodoaeliminar.siguiente.anterior = nodoaeliminar.anterior;

    }
    
    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(rabo == null) throw new NoSuchElementException("eliminasPrimero en lista vacía");
        
        longitud--;
        T primero = cabeza.elemento;
        eliminaNodo(cabeza);
        return primero;
    }


    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(rabo == null) throw new NoSuchElementException("eliminasUltimo en lista vacía");

        longitud--;
        T ultimo = rabo.elemento;
        eliminaNodo(rabo);
        return ultimo;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversa = new Lista<T>();
        Nodo it = rabo;
        while(it != null){
            reversa.agrega(it.elemento);
            it = it.anterior;
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<T>();
        Nodo it = cabeza;
        while(it != null){
            copia.agrega(it.elemento);
            it = it.siguiente;
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(rabo == null) throw new NoSuchElementException("get primero de lista vacía");

        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(rabo == null) throw new NoSuchElementException("get primero de lista vacía");
 
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i<0 || i>=longitud) throw new ExcepcionIndiceInvalido("get de índice inválido");

        return iesimoNodo(i).elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        
        Nodo it = cabeza;
        int i = 0;
        while(it != null){
            if(it.elemento.equals(elemento)) return i;
            it = it.siguiente;
            i++;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if(rabo == null) return "[]";

        String toString = "[" + cabeza.elemento.toString();

        Nodo it = cabeza.siguiente;

        while(it != null){
            toString += ", " + it.elemento.toString();
            it = it.siguiente;
        }

        return toString + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if(lista.getElementos() != this.longitud) return false;
         
        Nodo t = cabeza;
        Nodo o = lista.cabeza;


        while (t != null && o != null ){
            if(! t.elemento.equals(o.elemento)) return false;
            t = t.siguiente;
            o = o.siguiente;
        }
  
        return t == o;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
