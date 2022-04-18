/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Aug 4, 2019
 */

--listByDistrict
select * from somasi_subdistrict where districtid = {{id}}
order by name asc