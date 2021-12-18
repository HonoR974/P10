# P10
 
 <addr> Projet 10 du parcours Developpeur Web Java. 

 
> Modules : 
 
  * Client UI 
  * API Bibliotheque 
  * Batch 
 
 > Installation necessaire au deploiement : 
  * Installer la version 4.0.0 de Maven. 
  * Installer la version 11 de Java. 
 
  
 > Implementation App Web : 
 
  * Se rendre dans le fichier *P10/bibliotheque/src/main/resources/application.properties* de l'api Bibliotheque. 
  * Remplacer les informations vous concernant pour la connexion à votre base de donnée. 
  * Dans votre base de donnée utiliser le scripts SLQ de création de base de données avec le jeu de démo,
    "P10/SQL/CreationBDD&Demo.SQL". 
  * Ouvrer un terminal et rendez-vous à la racine du dossier "bibliotheque" et utliser la commande : mvn deploy 
  puis *Clientui*. 
 
 > Implementation Batch : 
 
  * Rendez vous dans le fichier *application.properties* du batch.
  * Ajouter les informations concernant votre boite mail.
  * Demarrer le module *API Bibliotheque* puis *Batch*. 
 
  > Test Batch - Pret ( Dump/DumpP7.sql ) : 
 
 * Demarrer le batch.
 * Changer la date de fin d'un des prets, si aucune date n'est dépassé.
 * Redemarrer le batch. 
 
   > Test Batch - Reservation ( Dump/DumpP10.sql )  : 
 
 * Demarrer le batch.
 * Une fois que le livre est disponible et que l'admin l'a recu dans son espace personel. 
 * Changer l'email de l'user "Gandalfe" par la votre ( differente de celle utilisé pour l'implementation du batch ) . 
 * Redemarrer le batch. 
 
 
 
 
