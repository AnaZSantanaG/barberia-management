# barberia-management
Desarrollar una aplicación de escritorio con interfaz gráfica utilizando Java y programación orientada a objetos (POO) en Apache NetBeans, que permita a un peluquero gestionar su barbería de forma eficiente.

Configuración de la Base de Datos (para desarrolladores)
Este proyecto incluye un script SQL con la estructura y datos iniciales necesarios para que puedas ejecutar la aplicación en tu computadora. A continuación te explicamos cómo configurar la base de datos localmente.

Estas instrucciones están pensadas para que cualquier persona (compañero, profesor o evaluador) pueda probar el proyecto sin problemas. 

Se encuentra en:
barberia-management/database/schema.sql
Este archivo contiene:

La creación de todas las tablas.
Datos iniciales de prueba (como usuarios, servicios, etc.).
El rol admin y un usuario de prueba para pruebas.

Requisitos previos
Antes de continuar, asegúrate de tener instalado:

MySQL Server (versión 5.7 o superior)
🔗 Descarga: https://dev.mysql.com/downloads/installer/
MySQL Workbench (opcional, para interfaz gráfica)
O usa la consola de comandos (mysql).
Git (para clonar el repositorio)
🔗 https://git-scm.com/downloads

 Paso 1: Clonar el repositorio
Abre la terminal o Git Bash y ejecuta:
1.git clone https://github.com/tu-usuario/barberia-management.git
2.cd barberia-management
Reemplaza tu-usuario con el nombre real del dueño del repositorio. 

Paso 2: Iniciar MySQL
Asegúrate de que el servicio de MySQL esté en ejecución.
En Windows: Ve al Administrador de tareas > Servicios y busca MySQL, y asegúrate de que esté corriendo.
En macOS/Linux: Ejecuta en la terminal
1.sudo systemctl start mysql o 2.brew services start mysql

Paso 3: Crear la base de datos
Abre la terminal y entra al cliente de MySQL:
mysql -u root -p
 Ingresa la contraseña que configuraste para root. Si no has cambiado, puede ser root o estar vacía.
Una vez dentro de MySQL, crea la base de datos:
CREATE DATABASE IF NOT EXISTS barberia_system;
USE barberia_system;
Sal de MySQL con: EXIT;

Paso 4: Ejecutar el script schema.sql
Desde la terminal, ejecuta este comando para aplicar el esquema:
mysql -u root -p barberia_system< barberia-management/database/schema.sql Se te pedirá tu contraseña de MySQL (root o la que uses). 

Este comando:
Crea las tablas.
Inserta datos de prueba.
Agrega el usuario con rol admin.

Paso 5: Verificar que todo funcionó
Vuelve a entrar a MySQL:
mysql -u root -p
USE barberia_system;
SELECT nombre, email, tipo FROM users WHERE tipo = 'admin';
Deberías ver algo como:
+----------------------+------------------+------+
| email                | clave            | tipo |
+----------------------+------------------+------+
| admin@barbershop.com | admin123         | admin|
+----------------------+------------------+------+

Paso 6: Ejecutar la aplicación
Ahora que la base de datos está lista, puedes abrir el proyecto en tu IDE (NetBeans, IntelliJ, etc.) y ejecutar la clase principal (por ejemplo, Login.java o Main.java).
La aplicación se conectará automáticamente a thebarbershop si tu DatabaseConnection está configurada así.






