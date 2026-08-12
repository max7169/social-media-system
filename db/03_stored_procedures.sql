USE social_media;
GO



-- =========================
-- 取得所有文章
-- =========================
CREATE OR ALTER PROCEDURE sp_GetAllPosts
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        p.PostId,
        p.UserId,
        u.UserName,
        p.Content,
        p.Image,
        p.CreatedAt,
        p.UpdatedAt
    FROM Posts p
    INNER JOIN Users u
        ON p.UserId = u.UserId
    ORDER BY p.CreatedAt DESC;
END;
GO



-- =========================
-- 取得單篇文章
-- =========================
CREATE OR ALTER PROCEDURE sp_GetPostById
    @PostId INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        p.PostId,
        p.UserId,
        u.UserName,
        p.Content,
        p.Image,
        p.CreatedAt,
        p.UpdatedAt
    FROM Posts p
    INNER JOIN Users u
        ON p.UserId = u.UserId
    WHERE p.PostId = @PostId;
END;
GO

-- =========================
-- 新增文章
-- =========================
CREATE OR ALTER PROCEDURE sp_CreatePost
    @UserId INT,
    @Content NVARCHAR(MAX),
    @Image NVARCHAR(500) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Posts
    (
        UserId,
        Content,
        Image
    )
    VALUES
    (
        @UserId,
        @Content,
        @Image
    );

    SELECT
        CAST(SCOPE_IDENTITY() AS INT) AS PostId;
END;
GO

-- =========================
-- 修改文章
-- =========================
CREATE OR ALTER PROCEDURE sp_UpdatePost
    @PostId INT,
    @UserId INT,
    @Content NVARCHAR(MAX),
    @Image NVARCHAR(500) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Posts
    SET
        Content = @Content,
        Image = @Image,
        UpdatedAt = GETDATE()
    WHERE
        PostId = @PostId
        AND UserId = @UserId;

    SELECT @@ROWCOUNT AS AffectedRows;
END;
GO

-- =========================
-- 刪除文章
-- =========================
CREATE OR ALTER PROCEDURE sp_DeletePost
    @PostId INT,
    @UserId INT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        BEGIN TRANSACTION;

        DELETE FROM Comments
        WHERE PostId = @PostId;

        DELETE FROM Posts
        WHERE PostId = @PostId
          AND UserId = @UserId;

        IF @@ROWCOUNT = 0
        BEGIN
            THROW 50001, 'Post not found or user does not have permission.', 1;
        END;

        COMMIT TRANSACTION;

    END TRY
    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;

    END CATCH
END;
GO

-- =========================
-- 新增留言
-- =========================
CREATE OR ALTER PROCEDURE sp_CreateComment
    @UserId INT,
    @PostId INT,
    @Content NVARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Comments
    (
        UserId,
        PostId,
        Content
    )
    VALUES
    (
        @UserId,
        @PostId,
        @Content
    );

    SELECT
        CAST(SCOPE_IDENTITY() AS INT) AS CommentId;
END;
GO

-- =========================
-- 查詢文章留言
-- =========================
CREATE OR ALTER PROCEDURE sp_GetCommentsByPostId
    @PostId INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        c.CommentId,
        c.UserId,
        u.UserName,
        c.PostId,
        c.Content,
        c.CreatedAt
    FROM Comments c
    INNER JOIN Users u
        ON c.UserId = u.UserId
    WHERE c.PostId = @PostId
    ORDER BY c.CreatedAt ASC;
END;
GO

-- =========================
-- 透過手機號碼取得使用者
-- =========================
CREATE OR ALTER PROCEDURE sp_GetUserByPhone
    @Phone NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        UserId,
        UserName,
        Phone,
        Email,
        PasswordHash,
        CoverImage,
        Biography,
        CreatedAt,
        UpdatedAt
    FROM Users
    WHERE Phone = @Phone;
END;
GO