# Technologie Sieciowe

Zadania na laboratoria (kod i sprawozdania) tworzone w ramach kursu na PWr prowadzonego przez dr Krzywieckiego.

## Spis treści

Treść zadań kolejnych list.

### Lista 1

1. Przetestuj działanie programów:
    - Ping: Sprawdź za jego pomocą ile jest węzłów na trasie do (i od) wybranego, odległego geograficznie, serwera. Uwaga: trasy tam i z powrotem mogą być różne. Zbadaj jaki wpływ ma na to wielkość pakietu. Zbadaj jak wielkość pakietu wpływa na obserwowane czasy propagacji. Zbadaj jaki wpływ na powyższe ma konieczność fragmentacji pakietów. Jaki największy niefragmentowany pakiet uda się przesłac. Przeanalizuj te same zagadnienia dla krótkich tras (do serwerów bliskich geograficznie). Określ "średnicę" internetu (najdłuższą sćieżkę którą uda sie wyszukać). Czy potraficz wyszukać trasy przebiegające przez sieci wirtualne (zdalne platformy "cloud computing"). Ile węzłów mają scieżki w tym przypadku.
    - Traceroute
    - WireShark
2. Napisz sprawozdanie zawierające: opis programów, wywołania dla powyższych zagadnień z analizą wyników, wnioski dotyczące przydatności tych programów.

### Lista 2

1. Rozważmy model sieci, w którym czas działania podzielony jest na interwały. Niech S = < G, H > będzie modelem sieci takim, że zbiór V grafu G = < V, E > zawiera 20 wierzchołków oznaczonych przez v(i), dla i = 1,..20; a zbiór E zawiera 19 krawędzi e(j,j+1), dla j = 1, ...,19, (przy czym zapis e(j,k) oznacza krawędź łączącą wierzchołki v(i) i v(k) ). Zbiór H zawiera funkcję niezawodości 'h' przyporządkowującą każdej krawędzi e(j,k) ze zbioru E wartość 0.95 oznacząjącą prawdopodobieństwo nieuszkodzenia (nierozerwania) tego kanału komunikacyjnego w dowolnym przedziale czasowym. (Zakładamy, że wierzchołki nie ulegaja uszkodzeniom).
    - Napisz program szacujący niezawodność (rozumianą jako prawdopodobieństwo nierozspójnienia) takiej sieci w dowolnym interwale.
    - Jak zmieni się niezawodność tej sieci po dodaniu krawędzi e(1,20) takiej, że h(e(1,20))=0.95
    - A jak zmieni się niezawodność tej sieci gdy dodatkowo dodamy jeszcze krawędzie e(1,10) oraz e(5,15) takie, że: h(e(1,10))=0.8, a h(e(5,15))=0.7.
    - A jak zmieni się niezawodność tej sieci gdy dodatkowo dodamy jeszcze 4 krawedzie pomiedzy losowymi wierzchołkami o h=0.4.
Uwaga! Do szacowania niezawodności (spójności) najlepiej posłużyć się metodą Monte Carlo.
2. Rozważmy model sieci S = < G, H >. Przez N=[n(i,j)] będziemy oznaczać macierz natężeń strumienia pakietów, gdzie element n(i,j) jest liczbą pakietów przesyłanych (wprowadzanych do sieci) w ciągu sekundy od źródła v(i) do ujścia v(j).
    - Zaproponuj topologię grafu G ale tak aby żaden wierzchołek nie był izolowany oraz aby: |V|=10, |E|<20. Zaproponuj N oraz następujące funkcje krawędzi ze zbioru H: funkcję przepustowości 'c' (rozumianą jako maksymalną liczbę bitów, którą można wprowadzić do kanału komunikacyjnego w ciągu sekundy), oraz funkcję przepływu 'a' (rozumianą jako faktyczną liczbę pakietów, które wprowadza się do kanału komunikacyjego w ciągu sekundy). Pamiętaj aby funkcja przeplywu realizowała macierz N oraz aby dla każdego kanału 'e' zachodziło: c(e) > a(e).
    - Napisz program, w którym propozycje będzie można testować, tzn. który dla wybranych reprezentacji zadanych odpowiednimi macierzami, będzie obliczał średnie opóźnienie pakietu 'T' dane wzorem: T = 1/G * SUM_e( a(e)/(c(e)/m - a(e)) ), gdzie SUM_e oznacza sumowanie po wszystkich krawędziach 'e' ze zbioru E, 'G' jest sumą wszystkich elementów macierzy natężeń, a 'm' jest średnią wielkością pakietu w bitach.
    - Niech miarą niezawodności sieci jest prawdopodobieństwo tego, że w dowolnym przedziale czasowym, nierozspójniona sieć zachowuje T < T_max. Napisz program szacujący niezawodność takiej sieci przyjmując, że prawdopodobieństwo nieuszkodzenia każdej krawędzi w dowolnym interwale jest równe 'p'. Uwaga: 'N', 'p', 'T_max' oraz topologia wyjsciowa sieci są parametrami. Napisz sprawozdanie!

### Lista 3

