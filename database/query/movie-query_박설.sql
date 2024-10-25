#01. 영화 '퍼스트 맨'의 제작 연도, 영문 제목, 러닝 타임, 플롯을 출력하세요.
select ReleaseYear, Title, RunningTime, Plot
from movie
where KoreanTitle = '퍼스트 맨';

#02. 2003년에 개봉한 영화의 한글 제목과 영문 제목을 출력하세요.
select KoreanTitle, Title
from Movie
where ReleaseYear = '2003';

#03. 영화 '글래디에이터'의 작곡가를 고르세요.
SELECT 
    Person.Name
FROM 
    Movie
JOIN 
    Appear ON Movie.MovieID = Appear.MovieID
JOIN 
    Person ON Appear.personID = Person.personID
JOIN 
    Role ON Role.RoleID = Appear.RoleID
WHERE 
    Role.RoleID = 27
AND
	Movie.KoreanTitle = '글래디에이터';

#04. 영화 '매트릭스' 의 감독이 몇명인지 출력하세요.
select count(Person.PersonID)
FROM 
    Movie
JOIN 
    Appear ON Movie.MovieID = Appear.MovieID
JOIN 
    Person ON Appear.personID = Person.personID
JOIN 
    Role ON Role.RoleID = Appear.RoleID
WHERE 
    Role.RoleID = 2
AND 
    Movie.KoreanTitle = '매트릭스';
    
#05. 감독이 2명 이상인 영화를 출력하세요.
select Movie.Title
FROM 
    Movie
JOIN 
    Appear ON Movie.MovieID = Appear.MovieID
JOIN 
    Person ON Appear.personID = Person.personID
JOIN 
    Role ON Role.RoleID = Appear.RoleID
where Role.RoleID = 2
group by Movie.MovieID
having count(Person.PersonID) >= 2;

#06. '한스 짐머'가 참여한 영화 중 아카데미를 수상한 영화를 출력하세요.
SELECT 
    movie.Title
FROM 
    movie
JOIN 
    appear ON movie.MovieID = appear.MovieID
JOIN 
    person ON appear.PersonID = person.PersonID
JOIN 
    awardinvolve ON appear.AppearID = awardinvolve.AppearID
JOIN 
    winning ON awardinvolve.WinningID = winning.WinningID    
where winning.winningID = 2 
and Person.KoreanName = '한스 짐머';

#07. 감독이 '제임스 카메론'이고 '아놀드 슈워제네거'가 출연한 영화를 출력하세요.
SELECT movie.Title
FROM   movie
       JOIN appear
         ON movie.MovieID = appear.MovieID
WHERE  Appear.MovieID IN (SELECT Appear.MovieID
                          FROM   Appear
                                 JOIN Person
                                   ON Person.personID = Appear.personID
                                 JOIN Role
                                   ON Role.RoleID = Appear.RoleID
                          WHERE  Person.KoreanName = '제임스 카메론'
                                 AND Role.RoleID = 2)
       AND Appear.MovieID IN (SELECT Appear.MovieID
                              FROM   Appear
                                     JOIN Person
                                       ON Appear.personID = Person.personID
                                     JOIN Role
                                       ON Role.RoleID = Appear.RoleID
                              WHERE
           Person.KoreanName = '아놀드 슈워제네거'
           AND Role.RoleID = 6)
GROUP  BY movie.MovieID; 

-- 08. 상영시간이 100분 이상인 영화 중 레오나르도 디카프리오가 출연한 영화를 고르시오.
select movie.title
from movie
JOIN 
    Appear ON Movie.MovieID = Appear.MovieID
JOIN 
    Person ON Appear.personID = Person.personID
JOIN 
    Role ON Role.RoleID = Appear.RoleID
where runningTime >= 100
and Appear.MovieID in 
(select Appear.MovieID from Appear
JOIN 
    Person ON Appear.personID = Person.personID
JOIN 
    Role ON Role.RoleID = Appear.RoleID 
    where Person.KoreanName = '레오나르도 디카프리오' and Role.RoleID = 6)
group by movie.title;
    
-- 09. 청소년 관람불가 등급의 영화 중 가장 많은 수익을 얻은 영화를 고르시오.
select movie.title
from movie
where GradeInKoreaID = 4
order by BoxOfficeWWGross desc
limit 1;

-- 10. 1999년 이전에 제작된 영화의 수익 평균을 고르시오.
select avg(movie.BoxOfficeWWGross)
from movie
where ReleaseYear < 1999;

-- 11. 가장 많은 제작비가 투입된 영화를 고르시오.
select movie.title
from movie
order by movie.Budget desc
limit 1;

