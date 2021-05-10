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

![Diagrama de la Arquitectura por Capas](/assets/diagrama.png)

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



# Documentación de la API
## Documentación de cada Endpoint por Entidad

**Calificaciones**
----
### Ver Calificaciones de un Libro

Devuelve todas las calificaciones que han sido asignadas, por un usuario, a un libro.

- **URL**

    /btfy/ratings/?id_book=$

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

    /btfy/ratings

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

    /btfy/ratings/users/{user_id}/books/{book_id}

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

    /btfy/ratings/users/{user_id}/books/{book_id}

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