# Documento de Arquitectura de Software 

# Introducción
## Propósito

El propósito de este documento es proveer un contexto sobre la aplicación de streaming y búsqueda de libros que será desarrollada y que será capaz de ofrecer el servicio de lectura de libros digitales a todo usuario previamente registrado en el sitio, teniendo en cuenta diferencias entre los dos tipos de cuenta con los que contará la API, los cuales son Free y Premium. Así pues, estas diferencias entre los usuarios radican en la lectura, almacenamiento y acceso a los libros.

Toda la documentación con respecto a los tipos de usuarios y sus características será descrito más adelante en el apartado correspondiente a las entidades de la API.

## Alcance

La aplicación se extiende a todas las personas deseosas de ampliar su lectura con diversos géneros literarios que en ocasiones son difíciles de encontrar o comprar. Así pues, la aplicación funcionará como un repositorio de libros que estarán almacenados en un mismo lugar y que podrán hallarse de manera sencilla y eficaz.

## Documentos de Referencia

- [What is REST?](https://restfulapi.net/)
- [Document your REST API](https://gist.github.com/iros/3426278)
- [ISO/IEC 9126](https://en.wikipedia.org/wiki/ISO/IEC_9126)


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

La aplicación está diseñada usando una arquitectura cliente-servidor, el cliente realiza las peticiones al servidor para solicitar información o modificar el estado, agregando nuevas entidades en la base de datos, actualizando y eliminando.

Por otro lado, el lado del servidor fue diseñado tomando en cuenta el patrón de arquitectura por capas de manera que, para nuestra aplicación, las capas se dividen en los niveles que a continuación se enlistan:
  - **Capa Rest:** Esta capa se encarga de recibir las peticiones desde un cliente, verificar que los parámetros de la petición sean correctos y retornar la respuesta con los datos solicitados al cliente. Esta capa solo tiene comunicación con la capa inferior (*Capa de servicios*).
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

<br/>

# Documentación de la API
## Documentación de cada Endpoint por Entidad

**Autentificación**
---- 
### Acceder a una Cuenta de Usuario

Permite identificar a un usuario y acceder a su respectiva cuenta.

* **URL**

  /auth/login

* **Método:**

  `POST`
  
*  **Parámetros de la URL**

   **Requeridos:**
   
   Ninguno

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**
  ```json 
  body {
   "username": "string",
   "password": "string",
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
    
**Libros**
---
### Obtener Todos los Libros
Despliga la lista de todos los libros.

* **URL**

  `/libros`


* **Método:**

  `GET`
  
*  **Parámetros de la URL**

   **Requeridos:**
   
   Ninguno

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br/>
    **Contenido:** `[ {Libro1}, {Libro2}, ... {LibroN} ]`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server <br/>
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
  
### Obtener un Libro Específico
Permite encontrar los datos de un libro proporcionando el ID.

* **URL**

  `libros/{book_id}`


* **Método:**

  `GET`
  
*  **Parámetros de la URL**

   **Requeridos:**
   
   `book_id`: Id del libro que se desea encontrar. (integer)

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

   Ninguno
* **Respuesta Exitosa:**
  * **Código:** 200 <br/>
    **Contenido:** `{LibroN}`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found <br/>
    **Contenido:** ```json { error : ”No se encontró el libro solicitado." }```
  <br />
  * **Código:** 500 Error Server <br/>
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
### Buscar Libros
Permite buscar libros según los parámetros enviados.
* **URL**

  `libros/?params`


* **Método:**

  `GET`
  
*  **Parámetros de la URL**

   **Requeridos:**
   
   Ninguno 

   **Opcionales:**

   `book`: Titúlo del libro. (string) <br/>
   `autor`: Nombre del autor. (string) <br/>
   `release_date`: fecha de lanzamiento. (date) <br/>
   `language`: Idioma del libro. (string)

* **Parámetros del Cuerpo**

   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br/>
    **Contenido:** `[{Libro1}, {Libro2}, ... {LibroN}]`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found <br/>
    **Contenido:** 
    ```json 
    { "error" : "No se encontraron coincidencias." }
    ```
  * **Código:** 500 Error Server  <br/>
    **Contenido:** 
    ```json 
    { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/libros/?book=”title”&& autor=”autor”&&release_date=”15-02-2000”",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
### Agregar un Nuevo Libro
Permite añadir un nuevo libro al sistema.

* **URL**

  `/libros`


* **Método:**

  `POST`
  
*  **Parámetros de la URL**

   **Requeridos:**
   
   Ninguno

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

   ```json
  body{
    "title": "string",
    "author": "string",
    "content": "string",
    "publisher": "string",
    "publisher_date": "date",
    "pages": "integer",
    "language": "string",
    "url_download": "string",
    "cover": "string"
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
        "title": "El Hobbit",
        "author": "J.R.R. Tolkien",
        "content": "La historia del hobbit donde...",
        "publisher": "Allen & Unwin",
        "publisher_date": 1937,
        "pages": 400,
        "language": "spanish",
        "url_download": "https://www.librodownload.com",
        "cover": "cover.jpg"
      }
      type : "POST",
      success : Contenido
    });
  ```
  
### Eliminar un Libro

Elimina un libro específico según su ID.

* **URL**
  /libros/(:book_id)

* **Método:**
  `DELETE`
  
*  **Parámetros de la URL**

   **Requeridos:**
   `:book_id`: ID del libro que se desea encontrar. (integer)

   **Opcionales:**
   Ninguno

* **Parámetros del Cuerpo**
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
  

**Usuarios**
----
### Encontrar un Usuario

Devuelve los datos de un usuario en específico.

* **URL**

  /users/{user_id}

* **Método:**

  `GET`
  
*  **Parámetros de la URL**

   **Requeridos:**

   `user_id`: ID del usuario que es solicitado.

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

    Ninguno

* **Respuesta Exitosa:**

  * **Código:** 200 <br />
    **Contenido:** `{UsuarioN}`
 
* **Respuesta Errónea:**
  * **Código:** 400 Error Request <br />
    **Contenido:** `{ error : "Usuario solicitado inexistente." }`

  * **Código:** 500 Error Server <br />
    **Contenido:** `{ error : "Falla en el servidor." }`

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/users/123",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
### Agregar un Nuevo Usuario
Permite añadir un nuevo usuario al sistema.

* **URL**

  `/users`


* **Método:**

  `POST`
  
*  **Parámetros de la URL**

   **Required:**
   
   Ninguno

   **Optional:**

   Ninguno

* **Parámetros del Cuerpo**

   ```json
  body{
    "id": "integer",
    "email": "string",
    "password": "string",
    "fullname" : "string",
    "lastname" : "string",
    "role_id": "integer"
  }
  ```
  
* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server <br>
    **Contenido:** ```json { error : "Falla en el servidor." }```


* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/users",
      dataType: "json",
      data:{
        "email": "ejemplo@test.com",
        "password": "456",
        "fullname" : "aaaa",
        "lastname" : "bbbb",
        "role_id": 2
      }
      type : "POST",
      success : Contenido
    });
  ```

### Eliminar un Usuario

Elimina un usuario específico según su ID.

* **URL**

  /users/(:user_id)

* **Método:**

  `DELETE`
  
*  **Parámetros de la URL**

   **Requeridos:** <br>
   `user_id`: ID del usuario que se desea borrar. (integer)

   **Opcionales:** <br>
   Ninguno

* **Parámetros del Cuerpo** <br>
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
  
### Actualizar los Datos de un Usuario

Actualiza los datos de un usuario específico según su ID.

* **URL**

    /users/(:user_id)

* **Método**

    `PATCH`

* **Parámetros de la URL**

    **Requeridos:**

    `:user_id`: ID del usuario que se desea actualizar. (integer)

    **Opcionales:**

    Ninguno

* **Parámetros del Cuerpo**

    ```json
    body{
      "email": "string",
      "password": "string",
      "fullname" : "string",
      "lastname" : "string",
    }
   ```

* **Respuesta Exitosa**

    * **Código:** 200 <br/>
    **Contenido:** `Success`

* **Respuesta Errónea**

    * **Código:** 404 NOT FOUND <br>
      **Contenido:** ```{ error : ”Usuario no existente." }```

    * **Código:** 500 SERVER ERROR <br>
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

* **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/users/123",
        dataType: "json",
        data: {
         "email": "ejemplo@test.com",
         "password": "789",
         "fullname" : "bbb",
         "lastname" : "aaa",
        }
        type : "PATCH",
        success : Contenido
    });
