# Documento de Arquitectura de Software

# Introducción
## Propósito

El propósito de este documento es proveer un contexto sobre la aplicación de streaming y búsqueda de libros que será desarrollada y que será capaz de ofrecer el servicio de lectura de libros digitales a todo usuario previamente registrado en el sitio, teniendo en cuenta diferencias entre los dos tipos de cuenta con los que contará la API, los cuales son Free y Premium. Así pues, estas diferencias entre los usuarios radican en la lectura, almacenamiento y acceso a los libros.

Toda la documentación con respecto a los tipos de usuarios y sus características será descrito más adelante en el apartado correspondiente a las entidades de la API.

## Alcance

La aplicación se extiende a todas las personas deseosas de ampliar su lectura con diversos géneros literarios que en ocasiones son difíciles de encontrar o comprar. Así pues, la aplicación funcionará como un repositorio de libros que estarán almacenados en un mismo lugar y que podrán hallarse de manera sencilla y eficaz.

## Documentos de Referencia

# Arquitectura

En esta sección se describirá a fondo la arquitectura de la API desarrollada, sus componentes y cómo estos se relacionan y comunican entre sí para el funcionamiento del software desarrollado.

## Descripción de la Arquitectura por Capas
La arquitectura por capas está enfocada en la distribución de roles y responsabilidades de forma jerárquica para proveer una separación de responsabilidades que le permite al sistema tener un menor grado de acoplamiento entre sus componentes, de manera que existen características importantes como se listan a continuación:

- **Roles** que indican el modo y tipo de interacción que tienen las capas entre sí.
- **Responsabilidades** para cada capa, las cuales indican el papel que juegan los componentes que conforman cada nivel.
- **Comunicación** entre los diferentes niveles por medio de interfaces bien definidas. La comunicación únicamente se da de manera que la mayoría de la interacción sucede únicamente entre capas vecinas.
- **La distribución** de las capas puede darse en una misma computadora o en diferentes.

Es importante mencionar que, si bien esta arqutiectura se caracteriza por dividir las responsabilidades en, comúnmente, 3 capas principales, estas no dependen de las otras, lo cual ayuda a encapsular los fallos en su respectiva división.
Así pues, las capas principales de esta arquitectura se muestran y describen a continuación:

![Diagrama de la Arquitectura por Capas](/assets/diagramaCapas.png)

- **Capa de Presentación.** Esta capa va dirigida al usuario y es también conocida como interfaz gráfica puesto que es la encargada de presentarle la información de manera "amigable" a los usuarios.

- **Capa de Negocios.** En este nivel se definen todas las reglas que deben de cumplirse para enviar la información a la última capa y a la interfaz  gráfica. Aquí se reciben las peticiones de los usuarios y se envían las respuestas que existen detrás de cada acción posible en el software.

- **Capa de Datos o Persistencia.** En este nivel residen los datos como tal. Esta capa se encarga de recibir, almacenar y proporcionar información, interactuando siempre con la capa de negocios para proporcionar los datos necesarios de las acciones que provienen de la capa de presentación.

## Arquitectura del Proyecto
![Diagrama de la arquitectura del proyecto](/assets/arquitectura.jpg)

La aplicación es diseñada usando una arquitectura cliente-servidor, el cliente realiza las peticiones al servidor para solicitar información o modificar el estado, agregando nuevas entidades en la base de datos, actualizando y eliminando.

Por otro lado, el lado del servidor fue diseñado tomando en cuenta el patrón de arquitectura por capas, de manera que para nuestra aplicación, las capas se dividen en los niveles que a continuación se enlistan:
  - **Capa Rest:** Esta capa se encarga de recibir las peticiones desde un cliente, verificar que los parametros de la petición es correcta y retornar la respuesta con los datos solicitados al cliente. Esta capa solo tiene comunicación con la capa inferior (*Capa de servicios*).
  - **Capa Servicios:** Esta capa es la encargada de la lógica de negocios, en esta se establecen las reglas que debe cumplir la aplicación, así como solicitudes a servicios externos. Esta capa se comunica con la capa superior (*Capa Rest*) y la inferior (*Persistencia*).
  -  **Capa de persistencia:** En esta capa se encarga de recibir, almacenar y proporcionar los datos proveniente de la capa de servicios. Esto a través de una conexión a un sistema manejador de base de datos (*DBMS*).
  -  **Base de datos:** Permite almacenar una colección organizada de información, controlado por un DBMS.
  -  **Modelos:** Capa vertical que se encarga de modelar las entidades de la aplicación.
  -  **Elasticsearch:** Esta capa vertical está encargada de indexar los datos (libros), para realizar consultas complejas sobre los datos.

