use test;

create table emp (
	empno int NOT NULL,
	ename varchar(32) NOT NULL,
	job varchar(64) NOT NULL,
	sal decimal(10,2) NOT NULL,
	primary key(empno)
);

insert into emp(empno, ename, job, sal) values(7369, 'SMITH', 'CLERK', 800);
