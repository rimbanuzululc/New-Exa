--list
select
sa.*,
sc."name" as countryName,
ss."name" as stateName,
sc2."name" as cityName,
sd."name" as districtName,
ss2."name" as subDistrictName
from somasi_agentpos sa 
left join somasi_country sc on sa.countryid = sc.countryid 
left join somasi_state ss on sa.stateid = ss.stateid 
left join somasi_city sc2 on sa.cityid = sc2.cityid 
left join somasi_district sd on sa.districtid = sd.districtid 
left join somasi_subdistrict ss2 on sa.subdistrictid = ss2.subdistrictid

--getByZipcode
select 
* 
from somasi_agentpos where zipcode = {{zipcode}}

--getById
select
sa.*,
sc."name" as countryName,
ss."name" as stateName,
sc2."name" as cityName,
sd."name" as districtName,
ss2."name" as subDistrictName
from somasi_agentpos sa 
left join somasi_country sc on sa.countryid = sc.countryid 
left join somasi_state ss on sa.stateid = ss.stateid 
left join somasi_city sc2 on sa.cityid = sc2.cityid 
left join somasi_district sd on sa.districtid = sd.districtid 
left join somasi_subdistrict ss2 on sa.subdistrictid = ss2.subdistrictid
where sa.idagentpos = {{id}};

--listAssign
select
a.*
from somasi_assign a;

--getByUsername
select
sa.*,
sc."name" as countryName,
ss."name" as stateName,
sc2."name" as cityName,
sd."name" as districtName,
ss2."name" as subDistrictName
from somasi_agentpos sa 
left join somasi_country sc on sa.countryid = sc.countryid 
left join somasi_state ss on sa.stateid = ss.stateid 
left join somasi_city sc2 on sa.cityid = sc2.cityid 
left join somasi_district sd on sa.districtid = sd.districtid 
left join somasi_subdistrict ss2 on sa.subdistrictid = ss2.subdistrictid
where sa.username = {{username}}

--searchByCode
select
    *
from 
    somasi_setting
where
    code = {{code}}

--search
select * from somasi_agentpos where districtid = {{districtid}}

--validOtp
select * from somasi_otp so where nodebitur = {{no}} and idagentpos = {{id}}

--checkName
select 
sc."name" as countryName,
ss."name" as stateName,
sc2."name" as cityName,
sd."name" as districtName,
ss2."name" as subDistrictName
from somasi_agentpos sa 
left join somasi_country sc on sa.countryid = sc.countryid 
left join somasi_state ss on sa.stateid = ss.stateid 
left join somasi_city sc2 on sa.cityid = sc2.cityid 
left join somasi_district sd on sa.districtid = sd.districtid 
left join somasi_subdistrict ss2 on sa.subdistrictid = ss2.subdistrictid 
where sa.idagentpos = {{id}}
