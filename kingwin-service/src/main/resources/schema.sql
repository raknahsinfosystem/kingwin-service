drop schema if exists webzion; 
create schema if not exists webzion;
use webzion;
create table if not exists branch(id int primary key AUTO_INCREMENT, syllabus varchar(50), userType varchar(50),branch varchar(50) ,password varchar(50));

/*create table if not exists subject(id int primary key AUTO_INCREMENT, subjectId int, subjectName varchar(50),syllabus varchar(50),branch varchar(50),file blob,createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);*/
create table if not exists ebook(id int primary key AUTO_INCREMENT, eBookId varchar(50), eBookName varchar(50),eBookType varchar(50), syllabus varchar(50),branch varchar(50),file LONGBLOB NULL DEFAULT NULL,origFileName varchar(50), language varchar(50) DEFAULT NULL,dateToShow DATE NULL, createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);

insert into branch(syllabus,userType,branch,password) values("CMC/NEET","admin","admin","admin@webzion");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","velur","admin@velurCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","velur","admin@velurNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","tiruvannamalai","admin@tiruvannamalaiCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","tiruvannamalai","admin@tiruvannamalaiNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","ranipetai","admin@ranipettaiCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","ranipetai","admin@ranipettaiNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","cheyyar","admin@cheyyarCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","cheyyar","admin@cheyyarNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","aambur","admin@aamburCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","aambur","admin@aamburNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","tiruppathur","admin@tiruppathurCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","tiruppathur","admin@tiruppathurNEET");
insert into branch(syllabus,userType,branch,password) values("CMC/NEET","student","all","admin@allCMC");
insert into branch(syllabus,userType,branch,password) values("NEET","student","all","admin@allNEET");
