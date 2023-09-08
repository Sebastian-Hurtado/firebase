# firebase
en la primera parte del codigo se conecta en la base de datos de firebase, acto seguido se ingresa unos datos mediante la funcion de save, para usar estos metodos que estaban privados lo que hago en instanciar y crear un objeto en la claase firebaseSaveObject 
este es el metodo save
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/391e93b1-4ea8-4266-ac05-2c11091f08dc)

luego se ejecuta el metodo recover que nos ayuda a saber que dato queremos recuperar o hacer lectura de nuestra base de datos el metodo es el siguiente 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/c4ec8db7-717e-4904-9015-af04f375f942)
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/50fdc03c-9d48-408a-8f61-969844e365f7)

luego llamo un metodo que llame remove el cual su finalidad es borrar un dato dentro de la base de datos donde nosotros podemos escoger por consola que dato vamos a eliminar 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/f32825cc-3254-47ac-b86a-5b64265a6c36)

y por ultimo llamo un metodo que se llama modificar el cual su finalidad es modificar un tipo de datos que queramos por consola y reemplazar ese valor con otro dato que podemos ingresar por consola 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/5b7039bf-c137-4349-a0a1-02c716bccc4d)

para este metodo use value of para tener el mismo tipo de dato que tenia la base de datos originalmente y que no quedara todo en string, adicional use lo que vimos hoy en clase sobre hacer un objeto y cambiara el tipo de dato de acuerdo a las necesidades del programa y cree un metodo que se llama captura de datos que fue el que use para capturar todo por consola
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/3aeac364-2cca-4e21-8d6c-8835e59d5840)

adjunto imagenes de su funcionamiento por pasos 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/e3163550-d322-42d5-ba78-b8ec530737c0)

aca ingreso un dato que quiero ver 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/ea4a3f5b-70ee-45f5-a2d7-0b07b6a1a61e)

podemos ver que ya nos mostro el dato y nos pide un dato que queremos borrar 

![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/861b664f-3ad6-49db-a12d-14db587c0681)

donde podemos confirmar en firebase que el dato a sido borrado 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/e27da757-1e11-4e50-88ec-952577df40d6)

continuamos ingresando el valor que quiero modificar 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/75ed2df7-976a-4840-8d33-e4a8beeada05)

podemos ver que el dato fue actualizado pero podemos corroborar en firebase 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/51e81670-df75-43a4-a982-15c0744223f2)

donde efectivamente el dato name se actualizo con el valor ingresado 

por ultimo adjunto mi metodo main donde tuve que usar un metodo que me ayuda a pausar su ejecucion por 2 seg ya que la base de datos se demoraba un poco en darme respuesta y los mensajes en consola salian en desorden 
![image](https://github.com/Sebastian-Hurtado/firebase/assets/87822716/75933726-e1ed-45de-ae1c-171fc87d889d)




















