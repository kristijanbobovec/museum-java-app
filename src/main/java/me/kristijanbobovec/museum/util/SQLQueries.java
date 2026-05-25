package me.kristijanbobovec.museum.util;

public class SQLQueries {
    private SQLQueries() {}

    public final static String USE_DATABASE = "USE %s";
    public final static String CREATE_DATABASE = "CREATE DATABASE %s";
    public final static String DROP_DATABASE = """
            ALTER DATABASE %s SET SINGLE_USER WITH ROLLBACK IMMEDIATE
            DROP DATABASE %s
            """;
    public final static String INSERT_ROLE = "{call usp_Role_Insert(?, ?)}";
    public final static String INSERT_USER = "{call usp_User_Insert(?, ?, ?, ?, ?, ?, ?)}";
    public final static String GETALL_USERS = "{call usp_User_GetAll}";
    public final static String GETBYID_USER = "{call usp_User_GetById(?)}";
    public final static String GETBYUSERNAME_USER = "{call usp_User_GetByUsername(?)}";
    public final static String USERNAMEEXISTS_USER = "{call usp_User_UsernameExists(?, ?)}";
    public final static String EMAILEXISTS_USER = "{call usp_User_EmailExists(?, ?)}";
    public final static String UPDATE_USER = "{call usp_User_Update(?, ?, ?, ?, ?)}";
    public final static String DELETE_USER = "{call usp_User_Delete(?)}";
    public final static String GETALL_MUSEUMS             = "{call usp_Museum_GetAll}";
    public final static String GETBYID_MUSEUM             = "{call usp_Museum_GetById(?)}";
    public final static String GETBYID_MUSEUM_WITH_DETAILS = "{call usp_Museum_GetByIdWithDetails(?)}";
    public final static String GETALL_MUSEUMS_WITH_DETAILS       = "{call usp_Museum_GetAllWithDetails}";
    public final static String GETALL_MUSEUMS_FOR_XML_EXPORT     = "{call usp_Museum_GetAllForXMLExport}";
    public final static String INSERT_MUSEUM              = "{call usp_Museum_Insert(?, ?, ?, ?, ?, ?, ?)}";
    public final static String UPDATE_MUSEUM              = "{call usp_Museum_Update(?, ?, ?, ?, ?, ?, ?)}";
    public final static String DELETE_MUSEUM              = "{call usp_Museum_Delete(?)}";
    public final static String GETALL_EXHIBITS             = "{call usp_Exhibit_GetAll}";
    public final static String GETBYID_EXHIBIT             = "{call usp_Exhibit_GetById(?)}";
    public final static String GETBYMUSEUMID_EXHIBIT       = "{call usp_Exhibit_GetByMuseumId(?)}";
    public final static String INSERT_EXHIBIT              = "{call usp_Exhibit_Insert(?, ?, ?, ?, ?, ?, ?)}";
    public final static String UPDATE_EXHIBIT              = "{call usp_Exhibit_Update(?, ?, ?, ?, ?, ?, ?)}";
    public final static String DELETE_EXHIBIT              = "{call usp_Exhibit_Delete(?)}";
    public final static String GETALL_MUSEUMCOLLECTIONS             = "{call usp_MuseumCollection_GetAll}";
    public final static String GETBYID_MUSEUMCOLLECTION             = "{call usp_MuseumCollection_GetById(?)}";
    public final static String GETBYMUSEUMID_MUSEUMCOLLECTION       = "{call usp_MuseumCollection_GetByMuseumId(?)}";
    public final static String INSERT_MUSEUMCOLLECTION              = "{call usp_MuseumCollection_Insert(?, ?)}";
    public final static String UPDATE_MUSEUMCOLLECTION              = "{call usp_MuseumCollection_Update(?, ?)}";
    public final static String DELETE_MUSEUMCOLLECTION              = "{call usp_MuseumCollection_Delete(?)}";
    public final static String GETALL_COUNTRIES  = "{call usp_Country_GetAll}";
    public final static String GETBYID_COUNTRY   = "{call usp_Country_GetById(?)}";
    public final static String INSERT_COUNTRY    = "{call usp_Country_Insert(?, ?)}";
    public final static String UPDATE_COUNTRY    = "{call usp_Country_Update(?, ?)}";
    public final static String DELETE_COUNTRY    = "{call usp_Country_Delete(?)}";
    public final static String GETALL_CITIES                = "{call usp_City_GetAll}";
    public final static String GETBYID_CITY                 = "{call usp_City_GetById(?)}";
    public final static String GETALL_CITIES_WITH_DETAILS   = "{call usp_City_GetAllWithDetails}";
    public final static String GETBYID_CITY_WITH_DETAILS    = "{call usp_City_GetByIdWithDetails(?)}";
    public final static String INSERT_CITY                  = "{call usp_City_Insert(?, ?, ?)}";
    public final static String UPDATE_CITY                  = "{call usp_City_Update(?, ?, ?)}";
    public final static String DELETE_CITY                  = "{call usp_City_Delete(?)}";
    public final static String GETALL_CURATORS                       = "{call usp_Curator_GetAll}";
    public final static String GETBYID_CURATOR                       = "{call usp_Curator_GetById(?)}";
    public final static String GETALL_CURATORS_WITH_DETAILS          = "{call usp_Curator_GetAllWithDetails}";
    public final static String GETBYID_CURATOR_WITH_DETAILS          = "{call usp_Curator_GetByIdWithDetails(?)}";
    public final static String INSERT_CURATOR                        = "{call usp_Curator_Insert(?, ?, ?, ?)}";
    public final static String UPDATE_CURATOR                        = "{call usp_Curator_Update(?, ?, ?, ?)}";
    public final static String DELETE_CURATOR                        = "{call usp_Curator_Delete(?)}";
    public final static String GETALL_CURATOR_COLLECTIONS            = "{call usp_CuratorCollection_GetAll}";
    public final static String GETBYID_CURATOR_COLLECTIONS           = "{call usp_CuratorCollection_GetByCuratorId(?)}";
    public final static String INSERT_CURATOR_COLLECTION             = "{call usp_CuratorCollection_Insert(?, ?)}";
    public final static String DELETE_CURATOR_COLLECTION             = "{call usp_CuratorCollection_Delete(?, ?)}";
    public final static String DELETE_CURATOR_COLLECTION_BY_CURATOR  = "{call usp_CuratorCollection_DeleteByCuratorId(?)}";
    public final static String IMAGE_USAGE_COUNT                     = "{call usp_ImagePath_GetUsageCount(?, ?)}";
    public final static String DELETE_ALL_DATA                       = "{call usp_DeleteAllData}";
    public static final String PROCEDURES = """
        CREATE OR ALTER PROCEDURE usp_User_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDUser,
                Username,
                Email,
                FirstName,
                LastName,
                Password,
                RoleID
            FROM [User];
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_GetById
            @IDUser INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDUser,
                Username,
                Email,
                FirstName,
                LastName,
                Password,
                RoleID
            FROM [User]
            WHERE IDUser = @IDUser;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_GetByUsername
            @Username NVARCHAR(100)
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDUser,
                Username,
                Email,
                FirstName,
                LastName,
                Password,
                RoleID
            FROM [User]
            WHERE Username = @Username;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_UsernameExists
            @Username   NVARCHAR(100),
            @Exists     BIT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            IF EXISTS (SELECT 1 FROM [User] WHERE Username = @Username)
                SET @Exists = 1;
            ELSE
                SET @Exists = 0;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_EmailExists
            @Email  NVARCHAR(200),
            @Exists BIT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            IF EXISTS (SELECT 1 FROM [User] WHERE Email = @Email)
                SET @Exists = 1;
            ELSE
                SET @Exists = 0;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Role_Insert
            @IDRole INT,
            @Name   NVARCHAR(50)
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Role (IDRole, Name)
            VALUES (@IDRole, @Name);
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_Delete
            @IDUser INT
        AS
        BEGIN
            DELETE FROM [User]
            WHERE IDUser = @IDUser;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_Update
            @IDUser     INT,
            @Username   NVARCHAR(100),
            @Email      NVARCHAR(200),
            @FirstName  NVARCHAR(100),
            @LastName   NVARCHAR(100)
        AS
        BEGIN
            UPDATE [User]
            SET
                Username  = @Username,
                Email     = @Email,
                FirstName = @FirstName,
                LastName  = @LastName
            WHERE IDUser = @IDUser;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_User_Insert
            @Username   NVARCHAR(100),
            @Email      NVARCHAR(200),
            @FirstName  NVARCHAR(100),
            @LastName   NVARCHAR(100),
            @Password   NVARCHAR(500),
            @RoleID     INT,
            @IDInserted     INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID)
            VALUES (@Username, @Email, @FirstName, @LastName, @Password, @RoleID);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                m.IDMuseum,
                m.Name,
                m.Adress,
                m.EstablishmentYear,
                m.Description,
                m.ImagePath,
                m.CityID
            FROM Museum  m
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_GetById
            @IDMuseum INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                m.IDMuseum,
                m.Name,
                m.Adress,
                m.EstablishmentYear,
                m.Description,
                m.ImagePath,
                m.CityID
            FROM Museum  m
            WHERE m.IDMuseum = @IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_Insert
            @Name               NVARCHAR(200),
            @Adress             NVARCHAR(300),
            @EstablishmentYear  INT,
            @Description        NVARCHAR(MAX),
            @ImagePath          NVARCHAR(500),
            @CityID             INT,
            @IDInserted         INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
            VALUES (@Name, @Adress, @EstablishmentYear, @Description, @ImagePath, @CityID);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_Update
            @IDMuseum           INT,
            @Name               NVARCHAR(200),
            @Adress             NVARCHAR(300),
            @EstablishmentYear  INT,
            @Description        NVARCHAR(MAX),
            @ImagePath          NVARCHAR(500),
            @CityID             INT
        AS
        BEGIN
            UPDATE Museum
            SET
                Name              = @Name,
                Adress            = @Adress,
                EstablishmentYear = @EstablishmentYear,
                Description       = @Description,
                ImagePath         = @ImagePath,
                CityID            = @CityID
            WHERE IDMuseum = @IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_Delete
            @IDMuseum INT
        AS
        BEGIN
            DELETE FROM CuratorCollection WHERE CuratorID IN (SELECT IDCurator FROM Curator WHERE MuseumID = @IDMuseum);
            DELETE FROM Curator            WHERE MuseumID = @IDMuseum;
            DELETE FROM Exhibit            WHERE MuseumID = @IDMuseum;
            DELETE FROM Museum             WHERE IDMuseum = @IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_GetByIdWithDetails
            @IDMuseum INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                m.IDMuseum,
                m.Name,
                m.Adress,
                m.EstablishmentYear,
                m.Description,
                m.ImagePath,
                m.CityID,
                c.IDCity,
                c.Name          AS CityName,
                co.IDCountry,
                co.Name         AS CountryName
            FROM Museum m
            INNER JOIN City    c  ON m.CityID    = c.IDCity
            INNER JOIN Country co ON c.CountryID = co.IDCountry
            WHERE m.IDMuseum = @IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_GetAllWithDetails
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                m.IDMuseum,
                m.Name,
                m.Adress,
                m.EstablishmentYear,
                m.Description,
                m.ImagePath,
                m.CityID,
                c.IDCity,
                c.Name          AS CityName,
                co.IDCountry,
                co.Name         AS CountryName
            FROM Museum m
            INNER JOIN City    c  ON m.CityID    = c.IDCity
            INNER JOIN Country co ON c.CountryID = co.IDCountry;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                e.IDExhibit,
                e.Name,
                e.Description,
                e.CreationYear,
                e.ImagePath,
                e.MuseumCollectionID,
                mc.Name             AS CollectionName,
                m.IDMuseum,
                m.Name              AS MuseumName,
                m.Adress            AS MuseumAdress,
                m.EstablishmentYear,
                m.Description       AS MuseumDescription,
                m.ImagePath         AS MuseumImagePath,
                m.CityID
            FROM Exhibit e
            INNER JOIN Museum           m   ON e.MuseumID           = m.IDMuseum
            LEFT  JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_GetById
            @IDExhibit INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                e.IDExhibit,
                e.Name,
                e.Description,
                e.CreationYear,
                e.ImagePath,
                e.MuseumCollectionID,
                mc.Name             AS CollectionName,
                m.IDMuseum,
                m.Name              AS MuseumName,
                m.Adress            AS MuseumAdress,
                m.EstablishmentYear,
                m.Description       AS MuseumDescription,
                m.ImagePath         AS MuseumImagePath,
                m.CityID
            FROM Exhibit e
            INNER JOIN Museum           m   ON e.MuseumID           = m.IDMuseum
            LEFT  JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
            WHERE e.IDExhibit = @IDExhibit
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_GetByMuseumId
            @MuseumID INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                e.IDExhibit,
                e.Name,
                e.Description,
                e.CreationYear,
                e.ImagePath,
                e.MuseumCollectionID,
                mc.Name             AS CollectionName,
                m.IDMuseum,
                m.Name              AS MuseumName,
                m.Adress            AS MuseumAdress,
                m.EstablishmentYear,
                m.Description       AS MuseumDescription,
                m.ImagePath         AS MuseumImagePath,
                m.CityID
            FROM Exhibit e
            INNER JOIN Museum           m   ON e.MuseumID           = m.IDMuseum
            LEFT  JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
            WHERE e.MuseumID = @MuseumID
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_Insert
            @Name               NVARCHAR(200),
            @Description        NVARCHAR(MAX),
            @CreationYear       INT,
            @ImagePath          NVARCHAR(500),
            @MuseumCollectionID INT,
            @MuseumID           INT,
            @IDInserted         INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
            VALUES (@Name, @Description, @CreationYear, @ImagePath, @MuseumCollectionID, @MuseumID);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_Update
            @IDExhibit          INT,
            @Name               NVARCHAR(200),
            @Description        NVARCHAR(MAX),
            @CreationYear       INT,
            @ImagePath          NVARCHAR(500),
            @MuseumCollectionID INT,
            @MuseumID           INT
        AS
        BEGIN
            UPDATE Exhibit
            SET
                Name                = @Name,
                Description         = @Description,
                CreationYear        = @CreationYear,
                ImagePath           = @ImagePath,
                MuseumCollectionID  = @MuseumCollectionID,
                MuseumID            = @MuseumID
            WHERE IDExhibit = @IDExhibit;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Exhibit_Delete
            @IDExhibit INT
        AS
        BEGIN
            DELETE FROM Exhibit   WHERE IDExhibit = @IDExhibit;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDMuseumCollection,
                Name
            FROM MuseumCollection;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_GetById
            @IDMuseumCollection INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDMuseumCollection,
                Name
            FROM MuseumCollection
            WHERE IDMuseumCollection = @IDMuseumCollection;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_GetByMuseumId
            @MuseumID INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT DISTINCT
                mc.IDMuseumCollection,
                mc.Name
            FROM MuseumCollection mc
            INNER JOIN Exhibit e ON e.MuseumCollectionID = mc.IDMuseumCollection
            WHERE e.MuseumID = @MuseumID;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_Insert
            @Name               NVARCHAR(200),
            @IDInserted         INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO MuseumCollection (Name)
            VALUES (@Name);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_Update
            @IDMuseumCollection INT,
            @Name               NVARCHAR(200)
        AS
        BEGIN
            UPDATE MuseumCollection
            SET Name = @Name
            WHERE IDMuseumCollection = @IDMuseumCollection;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_MuseumCollection_Delete
            @IDMuseumCollection INT
        AS
        BEGIN
            DELETE FROM CuratorCollection WHERE MuseumCollectionID = @IDMuseumCollection;
            UPDATE Exhibit SET MuseumCollectionID = NULL WHERE MuseumCollectionID = @IDMuseumCollection;
            DELETE FROM MuseumCollection WHERE IDMuseumCollection = @IDMuseumCollection;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Country_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCountry,
                Name
            FROM Country;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Country_GetById
            @IDCountry INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCountry,
                Name
            FROM Country
            WHERE IDCountry = @IDCountry;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Country_Insert
            @Name       NVARCHAR(100),
            @IDInserted INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Country (Name)
            VALUES (@Name);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Country_Update
            @IDCountry  INT,
            @Name       NVARCHAR(100)
        AS
        BEGIN
            UPDATE Country
            SET Name = @Name
            WHERE IDCountry = @IDCountry;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Country_Delete
            @IDCountry INT
        AS
        BEGIN
            DELETE FROM CuratorCollection
            WHERE CuratorID IN (
                SELECT IDCurator FROM Curator
                WHERE MuseumID IN (
                    SELECT IDMuseum FROM Museum
                    WHERE CityID IN (SELECT IDCity FROM City WHERE CountryID = @IDCountry)
                )
            );
            DELETE FROM Curator
            WHERE MuseumID IN (
                SELECT IDMuseum FROM Museum
                WHERE CityID IN (SELECT IDCity FROM City WHERE CountryID = @IDCountry)
            );
            DELETE FROM Exhibit
            WHERE MuseumID IN (
                SELECT IDMuseum FROM Museum
                WHERE CityID IN (SELECT IDCity FROM City WHERE CountryID = @IDCountry)
            );
            DELETE FROM Museum WHERE CityID IN (SELECT IDCity FROM City WHERE CountryID = @IDCountry);
            DELETE FROM City    WHERE CountryID = @IDCountry;
            DELETE FROM Country WHERE IDCountry = @IDCountry;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCity,
                Name,
                CountryID
            FROM City;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_GetById
            @IDCity INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCity,
                Name,
                CountryID
            FROM City
            WHERE IDCity = @IDCity;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_GetAllWithDetails
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                c.IDCity,
                c.Name,
                c.CountryID,
                co.IDCountry,
                co.Name     AS CountryName
            FROM City c
            INNER JOIN Country co ON c.CountryID = co.IDCountry;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_GetByIdWithDetails
            @IDCity INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                c.IDCity,
                c.Name,
                c.CountryID,
                co.IDCountry,
                co.Name     AS CountryName
            FROM City c
            INNER JOIN Country co ON c.CountryID = co.IDCountry
            WHERE c.IDCity = @IDCity;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_Insert
            @Name       NVARCHAR(100),
            @CountryID  INT,
            @IDInserted INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO City (Name, CountryID)
            VALUES (@Name, @CountryID);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_Update
            @IDCity     INT,
            @Name       NVARCHAR(100),
            @CountryID  INT
        AS
        BEGIN
            UPDATE City
            SET
                Name      = @Name,
                CountryID = @CountryID
            WHERE IDCity = @IDCity;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_City_Delete
            @IDCity INT
        AS
        BEGIN
            DELETE FROM CuratorCollection
            WHERE CuratorID IN (
                SELECT IDCurator FROM Curator
                WHERE MuseumID IN (SELECT IDMuseum FROM Museum WHERE CityID = @IDCity)
            );
            DELETE FROM Curator
            WHERE MuseumID IN (SELECT IDMuseum FROM Museum WHERE CityID = @IDCity);
            DELETE FROM Exhibit
            WHERE MuseumID IN (SELECT IDMuseum FROM Museum WHERE CityID = @IDCity);
            DELETE FROM Museum WHERE CityID = @IDCity;
            DELETE FROM City   WHERE IDCity  = @IDCity;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCurator,
                FirstName,
                LastName,
                MuseumID
            FROM Curator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_GetById
            @IDCurator INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                IDCurator,
                FirstName,
                LastName,
                MuseumID
            FROM Curator
            WHERE IDCurator = @IDCurator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_GetAllWithDetails
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                cu.IDCurator,
                cu.FirstName,
                cu.LastName,
                cu.MuseumID,
                m.Name          AS MuseumName,
                m.Adress        AS MuseumAdress,
                m.EstablishmentYear,
                m.Description   AS MuseumDescription,
                m.ImagePath     AS MuseumImagePath,
                m.CityID
            FROM Curator cu
            INNER JOIN Museum m ON cu.MuseumID = m.IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_GetByIdWithDetails
            @IDCurator INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                cu.IDCurator,
                cu.FirstName,
                cu.LastName,
                cu.MuseumID,
                m.Name          AS MuseumName,
                m.Adress        AS MuseumAdress,
                m.EstablishmentYear,
                m.Description   AS MuseumDescription,
                m.ImagePath     AS MuseumImagePath,
                m.CityID
            FROM Curator cu
            INNER JOIN Museum m ON cu.MuseumID = m.IDMuseum
            WHERE cu.IDCurator = @IDCurator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_Insert
            @FirstName  NVARCHAR(100),
            @LastName   NVARCHAR(100),
            @MuseumID   INT,
            @IDInserted INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Curator (FirstName, LastName, MuseumID)
            VALUES (@FirstName, @LastName, @MuseumID);
            SET @IDInserted = SCOPE_IDENTITY();
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_Update
            @IDCurator  INT,
            @FirstName  NVARCHAR(100),
            @LastName   NVARCHAR(100),
            @MuseumID   INT
        AS
        BEGIN
            UPDATE Curator
            SET
                FirstName = @FirstName,
                LastName  = @LastName,
                MuseumID  = @MuseumID
            WHERE IDCurator = @IDCurator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Curator_Delete
            @IDCurator INT
        AS
        BEGIN
            DELETE FROM CuratorCollection WHERE CuratorID  = @IDCurator;
            DELETE FROM Curator           WHERE IDCurator = @IDCurator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_CuratorCollection_GetAll
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                cc.CuratorID,
                mc.IDMuseumCollection,
                mc.Name AS CollectionName
            FROM CuratorCollection cc
            INNER JOIN MuseumCollection mc ON cc.MuseumCollectionID = mc.IDMuseumCollection;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_CuratorCollection_GetByCuratorId
            @IDCurator INT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                mc.IDMuseumCollection,
                mc.Name AS CollectionName
            FROM CuratorCollection cc
            INNER JOIN MuseumCollection mc ON cc.MuseumCollectionID = mc.IDMuseumCollection
            WHERE cc.CuratorID = @IDCurator;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_CuratorCollection_Insert
            @CuratorID          INT,
            @MuseumCollectionID INT
        AS
        BEGIN
            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID)
            VALUES (@MuseumCollectionID, @CuratorID);
        END
        GO
        CREATE OR ALTER PROCEDURE usp_CuratorCollection_Delete
            @CuratorID          INT,
            @MuseumCollectionID INT
        AS
        BEGIN
            DELETE FROM CuratorCollection
            WHERE CuratorID = @CuratorID AND MuseumCollectionID = @MuseumCollectionID;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_CuratorCollection_DeleteByCuratorId
            @CuratorID INT
        AS
        BEGIN
            DELETE FROM CuratorCollection WHERE CuratorID = @CuratorID;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_Museum_GetAllForXMLExport
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT
                m.IDMuseum,
                m.Name,
                m.Adress,
                m.EstablishmentYear,
                m.Description,
                m.ImagePath,
                m.CityID,
                e.IDExhibit,
                e.Name              AS ExhibitName,
                e.Description       AS ExhibitDescription,
                e.CreationYear,
                e.ImagePath         AS ExhibitImagePath,
                mc.IDMuseumCollection,
                mc.Name             AS CollectionName
            FROM Museum m
            LEFT JOIN Exhibit          e  ON e.MuseumID            = m.IDMuseum
            LEFT JOIN MuseumCollection mc ON e.MuseumCollectionID  = mc.IDMuseumCollection
            ORDER BY m.IDMuseum;
        END
        GO
        CREATE OR ALTER PROCEDURE usp_DeleteAllData
        AS
        BEGIN
            SET NOCOUNT ON;
            DELETE FROM CuratorCollection;
            DELETE FROM Exhibit;
            DELETE FROM Curator;
            DELETE FROM Museum;
            DELETE FROM MuseumCollection;
            DELETE FROM City;
            DELETE FROM Country;
            DELETE FROM [User] WHERE RoleID != (SELECT IDRole FROM Role WHERE Name = 'ADMINISTRATOR');
        END
        GO
        CREATE OR ALTER PROCEDURE usp_ImagePath_GetUsageCount
            @ImagePath          NVARCHAR(500),
            @ReferencesCount    INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            SELECT @ReferencesCount =
                (SELECT COUNT(*) FROM Museum  WHERE ImagePath = @ImagePath) +
                (SELECT COUNT(*) FROM Exhibit WHERE ImagePath = @ImagePath);
        END
    """;

