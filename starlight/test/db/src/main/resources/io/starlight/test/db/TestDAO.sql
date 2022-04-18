--selectAll
select
    *
from
    tbl_test2

--selectFilter
select
    *
from
    tbl_test2
where
    dataString like '%[[filter]]%' and dataInt > {{id}}
