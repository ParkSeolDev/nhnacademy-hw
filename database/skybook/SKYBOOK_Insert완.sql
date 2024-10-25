DROP DATABASE IF EXISTS `SKYBOOK`;
CREATE DATABASE SKYBOOK;
USE SKYBOOK;

-- CREATE START
-- 고객분류
DROP TABLE IF EXISTS `CustomerType`;

CREATE TABLE `CustomerType` (
	`CustomerTypeNo`	INT	NOT NULL PRIMARY KEY,
	`CustomerTypeName`	varchar(4)	NOT NULL
);

-- 개인고객상태
DROP TABLE IF EXISTS `CustomerStatus`;

CREATE TABLE `CustomerStatus` (
	`CustomerStatusId`	int	NOT NULL PRIMARY KEY,
	`StatusName`	varchar(10)	NOT NULL
);

-- 소셜 분류
DROP TABLE IF EXISTS `SocialType`;

CREATE TABLE `SocialType` (
	`SocialTypeNo`	INT	NOT NULL PRIMARY KEY,
	`SocialTypeName`	varchar(10)	NOT NULL
);

-- 개인고객분류
DROP TABLE IF EXISTS `PersonalCustomerType`;

CREATE TABLE `PersonalCustomerType` (
	`PersonalCustomerTypeID`	INT	NOT NULL PRIMARY KEY,
	`PersonalCustomerTypeName`	varchar(10)	NOT NULL
);

-- 발권상태
DROP TABLE IF EXISTS `TicketingStatus`;

CREATE TABLE `TicketingStatus` (
	`TicketingStatusID`	int	NOT NULL PRIMARY KEY,
	`TicketingStatusName`	varchar(10)	NOT NULL
);

-- 결제수단
DROP TABLE IF EXISTS `PaymentType`;

CREATE TABLE `PaymentType` (
	`PaymentTypeID`	int	NOT NULL PRIMARY KEY,
	`PaymentTypeName`	varchar(10)	NOT NULL
);

-- 쿠폰타입
DROP TABLE IF EXISTS `CouponType`;

CREATE TABLE `CouponType` (
	`CouponTypeID`	int	NOT NULL PRIMARY KEY,
	`CouponTypeName`	varchar(10)	NOT NULL
);

-- 제휴상품분류
DROP TABLE IF EXISTS `PartnerProductType`;

CREATE TABLE `PartnerProductType` (
	`PartnerProductTypeID`	INT	NOT NULL PRIMARY KEY,
	`PartnerProductTypeName`	varchar(50)	NOT NULL
);

-- 액세서리 분류
DROP TABLE IF EXISTS `AccType`;

CREATE TABLE `AccType` (
	`AccTypeId`	int	NOT NULL PRIMARY KEY,
	`AccName`	varchar(20)	NOT NULL
);

-- 부가서비스분류
DROP TABLE IF EXISTS `ExtraServiceType`;

CREATE TABLE `ExtraServiceType` (
	`ExtraServiceTypeID`	INT	NOT NULL PRIMARY KEY,
	`ExtraServiceTypeName`	varchar(10)	NOT NULL
);

-- 상품분류
DROP TABLE IF EXISTS `ProductType`;

CREATE TABLE `ProductType` (
	`ProductTypeId`	int	NOT NULL PRIMARY KEY,
	`ProductTypeName`	varchar(20)	NOT NULL
);

-- atc advisory
DROP TABLE IF EXISTS `ATC Advisory`;

CREATE TABLE `ATC Advisory` (
	`FlightStatus`	int	NOT NULL PRIMARY KEY,
	`FlightStatusName`	varchar(30)	NOT NULL
);

-- 시즌
DROP TABLE IF EXISTS `Season`;

CREATE TABLE `Season` (
	`SeasonId`	int	NOT NULL PRIMARY KEY,
	`SeasonName`	varchar(20)	NOT NULL
);

-- 항공사
 DROP TABLE IF EXISTS `Airline`;

CREATE TABLE `Airline` (
	`AirlineId`	int	NOT NULL PRIMARY KEY,
	`AirlineName`	varchar(50)	NOT NULL
);

-- 항공기
DROP TABLE IF EXISTS `Aircraft`;

CREATE TABLE `Aircraft` (
	`AircraftNo`	int	NOT NULL PRIMARY KEY,
	`KindOfAircraft`	varchar(50)	NOT NULL,
	`AirlineId`	int	NOT NULL
);

-- 상품
DROP TABLE IF EXISTS `Product`;

CREATE TABLE `Product` (
	`ProductId`	int	NOT NULL PRIMARY KEY auto_increment,
	`ProductTypeId`	int	NOT NULL,
	`ProductSoldStatus`	varchar(20)	NOT NULL
);

-- 제휴상품
DROP TABLE IF EXISTS `PartnerProduct`;

CREATE TABLE `PartnerProduct` (
	`ProductId`	int	NOT NULL,
	`PartnerProductName`	varchar(50)	NOT NULL,
	`Description`	varchar(200)	NULL,
	`StartDate`	date	NOT NULL,
	`EndDate`	date	NULL,
	`PartnerProductTypeID`	INT	NOT NULL
);

-- 액세서리
DROP TABLE IF EXISTS `Acc`;

CREATE TABLE `Acc` (
	`AccId`	int	NOT NULL,
	`AccTypeId`	int	NOT NULL,
	`Price`	int	NOT NULL
);

-- 결합상품
DROP TABLE IF EXISTS `Package`;

CREATE TABLE `Package` (
	`ProductId`	int	NOT NULL,
	`PackageName`	varchar(20)	NOT NULL
);

-- 결합상품구성상세
DROP TABLE IF EXISTS `PackageDetails`;

CREATE TABLE `PackageDetails` (
	`ProductId`	int	NOT NULL,
	`ProductId2` int NOT NULL
);

-- 국가
DROP TABLE IF EXISTS `Country`;

CREATE TABLE `Country` (
	`CountryId`	int	NOT NULL PRIMARY KEY,
	`CountryName`	varchar(50)	NOT NULL unique
);

