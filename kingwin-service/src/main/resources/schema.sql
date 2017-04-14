create schema if not exists webzion;
use webzion;
create table if not exists branch(id int primary key AUTO_INCREMENT, syllabusType varchar(50), userType varchar(50),place varchar(50) ,password varchar(50));

/*create table if not exists subject(id int primary key AUTO_INCREMENT, subjectId int, subjectName varchar(50),syllabus varchar(50),branch varchar(50),file blob,createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);*/
create table if not exists ebook(id int primary key AUTO_INCREMENT, eBookId int, eBookName varchar(50),eBookType varchar(50), syllabus varchar(50),branch varchar(50),file blob, language varchar(50),createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);

insert into branch(syllabusType,userType,place,password) values("CMC/NEET","admin","admin","admin@webzion");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","velur","admin@velurCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","velur","admin@velurNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","tiruvannamalai","admin@tiruvannamalaiCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","tiruvannamalai","admin@tiruvannamalaiNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","ranipetai","admin@ranipettaiCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","ranipetai","admin@ranipettaiNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","cheyyar","admin@cheyyarCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","cheyyar","admin@cheyyarNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","aambur","admin@aamburCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","aambur","admin@aamburNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","tiruppathur","admin@tiruppathurCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","tiruppathur","admin@tiruppathurNEET");
insert into branch(syllabusType,userType,place,password) values("CMC/NEET","student","all","admin@allCMC");
insert into branch(syllabusType,userType,place,password) values("NEET","student","all","admin@allNEET");
