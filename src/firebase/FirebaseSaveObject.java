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
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;


public class FirebaseSaveObject {
    private FirebaseDatabase firebaseDatabase;
    
    public static void main(String[] args) throws FileNotFoundException {
        Item item = new Item();
        item.setId(100L);
        item.setName("PruebaNetbeans");
        item.setPrice(100.8);
        
        FirebaseSaveObject serverObject =new FirebaseSaveObject();
        serverObject.initFirebase();
        serverObject.save(item);
        
        
        String consulta = capturarDatos("Que dato quieres ver ?");
        serverObject.recover(consulta);
        
        String borrar = capturarDatos("Que deseas borrar ?");
        serverObject.remove(borrar);
        try {
            Thread.sleep(2000); // Pausa el hilo actual durante 2000 milisegundos (2 segundos)
        } catch (InterruptedException e) {
            // Manejo de excepciones en caso de que se interrumpa la pausa
            e.printStackTrace();
        }
        String modificar = capturarDatos("que dato desear modificar ?");
        String datoMod = capturarDatos("que quieres ingresar ?");
        serverObject.modificar(modificar,datoMod);
        
        
    }

    

    /**
         * inicialización de firebase.
*/
    private  void initFirebase() {
        
        try {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()   
                    .setDatabaseUrl("https://base2-73c02-default-rtdb.firebaseio.com")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\David\\Documents\\NetBeansProjects\\Firebase-master\\base2-73c02-firebase-adminsdk-fsvam-52f2178262.json/")))

                   // .setDatabaseUrl("https://prueba-esp-a7c2a.firebaseio.com")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa....");
        }catch (RuntimeException ex) {
            System.out.println("Problema ejecucion ");
        }catch (FileNotFoundException ex) {
            System.out.println("Problema archivo");
        }

         
    }

    /**
     * Save item object in Firebase.
     * @param item 
     */
    private void save(Item item) throws FileNotFoundException {
        if (item != null) {
           // initFirebase();
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("item");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
             * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
             * using `countDownLatch.countDown()` and application will continues its execution.
             */
            CountDownLatch countDownLatch;
            countDownLatch = new CountDownLatch(1);
            childReference.setValue(item, (DatabaseError de, DatabaseReference dr) -> {
                System.out.println("Registro guardado!");
                // decrement countDownLatch value and application will be continues its execution.
                countDownLatch.countDown();
            });
            try {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void recover(String consulta) {
        
            //initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("item/"+consulta);

            /* Get existing child or will be created new child. */
            //DatabaseReference childReference = databaseReference.child("item");

            /**
             * The Firebase Javaw client uses daemon threads, meaning it will not
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
                        
                        Object valor = new Object();
                        switch(databaseReference.getKey()){
                            case "id":
                                valor = dataSnapshot.getValue(Long.class);
                                break;
                            case "name":
                                valor = dataSnapshot.getValue(String.class);
                                break;
                            case "price":
                                valor = dataSnapshot.getValue(Double.class);
                                break;
                        }
                        
                        //System.out.println("valor: "+ value.getId());
                        System.out.println( valor);

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
    private void remove(String borrar){
        DatabaseReference databaseReference = firebaseDatabase.getReference("item/"+borrar);
        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    
                    System.out.println("Datos eliminados exitosamente.");
                } else {
                    System.out.println("Error al eliminar datos: " + databaseError.getMessage());
                }
            }
        });    
    }
    public static String capturarDatos(String mensaje){
        Scanner scanner = new Scanner(System.in);
        System.out.println(mensaje);
        String tomadeDatos;
        tomadeDatos = scanner.nextLine();
        
        return tomadeDatos;
    }
    private void modificar(String modificar,String datoMod){
        Object valor = new Object();
        switch(modificar){
                            case "id":
                                valor = Long.valueOf(datoMod);
                                break;
                            case "name":
                                valor = String.valueOf(datoMod);
                                break;
                            case "price":
                                valor = Double.valueOf(datoMod);
                                break;
                        }
        
        DatabaseReference databaseReference = firebaseDatabase.getReference("item/"+modificar);
        databaseReference.setValue(valor,new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    System.out.println("Valor actualizado exitosamente.");
                } else {
                    System.out.println("Error al Actualizar datos: " + databaseError.getMessage());
                }
            }
        });
              
    }
    
}
//Realizar la recuperacion de informacion
//perfeccionar el metodo en que se realiza una operacion, suprimir el conteo regresivo