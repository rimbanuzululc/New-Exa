create table somasi_konsumen (
	idkonsumen serial primary key,
	ref_no varchar,
	cardNo varchar,
	"name" varchar,
	outstanding varchar, 
	main_address_1 varchar,
	address_1_city varchar,
	address_1_zipcode varchar,
	address_2 varchar,
	address_2_zipcode varchar,
	address_2_city varchar,
	address_3 varchar,
	address_3_zipcode varchar,
	address_3_city varchar,
	hp_no varchar, 
	hp_no2 varchar,
	home_phone_1 varchar,
	office_phone_1 varchar,
	no_ktp varchar,
	expired_date varchar,
	upload_date TIMESTAMP,
	last_modify TIMESTAMP,
	dpd int,
        statusSomasi varchar,
);


CREATE TABLE public.somasi_agentpos (
	idagentpos serial NOT NULL,
	username varchar NULL,
	cityagent varchar NULL,
	CONSTRAINT somasi_agentpos_pkey PRIMARY KEY (idagentpos)
);

create table somase_historyagent (
	idhistoryagent serial primary key,
	idagentpos int,
	assigndate timestamp,
	remark varchar
);

create table somasi_city (
	idCity int primary key,
	cityName varchar(255)
);

create table somasi_user
        (
            userId varchar(255),
            name varchar(255),
            password varchar(255),
            primary key (userId),
roleid INTEGER REFERENCES somasi_role(roleid)
        );

create table somasi_rolemenu (  roleid INTEGER REFERENCES somasi_role(roleid),menuid INTEGER REFERENCES somasi_menu(menuid));

create table somasi_role(roleid serial primary key,
name varchar(225));

create table somasi_menu(
	menuid serial primary key,
title varchar(225),
target varchar(225),
parentid int,
icon varchar(225)
);


create table konsumen (
	konsumenId serial primary key,
	noAggrement varchar,
	namaDebitur varchar,
	alamatKtp varchar,
	provinsiKtp varchar,
	kotaKtp varchar,
	kecamatanKtp varchar,
	kelurahanKtp varchar,
	zipcodeKtp int,
	alamatTinggal varchar,
	provinsiTinggal varchar,
	kotaTinggal varchar,
	kecamatanTinggal varchar,
	kelurahanTinggal varchar,
	zipcodeTinggal int,
	noTelp varchar,
	jenisPerjanjian varchar,
	tglPerjanjian Date,
	merkKendaraan varchar,
	tahunKendaraan varchar,
	kondisiKendaraan varchar,
	nomorPolisi varchar,
	nomorRangka varchar,
	nomorMesin varchar,
	warna varchar,
	nomorBpkb varchar,
	nomorStnk varchar,
	sertifikatJaminan varchar,
	atasNamaBpkb varchar,
	alamatBpkb varchar,
	installment int,
	sisaOutstanding int,
	tglMulaiMenunggak date,
	tglSp1 date,
	tglSp2 date,
	kronologi varchar,
	upload_date date,
	last_modify date
	
)

create table somasi_assignfinance (
	idAssignFinance serial primary key,
	noAggrement varchar,
	assign_date date,
	last_modify date,
	idagentpos int references somasi_agentpos (idagentpos),
	konsumenId int references konsumen (konsumenid)
);

create table somasi_state (
	stateid int primary key,
	name varchar
)

create table somasi_city (
	cityid int primary key,
	name varchar,
	stateid int references somasi_state(stateid)
);

create table somasi_district (
	districtid int primary key,
	name varchar,
	cityid int references somasi_city(cityid)
)

create table somasi_subdistrict (
	subdistrictid int primary key,
	name varchar,
	districtid int references somasi_district(districtid)
)

create table somasi_mappingarea (
	idmappingarea serial primary key,
	codeArea varchar,
	districId int references somasi_district (districtid)
)

create table somasi_imageKonsumenFinance (
	idKonsumenFinance serial primary key,
	imageName varchar,
	imagePath varchar,
	imageLongitude text,
	konsumenid int references konsumen (konsumenid)
)

create table somasi_status1 (
	idStatus1 serial primary key,
	code varchar,
	description varchar
)

create table somasi_status2 (
	idStatus2 serial primary key,
	code varchar,
	description varchar
)

create table somasi_historystatus (
	idstatuskonsumen serial primary key,
	idstatus1 int references somasi_status1(idstatus1),
	idstatus2 int references somasi_status2(idstatus2),
	konsumenid int references konsumen(konsumenid),
	idagentpos int references somasi_agentpos(idagentpos),
	created date
)