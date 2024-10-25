CREATE database GOVERMENT24;
USE GOVERMENT24;

CREATE TABLE `Person` (
	`PersonId` int NOT NULL auto_increment PRIMARY KEY,
	`registerNo` varchar(14) NOT NULL,
    `Name`	varchar(10)	NOT NULL,
    `BirthDate` date NOT NULL,
	`GenderNo`	int	NOT NULL,
	`Email`	varchar(20)	NULL,
	`Phone`	varchar(15)	NULL
);

CREATE TABLE `Gender` (
	`GenderNo` int NOT NULL PRIMARY KEY,
	`GenderName` varchar(2)	NOT NULL
);

CREATE TABLE `FamilyRelation` (
	`FamilyRelationNo` int NOT NULL PRIMARY KEY,
	`FamilyRelationName` varchar(5)	NOT NULL
);

CREATE TABLE `ImmediateFamily` (
	`PersonId`	int	NOT NULL,
    `OtherPersonId`	int	NOT NULL,
	`FamilyRelationNo` int NOT NULL
);

CREATE TABLE `Address` (
	`AddressId` int NOT NULL PRIMARY KEY auto_increment,
	`ZipCode` int NOT NULL,
	`State` varchar(20) NOT NULL,
	`City` varchar(100) NOT NULL,
    `Road` varchar(100) NOT NULL
);

CREATE TABLE `AddressHistory` (
	`PersonId` int NOT NULL,
	`AddressId`	int	NOT NULL,
	`ChangeDate` date NOT NULL
);

CREATE TABLE `Household` (
	`HouseholdPersonId` int NOT NULL,
	`AddressId` int NOT NULL
);

CREATE TABLE `ChangeReason` (
	`ChangeReasonNo` int NOT NULL PRIMARY KEY,
	`ChangeReasonName` varchar(10) NOT NULL
);

CREATE TABLE `HouseholdRelation` (
	`HouseholdRelationNo` int NOT NULL PRIMARY KEY,
	`HouseholdName` varchar(10)	NOT NULL
);

CREATE TABLE `HouseholdHistory` (
	`HouseholdPersonId` int NOT NULL,
	`PersonId` int NOT NULL,
	`ChangeReasonNo` int NOT NULL,
	`HouseholdRelationNo` int NOT NULL,
	`ChangeDate` date NOT NULL
);

CREATE TABLE `Qualification` (
	`QualificationNo` int NOT NULL PRIMARY KEY,
	`QualificationName` varchar(10)	NOT NULL
);

CREATE TABLE `BirthLocation` (
	`BirthLocationNo` int NOT NULL PRIMARY KEY,
	`BirthLocationName` varchar(20)	NOT NULL
);

CREATE TABLE `Birth` (
	`PersonId` int NOT NULL,
	`BirthLocationNo` int NOT NULL,
	`QualificationNo` int NOT NULL,
	`BirthDatetime` datetime NOT NULL,
    `AddressId` int NOT NULL,
    `ReporterId` int NOT NULL,
    `ReportDate` date NOT NULL
);

CREATE TABLE `DeathLocation` (
	`DeathLocationNo` int NOT NULL PRIMARY KEY,
	`DataLocationName` varchar(20) NOT NULL
);

CREATE TABLE `Death` (
	`PersonId` int NOT NULL,
	`DeathLocationNo` int NOT NULL,
	`QualificationNo` int NOT NULL,
	`DeathDateTime` datetime NOT NULL,
    `AddressId` int NOT NULL,
    `ReporterId` int NOT NULL,
    `ReportDate` date NOT NULL
);

CREATE TABLE `CertificateType` (
	`CertificateID`	int	NOT NULL PRIMARY KEY,
	`CertificateName` varchar(20) NOT NULL
);

CREATE TABLE `Certificate` (
    `CertificateCode` int NOT NULL PRIMARY KEY auto_increment,
	`CertificateID`	int	NOT NULL,
	`PersonId` int NOT NULL,
	`DateGenerated` date NOT NULL
);

ALTER TABLE Person
ADD CONSTRAINT fk_person_genderNo
FOREIGN KEY (GenderNo) REFERENCES Gender(GenderNo);

