--search
select
    *
from
    somasi_user
where
    name like '%[[filter]]%'
order by name asc
limit [[rowPerPage]] offset [[start]]


--hakAkses
select 
	m.menuid as id, 
	m.title as title,
	m.icon,
	m.target, 
	usr.userid
from somasi_role r 
  join somasi_rolemenu rm on r.roleid = rm.roleid
  join somasi_menu m on   m.menuid = rm.menuid
	join somasi_user usr on usr.roleid = r.roleid
where usr.userid = '[[userId]]'

--subMenu
select 
	m.menuid as idParent, 
	m.title as title,
	m.icon,
	m.target, 
	usr.userid,
	m.parentid
from somasi_role r 
  join somasi_rolemenu rm on r.roleid = rm.roleid
  join somasi_menu m on   m.menuid = rm.menuid
	join somasi_user usr on usr.roleid = r.roleid
where 
usr.userid = '[[userId]]' and
m.parentid = [[parentId]]

--listAll
select 
su.*,
sr."name" as roleName
from somasi_user su 
left join somasi_role sr on su.roleid = sr.roleid 

--getById
select *
from login 
where userid = '{{userId}}'
