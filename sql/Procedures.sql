-- ============================================================
-- COUNTRY
-- ============================================================

CREATE OR ALTER PROCEDURE usp_Country_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDCountry, Name FROM Country ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Country_GetByID
    @IDCountry INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDCountry, Name FROM Country WHERE IDCountry = @IDCountry;
END;
GO

CREATE OR ALTER PROCEDURE usp_Country_Insert
    @Name  NVARCHAR(100),
    @NewID INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO Country (Name) VALUES (@Name);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_Country_Update
    @IDCountry INT,
    @Name      NVARCHAR(100)
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE Country SET Name = @Name WHERE IDCountry = @IDCountry;
END;
GO

CREATE OR ALTER PROCEDURE usp_Country_Delete
    @IDCountry INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM Country WHERE IDCountry = @IDCountry;
END;
GO


-- ============================================================
-- CITY
-- ============================================================

CREATE OR ALTER PROCEDURE usp_City_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT c.IDCity, c.Name, c.CountryID, co.Name AS CountryName
FROM City c
         INNER JOIN Country co ON co.IDCountry = c.CountryID
ORDER BY c.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_City_GetByID
    @IDCity INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT c.IDCity, c.Name, c.CountryID, co.Name AS CountryName
FROM City c
         INNER JOIN Country co ON co.IDCountry = c.CountryID
WHERE c.IDCity = @IDCity;
END;
GO