ALTER TABLE Certificate
ADD CONSTRAINT fk_cerficate_certificateId
FOREIGN KEY (CertificateId) REFERENCES CertificateType(CertificateId);

ALTER TABLE Certificate
ADD CONSTRAINT fk_cerficate_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE ImmediateFamily
ADD CONSTRAINT fk_immediateFamily_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE ImmediateFamily
ADD CONSTRAINT fk_immediateFamily_otherPersonId
FOREIGN KEY (OtherPersonId) REFERENCES Person(PersonId);

ALTER TABLE ImmediateFamily
ADD CONSTRAINT fk_immediateFamily_familyRelationNo
FOREIGN KEY (FamilyRelationNo) REFERENCES FamilyRelation(FamilyRelationNo);

ALTER TABLE Household
ADD CONSTRAINT fk_household_householdPersonId
FOREIGN KEY (HouseholdPersonId) REFERENCES Person(PersonId);

ALTER TABLE Household
ADD CONSTRAINT fk_household_addressId
FOREIGN KEY (AddressId) REFERENCES Address(AddressId);

ALTER TABLE AddressHistory
ADD CONSTRAINT fk_addressHistory_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE AddressHistory
ADD CONSTRAINT fk_AddressHistory_address
FOREIGN KEY (AddressId) REFERENCES address(AddressId);

ALTER TABLE HouseholdHistory
ADD CONSTRAINT fk_householdHistory_householdPersonId
FOREIGN KEY (HouseholdPersonId) REFERENCES Person(PersonId);

ALTER TABLE HouseholdHistory
ADD CONSTRAINT fk_householdHistory_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE HouseholdHistory
ADD CONSTRAINT fk_householdHistory_changeReasonNo
FOREIGN KEY (ChangeReasonNo) REFERENCES ChangeReason(ChangeReasonNo);

