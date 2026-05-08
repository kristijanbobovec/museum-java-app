-- ------------------------------------------------------------
-- 1. Country
-- ------------------------------------------------------------
CREATE TABLE Country (
                         IDCountry   INT           NOT NULL IDENTITY(1,1),
                         Name        NVARCHAR(100) NOT NULL,
                         CONSTRAINT PK_Country PRIMARY KEY (IDCountry)
);

-- ------------------------------------------------------------
-- 2. City
-- ------------------------------------------------------------
CREATE TABLE City (
                      IDCity      INT           NOT NULL IDENTITY(1,1),
                      Name        NVARCHAR(100) NOT NULL,
                      CountryID   INT           NOT NULL,
                      CONSTRAINT PK_City PRIMARY KEY (IDCity),
                      CONSTRAINT FK_City_Country FOREIGN KEY (CountryID)
                          REFERENCES Country (IDCountry)
);

-- ------------------------------------------------------------
-- 3. Museum
-- ------------------------------------------------------------
CREATE TABLE Museum (
                        IDMuseum            INT            NOT NULL IDENTITY(1,1),
                        Name                NVARCHAR(200)  NOT NULL,
                        Adress              NVARCHAR(300)  NULL,
                        EstablishmentYear   INT            NULL,
                        Description         NVARCHAR(MAX)  NULL,
                        ImagePath           NVARCHAR(500)  NULL,
                        CityID              INT            NOT NULL,
                        CONSTRAINT PK_Museum PRIMARY KEY (IDMuseum),
                        CONSTRAINT FK_Museum_City FOREIGN KEY (CityID)
                            REFERENCES City (IDCity)
);

-- ------------------------------------------------------------
-- 4. MuseumCollection
-- ------------------------------------------------------------
CREATE TABLE MuseumCollection (
                                  IDMuseumCollection  INT           NOT NULL IDENTITY(1,1),
                                  Name                NVARCHAR(200) NOT NULL,
                                  CONSTRAINT PK_MuseumCollection PRIMARY KEY (IDMuseumCollection)
);

-- ------------------------------------------------------------
-- 5. Exhibit
-- ------------------------------------------------------------
CREATE TABLE Exhibit (
                         IDExhibit           INT            NOT NULL IDENTITY(1,1),
                         Name                NVARCHAR(200)  NOT NULL,
                         Description         NVARCHAR(MAX)  NULL,
                         CreationYear        INT            NULL,
                         MuseumCollectionID  INT            NULL,
                         MuseumID            INT            NOT NULL,
                         CONSTRAINT PK_Exhibit PRIMARY KEY (IDExhibit),
                         CONSTRAINT FK_Exhibit_MuseumCollection FOREIGN KEY (MuseumCollectionID)
                             REFERENCES MuseumCollection (IDMuseumCollection),
                         CONSTRAINT FK_Exhibit_Museum FOREIGN KEY (MuseumID)
                             REFERENCES Museum (IDMuseum)
);

-- ------------------------------------------------------------
-- 6. ImagePath
-- ------------------------------------------------------------
CREATE TABLE ImagePath (
                           IDImagePath     INT            NOT NULL IDENTITY(1,1),
                           ImagePath       NVARCHAR(500)  NOT NULL,
                           ExhibitID       INT            NOT NULL,
                           CONSTRAINT PK_ImagePath PRIMARY KEY (IDImagePath),
                           CONSTRAINT FK_ImagePath_Exhibit FOREIGN KEY (ExhibitID)
                               REFERENCES Exhibit (IDExhibit)
);

-- ------------------------------------------------------------
-- 7. Curator
-- ------------------------------------------------------------
CREATE TABLE Curator (
                         IDCurator   INT           NOT NULL IDENTITY(1,1),
                         FirstName   NVARCHAR(100) NOT NULL,
                         LastName    NVARCHAR(100) NOT NULL,
                         MuseumID    INT           NOT NULL,
                         CONSTRAINT PK_Curator PRIMARY KEY (IDCurator),
                         CONSTRAINT FK_Curator_Museum FOREIGN KEY (MuseumID)
                             REFERENCES Museum (IDMuseum)
);

-- ------------------------------------------------------------
-- 8. CuratorCollection  (vezna tablica M:N)
-- ------------------------------------------------------------
CREATE TABLE CuratorCollection (
                                   IDCuratorCollection INT NOT NULL IDENTITY(1,1),
                                   MuseumCollectionID  INT NOT NULL,
                                   CuratorID           INT NOT NULL,
                                   CONSTRAINT PK_CuratorCollection PRIMARY KEY (IDCuratorCollection),
                                   CONSTRAINT FK_CuratorCollection_MuseumCollection FOREIGN KEY (MuseumCollectionID)
                                       REFERENCES MuseumCollection (IDMuseumCollection),
                                   CONSTRAINT FK_CuratorCollection_Curator FOREIGN KEY (CuratorID)
                                       REFERENCES Curator (IDCurator),
                                   CONSTRAINT UQ_CuratorCollection UNIQUE (MuseumCollectionID, CuratorID)
);

-- ------------------------------------------------------------
-- 9. Role
-- ------------------------------------------------------------
CREATE TABLE Role (
                      IDRole  INT           NOT NULL IDENTITY(1,1),
                      Name    NVARCHAR(50)  NOT NULL,
                      CONSTRAINT PK_Role PRIMARY KEY (IDRole),
                      CONSTRAINT UQ_Role_Name UNIQUE (Name)
);

-- ------------------------------------------------------------
-- 10. [User]
-- ------------------------------------------------------------
CREATE TABLE [User] (
    IDUser      INT           NOT NULL IDENTITY(1,1),
    Username    NVARCHAR(100) NOT NULL,
    Email       NVARCHAR(200) NOT NULL,
    FirstName   NVARCHAR(100) NULL,
    LastName    NVARCHAR(100) NULL,
    Password    NVARCHAR(500) NOT NULL,
    RoleID      INT           NOT NULL,
    CONSTRAINT PK_User PRIMARY KEY (IDUser),
    CONSTRAINT FK_User_Role     FOREIGN KEY (RoleID) REFERENCES Role (IDRole),
    CONSTRAINT UQ_User_Username UNIQUE (Username),
    CONSTRAINT UQ_User_Email    UNIQUE (Email)
    );