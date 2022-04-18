
--searchRole
select
    *
from 
    somasi_role
where
    name like '%[[filter]]%'
order by name asc
limit [[rowPerPage]] offset [[start]]

--selectByCode
select 
    *
from 
    somasi_role 
where roleid='[[role]]'

--listRole
select
    *
from 
    somasi_role
order by name asc

