# barberia-management

Desarrollar una aplicación de escritorio con interfaz gráfica utilizando Java y programación orientada a objetos (POO) en Apache NetBeans, que permita a un peluquero gestionar su barbería de forma eficiente.

##  Configuración de la Base de Datos (para desarrolladores)

Este proyecto incluye un script SQL con la estructura y datos iniciales necesarios para que puedas ejecutar la aplicación en tu computadora. A continuación te explicamos cómo configurar la base de datos localmente.

**📁 Ubicación del script:** `barberia-management/database/schema.sql`

### ¿Qué contiene el archivo schema.sql?
-  Creación de todas las tablas necesarias
-  Datos iniciales de prueba (usuarios, servicios, etc.)
-  Usuario administrador para pruebas

---

##  Requisitos previos

Antes de continuar, asegúrate de tener instalado:

1. **MySQL Server** (versión 5.7 o superior)
   -  [Descarga MySQL](https://dev.mysql.com/downloads/installer/)

2. **MySQL Workbench** (opcional, para interfaz gráfica)
   - O usar la consola de comandos (`mysql`)

3. **Git** (para clonar el repositorio)
   -  [Descarga Git](https://git-scm.com/downloads)

---

##  Pasos de instalación

### Paso 1: Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/barberia-management.git
cd barberia-management
```
>  **Importante:** Reemplaza `tu-usuario` con el nombre real del dueño del repositorio.

### Paso 2: Iniciar MySQL
Asegúrate de que el servicio de MySQL esté en ejecución:

**Windows:**
- Ve al Administrador de tareas → Servicios
- Busca "MySQL" y asegúrate de que esté corriendo

**macOS/Linux:**
```bash
# Opción 1 (systemd)
sudo systemctl start mysql

# Opción 2 (Homebrew en macOS)
brew services start mysql
```

### Paso 3: Crear la base de datos
1. Abre la terminal y entra al cliente de MySQL:
```bash
mysql -u root -p
```

2. Ingresa tu contraseña de MySQL (por defecto puede ser `root` o estar vacía)

3. Crea la base de datos:
```sql
CREATE DATABASE IF NOT EXISTS barberia_system;
USE barberia_system;
```

4. Sal de MySQL:
```sql
EXIT;
```

### Paso 4: Ejecutar el script schema.sql
Desde la terminal, ejecuta:
```bash
mysql -u root -p barberia_system < database/schema.sql
```
> Se te pedirá tu contraseña de MySQL

 **Esto creará:**
- Todas las tablas necesarias
- Datos de prueba
- Usuario administrador

### Paso 5: Verificar la instalación
1. Vuelve a entrar a MySQL:
```bash
mysql -u root -p
```

2. Verifica que los datos se insertaron correctamente:
```sql
USE barberia_system;
SELECT email, clave, tipo FROM users WHERE tipo = 'admin';
```

3. Deberías ver algo como:
```
+----------------------+----------+-------+
| email                | clave    | tipo  |
+----------------------+----------+-------+
| admin@barbershop.com | admin123 | admin |
+----------------------+----------+-------+
```

### Paso 6: Ejecutar la aplicación
1. Abre el proyecto en tu IDE preferido (NetBeans, IntelliJ, Eclipse, etc.)
2. Ejecuta la clase principal (por ejemplo, `Login.java` o `Main.java`)
3. La aplicación se conectará automáticamente a la base de datos `barberia_system`

---

##  Credenciales de prueba

**Usuario Administrador:**
- **Email:** admin@barbershop.com
- **Contraseña:** admin123

---

##  Solución de problemas comunes

### Error: "Access denied for user 'root'"
- Verifica que la contraseña de MySQL sea correcta
- Intenta con: `mysql -u root` (sin contraseña)

### Error: "Can't connect to MySQL server"
- Asegúrate de que el servicio MySQL esté corriendo
- Verifica que MySQL esté instalado correctamente

### Error: "Database doesn't exist"
- Asegúrate de haber ejecutado el Paso 3 correctamente
- Verifica que el nombre de la base de datos sea `barberia_system`

---

##  ¿Necesitas ayuda?

Si tienes problemas con la configuración, contacta con el equipo de desarrollo o abre un issue en el repositorio.

---

**¡Listo! Tu entorno de desarrollo está configurado y puedes empezar a trabajar en el proyecto.**