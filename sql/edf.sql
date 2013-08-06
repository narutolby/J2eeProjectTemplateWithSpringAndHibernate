create database EDF;
use EDF;
create table EMPLOY_INFO(
   id varchar(32) primary key,
   company_id varchar(32),
   company_name varchar(255),
   job_name varchar(255),
   company_link varchar(255),
   job_link varchar(255),
   employ_num int(11),
   job_type varchar(255),
   job_city varchar(255),
   pub_date varchar(255)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8;
create table COMPANY_INFO(
   id varchar(32) primary key,
   name varchar(32),
   description text,
   home_page varchar(32)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8;