    public static final String SEED = """
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

            INSERT INTO Country (Name) VALUES ('Hrvatska');               SET @idHrvatska   = SCOPE_IDENTITY();
            INSERT INTO Country (Name) VALUES ('Italija');                SET @idItalija    = SCOPE_IDENTITY();
            INSERT INTO Country (Name) VALUES ('Francuska');              SET @idFrancuska  = SCOPE_IDENTITY();
            INSERT INTO Country (Name) VALUES ('Ujedinjeno Kraljevstvo'); SET @idUK         = SCOPE_IDENTITY();
            INSERT INTO Country (Name) VALUES ('SAD');                    SET @idSAD        = SCOPE_IDENTITY();
            INSERT INTO Country (Name) VALUES ('Nizozemska');             SET @idNizozemska = SCOPE_IDENTITY();

            INSERT INTO City (Name, CountryID) VALUES ('Zagreb',    @idHrvatska);   SET @idZagreb    = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('Split',     @idHrvatska);   SET @idSplit     = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('Rim',       @idItalija);    SET @idRim       = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('Firenza',   @idItalija);    SET @idFirenza   = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('Pariz',     @idFrancuska);  SET @idParis     = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('London',    @idUK);         SET @idLondon    = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('New York',  @idSAD);        SET @idNewYork   = SCOPE_IDENTITY();
            INSERT INTO City (Name, CountryID) VALUES ('Amsterdam', @idNizozemska); SET @idAmsterdam = SCOPE_IDENTITY();

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

            INSERT INTO MuseumCollection (Name) VALUES ('Antička zbirka');              SET @idKol1 = SCOPE_IDENTITY();
            INSERT INTO MuseumCollection (Name) VALUES ('Moderna umjetnost');           SET @idKol2 = SCOPE_IDENTITY();
            INSERT INTO MuseumCollection (Name) VALUES ('Renesansna skulptura');        SET @idKol3 = SCOPE_IDENTITY();
            INSERT INTO MuseumCollection (Name) VALUES ('Namještaj i dekorativna art'); SET @idKol4 = SCOPE_IDENTITY();

            INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ivan',   'Horvat', @idMUO);     SET @idKur1 = SCOPE_IDENTITY();
            INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Ana',    'Kovač',  @idAM);      SET @idKur2 = SCOPE_IDENTITY();
            INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Marco',  'Rossi',  @idVatikan); SET @idKur3 = SCOPE_IDENTITY();
            INSERT INTO Curator (FirstName, LastName, MuseumID) VALUES ('Sophie', 'Martin', @idLouvre);  SET @idKur4 = SCOPE_IDENTITY();

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

            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol1, @idKur2);
            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur4);
            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol3, @idKur3);
            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol4, @idKur1);
            INSERT INTO CuratorCollection (MuseumCollectionID, CuratorID) VALUES (@idKol2, @idKur1);
        END
    """;

