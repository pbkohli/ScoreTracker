CREATE Table [dbo].[Course] (
CourseID [int] IDENTITY (1, 1) NOT NULL
, Name varchar(50) NOT NULL
, [State] varchar(2) NULL
, Tees varchar(50) NULL
, CONSTRAINT [PK_Course] PRIMARY KEY NONCLUSTERED (CourseID ASC)
, CONSTRAINT [UC_Course_NameStateTees] UNIQUE (
	Name ASC
	, State ASC
	, Tees ASC
	)
)

CREATE Table [dbo].[Hole] (
HoleID [int] IDENTITY (1, 1) NOT NULL
, Number [int] NOT NULL
, Par [int] NOT NULL
, Handicap [int] NULL
, Yardage [int] NULL
, CourseID [int] NOT NULL
, CONSTRAINT [UC_Hole_CourseNumber] UNIQUE (
	CourseID ASC
	, Number ASC
	)
)
ALTER TABLE [dbo].[Hole]
	ADD CONSTRAINT FK_Investment_CourseID
	FOREIGN KEY (CourseID)
	REFERENCES dbo.Course(CourseID)


CREATE Table [dbo].[User] (
UserID [int] IDENTITY(1, 1) NOT NULL
, FirstName [varchar] (50) NOT NULL
, LastName [varchar] (50) NOT NULL
, Email [varchar] (50) NOT NULL
, Password [varchar] (250) NOT NULL
, dob [datetime] NOT NULL
, CONSTRAINT [UC_User_Email] UNIQUE (
	Email
	)
)


CREATE Table [dbo].[Round] (
RoundID [int] IDENTITY(1, 1) NOT NULL
, UserID [int] NOT NULL
, CourseID [int] NOT NULL
, [Date] [datetime] NULL
, CONSTRAINT [PK_Round] PRIMARY KEY NONCLUSTERED (RoundID ASC)
)


CREATE TABLE [dbo].[HoleScore] (
ScoreID [int] IDENTITY(1, 1) NOT NULL
, Strokes [int] NOT NULL
, ToGreen [int] NULL
, Penalty bit NULL
, Sand bit NULL
, RoundID [int] NOT NULL
, Hole int NOT NULL
, CONSTRAINT [UC_HoleScore_RoundHole] UNIQUE (
	RoundID
	, Hole
	)
)
ALTER TABLE [dbo].[HoleScore]
	ADD CONSTRAINT FK_HoleScore_RoundID
	FOREIGN KEY (RoundID)
	REFERENCES [dbo].[Round](RoundID)