-- 공항
DROP TABLE IF EXISTS `Airport`;

CREATE TABLE `Airport` (
	`AirportId`	int	NOT NULL PRIMARY KEY auto_increment,
	`AirportName`	varchar(50)	NOT NULL,
	`CountryId`	int	NOT NULL
);

-- 운항경로
DROP TABLE IF EXISTS `FlightWay`;

CREATE TABLE `FlightWay` (
	`FlightWayId`	int	NOT NULL PRIMARY KEY auto_increment,
	`Departure`	int	NOT NULL,
	`Arrival`	int	NOT NULL
);

-- 운항계획
DROP TABLE IF EXISTS `FlightPlan`;

CREATE TABLE `FlightPlan` (
	`FlightPlanNo`	int	NOT NULL	PRIMARY KEY auto_increment,
	`AircraftNo`	int	NOT NULL,
	`FlightWayId`	int	NOT NULL,
	`SeasonId`	int	NOT NULL,
	`FlightDate`	Datetime	NOT NULL,
	`SalesStartDate`	Datetime	NOT NULL
);

-- 항공권
DROP TABLE IF EXISTS `FlightTicket`;

CREATE TABLE `FlightTicket` (
	`FlightId`	int	NOT NULL,
	`FlightPlanNo`	int	NOT NULL,
	`FlightStatus`	int	NOT NULL,
	`Price`	int	NOT NULL
);

-- 승무원
DROP TABLE IF EXISTS `Crew`;

CREATE TABLE `Crew` (
	`CrewId`	int	NOT NULL PRIMARY KEY auto_increment,
	`AirlineId`	int	NOT NULL,
	`Name`	varchar(50)	NOT NULL
);

-- 고객
DROP TABLE IF EXISTS `Customer`;
CREATE TABLE `Customer` (
	`CustomerID`	INT	NOT NULL	PRIMARY KEY auto_increment,
	`CustomerTypeNo`	INT	NOT NULL
);

-- 개인고객
DROP TABLE IF EXISTS `PersonalCustomer`;

CREATE TABLE `PersonalCustomer` (
	`PersonalCustomerID`	INT	NOT NULL,
	`KorName`	varchar(30)	NOT NULL,
	`EngName`	varchar(30)	NOT NULL,
	`Email`	varchar(30)	NOT NULL	 UNIQUE,
	`PhoneNumber`	varchar(30)	NOT NULL	 UNIQUE,
	`ID`	varchar(30)	NOT NULL	 UNIQUE,
	`Password`	varchar(20)	NOT NULL,
	`Point`	INT	NOT NULL	DEFAULT 0,
	`PersonalCustomerTypeID`	INT	NOT NULL,
	`CustomerStatusId`	int	NOT NULL
);

-- 법인고객
DROP TABLE IF EXISTS `CorporateCustomer`;

CREATE TABLE `CorporateCustomer` (
	`CorporateCustomerID`	INT	NOT NULL ,
	`RepresentativeName_kor`	varchar(20)	NULL,
	`RepresentativeName_eng`	varchar(20)	NOT NULL,
	`CompanyName`	varchar(20)	NOT NULL,
	`BusinessRegistrationNo`	varchar(30)	NOT NULL UNIQUE,
	`CompanyAddress`	varchar(30)	NULL,
	`CompanyEmail`	varchar(30)	NOT NULL,
	`Active`	varchar(1)	NOT NULL	DEFAULT  'Y'
);

-- 법인우대혜택
DROP TABLE IF EXISTS `CorporateBenefit`;

CREATE TABLE `CorporateBenefit` (
	`CorporateCustomerID`	INT	NOT NULL,
	`PriorityBoarding`	varchar(1)	NOT NULL	DEFAULT 'N',
	`SeatUpgrade`	varchar(1)	NOT NULL	DEFAULT 'N',
	`ExtraBaggageAllowance`	INT	NOT NULL	DEFAULT 0,
	`LoungeAccess`	varchar(1)	NOT NULL	DEFAULT 'N',
	`PreferredSeating`	varchar(1)	NOT NULL	DEFAULT 'N'
);

-- 법인고객직원
DROP TABLE IF EXISTS `CorporateCrew`;

CREATE TABLE `CorporateCrew` (
	`PersonalCustomerID`	INT	NOT NULL,
	`CorporateCustomerID`	INT	NOT NULL
);

-- 고객소셜정보
DROP TABLE IF EXISTS `CustomerSocialDetails`;

CREATE TABLE `CustomerSocialDetails` (
	`CustomerID`	INT	NOT NULL,
	`SocialTypeNo`	INT	NOT NULL,
	`SocialID`	varchar(30)	NOT NULL,
	`SocailPassword`	varchar(30)	NOT NULL
);

-- 항공권예약정보
DROP TABLE IF EXISTS `PNR`;

CREATE TABLE `PNR` (
	`PNRId`	int	NOT NULL PRIMARY KEY,
	`CustomerID`	INT	NOT NULL
);

-- 탑승객
DROP TABLE IF EXISTS `PAX`;

CREATE TABLE `PAX` (
	`PassengerId`	int	NOT NULL PRIMARY KEY auto_increment,
	`PNRId`	int	NOT NULL,
	`Name`	varchar(20)	NOT NULL,
	`Age`	int	NOT NULL,
	`PhoneNumber`	varchar(30)	NOT NULL
);

-- 여행일정
DROP TABLE IF EXISTS `SEG`;

CREATE TABLE `SEG` (
	`SEGId`	int	NOT NULL	PRIMARY KEY auto_increment,
	`PNRId`	int	NOT NULL,
	`FlightId`	int	NOT NULL
);

-- 특별서비스요청
DROP TABLE IF EXISTS `SSR`;

CREATE TABLE `SSR` (
	`PassengerId`	int	NOT NULL,
	`ResquestMessage`	varchar(100)	NOT NULL,
	`SEGId`	int	NOT NULL
);

-- 부가서비스
DROP TABLE IF EXISTS `ExtraService`;