    public static final String DDL = """
    CREATE TABLE Country (
        IDCountry   INT           NOT NULL IDENTITY(1,1),
        Name        NVARCHAR(100) NOT NULL,
        CONSTRAINT PK_Country PRIMARY KEY (IDCountry)
    )
    GO
    CREATE TABLE City (
        IDCity      INT           NOT NULL IDENTITY(1,1),
        Name        NVARCHAR(100) NOT NULL,
        CountryID   INT           NOT NULL,
        CONSTRAINT PK_City PRIMARY KEY (IDCity),
        CONSTRAINT FK_City_Country FOREIGN KEY (CountryID)
            REFERENCES Country (IDCountry)
    )
    GO
    CREATE TABLE Museum (
        IDMuseum            INT            NOT NULL IDENTITY(1,1),
        Name                NVARCHAR(200)  NOT NULL,
        Adress              NVARCHAR(300)  NULL,
        EstablishmentYear   INT            NOT NULL,
        Description         NVARCHAR(MAX)  NULL,
        ImagePath           NVARCHAR(500)  NULL,
        CityID              INT            NOT NULL,
        CONSTRAINT PK_Museum PRIMARY KEY (IDMuseum),
        CONSTRAINT FK_Museum_City FOREIGN KEY (CityID)
            REFERENCES City (IDCity)
    )
    GO
    CREATE TABLE MuseumCollection (
        IDMuseumCollection  INT           NOT NULL IDENTITY(1,1),
        Name                NVARCHAR(200) NOT NULL,
        CONSTRAINT PK_MuseumCollection PRIMARY KEY (IDMuseumCollection)
    )
    GO
    CREATE TABLE Exhibit (
        IDExhibit           INT            NOT NULL IDENTITY(1,1),
        Name                NVARCHAR(200)  NOT NULL,
        Description         NVARCHAR(MAX)  NULL,
        CreationYear        INT            NULL,
        ImagePath           NVARCHAR(500)  NULL,
        MuseumCollectionID  INT            NULL,
        MuseumID            INT            NOT NULL,
        CONSTRAINT PK_Exhibit PRIMARY KEY (IDExhibit),
        CONSTRAINT FK_Exhibit_MuseumCollection FOREIGN KEY (MuseumCollectionID)
            REFERENCES MuseumCollection (IDMuseumCollection),
        CONSTRAINT FK_Exhibit_Museum FOREIGN KEY (MuseumID)
            REFERENCES Museum (IDMuseum)
    )
    GO
    CREATE TABLE Curator (
        IDCurator   INT           NOT NULL IDENTITY(1,1),
        FirstName   NVARCHAR(100) NOT NULL,
        LastName    NVARCHAR(100) NOT NULL,
        MuseumID    INT           NOT NULL,
        CONSTRAINT PK_Curator PRIMARY KEY (IDCurator),
        CONSTRAINT FK_Curator_Museum FOREIGN KEY (MuseumID)
            REFERENCES Museum (IDMuseum)
    )
    GO
    CREATE TABLE CuratorCollection (
        MuseumCollectionID  INT NOT NULL,
        CuratorID           INT NOT NULL,
        CONSTRAINT PK_CuratorCollection PRIMARY KEY (MuseumCollectionID, CuratorID),
        CONSTRAINT FK_CuratorCollection_MuseumCollection FOREIGN KEY (MuseumCollectionID)
            REFERENCES MuseumCollection (IDMuseumCollection),
        CONSTRAINT FK_CuratorCollection_Curator FOREIGN KEY (CuratorID)
            REFERENCES Curator (IDCurator)
    )
    GO
    CREATE TABLE Role (
        IDRole  INT           NOT NULL,
        Name    NVARCHAR(50)  NOT NULL,
        CONSTRAINT PK_Role PRIMARY KEY (IDRole),
        CONSTRAINT UQ_Role_Name UNIQUE (Name)
    )
    GO
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
    )
    """;
}
