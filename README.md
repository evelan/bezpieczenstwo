# security-laboratory

Aplikacja: komunikator sieciowy z protokołem wymiany kluczy diffiego-hellmana

### Jak uruchomić

Serwer:

`java -jar Server.jar 8080`

Port jest opcjonalny, jeśli go nie będzie zostanie użyty domyślny 9000

Klient:

`java -jar Client.jar onet.pl 8080`

Adres serwera i port są opcjonalne, jeśli ich nie bedzie zostaną użyte domyślne wartosci czyli localhost:9000


### Jak skompilować

* Pobrać projekt
* Otworzyć w IntelJ IDEA 
* Kliknąć Buil -> Build artifact... -> All -> Build...

### Wymagania
* Java 1.8.0_102 (vendor: Oracle Corp.)
* Maven 3.3.9
* InteliJ IDEA - w celu kompilacji 