1. Napisz program ramkujący zgodnie z zasadą "rozpychania bitów" (podaną na wykładzie), oraz weryfikujacy poprawność ramki metodą CRC . Program ma odczytywać pewien źródłowy plik tekstowy 'Z' zawierający dowolny ciąg złożony ze znaków '0' i '1' (symulujacy strumień bitów) i zapisywać ramkami odpowiednio sformatowany ciąg do inngo pliku tekstowego 'W'. Program powinien obliczać i wstawiać do ramki pola kontrolne CRC - formatowane za pomocą ciągów złożonych ze znaków '0' i '1'. Napisz program, realizujacy procedure odwrotną, tzn. który odzczytuje plik wynikowy 'W' i dla poprawnych danych CRC przepisuje jego zawartość tak, aby otrzymać kopię oryginalnego pliku źródłowego 'Z'.
2. Napisz program (grupę programów) do symulowania ethernetowej metody dostepu do medium transmisyjnego (CSMA/CD). Wspólne łącze realizowane jest za pomocą tablicy: propagacja sygnału symulowana jest za pomoca propagacji wartości do sąsiednich komórek. Zrealizuj ćwiczenie tak, aby symulacje można było w łatwy sposób testować i aby otrzymane wyniki były łatwe w interpretacji.

### Lista 4
1. Przykładowe programy: Z2Forwarder.java, Z2Packet.java, Z2Receiver.java, Z2Sender.java. Plik plik.txt zawiera przykładowe dane.
2. Program Z2Sender wysyła w osobnych datagramach po jednym znaku wczytanym z wejścia do portu o numerze podanym jako drugi parametr wywołania programu. Jednocześnie drukuje na wyjściu informacje o pakietach otrzymanych w porcie podanym jako pierwszy parametr wywołania. Program Z2Receiver drukuje informacje o każdym pakiecie, który otrzymał w porcie o numrze podanym jako pierwszy parametr wywołania programu i odsyła go do portu podanego jako drugi paramer wywołania programu. Klasa Z2Packet, umożliwia wygodne wstawianie i odczytywanie czterobajtowych liczb całkowitych do tablicy bajtów przesyłanych w datagramie - metody: public void setIntAt(int value, int idx) oraz public int getIntAt(int idx). Wykorzystane jest to do wstawiania i odczytywania numerów sekwencyjnych pakietów.
Po skompilowaniu, można je uruchomić w terminalu w następujący sposób: java Z2Receiver 6001 6000 & java Z2Sender 6000 6001 < plik.txt W tej konfiguracji Z2Receiver powinien otrzymywać wszystkie pakiety w odpowiedniej kolejności i bez strat, i odsyłane prze niego potwierdzenia, dochodzą do Z2Sender w taki sam (niezadwodny) sposób. Program Z2Sender po zakończeniu transmisji musi być ręcznie przerwany (CTRL+C), bo wątek odbierający oczekuje na kolejne pakiety. Program Z2Receiver można zatrzymać przy użyciu poleceń ps (aby odczytać nr procesu) i kill (aby wysłać do procesu sygnał zakończenia). W Internecie każdy pakiet przesyłany jest niezależnie i w miarę dostępnych możliwości. W związku z tym pakiety wysyłane przez nadawcę mogą być tracone, przybywać z różnymi opóźnieniami, w zmienionej kolejności, a nawet mogą być duplikowane. Program Z1Forwarder symuluje tego typu połączenie. Aby go użyć można wykonać (po zabiciu innych programów korzystających z portów 6000, 6001, 6002, 6003) następujące polecenia: java Z2Receiver 6002 6003 & java Z2Forwarder 6001 6002 & java Z2Forwarder 6003 6000 & java Z2Sender 6000 6001 < plik.txt W tej konfiguracji pierwszy Z2Forwarder przekazuje pakiety od Z2Sender do Z2Receiver, a drugi - w przeciwnym kierunku. (Może wystąpić pewne opóźnienie, zanim zaczną się pojawiać wyniki drukowane przez Z2Receiver i Z2Sender.)
3. Zadanie polega na takim wykorzystaniu potwierdzeń i numerów sekwencyjnych przez nadawcę i odbiorcę, aby odbiorca wydrukował wszystkie pakiety w kolejności ich numerów sekwencyjnych nawet jeśli połączenie w obie strony odbywa się przez Z2Frowarder. Nadawca może przypuszczać, że pakiet nie dotarł do celu jeśli przez długi czas nie otrzyma potwierdzenia od odbiorcy Może wtedy ten pakiet ponownie wysłać (retransmitować). Odbiorca może wykorzystywać numery sekwencyjne pakietów aby się zorientować czy ma prawo drukować dany pakiet, czy też musi czekać na brakujące wcześniejsza pakiety albo dany pakiet już był drukowany (np. jest duplikatem).

### Lista 5
Plik server3.pl zawiera przykładowy program serwera protokołu HTTP.
1. Uruchom ten skrypt, przetestuj, zastanów się jak działa.
2. Nawiąż połączenie za pomocą przykładowych klientów z listy poprzedniej.
3. Nawiąż połączenie za pomocą przeglądarki internetowej.
4. Zmień skrypt (lub napisz własny serwer w dowolnym języku programowania) tak aby wysyłał do klienta nagłówek jego żądania.
5. Zmień skrypt (lub napisz własny serwer w dowolnym języku programowania) tak aby obsugiwał żądania klienta do prostego tekstowego serwisu WWW (kilka statycznych ston z wzajemnymi odwołaniami) zapisanego w pewnym katalogu dysku lokalnego komputera na którym uruchomiony jest skrypt serwera.
6. Przechwyć komunikaty do/od serwera za pomocą analizatora sieciowego 'sniffera' - przeanalizuj ich konstrukcję.
7. Napisz zwięzłe (bardzo krótkie) sprawozdanie z wykonanych ćwiczeń.