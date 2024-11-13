package ds.proj.pkg3;

/**
 *
 * @author Tyler Elvis, Angelo Martino, Group 9
 * @param <E>
 */
public class Node<E> 
{
    
    private E data;
    private Node<E> next;

    /**
     * The Node() constructor allows to do operations upon creation
     *
     * @param initialData is the data input
     * @param initialNext is the next node in the bag
     */
    public Node(E initialData, Node<E> initialNext) 
    {
        data = initialData;
        next = initialNext;
    }

    /**
     * The getData() method gets the data
     *
     * @return data
     */
    public E getData() 
    {
        return data;
    }

    /**
     * The getNext() method gets the next link in the bag
     *
     * @return next link in the bag
     */
    public Node<E> getNext()
    {
        return next;
    }

    /**
     * The setData() method sets the arg to the private var data
     *
     * @param newData arg inputed from main
     */
    public void setData(E newData) 
    {
        data = newData;

    }

    /**
     * *
     * the setNext() method sets the arg to the private var next
     *
     * @param newNext arg input from the main
     */
    public void setNext(Node<E> newNext) 
    {
        next = newNext;
    }    
}
