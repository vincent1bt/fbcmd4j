# fbcmd4j

## Instalación

Si se quiere compilar desde la terminal usar el siguiente comando:

```
 javac org/fbcmd4j/Main.java -d . org/fbcmd4j/LoadHelper.java -cp "lib/facebook4j-core-2.4.13.jar:lib/log4j-api-2.12.1.jar:lib/log4j-core-2.12.1.jar"
```

Para crear el archivo **jar**:

```
jar cfm Main.jar Manifest.txt org/fbcmd4j/Main.class org/fbcmd4j/helper/LoadHelper.class lib log4j2.xml
```

Crear una aplicacion en [https://developers.facebook.com/apps](https://developers.facebook.com/apps) para obtener **appId**, **appSecret** y **accessToken**. Si se quieren usar todas las funcionalidades se tienen que pedir permisos extra.

## Uso

Para ejecutar la aplicación desde la terminal:
```
java -cp .:"lib/facebook4j-core-2.4.13.jar:lib/log4j-api-2.12.1.jar:lib/log4j-core-2.12.1.jar" org.fbcmd4j.Main
```

Ejecutar la aplicación usando el archivo jar:

```
java -jar Main.jar
```

## Créditos
Desarrollado por: Vicente de Jesus Rodriguez Cuellar.

## Licencia
El código esta disponible bajo la licencia **MIT**.
