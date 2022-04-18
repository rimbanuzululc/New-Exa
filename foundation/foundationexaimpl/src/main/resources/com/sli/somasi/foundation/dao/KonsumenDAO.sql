--listKonsumen
select
k.*
from backup_konsumen k
limit 20;

--seacrhNama
select
k.*,
a.assigndate as assignDate
from backup_konsumen k
left join somasi_assign a on k.idkonsumen = a.idkonsumen
where k."name" ilike '%[[param]]%' order by k.idkonsumen asc
limit [[rowPerPage]] offset [[start]];

--seacrhRef
select
k.*,
a.assigndate
from backup_konsumen k
left join somasi_assign a on k.idkonsumen = a.idkonsumen
where k.ref_no ilike '%[[param]]%' order by k.idkonsumen asc
limit [[rowPerPage]] offset [[start]];

--seacrhCard
select
k.*,
a.assigndate
from backup_konsumen k
left join somasi_assign a on k.idkonsumen = a.idkonsumen
where k.cardno ilike '%[[param]]%' order by k.idkonsumen asc
limit [[rowPerPage]] offset [[start]];

--seacrhStatus
select
k.*,
a.assigndate
from backup_konsumen k
left join somasi_assign a on k.idkonsumen = a.idkonsumen
where k.statuspengiriman ilike '%[[param]]%' order by k.idkonsumen asc 
limit [[rowPerPage]] offset [[start]];

--getById
select
k.*
from backup_konsumen k
where k.idkonsumen = {{id}}