## Diagrama de Secuencia (Operaciones CRUD)

A continuación, se adjuntan los diagramas de secuencia para los verbos GET y POST para la búsqueda y almacenamiento de libros dentro de la API, los cuales son considerados los más importantes para el proyecto.

![Diagrama de Secuencia para el verbo GET libros](/assets/GetDiagram.jpg)
En la imagen superior puede apreciarse cómo sucede la interacción entre las capas de la aplicación cuando un usuario de tipo *Cliente* (sin importar si este es Free o Premium) realiza una petición utilizando el verbo *GET* para indexar todos los libros almacenados en la base de datos.

![Diagrama de Secuencia para el verbo POST libro](/assets/PostDiagram.jpg)
En la imagen superior, puede observarse la interacción de las capas de la aplicación cuando un usuario de tipo *Administrador* realiza una petición utilizando el verbo *POST* para almacenar un libro nuevo dentro de la base de datos.


## Diagrama de la Base de Datos
![Diagrama de la base de datos](/assets/Diagrama-DB.png)

## Descripción de las Entidades

- **Usuario:** Entidad que representa a los clientes que interactuarán con el sistema de alguna forma. A partir de estos sujetos surgen los casos de uso, además de que esta entidad es una superclase para los tipos de usuario *Administrador*, *Lector Free* y *Lector Premium*.

- **Rol:** Entidad que representa el tipo de *Usuario* que interactúa con el sistema, los cuales pueden ser:
    - Administrador: tendrá los privilegios para agregar, actualizar o eliminar libros del sistema.
    - Lector Free: será capaz de entrar al sistema pero estará limitado a leer 3 libros por 2 meses.
    - Lector Premium: contará con todas las características que ofrece la aplicación, como la creación de *Readlist* y el acceso a libros de manera ilimitada.

- **Libro:** Entidad que representa un libro virtual al cual tendrá acceso un usuario, el cual será capaz de leer según las restricciones mencionadas anteriormente; no obstante, el libro tendrá características como género, número de páginas, título, fecha de publicación y portada.

- **Readlist:** Entidad que representa una colección de libros de acuerdo a un tema o nombre según lo desee el usuario de tipo *Premium*, el cual podrá ordenar sus libros de acuerdo a sus gustos, como se ha mencionado anteriormente.
En esta entidad viene implícito la entidad de tipo Readlist conocida como *"Favoritos"*.

- **Rating:** Entidad que representa la calificación que tiene un libro y que ha sido otorgada por los usuarios.

## Diagrama Entidad-Relación

![Diagrama entidad-relación](/assets/Diagrama-ER.jpg)

# Documentación de la API
## Documentación de cada Endpoint por Entidad

## Autentificación
---- 
### Acceder a una Cuenta de Usuario

Permite identificar a un usuario y acceder a su respectiva cuenta.

* **URL**

  /auth/login

* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   Ninguno

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**
  ```json 
  body {
   “username”: string,
   “password”: string,
   }
  ```
