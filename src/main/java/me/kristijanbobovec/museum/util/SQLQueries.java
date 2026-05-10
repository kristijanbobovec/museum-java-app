package me.kristijanbobovec.museum.util;

public class SQLQueries {
    private SQLQueries() {}

    public final static String USE_DATABASE = "USE %s";
    public final static String CREATE_DATABASE = "CREATE DATABASE %s";
    public final static String DROP_DATABASE = "DROP DATABASE %s";

    public final static String INSERT_ROLE = "{call usp_Role_Insert(?, ?)}";
    public final static String INSERT_USER = "{call usp_User_Insert(?, ?, ?, ? ,?, ?)}";
    public final static String GETALL_USERS = "{call usp_User_GetAll}";
    public final static String GETBYID_USER = "{call usp_User_GetById(?)}";
    public final static String GETBYUSERNAME_USER = "{call usp_User_GetByUsername(?)}";
    public final static String USERNAMEEXISTS_USER = "{call usp_User_UsernameExists(?, ?)}";
    public final static String EMAILEXISTS_USER = "{call usp_User_EmailExists(?, ?)}";





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
        CREATE OR ALTER PROCEDURE usp_User_Insert
            @Username   NVARCHAR(100),
            @Email      NVARCHAR(200),
            @FirstName  NVARCHAR(100),
            @LastName   NVARCHAR(100),
            @Password   NVARCHAR(500),
            @RoleID     INT
        AS
        BEGIN
            SET NOCOUNT ON;
            INSERT INTO [User] (Username, Email, FirstName, LastName, Password, RoleID)
            VALUES (@Username, @Email, @FirstName, @LastName, @Password, @RoleID);
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
        MuseumCollectionID  INT            NULL,
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
