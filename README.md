# ProductMgmtGUI
ProductMgmtGUI este un program care se conecteaza la o baza de date care contine un tabel cu Produsele, un tabel care contine toate 
seriile inregistrate si legatura lor cu produsele si un tabel care inregistreaza pe un rand toate timpurile corespunzatoare unei serii.

Clasa DBconnector se conecteaza la baza de date si inmagazineaza in liste tabelele (exista clase pentru produs, serii si timpi cu campuri
pentru coloanele din tabele). Seriile si produsele se afiseaza prin listView-uri care obtin datele din DBconnector printr-un querry.

O fereastra noua afiseaza timpii corespunzatori unei serii. Din aceasta fereastra se pot adauga timpii care lipsesc, si ptrintr-un menu 
contextual se pot opera modificari ale timpilor sau se pot sterge timpii incorecti.