* **Respuesta Exitosa:**
  * **Código:** 200 <br />
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
   * **Código:** 400 Bad Request <br />
    **Contenido:** ```json { error : ”Nombre de usuario o contraseña incorrectos." }```

  * **Código:** 500 Error Server <br />
    **Contenido:** ```json { error : "Falla en el servidor." }```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/auth/login",
      dataType: "json",
      data: {
        “username”: “usuario@email.com”,
        “password”: “contraseña123”
      }
      type : "POST",
      success : Contenido
    });
  ```
    
## Libros
---
### Obtener todos los libros
Despliga la lista de todos los libros.

* **URL**

  `/libros`


* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Required:**
   
   Ninguno

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**

   Ninguno
* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server 
    **Contenido:** ```json { error : "Falla en el servidor." }```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
### Obtener un libro específico
Permite encontrar los datos de un libro proporcinando el id.

* **URL**

  `libros/(:id_book)`


* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Required:**
   
   `:id_book`: Id del libro que se desea encontrar. (Int)

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**

   Ninguno
* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found
    **Contenido:** ```json { error : ”No se encontro el libro" }```
  <br />
  * **Código:** 500 Error Server 
    **Contenido:** ```json { error : "Falla en el servidor." }```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/1",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
### Buscar libros
Permite buscar libros según los parametros enviados
* **URL**

  `libros/find/?book=”title”&& autor=”autor”`


* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Required:**
   
   Ninguno 

   **Optional:**

   `book`: Titúlo del libro. (string)
   `autor`: Nombre del autor. (string)
   `release_date`: fecha de lanzamiento. (date)
   `language`: Idioma del libro. (string)

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found
    **Contenido:** 
    ```json 
    { "error" : "No se encontraron coincidencias" }
    ```
  * **Código:** 500 Error Server 
    **Contenido:** 
    ```json 
    { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/find/?book=”title”&& autor=”autor”&&release_date=”15-02-200”",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
  ### Agregar un nuevo libro
Permite añadir un nuevo libto al sistema.

* **URL**

  `/libros`


* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   Ninguno

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**

   ```json
  body{
    “title”: string,
    “author”: string,
    “content”: string,
    “publisher”: string,
    “publisher_date”: date,
    “pages”: integer,
    “language”: string,
    “url_download”: string,
    “cover”: string
  }
  ```
  
* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server 
    **Contenido:** ```json { error : "Falla en el servidor." }```


* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/1",
      dataType: "json",
      data:{
        “title”: “El Hobbit”,
        “author”: “J.R.R. Tolkien”,
        “content”: “La historia del hobbit donde se casó.”,
        “publisher”: “Allen & Unwin”,
        “publisher_date”: “1937”,
        “pages”: “400”,
        “language”: “spanish”,
        “url_download”: “https://www.librodownload.com”,
        “cover”: “cover.jpg”
      }
      type : "POST",
      success : Contenido
    });
  ```
  
  ### Eliminar un libro

Elimina un libro especifico segun su ID.

* **URL**
  /libros/(:id_book)

* **Método:**
  `DELETE`
  
*  **URL Parámetros**

   **Requeridos:**
   `:id_book`: Id del libro que se desea encontrar. (Int)

   **Opcionales:**
   Ninguno

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br />
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
    * **Código:** 404 NOT FOUND <br/>
    **Contenido:** ```{ error : ”Libro no existente." }```

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/123",
      dataType: "json",
      type : "DELETE",
      success : Contenido
    });
  ```
  

## Usuarios
----
### Encontrar Usuario

Devuelve los datos de un usuario en específico.

* **URL**

  /users/{user_id}

* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Requeridos:**

   Ninguno

   **Opcionales:**

   Ninguno

* **Body Parámetros**

    Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 <br />
    **Contenido:** `{UsuarioN}`
 
* **Respuesta Errónea:**
  * **Código:** 400 Error Request <br />
    **Contenido:** `{ error : "Usuario pedido inexistente." }`

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/users",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
  ### Agregar un nuevo usuario
Permite añadir un nuevo usuario al sistema.

* **URL**

  `/users`


* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   Ninguno

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**

   ```json
  body{
    "id": int,
    "email": string,
    "password": string,
    "fullname" : string,
    "lastname" : string,
    "Role_id": int
  }
  ```
  
* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server 
    **Contenido:** ```json { error : "Falla en el servidor." }```


* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/1",
      dataType: "json",
      data:{
        "id": "123",
        "email": "ejemplo@test.com",
        "password": "456",
        "fullname" : "aaaa",
        "lastname" : "bbbb",
        "Role_id": "2"
      }
      type : "POST",
      success : Contenido
    });
  ```

### Eliminar un usuario

Elimina un usuario especifico segun su ID.

* **URL**
  /users/(:id_user)

* **Método:**
  `DELETE`
  
