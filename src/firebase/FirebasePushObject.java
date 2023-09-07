
package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.concurrent.CountDownLatch;


public class FirebasePushObject {
    
    /**
     *
     * 
     */
    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1L);
        item.setName("iphone");
        item.setPrice(100.156);
        //test de prueba
        // You can use List<Item> also.
        new FirebasePushObject().saveUsingPush(item);
        System.out.println("hola mundo");
        
        
    }
    
    private FirebaseDatabase firebaseDatabase;

    /**
     * inicialización de firebase.
     */
    private void initFirebase() {
        
        try {
            
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    //.setDatabaseUrl("https://prueba-esp-a7c2a.firebaseio.com") // 
                    .setDatabaseUrl("https://base2-73c02-default-rtdb.firebaseio.com")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\David\\Documents\\NetBeansProjects\\Firebase-master\\base2-73c02-firebase-adminsdk-fsvam-52f2178262.json/")))

                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("La conexión se realizo exitosamente...");
            
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (RuntimeException ex) {   
            ex.printStackTrace();
        }
        
        
    }

   
    private void saveUsingPush(Item item) {
        if (item != null) {
            initFirebase();
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("CarpetaPrueba");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
             * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
             * using `countDownLatch.countDown()` and application will continues its execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            
            /**
             * push()
             * Add to a list of data in the database. Every time you push a new node onto a list, 
             * your database generates a unique key, like items/unique-item-id/data
             */
            childReference.push().setValue(item, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    System.out.println("Registro guardado!");
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                }
            });
            try {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void recover() {
        
            initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("item");

            /* Get existing child or will be created new child. */
            //DatabaseReference childReference = databaseReference.child("item");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             * wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        //System.out.println("valor: "+ value.getId());
                        System.out.println("valor: "+ value);

                        //System.out.println("valor: "+ value.getPrice());
                        //System.out.println("valor: "+ value.getName());
                        countDownLatch.countDown();

                        //Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                       // Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
        try {
            //wait for firebase to saves record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        
    }
}