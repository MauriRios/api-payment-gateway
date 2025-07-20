# API Payment Gateway

Este proyecto es una API unificada para gestionar pagos a través de múltiples proveedores.  
Actualmente soporta **Mercado Pago** y **Stripe**, y está diseñada para escalar fácilmente e incorporar nuevos proveedores en el futuro.

## 🧩 ¿Cómo funciona?

El sistema sigue un diseño basado en el patron Strategy, donde cada proveedor implementa su propio `PaymentProcessor`. 
Esta api esta desarrollada en Java 17, utilizando SpringBoot y JWT.

```
Request -> PaymentService -> Processor correspondiente (ej: MercadoPagoProcessor, StripeProcessor) -> Redirección o manejo
```

La selección del proveedor se realiza dinámicamente según el campo `"provider"` enviado en la solicitud.

## ✅ Proveedores actuales

- `MERCADO_PAGO`
- `STRIPE`

Cada uno tiene su propia implementación de:

- `PaymentProcessor`
- `PaymentData` con los datos necesarios del proveedor

## ➕ ¿Cómo agregar un nuevo proveedor?

1. Crear una clase que extienda `AbstractPaymentData` ejemplo: "MercadoPagoPaymentData" con los datos necesarios del proveedor
2. Crear un service processor que implemente `PaymentProcessor` ejemplo: "MercadoPagoProcessor" donde va la logica para iniciar el pago
3. Registrar el nuevo tipo en `AbstractPaymentData` con la anotación:
```java
@JsonSubTypes.Type(value = TuNuevoPaymentData.class, name = "NUEVO_PROVEEDOR")
```
   
## 🔐 Variables de entorno necesarias

| Variable                  | Descripción                         |
|---------------------------|-------------------------------------|
| `MERCADOPAGO_ACCESS_TOKEN` | Token secreto de Mercado Pago       |
| `STRIPE_SECRET_KEY`       | Clave privada de Stripe             |
| `JWT_BASE64_SECRET`       | Secreto para generar los JWT tokens |

## 💻 Cómo configurar las variables de entorno

### En **Windows (PowerShell)**:

```powershell
[Environment]::SetEnvironmentVariable("MERCADOPAGO_ACCESS_TOKEN", "TU_TOKEN_MP", "User")
[Environment]::SetEnvironmentVariable("STRIPE_SECRET_KEY", "TU_CLAVE_STRIPE", "User")
[Environment]::SetEnvironmentVariable("JWT_BASE64_SECRET", "TU_SECRETO_JWT", "User")
```

### En **Linux / macOS (bash/zsh)**:

```bash
echo 'export MERCADOPAGO_ACCESS_TOKEN="TU_TOKEN_MP"' >> ~/.bashrc
echo 'export STRIPE_SECRET_KEY="TU_CLAVE_STRIPE"' >> ~/.bashrc
echo 'export JWT_BASE64_SECRET="TU_SECRETO_JWT"' >> ~/.bashrc
source ~/.bashrc
```

## 🚀 Cómo ejecutar el proyecto

Primero asegurate de tener las variables de entorno configuradas.

Luego, ejecutá el comando: mvn clean spring-boot:run


## 🔧 Próximas implementaciones

En futuras versiones se implementarán:

- **Registro de logs en base de datos**: para mantener un historial estático de los movimientos de pagos iniciados, exitosos o fallidos.
- **Webhooks**: integración con los servicios de pago (ej. Stripe, Mercado Pago) para recibir notificaciones en tiempo real sobre los cambios de estado de los pagos (approved, rejected, pending, etc).

Esto permitirá tener un mayor control y trazabilidad de las operaciones de pago dentro del sistema.


## 🔒 Licencia

Este proyecto está protegido bajo una licencia privada.  
**No está permitido su uso, redistribución ni modificación sin autorización expresa del autor.**

Copyright (c) 2025, Mauricio Osvaldo Ríos. All rights reserved.

This software and associated documentation files (the "Software") are proprietary and confidential. 
Unauthorized copying, modification, distribution, or use of this software is strictly prohibited.

For inquiries regarding licensing or usage rights, please contact: mauri.rios991@gmail.com
