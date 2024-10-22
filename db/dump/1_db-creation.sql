
DROP TABLE if exists public.account CASCADE;

/* TODO : ajouter la création de la table "client" */

CREATE TABLE Account(
   Id_Account SERIAL,
   creationTime TIMESTAMP,
   balance NUMERIC(15,2)  ,
   PRIMARY KEY(Id_Account)
);

CREATE TABLE Client(
   Id_Client UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   firstName VARCHAR(50) ,
   lastName VARCHAR(50) ,
   email VARCHAR(50) ,
   birthDate DATE,
   Id_Account INTEGER NOT NULL,
   FOREIGN KEY(Id_Account) REFERENCES Account(Id_Account)
);