*  **URL Parámetros**

   **Requeridos:**
   `:id_user`: Id del usuario que se desea borrar. (Int)

   **Opcionales:**
   Ninguno

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br />
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
    * **Código:** 404 NOT FOUND <br/>
    **Contenido:** ```{ error : ”Usuario no existente." }```

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/users/123",
      dataType: "json",
      type : "DELETE",
      success : Contenido
    });
  ```
  
  ### Actualizar los datos de un usuario

Actualiza los datos de un usuario especifico segun su ID.

* **URL**

    /users/(:id_user)

* **Método**

    `PATCH`

* **Parámetros de la URL**

    **Requeridos:**

    `:id_user`: Id del usuario que se desea actualizar. (Int)

    **Opcionales:**

    Ninguno

* **Cuerpo Parámetros**

    ```json
    body{
      "email": string,
      "password": string,
      "fullname" : string,
      "lastname" : string,
    }
   ```

* **Respuesta Exitosa**

    * **Código:** 200 <br/>
    **Contenido:** `Success`

* **Respuesta Errónea**

    * **Código:** 404 NOT FOUND 
    **Contenido:** ```{ error : ”Usuario no existente." }```

    * **Código:** 500 SERVER ERROR
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

* **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/users/{user_id}",
        dataType: "json",
        data: {
         "email": "ejemplo@test.com",
         "password": "456",
         "fullname" : "aaaa",
         "lastname" : "bbbb",
        }
        type : "PATCH",
        success : Contenido
    });
```

### Actualizar el tipo de usuario

Actualiza el tipo de un usuario especifico segun su ID.

* **URL**

    /users/(:id_user)/type

* **Método**

    `PATCH`

* **Parámetros de la URL**

    **Requeridos:**

    `:id_user`: Id del usuario que se desea actualizar. (Int)

    **Opcionales:**

    Ninguno

* **Cuerpo Parámetros**

    ```json
    body{
      "Role_id": int
    }
   ```

* **Respuesta Exitosa**

    * **Código:** 200 <br/>
    **Contenido:** `Success`

* **Respuesta Errónea**

    * **Código:** 404 NOT FOUND 
    **Contenido:** ```{ error : ”Usuario no existente." }```

    * **Código:** 500 SERVER ERROR
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

* **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/users/{user_id}",
        dataType: "json",
        data: {
         "Role_id": "2"
        }
        type : "PATCH",
        success : Contenido
    });
