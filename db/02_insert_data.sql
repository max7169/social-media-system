USE social_media;
GO

-- =========================
-- Users
-- =========================

INSERT INTO Users
(
    UserName,
    Phone,
    Email,
    PasswordHash,
    Biography
)
VALUES
(
    N'王小明',
    N'0912345678',
    N'xiaoMing@example.com',
    N'TEMP_HASH_001',
    N'這是王小明的自我介紹'
),
(
    N'李小華',
    N'0923456789',
    N'xiaoHua@example.com',
    N'TEMP_HASH_002',
    N'大家好，我是小華'
),
(
    N'陳小美',
    N'0934567890',
    N'xiaoMei@example.com',
    N'TEMP_HASH_003',
    N'喜歡分享生活'
);
GO


-- =========================
-- Posts
-- =========================

INSERT INTO Posts
(
    UserId,
    Content
)
VALUES
(
    1,
    N'大家好，這是我的第一篇文章！'
),
(
    2,
    N'今天天氣很好，適合出去走走。'
),
(
    3,
    N'正在學習 Vue.js 和 Spring Boot。'
);
GO


-- =========================
-- Comments
-- =========================

INSERT INTO Comments
(
    UserId,
    PostId,
    Content
)
VALUES
(
    2,
    1,
    N'歡迎加入！'
),
(
    3,
    1,
    N'第一篇文章很棒！'
),
(
    1,
    2,
    N'真的，今天的天氣不錯。'
);
GO