CREATE TABLE `ExtraService` (
	`ProductId`	int	NOT NULL,
	`AirportId`	int	NOT NULL,
	`ExtraServiceTypeID`	INT	NOT NULL,
	`ExtraServiceName_kor`	varchar(20)	NULL,
	`ExtraServiceName_eng`	varchar(20)	NOT NULL,
	`Price`	INT	NOT NULL
);

-- 부가서비스예약내역
DROP TABLE IF EXISTS `ExtraServiceReservation`;

CREATE TABLE `ExtraServiceReservation` (
	`ESRId`	int	NOT NULL	PRIMARY KEY auto_increment,
	`PassengerId`	int	NOT NULL,
	`ProductId`	int	NOT NULL,
	`SEGId`	int	NOT NULL
);

-- 선호비행정보	
DROP TABLE IF EXISTS `FlightPreferences`;

CREATE TABLE `FlightPreferences` (
	`CustomerID`	INT	NOT NULL,
	`PreferredSeatType`	varchar(10)	NULL,
	`PreferredSeat`	varchar(10)	NULL,
	`PreferredDepartureAirportID`	int	NOT NULL,
	`PreferredArrivalAirportID`	int	NOT NULL,
	`PreferredProductId`	int	NOT NULL
);

-- 승무원 비행계획
DROP TABLE IF EXISTS `CrewFlightPlan`;

CREATE TABLE `CrewFlightPlan` (
	`FlightPlanNo`	int	NOT NULL,
	`CrewId` int NOT NULL
);

-- 티켓
DROP TABLE IF EXISTS `Ticket`;

CREATE TABLE `Ticket` (
	`TicketID`	int	NOT NULL	PRIMARY KEY auto_increment,
	`PassengerId`	int	NOT NULL,
	`TicketingStatusID`	int	NOT NULL
);

-- 쿠폰
DROP TABLE IF EXISTS `Coupon`;

CREATE TABLE `Coupon` (
	`CouponID`	int	NOT NULL	PRIMARY KEY auto_increment,
	`CouponName`	varchar(20)	NOT NULL,
	`CouponTypeID`	int	NOT NULL,
	`DiscountProductId`	int	NOT NULL,
	`Discount`	int	NOT NULL,
	`VaildFrom`	date	NOT NULL,
	`VaildTo`	date	NOT NULL
);

-- 결제
DROP TABLE IF EXISTS `Payment`;

CREATE TABLE `Payment` (
	`PaymentID`	int	NOT NULL	PRIMARY KEY auto_increment,
	`Fare`	int	NOT NULL,
	`Tax`	int	NOT NULL,
	`CouponID`	int	NOT NULL,
	`TicketID`	int	NOT NULL,
	`PaymentTypeID`	int	NOT NULL
);

-- 환불
DROP TABLE IF EXISTS `Refund`;

CREATE TABLE `Refund` (
	`PaymentID`	int	NOT NULL,
	`Fare`	int	NOT NULL,
	`Tax`	int	NOT NULL
);

-- ALTER START
ALTER TABLE Customer ADD CONSTRAINT FK_CustomerType_TO_Customer_1 FOREIGN KEY (
	CustomerTypeNo
)
REFERENCES CustomerType (
	CustomerTypeNo
);

ALTER TABLE CorporateCustomer ADD CONSTRAINT FK_Customer_TO_CorporateCustomer_1 FOREIGN KEY (
	CorporateCustomerID
)
REFERENCES Customer (
	CustomerID
);

ALTER TABLE PersonalCustomer ADD CONSTRAINT FK_Customer_TO_PersonalCustomer_1 FOREIGN KEY (
	PersonalCustomerID
)
REFERENCES Customer (
	CustomerID
);

ALTER TABLE PersonalCustomer ADD CONSTRAINT FK_PersonalCustomerType_TO_PersonalCustomer_1 FOREIGN KEY (
	PersonalCustomerTypeID
)
REFERENCES PersonalCustomerType (
	PersonalCustomerTypeID
);

ALTER TABLE PersonalCustomer ADD CONSTRAINT FK_CustomerStatus_TO_PersonalCustomer_1 FOREIGN KEY (
	CustomerStatusId
)
REFERENCES CustomerStatus (
	CustomerStatusId
);

ALTER TABLE Product ADD CONSTRAINT FK_ProductType_TO_Product_1 FOREIGN KEY (
	ProductTypeId
)
REFERENCES ProductType (
	ProductTypeId
);

ALTER TABLE FlightPlan ADD CONSTRAINT FK_Aircraft_TO_FlightPlan_1 FOREIGN KEY (
	AircraftNo
)
REFERENCES Aircraft (
	AircraftNo
);

ALTER TABLE FlightPlan ADD CONSTRAINT FK_FlightWay_TO_FlightPlan_1 FOREIGN KEY (
	FlightWayId
)
REFERENCES FlightWay (
	FlightWayId
);

ALTER TABLE FlightPlan ADD CONSTRAINT FK_Season_TO_FlightPlan_1 FOREIGN KEY (
	SeasonId
)
REFERENCES Season (
	SeasonId
);

ALTER TABLE Aircraft ADD CONSTRAINT FK_Airline_TO_Aircraft_1 FOREIGN KEY (
	AirlineId
)
REFERENCES Airline (
	AirlineId
);

ALTER TABLE Airport ADD CONSTRAINT FK_Country_TO_Airport_1 FOREIGN KEY (
	CountryId
)
REFERENCES Country (
	CountryId
);

ALTER TABLE Crew ADD CONSTRAINT FK_Airline_TO_Crew_1 FOREIGN KEY (
	AirlineId
)
REFERENCES Airline (
	AirlineId
);

ALTER TABLE CustomerSocialDetails ADD CONSTRAINT FK_PersonalCustomer_TO_CustomerSocialDetails_1 FOREIGN KEY (
	CustomerID
)
REFERENCES PersonalCustomer (
	PersonalCustomerID
);

ALTER TABLE CustomerSocialDetails ADD CONSTRAINT FK_SocialType_TO_CustomerSocialDetails_1 FOREIGN KEY (
	SocialTypeNo
)
REFERENCES SocialType (
	SocialTypeNo
);