-- 12. 제작한 영화의 제작비 총합이 가장 높은 감독은 누구입니까?
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Movie on Movie.MovieID = Appear.MovieID
join Role on Role.RoleID = Appear.RoleID
where Role.RoleID = 2
group by person.personID
order by sum(movie.budget) desc
limit 1;

-- 13. 출연한 영화의 모든 수익을 합하여, 총 수입이 가장 많은 배우를 출력하세요.
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Movie on Movie.MovieID = Appear.MovieID
join Role on Role.RoleID = Appear.RoleID
where Role.RoleID = 6 or Role.RoleID = 7
group by person.personID
order by sum(movie.BoxOfficeWWGross) desc
limit 1;

-- 14. 제작비가 가장 적게 투입된 영화의 수익을 고르세요. (제작비가 0인 영화는 제외합니다)
select movie.BoxOfficeWWGross
from Movie
join Appear on Movie.MovieID = Appear.MovieID
join Person on Appear.PersonID = Person.PersonID
join Role on Role.RoleID = Appear.RoleID
where movie.budget > 0
order by movie.budget asc
limit 1;

-- 15. 제작비가 5000만 달러 이하인 영화의 미국내 평균 수익을 고르세요.
select avg(movie.BoxOfficeUSGross)
from Movie
where movie.budget <= 50000000;

-- 16. 액션 장르 영화의 평균 수익을 고르세요.
select avg(movie.BoxOfficeWWGross)
from Movie
join MovieGenre on Movie.MovieID = MovieGenre.MovieID
join Genre on MovieGenre.GenreID = Genre.GenreID
where Genre.GenreID = 4;

-- 17. 드라마, 전쟁 장르의 영화를 고르세요.
SELECT m.Title
FROM movie m
JOIN moviegenre mg ON mg.MovieID = m.MovieID
WHERE mg.GenreID = 1 or mg.GenreID = 19
group by m.MovieID
HAVING COUNT(m.MovieID) = 2;

-- 18. 톰 행크스가 출연한 영화 중 상영 시간이 가장 긴 영화의 제목, 한글제목, 개봉연도를 출력하세요.
select movie.Title, movie.KoreanTitle, movie.ReleaseYear
from movie
join Appear on Movie.MovieID = Appear.MovieID
join Person on Appear.PersonID = Person.PersonID
where Person.KoreanName = '톰 행크스'
order by Movie.RunningTime desc
limit 1;

-- 19. 아카데미 남우주연상을 가장 많이 수상한 배우를 고르시오.
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Awardinvolve on Awardinvolve.AppearID = Appear.AppearID
join Sector on Sector.SectorID = Awardinvolve.SectorID
join Winning on Awardinvolve.WinningID = Winning.WinningID
where Sector.SectorID = 2
and Winning.WinningID = 2
group by person.Name
having count(person.Name)
order by count(person.Name) desc
limit 1;

-- 20. 아카데미상을 가장 많이 수상한 배우를 고르시오. ('수상자 없음'이 이름인 영화인은 제외합니다)
select Name from person p join appear a on a.PersonID = p.PersonID
join awardinvolve ai on ai.AppearID = a.AppearID
       JOIN role r
         ON r.RoleID = a.RoleID
	JOIN winning w
         ON ai.WinningID = w.WinningID
join sector s on ai.SectorID = s.SectorID 
where r.RoleKorName='배우' and ai.WinningID = 2 and p.KoreanName not in ('수상자 없음') 
group by p.PersonID 
order by count(s.SectorID) 
desc limit 1;

-- 21. 아카데미 남우주연상을 2번 이상 수상한 배우를 고르시오.
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Awardinvolve on Awardinvolve.AppearID = Appear.AppearID
join Sector on Sector.SectorID = Awardinvolve.SectorID
join Winning on Awardinvolve.WinningID = Winning.WinningID
where Sector.SectorID = 2
and Winning.WinningID = 2
group by person.Name
having count(person.Name) >= 2;

-- 23. 아카데미상을 가장 많이 수상한 사람을 고르세요.
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Awardinvolve on Awardinvolve.AppearID = Appear.AppearID
join Winning on Winning.WinningID = Awardinvolve.WinningID
join Role on Role.RoleID = Appear.RoleID
where Winning.WinningID = 2
group by person.Name
having count(person.Name)
order by count(person.Name) desc
limit 1;

-- 24. 아카데미상에 가장 많이 노미네이트 된 영화를 고르세요.
select movie.Title
from movie
join Appear on Appear.MovieID = Movie.MovieID
join Awardinvolve on Awardinvolve.AppearID = Appear.AppearID
join Winning on Winning.WinningID = Awardinvolve.WinningID
where Winning.WinningID = 2
group by movie.Title
having count(movie.Title)
order by count(movie.Title) desc
limit 1;

