# Documentación de Arquitectura de Software - Sistema de Tipo de Cambio

## 1. Visión General
El sistema está diseñado siguiendo una arquitectura de microservicios, dividida en dos APIs principales que trabajan de manera coordinada para proporcionar un servicio de tipo de cambio seguro y escalable.

## 2. Componentes Principales

### 2.1 Experience API
#### Responsabilidades:
- Autenticación y autorización mediante JWT
- Validación de usuarios contra GoRest API
- Enrutamiento de solicitudes al Support API
- Manejo de errores y excepciones de nivel superior

#### Tecnologías:
- Spring WebFlux
- Spring Security
- JWT
- WebClient para llamadas HTTP reactivas

### 2.2 Support API
#### Responsabilidades:
- Lógica de negocio core
- Operaciones CRUD de tipo de cambio
- Procesamiento de conversiones
- Persistencia de datos

#### Tecnologías:
- Spring WebFlux
- R2DBC para operaciones reactivas con base de datos
- H2 Database

## 3. Capas de la Arquitectura

### 3.1 Capa de Presentación (API Layer)
- Controllers reactivos
- DTOs para transferencia de datos
- Validación de entrada
- Documentación Swagger
- Manejo de respuestas HTTP

### 3.2 Capa de Negocio (Service Layer)
- Servicios reactivos
- Lógica de negocio
- Validaciones de negocio
- Transformación de datos
- Orquestación de operaciones

### 3.3 Capa de Datos (Data Layer)
- Repositorios reactivos
- Entidades
- Mapeo objeto-relacional
- Consultas y transacciones

## 4. Patrones de Diseño Implementados

### 4.1 Patrones Arquitectónicos
- Microservicios
- Reactive Programming

### 4.2 Patrones de Diseño
- Builder Pattern (DTOs)
- Factory Pattern (Servicios)
- Repository Pattern

## 5. Seguridad

### 5.1 Autenticación
- JWT Token-based
- Validación de usuarios contra GoRest
- Manejo de tokens expirados

### 5.2 Autorización
- Roles y permisos
- Validación de estado de usuario
- Filtros de seguridad

## 6. Manejo de Datos

### 6.1 Esquema de Base de Datos
- Tablas normalizadas
- Relaciones definidas

## 7. Integración y Comunicación

### 7.1 Comunicación Interna
- WebFlux para comunicación reactiva
- Eventos y mensajes asíncronos

### 7.2 Comunicación Externa
- REST APIs
- HTTP/HTTPS
- JWT para autenticación

## 8. Aspectos Técnicos

### 8.1 Configuración
- Propiedades externalizadas
- Variables de entorno

## 9. Testing

### 9.1 Herramientas
- JUnit 5
- Mockito

## 10. Despliegue

### 10.1 Requerimientos
- JDK 17
- Maven 3.x
- H2 Database
- Configuración de ambiente

### 10.2 Proceso
- Build con Maven
- Generación de artefactos
- Configuración de propiedades
- Despliegue de servicios