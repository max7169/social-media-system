USE social_media;
GO

-- =========================
-- Users
-- =========================
CREATE TABLE Users (
    UserId INT IDENTITY(1,1) PRIMARY KEY,
    UserName NVARCHAR(50) NOT NULL,
    Phone NVARCHAR(20) NOT NULL UNIQUE,
    Email NVARCHAR(100) NULL,
    PasswordHash NVARCHAR(255) NOT NULL,
    CoverImage NVARCHAR(500) NULL,
    Biography NVARCHAR(500) NULL,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),
    UpdatedAt DATETIME2 NULL
);
GO


-- =========================
-- Posts
-- =========================
CREATE TABLE Posts (
    PostId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    Image NVARCHAR(500) NULL,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),
    UpdatedAt DATETIME2 NULL,

    CONSTRAINT FK_Posts_Users
        FOREIGN KEY (UserId)
        REFERENCES Users(UserId)
);
GO


-- =========================
-- Comments
-- =========================
CREATE TABLE Comments (
    CommentId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT NOT NULL,
    PostId INT NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    CreatedAt DATETIME2 NOT NULL DEFAULT GETDATE(),

    CONSTRAINT FK_Comments_Users
        FOREIGN KEY (UserId)
        REFERENCES Users(UserId),

    CONSTRAINT FK_Comments_Posts
        FOREIGN KEY (PostId)
        REFERENCES Posts(PostId)
);
GO