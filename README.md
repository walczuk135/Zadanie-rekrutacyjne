
Budowanie programu
Proces budowy rozpoczynamy wpisując komende w folderze głównym:
mvn clean install
w przybadku braku narzędzia maven można skorzystać z wersji wbudowanej w IntelliJ IDEA.

Startowanie Aplikacji
Po zakończeniu procesu budowy pojawi się folder target, a w nim plik productRow-service-0.0.1-SNAPSHOT.jar.
Aplikacjie można wystartować poleceniem:
java -jar productRow-service-0.0.1-SNAPSHOT.jar
Aplikacjia do działania nie potrzebuje zewnetrznego serwera aplikacyjnego (serwer wbudowany jest w projekt).
Aplikacjia domyślnie uruchamia się na porcie 8080, aby zmienic port nalerzy wpisać wartość portu w pliku application.properties.

Użytkowanie aplikacji
Aby wyświetlić produkt po id należy wysłać żądanie GET http://localhost:8080/product/id/{id} 
Aby wyświetlić produkt po nazwie należy wysłać żądanie GET http://localhost:8080/product/{name}
Aby wyśietlić szystkie produkty należy wysłać żądanie GET http://localhost:8080/product/getAll
Aby storzyć nowy produkt należy wysłać żądanie POST http://localhost:8080/product/
Aby usunąć  produkt należy wysłać żądanie Delete http://localhost:8080/product/

Użyte technologie
Spring Boot, Spring Data, H2 database, Maven
