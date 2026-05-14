IF NOT EXISTS (SELECT 1 FROM Country)
BEGIN
    DECLARE @idHrvatska  INT, @idItalija    INT, @idFrancuska INT;
    DECLARE @idZagreb    INT, @idSplit      INT, @idRim        INT, @idParis INT;
    DECLARE @idMUO       INT, @idAM         INT, @idVatikan    INT, @idLouvre INT;
    DECLARE @idKol1      INT, @idKol2       INT, @idKol3       INT;
    DECLARE @idKur1      INT, @idKur2       INT, @idKur3       INT, @idKur4 INT;

    -- Countries
    INSERT INTO Country (Name) VALUES ('Hrvatska');   SET @idHrvatska  = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Italija');    SET @idItalija   = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Francuska');  SET @idFrancuska = SCOPE_IDENTITY();

    -- Cities
    INSERT INTO City (Name, CountryID) VALUES ('Zagreb', @idHrvatska);  SET @idZagreb = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Split',  @idHrvatska);  SET @idSplit  = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Rim',    @idItalija);   SET @idRim    = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Pariz',  @idFrancuska); SET @idParis  = SCOPE_IDENTITY();

    -- Museums
    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Muzej za umjetnost i obrt', 'Trg Republike Hrvatske 10', 1880,
            'Zbirka primijenjene umjetnosti i dizajna od romanike do danas.', NULL, @idZagreb);
    SET @idMUO = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Arheološki muzej Split', 'Zrinsko-Frankopanska 25', 1820,
            'Najstariji muzej u Hrvatskoj s bogatom antičkom zbirkom.', NULL, @idSplit);
    SET @idAM = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Vatikanski muzeji', 'Viale Vaticano', 1506,
            'Jedna od najvećih i najvažnijih muzejskih zbirki na svijetu.', NULL, @idRim);
    SET @idVatikan = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Louvre', 'Rue de Rivoli', 1793,
            'Najveći muzej na svijetu s više od 35 000 izloženih predmeta.', NULL, @idParis);
    SET @idLouvre = SCOPE_IDENTITY();

    -- Museum collections
    INSERT INTO MuseumCollection (Name) VALUES ('Antička zbirka');       SET @idKol1 = SCOPE_IDENTITY();
    INSERT INTO MuseumCollection (Name) VALUES ('Moderna umjetnost');    SET @idKol2 = SCOPE_IDENTITY();
    INSERT INTO MuseumCollection (Name) VALUES ('Renesansna skulptura'); SET @idKol3 = SCOPE_IDENTITY();

    -- Curators
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ivan',   'Horvat', @idMUO);     SET @idKur1 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ana',    'Kovač',  @idAM);      SET @idKur2 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Marco',  'Rossi',  @idVatikan); SET @idKur3 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Sophie', 'Martin', @idLouvre);  SET @idKur4 = SCOPE_IDENTITY();

    -- Exhibits
    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Rimska amfora', 'Keramička amfora za prijevoz vina iz 2. st. n.e.', 150, @idKol1, @idAM);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Grčka crvenofiguralna vaza', 'Vaza s prikazom olimpijskih igara iz 5. st. p.n.e.', -450, @idKol1, @idAM);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Secesijska vitrina', 'Drvena vitrina s intarzijama iz razdoblja bečke secesije.', 1905, NULL, @idMUO);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Art Nouveau stolica', 'Stolica Henryja van de Veldea, primjer funkcionalnog dizajna.', 1902, @idKol2, @idMUO);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Mona Lisa', 'Uljana slika Leonarda da Vincija, najpoznatije djelo na svijetu.', 1503, @idKol2, @idLouvre);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Venera Milska', 'Mramorna skulptura iz helenističkog razdoblja, oko 100. g. p.n.e.', -100, @idKol1, @idLouvre);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Pietà', 'Mramorna skulptura Michelangela Buonarrotija.', 1499, @idKol3, @idVatikan);

    INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
    VALUES ('Laokoont i sinovi', 'Helenistička skulpturna grupa iz 1. st. p.n.e.', -50, @idKol3, @idVatikan);

    -- Curator collections
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol1, @idKur2);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur4);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol3, @idKur3);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur1);
END