-- 25. 가장 많은 영화에 출연한 여배우를 고르세요.
select Person.Name
FROM 
    Person
JOIN 
    Appear ON Appear.personID = Person.personID
JOIN 
    Movie ON Movie.MovieID = Appear.MovieID
JOIN 
    Role ON Role.RoleID = Appear.RoleID
where Role.RoleID = 7
group by Person.Name
having count(DISTINCT Movie.MovieID) > 0
order by count(DISTINCT Movie.MovieID) desc;

-- 26. 수익이 가장 높은 영화 TOP 10을 출력하세요.
select movie.title from movie
order by BoxOfficeWWGross desc
limit 10;

-- 27. 수익이 10억불 이상인 영화중 제작비가 1억불 이하인 영화를 고르시오.
select movie.title from movie
where movie.BoxOfficeWWGross >= 1000000000
and movie.budget <= 100000000;

-- 28. 전쟁 영화를 가장 많이 감독한 사람을 고르세요.
SELECT Person.Name
FROM Person
JOIN Appear ON Appear.PersonID = Person.PersonID
JOIN Movie ON Movie.MovieID = Appear.MovieID
JOIN Role ON Role.RoleID = Appear.RoleID
JOIN MovieGenre ON Movie.MovieID = MovieGenre.MovieID
JOIN Genre ON MovieGenre.GenreID = Genre.GenreID
WHERE Role.RoleID = 2
  AND Genre.GenreID = 19
GROUP BY Person.PersonID
ORDER BY COUNT(Movie.MovieID) DESC
LIMIT 1;

-- 29. 드라마에 가장 많이 출연한 사람을 고르세요.
select person.Name
from Person
join Appear on Appear.PersonID = Person.PersonID
join Movie on Movie.MovieID = Appear.MovieID
join Role on Role.RoleID = Appear.RoleID
join MovieGenre on Movie.MovieID = MovieGenre.MovieID
join Genre on MovieGenre.GenreID = Genre.GenreID
where Role.RoleID = 6 or Role.RoleID = 7
and MovieGenre.GenreID = 1
group by Person.PersonID
order by count(*) desc limit 1;

-- 30. 드라마 장르에 출연했지만 호러 영화에 한번도 출연하지 않은 사람을 고르세요.
SELECT p.name
FROM   person p
       JOIN appear ap
         ON ap.personid = p.personid
       JOIN moviegenre mg
         ON mg.movieid = ap.movieid
WHERE  mg.genreid IN ( 1 )
		AND ap.roleid IN ( 6, 7 )
       AND p.personid NOT IN (SELECT p.personid
                              FROM   person p
                              WHERE  mg.genreid IN ( 22 )
                                     AND ap.roleid IN ( 6, 7 ))
GROUP  BY p.personid;

-- 31. 아카데미 영화제가 가장 많이 열린 장소는 어디인가요?
select AwardYear.Location from AwardYear
group by Location
order by count(AwardYear.Location) desc limit 1;

-- 33. 첫 번째 아카데미 영화제가 열린지 올해 기준으로 몇년이 지났나요?
select year(current_date()) - min(AwardYear.Year) from AwardYear;

-- 34. SF 장르의 영화 중 아카데미 영화제 후보에 가장 많이 오른 영화를 구하세요.
select movie.Title
from movie
join Appear on Appear.MovieID = Movie.MovieID
join Awardinvolve on Awardinvolve.AppearID = Appear.AppearID
join Winning on Winning.WinningID = Awardinvolve.WinningID
join MovieGenre on Movie.MovieID = MovieGenre.MovieID
join Genre on MovieGenre.GenreID = Genre.GenreID
where Genre.GenreID = 15
and Winning.WinningID = 1
group by movie.Title
having count(movie.Title)
order by count(movie.Title) desc
limit 1;

-- 35. 드라마 장르의 영화의 아카데미 영화제 작품상 수상 비율을 구하세요.
SELECT (
    SELECT COUNT(DISTINCT ap.movieid)
    FROM appear ap
    JOIN awardinvolve aw ON aw.appearid = ap.appearid
    JOIN moviegenre mg ON mg.movieid = ap.movieid
    WHERE aw.sectorid IN (1, 27, 104) AND aw.winningid IN (2) AND mg.genreid IN (1)
) / (
    SELECT COUNT(DISTINCT m.movieid)
    FROM movie m
    JOIN moviegenre mg ON m.movieid = mg.movieid
    WHERE mg.genreid = 1
) * 100 AS award_ratio;
