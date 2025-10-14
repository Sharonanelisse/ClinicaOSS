# ClincaOss

Sistema web y catálogo de sistema para clínica odontológica.

## Autores
- Sharon Anelisse Marroquín Hernandez
- Eddy Alexander Cheguen Garcia
- Araceli de los Angeles Asencio y Asencio
- David Roberto Escobar Mérida
- Ismael Alejandro Liquez Muñoz
- Josimar Brandon Andrée Hernández Calzadia

## Estructura del Proyecto

```
src/main/java/com/smarroquin/clinicaoss
├── config/           
├── controllers/
├── converters/     
├── health/               
├── models/     
├── repositories/  
└── services/           
```

### Base de Datos
```bash
docker run --name postgres-jpql -e POSTGRES_PASSWORD=admin123 -e POSTGRES_USER=postgres -e POSTGRES_DB=clinicaoss -p 5433:5432 -d postgres
```
