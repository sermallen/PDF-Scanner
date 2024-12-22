## Como ejecutar el programa
Ejecutar en una consola de comandos el script `run.bat` seguido de un fichero pdf.
Ejemplo:
```
.\run.bat resources\pdfs\01.pdf
```

## Salida del programa
El porgrama actualmente genera un ficheros en la carpeta `output`: `content.txt`.
`content.txt` es una lista del texto del fichero, dividido en las diferentes fuentes, tamaños y otros parametros como la posición. Tambien contiene el contenido texto sin procesar de cada fuente.

> **Nota:** Puede ser que el texto sin procesar esté en HEX o contenga caracteres desconocidos.


El programa guarda en memoria una lista de las fuentes utilizadas. Actualmente se escribe por salida estándar el *outline* con la posición de la sección y la página. Esto se utilizará en el futuro para diferenciar entre las secciones.
También se escribe por salida estándar elttitulo del artículo, si está disponible.