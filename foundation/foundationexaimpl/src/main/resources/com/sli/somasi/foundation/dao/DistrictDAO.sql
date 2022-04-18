/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Aug 4, 2019
 */

--listByCity
select * from somasi_district where cityid = {{id}}
order by name asc

--listDistrictByKodeArea
select d.* from somasi_district d 
left join somasi_mappingarea m on m.districtid = d.districtid
where m.codearea =  {{idMapping}}