ALTER TABLE HouseholdHistory
ADD CONSTRAINT fk_householdHistory_householdRelationNo
FOREIGN KEY (HouseholdRelationNo) REFERENCES HouseholdRelation(HouseholdRelationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_birth_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE Birth
ADD CONSTRAINT fk_birth_birthLocationNo
FOREIGN KEY (BirthLocationNo) REFERENCES BirthLocation(BirthLocationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_birth_qualification
FOREIGN KEY (QualificationNo) REFERENCES Qualification(QualificationNo);

ALTER TABLE Birth
ADD CONSTRAINT fk_birth_addressId
FOREIGN KEY (AddressId) REFERENCES Address(AddressId);

ALTER TABLE Birth
ADD CONSTRAINT fk_birth_reporterId
FOREIGN KEY (ReporterId) REFERENCES Person(PersonId);

ALTER TABLE Death
ADD CONSTRAINT fk_death_personId
FOREIGN KEY (PersonId) REFERENCES Person(PersonId);

ALTER TABLE Death
ADD CONSTRAINT fk_death_birthLocationNo
FOREIGN KEY (DeathLocationNo) REFERENCES DeathLocation(DeathLocationNo);

ALTER TABLE Death
ADD CONSTRAINT fk_death_qualification
FOREIGN KEY (QualificationNo) REFERENCES Qualification(QualificationNo);

ALTER TABLE Death
ADD CONSTRAINT fk_death_addressId
FOREIGN KEY (AddressId) REFERENCES Address(AddressId);

ALTER TABLE Death
ADD CONSTRAINT fk_death_reporterId
FOREIGN KEY (ReporterId) REFERENCES Person(PersonId);

-- Gender 테이블 더미 데이터 삽입
INSERT INTO Gender (GenderNo, GenderName) VALUES
(1, 'M'),
(2, 'F');

-- FamilyRelation 테이블 더미 데이터 삽입
INSERT INTO FamilyRelation (FamilyRelationNo, FamilyRelationName) VALUES
(1, '본인'),
(2, '부'),
(3, '모'),
(4, '배우자'),
(5, '자녀'),
(6, '형제자매');

-- ChangeReason 테이블 더미 데이터 삽입
INSERT INTO ChangeReason (ChangeReasonNo, ChangeReasonName) VALUES
(1, '세대분리'),
(2, '전입'),
(3, '출생등록'); 

-- Address 테이블 더미 데이터 삽입
INSERT INTO Address (zipCode, State, City, Road) VALUES
(12345, '서울시', '동작구', '상도로 940번길'),
(12346, '경기도', '성남시 분당구', '불정로 90번길'),
(12347, '경기도', '성남시 분당구', '대왕판교로 645번길'),
(12348, '강원도', '고성군', '금강산로 290번길');

-- HouseholdRelation 테이블 더미 데이터 삽입
INSERT INTO HouseholdRelation (HouseholdRelationNo, HouseholdName) VALUES
(1, '본인'),
(2, '배우자'),
(3, '자녀'),
(4, '동거인');

-- BirthLocation 테이블 더미 데이터 삽입
INSERT INTO BirthLocation (BirthLocationNo, BirthLocationName) VALUES
(1, '자택'),
(2, '병원'),
(3, '기타');

-- Qualification 테이블 더미 데이터 삽입
INSERT INTO Qualification (QualificationNo, QualificationName) VALUES
(1, '부'),
(2, '모'),
(3, '동거친족'),
(4, '기타'),
(5, '비동거친족'),
(6, '동거자');

-- DeathLocation 테이블 더미 데이터 삽입
INSERT INTO DeathLocation (DeathLocationNo, DataLocationName) VALUES
(1, '주택'),
(2, '의료기관'),
(3, '사회복지시설(양로원, 고아원 등)'),
(4, '산업장'),
(5, '공공시설(학교, 운동장 등)'),
(6, '도로'),
(7, '상업 / 서비스시설(상점, 호텔 등)'),
(8, '농장(논밭, 축사, 양식장 등)'),
(9, '병원 이송 중 사망'),
(10, '기타');

-- Certificate 테이블 더미 데이터 삽입
INSERT INTO CertificateType (CertificateID, CertificateName) VALUES
(1, '출생신고서'),
(2, '사망신고서'),
(3, '가족관계증명서'),
(4, '주민등록등본');
 
-- Person 테이블 더미 데이터 삽입
INSERT INTO Person (registerNo, Name, BirthDate, GenderNo, Email, Phone) VALUES
('790510-7891011', '남기준', 19790510, 1, '남기준@example.com', '010-5647-8901'),
('540514-7891011', '남석환', 19540514, 1, '남석환@example.com', '010-5647-8901'),
('551022-7891011', '박한나', 19551022, 2, '박한나@example.com', '010-5647-8901'),
('820821-7891011', '이주은', 19820821, 2, '이주은@example.com', '010-5647-8901'),
('120315-7891011', '남기석', 20120315, 1, '남기석@example.com', '010-5647-8901'),
('851205-7891011', '이선미', 19851205, 2, '이선미@example.com', '010-5647-8901'),
('130914-7891011', '남길동', 19130914, 1, '남길동@example.com', '010-5647-8901');

-- ImmediateFamily 테이블 더미 데이터 삽입   
INSERT INTO ImmediateFamily VALUES
(1, 1, 1), -- 남기준 기준
(1, 2, 5),
(1, 3, 5),
(1, 4, 4),
(1, 5, 2),

(2, 2, 1), -- 남석환 기준
(2, 7, 5),
(2, 3, 4),
(2, 1, 2),

(3, 3, 1), -- 박한나 기준
(3, 2, 4),
(3, 1, 3),

(4, 4, 1), -- 이주은 기준
(4, 1, 4),
(4, 5, 5),
(4, 6, 6),

(5, 5, 1), -- 남기석 기준
(5, 1, 5),
(5, 4, 5),

(6, 6, 1), -- 이선미 기준
(6, 4, 6),

(7, 7, 1), -- 남길동 기준
(7, 2, 2);

-- Household 테이블 더미 데이터 삽입
INSERT INTO Household VALUES
(7, 4),
(2, 1),
(1, 3);

-- HouseholdHistory 테이블 더미 데이터 삽입
INSERT INTO HouseholdHistory VALUES
(1, 1, 1, 1, '2009-10-02'), -- 남석환
(1, 4, 2, 2, '2010-02-15'),
(1, 5, 3, 3, '2012-03-17'),
(1, 6, 2, 4, '2010-02-15'),

(2, 2, 1, 1, '1984-10-02'), -- 남석환
(2, 3, 2, 2, '1985-02-15'),
(2, 1, 3, 3, '2012-03-17'),
(2, 1, 3, 3, '1979-05-10'),

(7, 7, 1, 1, '1913-09-14'), -- 남길동
(7, 2, 3, 3, '1913-09-14');
    
-- AddressHistory 테이블 더미 데이터 삽입
INSERT INTO AddressHistory (PersonId, AddressId, ChangeDate) VALUES
(1, 1, '2007-10-31'),
(1, 2, '2009-10-31'),
(1, 3, '2013-03-05');

-- Birth 테이블 더미 데이터 삽입
INSERT INTO Birth (PersonId, BirthLocationNo, QualificationNo, BirthDatetime, AddressId, ReporterId, ReportDate) VALUES
(5, 2, 1, '2012-03-15 14:59:00', 3, 1, now());
    
-- Death 테이블 더미 데이터 삽입
INSERT INTO Death (PersonId, DeathLocationNo, QualificationNo, DeathDateTime, AddressId, ReporterId, ReportDate) VALUES
(7, 1, 5, '2021-04-29 09:03:00', 4, 2, now());

-- CertificateNo 테이블 더미 데이터 삽입
INSERT INTO Certificate (CertificateID, PersonId, DateGenerated) VALUES
(1, 1, now());

INSERT INTO Certificate (CertificateID, PersonId, DateGenerated) VALUES
(2, 1, now());

INSERT INTO Certificate (CertificateID, PersonId, DateGenerated) VALUES
(3, 1, now());

INSERT INTO Certificate (CertificateID, PersonId, DateGenerated) VALUES
(4, 1, now());

-- 가족관계증명서 Select
SELECT 
    '가족관계증명서' AS 증명서종류,
    c.DateGenerated AS 발급일,
    f.FamilyRelationName AS 구분,
    fm.Name AS 성명,
    fm.BirthDate AS 출생연원일,
    fm.registerNo AS 주민등록번호,
    g.GenderName AS 성별
FROM 
    Certificate c
INNER JOIN 
    Person p ON c.PersonId = p.PersonId
INNER JOIN 
    ImmediateFamily im ON p.PersonId = im.PersonId
INNER JOIN 
    FamilyRelation f ON im.FamilyRelationNo = f.FamilyRelationNo
INNER JOIN 
    Person fm ON im.OtherPersonId = fm.PersonId
INNER JOIN 
    Gender g ON fm.GenderNo = g.GenderNo
WHERE 
    p.PersonId = 1
    AND c.CertificateID = 3;
    
-- 주민등록등본 - 상단 조회영역 Select
SELECT 
    '주민등록등본 - 상단 조회영역' AS 증명서종류,
    c.DateGenerated AS 발급일,
    c.CertificateCode AS 증명서확인번호,
    p.Name AS 세대주성명,
    cr.ChangeReasonName AS 세대구성사유,
    hh.ChangeDate AS 일자
FROM 
    HouseholdHistory hh
INNER JOIN 
    HouseholdRelation hr ON hh.HouseholdRelationNo = hr.HouseholdRelationNo
INNER JOIN 
    Person p ON hh.PersonId = p.PersonId
INNER JOIN 
    ChangeReason cr ON hh.ChangeReasonNo = cr.ChangeReasonNo
LEFT JOIN 
    Certificate c ON p.PersonId = c.PersonId AND c.CertificateID = 4
WHERE 
    hh.HouseholdPersonId = 1
    AND p.PersonId = 1
ORDER BY 
    hh.ChangeDate DESC;

-- 주민등록등본 - 전입주소 조회 영역 Select
SELECT 
    '주민등록등본 - 전입주소 조회 영역' AS 증명서종류,
    CONCAT_WS(' ', 
              CASE WHEN ROW_NUMBER() OVER(PARTITION BY ah.PersonId ORDER BY ah.ChangeDate DESC) = 1 THEN '현주소: ' ELSE '전주소: ' END,
              a.State,
              a.City,
              a.Road) AS 주소,
    ah.ChangeDate AS 신고일
FROM 
    AddressHistory ah
INNER JOIN 
    Person p ON ah.PersonId = p.PersonId
INNER JOIN 
    Address a ON ah.AddressId = a.AddressId
WHERE 
    p.PersonId = 1
ORDER BY 
    ah.ChangeDate DESC;

-- 주민등록등본 - 세대구성원 조회 영역 Select
SELECT 
    '주민등록등본 - 세대구성원 조회 영역' AS 증명서종류,
    hr.HouseholdName AS 세대주관계,
    p.Name AS 성명,
    p.registerNo AS 주민등록번호,
    hh.ChangeDate AS 신고일,
    cr.ChangeReasonName AS 변동사유
FROM 
    HouseholdHistory hh
INNER JOIN 
    HouseholdRelation hr ON hh.HouseholdRelationNo = hr.HouseholdRelationNo
INNER JOIN 
    Person p ON hh.PersonId = p.PersonId
INNER JOIN 
    ChangeReason cr ON hh.ChangeReasonNo = cr.ChangeReasonNo
WHERE 
    hh.HouseholdPersonId = 1
ORDER BY 
    hh.HouseholdRelationNo ASC, hh.ChangeDate DESC;

-- 출생신고서 - 출생자 / 신고인 Select
SELECT 
    '출생신고서 - 출생자 / 신고인' AS 증명서종류,
    b.ReportDate AS 신고일,
    pb.Name AS 출생자성명,
    g.GenderName AS 출생자성별,
    b.BirthDatetime AS 출생일시,
    bl.BirthLocationName AS 출생장소,
    CONCAT(a.State, ' ', a.City, ' ', a.Road) AS 주소,
    p.Name AS 신고인,
    p.registerNo AS '신고인 주민등록번호', 
    q.QualificationName AS '신고인 자격'
FROM 
    Birth b
INNER JOIN 
    BirthLocation bl ON b.BirthLocationNo = bl.BirthLocationNo
INNER JOIN 
    Qualification q ON b.QualificationNo = q.QualificationNo
INNER JOIN 
    Address a ON b.AddressId = a.AddressId
INNER JOIN 
    Person p ON b.ReporterId = p.PersonId
INNER JOIN
    Person pb ON b.PersonId = pb.PersonId
INNER JOIN
    Gender g ON pb.GenderNo = g.GenderNo
LEFT JOIN
    ImmediateFamily AS im1 ON pb.PersonId = im1.PersonId AND im1.FamilyRelationNo = 1
LEFT JOIN
    Person AS fm ON im1.OtherPersonId = fm.PersonId
LEFT JOIN
    ImmediateFamily AS im2 ON pb.PersonId = im2.PersonId AND im2.FamilyRelationNo = 3
LEFT JOIN
    Person AS mm ON im2.OtherPersonId = mm.PersonId
WHERE 
    b.PersonId = 5;

-- 출생신고서 - 부모 Select
SELECT 
    '출생신고서 - 부모' AS 증명서종류,
    father.Name AS 부의이름,
    father.registerNo AS 부의주민등록번호
FROM 
    ImmediateFamily AS im
INNER JOIN 
    Person AS father ON im.OtherPersonId = father.PersonId
WHERE 
    im.PersonId = 5
    AND im.FamilyRelationNo = 5;

-- 사망신고서 Select
SELECT 
    '사망신고서' AS 증명서종류,
    d.ReportDate AS 신고일,
    dece.Name AS 사망자성명,
    dece.registerNo AS 사망자주민등록번호,
    d.DeathDateTime AS 사망일시,
    dl.DataLocationName AS 사망장소,
    CONCAT(a.State, ' ', a.City, ' ', a.Road) AS 주소,
    reporter.Name AS 신고인,
    reporter.registerNo AS '신고인 주민등록번호',
    q.QualificationName AS '신고인 자격',
    reporter.email AS '신고인 이메일',
    reporter.phone AS '신고인 전화번호'
FROM 
    Death d
INNER JOIN 
    DeathLocation dl ON d.DeathLocationNo = dl.DeathLocationNo
INNER JOIN 
    Qualification q ON d.QualificationNo = q.QualificationNo
INNER JOIN 
    Address a ON d.AddressId = a.AddressId
INNER JOIN 
    Person reporter ON d.ReporterId = reporter.PersonId
INNER JOIN
    Person dece ON d.PersonId = dece.PersonId
INNER JOIN
    Gender g ON dece.GenderNo = g.GenderNo
WHERE 
    d.PersonId = 7; -- 죽은 사람의 PersonId

