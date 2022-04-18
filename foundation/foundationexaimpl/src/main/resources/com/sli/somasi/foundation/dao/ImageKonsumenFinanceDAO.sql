/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Aug 29, 2019
 */

--checkImage
select i.* from somasi_imagekonsumenfinance i 
where konsumenid = {{id}}

--checkKonsumenImage
select * from somasi_imagekonsumenfinance
where konsumenid = {{konsumenId}} and imagename = {{imageName}}

--listbykonsumen
select distinct  i.imagename, i.imagepath , i.konsumenid
from somasi_imagekonsumenfinance i where konsumenid = {{id}}