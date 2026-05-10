USE museum;
GO

-- Roles
INSERT INTO Role (Name) VALUES
    ('admin'),
    ('user');
GO

-- Countries
INSERT INTO Country (Name) VALUES
    ('Croatia'),
    ('France'),
    ('Italy'),
    ('United Kingdom'),
    ('United States');
GO

-- Cities
INSERT INTO City (Name, CountryID) VALUES
    ('Zagreb',   1),
    ('Split',    1),
    ('Paris',    2),
    ('Rome',     3),
    ('London',   4),
    ('New York', 5);
GO

-- Museums
INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID) VALUES
    ('Muzej za umjetnost i obrt',   'Trg Republike Hrvatske 10, Zagreb',  1880, 'Najveći muzej primijenjene umjetnosti u Hrvatskoj.',                        'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Museum_of_Arts_and_Crafts_Zagreb.jpg/1280px-Museum_of_Arts_and_Crafts_Zagreb.jpg', 1),
    ('Hrvatski povijesni muzej',    'Matoševa ul. 9, Zagreb',             1846, 'Čuva predmete vezane uz hrvatsku povijest.',                                'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Hrvatski_povijesni_muzej.jpg/1280px-Hrvatski_povijesni_muzej.jpg',               1),
    ('Muzej grada Splita',          'Papalićeva ul. 1, Split',            1946, 'Smješten u Dioklecijanovoj palači, prikazuje povijest Splita.',             'https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Dioklecijanova_palaca_-_pogled.jpg/1280px-Dioklecijanova_palaca_-_pogled.jpg', 2),
    ('Louvre',                      'Rue de Rivoli, Paris',               1793, 'Jedan od najvećih i najposjećenijih muzeja na svijetu.',                    'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Louvre_I_M_Pei_Pyramid.jpg/1280px-Louvre_I_M_Pei_Pyramid.jpg',               3),
    ('Musei Vaticani',              'Viale Vaticano, Rome',               1506, 'Muzejski kompleks u Vatikanu s kolekcijom od oko 70 000 predmeta.',          'https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Vatican_Museums_entrance.jpg/1280px-Vatican_Museums_entrance.jpg',           4),
    ('British Museum',              'Great Russell St, London',           1753, 'Posvećen ljudskoj povijesti, umjetnosti i kulturi.',                        'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/British_Museum_from_NE_2.JPG/1280px-British_Museum_from_NE_2.JPG',             5),
    ('Metropolitan Museum of Art',  '1000 Fifth Ave, New York',           1870, 'Jedan od najvećih umjetničkih muzeja na svijetu.',                          'https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Metropolitan_Museum_of_Art_%28The_Met%29_-_Central_Park%2C_NYC.jpg/1280px-Metropolitan_Museum_of_Art_%28The_Met%29_-_Central_Park%2C_NYC.jpg', 6);
GO

-- Museum collections
INSERT INTO MuseumCollection (Name) VALUES
    ('Antička zbirka'),
    ('Zbirka fotografija'),
    ('Impresionizam'),
    ('Egipatske starine'),
    ('Renesansa'),
    ('Moderna umjetnost');
GO

-- Curators
INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES
    ('Ana',      'Kovač',     1),
    ('Marko',    'Horvat',    1),
    ('Ivana',    'Perić',     2),
    ('Jean',     'Dupont',    4),
    ('Marco',    'Rossi',     5),
    ('James',    'Smith',     6),
    ('Emily',    'Johnson',   7);
GO

-- Curator ↔ Collection links
INSERT INTO CuratorCollection (CuratorID, MuseumCollectionID) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 3),
    (5, 5),
    (6, 4),
    (7, 6);
GO

-- Exhibits
INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID) VALUES
    ('Rimska posuda',         'Keramička posuda iz rimskog doba.',              100,  1, 1),
    ('Hrvatska nošnja',       'Tradicijska nošnja iz 19. stoljeća.',            1850, 2, 1),
    ('Fotografija Zagreba',   'Crno-bijela fotografija Gornjeg grada iz 1910.', 1910, 2, 2),
    ('Mona Lisa',             'Leonardova slika iz ranoeg 16. stoljeća.',       1503, 5, 4),
    ('Suncokreti',            'Van Goghovi suncokreti iz 1888. godine.',        1888, 3, 4),
    ('Rosetta Stone',         'Kameni natpis iz 196. pr. Kr.',                 -196, 4, 6),
    ('Michelangelov David',   'Mramorna skulptura iz 1504. godine.',            1504, 5, 5),
    ('Zvjezdana noć',         'Van Goghova Zvjezdana noć iz 1889. godine.',     1889, 6, 7),
    ('Egipatska mumija',      'Mumija iz Starog kraljevstva, oko 2500. pr. Kr.',-2500,4, 6),
    ('Lopoči',                'Monetov ciklus slika vodenih ljiljana.',         1906, 3, 4);
GO

-- Image paths (Wikimedia public domain URLs)
INSERT INTO ImagePath (ImagePath, ExhibitID) VALUES
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg', 4),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Vincent_van_Gogh_-_Sunflowers_%281888%2C_National_Gallery_London%29.jpg/1280px-Vincent_van_Gogh_-_Sunflowers_%281888%2C_National_Gallery_London%29.jpg', 5),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Rosetta_Stone.JPG/800px-Rosetta_Stone.JPG', 6),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/%27David%27_by_Michelangelo_Fir_JBU002.jpg/800px-%27David%27_by_Michelangelo_Fir_JBU002.jpg', 7),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg/1280px-Van_Gogh_-_Starry_Night_-_Google_Art_Project.jpg', 8),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Mummy_of_Katebet.jpg/800px-Mummy_of_Katebet.jpg', 9),
    ('https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Claude_Monet_-_Water_Lilies_-_1906%2C_Ryerson.jpg/1280px-Claude_Monet_-_Water_Lilies_-_1906%2C_Ryerson.jpg', 10);
GO

-- Users (passwords are plain-text placeholders — hash before production use)
INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID) VALUES
    ('admin',    'admin@museum.hr',    'Admin',     'User',    'admin123', 1),
    ('kbobovec', 'kristijan@museum.hr','Kristijan', 'Bobovec', 'pass123',  2),
    ('jdoe',     'jdoe@museum.hr',     'John',      'Doe',     'pass123',  2),
    ('akovac',   'ana@museum.hr',      'Ana',       'Kovač',   'pass123',  2);
GO
