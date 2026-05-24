-- Notification schema update (run manually on SQL Server)

IF OBJECT_ID('dbo.tblNotificationRead', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.tblNotificationRead (
        UserID INT NOT NULL,
        NotificationID INT NOT NULL,
        ReadDate DATETIME NOT NULL DEFAULT GETDATE(),
        CONSTRAINT PK_tblNotificationRead PRIMARY KEY (UserID, NotificationID)
    );

    ALTER TABLE dbo.tblNotificationRead
        ADD CONSTRAINT FK_tblNotificationRead_User
        FOREIGN KEY (UserID) REFERENCES dbo.Users(user_id);

    ALTER TABLE dbo.tblNotificationRead
        ADD CONSTRAINT FK_tblNotificationRead_Notification
        FOREIGN KEY (NotificationID) REFERENCES dbo.tblNotification(NotificationID);
END
GO

-- Expand target types
IF EXISTS (
    SELECT 1
    FROM sys.check_constraints
    WHERE name = 'CK_tblNotification_TargetType'
)
BEGIN
    ALTER TABLE dbo.tblNotification DROP CONSTRAINT CK_tblNotification_TargetType;
END
GO

ALTER TABLE dbo.tblNotification
ADD CONSTRAINT CK_tblNotification_TargetType
CHECK (TargetType IN ('ALL', 'ALL_TEACHER', 'ALL_STUDENT', 'CLASS'));
GO
