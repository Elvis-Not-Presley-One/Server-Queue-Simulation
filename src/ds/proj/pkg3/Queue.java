package ds.proj.pkg3;

/**
 *
 * @author Tyler Elvis, Angelo Martino, Group 9
 * @param <E>
 */
public class Queue<E> 
{

    private Node<E> front;
    private Node<E> rear;
    private int numElements;

    /**
     * The Queue() constructor sets all attribute to vars      * 
     */
    public Queue() 
    {
        front = null;
        rear = null;
        numElements = 0;
    }

    /**
     * The add() method adds to the queue
     * 
     * @param element whatever obj to add to the queue
     */
    public void add(E element) 
    {
        if (rear == null) // Queue empty?
        {
            front = new Node<E>(element, null);
            rear = front;
        } 
        else 
        {
            rear.setNext(new Node<E>(element, null));
            rear = rear.getNext();
        }
        numElements++;
    }

    /**
     * 
     * The remove method removes from the queue
     * @return the value of the last node 
     *
     * @throws EmptyQueue 
     */
    public E remove() throws EmptyQueue 
    {
        E value;
        if (front == null) // Queue empty?
        {
            throw new EmptyQueue();
        } 
        else 
        {
            value = front.getData();
            if (front != rear) 
            {
                front = front.getNext();
            } 
            else // removing last node
            {
                front = null;
                rear = null;
            }
            numElements--;
        }
        return value;
    }

    /**
     * The size method gets the size of the queue
     * 
     * @return the number of elements in the queue
     */
    public int size() 
    {
        return numElements;
    }

}
