--searchMenu
select 
    * 
from 
    somasi_menu 
where
    title like '%[[titleFilter]]%'
    [[parentId]]
order by title asc
limit [[rowPerPage]] offset [[start]]


--listAllMenu
select 
    * 
from 
    somasi_menu 

--updateMenu
update somasi_menu
set
    title = {{title}},
    target = {{target}},
    parentId = {{parentId}},
    displayOrder = {{displayOrder}},
    icon = {{icon}} 
where  
    menuId = {{menuId}}