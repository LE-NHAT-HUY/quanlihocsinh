IF COL_LENGTH('dbo.ScoreLog', 'ClassID') IS NULL
BEGIN
    ALTER TABLE dbo.ScoreLog
    ADD ClassID INT NULL;
END
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.indexes
    WHERE name = 'IX_ScoreLog_Class_Subject_Semester_Date'
      AND object_id = OBJECT_ID('dbo.ScoreLog')
)
BEGIN
    CREATE NONCLUSTERED INDEX IX_ScoreLog_Class_Subject_Semester_Date
    ON dbo.ScoreLog (ClassID, SubjectID, SemesterID, ChangeDate DESC)
    INCLUDE (TeacherID, StudentID, ActionType);
END
GO

IF NOT EXISTS (
    SELECT 1
    FROM sys.indexes
    WHERE name = 'IX_ScoreLog_ChangeDate'
      AND object_id = OBJECT_ID('dbo.ScoreLog')
)
BEGIN
    CREATE NONCLUSTERED INDEX IX_ScoreLog_ChangeDate
    ON dbo.ScoreLog (ChangeDate DESC)
    INCLUDE (ClassID, SubjectID, SemesterID, TeacherID, StudentID, ActionType);
END
GO