```

  
 ## ReadList
 ----

### Obtener una Readlist

Permite obtener todas las ReadList de un usuario o una Readlist en específico

* **URL**

  `readlists/user/(:id)`
  `readlist/user/(:id)?list_name=""`


* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Required:**
   
   `(:id)`: ID del usuario (int)

   **Optional:**

   `list_name`: Nombre de una lista específica. (string)

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found
    **Contenido:** 
    ```json 
    { "error" : "No se encontraron coincidencias" }
    ```

  * **Código:** 500 Error Server 
    **Contenido:**
    ```json 
     { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/user/(:id)?list_name=terror",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
  ### Agregar una nueva ReadList
Permite añadir una nueva readlist para un usuario.

* **URL**

  `/readlists/users/(:id_user)`


* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   `:id_user`: Id del usuario que esta creando la ReadList. (Int)

   **Optional:**

   Ninguno

* **Cuerpo Parámetros**

   ```json
  body{
    “name”: string,
  }
  ```
  
* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server 
    **Contenido:** ```json { error : "Falla en el servidor." }```
    
    * **Código:** 404 Not Found
    **Contenido:** 
    ```json 
    { "error" : "Usuario no existente" }
    ```


* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/users/1",
      dataType: "json",
      data:{
        “name”: "asd",
      }
      type : "POST",
      success : Contenido
    });
  ```
  
### Agregar un libro a una lista
Permite agregar un libro a una lista del usuario


* **URL**

  `readlists`

* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   `user_id`: id de usuario. (int)
   `book_id`: id del libro. (int)

   **Optional:**
 ```json
  body{
    "user_id": 1,
    "name": 1,
  }
  ```

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found
    **Contenido:** 
    ```json 
    { "error" : "No se encontraron coincidencias" }
    ```
  * **Código:** 500 Error Server
    **Contenido:** 
    ```json 
    { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/user/(:id)?list_name=terror",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```

### Eliminar una Readlist

Elimina una Readlist previamente creada desde la cuenta de un usuario específico.

* **URL**
  /readlist/(:id)

* **Método:**
  `DELETE`
  
*  **URL Parámetros**

   **Requeridos:**
   Ninguno

   **Opcionales:**
   Ninguno

* **Parámetros del Body**
  Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br />
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 400 Error Request <br />
    **Contenido:** `{ error : "Readlist no existente." }`

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/1",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
  
  
### Eliminar un Libro de una Readlist

Elimina un libro que ha sido añadido a una Readlist única en la cuenta de un usuario en específico.

* **URL**

  /readlist/(:id)/books/(:id_book)

* **Método:**

  `DELETE`
  
*  **Parámetros de la URL**

   **Requeridos:**

   Ninguno

   **Opcionales:**

   Ninguno

* **Parámetros del Body**

  Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br />
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 400 Error Request <br />
    **Contenido:** `{ error : "Libro no existente en la readlist indicada." }`

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/1/books/324",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  


**Calificaciones**
----
### Ver Calificaciones de un Libro

Devuelve todas las calificaciones que han sido asignadas, por un usuario, a un libro.

- **URL**

    /ratings/?id_book=$

- **Método**

    `GET`

- **Parámetros de la URL**

    **Requeridos:**

    `book_id=[integer]`

    **Opcionales:**

    Ninguno

- **Parámetros del Body**

    Ninguno

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br/>
    **Contenido:** ```{ error : ”Algún dato erróneo." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Falla en el servidor." }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/btfy/ratings/?book_id=$",
        dataType: "json",
        type : "GET",
        data: {
            "book_id": 3578
        }
        success : Contenido
    });
```

### Añadir Calificaciones a un Libro

Añade la calificación de un usuario para un libro en específico.

- **URL**

    /ratings

- **Método**

    `POST`

- **Parámetros de la URL**

    **Requeridos:**

    Ninguno

    **Opcionales:**

    Ninguno

- **Parámetros del Body**

    ```json
    body {
        “user_id”: integer,
        “score”: integer,
        “comment”: string
    }
    ```

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br/>
    **Contenido:** ```{ error : ”Hacen falta datos." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Error en el servidor" }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/btfy/ratings",
        dataType: "json",
        data: {
        "user_id": 12345,
        "score": 5,
        "comment": "Excelente libro, lo recomiendo mucho."
        }
        type : "POST",
        success : Contenido
    });
```

### Eliminar Calificación de un Libro

Elimina la calificación de un libro en específico que ha sido asignada por un usuario.

- **URL**

    /ratings/users/{user_id}/books/{book_id}

- **Método**

    `DELETE`

- **Parámetros de la URL**

    **Requeridos:**

    `user_id=[integer]` <br/>
    `book_id=[integer]`

    **Opcionales:**

    Ninguno

- **Parámetros del Body**

    Ninguno

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br/>
    **Contenido:** ```{ error : ”El usuario o el libro al que se ha tratado de acceder es incorrecto o no existe." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor" }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/btfy/ratings/users/{user_id}/books/{book_id}",
        dataType: "json",
        data: {
        "user_id": 12345,
        "book_id": 6498
        }
        type : "DELETE",
        success : Contenido
    });
```

### Actualizar una Calificación para un Libro

Actualiza la calificación (con su respectivo comentario, si así lo desea el usuario) de un libro en específico que ha sido asignada por un usuario particular.

- **URL**

    /ratings/users/{user_id}/books/{book_id}

- **Método**

    `PUT`

- **Parámetros de la URL**

    **Requeridos:**

    `user_id=[integer]` <br/>
    `book_id=[integer]`

    **Opcionales:**

    Ninguno

- **Parámetros del Body**

    ```json
    body{
        “score”: integer,
        “comment”: string
    }
    ```

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br/>
    **Contenido:** ```{ error : ”El usuario o el libro al que se ha tratado de acceder es incorrecto o no existe." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/btfy/ratings/users/{user_id}/books/{book_id}",
        dataType: "json",
        data: {
        "user_id": 12345,
        "book_id": 6498,
        "score": 5,
        "comment": "Excelente libro, lo recomiendo mucho."
        }
        type : "UPDATE",
        success : Contenido
    });
```

# Criterios de Calidad

# Referencias
- [Introducción al patrón de arquitectura por capas](https://arevalomaria.wordpress.com/2010/12/02/introduccion-al-patron-de-arquitectura-por-capas/)
- [Platzi: Patrón arquitectónico de capas](https://platzi.com/tutoriales/1248-pro-arquitectura/5439-patron-arquitectonico-de-capas-layers/#:~:text=La%20arquitectura%20en%20capas%20es,de%20lo%20que%20le%20cooresponde)
- [Los 5 principales patrones arquitectónicos de software](https://apiumhub.com/es/tech-blog-barcelona/principales-patrones-arquitectura-software/)
