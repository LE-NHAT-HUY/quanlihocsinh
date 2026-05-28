IF OBJECT_ID('dbo.tblSubject_Grade', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.tblSubject_Grade (
        SubjectID INT NOT NULL,
        GradeID INT NOT NULL,
        Periods INT NOT NULL,
        CONSTRAINT PK_tblSubject_Grade PRIMARY KEY (SubjectID, GradeID),
        CONSTRAINT FK_tblSubject_Grade_Subject FOREIGN KEY (SubjectID)
            REFERENCES dbo.tblSubject (SubjectID)
            ON DELETE CASCADE,
        CONSTRAINT FK_tblSubject_Grade_Grade FOREIGN KEY (GradeID)
            REFERENCES dbo.tblGrade (GradeID)
            ON DELETE CASCADE
    );

    CREATE INDEX IX_tblSubject_Grade_GradeID ON dbo.tblSubject_Grade (GradeID);
END
GO
