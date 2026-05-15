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
    public final static String GETALL_MUSEUMS_WITH_DETAILS      = "{call usp_Museum_GetAllWithDetails}";
    public final static String INSERT_MUSEUM              = "{call usp_Museum_Insert(?, ?, ?, ?, ?, ?, ?)}";
    public final static String UPDATE_MUSEUM              = "{call usp_Museum_Update(?, ?, ?, ?, ?, ?, ?)}";
    public final static String DELETE_MUSEUM              = "{call usp_Museum_Delete(?)}";
    public final static String GETALL_EXHIBITS             = "{call usp_Exhibit_GetAll}";
    public final static String GETBYID_EXHIBIT             = "{call usp_Exhibit_GetById(?)}";
    public final static String GETBYMUSEUMID_EXHIBIT       = "{call usp_Exhibit_GetByMuseumId(?)}";
    public final static String INSERT_EXHIBIT              = "{call usp_Exhibit_Insert(?, ?, ?, ?, ?, ?, ?)}";
    public final static String UPDATE_EXHIBIT              = "{call usp_Exhibit_Update(?, ?, ?, ?, ?, ?, ?)}";
    public final static String DELETE_EXHIBIT              = "{call usp_Exhibit_Delete(?)}";





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
            @IDUser     INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID)
            VALUES (@Username, @Email, @FirstName, @LastName, @Password, @RoleID);
            SET @IDUser = SCOPE_IDENTITY();
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
            @IDMuseum           INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Museum (Name, Adress, EstablishmentYear, Description, ImagePath, CityID)
            VALUES (@Name, @Adress, @EstablishmentYear, @Description, @ImagePath, @CityID);
            SET @IDMuseum = SCOPE_IDENTITY();
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
            DELETE FROM Museum
            WHERE IDMuseum = @IDMuseum;
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
            INNER JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
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
            INNER JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
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
            INNER JOIN MuseumCollection mc  ON e.MuseumCollectionID = mc.IDMuseumCollection
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
            @IDExhibit          INT OUTPUT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO Exhibit (Name, Description, CreationYear, ImagePath, MuseumCollectionID, MuseumID)
            VALUES (@Name, @Description, @CreationYear, @ImagePath, @MuseumCollectionID, @MuseumID);
            SET @IDExhibit = SCOPE_IDENTITY();
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
            DELETE FROM Exhibit
            WHERE IDExhibit = @IDExhibit;
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
        EstablishmentYear   INT            NULL,
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
        MuseumCollectionID  INT            NOT NULL,
        MuseumID            INT            NOT NULL,
        CONSTRAINT PK_Exhibit PRIMARY KEY (IDExhibit),
        CONSTRAINT FK_Exhibit_MuseumCollection FOREIGN KEY (MuseumCollectionID)
            REFERENCES MuseumCollection (IDMuseumCollection),
        CONSTRAINT FK_Exhibit_Museum FOREIGN KEY (MuseumID)
            REFERENCES Museum (IDMuseum)
    )
    GO
    CREATE TABLE ImagePath (
        IDImagePath     INT            NOT NULL IDENTITY(1,1),
        ImagePath       NVARCHAR(500)  NOT NULL,
        ExhibitID       INT            NOT NULL,
        CONSTRAINT PK_ImagePath PRIMARY KEY (IDImagePath),
        CONSTRAINT FK_ImagePath_Exhibit FOREIGN KEY (ExhibitID)
            REFERENCES Exhibit (IDExhibit)
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
        IDCuratorCollection INT NOT NULL IDENTITY(1,1),
        MuseumCollectionID  INT NOT NULL,
        CuratorID           INT NOT NULL,
        CONSTRAINT PK_CuratorCollection PRIMARY KEY (IDCuratorCollection),
        CONSTRAINT FK_CuratorCollection_MuseumCollection FOREIGN KEY (MuseumCollectionID)
            REFERENCES MuseumCollection (IDMuseumCollection),
        CONSTRAINT FK_CuratorCollection_Curator FOREIGN KEY (CuratorID)
            REFERENCES Curator (IDCurator),
        CONSTRAINT UQ_CuratorCollection UNIQUE (MuseumCollectionID, CuratorID)
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
