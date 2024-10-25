DROP DATABASE IF EXISTS Izeinai;

CREATE DATABASE Izeinai;

use Izeinai;

CREATE TABLE UserType ( -- 관리자, 일반 
	userType int PRIMARY KEY,
    userTypeName VARCHAR(10) NOT NULL
);

CREATE TABLE UserStatus ( -- 휴면, 탈퇴, 일반
	userStatusNo int PRIMARY KEY,
    userStatusName VARCHAR(20) DEFAULT 0
);

CREATE TABLE User ( -- 찐 유저
	userId int PRIMARY KEY AUTO_INCREMENT,
	id VARCHAR(10) NOT NULL UNIQUE,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    nickname VARCHAR(10) NOT NULL UNIQUE,
    registerDate DATETIME NOT NULL,
    userType int NOT NULL,
    userStatusNo int NOT NULL,
    CONSTRAINT fk_User_UserType FOREIGN KEY (userType) REFERENCES UserType(userType),
    CONSTRAINT fk_User_UserStatus FOREIGN KEY (userStatusNo) REFERENCES UserStatus(userStatusNo)
);

CREATE TABLE PostGrade ( -- 글 종류
	postGradeNo int PRIMARY KEY NOT NULL DEFAULT 0,
    postGradeName VARCHAR(10) NOT NULL
);

CREATE TABLE Category ( -- 게시판 카테고리
	categoryNo int PRIMARY KEY NOT NULL DEFAULT 0,
    categoryName VARCHAR(10) NOT NULL
);

CREATE TABLE Post ( -- 게시판
	postId int(10) PRIMARY KEY AUTO_INCREMENT,
    userId int,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(5000) NOT NULL,
    postGradeNo int NOT NULL,
    categoryNo int NOT NULL,
    createDate DATETIME NOT NULL,
    modifyDate DATETIME,
    isDeleted VARCHAR(1) DEFAULT 'N',
    view int(6) DEFAULT 0,
    CONSTRAINT fk_userId_User FOREIGN KEY (userId) REFERENCES User(userId),
    CONSTRAINT fk_postGradeNo_PostGrade FOREIGN KEY (postGradeNo) REFERENCES PostGrade(postGradeNo),
    CONSTRAINT fk_categoryNo_Category FOREIGN KEY (categoryNo) REFERENCES Category(categoryNo)
);

CREATE TABLE Comment ( -- 댓글
	commentId int PRIMARY KEY AUTO_INCREMENT,
    postId int,
    userId int,
    content VARCHAR(200),
    parentCommentId int,
    createDate DATETIME NOT NULL,
    modifyDate DATETIME,
    isDeleted VARCHAR(1) DEFAULt 'N',
    CONSTRAINT fk_Comment_Post FOREIGN KEY (postId) REFERENCES Post(postId),
    CONSTRAINT fk_Comment_User FOREIGN KEY (userId) REFERENCES User(userId),
    CONSTRAINT fk_Comment_ParentComment FOREIGN KEY (parentCommentId) REFERENCES Comment(commentId)
);

INSERT INTO UserType (userType, userTypeName) VALUES (1, '관리자');
INSERT INTO UserType (userType, userTypeName) VALUES (2, '일반');

SELECT * FROM UserType;

INSERT INTO UserStatus (userStatusNo, userStatusName) VALUES (1, '일반');
INSERT INTO UserStatus (userStatusNo, userStatusName) VALUES (2, '휴면');
INSERT INTO UserStatus (userStatusNo, userStatusName) VALUES (3, '탈퇴');

SELECT * FROM UserStatus;

INSERT INTO User (id, email, password, nickname, registerDate, userType, userStatusNo) VALUES ('a', 'a@naver.com', 'a', 'a', NOW(), 1, 1);
INSERT INTO User (id, email, password, nickname, registerDate, userType, userStatusNo) VALUES ('b', 'b@naver.com', 'b', 'b', NOW(), 2, 1);
INSERT INTO User (id, email, password, nickname, registerDate, userType, userStatusNo) VALUES ('c', 'c@naver.com', 'c', 'c', NOW(), 2, 1);
INSERT INTO User (id, email, password, nickname, registerDate, userType, userStatusNo) VALUES ('d', 'd@naver.com', 'd', 'd', NOW(), 2, 1);
INSERT INTO User (id, email, password, nickname, registerDate, userType, userStatusNo) VALUES ('e', 'e@naver.com', 'e', 'e', NOW(), 2, 1);

SELECT * FROM User;

INSERT INTO PostGrade (postGradeNo, postGradeName) VALUES (1, '일반');
INSERT INTO PostGrade (postGradeNo, postGradeName) VALUES (2, '공지');
INSERT INTO PostGrade (postGradeNo, postGradeName) VALUES (3, '비밀글');

SELECT * FROM PostGrade;

INSERT INTO Category (categoryNo, categoryName) VALUES (1, '일상');
INSERT INTO Category (categoryNo, categoryName) VALUES (2, '질문');

SELECT * FROM Category;

INSERT INTO Post (userId, title, content, postGradeNo, categoryNo, createDate) VALUES (1, '안녕', '안녕하세요', 1, 1, NOW());
INSERT INTO Post (userId, title, content, postGradeNo, categoryNo, createDate) VALUES (2, 'hi', 'hello', 2, 1, NOW());
INSERT INTO Post (userId, title, content, postGradeNo, categoryNo, createDate) VALUES (3, '제목', '내용', 3, 1, NOW());

SELECT * FROM Post;

INSERT INTO Comment (postId, userId, content, createDate) VALUES (1, 1, '안녕하세요', NOW());
INSERT INTO Comment (postId, userId, content, createDate) VALUES (2, 2, 'hi', NOW());
INSERT INTO Comment (postId, userId, content, createDate) VALUES (3, 3, '내용', NOW());

SELECT * FROM Comment;