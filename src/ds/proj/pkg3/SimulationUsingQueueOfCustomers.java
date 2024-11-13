package ds.proj.pkg3;

/**
 * Created by Michael Main and mucked up by Stephen Brower pillaged from Sample
 * Simulation using a Queue of Integer and changed to a Queue of Customer
 *
 * @author Stephen T. Brower<stephen.brower@raritanval.edu>
 *
 * Modified by: STB 7/23/2018 - brought in Server class from a variation ????
 * Tyler Elvis added express server and multiple regular servers
 */
public class SimulationUsingQueueOfCustomers 
{

    /**
     * main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // settings/declarations
        int currentSecond;                          // for a specific second in simulation
        final int MAX_TIME_FOR_SIMULATION = 90 * 60;  // 90 minutes

        // probability of arrival for a second
        // based on 20 customers an hour
        final double PROBABILITY_OF_ARRIVAL = 300.0 / (60 * 60);
        final int MAX_TIME_TO_CHECKOUT = 5 * 60;      // maximum time to process "customer"
        int numCustomersArrived = 0;                // counter for number of customers that arrived
        int nemExpServerCustomers = 0;
        Server regServer[] = new Server[10];

        //create all servers 
        for (int i = 0; i < regServer.length; i++) 
        {
            regServer[i] = new Server();
        }

        //create a new server for costumers < 60 work
        Server expressServer = new Server();

        int totalWaitingTime = 0;                   // initialize total waiting time

        // loop to simulate elapsing time
        for (currentSecond = 0; currentSecond < MAX_TIME_FOR_SIMULATION; currentSecond++) 
        {

            // everything inside this loop is for 1 second of time
            // has someone arrived at this second?
            if (Math.random() < PROBABILITY_OF_ARRIVAL) 
            {
                // a customer arrived - count them
                numCustomersArrived++;

                // 'create' this newCustomer with an estimate of time to take
                Customer newCustomer = new Customer(currentSecond, 
                        ((int) (Math.random() * MAX_TIME_TO_CHECKOUT)));
                // stick this new customer on Server's customerArrivalQueue queue

                if (newCustomer.getInitialWorkToDo() <= 60) 
                {
                    expressServer.addCustomer(newCustomer);

                    System.out.println("Express Server: At time " + currentSecond
                            + " a customer arrived. "
                            + "work will be " + newCustomer.getInitialWorkToDo());

                    nemExpServerCustomers++;
                } 
                else 
                {
                    int serverIterator = 0;
                    boolean wasUserAdded = false;
                    while (serverIterator < regServer.length && !wasUserAdded) 
                    {
                        if (regServer[serverIterator].isFree()) 
                        {

                            regServer[serverIterator].addCustomer(newCustomer);

                            wasUserAdded = true;
                        }
                        serverIterator++;

                    }
                    System.out.println("Regular Server(" + serverIterator + "): At time "
                            + currentSecond + " a customer arrived. "
                            + "work will be " + newCustomer.getInitialWorkToDo());

                }

            }

            // for this second, is a server free and someone waiting?
            for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) 
            {
                if (regServer[serverIterator].isFree() && regServer[serverIterator].queueSize() > 0)
                {
                    regServer[serverIterator].serveCustomer(); // mark the 'server' as busy

                    // accumulate waiting time---note: the time this customer waited is: currentSecond - customerBeingServedArrivalTime
                    totalWaitingTime += currentSecond - regServer[serverIterator]
                            .currentCustomerArrivalTime();

                    // display working info
                    System.out.println("Regular Server (" + serverIterator + ") "
                            + ": At " + currentSecond
                            + " started working on customer who arrived at "
                            + regServer[serverIterator].currentCustomerArrivalTime()
                            + " -- work remainng is " + regServer[serverIterator].customerWorkToDoRemaining());
                }
            }

            if (expressServer.isFree() && expressServer.queueSize() > 0) 
            {
                expressServer.serveCustomer();

                totalWaitingTime += currentSecond - expressServer.currentCustomerArrivalTime();

                System.out.println("Express Server: At " + currentSecond
                        + " started working on customer who arrived at "
                        + expressServer.currentCustomerArrivalTime()
                        + " -- work remainng is " + expressServer.customerWorkToDoRemaining());

            }

            // for this second, if a server is working on a customer then decrease work to do by 1 second
            for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) {
                if (regServer[serverIterator].isNotFree()) 
                {
                    // decrement work to do by 1 second
                    regServer[serverIterator].doWork();

                    // are we done with customer?
                    if (regServer[serverIterator].isFree()) 
                    {
                        System.out.println("Regular Server (" + serverIterator + "): At " + currentSecond + " server free!");
                    }
                }
            }

            if (expressServer.isNotFree()) 
            {
                expressServer.doWork();

                if (expressServer.isFree()) {
                    
                    System.out.println("Express Server: At " + currentSecond + " server free!");

                }
            }

            // for this second, if it is the last second, announce last second of simulation
            if (currentSecond == (MAX_TIME_FOR_SIMULATION - 1)) 
            {
                System.out.println("At " + currentSecond + " \t\t\t\t\t\t\tWe are closed");
                // Display the number of customers stuck on line (still in Queue)
                for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) 
                {
                    System.out.println("\t\t\t\t\t\t\t\tRegular Server(" + serverIterator + "): number of "
                            + "customers stuck on line: " + regServer[serverIterator].queueSize());
                }
                System.out.println("\t\t\t\t\t\t\t\tExpress Server: number "
                        + "of customers stuck on line: " + expressServer.queueSize());

            }
        } // for loop end

        System.out.println("\nAt " + currentSecond + " Simulation ended");

        // display statistics from simulation
        // display number of customers that arrived
        System.out.println("\n" + numCustomersArrived + " customers arrived");

        // Display the counter for the number of customers served
        for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) 
        {
            System.out.println("Number of customers served by regular server(" + serverIterator + "): "
                    + regServer[serverIterator].getCustomersServed());
        }
        System.out.println("Number of customers served by express server: "
                + expressServer.getCustomersServed());

        // Display the total wait time that ALL customers served had waiting on line
        System.out.println("Total Wait Time of customers Served: " + totalWaitingTime);

        // Display the average wait time that the customers served had to wait on lin
        int totalCustomers = 0;
        for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) 
        {
            totalCustomers = totalCustomers + regServer[serverIterator].getCustomersServed();
        }

        totalCustomers = totalCustomers + expressServer.getCustomersServed();

        System.out.printf("Average Wait Time of customers Served: %,.1f\n",
                ((double) totalWaitingTime / totalCustomers));

        // Display the number of customers stuck on line (still in Queue)
        for (int serverIterator = 0; serverIterator < regServer.length; serverIterator++) 
        {
            System.out.println("Regular Server(" + serverIterator + "): number of customers stuck on line: " + regServer[serverIterator].queueSize());
        }
        System.out.println("Express Server: number of customers stuck on line: " + expressServer.queueSize());

    }

}
