package model;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

public class dbOperations {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2BANKACCOUNTPU");
    static EntityManager em = emf.createEntityManager();
    static Random rand = new Random(1000000000); //Random id generator for security purposes.
    int randID;

    public int randomID() {
        final int MAXID = 100;

        for (int i = 0; i < 100; i++) {

            randID = rand.nextInt();

        }
        return randID;
    }
  public void viewClients(){
        em.getTransaction().begin();
        TypedQuery<Client> q = em.createQuery("select c from Client c", Client.class);
        List<Client> results = q.getResultList();
        if (results.isEmpty()) {
            System.out.println("No Accounts found");
        } else {
            System.out.println("Account List");
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i).getCid()); //curent id of the bank account
                int size = results.get(i).getBlist().size(); 
                for (int j = 0; j < size; j++) {
                    System.out.println("\t" + results.get(i).getClist().get(j).getFname());
               
            }
        }
        em.getTransaction().commit();
    }
  }
    public void viewAccounts() {

        em.getTransaction().begin();
        TypedQuery<BankAccount> q = em.createQuery("select a from BankAccount a", BankAccount.class);
        List<BankAccount> results = q.getResultList();
        if (results.isEmpty()) {
            System.out.println("No Accounts found");
        } else {
            System.out.println("Account List");
            for (int i = 0; i < results.size(); i++) {
                    if((results.get(i)) instanceof CurrentAccount){
                        CurrentAccount cacc = (CurrentAccount)results.get(i);
                        System.out.println(results.get(i).getBid()+" Balance: "+results.get(i).getBalance());
                        int size = results.get(i).getClist().size(); //client list#
                            for(int j=0; j<size; j++){
                                System.out.println("\n\t" +results.get(i).getClist().get(j).getCid());
                            }
                    }
                            else if((results.get(i)) instanceof SavingsAccount){
                                SavingsAccount sacc = (SavingsAccount)results.get(i);
                                System.out.println(results.get(i).getBid()+" Balance: "+results.get(i).getBalance()+" Amount of transactions: "+results.get(i).getMontlyTransactions());
                                int size = results.get(i).getClist().size();
                                for(int j=0; j<size; j++){
                                    System.out.println("\n\t"+results.get(i).getClist().get(j).getCid());
                                }
                        
                    }
                    
              
                
                
            }
        }
        em.getTransaction().commit();
    }
     public void viewClientAccounts(int cid) {
        em.getTransaction().begin();
        Client c = em.find(Client.class, cid);
        BankAccount b = new BankAccount();
        List<BankAccount> results = b.getBlist();
        if (results.isEmpty()) {
            System.out.println("No Accounts found");
        } else {
        System.out.println("Account List for: " + c.getCid()+", "+c.getFname());
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
        }
        em.getTransaction().commit();
    }
       public void addClient(Client c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
       public void addAccount(BankAccount ba){
           em.getTransaction().begin();
           em.persist(ba);
           em.getTransaction().commit();
       }
       public void assignAccount(int cid, int bid){
           em.getTransaction().begin();
           Client c = em.find(Client.class, cid);
           BankAccount ba = em.find(BankAccount.class, bid);
           c.addAccount(c);
           em.getTransaction().commit();
  
       }
   

}
