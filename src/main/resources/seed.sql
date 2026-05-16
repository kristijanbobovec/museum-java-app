IF NOT EXISTS (SELECT 1 FROM Country)
BEGIN
    DECLARE @idHrvatska  INT, @idItalija    INT, @idFrancuska  INT;
    DECLARE @idUK        INT, @idSAD        INT, @idNizozemska INT;

    DECLARE @idZagreb    INT, @idSplit      INT, @idRim        INT;
    DECLARE @idParis     INT, @idLondon     INT, @idNewYork    INT;
    DECLARE @idFirenza   INT, @idAmsterdam  INT;

    DECLARE @idMUO       INT, @idAM         INT, @idVatikan    INT, @idLouvre     INT;
    DECLARE @idBritish   INT, @idMet        INT, @idUffizi     INT, @idRijks      INT;

    DECLARE @idKol1      INT, @idKol2       INT, @idKol3       INT, @idKol4       INT;
    DECLARE @idKur1      INT, @idKur2       INT, @idKur3       INT, @idKur4       INT;

    -- Countries
    INSERT INTO Country (Name) VALUES ('Hrvatska');               SET @idHrvatska   = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Italija');                SET @idItalija    = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Francuska');              SET @idFrancuska  = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Ujedinjeno Kraljevstvo'); SET @idUK         = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('SAD');                    SET @idSAD        = SCOPE_IDENTITY();
    INSERT INTO Country (Name) VALUES ('Nizozemska');             SET @idNizozemska = SCOPE_IDENTITY();

    -- Cities
    INSERT INTO City (Name, CountryID) VALUES ('Zagreb',    @idHrvatska);   SET @idZagreb    = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Split',     @idHrvatska);   SET @idSplit     = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Rim',       @idItalija);    SET @idRim       = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Firenza',   @idItalija);    SET @idFirenza   = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Pariz',     @idFrancuska);  SET @idParis     = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('London',    @idUK);         SET @idLondon    = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('New York',  @idSAD);        SET @idNewYork   = SCOPE_IDENTITY();
    INSERT INTO City (Name, CountryID) VALUES ('Amsterdam', @idNizozemska); SET @idAmsterdam = SCOPE_IDENTITY();

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
            'Najveći muzej na svijetu s više od 35 000 izloženih predmeta.',
            '/assets/images/louvre.jpg', @idParis);
    SET @idLouvre = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('British Museum', 'Great Russell St, London WC1B 3DG', 1753,
            'Jedan od najvećih i najstarijih muzeja na svijetu, posvećen povijesti i kulturi čovječanstva.',
            '/assets/images/british_museum.jpg', @idLondon);
    SET @idBritish = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('The Metropolitan Museum of Art', '1000 Fifth Ave, New York', 1870,
            'Najveći muzej umjetnosti u SAD-u s više od 2 milijuna predmeta koji pokrivaju 5 000 godina svjetske kulture.',
            '/assets/images/metropolitan.jpg', @idNewYork);
    SET @idMet = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Galerija Uffizi', 'Piazzale degli Uffizi 6, Firenza', 1769,
            'Jedan od najstarijih i najvažnijih muzeja na svijetu s iznimnom zbirkom renesansnih majstora.',
            '/assets/images/uffizi.jpg', @idFirenza);
    SET @idUffizi = SCOPE_IDENTITY();

    INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
    VALUES ('Rijksmuseum', 'Museumstraat 1, Amsterdam', 1800,
            'Nizozemski nacionalni muzej posvećen umjetnosti i povijesti. Čuva najveću zbirku slika zlatnog doba Nizozemske.',
            '/assets/images/rijksmuseum.jpg', @idAmsterdam);
    SET @idRijks = SCOPE_IDENTITY();

    -- Museum collections
    INSERT INTO MuseumCollection (Name) VALUES ('Antička zbirka');              SET @idKol1 = SCOPE_IDENTITY();
    INSERT INTO MuseumCollection (Name) VALUES ('Moderna umjetnost');           SET @idKol2 = SCOPE_IDENTITY();
    INSERT INTO MuseumCollection (Name) VALUES ('Renesansna skulptura');        SET @idKol3 = SCOPE_IDENTITY();
    INSERT INTO MuseumCollection (Name) VALUES ('Namještaj i dekorativna art'); SET @idKol4 = SCOPE_IDENTITY();

    -- Curators
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ivan',   'Horvat', @idMUO);     SET @idKur1 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ana',    'Kovač',  @idAM);      SET @idKur2 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Marco',  'Rossi',  @idVatikan); SET @idKur3 = SCOPE_IDENTITY();
    INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Sophie', 'Martin', @idLouvre);  SET @idKur4 = SCOPE_IDENTITY();

    -- Exhibits
    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Rimska amfora', 'Keramička amfora za prijevoz vina iz 2. st. n.e.', 150, NULL, @idKol1, @idAM);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Grčka crvenofiguralna vaza', 'Vaza s prikazom olimpijskih igara iz 5. st. p.n.e.', -450, NULL, @idKol1, @idAM);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Secesijska vitrina', 'Drvena vitrina s intarzijama iz razdoblja bečke secesije.', 1905, NULL, @idKol4, @idMUO);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Art Nouveau stolica', 'Stolica Henryja van de Veldea, primjer funkcionalnog dizajna.', 1902, NULL, @idKol4, @idMUO);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Secesijski ormar s intarzijama', 'Ormar od orahovine ukrašen intarzijama, Beč oko 1900.', 1900, NULL, @idKol4, @idMUO);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Mona Lisa', 'Uljana slika Leonarda da Vincija, najpoznatije djelo na svijetu.', 1503, '/assets/images/mona_lisa.png', @idKol2, @idLouvre);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Venera Milska', 'Mramorna skulptura iz helenističkog razdoblja, oko 100. g. p.n.e.', -100, NULL, @idKol1, @idLouvre);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Sloboda predvodi narod', 'Uljana slika Eugènea Delacroixa iz 1830. godine.', 1830, NULL, @idKol2, @idLouvre);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Pietà', 'Mramorna skulptura Michelangela Buonarrotija.', 1499, NULL, @idKol3, @idVatikan);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Laokoont i sinovi', 'Helenistička skulpturna grupa iz 1. st. p.n.e.', -50, NULL, @idKol3, @idVatikan);

    INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
    VALUES ('Karta Rimskog Carstva', 'Detaljna mozaična karta iz 4. st. n.e., pronađena u Rimu.', 350, NULL, @idKol1, @idVatikan);

    -- Curator collections
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol1, @idKur2);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur4);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol3, @idKur3);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol4, @idKur1);
    INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur1);
END