ALTER TABLE CorporateCrew ADD CONSTRAINT FK_PersonalCustomer_TO_CorporateCrew_1 FOREIGN KEY (
	PersonalCustomerID
)
REFERENCES PersonalCustomer (
	PersonalCustomerID
);

ALTER TABLE CorporateCrew ADD CONSTRAINT FK_CorporateCustomer_TO_CorporateCrew_1 FOREIGN KEY (
	CorporateCustomerID
)
REFERENCES CorporateCustomer (
	CorporateCustomerID
);

ALTER TABLE CorporateBenefit ADD CONSTRAINT FK_CorporateCustomer_TO_CorporateBenefit_1 FOREIGN KEY (
	CorporateCustomerID
)
REFERENCES CorporateCustomer (
	CorporateCustomerID
);

ALTER TABLE FlightPreferences ADD CONSTRAINT FK_Customer_TO_FlightPreferences_1 FOREIGN KEY (
	CustomerID
)
REFERENCES Customer (
	CustomerID
);

ALTER TABLE FlightPreferences ADD CONSTRAINT FK_Airport_TO_FlightPreferences_1 FOREIGN KEY (
	PreferredDepartureAirportID
)
REFERENCES Airport (
	AirportId
);

ALTER TABLE FlightPreferences ADD CONSTRAINT FK_Airport_TO_FlightPreferences_2 FOREIGN KEY (
	PreferredArrivalAirportID
)
REFERENCES Airport (
	AirportId
);

