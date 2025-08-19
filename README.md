# TheBarberShop - Sistema de Gestión de Barberías

> **Aplicación de escritorio desarrollada en Java con interfaz Swing para la gestión completa de citas y servicios en barberías.**

## Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Manual de Usuario](#manual-de-usuario)
- [Características](#características)
- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación](#instalación)
- [Roles del Equipo](#roles-del-equipo)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Soporte](#soporte)

##  Descripción del Proyecto

Desarrollar una aplicación de escritorio con interfaz gráfica utilizando Java y programación orientada a objetos (POO) en Apache NetBeans, que permita a un peluquero gestionar su barbería de forma eficiente.

---

##  MANUAL DE USUARIO

### Información General
- **Versión:** 1.0
- **Desarrollado por:** Ana, Charlie y Jael (Ancharja Studios)
- **Plataforma:** Aplicación de Escritorio (Java - Swing)
- **Base de Datos:** MySQL

###  Inicio Rápido

#### 1. Inicio de Sesión
1. Ingrese su correo electrónico registrado
2. Ingrese su contraseña
3. Haga clic en "Entrar"
4. Será redirigido al menú correspondiente (Cliente o Barbero)
<img width="920" height="529" alt="image" src="https://github.com/user-attachments/assets/6a42f273-be0a-441c-ab6e-0bfd935b3b5d" />


#### 2. Registro de Usuario

**Para Clientes:**
- Complete: nombre, correo, ciudad, teléfono, contraseña
- Seleccione "Cliente" como tipo de usuario
- Haga clic en "Registrar"
<img width="920" height="527" alt="image" src="https://github.com/user-attachments/assets/8c71b679-87dc-40d7-9232-94acabae7a66" />


**Para Barberos:**
- Complete los mismos datos del cliente
- Agregue: experiencia y nombre de la barbería
- Seleccione "Barbero" como tipo de usuario
<img width="923" height="526" alt="image" src="https://github.com/user-attachments/assets/ea7587e4-3760-4e14-80b0-d88340d2827a" />


###  Funciones para Clientes

#### Agendar Cita
1. Seleccione "Agendar Cita" desde el menú principal
2. Elija un barbero de la lista disponible
3. Seleccione el servicio deseado
4. Escoja fecha y hora disponible
5. Agregue notas adicionales (opcional)
6. Confirme la cita

#### Mi Perfil
- Editar datos personales (nombre, teléfono, ciudad)
- Cambiar foto de perfil
- Modificar contraseña
- Eliminar cuenta (acción irreversible)

###  Funciones para Barberos

#### Gestionar Disponibilidad
1. Acceda a "Disponibilidad" desde el menú
2. Seleccione días de trabajo
3. Elija turnos (mañana/tarde)
4. El sistema asigna horarios automáticamente
5. Guarde los cambios

#### Portafolio de Trabajos
- Subir fotos de trabajos realizados
- Ver galería de trabajos anteriores
- Agregar descripciones a las imágenes
- Mostrar estilos a nuevos clientes

#### Ver Citas y Actividad
- Lista de citas por día
- Resumen de actividad (total citas e ingresos)
- Gestión de perfil profesional

###  Funciones Generales
- **Seguridad:** Contraseñas encriptadas con BCrypt
- **Validaciones:** Correos únicos, formatos correctos
- **Ayuda:** Información del software y desarrolladores
- **Sesiones:** Cerrar sesión o salir del sistema

---

##  Características

-  **Sistema de autenticación seguro** con encriptación BCrypt
-  **Gestión dual de usuarios** (Clientes y Barberos)
-  **Sistema de citas inteligente** con validación de disponibilidad
-  **Portafolio visual** para barberos
-  **Seguimiento de ingresos** y estadísticas
-  **Gestión de perfiles** con fotos personalizadas
-  **Control de horarios** y disponibilidad

##  Requisitos del Sistema

| Componente | Requisito |
|------------|-----------|
| Sistema Operativo | Windows, Linux o macOS |
| Java | JDK 11 o superior |
| IDE | NetBeans o IntelliJ IDEA |
| Base de Datos | MySQL 8.0 |
| Memoria RAM | 2 GB mínimo |
| Espacio en disco | 100 MB |

##  Instalación

### Paso 1: Configurar Base de Datos
```sql
-- Ejecutar script SQL del archivo database/schema.sql
-- Crear base de datos barberia_system
-- Importar todas las tablas requeridas
```

### Paso 2: Configurar Proyecto
```bash
# Clonar repositorio
git clone https://github.com/AnaZSantanaG/barberia-management.git

# Importar en NetBeans
# Agregar mysql-connector-java como dependencia
# Ejecutar IniciarSesion.java
```


##  Roles del Equipo

| Miembro | Rol | Responsabilidades |
|---------|-----|------------------|
| **Charlie** | SQA | diseño de interfaces y Realizó pruebas. |
| **Ana** | BDA | Diseñó la base de datos y scripts SQL y validaciones. |
| **Jael** | Lider de Proyecto | Coordinó el proyecto y revisó el código. |

##  Tecnologías Utilizadas

- **Lenguaje:** Java
- **Framework GUI:** Swing
- **Base de Datos:** MySQL
- **IDE:** Apache NetBeans
- **Seguridad:** BCrypt
- **Paradigma:** Programación Orientada a Objetos (POO)

##  Soporte

Para reportar errores o solicitar ayuda:

-  **Email:** soporte@thebarbershop.dev
-  **Issues:** [GitHub Issues](https://github.com/AnaZSantanaG/barberia-management.git/issues)
-  **Documentación completa:** Ver archivos en `/docs`

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

<div align="center">

**TheBarberShop v1.0**  
*Desarrollado con ❤️ por Ancharja Studios*

[Ana](https://github.com/AnaZSantanaG) • [Charlie](https://github.com/charlielangumas) • [Jael](https://github.com/Jaeljc)

</div>