CREATE OR ALTER PROCEDURE usp_City_GetByCountry
    @CountryID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDCity, Name, CountryID FROM City WHERE CountryID = @CountryID ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_City_Insert
    @Name      NVARCHAR(100),
    @CountryID INT,
    @NewID     INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO City (Name, CountryID) VALUES (@Name, @CountryID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_City_Update
    @IDCity    INT,
    @Name      NVARCHAR(100),
    @CountryID INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE City SET Name = @Name, CountryID = @CountryID WHERE IDCity = @IDCity;
END;
GO

CREATE OR ALTER PROCEDURE usp_City_Delete
    @IDCity INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM City WHERE IDCity = @IDCity;
END;
GO


-- ============================================================
-- MUSEUM
-- ============================================================

CREATE OR ALTER PROCEDURE usp_Museum_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT m.IDMuseum, m.Name, m.Adress, m.EstablishmentYear,
       m.Description, m.ImagePath, m.CityID,
       c.Name AS CityName, co.IDCountry, co.Name AS CountryName
FROM Museum m
         INNER JOIN City    c  ON c.IDCity     = m.CityID
         INNER JOIN Country co ON co.IDCountry = c.CountryID
ORDER BY m.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Museum_GetByID
    @IDMuseum INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT m.IDMuseum, m.Name, m.Adress, m.EstablishmentYear,
       m.Description, m.ImagePath, m.CityID,
       c.Name AS CityName, co.IDCountry, co.Name AS CountryName
FROM Museum m
         INNER JOIN City    c  ON c.IDCity     = m.CityID
         INNER JOIN Country co ON co.IDCountry = c.CountryID
WHERE m.IDMuseum = @IDMuseum;
END;
GO

CREATE OR ALTER PROCEDURE usp_Museum_GetByCity
    @CityID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDMuseum, Name, Adress, EstablishmentYear, Description, ImagePath, CityID
FROM Museum WHERE CityID = @CityID ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Museum_Insert
    @Name              NVARCHAR(200),
    @Adress            NVARCHAR(300) = NULL,
    @EstablishmentYear INT           = NULL,
    @Description       NVARCHAR(MAX) = NULL,
    @ImagePath         NVARCHAR(500) = NULL,
    @CityID            INT,
    @NewID             INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
VALUES (@Name, @Adress, @EstablishmentYear, @Description, @ImagePath, @CityID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_Museum_Update
    @IDMuseum          INT,
    @Name              NVARCHAR(200),
    @Adress            NVARCHAR(300) = NULL,
    @EstablishmentYear INT           = NULL,
    @Description       NVARCHAR(MAX) = NULL,
    @ImagePath         NVARCHAR(500) = NULL,
    @CityID            INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE Museum
SET Name = @Name, Adress = @Adress, EstablishmentYear = @EstablishmentYear,
    Description = @Description, ImagePath = @ImagePath, CityID = @CityID
WHERE IDMuseum = @IDMuseum;
END;
GO

CREATE OR ALTER PROCEDURE usp_Museum_Delete
    @IDMuseum INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM Museum WHERE IDMuseum = @IDMuseum;
END;
GO


-- ============================================================
-- MUSEUMCOLLECTION
-- ============================================================

CREATE OR ALTER PROCEDURE usp_MuseumCollection_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDMuseumCollection, Name FROM MuseumCollection ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_MuseumCollection_GetByID
    @IDMuseumCollection INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDMuseumCollection, Name FROM MuseumCollection WHERE IDMuseumCollection = @IDMuseumCollection;
END;
GO

CREATE OR ALTER PROCEDURE usp_MuseumCollection_Insert
    @Name  NVARCHAR(200),
    @NewID INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO MuseumCollection (Name) VALUES (@Name);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_MuseumCollection_Update
    @IDMuseumCollection INT,
    @Name               NVARCHAR(200)
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE MuseumCollection SET Name = @Name WHERE IDMuseumCollection = @IDMuseumCollection;
END;
GO

CREATE OR ALTER PROCEDURE usp_MuseumCollection_Delete
    @IDMuseumCollection INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM MuseumCollection WHERE IDMuseumCollection = @IDMuseumCollection;
END;
GO


-- ============================================================
-- EXHIBIT
-- ============================================================

CREATE OR ALTER PROCEDURE usp_Exhibit_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT e.IDExhibit, e.Name, e.Description, e.CreationYear,
       e.MuseumCollectionID, mc.Name AS MuseumCollectionName,
       e.MuseumID, m.Name AS MuseumName
FROM Exhibit e
         LEFT  JOIN MuseumCollection mc ON mc.IDMuseumCollection = e.MuseumCollectionID
         INNER JOIN Museum           m  ON m.IDMuseum            = e.MuseumID
ORDER BY e.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_GetByID
    @IDExhibit INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT e.IDExhibit, e.Name, e.Description, e.CreationYear,
       e.MuseumCollectionID, mc.Name AS MuseumCollectionName,
       e.MuseumID, m.Name AS MuseumName
FROM Exhibit e
         LEFT  JOIN MuseumCollection mc ON mc.IDMuseumCollection = e.MuseumCollectionID
         INNER JOIN Museum           m  ON m.IDMuseum            = e.MuseumID
WHERE e.IDExhibit = @IDExhibit;
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_GetByMuseum
    @MuseumID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT e.IDExhibit, e.Name, e.Description, e.CreationYear,
       e.MuseumCollectionID, mc.Name AS MuseumCollectionName, e.MuseumID
FROM Exhibit e
         LEFT JOIN MuseumCollection mc ON mc.IDMuseumCollection = e.MuseumCollectionID
WHERE e.MuseumID = @MuseumID ORDER BY e.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_GetByMuseumCollection
    @MuseumCollectionID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDExhibit, Name, Description, CreationYear, MuseumCollectionID, MuseumID
FROM Exhibit WHERE MuseumCollectionID = @MuseumCollectionID ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_Insert
    @Name               NVARCHAR(200),
    @Description        NVARCHAR(MAX) = NULL,
    @CreationYear       INT           = NULL,
    @MuseumCollectionID INT           = NULL,
    @MuseumID           INT,
    @NewID              INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO Exhibit (Name, Description, CreationYear, MuseumCollectionID, MuseumID)
VALUES (@Name, @Description, @CreationYear, @MuseumCollectionID, @MuseumID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_Update
    @IDExhibit          INT,
    @Name               NVARCHAR(200),
    @Description        NVARCHAR(MAX) = NULL,
    @CreationYear       INT           = NULL,
    @MuseumCollectionID INT           = NULL,
    @MuseumID           INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE Exhibit
SET Name = @Name, Description = @Description, CreationYear = @CreationYear,
    MuseumCollectionID = @MuseumCollectionID, MuseumID = @MuseumID
WHERE IDExhibit = @IDExhibit;
END;
GO

CREATE OR ALTER PROCEDURE usp_Exhibit_Delete
    @IDExhibit INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM Exhibit WHERE IDExhibit = @IDExhibit;
END;
GO


-- ============================================================
-- IMAGEPATH
-- ============================================================

CREATE OR ALTER PROCEDURE usp_ImagePath_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDImagePath, ImagePath, ExhibitID FROM ImagePath ORDER BY ExhibitID;
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_GetByID
    @IDImagePath INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDImagePath, ImagePath, ExhibitID FROM ImagePath WHERE IDImagePath = @IDImagePath;
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_GetByExhibit
    @ExhibitID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDImagePath, ImagePath, ExhibitID FROM ImagePath WHERE ExhibitID = @ExhibitID;
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_Insert
    @ImagePath NVARCHAR(500),
    @ExhibitID INT,
    @NewID     INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO ImagePath (ImagePath, ExhibitID) VALUES (@ImagePath, @ExhibitID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_Update
    @IDImagePath INT,
    @ImagePath   NVARCHAR(500),
    @ExhibitID   INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE ImagePath SET ImagePath = @ImagePath, ExhibitID = @ExhibitID WHERE IDImagePath = @IDImagePath;
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_Delete
    @IDImagePath INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM ImagePath WHERE IDImagePath = @IDImagePath;
END;
GO

CREATE OR ALTER PROCEDURE usp_ImagePath_DeleteByExhibit
    @ExhibitID INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM ImagePath WHERE ExhibitID = @ExhibitID;
END;
GO


-- ============================================================
-- CURATOR
-- ============================================================

CREATE OR ALTER PROCEDURE usp_Curator_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT cu.IDCurator, cu.FirstName, cu.LastName, cu.MuseumID, m.Name AS MuseumName
FROM Curator cu
         INNER JOIN Museum m ON m.IDMuseum = cu.MuseumID
ORDER BY cu.LastName, cu.FirstName;
END;
GO

CREATE OR ALTER PROCEDURE usp_Curator_GetByID
    @IDCurator INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT cu.IDCurator, cu.FirstName, cu.LastName, cu.MuseumID, m.Name AS MuseumName
FROM Curator cu
         INNER JOIN Museum m ON m.IDMuseum = cu.MuseumID
WHERE cu.IDCurator = @IDCurator;
END;
GO

CREATE OR ALTER PROCEDURE usp_Curator_GetByMuseum
    @MuseumID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDCurator, FirstName, LastName, MuseumID
FROM Curator WHERE MuseumID = @MuseumID ORDER BY LastName, FirstName;
END;
GO

CREATE OR ALTER PROCEDURE usp_Curator_Insert
    @FirstName NVARCHAR(100),
    @LastName  NVARCHAR(100),
    @MuseumID  INT,
    @NewID     INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES (@FirstName, @LastName, @MuseumID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_Curator_Update
    @IDCurator INT,
    @FirstName NVARCHAR(100),
    @LastName  NVARCHAR(100),
    @MuseumID  INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE Curator SET FirstName = @FirstName, LastName = @LastName, MuseumID = @MuseumID
WHERE IDCurator = @IDCurator;
END;
GO

CREATE OR ALTER PROCEDURE usp_Curator_Delete
    @IDCurator INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM Curator WHERE IDCurator = @IDCurator;
END;
GO


-- ============================================================
-- CURATORCOLLECTION  (vezna tablica)
-- ============================================================

CREATE OR ALTER PROCEDURE usp_CuratorCollection_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT cc.IDCuratorCollection, cc.MuseumCollectionID, cc.CuratorID,
       mc.Name AS MuseumCollectionName, cu.FirstName, cu.LastName
FROM CuratorCollection cc
         INNER JOIN MuseumCollection mc ON mc.IDMuseumCollection = cc.MuseumCollectionID
         INNER JOIN Curator          cu ON cu.IDCurator          = cc.CuratorID
ORDER BY cu.LastName, mc.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_CuratorCollection_GetByID
    @IDCuratorCollection INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT cc.IDCuratorCollection, cc.MuseumCollectionID, cc.CuratorID,
       mc.Name AS MuseumCollectionName, cu.FirstName, cu.LastName
FROM CuratorCollection cc
         INNER JOIN MuseumCollection mc ON mc.IDMuseumCollection = cc.MuseumCollectionID
         INNER JOIN Curator          cu ON cu.IDCurator          = cc.CuratorID
WHERE cc.IDCuratorCollection = @IDCuratorCollection;
END;
GO

CREATE OR ALTER PROCEDURE usp_CuratorCollection_GetByCurator
    @CuratorID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT cc.IDCuratorCollection, cc.MuseumCollectionID, cc.CuratorID,
       mc.Name AS MuseumCollectionName
FROM CuratorCollection cc
         INNER JOIN MuseumCollection mc ON mc.IDMuseumCollection = cc.MuseumCollectionID
WHERE cc.CuratorID = @CuratorID ORDER BY mc.Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_CuratorCollection_Insert
    @MuseumCollectionID INT,
    @CuratorID          INT,
    @NewID              INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (
        SELECT 1 FROM CuratorCollection
        WHERE MuseumCollectionID = @MuseumCollectionID AND CuratorID = @CuratorID
    )
BEGIN
INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID)
VALUES (@MuseumCollectionID, @CuratorID);
SET @NewID = SCOPE_IDENTITY();
END
ELSE
BEGIN
SELECT @NewID = IDCuratorCollection FROM CuratorCollection
WHERE MuseumCollectionID = @MuseumCollectionID AND CuratorID = @CuratorID;
END;
END;
GO

CREATE OR ALTER PROCEDURE usp_CuratorCollection_Delete
    @IDCuratorCollection INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM CuratorCollection WHERE IDCuratorCollection = @IDCuratorCollection;
END;
GO

CREATE OR ALTER PROCEDURE usp_CuratorCollection_DeleteLink
    @CuratorID          INT,
    @MuseumCollectionID INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM CuratorCollection
WHERE CuratorID = @CuratorID AND MuseumCollectionID = @MuseumCollectionID;
END;
GO


-- ============================================================
-- ROLE
-- ============================================================

CREATE OR ALTER PROCEDURE usp_Role_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDRole, Name FROM Role ORDER BY Name;
END;
GO

CREATE OR ALTER PROCEDURE usp_Role_InitRoles
    AS
BEGIN
    SET NOCOUNT ON;
    IF NOT EXISTS (SELECT 1 FROM Role WHERE Name = 'ADMINISTRATOR')
        INSERT INTO Role (Name) VALUES ('ADMINISTRATOR');

    IF NOT EXISTS (SELECT 1 FROM Role WHERE Name = 'USER')
        INSERT INTO Role (Name) VALUES ('USER');
END;
GO


-- ============================================================
-- USER
-- ============================================================

CREATE OR ALTER PROCEDURE usp_User_GetAll
    AS
BEGIN
    SET NOCOUNT ON;
SELECT u.IDUser, u.Username, u.Email, u.FirstName, u.LastName,
       u.Password, u.RoleID, r.Name AS RoleName
FROM [User] u
    INNER JOIN Role r ON r.IDRole = u.RoleID
ORDER BY u.Username;
END;
GO

CREATE OR ALTER PROCEDURE usp_User_GetByID
    @IDUser INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT u.IDUser, u.Username, u.Email, u.FirstName, u.LastName,
       u.Password, u.RoleID, r.Name AS RoleName
FROM [User] u
    INNER JOIN Role r ON r.IDRole = u.RoleID
WHERE u.IDUser = @IDUser;
END;
GO

CREATE OR ALTER PROCEDURE usp_User_GetByUsername
    @Username NVARCHAR(100)
    AS
BEGIN
    SET NOCOUNT ON;
SELECT u.IDUser, u.Username, u.Email, u.FirstName, u.LastName,
       u.Password, u.RoleID, r.Name AS RoleName
FROM [User] u
    INNER JOIN Role r ON r.IDRole = u.RoleID
WHERE u.Username = @Username;
END;
GO

CREATE OR ALTER PROCEDURE usp_User_GetByEmail
    @Email NVARCHAR(200)
    AS
BEGIN
    SET NOCOUNT ON;
SELECT u.IDUser, u.Username, u.Email, u.FirstName, u.LastName,
       u.Password, u.RoleID, r.Name AS RoleName
FROM [User] u
    INNER JOIN Role r ON r.IDRole = u.RoleID
WHERE u.Email = @Email;
END;
GO

CREATE OR ALTER PROCEDURE usp_User_GetByRole
    @RoleID INT
    AS
BEGIN
    SET NOCOUNT ON;
SELECT IDUser, Username, Email, FirstName, LastName, Password, RoleID
FROM [User] WHERE RoleID = @RoleID ORDER BY Username;
END;
GO

-- Insert
CREATE OR ALTER PROCEDURE usp_User_Insert
    @Username  NVARCHAR(100),
    @Email     NVARCHAR(200),
    @FirstName NVARCHAR(100) = NULL,
    @LastName  NVARCHAR(100) = NULL,
    @Password  NVARCHAR(500),
    @RoleID    INT,
    @NewID     INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;
INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID)
VALUES (@Username, @Email, @FirstName, @LastName, @Password, @RoleID);
SET @NewID = SCOPE_IDENTITY();
END;
GO

CREATE OR ALTER PROCEDURE usp_User_Update
    @IDUser    INT,
    @Username  NVARCHAR(100),
    @Email     NVARCHAR(200),
    @FirstName NVARCHAR(100) = NULL,
    @LastName  NVARCHAR(100) = NULL,
    @RoleID    INT
    AS
BEGIN
    SET NOCOUNT ON;
UPDATE [User]
SET Username = @Username, Email = @Email,
    FirstName = @FirstName, LastName = @LastName, RoleID = @RoleID
WHERE IDUser = @IDUser;
END;
GO

-- Delete
CREATE OR ALTER PROCEDURE usp_User_Delete
    @IDUser INT
    AS
BEGIN
    SET NOCOUNT ON;
DELETE FROM [User] WHERE IDUser = @IDUser;
END;
GO


CREATE OR ALTER PROCEDURE usp_User_CreateAdmin
    @Username  NVARCHAR(100),
    @Email     NVARCHAR(200),
    @FirstName NVARCHAR(100) = NULL,
    @LastName  NVARCHAR(100) = NULL,
    @Password  NVARCHAR(500),
    @NewID     INT OUTPUT
    AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AdminRoleID INT;
SELECT @AdminRoleID = IDRole FROM Role WHERE Name = 'ADMINISTRATOR';

IF @AdminRoleID IS NULL
BEGIN
        RAISERROR('Nema role administratora!', 16, 1);
        RETURN;
END;

INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID)
VALUES (@Username, @Email, @FirstName, @LastName, @Password, @AdminRoleID);

SET @NewID = SCOPE_IDENTITY();
END;
GO