ALTER TABLE FlightPreferences ADD CONSTRAINT FK_Product_TO_FlightPreferences_1 FOREIGN KEY (
	PreferredProductId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE FlightWay ADD CONSTRAINT FK_Airport_TO_FlightWay_1 FOREIGN KEY (
	Departure
)
REFERENCES Airport (
	AirportId
);

ALTER TABLE FlightWay ADD CONSTRAINT FK_Airport_TO_FlightWay_2 FOREIGN KEY (
	Arrival
)
REFERENCES Airport (
	AirportId
);

ALTER TABLE CrewFlightPlan ADD CONSTRAINT FK_FlightPlan_TO_CrewFlightPlan_1 FOREIGN KEY (
	FlightPlanNo
)
REFERENCES FlightPlan (
	FlightPlanNo
);

ALTER TABLE CrewFlightPlan ADD CONSTRAINT FK_Crew_TO_CrewFlightPlan_1 FOREIGN KEY (
	CrewId
)
REFERENCES Crew (
	CrewId
);

ALTER TABLE FlightTicket ADD CONSTRAINT FK_Product_TO_FlightTicket_1 FOREIGN KEY (
	FlightId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE FlightTicket ADD CONSTRAINT FK_FlightPlan_TO_FlightTicket_1 FOREIGN KEY (
	FlightPlanNo
)
REFERENCES FlightPlan (
	FlightPlanNo
);

ALTER TABLE FlightTicket ADD CONSTRAINT FK_ATC_Advisory_TO_FlightTicket_1 FOREIGN KEY (
	FlightStatus
)
REFERENCES `ATC Advisory` (
	FlightStatus
);

ALTER TABLE ExtraService ADD CONSTRAINT FK_Product_TO_ExtraService_1 FOREIGN KEY (
	ProductId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE ExtraService ADD CONSTRAINT FK_Airport_TO_ExtraService_1 FOREIGN KEY (
	AirportId
)
REFERENCES Airport (
	AirportId
);

ALTER TABLE ExtraService ADD CONSTRAINT FK_ExtraServiceType_TO_ExtraService_1 FOREIGN KEY (
	ExtraServiceTypeID
)
REFERENCES ExtraServiceType (
	ExtraServiceTypeID
);

ALTER TABLE PartnerProduct ADD CONSTRAINT FK_Product_TO_PartnerProduct_1 FOREIGN KEY (
	ProductId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE PartnerProduct ADD CONSTRAINT FK_PartnerProductType_TO_PartnerProduct_1 FOREIGN KEY (
	PartnerProductTypeID
)
REFERENCES PartnerProductType (
	PartnerProductTypeID
);

ALTER TABLE Acc ADD CONSTRAINT FK_Product_TO_Acc_1 FOREIGN KEY (
	AccId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE Acc ADD CONSTRAINT FK_AccType_TO_Acc_1 FOREIGN KEY (
	AccTypeId
)
REFERENCES AccType (
	AccTypeId
);

ALTER TABLE Package ADD CONSTRAINT FK_Product_TO_Package_1 FOREIGN KEY (
	ProductId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE PackageDetails ADD CONSTRAINT FK_Package_TO_PackageDetails_1 FOREIGN KEY (
	ProductId
)
REFERENCES Package (
	ProductId
);

ALTER TABLE PackageDetails ADD CONSTRAINT FK_Product_TO_PackageDetails_1 FOREIGN KEY (
	ProductId2
)
REFERENCES Product (
	ProductId
);

ALTER TABLE Ticket ADD CONSTRAINT FK_PAX_TO_Ticket_1 FOREIGN KEY (
	PassengerId
)
REFERENCES PAX (
	PassengerId
);

ALTER TABLE Ticket ADD CONSTRAINT FK_TicketingStatus_TO_Ticket_1 FOREIGN KEY (
	TicketingStatusID
)
REFERENCES TicketingStatus (
	TicketingStatusID
);

ALTER TABLE Coupon ADD CONSTRAINT FK_CouponType_TO_Coupon_1 FOREIGN KEY (
	CouponTypeID
)
REFERENCES CouponType (
	CouponTypeID
);

ALTER TABLE Coupon ADD CONSTRAINT FK_Product_TO_Coupon_1 FOREIGN KEY (
	DiscountProductId
)
REFERENCES Product (
	ProductId
);

ALTER TABLE PNR ADD CONSTRAINT FK_Customer_TO_PNR_1 FOREIGN KEY (
	CustomerID
)
REFERENCES Customer (
	CustomerID
);

ALTER TABLE PAX ADD CONSTRAINT FK_PNR_TO_PAX_1 FOREIGN KEY (
	PNRId
)
REFERENCES PNR (
	PNRId
);

ALTER TABLE Payment ADD CONSTRAINT FK_Coupon_TO_Payment_1 FOREIGN KEY (
	CouponID
)
REFERENCES Coupon (
	CouponID
);

ALTER TABLE Payment ADD CONSTRAINT FK_Ticket_TO_Payment_1 FOREIGN KEY (
	TicketID
)
REFERENCES Ticket (
	TicketID
);

ALTER TABLE Payment ADD CONSTRAINT FK_PaymentType_TO_Payment_1 FOREIGN KEY (
	PaymentTypeID
)
REFERENCES PaymentType (
	PaymentTypeID
);

ALTER TABLE ExtraServiceReservation ADD CONSTRAINT FK_PAX_TO_ExtraServiceReservation_1 FOREIGN KEY (
	PassengerId
)
REFERENCES PAX (
	PassengerId
);

ALTER TABLE ExtraServiceReservation ADD CONSTRAINT FK_ExtraService_TO_ExtraServiceReservation_1 FOREIGN KEY (
	ProductId
)
REFERENCES ExtraService (
	ProductId
);

ALTER TABLE ExtraServiceReservation ADD CONSTRAINT FK_SEG_TO_ExtraServiceReservation_1 FOREIGN KEY (
	SEGId
)
REFERENCES SEG (
	SEGId
);

ALTER TABLE SSR ADD CONSTRAINT FK_PAX_TO_SSR_1 FOREIGN KEY (
	PassengerId
)
REFERENCES PAX (
	PassengerId
);

ALTER TABLE SSR ADD CONSTRAINT FK_SEG_TO_SSR_1 FOREIGN KEY (
	SEGId
)
REFERENCES SEG (
	SEGId
);

ALTER TABLE SEG ADD CONSTRAINT FK_PNR_TO_SEG_1 FOREIGN KEY (
	PNRId
)
REFERENCES PNR (
	PNRId
);

ALTER TABLE SEG ADD CONSTRAINT FK_FlightTicket_TO_SEG_1 FOREIGN KEY (
	FlightId
)
REFERENCES FlightTicket (
	FlightId
);

ALTER TABLE Refund ADD CONSTRAINT FK_Payment_TO_Refund_1 FOREIGN KEY (
	PaymentID
)
REFERENCES Payment (
	PaymentID
);

-- INSERT START
-- CustomerType 더미 데이터 삽입
INSERT INTO CustomerType VALUES
(1, '개인고객'),
(2, '법인고객');

-- CustomerStatus 더미 데이터 삽입
INSERT INTO CustomerStatus VALUES
(1, '정상'),
(2, '휴면'),
(3, '탈퇴');

-- PersonalCustomerType 더미 데이터 삽입
INSERT INTO PersonalCustomerType VALUES
(1, '정회원'),
(2, '간편가입회원');

-- SocialType 더미 데이터 삽입
INSERT INTO SocialType VALUES
(1, '네이버'),
(2, '카카오'),
(3, '제주항공'),
(4, '페이코');

-- TicketingStatus 더미 데이터 삽입
INSERT INTO TicketingStatus VALUES
(1, '결제대기'),
(2, '결제완료'),
(3, '환불요청'),
(4, '환불완료');

-- PaymentType 더미 데이터 삽입
INSERT INTO PaymentType VALUES
(1, '일반'),
(2, '포인트'),
(3, '기프티켓');

-- CouponType 더미 데이터 삽입
INSERT INTO CouponType VALUES
(1, '금액권'),
(2, '할인율권');

-- ProductType 더미 데이터 삽입
INSERT INTO ProductType VALUES
(1, '항공권'),
(2, '부가서비스'),
(3, '제휴상품'),
(4, '액세서리'),
(5, '결합상품');

-- ATC Advisory 더미 데이터 삽입
INSERT INTO `ATC Advisory` VALUES
(1, 'Scheduled'),
(2, 'On Time'),
(3, 'Delayed'),
(4, 'Cancelled');

-- Season 더미 데이터 삽입
INSERT INTO Season VALUES
(1, 'Peak Season'),
(2, 'Off-peak Season'),
(3, 'Summer Vacation'),
(4, 'Winter Holidays');

-- Airline 더미 데이터 삽입
INSERT INTO Airline VALUES
(1, 'Jeju Air');
-- (2, 'Asiana Airlines'),
-- (3, 'Korean Air'),
-- (4, 'Tway Air'),
-- (5, 'Jin Air'),
-- (6, 'Delta Air Lines'),
-- (7, 'Singapore Airlines'),
-- (8, 'Emirates'),
-- (9, 'Lufthansa');

-- Aircraft 더미 데이터 삽입
INSERT INTO Aircraft VALUES
(1, 'Boeing 787', 1),
(2, 'Airbus A330', 1),
(3, 'Boeing 747', 1),
(4, 'Boeing 777', 1),
(5, 'Airbus A350', 1),
(6, 'Airbus A380', 1);

-- Crew 더미 데이터 삽입
INSERT INTO Crew VALUES
(1, 1, 'John Smith'),
(2, 1, 'Emily Johnson'),
(3, 1, 'Michael Lee'),
(4, 1, 'Jessica Kim'),
(5, 1, 'David Park'),
(6, 1, 'Nathan Kim'),
(7, 1, 'Grace Park'),
(8, 1, 'Benjamin Lee'),
(9, 1, 'Chloe Kim'),
(10, 1, 'Jacob Park'),
(11, 1, 'Mason Kim'),
(12, 1, 'Mia Park'),
(13, 1, 'Ethan Lee'),
(14, 1, 'Amelia Kim'),
(15, 1, 'Liam Park');

-- Country 더미 데이터 삽입
INSERT INTO Country VALUES
(1, 'South Korea'),
(2, 'United States'),
(3, 'Japan'),
(4, 'China'),
(5, 'Germany'),
(6, 'France'),
(7, 'United Kingdom'),
(8, 'Italy'),
(9, 'Canada'),
(10, 'Brazil');
 
-- Airport 더미 데이터 삽입
INSERT INTO Airport VALUES
(1, 'Incheon International Airport', 1),
(2, 'Gimpo International Airport', 1),
(3, 'Los Angeles International Airport', 2),
(4, 'John F. Kennedy International Airport', 2),
(5, 'Narita International Airport', 3),
(6, 'Haneda Airport', 3),
(7, 'Beijing Capital International Airport', 4),
(8, 'Guangzhou Baiyun International Airport', 4),
(9, 'Frankfurt Airport', 5),
(10, 'Munich Airport', 5),
(11, 'Paris Charles de Gaulle Airport', 6),
(12, 'Orly Airport', 6),
(13, 'Heathrow Airport', 7),
(14, 'Gatwick Airport', 7),
(15, 'Leonardo da Vinci International Airport', 8),
(16, 'Malpensa Airport', 8),
(17, 'Toronto Pearson International Airport', 9),
(18, 'Vancouver International Airport', 9),
(19, 'São Paulo–Guarulhos International Airport', 10),
(20, 'Rio de Janeiro–Galeão International Airport', 10);

-- FlightWay 더미 데이터 삽입
INSERT INTO FlightWay VALUES
(1, 1, 3),
(2, 3, 1),
(3, 1, 5),
(4, 5, 1),
(5, 1, 7),
(6, 7, 1),
(7, 1, 9),
(8, 9, 1),
(9, 1, 11),
(10, 11, 1),
(11, 1, 13),
(12, 13, 1),
(13, 1, 15),
(14, 15, 1),
(15, 1, 17),
(16, 17, 1),
(17, 1, 19),
(18, 19, 1);

-- FlightPlan 더미 데이터 삽입
INSERT INTO FlightPlan(AircraftNo, FlightWayId, SeasonId, FlightDate, SalesStartDate) VALUES
(1, 1, 1, '2024-04-15 08:00:00', '2024-04-01 00:00:00'),
(2, 2, 1, '2024-04-15 10:00:00', '2024-04-01 00:00:00'),
(3, 3, 1, '2024-04-15 12:00:00', '2024-04-01 00:00:00'),
(4, 4, 1, '2024-04-15 14:00:00', '2024-04-01 00:00:00'),
(5, 5, 1, '2024-04-15 16:00:00', '2024-04-01 00:00:00'),
(6, 6, 1, '2024-04-15 18:00:00', '2024-04-01 00:00:00'),
(1, 7, 1, '2024-04-16 08:00:00', '2024-04-01 00:00:00'),
(2, 8, 1, '2024-04-16 10:00:00', '2024-04-01 00:00:00'),
(3, 9, 1, '2024-04-16 12:00:00', '2024-04-01 00:00:00'),
(4, 10, 1, '2024-04-16 14:00:00', '2024-04-01 00:00:00'),
(5, 11, 1, '2024-04-16 16:00:00', '2024-04-01 00:00:00'),
(6, 12, 1, '2024-04-16 18:00:00', '2024-04-01 00:00:00'),
(1, 13, 1, '2024-04-17 08:00:00', '2024-04-01 00:00:00'),
(2, 14, 1, '2024-04-17 10:00:00', '2024-04-01 00:00:00'),
(3, 15, 1, '2024-04-17 12:00:00', '2024-04-01 00:00:00'),
(4, 16, 1, '2024-04-17 14:00:00', '2024-04-01 00:00:00'),
(5, 17, 1, '2024-04-17 16:00:00', '2024-04-01 00:00:00'),
(6, 18, 1, '2024-04-17 18:00:00', '2024-04-01 00:00:00');

-- CrewFlightPlan 더미 데이터 삽입
INSERT INTO CrewFlightPlan VALUES
(1, 1), (1, 4), (1, 7), (1, 10), (1, 13),
(2, 2), (2, 5), (2, 8), (2, 11), (2, 14),
(3, 3), (3, 6), (3, 9), (3, 12), (3, 15),
(4, 4), (4, 7), (4, 10), (4, 13), (4, 1),
(5, 5), (5, 8), (5, 11), (5, 14), (5, 2),
(6, 6), (6, 9), (6, 12), (6, 15), (6, 3),
(7, 7), (7, 10), (7, 13), (7, 1), (7, 4),
(8, 8), (8, 11), (8, 14), (8, 2), (8, 5),
(9, 9), (9, 12), (9, 15), (9, 3), (9, 6),
(10, 10), (10, 13), (10, 1), (10, 4), (10, 7),
(11, 11), (11, 14), (11, 2), (11, 5), (11, 8),
(12, 12), (12, 15), (12, 3), (12, 6), (12, 9),
(13, 13), (13, 1), (13, 4), (13, 7), (13, 10),
(14, 14), (14, 2), (14, 5), (14, 8), (14, 11),
(15, 15), (15, 3), (15, 6), (15, 9), (15, 12),
(16, 1), (16, 4), (16, 7), (16, 10), (16, 13),
(17, 2), (17, 5), (17, 8), (17, 11), (17, 14),
(18, 3), (18, 6), (18, 9), (18, 12), (18, 15);

-- PartnerProductType 더미 데이터 삽입
INSERT INTO PartnerProductType (PartnerProductTypeID, PartnerProductTypeName) VALUES
(1, 'Hotel/Accommodation'),
(2, 'Insurance Product'),
(3, 'Lounge'),
(4, 'Day Tour'),
(5, 'Wi-Fi'),
(6, 'Rental Car'),
(7, 'Overseas Golf');

-- Product 테이블 더미 데이터 삽입
INSERT INTO Product (ProductId, ProductTypeId, ProductSoldStatus) VALUES
-- Flight Ticket (항공권, 총 18개)
(1, 1, 'Y'),
(2, 1, 'N'),
(3, 1, 'Y'),
(4, 1, 'Y'),
(5, 1, 'Y'),
(6, 1, 'Y'),
(7, 1, 'Y'),
(8, 1, 'Y'),
(9, 1, 'Y'),
(10, 1, 'Y'),
(11, 1, 'Y'),
(12, 1, 'Y'),
(13, 1, 'Y'),
(14, 1, 'Y'),
(15, 1, 'Y'),
(16, 1, 'Y'),
(17, 1, 'N'),
(18, 1, 'Y'),

-- Extra Service (부가서비스, 총 3개)
(19, 2, 'Y'),
(20, 2, 'Y'),
(21, 2, 'Y'),

-- Partner Product (제휴상품, 총 12개)
(22, 3, 'Y'),
(23, 3, 'Y'),
(24, 3, 'Y'),
(25, 3, 'Y'),
(26, 3, 'N'),
(27, 3, 'Y'),
(28, 3, 'Y'),
(29, 3, 'Y'),
(30, 3, 'Y'),
(31, 3, 'Y'),
(32, 3, 'Y'),
(33, 3, 'Y'),

-- Accessory (액세서리, 총 24개)
(34, 4, 'Y'),
(35, 4, 'Y'),
(36, 4, 'Y'),
(37, 4, 'Y'),
(38, 4, 'Y'),
(39, 4, 'Y'),
(40, 4, 'Y'),
(41, 4, 'Y'),
(42, 4, 'Y'),
(43, 4, 'N'),
(44, 4, 'N'),
(45, 4, 'N'),
(46, 4, 'Y'),
(47, 4, 'Y'),
(48, 4, 'Y'),
(49, 4, 'Y'),
(50, 4, 'Y'),
(51, 4, 'Y'),
(52, 4, 'Y'),
(53, 4, 'Y'),
(54, 4, 'N'),
(55, 4, 'Y'),
(56, 4, 'Y'),
(57, 4, 'Y'),

-- Package (결합상품, 총 5개)
(58, 5, 'N'),
(59, 5, 'Y'),
(60, 5, 'N'),
(61, 5, 'Y'),
(62, 5, 'Y');

-- PartnerProduct 더미 데이터 삽입
INSERT INTO PartnerProduct VALUES
(1, 'Budget Hotel', 'Affordable accommodation for budget travelers', '2024-04-15', '2024-12-31', 1),
(2, 'Medical Insurance', 'Specialized medical insurance coverage for travelers', '2024-04-15', '2025-04-15', 2),
(3, 'VIP Lounge Access', 'Exclusive access to VIP airport lounges', '2024-04-15', '2024-12-31', 3),
(4, 'Airport Lounge Pass', 'Access pass to airport lounges for relaxation and comfort', '2024-04-15', '2024-12-31', 3),
(5, 'Adventure Tour', 'Exciting adventure tour with experienced guides', '2024-04-15', '2024-12-31', 4),
(6, 'Cultural Tour', 'Immersive cultural tour to explore local traditions and heritage', '2024-04-15', '2024-12-31', 4),
(7, 'In-flight Wi-Fi Pass', 'Wi-Fi access during flights for uninterrupted connectivity', '2024-04-15', '2024-12-31', 5),
(8, 'Global Wi-Fi Subscription', 'Subscription-based Wi-Fi service for global connectivity', '2024-04-15', '2025-04-15', 5),
(9, 'Luxury Car Rental', 'Premium car rental service for luxury travel experiences', '2024-04-15', '2024-12-31', 6),
(10, 'SUV Rental', 'Spacious SUV rental for outdoor adventures and family trips', '2024-04-15', '2024-12-31', 6),
(11, 'Golf Course Membership', 'Membership to prestigious golf courses around the world', '2024-04-15', '2024-12-31', 7),
(12, 'Golf Lesson Package', 'Package including golf lessons with professional instructors', '2024-04-15', '2024-12-31', 7);

-- AccType 더미 데이터 삽입
INSERT INTO AccType VALUES
(1, 'Post-it'),
(2, 'Pen'),
(3, 'Neck Pillow'),
(4, 'Eye Mask'),
(5, 'Headphones'),
(6, 'Travel Blanket'),
(7, 'Travel Adapter'),
(8, 'Luggage Tag'),
(9, 'Travel Wallet'),
(10, 'Portable Charger');

-- Acc 더미 데이터 삽입
INSERT INTO Acc VALUES
(1, 1, 15000),  -- Neck Pillow
(2, 2, 8000),   -- Eye Mask
(3, 3, 25000),  -- Headphones
(4, 4, 20000),  -- Travel Blanket
(5, 5, 15000),  -- Travel Adapter
(6, 6, 5000),   -- Luggage Tag
(7, 7, 20000),  -- Travel Wallet
(8, 8, 30000),  -- Portable Charger
(9, 1, 12000),  -- Memory Foam Travel Pillow
(10, 2, 7000),  -- Sleep Mask with Earplugs
(11, 3, 18000), -- Noise Cancelling Headphones
(12, 4, 22000), -- Fleece Travel Blanket
(13, 5, 18000), -- Universal Travel Adapter
(14, 6, 4000),  -- Personalized Luggage Tag
(15, 7, 25000), -- RFID Travel Wallet
(16, 8, 35000), -- Solar Power Bank
(17, 1, 17000), -- Inflatable Neck Pillow
(18, 2, 6000),  -- Silk Sleep Mask
(19, 3, 30000), -- Wireless Bluetooth Headphones
(20, 4, 25000), -- Plush Travel Blanket
(21, 5, 12000), -- USB-C Travel Adapter
(22, 6, 4500),  -- Leather Luggage Tag
(23, 7, 30000), -- Passport Holder Wallet
(24, 8, 40000); -- Fast Charging Power Bank

-- ExtraServiceType 더미 데이터 삽입
INSERT INTO ExtraServiceType VALUES
(1, '우선 탑승 서비스'),
(2, '스피디 체크인'),
(3, '수하물 추가');

-- Customer 테이블 더미 데이터 삽입
INSERT INTO Customer (CustomerID, CustomerTypeNo) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 1),
(6, 1),
(7, 1),
(8, 2);

-- PNR 테이블에 더미 데이터 추가
INSERT INTO PNR VALUES
(1, 1),
(2, 2);

-- PAX 테이블에 더미 데이터 삽입
INSERT INTO PAX  VALUES
(1, 1, 'John Doe', 30, '010123456789'),
(2, 1, 'Jane Doe', 28, '010987654321'),
(3, 2, 'Alice Smith', 35, '010111222333'),
(4, 2, 'Bob Smith', 40, '010444555666'),
(5, 2, 'Emily Johnson', 25, '010777888999');

-- ExtraService 더미 데이터 삽입
INSERT INTO ExtraService (ProductId, AirportId, ExtraServiceTypeID, ExtraServiceName_kor, ExtraServiceName_eng, Price) VALUES
(1, 1, 1, '우선 탑승 서비스', 'Priority Boarding', 20000),
(2, 2, 2, '속편한 체크인 서비스', 'Speedy Check-in', 15000),
(3, 3, 3, '추가 수하물', 'Additional Baggage', 30000);

-- FlightTicket 테이블 더미 데이터 삽입
INSERT INTO FlightTicket VALUES
(1, 1, 1, 100000),
(2, 2, 2, 120000),
(3, 3, 3, 150000),
(4, 4, 4, 130000),
(5, 5, 1, 180000),
(6, 6, 2, 200000),
(7, 7, 3, 100000),
(8, 8, 4, 120000),
(9, 9, 1, 150000),
(10, 10, 2, 130000),
(11, 11, 3, 180000),
(12, 12, 4, 200000),
(13, 13, 1, 100000),
(14, 14, 2, 120000),
(15, 15, 3, 150000),
(16, 16, 4, 130000),
(17, 17, 1, 180000),
(18, 18, 2, 200000);

-- SEG 테이블에 더미 데이터 삽입
INSERT INTO SEG VALUES
(1, 1, 2),
(2, 1, 3),
(3, 2, 3);

-- ExtraServiceReservation 테이블에 더미 데이터 삽입
INSERT INTO ExtraServiceReservation VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3);

-- Package 테이블 더미 데이터 삽입
INSERT INTO Package (ProductId, PackageName) VALUES
(1, 'Economy Package'),
(2, 'Business Package'),
(3, 'Premium Package'),
(4, 'Family Package'),
(5, 'Luxury Package');

-- PackageDetails 테이블 더미 데이터 삽입
INSERT INTO PackageDetails (ProductId, ProductId2) VALUES
(1, 1), (1,19), (1,34),
(2,3), (2,22),(2,56),
(3, 2), (3, 23), (3, 40),
(4, 4), (4, 24), (4, 50),
(5, 5), (5, 25), (5, 45);

-- PersonalCustomer 테이블에 더미 데이터 삽입
INSERT INTO PersonalCustomer (PersonalCustomerID, KorName, EngName, Email, PhoneNumber, ID, Password, PersonalCustomerTypeID, CustomerStatusId) VALUES
(1, '홍길동', 'Hong Gil Dong', 'hong@example.com', '010123456789', 'hong123', 'password', 1, 1),
(2, '김철수', 'Kim Cheol Soo', 'kim@example.com', '010987654321', 'kim456', 'password', 2, 1),
(5, '이영희', 'Lee Young Hee', 'lee@example.com', '010567891234', 'lee789', 'password', 1, 1),
(6, '채상희', 'Chae Sang Hui', 'Chae@example.com', '010467891234', 'Chae789', 'password', 2, 1),
(7, '문희준', 'Moon Hui Joon', 'Moon@example.com', '010987891234', 'Moon789', 'password', 1, 1),
(8, '박설', 'Park Seol', 'Park@example.com', '010127891234', 'Park789', 'password', 1, 1);

-- CorporateCustomer 테이블에 더미 데이터 추가
INSERT INTO CorporateCustomer (CorporateCustomerID, RepresentativeName_kor, RepresentativeName_eng,
CompanyName, BusinessRegistrationNo, CompanyAddress, CompanyEmail) VALUES
(3, '이순신', 'Lee Soon Shin', 'GHI 주식회사', '111-22-33333', '광주광역시 동구 185', 'ghi@example.com'),
(4, '유관순', 'Yu Gwan Soon', 'JKL 주식회사', '444-55-66666', '광주광역시 남구 81', 'jkl@example.com');
    
-- CorporateCrew 테이블에 더미 데이터 추가
INSERT INTO CorporateCrew values
(6, 3),
(7, 3),
(8, 4);

-- CorporateBenefit 테이블에 더미 데이터 추가
INSERT INTO CorporateBenefit(CorporateCustomerID) values
(3),
(4);
    
-- CustomerSocialDetails 테이블에 더미 데이터 추가
INSERT INTO CustomerSocialDetails values
(2, 1, 'naverId', 'naverPassword'),
(6, 2, 'kakaoId', 'kakaoPassword');

-- Coupon 테이블에 더미 데이터 추가
INSERT INTO Coupon VALUES
(1, '할인쿠폰', 2, 1, 20, '2024-04-01', '2024-04-30'),
(2, '금액할인쿠폰', 1, 2, 5000, '2024-04-01', '2024-04-30');

-- SSR 테이블에 더미 데이터 삽입
INSERT INTO SSR VALUES
(1, '안녕하세요. 에어컨 좀 많이 틀지 마세요. 추워요', 1),
(2, '채식주의자에요.', 1),
(4, '휠체어 부탁드려요.', 2);

-- Ticket 테이블에 더미 데이터 삽입
INSERT INTO Ticket VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 2),
(4, 4, 3),
(5, 5, 4);

-- Payment 테이블에 더미 데이터 삽입
INSERT INTO Payment VALUES
(1, 100000, 20000, 1, 1, 1),
(2, 120000, 25000, 2, 2, 2),
(3, 150000, 30000, 1, 3, 1),
(4, 130000, 22000, 2, 4, 2),
(5, 180000, 35000, 1, 5, 1);

-- Refund 테이블에 더미 데이터 삽입
INSERT INTO Refund VALUES
(4, 130000, 22000),
(5, 180000, 35000);