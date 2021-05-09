# Documento de Arquitectura de Software

# Introducción
## Propósito

Crear una aplicación de streaming capaz de ofrecer el servicio de lectura de libros digitales a todo usuario previamente registrado en el sitio, teniendo en cuenta las diferencias entre cuenta Free y Premium, y además pueda ser capaz de organizarlos en listas según sus propios criterios.

## Alcance

El proyecto pretende alcanzar a todas las personas deseosas de ampliar su lectura con diversos libros, que en ocasiones es difícil de encontrar o comprar, en un mismo lugar y de una manera sencilla y eficaz.

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
[Insertar diagrama de la arquitectura completa del proyecto]

## Diagrama de Secuencia (Operaciones CRUD)


## Diagrama de la Base de Datos


## Descripción de las Entidades

- **Usuario:** Entidad que representa a los clientes que interactuarán con el sistema de alguna forma directa, a partir de estos surgen los Casos de Uso, esta entidad es una superclase para los tipos de usuario Administrador, Lector Free y Lector Premium.

- **Rol:** Entidad que representa al tipo de Usuario que interactúa con el sistema, Administrador que tendrá los privilegios para agregar, actualizar o eliminar libros del sistema; Lector Free que será capaz de entrar al sistema pero estará limitado a leer 3 libros por 2 meses; por otra parte, el Lector Premium contará con todas las características como creación de Readlist y leer libros ilimitadamente.

- **Libro:** Entidad que representa al libro vitual por el cual, un usuario, será capaz de leer según las restricciones mencionadas anteriormente, tendrá varias características como género, numero de páginas, titulo, fecha y la portada del mismo.

- **Readlist:** Entidad que representa a una colección de libros de acuerdo a un tema o nombre según como lo desee el tipo Lector Premium para ordenar sus libros, aquí viene implícito el Readlist "Favoritos".

- **Rating:** Entidad que representa la calificación del respectivo libro por parte de los usuarios, aquí predomina más la calificación otorgada.

## Diagrama Entidad-Relación



# Documentación de la API
## Documentación de cada Endpoint por Entidad

# Criterios de Calidad

# Referencias
- [Introducción al patrón de arquitectura por capas](https://arevalomaria.wordpress.com/2010/12/02/introduccion-al-patron-de-arquitectura-por-capas/)
- [Platzi: Patrón arquitectónico de capas](https://platzi.com/tutoriales/1248-pro-arquitectura/5439-patron-arquitectonico-de-capas-layers/#:~:text=La%20arquitectura%20en%20capas%20es,de%20lo%20que%20le%20cooresponde)
- [Los 5 principales patrones arquitectónicos de software](https://apiumhub.com/es/tech-blog-barcelona/principales-patrones-arquitectura-software/)