USE [master]
GO
/****** Object:  Database [FurnitureStore]    Script Date: 12/27/2020 4:44:08 PM ******/
CREATE DATABASE [FurnitureStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'FurnitureStore', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\FurnitureStore.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'FurnitureStore_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\FurnitureStore_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [FurnitureStore] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [FurnitureStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [FurnitureStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [FurnitureStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [FurnitureStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [FurnitureStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [FurnitureStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [FurnitureStore] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [FurnitureStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [FurnitureStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [FurnitureStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [FurnitureStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [FurnitureStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [FurnitureStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [FurnitureStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [FurnitureStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [FurnitureStore] SET  DISABLE_BROKER 
GO
ALTER DATABASE [FurnitureStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [FurnitureStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [FurnitureStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [FurnitureStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [FurnitureStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [FurnitureStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [FurnitureStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [FurnitureStore] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [FurnitureStore] SET  MULTI_USER 
GO
ALTER DATABASE [FurnitureStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [FurnitureStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [FurnitureStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [FurnitureStore] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [FurnitureStore] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [FurnitureStore] SET QUERY_STORE = OFF
GO
USE [FurnitureStore]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[ProductID] [varchar](10) NOT NULL,
	[ProductName] [nvarchar](100) NOT NULL,
	[Price] [decimal](18, 2) NOT NULL,
	[CategoryID] [varchar](10) NOT NULL,
	[BrandID] [varchar](10) NOT NULL,
	[TypeID] [varchar](10) NOT NULL,
	[ProductImages] [varchar](max) NOT NULL,
	[Descriptions] [nvarchar](max) NULL,
	[Feature] [nvarchar](100) NULL,
	[ProductState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ratings]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ratings](
	[RatingID] [int] IDENTITY(1,1) NOT NULL,
	[ProductID] [varchar](10) NOT NULL,
	[CustomerEmail] [varchar](100) NOT NULL,
	[Rate] [int] NOT NULL,
	[RatingDate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[RatingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[AverageRatings]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[AverageRatings] AS
	SELECT ROW_NUMBER() OVER (ORDER BY p.ProductName) AS id, p.ProductID, p.ProductName, AVG(convert(decimal(4,2),r.Rate)) AS averageRating
	FROM Products p JOIN Ratings r ON p.ProductID = r.ProductID
	GROUP BY p.ProductName, p.ProductID
GO
/****** Object:  Table [dbo].[Admins]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admins](
	[Email] [varchar](100) NOT NULL,
	[Password] [varchar](20) NOT NULL,
	[FullName] [nvarchar](50) NOT NULL,
	[CreatedDate] [date] NULL,
	[AdminState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Brands]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Brands](
	[BrandID] [varchar](10) NOT NULL,
	[BrandName] [nvarchar](100) NOT NULL,
	[BrandImages] [nvarchar](max) NULL,
	[Descriptions] [nvarchar](max) NULL,
	[BrandState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[BrandID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [varchar](10) NOT NULL,
	[CategoryName] [nvarchar](100) NOT NULL,
	[CategoryImage] [varchar](max) NOT NULL,
	[CategoryState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[Email] [varchar](100) NOT NULL,
	[Password] [varchar](20) NOT NULL,
	[FirstName] [nvarchar](50) NOT NULL,
	[LastName] [nvarchar](50) NOT NULL,
	[Gender] [bit] NULL,
	[Phone] [varchar](20) NULL,
	[CreatedDate] [date] NULL,
	[CustomerState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[OrderID] [varchar](10) NOT NULL,
	[CustomerEmail] [varchar](100) NOT NULL,
	[Total] [decimal](18, 2) NOT NULL,
	[ShipName] [nvarchar](100) NOT NULL,
	[ShipPhone] [varchar](20) NOT NULL,
	[ShipAddress] [nvarchar](200) NOT NULL,
	[ShipDate] [date] NOT NULL,
	[ShipNote] [nvarchar](max) NULL,
	[OrderDate] [date] NULL,
	[PaymentMethod] [nvarchar](100) NOT NULL,
	[ProcessStatus] [nvarchar](50) NOT NULL,
	[OrderState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrdersDetails]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrdersDetails](
	[OdID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [varchar](10) NOT NULL,
	[ProductID] [varchar](10) NOT NULL,
	[SellingPrice] [decimal](18, 2) NOT NULL,
	[Quantity] [int] NOT NULL,
	[OdState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[OdID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Types]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Types](
	[TypeID] [varchar](10) NOT NULL,
	[TypeName] [nvarchar](100) NOT NULL,
	[Descriptions] [nvarchar](max) NULL,
	[TypeState] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[TypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Wishlist]    Script Date: 12/27/2020 4:44:08 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Wishlist](
	[WishlistID] [int] IDENTITY(1,1) NOT NULL,
	[CustomerEmail] [varchar](100) NOT NULL,
	[ProductID] [varchar](10) NOT NULL,
	[Quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[WishlistID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Admins] ([Email], [Password], [FullName], [CreatedDate], [AdminState]) VALUES (N'AnhBua@gmail.com', N'admin', N'anhbua', CAST(N'2020-12-15' AS Date), 1)
GO
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR001', N'Samsung', N'images/Brands/BR001.jpg', N'', 1)
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR002', N'Havey Norman', N'images/Brands/BR002.jpg', N'', 1)
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR003', N'Toshiba', N'images/Brands/BR004.jpg', N'', 1)
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR004', N'Aaron''s', N'images/Brands/BR005.jpg', N'', 1)
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR005', N'Ashley', N'images/Brands/BR006.jpg', N'', 1)
INSERT [dbo].[Brands] ([BrandID], [BrandName], [BrandImages], [Descriptions], [BrandState]) VALUES (N'BR006', N'Mercury Row', N'images/Brands/BR007.jpg', N'', 1)
GO
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA001', N'Table', N'images/Categories/Table.jpg', 1)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA002', N'Sofa', N'images/Categories/Sofa.jpg', 1)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA003', N'Tivi', N'images/Categories/Tivi.jpg', 1)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA004', N'Chair', N'images/Categories/Chair.jpg', 1)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA005', N'Dinner Table', N'images/Categories/tableandchair.jpg', 1)
INSERT [dbo].[Categories] ([CategoryID], [CategoryName], [CategoryImage], [CategoryState]) VALUES (N'CA006', N'Sofa Table', N'images/Categories/Sofatable.jpg', 1)
GO
INSERT [dbo].[Customers] ([Email], [Password], [FirstName], [LastName], [Gender], [Phone], [CreatedDate], [CustomerState]) VALUES (N'giaphuc@gmail.com', N'1234abcd', N'Phuc', N'Le', 1, N'0914556692', CAST(N'2020-12-26' AS Date), 1)
INSERT [dbo].[Customers] ([Email], [Password], [FirstName], [LastName], [Gender], [Phone], [CreatedDate], [CustomerState]) VALUES (N'trungtruc@gmail.com', N'1234abcd', N'Truc', N'Nguyen', 1, N'0914556699', CAST(N'2020-12-25' AS Date), 1)
GO
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR001', N'midium sofa 11', CAST(255.00 AS Decimal(18, 2)), N'CA002', N'BR001', N'TY001', N'pr001-1.jpg;', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'Normal', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR002', N'Chair vip 01 ', CAST(100.00 AS Decimal(18, 2)), N'CA004', N'BR001', N'TY001', N'pr020-1.jpg;pr020-2.jpg;pr020-3.jpg;pr020-4.jpg', N'Office Chair Ergonomic Desk Computer Chair Mesh Computer Chair with Flip Up Arms Lumbar Support and Mid Back Task Home Office Chair White', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR003', N'Marvel Avengers 01 ', CAST(219.00 AS Decimal(18, 2)), N'CA004', N'BR002', N'TY003', N'pr021-1.jpg;pr021-2.jpg;pr021-3.jpg;pr021-4.jpg', N'Marvel Avengers Massage Gaming Chair Desk Office Computer Racing Chairs - Adults Gamer Ergonomic Game Reclining High Back Support Racer Leather (Spider-Man)', N'Hot', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR004', N'Christopher Knight ', CAST(250.00 AS Decimal(18, 2)), N'CA004', N'BR002', N'TY002', N'pr022-1.jpg;pr022-2.jpg;pr022-3.jpg', N'Christopher Knight Home Kwame Fabric / Walnut Finish Dining Chairs, 2-Pcs Set, Mint', N'Normal', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR005', N'Table and chair 01', CAST(110.00 AS Decimal(18, 2)), N'CA005', N'BR002', N'TY003', N'pr023-1.jpg;pr023-2.jpg;pr023-3.jpg;pr023-4.jpg', N'The model of dining table and chairs code 35 is a model of dining table with 04 100% natural wood, modern design suitable for modern style dining space. The entire dining table and chairs are made of 100% natural wood with M10% gloss PU coating.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR006', N'Table and chair 02 ', CAST(599.00 AS Decimal(18, 2)), N'CA005', N'BR001', N'TY002', N'pr024-1.jpg;pr024-2.jpg;pr024-3.jpg;pr024-4.jpg', N'The model of dining table and chairs code 35 is a model of dining table with 04 100% natural wood, modern design suitable for modern style dining space. The entire dining table and chairs are made of 100% natural wood with M10% gloss PU coating.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR007', N'Samsum 43 inch', CAST(500.00 AS Decimal(18, 2)), N'CA003', N'BR001', N'TY001', N'pr025-1.jpg;pr025-2.jpg;pr025-3.jpg;pr025-4.jpg', N'Type of TV: Smart TV, 32 inch
Resolution: HD
Operating system: WebOS 4.5
Available apps: Youtube, Netflix, Web Browser, LG Content Store
Image Technology: Smart Quad Core Processor, Active HDR, Resolution Upscaler, Dynamic Color
Control TV by phone: Using LG TV Plus app', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR008', N'tivi LG 50 inch', CAST(699.00 AS Decimal(18, 2)), N'CA003', N'BR001', N'TY002', N'pr026-1.jpg;pr026-2.jpg;pr026-3.jpg;pr026-4.jpg', N'Type of TV: Smart TV, 32 inch
Resolution: HD
Operating system: WebOS 4.5
Available apps: Youtube, Netflix, Web Browser, LG Content Store
Image Technology: Smart Quad Core Processor, Active HDR, Resolution Upscaler, Dynamic Color
Control TV by phone: Using LG TV Plus app', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR009', N'Sam sum  4k 65inch', CAST(120.00 AS Decimal(18, 2)), N'CA003', N'BR003', N'TY003', N'pr027-1.jpg;pr027-2.jpg;pr027-3.jpg;pr027-4.jpg', N'Type of TV: Smart TV, 32 inch
Resolution: HD
Operating system: WebOS 4.5
Available apps: Youtube, Netflix, Web Browser, LG Content Store
Image Technology: Smart Quad Core Processor, Active HDR, Resolution Upscaler, Dynamic Color
Control TV by phone: Using LG TV Plus app', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR010', N'Nhadep', CAST(11.00 AS Decimal(18, 2)), N'CA002', N'BR003', N'TY003', N'pr051-1.png;', N'Get trend-led treads in our edit of this season''s most talked aboutÂ shoes. Brogues and loafers put the sharp in shoes, while preppy plimsolls and trusty trainers make sure your off-duty style stays on-trend. This season desert boots andÂ skinny jeansÂ are your day-to-night staple, and Chelsea boots best for chanelling preppy.', N'Hot', 0)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR011', N'Stone Table 111', CAST(450.00 AS Decimal(18, 2)), N'CA001', N'BR001', N'TY001', N'pr052-1.jpg;pr052-2.jpg;pr052-3.jpg;pr052-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'Normal', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR012', N'Perfect Sofa', CAST(100.00 AS Decimal(18, 2)), N'CA002', N'BR001', N'TY002', N'pr053-1.jpg;pr053-1.jpg;pr053-1.jpg;pr053-1.jpg;', N'wow', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR013', N'Tivi LG Pro 44inch', CAST(500.00 AS Decimal(18, 2)), N'CA003', N'BR004', N'TY002', N'pr054-1.jpg;pr054-2.jpg;pr054-3.jpg;pr054-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR014', N'Stone Table luxury', CAST(700.00 AS Decimal(18, 2)), N'CA001', N'BR004', N'TY003', N'pr055-1.jpg;pr055-2.jpg;pr055-3.jpg;pr055-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR015', N'Stone Table  perfect', CAST(499.00 AS Decimal(18, 2)), N'CA001', N'BR001', N'TY002', N'pr056-1.jpg;pr056-2.jpg;pr056-3.jpg;pr056-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR016', N'Stone Table cheap', CAST(299.00 AS Decimal(18, 2)), N'CA001', N'BR001', N'TY001', N'pr057-1.jpg;pr057-2.jpg;pr057-3.jpg;pr057-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'Hot', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR017', N'Glass Table', CAST(99.00 AS Decimal(18, 2)), N'CA001', N'BR005', N'TY001', N'pr058-1.jpg;pr058-2.jpg;pr058-3.jpg;pr058-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'Hot', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR018', N'Table Wood rare 111', CAST(999.00 AS Decimal(18, 2)), N'CA001', N'BR005', N'TY003', N'pr059-1.jpg;pr059-2.jpg;pr059-3.jpg;pr059-4.jpg', N'Having been a technology company with a long history and sprawling businesses, Toshiba has been a household name in Japan and looked upon as a symbol of the country''s technological prowess, though its reputation was heavily damaged following the accounting scandal in 2015 and the bankruptcy of Westinghouse in 2017, by when it had to shed a myriad number of its valuable or underperforming businesses, essentially eradicating the company''s century-long presence in consumer markets', N'Hot', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR019', N'Sofa luxury 11', CAST(120.00 AS Decimal(18, 2)), N'CA002', N'BR005', N'TY003', N'pr060-1.jpg;pr060-2.jpg;pr060-3.jpg;pr060-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR020', N'Sofa luxury 22', CAST(140.00 AS Decimal(18, 2)), N'CA002', N'BR001', N'TY002', N'pr061-1.jpg;pr061-2.jpg;pr061-3.jpg;pr061-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'Hot', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR021', N'Sofa luxury 33', CAST(199.00 AS Decimal(18, 2)), N'CA002', N'BR001', N'TY003', N'pr062-1.jpg;pr062-2.jpg;pr062-3.jpg;pr062-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR022', N'Sofa cheap', CAST(599.00 AS Decimal(18, 2)), N'CA006', N'BR006', N'TY001', N'pr063-1.jpg;pr063-2.jpg;pr063-3.jpg;pr063-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR023', N'Sofa cheap 22', CAST(399.00 AS Decimal(18, 2)), N'CA002', N'BR006', N'TY001', N'pr064-1.jpg;pr064-2.jpg;pr064-3.jpg;pr064-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR024', N'Sofa  Table cheap 33', CAST(199.00 AS Decimal(18, 2)), N'CA006', N'BR006', N'TY001', N'pr065-1.jpg;pr065-2.jpg;pr065-3.jpg;pr065-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
INSERT [dbo].[Products] ([ProductID], [ProductName], [Price], [CategoryID], [BrandID], [TypeID], [ProductImages], [Descriptions], [Feature], [ProductState]) VALUES (N'PR025', N'Sofa cheap 44', CAST(450.00 AS Decimal(18, 2)), N'CA002', N'BR006', N'TY001', N'pr066-1.jpg;pr066-2.jpg;pr066-3.jpg;pr066-4.jpg', N'Supersize your silhouette in a padded jacket, stick to sporty styling with a bomber, or protect yourself from the elements in a plastic raincoat. For a more luxe layering piece, faux fur coats come in fondant shades and longline duster coats give your look an androgynous edge.', N'New', 1)
GO
SET IDENTITY_INSERT [dbo].[Ratings] ON 

INSERT [dbo].[Ratings] ([RatingID], [ProductID], [CustomerEmail], [Rate], [RatingDate]) VALUES (38, N'PR002', N'giaphuc@gmail.com', 4, CAST(N'2020-12-27' AS Date))
SET IDENTITY_INSERT [dbo].[Ratings] OFF
GO
INSERT [dbo].[Types] ([TypeID], [TypeName], [Descriptions], [TypeState]) VALUES (N'TY001', N'Cheap', N'', 1)
INSERT [dbo].[Types] ([TypeID], [TypeName], [Descriptions], [TypeState]) VALUES (N'TY002', N'Normal', N'', 1)
INSERT [dbo].[Types] ([TypeID], [TypeName], [Descriptions], [TypeState]) VALUES (N'TY003', N'Premium', N'', 1)
GO
SET IDENTITY_INSERT [dbo].[Wishlist] ON 

INSERT [dbo].[Wishlist] ([WishlistID], [CustomerEmail], [ProductID], [Quantity]) VALUES (20, N'giaphuc@gmail.com', N'PR006', 1)
INSERT [dbo].[Wishlist] ([WishlistID], [CustomerEmail], [ProductID], [Quantity]) VALUES (22, N'trungtruc@gmail.com', N'PR006', 1)
SET IDENTITY_INSERT [dbo].[Wishlist] OFF
GO
ALTER TABLE [dbo].[Admins] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[Admins] ADD  DEFAULT ((1)) FOR [AdminState]
GO
ALTER TABLE [dbo].[Brands] ADD  DEFAULT ((1)) FOR [BrandState]
GO
ALTER TABLE [dbo].[Categories] ADD  DEFAULT ((1)) FOR [CategoryState]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT ((1)) FOR [Gender]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT (getdate()) FOR [CreatedDate]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT ((1)) FOR [CustomerState]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT (getdate()) FOR [OrderDate]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ((1)) FOR [OrderState]
GO
ALTER TABLE [dbo].[OrdersDetails] ADD  DEFAULT ((1)) FOR [OdState]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT ((1)) FOR [ProductState]
GO
ALTER TABLE [dbo].[Ratings] ADD  DEFAULT (getdate()) FOR [RatingDate]
GO
ALTER TABLE [dbo].[Types] ADD  DEFAULT ((1)) FOR [TypeState]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([CustomerEmail])
REFERENCES [dbo].[Customers] ([Email])
GO
ALTER TABLE [dbo].[OrdersDetails]  WITH CHECK ADD FOREIGN KEY([OrderID])
REFERENCES [dbo].[Orders] ([OrderID])
GO
ALTER TABLE [dbo].[OrdersDetails]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([BrandID])
REFERENCES [dbo].[Brands] ([BrandID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([CategoryID])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([TypeID])
REFERENCES [dbo].[Types] ([TypeID])
GO
ALTER TABLE [dbo].[Ratings]  WITH CHECK ADD FOREIGN KEY([CustomerEmail])
REFERENCES [dbo].[Customers] ([Email])
GO
ALTER TABLE [dbo].[Ratings]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[Wishlist]  WITH CHECK ADD FOREIGN KEY([CustomerEmail])
REFERENCES [dbo].[Customers] ([Email])
GO
ALTER TABLE [dbo].[Wishlist]  WITH CHECK ADD FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
USE [master]
GO
ALTER DATABASE [FurnitureStore] SET  READ_WRITE 
GO
