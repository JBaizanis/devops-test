Οδηγίες για την εκτέλεση της εφαρμογής:

1) Εκτέλεση της εντολής: docker run --name ds-lab-pg --rm `
			>> -e POSTGRES_PASSWORD=pass123 `
			>> -e POSTGRES_USER=dbuser `
			>> -e POSTGRES_DB=compensation `
			>> -d `
			>> -v ds-lab-vol:/var/lib/postgresql/data `
			>> -p 5432:5432 `
			>> postgres:14
2) Μετάβαση στο κουτάκι database δεξιά της οθόνης στο intellij και δημιουργία της postgres βάσης (Πατάμε στο "+" και επιλέγουμε στα data sources το postgreSQL. Όπου username = dbuser, password = pass123, database = compensation και τέλος κάνουμε test connection και πατάμε ok).
3) Εκτέλεση της εφαρμογής από το Intellij.
4) Μετάβαση στον σύνδεσμο http://localhost:8080/swagger-ui/index.html/ όπου βρίσκονται αναλυτικά όλες οι μέθοδοι των Controllers, Rest Controllers
   και ο Auth Controller.
5) Εκτέλεση των αντίστοιχων μεθόδων.