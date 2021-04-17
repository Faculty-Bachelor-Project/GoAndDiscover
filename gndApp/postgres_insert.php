<?php

$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
$conn=pg_connect($conn_string) or die(exit);


//$sql_insert = "INSERT INTO locations (titlename, describelocation) VALUES ('Tampa', 'Tampa (in maghiara Cenk, in germana Zinne, alternativ Kapellenberg) este un munte care apartine de masivul Postavaru, localizat in sudul Carpatilor Orientali (mai precis in Carpatii de Curbura) si inconjurat aproape in totalitate de municipiul Brasov. Muntele este alcatuit in principal din formatiuni calcaroase formate in urma procesului de incretire a scoartei terestre. Inaltimea maxima atinsa este de 960m (dupa unele surse 995m), la aproape 400m deasupra orasului. Mare parte a sa (150 ha) este instituita ca rezervatie naturala de tip florisitc, faunistic si peisagistic; corespunzatoare categoriei a IV-a IUCN. Aceasta a fost infiintata in anul 1980, urmand ca apoi, prin Legea Nr.5 din 6 martie 2000 (privind aprobarea Planului de amenajare a teritoriului national - Sectiunea a -III-a - zone protejate sa fie declarata arie protejata de interes national. La baza desemnarii acesteia se afla mai multe specii de animale (ursi, rasi, lupi, fluturi - 35% din totalul speciilor din tara noastra, pasari) si plante rare (crucea voinicului, obsiga barsana) protejate la nivel european.')";
//$sql_insert="INSERT INTO locations (titlename, describelocation) VALUES ('Turnul Alb', 'Construit intre anii 1460 si 1494, Turnul Alb impresioneaza si astazi prin masivitatea si zveltetea liniilor sale arhitectonice. Rezumand arhitectura sa in date putem spune: plan semicircular deschis; peste 30 m diferenta de nivel fata de zidurile orasului; inaltime: 20 m spre oras si 18 m inspre deal; zidurile au la baza 4 m, iar diametrul turnului masoara 19 m. De-a lungul zidurilor sale, turnul prezinta metereze, guri pentru smoala si balcoane sustinute de console cioplite in piatra. Aflandu-se la 59 m departare de zidul cetatii, turnul comunica cu aceasta printr-un pod mobil ce facea legatura intre turn si Bastionul Graft. Avea vedere spre Blumana si, cu cele 5 etaje ale sale, era cel mai ridicat punct de fortificatie din Brasov. In interiorul turnului s-a pastrat cosul de fum de deasupra unei vetre, care putea servi si pentru incalzirea paznicilor si a aparatorilor - breslasi cositorari si aramari. In 1678, breasla cositorilor a rascumparat obligatia de aparare a turnului, numarul mesterilor fiind scazut. Cu ocazia marelui incendiu din 21 aprilie 1689, focul dus de un vant puternic a cuprins si Turnul Alb, care a ars, fiind renovat de abia in 1723. Alte actiuni de restaurare au fost efectuate in 1902, 1974, 2002 si 2005 - 2006. Astazi detine un punct muzeal.')";

//$sql_insert = "INSERT INTO images (imagelink, locations_id) VALUES ('http://192.168.0.164/AplicatieLicenta/imagini/turnul_alb_distanta.jpg',2)";

$retval = pg_query($conn, $sql_insert);
if(! $retval )
{
  die('Could not insert data: ' . exit);
}
echo "Data inserted successfully\n";

?>