```

### Actualizar el Tipo de Usuario

Actualiza el tipo de un usuario específico según su ID.

* **URL**

    /users/(:user_id)/type

* **Método**

    `PATCH`

* **Parámetros de la URL**

    **Requeridos:**

    `user_id`: ID del usuario que se desea actualizar. (Int)

    **Opcionales:**

    Ninguno

* **Parámetros del Cuerpo**

    ```json
    body{
      "role_id": "integer"
    }
   ```

* **Respuesta Exitosa**

    * **Código:** 200 <br>
    **Contenido:** `Success`

* **Respuesta Errónea**

    * **Código:** 404 NOT FOUND <br>
    **Contenido:** ```{ error : ”Usuario no existente." }```

    * **Código:** 500 SERVER ERROR <br>
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

* **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/users/123",
        dataType: "json",
        data: {
         "role_id": 3
        }
        type : "PATCH",
        success : Contenido
    });
```

  
**ReadList**
----
### Obtener una Readlist

Permite obtener todas las ReadList de un usuario o una Readlist en específico.

* **URL**

  `api/v1/readlists/?params`


* **Método:**

  `GET`
  
*  **URL Parámetros**

   **Required:**
   
   `(user)`: ID del usuario (integer)

   **Optional:**

   `(list_name)`: Nombre de una lista específica. (string)

* **Cuerpo Parámetros**
   Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `[{Readlist1},{Readlist2}, ... {ReadlistN}]`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found <br>
    **Contenido:** 
    ```json 
    { "error" : "No se encontraron coincidencias" }
    ```

  * **Código:** 500 Error Server <br>
    **Contenido:**
    ```json 
     { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/?user=123&&list_name="terror",
      dataType: "json",
      type : "GET",
      success : Contenido
    });
  ```
  
  ### Agregar una Nueva ReadList
Permite añadir una nueva readlist para un usuario.

* **URL**

  `/readlists`


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
    "user_id": "integer",
    "name": "string"
  }
  ```
  
* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 500 Error Server <br>
    **Contenido:** ```json { error : "Falla en el servidor." }```
    
  * **Código:** 404 Not Found <br>
    **Contenido:** ```json { "error" : "Usuario solicitado no existente." } ```


* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists",
      dataType: "json",
      data:{
        "user_id": 123
        "name": "asd",
      }
      type : "POST",
      success : Contenido
    });
  ```
  
### Agregar un Libro a una ReadList
Permite agregar un libro a una lista del usuario


* **URL**

  `readlists/(:readlist_id)`

* **Método:**

  `POST`
  
*  **URL Parámetros**

   **Required:**
   
   `readlist_id`: ID de la Readlist. (integer) <br/>

   **Optional:**
   Ninguno

* **Parámetros del Cuerpo**

   ```json
  body{
    "book_id": "integer"
  }
  ```

* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found <br>
    **Contenido:** 
    ```json 
    { "error" : "No se encontraró la Readlist solicitada." }
    ```
  * **Código:** 500 Error Server <br>
    **Contenido:** 
    ```json 
    { "error" : "Falla en el servidor." }
    ```

* **Ejemplo de Request:**
 ```javascript
    $.ajax({
      url: "api/v1/readlists/12",
      dataType: "json",
      data: {
         "book_id": 234
        }
      type : "POST",
      success : Contenido
    });
  ```

### Eliminar una Readlist

Elimina una Readlist previamente creada desde la cuenta de un usuario específico.

* **URL**

  /readlists/(:readlist_id)

* **Método:**

  `DELETE`
  
*  **Parámetros de la URL**

   **Requeridos:**

   `readlist_id`: ID de la Readlist. (integer)

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

  Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 400 Error Request <br>
    **Contenido:** `{ error : "Readlist solicitada no existente." }`

  * **Código:** 500 Error Server <br>
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

  /readlists/(:readlist_id)/books/(:book_id)

* **Método:**

  `DELETE`
  
*  **Parámetros de la URL**

   **Requeridos:**

   `readlist_id`: ID de la Readlist. (integer)
   `book_id`: ID del libro a eliminar. (integer)

   **Opcionales:**

   Ninguno

* **Parámetros del Cuerpo**

  Ninguno

* **Respuesta Exitosa:**
  * **Código:** 200 <br>
    **Contenido:** `Success`
 
* **Respuesta Errónea:**
  * **Código:** 404 Not Found <br>
    **Contenido:** `{ error : "Libro no existente en la readlist indicada." }` <br>
    **Contenido:** `{ error : "Readlist solicitada no existente." }`

  * **Código:** 500 Error Server <br>
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

    /ratings/?params

- **Método**

    `GET`

- **Parámetros de la URL**

    **Requeridos:**

   Ninguno

    **Opcionales:**

    `user_id=[integer]`
    `book_id=[integer]`

- **Parámetros del Cuerpo**

    Ninguno

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br/>
    **Contenido:** ```{ error : ”Usuario solicitado no existente." }```<br>
    **Contenido:** ```{ error : ”Libro solicitado no existente." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Falla en el servidor." }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/ratings/?book_id=12&&user_id=1",
        dataType: "json",
        type : "GET",
        success : Contenido
    });
```

### Añadir Calificaciones a un Libro

Añade la calificación de un usuario para un libro en específico.

- **URL**

    /ratings/(:book_id)

- **Método**

    `POST`

- **Parámetros de la URL**

    **Requeridos:**

    `book_id`: ID del libro al que pertenece el rating.

    **Opcionales:**

    Ninguno

- **Parámetros del Cuerpo**

    ```json
    body {
        "user_id": "integer",
        "score": "integer",
        "comment": "string"
    }
    ```

- **Respuesta Exitosa**

    -  **Código:** 200 <br/>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `400 BAD REQUEST` <br/>
    **Contenido:** ```{ error : ”Hacen falta datos." }```

    - **Código:** `500 SERVER ERROR` <br />
    **Contenido:** ```{ error : "Error en el servidor" }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/ratings/324",
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

    `user_id`: ID del usuario que eliminará un rating. <br/>
    `book_id`: ID del libro a eliminar su rating.

    **Opcionales:**

    Ninguno

- **Parámetros del Cuerpo**

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
        url: "api/v1/ratings/users/45/books/632",
        dataType: "json",
        type : "DELETE",
        success : Contenido
    });
```

### Actualizar una Calificación para un Libro

Actualiza la calificación (con su respectivo comentario, si así lo desea el usuario) de un libro en específico que ha sido asignada por un usuario particular.

- **URL**

    /ratings/users/{user_id}/books/{book_id}

- **Método**

    `PATCH`

- **Parámetros de la URL**

    **Requeridos:**

    `user_id`: ID del usuario para actualizar su rating. <br>
    `book_id`: ID del libro a actualizar su rating.

    **Opcionales:**

    Ninguno

- **Parámetros del Cuerpo**

    ```json
    body{
        "score": "integer",
        "comment": "string"
    }
    ```

- **Respuesta Exitosa**

    -  **Código:** 200 <br>
    **Contenido:** `Success`

- **Respuesta Errónea**

    - **Código:** `404 NOT FOUND` <br>
    **Contenido:** ```{ error : ”El usuario o el libro al que se ha tratado de acceder es incorrecto o no existe." }```

    - **Código:** `500 SERVER ERROR` <br>
    **Contenido:** ```{ error : "Ha ocurrido un error en el servidor." }```

- **Ejemplo del Request**

```javascript
    $.ajax({
        url: "api/v1/ratings/users/23/books/100",
        dataType: "json",
        data: {
        "score": 1,
        "comment": "Horrible libro, no lo lean."
        }
        type : "PATCH",
        success : Contenido
    });
```

# Criterios de Calidad

## Funcionalidad
Se deberan poder realizar todas las funcionalidades especificadas para el sistema, satisfacer las necesidades de los usuarios que lo utilizan y tener un alto grado de seguridad.

## Fiabilidad
Cualquier error que surga debe poder ser identificado y manejado por el sistema sin falta, y debe ser posible recuperarse de este con facilidad.

## Usabilidad
El sistema debera tener una alta usabilidad, pudiendo ser utilizado con facilidad por los usuarios finales sin necesidad de capacitación.

## Eficiencia
Gracias a las caracteristicas de un sistema RESTful, como la falta de estado y la cacheabilidad el sistema, debe ser altamente eficiente.

## Mantenibilidad
Debido a la interfaz uniforme del sistema este debe ser facil de probar, y debe ser posible hacer correciones y añadir nuevas características sin complicaciones.

## Portabilidad
La capacidad de separar la interfaz de usuario del almacenamiento de los datos debido a la estructura cliente-servidor debe permitir al sistema tener mayor portabilidad a través de diferentes plataformas.