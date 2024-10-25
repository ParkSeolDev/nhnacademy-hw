
-- 예매 가능한 나라 출력
-- DROP procedure IF EXISTS `show_Country`;
DELIMITER $$
CREATE PROCEDURE `show_Country` ()
BEGIN
select concat(Airportname,'(',countryname,')')  as '모든출발지', 
		concat(Airportname,'(',countryname,')') as '모든도착지'
        from airport a join country c on a.Countryid = c.Countryid;
END$$
DELIMITER ;

select concat(Airportname,'(',countryname,')')  as '모든출발지', 
		concat(Airportname,'(',countryname,')') as '모든도착지'
        from airport a join country c on a.Countryid = c.Countryid;

-- 운항계획  출력
-- 출발지와 도착지, 출발일자와 도착일자를 지정합니다.
DROP procedure IF EXISTS `showFlightTicket`;
DELIMITER $$
CREATE PROCEDURE `showFlightTicket` (
p_date varchar(10),
p_departure varchar(50),
p_arrival varchar(50))
BEGIN
select concat(departure,'(',countryname,')')  as '출발지', 
		concat(arrival,'(',countryname,')') as '도착지',
        price as '항공권가격',
		aircraftNo as '편명',
        flightdate as '비행스케줄'
        from flightticket ft join flightplan fp  on ft.flightplanNo = fp.flightplanNo
			join flightway fw on fp.flightwayid = fw.fligjtwayid
            join airport a on fw.departure = a.airportid and fw.arrival = a.airportid 
            join country c on a.countryid = c.countryid
        where departure = p_departure  
        and arrival = p_arrival 
        and DATE_FORMAT(flightdate, '%Y-%m-%d') = DATE_FORMAT(p_date, '%Y-%m-%d'); 
END$$
DELIMITER ;

select concat(Airportname,'(',countryname,')')  as '출발지', 
        price as '항공권가격',
		aircraftNo as '편명',
        flightdate as '비행스케줄'
        from flightticket ft join flightplan fp  on ft.flightplanNo = fp.flightplanNo
			join flightway fw on fp.flightwayid = fw.flightwayid
            join airport a on fw.departure = a.airportid
            join country c on a.countryid = c.countryid
        where Airportname = 'Incheon International Airport'
        and DATE_FORMAT(flightdate, '%Y-%m-%d') = DATE_FORMAT('2024-04-15', '%Y-%m-%d');
        
select 
		concat(arrival,'(',countryname,')') as '도착지',
        price as '항공권가격',
		aircraftNo as '편명',
        flightdate as '비행스케줄'
        from flightticket ft join flightplan fp  on ft.flightplanNo = fp.flightplanNo
			join flightway fw on fp.flightwayid = fw.flightwayid
            join airport a on fw.arrival = a.airportid
            join country c on a.countryid = c.countryid
        where Airportname = 'Los Angeles International Airport'
        and DATE_FORMAT(flightdate, '%Y-%m-%d') = DATE_FORMAT('2024-04-15', '%Y-%m-%d'); 

WITH IncheonFlights AS (
    SELECT 
		FT1.flightid as '항공권(가는편)',
        '출발지' AS type,
        CONCAT(A1.Airportname, '(', C1.countryname, ')') AS location, 
        FT1.price AS '항공권가격',
        FP1.aircraftNo AS '편명',
        FP1.flightdate AS '비행스케줄'
    FROM 
        flightticket FT1
    INNER JOIN 
        flightplan FP1 ON FT1.flightplanNo = FP1.flightplanNo
    INNER JOIN 
        flightway FW1 ON FP1.flightwayid = FW1.flightwayid
    INNER JOIN 
        airport A1 ON FW1.departure = A1.airportid
    INNER JOIN 
        country C1 ON A1.countryid = C1.countryid
    WHERE 
        A1.Airportname = 'Incheon International Airport'
        AND DATE_FORMAT(FP1.flightdate, '%Y-%m-%d') = DATE_FORMAT('2024-04-15', '%Y-%m-%d')
),
LosAngelesFlights AS (
    SELECT 
    FT2.flightid as '항공권(오는편)',
        '도착지' AS type,
        CONCAT(A2.Airportname, '(', C2.countryname, ')') AS location, 
        FT2.price AS '항공권가격',
        FP2.aircraftNo AS '편명',
        FP2.flightdate AS '비행스케줄'
    FROM 
        flightticket FT2
    INNER JOIN 
        flightplan FP2 ON FT2.flightplanNo = FP2.flightplanNo
    INNER JOIN 
        flightway FW2 ON FP2.flightwayid = FW2.flightwayid
    INNER JOIN 
        airport A2 ON FW2.arrival = A2.airportid
    INNER JOIN 
        country C2 ON A2.countryid = C2.countryid
    WHERE 
        A2.Airportname = 'Los Angeles International Airport'
        AND DATE_FORMAT(FP2.flightdate, '%Y-%m-%d') = DATE_FORMAT('2024-04-15', '%Y-%m-%d')
)
SELECT * FROM IncheonFlights
JOIN LosAngelesFlights ON 1 = 1;



-- 가는편, 오는편 항공권을 선택합니다.
-- select concat(Airportname,'(',countryname,')')  as '가는편',
-- 		flightdate '비행일자',
--         price '가격'
--         from airport a join country c on a.Countryid = c.Countryid
-- 	    where flightdate = '2024-04-15' and a.Airportname = 'Los Angeles International Airport';
--         
--         
-- select concat(Airportname,'(',countryname,')')  as '오는편',
-- 		flightdate '비행일자',
--         price '가격'
--         from airport a join country c on a.Countryid = c.Countryid
-- 	    where flightdate = '2024-04-12' and countryname = '정해야됨';


-- 로그인하고 탑승객 정보를 입력합니다.!

DROP procedure IF EXISTS `reservation`;
DELIMITER $$
CREATE PROCEDURE `reservation` (
p_id varchar(50),
p_password varchar(50),
p_name varchar(50),
p_age int,
p_phoneNumber varchar(13),
p_departure int,
p_arrival int)
BEGIN
    DECLARE p_customerId int;
    DECLARE PNRId varchar(6);
	DECLARE reservation_number int;
    
    
    -- 아이디와 비밀번호 확인
    set p_customerId = (SELECT Personalcustomerid FROM Personalcustomer WHERE id = p_id AND password = p_password);
    
    -- 만약 인증이 되었다면 다음 쿼리 실행
    IF p_customerId is not null THEN
		set reservation_number = CAST(CONCAT(p_customerId, DATE_FORMAT(NOW(), '%Y%m%d')) AS UNSIGNED);
        -- 예약번호 생성
        INSERT INTO PNR (PNRid,customerid) VALUES (reservation_number, p_customerId);

        INSERT into seg (PNRid, flightid) VALUES(reservation_number, p_departure);
        INSERT into seg (PNRid, flightid) VALUES(reservation_number, p_arrival);
        
        insert into PAX (PNRId, name, Age, PhoneNumber)  values(reservation_number,p_name,p_age,p_phoneNumber);
        SELECT 'Reservation successful'; -- 예약 성공 메시지 반환
    ELSE
        SELECT 'Authentication failed'; -- 인증 실패 메시지 반환
    END IF;
    
    
END$$
DELIMITER ;

call reservation('hong123','password','홍길동',30,'010-1234-1234',5,1);

