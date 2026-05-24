-- Notification attachment and sender department migration (run manually on SQL Server)

IF COL_LENGTH('dbo.tblNotification', 'SenderDepartment') IS NULL
BEGIN
    ALTER TABLE dbo.tblNotification
    ADD SenderDepartment NVARCHAR(255) NULL;
END
GO

IF OBJECT_ID('dbo.tblNotificationAttachment', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.tblNotificationAttachment (
        AttachmentID INT IDENTITY(1,1) NOT NULL,
        NotificationID INT NOT NULL,
        FileName NVARCHAR(255) NOT NULL,
        FilePath NVARCHAR(500) NOT NULL,
        FileSize INT NULL,
        CONSTRAINT PK_tblNotificationAttachment PRIMARY KEY (AttachmentID)
    );

    ALTER TABLE dbo.tblNotificationAttachment
        ADD CONSTRAINT FK_tblNotificationAttachment_Notification
        FOREIGN KEY (NotificationID) REFERENCES dbo.tblNotification(NotificationID);
END
GO